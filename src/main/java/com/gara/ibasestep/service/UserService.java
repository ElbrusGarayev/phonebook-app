package com.gara.ibasestep.service;

import com.gara.ibasestep.model.request.AddUserRequest;
import com.gara.ibasestep.model.request.UpdateUserRequest;
import com.gara.ibasestep.model.response.UserOperationResponse;
import com.gara.ibasestep.model.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getUsers();

    UserOperationResponse addUser(AddUserRequest addUserRequest);

    UserOperationResponse updateUser(int userId, UpdateUserRequest updateUserRequest);

    UserOperationResponse deleteUser(int userId);

}
