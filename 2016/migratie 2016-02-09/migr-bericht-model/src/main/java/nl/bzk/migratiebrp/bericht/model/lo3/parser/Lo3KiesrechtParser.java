/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 verblijfsplaats velden in de excel input.
 * 
 */
public class Lo3KiesrechtParser extends AbstractLo3CategorieParser<Lo3KiesrechtInhoud> {

    @Override
    public final Lo3Stapel<Lo3KiesrechtInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3KiesrechtInhoud>> kiesrechtList = new ArrayList<>();

        for (final Lo3CategorieWaarde categorie : categorieen) {
            final Map<Lo3ElementEnum, String> elementen = new HashMap<>(categorie.getElementen());

            final Lo3AanduidingEuropeesKiesrecht aanduidingEuropeesKiesrecht =
                    Parser.parseLo3AanduidingEuropeesKiesrecht(elementen, Lo3ElementEnum.ELEMENT_3110, null, null);
            final Lo3Datum datumEuropeesKiesrecht = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_3120, null, null);
            final Lo3Datum einddatumUitsluitingEuropeesKiesrecht = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_3130, null, null);

            final Lo3AanduidingUitgeslotenKiesrecht aanduidingUitgeslotenKiesrecht =
                    Parser.parseLo3AanduidingUitgeslotenKiesrecht(elementen, Lo3ElementEnum.ELEMENT_3810, null, null);
            final Lo3Datum einddatumUitsluitingKiesrecht = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_3820, null, null);

            final Lo3Documentatie lo3Documentatie = parseLo3Documentatie(elementen, null, null);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            final Lo3Categorie<Lo3KiesrechtInhoud> kiesrecht =
                    new Lo3Categorie<>(new Lo3KiesrechtInhoud(
                        aanduidingEuropeesKiesrecht,
                        datumEuropeesKiesrecht,
                        einddatumUitsluitingEuropeesKiesrecht,
                        aanduidingUitgeslotenKiesrecht,
                        einddatumUitsluitingKiesrecht), lo3Documentatie, null, Lo3Historie.NULL_HISTORIE, categorie.getLo3Herkomst());

            kiesrechtList.add(kiesrecht);
        }
        return kiesrechtList.isEmpty() ? null : new Lo3Stapel<>(kiesrechtList);
    }
}
