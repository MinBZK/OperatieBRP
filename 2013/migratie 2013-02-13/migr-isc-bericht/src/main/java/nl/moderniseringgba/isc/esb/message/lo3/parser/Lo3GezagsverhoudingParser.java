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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Gezagsverhouding parser.
 * 
 */
public final class Lo3GezagsverhoudingParser extends Lo3CategorieParser<Lo3GezagsverhoudingInhoud> {

    @Override
    public Lo3Stapel<Lo3GezagsverhoudingInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> gezagsverhoudingList =
                new ArrayList<Lo3Categorie<Lo3GezagsverhoudingInhoud>>();

        for (final Lo3CategorieWaarde categorie : categorieen) {

            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            // Inhoud
            final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige =
                    Parser.parseLo3IndicatieGezagMinderjarige(elementen.remove(Lo3ElementEnum.ELEMENT_3210));
            final Lo3IndicatieCurateleregister indicatieCurateleregister =
                    Parser.parseLo3IndicatieCurateleregister(elementen.remove(Lo3ElementEnum.ELEMENT_3310));

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

            Lo3Categorie<Lo3GezagsverhoudingInhoud> gezagsverhouding = null;
            try {
                gezagsverhouding =
                        new Lo3Categorie<Lo3GezagsverhoudingInhoud>(new Lo3GezagsverhoudingInhoud(
                                indicatieGezagMinderjarige, indicatieCurateleregister), documentatie, historie,
                                categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }

            gezagsverhoudingList.add(gezagsverhouding);
        }

        return gezagsverhoudingList.isEmpty() ? null : new Lo3Stapel<Lo3GezagsverhoudingInhoud>(gezagsverhoudingList);
    }
}
