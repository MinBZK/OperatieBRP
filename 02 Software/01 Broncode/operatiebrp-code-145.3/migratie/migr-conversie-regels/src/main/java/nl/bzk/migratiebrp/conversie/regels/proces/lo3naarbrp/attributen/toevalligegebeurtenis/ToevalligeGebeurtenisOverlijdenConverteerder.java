/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisOverlijden;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;
import org.springframework.stereotype.Component;

/**
 * Converteer overlijden.
 */
@Component
public final class ToevalligeGebeurtenisOverlijdenConverteerder {

    private final Lo3AttribuutConverteerder lo3AttribuutConverteerder;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder lo3 attribuut converteerder
     */
    @Inject
    public ToevalligeGebeurtenisOverlijdenConverteerder(
            final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        this.lo3AttribuutConverteerder = lo3AttribuutConverteerder;
    }

    /**
     * Converteer een Lo3ToevalligeGebeurtenisOverlijden naar een BrpToevalligeGebeurtenisOverlijden.
     * @param overlijden lo3 representatie
     * @return brp representatie
     */
    public BrpToevalligeGebeurtenisOverlijden converteer(final Lo3Categorie<Lo3OverlijdenInhoud> overlijden) {
        BrpToevalligeGebeurtenisOverlijden result = null;

        if (overlijden != null) {
            final Lo3OverlijdenInhoud inhoud = overlijden.getInhoud();
            final BrpDatum datum = lo3AttribuutConverteerder.converteerDatum(inhoud.getDatum());
            final BrpPlaatsLand plaatsLand
                    = new Lo3PlaatsLandConversieHelper(inhoud.getGemeenteCode(), inhoud.getLandCode(), lo3AttribuutConverteerder).converteerNaarBrp();

            result = new BrpToevalligeGebeurtenisOverlijden(
                    datum,
                    plaatsLand.getBrpGemeenteCode(),
                    plaatsLand.getBrpBuitenlandsePlaats(),
                    plaatsLand.getBrpLandOfGebiedCode(),
                    plaatsLand.getBrpOmschrijvingLocatie());
        }
        return result;
    }

}
