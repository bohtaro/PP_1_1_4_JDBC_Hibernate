package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable(); //Создание таблицы User(ов)

        userService.saveUser("Жан", "Же", (byte) 21);
        userService.saveUser("Клод", "Рар", (byte) 22);
        userService.saveUser("Ван", "Де", (byte) 23);
        userService.saveUser("Дам", "Падье", (byte) 24); // Добавление 4 User(ов)

        userService.cleanUsersTable(); // Очистка таблицы User(ов)

        userService.dropUsersTable(); // Удаление таблицы
    }
}
