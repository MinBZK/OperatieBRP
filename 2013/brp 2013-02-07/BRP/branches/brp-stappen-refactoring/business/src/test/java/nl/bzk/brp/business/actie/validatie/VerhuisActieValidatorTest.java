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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Test;


public class VerhuisActieValidatorTest {

    @Test
    public void testAlleVeldenLeeg() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieAdresBericht();
        PersoonBericht persoon = new PersoonBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }

    @Test
    public void testLeegMetPersoonIdentificatienummers() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieAdresBericht();
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
        ActieBericht actie = new ActieRegistratieAdresBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        PersoonAdresBericht persAdres = new PersoonAdresBericht();
        persAdres.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoon.getAdressen().add(persAdres);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(4, meldingen.size());
    }

    @Test
    public void testLeegMet1AdresVeldGevuld() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieAdresBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(new PersoonAdresBericht());
        PersoonAdresBericht adres = persoon.getAdressen().iterator().next();
        adres.setStandaard(new PersoonAdresStandaardGroepBericht());
        adres.getStandaard().setSoort(FunctieAdres.BRIEFADRES);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(4, meldingen.size());
    }

    @Test
    public void testMetAlleVeldenGevuld() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieAdresBericht();
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456789"));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(new PersoonAdresBericht());
        PersoonAdresBericht adres = persoon.getAdressen().iterator().next();
        adres.setStandaard(new PersoonAdresStandaardGroepBericht());
        adres.getStandaard().setSoort(FunctieAdres.BRIEFADRES);
        // adres.getStandaard().setRedenWijziging(RedenWijzigingAdres.PERSOON);
        adres.getStandaard().setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_INFRA);
        adres.getStandaard().setDatumAanvangAdreshouding(new Datum(1));
        adres.getStandaard().setGemeente(StatischeObjecttypeBuilder.GEMEENTE_BREDA);
        // adres.getStandaard().getGemeente().setGemeentecode("1234");
        adres.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimte("sdfds"));
        adres.getStandaard().setHuisnummer(new Huisnummer("" + 5));
        adres.getStandaard().setLand(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }
}
