package com.chandan.service.impl;

import com.chandan.exception.UserException;
import com.chandan.model.User;
import com.chandan.payload.dto.KeycloakUserDTO;
import com.chandan.repository.UserRepository;
import com.chandan.service.KeycloakService;
import com.chandan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isPresent()) {
            return otp.get();
        }
        throw new UserException("User not Found");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isEmpty()) {
            throw new UserException("user not found with id" +id);

        }
        userRepository.deleteById(otp.get().getId());

    }

    @Override
    public User updateUser(Long id, User user) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isEmpty()) {
            throw new UserException("user not found with id" +id);
        }
        User existingUser = otp.get();
        existingUser.setId(id);
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        existingUser.setUsername(user.getUsername());
        return userRepository.save(existingUser);
    }

    @Override
    public User getUserFromJwt(String jwt) throws Exception {
        KeycloakUserDTO keycloakUserDTO=keycloakService.fetchUserProfileByJwt(jwt);
        User user=userRepository.findByEmail(keycloakUserDTO.getEmail());
        return user;
    }
}
