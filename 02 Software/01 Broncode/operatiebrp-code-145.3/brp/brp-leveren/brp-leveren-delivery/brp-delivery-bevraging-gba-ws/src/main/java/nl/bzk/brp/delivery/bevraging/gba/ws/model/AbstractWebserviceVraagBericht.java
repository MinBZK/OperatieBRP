/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Zoekparameter;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.vragen.filter.PuntAdresFilter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

/**
 * Interface voor webservice vraag berichten.
 */
public abstract class AbstractWebserviceVraagBericht implements WebserviceBericht {

    private static final Pattern rubriekPattern = Pattern.compile("^(\\d{2})(\\d{4})$");
    private static final int CATEGORIE_GROUP = 1;
    private static final int ELEMENT_GROUP = 2;

    List<String> bepaalGevraagdeRubrieken(final List<Integer> masker) {
        return masker.stream().map(this::formatRubriek).collect(toList());
    }

    List<String> bepaalZoekRubrieken(final List<Zoekparameter> parameters) {
        return parameters.stream().map(Zoekparameter::getRubrieknummer).map(this::formatRubriek).collect(toList());
    }

    List<Lo3CategorieWaarde> bepaalZoekCriteria(final List<Zoekparameter> parameters) {
        final Map<String, String> zoekWaardes = parameters.stream().collect(
                Collectors.toMap(
                        param -> formatRubriek(param.getRubrieknummer()),
                        Zoekparameter::getZoekwaarde));

        Map<Lo3CategorieEnum, Map<Lo3ElementEnum, String>> elementenPerCategorie = zoekWaardes.keySet().stream()
                .map(this::splitRubriek)
                .map(this::convertRubriek)
                .collect(groupingBy(Pair::getLeft, mapping(pair -> pair, toMap(Pair::getRight, pair -> zoekWaardes.get(rubriekKey(pair))))));

        return elementenPerCategorie.entrySet().stream()
                .map(entry -> new Lo3CategorieWaarde(entry.getKey(), 0, 0, entry.getValue()))
                .map(PuntAdresFilter::replaceInCategorieWaarde)
                .collect(toList());

    }

    private String formatRubriek(final Integer rubriek) {
        return String.format("%06d", rubriek);
    }

    private Pair<String, String> splitRubriek(final String rubriek) {
        Matcher m = rubriekPattern.matcher(rubriek);
        Assert.isTrue(m.find(), String.format("Rubriek %s heeft een onjuist formaat.", rubriek));
        return Pair.of(m.group(CATEGORIE_GROUP), m.group(ELEMENT_GROUP));
    }

    private Pair<Lo3CategorieEnum, Lo3ElementEnum> convertRubriek(final Pair<String, String> rubriek) {
        try {
            return Pair.of(
                    Lo3CategorieEnum.getLO3Categorie(rubriek.getLeft()),
                    Lo3ElementEnum.getLO3Element(rubriek.getRight()));
        } catch (final Lo3SyntaxException e) {
            throw new IllegalArgumentException("Incorrecte categorie- of elementwaarde", e);
        }
    }

    private String rubriekKey(final Pair<Lo3CategorieEnum, Lo3ElementEnum> pair) {
        return pair.getLeft().getCategorie() + pair.getRight().getElementNummer();
    }
}
