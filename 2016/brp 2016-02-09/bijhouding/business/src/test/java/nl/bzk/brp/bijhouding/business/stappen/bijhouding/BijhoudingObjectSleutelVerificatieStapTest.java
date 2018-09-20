/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAfstammingBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingBinnengemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerwijderingAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.webservice.business.service.ObjectSleutelPartijInvalideExceptie;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.service.ObjectSleutelVerlopenExceptie;
import nl.bzk.brp.webservice.business.service.ObjectSleutelVersleutelingFoutExceptie;
import nl.bzk.brp.webservice.business.service.OngeldigeObjectSleutelExceptie;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de {@link BijhoudingRootObjectenOpHaalStap} en de in deze stap opgenomen methodes.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingObjectSleutelVerificatieStapTest {

    private final PersoonHisVolledigImpl testPersoon =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testPersoonVader =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testPersoonMoeder =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testNietIngeschrevenMoeder =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

    @InjectMocks
    private final BijhoudingObjectSleutelVerificatieStap stap = new BijhoudingObjectSleutelVerificatieStap();

    @Mock
    private ObjectSleutelService objectSleutelService;

    private BijhoudingBerichtContext berichtContext;

    @Before
    public void initialiseer() {
        ReflectionTestUtils.setField(stap, "objectSleutelService", objectSleutelService);

        ReflectionTestUtils.setField(testPersoon, "iD", 1);
        ReflectionTestUtils.setField(testPersoonVader, "iD", 2);
        ReflectionTestUtils.setField(testPersoonMoeder, "iD", 3);
        ReflectionTestUtils.setField(testNietIngeschrevenMoeder, "iD", 99);
        berichtContext = maakBerichtContext();
    }

    @Test
    public void testVerificatieObjectSleutel()
        throws ObjectSleutelVerlopenExceptie, ObjectSleutelPartijInvalideExceptie, OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();

        when(objectSleutelService.bepaalPersoonId(
            "persoonID", 1)).thenReturn(1);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());

        Collection<Integer> lockingIds = berichtContext.getLockingIds();
        assertNotNull(lockingIds);
        assertEquals(1, lockingIds.size());
        assertTrue(lockingIds.contains(1));
    }

    @Test
    public void testVerificatieObjectsleutelMetOngeldigeObjectsleutel() throws OngeldigeObjectSleutelExceptie {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();

        when(objectSleutelService.bepaalPersoonId(
            "persoonID", 1)).thenThrow(ObjectSleutelVersleutelingFoutExceptie.class);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(Regel.BRAL0016, resultaat.getMeldingen().iterator().next().getRegel());
        assertEquals("COMID_OUS", resultaat.getMeldingen().iterator().next().getReferentieID());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerificatieVerlopenObjectSleutel() throws OngeldigeObjectSleutelExceptie {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();

        when(objectSleutelService.bepaalPersoonId(
            "persoonID", 1)).thenThrow(ObjectSleutelVerlopenExceptie.class);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(Regel.BRAL0014, resultaat.getMeldingen().iterator().next().getRegel());
        assertEquals("COMID_OUS", resultaat.getMeldingen().iterator().next().getReferentieID());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerificatieObjectSleutelMetOngeldigePartijInObjectSleutel() throws OngeldigeObjectSleutelExceptie {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();

        when(objectSleutelService.bepaalPersoonId(
            "persoonID", 1)).thenThrow(ObjectSleutelPartijInvalideExceptie.class);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(Regel.BRAL0015, resultaat.getMeldingen().iterator().next().getRegel());
        assertEquals("COMID_OUS", resultaat.getMeldingen().iterator().next().getReferentieID());
    }

    @Test
    public void testVerificatieObjectSleutelPersoonMetBetrokkenheden() throws OngeldigeObjectSleutelExceptie {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();

        final PersoonBericht persoonBericht =
                (PersoonBericht) bericht.getAdministratieveHandeling().getActies().iterator().next().getRootObject();

        BetrokkenheidBericht kindBetr = new KindBericht();
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                new FamilierechtelijkeBetrekkingBericht();
        kindBetr.setRelatie(familierechtelijkeBetrekkingBericht);
        persoonBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        persoonBericht.getBetrokkenheden().add(kindBetr);

        OuderBericht ouderBetr = new OuderBericht();
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(ouderBetr);
        PersoonBericht ouder = new PersoonBericht();
        ouder.setObjectSleutel("OUDERID");
        ouderBetr.setPersoon(ouder);

        when(objectSleutelService.bepaalPersoonId(
            "persoonID", 1)).thenReturn(1);
        when(objectSleutelService.bepaalPersoonId(
            "OUDERID", 1)).thenReturn(2);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());

        Collection<Integer> lockingIds = berichtContext.getLockingIds();

        assertNotNull(lockingIds);
        assertEquals(2, lockingIds.size());
        assertTrue(lockingIds.contains(1));
        assertTrue(lockingIds.contains(2));
    }

    private BijhoudingsBericht maakBerichtMetBsnIdentificatieVoorAdministratieveHandeling(
        final AdministratieveHandelingBericht administratieveHandeling)
        throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();
        administratieveHandeling.setActies(bericht.getAdministratieveHandeling().getActies());
        bericht.getStandaard().setAdministratieveHandeling(administratieveHandeling);

        PersoonBericht persoon =
                (PersoonBericht) bericht.getAdministratieveHandeling().getActies().get(0).getRootObject();
        persoon.setObjectSleutel((String) null);

        final BurgerservicenummerAttribuut bsn = new BurgerservicenummerAttribuut(123456789);
        PersoonIdentificatienummersGroepBericht idBericht = new PersoonIdentificatienummersGroepBericht();
        idBericht.setBurgerservicenummer(bsn);
        persoon.setIdentificatienummers(idBericht);
        idBericht.setCommunicatieID("communicatieId");

        PersoonHisVolledigImpl mockPersoon = mock(PersoonHisVolledigImpl.class);
        when(mockPersoon.getSoort()).thenReturn(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        when(mockPersoon.getID()).thenReturn(1);

        return bericht;
    }


    @Test
    public void testVerificatieObjectSleutelMetRelatieZonderObjectSleutelOpRelatie()
        throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwRelatieBijhoudingsBericht(null);

        when(objectSleutelService.bepaalPersoonId("vaderID", 1))
               .thenReturn(2);
        when(objectSleutelService.bepaalPersoonId("moederID", 1))
               .thenReturn(3);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());

        Collection<Integer> lockingIds = berichtContext.getLockingIds();

        assertNotNull(lockingIds);
        assertEquals(2, lockingIds.size());
        assertTrue(lockingIds.contains(2));
        assertTrue(lockingIds.contains(3));
    }

    @Test
    public void testVerificatieObjectSleutelMetRelatieMetObjectSleutelOpRelatie()
        throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwRelatieBijhoudingsBericht("300");

        when(objectSleutelService.bepaalPersoonId("vaderID", 1))
               .thenReturn(2);
        when(objectSleutelService.bepaalPersoonId("moederID", 1))
               .thenReturn(3);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());

        Collection<Integer> lockingIds = berichtContext.getLockingIds();

        assertNotNull(lockingIds);
        assertEquals(2, lockingIds.size());
        assertTrue(lockingIds.contains(2));
        assertTrue(lockingIds.contains(3));
    }

    /**
     * Instantieert en retourneert een valide bericht context t.b.v. tests.
     *
     * @return een valide bericht context.
     */
    private BijhoudingBerichtContext maakBerichtContext() {
        return new BijhoudingBerichtContext(
            new BerichtenIds(1L, 2L),
            TestPartijBuilder.maker().metCode(1).maak(),
            "123",
            null);
    }

    /**
     * Instantieert en retourneert een valide persoon bijhoudings bericht t.b.v. tests.
     *
     * @return een valide bijhoudings bericht met een persoon als root object.
     */
    private BijhoudingsBericht bouwPersoonBijhoudingsBericht()
        throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        ActieRegistratieAdresBericht registratieAdresBericht = new ActieRegistratieAdresBericht();
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoonBericht.setObjectSleutel("persoonID");
        persoonBericht.setCommunicatieID("COMID_OUS");
        when(objectSleutelService.bepaalPersoonId("persoonID", 1))
               .thenReturn(123);
        registratieAdresBericht.setRootObject(persoonBericht);
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(registratieAdresBericht);
        return bericht;
    }

     /**
     * Instantieert en retourneert een valide relatie bijhoudings bericht t.b.v. tests.
     *
     * @return een valide bijhoudings bericht met een relatie als root object.
     */
    private BijhoudingsBericht bouwRelatieBijhoudingsBericht(final String technischeSleutelRelatie) {
        final BijhoudingsBericht bericht = new RegistreerGeboorteBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        ActieRegistratieAfstammingBericht registratieAfstammingBericht = new ActieRegistratieAfstammingBericht();
        RelatieBericht familieRechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        familieRechtelijkeBetrekking.setObjectSleutel(technischeSleutelRelatie);
        familieRechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        PersoonBericht vader = new PersoonBericht();
        vader.setObjectSleutel("vaderID");
        OuderBericht vaderBetr = new OuderBericht();
        vaderBetr.setPersoon(vader);
        PersoonBericht moeder = new PersoonBericht();
        moeder.setObjectSleutel("moederID");
        OuderBericht moederBetr = new OuderBericht();
        moederBetr.setPersoon(moeder);
        PersoonBericht kind = new PersoonBericht();
        KindBericht kindBetr = new KindBericht();
        kindBetr.setPersoon(kind);
        familieRechtelijkeBetrekking.getBetrokkenheden().add(vaderBetr);
        familieRechtelijkeBetrekking.getBetrokkenheden().add(moederBetr);
        familieRechtelijkeBetrekking.getBetrokkenheden().add(kindBetr);
        registratieAfstammingBericht.setRootObject(familieRechtelijkeBetrekking);
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(registratieAfstammingBericht);
        return bericht;
    }

    /**
    * Instantieert en retourneert een valide relatie bijhoudings bericht t.b.v. tests.
    *
    * @return een valide bijhoudings bericht met een relatie als root object.
    */
    private BijhoudingsBericht bouwRelatieBijhoudingsBerichtMetBetrokkenhedenZonderPersoon(final String technischeSleutelRelatie) {
        final BijhoudingsBericht bericht = new RegistreerGeboorteBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        ActieRegistratieAfstammingBericht registratieAfstammingBericht = new ActieRegistratieAfstammingBericht();
        RelatieBericht familieRechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        familieRechtelijkeBetrekking.setObjectSleutel(technischeSleutelRelatie);
        familieRechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        OuderBericht vaderBetr = new OuderBericht();
        OuderBericht moederBetr = new OuderBericht();
        KindBericht kindBetr = new KindBericht();
        familieRechtelijkeBetrekking.getBetrokkenheden().add(vaderBetr);
        familieRechtelijkeBetrekking.getBetrokkenheden().add(moederBetr);
        familieRechtelijkeBetrekking.getBetrokkenheden().add(kindBetr);
        registratieAfstammingBericht.setRootObject(familieRechtelijkeBetrekking);
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(registratieAfstammingBericht);
        return bericht;
    }

    /**
     * Testen die zijn toegevoegd om de code coverage te verhogen.
     */
    @Test
    public void testVerificatieObjectSleutelMetRelatieMetbetrokkenhedenZonderPersonen()
        throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwRelatieBijhoudingsBerichtMetBetrokkenhedenZonderPersoon("300");

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());

        Collection<Integer> lockingIds = berichtContext.getLockingIds();

        assertNotNull(lockingIds);
        assertEquals(0, lockingIds.size());
    }

    @Test
    public void testVerificatieObjectSleutelMetBerichtZonderAdministratieveHandeling()
        throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwRelatieBijhoudingsBericht("300");
        ReflectionTestUtils.setField(bericht.getStandaard(), "administratieveHandeling", null);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());

        Collection<Integer> lockingIds = berichtContext.getLockingIds();

        assertNotNull(lockingIds);
        assertEquals(0, lockingIds.size());
    }

    @Test
    public void testVerificatieObjectSleutelMetPersoonBetrokkenheidPersoon()
        throws OngeldigeObjectSleutelExceptie
    {
        AdministratieveHandelingBericht administratieveHandeling = new HandelingVerwijderingAfnemerindicatieBericht();
        final BijhoudingsBericht bericht =
                maakBerichtMetBsnIdentificatieVoorAdministratieveHandeling(administratieveHandeling);


        PersoonBericht persoonBericht = (PersoonBericht) administratieveHandeling
                .getActies().get(0).getRootObject();
        persoonBericht.setIdentificerendeSleutel("identificerendeSleutel");
        persoonBericht.setObjectSleutel((String) null);

        persoonBericht.setObjectSleutel("persoonID");
        PersoonBericht partner = new PersoonBericht();
        PersoonIdentificatienummersGroepBericht identificatienummers = new PersoonIdentificatienummersGroepBericht();
        identificatienummers.setBurgerservicenummer(new BurgerservicenummerAttribuut(123));
        partner.setIdentificatienummers(identificatienummers);
        BetrokkenheidBericht betrokkenheidBericht = new PartnerBericht();
        betrokkenheidBericht.setPersoon(partner);
        persoonBericht.setBetrokkenheden(Arrays.asList(betrokkenheidBericht));

        when(objectSleutelService.bepaalPersoonId("persoonID", 1))
            .thenReturn(2);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testVerificatieObjectSleutelMetPersoonBetrokkenheidZonderPersoon()
        throws OngeldigeObjectSleutelExceptie
    {
        AdministratieveHandelingBericht administratieveHandeling = new HandelingVerwijderingAfnemerindicatieBericht();
        final BijhoudingsBericht bericht =
                maakBerichtMetBsnIdentificatieVoorAdministratieveHandeling(administratieveHandeling);


        PersoonBericht persoonBericht = (PersoonBericht) administratieveHandeling
                .getActies().get(0).getRootObject();
        persoonBericht.setIdentificerendeSleutel("identificerendeSleutel");
        persoonBericht.setObjectSleutel((String) null);

        persoonBericht.setObjectSleutel("persoonID");
        BetrokkenheidBericht betrokkenheidBericht = new PartnerBericht();
        persoonBericht.setBetrokkenheden(Arrays.asList(betrokkenheidBericht));

        when(objectSleutelService.bepaalPersoonId("persoonID", 1))
            .thenReturn(2);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testVerificatieObjectSleutelMetPersoonBetrokkenheidRelatie()
        throws OngeldigeObjectSleutelExceptie
    {
        AdministratieveHandelingBericht administratieveHandeling = new HandelingVerwijderingAfnemerindicatieBericht();
        final BijhoudingsBericht bericht =
                maakBerichtMetBsnIdentificatieVoorAdministratieveHandeling(administratieveHandeling);


        PersoonBericht persoonBericht = (PersoonBericht) administratieveHandeling
                .getActies().get(0).getRootObject();
        persoonBericht.setIdentificerendeSleutel("identificerendeSleutel");
        persoonBericht.setObjectSleutel((String) null);

        persoonBericht.setObjectSleutel("persoonID");
        PersoonBericht partner = new PersoonBericht();
        PersoonIdentificatienummersGroepBericht identificatienummers = new PersoonIdentificatienummersGroepBericht();
        identificatienummers.setBurgerservicenummer(new BurgerservicenummerAttribuut(123));
        partner.setIdentificatienummers(identificatienummers);

        RelatieBericht relatie = new HuwelijkBericht();
        BetrokkenheidBericht partnerBetrokkenheidBericht = new PartnerBericht();
        persoonBericht.setBetrokkenheden(Arrays.asList(partnerBetrokkenheidBericht));
        partnerBetrokkenheidBericht.setRelatie(relatie);

        when(objectSleutelService.bepaalPersoonId("persoonID", 1))
            .thenReturn(2);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testVerificatieObjectSleutelMetPersoonBetrokkenheidRelatieBetrokkenheid()
        throws OngeldigeObjectSleutelExceptie
    {
        AdministratieveHandelingBericht administratieveHandeling = new HandelingVerwijderingAfnemerindicatieBericht();
        final BijhoudingsBericht bericht =
                maakBerichtMetBsnIdentificatieVoorAdministratieveHandeling(administratieveHandeling);


        PersoonBericht persoonBericht = (PersoonBericht) administratieveHandeling
                .getActies().get(0).getRootObject();
        persoonBericht.setIdentificerendeSleutel("identificerendeSleutel");
        persoonBericht.setObjectSleutel((String) null);

        persoonBericht.setObjectSleutel("persoonID");
        PersoonBericht partner = new PersoonBericht();
        PersoonIdentificatienummersGroepBericht identificatienummers = new PersoonIdentificatienummersGroepBericht();
        identificatienummers.setBurgerservicenummer(new BurgerservicenummerAttribuut(123));
        partner.setIdentificatienummers(identificatienummers);

        RelatieBericht relatie = new HuwelijkBericht();
        BetrokkenheidBericht partnerBetrokkenheidBericht = new PartnerBericht();
        persoonBericht.setBetrokkenheden(Arrays.asList(partnerBetrokkenheidBericht));
        partnerBetrokkenheidBericht.setRelatie(relatie);

        BetrokkenheidBericht relatieBetrokkenheidBericht = new PartnerBericht();
        persoonBericht.setBetrokkenheden(Arrays.asList(partnerBetrokkenheidBericht));
        relatie.setBetrokkenheden(Arrays.asList(relatieBetrokkenheidBericht));

        when(objectSleutelService.bepaalPersoonId("persoonID", 1))
            .thenReturn(2);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertTrue(resultaat.getMeldingen().isEmpty());
    }
}
