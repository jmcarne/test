package com.scmspain.middleware.domain.model.dao;

import com.scmspain.middleware.domain.model.Resource;
import com.scmspain.middleware.framework.context.database.impl.DataBaseAccessImpl;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class UserDao {
    private static final String USER = "USER";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ROLE = "ROLE";

    public Resource findByUser(String user) {

    }

    public void create(Resource resource) {

        final DataSource dataSource = ApplicationWebContext.getInstance().getDataSource();
        final DataBaseAccessImpl dataBaseAccess = new DataBaseAccessImpl(dataSource);

        dataBaseAccess.executeUpdate(
                "INSERT INTO ACCOUNT VALUES (?, ?, ?, ?)",
                preparedStatement -> {
                    //preparedStatement.setString(1, resource.getCode());
                    preparedStatement.setString(1, resource.getName());
                    preparedStatement.setString(2, resource.getSurname());
                    preparedStatement.setString(3, resource.getPassword());
                    preparedStatement.setString(4, resource.getRole());
                });
    }

    public void deleteByUser(String user) {
        final DataSource dataSource = ApplicationWebContext.getInstance().getDataSource();
        final DataBaseAccessImpl dataBaseAccess = new DataBaseAccessImpl(dataSource);

        dataBaseAccess.executeUpdate("DELETE FROM ACCOUNT WHERE USER = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, user);
                });

    }

    public Resource findByUserAndPassword(String user, String password) {
        final DataSource dataSource = ApplicationWebContext.getInstance().getDataSource();
        final DataBaseAccessImpl dataBaseAccess = new DataBaseAccessImpl(dataSource);

        final List<Map<String, String>> results =
                dataBaseAccess.executeQuery("SELECT * FROM USER WHERE USER = ? AND PASSWORD = ?",
                        answer ->
                        {
                            final List<Map<String, String>> result = new ArrayList<>();
                            while (answer.next()) {
                                final Map<String, String> row = new HashMap<>();
                                row.put(USER, answer.getString(USER));
                                row.put(NAME, answer.getString(NAME));
                                row.put(SURNAME, answer.getString(SURNAME));
                                row.put(PASSWORD, answer.getString(PASSWORD));
                                row.put(ROLE, answer.getString(ROLE));
                                result.add(row);
                            }

                            return result;
                        },
                        preparedStatement -> {
                            preparedStatement.setString(1, user);
                            preparedStatement.setString(2, password);
                        });

        UserDao userdao = null;
        if (!results.isEmpty()) {
            final Map<String, String> row = results.get(0);

            userdao = new UserDao(row.get(USER), row.get(NAME),
                    row.get(SURNAME), null, row.get(ROLE));
        }

        return account;
    }

    public boolean findURLsByUser (String user) {
        return true;
    }
}
