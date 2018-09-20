/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

//CHECKSTYLE:OFF - static import maakt code leesbaarder 
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_01;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_02;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_03;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_04;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_05;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_06;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_07;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_08;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_09;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_10;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_11;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_12;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_13;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_21;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_51;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_52;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_53;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_54;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_55;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_56;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_58;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_59;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_60;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_61;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum.CATEGORIE_71;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8110;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8120;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8210;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8220;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8230;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8410;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8510;
import static nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum.ELEMENT_8610;

import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

//CHECKSTYLE:ON

/**
 * Lo3 persoonslijst formatter.
 */
// CHECKSTYLE:OFF - Fan out complexity - logisch door verschillende categorieen en formatters
public final class Lo3PersoonslijstFormatter {
    // CHECKSTYLE:ON

    private static final Lo3CategorieFormatter<Lo3PersoonInhoud> PERSOON_FORMAT = new Lo3PersoonFormatter();
    private static final Lo3CategorieFormatter<Lo3OuderInhoud> OUDER_FORMAT = new Lo3OuderFormatter();
    private static final Lo3CategorieFormatter<Lo3NationaliteitInhoud> NATIONALITEIT_FORMAT =
            new Lo3NationaliteitFormatter();
    private static final Lo3CategorieFormatter<Lo3HuwelijkOfGpInhoud> HUWELIJK_FORMAT =
            new Lo3HuwelijkOfGpFormatter();
    private static final Lo3CategorieFormatter<Lo3OverlijdenInhoud> OVERLIJDEN_FORMAT = new Lo3OverlijdenFormatter();
    private static final Lo3CategorieFormatter<Lo3InschrijvingInhoud> INSCHRIJVING_FORMAT =
            new Lo3InschrijvingFormatter();
    private static final Lo3CategorieFormatter<Lo3VerblijfplaatsInhoud> VERBLIJFPLAATS =
            new Lo3VerblijfplaatsFormatter();
    private static final Lo3CategorieFormatter<Lo3KindInhoud> KIND_FORMAT = new Lo3KindFormatter();
    private static final Lo3CategorieFormatter<Lo3VerblijfstitelInhoud> VERBLIJFSTITEL_FORMAT =
            new Lo3VerblijfstitelFormatter();
    private static final Lo3CategorieFormatter<Lo3GezagsverhoudingInhoud> GEZAGSVERHOUDING_FORMAT =
            new Lo3GezagsverhoudingFormatter();
    private static final Lo3CategorieFormatter<Lo3ReisdocumentInhoud> REISDOCUMENT_FORMAT =
            new Lo3ReisdocumentFormatter();
    private static final Lo3CategorieFormatter<Lo3KiesrechtInhoud> KIESRECHT_FORMAT = new Lo3KiesrechtFormatter();
    private static final Lo3CategorieFormatter<Lo3VerwijzingInhoud> VERWIJZING_FORMAT = new Lo3VerwijzingFormatter();

    /**
     * Format een lo3 persoonslijst.
     * 
     * @param persoonslijst
     *            lo3 persoonslijst
     * @return categoriewaarde lijst
     */
    public List<Lo3CategorieWaarde> format(final Lo3Persoonslijst persoonslijst) {
        if (persoonslijst == null) {
            return Collections.emptyList();
        }

        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();

        format(formatter, PERSOON_FORMAT, CATEGORIE_01, CATEGORIE_51, persoonslijst.getPersoonStapel());
        format(formatter, OUDER_FORMAT, CATEGORIE_02, CATEGORIE_52, persoonslijst.getOuder1Stapels());
        format(formatter, OUDER_FORMAT, CATEGORIE_03, CATEGORIE_53, persoonslijst.getOuder2Stapels());
        format(formatter, NATIONALITEIT_FORMAT, CATEGORIE_04, CATEGORIE_54, persoonslijst.getNationaliteitStapels());
        format(formatter, HUWELIJK_FORMAT, CATEGORIE_05, CATEGORIE_55, persoonslijst.getHuwelijkOfGpStapels());
        format(formatter, OVERLIJDEN_FORMAT, CATEGORIE_06, CATEGORIE_56, persoonslijst.getOverlijdenStapel());
        format(formatter, INSCHRIJVING_FORMAT, CATEGORIE_07, null, persoonslijst.getInschrijvingStapel());
        format(formatter, VERBLIJFPLAATS, CATEGORIE_08, CATEGORIE_58, persoonslijst.getVerblijfplaatsStapel());
        format(formatter, KIND_FORMAT, CATEGORIE_09, CATEGORIE_59, persoonslijst.getKindStapels());
        format(formatter, VERBLIJFSTITEL_FORMAT, CATEGORIE_10, CATEGORIE_60, persoonslijst.getVerblijfstitelStapel());
        format(formatter, GEZAGSVERHOUDING_FORMAT, CATEGORIE_11, CATEGORIE_61,
                persoonslijst.getGezagsverhoudingStapel());
        format(formatter, REISDOCUMENT_FORMAT, CATEGORIE_12, null, persoonslijst.getReisdocumentStapels());
        format(formatter, KIESRECHT_FORMAT, CATEGORIE_13, null, persoonslijst.getKiesrechtStapel());

        return formatter.getList();
    }

    /**
     * Format een verwijzing.
     * 
     * @param verwijzing
     *            verwijzing
     * @return categoriewaarde lijst
     */
    public List<Lo3CategorieWaarde> formatVerwijzing(final Lo3Categorie<Lo3VerwijzingInhoud> verwijzing) {
        if (verwijzing == null) {
            return Collections.emptyList();
        }

        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();

        final Lo3Stapel<Lo3VerwijzingInhoud> stapel =
                new Lo3Stapel<Lo3VerwijzingInhoud>(Collections.singletonList(verwijzing));
        format(formatter, VERWIJZING_FORMAT, CATEGORIE_21, CATEGORIE_71, stapel);

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
                formatter.categorie(i == 0 ? actueleCategorie : historischeCategorie);
                final Lo3Categorie<T> categorie = stapel.get(stapel.size() - 1 - i);
                lo3Formatter.format(categorie.getInhoud(), formatter);
                formatDocumentatie(categorie.getDocumentatie(), formatter);
                formatHistorie(categorie.getHistorie(), formatter);
            }
        }
    }

    private static void formatDocumentatie(final Lo3Documentatie documentatie, final Lo3Formatter formatter) {
        if (documentatie != null) {
            formatter.element(ELEMENT_8110, Lo3Format.format(documentatie.getGemeenteAkte()));
            formatter.element(ELEMENT_8120, Lo3Format.format(documentatie.getNummerAkte()));
            formatter.element(ELEMENT_8210, Lo3Format.format(documentatie.getGemeenteDocument()));
            formatter.element(ELEMENT_8220, Lo3Format.format(documentatie.getDatumDocument()));
            formatter.element(ELEMENT_8230, Lo3Format.format(documentatie.getBeschrijvingDocument()));

        }
    }

    private static void formatHistorie(final Lo3Historie historie, final Lo3Formatter formatter) {
        if (historie != null && !historie.isNullHistorie()) {
            formatter.element(ELEMENT_8410, Lo3Format.format(historie.getIndicatieOnjuist()));
            formatter.element(ELEMENT_8510, Lo3Format.format(historie.getIngangsdatumGeldigheid()));
            formatter.element(ELEMENT_8610, Lo3Format.format(historie.getDatumVanOpneming()));
        }
    }

}
