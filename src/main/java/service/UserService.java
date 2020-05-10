package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    private static UserService userService;


    private UserService() {
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public List<User> getAllUsers() {
        ArrayList<User> usersDataBase = new ArrayList<>();
        for (long i = 0; i < maxId.longValue(); i++) {
            usersDataBase.add(getUserById(i));
        }
        return usersDataBase;
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        if (!isExistsThisUser(user)) {
            user.setId(maxId.incrementAndGet());
            return true;
        }
        if (dataBase.isEmpty()) {
            user.setId(maxId.incrementAndGet());
            dataBase.put(user.getId(), user);
            return true;
        }
        return false;
    }

    public void deleteAllUser() {
        dataBase.clear();
        maxId = new AtomicLong(0);
        authMap.clear();
    }

    public boolean isExistsThisUser(User user) {
        while (getAllUsers().iterator().hasNext()) {
            if (getAllUsers().iterator().next().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getAllAuth() {
        return new ArrayList<>(authMap.values());
    }

    public boolean authUser(User user) {
        return authMap.containsValue(user);
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        while (getAllAuth().iterator().hasNext()) {
            if (getAllAuth().contains(id));
            return true;
        }
        return false;
    }

}