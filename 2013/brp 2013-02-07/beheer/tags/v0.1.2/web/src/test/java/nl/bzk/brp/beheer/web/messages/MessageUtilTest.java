/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.messages;

import java.util.List;
import java.util.Locale;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@RunWith(MockitoJUnitRunner.class)
public class MessageUtilTest {

    private MessageUtil            messageUtil;

    @Mock
    private MessageSource          messageSource;

    private MockHttpServletRequest httpRequest;

    @Before
    public void setUp() {
        httpRequest = new MockHttpServletRequest();

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpRequest));

        messageUtil = new MessageUtil();
        messageUtil.setMessageSource(messageSource);

        Mockito.when(messageSource.getMessage("code.bericht", new Object[0], "code.bericht", Locale.getDefault()))
                .thenReturn("vertaalde text");
        Mockito.when(messageSource.getMessage("code.bericht1", new Object[0], "code.bericht1", Locale.getDefault()))
                .thenReturn("vertaalde text1");
    }

    /**
     * Test berichten toevoegen.
     */
    @Test
    public void testBerichtToevoegen() {
        messageUtil.addMessage(MessageSeverity.SUCCESS, "code.bericht", null, false);

        @SuppressWarnings("unchecked")
        List<Message> berichten = (List<Message>) httpRequest.getAttribute("infoMessages");

        Assert.assertEquals("vertaalde text", berichten.get(0).getValue());
        Assert.assertEquals(MessageSeverity.SUCCESS, berichten.get(0).getSeverity());
    }

    /**
     * Test meerder berichten toevoegen.
     */
    @Test
    public void testBerichtToevoegenAanBestaandeBerichten() {
        messageUtil.addMessage(MessageSeverity.SUCCESS, "code.bericht", null, false);
        messageUtil.addMessage(MessageSeverity.ERROR, "code.bericht1", null, false);

        @SuppressWarnings("unchecked")
        List<Message> berichten = (List<Message>) httpRequest.getAttribute("infoMessages");

        Assert.assertEquals("vertaalde text", berichten.get(0).getValue());
        Assert.assertEquals(MessageSeverity.SUCCESS, berichten.get(0).getSeverity());
        Assert.assertEquals("vertaalde text1", berichten.get(1).getValue());
        Assert.assertEquals(MessageSeverity.ERROR, berichten.get(1).getSeverity());
    }

    /**
     * Test bericht toevoegen voor redirect.
     */
    @Test
    public void testBerichtToevoegenVoorRedirect() {
        messageUtil.addMessage(MessageSeverity.SUCCESS, "code.bericht", null, true);

        @SuppressWarnings("unchecked")
        List<Message> berichten = (List<Message>) httpRequest.getAttribute("flash.infoMessages");

        Assert.assertEquals("vertaalde text", berichten.get(0).getValue());
        Assert.assertEquals(MessageSeverity.SUCCESS, berichten.get(0).getSeverity());
    }

    /**
     * Test bericht toevoegen met argumenten.
     */
    @Test
    public void testBerichtenToevoegenMetArgumenten() {
        Mockito.when(messageSource.getMessage("arg1", null, "arg1", Locale.getDefault())).thenReturn("arg abc");
        Mockito.when(
                messageSource.getMessage("code.bericht", new Object[] { "arg abc" }, "code.bericht",
                        Locale.getDefault())).thenReturn("vertaalde text arg abc");

        messageUtil.addMessage(MessageSeverity.SUCCESS, "code.bericht", new String[] { "arg1" }, false);

        @SuppressWarnings("unchecked")
        List<Message> berichten = (List<Message>) httpRequest.getAttribute("infoMessages");

        Assert.assertEquals("vertaalde text arg abc", berichten.get(0).getValue());
        Assert.assertEquals(MessageSeverity.SUCCESS, berichten.get(0).getSeverity());
    }
}
