/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanschrijvingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;


public class WijzigNaamgebruikActieValidatorTest {

    @Test
    public void testAlleVeldenLeeg() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        PersoonBericht persoon = new PersoonBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new WijzigingNaamgebruikActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        // missende 2 groepen (IdentificatieNummers, Aanschrijving)
        // AdministratiefAfgeleid is noog out of scope
        Assert.assertEquals(2, meldingen.size());

    }

    @Test
    public void testLeegMetPersoonIdentificatienummers() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456782"));
        persoon.setAfgeleidAdministratief(new PersoonAfgeleidAdministratiefGroepBericht());
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new WijzigingNaamgebruikActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        // missende 3 groepen (bsn, Aanschrijving)
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testLeegMetAanschrijving() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456782"));
        persoon.setAfgeleidAdministratief(new PersoonAfgeleidAdministratiefGroepBericht());
        persoon.setAanschrijving(new PersoonAanschrijvingGroepBericht());
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new WijzigingNaamgebruikActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        // wijzegebruik, algoritmisch, geslachtsnaam
        Assert.assertEquals(3, meldingen.size());
    }

    @Test
    public void testMetAlleVeldenGevuld() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456789"));
        persoon.setAfgeleidAdministratief(new PersoonAfgeleidAdministratiefGroepBericht());
        persoon.setAanschrijving(new PersoonAanschrijvingGroepBericht());
        persoon.getAanschrijving().setNaamgebruik(WijzeGebruikGeslachtsnaam.PARTNER_EIGEN);
        persoon.getAanschrijving().setGeslachtsnaamAanschrijving(new Geslachtsnaam("Peterson"));
        persoon.getAanschrijving().setIndicatieTitelsPredikatenBijAanschrijven(JaNee.NEE);
        persoon.getAanschrijving().setIndicatieAanschrijvingAlgoritmischAfgeleid(JaNee.NEE);

        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new WijzigingNaamgebruikActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }
}
