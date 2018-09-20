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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 verblijfstitel velden in de excel input.
 * 
 */
public final class Lo3VerblijfstitelParser extends Lo3CategorieParser<Lo3VerblijfstitelInhoud> {

    @Override
    public Lo3Stapel<Lo3VerblijfstitelInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> verblijfstitelList =
                new ArrayList<Lo3Categorie<Lo3VerblijfstitelInhoud>>();

        for (final Lo3CategorieWaarde categorie : categorieen) {

            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode =
                    Parser.parseLo3AanduidingVerblijfstitelCode(elementen.remove(Lo3ElementEnum.ELEMENT_3910));
            final Lo3Datum datumEindeVerblijfstitel =
                    Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_3920));
            final Lo3Datum ingangsdatumVerblijfstitel =
                    Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_3930));

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

            Lo3Categorie<Lo3VerblijfstitelInhoud> verblijfstitel = null;
            try {
                verblijfstitel =
                        new Lo3Categorie<Lo3VerblijfstitelInhoud>(new Lo3VerblijfstitelInhoud(
                                aanduidingVerblijfstitelCode, datumEindeVerblijfstitel, ingangsdatumVerblijfstitel),
                                null, historie, categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }

            verblijfstitelList.add(verblijfstitel);
        }
        return verblijfstitelList.isEmpty() ? null : new Lo3Stapel<Lo3VerblijfstitelInhoud>(verblijfstitelList);
    }
}
