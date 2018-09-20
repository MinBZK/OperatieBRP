/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.web.servlet.tags.menu;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;


/**
 * Tag voor het weergeven van menu items en markeerd de actief item.
 *
 */
public class MenuTag extends RequestContextAwareTag implements BodyTag {

    private static final long   serialVersionUID = 883407425945694286L;

    private static final String CSS_ACTIEF       = "active";

    private BodyContent         bodyContent;

    private String              cssClass         = "";

    private String              liCssClass       = "";

    private int                 level            = 0;

    @Override
    protected int doStartTagInternal() {
        // do nothing
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public void doInitBody() {
        // do nothing
    }

    @Override
    public void setBodyContent(final BodyContent bodyContent) {
        this.bodyContent = bodyContent;
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            String content = leesBodyContent();

            String[] items = content.split("\\n");

            StringBuffer buffer = new StringBuffer();

            if ("".equals(cssClass)) {
                buffer.append("<ul>");
            } else {
                buffer.append("<ul class=\"");
                buffer.append(cssClass);
                buffer.append("\">");
            }

            for (String item : items) {
                if (StringUtils.isNotBlank(item)) {
                    String[] link = StringUtils.trim(item).split("=");
                    String label = link[0];
                    String url = link[1];

                    String activeClass = "";
                    if (isActiveItem(url)) {
                        activeClass = CSS_ACTIEF;
                    }

                    buffer.append("<li class=\"");
                    if (!"".equals(liCssClass)) {
                        buffer.append(liCssClass + " ");
                    }
                    buffer.append(activeClass);
                    buffer.append("\">");
                    buffer.append("<a href=\"");
                    buffer.append(url);
                    buffer.append("\">");
                    buffer.append(label);
                    buffer.append("</a>");

                    buffer.append("</li>");
                }
            }

            buffer.append("</ul>");

            schrijfBodyContent(buffer.toString());
        } catch (IOException ex) {
            throw new JspException("Could not write escaped body", ex);
        }
        return SKIP_BODY;
    }

    public void setCssClass(final String cssClass) {
        this.cssClass = cssClass;
    }

    public void setLiCssClass(final String liCssClass) {
        this.liCssClass = liCssClass;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    /**
     * Lees de unescaped body content van de pagina.
     *
     * @return de content
     * @throws IOException
     *             exceptie als het lezen fout gaat
     */
    protected String leesBodyContent() throws IOException {
        return bodyContent.getString();
    }

    /**
     * Schrijf de escaped body content naar de pagina.
     *
     * @param content
     *            de te schrijven content
     * @throws IOException
     *             als schrijven fout gaat
     */
    protected void schrijfBodyContent(final String content) throws IOException {
        bodyContent.getEnclosingWriter().print(content);
    }

    /**
     * Checkt of de url de active url is.
     *
     * @param url de url
     * @return true als menu item url deels of volledige overkeenkomt met de requestUri.
     */
    private boolean isActiveItem(final String url) {
        String requestUri = haalLaatsteStukVanUrlWeg(getRequestContext().getRequestUri());
        String bUrl = haalLaatsteStukVanUrlWeg(url);

        return requestUri.equalsIgnoreCase(bUrl);
    }

    /**
     * Haal de laatste stuk van het url eraf.
     *
     * @param url url om te bewerken
     * @return afhakte string voor de vergelijking.
     */
    private String haalLaatsteStukVanUrlWeg(final String url) {
        String[] splitUrl = url.split("/");

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < splitUrl.length - level; i++) {
            buf.append(splitUrl[i]);
        }

        return buf.toString();
    }

}
