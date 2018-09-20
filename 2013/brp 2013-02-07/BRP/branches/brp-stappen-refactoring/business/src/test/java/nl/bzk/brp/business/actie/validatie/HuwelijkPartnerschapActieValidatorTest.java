/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Test;


public class HuwelijkPartnerschapActieValidatorTest {

    @Test
    public void testGeenHuwelijk() {
        ActieBericht actie = new ActieRegistratieHuwelijkBericht();

        List<Melding> meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertTrue(meldingen.get(0).getOmschrijving().endsWith("huwelijk"));
    }

    @Test
    public void testHuwelijkIncompleet() {
        ActieBericht actie = bouwBasisRelatieBericht();
        ((RelatieBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(null);

        List<Melding> meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(3, meldingen.size());
        Assert.assertTrue(meldingen.get(0).getOmschrijving().endsWith("datumAanvang"));
        Assert.assertTrue(meldingen.get(1).getOmschrijving().endsWith("gemeenteAanvangCode"));
        // Deze zou in principe niet voor kunnen komen
        Assert.assertEquals("Ontbrekende partners", meldingen.get(2).getOmschrijving());

        // Test case voor uitzonderlijk geval wanneer gegevens null is wat in principe niet voor zou kunnen komen.
        ((HuwelijkGeregistreerdPartnerschapBericht) actie.getRootObjecten().get(0)).setStandaard(null);
        meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertEquals("Ontbrekende huwelijk gegevens", meldingen.get(0).getOmschrijving());
        Assert.assertEquals("Ontbrekende partners", meldingen.get(1).getOmschrijving());
    }

    @Test
    public void testHuwelijkZonderPartners() {
        ActieBericht actie = bouwBasisRelatieBericht();
        ((HuwelijkBericht) actie.getRootObjecten().get(0)).getStandaard().setDatumAanvang(new Datum(20120101));
        ((HuwelijkBericht) actie.getRootObjecten().get(0)).getStandaard().setGemeenteAanvang(
                StatischeObjecttypeBuilder.GEMEENTE_BREDA);

        List<Melding> meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertTrue(meldingen.get(0).getOmschrijving().endsWith("partner"));
        Assert.assertTrue(meldingen.get(1).getOmschrijving().endsWith("partner"));

    }

    // TODO momenteel alleen partners ondersteund met BSN, zonder bsn is op het moment out of scope
    @Test
    public void testPartnerIncompleet() {
        PersoonIdentificatienummersGroepBericht idGroep = new PersoonIdentificatienummersGroepBericht();

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(idGroep);

        PartnerBericht betrokkenheidBericht1 = new PartnerBericht();
        betrokkenheidBericht1.setPersoon(persoonBericht);
        PartnerBericht betrokkenheidBericht2 = new PartnerBericht();
        betrokkenheidBericht2.setPersoon(persoonBericht);

        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        betrokkenheden.add(betrokkenheidBericht1);
        betrokkenheden.add(betrokkenheidBericht2);

        ActieBericht actie = bouwBasisRelatieBericht();
        ((HuwelijkBericht) actie.getRootObjecten().get(0)).getStandaard().setDatumAanvang(new Datum(20120101));
        ((HuwelijkBericht) actie.getRootObjecten().get(0)).getStandaard().setGemeenteAanvang(
                StatischeObjecttypeBuilder.GEMEENTE_BREDA);
        ((HuwelijkBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(betrokkenheden);


        List<Melding> meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertTrue(meldingen.get(0).getOmschrijving().endsWith("burgerservicenummer"));
        Assert.assertTrue(meldingen.get(1).getOmschrijving().endsWith("burgerservicenummer"));

        //Tijdelijke test geval, partners zonder bsn nummer
        persoonBericht.setIdentificatienummers(null);
        meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertEquals("Personen zonder BSN wordt op het moment nog niet ondersteund",
            meldingen.get(0).getOmschrijving());
        Assert.assertEquals("Personen zonder BSN wordt op het moment nog niet ondersteund",
            meldingen.get(1).getOmschrijving());
    }

    @Test
    public void testValideBericht() {
        PersoonIdentificatienummersGroepBericht idGroep = new PersoonIdentificatienummersGroepBericht();
        idGroep.setBurgerservicenummer(new Burgerservicenummer("123"));

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(idGroep);

        PartnerBericht betrokkenheidBericht1 = new PartnerBericht();
        betrokkenheidBericht1.setPersoon(persoonBericht);
        PartnerBericht betrokkenheidBericht2 = new PartnerBericht();
        betrokkenheidBericht2.setPersoon(persoonBericht);

        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        betrokkenheden.add(betrokkenheidBericht1);
        betrokkenheden.add(betrokkenheidBericht2);

        ActieBericht actie = bouwBasisRelatieBericht();
        ((HuwelijkBericht) actie.getRootObjecten().get(0)).getStandaard().setDatumAanvang(new Datum(20120101));
        ((HuwelijkBericht) actie.getRootObjecten().get(0)).getStandaard().setGemeenteAanvang(
                StatischeObjecttypeBuilder.GEMEENTE_BREDA);
        ((HuwelijkBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(betrokkenheden);


        List<Melding> meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }

    private ActieBericht bouwBasisRelatieBericht() {
        PartnerBericht betrokkenheid1 = new PartnerBericht();
        PartnerBericht betrokkenheid2 = new PartnerBericht();

        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        betrokkenheden.add(betrokkenheid1);
        betrokkenheden.add(betrokkenheid2);

        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht gegevens =
                new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht();

        HuwelijkBericht relatie = new HuwelijkBericht();
        relatie.setStandaard(gegevens);
        relatie.setBetrokkenheden(betrokkenheden);

        List<RootObject> relaties = new ArrayList<RootObject>();
        relaties.add(relatie);

        ActieBericht actie = new ActieRegistratieHuwelijkBericht();
        actie.setRootObjecten(relaties);

        return actie;
    }

}
