package com.gara.ibasestep.service.impl;

import com.gara.ibasestep.dao.entity.User;
import com.gara.ibasestep.dao.repository.UserRepository;
import com.gara.ibasestep.enums.OperationStatus;
import com.gara.ibasestep.enums.OperationType;
import com.gara.ibasestep.model.request.AddUserRequest;
import com.gara.ibasestep.model.request.UpdateUserRequest;
import com.gara.ibasestep.model.response.UserOperationResponse;
import com.gara.ibasestep.model.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    
    private static final int ID = 1;
    private static final int SIZE = 1;
    private static final String NAME = "John Doe";
    private static final String PHONE = "4105551213";
    
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    private User user;
    private UpdateUserRequest updateUserRequest;

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpUpdateUserRequest();
    }

    @Test
    void getUsersThenSuccessResult() {
        // arrange
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // act
        List<UserResponse> actualUsers = userServiceImpl.getUsers();

        // assert
        assertEquals(SIZE, actualUsers.size());
        UserResponse getResult = actualUsers.get(0);
        assertEquals(NAME, getResult.getName());
        assertEquals(ID, getResult.getUserId());
        assertEquals(PHONE, getResult.getPhone());
        verify(userRepository).findAll();
    }

    @Test
    void addUserThenSuccessResult() {
        // arrange
        when(userRepository.save(any())).thenReturn(user);

        // act
        UserOperationResponse actualAddUserResult = userServiceImpl.addUser(new AddUserRequest(NAME, PHONE));

        // assert
        assertEquals(OperationStatus.SUCCESS, actualAddUserResult.getOperationStatus());
        assertEquals(ID, actualAddUserResult.getUserId());
        assertEquals(OperationType.ADD, actualAddUserResult.getOperationType());
        verify(userRepository).save(any());
        assertTrue(userServiceImpl.getUsers().isEmpty());
    }

    @Test
    void addUserThenFailResult() {
        // arrange
        when(userRepository.save(any())).thenReturn(user);

        // act
        UserOperationResponse actualAddUserResult = userServiceImpl.addUser(null);

        // assert
        assertEquals(OperationStatus.FAILED, actualAddUserResult.getOperationStatus());
        assertEquals(OperationType.ADD, actualAddUserResult.getOperationType());
    }

    @Test
    void updateUserThenSuccessResult() {
        // arrange
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // act
        UserOperationResponse actualUpdateUserResult = userServiceImpl.updateUser(ID, updateUserRequest);

        // assert
        assertEquals(OperationStatus.SUCCESS, actualUpdateUserResult.getOperationStatus());
        assertEquals(ID, actualUpdateUserResult.getUserId());
        assertEquals(OperationType.EDIT, actualUpdateUserResult.getOperationType());
        verify(userRepository).findById(any());
        verify(userRepository).save(any());
        assertTrue(userServiceImpl.getUsers().isEmpty());
    }

    @Test
    void updateUserThenFailResult() {
        // arrange
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // act
        UserOperationResponse actualUpdateUserResult = userServiceImpl.updateUser(ID, updateUserRequest);

        // assert
        assertEquals(OperationStatus.FAILED, actualUpdateUserResult.getOperationStatus());
        assertEquals(OperationType.EDIT, actualUpdateUserResult.getOperationType());
        verify(userRepository).findById(any());
        assertTrue(userServiceImpl.getUsers().isEmpty());
    }

    @Test
    void deleteUserThenSuccessResult() {
        // arrange
        doNothing().when(userRepository).deleteById(any());

        // act
        UserOperationResponse actualDeleteUserResult = userServiceImpl.deleteUser(ID);

        // assert
        assertEquals(OperationStatus.SUCCESS, actualDeleteUserResult.getOperationStatus());
        assertEquals(ID, actualDeleteUserResult.getUserId());
        assertEquals(OperationType.DELETE, actualDeleteUserResult.getOperationType());
        verify(userRepository).deleteById(any());
        assertTrue(userServiceImpl.getUsers().isEmpty());
    }
    
    private void setUpUser() {
        user = User.builder()
                .id(ID)
                .name(NAME)
                .phone(PHONE)
                .build();
    }

    private void setUpUpdateUserRequest() {
        updateUserRequest = UpdateUserRequest.builder()
                .name(NAME)
                .phone(PHONE)
                .build();
    }

}

