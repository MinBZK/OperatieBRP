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
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze class levert de functionaliteit om een lijst van LO3 elementen als Strings te parsen naar een Lo3Ouder object.
 */
public class Lo3OuderParser extends AbstractLo3CategorieParser<Lo3OuderInhoud> {

    @Override
    public final Lo3Stapel<Lo3OuderInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouderList = new ArrayList<>();

        for (final Lo3CategorieWaarde categorie : categorieen) {
            final EnumMap<Lo3ElementEnum, String> elementen = new EnumMap<>(Lo3ElementEnum.class);
            elementen.putAll(categorie.getElementen());
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();
            final Lo3Onderzoek lo3Onderzoek = parseLo3Onderzoek(elementen, lo3Herkomst);
            final Lo3CategorieEnum herkomstCategorie = lo3Herkomst.getCategorie();

            final Lo3String aNummer = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_0110, herkomstCategorie, lo3Onderzoek);
            final Lo3String burgerservicenummer = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_0120, herkomstCategorie, lo3Onderzoek);
            final Lo3String voornamen = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_0210, herkomstCategorie, lo3Onderzoek);
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode =
                    Parser.parseLo3AdellijkeTitelPredikaatCode(elementen, Lo3ElementEnum.ELEMENT_0220, herkomstCategorie, lo3Onderzoek);
            final Lo3String voorvoegselGeslachtsnaam = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_0230, herkomstCategorie, lo3Onderzoek);
            final Lo3String geslachtsnaam = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_0240, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum geboorteDatum = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_0310, herkomstCategorie, lo3Onderzoek);
            final Lo3GemeenteCode gemeenteCode = Parser.parseLo3GemeenteCode(elementen, Lo3ElementEnum.ELEMENT_0320, herkomstCategorie, lo3Onderzoek);
            final Lo3LandCode landCode = Parser.parseLo3LandCode(elementen, Lo3ElementEnum.ELEMENT_0330, herkomstCategorie, lo3Onderzoek);
            final Lo3Geslachtsaanduiding geslachtsaanduiding =
                    Parser.parseLo3Geslachtsaanduiding(elementen, Lo3ElementEnum.ELEMENT_0410, herkomstCategorie, lo3Onderzoek);

            final Lo3Datum familierechtelijkeBetrekking = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_6210, herkomstCategorie, lo3Onderzoek);

            final Lo3Documentatie lo3Documentatie = parseLo3Documentatie(elementen, herkomstCategorie, lo3Onderzoek);
            final Lo3Historie lo3Historie = parseLo3Historie(elementen, herkomstCategorie, lo3Onderzoek);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            final Lo3Categorie<Lo3OuderInhoud> ouder =
                    new Lo3Categorie<>(new Lo3OuderInhoud(
                            aNummer,
                            burgerservicenummer,
                            voornamen,
                            adellijkeTitelPredikaatCode,
                            voorvoegselGeslachtsnaam,
                            geslachtsnaam,
                            geboorteDatum,
                            gemeenteCode,
                            landCode,
                            geslachtsaanduiding,
                            familierechtelijkeBetrekking), lo3Documentatie, lo3Onderzoek, lo3Historie, categorie.getLo3Herkomst());
            ouderList.add(ouder);
        }
        return ouderList.isEmpty() ? null : new Lo3Stapel<>(ouderList);
    }
}
