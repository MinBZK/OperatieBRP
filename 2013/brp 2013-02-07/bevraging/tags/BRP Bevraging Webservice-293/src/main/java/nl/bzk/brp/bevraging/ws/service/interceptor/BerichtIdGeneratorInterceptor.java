/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.BerichtIdGenerator;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * CXF Interceptor die een unieke bericht id genereert en deze aan het bericht toevoegd, zodat in de verdere
 * verwerking hier naar gerefereerd kan worden.
 */
public class BerichtIdGeneratorInterceptor extends AbstractSoapInterceptor {

    /** De naam waaronder de gegenereerde id aan het bericht wordt toegevoegd. */
    public static final String BRP_BERICHT_ID = "BRP_BERICHT_ID";

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtIdGeneratorInterceptor.class);

    @Inject
    private BerichtIdGenerator berichtIdGenerator;

    /**
     * Standaard constructor die de phase zet waarin deze interceptor gebruikt wordt; RECEIVE phase.
     */
    public BerichtIdGeneratorInterceptor() {
        super(Phase.RECEIVE);
    }

    /**
     * Vangt een bericht af, genereert een nieuwe id middels de {@link BerichtIdGenerator} uit de service/business
     * laag en voegt het nieuwe, unieke id aan het bericht toe.
     *
     * Merk op dat de 'chain' de afhandeling van het bericht verder oppakt en dat vanuit hier dus niet de volgende
     * interceptor hoeft te worden aangeroepen.
     *
     * @param message het bericht dat wordt afgehandeld.
     */
    @Override
    public void handleMessage(final SoapMessage message) {
        Long berichtId = berichtIdGenerator.volgendeId();

        LOGGER.debug("Bericht ID '{}' toegevoegd aan binnenkomend bericht.", berichtId);

        message.put(BRP_BERICHT_ID, berichtId);
    }

}
