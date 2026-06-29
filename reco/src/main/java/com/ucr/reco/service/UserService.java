package com.ucr.reco.service;

import com.ucr.reco.model.User;
import com.ucr.reco.model.dto.LoginDTO;
import com.ucr.reco.model.dto.UserDTO;
import com.ucr.reco.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserJpaRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }


    public User add(UserDTO user) {

        if (repository.existsByEmail(user.getEmail())) {
            return null;
        } else {
            if (user.getName() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null) {
                return null;
            }
        }
        User userTemp = new User(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
        return repository.save(userTemp);

    }

    public User getById(Integer id) {
        User user = repository.findById(id.intValue());
        if (user != null) {
            return user;
        }

        return null;
    }

    public User login(LoginDTO login) {
        if (repository.existsByEmail(login.getEmail()) && repository.existsByPassword(login.getPassword())) {
            User userTemp = new User(login.getEmail(), login.getPassword());
            return userTemp;

        }
        return null;
    }

    public User update(User user) {
        User userExits = repository.getByEmail(user.getEmail());
        if (userExits != null) {
            if (user.getName() != null) {
                userExits.setName(user.getName());
            }
            if (user.getPassword() != null) {
                userExits.setPassword(user.getPassword());
            }
            if (user.getRole() != null) {
                userExits.setRole(user.getRole());
            }

        } else {
            return null;
        }
        return repository.save(userExits);
    }

    public User delete(Integer id) {
        Optional<User> userExits = repository.findById(id);
        if (userExits.isPresent()) {
            repository.deleteById(id);
            return (User) userExits.get();
        } else {
            return null;
        }
    }

    public User changePassword(String email, String newPassword) {
        User userExits = repository.getByEmail(email);
        if (userExits != null) {
            userExits.setPassword(newPassword);
            return repository.save(userExits);
        } else {
            return null;
        }
    }

    public User getUserByEmail(String email){
        return repository.getByEmail(email);
    }
}
