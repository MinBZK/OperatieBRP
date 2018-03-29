/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze class implementeert de functionaliteit voor het parsen van een lijst LO3 elementen als String naar een
 * Lo3VerwijzingInhoud object.
 */
public final class Lo3VerwijzingParser extends AbstractLo3CategorieParser<Lo3VerwijzingInhoud> {

    @Override
    public Lo3Stapel<Lo3VerwijzingInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3VerwijzingInhoud>> list = new ArrayList<>();

        for (final Lo3CategorieWaarde categorie : categorieen) {

            final EnumMap<Lo3ElementEnum, String> elementen = new EnumMap<>(Lo3ElementEnum.class);
            elementen.putAll(categorie.getElementen());
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();
            final Lo3Onderzoek lo3Onderzoek = parseLo3Onderzoek(elementen, lo3Herkomst);
            final Lo3CategorieEnum herkomstCategorie = lo3Herkomst.getCategorie();

            final Lo3Long aNummer = Parser.parseLo3Long(elementen, Lo3ElementEnum.ELEMENT_0110, herkomstCategorie, lo3Onderzoek);
            final Lo3Integer burgerservicenummer = Parser.parseLo3Integer(elementen, Lo3ElementEnum.ELEMENT_0120, herkomstCategorie, lo3Onderzoek);
            final Lo3String voornamen = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_0210, herkomstCategorie, lo3Onderzoek);
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode =
                    Parser.parseLo3AdellijkeTitelPredikaatCode(elementen, Lo3ElementEnum.ELEMENT_0220, herkomstCategorie, lo3Onderzoek);
            final Lo3String voorvoegselGeslachtsnaam = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_0230, herkomstCategorie, lo3Onderzoek);
            final Lo3String geslachtsnaam = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_0240, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum geboorteDatum = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_0310, herkomstCategorie, lo3Onderzoek);
            final Lo3GemeenteCode geboorteGemeenteCode =
                    Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_0320, herkomstCategorie, lo3Onderzoek);
            final Lo3LandCode geboorteLandCode = Parser.parseLo3LandCode(elementen, Lo3ElementEnum.ELEMENT_0330, herkomstCategorie, lo3Onderzoek);
            final Lo3GemeenteCode gemeenteInschrijving =
                    Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_0910, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum datumInschrijving = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_0920, herkomstCategorie, lo3Onderzoek);
            final Lo3IndicatieGeheimCode indicatieGeheimCode =
                    Parser.parseLo3IndicatieGeheimCode(elementen, Lo3ElementEnum.ELEMENT_7010, herkomstCategorie, lo3Onderzoek);

            final Lo3Historie lo3Historie = parseLo3Historie(elementen, herkomstCategorie, lo3Onderzoek);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            final Lo3Categorie<Lo3VerwijzingInhoud> verblijfstitel =
                    new Lo3Categorie<>(new Lo3VerwijzingInhoud(
                            aNummer,
                            burgerservicenummer,
                            voornamen,
                            adellijkeTitelPredikaatCode,
                            voorvoegselGeslachtsnaam,
                            geslachtsnaam,
                            geboorteDatum,
                            geboorteGemeenteCode,
                            geboorteLandCode,
                            gemeenteInschrijving,
                            datumInschrijving,
                            indicatieGeheimCode), null, lo3Onderzoek, lo3Historie, categorie.getLo3Herkomst());

            list.add(verblijfstitel);
        }

        return list.isEmpty() ? null : new Lo3Stapel<>(list);
    }
}
