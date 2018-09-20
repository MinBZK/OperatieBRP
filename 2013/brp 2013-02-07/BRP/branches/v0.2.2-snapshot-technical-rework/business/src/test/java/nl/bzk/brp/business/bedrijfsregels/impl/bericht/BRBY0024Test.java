/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBericht;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor de {@link BRBY0024} bedrijfsregel. */
public class BRBY0024Test {

    private static final int DAG1 = 20100101;
    private static final int DAG2 = 20110101;
    private static final int DAG3 = 20120101;
    private static final int DAG4 = 20130101;

    private BRBY0024 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0024();
    }

    @Test
    public void testGeenBericht() {
        Assert.assertTrue(bedrijfsregel.executeer(null).isEmpty());
    }

    @Test
    public void testGeenActies() {
        Assert.assertTrue(bedrijfsregel.executeer(bouwBerichtMetActies(null)).isEmpty());
    }

    @Test
    public void testEnkeleActie() {
        AbstractBijhoudingsBericht bericht;

        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());

        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, null));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testOverlapEindeActie1MetBeginActie2() {
        AbstractBijhoudingsBericht bericht;

        // Test twee (bijna) aansluitende acties: einde actie 1 < aanvang actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2 - 1, DAG2, DAG3));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());

        // Test twee geheel aansluitende acties: einde actie 1 = aanvang actie 2
        // Deze overlapt niet daar einde datum een 'tot' datum is en aanvang een 'vanaf'
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2, DAG2, DAG3));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());

        // Test twee (deels) overlapte acties: einde actie 1 > aanvang actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2 + 1, DAG2, DAG3));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testOverlapBeginActie1MetEindeActie2() {
        AbstractBijhoudingsBericht bericht;

        // Test twee (bijna) aansluitende acties: aanvang actie 1 > einde actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG2, DAG3, DAG1, DAG2 - 1));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());

        // Test twee geheel aansluitende acties: aanvang actie 1 = einde actie 2
        // Deze overlapt niet daar einde datum een 'tot' datum is en aanvang een 'vanaf'
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG2, DAG3, DAG1, DAG2));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());

        // Test twee (deels) overlapte acties: aanvang actie 1 < einde actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG2, DAG3, DAG1, DAG2 + 1));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testGeheleOverlap() {
        AbstractBijhoudingsBericht bericht;

        // Test actie 1 overlapt actie 2 geheel
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG3, DAG2, DAG2 + 5));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());

        // Test actie 2 overlapt actie 1 geheel
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG2, DAG2 + 5, DAG1, DAG3));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testMeedereActies() {
        AbstractBijhoudingsBericht bericht;

        // Geen van de acties overlappen
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2 - 1, DAG2 + 1, DAG3 - 1, DAG3 + 1, DAG4));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG2 + 1, DAG3 - 1, DAG1, DAG2 - 1, DAG3 + 1, DAG4));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG2 + 1, DAG3 - 1, DAG3 + 1, DAG4, DAG1, DAG2 - 1));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());

        // 2 acties overlappen
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2 + 1, DAG2 - 1, DAG3 - 1, DAG3 + 1, DAG4));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG2 - 1, DAG3 - 1, DAG1, DAG2 + 1, DAG3 + 1, DAG4));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG2 - 1, DAG3 - 1, DAG3 + 1, DAG4, DAG1, DAG2 + 1));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testActiesZonderEindeGeldigheid() {
        AbstractBijhoudingsBericht bericht;

        // Einde actie 2 is null: test met einde actie 1 voor aanvang actie 2 en met einde actie 1 na aanvang actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2, DAG3, null));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG3, DAG4, DAG1, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());

        // Einde actie 1 is null: test met aanvang actie 1 voor einde actie 2 en met aanvang actie 1 na einde actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, null, DAG3, DAG4));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG3, null, DAG1, DAG2));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());

        // Beide eind data zijn null: test met aanvang actie 1 voor aanvang actie 2 en met aanvang actie 1 na
        // aanvang actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, null, DAG3, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG3, null, DAG1, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testActiesZonderAanvangGeldigheid() {
        AbstractBijhoudingsBericht bericht;

        // Aanvang actie 2 is null: test met einde actie 1 voor aanvang actie 2 en met einde actie 1 na aanvang actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2, null, DAG4));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG3, DAG4, null, DAG2));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());

        // Aanvang actie 1 is null: test met einde actie 1 voor aanvang actie 2 en met einde actie 1 na aanvang actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, DAG2, DAG3, DAG4));
        Assert.assertTrue(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, DAG4, DAG1, DAG2));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());

        // Beide aanvang data zijn null: test met einde actie 1 voor einde actie 2 en met einde actie 1 na
        // einde actie 2
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, DAG2, null, DAG4));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, DAG4, null, DAG2));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
    }

    @Test
    public void testActiesMetGeenEnkeleGeldigheid() {
        AbstractBijhoudingsBericht bericht;

        // Actie 1 heeft geen enkele geldigheid
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, null, DAG1, DAG2));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, null, DAG1, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, null, null, DAG2));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, null, null, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());

        // Actie 2 heeft geen enkele geldigheid
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, DAG2, null, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(DAG1, null, null, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, DAG2, null, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
        bericht = bouwBerichtMetActies(bouwLijstVanActies(null, null, null, null));
        Assert.assertFalse(bedrijfsregel.executeer(bericht).isEmpty());
    }

    /**
     * Bouwt een lijst van acties op waarvan de datum aanvang en datum einde geldigheid zijn gevuld met de opgegeven
     * datums. Per twee datums wordt er dus een actie aan de lijst toegevoegd waarvan de eerste datum de datum
     * aanvang van de actie is en de tweede dat datum einde geldigheid.
     *
     * @param datums lijst van datums (als integers).
     * @return een lijst van acties.
     */
    private List<Actie> bouwLijstVanActies(final Integer... datums) {
        List<Actie> acties = new ArrayList<Actie>();
        for (int i = 0; i < datums.length; i = i + 2) {
            Integer datumAanvang;
            Integer datumEinde;

            datumAanvang = datums[i];
            if (datums.length > i + 1) {
                datumEinde = datums[i + 1];
            } else {
                datumEinde = null;
            }

            acties.add(ActieBerichtBuilder.bouwNieuweActie(SoortActie.DUMMY)
                                          .setDatumAanvang(datumAanvang)
                                          .setDatumEinde(datumEinde)
                                          .getActie());
        }

        return acties;
    }

    /**
     * Instantieert en retourneert een (bijhoudings) bericht met de opgegeven acties.
     *
     * @param acties de acties voor het bericht.
     * @return een nieuw bericht met opgegeven acties.
     */
    private AbstractBijhoudingsBericht bouwBerichtMetActies(final List<Actie> acties) {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
            @Override
            public SoortBericht getSoortBericht() {
                return null;
            }
        };
        bericht.setBrpActies(acties);
        return bericht;
    }

}
