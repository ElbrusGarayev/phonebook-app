package com.gara.ibasestep.controller;

import com.gara.ibasestep.model.request.AddUserRequest;
import com.gara.ibasestep.model.request.UpdateUserRequest;
import com.gara.ibasestep.model.response.UserOperationResponse;
import com.gara.ibasestep.model.response.UserResponse;
import com.gara.ibasestep.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("user/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable int userId) {
        return userService.findById(userId);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        log.info("getting users");
        return userService.getUsers();
    }

    @PostMapping
    public UserOperationResponse addUser(@RequestBody @Valid AddUserRequest addUserRequest) {
        log.info("adding user {}", addUserRequest);
        return userService.addUser(addUserRequest);
    }

    @DeleteMapping("{userId}")
    public UserOperationResponse deleteUser(@PathVariable int userId) {
        log.info("deleting user {}", userId);
        return userService.deleteUser(userId);
    }

    @PutMapping("{userId}")
    public UserOperationResponse updateUser(@PathVariable int userId,
                                            @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        log.info("updating user {}", updateUserRequest);
        return userService.updateUser(userId, updateUserRequest);
    }

}
