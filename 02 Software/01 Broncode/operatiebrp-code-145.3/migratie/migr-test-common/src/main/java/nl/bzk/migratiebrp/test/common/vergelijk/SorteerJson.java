/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Json sorteerder.
 */
public final class SorteerJson {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;

    private SorteerJson() {
        // Niet instantieerbaar
    }

    /**
     * Sorteer een JSON string.
     * @param input json
     * @return gesorteerde json
     */
    public static String sorteer(String input) {
        try {
            final JsonNode node = OBJECT_MAPPER.readTree(input);

            JsonNode gesorteerdeNode = sorteer(node);

            return naarString(gesorteerdeNode);
        } catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static JsonNode sorteer(final JsonNode input) throws JsonProcessingException {
        final JsonNode resultaat;
        final JsonNodeType nodeType = input.getNodeType();
        switch (nodeType) {
            case ARRAY:
                resultaat = sorteerJsonArray(input);
                break;
            case OBJECT:
                resultaat = sorteerJsonObject(input);
                break;
            default:
                resultaat = input;
        }

        return resultaat;
    }

    private static JsonNode sorteerJsonObject(final JsonNode input) throws JsonProcessingException {
        final SortedSet<String> gesorteerdeFieldNames = new TreeSet<>();

        final Iterator<String> fieldNameIterator = input.fieldNames();
        while(fieldNameIterator.hasNext()) {
            gesorteerdeFieldNames.add(fieldNameIterator.next());
        }

        final ObjectNode resultaat = JSON_NODE_FACTORY.objectNode();
        for(final String fieldName : gesorteerdeFieldNames) {
            resultaat.set(fieldName, sorteer(input.get(fieldName)));
        }

        return resultaat;
    }

    private static JsonNode sorteerJsonArray(final JsonNode input) throws JsonProcessingException {
        final SortedMap<String,JsonNode> gesorteerdeArrayItems = new TreeMap<>();

        for(int index = 0; index < input.size(); index++) {
            JsonNode arrayItem = input.get(index);
            gesorteerdeArrayItems.put(naarString(arrayItem), sorteer(arrayItem));
        }

        final ArrayNode resultaat = JSON_NODE_FACTORY.arrayNode(input.size());
        resultaat.addAll(gesorteerdeArrayItems.values());

        return resultaat;
    }

    private static String naarString(final JsonNode arrayItem) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(arrayItem);

    }
}
