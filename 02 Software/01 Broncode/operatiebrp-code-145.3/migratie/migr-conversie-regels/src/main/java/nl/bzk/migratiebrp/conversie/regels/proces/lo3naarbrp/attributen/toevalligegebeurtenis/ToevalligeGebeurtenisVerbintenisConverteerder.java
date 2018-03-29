/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisOntbinding;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisSluiting;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;
import org.springframework.stereotype.Component;

/**
 * Verbintenis conversie.
 */
@Component
public final class ToevalligeGebeurtenisVerbintenisConverteerder {

    private final Lo3AttribuutConverteerder lo3AttribuutConverteerder;
    private final ToevalligeGebeurtenisPersoonConverteerder persoonConverteerder;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder lo3 attribuut converteerder
     * @param persoonConverteerder persoon converteerder
     */
    @Inject
    public ToevalligeGebeurtenisVerbintenisConverteerder(
            final Lo3AttribuutConverteerder lo3AttribuutConverteerder,
            final ToevalligeGebeurtenisPersoonConverteerder persoonConverteerder) {
        this.lo3AttribuutConverteerder = lo3AttribuutConverteerder;
        this.persoonConverteerder = persoonConverteerder;
    }

    /**
     * Converteer een Lo3ToevalligeGebeurtenisVerbintenis naar een BrpToevalligeGebeurtenisVerbintenis.
     * @param verbintenis lo3 representatie
     * @return brp representatie
     */
    public BrpToevalligeGebeurtenisVerbintenis converteer(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis) {
        BrpToevalligeGebeurtenisVerbintenis result = null;
        final BrpToevalligeGebeurtenisPersoon partner;
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting;
        BrpToevalligeGebeurtenisVerbintenisOntbinding ontbinding = null;
        BrpToevalligeGebeurtenisVerbintenisSluiting omzetting = null;

        if (verbintenis != null) {
            // Partner altijd op 'volledige' gegevens
            partner = persoonConverteerder.converteer(verbintenis.getCategorieen().get(0).getInhoud());

            if (verbintenis.getCategorieen().size() > 1) {
                final Lo3HuwelijkOfGpInhoud inhoudOud = verbintenis.getCategorieen().get(1).getInhoud();
                sluiting = converteerSluiting(inhoudOud);
                final Lo3HuwelijkOfGpInhoud inhoudNieuw = verbintenis.getCategorieen().get(0).getInhoud();
                if (inhoudNieuw.isOntbinding()) {
                    ontbinding = converteerOntbinding(inhoudNieuw);
                } else {
                    omzetting = converteerSluiting(inhoudNieuw);
                }
            } else {
                final Lo3HuwelijkOfGpInhoud inhoud = verbintenis.getCategorieen().get(0).getInhoud();
                sluiting = converteerSluiting(inhoud);
            }

            result = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, ontbinding, omzetting);
        }
        return result;
    }

    private BrpToevalligeGebeurtenisVerbintenisSluiting converteerSluiting(final Lo3HuwelijkOfGpInhoud sluiting) {
        final BrpSoortRelatieCode relatieCode = lo3AttribuutConverteerder.converteerLo3SoortVerbintenis(sluiting.getSoortVerbintenis());
        final BrpDatum datum = lo3AttribuutConverteerder.converteerDatum(sluiting.getDatumSluitingHuwelijkOfAangaanGp());
        final BrpPlaatsLand plaatsLand =
                new Lo3PlaatsLandConversieHelper(
                        sluiting.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                        sluiting.getLandCodeSluitingHuwelijkOfAangaanGp(),
                        lo3AttribuutConverteerder).converteerNaarBrp();

        return new BrpToevalligeGebeurtenisVerbintenisSluiting(
                relatieCode,
                datum,
                plaatsLand.getBrpGemeenteCode(),
                plaatsLand.getBrpBuitenlandsePlaats(),
                plaatsLand.getBrpLandOfGebiedCode(),
                plaatsLand.getBrpOmschrijvingLocatie());
    }

    private BrpToevalligeGebeurtenisVerbintenisOntbinding converteerOntbinding(final Lo3HuwelijkOfGpInhoud ontbinding) {
        final BrpDatum datum = lo3AttribuutConverteerder.converteerDatum(ontbinding.getDatumOntbindingHuwelijkOfGp());
        final BrpPlaatsLand plaatsLand =
                new Lo3PlaatsLandConversieHelper(
                        ontbinding.getGemeenteCodeOntbindingHuwelijkOfGp(),
                        ontbinding.getLandCodeOntbindingHuwelijkOfGp(),
                        lo3AttribuutConverteerder).converteerNaarBrp();
        final BrpRedenEindeRelatieCode redenEindeRelatieCode =
                lo3AttribuutConverteerder.converteerLo3RedenOntbindingHuwelijkOfGpCode(ontbinding.getRedenOntbindingHuwelijkOfGpCode());

        return new BrpToevalligeGebeurtenisVerbintenisOntbinding(
                datum,
                plaatsLand.getBrpGemeenteCode(),
                plaatsLand.getBrpBuitenlandsePlaats(),
                plaatsLand.getBrpLandOfGebiedCode(),
                plaatsLand.getBrpOmschrijvingLocatie(),
                redenEindeRelatieCode);
    }

}
