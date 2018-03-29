/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.neoload;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.mailbox.client.impl.MailboxServerProxyImpl;
import nl.bzk.migratiebrp.voisc.mailbox.client.impl.SslConnectionFactory;
import nl.bzk.migratiebrp.voisc.mailbox.client.impl.SslConnectionImpl;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

/**
 * Client factory.
 */
class ClientFactory {

    public static final String CHANGEIT = "changeit";
    private final ApplicationContext configurerApplicationContext;
    private final AutowireCapableBeanFactory configurerBeanFactory;

    private String hostname;
    private int portNumber;

    public ClientFactory(final String hostname, final int portNumber) {
        configurerApplicationContext = (ApplicationContext) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{ApplicationContext.class}, new ConfigurerApplicationContext());
        configurerBeanFactory = (AutowireCapableBeanFactory) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{AutowireCapableBeanFactory.class}, new ConfigurerBeanFactory());

        this.hostname = hostname;
        this.portNumber = portNumber;
    }

    public MailboxClient getMailboxClient() {
        final SslConnectionFactory sslConnectionFactory = new SslConnectionFactory();
        sslConnectionFactory.setHostname(hostname);
        sslConnectionFactory.setPortNumber(portNumber);
        sslConnectionFactory.setKeystorePassword(CHANGEIT);
        sslConnectionFactory.setKeyPassword(CHANGEIT);
        sslConnectionFactory.setTruststorePassword(CHANGEIT);
        sslConnectionFactory.setApplicationContext(configurerApplicationContext);

        return new MailboxServerProxyImpl(sslConnectionFactory);
    }


    class ConfigurerApplicationContext implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getAutowireCapableBeanFactory".equals(method.getName()) && args == null) {
                return configurerBeanFactory;
            }
            throw new IllegalArgumentException("Aanroep niet verwacht.");
        }
    }

    class ConfigurerBeanFactory implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("autowireBean".equals(method.getName()) && args != null && args.length == 1 && args[0] instanceof SslConnectionImpl) {
                autowireSslConnectionImpl((SslConnectionImpl) args[0]);
                return null;
            }
            throw new IllegalArgumentException("Aanroep niet verwacht.");
        }


        private void autowireSslConnectionImpl(final SslConnectionImpl sslConnection) {
            final String allowedProtocols = (String) getField(SslConnectionImpl.class, "DEFAULT_ALLOWED_PROTOCOLS", null);
            final String allowedCipherSuites = (String) getField(SslConnectionImpl.class, "DEFAULT_ALLOWED_CIPHERSUITES", null);

            setField(SslConnectionImpl.class, "tlsAllowedProtocols", sslConnection, allowedProtocols.split(","));
            setField(SslConnectionImpl.class, "tlsAllowedCipherSuites", sslConnection, allowedCipherSuites.split(","));

            setField(SslConnectionImpl.class, "keystore", sslConnection, createResource("/keystores/keystore.jks"));
            setField(SslConnectionImpl.class, "truststore", sslConnection, createResource("/keystores/truststore.jks"));
        }

        private Resource createResource(final String resource) {
            return new InputStreamResource(getClass().getResourceAsStream(resource), resource);
        }

        private Object getField(final Class<?> clazz, final String fieldName, final Object instance) {
            try {
                final Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(instance);
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException(e);
            }
        }

        private void setField(final Class<?> clazz, final String fieldName, final Object instance, final Object value) {
            try {
                final Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(instance, value);
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
