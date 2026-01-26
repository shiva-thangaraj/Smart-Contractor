package com.smartcontractor.controller;

import com.smartcontractor.common.ApiResponse;
import com.smartcontractor.mapper.UserMapper;
import com.smartcontractor.model.mappermodel.UserMap;
import com.smartcontractor.model.User;
import com.smartcontractor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody User user) {
        try {
            // Check if user exists (Quick check, though service could also handle this for better atomicity)
            if (userService.getAllUsers().stream().anyMatch(u -> u.getUserEmail().equals(user.getUserEmail()))) {
                 return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "User Unable to created", "User Already exist with this email Id"), HttpStatus.BAD_REQUEST);
            }
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.CREATED.value(), "User created successfully", createdUser), HttpStatus.CREATED);
        } catch (Exception e) {
             return new ResponseEntity<>(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error creating user", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Users fetched successfully", "No User has been Added till now"));
        }
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Users fetched successfully", users));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginUser(@RequestBody User user) {
        try {

            User loggedInUser = userService.loginUser(user.getUserEmail(), user.getUserPass());

            UserMap userRes = UserMapper.toLoginUserRes(loggedInUser);

            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "User Login Successful", userRes), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Distinguish errors based on message or create custom exceptions
            String msg = e.getMessage();
            if ("User not found".equals(msg)) {
                 return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "User Login Failed", "User not Found"), HttpStatus.NOT_FOUND);
            } else if ("Password is incorrect".equals(msg)) {
                 return new ResponseEntity<>(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "User Login Failed", "Password is incorrect"), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Login Failed", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable String userId) {
        boolean isDeleted = userService.deleteUser(userId);
        if (isDeleted) {
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "User deleted successfully", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "User deletion failed", "User not found"), HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getUserById(@RequestParam String userId) {
        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "User fetched successfully", user), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "User fetch failed", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(user);
            UserMap userMap = UserMapper.toLoginUserRes(updatedUser);
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "User Updated successfully", userMap), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "User Updated failed", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/all/admin")
    public ResponseEntity<ApiResponse<?>> getAllUsersForAdmin() {
        List<User> users = userService.getAllUsers();
        List<UserMap> userMapper = UserMapper.toLoginUserResList(users);
        if (userMapper.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Users fetched successfully", "No User has been Added till now"));
        }
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Users fetched successfully", userMapper));
    }




}
