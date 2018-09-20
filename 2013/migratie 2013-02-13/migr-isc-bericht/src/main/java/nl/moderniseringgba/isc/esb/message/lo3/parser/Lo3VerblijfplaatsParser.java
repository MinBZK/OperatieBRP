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
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 verblijfsplaats velden in de excel input.
 * 
 */
// CHECKSTYLE:OFF - Fan-out complexity
public final class Lo3VerblijfplaatsParser extends Lo3CategorieParser<Lo3VerblijfplaatsInhoud> {
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF - Meer dan 30 statements is hier niet ingewikkeld
    @Override
    public Lo3Stapel<Lo3VerblijfplaatsInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        // CHECKSTYLE:ON
        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> verblijfplaatsList =
                new ArrayList<Lo3Categorie<Lo3VerblijfplaatsInhoud>>();

        for (int categorieIndex = 0; categorieIndex < categorieen.size(); categorieIndex++) {
            final Lo3CategorieWaarde categorie = categorieen.get(categorieIndex);

            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            final Lo3GemeenteCode gemeenteInschrijving =
                    Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_0910));
            final Lo3Datum datumInschrijving = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_0920));
            final Lo3FunctieAdres functieAdres =
                    Parser.parseLo3FunctieAdres(elementen.remove(Lo3ElementEnum.ELEMENT_1010));
            final String gemeenteDeel = elementen.remove(Lo3ElementEnum.ELEMENT_1020);
            final Lo3Datum aanvangAdreshouding = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_1030));
            final String straatnaam = elementen.remove(Lo3ElementEnum.ELEMENT_1110);
            final String naamOpenbareRuimte = elementen.remove(Lo3ElementEnum.ELEMENT_1115);
            final Lo3Huisnummer huisnummer = Parser.parseLo3Huisnummer(elementen.remove(Lo3ElementEnum.ELEMENT_1120));
            final Character huisletter = Parser.parseCharacter(elementen.remove(Lo3ElementEnum.ELEMENT_1130));
            final String huisnummertoevoeging = elementen.remove(Lo3ElementEnum.ELEMENT_1140);
            final Lo3AanduidingHuisnummer aanduidingHuisnummer =
                    Parser.parseLo3AanduidingHuisnummer(elementen.remove(Lo3ElementEnum.ELEMENT_1150));
            final String postcode = elementen.remove(Lo3ElementEnum.ELEMENT_1160);
            final String woonplaatsnaam = elementen.remove(Lo3ElementEnum.ELEMENT_1170);
            final String identificatiecodeVerblijfplaats = elementen.remove(Lo3ElementEnum.ELEMENT_1180);
            final String identificatiecodeNummeraanduiding = elementen.remove(Lo3ElementEnum.ELEMENT_1190);
            final String locatieBeschrijving = elementen.remove(Lo3ElementEnum.ELEMENT_1210);
            final Lo3LandCode landWaarnaarVertrokken =
                    Parser.parseLo3LandCode(elementen.remove(Lo3ElementEnum.ELEMENT_1310));
            final Lo3Datum vertrekUitNederland = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_1320));
            final String adresBuitenland1 = elementen.remove(Lo3ElementEnum.ELEMENT_1330);
            final String adresBuitenland2 = elementen.remove(Lo3ElementEnum.ELEMENT_1340);
            final String adresBuitenland3 = elementen.remove(Lo3ElementEnum.ELEMENT_1350);
            final Lo3LandCode landVanwaarIngeschreven =
                    Parser.parseLo3LandCode(elementen.remove(Lo3ElementEnum.ELEMENT_1410));
            final Lo3Datum vestigingInNederland = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_1420));
            final Lo3AangifteAdreshouding aangifteAdreshouding =
                    Parser.parseLo3AangifteAdreshouding(elementen.remove(Lo3ElementEnum.ELEMENT_7210));
            final Lo3IndicatieDocument indicatieDocument =
                    Parser.parseLo3IndicatieDocument(elementen.remove(Lo3ElementEnum.ELEMENT_7510));

            // TODO: Procedure wordt nog niet verwerkt
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

            Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats = null;
            try {
                verblijfplaats =
                        new Lo3Categorie<Lo3VerblijfplaatsInhoud>(new Lo3VerblijfplaatsInhoud(gemeenteInschrijving,
                                datumInschrijving, functieAdres, gemeenteDeel, aanvangAdreshouding, straatnaam,
                                naamOpenbareRuimte, huisnummer, huisletter, huisnummertoevoeging,
                                aanduidingHuisnummer, postcode, woonplaatsnaam, identificatiecodeVerblijfplaats,
                                identificatiecodeNummeraanduiding, locatieBeschrijving, landWaarnaarVertrokken,
                                vertrekUitNederland, adresBuitenland1, adresBuitenland2, adresBuitenland3,
                                landVanwaarIngeschreven, vestigingInNederland, aangifteAdreshouding,
                                indicatieDocument), null, historie, categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }
            verblijfplaatsList.add(verblijfplaats);
        }
        return verblijfplaatsList.isEmpty() ? null : new Lo3Stapel<Lo3VerblijfplaatsInhoud>(verblijfplaatsList);
    }
}
