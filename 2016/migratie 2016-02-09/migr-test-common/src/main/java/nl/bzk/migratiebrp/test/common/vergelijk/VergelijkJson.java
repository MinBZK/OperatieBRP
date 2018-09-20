/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import nl.bzk.migratiebrp.test.common.vergelijk.vergelijking.VergelijkingContext;

/**
 * Util om JSON te vergelijken.
 */
public class VergelijkJson {

    /**
     * Vergelijk json.
     *
     * @param verwacht
     *            verwachte json
     * @param actueel
     *            actuele json
     * @return true, als de json vergelijkbaar is, anders false
     */
    public static boolean vergelijkJson(final String verwacht, final String actueel) {
        return vergelijkJson(new VergelijkingContext(), verwacht, actueel);
    }

    /**
     * Vergelijk json.
     *
     * @param vergelijkingContext
     *            vergelijking context
     * @param verwacht
     *            verwachte json
     * @param actueel
     *            actuele json
     * @return true, als de json vergelijkbaar is, anders false
     */
    public static boolean vergelijkJson(final VergelijkingContext vergelijkingContext, final String verwacht, final String actueel) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode verwachtJsonNode = objectMapper.readTree(verwacht);
            final JsonNode actueeljsonNode = objectMapper.readTree(actueel);

            return vergelijkJsonNode(vergelijkingContext, verwachtJsonNode, actueeljsonNode);

            // return actueeljsonNode.equals(verwachtJsonNode);
        } catch (final IOException ioe) {
            throw new IllegalArgumentException("Exceptie tijdens JSON vergelijking", ioe);
        }
    }

    private static boolean vergelijkJsonNode(final VergelijkingContext vergelijkingContext, final JsonNode verwacht, final JsonNode actueel) {
        final boolean result;
        if (verwacht == null) {
            result = actueel == null;
        } else {
            if (actueel == null) {
                result = false;
            } else {
                final JsonNodeType verwachtNodeType = verwacht.getNodeType();
                if (verwachtNodeType != actueel.getNodeType()) {
                    result = false;
                } else {
                    switch (verwachtNodeType) {
                        case MISSING:
                        case NULL:
                            result = true;
                            break;
                        case ARRAY:
                            result = vergelijkJsonArray(vergelijkingContext, verwacht, actueel);
                            break;
                        case OBJECT:
                        case POJO:
                            result = vergelijkJsonObject(vergelijkingContext, verwacht, actueel);
                            break;
                        case BINARY:
                        case BOOLEAN:
                        case NUMBER:
                        case STRING:
                            result = vergelijkJsonValue(vergelijkingContext, verwacht.asText(), actueel.asText());
                            break;
                        default:
                            throw new IllegalArgumentException("JsonNodeType '" + verwachtNodeType + "' onbekend.");
                    }
                }
            }
        }

        return result;
    }

    private static boolean vergelijkJsonArray(final VergelijkingContext vergelijkingContext, final JsonNode verwacht, final JsonNode actueel) {
        boolean result = true;
        // Controleer allebei evenveel waarden in array
        if (verwacht.size() != actueel.size()) {
            result = false;
        }

        // Controleer array waarden inhoudelijk
        for (int index = 0; result && index < verwacht.size(); index++) {
            result = vergelijkJsonNode(vergelijkingContext, verwacht.get(index), actueel.get(index));
        }

        return result;
    }

    private static boolean vergelijkJsonObject(final VergelijkingContext vergelijkingContext, final JsonNode verwacht, final JsonNode actueel) {
        boolean result = true;
        // Controleer allebei evenveel fields
        if (verwacht.size() != actueel.size()) {
            result = false;
        }

        // Controleer velden inhoudelijk
        final Iterator<Map.Entry<String, JsonNode>> fieldIterator = verwacht.fields();
        while (result && fieldIterator.hasNext()) {
            final Map.Entry<String, JsonNode> field = fieldIterator.next();
            result = vergelijkJsonNode(vergelijkingContext, field.getValue(), actueel.get(field.getKey()));
        }

        return result;
    }

    private static boolean vergelijkJsonValue(final VergelijkingContext vergelijkingContext, final String verwacht, final String actueel) {
        return Vergelijk.vergelijk(vergelijkingContext, verwacht, actueel);
    }
}
