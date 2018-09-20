/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * Verzorgt het weergeven van berichten.
 *
 */
@Component
public class MessageUtil {

    private static final String REDIRECT = "flash.";
    private static final String MESSAGES = "infoMessages";

    @Autowired
    private MessageSource       messageSource;

    /**
     * Deze zorgt voor het weergeven van een bericht, wanneer een bericht moet worden weergegeven na een redirect wordt
     * het bericht in de flashScope geplaatst.
     *
     * @param severity
     *            de bericht niveau van het bericht
     * @param code
     *            de code voor het bericht
     * @param args parameters voor het bericht
     * @param redirect true wanneer het bericht uitgelezen moet worden na een redirect
     */
    public void addMessage(final MessageSeverity severity, final String code, final String[] args,
            final boolean redirect)
    {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        String messageVar = MESSAGES;
        if (redirect) {
            messageVar = REDIRECT + MESSAGES;
        }

        @SuppressWarnings("unchecked")
        List<Message> messages =
            (List<Message>) requestAttributes.getAttribute(messageVar, RequestAttributes.SCOPE_REQUEST);

        if (messages == null) {
            messages = new ArrayList<Message>();
        }

        List<String> argument = new ArrayList<String>();
        if (args != null) {

            for (String arg : args) {
                argument.add(messageSource.getMessage(arg, null, arg, Locale.getDefault()));
            }
        }

        messages.add(new Message(severity,
                messageSource.getMessage(code, argument.toArray(), code, Locale.getDefault())));

        RequestContextHolder.getRequestAttributes().setAttribute(messageVar, messages, RequestAttributes.SCOPE_REQUEST);
    }

    public void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
