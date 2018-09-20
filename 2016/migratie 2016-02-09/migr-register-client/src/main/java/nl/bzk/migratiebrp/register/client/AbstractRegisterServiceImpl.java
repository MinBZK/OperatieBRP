/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Basis implementatie voor blocking en caching register service.
 *
 * @param <T>
 *            regsiter type
 */
public abstract class AbstractRegisterServiceImpl<T> implements RegisterService<T>, MessageListener {

    private static final int REGISTER_MAX_LOOPS = 6;

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int DEFAULT_TIMEOUT = 10;
    private static final TimeUnit DEFAULT_TIMEOUT_UNIT = TimeUnit.SECONDS;

    private final Object lock = new Object();

    private final int timeout;
    private final TimeUnit timeoutUnit;

    private volatile CountDownLatch registerOntvangenLatch = new CountDownLatch(1);
    private volatile boolean started;
    private volatile T register;

    private JmsTemplate jmsTemplate;

    /**
     * Constructor.
     */
    protected AbstractRegisterServiceImpl() {
        this(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT_UNIT);
    }

    /**
     * Constructor.
     *
     * @param timeout
     *            timeout
     * @param timeoutUnit
     *            timeout unit
     */
    protected AbstractRegisterServiceImpl(final int timeout, final TimeUnit timeoutUnit) {
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    /**
     * Zet het JMS Template.
     * 
     * @param jmsTemplate
     *            het te zetten JMS Template
     */
    @Required
    public final void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    @Transactional(transactionManager = "registerTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public final void refreshRegister() {
        synchronized (lock) {
            LOG.info("Versturen register verzoek ({})", getClass());
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    return session.createTextMessage(maakVerzoek());
                }
            });
            started = true;
        }
    }

    /**
     * Maak het register verzoek bericht.
     *
     * @return verzoek bericht
     */
    protected abstract String maakVerzoek();

    @Override
    public final void clearRegister() {
        synchronized (lock) {
            started = false;
            register = null;
            registerOntvangenLatch = new CountDownLatch(1);
        }
    }

    @Override
    public final T geefRegister() {
        if (!started) {
            refreshRegister();
        }

        try {
            int waitLoop = 0;
            while (!registerOntvangenLatch.await(timeout, timeoutUnit)) {
                waitLoop++;
                LOG.info("Register ({}) niet ontvangen na {} {}", getClass(), waitLoop * timeout, timeoutUnit.toString());
                if (waitLoop >= REGISTER_MAX_LOOPS) {
                    throw new IllegalArgumentException("Register niet ontvangen, verwerking wordt afgebroken");
                }
            }
        } catch (final InterruptedException e) {
            throw new IllegalArgumentException("Register niet ontvangen (interrupted)", e);
        }
        return register;
    }

    @Override
    public final void onMessage(final Message message) {
        synchronized (lock) {
            try {
                LOG.info("Ontvangen register ({})", getClass());
                register = leesRegister(leesMessage(message));
                registerOntvangenLatch.countDown();
            } catch (final JMSException e) {
                LOG.warn("Kon binnenkomend bericht voor register niet verwerken. Bericht wordt genegeerd ...", e);
            }
        }
    }

    /**
     * Lees het register uit het gepubliceerde bericht.
     *
     * @param bericht
     *            bericht
     * @return register
     * @throws JMSException
     *             bij lees fouten
     */
    protected abstract T leesRegister(String bericht) throws JMSException;

    private static String leesMessage(final Message message) throws JMSException {
        if (message == null) {
            return null;
        }

        // content
        final String content;
        if (message instanceof TextMessage) {
            content = ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("Message type niet ondersteund: " + message.getClass());
        }

        return content;
    }
}
