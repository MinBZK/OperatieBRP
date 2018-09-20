/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test class voor het testen van de functionaliteit in de {@link BevragingAntwoordBericht} class. */
public class BevragingAntwoordBerichtTest {

    @Test
    public void testAntwoordZonderPersonen() {
        BevragingAntwoordBericht antwoordBericht = new BevragingAntwoordBericht(bouwOpvragenPersoonResultaat());

        Assert.assertNull(antwoordBericht.getPersonen());
    }

    @Test
    public void testAntwoordMetEnkelPersoon() {
        BevragingAntwoordBericht antwoordBericht = new BevragingAntwoordBericht(
            bouwOpvragenPersoonResultaat(bouwPersoon(2, "123456789")));

        Assert.assertNotNull(antwoordBericht.getPersonen());
        Assert.assertEquals(1, antwoordBericht.getPersonen().size());
        Assert.assertEquals("123456789",
            antwoordBericht.getPersonen().get(0).getIdentificatienummers().getBurgerservicenummer().getWaarde());
    }

    @Test
    public void testAntwoordMetMeerderePersonen() {
        BevragingAntwoordBericht antwoordBericht = new BevragingAntwoordBericht(
            bouwOpvragenPersoonResultaat(bouwPersoon(2, "123456789"), bouwPersoon(4, "234567890")));

        Assert.assertNotNull(antwoordBericht.getPersonen());
        Assert.assertEquals(2, antwoordBericht.getPersonen().size());
    }


    @Test
    public void testAntwoordMetRelatieEnBetrokkenheden() {
        PersoonModel persoon1 = bouwPersoon(2, "123456789");
        PersoonModel persoon2 = bouwPersoon(4, "234567890");
        trouwPersonen(persoon1, persoon2);

        BevragingAntwoordBericht antwoordBericht = new BevragingAntwoordBericht(bouwOpvragenPersoonResultaat(persoon1));

        Assert.assertNotNull(antwoordBericht.getPersonen());
        Assert.assertEquals(1, antwoordBericht.getPersonen().size());

        PersoonModel testPersoon = antwoordBericht.getPersonen().get(0);
        valideerGevondenPersoonMetPartnerBetrokkenheid(testPersoon, "123456789", "234567890");
    }

    @Test
    public void testAntwoordMetRelatieEnBetrokkenhedenMetMeerderePersonen() {
        PersoonModel persoon1 = bouwPersoon(2, "123456789");
        PersoonModel persoon2 = bouwPersoon(4, "234567890");
        trouwPersonen(persoon1, persoon2);

        BevragingAntwoordBericht antwoordBericht =
            new BevragingAntwoordBericht(bouwOpvragenPersoonResultaat(persoon1, persoon2));

        Assert.assertNotNull(antwoordBericht.getPersonen());
        Assert.assertEquals(2, antwoordBericht.getPersonen().size());

        PersoonModel testPersoon1 = antwoordBericht.getPersonen().get(0);
        PersoonModel testPersoon2 = antwoordBericht.getPersonen().get(1);

        if (testPersoon1.getIdentificatienummers().getBurgerservicenummer().getWaarde().equals("123456789")) {
            valideerGevondenPersoonMetPartnerBetrokkenheid(testPersoon1, "123456789", "234567890");
            valideerGevondenPersoonMetPartnerBetrokkenheid(testPersoon2, "234567890", "123456789");
        } else {
            valideerGevondenPersoonMetPartnerBetrokkenheid(testPersoon1, "234567890", "123456789");
            valideerGevondenPersoonMetPartnerBetrokkenheid(testPersoon2, "123456789", "234567890");
        }
    }

    /**
     * Valideert of de opgegeven persoon een betrokkenheid heeft met een partner en controleert de eigen bsn en die
     * van de partner in de betrokkenheid. Ook wordt er gevalideerd dat de eigen betrokkenheid niet nog in de relatie
     * voorkomt als je de relatie van de eigen betrokkenheid opvraagt.
     *
     * @param persoon de persoon die gevalideerd moet worden.
     * @param eigenBsn de bsn van de controleren persoon.
     * @param partnerBsn de bsn van de partner.
     */
    private void valideerGevondenPersoonMetPartnerBetrokkenheid(final PersoonModel persoon, final String eigenBsn,
        final String partnerBsn)
    {
        Assert.assertEquals(eigenBsn, persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        Assert.assertEquals(1, persoon.getPartnerBetrokkenHeden().size());

        RelatieModel testRelatie = persoon.getBetrokkenheden().iterator().next().getRelatie();
        Assert.assertEquals(1, testRelatie.getBetrokkenheden().size());
        Assert.assertNotSame(
            "Eigen betrokkenheid uit de relatie gefilterd, resterende betrokkenheid dus andere dan eigen betrokkenheid",
            persoon.getBetrokkenheden().iterator().next(), testRelatie.getBetrokkenheden().iterator().next());
        PersoonModel testPartner = testRelatie.getBetrokkenheden().iterator().next().getBetrokkene();
        Assert.assertNotSame(persoon, testPartner.getId());
        Assert.assertEquals(partnerBsn, testPartner.getIdentificatienummers().getBurgerservicenummer().getWaarde());
    }

    /**
     * Bouwt een {@link OpvragenPersoonResultaat} instantie met de opgegeven gevonden personen.
     *
     * @param gevondenPersonen de personen die gevonden zijn.
     * @return een {@link OpvragenPersoonResultaat} instantie.
     */
    private OpvragenPersoonResultaat bouwOpvragenPersoonResultaat(final PersoonModel... gevondenPersonen) {
        OpvragenPersoonResultaat opvragenPersoonResultaat = new OpvragenPersoonResultaat(Collections.EMPTY_LIST);
        if (gevondenPersonen != null && gevondenPersonen.length > 0) {
            opvragenPersoonResultaat.setGevondenPersonen(new HashSet<PersoonModel>(Arrays.asList(gevondenPersonen)));
        } else {
            opvragenPersoonResultaat.setGevondenPersonen(Collections.EMPTY_SET);
        }
        return opvragenPersoonResultaat;
    }


    /**
     * Bouwt een {@link PersoonModel} instantie met opgegeven id en bsn.
     *
     * @param id het id van de persoon.
     * @param bsn het bsn van de persoon.
     * @return de persoon.
     */
    private PersoonModel bouwPersoon(final int id, final String bsn) {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoonBericht.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));

        PersoonModel persoon = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoon, "id", id);
        return persoon;
    }

    /**
     * Wijzigt de opgegeven persoon instanties zo dat de juiste betrokkenheden en relatie objecten worden toegevoegd
     * aan beide personen alsof ze getrouwd zijn.
     *
     * @param persoon1 persoon 1
     * @param persoon2 persoon 2
     */
    private void trouwPersonen(final PersoonModel persoon1, final PersoonModel persoon2) {
        RelatieBericht relatieBericht = new RelatieBericht();
        relatieBericht.setSoort(SoortRelatie.HUWELIJK);
        relatieBericht.setGegevens(new RelatieStandaardGroepBericht());
        relatieBericht.getGegevens().setDatumAanvang(new Datum(20120325));

        BetrokkenheidBericht betrokkenheidBericht1 = new BetrokkenheidBericht();
        betrokkenheidBericht1.setRol(SoortBetrokkenheid.PARTNER);
        BetrokkenheidBericht betrokkenheidBericht2 = new BetrokkenheidBericht();
        betrokkenheidBericht2.setRol(SoortBetrokkenheid.PARTNER);

        RelatieModel relatie = new RelatieModel(relatieBericht);
        BetrokkenheidModel betrokkenheid1 = new BetrokkenheidModel(betrokkenheidBericht1, persoon1, relatie);
        ReflectionTestUtils.setField(betrokkenheid1, "id", 21);
        BetrokkenheidModel betrokkenheid2 = new BetrokkenheidModel(betrokkenheidBericht2, persoon2, relatie);
        ReflectionTestUtils.setField(betrokkenheid2, "id", 22);
        Set<BetrokkenheidModel> betrokkenhedenRelatie =
            new HashSet<BetrokkenheidModel>(Arrays.asList(betrokkenheid1, betrokkenheid2));
        ReflectionTestUtils.setField(relatie, "betrokkenheden", betrokkenhedenRelatie);
        ReflectionTestUtils.setField(relatie, "id", 20);

        Set<BetrokkenheidModel> betrokkenheden1 = new HashSet<BetrokkenheidModel>(Arrays.asList(betrokkenheid1));
        ReflectionTestUtils.setField(persoon1, "betrokkenheden", betrokkenheden1);
        Set<BetrokkenheidModel> betrokkenheden2 = new HashSet<BetrokkenheidModel>(Arrays.asList(betrokkenheid2));
        ReflectionTestUtils.setField(persoon2, "betrokkenheden", betrokkenheden2);
    }
}
