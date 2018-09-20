/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import nl.bzk.brp.model.bericht.kern.HandelingInschrijvingDoorGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import org.junit.Test;


/** Unit test voor de {@link AbstractBijhoudingsBericht} class. */
public class AlgemeneAbstractBijhoudingsBerichtTest {

    @Test
    public void testGetterEnSetterVoorActies() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };

        Assert.assertNull(bericht.getAdministratieveHandeling());

        List<ActieBericht> acties = new ArrayList<ActieBericht>();
        acties.add(new ActieRegistratieOverlijdenBericht());
        acties.add(new ActieRegistratieOverlijdenBericht());

        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(acties);

        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());
        Assert.assertSame(acties, bericht.getAdministratieveHandeling().getActies());
    }

    @Test
    public void testGetterEnSetterVoorOverruledMeldingen() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };

        Assert.assertNull(bericht.getAdministratieveHandeling());

        //Na refactoring met code generatie is de lijst verplaats naar administratievehandeling en nu standaard null.
        bericht.setAdministratieveHandeling(new HandelingInschrijvingDoorGeboorteBericht());
        Assert.assertNull(bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen());

        List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> meldingen =
                Arrays.asList(new AdministratieveHandelingGedeblokkeerdeMeldingBericht(),
                              new AdministratieveHandelingGedeblokkeerdeMeldingBericht());

        bericht.getAdministratieveHandeling().setGedeblokkeerdeMeldingen(meldingen);

        Assert.assertNotNull(bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen());
        Assert.assertSame(meldingen, bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen());
    }

    @Test
    public void testReadBsnLocks() {
        AbstractBijhoudingsBericht bericht;

        bericht = maakBericht(bouwActieLijstMetPersonen());
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());

        bericht = maakBericht(bouwActieLijstMetPersonen("123456789"));
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());
    }

    @Test
    public void testWriteBsnLocks() {
        AbstractBijhoudingsBericht bericht;

        bericht = maakBericht(bouwActieLijstMetPersonen());
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());

        bericht = maakBericht(bouwActieLijstMetPersonen("123456789"));
        Assert.assertEquals(1, bericht.getWriteBsnLocks().size());

        bericht = maakBericht(bouwActieLijstMetPersonen("123456789", "234567890"));
        Assert.assertEquals(2, bericht.getWriteBsnLocks().size());

        // Geen acties
        bericht = maakBericht(null);
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

        // Geen root objecten in actie
        bericht = maakBericht(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(new ActieRegistratieOverlijdenBericht());
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

        // Lijst van root objecten is leeg
        bericht = maakBericht(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(new ActieRegistratieOverlijdenBericht());
        bericht.getAdministratieveHandeling().getActies().get(0).setRootObjecten(new ArrayList<RootObject>());
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

        // Lijst van root objecten niet van het instance persoon
        bericht = maakBericht(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(new ActieRegistratieOverlijdenBericht());
        bericht.getAdministratieveHandeling().getActies().get(0).setRootObjecten(new ArrayList<RootObject>());
        bericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().add(new HuwelijkBericht());
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

        // Geen persoon identificatienummers
        bericht = maakBericht(bouwActieLijstMetPersonen("123456789"));
        ((PersoonBericht) bericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0)).getIdentificatienummers()
                                                                                 .setBurgerservicenummer(null);
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());
        ((PersoonBericht) bericht.getAdministratieveHandeling().getActies().get(0).getRootObjecten().get(0)).setIdentificatienummers(null);
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

    }

    @Test
    public void testWriteBsnLocksMetLeegRootObject() {
        AbstractBijhoudingsBericht bericht = maakBericht(bouwActieLijstMetPersonen("123456789"));
        ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        actieBericht.setRootObjecten(null);
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());
    }

    /**
     * Bouwt een lijst van acties op met voor elke bsn een actie met een persoon met opgegeven bsn.
     *
     * @param bsns de bsns van de personen van de acties.
     * @return een lijst van acties.
     */
    private List<ActieBericht> bouwActieLijstMetPersonen(final String... bsns) {
        List<ActieBericht> acties = new ArrayList<ActieBericht>();
        for (String bsn : bsns) {
            ActieBericht actie = new ActieRegistratieOverlijdenBericht();
            PersoonBericht persoon = new PersoonBericht();
            persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
            actie.setRootObjecten(Arrays.asList((RootObject) persoon));
            acties.add(actie);
        }
        return acties;
    }

    private AbstractBijhoudingsBericht maakBericht(final List<ActieBericht> acties) {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(acties);
        return bericht;
    }
}
