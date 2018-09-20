/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.lo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.util.NaamUtil;
import org.springframework.stereotype.Component;

/**
 * De builder die de Lo3Persoonslijst omzet naar het viewer model.
 */
@Component
public class GgoLo3Builder {
    @Inject
    private GgoLo3ValueConvert lo3ValueConvert;
    @Inject
    private GgoLo3InhoudBuilder lo3CategorieBuilder;

    /**
     * Build de persoonslijst en geef een viewer model terug.
     * 
     * @param lo3Persoonslijst
     *            Lo3Persoonslijst
     * @return Map met de categorie.
     */
    public final List<GgoStapel> build(final Lo3Persoonslijst lo3Persoonslijst) {
        if (lo3Persoonslijst == null) {
            return null;
        }

        final List<GgoStapel> ggoLo3Persoonslijst = new ArrayList<>();

        // Doorloop de persoonstapel (01)
        createPersoonStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de ouder1stapels (02)
        createOuder1Stapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de ouder2stapels (03)
        createOuder2Stapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de nationaliteitstapels (04)
        createNationaliteitStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de huwelijkstapels (05)
        createHuwelijkStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de overlijdenstapels (06)
        createOverlijdenStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de inschrijvingstapel (07)
        createInschrijvingStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de verblijfplaatsstapel (08)
        createVerblijfplaatsStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de kindstapels (09)
        createKindStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de verblijfstitelstapel (10)
        createVerblijfstitelStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de gezagsverhoudingStapel (11)
        createGezagsverhoudingStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de reisdocumentstapels (12)
        createReisdocumentStapels(ggoLo3Persoonslijst, lo3Persoonslijst);
        // Doorloop de kiesrechtstapel(13)
        createKiesrechtStapels(ggoLo3Persoonslijst, lo3Persoonslijst);

        return ggoLo3Persoonslijst;
    }

    // Create Persoonstapel
    private void createPersoonStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3PersoonInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3PersoonInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3PersoonInhoud> categorie) {
                return lo3CategorieBuilder.createPersoonInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getPersoonStapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_01);
    }

    // Create Ouder1stapel
    private void createOuder1Stapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3OuderInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3OuderInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3OuderInhoud> categorie) {
                return lo3CategorieBuilder.createOuderInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getOuder1Stapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_02);
    }

    // Create Ouder2stapel
    private void createOuder2Stapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3OuderInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3OuderInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3OuderInhoud> categorie) {
                return lo3CategorieBuilder.createOuderInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getOuder2Stapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_03);
    }

    // Create nationaliteitstapel
    private void createNationaliteitStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3NationaliteitInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3NationaliteitInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3NationaliteitInhoud> categorie) {
                return lo3CategorieBuilder.createNationaliteitInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }

            @Override
            public String bepaalStapelOmschrijving(final Lo3Categorie<Lo3NationaliteitInhoud> categorie) {
                String omschrijving = null;
                final Lo3NationaliteitCode lo3Nat = categorie.getInhoud().getNationaliteitCode();
                if (lo3Nat != null && lo3Nat.getWaarde() != null) {
                    omschrijving = lo3ValueConvert.convertToViewerValue(lo3Nat, Lo3ElementEnum.ELEMENT_0510);
                }
                return omschrijving;
            }
        };
        maakInhoudCallback.createMeervoudigeStapels(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getNationaliteitStapels(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_04);
    }

    // Create huwelijkstapel
    private void createHuwelijkStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3HuwelijkOfGpInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3HuwelijkOfGpInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
                return lo3CategorieBuilder.createHuwelijkInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createMeervoudigeStapels(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getHuwelijkOfGpStapels(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_05);
    }

    // Create overlijdenstapel
    private void createOverlijdenStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3OverlijdenInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3OverlijdenInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
                return lo3CategorieBuilder.createOverlijdenInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getOverlijdenStapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_06);
    }

    // Create inschrijvingstapel
    private void createInschrijvingStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {
        final MaakInhoudCallback<Lo3InschrijvingInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3InschrijvingInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3InschrijvingInhoud> categorie) {
                return lo3CategorieBuilder.createInschrijvingInhoud(categorie.getInhoud(), categorie.getHistorie(), categorie.getDocumentatie());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getInschrijvingStapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_07);
    }

    // Create verblijfplaatsstapel
    private void createVerblijfplaatsStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3VerblijfplaatsInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3VerblijfplaatsInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorie) {
                return lo3CategorieBuilder.createVerblijfplaatsInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getVerblijfplaatsStapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_08);
    }

    // Create kindstapel
    private void createKindStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3KindInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3KindInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3KindInhoud> categorie) {
                return lo3CategorieBuilder.createKindInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createMeervoudigeStapels(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getKindStapels(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_09);
    }

    // Create verblijfstitel stapel.
    private void createVerblijfstitelStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3VerblijfstitelInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3VerblijfstitelInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3VerblijfstitelInhoud> categorie) {
                return lo3CategorieBuilder.createVerblijfstitelInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getVerblijfstitelStapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_10);
    }

    // Create gezagsverhouding.
    private void createGezagsverhoudingStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3GezagsverhoudingInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3GezagsverhoudingInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorie) {
                return lo3CategorieBuilder.createGezagsverhoudingInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getGezagsverhoudingStapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_11);
    }

    // Create reisdocument.
    private void createReisdocumentStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3ReisdocumentInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3ReisdocumentInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3ReisdocumentInhoud> categorie) {
                return lo3CategorieBuilder.createReisdocumentInhoud(
                    categorie.getInhoud(),
                    categorie.getHistorie(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek());
            }

            @Override
            public String bepaalStapelOmschrijving(final Lo3Categorie<Lo3ReisdocumentInhoud> categorie) {
                String omschrijving = null;
                final Lo3SoortNederlandsReisdocument lo3Reisdoc = categorie.getInhoud().getSoortNederlandsReisdocument();
                if (lo3Reisdoc != null) {
                    omschrijving = lo3Reisdoc.getWaarde();
                }
                return omschrijving;
            }
        };
        maakInhoudCallback.createMeervoudigeStapels(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getReisdocumentStapels(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_12);
    }

    // Create kiesrecht.
    private void createKiesrechtStapels(final List<GgoStapel> ggoLo3Persoonslijst, final Lo3Persoonslijst lo3Persoonslijst) {

        final MaakInhoudCallback<Lo3KiesrechtInhoud> maakInhoudCallback = new AbstractMaakInhoudCallback<Lo3KiesrechtInhoud>() {
            @Override
            public Map<String, String> maakInhoud(final Lo3Categorie<Lo3KiesrechtInhoud> categorie) {
                return lo3CategorieBuilder.createKiesrechtInhoud(categorie.getInhoud(), categorie.getHistorie(), categorie.getDocumentatie());
            }
        };
        maakInhoudCallback.createEnkelvoudigeStapel(
            ggoLo3Persoonslijst,
            lo3Persoonslijst.getKiesrechtStapel(),
            lo3Persoonslijst.getActueelAdministratienummer(),
            Lo3CategorieEnum.CATEGORIE_13);
    }

    /**
     * Callback die we gebruiken om niet 20x dezelfde method te hoeven schrijven. De functies createMeervoudige- en
     * -EnkelvoudigeStapels worden ook in deze class gezet om type safety redenen. Het resultaat is dat je deze class
     * instantieert, de callback implementeert en vervolgens een van de twee bovengenoemde functies aanroept om dit te
     * activeren.
     */
    private interface MaakInhoudCallback<T extends Lo3CategorieInhoud> {
        /**
         * Bouwt een GGO Stapel op op basis van een Lo3 Stapel.
         */
        void createEnkelvoudigeStapel(
            final List<GgoStapel> ggoLo3Persoonslijst,
            final Lo3Stapel<T> lo3Stapel,
            final Long aNummer,
            final Lo3CategorieEnum catEnum);

        /**
         * Bouwt een GGO Stapel op op basis van een lijst van Lo3 Stapel.
         */
        void createMeervoudigeStapels(
            final List<GgoStapel> ggoLo3Persoonslijst,
            final List<Lo3Stapel<T>> lo3Stapels,
            final Long aNummer,
            final Lo3CategorieEnum catEnum);

        /**
         * Bepaalt de omschrijving van de Stapel. Bij sommige Stapels zoals Reisdocument is een omschrijving vereist
         * bijvoorbeeld om het soort reisdocument te tonen in de label van de stapel.
         * 
         * @return omschrijving
         */
        String bepaalStapelOmschrijving(final Lo3Categorie<T> categorie);

        Map<String, String> maakInhoud(Lo3Categorie<T> categorie);
    }

    /**
     * Callback die we gebruiken om niet 20x dezelfde method te hoeven schrijven. De functies createMeervoudige- en
     * -EnkelvoudigeStapels worden ook in deze class gezet om type safety redenen. Het resultaat is dat je deze class
     * instantieert, de callback implementeert en vervolgens een van de twee bovengenoemde functies aanroept om dit te
     * activeren.
     */
    private abstract class AbstractMaakInhoudCallback<T extends Lo3CategorieInhoud> implements MaakInhoudCallback<T> {

        /**
         * Bouwt een GGO Stapel op op basis van een Lo3 Stapel.
         */
        public void createEnkelvoudigeStapel(
            final List<GgoStapel> ggoLo3Persoonslijst,
            final Lo3Stapel<T> lo3Stapel,
            final Long aNummer,
            final Lo3CategorieEnum catEnum)
        {

            createStapel(ggoLo3Persoonslijst, lo3Stapel, aNummer, catEnum, 0);
        }

        /**
         * Bouwt een GGO Stapel op op basis van een lijst van Lo3 Stapel.
         */
        public void createMeervoudigeStapels(
            final List<GgoStapel> ggoLo3Persoonslijst,
            final List<Lo3Stapel<T>> lo3Stapels,
            final Long aNummer,
            final Lo3CategorieEnum catEnum)
        {
            if (lo3Stapels != null) {
                int vermoedelijkStapelNr = 0;
                for (final Lo3Stapel<T> lo3Stapel : lo3Stapels) {
                    createStapel(ggoLo3Persoonslijst, lo3Stapel, aNummer, catEnum, vermoedelijkStapelNr);
                    vermoedelijkStapelNr++;
                }
            }
        }

        private void createStapel(
            final List<GgoStapel> ggoLo3Persoonslijst,
            final Lo3Stapel<T> lo3Stapel,
            final Long aNummer,
            final Lo3CategorieEnum catEnum,
            final int vermoedelijkStapelNr)
        {
            if (lo3Stapel != null) {
                final List<Lo3Categorie<T>> categorieen = lo3Stapel.getCategorieen();
                Collections.reverse(categorieen);

                int catNr = catEnum.getCategorieAsInt();
                final GgoStapel stapel = new GgoStapel(NaamUtil.createLo3CategorieStapelLabel(catNr));

                int vermoedelijkVoorkomenNr = 0;
                for (final Lo3Categorie<T> categorie : categorieen) {
                    final GgoVoorkomen voorkomen =
                            lo3ValueConvert.createVoorkomen(categorie, aNummer, catNr, vermoedelijkStapelNr, vermoedelijkVoorkomenNr);

                    voorkomen.setInhoud(maakInhoud(categorie));

                    // bepaal stapel omschrijving. In lo3 kan het zo zijn dat het actuele voorkomen een lege rij is en
                    // hierdoor niet de omschrijving heeft. Vandaar bepalen binnen de loop.
                    if (stapel.getOmschrijving() == null) {
                        stapel.setOmschrijving(bepaalStapelOmschrijving(categorie));
                    }

                    stapel.addVoorkomen(voorkomen);
                    catNr = maakHistorisch(catNr);
                    vermoedelijkVoorkomenNr++;
                }
                ggoLo3Persoonslijst.add(stapel);
            }
        }

        private int maakHistorisch(final int catNr) {
            int historischCatNr = catNr;
            if (catNr < GgoLo3ValueConvert.HISTORISCH) {
                historischCatNr += GgoLo3ValueConvert.HISTORISCH;
            }
            return historischCatNr;
        }

        /**
         * Bepaalt de omschrijving van de Stapel. Bij sommige Stapels zoals Reisdocument is een omschrijving vereist
         * bijvoorbeeld om het soort reisdocument te tonen in de label van de stapel.
         * 
         * @return omschrijving
         */
        public String bepaalStapelOmschrijving(final Lo3Categorie<T> categorie) {
            return null;
        }
    }
}
