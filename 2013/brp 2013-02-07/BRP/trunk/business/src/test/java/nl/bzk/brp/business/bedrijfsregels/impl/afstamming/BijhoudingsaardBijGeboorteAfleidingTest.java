/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class BijhoudingsaardBijGeboorteAfleidingTest {

    private BijhoudingsaardBijGeboorteAfleiding afleiding;

    @Before
    public void init() {
        afleiding = new BijhoudingsaardBijGeboorteAfleiding();
    }

    @Test
    public void testNormaleFlow() {
        FamilierechtelijkeBetrekkingBericht relatie = bouwRelatie();

        relatie.getKindBetrokkenheid().getPersoon().setBijhoudingsaard(null);

        afleiding.executeer(null, relatie, null);

        Assert.assertNotNull(relatie.getKindBetrokkenheid().getPersoon().getBijhoudingsaard());
        Assert.assertEquals(Bijhoudingsaard.INGEZETENE,
            relatie.getKindBetrokkenheid().getPersoon().getBijhoudingsaard().getBijhoudingsaard());
    }


    private FamilierechtelijkeBetrekkingBericht bouwRelatie() {
        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        relatie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer("123")));
        relatie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("456")));
        relatie.getBetrokkenheden()
               .add(maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer("789")));

        return relatie;
    }

    private BetrokkenheidBericht maakBetrokkenheid(final SoortBetrokkenheid soort, final RelatieBericht relatie,
        final Burgerservicenummer bsn)
    {
        BetrokkenheidBericht betr;

        if (soort == SoortBetrokkenheid.KIND) {
            betr = new KindBericht();
        } else if (soort == SoortBetrokkenheid.OUDER) {
            betr = new OuderBericht();
        } else {
            throw new IllegalArgumentException("Alleen Ouder of Kind");
        }

        betr.setRelatie(relatie);
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(bsn.getWaarde(), null, null, null, "test", null, null);
        betr.setPersoon(persoon);
        return betr;
    }
}
