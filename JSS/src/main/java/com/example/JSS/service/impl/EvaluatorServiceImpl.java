package com.example.JSS.service.impl;

import com.example.JSS.dto.UsersDto;
import com.example.JSS.entity.Users;
import com.example.JSS.model.EvaluatorResponse;
import com.example.JSS.model.RegisterRequest;
import com.example.JSS.model.RegisterResponse;
import com.example.JSS.repository.UsersRepository;
import com.example.JSS.service.EvaluatorService;
import com.example.JSS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluatorServiceImpl implements EvaluatorService {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final UsersRepository usersRepository;

    @Override
    public RegisterResponse createUsers(UsersDto usersDto) {
        usersDto.setRole("evaluator");
        RegisterRequest registerRequest= modelMapper.map(usersDto, RegisterRequest.class);
        return userService.registerUser(registerRequest);
    }

    @Override
    public List<EvaluatorResponse> getAllEvaluator() {
        List<Users> evaluators = usersRepository.findByRole("evaluator");
        return evaluators.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    private EvaluatorResponse convertToResponse(Users user) {
        return modelMapper.map(user, EvaluatorResponse.class);
    }
}
