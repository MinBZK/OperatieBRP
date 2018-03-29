/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.gerelateerd.GerelateerdeInformatieDao;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistreerGerelateerdeInformatieActionTest {

    @Mock
    private BerichtenDao berichtenDao;

    @Mock
    private GerelateerdeInformatieDao gerelateerdeInformatieDao;

    @InjectMocks
    private RegistreerGerelateerdeInformatieAction subject;

    @Mock
    private ExecutionContext executionContext;
    @Mock
    private ProcessInstance processInstance;

    @Before
    public void setupContext() {
        ExecutionContext.pushCurrentContext(executionContext);
    }

    @After
    public void teardownContext() {
        ExecutionContext.popCurrentContext(executionContext);
    }

    @Test
    public void test() {
        Mockito.when(executionContext.getProcessInstance()).thenReturn(processInstance);
        final Long processInstanceId = 142L;
        Mockito.when(processInstance.getId()).thenReturn(processInstanceId);

        // Parameters
        final Map<String, Object> parameters = new HashMap<>();
        final String berichtIdVariabele = "testBericht";
        final Long berichtId = 2348734L;
        parameters.put("berichtIdVariabele", berichtIdVariabele);
        parameters.put(berichtIdVariabele, berichtId);

        // Bericht
        final GerelateerdeInformatie gerelateerdeInformatie = new GerelateerdeInformatie(null, Arrays.asList("0560", "0599"), Arrays.asList("1234567890"));
        final Bericht bericht = new TestBericht(gerelateerdeInformatie);
        Mockito.when(berichtenDao.leesBericht(berichtId)).thenReturn(bericht);

        // Execute
        Assert.assertNull(subject.execute(parameters));

        // Verify
        Mockito.verify(berichtenDao).leesBericht(berichtId);
        Mockito.verify(gerelateerdeInformatieDao).toevoegenGerelateerdeGegevens(processInstanceId, gerelateerdeInformatie);
    }

    private final class TestBericht extends AbstractBericht {
        private static final long serialVersionUID = 1L;

        private final GerelateerdeInformatie gerelateerdeInformatie;

        public TestBericht(final GerelateerdeInformatie gerelateerdeInformatie) {
            super();
            this.gerelateerdeInformatie = gerelateerdeInformatie;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.bericht.model.Bericht#getBerichtType()
         */
        @Override
        public String getBerichtType() {
            return "Test";
        }

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.bericht.model.Bericht#getStartCyclus()
         */
        @Override
        public String getStartCyclus() {
            return null;
        }

        @Override
        public String format() throws BerichtInhoudException {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
         */
        @Override
        public GerelateerdeInformatie getGerelateerdeInformatie() {
            return gerelateerdeInformatie;
        }

    }
}
