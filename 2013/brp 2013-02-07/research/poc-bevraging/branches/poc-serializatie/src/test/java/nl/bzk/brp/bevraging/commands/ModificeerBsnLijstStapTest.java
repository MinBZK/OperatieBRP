/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ModificeerBsnLijstStapTest {

    private ModificeerBsnLijstStap stap = new ModificeerBsnLijstStap();
    private Context context = new ContextBase();

    @Before
    public void setup() {
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 7);
        context.put(ContextParameterNames.DUBBELE_PER, 3);
        context.put(ContextParameterNames.BSNLIJST,
            new ArrayList<Integer>(Arrays.asList(100000001, 100000002, 100000003, 123456789, 234567891, 987654321, 654321987)));
    }

    @Test
    public void test() throws Exception {
        stap.execute(context);

        List<Integer> bsns = (List<Integer>) context.get(ContextParameterNames.BSNLIJST);

        assertThat(bsns.size(), is(7));
        assertEquals(bsns.get(2), bsns.get(3));
    }
}
