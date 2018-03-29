/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Test util voor JSON.
 */
public class JsonUtil {

    private JsonUtil() {

    }

    public static boolean jsonEquals(final String leftJson, final String rightJson) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final JsonNode tree1 = mapper.readTree(leftJson);
        final JsonNode tree2 = mapper.readTree(rightJson);

        return tree1.equals(tree2);
    }
}
