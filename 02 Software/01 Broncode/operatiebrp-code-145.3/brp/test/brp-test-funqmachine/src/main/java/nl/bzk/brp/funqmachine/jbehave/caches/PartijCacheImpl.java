/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.caches;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import nl.bzk.brp.funqmachine.processors.SqlProcessor;
import org.springframework.stereotype.Component;

/**
 * Partijcache.
 */
@Component
public class PartijCacheImpl implements PartijCache {

    private Data data;

    /**
     * Initialisatie van de {@link PartijCache}.
     */
    @PostConstruct
    public void init() {
        final List<Map<String, Object>> queryResult = SqlProcessor.getInstance().voerQueryUit("select code,naam,oin from kern.partij");
        data = new Data(queryResult);
    }

    @Override
    public String geefPartijCode(final String partijNaam) {
        return data.partijNaamNaarCode.get(partijNaam);
    }

    @Override
    public String geefPartijNaam(final String partijCode) {
        return data.partijCodeNaarNaam.get(partijCode);
    }

    @Override
    public String geefPartijOin(final String partijNaam) {
        return data.partijNaamNaarOin.get(partijNaam);
    }

    /**
     * Data. Data holder for swap in.
     */
    private static class Data {
        private final Map<String, String> partijCodeNaarNaam;
        private final Map<String, String> partijNaamNaarCode;
        private final Map<String, String> partijNaamNaarOin;

        /**
         * Data constructor.
         */
        Data(final List<Map<String, Object>> queryResult) {
            final Map<String, String> partijCodeNaarNaamTemp = new HashMap<>();
            final Map<String, String> partijNaamNaarCodeTemp = new HashMap<>();
            final Map<String, String> partijNaamNaarOinTemp = new HashMap<>();

            for (final Map<String, Object> row : queryResult) {
                partijCodeNaarNaamTemp.put((String) row.get("code"), (String) row.get("naam"));
                partijNaamNaarOinTemp.put((String) row.get("naam"), (String) row.get("oin"));
                partijNaamNaarCodeTemp.put((String) row.get("naam"), (String) row.get("code"));
            }

            this.partijCodeNaarNaam = Collections.unmodifiableMap(partijCodeNaarNaamTemp);
            this.partijNaamNaarOin = Collections.unmodifiableMap(partijNaamNaarOinTemp);
            this.partijNaamNaarCode = Collections.unmodifiableMap(partijNaamNaarCodeTemp);
        }
    }
}
