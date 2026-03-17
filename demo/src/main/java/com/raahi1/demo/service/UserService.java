package com.raahi1.demo.service;

import com.raahi1.demo.dto.UserRegistrationDTO;
import com.raahi1.demo.models.User;
import com.raahi1.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserRegistrationDTO dto) {
        if (userRepository.existsByMobile(dto.getMobile())) {
            throw new IllegalArgumentException("Mobile number already registered.");
        }
        User user = new User();
        user.setFullName(dto.getFullname());
        user.setMobile(dto.getMobile());
        user.setBuilding(dto.getBuilding());
        user.setStreet(dto.getStreet());
        user.setState(dto.getState());
        user.setCity(dto.getCity());
        user.setCountry(dto.getCountry() != null ? dto.getCountry() : "India");
        user.setContact1(dto.getContact1());
        user.setContact2(dto.getContact2());
        user.setContact3(dto.getContact3());
        return userRepository.save(user);
    }
    public User getUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No account found for this number."));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}