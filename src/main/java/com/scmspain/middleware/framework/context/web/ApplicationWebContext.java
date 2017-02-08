package com.scmspain.middleware.framework.context.web;

import com.scmspain.middleware.framework.context.ApplicationContext;
import com.scmspain.middleware.framework.context.datasource.impl.ContextDataSource;
import com.scmspain.middleware.framework.http.handle.HttpHandler;
import com.scmspain.middleware.framework.http.handle.LoginHandle;
import com.scmspain.middleware.framework.http.handle.SessionHandler;

import javax.sql.DataSource;

/**
 * Created by josep.carne on 07/02/2017.
 */
public class ApplicationWebContext implements ApplicationContext {

    private final DataSource dataSource;
    private final HttpHandler sessionHandler;
    private final HttpHandler loginHandler;


    private ApplicationWebContext() {
        this.dataSource = ContextDataSource.getInstance().getDataSource();
        this.sessionHandler = new SessionHandler();
        this.loginHandler = new LoginHandle(sessionHandler);
    }

    public static ApplicationContext getInstance() {
        return ApplicationWebContextHolder.INSTANCE;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public HttpHandler getLoginHandler() {
        return this.loginHandler;
    }

    @Override
    public HttpHandler getSessionHandler() {
        return this.sessionHandler;
    }

    private static class ApplicationWebContextHolder {
        private static final ApplicationContext INSTANCE = new ApplicationWebContext();
    }
}
