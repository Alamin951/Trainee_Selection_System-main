package com.example.JSS.service;

import com.example.JSS.model.LoginRequest;
import com.example.JSS.model.LoginResponse;
import com.example.JSS.model.RegisterRequest;
import com.example.JSS.model.RegisterResponse;

public interface UserService {
    public RegisterResponse registerUser(RegisterRequest registerRequest);
    public LoginResponse loginUser(LoginRequest loginRequest);
}
