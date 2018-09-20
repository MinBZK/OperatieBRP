/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;
/**
 * De volgende combinaties van locatiegegevens zijn toegestaan, bij geboorte, overlijden, relatie
 * van de soort "huwelijk/ geregistreerd partnerschap" (zowel aanvang als einde),
 * of als onderdeel van de geboortegegevens van een niet-ingeschrevene:
 *
 *          Gemeente + Land (mits land is Nederland)    of
 *          Gemeente + Woonplaats + Land (mits land is Nederland) of
 *          Buitenlandse plaats + Buitenlandse regio + Land  (mits land is niet Nederland) of
 *          Buitenlandse plaats + Land  (mits land is niet Nederland)
 *          Omschrijving locatie + Land  (mits land is niet Nederland)
 *
 * Niveau: Fout
 *
 * @brp.bedrijfsregel BRAL0210
 */
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/** Een persoon die bij een relatie betrokken is heeft precies een rol (een Soort betrokkenheid). */
public class BRAL0210 implements ActieBedrijfsRegel<Persoon> {

    @Override
    public String getCode() {
        return "BRAL0210";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        if (null != nieuweSituatie.getOverlijden()
            && !geldigLocatieCombinatie(
                nieuweSituatie.getOverlijden().getGemeenteOverlijden(),
                nieuweSituatie.getOverlijden().getLandOverlijden(),
                nieuweSituatie.getOverlijden().getWoonplaatsOverlijden(),
                nieuweSituatie.getOverlijden().getBuitenlandsePlaatsOverlijden(),
                nieuweSituatie.getOverlijden().getBuitenlandseRegioOverlijden(),
                nieuweSituatie.getOverlijden().getOmschrijvingLocatieOverlijden()
            ))
        {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRAL0210,
                (Identificeerbaar) nieuweSituatie.getOverlijden(), "overlijden"));

        }
        return meldingen;
    }

    /**
     * .
     *
     * @param gemeente .
     * @param land .
     * @param plaats .
     * @param buitenlandseplaats .
     * @param buitenlandseRegio .
     * @param locatie .
     * @return .
     */
    private boolean geldigLocatieCombinatie(final Partij gemeente, final Land land, final Plaats plaats,
        final BuitenlandsePlaats buitenlandseplaats, final BuitenlandseRegio buitenlandseRegio,
        final LocatieOmschrijving locatie)
    {
        boolean combinationOk = true;
        if (null == land) {
            combinationOk = false;
        } else if (land.getCode().equals(BrpConstanten.NL_LAND_CODE)) {
            // NL  + Gemeente [ + Woonplaats]
            if (null != buitenlandseplaats || null != buitenlandseRegio || null != locatie) {
                combinationOk = false;
            } else if (null == gemeente) {
                combinationOk = false;
            }
        } else {
            // land + { buitenlandseplaats [+ buitenlandseRegio] | locatie }
            if (null != gemeente || null != plaats
                || (null == buitenlandseplaats && null == buitenlandseRegio && null == locatie))
            {
                combinationOk = false;
            } else if (null != buitenlandseplaats) {
                if (null != locatie) {
                    combinationOk = false;
                }
            } else {
                if (null != buitenlandseplaats || null != buitenlandseRegio) {
                    combinationOk = false;
                }
            }
        }
        return combinationOk;
    }
}
