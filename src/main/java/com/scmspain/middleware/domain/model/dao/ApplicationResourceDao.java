package com.scmspain.middleware.domain.model.dao;

import com.scmspain.middleware.domain.model.ApplicationResource;
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
public class ApplicationResourceDao {
    private static final String URL_PATTERN = "URL_PATTERN";
    private static final String HTTP_METHOD = "HTTP_METHOD";

    public List<ApplicationResource> findURLsByUserName(String userName) {
        final DataSource dataSource = ApplicationWebContext.getInstance().getDataSource();
        final DataBaseAccessImpl dataBaseAccess = new DataBaseAccessImpl(dataSource);

        final List<Map<String, String>> results = dataBaseAccess.executeQuery(""
                        + "SELECT APP_RES.URL_PATTERN, APP_RES.HTTP_METHOD FROM ROLE ROLE "
                        + "INNER JOIN RESOURCE_ROLE ROLE ON APP_ROLE.CODE = APP_RES_APP_ROLE.APPLICATION_ROLE_CODE "
                        + "INNER JOIN APPLICATION_RESOURCE APP_RES ON APP_RES.URL_PATTERN = APP_RES_APP_ROLE.APPLICATION_RESOURCE_URL_PATTERN "
                        + "INNER JOIN USER ACC ON ACC.APPLICATION_ROLE_CODE = APP_ROLE.CODE "
                        + "WHERE ACC.CODE = ? ",
                answer ->
                {
                    final List<Map<String, String>> result = new ArrayList<>();
                    while (answer.next()) {
                        final Map<String, String> row = new HashMap<>();
                        String urlPatternValue = answer.getString(URL_PATTERN);
                        String httpMethodValue = answer.getString(HTTP_METHOD);
                        row.put(URL_PATTERN, urlPatternValue);
                        row.put(HTTP_METHOD, httpMethodValue);
                        result.add(row);
                    }

                    return result;
                },
                preparedStatement -> {
                    preparedStatement.setString(1, userName);
                });

        final List<ApplicationResource> applicationResources = new ArrayList<>();
        if (!results.isEmpty()) {
            results.forEach(row ->
            {
                ApplicationResource resource =
                        new ApplicationResource(row.get(URL_PATTERN), row.get(HTTP_METHOD));
                applicationResources.add(resource);
            });
        }

        return applicationResources;
    }
}
