/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Json utilities.
 */
public interface JsonUtils {

    /**
     * Geef node waarde als Short.
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @return waarde van de node als Short
     */
    static Short getAsShort(final JsonNode parentNode, final String nodeName) {
        final Short result;
        final Integer resultAsInteger = getAsInteger(parentNode, nodeName);
        if (resultAsInteger != null) {
            result = resultAsInteger.shortValue();
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Geef node waarde als Integer.
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @return waarde van de node als Integer
     */
    static Integer getAsInteger(final JsonNode parentNode, final String nodeName) {
        final Integer result;
        final JsonNode theNode = parentNode.get(nodeName);
        if (theNode != null && !theNode.isNull() && !"".equals(theNode.asText())) {
            result = theNode.asInt();
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Geef node waarde als String.
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @return waarde van de node als String
     */
    static String getAsString(final JsonNode parentNode, final String nodeName) {
        final String result;
        final JsonNode theNode = parentNode.get(nodeName);
        if (theNode != null && !theNode.isNull() && !"".equals(theNode.asText())) {
            result = theNode.asText();
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Geef de subnodes van een node.
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @return lijst met de gevonden subnodes van de node
     */
    static List<JsonNode> getJsonNodesAsList(final JsonNode parentNode, final String nodeName) {
        final List<JsonNode> result = new ArrayList<>();
        final JsonNode theNode = parentNode.get(nodeName);
        if (theNode == null) {
            return result;
        }
        final JsonNode theNodeContent = theNode.get("content");
        if (theNodeContent != null && !theNodeContent.isNull()) {
            final Iterator<JsonNode> theNodeElements = theNodeContent.elements();
            while (theNodeElements.hasNext()) {
                final JsonNode theNodeElement = theNodeElements.next();
                result.add(theNodeElement);
            }
        }
        return result;
    }

    /**
     * Geef node waarde als Character.
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @return waarde van de node als Character
     */
    static Character getAsCharacter(final JsonNode parentNode, final String nodeName) {
        final Character result;
        final JsonNode theNode = parentNode.get(nodeName);
        if (theNode != null && !theNode.isNull() && !"".equals(theNode.asText())) {
            result = theNode.asText().charAt(0);
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Geef node waarde als Boolean.
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @param checkValue waarde waarmee gecontroleerd wordt (mag niet null zijn)
     * @param checkBoolean resultaat als de waarde van de node gelijk is aan 'checkValue' (mag null zijn)
     * @param elseBoolean resultaat als de waarde van de node niet gelijk is aan 'checkValue' (mag null zijn)
     * @return checkBoolean of elseBoolean (kan dus null zijn), naar gelang de waarde van de node en checkValue
     */
    static Boolean getAsBoolean(
            final JsonNode parentNode,
            final String nodeName,
            final String checkValue,
            final Boolean checkBoolean,
            final Boolean elseBoolean) {
        final Boolean result;
        final JsonNode theNode = parentNode.get(nodeName);
        if (theNode != null && !theNode.isNull() && checkValue.equals(theNode.asText())) {
            result = checkBoolean;
        } else {
            result = elseBoolean;
        }
        return result;
    }

    /**
     * Schrijf als integer.
     * @param jgen json generator
     * @param nodeName node naam
     * @param waarde node waarde
     * @throws IOException bij schrijf fouten
     */
    static void writeAsInteger(final JsonGenerator jgen, final String nodeName, final Number waarde) throws IOException {
        if (waarde == null) {
            return;
        }
        if (waarde instanceof Long) {
            jgen.writeNumberField(nodeName, waarde.longValue());
        } else {
            jgen.writeNumberField(nodeName, waarde.intValue());
        }
    }

    /**
     * Schrijf als text.
     * @param jgen json generator
     * @param nodeName node naam
     * @param waarde node waarde
     * @throws IOException bij schrijf fouten
     */
    static void writeAsString(final JsonGenerator jgen, final String nodeName, final String waarde) throws IOException {
        if (waarde == null) {
            return;
        }
        jgen.writeStringField(nodeName, waarde);
    }

    /**
     * Schrijf boolean als text.
     * @param jgen json generator
     * @param nodeName node naam
     * @param waarde node waarde
     * @param trueWaarde text als node waarde TRUE is
     * @param elseWaarde text als node waarde niet TRUE is
     * @throws IOException bij schrijf fouten
     */
    static void writeAsString(final JsonGenerator jgen, final String nodeName, final Boolean waarde, final String trueWaarde, final String elseWaarde)
            throws IOException {
        if (waarde != null && waarde) {
            jgen.writeStringField(nodeName, trueWaarde);
        } else {
            jgen.writeStringField(nodeName, elseWaarde);
        }
    }

    /**
     * Schrijf object als Array.
     * @param jgen json generator
     * @param nodeName node naam
     * @param waarde node waarde
     * @throws IOException bij schrijf fouten
     */
    static void writeAsArray(final JsonGenerator jgen, final String nodeName, final Collection<?> waarde) throws IOException {
        if (waarde != null && !waarde.isEmpty()) {
            jgen.writeFieldName(nodeName);
            jgen.writeStartArray(waarde.size());
            for (final Object huidigObject : waarde) {
                jgen.writeObject(huidigObject);
            }
            jgen.writeEndArray();
        }
    }

}
