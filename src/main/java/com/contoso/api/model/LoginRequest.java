package com.contoso.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Username should not be null or empty")
    @Min(value = 3, message = "Username named should not be less than 3 character")
    private String userName;

    @NotEmpty(message = "Password should not be null or empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$",
            message = "Password must be of minimum 6 character," +
                    "consist of At least one upper case,one lower case,one digit and one special character")
    private String password;
}
