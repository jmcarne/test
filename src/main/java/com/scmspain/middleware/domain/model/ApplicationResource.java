package com.scmspain.middleware.domain.model;

/**
 * Created by josep.carne on 07/02/2017.
 */
public class ApplicationResource {

    private final String urlPattern;
    private final String httpMethod;

    public ApplicationResource(String urlPattern, String httpMethod) {
        this.urlPattern = urlPattern;
        this.httpMethod = httpMethod;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

}
