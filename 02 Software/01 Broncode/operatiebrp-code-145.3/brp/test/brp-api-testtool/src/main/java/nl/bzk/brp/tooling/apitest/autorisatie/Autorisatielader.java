/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;

/**
 * Util voor laden van de autorisaties.
 */
public final class Autorisatielader {

    private static final Map<String, AutorisatieData> DATA_SET_MAP = Maps.newConcurrentMap();

    private Autorisatielader() {
    }

    /**
     * Laadt de gegeven autorisaties.
     *
     * @param resources lijst met autorisaties
     * @return een AutorisatieData object
     */
    public static synchronized AutorisatieData laadAutorisatie(final List<Resource> resources) {
        final String key = resources.stream().map(Object::toString).collect(Collectors.toList()).toString();
        if (DATA_SET_MAP.containsKey(key)) {
            return DATA_SET_MAP.get(key);
        }
        final IdGenerator idGenerator = new IdGenerator();
        final AutorisatieData autorisatieData = new AutorisatieData();
        try {
            for (final Resource resource : resources) {
                final DslParser resultaat = AutorisatieDslUtil.parse(resource, idGenerator);
                resultaat.collect(autorisatieData);
            }
        } catch (IOException e) {
            throw new RuntimeException("Laden autorisaties mislukt", e);
        }
        DATA_SET_MAP.put(key, autorisatieData);
        return autorisatieData;
    }
}
