package com.scmspain.middleware.framework.context;

import javax.sql.DataSource;

/**
 * Created by josep.carne on 07/02/2017.
 */
public interface ApplicationContext {

    DataSource getDataSource();
}
