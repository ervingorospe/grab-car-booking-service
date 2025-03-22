package com.ervingorospe.grab_booking_service.service.booking;

import com.ervingorospe.grab_booking_service.constant.BookingStatus;
import com.ervingorospe.grab_booking_service.handler.error.BookingCancellationException;
import com.ervingorospe.grab_booking_service.handler.error.BookingNotFoundException;
import com.ervingorospe.grab_booking_service.model.DTO.BookingRequestDTO;
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
    public Booking save(BookingRequestDTO bookingRequestDTO) {
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

        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    @PreAuthorize("@securityService.isOwnerOfAccount(#id) && @securityService.isClient(#id")
    public Booking cancelBooking(String id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));

        if (booking.getPickupTime() == null && booking.getArrivalTime() == null) {
            changeStatus(booking, BookingStatus.CANCELED); // Assuming you meant CANCELED, not PENDING
            return bookingRepository.save(booking);
        }

        throw new BookingCancellationException("Booking cannot be cancelled as the trip has already started.");
    }

    private void changeStatus(Booking booking, BookingStatus status) throws IllegalArgumentException {
        booking.setStatus(status.toString());
    }
}
