package com.ervingorospe.grab_booking_service.service.booking;

import com.ervingorospe.grab_booking_service.constant.BookingStatus;
import com.ervingorospe.grab_booking_service.handler.error.BookingCancellationException;
import com.ervingorospe.grab_booking_service.handler.error.BookingNotFoundException;
import com.ervingorospe.grab_booking_service.handler.error.CustomAccessDeniedException;
import com.ervingorospe.grab_booking_service.model.DTO.BookingDTO;
import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
import com.ervingorospe.grab_booking_service.model.DTO.CustomerDTO;
import com.ervingorospe.grab_booking_service.model.entity.Booking;
import com.ervingorospe.grab_booking_service.model.entity.Customer;
import com.ervingorospe.grab_booking_service.repository.BookingRepository;
import com.ervingorospe.grab_booking_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(CustomerRepository customerRepository, BookingRepository bookingRepository) {
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    @PreAuthorize("@securityService.isOwnerOfAccount(#bookingRequestDTO)")
    public BookingDTO save(BookingRequestDTO bookingRequestDTO) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(bookingRequestDTO.customer().email());

        Customer customer = existingCustomer.orElseGet(() -> {
            Customer newCustomer = new Customer(bookingRequestDTO);
            return customerRepository.save(newCustomer);
        });

        Booking booking = new Booking(
            UUID.randomUUID().toString(),
            customer.getId(),
            new Customer(bookingRequestDTO),
            bookingRequestDTO.pickupLocation(),
            bookingRequestDTO.dropOffLocation(),
            LocalDateTime.now(),
            BookingStatus.PENDING.toString()
        );

        return new BookingDTO(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    @PreAuthorize("@securityService.isOwnerOfAccount(#id) && @securityService.isClient(#id)")
    public BookingDTO cancelBooking(String id) {
        Booking booking = this.findBookingById(id);

        if (booking.getPickupTime() == null && booking.getArrivalTime() == null) {
            changeStatus(booking, BookingStatus.CANCELED); // Assuming you meant CANCELED, not PENDING
            return new BookingDTO(bookingRepository.save(booking));
        }

        throw new BookingCancellationException("Booking cannot be cancelled as the trip has already started.");
    }

    @Override
    @PreAuthorize("@securityService.isOwnerOfAccount(#customerDTO) && @securityService.isDriver(#customerDTO)")
    public BookingDTO acceptBooking(CustomerDTO customerDTO, String id) {
        Booking booking = this.findBookingById(id);
        Customer driver = new Customer(customerDTO);

        if (booking.getCustomer().getId().equals(driver.getId())) {
            throw new CustomAccessDeniedException("Can't book own booking");
        }

        booking.setDriver(driver);
        booking.setDriverId(driver.getId());

        return new BookingDTO(bookingRepository.save(booking));
    }

    private void changeStatus(Booking booking, BookingStatus status) throws IllegalArgumentException {
        booking.setStatus(status.toString());
    }

    private Booking findBookingById(String id) {
        return bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
    }
}
