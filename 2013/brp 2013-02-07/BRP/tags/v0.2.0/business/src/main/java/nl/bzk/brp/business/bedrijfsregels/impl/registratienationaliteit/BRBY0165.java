/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.registratienationaliteit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementatie van bedrijfsregel BRBY0165.
 * <p/>
 * Als een persoon in het bericht een nationaliteit verkrijgt met als waarde Nederlandse,
 *      dan moet de reden verkrijging in de groep Nationaliteit gevuld zijn.
 * <p/>
 *
 * @brp.bedrijfsregel BRBY0165
 */
public class BRBY0165 implements ActieBedrijfsRegel<Persoon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0165.class);

    @Override
    public String getCode() {
        return "BRBY0165";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (nieuweSituatie == null || nieuweSituatie.getNationaliteiten() == null) {
            meldingen = Collections.emptyList();
            LOGGER.warn("Bedrijfsregel {} aangeroepen met null waarde voor nieuwe situatie (of nationaliteiten). "
                + "Bedrijfsregel daarom niet verder uitgevoerd (geen meldingen naar gebruiker).", getCode());
        } else {
            for (PersoonNationaliteit nat : nieuweSituatie.getNationaliteiten()) {
                if (nat.getNationaliteit() == null || nat.getNationaliteit().getNationaliteitcode() == null) {
                    LOGGER.warn("Bedrijfsregel {} aangeroepen met null waarde voornationaliteit of nationaliteitcode. "
                        + "Bedrijfsregel daarom niet verder uitgevoerd (geen meldingen naar gebruiker).", getCode());
                } else {
                    if (BrpConstanten.NL_NATIONALITEIT_CODE.equals(nat.getNationaliteit().getNationaliteitcode())) {
                        // check nu of de redenVerkrijging gevuld is.
                        if (null == nat.getGegevens() || null == nat.getGegevens().getRedenVerkregenNlNationaliteit()) {
                            meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBY0165,
                                    (Identificeerbaar) nat, "redenVerkrijgingNaam"));
                        }
                    }
                }
            }
        }
        return meldingen;
    }

}
