package com.scmspain.middleware.framework.context.web;

import com.scmspain.middleware.framework.context.ApplicationContext;
import com.scmspain.middleware.framework.context.datasource.impl.ContextDataSource;

import javax.sql.DataSource;

/**
 * Created by josep.carne on 07/02/2017.
 */
public class ApplicationWebContext implements ApplicationContext {

    private final DataSource dataSource;

    private ApplicationWebContext() {
        this.dataSource = ContextDataSource.getInstance().getDataSource();
    }

    public ApplicationWebContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ApplicationContext getInstance() {
        return ApplicationWebContextHolder.INSTANCE;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    private static class ApplicationWebContextHolder {
        private static final ApplicationContext INSTANCE = new ApplicationWebContext();
    }
}
