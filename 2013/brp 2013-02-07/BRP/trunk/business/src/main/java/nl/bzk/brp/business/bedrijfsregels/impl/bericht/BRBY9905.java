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
 * Dit is een business regel die geldt voor ELK bericht, gevalideerd moet worden dat een referentieID niet naar zichzelf
 * mag wijzen of naar een ander object dat weer naar een ander object verwijst.
 * .
 *
 * Een request mag geen keten- of zelfverwijzing bevatten.
 *
 * @brp.bedrijfsregel BRBY9905
 */
public class BRBY9905 implements BerichtBedrijfsRegel<BerichtBericht> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY9905.class);

    @Override
    public String getCode() {
        return "BRBY9905";
    }

    /**
     * .
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
                    for (Identificeerbaar ident : map.get(key)) {
                        if (StringUtils.isNotBlank(ident.getReferentieID())) {
                            String refId = ident.getReferentieID();
                            List<Identificeerbaar> targets = map.get(refId);
                            if (null != targets && 1 == targets.size()) {
                                // Als deze null is, is dit een fout die al eerder gecontateerd,
                                // we kunnen hier niks meer verifieren.
                                // als deze duplicaten bevat, is dit ook al eerder gecontateerd, en kunen we OOK HIER
                                // niets concluderen (want wat als 1 correct en 1 naar zichzelf refereert
                                // en de laatste is een keten.
                                // DUS ==> 0 or meer dan 1, NIET testen
                                Identificeerbaar target =  map.get(refId).get(0);
                                if (null != target) {
                                    // OK, test nu of deze naar zichzelf verwijst
                                    if (target == ident) {
                                        LOGGER.error("CommID [" + key + "]Referentie ID [" + refId
                                            + "] refereert naar zichzelf. Object: " + ident);
                                        meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY9905,
                                            ident, "referentieID"));
                                    } else {
                                        String targetReferentieID = target.getReferentieID();
                                        if (StringUtils.isNotBlank(targetReferentieID)) {
                                            //refereert dit object naar een ander object ?
                                            LOGGER.error("CommID [" + key + "]Referentie ID [" + refId
                                                + "] refereert naar Object: "
                                                + ident + " dat weer refereert naar " + targetReferentieID);
                                            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY9905,
                                                ident, "referentieID"));
                                        }
                                    }
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
