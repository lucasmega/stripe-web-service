package com.cutconnect.services;

import com.cutconnect.domains.User;
import com.cutconnect.domains.form.FavoriteBarbershop;
import com.cutconnect.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User find(String id) {
        Optional<User> object = userRepository.findById(id);
        return object.orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        User userDB = find(user.getId());

        if (userDB == null || userDB.getId() == null) {
            throw new IllegalArgumentException("Não foi possível encontrar registros para serem atualizados");
        }

        updateData(userDB, user);
        return userRepository.save(userDB);
    }
    private void updateData(User userDB, User user) {
        userDB.setEmail(user.getEmail());
        userDB.setBarbershopId(user.getBarbershopId());
    }

    public void delete(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        User userDB = find(id);

        if (userDB == null) {
            throw new EntityNotFoundException("Usuário não encontrado para o ID: " + id);
        }

        userRepository.delete(userDB);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addFavoriteBarbershop(FavoriteBarbershop object) {

        User userDB = find(object.getEmail());

        if (userDB == null || userDB.getEmail() == null) {
            save(new User(object.getUid(), object.getEmail(),null, object.getBarbershopId()));
        }

        if (userDB != null && userDB.getEmail() != null) {
            update(new User(object.getUid(), object.getEmail(),null, object.getBarbershopId()));
        }

    }
}
