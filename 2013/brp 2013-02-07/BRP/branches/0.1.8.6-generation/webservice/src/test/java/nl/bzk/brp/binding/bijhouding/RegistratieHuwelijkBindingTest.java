/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.io.IOException;
import java.util.List;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.attribuuttype.Woonplaatscode;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class RegistratieHuwelijkBindingTest
        extends AbstractBindingInIntegratieTest<HuwelijkEnGeregistreerdPartnerschapBericht>
{

    @Test
    public void testBindingMaxBericht() throws IOException, JiBXException {
        final String xml = leesBestand("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_MAX.xml");
        valideerTegenSchema(xml);
        HuwelijkEnGeregistreerdPartnerschapBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        //Stuurgegevens
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("string", berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals("string", berichtStuurgegevens.getApplicatie());
        Assert.assertEquals("string", berichtStuurgegevens.getReferentienummer());
        Assert.assertEquals(JaNee.Ja, berichtStuurgegevens.getPrevalidatieBericht());
        Assert.assertEquals("string", berichtStuurgegevens.getVerzendendId());

        //BRP Acties
        List<Actie> acties = bericht.getBrpActies();
        Assert.assertNotNull(acties);
        Assert.assertTrue(acties.size() == 3);

        //Huwelijksactie
        Actie huwelijksActie = acties.get(0);
        Assert.assertNotNull(huwelijksActie);
        Assert.assertEquals(new Datum(201), huwelijksActie.getDatumAanvangGeldigheid());
        Assert.assertEquals(SoortActie.REGISTRATIE_HUWELIJK, huwelijksActie.getSoort());
        List<RootObject> rootObjects = huwelijksActie.getRootObjecten();
        Assert.assertNotNull(rootObjects);
        Assert.assertTrue(rootObjects.size() == 1);
        RootObject rootObject = rootObjects.get(0);
        Assert.assertTrue(rootObject instanceof RelatieBericht);
        RelatieBericht relatie = (RelatieBericht) rootObject;
        Assert.assertEquals(SoortRelatie.HUWELIJK, relatie.getSoort());
        Assert.assertEquals("string", relatie.getVerzendendId());
        RelatieStandaardGroepBericht standaardGroep = relatie.getGegevens();
        Assert.assertNotNull(standaardGroep);
        Assert.assertEquals(new Datum(201), standaardGroep.getDatumAanvang());
        Assert.assertEquals(new Gemeentecode((short) 34), standaardGroep.getGemeenteAanvangCode());
        Assert.assertEquals(new Woonplaatscode((short) 243), standaardGroep.getWoonPlaatsAanvangCode());

        //Betrokkenheden
        List<BetrokkenheidBericht> partners = relatie.getBetrokkenheden();
        Assert.assertNotNull(partners);
        Assert.assertTrue(partners.size() == 2);

        //Partner 1
        BetrokkenheidBericht pbetr1 = partners.get(0);
        Assert.assertEquals("string", pbetr1.getVerzendendId());
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, pbetr1.getRol());
        Assert.assertEquals("string", pbetr1.getVerzendendId());
        PersoonBericht partner1 = pbetr1.getBetrokkene();
        Assert.assertNotNull(partner1.getIdentificatienummers());
        Assert.assertEquals("string", partner1.getIdentificatienummers().getVerzendendId());
        Assert.assertEquals(new Burgerservicenummer("string"), partner1.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNotNull(partner1.getGeslachtsaanduiding());
        Assert.assertEquals(Geslachtsaanduiding.ONBEKEND, partner1.getGeslachtsaanduiding().getGeslachtsaanduiding());
        Assert.assertEquals("string", partner1.getGeslachtsaanduiding().getVerzendendId());
        Assert.assertNotNull(partner1.getSamengesteldeNaam());
        Assert.assertEquals(new PredikaatCode("J"), partner1.getSamengesteldeNaam().getPredikaatCode());
        Assert.assertEquals(new AdellijkeTitelCode("M"), partner1.getSamengesteldeNaam().getAdellijkeTitelCode());
        Assert.assertEquals(new Voornaam("string"), partner1.getSamengesteldeNaam().getVoornamen());
        Assert.assertEquals(new Voorvoegsel("string"), partner1.getSamengesteldeNaam().getVoorvoegsel());
        Assert.assertEquals(new Scheidingsteken("s"), partner1.getSamengesteldeNaam().getScheidingsteken());
        Assert.assertEquals(new Geslachtsnaamcomponent("string"), partner1.getSamengesteldeNaam().getGeslachtsnaam());
        Assert.assertEquals("string", partner1.getSamengesteldeNaam().getVerzendendId());
        Assert.assertNotNull(partner1.getGeboorte());
        Assert.assertEquals(new Datum(201), partner1.getGeboorte().getDatumGeboorte());
        Assert.assertEquals(new Gemeentecode((short) 34), partner1.getGeboorte().getGemeenteGeboorteCode());
        Assert.assertEquals(new Woonplaatscode((short) 243), partner1.getGeboorte().getWoonplaatsGeboorteCode());
        Assert.assertEquals(new BuitenlandsePlaats("string"), partner1.getGeboorte().getBuitenlandseGeboortePlaats());
        Assert.assertEquals(new BuitenlandseRegio("string"), partner1.getGeboorte().getBuitenlandseRegioGeboorte());
        Assert.assertEquals(new OmschrijvingEnumeratiewaarde("string"), partner1.getGeboorte().getOmschrijvingGeboorteLocatie());
        Assert.assertEquals(new Landcode((short) 2), partner1.getGeboorte().getLandGeboorteCode());
        Assert.assertEquals("string", partner1.getGeboorte().getVerzendendId());
        Assert.assertEquals("string", partner1.getVerzendendId());

         //Partner 2
        BetrokkenheidBericht pbetr2 = partners.get(0);
        Assert.assertEquals("string", pbetr2.getVerzendendId());
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, pbetr2.getRol());
        Assert.assertEquals("string", pbetr2.getVerzendendId());
        PersoonBericht partner2 = pbetr2.getBetrokkene();
        Assert.assertNotNull(partner2.getIdentificatienummers());
        Assert.assertEquals("string", partner2.getIdentificatienummers().getVerzendendId());
        Assert.assertEquals(new Burgerservicenummer("string"), partner2.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNotNull(partner2.getGeslachtsaanduiding());
        Assert.assertEquals(Geslachtsaanduiding.ONBEKEND, partner2.getGeslachtsaanduiding().getGeslachtsaanduiding());
        Assert.assertEquals("string", partner2.getGeslachtsaanduiding().getVerzendendId());
        Assert.assertNotNull(partner2.getSamengesteldeNaam());
        Assert.assertEquals(new PredikaatCode("J"), partner2.getSamengesteldeNaam().getPredikaatCode());
        Assert.assertEquals(new AdellijkeTitelCode("M"), partner2.getSamengesteldeNaam().getAdellijkeTitelCode());
        Assert.assertEquals(new Voornaam("string"), partner2.getSamengesteldeNaam().getVoornamen());
        Assert.assertEquals(new Voorvoegsel("string"), partner2.getSamengesteldeNaam().getVoorvoegsel());
        Assert.assertEquals(new Scheidingsteken("s"), partner2.getSamengesteldeNaam().getScheidingsteken());
        Assert.assertEquals(new Geslachtsnaamcomponent("string"), partner2.getSamengesteldeNaam().getGeslachtsnaam());
        Assert.assertEquals("string", partner2.getSamengesteldeNaam().getVerzendendId());
        Assert.assertNotNull(partner2.getGeboorte());
        Assert.assertEquals(new Datum(201), partner2.getGeboorte().getDatumGeboorte());
        Assert.assertEquals(new Gemeentecode((short) 34), partner2.getGeboorte().getGemeenteGeboorteCode());
        Assert.assertEquals(new Woonplaatscode((short) 243), partner2.getGeboorte().getWoonplaatsGeboorteCode());
        Assert.assertEquals(new BuitenlandsePlaats("string"), partner2.getGeboorte().getBuitenlandseGeboortePlaats());
        Assert.assertEquals(new BuitenlandseRegio("string"), partner2.getGeboorte().getBuitenlandseRegioGeboorte());
        Assert.assertEquals(new OmschrijvingEnumeratiewaarde("string"), partner2.getGeboorte().getOmschrijvingGeboorteLocatie());
        Assert.assertEquals(new Landcode((short) 2), partner2.getGeboorte().getLandGeboorteCode());
        Assert.assertEquals("string", partner2.getGeboorte().getVerzendendId());
        Assert.assertEquals("string", partner2.getVerzendendId());

        //Wijziging naamgebruik actie partner 1
        Actie wijzigNaamGebruikActie = acties.get(1);
        Assert.assertEquals(SoortActie.WIJZIGING_NAAMGEBRUIK, wijzigNaamGebruikActie.getSoort());
        Assert.assertEquals(new Datum(201), wijzigNaamGebruikActie.getDatumAanvangGeldigheid());
        List<RootObject> rootObjecten = wijzigNaamGebruikActie.getRootObjecten();
        Assert.assertTrue(rootObjecten.size() == 1);
        RootObject persoon = rootObjecten.get(0);
        Assert.assertTrue(persoon instanceof PersoonBericht);
        PersoonBericht persoonb = (PersoonBericht) persoon;
        Assert.assertNotNull(persoonb.getIdentificatienummers());
        Assert.assertEquals("string", persoonb.getIdentificatienummers().getVerzendendId());
        Assert.assertEquals(new Burgerservicenummer("string"), persoonb.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNotNull(persoonb.getAanschrijving());
        Assert.assertEquals("string", persoonb.getAanschrijving().getVerzendendId());
        Assert.assertEquals(WijzeGebruikGeslachtsnaam.EIGEN, persoonb.getAanschrijving().getGebruikGeslachtsnaam());
        Assert.assertEquals(JaNee.Nee, persoonb.getAanschrijving().getIndAanschrijvingAlgorthmischAfgeleid());
        Assert.assertEquals(new Voorvoegsel("string"), persoonb.getAanschrijving().getVoorvoegsel());
        Assert.assertEquals(new Scheidingsteken("s"), persoonb.getAanschrijving().getScheidingsteken());
        Assert.assertEquals(new Geslachtsnaamcomponent("string"), persoonb.getAanschrijving().getGeslachtsnaam());
        Assert.assertEquals("string", persoonb.getVerzendendId());

        //Wijziging naamgebruik actie partner 2
        Actie wijzigNaamGebruikActie2 = acties.get(2);
        Assert.assertEquals(SoortActie.WIJZIGING_NAAMGEBRUIK, wijzigNaamGebruikActie2.getSoort());
        Assert.assertEquals(new Datum(201), wijzigNaamGebruikActie2.getDatumAanvangGeldigheid());
        List<RootObject> rootObjecten2 = wijzigNaamGebruikActie2.getRootObjecten();
        Assert.assertTrue(rootObjecten2.size() == 1);
        RootObject persoon2 = rootObjecten2.get(0);
        Assert.assertTrue(persoon2 instanceof PersoonBericht);
        PersoonBericht persoonb2 = (PersoonBericht) persoon2;
        Assert.assertNotNull(persoonb2.getIdentificatienummers());
        Assert.assertEquals("string", persoonb2.getIdentificatienummers().getVerzendendId());
        Assert.assertEquals(new Burgerservicenummer("string"), persoonb2.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNotNull(persoonb2.getAanschrijving());
        Assert.assertEquals("string", persoonb2.getAanschrijving().getVerzendendId());
        Assert.assertEquals(WijzeGebruikGeslachtsnaam.EIGEN, persoonb2.getAanschrijving().getGebruikGeslachtsnaam());
        Assert.assertEquals(JaNee.Nee, persoonb2.getAanschrijving().getIndAanschrijvingAlgorthmischAfgeleid());
        Assert.assertEquals(new Voorvoegsel("string"), persoonb2.getAanschrijving().getVoorvoegsel());
        Assert.assertEquals(new Scheidingsteken("s"), persoonb2.getAanschrijving().getScheidingsteken());
        Assert.assertEquals(new Geslachtsnaamcomponent("string"), persoonb2.getAanschrijving().getGeslachtsnaam());
        Assert.assertEquals("string", persoonb2.getVerzendendId());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_MIN.xml");
        valideerTegenSchema(xml);
        HuwelijkEnGeregistreerdPartnerschapBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        //Stuurgegevens
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("string", berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals("string", berichtStuurgegevens.getApplicatie());
        Assert.assertEquals("string", berichtStuurgegevens.getReferentienummer());
        Assert.assertEquals("string", berichtStuurgegevens.getVerzendendId());

        //BRP Acties
        List<Actie> acties = bericht.getBrpActies();
        Assert.assertNotNull(acties);
        Assert.assertTrue(acties.size() == 1);
    }

    @Override
    protected Class<HuwelijkEnGeregistreerdPartnerschapBericht> getBindingClass() {
        return HuwelijkEnGeregistreerdPartnerschapBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_HuwelijkPartnerschap_Berichten.xsd";
    }
}
