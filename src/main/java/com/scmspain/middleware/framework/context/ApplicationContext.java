package com.scmspain.middleware.framework.context;

import com.sun.net.httpserver.HttpHandler;

import javax.sql.DataSource;

/**
 * Created by josep.carne on 07/02/2017.
 */
public interface ApplicationContext {

    DataSource getDataSource();

    HttpHandler getLoginHandler();

    HttpHandler getSessionHandler();
}
