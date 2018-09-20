/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.bevraging.AbstractIntegrationTest;
import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: Add documentation
 */
public class SerializeerPersoonsLijstStapTest extends AbstractIntegrationTest {

    @Inject @Named("serializeerPersoonsLijstStap")
    private Command command;

    @Test
    public void test() throws Exception {
        // given
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_THREADS, 2);
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);
        context.put(ContextParameterNames.BSNLIJST,
                    Arrays.asList(100000001, 100000002, 100000003, 123456789, 234567891));

        // when
        command.execute(context);

        // then
        List<BevraagInfo> data = (List<BevraagInfo>) context.get(ContextParameterNames.TASK_INFO_LIJST);
        assertThat(data.size(), is(5));
        assertThat(data.get(0).getResult(), is("OK"));
    }

}
