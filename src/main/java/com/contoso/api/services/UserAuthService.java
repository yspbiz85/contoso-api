package com.contoso.api.services;

import com.contoso.api.model.LoginRequest;
import com.contoso.api.model.SignupRequest;
import com.contoso.api.model.UserResponse;

public interface UserAuthService {

    Boolean signup(SignupRequest signupRequest);

    UserResponse login(LoginRequest loginRequest);
}
