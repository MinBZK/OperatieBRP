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
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Gezagsverhouding parser.
 */
public final class Lo3GezagsverhoudingParser extends AbstractLo3CategorieParser<Lo3GezagsverhoudingInhoud> {

    @Override
    public Lo3Stapel<Lo3GezagsverhoudingInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> gezagsverhoudingList = new ArrayList<>();
        for (final Lo3CategorieWaarde categorie : categorieen) {
            final EnumMap<Lo3ElementEnum, String> elementen = new EnumMap<>(Lo3ElementEnum.class);
            elementen.putAll(categorie.getElementen());
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();
            final Lo3Onderzoek lo3Onderzoek = parseLo3Onderzoek(elementen, lo3Herkomst);
            final Lo3CategorieEnum herkomstCategorie = lo3Herkomst.getCategorie();

            // Inhoud
            final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige =
                    Parser.parseLo3IndicatieGezagMinderjarige(elementen, Lo3ElementEnum.ELEMENT_3210, herkomstCategorie, lo3Onderzoek);
            final Lo3IndicatieCurateleregister indicatieCurateleregister =
                    Parser.parseLo3IndicatieCurateleregister(elementen, Lo3ElementEnum.ELEMENT_3310, herkomstCategorie, lo3Onderzoek);

            final Lo3Documentatie lo3Documentatie = parseLo3Documentatie(elementen, herkomstCategorie, lo3Onderzoek);
            final Lo3Historie lo3Historie = parseLo3Historie(elementen, herkomstCategorie, lo3Onderzoek);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            final Lo3Categorie<Lo3GezagsverhoudingInhoud> gezagsverhouding =
                    new Lo3Categorie<>(
                            new Lo3GezagsverhoudingInhoud(indicatieGezagMinderjarige, indicatieCurateleregister),
                            lo3Documentatie,
                            lo3Onderzoek,
                            lo3Historie,
                            categorie.getLo3Herkomst());

            gezagsverhoudingList.add(gezagsverhouding);
        }

        return gezagsverhoudingList.isEmpty() ? null : new Lo3Stapel<>(gezagsverhoudingList);
    }
}
