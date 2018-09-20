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
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;


public class HuwelijkPartnerschapActieValidatorTest {

    @Test
    public void testGeenHuwelijk() {
        ActieBericht actie = new ActieBericht();

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
        ((RelatieBericht) actie.getRootObjecten().get(0)).setGegevens(null);
        meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertEquals("Ontbrekende huwelijk gegevens", meldingen.get(0).getOmschrijving());
        Assert.assertEquals("Ontbrekende partners", meldingen.get(1).getOmschrijving());
    }

    @Test
    public void testHuwelijkZonderPartners() {
        ActieBericht actie = bouwBasisRelatieBericht();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getGegevens().setDatumAanvang(new Datum(20120101));
        ((RelatieBericht) actie.getRootObjecten().get(0)).getGegevens().setGemeenteAanvang(new Partij());

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

        BetrokkenheidBericht betrokkenheidBericht1 = new BetrokkenheidBericht();
        betrokkenheidBericht1.setBetrokkene(persoonBericht);
        betrokkenheidBericht1.setRol(SoortBetrokkenheid.PARTNER);
        BetrokkenheidBericht betrokkenheidBericht2 = new BetrokkenheidBericht();
        betrokkenheidBericht2.setRol(SoortBetrokkenheid.PARTNER);
        betrokkenheidBericht2.setBetrokkene(persoonBericht);

        List<BetrokkenheidBericht> betrokkenheden = Arrays.asList(betrokkenheidBericht1, betrokkenheidBericht2);

        ActieBericht actie = bouwBasisRelatieBericht();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getGegevens().setDatumAanvang(new Datum(20120101));
        ((RelatieBericht) actie.getRootObjecten().get(0)).getGegevens().setGemeenteAanvang(new Partij());
        ((RelatieBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(betrokkenheden);


        List<Melding> meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertTrue(meldingen.get(0).getOmschrijving().endsWith("burgerservicenummer"));
        Assert.assertTrue(meldingen.get(1).getOmschrijving().endsWith("burgerservicenummer"));

        //Tijdelijke test geval, partners zonder bsn nummer
        persoonBericht.setIdentificatienummers(null);
        meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertEquals(2, meldingen.size());
        Assert.assertEquals("Personen zonder BSN wordt op het moment nog niet ondersteund", meldingen.get(0).getOmschrijving());
        Assert.assertEquals("Personen zonder BSN wordt op het moment nog niet ondersteund", meldingen.get(1).getOmschrijving());
    }

    @Test
    public void testValideBericht() {
        PersoonIdentificatienummersGroepBericht idGroep = new PersoonIdentificatienummersGroepBericht();
        idGroep.setBurgerservicenummer(new Burgerservicenummer("123"));

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(idGroep);

        BetrokkenheidBericht betrokkenheidBericht1 = new BetrokkenheidBericht();
        betrokkenheidBericht1.setBetrokkene(persoonBericht);
        betrokkenheidBericht1.setRol(SoortBetrokkenheid.PARTNER);
        BetrokkenheidBericht betrokkenheidBericht2 = new BetrokkenheidBericht();
        betrokkenheidBericht2.setRol(SoortBetrokkenheid.PARTNER);
        betrokkenheidBericht2.setBetrokkene(persoonBericht);

        List<BetrokkenheidBericht> betrokkenheden = Arrays.asList(betrokkenheidBericht1, betrokkenheidBericht2);

        ActieBericht actie = bouwBasisRelatieBericht();
        ((RelatieBericht) actie.getRootObjecten().get(0)).getGegevens().setDatumAanvang(new Datum(20120101));
        ((RelatieBericht) actie.getRootObjecten().get(0)).getGegevens().setGemeenteAanvang(new Partij());
        ((RelatieBericht) actie.getRootObjecten().get(0)).setBetrokkenheden(betrokkenheden);


        List<Melding> meldingen = new HuwelijkPartnerschapActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }

    private ActieBericht bouwBasisRelatieBericht() {
        BetrokkenheidBericht betrokkenheid1 = new BetrokkenheidBericht();
        betrokkenheid1.setRol(SoortBetrokkenheid.PARTNER);
        BetrokkenheidBericht betrokkenheid2 = new BetrokkenheidBericht();
        betrokkenheid2.setRol(SoortBetrokkenheid.PARTNER);

        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        betrokkenheden.add(betrokkenheid1);
        betrokkenheden.add(betrokkenheid2);

        RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();

        RelatieBericht relatie = new RelatieBericht();
        relatie.setGegevens(gegevens);
        relatie.setSoort(SoortRelatie.HUWELIJK);
        relatie.setBetrokkenheden(betrokkenheden);

        List<RootObject> relaties = new ArrayList<RootObject>();
        relaties.add(relatie);

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(relaties);

        return actie;
    }

}
