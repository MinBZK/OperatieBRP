/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.dataaccess.BestandRepository;
import org.apache.commons.chain.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test of de rapportagestap output bij gegeven input op de juiste plek terecht komt en de output genereert.
 */
@RunWith(MockitoJUnitRunner.class)
public class RapporteerStapTest {
    @Mock
    private Context context;

    @Mock
    private BestandRepository bestandRepository;

    @InjectMocks
    private RapporteerStap rapporteerStap = new RapporteerStap();

    @Before
    public void setup() {
        doNothing().when(bestandRepository).schrijfRegels(anyString(), anyListOf(String.class));
    }

    @Test
    public void test() throws Exception {
        List<BevraagInfo> infos = new ArrayList<BevraagInfo>();
        infos.add(new BevraagInfo("bsn", "OK", 123));
        infos.add(new BevraagInfo("bsn", "OK", 286));
        infos.add(new BevraagInfo("bsn", "OK", 354));
        infos.add(new BevraagInfo("bsn2", "FAIL", 31));
        infos.add(new BevraagInfo("bsn2", "FAIL", 41));
        infos.add(new BevraagInfo("bsn2", "FAIL", 23));

        Map<String, Object> dbinfo = new HashMap<String, Object>(1);
        dbinfo.put("cachedEntities", Collections.emptyList());

        when(context.get(ContextParameterNames.TASK_INFO_LIJST)).thenReturn(infos);
        when(context.get(ContextParameterNames.SCENARIO)).thenReturn("RapporteerStapTest");
        when(context.get(ContextParameterNames.BSNLIJST)).thenReturn(Collections.emptyList());
        when(context.get(ContextParameterNames.AANTAL_THREADS)).thenReturn(2);
        when(context.get(ContextParameterNames.AANTAL_PERSOONSLIJSTEN)).thenReturn(200);
        when(context.get(ContextParameterNames.DATABASE_INFO)).thenReturn(dbinfo);
        when(context.get(ContextParameterNames.COMMENT)).thenReturn("Foo bar");
        when(context.get(ContextParameterNames.TIMING)).thenReturn("super fast");

        boolean result = rapporteerStap.postprocess(context, null);

        Assert.assertFalse(result);
    }

    @Test
    public void noDataShouldResultInNoReport() throws Exception {
        when(context.get(ContextParameterNames.TASK_INFO_LIJST)).thenReturn(null);

        boolean result = rapporteerStap.postprocess(context, null);

        Assert.assertTrue(result);
    }

}
