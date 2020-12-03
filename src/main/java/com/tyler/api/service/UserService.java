package com.tyler.api.service;

import com.tyler.api.dao.DatabaseUserDAO;
import com.tyler.api.exceptions.UserNotFoundException;
import com.tyler.api.models.User;

import java.sql.SQLException;
import java.util.Map;

public class UserService {
    private DatabaseUserDAO userDAO;

    public UserService() {
        this.userDAO = new DatabaseUserDAO();
    }

    public Map<Integer, User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    public User getUser(int id) throws UserNotFoundException { return userDAO.getUser(id); }
    public User addUser(String firstName, String lastName,
                        String email, String username, String password) {
        return userDAO.addUser(firstName, lastName, email, username, password); }
    public void deleteUser(int id) { userDAO.deleteUser(id); }
    public User editUserPassword(int id, String newPassword) throws UserNotFoundException {
        userDAO.getUser(id);
        return userDAO.editUserPassword(id, newPassword);
    }

    public User getUser(String username) throws UserNotFoundException, SQLException {
        return userDAO.getUser(username);
    }
    public boolean authenticateVersionOne(String username, String password) { return userDAO.authenticateVersionOne(username, password); }

    public User flipAdmin(int id) throws UserNotFoundException {
        userDAO.getUser(id);
        return userDAO.flipAdmin(id);
    }
}
