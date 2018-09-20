/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;
import org.springframework.stereotype.Component;

/**
 * Deze class bevat de conversie logica om de LO3 Categorie Overlijden te converteren naar BRP.
 * 
 * 
 * 
 */
@Component
@Requirement({Requirements.CGR08_LB01, Requirements.CGR08_LB02, Requirements.CGR08_LB03 })
public class OverlijdenConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Overlijden categorie naar het BRP model en vult hiermee de tussenPersoonslijstBuilder aan.
     * 
     * @param lo3OverlijdenStapel
     *            de overlijden stapel, mag null zijn
     * @param tussenPersoonslijstBuilder
     *            de migratie builder
     */
    public final void converteer(final Lo3Stapel<Lo3OverlijdenInhoud> lo3OverlijdenStapel, final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {
        if (lo3OverlijdenStapel == null) {
            return;
        }
        final List<TussenGroep<BrpOverlijdenInhoud>> overlijdenGroepen = new ArrayList<>();
        for (final Lo3Categorie<Lo3OverlijdenInhoud> lo3Overlijden : lo3OverlijdenStapel) {

            controleerBijzondereSituatie(lo3Overlijden);

            final Lo3GemeenteCode gemeenteCode = lo3Overlijden.getInhoud().getGemeenteCode();
            final Lo3LandCode landCode = lo3Overlijden.getInhoud().getLandCode();
            final BrpPlaatsLand brpPlaatsLand = new Lo3PlaatsLandConversieHelper(gemeenteCode, landCode, converteerder).converteerNaarBrp();

            final BrpOverlijdenInhoud inhoud =
                    new BrpOverlijdenInhoud(
                        converteerder.converteerDatum(lo3Overlijden.getInhoud().getDatum()),
                        brpPlaatsLand.getBrpGemeenteCode(),
                        brpPlaatsLand.getBrpWoonplaatsnaam(),
                        brpPlaatsLand.getBrpBuitenlandsePlaats(),
                        brpPlaatsLand.getBrpBuitenlandseRegio(),
                        brpPlaatsLand.getBrpLandOfGebiedCode(),
                        brpPlaatsLand.getBrpOmschrijvingLocatie());
            overlijdenGroepen.add(new TussenGroep<>(
                inhoud,
                lo3Overlijden.getHistorie(),
                lo3Overlijden.getDocumentatie(),
                lo3Overlijden.getLo3Herkomst(),
                false,
                lo3Overlijden.getInhoud().isLeeg()));
        }
        final TussenStapel<BrpOverlijdenInhoud> migratieOverlijdenStapel = new TussenStapel<>(overlijdenGroepen);
        tussenPersoonslijstBuilder.overlijdenStapel(migratieOverlijdenStapel);
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB025)
    private void controleerBijzondereSituatie(final Lo3Categorie<Lo3OverlijdenInhoud> lo3Categorie) {
        final Lo3OverlijdenInhoud overlijden = lo3Categorie.getInhoud();
        final Lo3Datum overlijdenDatum = overlijden.getDatum();
        if (Validatie.isElementGevuld(overlijdenDatum)
            && !overlijdenDatum.getIntegerWaarde().equals(lo3Categorie.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde()))
        {
            Foutmelding.logMeldingFoutInfo(lo3Categorie.getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB025, null);
        }
    }
}
