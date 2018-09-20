/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.business.service.AuthenticatieService;
import nl.bzk.brp.business.service.BerichtVerwerker;
import nl.bzk.brp.model.logisch.BRPActie;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSSecurityEngineResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** Implementatie van BRP bijhoudingsservice. */
@WebService(wsdlLocation = "WEB-INF/wsdl/bijhouding.wsdl", serviceName = "BijhoudingService",
    portName = "BijhoudingPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BijhoudingServiceImpl implements BijhoudingService {

    private static final Logger LOG = LoggerFactory.getLogger(BijhoudingServiceImpl.class);

    @Inject
    private BerichtVerwerker berichtVerwerker;

    @Inject
    private AuthenticatieService authenticatieService;

    @Resource
    private WebServiceContext wsContext;

    /** {@inheritDoc} */
    @Override
    public BerichtResultaat bijhouden(@WebParam(name = "Bijhouding", partName = "bericht") final BRPBericht bericht)
    {

        final BerichtResultaat berichtResultaat = new BerichtResultaat(new ArrayList<Melding>());
        final boolean isGeauthenticeerd = authenticeer(bericht, berichtResultaat);

        if (isGeauthenticeerd) {
            return berichtVerwerker.verwerkBericht(bericht);
        }
        return berichtResultaat;
    }

    /**
     * Voert authenticatie uit alvorens een bijhouding wordt gedaan. Dit wordt gedaan op basis van het binnengekomen
     * X509 certificaat en de partij die de bijhouding doet.
     *
     * @param bericht Het bijhoudingsbericht.
     * @param berichtResultaat Resultaat waarin het resultaat van de authenticatie in wordt opgeslagen.
     * @return true indien de partij geauthenticeerd is anders false.
     */
    private boolean authenticeer(final BRPBericht bericht, final BerichtResultaat berichtResultaat) {
        final X509Certificate certificaat = haalClientCertificaatOp();
        boolean geAuthenticeerd = false;
        if (certificaat != null) {
            for (BRPActie brpActie : bericht.getBrpActies()) {
                geAuthenticeerd =
                    authenticatieService.isPartijGeauthenticeerdInBRP(brpActie.getPartij().getId(), certificaat);
            }
        }

        if (!geAuthenticeerd) {
            Melding melding =
                new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001, "NIET GEAUTHENTICEERD");
            berichtResultaat.voegMeldingToe(melding);
        }
        return geAuthenticeerd;
    }

    /**
     * Haalt het X509 certificaat op uit de messageContext van CXF. Dit certificaat staat in het resultaat wat eerder
     * is afgeleverd door de WSS4JInInterceptor.
     *
     * @return Implementatie van het X509 certificaat.
     */
    private X509Certificate haalClientCertificaatOp() {
        X509Certificate certificaat = null;
        final MessageContext messageContext = wsContext.getMessageContext();

        final Object wsSecurityResultaat = messageContext.get(WSS4JInInterceptor.SIGNATURE_RESULT);
        if (wsSecurityResultaat == null) {
            LOG.error("Resultaat van WS-Security ondertekeningscontrole is niet gevonden! "
                + "Klopt de WS-Security configuratie?");
        } else {
            final WSSecurityEngineResult securityResult = (WSSecurityEngineResult) wsSecurityResultaat;
            final Object certificaatObj = securityResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE);
            if (certificaatObj == null) {
                LOG.error("Binnenkomend bericht is niet ondertekend met een X509 certificaat.");
            } else {
                certificaat = (X509Certificate) certificaatObj;
            }
        }

        return certificaat;
    }

}
