/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.logisch.BRPActie;
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
        bericht.setBrpActies(bouwActieListMetPartijIds());
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetEnkeleActie() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBrpActies(bouwActieListMetPartijIds(Integer.valueOf(2)));
        Assert.assertEquals(Integer.valueOf(2), bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetMeerdereActiesMetZelfdeId() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBrpActies(bouwActieListMetPartijIds(Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3)));
        Assert.assertEquals(Integer.valueOf(3), bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetMeerdereActiesMetVerschillendeIds() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBrpActies(bouwActieListMetPartijIds(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)));
        Assert.assertEquals(Integer.valueOf(1), bericht.getPartijId());
    }


    /**
     * Bouwt een lijst van acties op met voor elke id een actie met een partij met opgegeven id.
     * @param ids de ids van de partijen van de acties.
     * @return een lijst van acties.
     */
    private List<BRPActie> bouwActieListMetPartijIds(final Integer... ids) {
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
}
