package com.scmspain.middleware.framework.context.datasource.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.scmspain.middleware.framework.context.datasource.DoDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by josep.carne on 06/02/2017.
 */
public class ContextDataSource implements DoDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextDataSource.class);
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private static final String JDBC_URL = "jdbc:h2:mem:PRUEBA;INIT=create schema if not exists PRUEBA\\;SET SCHEMA PRUEBA;MODE=DB2;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private final DataSource dataSource;

    private ContextDataSource() {
        this.dataSource = doDataSource();
    }

    public static DoDataSource getInstance() {
        return DoDataSourceContextHolder.INSTANCE;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    protected DataSource doDataSource() {
        DataSource dataSource = null;
        try {
            dataSource = DataSourceThrowable();
        } catch (PropertyVetoException exception) {
            LOGGER.error("DataSource init error", exception);

            throw new IllegalStateException("DataSource init error", exception);
        }

        return dataSource;
    }

    private DataSource DataSourceThrowable() throws PropertyVetoException {
        final ComboPooledDataSource pool = new ComboPooledDataSource();
        pool.setUser(USERNAME);
        pool.setPassword(PASSWORD);
        pool.setDriverClass(DRIVER_CLASS);
        pool.setJdbcUrl(JDBC_URL);
        pool.setInitialPoolSize(10);
        pool.setMaxPoolSize(35);
        pool.setMinPoolSize(10);
        pool.setAcquireIncrement(1);
        pool.setAcquireRetryAttempts(5);
        pool.setAcquireRetryDelay(1000);
        pool.setAutomaticTestTable("con_test");
        pool.setCheckoutTimeout(5000);

        return pool;
    }

    private static class DoDataSourceContextHolder {
        private static final ContextDataSource INSTANCE = new ContextDataSource();
    }
}
