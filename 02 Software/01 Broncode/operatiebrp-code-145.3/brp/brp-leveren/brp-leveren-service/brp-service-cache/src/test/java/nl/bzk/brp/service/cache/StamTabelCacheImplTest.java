/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.service.dalapi.StamTabelRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * Unit test voor {@link StamTabelCacheImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StamTabelCacheImplTest {

    @Mock
    private StamTabelRepository stamTabelRepository;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private BrpCache brpCache;

    @InjectMocks
    private StamTabelCacheImpl stamTabelCache;


    @Before
    public final void voorTest() {
        vulCache();

        final CacheEntry cacheEntry = stamTabelCache.herlaad();
        Mockito.when(brpCache.getCache(StamTabelCacheImpl.CACHE_NAAM)).thenReturn(cacheEntry.getData());
    }

    @Test
    public final void testGeefStamgegevensGevuld() {
        final StamtabelGegevens stamtabelGegevens = stamTabelCache.geefSynchronisatieStamgegevensUitRepository(
                getObjectElement(Element.GEMEENTE).getXmlNaam() + StamtabelGegevens.TABEL_POSTFIX);
        Assert.assertNotNull(stamtabelGegevens);

        final List<Map<String, Object>> lijst = stamtabelGegevens.getStamgegevens();
        Assert.assertNotNull(lijst);
        Assert.assertEquals(1, lijst.size());
    }

    @Test
    public final void testGeefStamgegevensLeeg() {
        final StamtabelGegevens stamtabelGegevens = stamTabelCache.geefSynchronisatieStamgegevensUitRepository(
                getObjectElement(Element.SOORTACTIE).getXmlNaam() + StamtabelGegevens.TABEL_POSTFIX);
        Assert.assertNotNull(stamtabelGegevens);
        Assert.assertNotNull(stamtabelGegevens.getStamgegevenTabel());
        Assert.assertTrue(stamtabelGegevens.getStamgegevens().isEmpty());
    }

    @Test
    public final void testGeefStamgegevensNull() {
        final StamtabelGegevens stamtabelGegevens = stamTabelCache.geefSynchronisatieStamgegevensUitRepository("abc");
        Assert.assertNull(stamtabelGegevens);

    }

    private void vulCache() {
        final ObjectElement object = getObjectElement(Element.GEMEENTE);
        final List<AttribuutElement> stamgegevenAttributenInBericht = new ArrayList<>();
        final List<AttribuutElement> stamgegevenAttributen = new ArrayList<>();

        for (GroepElement groepElement : object.getGroepen()) {
            for (AttribuutElement attribuutElement : groepElement.getAttributenInGroep()) {
                stamgegevenAttributenInBericht.add(attribuutElement);
                stamgegevenAttributen.add(attribuutElement);
            }
        }
        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(object, stamgegevenAttributenInBericht, stamgegevenAttributen);
        final List<Map<String, Object>> stamgegevensVoorTabel = new ArrayList<>();
        final Map<String, Object> stringObjectMap = new HashMap<>();
        for (AttribuutElement attribuutElement : stamgegevenAttributen) {
            stringObjectMap.put(attribuutElement.getElement().getElementWaarde().getIdentdb().toLowerCase(), maakWaarde(attribuutElement));
        }
        stamgegevensVoorTabel.add(stringObjectMap);

        //zonder attributen
        final ObjectElement objectLeeg = getObjectElement(Element.SOORTACTIE);
        final List<AttribuutElement> stamgegevenAttributenInBerichtLeeg = new ArrayList<>();
        final List<AttribuutElement> stamgegevenAttributenLeeg = new ArrayList<>();
        final StamgegevenTabel stamgegevenTabelLeeg = new StamgegevenTabel(objectLeeg, stamgegevenAttributenInBerichtLeeg, stamgegevenAttributenLeeg);

        //Element met ref naar SoortElement
        final ObjectElement objectElement = getObjectElement(Element.ELEMENT);
        final List<AttribuutElement> stamgegevenAttributenInBerichtElement = new ArrayList<>();
        final List<AttribuutElement> stamgegevenAttributenElement = new ArrayList<>();

        for (GroepElement groepElement : objectElement.getGroepen()) {
            for (AttribuutElement attribuutElement : groepElement.getAttributenInGroep()) {
                stamgegevenAttributenInBerichtElement.add(attribuutElement);
                stamgegevenAttributenElement.add(attribuutElement);
            }
        }
        final StamgegevenTabel stamgegevenTabelElement = new StamgegevenTabel(objectElement, stamgegevenAttributenInBerichtElement,
                stamgegevenAttributenElement);
        final List<Map<String, Object>> stamgegevensVoorTabelElement = new ArrayList<>();

        final Map<String, Object> stringObjectMapElement = new HashMap<>();
        for (AttribuutElement attribuutElement : stamgegevenAttributenElement) {
            stringObjectMapElement.put(attribuutElement.getElement().getElementWaarde().getIdentdb().toLowerCase(), maakWaarde(attribuutElement));
        }
        stamgegevensVoorTabelElement.add(stringObjectMapElement);

        Mockito.when(stamTabelRepository.vindAlleStamgegevensVoorTabel(Mockito.any())).thenAnswer(invocationOnMock -> {
            final Object[] args = invocationOnMock.getArguments();
            StamgegevenTabel stamgegevenTabelArg = (StamgegevenTabel) args[0];
            if (stamgegevenTabelArg.getObjectElement().equals(stamgegevenTabel.getObjectElement())) {
                return stamgegevensVoorTabel;
            } else if (stamgegevenTabelArg.getObjectElement().equals(stamgegevenTabelElement.getObjectElement())) {
                return stamgegevensVoorTabelElement;
            } else if (stamgegevenTabelArg.getObjectElement().equals(stamgegevenTabelLeeg.getObjectElement())) {
                return Collections.<Map<String, Object>>emptyList();
            } else {
                return null;
            }
        });
    }

    private Object maakWaarde(final AttribuutElement attribuutElement) {
        if (attribuutElement.getType() != null) {
            //referentie
            return 1;
        }
        final Object waarde;
        switch (attribuutElement.getDatatype()) {
            case STRING:
                waarde = "x";
                break;
            case GETAL:
            case GROOTGETAL:
                waarde = 1;
                break;
            case DATUM:
                waarde = 201010;
                break;
            default:
                waarde = "y";
        }
        return waarde;
    }

}
