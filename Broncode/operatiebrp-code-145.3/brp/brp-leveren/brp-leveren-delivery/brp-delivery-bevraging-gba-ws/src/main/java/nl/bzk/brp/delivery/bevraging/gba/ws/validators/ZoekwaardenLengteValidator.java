/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
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
 * Valideer dat er geen zoekwaarden met een onjuiste lengte in de zoekcriteria staan.
 * @param <T> subtype van WebserviceBericht dat gevalideerd dient te worden
 */
public class ZoekwaardenLengteValidator<T extends WebserviceBericht> implements Validator<T> {
    @Override
    public Optional<Antwoord> apply(final WebserviceBericht vraag) {
        Map<Integer, String> rubrieken = vraag.getZoekCriteria().stream()
                .map(categorie -> categorie.getElementen().entrySet().stream().map(e -> Pair.of(categorie, e)))
                .flatMap(Function.identity())
                .filter(this::isGeenLegeWaarde)
                .filter(this::isExactZoeken)
                .filter(this::heeftIncorrecteLengte)
                .filter(this::isGeenGedeeltelijkOnbekendeDatum)
                .collect(toMap(this::format, this::extractWaarde));

        return rubrieken.isEmpty()
                ? Optional.empty()
                : Optional.of(Antwoorden.foutief(
                        AntwoordBerichtResultaat.TECHNISCHE_FOUT_026,
                        rubrieken.entrySet().stream()
                                .sorted(Comparator.comparing(Map.Entry::getKey))
                                .map(e -> e.getKey() + "=" + e.getValue())
                                .collect(Collectors.joining(", "))));
    }

    private boolean heeftIncorrecteLengte(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return eigenlijkeWaarde(pair).length() < pair.getRight().getKey().getMinimumLengte()
                || eigenlijkeWaarde(pair).length() > pair.getRight().getKey().getMaximumLengte();
    }

    private boolean isGeenLegeWaarde(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return StringUtils.isNotEmpty(eigenlijkeWaarde(pair));
    }

    private boolean isExactZoeken(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return extractWaarde(pair).startsWith("\\") || !(extractWaarde(pair).startsWith("\\") || extractWaarde(pair).endsWith("*"));
    }

    private boolean isGeenGedeeltelijkOnbekendeDatum(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return !(isNumeriekType(pair) && (valideerDatumLengteMetSuffix(pair, "00") || valideerDatumLengteMetSuffix(pair, "0000")));
    }

    private boolean valideerDatumLengteMetSuffix(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair, final String suffix) {
        String waarde = extractWaarde(pair) + suffix;
        return (waarde.length() == pair.getRight().getKey().getMinimumLengte() || waarde.length() == pair.getRight().getKey().getMaximumLengte()) &&
                Integer.valueOf(waarde) < 9999_99_99 && Integer.valueOf(waarde) > 1000_00_00;
    }

    private boolean isNumeriekType(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return Lo3ElementEnum.Type.NUMERIEK == pair.getRight().getKey().getType();
    }

    private Integer format(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return Integer.valueOf(pair.getLeft().getCategorie().getCategorie() + pair.getRight().getKey().getElementNummer());
    }

    private String extractWaarde(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return pair.getRight().getValue();
    }

    private String eigenlijkeWaarde(final Pair<Lo3CategorieWaarde, Map.Entry<Lo3ElementEnum, String>> pair) {
        return extractWaarde(pair).replaceAll("\\\\", "");
    }
}
