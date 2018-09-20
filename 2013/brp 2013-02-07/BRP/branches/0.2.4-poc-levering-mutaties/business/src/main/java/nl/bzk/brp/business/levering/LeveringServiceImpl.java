/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.levering;

import javax.inject.Inject;
import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jibx.JibxDataBinding;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.apache.cxf.ws.rm.feature.RMFeature;
import org.apache.cxf.ws.rm.manager.AcksPolicyType;
import org.apache.cxf.ws.rm.manager.DestinationPolicyType;
import org.apache.cxf.ws.rmp.v200502.RMAssertion;
import org.springframework.stereotype.Service;

/** Implementatie van BRP leveringservice. */
@Service
public class LeveringServiceImpl implements LeveringService {

    @Inject
    JibxDataBinding jibxDatabinding;

	@Override
	public LEVLeveringBijgehoudenPersoonLvAntwoord verzendMutatieBericht(final LEVLeveringBijgehoudenPersoonLv bericht,
			final String endpoint) {
		final JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();

         /*
		// Ten behoeve van WS-Addressing
		proxyFactory.getFeatures().add(new WSAddressingFeature());

        RMFeature rmFeature = new RMFeature();

        RMAssertion rmAssertion = new RMAssertion();
        RMAssertion.BaseRetransmissionInterval baseRetransmissionInterval = new RMAssertion.BaseRetransmissionInterval();
        baseRetransmissionInterval.setMilliseconds(4000L);
        RMAssertion.AcknowledgementInterval acknowledgementInterval = new RMAssertion.AcknowledgementInterval();
        acknowledgementInterval.setMilliseconds(2000L);
        AcksPolicyType apt = new AcksPolicyType();
        apt.setIntraMessageThreshold(0);
        DestinationPolicyType dpt = new DestinationPolicyType();
        dpt.setAcksPolicy(apt);
        rmFeature.setDestinationPolicy(dpt);
        rmAssertion.setBaseRetransmissionInterval(baseRetransmissionInterval);
        rmAssertion.setAcknowledgementInterval(acknowledgementInterval);
        rmFeature.setRMAssertion(rmAssertion);
        proxyFactory.getFeatures().add(rmFeature);
        */

		proxyFactory.setAddress(endpoint);
		proxyFactory.setDataBinding(jibxDatabinding);
		proxyFactory.setEndpointName(new QName("http://www.bprbzk.nl/BRP/levering/service", "LeveringPort"));
		proxyFactory.setServiceName(new QName("http://www.bprbzk.nl/BRP/levering/service", "LeveringService"));
		proxyFactory.setServiceClass(LeveringPortType.class);

		final LeveringPortType leveringPortType = (LeveringPortType) proxyFactory.create();
	    LEVLeveringBijgehoudenPersoonLvAntwoord antwoordBericht = leveringPortType.mutatiePersoon(bericht);

	    return antwoordBericht;
	}
}
