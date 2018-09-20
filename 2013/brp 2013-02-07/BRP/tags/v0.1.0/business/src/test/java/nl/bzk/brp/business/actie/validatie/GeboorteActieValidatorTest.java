/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class GeboorteActieValidatorTest {

    @Test
    public void testAlleVeldenLeeg() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(3, meldingen.size());
    }

    @Test
    public void testLeegMetPersoonIdentificatieNummers() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("564653");
        persoon.getIdentificatienummers().setAdministratienummer("56461");
        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }

    @Test
    public void testLeegMetGeslachtsAanduiding() {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        persoon.setPersoonGeboorte(new PersoonGeboorte());
        persoon.getPersoonGeboorte().setDatumGeboorte(7);
        persoon.getPersoonGeboorte().setGemeenteGeboorte(new Partij());
        persoon.getPersoonGeboorte().setLandGeboorte(new Land());
        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }


    @Test
    public void testMetAlleVeldenGevuld() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();

        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456789");
        persoon.getIdentificatienummers().setAdministratienummer("123456789");

        persoon.setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        persoon.getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(GeslachtsAanduiding.MAN);

        persoon.setPersoonGeboorte(new PersoonGeboorte());
        persoon.getPersoonGeboorte().setDatumGeboorte(7);
        persoon.getPersoonGeboorte().setGemeenteGeboorte(new Partij());
        persoon.getPersoonGeboorte().setLandGeboorte(new Land());

        SortedSet<PersoonVoornaam> voornamen = new TreeSet<PersoonVoornaam>();
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setVolgnummer(1);
        voornaam.setNaam("piets");
        voornamen.add(voornaam);
        ReflectionTestUtils.setField(persoon, "persoonVoornamen", voornamen);

        SortedSet<PersoonGeslachtsnaamcomponent> geslComponenten = new TreeSet<PersoonGeslachtsnaamcomponent>();
        PersoonGeslachtsnaamcomponent geslComp = new PersoonGeslachtsnaamcomponent();
        geslComp.setVolgnummer(1);
        geslComp.setNaam("piets");
        geslComponenten.add(geslComp);
        ReflectionTestUtils.setField(persoon, "geslachtsnaamcomponenten", geslComponenten);

        actie.voegPersoonToe(persoon);
        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }
}
