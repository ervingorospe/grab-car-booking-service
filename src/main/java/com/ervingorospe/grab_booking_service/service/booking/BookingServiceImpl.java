package com.ervingorospe.grab_booking_service.service.booking;

import com.ervingorospe.grab_booking_service.constant.BookingStatus;
import com.ervingorospe.grab_booking_service.handler.error.BookingAssignmentException;
import com.ervingorospe.grab_booking_service.handler.error.BookingCancellationException;
import com.ervingorospe.grab_booking_service.handler.error.BookingNotFoundException;
import com.ervingorospe.grab_booking_service.handler.error.CustomAccessDeniedException;
import com.ervingorospe.grab_booking_service.model.DTO.BookingDTO;
import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.DTO.CustomerDTO;
import com.ervingorospe.grab_booking_service.model.DTO.CustomerResponseDTO;
import com.ervingorospe.grab_booking_service.model.entity.Booking;
import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.repository.BookingRepository;
import com.ervingorospe.grab_booking_service.repository.CustomerRepository;
import com.ervingorospe.grab_booking_service.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {
    private final CustomerService customerService;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(CustomerService customerService, BookingRepository bookingRepository) {
        this.customerService = customerService;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    @PreAuthorize("@securityService.isOwnerOfAccount(#bookingRequestDTO)")
    public BookingDTO save(BookingRequestDTO bookingRequestDTO) {
        Customer customer = customerService.save(bookingRequestDTO.customer());

        Booking booking = new Booking(
            UUID.randomUUID().toString(),
            customer.getId(),
            new Customer(bookingRequestDTO.customer()),
            bookingRequestDTO.pickupLocation(),
            bookingRequestDTO.dropOffLocation(),
            LocalDateTime.now(),
            BookingStatus.PENDING.toString()
        );

        return  new BookingDTO(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    @PreAuthorize("@securityService.isOwnerOfAccount(#id) && @securityService.isClient(#id)")
    public BookingDTO cancelBooking(String id) {
        Booking booking = this.findBookingById(id);

        if (booking.getPickupTime() == null && booking.getStatus().equals(BookingStatus.PENDING.toString())) {
            changeStatus(booking, BookingStatus.CANCELED);
            return new BookingDTO(bookingRepository.save(booking));
        }

        throw new BookingCancellationException("Booking cannot be canceled.");
    }

    @Override
    @PreAuthorize("@securityService.isOwnerOfAccount(#customerDTO) && @securityService.isDriver(#customerDTO)")
    public BookingDTO acceptBooking(CustomerDTO customerDTO, String id) {
        Booking booking = this.findBookingById(id);
        Customer driver = new Customer(customerDTO);

        if (booking.getCustomer().getId().equals(driver.getId()) || !booking.getStatus().equals(BookingStatus.PENDING.toString())) {
            throw new BookingAssignmentException("Can't accept Booking");
        }

        booking.setDriver(driver);
        booking.setDriverId(driver.getId());
        changeStatus(booking, BookingStatus.DRIVER_FOUND);

        return new BookingDTO(bookingRepository.save(booking));
    }

    @Override
    @PreAuthorize("@securityService.isTheDriver(#id)")
    public BookingDTO driverArrived(String id) {
        Booking booking = this.findBookingById(id);

        if (booking.getDriver() == null) {
            throw new BookingAssignmentException("Driver cant be empty");
        }

        changeStatus(booking, BookingStatus.DRIVER_ARRIVED);

        return new BookingDTO(bookingRepository.save(booking));
    }

    @Override
    @PreAuthorize("@securityService.isTheDriver(#id)")
    public BookingDTO pickupBooking(String id) {
        Booking booking = this.findBookingById(id);

        if (booking.getDriver() == null) {
            throw new BookingAssignmentException("Driver cant be empty");
        }

        changeStatus(booking, BookingStatus.IN_PROGRESS);
        booking.setPickupTime(LocalDateTime.now());

        return new BookingDTO(bookingRepository.save(booking));
    }

    @Override
    @PreAuthorize("@securityService.isTheDriver(#id)")
    public BookingDTO arrivedAtDestination(String id) {
        Booking booking = this.findBookingById(id);

        if (booking.getDriver() == null && !booking.getStatus().equals(BookingStatus.IN_PROGRESS.toString()) && booking.getPickupTime() == null) {
            throw new BookingAssignmentException("Booking can't be completed");
        }

        changeStatus(booking, BookingStatus.COMPLETED);
        booking.setArrivalTime(LocalDateTime.now());

        return new BookingDTO(bookingRepository.save(booking));
    }

    private void changeStatus(Booking booking, BookingStatus status) throws IllegalArgumentException {
        booking.setStatus(status.toString());
    }

    public Booking findBookingById(String id) {
        return bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
    }
}
