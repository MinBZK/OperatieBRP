/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;

import org.apache.commons.lang.StringUtils;


/**
 * Valideert dat elk referentieID daadwerkelijk
 * naar een bestaand communicatieID verwijst.
 * <p/>
 * "Elk referentieID in het request moet verwijzen naar een communicatieID in het request"
 *
 * @brp.bedrijfsregel BRBY9902
 */
@Named("BRBY9902")
public class BRBY9902 implements VoorBerichtRegel<BerichtBericht> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final Regel getRegel() {
        return Regel.BRBY9902;
    }

    /**
     * Test bestaan van communicatieID's van alle referentieID's.
     *
     * @param bericht het bericht waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return een lijst met eventuele meldingen indien het bericht niet voldoet aan de bedrijfsregel.
     */
    @Override
    public final List<BerichtIdentificeerbaar> voerRegelUit(final BerichtBericht bericht) {
        final List<BerichtIdentificeerbaar> overtredendeObjecten = new ArrayList<>();

        if (bericht != null && bericht.getCommunicatieIdMap() != null) {
            final CommunicatieIdMap objectenPerCommunicatieId = bericht.getCommunicatieIdMap();
            for (final Map.Entry<String, List<BerichtIdentificeerbaar>> communicatieIdMetObjecten : objectenPerCommunicatieId.entrySet()) {
                if (communicatieIdMetObjecten.getValue() != null) {
                    for (final BerichtIdentificeerbaar object : communicatieIdMetObjecten.getValue()) {
                        if (object instanceof BerichtEntiteit) {
                            final BerichtEntiteit berichtEntiteit = (BerichtEntiteit) object;
                            overtredendeObjecten.addAll(voerRegelUit(objectenPerCommunicatieId, berichtEntiteit));
                        }
                    }
                }
            }
        }
        return overtredendeObjecten;
    }

    private List<BerichtIdentificeerbaar> voerRegelUit(final CommunicatieIdMap objectenPerCommunicatieId, final BerichtEntiteit object) {
        final List<BerichtIdentificeerbaar> overtredendeObjecten = new ArrayList<>();
        final String referentieId = object.getReferentieID();
        if (StringUtils.isNotBlank(referentieId) && objectenPerCommunicatieId.get(referentieId) == null) {
            LOGGER.error("Kan referentie ID [" + referentieId + "] niet vinden in de commicatieID tabel. Object: " + object);
            overtredendeObjecten.add(object);
        }
        return overtredendeObjecten;
    }

}
