/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
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
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser parsed een kolom uit een excel sheet naar een lo3 persoonslijst.
 */
public final class Lo3PersoonslijstParser {

    /**
     * Parse een persoonslijst.
     * @param persoonslijstWaarden alle waarden van een kolom uit de excel sheet
     * @return een lo3 persoonslijst
     */
    public Lo3Persoonslijst parse(final List<Lo3CategorieWaarde> persoonslijstWaarden) {
        final Lo3PersoonslijstBuilder persoonslijstBuilder = new Lo3PersoonslijstBuilder();

        persoonslijstBuilder.persoonStapel(
                parseLo3PersoonStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_01)));
        persoonslijstBuilder.ouder1Stapel(
                parseLo3OuderStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_02)));
        persoonslijstBuilder.ouder2Stapel(
                parseLo3OuderStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_03)));
        persoonslijstBuilder.nationaliteitStapels(
                parseLo3NationaliteitStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_04)));
        persoonslijstBuilder.huwelijkOfGpStapels(
                parseLo3HuwelijkOfGpStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_05)));
        persoonslijstBuilder.overlijdenStapel(
                parseLo3OverlijdenStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_06)));
        persoonslijstBuilder.inschrijvingStapel(
                parseLo3InschrijvingStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_07)));
        persoonslijstBuilder.verblijfplaatsStapel(
                parseLo3VerblijfplaatsStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_08)));
        persoonslijstBuilder.kindStapels(
                parseLo3KindStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_09)));
        persoonslijstBuilder.verblijfstitelStapel(
                parseLo3VerblijfstitelStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_10)));
        persoonslijstBuilder.gezagsverhoudingStapel(
                parseLo3GezagsverhoudingStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_11)));
        persoonslijstBuilder.reisdocumentStapels(
                parseLo3ReisdocumentStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_12)));
        persoonslijstBuilder.kiesrechtStapel(
                parseLo3KiesrechtStapel(Lo3ParserUtil.getGesorteerdeCategorieenPerStapel(persoonslijstWaarden, Lo3CategorieEnum.CATEGORIE_13)));

        return persoonslijstBuilder.build();
    }

    private Lo3Stapel<Lo3KiesrechtInhoud> parseLo3KiesrechtStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        if (categorieWaarden.size() > 1) {
            throw new CategorieKomtVakerVoorDanToegestaanExceptie(categorieWaarden.get(0).get(0).getCategorie(), 1, categorieWaarden.size());
        }

        return new Lo3KiesrechtParser().parse(categorieWaarden.get(0));
    }

    private List<Lo3Stapel<Lo3ReisdocumentInhoud>> parseLo3ReisdocumentStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        final List<Lo3Stapel<Lo3ReisdocumentInhoud>> result = new ArrayList<>();

        for (final List<Lo3CategorieWaarde> categorieWaarde : categorieWaarden) {
            final Lo3ReisdocumentParser lo3ReisdocumentParser = new Lo3ReisdocumentParser();
            if (!lo3ReisdocumentParser.bevatAanduidingBezitNederlandsReisdocument(categorieWaarde)) {
                result.add(lo3ReisdocumentParser.parse(categorieWaarde));
            }
        }
        return result;
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> parseLo3GezagsverhoudingStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        if (categorieWaarden.size() > 1) {
            Lo3CategorieEnum categorie = categorieWaarden.get(0).get(0).getCategorie();

            if (!categorie.isActueel()) {
                categorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
            }

            throw new CategorieKomtVakerVoorDanToegestaanExceptie(categorie, 1, categorieWaarden.size());
        }

        return new Lo3GezagsverhoudingParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3VerblijfstitelInhoud> parseLo3VerblijfstitelStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        if (categorieWaarden.size() > 1) {
            Lo3CategorieEnum categorie = categorieWaarden.get(0).get(0).getCategorie();

            if (!categorie.isActueel()) {
                categorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
            }

            throw new CategorieKomtVakerVoorDanToegestaanExceptie(categorie, 1, categorieWaarden.size());
        }

        return new Lo3VerblijfstitelParser().parse(categorieWaarden.get(0));
    }

    private List<Lo3Stapel<Lo3KindInhoud>> parseLo3KindStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        final List<Lo3Stapel<Lo3KindInhoud>> result = new ArrayList<>();
        for (final List<Lo3CategorieWaarde> categorieWaarde : categorieWaarden) {
            result.add(new Lo3KindParser().parse(categorieWaarde));
        }
        return result;
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> parseLo3VerblijfplaatsStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        if (categorieWaarden.size() > 1) {
            Lo3CategorieEnum categorie = categorieWaarden.get(0).get(0).getCategorie();

            if (!categorie.isActueel()) {
                categorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
            }

            throw new CategorieKomtVakerVoorDanToegestaanExceptie(categorie, 1, categorieWaarden.size());
        }

        return new Lo3VerblijfplaatsParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> parseLo3InschrijvingStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        if (categorieWaarden.size() > 1) {
            throw new CategorieKomtVakerVoorDanToegestaanExceptie(categorieWaarden.get(0).get(0).getCategorie(), 1, categorieWaarden.size());
        }

        return new Lo3InschrijvingParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3OverlijdenInhoud> parseLo3OverlijdenStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        if (categorieWaarden.size() > 1) {
            Lo3CategorieEnum categorie = categorieWaarden.get(0).get(0).getCategorie();

            if (!categorie.isActueel()) {
                categorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
            }

            throw new CategorieKomtVakerVoorDanToegestaanExceptie(categorie, 1, categorieWaarden.size());
        }

        return new Lo3OverlijdenParser().parse(categorieWaarden.get(0));
    }

    private List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> parseLo3HuwelijkOfGpStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<>();
        for (final List<Lo3CategorieWaarde> categorieWaarde : categorieWaarden) {
            result.add(new Lo3HuwelijkOfGpParser().parse(categorieWaarde));
        }
        return result;
    }

    private List<Lo3Stapel<Lo3NationaliteitInhoud>> parseLo3NationaliteitStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> result = new ArrayList<>();
        for (final List<Lo3CategorieWaarde> categorieWaarde : categorieWaarden) {
            result.add(new Lo3NationaliteitParser().parse(categorieWaarde));
        }
        return result;
    }

    /* Static methods */

    private Lo3Stapel<Lo3OuderInhoud> parseLo3OuderStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        if (categorieWaarden.size() > 1) {
            Lo3CategorieEnum categorie = categorieWaarden.get(0).get(0).getCategorie();

            if (!categorie.isActueel()) {
                categorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
            }

            throw new CategorieKomtVakerVoorDanToegestaanExceptie(categorie, 1, categorieWaarden.size());
        }

        return new Lo3OuderParser().parse(categorieWaarden.get(0));
    }

    private Lo3Stapel<Lo3PersoonInhoud> parseLo3PersoonStapel(final List<List<Lo3CategorieWaarde>> categorieWaarden) {
        if (categorieWaarden.isEmpty()) {
            return null;
        }

        if (categorieWaarden.size() > 1) {
            Lo3CategorieEnum categorie = categorieWaarden.get(0).get(0).getCategorie();

            if (!categorie.isActueel()) {
                categorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
            }

            throw new CategorieKomtVakerVoorDanToegestaanExceptie(categorie, 1, categorieWaarden.size());
        }

        return new Lo3PersoonParser().parse(categorieWaarden.get(0));
    }
}
