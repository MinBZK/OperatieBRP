/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import org.junit.Test;

/** Unit test class voor de methodes in de {@link Betrokkenheid} class. */
public class BetrokkenheidTest {

    @Test
    public void testSoortHulpMethodes() {
        Betrokkenheid partnerBetrokkenheid = bouwBetrokkenheid(SoortBetrokkenheid.PARTNER);
        Betrokkenheid kindBetrokkenheid = bouwBetrokkenheid(SoortBetrokkenheid.KIND);
        Betrokkenheid ouderBetrokkenheid = bouwBetrokkenheid(SoortBetrokkenheid.OUDER);

        Assert.assertTrue(partnerBetrokkenheid.isPartner());
        Assert.assertFalse(partnerBetrokkenheid.isKind());
        Assert.assertFalse(partnerBetrokkenheid.isOuder());

        Assert.assertFalse(kindBetrokkenheid.isPartner());
        Assert.assertTrue(kindBetrokkenheid.isKind());
        Assert.assertFalse(kindBetrokkenheid.isOuder());

        Assert.assertFalse(ouderBetrokkenheid.isPartner());
        Assert.assertFalse(ouderBetrokkenheid.isKind());
        Assert.assertTrue(ouderBetrokkenheid.isOuder());
    }

    @Test
    public void testOuderschapGroep() {
        Betrokkenheid kindBetrokkenheid = bouwBetrokkenheid(SoortBetrokkenheid.KIND);
        Assert.assertFalse(kindBetrokkenheid.heeftOuderschapGroep());

        Betrokkenheid ouderBetrokkenheid = bouwBetrokkenheid(SoortBetrokkenheid.OUDER);
        Assert.assertFalse(ouderBetrokkenheid.heeftOuderschapGroep());

        ouderBetrokkenheid.setDatumAanvangOuderschap(Integer.valueOf(20060325));
        Assert.assertTrue(ouderBetrokkenheid.heeftOuderschapGroep());
    }

    @Test
    public void testOuderlijkGezagGroep() {
        Betrokkenheid kindBetrokkenheid = bouwBetrokkenheid(SoortBetrokkenheid.KIND);
        Assert.assertFalse(kindBetrokkenheid.heeftOuderlijkGezagGroep());

        Betrokkenheid ouderBetrokkenheid = bouwBetrokkenheid(SoortBetrokkenheid.OUDER);
        Assert.assertFalse(ouderBetrokkenheid.heeftOuderlijkGezagGroep());

        ouderBetrokkenheid.setIndOuderHeeftGezag(Boolean.FALSE);
        Assert.assertTrue(ouderBetrokkenheid.heeftOuderlijkGezagGroep());
    }

    /**
     * Bouwt een nieuwe {@link Betrokkenheid} instantie op met opgegeven soort.
     *
     * @param soort de soort van de op te bouwen betrokkenheid.
     * @return de nieuw gebouwde betrokkenheid.
     */
    private Betrokkenheid bouwBetrokkenheid(final SoortBetrokkenheid soort) {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setSoortBetrokkenheid(soort);
        return betrokkenheid;
    }

}
