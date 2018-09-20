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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 verblijfsplaats velden in de excel input.
 * 
 */
public class Lo3KiesrechtParser extends Lo3CategorieParser<Lo3KiesrechtInhoud> {

    @Override
    public final Lo3Stapel<Lo3KiesrechtInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3KiesrechtInhoud>> kiesrechtList =
                new ArrayList<Lo3Categorie<Lo3KiesrechtInhoud>>();

        for (final Lo3CategorieWaarde categorie : categorieen) {
            final Map<Lo3ElementEnum, String> elementen =
                    new HashMap<Lo3ElementEnum, String>(categorie.getElementen());

            final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht =
                    Parser.parseLo3AanduidingEuropeesKiesrecht(elementen.remove(Lo3ElementEnum.ELEMENT_3110));
            final Lo3Datum datumEuropeesKiesrecht =
                    Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_3120));
            final Lo3Datum einddatumUitsluitingEuropeesKiesrecht =
                    Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_3130));

            final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht =
                    Parser.parseLo3AanduidingUitgeslotenKiesrecht(elementen.remove(Lo3ElementEnum.ELEMENT_3810));
            final Lo3Datum einddatumUitsluitingKiesrecht =
                    Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_3820));

            final Lo3Documentatie documentatie =
                    Lo3Documentatie.build(null, null,
                            Parser.parseLo3GemeenteCode(elementen.remove(Lo3ElementEnum.ELEMENT_8210)),
                            Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8220)),
                            elementen.remove(Lo3ElementEnum.ELEMENT_8230));

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            Lo3Categorie<Lo3KiesrechtInhoud> kiesrecht = null;
            try {
                kiesrecht =
                        new Lo3Categorie<Lo3KiesrechtInhoud>(new Lo3KiesrechtInhoud(aanduidingEuropeesKiesrecht,
                                datumEuropeesKiesrecht, einddatumUitsluitingEuropeesKiesrecht,
                                aanduidingUitgeslotenKiesrecht, einddatumUitsluitingKiesrecht), documentatie,
                                Lo3Historie.NULL_HISTORIE, categorie.getLo3Herkomst());
            } catch (final NullPointerException npe) {
                throw new ParseException(npe.getMessage(), npe);
            }

            kiesrechtList.add(kiesrecht);
        }
        return kiesrechtList.isEmpty() ? null : new Lo3Stapel<Lo3KiesrechtInhoud>(kiesrechtList);
    }
}
