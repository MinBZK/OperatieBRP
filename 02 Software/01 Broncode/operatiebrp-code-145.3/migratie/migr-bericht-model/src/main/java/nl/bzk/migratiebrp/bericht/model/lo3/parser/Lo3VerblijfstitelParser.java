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
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze parser verwerkt de LO3 verblijfstitel velden in de excel input.
 */
public final class Lo3VerblijfstitelParser extends AbstractLo3CategorieParser<Lo3VerblijfstitelInhoud> {

    @Override
    public Lo3Stapel<Lo3VerblijfstitelInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> verblijfstitelList = new ArrayList<>();
        for (final Lo3CategorieWaarde categorie : categorieen) {
            final EnumMap<Lo3ElementEnum, String> elementen = new EnumMap<>(Lo3ElementEnum.class);
            elementen.putAll(categorie.getElementen());
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();
            final Lo3Onderzoek lo3Onderzoek = parseLo3Onderzoek(elementen, lo3Herkomst);
            final Lo3CategorieEnum herkomstCategorie = lo3Herkomst.getCategorie();

            final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode =
                    Parser.parseLo3AanduidingVerblijfstitelCode(elementen, Lo3ElementEnum.ELEMENT_3910, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum datumEindeVerblijfstitel = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_3920, herkomstCategorie, lo3Onderzoek);
            final Lo3Datum datumAanvangVerblijfstitel = Parser.parseLo3Datum(elementen, Lo3ElementEnum.ELEMENT_3930, herkomstCategorie, lo3Onderzoek);

            final Lo3Historie lo3Historie = parseLo3Historie(elementen, herkomstCategorie, lo3Onderzoek);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            final Lo3Categorie<Lo3VerblijfstitelInhoud> verblijfstitel =
                    new Lo3Categorie<>(
                            new Lo3VerblijfstitelInhoud(aanduidingVerblijfstitelCode, datumEindeVerblijfstitel, datumAanvangVerblijfstitel),
                            null,
                            lo3Onderzoek,
                            lo3Historie,
                            categorie.getLo3Herkomst());

            verblijfstitelList.add(verblijfstitel);
        }
        return verblijfstitelList.isEmpty() ? null : new Lo3Stapel<>(verblijfstitelList);
    }
}
