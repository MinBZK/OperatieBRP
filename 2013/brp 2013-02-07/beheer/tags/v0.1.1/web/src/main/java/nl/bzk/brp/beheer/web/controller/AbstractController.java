/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.bzk.brp.beheer.web.messages.MessageSeverity;
import nl.bzk.brp.beheer.web.messages.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.js.ajax.AjaxHandler;
import org.springframework.js.ajax.SpringJavascriptAjaxHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Basis class voor controllers, deze class bevat algemene functies voor een
 * controller.
 *
 */
public abstract class AbstractController {

    /** session attribute ten behoeve identificeren van entity. */
    public static final String SESSION_ATTRIBUTE_ID        = "id";
    /** leesModus attribute ten behoeve voor het aangeven of de schermen read only view bevinden. */
    public static final String SESSION_ATTRIBUTE_LEESMODUS = "leesModus";
    /** form bean attribute. */
    public static final String SESSION_ATTRIBUTE_COMMAND   = "command";

    @Autowired
    private MessageUtil        messageUtil;

    /**
     * Helper methode om een redirect uit te voeren zonder request parameters.
     *
     * @param url
     *            locatie waarnaar ge-redirect moet worden
     * @return ModelAndView met een RedirectView
     */
    protected ModelAndView redirect(final String url) {
        RedirectView redirectView = new RedirectView(url);
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
    }

    /**
     * Handelt database exceptie af.
     *
     * @param ex
     *            de exceptie
     * @param request
     *            de request waarin aangegeven is in de header of het om een
     *            ajax call gaat.
     * @param response
     *            de response waarnaar de redirect naar geschreven wordt
     * @return een view
     * @throws IOException als redirect mislukt
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handelAfDataIntegrityViolationException(final DataIntegrityViolationException ex,
            final HttpServletRequest request, final HttpServletResponse response) throws IOException
    {
        messageUtil.addMessage(MessageSeverity.ERROR, "bericht.ietsfoutgegaan", null, true);

        String[] url = request.getRequestURI().split("/");
        return redirect(url[url.length - 1], request.getRequestURL().toString(), request, response);
    }

    /**
     * Handelt algemene exceptie af.
     *
     * @param ex
     *            de exceptie
     * @param request
     *            de request waarin aangegeven is in de header of het om een
     *            ajax call gaat.
     * @param response
     *            de response waarnaar de redirect naar geschreven wordt
     * @return een view
     * @throws IOException als redirect mislukt
     */
    @ExceptionHandler(Exception.class)
    public String handelAfException(final Exception ex, final HttpServletRequest request,
            final HttpServletResponse response) throws IOException
    {
        return redirect(request.getContextPath() + "/error.html", "/error.html", request, response);
    }

    /**
     * Deze methode zorgt de juiste afhandeling van een redirect voor een Ajax
     * request of een normale request.
     *
     * @param ajaxUrl de url voor javascript om de redirect uit te voeren. Als de URL een '/' bevat dan zal de
     *            javascript de naarde URL proberen de herleiden. Als de URL geen '/' bevat zal de javascript proberen
     *            om de pagina in te laden met ajax.
     * @param url de url voor een browser redirect wanneer de request geen ajax request is.
     * @param request de request waarin gekeken wordt of het om een ajax request gaat
     * @param response de response waarin de redirect naar geschreven wordt
     * @return null als het om een ajax request gaat en redirect url als het niet om een ajax request gaat
     * @throws IOException als redirect mislukt
     */
    private String redirect(final String ajaxUrl, final String url, final HttpServletRequest request,
            final HttpServletResponse response) throws IOException
    {
        AjaxHandler ajaxHandler = new SpringJavascriptAjaxHandler();
        if (ajaxHandler.isAjaxRequest(request, response)) {
            ajaxHandler.sendAjaxRedirect(ajaxUrl, request, response, false);
            return null;
        }

        return "redirect:" + url;
    }

    public void setMessageUtil(final MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    public MessageUtil getMessageUtil() {
        return messageUtil;
    }
}
