package com.tyler.api.service;

import com.tyler.api.dao.DatabaseUserDAO;
import com.tyler.api.models.User;

import java.util.Map;

public class UserService {
    private DatabaseUserDAO userDAO;

    public UserService() {
        this.userDAO = new DatabaseUserDAO();
    }

    public Map<String, User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    public User getUser(int id) { return userDAO.getUser(id); }
    public void addUser(String firstName, String lastName,
                        String email, String username, String password) {
        userDAO.addUser(firstName, lastName, email, username, password); }
    public void deleteUser(int id) { userDAO.deleteUser(id); }
    public void editUserPassword(int id, String newPassword) { userDAO.editUserPassword(id, newPassword); }

}
