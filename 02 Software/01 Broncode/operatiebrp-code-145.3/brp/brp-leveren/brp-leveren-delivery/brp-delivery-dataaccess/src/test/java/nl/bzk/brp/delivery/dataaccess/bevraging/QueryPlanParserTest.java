/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.io.IOException;
import java.io.InputStream;
import nl.bzk.algemeenbrp.util.common.postgres.QueryPlan;
import nl.bzk.algemeenbrp.util.common.postgres.QueryPlanParser;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * QueryPlanParserTest.
 */
public class QueryPlanParserTest {

    @Test
    public void parseQueryPlan() throws IOException {
        final InputStream resourceAsStream = QueryPlanParserTest.class.getResourceAsStream("/data/queryplan.json");
        final String queryPlanJson = IOUtils.toString(resourceAsStream);
        final QueryPlan queryPlan = new QueryPlanParser(queryPlanJson).parse();
        Assert.assertEquals(53.56, queryPlan.getPlan().getTotalCost(), 0);
    }
}
