package com.scmspain.middleware.domain.model.dao;

import com.scmspain.middleware.domain.model.Resource;
import com.scmspain.middleware.framework.context.database.DatabaseAccess;
import com.scmspain.middleware.framework.context.database.impl.DataBaseAccessImpl;
import com.scmspain.middleware.framework.context.web.ApplicationWebContext;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by josep.carne on 07/02/2017.
 */
public class ResourceDao {
    private static final String USER = "USER";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ROLE = "ROLE";

    public Resource findByCode(String accountCode) {
        final DataSource dataSource = ApplicationWebContext.getInstance().getDataSource();
        final DatabaseAccess dataBaseAccess = new DataBaseAccessImpl(dataSource);

        final List<Map<String, String>> results =
                dataBaseAccess.executeQuery("SELECT * FROM USER WHERE USER = ?",
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
                            preparedStatement.setString(1, accountCode);
                        });

        Resource account = null;
        if (!results.isEmpty()) {
            final Map<String, String> row = results.get(0);

            account = new Resource(row.get(USER), row.get(NAME),
                    row.get(SURNAME), null, row.get(ROLE));
        }

        return account;
    }

    public void create(Resource resource) {
        final DataSource dataSource = ApplicationWebContext.getInstance().getDataSource();
        final DataBaseAccessImpl dataBaseAccess = new DataBaseAccessImpl(dataSource);

        dataBaseAccess.executeUpdate(
                "INSERT INTO USER VALUES (?, ?, ?, ?, ?)",
                preparedStatement -> {
                    preparedStatement.setString(1, resource.getCode());
                    preparedStatement.setString(2, resource.getName());
                    preparedStatement.setString(3, resource.getSurname());
                    preparedStatement.setString(4, resource.getPassword());
                    preparedStatement.setString(5, resource.getRole());
                });

    }

    public void deleteByCode(String user) {
        final DataSource dataSource = ApplicationWebContext.getInstance().getDataSource();
        final DataBaseAccessImpl dataBaseAccess = new DataBaseAccessImpl(dataSource);

        dataBaseAccess.executeUpdate("DELETE FROM USER WHERE USER = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, user);
                });

    }

    public Resource findByCodeAndPassword(String username, String password) {
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
                            preparedStatement.setString(1, username);
                            preparedStatement.setString(2, password);
                        });

        Resource account = null;
        if (!results.isEmpty()) {
            final Map<String, String> row = results.get(0);

            account = new Resource(row.get(USER), row.get(NAME),
                    row.get(SURNAME), null, row.get(ROLE));
        }

        return account;
    }
}
