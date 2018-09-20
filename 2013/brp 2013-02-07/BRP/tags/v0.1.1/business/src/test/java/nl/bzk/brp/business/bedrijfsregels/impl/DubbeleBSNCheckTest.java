/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class DubbeleBSNCheckTest {

    @Mock
    private PersoonRepository persoonRepository;

    private DubbeleBSNCheck dubbeleBSNCheck;

    @Before
    public void setUp() {
        dubbeleBSNCheck = new DubbeleBSNCheck();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(dubbeleBSNCheck, "persoonRepository", persoonRepository);
    }

    @Test
    public void testBSNNogNietAanwezig() {
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456789");
        Mockito.when(persoonRepository.isBSNAlIngebruik(Mockito.anyString())).thenReturn(false);
        Melding melding = dubbeleBSNCheck.executeer(null, persoon);
        Assert.assertNull(melding);
    }

    @Test
    public void testBSNAlAanwezig() {
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("111111111");
        Mockito.when(persoonRepository.isBSNAlIngebruik(Mockito.anyString())).thenReturn(true);
        Melding melding = dubbeleBSNCheck.executeer(null, persoon);
        Assert.assertNotNull(melding);
    }
}
