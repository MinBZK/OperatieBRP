/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;


public class VerhuisActieValidatorTest {

    @Test
    public void testAlleVeldenLeeg() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        PersoonBericht persoon = new PersoonBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }

    @Test
    public void testLeegMetPersoonIdentificatienummers() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }

    @Test
    public void testLeegMetAdres() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        PersoonAdresBericht persAdres = new PersoonAdresBericht();
        persAdres.setGegevens(new PersoonAdresStandaardGroepBericht());
        persoon.getAdressen().add(persAdres);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(4, meldingen.size());
    }

    @Test
    public void testLeegMet1AdresVeldGevuld() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(new PersoonAdresBericht());
        PersoonAdresBericht adres = persoon.getAdressen().iterator().next();
        adres.setGegevens(new PersoonAdresStandaardGroepBericht());
        adres.getGegevens().setSoort(FunctieAdres.BRIEFADRES);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(4, meldingen.size());
    }

    @Test
    public void testMetAlleVeldenGevuld() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456789"));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(new PersoonAdresBericht());
        PersoonAdresBericht adres = persoon.getAdressen().iterator().next();
        adres.setGegevens(new PersoonAdresStandaardGroepBericht());
        adres.getGegevens().setSoort(FunctieAdres.BRIEFADRES);
        // adres.getGegevens().setRedenWijziging(RedenWijzigingAdres.PERSOON);
        adres.getGegevens().setRedenwijziging(new RedenWijzigingAdres());
        adres.getGegevens().setDatumAanvangAdreshouding(new Datum(1));
        adres.getGegevens().setGemeente(new Partij());
        // adres.getGegevens().getGemeente().setGemeentecode("1234");
        adres.getGegevens().setNaamOpenbareRuimte(new NaamOpenbareRuimte("sdfds"));
        adres.getGegevens().setHuisnummer(new Huisnummer(5));
        adres.getGegevens().setLand(new Land());
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }
}
