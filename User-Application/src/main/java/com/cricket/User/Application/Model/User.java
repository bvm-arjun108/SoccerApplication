package com.cricket.User.Application.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "First Name is required")
    @Valid
    private String firstName;
    @NotNull(message = "Last Name is required")
    @Valid
    private String lastName;
    @NotNull(message = "Username is required")
    @Size(min = 5, message = "Username is not provided or too short")
    @Column(unique = true)
    @Valid
    private String username;
    @NotNull(message = "Email is required")
    @Email(message = "Email is not valid")
    @Column(unique = true)
    @Valid
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 10, message = "Password must be at least 10 characters long")
    @Pattern.List({
            @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one numeric character"),
            @Pattern(regexp = ".*[!@#$%^&*].*", message = "Password must contain at least one special symbol")
    })
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // to make password not null during request payload
    @Valid
    private String password;
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters long")
    private String phone;
    private String address;
    private String teamName;

    @JsonIgnore // to ignore password during response payload
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("username", username)
                .append("email", email)
                .append("phone", phone)
                .append("address", address)
                .append("teamName", teamName)
                .toString();
    }
}
