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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze class levert de functionaliteit om een lijst van LO3 elementen als String te parsen naar een Lo3Nationaliteit
 * object.
 * 
 */
public class Lo3NationaliteitParser extends Lo3CategorieParser<Lo3NationaliteitInhoud> {

    @Override
    public final Lo3Stapel<Lo3NationaliteitInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> nationaliteitList =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

        for (final Lo3CategorieWaarde categorie : categorieen) {

            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            final Lo3NationaliteitCode nationaliteitCode =
                    Parser.parseLo3NationaliteitCode(elementen.remove(Lo3ElementEnum.ELEMENT_0510));
            final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode =
                    Parser.parseLo3RedenNederlandschapCode(elementen.remove(Lo3ElementEnum.ELEMENT_6310));
            final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode =
                    Parser.parseLo3RedenNederlandschapCode(elementen.remove(Lo3ElementEnum.ELEMENT_6410));
            final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap =
                    Parser.parseLo3AanduidingBijzonderNederlandschap(elementen.remove(Lo3ElementEnum.ELEMENT_6510));

            final Lo3Documentatie documentatie =
                    Lo3Documentatie.build(null, null,
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

            Lo3Categorie<Lo3NationaliteitInhoud> nationaliteit = null;
            try {

                nationaliteit =
                        new Lo3Categorie<Lo3NationaliteitInhoud>(new Lo3NationaliteitInhoud(nationaliteitCode,
                                redenVerkrijgingNederlandschapCode, redenVerliesNederlandschapCode,
                                aanduidingBijzonderNederlandschap), documentatie, historie,
                                categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }

            nationaliteitList.add(nationaliteit);
        }
        return nationaliteitList.isEmpty() ? null : new Lo3Stapel<Lo3NationaliteitInhoud>(nationaliteitList);
    }
}
