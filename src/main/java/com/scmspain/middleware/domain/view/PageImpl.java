package com.scmspain.middleware.domain.view;

import java.io.IOException;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class PageImpl {
    public String doPage(int number, String userName) throws IOException {
        final HtmlCanvas html = new HtmlCanvas();
        return html
                .html()
                .body()
                .h1().content("PAGE: " + number)
                .h1().content("Hello " + userName)
                .h1().a(href("/app/login/logout.html")).write("Logout")._a()._h1()
                ._body()
                ._html()
                .toHtml();
    }
}
