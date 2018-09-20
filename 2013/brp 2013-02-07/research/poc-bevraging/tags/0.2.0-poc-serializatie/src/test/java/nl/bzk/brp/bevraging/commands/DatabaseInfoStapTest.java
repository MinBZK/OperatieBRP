/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.bevraging.AbstractIntegrationTest;
import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.junit.Test;

/**
 */
public class DatabaseInfoStapTest extends AbstractIntegrationTest {

    @Inject
    @Named("databaseInfoStap")
    private Command command;

    @Test
    public void test() throws Exception {
        // given
        Context context = new ContextBase();

        // when
        command.execute(context);

        // then
        Map<String, Object> result = (Map<String, Object>) context.get(ContextParameterNames.DATABASE_INFO);
        assertThat(result, notNullValue());
        assertThat(result.size(), is(5));
        assertThat(result.keySet(), containsInAnyOrder("cachedEntities", "dataBaseUrl", "persoonModelCount",
                                                       "driverName", "dataBaseProductName"));
    }
}
