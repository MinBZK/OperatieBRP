/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.annotations.AsParameters;
import org.jbehave.core.annotations.Parameter;
import org.springframework.util.Assert;

/**
 * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met verwerkingssoort aanduiding.
 */
@AsParameters
public class GegevensRegels {
    @Parameter(name = "veld")
    public String field;
    @Parameter(name = "waarde")
    public String value;


    @Deprecated
    public static void check(List<GegevensRegels> gegevensRegels, final Map<String, Object> stringObjectMap) {
        for (GegevensRegels gegevensRegel : gegevensRegels) {
            final String waarde = String.valueOf(stringObjectMap.get(gegevensRegel.field));
            Assert.isTrue(StringUtils.equalsIgnoreCase(waarde, gegevensRegel.value));
        }
    }


    public static Map<String, String> map(final List<GegevensRegels> gegevensRegels) {
        final Map<String, String> map = Maps.newLinkedHashMap();
        for (GegevensRegels gegevensRegel : gegevensRegels) {
            map.put(gegevensRegel.field, gegevensRegel.value);
        }
        return map;
    }
}
