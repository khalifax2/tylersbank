package com.tylersbank.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tylersbank.account.enums.GenderEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerDto {

    private String customerId;

    @NotBlank(message = "First name must not be blank")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password must not be blank")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    @NotBlank(message = "Mobile number must not be blank")
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

//    private AddressDto address;
}