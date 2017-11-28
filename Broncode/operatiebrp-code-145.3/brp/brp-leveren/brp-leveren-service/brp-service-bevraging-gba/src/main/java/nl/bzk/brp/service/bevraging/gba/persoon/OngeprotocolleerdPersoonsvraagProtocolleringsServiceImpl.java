/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.bevraging.algemeen.AntwoordBerichtResultaat;
import nl.bzk.brp.service.bevraging.algemeen.Bevraging;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import org.springframework.stereotype.Component;

/**
 * Protocollering service die het antwoord op de zoekvraag niet protocolleerd.
 */
@Component
public class OngeprotocolleerdPersoonsvraagProtocolleringsServiceImpl
        implements Bevraging.ProtocolleerBerichtService<OngeprotocolleerdPersoonsvraagVerzoek, BevragingResultaat> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public void protocolleer(final OngeprotocolleerdPersoonsvraagVerzoek verzoek, final BevragingResultaat berichtResultaat,
                             final AntwoordBerichtResultaat antwoordBerichtResultaat) {
        // Deze zoekvraag moet niet geprotocolleerd worden daar deze onderdeel uitmaakt van
        // een dubbele zoekvraag, waarbij alleen de laatste moet worden geprotocolleerd.
        LOG.debug("Verzoek terecht niet geprotocolleerd");
    }
}
