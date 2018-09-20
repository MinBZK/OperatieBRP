/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import org.junit.Test;

/**
 * Unit test voor de {@link BijhoudingsBericht} class.
 */
public class BijhoudingsBerichtTest {

    @Test
    public void testGetPartijIdVoorBerichtMetNullVoorActies() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtZonderActies() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBerichtStuurgegevens(null);
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetEnkeleActie() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBerichtStuurgegevens(bouwStuurgegevens(3));
        Assert.assertEquals(Integer.valueOf(3), bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetMeerdereActiesMetZelfdeId() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBerichtStuurgegevens(bouwStuurgegevens(3));
        Assert.assertEquals(Integer.valueOf(3), bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetMeerdereActiesMetVerschillendeIds() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBerichtStuurgegevens(bouwStuurgegevens(1));
        Assert.assertEquals(Integer.valueOf(1), bericht.getPartijId());
    }

    @Test
    public void testOngeldigePartijId() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens(null, "BRP", null, null));
        Assert.assertNull(bericht.getPartijId());

        bericht.getBerichtStuurgegevens().setOrganisatie("");
        Assert.assertNull(bericht.getPartijId());

        bericht.getBerichtStuurgegevens().setOrganisatie("abcd");
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testReadBsnLocks() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();

        bericht.setBrpActies(bouwActieLijstMetPersonen());
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());

        bericht.setBrpActies(bouwActieLijstMetPersonen("123456789"));
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());
    }

    @Test
    public void testWriteBsnLocks() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();

        bericht.setBrpActies(bouwActieLijstMetPersonen());
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());

        bericht.setBrpActies(bouwActieLijstMetPersonen("123456789"));
        Assert.assertEquals(1, bericht.getWriteBsnLocks().size());

        bericht.setBrpActies(bouwActieLijstMetPersonen("123456789", "234567890"));
        Assert.assertEquals(2, bericht.getWriteBsnLocks().size());
    }

    @Test
    public void testWriteBsnLocksMetLeegRootObject() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();

        bericht.setBrpActies(bouwActieLijstMetPersonen("123456789"));
        bericht.getBrpActies().get(0).setRootObjecten(null);
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());

        bericht.getBrpActies().get(0).setRootObjecten(new ArrayList<RootObject>());
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());
    }

    private BerichtStuurgegevens bouwStuurgegevens(final Integer organisatie) {
        return new BerichtStuurgegevens(String.valueOf(organisatie), "BRP", null, null);
    }

    /**
     * Bouwt een lijst van acties op met voor elke bsn een actie met een persoon met opgegeven bsn.
     * @param bsns de bsns van de personen van de acties.
     * @return een lijst van acties.
     */
    private List<BRPActie> bouwActieLijstMetPersonen(final String... bsns) {
        List<BRPActie> acties = new ArrayList<BRPActie>();
        for (String bsn : bsns) {
            BRPActie actie = new BRPActie();
            Persoon persoon = new Persoon();
            persoon.setIdentificatienummers(new PersoonIdentificatienummers());
            persoon.getIdentificatienummers().setBurgerservicenummer(bsn);
            actie.voegRootObjectToe(persoon);
            acties.add(actie);
        }
        return acties;
    }
}
