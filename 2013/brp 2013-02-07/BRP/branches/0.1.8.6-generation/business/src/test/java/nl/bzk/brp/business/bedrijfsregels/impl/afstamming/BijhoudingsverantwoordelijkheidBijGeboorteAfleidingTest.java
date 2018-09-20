/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;

import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class BijhoudingsverantwoordelijkheidBijGeboorteAfleidingTest {

    private BijhoudingsverantwoordelijkheidBijGeboorteAfleiding afleiding;

    @Before
    public void init() {
        afleiding = new BijhoudingsverantwoordelijkheidBijGeboorteAfleiding();
    }

    @Test
    public void testNormaleFlow() {
        RelatieBericht relatie = bouwRelatie();

        Assert.assertNull(relatie.getKindBetrokkenheid().getBetrokkene().getBijhoudingsverantwoordelijkheid().getVerantwoordelijke());

        afleiding.executeer(null, relatie, null, null);

        Assert.assertNotNull(relatie.getKindBetrokkenheid().getBetrokkene().getBijhoudingsverantwoordelijkheid());
        Assert.assertEquals(Verantwoordelijke.COLLEGE_VAN_BURGEMEESTER_EN_WETHOUDERS, relatie.getKindBetrokkenheid().getBetrokkene().getBijhoudingsverantwoordelijkheid().getVerantwoordelijke());
    }


    private RelatieBericht bouwRelatie() {
        RelatieBericht relatie = new RelatieBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        relatie.getBetrokkenheden().add(maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("kind")));
        relatie.getBetrokkenheden().add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("vader")));
        relatie.getBetrokkenheden().add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("moeder")));

        return relatie;
    }

    private BetrokkenheidBericht maakBetrokkenheid(final SoortBetrokkenheid soort, final RelatieBericht relatie,
                                                   final Burgerservicenummer bsn)
    {
        BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(soort);
        betr.setRelatie(relatie);
        String bsnAlsString = null;
        if (bsn != null) {
            bsnAlsString = bsn.toString();
        }
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(bsnAlsString, null, null, null, "test", null, null);
        betr.setBetrokkene(persoon);
        return betr;
    }
}
