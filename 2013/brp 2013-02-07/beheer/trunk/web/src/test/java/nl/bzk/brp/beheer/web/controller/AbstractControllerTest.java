/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.controller;

import nl.bzk.brp.beheer.web.messages.MessageSeverity;
import nl.bzk.brp.beheer.web.messages.MessageUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.js.ajax.SpringJavascriptAjaxHandler;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

//CHECKSTYLE:OFF
@RunWith(MockitoJUnitRunner.class)
public class AbstractControllerTest {
//CHECKSTYLE:ON
    private AbstractController      baseController;

    @Mock
    private MessageUtil             messageUtil;

    private MockHttpServletRequest  httpServletRequest;

    private MockHttpServletResponse httpServletResponse;

    @Before
    public void setUp() {
        httpServletRequest = new MockHttpServletRequest();
        httpServletResponse = new MockHttpServletResponse();

        baseController = new AbstractController() {
        };

        baseController.setMessageUtil(messageUtil);

        httpServletRequest.setRequestURI("/web/abc.html");
    }

    /**
     * Test DataIntegrityViolationException bij een request zonder Ajax.
     *
     * @throws Exception
     */
    @Test
    public void testHandelAfDataIntegrityViolationExceptionZonderAjax() throws Exception {
        String result =
            baseController.handelAfDataIntegrityViolationException(new DataIntegrityViolationException("mag niet"),
                    httpServletRequest, httpServletResponse);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.ERROR, "bericht.ietsfoutgegaan", null,
                true);

        Assert.assertEquals("Redirect moet terug naar de request URL", "redirect:http://localhost:80/web/abc.html",
                result);
    }

    /**
     * Test DataIntegrityViolationException bij een request met Ajax.
     *
     * @throws Exception
     */
    @Test
    public void testHandelAfDataIntegrityViolationExceptionMetAjax() throws Exception {
        httpServletRequest.addHeader("Accept", SpringJavascriptAjaxHandler.AJAX_ACCEPT_CONTENT_TYPE);

        String result =
            baseController.handelAfDataIntegrityViolationException(new DataIntegrityViolationException("mag niet"),
                    httpServletRequest, httpServletResponse);

        Mockito.verify(messageUtil, Mockito.times(1)).addMessage(MessageSeverity.ERROR, "bericht.ietsfoutgegaan", null,
                true);

        Assert.assertNull("Redirect wordt direct in de response geschreven", result);
        Assert.assertEquals("Redirect url moet in header staan", "abc.html",
                httpServletResponse.getHeader(SpringJavascriptAjaxHandler.REDIRECT_URL_HEADER));
    }

    /**
     * Test Exception bij een request zonder Ajax.
     *
     * @throws Exception
     */
    @Test
    public void testHandelAfExceptionZonderAjax() throws Exception {
        String result =
            baseController.handelAfException(new Exception("mag niet"), httpServletRequest, httpServletResponse);

        Mockito.verify(messageUtil, Mockito.times(0)).addMessage(Matchers.any(MessageSeverity.class),
                Matchers.anyString(), (String[]) Matchers.anyObject(), Matchers.anyBoolean());

        Assert.assertEquals("Redirect moet terug naar de request URL", "redirect:/error.html", result);
    }

    /**
     * Test Exception bij een request met Ajax.
     *
     * @throws Exception
     */
    @Test
    public void testHandelAfExceptionMetAjax() throws Exception {
        httpServletRequest.addHeader("Accept", SpringJavascriptAjaxHandler.AJAX_ACCEPT_CONTENT_TYPE);

        String result =
            baseController.handelAfException(new Exception("mag niet"), httpServletRequest, httpServletResponse);

        Mockito.verify(messageUtil, Mockito.times(0)).addMessage(Matchers.any(MessageSeverity.class),
                Matchers.anyString(), (String[]) Matchers.anyObject(), Matchers.anyBoolean());

        Assert.assertNull("Redirect wordt direct in de response geschreven", result);
        Assert.assertEquals("Redirect url moet in header staan", "/error.html",
                httpServletResponse.getHeader(SpringJavascriptAjaxHandler.REDIRECT_URL_HEADER));
    }

}
