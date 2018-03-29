/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.stamgegevens;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.service.cache.StamTabelCache;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * StamTabelServiceTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class StamTabelServiceImplTest {

    @Mock
    private StamTabelCache stamTabelCache;

    @InjectMocks
    private StamTabelServiceImpl stamTabelService;

    @Test
    public void testStamgegeven() {
        final String naam = "abc";
        final List<AttribuutElement> stamgegevenAttributen = new ArrayList<>();
        stamgegevenAttributen.add(getAttribuutElement(Element.GEMEENTE_CODE));
        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(getObjectElement(Element.GEMEENTE), stamgegevenAttributen, stamgegevenAttributen);
        final List<Map<String, Object>> stamgegevens = new ArrayList<>();
        final StamtabelGegevens testStamgegeven = new StamtabelGegevens(stamgegevenTabel, stamgegevens);
        Mockito.when(stamTabelCache.geefSynchronisatieStamgegevensUitRepository(naam)).thenReturn(testStamgegeven);

        final StamtabelGegevens stamtabelGegevens = stamTabelService.geefStamgegevens(naam);
        Assert.assertNotNull(stamtabelGegevens);
    }

    @Test
    public void testStamgegevenGeenAttributenInBericht() {
        final String naam = "abc";
        final List<AttribuutElement> stamgegevenAttributen = new ArrayList<>();
        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(getObjectElement(Element.GEMEENTE), stamgegevenAttributen, stamgegevenAttributen);
        final List<Map<String, Object>> stamgegevens = new ArrayList<>();
        final StamtabelGegevens testStamgegeven = new StamtabelGegevens(stamgegevenTabel, stamgegevens);
        Mockito.when(stamTabelCache.geefSynchronisatieStamgegevensUitRepository(naam)).thenReturn(testStamgegeven);

        final StamtabelGegevens stamtabelGegevens = stamTabelService.geefStamgegevens(naam);
        Assert.assertNull(stamtabelGegevens);
    }

    @Test
    public void testStamgegevenObjectNietInBericht() {
        for (ObjectElement objectElement : ElementHelper.getObjecten()) {
            if (!objectElement.inBericht()) {
                System.out.println(objectElement.toString());
            }
        }
        final String naam = "abc";
        final List<AttribuutElement> stamgegevenAttributen = new ArrayList<>();
        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(getObjectElement(Element.KOPPELVLAK), stamgegevenAttributen, stamgegevenAttributen);
        final List<Map<String, Object>> stamgegevens = new ArrayList<>();
        final StamtabelGegevens testStamgegeven = new StamtabelGegevens(stamgegevenTabel, stamgegevens);
        Mockito.when(stamTabelCache.geefSynchronisatieStamgegevensUitRepository(naam)).thenReturn(testStamgegeven);

        final StamtabelGegevens stamtabelGegevens = stamTabelService.geefStamgegevens(naam);
        Assert.assertNull(stamtabelGegevens);
    }

    @Test
    public void testStamgegevenVerkeerd() {
        final String verkeerd = "abc";
        Mockito.when(stamTabelCache.geefSynchronisatieStamgegevensUitRepository(verkeerd)).thenReturn(null);

        final StamtabelGegevens stamtabelGegevens = stamTabelService.geefStamgegevens(verkeerd);
        Assert.assertNull(stamtabelGegevens);
    }

    @Test
    public void testStamgegevenNull() {
        final StamtabelGegevens stamtabelGegevens = stamTabelService.geefStamgegevens(null);

        Assert.assertNull(stamtabelGegevens);
    }

}
