/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import javax.inject.Named;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.hisvolledig.util.PersoonUtil;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.Persoon;


/**
 * Bepalen meerder-/minderjarigheid.
 *
 * @brp.bedrijfsregel BRBY0003
 */
@Named("BRBY0003")
public class BRBY0003 {

    private static final int LEEFTIJD_MEERDERJARIG = 18;

    /**
     * Bepaalt of de meegegeven persoon minderjarig is op datum vandaag.
     *
     * @param persoon de persoon
     * @return of de persoon vandaag minderjarig is
     */
    public final boolean isMinderjarig(final Persoon persoon) {
        return isMinderjarig(persoon, DatumAttribuut.vandaag());
    }

    /**
     * Bepaalt of de meegegeven persoon meerderjarig is op datum vandaag.
     *
     * @param persoon de persoon
     * @return of de persoon vandaag meerderjarig is
     */
    public final boolean isMeerderjarig(final Persoon persoon) {
        return isMeerderjarig(persoon, DatumAttribuut.vandaag());
    }

    /**
     * Bepaalt of de meegegeven persoon minderjarig is op de meegegeven datum.
     * TODO: Houdt rekening met deels onbekende datums. Let op: dan houdt de aanname:
     * minderjarig != meerderjarig geen stand meer!
     *
     * @param persoon de persoon
     * @param datum de datum
     * @return of de persoon minderjarig is op de datum
     */
    public final boolean isMinderjarig(final Persoon persoon, final DatumAttribuut datum) {
        return !isMeerderjarig(persoon, datum);
    }

    /**
     * Bepaalt of de meegegeven persoon meerderjarig is op de meegegeven datum.
     * TODO: Houdt rekening met deels onbekende datums. Let op: dan houdt de aanname:
     * minderjarig != meerderjarig geen stand meer!
     *
     * @param persoon de persoon
     * @param datum de datum
     * @return of de persoon meerderjarig is op de datum
     */
    public final boolean isMeerderjarig(final Persoon persoon, final DatumAttribuut datum) {
        // Een persoon is meerderjarig als hij of zij een bepaald leeftijd heeft bereikt.
        boolean meerderjarig = PersoonUtil.isLeeftijdOfOuderOpDatum(persoon, LEEFTIJD_MEERDERJARIG, datum);

        // Een persoon kan ook meerderjarig zijn, omdat hij of zij een huwelijk of partnerschap is aangegaan.
        for (HuwelijkGeregistreerdPartnerschap hgp : PersoonUtil.getHGPs(persoon)) {
            if (hgp.getStandaard().getDatumAanvang() != null && hgp.getStandaard().getDatumAanvang().voorOfOp(datum)) {
                meerderjarig = true;
            }
        }

        return meerderjarig;
    }

}
