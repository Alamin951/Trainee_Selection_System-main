package com.example.JSS.service;

import com.example.JSS.dto.UsersDto;
import com.example.JSS.entity.Users;
import com.example.JSS.model.EvaluatorResponse;
import com.example.JSS.model.RegisterResponse;

import java.util.List;

public interface EvaluatorService {
    RegisterResponse createUsers(UsersDto usersDto);
    List<EvaluatorResponse> getAllEvaluator();
}
