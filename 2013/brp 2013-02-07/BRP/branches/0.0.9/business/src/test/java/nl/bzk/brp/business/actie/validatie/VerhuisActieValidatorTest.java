/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.RedenWijzigingAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;

public class VerhuisActieValidatorTest {

    @Test
    public void testAlleVeldenLeeg() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }

    @Test
    public void testLeegMetPersoonIdentificatieNummers() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }

    @Test
    public void testLeegMetAdres() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(new PersoonAdres());
        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(8, meldingen.size());
    }

    @Test
    public void testLeegMet1AdresVeldGevuld() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(new PersoonAdres());
        persoon.getAdressen().iterator().next().setSoort(FunctieAdres.BRIEFADRES);
        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(7, meldingen.size());
    }

    @Test
    public void testMetAlleVeldenGevuld() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456789");
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(new PersoonAdres());
        PersoonAdres adres = persoon.getAdressen().iterator().next();
        adres.setSoort(FunctieAdres.BRIEFADRES);
        
        RedenWijzigingAdres redenWijzigingCode = new RedenWijzigingAdres();
        redenWijzigingCode.setId(new Short("1"));
        redenWijzigingCode.setCode("P");
        redenWijzigingCode.setNaam("pppppp");
        adres.setRedenWijziging(redenWijzigingCode);
        adres.setDatumAanvangAdreshouding(1);
        adres.setGemeente(new Partij());
        adres.getGemeente().setGemeentecode("1234");
        adres.setNaamOpenbareRuimte("sdfds");
        adres.setHuisnummer("5");
        adres.setLand(new Land());
        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new VerhuisActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }
}
