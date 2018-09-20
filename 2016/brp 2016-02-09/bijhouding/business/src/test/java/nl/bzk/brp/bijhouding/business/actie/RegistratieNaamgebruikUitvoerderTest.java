/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.naamgebruik.NaamgebruikGroepVerwerker;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistratieNaamgebruikUitvoerderTest {

    private RegistratieNaamgebruikUitvoerder registratieNaamgebruikUitvoerder;

    @Before
    public void init() {
        registratieNaamgebruikUitvoerder = new RegistratieNaamgebruikUitvoerder() {
            @Override
            public PersoonBericht getBerichtRootObject() {
                final PersoonBericht dummyBericht = new PersoonBericht();
                dummyBericht.setAdressen(Arrays.asList(new PersoonAdresBericht()));
                return dummyBericht;
            }

            @Override
            public PersoonHisVolledigImpl getModelRootObject() {
                return TestPersoonJohnnyJordaan.maak();
            }
        };
    }

    @Test
    public void testVerzamelVerwerkingsregels() {
        Assert.assertEquals(0, getVerwerkingsregels(registratieNaamgebruikUitvoerder).size());

        registratieNaamgebruikUitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels(registratieNaamgebruikUitvoerder).size());
        Assert.assertTrue(getVerwerkingsregels(registratieNaamgebruikUitvoerder).get(0)
            instanceof NaamgebruikGroepVerwerker);
    }

    /**
     * Haalt de verwerkingsregels uit de uitvoerder.
     *
     * @param regUitvoerder uitvoerder
     * @return lijst met verwerkingsregels
     */
    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels(final RegistratieNaamgebruikUitvoerder regUitvoerder) {
        return (List<Verwerkingsregel>) ReflectionTestUtils.getField(regUitvoerder, "verwerkingsregels");
    }
}
