package com.chandan.service;

import com.chandan.exception.UserException;
import com.chandan.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUserById(Long id) throws UserException;
    User updateUser(Long id, User user) throws UserException;
    User getUserFromJwt(String jwt) throws Exception;
}
