/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;

/**
 * Register bericht handler.
 */
public final class RegisterHandler implements MessageListener {

    private final Deque<Bericht> berichten = new LinkedList<>();

    @Override
    public void onMessage(final Message message) {
        final Bericht bericht = new Bericht();

        try {
            if (message instanceof TextMessage) {
                bericht.setInhoud(((TextMessage) message).getText());
            } else {
                throw new IllegalArgumentException("Onbekend bericht type: " + message.getClass().getName());
            }
        } catch (final JMSException e) {
            throw new IllegalArgumentException(e);
        }

        berichten.add(bericht);
    }

    /**
     * Geef oudste bericht.
     * @return bericht
     */
    public Bericht getBericht() {
        return berichten.removeFirst();
    }

    /**
     * Geef alle berichten.
     * @return berichten
     */
    public List<Bericht> getBerichten() {
        final List<Bericht> result = new ArrayList<>(berichten);
        berichten.clear();

        return result;
    }

}
