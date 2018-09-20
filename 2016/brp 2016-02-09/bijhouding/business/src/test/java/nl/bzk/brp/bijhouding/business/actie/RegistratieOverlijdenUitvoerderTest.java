/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.OverlijdenGroepVerwerker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistratieOverlijdenUitvoerderTest {

    private RegistratieOverlijdenUitvoerder registratieOverlijdenUitvoerder;

    @Before
    public void init() {
        registratieOverlijdenUitvoerder = new RegistratieOverlijdenUitvoerder();
    }

    @Test
    public void testVerzamelVerwerkingsregels() {
        Assert.assertEquals(0, getVerwerkingsregels(registratieOverlijdenUitvoerder).size());

        registratieOverlijdenUitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(registratieOverlijdenUitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(registratieOverlijdenUitvoerder).get(0)
                                  instanceof OverlijdenGroepVerwerker);
    }

    /**
     * Haalt de verwerkingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie overlijden uitvoerder
     * @return lijst met verwerkingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels(final RegistratieOverlijdenUitvoerder regUitvoerder) {
        return (List<Verwerkingsregel>) ReflectionTestUtils.getField(regUitvoerder, "verwerkingsregels");
    }
}
