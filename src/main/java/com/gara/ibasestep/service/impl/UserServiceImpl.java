package com.gara.ibasestep.service.impl;

import com.gara.ibasestep.dao.entity.User;
import com.gara.ibasestep.dao.repository.UserRepository;
import com.gara.ibasestep.enums.OperationStatus;
import com.gara.ibasestep.enums.OperationType;
import com.gara.ibasestep.model.request.AddUserRequest;
import com.gara.ibasestep.model.request.UpdateUserRequest;
import com.gara.ibasestep.model.response.UserOperationResponse;
import com.gara.ibasestep.model.response.UserResponse;
import com.gara.ibasestep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gara.ibasestep.enums.OperationStatus.FAILED;
import static com.gara.ibasestep.enums.OperationStatus.SUCCESS;
import static com.gara.ibasestep.enums.OperationType.ADD;
import static com.gara.ibasestep.enums.OperationType.DELETE;
import static com.gara.ibasestep.enums.OperationType.EDIT;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserOperationResponse addUser(AddUserRequest addUserRequest) {
        try {
            User user = this.toUser(addUserRequest);
            User savedUser = userRepository.save(user);
            return buildUserOperationResponse(savedUser.getId(), ADD, SUCCESS);
        } catch (Exception e) {
            return buildUserOperationResponse(0, ADD, FAILED);
        }
    }

    @Override
    public UserOperationResponse updateUser(int userId, UpdateUserRequest updateUserRequest) {
        try {
            User user = userRepository.findById(userId).orElseThrow(null);
            user.setName(updateUserRequest.getName());
            user.setPhone(updateUserRequest.getPhone());
            userRepository.save(user);
            return buildUserOperationResponse(user.getId(), EDIT, SUCCESS);
        } catch (Exception e) {
            return buildUserOperationResponse(0, EDIT, FAILED);
        }
    }

    @Override
    public UserOperationResponse deleteUser(int userId) {
        try {
            userRepository.deleteById(userId);
            return buildUserOperationResponse(userId, DELETE, SUCCESS);
        } catch (Exception e) {
            return buildUserOperationResponse(0, DELETE, FAILED);
        }
    }

    private UserOperationResponse buildUserOperationResponse(int userId,
                                                             OperationType operationType,
                                                             OperationStatus operationStatus) {
        return UserOperationResponse.builder()
                .userId(userId)
                .operationStatus(operationStatus)
                .operationType(operationType)
                .build();
    }
    
    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .userId(user.getId())
                .phone(user.getPhone())
                .build();
    }
    
    private User toUser(AddUserRequest addUserRequest) {
        return User.builder()
                .name(addUserRequest.getName())
                .phone(addUserRequest.getPhone())
                .build();
    }

}
