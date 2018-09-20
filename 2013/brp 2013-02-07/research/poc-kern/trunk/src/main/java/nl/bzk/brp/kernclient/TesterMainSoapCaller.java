/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.kernclient;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;

import nl.brp.actie.toevoegennationaliteit.ObjectFactory;
import nl.brp.actie.toevoegennationaliteit.ToevoegenNationaliteit;
import nl.brp.basis.antwoord.StandaardAntwoord;
import nl.brp.contract.bewerkennationaliteitgeneriek.BewerkenNationaliteitGeneriekPortType;
import nl.bzk.brp.kern.service.ServerPasswordCallback;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;

public class TesterMainSoapCaller {

    private final static JaxWsProxyFactoryBean proxyFactory;
    private final static WSS4JOutInterceptor wss4JOutInterceptor;
    private final static WSS4JInInterceptor wss4JInInterceptor;
    private final static TLSClientParameters tlsClientParameters = new TLSClientParameters();

    static {
        proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(BewerkenNationaliteitGeneriekPortType.class);
        proxyFactory.setAddress(TesterMainSoap.url);

        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put("action", "Timestamp Signature");
        outProps.put("user", "serverkey");
        outProps.put("signaturePropFile", "client_sigpropfile.properties");
        outProps.put("passwordCallbackRef", new ServerPasswordCallback());
        wss4JOutInterceptor = new WSS4JOutInterceptor(outProps);


        Map<String, Object> inProps = new HashMap<String, Object>();
        inProps.put("action", "Timestamp Signature");
        inProps.put("signaturePropFile", "server_sigpropfile.properties");
        wss4JInInterceptor = new WSS4JInInterceptor(inProps);

        try {
            KeyStore keyStoree = KeyStore.getInstance("JKS");
            File keyStoreFile = new File(TesterMainSoap.keyStoreFileName);
            keyStoree.load(new FileInputStream(keyStoreFile), TesterMainSoap.keyStorePassword.toCharArray());
            KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyFactory.init(keyStoree, TesterMainSoap.keyStorePassword.toCharArray());
            KeyManager[] km = keyFactory.getKeyManagers();
            tlsClientParameters.setKeyManagers(km);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTestThread() {
        new MyThread().start();
    }

    public class MyThread extends Thread {

        @Override
        public void run() {
            while (Thread.currentThread().isAlive() && !Thread.currentThread().isInterrupted()) {
                call();
            }
        }
    }

    public void call() {
        try {
            BewerkenNationaliteitGeneriekPortType bewerkenNationaliteitGeneriekPortType =
                (BewerkenNationaliteitGeneriekPortType) proxyFactory.create();

            //BewerkenNationaliteitGeneriekService service = new BewerkenNationaliteitGeneriekService(this.getClass().getClassLoader().getResource("wsdl/BewerkenNationaliteitGeneriek.wsdl"));
            //BewerkenNationaliteitGeneriekPortType bewerkenNationaliteitGeneriekPortType = service.getBewerkenNationaliteitGeneriekPort();
            //((BindingProvider)bewerkenNationaliteitGeneriekPortType).getRequestContext().put( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getUrl() );

            org.apache.cxf.endpoint.Client client =
                ClientProxy.getClient(bewerkenNationaliteitGeneriekPortType);
            org.apache.cxf.endpoint.Endpoint cxfEndpoint = client.getEndpoint();

            cxfEndpoint.getOutInterceptors().add(wss4JOutInterceptor);
            cxfEndpoint.getInInterceptors().add(wss4JInInterceptor);


            ((HTTPConduit) client.getConduit()).setTlsClientParameters(tlsClientParameters);

            ToevoegenNationaliteit parameters = new ObjectFactory().createToevoegenNationaliteit();
            StandaardAntwoord standaardAntwoord = bewerkenNationaliteitGeneriekPortType.toevoegenNationaliteitGeneriek(parameters);
            System.out.println(Thread.currentThread().getName() + " standaardAntwoord: antwoordCode=" + standaardAntwoord.getAntwoordcode() + ", melding=" + standaardAntwoord.getMelding());

            ((HTTPConduit) client.getConduit()).close();
            client.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
