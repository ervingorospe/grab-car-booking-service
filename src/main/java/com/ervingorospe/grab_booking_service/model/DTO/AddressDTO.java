package com.ervingorospe.grab_booking_service.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(
    @NotNull(message = "Street can't be empty")
    String street,

    @NotNull(message = "City can't be empty")
    String city,

    @NotNull(message = "State can't be empty")
    String state,

    @NotNull(message = "Postalcode can't be empty")
    @JsonProperty("postal_code")
    String postalCode,

    @NotNull(message = "Country can't be empty")
    String country,

    @NotNull(message = "Latitude can't be empty")
    double latitude,

    @NotNull(message = "Longitude can't be empty")
    double longitude
) {
}
