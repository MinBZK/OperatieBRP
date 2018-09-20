/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze class implementeert de functionaliteit voor het parsen van een lijst LO3 elementen als String naar een
 * Lo3Persoon object.
 */
public class Lo3PersoonParser extends Lo3CategorieParser<Lo3PersoonInhoud> {

    // CHECKSTYLE:OFF - Meer dan 30 statements is hier niet ingewikkeld
    @Override
    public final Lo3Stapel<Lo3PersoonInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        // CHECKSTYLE:ON
        final List<Lo3Categorie<Lo3PersoonInhoud>> persoonList = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        for (final Lo3CategorieWaarde categorie : categorieen) {

            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            final Long aNummer = Parser.parseLong(elementen.remove(Lo3ElementEnum.ELEMENT_0110));
            final Long burgerservicenummer = Parser.parseLong(elementen.remove(Lo3ElementEnum.ELEMENT_0120));
            final String voornamen = elementen.remove(Lo3ElementEnum.ELEMENT_0210);
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode =
                    Parser.parseLo3AdellijkeTitelPredikaatCode(elementen.remove(Lo3ElementEnum.ELEMENT_0220));
            final String voorvoegselGeslachtsnaam = elementen.remove(Lo3ElementEnum.ELEMENT_0230);
            final String geslachtsnaam = elementen.remove(Lo3ElementEnum.ELEMENT_0240);
            final Lo3Datum geboorteDatum = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_0310));
            final Lo3GemeenteCode gemeenteCode =
                    Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_0320));
            final Lo3LandCode landCode = Parser.parseLo3LandCode(elementen.remove(Lo3ElementEnum.ELEMENT_0330));
            final Lo3Geslachtsaanduiding geslachtsaanduiding =
                    Parser.parseLo3Geslachtsaanduiding(elementen.remove(Lo3ElementEnum.ELEMENT_0410));
            final Long vorigANummer = Parser.parseLong(elementen.remove(Lo3ElementEnum.ELEMENT_2010));
            final Long volgendANummer = Parser.parseLong(elementen.remove(Lo3ElementEnum.ELEMENT_2020));
            final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode =
                    Parser.parseLo3AanduidingNaamgebruikCode(elementen.remove(Lo3ElementEnum.ELEMENT_6110));

            final Lo3Documentatie lo3Documentatie =
                    Lo3Documentatie.build(Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_8110)),
                            elementen.remove(Lo3ElementEnum.ELEMENT_8120),
                            Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_8210)),
                            Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8220)),
                            elementen.remove(Lo3ElementEnum.ELEMENT_8230));

            // TODO: procedure wordt nog niet verwerkt

            // final Integer aanduidingGegevensInOnderzoek =
            Parser.parseInteger(elementen.remove(Lo3ElementEnum.ELEMENT_8310));
            // final Lo3Datum datumIngangOnderzoek =
            Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8320));
            // final Lo3Datum datumEindeOnderzoek =
            Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8330));

            final Lo3Historie historie = parseLo3Historie(elementen);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            Lo3Categorie<Lo3PersoonInhoud> persoon = null;
            try {
                persoon =
                        new Lo3Categorie<Lo3PersoonInhoud>(new Lo3PersoonInhoud(aNummer, burgerservicenummer,
                                voornamen, adellijkeTitelPredikaatCode, voorvoegselGeslachtsnaam, geslachtsnaam,
                                geboorteDatum, gemeenteCode, landCode, geslachtsaanduiding,
                                aanduidingNaamgebruikCode, vorigANummer, volgendANummer), lo3Documentatie, historie,
                                categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }

            persoonList.add(persoon);
        }

        return persoonList.isEmpty() ? null : new Lo3Stapel<Lo3PersoonInhoud>(persoonList);
    }

}
