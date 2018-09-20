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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dit is een business regel die geldt voor ELK bericht, gevalideerd moet worden dat een communicatieID uniek moet zijn
 * binnen 1 bericht.
 *
 * "Elk referentieID in het request moet verwijzen naar een communicatieID in het request"
 *
 * @brp.bedrijfsregel BRBY9901
 */
public class BRBY9901 implements BerichtBedrijfsRegel<BerichtBericht> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY9901.class);

    @Override
    public String getCode() {
        return "BRBY9901";
    }

    /**
     * Test dat elk communicatieID uniek is binnen het bericht.
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
                    // heel makkelijk te testen of een commID uniek is door te kijken of de lijst van object slechts
                    // uit 1 lid bestaat.
                    List<Identificeerbaar> idents = map.get(key);
                    if (idents.size() > 1) {
                        LOGGER.error("Dubbele communicatie ID [" + key
                                + "]. Object: " + map.get(key));
                        meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY9901,
                                idents.get(0), "referentieID"));
                    }
                }
            }
        }
        return meldingen;
    }

}
