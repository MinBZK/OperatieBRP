/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.postgres;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * QueryPlanParser.
 */
public final class QueryPlanParser {

    private final String queryPlanStr;

    /**
     * @param queryPlanStr queryPlanStr
     */
    public QueryPlanParser(final String queryPlanStr) {
        this.queryPlanStr = queryPlanStr;
    }

    /**
     * @return queryPlan
     * @throws IOException fout bij parsen queryplan
     */
    public QueryPlan parse() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final QueryPlan[] array = mapper.readValue(queryPlanStr, QueryPlan[].class);
        return array[0];
    }
}
