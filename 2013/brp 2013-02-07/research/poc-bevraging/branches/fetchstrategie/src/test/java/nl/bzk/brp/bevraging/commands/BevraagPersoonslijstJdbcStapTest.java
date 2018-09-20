/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.bevraging.AbstractIntegrationTest;
import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class BevraagPersoonslijstJdbcStapTest extends AbstractIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BevraagPersoonslijstJdbcStapTest.class);

    @Inject
    @Named("bevraagPersoonslijstJdbcStap")
    private Command bevragingStap;

    @Test
    public void shouldReturnPersoonsLijst() throws Exception {
        // given
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_THREADS, 2);
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);
        context.put(ContextParameterNames.BSNLIJST,
                    Arrays.asList(100000001, 100000002, 100000003, 123456789, 234567891));

        // when
        bevragingStap.execute(context);

        // then
        List<BevraagInfo> average = (List) context.get(ContextParameterNames.TASK_INFO_LIJST);
        Assert.assertThat(average.size(), Matchers.is(5));
    }
}
