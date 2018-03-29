/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.format;

import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Lo3 persoonslijst formatter.
 */
public final class Lo3PersoonslijstFormatter {

    private static final Lo3CategorieFormatter<Lo3PersoonInhoud> PERSOON_FORMAT = new Lo3PersoonFormatter();
    private static final Lo3CategorieFormatter<Lo3OuderInhoud> OUDER_FORMAT = new Lo3OuderFormatter();
    private static final Lo3CategorieFormatter<Lo3NationaliteitInhoud> NATIONALITEIT_FORMAT = new Lo3NationaliteitFormatter();
    private static final Lo3CategorieFormatter<Lo3HuwelijkOfGpInhoud> HUWELIJK_FORMAT = new Lo3HuwelijkOfGpFormatter();
    private static final Lo3CategorieFormatter<Lo3OverlijdenInhoud> OVERLIJDEN_FORMAT = new Lo3OverlijdenFormatter();
    private static final Lo3CategorieFormatter<Lo3InschrijvingInhoud> INSCHRIJVING_FORMAT = new Lo3InschrijvingFormatter();
    private static final Lo3CategorieFormatter<Lo3VerblijfplaatsInhoud> VERBLIJFPLAATS = new Lo3VerblijfplaatsFormatter();
    private static final Lo3CategorieFormatter<Lo3KindInhoud> KIND_FORMAT = new Lo3KindFormatter();
    private static final Lo3CategorieFormatter<Lo3VerblijfstitelInhoud> VERBLIJFSTITEL_FORMAT = new Lo3VerblijfstitelFormatter();
    private static final Lo3CategorieFormatter<Lo3GezagsverhoudingInhoud> GEZAGSVERHOUDING_FORMAT = new Lo3GezagsverhoudingFormatter();
    private static final Lo3CategorieFormatter<Lo3ReisdocumentInhoud> REISDOCUMENT_FORMAT = new Lo3ReisdocumentFormatter();
    private static final Lo3CategorieFormatter<Lo3KiesrechtInhoud> KIESRECHT_FORMAT = new Lo3KiesrechtFormatter();
    private static final Lo3CategorieFormatter<Lo3VerwijzingInhoud> VERWIJZING_FORMAT = new Lo3VerwijzingFormatter();

    /**
     * Format een lo3 persoonslijst.
     * @param persoonslijst lo3 persoonslijst
     * @return categoriewaarde lijst
     */
    public List<Lo3CategorieWaarde> format(final Lo3Persoonslijst persoonslijst) {
        if (persoonslijst == null) {
            return Collections.emptyList();
        }

        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();

        format(formatter, PERSOON_FORMAT, Lo3CategorieEnum.CATEGORIE_01, Lo3CategorieEnum.CATEGORIE_51, persoonslijst.getPersoonStapel());
        format(formatter, OUDER_FORMAT, Lo3CategorieEnum.CATEGORIE_02, Lo3CategorieEnum.CATEGORIE_52, persoonslijst.getOuder1Stapel());
        format(formatter, OUDER_FORMAT, Lo3CategorieEnum.CATEGORIE_03, Lo3CategorieEnum.CATEGORIE_53, persoonslijst.getOuder2Stapel());
        format(formatter, NATIONALITEIT_FORMAT, Lo3CategorieEnum.CATEGORIE_04, Lo3CategorieEnum.CATEGORIE_54, persoonslijst.getNationaliteitStapels());
        format(formatter, HUWELIJK_FORMAT, Lo3CategorieEnum.CATEGORIE_05, Lo3CategorieEnum.CATEGORIE_55, persoonslijst.getHuwelijkOfGpStapels());
        format(formatter, OVERLIJDEN_FORMAT, Lo3CategorieEnum.CATEGORIE_06, Lo3CategorieEnum.CATEGORIE_56, persoonslijst.getOverlijdenStapel());
        format(formatter, INSCHRIJVING_FORMAT, Lo3CategorieEnum.CATEGORIE_07, null, persoonslijst.getInschrijvingStapel());
        format(formatter, VERBLIJFPLAATS, Lo3CategorieEnum.CATEGORIE_08, Lo3CategorieEnum.CATEGORIE_58, persoonslijst.getVerblijfplaatsStapel());
        format(formatter, KIND_FORMAT, Lo3CategorieEnum.CATEGORIE_09, Lo3CategorieEnum.CATEGORIE_59, persoonslijst.getKindStapels());
        format(formatter, VERBLIJFSTITEL_FORMAT, Lo3CategorieEnum.CATEGORIE_10, Lo3CategorieEnum.CATEGORIE_60, persoonslijst.getVerblijfstitelStapel());
        format(
                formatter,
                GEZAGSVERHOUDING_FORMAT,
                Lo3CategorieEnum.CATEGORIE_11,
                Lo3CategorieEnum.CATEGORIE_61,
                persoonslijst.getGezagsverhoudingStapel());
        format(formatter, REISDOCUMENT_FORMAT, Lo3CategorieEnum.CATEGORIE_12, null, persoonslijst.getReisdocumentStapels());
        format(formatter, KIESRECHT_FORMAT, Lo3CategorieEnum.CATEGORIE_13, null, persoonslijst.getKiesrechtStapel());

        return formatter.getList();
    }

    /**
     * Format een verwijzing.
     * @param verwijzing verwijzing
     * @return categoriewaarde lijst
     */
    public List<Lo3CategorieWaarde> formatVerwijzing(final Lo3Categorie<Lo3VerwijzingInhoud> verwijzing) {
        if (verwijzing == null) {
            return Collections.emptyList();
        }

        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();

        final Lo3Stapel<Lo3VerwijzingInhoud> stapel = new Lo3Stapel<>(Collections.singletonList(verwijzing));
        format(formatter, VERWIJZING_FORMAT, Lo3CategorieEnum.CATEGORIE_21, Lo3CategorieEnum.CATEGORIE_71, stapel);

        return formatter.getList();
    }

    private static <T extends Lo3CategorieInhoud> void format(
            final Lo3Formatter formatter,
            final Lo3CategorieFormatter<T> lo3Formatter,
            final Lo3CategorieEnum actueleCategorie,
            final Lo3CategorieEnum historischeCategorie,
            final List<Lo3Stapel<T>> stapels) {
        if (stapels != null) {
            for (final Lo3Stapel<T> stapel : stapels) {
                format(formatter, lo3Formatter, actueleCategorie, historischeCategorie, stapel);
            }
        }
    }

    private static <T extends Lo3CategorieInhoud> void format(
            final Lo3Formatter formatter,
            final Lo3CategorieFormatter<T> lo3Formatter,
            final Lo3CategorieEnum actueleCategorie,
            final Lo3CategorieEnum historischeCategorie,
            final Lo3Stapel<T> stapel) {
        if (stapel != null) {
            for (int i = 0; i < stapel.size(); i++) {
                final Lo3Categorie<T> categorie = stapel.get(stapel.size() - 1 - i);

                // Start categorie
                formatter.categorie(
                        i == 0 ? actueleCategorie : historischeCategorie,
                        getStapel(categorie.getLo3Herkomst()),
                        getVoorkomen(categorie.getLo3Herkomst()));
                // Format inhoud
                lo3Formatter.format(categorie.getInhoud(), formatter);
                // Format onderzoek
                formatOnderzoek(categorie.getOnderzoek(), formatter);
                // Format documentatie
                formatDocumentatie(categorie.getDocumentatie(), formatter);
                // Format historie
                formatHistorie(categorie.getHistorie(), formatter);
            }
        }
    }

    private static int getStapel(final Lo3Herkomst lo3Herkomst) {
        if (lo3Herkomst != null) {
            return lo3Herkomst.getStapel();
        } else {
            return Lo3CategorieWaarde.DEFAULT_STAPEL;
        }
    }

    private static int getVoorkomen(final Lo3Herkomst lo3Herkomst) {
        if (lo3Herkomst != null) {
            return lo3Herkomst.getVoorkomen();
        } else {
            return Lo3CategorieWaarde.DEFAULT_VOORKOMEN;
        }
    }

    /**
     * Format documentatie (groepen 81, 82 en 88).
     * @param documentatie documentatie
     * @param formatter formatter
     */
    public static void formatDocumentatie(final Lo3Documentatie documentatie, final Lo3Formatter formatter) {
        if (documentatie != null) {
            formatter.element(Lo3ElementEnum.ELEMENT_8110, Lo3Format.format(documentatie.getGemeenteAkte()));
            formatter.element(Lo3ElementEnum.ELEMENT_8120, Lo3Format.format(documentatie.getNummerAkte()));
            formatter.element(Lo3ElementEnum.ELEMENT_8210, Lo3Format.format(documentatie.getGemeenteDocument()));
            formatter.element(Lo3ElementEnum.ELEMENT_8220, Lo3Format.format(documentatie.getDatumDocument()));
            formatter.element(Lo3ElementEnum.ELEMENT_8230, Lo3Format.format(documentatie.getBeschrijvingDocument()));
            formatter.element(Lo3ElementEnum.ELEMENT_8810, Lo3Format.format(documentatie.getRniDeelnemerCode()));
            formatter.element(Lo3ElementEnum.ELEMENT_8820, Lo3Format.format(documentatie.getOmschrijvingVerdrag()));
        }
    }

    /**
     * Format historie (groepen 84, 85 en 86).
     * @param historie historie
     * @param formatter formatter
     */
    public static void formatHistorie(final Lo3Historie historie, final Lo3Formatter formatter) {
        if (historie != null) {
            formatter.element(Lo3ElementEnum.ELEMENT_8410, Lo3Format.format(historie.getIndicatieOnjuist()));
            formatter.element(Lo3ElementEnum.ELEMENT_8510, Lo3Format.format(historie.getIngangsdatumGeldigheid()));
            formatter.element(Lo3ElementEnum.ELEMENT_8610, Lo3Format.format(historie.getDatumVanOpneming()));
        }
    }

    /**
     * Format onderzoek (groep 83).
     * @param onderzoek onderzoek
     * @param formatter formatter
     */
    public static void formatOnderzoek(final Lo3Onderzoek onderzoek, final Lo3Formatter formatter) {
        if (onderzoek != null) {
            formatter.element(Lo3ElementEnum.ELEMENT_8310, onderzoek.getAanduidingGegevensInOnderzoekCode());
            formatter.element(Lo3ElementEnum.ELEMENT_8320, Lo3Format.format(onderzoek.getDatumIngangOnderzoek()));
            formatter.element(Lo3ElementEnum.ELEMENT_8330, Lo3Format.format(onderzoek.getDatumEindeOnderzoek()));
        }
    }
}
