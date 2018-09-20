/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;

/**
 * Json utilities.
 */
public final class JsonUtils {

    private JsonUtils() {
        // Niet instantieerbaar
    }

    /**
     * Geef node waarde als Short.
     *
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @return waarde van de node als Short
     */
    public static Short getAsShort(final JsonNode parentNode, final String nodeName) {
        final Short result;
        final JsonNode theNode = parentNode.get(nodeName);
        if (theNode != null && !theNode.isNull() && !"".equals(theNode.asText())) {
            result = Integer.valueOf(theNode.asInt()).shortValue();
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Geef node waarde als Integer.
     *
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @return waarde van de node als Integer
     */
    public static Integer getAsInteger(final JsonNode parentNode, final String nodeName) {
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
     *
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @return waarde van de node als String
     */
    public static String getAsString(final JsonNode parentNode, final String nodeName) {
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
     * Geef node waarde als Boolean.
     *
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @param checkValue waarde waarmee gecontroleerd wordt (mag niet null zijn)
     * @param checkBoolean resultaat als de waarde van de node gelijk is aan 'checkValue' (mag null zijn)
     * @param elseBoolean resultaat als de waarde van de node niet gelijk is aan 'checkValue' (mag null zijn)
     * @return checkBoolean of elseBoolean (kan dus null zijn), naar gelang de waarde van de node en checkValue
     */
    public static Boolean getAsBoolean(
        final JsonNode parentNode,
        final String nodeName,
        final String checkValue,
        final Boolean checkBoolean,
        final Boolean elseBoolean)
    {
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
     * Geef node waarde als Nee.
     *
     * @param parentNode parent node
     * @param nodeName naam van de node
     * @param checkValue waarde waarmee gecontroleerd wordt (mag niet null zijn)
     * @return {@link Nee#N} als de waarde van de node gelijk is aan checkValue, anders null
     */
    public static Nee getAsNee(final JsonNode parentNode, final String nodeName, final String checkValue) {
        final Nee result;
        final JsonNode theNode = parentNode.get(nodeName);
        if (theNode != null && !theNode.isNull() && !checkValue.equals(theNode.asText())) {
            result = null;
        } else {
            result = Nee.N;
        }
        return result;
    }

    /**
     * Schrijf als integer.
     *
     * @param jgen json generator
     * @param nodeName node naam
     * @param waarde node waarde
     * @throws IOException bij schrijf fouten
     */
    public static void writeAsInteger(final JsonGenerator jgen, final String nodeName, final Number waarde) throws IOException {
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
     *
     * @param jgen json generator
     * @param nodeName node naam
     * @param waarde node waarde
     * @throws IOException bij schrijf fouten
     */
    public static void writeAsString(final JsonGenerator jgen, final String nodeName, final String waarde) throws IOException {
        if (waarde == null) {
            return;
        }
        jgen.writeStringField(nodeName, waarde);
    }

    /**
     * Schrijf boolean als text.
     *
     * @param jgen json generator
     * @param nodeName node naam
     * @param waarde node waarde
     * @param trueWaarde text als node waarde TRUE is
     * @param elseWaarde text als node waarde niet TRUE is
     * @throws IOException bij schrijf fouten
     */
    public static void writeAsString(
        final JsonGenerator jgen,
        final String nodeName,
        final Boolean waarde,
        final String trueWaarde,
        final String elseWaarde) throws IOException
    {
        if (waarde != null && waarde) {
            jgen.writeStringField(nodeName, trueWaarde);
        } else {
            jgen.writeStringField(nodeName, elseWaarde);
        }
    }

    /**
     * Schrijf Nee als text.
     *
     * @param jgen json generator
     * @param nodeName node naam
     * @param waarde node waarde
     * @param neeWaarde text als node waarde Nee is
     * @param geenWaarde text als node waarde niet Nee is
     * @throws IOException bij schrijf fouten
     */
    public static void writeAsString(final JsonGenerator jgen, final String nodeName, final Nee waarde, final String neeWaarde, final String geenWaarde)
        throws IOException
    {
        if (waarde == Nee.N) {
            jgen.writeStringField(nodeName, neeWaarde);
        } else {
            jgen.writeStringField(nodeName, geenWaarde);
        }
    }
}
