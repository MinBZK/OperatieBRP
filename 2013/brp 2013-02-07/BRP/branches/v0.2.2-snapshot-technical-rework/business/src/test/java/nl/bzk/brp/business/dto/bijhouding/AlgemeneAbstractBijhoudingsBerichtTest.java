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
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBericht;
import nl.bzk.brp.model.validatie.OverruleMelding;
import org.junit.Test;


/**
 * Unit test voor de {@link AbstractBijhoudingsBericht} class.
 */
public class AlgemeneAbstractBijhoudingsBerichtTest {

    @Test
    public void testGetterEnSetterVoorActies() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {

            @Override
            public SoortBericht getSoortBericht() {
                return null;
            }
        };

        Assert.assertNull(bericht.getBrpActies());

        List<Actie> acties = Arrays.asList((Actie) new ActieBericht(), new ActieBericht());
        bericht.setBrpActies(acties);

        Assert.assertNotNull(bericht.getBrpActies());
        Assert.assertSame(acties, bericht.getBrpActies());
    }

    @Test
    public void testGetterEnSetterVoorOverruledMeldingen() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {

            @Override
            public SoortBericht getSoortBericht() {
                return null;
            }
        };

        // Standaard bevat een bericht een lege lijst van overruled meldingen
        Assert.assertNotNull(bericht.getOverruledMeldingen());
        Assert.assertTrue(bericht.getOverruledMeldingen().isEmpty());

        List<OverruleMelding> meldingen = Arrays.asList(new OverruleMelding(), new OverruleMelding());
        bericht.setOverruledMeldingen(meldingen);

        Assert.assertNotNull(bericht.getOverruledMeldingen());
        Assert.assertSame(meldingen, bericht.getOverruledMeldingen());
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
        bericht = maakBericht(new ArrayList<Actie>());
        bericht.getBrpActies().add(new ActieBericht());
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

        // Lijst van root objecten is leeg
        bericht = maakBericht(new ArrayList<Actie>());
        bericht.getBrpActies().add(new ActieBericht());
        ((ActieBericht) bericht.getBrpActies().get(0)).setRootObjecten(new ArrayList<RootObject>());
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

        // Lijst van root objecten niet van het instance persoon
        bericht = maakBericht(new ArrayList<Actie>());
        bericht.getBrpActies().add(new ActieBericht());
        ((ActieBericht) bericht.getBrpActies().get(0)).setRootObjecten(new ArrayList<RootObject>());
        ((ActieBericht) bericht.getBrpActies().get(0)).getRootObjecten().add(new RelatieBericht());
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

        // Geen persoon identificatienummers
        bericht = maakBericht(bouwActieLijstMetPersonen("123456789"));
        ((PersoonBericht) bericht.getBrpActies().get(0).getRootObjecten().get(0)).getIdentificatienummers()
                .setBurgerservicenummer(null);
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());
        ((PersoonBericht) bericht.getBrpActies().get(0).getRootObjecten().get(0)).setIdentificatienummers(null);
        Assert.assertEquals(0, bericht.getWriteBsnLocks().size());

    }

    @Test
    public void testWriteBsnLocksMetLeegRootObject() {
        AbstractBijhoudingsBericht bericht = maakBericht(bouwActieLijstMetPersonen("123456789"));
        ActieBericht actieBericht = (ActieBericht) bericht.getBrpActies().get(0);
        actieBericht.setRootObjecten(null);
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());
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
            persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
            actie.setRootObjecten(Arrays.asList((RootObject) persoon));
            acties.add(actie);
        }
        return acties;
    }

    private AbstractBijhoudingsBericht maakBericht(final List<Actie> acties) {
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
