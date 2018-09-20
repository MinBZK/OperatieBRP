/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.security.cert.X509Certificate;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.web.interceptor.ArchiveringBericht;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSSecurityEngineResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class met webservice gerelateerde utility methodes. De in deze utility class opgenomen methodes zijn
 * allen CXF afhankelijk en daarom in deze aparte utility class gestopt zodat de overige (web)service gerelateerd
 * classes CXF onafhankelijk kunnen blijven en alleen JAX-WS afhankelijk zijn.
 */
public final class WebserviceUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WebserviceUtil.class);

    /** Default private constructor om instantiatie te voorkomen. */
    private WebserviceUtil() {

    }

    /**
     * Haalt het X509 certificaat op uit de messageContext van CXF. Dit certificaat staat in het resultaat wat eerder
     * is afgeleverd door de WSS4JInInterceptor.
     *
     * @param wsContext De webservice context.
     * @return Implementatie van het X509 certificaat.
     */
    static X509Certificate haalClientCertificaatOp(final WebServiceContext wsContext) {
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

    /**
     * Haalt de ids van het gearchiveerde binnenkomende bericht op, alsmede het id voor het uitgaande bericht. Deze
     * worden gelezen van de CXF message context, welke onderwater wordt opgehaald middels threadlocal.
     *
     * @return de berichten ids.
     */
    static BerichtenIds haalBerichtenIdsOp() {
        Long berichtInId = (Long) PhaseInterceptorChain.getCurrentMessage().getExchange().get(
            ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID);
        Long berichtUitId = (Long) PhaseInterceptorChain.getCurrentMessage().getExchange().get(
            ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID);

        if (berichtInId == null || berichtUitId == null) {
            throw new IllegalStateException("Bericht archivering ids niet beschikbaar");
        }

        LOG.debug("Berichten ids opgehaald: IN={}, UIT={}", berichtInId, berichtUitId);
        return new BerichtenIds(berichtInId, berichtUitId);
    }

}
