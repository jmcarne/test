package com.scmspain.middleware.domain.model.dao;

import com.scmspain.middleware.domain.model.Resource;

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

    }

    public void deleteByUser(String user) {

    }

    public Resource findByUserAndPassword(String user, String password) {
        return null;
    }

    public boolean findURLsByUser (String user) {
        return true;
    }
}
