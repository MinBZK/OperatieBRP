/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.business.regels.impl.levering.afnemerindicatie.BRLV0001;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class AbstractBedrijfsregelManagerTest {

    private static final String REGELS_PROPERTIES = "regels.properties";

    @InjectMocks
    private final BedrijfsregelManagerImpl bedrijfsregelManagerImpl = new BedrijfsregelManagerImpl();

    @Mock
    private ApplicationContext applicationContext;

    private final Map<Enum, List<RegelInterface>> regels = new HashMap<>();

    @Before
    public void init() {
        Mockito.when(applicationContext.containsBean(Mockito.anyString())).thenReturn(Boolean.TRUE);
        Mockito.when(applicationContext.getBean(Mockito.anyString())).thenReturn(BRLV0001.class);
    }

    @Test
    public void testInitialiseerRegelsEnum() throws ConfigurationException {
        bedrijfsregelManagerImpl.initialiseerRegelsEnum(REGELS_PROPERTIES, regels, SoortBericht.class);
    }

    @Test(expected = IllegalStateException.class)
    public void testInitialiseerRegelsEnumRegelNietGevonden() throws ConfigurationException {
        Mockito.when(applicationContext.containsBean(Mockito.anyString())).thenReturn(Boolean.FALSE);

        bedrijfsregelManagerImpl.initialiseerRegelsEnum(REGELS_PROPERTIES, regels, SoortBericht.class);
    }

    @Test
    public void testDezeOfLegeLijst() {
        final List<RegelInterface> regelInterfaces = regels.get(SoortBericht.DUMMY);

        List<RegelInterface> regelInterfacesGefilterd = bedrijfsregelManagerImpl.dezeOfLegeLijst(regelInterfaces);

        Assert.assertEquals(0, regelInterfacesGefilterd.size());

        regelInterfacesGefilterd.add(new BRLV0001());
        regelInterfacesGefilterd = bedrijfsregelManagerImpl.dezeOfLegeLijst(regelInterfacesGefilterd);

        Assert.assertEquals(1, regelInterfacesGefilterd.size());
    }

    /**
     * Implementatie van de abstractbedrijfsregelmanager.
     */
    class BedrijfsregelManagerImpl extends AbstractBedrijfsregelManager {

        @Override
        public RegelParameters getRegelParametersVoorRegel(final RegelInterface bedrijfsregel) {
            return null;
        }

        @Override
        public RegelParameters getRegelParametersVoorRegel(final Regel regel) {
            return null;
        }
    }
}
