package com.gara.ibasestep.service.impl;

import com.gara.ibasestep.dao.entity.User;
import com.gara.ibasestep.dao.repository.UserRepository;
import com.gara.ibasestep.model.request.AddUserRequest;
import com.gara.ibasestep.model.request.UpdateUserRequest;
import com.gara.ibasestep.model.response.UserOperationResponse;
import com.gara.ibasestep.model.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.gara.ibasestep.enums.OperationStatus.FAILED;
import static com.gara.ibasestep.enums.OperationStatus.SUCCESS;
import static com.gara.ibasestep.enums.OperationType.ADD;
import static com.gara.ibasestep.enums.OperationType.DELETE;
import static com.gara.ibasestep.enums.OperationType.EDIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final int ID = 1;
    private static final int SIZE = 1;
    private static final String NAME = "John Doe";
    private static final String PHONE = "4105551213";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
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
        assertThat(actualUsers.size()).isSameAs(SIZE);
        UserResponse getResult = actualUsers.get(0);
        assertThat(getResult.getName()).isSameAs(NAME);
        assertThat(getResult.getUserId()).isSameAs(ID);
        assertThat(getResult.getPhone()).isSameAs(PHONE);
        verify(userRepository).findAll();
    }

    @Test
    void getUserByIdThenSuccessResult() {
        // arrange
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // act
        UserResponse userResponse = userServiceImpl.findById(ID);

        // assert
        assertThat(userResponse.getName()).isSameAs(NAME);
        assertThat(userResponse.getUserId()).isSameAs(ID);
        assertThat(userResponse.getPhone()).isSameAs(PHONE);
        verify(userRepository).findById(ID);
    }

    @Test
    void addUserThenSuccessResult() {
        // arrange
        when(userRepository.save(any())).thenReturn(user);

        // act
        UserOperationResponse actualAddUserResult = userServiceImpl.addUser(new AddUserRequest(NAME, PHONE));

        // assert
        assertThat(actualAddUserResult.getOperationStatus()).isSameAs(SUCCESS);
        assertThat(actualAddUserResult.getUserId()).isSameAs(ID);
        assertThat(actualAddUserResult.getOperationType()).isSameAs(ADD);
        verify(userRepository).save(any());
        assertThat(userServiceImpl.getUsers().isEmpty()).isTrue();
    }

    @Test
    void addUserThenFailResult() {
        // act
        UserOperationResponse actualAddUserResult = userServiceImpl.addUser(null);

        // assert
        assertThat(actualAddUserResult.getOperationStatus()).isSameAs(FAILED);
        assertThat(actualAddUserResult.getOperationType()).isSameAs(ADD);
    }

    @Test
    void updateUserThenSuccessResult() {
        // arrange
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // act
        UserOperationResponse actualUpdateUserResult = userServiceImpl.updateUser(ID, updateUserRequest);

        // assert
        assertThat(actualUpdateUserResult.getOperationStatus()).isSameAs(SUCCESS);
        assertThat(actualUpdateUserResult.getUserId()).isSameAs(ID);
        assertThat(actualUpdateUserResult.getOperationType()).isSameAs(EDIT);
        verify(userRepository).findById(any());
        verify(userRepository).save(any());
    }

    @Test
    void updateUserThenFailResult() {
        // arrange
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // act
        UserOperationResponse actualUpdateUserResult = userServiceImpl.updateUser(ID, updateUserRequest);

        // assert
        assertThat(actualUpdateUserResult.getOperationStatus()).isSameAs(FAILED);
        assertThat(actualUpdateUserResult.getOperationType()).isSameAs(EDIT);
        verify(userRepository).findById(any());
    }

    @Test
    void deleteUserThenSuccessResult() {
        // arrange
        doNothing().when(userRepository).deleteById(any());

        // act
        UserOperationResponse actualDeleteUserResult = userServiceImpl.deleteUser(ID);

        // assert
        assertThat(actualDeleteUserResult.getOperationStatus()).isSameAs(SUCCESS);
        assertThat(actualDeleteUserResult.getUserId()).isSameAs(ID);
        assertThat(actualDeleteUserResult.getOperationType()).isSameAs(DELETE);
        verify(userRepository).deleteById(any());
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

