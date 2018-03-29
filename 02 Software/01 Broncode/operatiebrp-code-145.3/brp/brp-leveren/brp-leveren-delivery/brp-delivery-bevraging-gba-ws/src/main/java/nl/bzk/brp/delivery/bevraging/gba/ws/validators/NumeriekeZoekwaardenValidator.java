/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static java.util.stream.Collectors.toSet;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.WebserviceBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Valideer dat er geen niet-numerieke waarde in de zoekcriteria staat voor een numeriek veld.
 * @param <T> subtype van WebserviceBericht dat gevalideerd dient te worden
 */
public class NumeriekeZoekwaardenValidator<T extends WebserviceBericht> implements Validator<T> {
    @Override
    public Optional<Antwoord> apply(final WebserviceBericht vraag) {
        Set<Integer> rubrieken = vraag.getZoekCriteria().stream()
                .map(categorie -> categorie.getElementen().entrySet().stream().map(e -> Pair.of(categorie, e)))
                .flatMap(Function.identity())
                .filter(this::isNumeriekType)
                .filter(this::isNietNumeriekeWaarde)
                .map(this::format)
                .collect(toSet());

        return rubrieken.isEmpty()
                ? Optional.empty()
                : Optional.of(Antwoorden.foutief(
                        AntwoordBerichtResultaat.TECHNISCHE_FOUT_022,
                        rubrieken.stream().sorted().map(Object::toString).collect(Collectors.joining(", "))));
    }

    private boolean isNumeriekType(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return Lo3ElementEnum.Type.NUMERIEK == pair.getRight().getKey().getType();
    }

    private boolean isNietNumeriekeWaarde(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return StringUtils.isNotEmpty(pair.getRight().getValue()) &&
                !(StringUtils.isNumeric(schoonExactZoeken(pair.getRight().getValue())) || StringUtils.isNumeric(schoonSlimZoeken(pair.getRight().getValue())));
    }

    private String schoonExactZoeken(final String value) {
        return value.replaceAll("^\\\\", "");
    }

    private String schoonSlimZoeken(final String value) {
        return value.replaceAll("\\*$", "");
    }

    private Integer format(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return Integer.valueOf(pair.getLeft().getCategorie().getCategorie() + pair.getRight().getKey().getElementNummer());
    }
}
