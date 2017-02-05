package com.scmspain.middleware.domain.view;

import java.io.IOException;

import org.rendersnake.HtmlCanvas;

import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class LoginFormImpl {

    public String doRequiredLogin(String requestedURI) throws IOException {
        final HtmlCanvas html = new HtmlCanvas();
        return html
                .html()
                .form(id("sample").action(requestedURI).method("post"))
                .label(for_(ID_USERNAME)).write("Username:")._label()
                .input(
                        id(ID_USERNAME)
                                .name(VAR_USERNAME))
                .br()
                .label(for_(ID_PASSWORD)).write("Password:")._label()
                .input(
                        type("password")
                                .id(ID_PASSWORD)
                                .name(VAR_PASSWORD))
                .br()
                .input(type("submit").value("Log me in"))
                ._form()
                .toHtml();
    }
    }

    public String doNoRequiredLogin(String requestedURI) throws IOException {
        final HtmlCanvas html = new HtmlCanvas();
        return html
                .html()
                .body()
                .h1().content("NO REQUIRED LOGIN")
                ._body()
                ._html()
                .toHtml();
    }
}
