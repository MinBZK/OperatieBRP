/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser parsed een kolom uit een excel sheet naar een lo3 persoonslijst.
 */
public final class Lo3ToevalligeGebeurtenisParser {

    /**
     * Parse de inhoud van een toevallige gebeurtenis (berichtinhoud Tb02 bericht).
     * @param categorieWaarden alle waarden uit de berichtinhoud van het Tb02 bericht
     * @param akteNummer aktenummer (uit de header)
     * @param ontvangendeGemeente ontvangende gemeente (uit de metadata van het bericht)
     * @param verzendendeGemeente verzendende gemeente (uit de metadata van het bericht)
     * @return een lo3 toevallige gebeurtenis
     */
    public Lo3ToevalligeGebeurtenis parse(
            final List<Lo3CategorieWaarde> categorieWaarden,
            final Lo3String akteNummer,
            final Lo3GemeenteCode ontvangendeGemeente,
            final Lo3GemeenteCode verzendendeGemeente) {
        final Lo3Stapel<Lo3PersoonInhoud> persoon =
                parseLo3PersoonStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapelNieuwNaarOud(categorieWaarden, Lo3CategorieEnum.CATEGORIE_01));
        final Lo3Stapel<Lo3OuderInhoud> ouder1 =
                parseLo3OuderStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapelNieuwNaarOud(categorieWaarden, Lo3CategorieEnum.CATEGORIE_02));
        final Lo3Stapel<Lo3OuderInhoud> ouder2 =
                parseLo3OuderStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapelNieuwNaarOud(categorieWaarden, Lo3CategorieEnum.CATEGORIE_03));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis =
                parseLo3HuwelijkOfGpStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapelNieuwNaarOud(categorieWaarden, Lo3CategorieEnum.CATEGORIE_05));
        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden =
                parseLo3OverlijdenStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapelNieuwNaarOud(categorieWaarden, Lo3CategorieEnum.CATEGORIE_06));

        return new Lo3ToevalligeGebeurtenis(verzendendeGemeente, ontvangendeGemeente, akteNummer, persoon, ouder1, ouder2, verbintenis, overlijden);
    }

    private Lo3Stapel<Lo3PersoonInhoud> parseLo3PersoonStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        controleerAantalStapels(categorieWaarden);

        return new Lo3PersoonParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3OuderInhoud> parseLo3OuderStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        controleerAantalStapels(categorieWaarden);

        return new Lo3OuderParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> parseLo3HuwelijkOfGpStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        controleerAantalStapels(categorieWaarden);

        return new Lo3HuwelijkOfGpParser().parse(categorieWaarden.get(0));
    }

    private Lo3Categorie<Lo3OverlijdenInhoud> parseLo3OverlijdenStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        controleerAantalStapels(categorieWaarden);

        return new Lo3OverlijdenParser().parse(categorieWaarden.get(0)).get(0);
    }

    private void controleerAantalStapels(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        // Er mag maximaal een stapel voorkomen en een stapel mag maximaal 2 groot zijn (bij overlijden is dit 1).
        if (categorieWaarden.size() > 1) {
            throw new ParseException("Te veel stapels");
        }
    }

}
