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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Deze class levert de functionaliteit om een lijst van LO3 elementen als String te parsen naar een Lo3Nationaliteit
 * object.
 */
public class Lo3NationaliteitParser extends AbstractLo3CategorieParser<Lo3NationaliteitInhoud> {

    @Override
    public final Lo3Stapel<Lo3NationaliteitInhoud> parse(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> nationaliteitList = new ArrayList<>();
        for (final Lo3CategorieWaarde categorie : categorieen) {
            final EnumMap<Lo3ElementEnum, String> elementen = new EnumMap<>(Lo3ElementEnum.class);
            elementen.putAll(categorie.getElementen());
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();
            final Lo3Onderzoek lo3Onderzoek = parseLo3Onderzoek(elementen, lo3Herkomst);
            final Lo3CategorieEnum herkomstCategorie = lo3Herkomst.getCategorie();

            final Lo3NationaliteitCode nationaliteitCode =
                    Parser.parseLo3NationaliteitCode(elementen, Lo3ElementEnum.ELEMENT_0510, herkomstCategorie, lo3Onderzoek);
            final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode =
                    Parser.parseLo3RedenNederlandschapCode(elementen, Lo3ElementEnum.ELEMENT_6310, herkomstCategorie, lo3Onderzoek);
            final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode =
                    Parser.parseLo3RedenNederlandschapCode(elementen, Lo3ElementEnum.ELEMENT_6410, herkomstCategorie, lo3Onderzoek);
            final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap =
                    Parser.parseLo3AanduidingBijzonderNederlandschap(elementen, Lo3ElementEnum.ELEMENT_6510, herkomstCategorie, lo3Onderzoek);
            final Lo3String euPersoonsnummer = Parser.parseLo3String(elementen, Lo3ElementEnum.ELEMENT_7310, herkomstCategorie, lo3Onderzoek);

            final Lo3Documentatie lo3Documentatie = parseLo3Documentatie(elementen, herkomstCategorie, lo3Onderzoek);
            final Lo3Historie lo3Historie = parseLo3Historie(elementen, herkomstCategorie, lo3Onderzoek);

            if (!elementen.isEmpty()) {
                throw new OnverwachteElementenExceptie(categorie.getCategorie(), elementen);
            }

            final Lo3Categorie<Lo3NationaliteitInhoud> nationaliteit =
                    new Lo3Categorie<>(
                            new Lo3NationaliteitInhoud(
                                    nationaliteitCode,
                                    redenVerkrijgingNederlandschapCode,
                                    redenVerliesNederlandschapCode,
                                    aanduidingBijzonderNederlandschap,
                                    euPersoonsnummer),
                            lo3Documentatie,
                            lo3Onderzoek,
                            lo3Historie,
                            categorie.getLo3Herkomst());

            nationaliteitList.add(nationaliteit);
        }
        return nationaliteitList.isEmpty() ? null : new Lo3Stapel<>(nationaliteitList);
    }
}
