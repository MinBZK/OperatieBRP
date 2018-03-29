/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortAkte;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * Familierechtelijke betrekking conversie.
 */
@Component
public final class ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerder {

    private final Lo3AttribuutConverteerder lo3AttribuutConverteerder;
    private final ToevalligeGebeurtenisPersoonConverteerder persoonConverteerder;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder lo3 attribuut converteerder
     * @param persoonConverteerder persoon converteerder
     */
    @Inject
    public ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerder(
            final Lo3AttribuutConverteerder lo3AttribuutConverteerder,
            final ToevalligeGebeurtenisPersoonConverteerder persoonConverteerder) {
        this.lo3AttribuutConverteerder = lo3AttribuutConverteerder;
        this.persoonConverteerder = persoonConverteerder;
    }

    /**
     * Converteer een Lo3ToevalligeGebeurtenisFamilierechtelijkeBetrekking naar een
     * BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking.
     * @param soortAkte soort akte
     * @param persoon eventuele naamswijziging
     * @param ouder1 ouder 1
     * @param ouder2 ouder 2
     * @return brp representatie
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking converteer(
            final Lo3SoortAkte soortAkte,
            final Lo3Stapel<Lo3PersoonInhoud> persoon,
            final Lo3Stapel<Lo3OuderInhoud> ouder1,
            final Lo3Stapel<Lo3OuderInhoud> ouder2) {
        BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking result = null;
        if (ouder1 != null || ouder2 != null) {

            // 1C / 1J erkenning
            // 1E / 1U ontkenning
            // 1N vernietiging erkenning
            // 1Q adoptie
            // 1V ?
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam naamWijziging = converteerNaam(persoon);
            BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder erkenning = null;
            BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder ontkenning = null;
            BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder vernietiging = null;
            BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie adoptie = null;

            switch (soortAkte) {
                case AKTE_1C:
                case AKTE_1J:
                    erkenning = converteerOuder(ouder1, ouder2);
                    break;
                case AKTE_1E:
                case AKTE_1U:
                    ontkenning = converteerOuder(ouder1, ouder2);
                    break;
                case AKTE_1N:
                    vernietiging = converteerOuder(ouder1, ouder2);
                    break;
                case AKTE_1Q:
                    adoptie = converteerAdoptie(ouder1, ouder2);
                    break;
                default:
                    return null;
            }
            result = new BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking(naamWijziging, erkenning, ontkenning, adoptie, vernietiging);
        }
        return result;

    }

    private BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam converteerNaam(final Lo3Stapel<Lo3PersoonInhoud> persoon) {
        BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam result = null;
        if (persoon != null && persoon.getCategorieen() != null) {
            final Lo3PersoonInhoud inhoud = persoon.getCategorieen().get(0).getInhoud();
            if (inhoud != null) {
                final BrpPredicaatCode predicaatCode =
                        lo3AttribuutConverteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(inhoud.getAdellijkeTitelPredikaatCode());
                final BrpString voornamen = lo3AttribuutConverteerder.converteerString(inhoud.getVoornamen());
                final VoorvoegselScheidingstekenPaar voorvoegselScheidingstekenPaar =
                        lo3AttribuutConverteerder.converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(inhoud.getVoorvoegselGeslachtsnaam());
                final BrpAdellijkeTitelCode adellijkeTitelCode =
                        lo3AttribuutConverteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(
                                inhoud.getAdellijkeTitelPredikaatCode());
                final BrpString geslachtsnaamstam = lo3AttribuutConverteerder.converteerString(inhoud.getGeslachtsnaam());

                result =
                        new BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam(
                                predicaatCode,
                                voornamen,
                                voorvoegselScheidingstekenPaar.getVoorvoegsel(),
                                voorvoegselScheidingstekenPaar.getScheidingsteken(),
                                adellijkeTitelCode,
                                geslachtsnaamstam);
            }
        }
        return result;
    }

    private BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder converteerOuder(
            final Lo3Stapel<Lo3OuderInhoud> ouder1,
            final Lo3Stapel<Lo3OuderInhoud> ouder2) {
        final Lo3Stapel<Lo3OuderInhoud> deOuder = ouder1 != null ? ouder1 : ouder2;
        BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder result = null;

        if (deOuder != null && deOuder.getCategorieen() != null) {
            final Lo3OuderInhoud inhoudNieuweOuder = deOuder.getCategorieen().get(0).getInhoud();
            final Lo3OuderInhoud inhoudOudeOuder = deOuder.getCategorieen().get(1).getInhoud();
            final BrpToevalligeGebeurtenisPersoon nieuweOuder = persoonConverteerder.converteer(inhoudNieuweOuder);
            final BrpToevalligeGebeurtenisPersoon oudeOuder = persoonConverteerder.converteer(inhoudOudeOuder);

            final BrpDatum datumIngangFamilierechtelijkeBetrekking =
                    lo3AttribuutConverteerder.converteerDatum(inhoudNieuweOuder.getFamilierechtelijkeBetrekking());

            result = new BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder(nieuweOuder, oudeOuder, datumIngangFamilierechtelijkeBetrekking);
        }

        return result;
    }

    private BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie converteerAdoptie(
            final Lo3Stapel<Lo3OuderInhoud> ouder1,
            final Lo3Stapel<Lo3OuderInhoud> ouder2) {

        final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder brpOouder1 = converteerOuder(ouder1, null);
        final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder brpOuder2 = converteerOuder(null, ouder2);

        return new BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie(brpOouder1, brpOuder2);
    }

}
