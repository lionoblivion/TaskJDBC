package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args){
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Adam", "Smith", (byte) 67);
        userService.saveUser("Karl", "Marx", (byte) 64);
        userService.saveUser("Carl", "Menger", (byte) 81);
        userService.saveUser("John", "Keynes", (byte) 62);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();




    }
}
