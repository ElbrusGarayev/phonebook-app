package com.gara.ibasestep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gara.ibasestep.model.request.AddUserRequest;
import com.gara.ibasestep.model.request.UpdateUserRequest;
import com.gara.ibasestep.model.response.UserOperationResponse;
import com.gara.ibasestep.model.response.UserResponse;
import com.gara.ibasestep.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static com.gara.ibasestep.enums.OperationStatus.SUCCESS;
import static com.gara.ibasestep.enums.OperationType.ADD;
import static com.gara.ibasestep.enums.OperationType.DELETE;
import static com.gara.ibasestep.enums.OperationType.EDIT;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserOperationResponse userOperationResponse;
    private UpdateUserRequest updateUserRequest;
    private AddUserRequest addUserRequest;

    @BeforeEach
    void setUp() {
        setUpUserOperationResponse();
        setUpUpdateUserRequest();
        setUpAddUserRequest();
    }

    @Test
    void testGetUser() throws Exception {
        when(this.userService.findById(anyInt())).thenReturn(new UserResponse(123, "Name", "4105551212"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/api/{userId}", 123);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"userId\":123,\"name\":\"Name\",\"phone\":\"4105551212\"}"));
    }

    @Test
    void testGetUsers() throws Exception {
        // arrange
        when(userService.getUsers()).thenReturn(new ArrayList<>());

        // act & assert
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/api");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAddUser() throws Exception {
        // arrange
        userOperationResponse.setOperationType(ADD);
        when(userService.addUser(any())).thenReturn(userOperationResponse);

        // act & assert
        String content = (new ObjectMapper()).writeValueAsString(addUserRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"userId\":123,\"operationType\":\"ADD\",\"operationStatus\":\"SUCCESS\"}"));
    }

    @Test
    void testDeleteUser() throws Exception {
        // arrange
        userOperationResponse.setOperationType(DELETE);
        when(userService.deleteUser(anyInt())).thenReturn(userOperationResponse);

        // act & assert
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/api/{userId}", 123);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"userId\":123,\"operationType\":\"DELETE\",\"operationStatus\":\"SUCCESS\"}"));
    }

    @Test
    void testUpdateUser() throws Exception {
        // arrange
        userOperationResponse.setOperationType(EDIT);
        when(userService.updateUser(anyInt(), any())).thenReturn(userOperationResponse);

        // act & assert
        String content = (new ObjectMapper()).writeValueAsString(updateUserRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/api/{userId}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"userId\":123,\"operationType\":\"EDIT\",\"operationStatus\":\"SUCCESS\"}"));
    }

    private void setUpUserOperationResponse() {
        userOperationResponse = UserOperationResponse.builder()
                .operationStatus(SUCCESS)
                .userId(123)
                .build();
    }

    private void setUpUpdateUserRequest() {
        updateUserRequest = UpdateUserRequest.builder()
                .phone("123456")
                .name("Name")
                .build();
    }

    private void setUpAddUserRequest() {
        addUserRequest = AddUserRequest.builder()
                .phone("123456")
                .name("Name")
                .build();
    }

}

