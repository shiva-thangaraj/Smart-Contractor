package com.smartcontractor.service;

import com.smartcontractor.model.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User loginUser(String userEmail, String userPass);
    boolean deleteUser(String userId);
    User getUserById(String userId);
    User updateUser(User user);
}
