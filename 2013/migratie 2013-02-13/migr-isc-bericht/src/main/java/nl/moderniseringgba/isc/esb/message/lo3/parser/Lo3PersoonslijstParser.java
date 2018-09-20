/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
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
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser parsed een kolom uit een excel sheet naar een lo3 persoonslijst.
 * 
 * 
 * 
 */
// CHECKSTYLE:OFF het is toegestaan dat deze class meer dan 7 andere classes instantieert
public final class Lo3PersoonslijstParser {
    // CHECKSTYLE:ON

    /**
     * Parse een persoonslijst.
     * 
     * @param persoonslijstWaarden
     *            alle waarden van een kolom uit de excel sheet
     * @return een lo3 persoonslijst
     */
    public Lo3Persoonslijst parse(final List<Lo3CategorieWaarde> persoonslijstWaarden) {
        final Lo3PersoonslijstBuilder persoonslijstBuilder = new Lo3PersoonslijstBuilder();

        persoonslijstBuilder.persoonStapel(parseLo3PersoonStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_01)));
        persoonslijstBuilder.ouder1Stapel(parseLo3OuderStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_02)));
        persoonslijstBuilder.ouder2Stapel(parseLo3OuderStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_03)));
        persoonslijstBuilder.nationaliteitStapels(parseLo3NationaliteitStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_04)));
        persoonslijstBuilder.huwelijkOfGpStapels(parseLo3HuwelijkOfGpStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_05)));
        persoonslijstBuilder.overlijdenStapel(parseLo3OverlijdenStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_06)));
        persoonslijstBuilder.inschrijvingStapel(parseLo3InschrijvingStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_07)));
        persoonslijstBuilder.verblijfplaatsStapel(parseLo3VerblijfplaatsStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_08)));
        persoonslijstBuilder.kindStapels(parseLo3KindStapel(getGesorteerdeCategorieenPerStapel(persoonslijstWaarden,
                Lo3CategorieEnum.CATEGORIE_09)));
        persoonslijstBuilder.verblijfstitelStapel(parseLo3VerblijfstitelStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_10)));
        persoonslijstBuilder
                .gezagsverhoudingStapel(parseLo3GezagsverhoudingStapel(getGesorteerdeCategorieenPerStapel(
                        persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_11)));
        persoonslijstBuilder.reisdocumentStapels(parseLo3ReisdocumentStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_12)));
        persoonslijstBuilder.kiesrechtStapel(parseLo3KiesrechtStapel(getGesorteerdeCategorieenPerStapel(
                persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_13)));

        return persoonslijstBuilder.build();
    }

    /* Categorie parser methoden */

    private Lo3Stapel<Lo3KiesrechtInhoud> parseLo3KiesrechtStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }
        return new Lo3KiesrechtParser().parse(categorieWaarden.get(0));
    }

    private List<Lo3Stapel<Lo3ReisdocumentInhoud>> parseLo3ReisdocumentStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        final List<Lo3Stapel<Lo3ReisdocumentInhoud>> result = new ArrayList<Lo3Stapel<Lo3ReisdocumentInhoud>>();

        for (final List<Lo3CategorieWaarde> categorieWaarde : categorieWaarden) {
            result.add(new Lo3ReisdocumentParser().parse(categorieWaarde));
        }
        return result;
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> parseLo3GezagsverhoudingStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }
        return new Lo3GezagsverhoudingParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3VerblijfstitelInhoud> parseLo3VerblijfstitelStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }
        return new Lo3VerblijfstitelParser().parse(categorieWaarden.get(0));
    }

    private List<Lo3Stapel<Lo3KindInhoud>> parseLo3KindStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        final List<Lo3Stapel<Lo3KindInhoud>> result = new ArrayList<Lo3Stapel<Lo3KindInhoud>>();
        for (final List<Lo3CategorieWaarde> categorieWaarde : categorieWaarden) {
            result.add(new Lo3KindParser().parse(categorieWaarde));
        }
        return result;
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> parseLo3VerblijfplaatsStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }
        return new Lo3VerblijfplaatsParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> parseLo3InschrijvingStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }
        return new Lo3InschrijvingParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3OverlijdenInhoud> parseLo3OverlijdenStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }
        return new Lo3OverlijdenParser().parse(categorieWaarden.get(0));
    }

    private List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> parseLo3HuwelijkOfGpStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Stapel<Lo3HuwelijkOfGpInhoud>>();
        for (final List<Lo3CategorieWaarde> categorieWaarde : categorieWaarden) {
            result.add(new Lo3HuwelijkOfGpParser().parse(categorieWaarde));
        }
        return result;
    }

    private List<Lo3Stapel<Lo3NationaliteitInhoud>> parseLo3NationaliteitStapel(
            final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> result = new ArrayList<Lo3Stapel<Lo3NationaliteitInhoud>>();
        for (final List<Lo3CategorieWaarde> categorieWaarde : categorieWaarden) {
            result.add(new Lo3NationaliteitParser().parse(categorieWaarde));
        }
        return result;
    }

    private Lo3Stapel<Lo3OuderInhoud> parseLo3OuderStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }
        return new Lo3OuderParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3PersoonInhoud> parseLo3PersoonStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }
        return new Lo3PersoonParser().parse(categorieWaarden.get(0));
    }

    /* Static methods */

    /**
     * Geeft per stapel de gesorteerde categorieen terug. De sortering is van oud naar nieuw.
     * 
     * @see #getCategorieWaarden(List, Lo3CategorieEnum)
     * @param categorieenLijst
     * @param categorie
     * @return de lijst met categoriewaarden
     */
    private static List<List<Lo3CategorieWaarde>> getGesorteerdeCategorieenPerStapel(
            final List<Lo3CategorieWaarde> categorieenLijst,
            final Lo3CategorieEnum categorie) {
        final List<Lo3CategorieWaarde> categorieeWaarden = getCategorieWaarden(categorieenLijst, categorie);
        final List<List<Lo3CategorieWaarde>> stapels = new ArrayList<List<Lo3CategorieWaarde>>();
        List<Lo3CategorieWaarde> stapel = null;
        for (final Lo3CategorieWaarde cat : categorieeWaarden) {
            if (cat.getCategorie().isActueel()) {
                stapel = new ArrayList<Lo3CategorieWaarde>();
                stapels.add(stapel);
            }
            if (stapel != null) {
                // negeer historische categorieen wanneer actuele categorie ontbreekt.
                stapel.add(cat);
            }
        }
        // sorteer de stapels
        for (final List<Lo3CategorieWaarde> teSorterenStapel : stapels) {
            Collections.reverse(teSorterenStapel);
        }
        return stapels;
    }

    /**
     * Geeft op basis van de complete lijst met categorieen, een lijst met die categorieen die voldoen aan de
     * opgevraagde categorie enum. Deze methode geeft zowel de actuele als historische categorieen terug. Verwacht wordt
     * dat in de excel sheet de stapels van nieuw naar oud worden opgesomd.
     * 
     * @param categorieenLijst
     *            de complete lijst met categorieen
     * @param categorie
     *            de gevraagde categorie
     * @return
     */
    private static List<Lo3CategorieWaarde> getCategorieWaarden(
            final List<Lo3CategorieWaarde> categorieenLijst,
            final Lo3CategorieEnum categorie) {
        final List<Lo3CategorieWaarde> result = new ArrayList<Lo3CategorieWaarde>();
        final Lo3CategorieEnum historisch = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);
        for (final Lo3CategorieWaarde categorieWaarde : categorieenLijst) {
            if (categorieWaarde.getCategorie().equals(categorie) || categorieWaarde.getCategorie().equals(historisch)) {
                result.add(categorieWaarde);
            }
        }
        return result;
    }
}
