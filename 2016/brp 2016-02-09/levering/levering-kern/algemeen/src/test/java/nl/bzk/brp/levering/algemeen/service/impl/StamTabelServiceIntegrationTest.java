/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import org.junit.Test;


public class StamTabelServiceIntegrationTest extends AbstractIntegratieTest {

    @Inject
    private StamTabelService stamTabelService;

    @Test
    public final void geefAlleElementen() {
        final Collection<Element> elementen = stamTabelService.geefAlleElementen();
        assertThat(elementen, is(not(nullValue())));
        assertThat(elementen.size(), is(greaterThan(0)));
    }

    @Test
    public final void geefAlleExpressieParserResultatenVanAttribuutElementenPerExpressie() {
        final Map<String, ParserResultaat> elementen = stamTabelService.geefAlleExpressieParserResultatenVanAttribuutElementenPerExpressie();
        assertThat(elementen, is(not(nullValue())));
        assertThat(elementen.size(), is(greaterThan(0)));
    }

    @Test
    public void geefElementById() {
        final Element element = stamTabelService.geefElementById(3637);
        assertThat(element, is(not(nullValue())));
    }
}
