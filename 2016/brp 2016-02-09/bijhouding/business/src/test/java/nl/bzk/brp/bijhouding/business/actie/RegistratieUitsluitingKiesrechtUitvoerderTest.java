/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.uitsluitingkiesrecht.UitsluitingKiesrechtGroepVerwerker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistratieUitsluitingKiesrechtUitvoerderTest {

    private RegistratieUitsluitingKiesrechtUitvoerder registratieUitsluitingKiesrechtUitvoerder;

    @Before
    public void init() {
        registratieUitsluitingKiesrechtUitvoerder = new RegistratieUitsluitingKiesrechtUitvoerder();
    }

    @Test
    public void testVerzamelVerwerkingsregels() {
        Assert.assertEquals(0, getVerwerkingsregels(registratieUitsluitingKiesrechtUitvoerder).size());

        registratieUitsluitingKiesrechtUitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(registratieUitsluitingKiesrechtUitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(registratieUitsluitingKiesrechtUitvoerder).get(0)
                                  instanceof UitsluitingKiesrechtGroepVerwerker);
    }

    /**
     * Haalt de verwerkingsregels uit de uitvoerder.
     *
     * @param regUitvoerder registratie overlijden uitvoerder
     * @return lijst met verwerkingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels(final RegistratieUitsluitingKiesrechtUitvoerder regUitvoerder) {
        return (List<Verwerkingsregel>) ReflectionTestUtils.getField(regUitvoerder, "verwerkingsregels");
    }
}
