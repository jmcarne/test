package com.scmspain.middleware.domain.view;

import org.rendersnake.HtmlCanvas;

import java.io.IOException;

/**
 * Created by josep.carne on 06/02/2017.
 */
public class UnAuthorizedImpl {
    public String doNotAuthorized() throws IOException {
        final HtmlCanvas html = new HtmlCanvas();
        return html
                .html()
                .body()
                .h1().content("403 ERROR: YOU ARE NOT AUTHORIZED")
                ._body()
                ._html()
                .toHtml();
    }
}
