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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze class implementeert de functionaliteit voor het parsen van een lijst LO3 elementen als String naar een
 * Lo3VerwijzingInhoud object.
 */
public final class Lo3VerwijzingParser extends Lo3CategorieParser<Lo3VerwijzingInhoud> {

    // CHECKSTYLE:OFF - Meer dan 30 statements is hier niet ingewikkeld
    @Override
    public Lo3Stapel<Lo3VerwijzingInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        // CHECKSTYLE:ON
        final List<Lo3Categorie<Lo3VerwijzingInhoud>> list = new ArrayList<Lo3Categorie<Lo3VerwijzingInhoud>>();

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
            final Lo3GemeenteCode geboorteGemeenteCode =
                    Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_0320));
            final Lo3LandCode geboorteLandCode =
                    Parser.parseLo3LandCode(elementen.remove(Lo3ElementEnum.ELEMENT_0330));
            final Lo3GemeenteCode gemeenteInschrijving =
                    Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_0910));
            final Lo3Datum datumInschrijving = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_0920));
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
            final Lo3IndicatieGeheimCode indicatieGeheimCode =
                    Parser.parseLo3IndicatieGeheimCode(elementen.remove(Lo3ElementEnum.ELEMENT_7010));

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

            Lo3Categorie<Lo3VerwijzingInhoud> verblijfstitel = null;
            try {
                verblijfstitel =
                        new Lo3Categorie<Lo3VerwijzingInhoud>(new Lo3VerwijzingInhoud(aNummer, burgerservicenummer,
                                voornamen, adellijkeTitelPredikaatCode, voorvoegselGeslachtsnaam, geslachtsnaam,
                                geboorteDatum, geboorteGemeenteCode, geboorteLandCode, gemeenteInschrijving,
                                datumInschrijving, straatnaam, naamOpenbareRuimte, huisnummer, huisletter,
                                huisnummertoevoeging, aanduidingHuisnummer, postcode, woonplaatsnaam,
                                identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding,
                                locatieBeschrijving, indicatieGeheimCode), null, historie, categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }

            list.add(verblijfstitel);
        }

        return list.isEmpty() ? null : new Lo3Stapel<Lo3VerwijzingInhoud>(list);
    }
}
