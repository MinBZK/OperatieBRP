/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Valideert dat het communicatieID binnen een bericht uniek is.
 *
 * @brp.bedrijfsregel BRBY9901
 */
@Named("BRBY9901")
public class BRBY9901 implements VoorBerichtRegel {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final Regel getRegel() {
        return Regel.BRBY9901;
    }

    /**
     * Test dat elk communicatieID uniek is binnen het bericht.
     *
     * @param bericht het bericht waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return een lijst met objecten die de bedrijfsregel overtreden.
     */
    @Override
    public final List<BerichtIdentificeerbaar> voerRegelUit(final BerichtBericht bericht) {
        final List<BerichtIdentificeerbaar> overtredendeObjecten = new ArrayList<>();

        if (bericht != null && bericht.getCommunicatieIdMap() != null) {
            final CommunicatieIdMap objectenPerCommunicatieId = bericht.getCommunicatieIdMap();
            for (final String communicatieId : objectenPerCommunicatieId.keySet()) {
                final List<BerichtIdentificeerbaar> objecten = objectenPerCommunicatieId.get(communicatieId);
                if (objecten != null && objecten.size() > 1) {
                    // deze objecten hebben geen uniek communicatieId, maar we voegen alleen de laatste toe als overtredendObject
                    LOGGER.error("Dubbele communicatie ID [" + communicatieId + "]. Objecten: " + objecten);
                    final BerichtIdentificeerbaar laatsteObject = objecten.get(objecten.size() - 1);
                    overtredendeObjecten.add(laatsteObject);
                }
            }
        }
        return overtredendeObjecten;
    }

}
