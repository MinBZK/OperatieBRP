/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.Partij;
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
        bericht.setBrpActies(bouwActieLijstMetPartijIds());
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetEnkeleActie() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBrpActies(bouwActieLijstMetPartijIds(Integer.valueOf(2)));
        Assert.assertEquals(Integer.valueOf(2), bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetMeerdereActiesMetZelfdeId() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBrpActies(bouwActieLijstMetPartijIds(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3)));
        Assert.assertEquals(Integer.valueOf(3), bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetMeerdereActiesMetVerschillendeIds() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBrpActies(bouwActieLijstMetPartijIds(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)));
        Assert.assertEquals(Integer.valueOf(1), bericht.getPartijId());
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

    /**
     * Bouwt een lijst van acties op met voor elke id een actie met een partij met opgegeven id.
     * @param ids de ids van de partijen van de acties.
     * @return een lijst van acties.
     */
    private List<BRPActie> bouwActieLijstMetPartijIds(final Integer... ids) {
        List<BRPActie> acties = new ArrayList<BRPActie>();
        for (Integer id : ids) {
            Partij partij = new Partij();
            partij.setId(id);
            BRPActie actie = new BRPActie();
            actie.setPartij(partij);
            acties.add(actie);
        }
        return acties;
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
