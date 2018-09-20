/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingGroepVerwerker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test klasse ten behoeve van de functionaliteit geboden door de {@link RegistratieBijhoudingUitvoerder}.
 */
public class RegistratieBijhoudingUitvoerderTest {

    private RegistratieBijhoudingUitvoerder registratieBijhoudingUitvoerder;

    @Before
    public void init() {
        registratieBijhoudingUitvoerder = new RegistratieBijhoudingUitvoerder();
    }

    @Test
    public void testVerzamelVerwerkingsregels() {
        Assert.assertEquals(0, getVerwerkingsregels(registratieBijhoudingUitvoerder).size());

        registratieBijhoudingUitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(registratieBijhoudingUitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(registratieBijhoudingUitvoerder).get(0)
            instanceof BijhoudingGroepVerwerker);
    }

    /**
     * Haalt de verwerkingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie overlijden uitvoerder
     * @return lijst met verwerkingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels(final RegistratieBijhoudingUitvoerder regUitvoerder) {
        return (List<Verwerkingsregel>) ReflectionTestUtils.getField(regUitvoerder, "verwerkingsregels");
    }

}
