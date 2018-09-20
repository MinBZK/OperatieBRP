/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.impl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Connection factory voor {@link nl.bzk.migratiebrp.voisc.spd.impl.SslConnectionImpl} objecten. Deze connectionfactory
 * houdt een {@link WeakReference weak referenced} list bij van aangemaakte connecties zodat een eventueel later gezet
 * wachtwoord ook doorgezet wordt naar deze connecties.
 */
public final class SslConnectionFactory implements FactoryBean<SslConnectionImpl>, ApplicationContextAware {

    private final List<WeakReference<SslConnectionImpl>> connecties = new ArrayList<>();

    private String hostname;
    private int portNumber;
    private String password;

    private ApplicationContext applicationContext;

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public SslConnectionImpl getObject() {
        final SslConnectionImpl connection = new SslConnectionImpl();
        connection.setHostname(hostname);
        connection.setPassword(password);
        connection.setPortNumber(portNumber);
        connecties.add(new WeakReference<>(connection));

        applicationContext.getAutowireCapableBeanFactory().autowireBean(connection);

        return connection;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<?> getObjectType() {
        return SslConnectionImpl.class;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

    /**
     * Zet de waarde van hostname.
     *
     * @param hostname
     *            hostname
     */
    public void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    /**
     * Zet de waarde van port number.
     *
     * @param portNumber
     *            port number
     */
    public void setPortNumber(final int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Sets the password to be used by newly created connections and sets the password in all previously created
     * connections.
     * 
     * @param password
     *            password
     */
    public void setPassword(final String password) {
        this.password = password;
        setPasswordInConnecties();
    }

    private void setPasswordInConnecties() {
        synchronized (connecties) {
            final Iterator<WeakReference<SslConnectionImpl>> connectieIterator = connecties.iterator();
            while (connectieIterator.hasNext()) {
                final SslConnectionImpl connectie = connectieIterator.next().get();
                if (connectie == null) {
                    connectieIterator.remove();
                } else {
                    connectie.setPassword(password);
                }
            }
        }
    }

}
