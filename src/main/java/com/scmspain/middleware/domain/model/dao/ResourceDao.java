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
public class ResourceDao {
    private static final String URL_ACCESS = "URL_ACCESS";
    private static final String METHOD_ACCESS = "METHOD_ACCESS";

    public List<Resource> findURLsByUserName(String userName) {
        final DataSource dataSource = ApplicationWebContext.getInstance().getDataSource();
        final DataBaseAccessImpl dataBaseAccess = new DataBaseAccessImpl(dataSource);

        final List<Map<String, String>> results = dataBaseAccess.executeQuery(""
                        + "SELECT APP_RES.URL_PATTERN, APP_RES.HTTP_METHOD FROM APPLICATION_ROLE APP_ROLE "
                        + "INNER JOIN APPLICATION_RESOURCE_APPLICATION_ROLE APP_RES_APP_ROLE ON APP_ROLE.CODE = APP_RES_APP_ROLE.APPLICATION_ROLE_CODE "
                        + "INNER JOIN APPLICATION_RESOURCE APP_RES ON APP_RES.URL_PATTERN = APP_RES_APP_ROLE.APPLICATION_RESOURCE_URL_PATTERN "
                        + "INNER JOIN ACCOUNT ACC ON ACC.APPLICATION_ROLE_CODE = APP_ROLE.CODE "
                        + "WHERE ACC.CODE = ? ",
                answer ->
                {
                    final List<Map<String, String>> result = new ArrayList<>();
                    while (answer.next()) {
                        final Map<String, String> row = new HashMap<>();
                        String urlPatternValue = answer.getString(URL_ACCESS);
                        String httpMethodValue = answer.getString(METHOD_ACCESS);
                        row.put(URL_ACCESS, urlPatternValue);
                        row.put(METHOD_ACCESS, httpMethodValue);
                        result.add(row);
                    }

                    return result;
                },
                preparedStatement -> {
                    preparedStatement.setString(1, userName);
                });

        final List<Resource> applicationResources = new ArrayList<>();
        if (!results.isEmpty()) {
            results.forEach(row ->
            {
                Resource resource =
                        new Resource(row.get(URL_ACCESS), row.get(METHOD_ACCESS));
                applicationResources.add(resource);
            });
        }

        return applicationResources;
    }
}
