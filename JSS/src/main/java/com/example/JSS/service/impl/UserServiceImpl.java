package com.example.JSS.service.impl;

import com.example.JSS.entity.Applicants;
import com.example.JSS.entity.Users;
import com.example.JSS.exception.UserAlreadyExistException;
import com.example.JSS.model.LoginRequest;
import com.example.JSS.model.LoginResponse;
import com.example.JSS.model.RegisterRequest;
import com.example.JSS.model.RegisterResponse;
import com.example.JSS.repository.ApplicantsRepository;
import com.example.JSS.repository.UsersRepository;
import com.example.JSS.service.JwtService;
import com.example.JSS.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository userRepository;
    private final ApplicantsRepository applicantsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    @Override
        public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new UserAlreadyExistException("USER_WITH_EMAIL_EXIST!!!");
        }
        Users users= modelMapper.map(registerRequest,Users.class);
        users.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Users newUser = userRepository.save(users);
        RegisterResponse response= new RegisterResponse();
        response.setEmail(newUser.getEmail());
        response.setMessage("Registered Successfully.");
        return response;
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
        String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(loginRequest.getEmail()));
        Users currentUser = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new EntityNotFoundException("User does not exist!!!!"));
        if(currentUser.getRole().equals("applicant")){
            Applicants currentApplicant = applicantsRepository.findByEmail(currentUser.getEmail());
            currentUser.setUserId(currentApplicant.getApplicantId());
        }

        return LoginResponse.builder()
                .token(jwtToken)
                .userId(currentUser.getUserId())
                .role(currentUser.getRole())
                .build();
    }
}
