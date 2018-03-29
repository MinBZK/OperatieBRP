/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.jbehave;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.jbehave.core.model.ExamplesTable;

/**
 * Util voor het converteren van JBehave specifieke API naar Java of BRP API.
 */
public final class ConversieUtil {

    public static final String KEY = "key";
    public static final String VALUE = "value";

    private ConversieUtil() {
    }

    public static Map<String, String> unquoteMapValues(final Map<String, String> map) {
        final Map<String, String> unquoteMap = Maps.newHashMap();
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            unquoteMap.put(entry.getKey(), entry.getValue().replace("'", ""));
        }
        return unquoteMap;
    }

    public static Map<String, String> alsKeyValueMap(final ExamplesTable table) {
        final Map<String, String> map = Maps.newHashMap();
        for (final Map<String, String> rowMap : table.getRows()) {
            map.put(rowMap.get(KEY), rowMap.get(VALUE));
        }
        return unquoteMapValues(map);
    }

    public static List<Map<String, String>> alsListMap(final ExamplesTable table) {
        final List<Map<String, String>> listMap = Lists.newLinkedList();
        for (Map<String, String> stringStringMap : table.getRows()) {
            listMap.add(unquoteMapValues(stringStringMap));
        }
        return listMap;
    }
}
