package com.smartcontractor.service;

import com.smartcontractor.model.User;
import com.smartcontractor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // Future-proof logic: Generate IDs if missing
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId(UUID.randomUUID().toString());
        }
        if (user.getAccessToken() == null || user.getAccessToken().isEmpty()) {
            user.setAccessToken(UUID.randomUUID().toString());
        }
        if (user.getUserCreatedAt() == null || user.getUserCreatedAt().isEmpty()) {
            user.setUserCreatedAt(LocalDateTime.now().toString());
        }
        if (user.getIsUserActive() == null || user.getIsUserActive().isEmpty()) {
            user.setIsUserActive("Y");
        }
        
        // Initialize lists if null to avoid NPEs
        if (user.getCompanies() == null) {
            user.setCompanies(new ArrayList<>());
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User loginUser(String userEmail, String userPass) {
        /*// Find user by email - returns List to handle duplicates gracefully
        List<User> users = userRepository.findByUserEmail(userEmail);

        if (!users.isEmpty()) {
            // Take the first one found (simulating "unique" behavior even if DB has duplicates)
            User user = users.get(0);
            
            // Check password (In real app, use BCrypt! But for now, simple string compare as requested)
            if (user.getUserPass() != null && user.getUserPass().equals(userPass)) {
                return user;
            }
            throw new RuntimeException("Password is incorrect");
        }
        throw new RuntimeException("User not found");*/


        Optional<User> userOpt = userRepository.findByUserEmail(userEmail);

        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getUserPass() != null && user.getUserPass().equals(userPass)) {
            return user;
        }

        throw new RuntimeException("Password is incorrect");
    }

    @Override
    public boolean deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(User updatedUser) {

        // 1. Fetch existing user
        User existingUser = userRepository.findById(updatedUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<User> userWithEmail = userRepository.findByUserEmail(updatedUser.getUserEmail());

        if (userWithEmail.isPresent() &&
                !userWithEmail.get().getUserId().equals(updatedUser.getUserId())) {
            throw new RuntimeException("Email already exists");
        }

        // 2. Update allowed fields
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setUserEmail(updatedUser.getUserEmail());
        existingUser.setUserPass(updatedUser.getUserPass());
        existingUser.setIsUserActive(updatedUser.getIsUserActive());
        // add other fields you want to allow updating

        // ❌ Do NOT update userId

        // 3. Save updated entity
        return userRepository.save(existingUser);
    }
}
