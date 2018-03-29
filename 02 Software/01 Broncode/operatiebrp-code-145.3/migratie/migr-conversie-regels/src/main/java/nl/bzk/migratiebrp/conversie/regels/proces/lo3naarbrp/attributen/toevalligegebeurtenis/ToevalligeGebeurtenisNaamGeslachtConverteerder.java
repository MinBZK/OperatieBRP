/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisNaamGeslacht;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * Naamgeslacht conversie.
 */
@Component
public final class ToevalligeGebeurtenisNaamGeslachtConverteerder {

    public static final int INT_TWEE = 2;
    private final Lo3AttribuutConverteerder lo3AttribuutConverteerder;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder lo3 attribuut converteerder
     */
    @Inject
    public ToevalligeGebeurtenisNaamGeslachtConverteerder(
            final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        this.lo3AttribuutConverteerder = lo3AttribuutConverteerder;
    }

    /**
     * Converteer een Lo3ToevalligeGebeurtenisNaamGeslacht naar een BrpToevalligeGebeurtenisNaamGeslacht.
     * @param persoon lo3 representatie
     * @return brp representatie
     */
    public BrpToevalligeGebeurtenisNaamGeslacht converteer(final Lo3Stapel<Lo3PersoonInhoud> persoon) {
        BrpToevalligeGebeurtenisNaamGeslacht result = null;

        if (persoon != null && persoon.getCategorieen().size() == INT_TWEE) {
            final Lo3PersoonInhoud inhoud = persoon.getCategorieen().get(0).getInhoud();
            final BrpPredicaatCode predicaatCode
                    = lo3AttribuutConverteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(inhoud.getAdellijkeTitelPredikaatCode());
            final BrpString voornamen = lo3AttribuutConverteerder.converteerString(inhoud.getVoornamen());
            final VoorvoegselScheidingstekenPaar voorvoegselScheidingstekenPaar
                    = lo3AttribuutConverteerder.converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(inhoud.getVoorvoegselGeslachtsnaam());
            final BrpAdellijkeTitelCode adellijkeTitelCode
                    = lo3AttribuutConverteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(inhoud.getAdellijkeTitelPredikaatCode());
            final BrpString geslachtsnaamstam = lo3AttribuutConverteerder.converteerString(inhoud.getGeslachtsnaam());

            final BrpGeslachtsaanduidingCode geslachtsaanduidingCode
                    = lo3AttribuutConverteerder.converteerLo3Geslachtsaanduiding(inhoud.getGeslachtsaanduiding());

            result = new BrpToevalligeGebeurtenisNaamGeslacht(
                    predicaatCode,
                    voornamen,
                    voorvoegselScheidingstekenPaar.getVoorvoegsel(),
                    voorvoegselScheidingstekenPaar.getScheidingsteken(),
                    adellijkeTitelCode,
                    geslachtsnaamstam,
                    geslachtsaanduidingCode);
        }
        return result;
    }
}
