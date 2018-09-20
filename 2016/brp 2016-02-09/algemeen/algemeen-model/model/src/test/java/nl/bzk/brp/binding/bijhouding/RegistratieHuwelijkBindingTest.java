/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.logisch.kern.Actie;

import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 */
public class RegistratieHuwelijkBindingTest extends
        AbstractBindingInIntegratieTest<RegistreerHuwelijkGeregistreerdPartnerschapBericht>
{

    @Test
    public void testBindingMaxBericht() throws IOException, JiBXException {
        final String xml = leesBestand("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_MAX.xml");
        valideerTegenSchema(xml);
        final RegistreerHuwelijkGeregistreerdPartnerschapBericht bericht = unmarshalObject(xml);
        assertNotNull(bericht);

        assertEquals(Verwerkingswijze.PREVALIDATIE, bericht.getParameters().getVerwerkingswijze().getWaarde());

        // Stuurgegevens
        final BerichtStuurgegevensGroepBericht berichtStuurgegevens = bericht.getStuurgegevens();
        assertNotNull(berichtStuurgegevens);

        assertEquals("123456", berichtStuurgegevens.getZendendePartijCode());
        assertEquals("UNITTEST", berichtStuurgegevens.getZendendeSysteem().getWaarde());
        assertEquals("12345678-1234-1234-1234-123456789123", berichtStuurgegevens.getReferentienummer()
            .getWaarde());
        assertEquals("stuurgegeven1", berichtStuurgegevens.getCommunicatieID());

        // BRP Acties
        final List<ActieBericht> acties = bericht.getAdministratieveHandeling().getActies();
        assertNotNull(acties);
        Assert.assertTrue(acties.size() == 3);

        // Huwelijksactie
        final Actie huwelijksActie = acties.get(0);
        assertNotNull(huwelijksActie);
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20101010), huwelijksActie.getDatumAanvangGeldigheid());
        assertEquals(SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            huwelijksActie.getSoort().getWaarde());
        final RootObject rootObject = huwelijksActie.getRootObject();
        Assert.assertTrue(rootObject instanceof RelatieBericht);
        final nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatie =
                (nl.bzk.brp.model.bericht.kern.HuwelijkBericht) rootObject;
        assertEquals(SoortRelatie.HUWELIJK, relatie.getSoort().getWaarde());
        assertEquals("id.rel.huw", relatie.getCommunicatieID());
        final RelatieStandaardGroepBericht standaardGroep = relatie.getStandaard();
        assertNotNull(standaardGroep);
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20100411), standaardGroep.getDatumAanvang());
        assertEquals("0034", standaardGroep.getGemeenteAanvangCode());
        assertEquals("0243", standaardGroep.getWoonplaatsnaamAanvang().getWaarde());

        // Betrokkenheden
        final List<BetrokkenheidBericht> partners = relatie.getBetrokkenheden();
        assertNotNull(partners);
        Assert.assertTrue(partners.size() == 2);

        // Partner 1
        checkPartner1(partners.get(0));

        // Partner 2
        checkPartner2(partners.get(1));

        // Wijziging naamgebruik actie partner 1
        final Actie wijzigNaamGebruikActie = acties.get(1);
        assertEquals(SoortActie.REGISTRATIE_NAAMGEBRUIK, wijzigNaamGebruikActie.getSoort().getWaarde());
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20101011),
            wijzigNaamGebruikActie.getDatumAanvangGeldigheid());
        final RootObject persoon = wijzigNaamGebruikActie.getRootObject();
        Assert.assertTrue(persoon instanceof PersoonBericht);
        final PersoonBericht persoonb = (PersoonBericht) persoon;
        assertEquals("id.aanschr1.pers", persoonb.getCommunicatieID());
        Assert.assertNull(persoonb.getIdentificatienummers());
        assertEquals("12314452", persoonb.getObjectSleutel());
        assertNotNull(persoonb.getNaamgebruik());
        assertEquals("id.aanschr1.pers.aanschr", persoonb.getNaamgebruik().getCommunicatieID());
        assertEquals(Naamgebruik.EIGEN, persoonb.getNaamgebruik().getNaamgebruik().getWaarde());
        assertEquals(JaNeeAttribuut.JA, persoonb.getNaamgebruik().getIndicatieNaamgebruikAfgeleid());
        assertEquals(new VoorvoegselAttribuut("arce scept"), persoonb.getNaamgebruik()
            .getVoorvoegselNaamgebruik());
        assertEquals(new ScheidingstekenAttribuut("i"), persoonb.getNaamgebruik()
            .getScheidingstekenNaamgebruik());
        assertEquals(new GeslachtsnaamstamAttribuut("ni faciat maria"), persoonb.getNaamgebruik()
            .getGeslachtsnaamstamNaamgebruik());

        // Wijziging naamgebruik actie partner 2
        final Actie wijzigNaamGebruikActie2 = acties.get(2);
        assertEquals(SoortActie.REGISTRATIE_NAAMGEBRUIK, wijzigNaamGebruikActie2.getSoort().getWaarde());
        assertEquals(Integer.valueOf(20101011), wijzigNaamGebruikActie2.getDatumAanvangGeldigheid().getWaarde());
        final RootObject persoon2 = wijzigNaamGebruikActie2.getRootObject();
        Assert.assertTrue(persoon2 instanceof PersoonBericht);
        final PersoonBericht persoonb2 = (PersoonBericht) persoon2;
        Assert.assertNull(persoonb2.getIdentificatienummers());
        assertEquals("12314452", ((PersoonBericht) persoon2).getObjectSleutel());
        assertNotNull(persoonb2.getNaamgebruik());
        assertEquals("id.aanschr2.pers.aanschr", persoonb2.getNaamgebruik().getCommunicatieID());
        assertEquals(Naamgebruik.EIGEN, persoonb2.getNaamgebruik().getNaamgebruik().getWaarde());
        assertEquals(JaNeeAttribuut.JA.getWaarde(), persoonb2.getNaamgebruik().getIndicatieNaamgebruikAfgeleid()
            .getWaarde());
        assertEquals("arce scept", persoonb2.getNaamgebruik().getVoorvoegselNaamgebruik().getWaarde());
        assertEquals("i", persoonb2.getNaamgebruik().getScheidingstekenNaamgebruik().getWaarde());
        assertEquals("ni faciat maria",
            persoonb2.getNaamgebruik().getGeslachtsnaamstamNaamgebruik().getWaarde());
        assertEquals("id.aanschr2.pers", persoonb2.getCommunicatieID());
    }

    private void checkPartner1(final BetrokkenheidBericht pbetr1) {
        assertEquals("id.betr.man", pbetr1.getCommunicatieID());
        assertEquals(SoortBetrokkenheid.PARTNER, pbetr1.getRol().getWaarde());
        final PersoonBericht partner1 = pbetr1.getPersoon();
        assertEquals("id.pers.man", partner1.getCommunicatieID());
        assertNotNull(partner1.getIdentificatienummers());
        assertEquals("id.pers.man.ident", partner1.getIdentificatienummers().getCommunicatieID());
        assertEquals("23234234", partner1.getObjectSleutel());
        assertNotNull(partner1.getGeslachtsaanduiding());
        assertEquals(Geslachtsaanduiding.MAN, partner1.getGeslachtsaanduiding().getGeslachtsaanduiding()
            .getWaarde());
        assertEquals("id.pers.man.geslacht", partner1.getGeslachtsaanduiding().getCommunicatieID());
        assertNotNull(partner1.getSamengesteldeNaam());
        assertEquals("id.pers.man.samnaam", partner1.getSamengesteldeNaam().getCommunicatieID());
        assertEquals("J", partner1.getSamengesteldeNaam().getPredicaatCode());
        assertEquals("M", partner1.getSamengesteldeNaam().getAdellijkeTitelCode());
        assertEquals(new VoornamenAttribuut("ipsa iovis"), partner1.getSamengesteldeNaam().getVoornamen());
        assertEquals(new VoorvoegselAttribuut("mollitque"), partner1.getSamengesteldeNaam().getVoorvoegsel());
        assertEquals(new ScheidingstekenAttribuut("i"), partner1.getSamengesteldeNaam().getScheidingsteken());
        assertEquals(new GeslachtsnaamstamAttribuut("gero et"), partner1.getSamengesteldeNaam()
            .getGeslachtsnaamstam());
        assertNotNull(partner1.getGeboorte());
        assertEquals("id.pers.man.geb", partner1.getGeboorte().getCommunicatieID());
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20101011), partner1.getGeboorte().getDatumGeboorte());
        assertEquals("0034", partner1.getGeboorte().getGemeenteGeboorteCode());
        assertEquals("0243", partner1.getGeboorte().getWoonplaatsnaamGeboorte().getWaarde());
        assertEquals(new BuitenlandsePlaatsAttribuut("et premere et"), partner1.getGeboorte()
            .getBuitenlandsePlaatsGeboorte());
        assertEquals(new BuitenlandseRegioAttribuut("qui foedere"), partner1.getGeboorte()
            .getBuitenlandseRegioGeboorte());
        assertEquals(new LocatieomschrijvingAttribuut("imposuit regemque"), partner1.getGeboorte()
            .getOmschrijvingLocatieGeboorte());
        assertEquals("0002", partner1.getGeboorte().getLandGebiedGeboorteCode());
    }

    private void checkPartner2(final BetrokkenheidBericht pbetr2) {
        assertEquals("id.betr.vrouw", pbetr2.getCommunicatieID());
        assertEquals(SoortBetrokkenheid.PARTNER, pbetr2.getRol().getWaarde());
        final PersoonBericht partner2 = pbetr2.getPersoon();
        assertEquals("id.pers.vrouw", partner2.getCommunicatieID());
        assertNotNull(partner2.getIdentificatienummers());
        assertEquals("id.pers.vrouw.ident", partner2.getIdentificatienummers().getCommunicatieID());
        assertEquals("23423423", partner2.getObjectSleutel());
        assertNotNull(partner2.getGeslachtsaanduiding());
        assertEquals("id.pers.vrouw.geslacht", partner2.getGeslachtsaanduiding().getCommunicatieID());
        assertEquals(Geslachtsaanduiding.VROUW, partner2.getGeslachtsaanduiding().getGeslachtsaanduiding()
            .getWaarde());
        assertNotNull(partner2.getSamengesteldeNaam());
        assertEquals("id.pers.vrouw.samnaam", partner2.getSamengesteldeNaam().getCommunicatieID());
        assertEquals("H", partner2.getSamengesteldeNaam().getPredicaatCode());
        assertEquals("B", partner2.getSamengesteldeNaam().getAdellijkeTitelCode());
        assertEquals(new VoornamenAttribuut("ego quae"), partner2.getSamengesteldeNaam().getVoornamen());
        assertEquals(new VoorvoegselAttribuut("aris impon"), partner2.getSamengesteldeNaam().getVoorvoegsel());
        assertEquals(new ScheidingstekenAttribuut("t"), partner2.getSamengesteldeNaam().getScheidingsteken());
        assertEquals(new GeslachtsnaamstamAttribuut("nimborum in"), partner2.getSamengesteldeNaam()
            .getGeslachtsnaamstam());
        assertNotNull(partner2.getGeboorte());
        assertEquals("id.pers.vrouw.geb", partner2.getGeboorte().getCommunicatieID());
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20101012), partner2.getGeboorte().getDatumGeboorte());
        assertEquals("0035", partner2.getGeboorte().getGemeenteGeboorteCode());
        assertEquals("0351", partner2.getGeboorte().getWoonplaatsnaamGeboorte().getWaarde());
        assertEquals(new BuitenlandsePlaatsAttribuut("imponet honorem talia"), partner2.getGeboorte()
            .getBuitenlandsePlaatsGeboorte());
        assertEquals(new BuitenlandseRegioAttribuut("volutans nimborum in"), partner2.getGeboorte()
            .getBuitenlandseRegioGeboorte());
        assertEquals(new LocatieomschrijvingAttribuut("et carcere"), partner2.getGeboorte()
            .getOmschrijvingLocatieGeboorte());
        assertEquals("0003", partner2.getGeboorte().getLandGebiedGeboorteCode());
    }

    @Test
    public void testBindingVoorbeeldFeitgemeenteBericht() throws IOException, JiBXException {
        final String xml = leesBestand("voorbeeld/bhg_hgpVoltrekkingHuwelijkInNederland_Bijhouding_Feitgemeente.xml");
        valideerTegenSchema(xml);
        final RegistreerHuwelijkGeregistreerdPartnerschapBericht bericht = unmarshalObject(xml);
        assertNotNull(bericht);

        assertEquals(Verwerkingswijze.BIJHOUDING, bericht.getParameters().getVerwerkingswijze().getWaarde());

        // Stuurgegevens
        final BerichtStuurgegevensGroepBericht berichtStuurgegevens = bericht.getStuurgegevens();
        assertNotNull(berichtStuurgegevens);

        assertEquals("053001", berichtStuurgegevens.getZendendePartijCode());
        assertEquals("BZM Leverancier A", berichtStuurgegevens.getZendendeSysteem().getWaarde());
        assertEquals("88409eeb-1aa5-43fc-8614-43055123a165", berichtStuurgegevens.getReferentienummer()
            .getWaarde());
        assertEquals("identificatie00B", berichtStuurgegevens.getCommunicatieID());

        // BRP Acties
        final List<ActieBericht> acties = bericht.getAdministratieveHandeling().getActies();
        assertNotNull(acties);
        assertEquals(2, acties.size());

        // Huwelijksactie
        final Actie huwelijksActie = acties.get(0);
        assertNotNull(huwelijksActie);
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20120613), huwelijksActie.getDatumAanvangGeldigheid());
        assertEquals(SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            huwelijksActie.getSoort().getWaarde());
        final RootObject rootObject = huwelijksActie.getRootObject();
        Assert.assertTrue(rootObject instanceof RelatieBericht);
        final nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatie =
            (nl.bzk.brp.model.bericht.kern.HuwelijkBericht) rootObject;
        assertEquals(SoortRelatie.HUWELIJK, relatie.getSoort().getWaarde());
        assertEquals("identificatie08B", relatie.getCommunicatieID());
        final RelatieStandaardGroepBericht standaardGroep = relatie.getStandaard();
        assertNotNull(standaardGroep);
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20150613), standaardGroep.getDatumAanvang());
        assertEquals("0530", standaardGroep.getGemeenteAanvangCode());

        // Betrokkenheden
        final List<BetrokkenheidBericht> partners = relatie.getBetrokkenheden();
        assertNotNull(partners);
        Assert.assertTrue(partners.size() == 2);

        // Partner 1
        checkVoorbeeldPartner1(partners.get(0));

        // Partner 2
        checkVoorbeeldPartner2(partners.get(1));

        // Wijziging naamgebruik actie partner 1
        final Actie wijzigNaamGebruikActie = acties.get(1);
        assertEquals(SoortActie.REGISTRATIE_NAAMGEBRUIK, wijzigNaamGebruikActie.getSoort().getWaarde());
        assertEquals(new DatumEvtDeelsOnbekendAttribuut(20150613),
            wijzigNaamGebruikActie.getDatumAanvangGeldigheid());
        final RootObject persoon = wijzigNaamGebruikActie.getRootObject();
        Assert.assertTrue(persoon instanceof PersoonBericht);
        final PersoonBericht persoonb = (PersoonBericht) persoon;
        assertEquals("identificatie17B", persoonb.getCommunicatieID());
        Assert.assertNull(persoonb.getIdentificatienummers());
        assertEquals("123456789987654321", persoonb.getObjectSleutel());
        assertNotNull(persoonb.getNaamgebruik());
        assertEquals("identificatie18B", persoonb.getNaamgebruik().getCommunicatieID());
        assertEquals(Naamgebruik.PARTNER, persoonb.getNaamgebruik().getNaamgebruik().getWaarde());
        assertEquals(JaNeeAttribuut.JA, persoonb.getNaamgebruik().getIndicatieNaamgebruikAfgeleid());
    }

    private void checkVoorbeeldPartner1(final BetrokkenheidBericht pbetr1) {
        assertEquals("identificatie10B", pbetr1.getCommunicatieID());
        assertEquals(SoortBetrokkenheid.PARTNER, pbetr1.getRol().getWaarde());
        final PersoonBericht partner1 = pbetr1.getPersoon();
        assertEquals("identificatie11B", partner1.getCommunicatieID());
        assertEquals("123456789987654321", partner1.getObjectSleutel());
    }

    private void checkVoorbeeldPartner2(final BetrokkenheidBericht pbetr2) {
        assertEquals("identificatie13B", pbetr2.getCommunicatieID());
        assertEquals(SoortBetrokkenheid.PARTNER, pbetr2.getRol().getWaarde());
        final PersoonBericht partner2 = pbetr2.getPersoon();
        assertEquals("identificatie14B", partner2.getCommunicatieID());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("huwelijkPartnerschap_RegistratieHuwelijk_Bijhouding_MIN.xml");
        valideerTegenSchema(xml);
        final RegistreerHuwelijkGeregistreerdPartnerschapBericht bericht = unmarshalObject(xml);
        assertNotNull(bericht);

        // Stuurgegevens
        final BerichtStuurgegevensGroepBericht berichtStuurgegevens = bericht.getStuurgegevens();
        assertNotNull(berichtStuurgegevens);

        assertEquals("123456", berichtStuurgegevens.getZendendePartijCode());
        assertEquals("UNITTEST", berichtStuurgegevens.getZendendeSysteem().getWaarde());
        assertEquals("12345678-1234-1234-1234-123456789123", berichtStuurgegevens.getReferentienummer()
            .getWaarde());

        // BRP Acties
        final List<ActieBericht> acties = bericht.getAdministratieveHandeling().getActies();
        assertNotNull(acties);
        Assert.assertTrue(acties.size() == 1);
    }

    @Override
    protected Class<RegistreerHuwelijkGeregistreerdPartnerschapBericht> getBindingClass() {
        return RegistreerHuwelijkGeregistreerdPartnerschapBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdHuwelijkPartnerschapBerichten();
    }
}
