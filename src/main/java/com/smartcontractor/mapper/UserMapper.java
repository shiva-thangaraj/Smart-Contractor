package com.smartcontractor.mapper;

import com.smartcontractor.model.mappermodel.UserMap;
import com.smartcontractor.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static UserMap toLoginUserRes(User user) {
        if (user == null) return null;

        UserMap res = new UserMap();
        res.setUserId(user.getUserId());
        res.setUserEmail(user.getUserEmail());
        res.setUserPass(user.getUserPass());
        res.setIsUserActive(user.getIsUserActive());
        res.setUserCreatedAt(user.getUserCreatedAt());
        res.setAccessToken(user.getAccessToken());
        return res;
    }

    public static List<UserMap> toLoginUserResList(List<User> users) {

        if (users == null) return List.of();

        return users.stream()
                .map(UserMapper::toLoginUserRes)
                .collect(Collectors.toList());
    }
}
