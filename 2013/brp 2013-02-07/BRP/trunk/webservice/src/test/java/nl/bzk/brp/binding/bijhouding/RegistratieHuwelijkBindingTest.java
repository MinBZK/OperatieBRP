/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.io.IOException;
import java.util.List;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
@Ignore
public class RegistratieHuwelijkBindingTest
    extends AbstractBindingInIntegratieTest<HuwelijkBericht>
{

    @Test
    public void testBindingMaxBericht() throws IOException, JiBXException {
        final String xml = leesBestand("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_MAX.xml");
        valideerTegenSchema(xml);
        HuwelijkBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        Assert.assertEquals(Verwerkingswijze.P, bericht.getParameters().getVerwerkingswijze());

        //Stuurgegevens
        BerichtStuurgegevensGroepBericht berichtStuurgegevens =
            bericht.getStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("0001", berichtStuurgegevens.getOrganisatie().getWaarde());
        Assert.assertEquals("string", berichtStuurgegevens.getApplicatie().getWaarde());
        Assert.assertEquals("string", berichtStuurgegevens.getReferentienummer().getWaarde());
        Assert.assertEquals("string", berichtStuurgegevens.getCommunicatieID());

        //BRP Acties
        List<ActieBericht> acties = bericht.getAdministratieveHandeling().getActies();
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
        nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatie = (nl.bzk.brp.model.bericht.kern.HuwelijkBericht) rootObject;
        Assert.assertEquals(SoortRelatie.HUWELIJK, relatie.getSoort());
        Assert.assertEquals("string", relatie.getCommunicatieID());
        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht standaardGroep = relatie.getStandaard();
        Assert.assertNotNull(standaardGroep);
        Assert.assertEquals(new Datum(201), standaardGroep.getDatumAanvang());
        Assert.assertEquals(new GemeenteCode((short) 34), standaardGroep.getGemeenteAanvangCode());
        Assert.assertEquals(new Woonplaatscode((short) 243), standaardGroep.getWoonplaatsAanvangCode());

        //Betrokkenheden
        List<BetrokkenheidBericht> partners = relatie.getBetrokkenheden();
        Assert.assertNotNull(partners);
        Assert.assertTrue(partners.size() == 2);

        //Partner 1
        BetrokkenheidBericht pbetr1 = partners.get(0);
        Assert.assertEquals("string", pbetr1.getCommunicatieID());
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, pbetr1.getRol());
        Assert.assertEquals("string", pbetr1.getCommunicatieID());
        PersoonBericht partner1 = pbetr1.getPersoon();
        Assert.assertNotNull(partner1.getIdentificatienummers());
        Assert.assertEquals("string", partner1.getIdentificatienummers().getCommunicatieID());
        Assert.assertEquals(new Burgerservicenummer("123456789"),
            partner1.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNotNull(partner1.getGeslachtsaanduiding());
        Assert.assertEquals(Geslachtsaanduiding.ONBEKEND, partner1.getGeslachtsaanduiding().getGeslachtsaanduiding());
        Assert.assertEquals("string", partner1.getGeslachtsaanduiding().getCommunicatieID());
        Assert.assertNotNull(partner1.getSamengesteldeNaam());
        Assert.assertEquals(new PredikaatCode("J"), partner1.getSamengesteldeNaam().getPredikaatCode());
        Assert.assertEquals(new AdellijkeTitelCode("M"), partner1.getSamengesteldeNaam().getAdellijkeTitelCode());
        Assert.assertEquals(new Voornaam("string"), partner1.getSamengesteldeNaam().getVoornamen());
        Assert.assertEquals(new Voorvoegsel("string"), partner1.getSamengesteldeNaam().getVoorvoegsel());
        Assert.assertEquals(new Scheidingsteken("s"), partner1.getSamengesteldeNaam().getScheidingsteken());
        Assert.assertEquals(new Geslachtsnaamcomponent("string"), partner1.getSamengesteldeNaam().getGeslachtsnaam());
        Assert.assertEquals("string", partner1.getSamengesteldeNaam().getCommunicatieID());
        Assert.assertNotNull(partner1.getGeboorte());
        Assert.assertEquals(new Datum(201), partner1.getGeboorte().getDatumGeboorte());
        Assert.assertEquals(new GemeenteCode((short) 34), partner1.getGeboorte().getGemeenteGeboorteCode());
        Assert.assertEquals(new Woonplaatscode((short) 243), partner1.getGeboorte().getWoonplaatsGeboorteCode());
        Assert.assertEquals(new BuitenlandsePlaats("string"), partner1.getGeboorte().getBuitenlandsePlaatsGeboorte());
        Assert.assertEquals(new BuitenlandseRegio("string"), partner1.getGeboorte().getBuitenlandseRegioGeboorte());
        Assert.assertEquals(new OmschrijvingEnumeratiewaarde("string"), partner1.getGeboorte().getOmschrijvingLocatieGeboorte());
        Assert.assertEquals(new Landcode((short) 2), partner1.getGeboorte().getLandGeboorteCode());
        Assert.assertEquals("string", partner1.getGeboorte().getCommunicatieID());
        Assert.assertEquals("string", partner1.getCommunicatieID());

        //Partner 2
        BetrokkenheidBericht pbetr2 = partners.get(1);
        Assert.assertEquals("string", pbetr2.getCommunicatieID());
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, pbetr2.getRol());
        PersoonBericht partner2 = pbetr2.getPersoon();
        Assert.assertNotNull(partner2.getIdentificatienummers());
        Assert.assertEquals("string", partner2.getIdentificatienummers().getCommunicatieID());
        Assert.assertEquals(new Burgerservicenummer("string"),
            partner2.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNotNull(partner2.getGeslachtsaanduiding());
        Assert.assertEquals(Geslachtsaanduiding.VROUW, partner2.getGeslachtsaanduiding().getGeslachtsaanduiding());
        Assert.assertEquals("string", partner2.getGeslachtsaanduiding().getCommunicatieID());
        Assert.assertNotNull(partner2.getSamengesteldeNaam());
        Assert.assertEquals(new PredikaatCode("H"), partner2.getSamengesteldeNaam().getPredikaatCode());
        Assert.assertEquals(new AdellijkeTitelCode("B"), partner2.getSamengesteldeNaam().getAdellijkeTitelCode());
        Assert.assertEquals(new Voornaam("string"), partner2.getSamengesteldeNaam().getVoornamen());
        Assert.assertEquals(new Voorvoegsel("string"), partner2.getSamengesteldeNaam().getVoorvoegsel());
        Assert.assertEquals(new Scheidingsteken("s"), partner2.getSamengesteldeNaam().getScheidingsteken());
        Assert.assertEquals(new Geslachtsnaamcomponent("string"), partner2.getSamengesteldeNaam().getGeslachtsnaam());
        Assert.assertEquals("string", partner2.getSamengesteldeNaam().getCommunicatieID());
        Assert.assertNotNull(partner2.getGeboorte());
        Assert.assertEquals(new Datum(201), partner2.getGeboorte().getDatumGeboorte());
        Assert.assertEquals(new GemeenteCode((short) 34), partner2.getGeboorte().getGemeenteGeboorteCode());
        Assert.assertEquals(new Woonplaatscode((short) 243), partner2.getGeboorte().getWoonplaatsGeboorteCode());
        Assert.assertEquals(new BuitenlandsePlaats("string"), partner2.getGeboorte().getBuitenlandsePlaatsGeboorte());
        Assert.assertEquals(new BuitenlandseRegio("string"), partner2.getGeboorte().getBuitenlandseRegioGeboorte());
        Assert.assertEquals(new OmschrijvingEnumeratiewaarde("string"), partner2.getGeboorte().getOmschrijvingLocatieGeboorte());
        Assert.assertEquals(new Landcode((short) 2), partner2.getGeboorte().getLandGeboorteCode());
        Assert.assertEquals("string", partner2.getGeboorte().getCommunicatieID());
        Assert.assertEquals("string", partner2.getCommunicatieID());

        //Wijziging naamgebruik actie partner 1
        Actie wijzigNaamGebruikActie = acties.get(1);
        Assert.assertEquals(SoortActie.REGISTRATIE_AANSCHRIJVING, wijzigNaamGebruikActie.getSoort());
        Assert.assertEquals(new Datum(201), wijzigNaamGebruikActie.getDatumAanvangGeldigheid());
        List<RootObject> rootObjecten = wijzigNaamGebruikActie.getRootObjecten();
        Assert.assertTrue(rootObjecten.size() == 1);
        RootObject persoon = rootObjecten.get(0);
        Assert.assertTrue(persoon instanceof PersoonBericht);
        PersoonBericht persoonb = (PersoonBericht) persoon;
        Assert.assertNotNull(persoonb.getIdentificatienummers());
        Assert.assertEquals("string", persoonb.getIdentificatienummers().getCommunicatieID());
        Assert.assertEquals(new Burgerservicenummer("string"),
            persoonb.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNotNull(persoonb.getAanschrijving());
        Assert.assertEquals("string", persoonb.getAanschrijving().getCommunicatieID());
        Assert.assertEquals(WijzeGebruikGeslachtsnaam.EIGEN, persoonb.getAanschrijving().getNaamgebruik());
        Assert.assertEquals(JaNee.NEE, persoonb.getAanschrijving().getIndicatieAanschrijvingAlgoritmischAfgeleid());
        Assert.assertEquals(new Voorvoegsel("string"), persoonb.getAanschrijving().getVoorvoegselAanschrijving());
        Assert.assertEquals(new Scheidingsteken("s"), persoonb.getAanschrijving().getScheidingstekenAanschrijving());
        Assert.assertEquals(new Geslachtsnaam("string"), persoonb.getAanschrijving().getGeslachtsnaamAanschrijving());
        Assert.assertEquals("string", persoonb.getCommunicatieID());

        //Wijziging naamgebruik actie partner 2
        Actie wijzigNaamGebruikActie2 = acties.get(2);
        Assert.assertEquals(SoortActie.REGISTRATIE_AANSCHRIJVING, wijzigNaamGebruikActie2.getSoort());
        Assert.assertEquals(new Datum(201), wijzigNaamGebruikActie2.getDatumAanvangGeldigheid());
        List<RootObject> rootObjecten2 = wijzigNaamGebruikActie2.getRootObjecten();
        Assert.assertTrue(rootObjecten2.size() == 1);
        RootObject persoon2 = rootObjecten2.get(0);
        Assert.assertTrue(persoon2 instanceof PersoonBericht);
        PersoonBericht persoonb2 = (PersoonBericht) persoon2;
        Assert.assertNotNull(persoonb2.getIdentificatienummers());
        Assert.assertEquals("string", persoonb2.getIdentificatienummers().getCommunicatieID());
        Assert.assertEquals(new Burgerservicenummer("string"),
            persoonb2.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNotNull(persoonb2.getAanschrijving());
        Assert.assertEquals("string", persoonb2.getAanschrijving().getCommunicatieID());
        Assert.assertEquals(WijzeGebruikGeslachtsnaam.EIGEN, persoonb2.getAanschrijving().getNaamgebruik());
        Assert.assertEquals(JaNee.NEE, persoonb2.getAanschrijving().getIndicatieAanschrijvingAlgoritmischAfgeleid());
        Assert.assertEquals(new Voorvoegsel("string"), persoonb2.getAanschrijving().getVoorvoegselAanschrijving());
        Assert.assertEquals(new Scheidingsteken("s"), persoonb2.getAanschrijving().getScheidingstekenAanschrijving());
        Assert.assertEquals(new Geslachtsnaamcomponent("string"), persoonb2.getAanschrijving().getGeslachtsnaamAanschrijving());
        Assert.assertEquals("string", persoonb2.getCommunicatieID());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_MIN.xml");
        valideerTegenSchema(xml);
        HuwelijkBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        //Stuurgegevens
        BerichtStuurgegevensGroepBericht berichtStuurgegevens =
            bericht.getStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("0364", berichtStuurgegevens.getOrganisatie().getWaarde());
        Assert.assertEquals("string", berichtStuurgegevens.getApplicatie().getWaarde());
        Assert.assertEquals("string", berichtStuurgegevens.getReferentienummer().getWaarde());
        Assert.assertEquals("string", berichtStuurgegevens.getCommunicatieID());

        //BRP Acties
        List<ActieBericht> acties = bericht.getAdministratieveHandeling().getActies();
        Assert.assertNotNull(acties);
        Assert.assertTrue(acties.size() == 1);
    }

    @Override
    protected Class<HuwelijkBericht> getBindingClass() {
        return HuwelijkBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_HuwelijkPartnerschap_Berichten.xsd";
    }
}
