/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.afnemerclient.client.impl;

import javax.inject.Inject;
import javax.xml.namespace.QName;

import nl.bzk.brp.afnemerclient.client.AfnemerClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jibx.JibxDataBinding;
import org.springframework.stereotype.Component;

@Component
public class AfnemerClientImpl implements AfnemerClient {

    @Inject
    JibxDataBinding jibxDatabinding;

    @Override
    public void verzendMutatieBericht() {
        /*
        final JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();

        proxyFactory.setAddress(endpoint);
        proxyFactory.setDataBinding(jibxDatabinding);
        proxyFactory.setEndpointName(new QName("http://www.bprbzk.nl/BRP/levering/service", "LeveringPort"));
        proxyFactory.setServiceName(new QName("http://www.bprbzk.nl/BRP/levering/service", "LeveringService"));
        proxyFactory.setServiceClass(LeveringPortType.class);

        final LeveringPortType leveringPortType = (LeveringPortType) proxyFactory.create();
        LEVLeveringBijgehoudenPersoonLvAntwoord antwoordBericht = leveringPortType.mutatiePersoon(bericht);

        return antwoordBericht;
        */
    }

}
