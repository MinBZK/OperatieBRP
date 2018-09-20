/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.BerichtBedrijfsRegel;
import nl.bzk.brp.model.administratie.CommunicatieIdMap;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dit is een business regel die geldt voor ELK bericht, gevalideerd moet worden dat een referentieID ook daadwerkelijk
 * naar een bestaand communicatieID moet verwijzen.
 *
 * "Elk referentieID in het request moet verwijzen naar een communicatieID in het request"
 *
 * @brp.bedrijfsregel BRBY9902
 */
public class BRBY9902 implements BerichtBedrijfsRegel<BerichtBericht> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY9902.class);

    @Override
    public String getCode() {
        return "BRBY9902";
    }

    /**
     * Test bestaan van communicatieID's van alle referentieID's.
     *
     * @param bericht het bericht waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return een lijst met eventuele meldingen indien het bericht niet voldoet aan de bedrijfsregel.
     */
    @Override
    public List<Melding> executeer(final BerichtBericht bericht) {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (null != bericht && null != bericht.getIdentificeerbaarObjectIndex()) {
            CommunicatieIdMap map =  bericht.getIdentificeerbaarObjectIndex();
            for (String key : map.keySet()) {
                if (null != map.get(key)) {
                    if (null != map.get(key)) {
                        for (Identificeerbaar ident : map.get(key)) {
                            if (StringUtils.isNotBlank(ident.getReferentieID())) {
                                String refId = ident.getReferentieID();
                                if (null == map.get(refId)) {
                                    LOGGER.error("Kan referentie ID [" + refId
                                        + "] niet vinden in de commicatieID tabel. Object: " + ident);
                                    meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY9902,
                                            ident, "referentieID"));
                                }
                            }

                        }
                    }
                }
            }
        }
        return meldingen;
    }

}
