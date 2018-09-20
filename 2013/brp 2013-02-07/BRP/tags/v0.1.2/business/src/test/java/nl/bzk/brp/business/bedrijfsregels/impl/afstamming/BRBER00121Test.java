/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBER00121Test {

    @Mock
    private PersoonRepository persoonRepository;

    private BRBER00121   dubbeleBSNCheck;

    @Before
    public void setUp() {
        dubbeleBSNCheck = new BRBER00121();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(dubbeleBSNCheck, "persoonRepository", persoonRepository);
    }

    @Test
    public void testBSNNogNietAanwezig() {
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456789");

        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        betrokkenheid.setBetrokkene(persoon);

        Relatie relatie = new Relatie();
        relatie.voegPartnerBetrokkenheidToe(betrokkenheid);

        Mockito.when(persoonRepository.isBSNAlIngebruik(Matchers.anyString())).thenReturn(false);
        Melding melding = dubbeleBSNCheck.executeer(null, relatie, null);
        Assert.assertNull(melding);
    }

    @Test
    public void testBSNAlAanwezig() {
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("111111111");

        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        betrokkenheid.setBetrokkene(persoon);

        Relatie relatie = new Relatie();
        relatie.voegPartnerBetrokkenheidToe(betrokkenheid);

        Mockito.when(persoonRepository.isBSNAlIngebruik(Matchers.anyString())).thenReturn(true);
        Melding melding = dubbeleBSNCheck.executeer(null, relatie, null);
        Assert.assertNotNull(melding);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenRelatie() {
        dubbeleBSNCheck.executeer(null, new Persoon(), null);
    }
}
