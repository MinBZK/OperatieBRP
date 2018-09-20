/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;

import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class AbstractAanroeper.
 *
 * @param <P> het generic type
 */
public abstract class AbstractAanroeper<P> implements Callable<System> {

    /** De Constante LOG. */
    private static final Logger                LOG                 = LoggerFactory.getLogger(AbstractAanroeper.class);

    /** De Constante TRUSTSTORE_FILENAME. */
    private static final String                TRUSTSTORE_FILENAME = "/certificaten/ca.jks";

    /** De Constante TRUSTSTORE_PASSWORD. */
    private static final String                TRUSTSTORE_PASSWORD = "hallo123";

    /** De Constante KEYSTORE_FILENAME. */
    private static final String                KEYSTORE_FILENAME   = "/certificaten/kern_soap_client.jks";

    /** De Constante KEYSTORE_PASSWORD. */
    private static final String                KEYSTORE_PASSWORD   = "hallo123";

    /** De eigenschappen. */
    private final Eigenschappen                eigenschappen;

    /** De port type. */
    private final P                            portType;

    /** De cxf client. */
    private final Client                       cxfClient;

    /** De http conduit. */
    private final HTTPConduit                  httpConduit;

    /** De Constante valueMap. */
    protected final Map<String, String> valueMap            = new HashMap<String, String>();

    /** De parameter list. */
    protected List<String>              parameterList       = new ArrayList<String>();

    /** De status. */
    private Bericht.STATUS                     status              = null;

    /**
     * Instantieert een nieuwe abstract aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param portType de port type
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    protected AbstractAanroeper(final Eigenschappen eigenschappen, final P portType,
            final Map<String, String> parameterMap) throws Exception
    {
        this.eigenschappen = eigenschappen;
        this.portType = portType;

        if (getParameterList() != null) {
            parameterList = getParameterList();
        }

        for (String parameter : parameterList) {
            if (parameterMap.containsKey(parameter)) {
                valueMap.put(parameter, parameterMap.get(parameter));
            }
        }

        InputStream in = TestClient.class.getResourceAsStream(TRUSTSTORE_FILENAME);
        File tempTruststoreFile = new File(System.getProperty("java.io.tmpdir"), "test-client-truststore.jks");

        OutputStream out = new FileOutputStream(tempTruststoreFile);
        IOUtils.copy(in, out);

        System.setProperty("javax.net.ssl.trustStore", tempTruststoreFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", TRUSTSTORE_PASSWORD);

        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put("action", "Timestamp Signature");
        outProps.put("user", "1");
        outProps.put("signaturePropFile", "sigpropfile_client.properties");
        outProps.put("passwordCallbackRef", new ServerPasswordCallback(this.eigenschappen));
        WSS4JOutInterceptor wss4JOutInterceptor = new WSS4JOutInterceptor(outProps);

        Map<String, Object> inProps = new HashMap<String, Object>();
        inProps.put("action", "Timestamp Signature");
        inProps.put("signaturePropFile", "sigpropfile_server.properties");
        WSS4JInInterceptor wss4JInInterceptor = new WSS4JInInterceptor(inProps);

        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(TestClient.class.getResourceAsStream(KEYSTORE_FILENAME), KEYSTORE_PASSWORD.toCharArray());
        KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyFactory.init(keyStore, KEYSTORE_PASSWORD.toCharArray());
        KeyManager[] km = keyFactory.getKeyManagers();
        tlsClientParameters.setKeyManagers(km);

        cxfClient = ClientProxy.getClient(portType);

        Endpoint cxfEndpoint = cxfClient.getEndpoint();
        cxfEndpoint.getOutInterceptors().add(wss4JOutInterceptor);
        cxfEndpoint.getInInterceptors().add(wss4JInInterceptor);

        httpConduit = (HTTPConduit) cxfClient.getConduit();
        httpConduit.setTlsClientParameters(tlsClientParameters);
    }

    /**
     * Roep aan.
     *
     * @param command de command
     * @return de long
     */
    public long roepAan(final AbstractSoapCommand<P, ?, ?> command) {
        long startTime = System.currentTimeMillis();
        command.voerUit(portType);
        long endTime = System.currentTimeMillis();

        if (command.isSucces()) {
            status = Bericht.STATUS.GOED;
        } else {
            status = Bericht.STATUS.FOUT;
            // throw new RuntimeException("Er is een fout opgetreden, de applicatie wordt gestopt.");
        }

        return endTime - startTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public System call() {
        fire();
        return null;
    }

    /**
     * Fire.
     */
    public abstract void fire();

    /**
     * Close.
     */
    public void close() {
        httpConduit.close();
        cxfClient.destroy();
    }

    /**
     * Haalt een eigenschappen op.
     *
     * @return eigenschappen
     */
    protected final Eigenschappen getEigenschappen() {
        return eigenschappen;
    }

    /**
     * Haalt een status op.
     *
     * @return status
     */
    public Bericht.STATUS getStatus() {
        return status;
    }

    /**
     * Bereken som.
     *
     * @param getallen de getallen
     * @return de int
     */
    protected int berekenSom(final int[] getallen) {
        int som = 0;
        for (int i = 0; i < getallen.length; i++) {
            som += getallen[i];
        }
        return som;
    }

    /**
     * Haalt een value from value map op.
     *
     * @param parameterName de parameter name
     * @param defaultValue de default value
     * @return value from value map
     */
    protected String getValueFromValueMap(final String parameterName, final String defaultValue) {
        String value = null;
        if (valueMap.containsKey(parameterName) && valueMap.get(parameterName) != null) {
            value = valueMap.get(parameterName);
        } else {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Haalt een parameter list op.
     *
     * @return parameter list
     */
    protected abstract List<String> getParameterList();

}
