package com.ervingorospe.grab_booking_service.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record BookingRequestDTO(
    @NotNull(message = "Customer must not be empty")
    @Valid
    CustomerDTO customer,

    @NotNull(message = "Pickup location must not be empty")
    @JsonProperty("pickup_location")
    @Valid
    AddressDTO pickupLocation,

    @NotNull(message = "Dropoff location must not be empty")
    @JsonProperty("drop_off_location")
    @Valid
    AddressDTO dropOffLocation
) {
}
