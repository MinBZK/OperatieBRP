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
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import org.junit.Test;


/**
 * Unit test voor de {@link AbstractBijhoudingsBericht} class.
 */
public class BijhoudingsBerichtTest {

    @Test
    public void testGetPartijIdVoorBerichtMetNullVoorActies() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtZonderActies() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        bericht.setBerichtStuurgegevens(null);
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetEnkeleActie() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        bericht.setBerichtStuurgegevens(bouwStuurgegevens(3));
        Assert.assertEquals(Integer.valueOf(3), bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetMeerdereActiesMetZelfdeId() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        bericht.setBerichtStuurgegevens(bouwStuurgegevens(3));
        Assert.assertEquals(Integer.valueOf(3), bericht.getPartijId());
    }

    @Test
    public void testGetPartijIdVoorBerichtMetMeerdereActiesMetVerschillendeIds() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        bericht.setBerichtStuurgegevens(bouwStuurgegevens(1));
        Assert.assertEquals(Integer.valueOf(1), bericht.getPartijId());
    }

    @Test
    public void testOngeldigePartijId() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens(null, "BRP", null, null, null));
        Assert.assertNull(bericht.getPartijId());

        bericht.getBerichtStuurgegevens().setOrganisatie("");
        Assert.assertNull(bericht.getPartijId());

        bericht.getBerichtStuurgegevens().setOrganisatie("abcd");
        Assert.assertNull(bericht.getPartijId());
    }

    @Test
    public void testReadBsnLocks() {
        AbstractBijhoudingsBericht bericht = maakBericht();

        bericht.setBrpActies(bouwActieLijstMetPersonen());
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());

        bericht.setBrpActies(bouwActieLijstMetPersonen("123456789"));
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());
    }

    @Test
    public void testWriteBsnLocks() {
        AbstractBijhoudingsBericht bericht = maakBericht();

        bericht.setBrpActies(bouwActieLijstMetPersonen());
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());

        bericht.setBrpActies(bouwActieLijstMetPersonen("123456789"));
        Assert.assertEquals(1, bericht.getWriteBsnLocks().size());

        bericht.setBrpActies(bouwActieLijstMetPersonen("123456789", "234567890"));
        Assert.assertEquals(2, bericht.getWriteBsnLocks().size());
    }

    @Test
    public void testWriteBsnLocksMetLeegRootObject() {
        AbstractBijhoudingsBericht bericht = maakBericht();

        bericht.setBrpActies(bouwActieLijstMetPersonen("123456789"));
        ArrayList<RootObject> rootObjects = new ArrayList<RootObject>();
        ActieBericht actieBericht = (ActieBericht) bericht.getBrpActies().get(0);
        actieBericht.setRootObjecten(rootObjects);
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());
    }

    @Test
    public void testVoegNullActieToe() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        bericht.voegActieToe(null);
        Assert.assertNull(bericht.getBrpActies());
    }

    @Test
    public void testVoegNotNullActieToe() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        bericht.voegActieToe(new ActieBericht());
        Assert.assertNotNull(bericht.getBrpActies());
        Assert.assertFalse(bericht.getBrpActies().isEmpty());
    }

    @Test
    public void testVoegNotNullVerhuizingToe() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.VERHUIZING);
        bericht.voegActieToe(actie);
        Assert.assertNotNull(bericht.getVerhuizingActie());
    }

    @Test
    public void testVoegNotNullGeboorteToe() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.AANGIFTE_GEBOORTE);
        bericht.voegActieToe(actie);
        Assert.assertNotNull(bericht.getInschrijvingGeboorteActie());
    }

    @Test
    public void testVoegNotNullNationaliteitToe() {
        AbstractBijhoudingsBericht bericht = maakBericht();
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        bericht.voegActieToe(actie);
        Assert.assertNotNull(bericht.getRegistratieNationaliteitActie());
    }

    private BerichtStuurgegevens bouwStuurgegevens(final Integer organisatie) {
        return new BerichtStuurgegevens(String.valueOf(organisatie), "BRP", null, null, null);
    }

    /**
     * Bouwt een lijst van acties op met voor elke bsn een actie met een persoon met opgegeven bsn.
     *
     * @param bsns de bsns van de personen van de acties.
     * @return een lijst van acties.
     */
    private List<Actie> bouwActieLijstMetPersonen(final String... bsns) {
        List<Actie> acties = new ArrayList<Actie>();
        for (String bsn : bsns) {
            ActieBericht actie = new ActieBericht();
            PersoonBericht persoon = new PersoonBericht();
            persoon.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
            persoon.getIdentificatieNummers().setBurgerServiceNummer(new Burgerservicenummer(bsn));
            ArrayList<RootObject> rootObjects = new ArrayList<RootObject>();
            rootObjects.add(persoon);
            actie.setRootObjecten(rootObjects);
            acties.add(actie);
        }
        return acties;
    }

    private AbstractBijhoudingsBericht maakBericht() {
        return new AbstractBijhoudingsBericht() {
        };
    }

}
