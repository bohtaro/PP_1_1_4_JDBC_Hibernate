package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS Users " +
                "(Id INT PRIMARY KEY AUTO_INCREMENT, " +
                "Name VARCHAR(50), " +
                "LastName VARCHAR(50), " +
                "Age INT)";

        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS users";

        Query query = session.createSQLQuery(sql);

        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "INSERT INTO Users (Name, LastName, Age) Values (?, ?, ?)";

        Query query = session.createSQLQuery(sql);
        query.setParameter(1, name);
        query.setParameter(2, lastName);
        query.setParameter(3, age);

        query.executeUpdate();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DELETE FROM Users WHERE ID = ?";

        Query query = session.createSQLQuery(sql);
        query.setParameter(1, id);

        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User").list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "TRUNCATE TABLE Users";

        Query query = session.createSQLQuery(sql);

        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
