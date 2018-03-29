/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.cache;

import com.google.common.collect.Sets;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieLijstMakerServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieLijstMakerServiceImplTest {

    @Mock
    private ConfiguratieService configuratieService;

    @InjectMocks
    private SelectieLijstMakerServiceImpl selectieLijstMakerService;

    @Before
    public void voorTest() {
        Mockito.when(configuratieService.getSelectiebestandFolder()).thenReturn("target/test-classes/selectielijsten");
    }

    @Test
    public void testMaakLijstDienst1() {
        final SelectieLijst selectieLijst = selectieLijstMakerService.maak(1, 1);
        Assert.assertNotNull(selectieLijst);
        Assert.assertTrue(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER == selectieLijst.getWaardeType().getElement());
        Assert.assertTrue(selectieLijst.getWaarden().size() == 2);
        Assert.assertTrue(selectieLijst.getWaarden().containsAll(Sets.newHashSet("123456789", "987654321")));
        Assert.assertFalse(selectieLijst.isLeeg());
    }

    @Test
    public void testMaakLijstDienst2() {
        final SelectieLijst selectieLijst = selectieLijstMakerService.maak(2, 2);
        Assert.assertNotNull(selectieLijst);
        Assert.assertTrue(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER == selectieLijst.getWaardeType().getElement());
        Assert.assertTrue(selectieLijst.getWaarden().size() == 3);
        Assert.assertTrue(selectieLijst.getWaarden().containsAll(Sets.newHashSet("223456789", "323456789", "987654321")));
        Assert.assertFalse(selectieLijst.isLeeg());
    }

    @Test
    public void testMaakLijstDienstGeenLijst() {
        final SelectieLijst selectieLijst = selectieLijstMakerService.maak(3, 3);
        Assert.assertNotNull(selectieLijst);
        Assert.assertTrue(selectieLijst.isLeeg());
    }

    @Test
    public void testMaakLijst_InvalidHeader() {
        final SelectieLijst selectieLijst = selectieLijstMakerService.maak(4, 4);
        Assert.assertEquals(SelectieLijst.GEEN_LIJST, selectieLijst);
    }
}
