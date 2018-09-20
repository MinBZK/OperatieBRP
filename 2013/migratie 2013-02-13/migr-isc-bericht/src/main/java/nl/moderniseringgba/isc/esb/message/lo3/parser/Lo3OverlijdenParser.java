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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 categorie overlijden in de excel input.
 * 
 */
public final class Lo3OverlijdenParser extends Lo3CategorieParser<Lo3OverlijdenInhoud> {

    @Override
    public Lo3Stapel<Lo3OverlijdenInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {

        final List<Lo3Categorie<Lo3OverlijdenInhoud>> overlijdenList =
                new ArrayList<Lo3Categorie<Lo3OverlijdenInhoud>>();

        for (final Lo3CategorieWaarde categorie : categorieen) {

            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            final Lo3Datum datumOverlijden = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_0810));
            final Lo3GemeenteCode gemeenteCodeOverlijden =
                    Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_0820));
            final Lo3LandCode landCodeOverlijden =
                    Parser.parseLo3LandCode(elementen.remove(Lo3ElementEnum.ELEMENT_0830));

            final Lo3Documentatie documentatie =
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

            Lo3Categorie<Lo3OverlijdenInhoud> overlijden = null;
            try {
                overlijden =
                        new Lo3Categorie<Lo3OverlijdenInhoud>(new Lo3OverlijdenInhoud(datumOverlijden,
                                gemeenteCodeOverlijden, landCodeOverlijden), documentatie, historie,
                                categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }
            overlijdenList.add(overlijden);
        }
        return overlijdenList.isEmpty() ? null : new Lo3Stapel<Lo3OverlijdenInhoud>(overlijdenList);
    }
}
