/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.cache;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.StamTabelRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class StamTabelCacheTest {

    @Mock
    private StamTabelRepository stamTabelRepository;

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private final StamTabelCacheImpl stamTabelCache = new StamTabelCacheImpl();

    private String expressie = "WAAR";
    private List<Element> alleElementen;
    private Element       element1;

    @Before
    public final void voorTest() {
        this.alleElementen = new ArrayList<>();
        element1 = TestElementBuilder.maker().metId(1).metSoort(SoortElement.ATTRIBUUT).metExpressie(expressie).maak();
        Element element2 = TestElementBuilder.maker().metId(2).metSoort(SoortElement.GROEP).metExpressie(expressie).maak();
        this.alleElementen.add(element1);
        this.alleElementen.add(element2);
        when(stamTabelRepository.geefAlleElementen()).thenReturn(alleElementen);
        stamTabelCache.naMaak();
    }

    @Test
    public void geeftAlleElementen() {
        final Collection<Element> alleElementenVanCache = stamTabelCache.geefAlleElementen();
        assertNotNull(alleElementenVanCache);
        assertEquals(alleElementen.size(), alleElementenVanCache.size());
    }

    @Test
    public void geeftAlleExpressieParserResultatenVanAttributeElementen() {
        final Map<String, ParserResultaat> parserResultaatMap = stamTabelCache.geefAlleExpressieParserResultatenVanAttributeElementen();

        assertNotNull(parserResultaatMap);
        assertEquals("alle elementen minus 1 groep element", alleElementen.size() - 1, parserResultaatMap.size());
    }

    @Test
    public void geeftElementPerId() {
        final Element element = stamTabelCache.geefElementById(1);

        assertThat(element, is(element1));
    }
}
