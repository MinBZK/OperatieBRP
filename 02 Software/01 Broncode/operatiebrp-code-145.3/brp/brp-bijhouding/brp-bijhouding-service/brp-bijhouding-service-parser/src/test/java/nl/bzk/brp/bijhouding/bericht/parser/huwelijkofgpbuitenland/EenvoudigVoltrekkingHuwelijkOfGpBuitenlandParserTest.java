/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.huwelijkofgpbuitenland;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.AbstractHuwelijkOfGpElement;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BetrokkenheidElement;
import nl.bzk.brp.bijhouding.bericht.model.BetrokkenheidElementSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BronElement;
import nl.bzk.brp.bijhouding.bericht.model.BronReferentieElement;
import nl.bzk.brp.bijhouding.bericht.model.DocumentElement;
import nl.bzk.brp.bijhouding.bericht.model.GeboorteElement;
import nl.bzk.brp.bijhouding.bericht.model.GedeblokkeerdeMeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.GeslachtsaanduidingElement;
import nl.bzk.brp.bijhouding.bericht.model.IdentificatienummersElement;
import nl.bzk.brp.bijhouding.bericht.model.ParametersElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieAanvangGeregistreerdPartnerschapActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieAanvangHuwelijkActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RelatieGroepElement;
import nl.bzk.brp.bijhouding.bericht.model.SamengesteldeNaamElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor BijhoudingVerzoekBerichtParser.
 */
public class EenvoudigVoltrekkingHuwelijkOfGpBuitenlandParserTest extends AbstractParserTest {

    public static final String EENVOUDIG_HUWELIJK_BERICHT = "eenvoudigVoltrekkingHuwelijkBuitenlandBericht.xml";
    public static final String EENVOUDIG_GP_BERICHT = "eenvoudigAangaanGpBuitenlandBericht.xml";

    @Test
    public void testValideerXmlBerichtHuwelijk() {
        valideerTegenSchema(this.getClass().getResourceAsStream(EENVOUDIG_HUWELIJK_BERICHT));
    }

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenSchema(this.getClass().getResourceAsStream(EENVOUDIG_GP_BERICHT));
    }

    @Test
    public void testParsenEenvoudigBerichtHuwelijk() throws ParseException {
        testParsenEenvoudigBericht(EENVOUDIG_HUWELIJK_BERICHT, false);
    }

    @Test
    public void testParsenEenvoudigBerichtGp() throws ParseException {
        testParsenEenvoudigBericht(EENVOUDIG_GP_BERICHT, true);
    }

    private void testParsenEenvoudigBericht(final String resource, final boolean isGp) throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(resource));
        Assert.assertNotNull(bericht);
        assertStuurgegevens(bericht.getStuurgegevens());
        Assert.assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP, bericht.getSoort());
        assertParameters(bericht.getParameters());
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling(), isGp);
    }

    private void assertStuurgegevens(final StuurgegevensElement stuurgegevens) {
        Assert.assertNotNull(stuurgegevens);
        assertEqualStringElement("053001", stuurgegevens.getZendendePartij());
        assertEqualStringElement("BZM Leverancier A", stuurgegevens.getZendendeSysteem());
        assertEqualStringElement("88409eeb-1aa5-43fc-8614-43055123a165", stuurgegevens.getReferentienummer());
        assertEqualDatumTijdElement("2016-03-21T09:32:03.234+02:00", stuurgegevens.getTijdstipVerzending());
    }

    private void assertParameters(final ParametersElement parameters) {
        Assert.assertNotNull(parameters);
        assertEqualStringElement("Bijhouding", parameters.getVerwerkingswijze());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling, final boolean isGp) {
        Assert.assertNotNull(administratieveHandeling);
        assertEqualStringElement("123456", administratieveHandeling.getPartijCode());
        assertEqualStringElement("Test toelichting op de ontlening", administratieveHandeling.getToelichtingOntlening());
        assertGedeblokkeerdeMeldingen(administratieveHandeling.getGedeblokkeerdeMeldingen());
        assertBronnen(administratieveHandeling.getBronnen());
        assertActies(administratieveHandeling.getActies(), isGp);
    }

    private void assertActies(final List<ActieElement> acties, final boolean isGp) {
        Assert.assertEquals(1, acties.size());
        if(isGp) {
            Assert.assertTrue(acties.get(0) instanceof RegistratieAanvangGeregistreerdPartnerschapActieElement);
            final RegistratieAanvangGeregistreerdPartnerschapActieElement actie = (RegistratieAanvangGeregistreerdPartnerschapActieElement) acties.get(0);
            assertBronReferenties(actie.getBronReferenties());
            assertHuwelijkOfGp(actie.getGeregistreerdPartnerschap());
        } else {
            Assert.assertTrue(acties.get(0) instanceof RegistratieAanvangHuwelijkActieElement);
            final RegistratieAanvangHuwelijkActieElement actie = (RegistratieAanvangHuwelijkActieElement) acties.get(0);
            assertBronReferenties(actie.getBronReferenties());
            assertHuwelijkOfGp(actie.getHuwelijk());
        }
    }

    private void assertHuwelijkOfGp(final AbstractHuwelijkOfGpElement huwelijkOfGp) {
        Assert.assertNotNull(huwelijkOfGp);
        assertRelatie(huwelijkOfGp.getRelatieGroep());
        assertBetrokkenheden(huwelijkOfGp.getBetrokkenheden());
    }

    private void assertBetrokkenheden(final List<BetrokkenheidElement> betrokkenheden) {
        Assert.assertEquals(2, betrokkenheden.size());
        final BetrokkenheidElement betrokkenheid1 = betrokkenheden.get(0);
        final BetrokkenheidElement betrokkenheid2 = betrokkenheden.get(1);
        assertBetrokkenheid1(betrokkenheid1);
        assertBetrokkenheid2(betrokkenheid2);
    }

    private void assertBetrokkenheid1(final BetrokkenheidElement betrokkenheid1) {
        Assert.assertNotNull(betrokkenheid1);
        Assert.assertEquals(BetrokkenheidElementSoort.PARTNER, betrokkenheid1.getSoort());
        Assert.assertNotNull(betrokkenheid1.getPersoon());
        Assert.assertNotNull(betrokkenheid1.getPersoon().getObjectSleutel());
        Assert.assertNull(betrokkenheid1.getPersoon().getIdentificatienummers());
        Assert.assertNull(betrokkenheid1.getPersoon().getGeboorte());
        Assert.assertNull(betrokkenheid1.getPersoon().getGeslachtsaanduiding());
        Assert.assertNull(betrokkenheid1.getPersoon().getSamengesteldeNaam());
    }

    private void assertBetrokkenheid2(final BetrokkenheidElement betrokkenheid2) {
        Assert.assertNotNull(betrokkenheid2);
        Assert.assertEquals(BetrokkenheidElementSoort.PARTNER, betrokkenheid2.getSoort());
        Assert.assertNotNull(betrokkenheid2.getPersoon());
        Assert.assertNull(betrokkenheid2.getPersoon().getObjectSleutel());
        final PersoonElement nietIngeschrevenPersoon = betrokkenheid2.getPersoon();
        assertIdentificatienummersBetrokkenheid2(nietIngeschrevenPersoon.getIdentificatienummers());
        assertSamengesteldeNaamBetrokkenheid2(nietIngeschrevenPersoon.getSamengesteldeNaam());
        assertGeboorteBetrokkenheid2(nietIngeschrevenPersoon.getGeboorte());
        assertGeslachtsaanduidingBetrokkenheid2(nietIngeschrevenPersoon.getGeslachtsaanduiding());
    }

    private void assertGeslachtsaanduidingBetrokkenheid2(final GeslachtsaanduidingElement geslachtsaanduiding) {
        Assert.assertNotNull(geslachtsaanduiding);
        assertEqualStringElement("M", geslachtsaanduiding.getCode());
    }

    private void assertGeboorteBetrokkenheid2(final GeboorteElement geboorte) {
        Assert.assertNotNull(geboorte);
        assertEqualDatumElement("1985-09-19", geboorte.getDatum());
        assertEqualStringElement("Maceio", geboorte.getBuitenlandsePlaats());
        assertEqualStringElement("6008", geboorte.getLandGebiedCode());
        assertEqualStringElement("1234", geboorte.getGemeenteCode());
        assertEqualStringElement("Plaats", geboorte.getWoonplaatsnaam());
        assertEqualStringElement("Regio", geboorte.getBuitenlandseRegio());
        assertEqualStringElement("Locatie", geboorte.getOmschrijvingLocatie());
    }

    private void assertSamengesteldeNaamBetrokkenheid2(final SamengesteldeNaamElement samengesteldeNaam) {
        Assert.assertNotNull(samengesteldeNaam);
        assertEqualBooleanElement("N", samengesteldeNaam.getIndicatieNamenreeks());
        assertEqualStringElement("dos", samengesteldeNaam.getVoorvoegsel());
        assertEqualStringElement("Willy ", samengesteldeNaam.getVoornamen());
        assertEqualCharacterElement(' ', samengesteldeNaam.getScheidingsteken());
        assertEqualStringElement("Santos da Victoria", samengesteldeNaam.getGeslachtsnaamstam());
        assertEqualStringElement(null, samengesteldeNaam.getPredicaatCode());
        assertEqualStringElement(null, samengesteldeNaam.getAdellijkeTitelCode());
    }

    private void assertIdentificatienummersBetrokkenheid2(final IdentificatienummersElement identificatienummers) {
        Assert.assertNotNull(identificatienummers);
        assertEqualStringElement("987654321", identificatienummers.getBurgerservicenummer());
        assertEqualStringElement("1234567890", identificatienummers.getAdministratienummer());
    }

    private void assertRelatie(final RelatieGroepElement relatie) {
        Assert.assertNotNull(relatie);
        assertEqualDatumElement("2016-03-22", relatie.getDatumAanvang());
        assertEqualStringElement("Barcelona", relatie.getBuitenlandsePlaatsAanvang());
        assertEqualStringElement("Catalonie", relatie.getBuitenlandseRegioAanvang());
        assertEqualStringElement("6037", relatie.getLandGebiedAanvangCode());
        assertEqualStringElement(null, relatie.getWoonplaatsnaamAanvang());
    }

    private void assertBronReferenties(final List<BronReferentieElement> bronReferenties) {
        Assert.assertEquals(1, bronReferenties.size());
        final BronReferentieElement bronReferentie = bronReferenties.get(0);
        final List<BronElement> bronnen = Collections.singletonList(bronReferentie.getReferentie());
        assertBronnen(bronnen);
    }

    private void assertBronnen(final List<BronElement> bronnen) {
        Assert.assertEquals(1, bronnen.size());
        final BronElement bron = bronnen.get(0);
        Assert.assertNotNull(bron);
        final DocumentElement document = bron.getDocument();
        Assert.assertNotNull(document);
        assertEqualStringElement(null, document.getAktenummer());
        assertEqualStringElement("Huwelijksakte", document.getSoortNaam());
        assertEqualStringElement(null, document.getOmschrijving());
        assertEqualStringElement("053001", document.getPartijCode());
    }

    private void assertGedeblokkeerdeMeldingen(final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen) {
        Assert.assertEquals(1, gedeblokkeerdeMeldingen.size());
        final GedeblokkeerdeMeldingElement gedeblokkeerdeMelding = gedeblokkeerdeMeldingen.get(0);
        Assert.assertNotNull(gedeblokkeerdeMelding);
        assertEqualStringElement("REGEL_1", gedeblokkeerdeMelding.getRegelCode());
        Assert.assertEquals("CI_gedeblokkeerde_melding_1", gedeblokkeerdeMelding.getCommunicatieId());
        Assert.assertEquals("GedeblokkeerdeMelding", gedeblokkeerdeMelding.getObjecttype());
        Assert.assertEquals("CI_ref_naar_vorig_bericht", gedeblokkeerdeMelding.getReferentieId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParsenNietBestaandBericht() throws ParseException {
        new BijhoudingVerzoekBerichtParser().parse(this.getClass().getResourceAsStream("nietbestaanderesource"));
    }
}
