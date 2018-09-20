/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.repository.PersoonOnderzoekSpringDataRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieHisVolledigRepository;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAfstammingBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingBinnengemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.service.ObjectSleutelPartijInvalideExceptie;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.service.ObjectSleutelVerlopenExceptie;
import nl.bzk.brp.webservice.business.service.OngeldigeObjectSleutelExceptie;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link BijhoudingRootObjectenOpHaalStap} en de in deze stap opgenomen methodes.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingRootObjectenOpHaalStapTest {

    private final PersoonHisVolledigImpl testPersoon =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testPersoonVader =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testPersoonMoeder =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testNietIngeschrevenMoeder =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

    @InjectMocks
    private final BijhoudingRootObjectenOpHaalStap stap = new BijhoudingRootObjectenOpHaalStap();

    @Mock
    private ObjectSleutelService                   objectSleutelService;
    @Mock
    private PersoonHisVolledigRepository           persoonHisVolledigRepository;
    @Mock
    private RelatieHisVolledigRepository           relatieHisVolledigRepository;
    @Mock
    private PersoonOnderzoekSpringDataRepository persoonOnderzoekSpringDataRepository;
    @Mock
    private PersoonRepository                      persoonRepository;

    private BijhoudingBerichtContext               berichtContext;

    @Before
    public void initialiseer() {
        ReflectionTestUtils.setField(stap, "objectSleutelService", objectSleutelService);
        ReflectionTestUtils.setField(stap, "persoonHisVolledigRepository", persoonHisVolledigRepository);
        ReflectionTestUtils.setField(stap, "relatieHisVolledigRepository", relatieHisVolledigRepository);
        ReflectionTestUtils.setField(stap, "persoonOnderzoekSpringDataRepository", persoonOnderzoekSpringDataRepository);

        ReflectionTestUtils.setField(testPersoon, "iD", 1);
        ReflectionTestUtils.setField(testPersoonVader, "iD", 2);
        ReflectionTestUtils.setField(testPersoonMoeder, "iD", 3);
        ReflectionTestUtils.setField(testNietIngeschrevenMoeder, "iD", 99);
        berichtContext = maakBerichtContext();
    }

    @Test
    public void testContextVerrijkingMetPersoon() throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();

        Mockito.when(objectSleutelService.bepaalPersoonId("persoonID", 1)).thenReturn(1);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(1)).thenReturn(testPersoon);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        final HisVolledigRootObject persoonHisVolledig =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("objectSleutel=persoonID");
        Assert.assertNotNull(persoonHisVolledig);
        Assert.assertTrue(persoonHisVolledig instanceof PersoonHisVolledigImpl);
        Assert.assertEquals(1, ((PersoonHisVolledigImpl) persoonHisVolledig).getID().intValue());
        Assert.assertEquals(1, haalBestaandeBijhoudingsObjectenUitContext(berichtContext).size());

        // Check ook of de soort persoon op het bericht nu correct is overgenomen.
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, ((PersoonBericht) bericht.getAdministratieveHandeling()
                            .getActies().iterator().next().getRootObject()).getSoort().getWaarde());
    }

    @Test
    public void testContextVerrijkingPersoonMetBetrokkenheden() throws OngeldigeObjectSleutelExceptie
    {
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

        Mockito.when(objectSleutelService.bepaalPersoonId("persoonID", 1)).thenReturn(
                1);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(1)).thenReturn(testPersoon);
        Mockito.when(objectSleutelService.bepaalPersoonId("OUDERID", 1)).thenReturn(2);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(2)).thenReturn(testPersoonVader);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        final HisVolledigRootObject persoonHisVolledigKind =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("objectSleutel=persoonID");
        final HisVolledigRootObject persoonHisVolledigVader =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("objectSleutel=OUDERID");
        Assert.assertNotNull(persoonHisVolledigKind);
        Assert.assertNotNull(persoonHisVolledigVader);
        Assert.assertTrue(persoonHisVolledigKind instanceof PersoonHisVolledigImpl);
        Assert.assertEquals(1, ((PersoonHisVolledigImpl) persoonHisVolledigKind).getID().intValue());
        Assert.assertEquals(2, haalBestaandeBijhoudingsObjectenUitContext(berichtContext).size());

        // Check ook of de soort persoon op het bericht nu correct is overgenomen.
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, ((PersoonBericht) bericht.getAdministratieveHandeling()
                .getActies().iterator().next().getRootObject()).getSoort().getWaarde());
    }

    @Test
    public void testVerrijkingSoortPersoonOpBericht() throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwGeboorteBijhoudingsBerichtVoorVerrijkingSoortPersoon("moederId");

        // NB: Slaat natuurlijk nergens op, een niet ingeschreven moeder, maar ok.
        Mockito.when(objectSleutelService.bepaalPersoonId("moederId", 1)).thenReturn(99);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(99)).thenReturn(testNietIngeschrevenMoeder);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        // De moeder was al niet ingeschrevene in de db, dus moet dat ook zijn in het bericht na verrijking.
        Assert.assertEquals(SoortPersoon.NIET_INGESCHREVENE, ((FamilierechtelijkeBetrekkingBericht) bericht
                .getAdministratieveHandeling().getActies().iterator().next().getRootObject()).getBetrokkenheden()
                .get(0).getPersoon().getSoort().getWaarde());
        // De vader is onbekend en heeft geen technische sleutel, dus nieuwe niet ingeschrevene na verrijking.
        Assert.assertEquals(SoortPersoon.NIET_INGESCHREVENE, ((FamilierechtelijkeBetrekkingBericht) bericht
                .getAdministratieveHandeling().getActies().iterator().next().getRootObject()).getBetrokkenheden()
                .get(1).getPersoon().getSoort().getWaarde());
        // Het kind is ingeschrevene, was al op bericht gezet, moet ongewijzigd zijn.
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, ((FamilierechtelijkeBetrekkingBericht) bericht
                .getAdministratieveHandeling().getActies().iterator().next().getRootObject()).getBetrokkenheden()
                .get(2).getPersoon().getSoort().getWaarde());
    }

    @Test
    public void testContextVerrijkingMetPersoonObvBurgerservicenummerNietToegestaan() throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(Regel.BRBY0014, resultaat.getMeldingen().iterator().next().getRegel());
        assertTrue(resultaat.bevatVerwerkingStoppendeMelding());
    }

    @Test
    public void testContextVerrijkingMetPersoonMetNietBestaandeId() throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwPersoonBijhoudingsBericht();

        Mockito.when(objectSleutelService.bepaalPersoonId("persoonID", 1)).thenReturn(1);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(1)).thenReturn(null);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().iterator().next().getSoort());
        Assert.assertEquals(Regel.BRBY0014, resultaat.getMeldingen().iterator().next().getRegel());
        Assert.assertNull(berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("persoonID"));
        Assert.assertEquals(0, haalBestaandeBijhoudingsObjectenUitContext(berichtContext).size());
    }

    @Test
    public void testContextVerrijkingMetRelatieZonderObjectSleutelOpRelatie() throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwRelatieBijhoudingsBericht(null);

        Mockito.when(objectSleutelService.bepaalPersoonId("vaderID", 1)).thenReturn(2);
        Mockito.when(objectSleutelService.bepaalPersoonId("moederID", 1)).thenReturn(3);

        Mockito.when(relatieHisVolledigRepository.leesGenormalizeerdModel(1)).thenReturn(bouwTestRelatie());
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(2)).thenReturn(testPersoonVader);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(3)).thenReturn(testPersoonMoeder);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        final HisVolledigRootObject relatieHisVolledig =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("relatieID");
        final HisVolledigRootObject persoonHisVolledigVader =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("objectSleutel=vaderID");
        final HisVolledigRootObject persoonHisVolledigMoeder =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("objectSleutel=moederID");

        Assert.assertNull(relatieHisVolledig);
        Assert.assertNotNull(persoonHisVolledigVader);
        Assert.assertNotNull(persoonHisVolledigMoeder);

        Assert.assertTrue(persoonHisVolledigVader instanceof PersoonHisVolledigImpl);
        Assert.assertTrue(persoonHisVolledigMoeder instanceof PersoonHisVolledigImpl);

        Assert.assertEquals(2, ((PersoonHisVolledigImpl) persoonHisVolledigVader).getID().intValue());
        Assert.assertEquals(3, ((PersoonHisVolledigImpl) persoonHisVolledigMoeder).getID().intValue());
        Assert.assertEquals(2, haalBestaandeBijhoudingsObjectenUitContext(berichtContext).size());
    }

    @Test
    public void testContextVerrijkingMetRelatieMetObjectSleutelOpRelatie() throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwRelatieBijhoudingsBericht("300");

        Mockito.when(objectSleutelService.bepaalPersoonId("vaderID", 1)).thenReturn(
                2);
        Mockito.when(objectSleutelService.bepaalPersoonId("moederID", 1)).thenReturn(
                3);

        Mockito.when(relatieHisVolledigRepository.leesGenormalizeerdModel(300)).thenReturn(bouwTestRelatie());
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(2)).thenReturn(testPersoonVader);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(3)).thenReturn(testPersoonMoeder);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        final HisVolledigRootObject relatieHisVolledig =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("300");
        final HisVolledigRootObject persoonHisVolledigVader =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("objectSleutel=vaderID");
        final HisVolledigRootObject persoonHisVolledigMoeder =
            berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("objectSleutel=moederID");

        Assert.assertNotNull(relatieHisVolledig);
        Assert.assertNotNull(persoonHisVolledigVader);
        Assert.assertNotNull(persoonHisVolledigMoeder);

        Assert.assertEquals(1, ((RelatieHisVolledigImpl) relatieHisVolledig).getID().intValue());
        Assert.assertEquals(3, haalBestaandeBijhoudingsObjectenUitContext(berichtContext).size());
    }

    @Test
    public void testContextVerrijkingMetRelatieMetOnbekendeObjectSleutelOpRelatie() throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwRelatieBijhoudingsBericht("relatieID");

        Mockito.when(objectSleutelService.bepaalPersoonId("vaderID", 1)).thenReturn(2);
        Mockito.when(objectSleutelService.bepaalPersoonId("moederID", 1)).thenReturn(3);

        Mockito.when(relatieHisVolledigRepository.leesGenormalizeerdModel(1)).thenReturn(bouwTestRelatie());
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(2)).thenReturn(testPersoonVader);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(3)).thenReturn(testPersoonMoeder);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().iterator().next().getSoort());
        Assert.assertEquals(Regel.BRBY0014, resultaat.getMeldingen().iterator().next().getRegel());
        Assert.assertNull(berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("relatieID"));
        Assert.assertEquals(2, haalBestaandeBijhoudingsObjectenUitContext(berichtContext).size());
    }

    @Test
    public void testContextVerrijkingMetRelatieMetNietBestaandeObjectSleutelOpRelatie() throws OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = bouwRelatieBijhoudingsBericht("relatieID");

        Mockito.when(objectSleutelService.bepaalPersoonId("vaderID", 1)).thenReturn(2);
        Mockito.when(objectSleutelService.bepaalPersoonId("moederID", 1)).thenReturn(3);

        Mockito.when(relatieHisVolledigRepository.leesGenormalizeerdModel(1)).thenReturn(null);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(2)).thenReturn(testPersoonVader);
        Mockito.when(persoonHisVolledigRepository.leesGenormalizeerdModel(3)).thenReturn(testPersoonMoeder);

        final Resultaat resultaat = stap.voerStapUit(bericht, berichtContext);

        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().iterator().next().getSoort());
        Assert.assertEquals(Regel.BRBY0014, resultaat.getMeldingen().iterator().next().getRegel());
        Assert.assertNull(berichtContext.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("relatieID"));
        Assert.assertEquals(2, haalBestaandeBijhoudingsObjectenUitContext(berichtContext).size());
    }

    @Test
    public void testVerrijkingVanIdentificerendeSleutel() throws OngeldigeObjectSleutelExceptie
    {
        RelatieBericht familieRechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        familieRechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        KindBericht kind = new KindBericht();
        kind.setPersoon(new PersoonBericht());
        kind.getPersoon().setCommunicatieID("com");
        familieRechtelijkeBetrekking.getBetrokkenheden().add(kind);

        OuderBericht ouder1 = new OuderBericht();
        ouder1.setPersoon(new PersoonBericht());
        ouder1.getPersoon().setObjectSleutel("123");
        Mockito.when(objectSleutelService.bepaalPersoonId("123", 1)).thenReturn(123);
        familieRechtelijkeBetrekking.getBetrokkenheden().add(ouder1);

        OuderBericht ouder2 = new OuderBericht();
        ouder2.setPersoon(new PersoonBericht());
        ouder2.getPersoon().setReferentieID("345");
        familieRechtelijkeBetrekking.getBetrokkenheden().add(ouder2);

        ActieRegistratieGeboorteBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setRootObject(familieRechtelijkeBetrekking);

        RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.<ActieBericht> asList(actie));

        ReflectionTestUtils.setField(berichtContext, "identificeerbareObjecten", new CommunicatieIdMap());
        stap.voerStapUit(bericht, berichtContext);

        Assert.assertEquals("com", kind.getPersoon().getCommunicatieID());
        Assert.assertEquals("objectSleutel=123", ouder1.getPersoon().getIdentificerendeSleutel());
        Assert.assertNull(ouder2.getPersoon().getIdentificerendeSleutel());
    }

    @Test
    public void testVerrijkingVanIdentificerendeSleutelMetIdentificerendeObjecten()
            throws ObjectSleutelVerlopenExceptie, ObjectSleutelPartijInvalideExceptie, OngeldigeObjectSleutelExceptie
    {
        RelatieBericht familieRechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        familieRechtelijkeBetrekking.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        KindBericht kind = new KindBericht();
        kind.setPersoon(new PersoonBericht());
        kind.getPersoon().setCommunicatieID("com");
        familieRechtelijkeBetrekking.getBetrokkenheden().add(kind);

        OuderBericht ouder1 = new OuderBericht();
        ouder1.setPersoon(new PersoonBericht());
        ouder1.getPersoon().setObjectSleutel("123");
        Mockito.when(objectSleutelService.bepaalPersoonId("123", 1)).thenReturn(123);
        familieRechtelijkeBetrekking.getBetrokkenheden().add(ouder1);

        OuderBericht ouder2 = new OuderBericht();
        ouder2.setPersoon(new PersoonBericht());

        final String referentieId = "345";

        ouder2.getPersoon().setReferentieID(referentieId);
        familieRechtelijkeBetrekking.getBetrokkenheden().add(ouder2);

        ActieRegistratieGeboorteBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setRootObject(familieRechtelijkeBetrekking);

        RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.<ActieBericht> asList(actie));

        CommunicatieIdMap identificeerbareObjecten = new CommunicatieIdMap();

        final List<BerichtIdentificeerbaar> berichtIdentificeerbaarLijst = new ArrayList<>();
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        berichtIdentificeerbaarLijst.add(persoonBericht);
        identificeerbareObjecten.put(referentieId, berichtIdentificeerbaarLijst);

        ReflectionTestUtils.setField(berichtContext, "identificeerbareObjecten", identificeerbareObjecten);
        stap.voerStapUit(bericht, berichtContext);

        Assert.assertEquals("com", kind.getPersoon().getCommunicatieID());
        Assert.assertEquals("objectSleutel=123", ouder1.getPersoon().getIdentificerendeSleutel());
        Assert.assertNull(ouder2.getPersoon().getIdentificerendeSleutel());
        Assert.assertEquals(SoortPersoon.NIET_INGESCHREVENE, ((PersoonBericht) berichtContext
                .getIdentificeerbareObjecten().get(referentieId).get(0)).getSoort().getWaarde());
    }

    /**
     * Instantieert en retourneert een valide bericht context t.b.v. tests.
     *
     * @return een valide bericht context.
     */
    private BijhoudingBerichtContext maakBerichtContext() {
        return new BijhoudingBerichtContext(new BerichtenIds(1L, 2L),
            TestPartijBuilder.maker().metCode(1).maak(), "123", null);
    }

    /**
     * Instantieert en retourneert een valide bericht verwerkings resultaat zonder meldingen.
     *
     * @return een valide bericht verwerkings resultaat.
     */
    private BijhoudingResultaat maakBerichtResultaat() {
        return new BijhoudingResultaat(new ArrayList<Melding>());
    }

    /**
     * Haalt de {@link Map} met bestaande bijhoudingsobjecten uit de context op en retourneert deze.
     *
     * @param berichtContext de bericht context.
     * @return een {@link Map} met bestaande bijhoudingsobjecten.
     */
    @SuppressWarnings("unchecked")
    private Map<String, HisVolledigRootObject> haalBestaandeBijhoudingsObjectenUitContext(
            final BerichtContext berichtContext)
    {
        return (Map<String, HisVolledigRootObject>) ReflectionTestUtils.getField(berichtContext,
                "bestaandeBijhoudingsRootObjecten");
    }

    /**
     * Instantieert en retourneert een valide persoon bijhoudings bericht t.b.v. tests.
     *
     * @return een valide bijhoudings bericht met een persoon als root object.
     */
    private BijhoudingsBericht bouwPersoonBijhoudingsBericht() throws ObjectSleutelVerlopenExceptie,
            ObjectSleutelPartijInvalideExceptie, OngeldigeObjectSleutelExceptie
    {
        final BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        ActieRegistratieAdresBericht registratieAdresBericht = new ActieRegistratieAdresBericht();
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoonBericht.setObjectSleutel("persoonID");
        persoonBericht.setCommunicatieID("COMID_OUS");
        Mockito.when(objectSleutelService.bepaalPersoonId("persoonID", 1)).thenReturn(123);
        registratieAdresBericht.setRootObject(persoonBericht);
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(registratieAdresBericht);
        return bericht;
    }

    /**
     * Instantieert en retourneert een valide geboorte bijhoudings bericht t.b.v. tests.
     * Niet helemaal consistent, maar genoeg voor de test.
     *
     * @param moederTechSleutel de tech.sleutel van de moeder
     * @return een valide geboorte bijhoudings bericht met een fam.recht.betr. als root object.
     */
    private BijhoudingsBericht bouwGeboorteBijhoudingsBerichtVoorVerrijkingSoortPersoon(
            final String moederTechSleutel)
    {
        final BijhoudingsBericht bericht = new RegistreerGeboorteBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        ActieRegistratieGeboorteBericht registratieBericht = new ActieRegistratieGeboorteBericht();
        FamilierechtelijkeBetrekkingBericht famRechtBetr = new FamilierechtelijkeBetrekkingBericht();
        famRechtBetr.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        PersoonBericht moederBericht = new PersoonBericht();
        moederBericht.setObjectSleutel(moederTechSleutel);
        OuderBericht moederOuder = new OuderBericht();
        moederOuder.setPersoon(moederBericht);
        famRechtBetr.getBetrokkenheden().add(moederOuder);

        PersoonBericht vaderBericht = new PersoonBericht();
        OuderBericht vaderOuder = new OuderBericht();
        vaderOuder.setPersoon(vaderBericht);
        famRechtBetr.getBetrokkenheden().add(vaderOuder);

        PersoonBericht kindBericht = new PersoonBericht();
        // Wordt al verrijkt in verrijkingsstap, dus dat simuleren we hier.
        kindBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        KindBericht kind = new KindBericht();
        kind.setPersoon(kindBericht);
        famRechtBetr.getBetrokkenheden().add(kind);

        registratieBericht.setRootObject(famRechtBetr);
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(registratieBericht);
        return bericht;
    }

    /**
     * Instantieert en retourneert een valide relatie bijhoudings bericht t.b.v. tests.
     *
     * @param technischeSleutelRelatie technische sleutel relatie
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
     * Instantieert en retourneert een {@link RelatieHisVolledig} instantie t.b.v. tests.
     *
     * @return een valide relatie instantie.
     */
    private RelatieHisVolledig bouwTestRelatie() {
        final RelatieHisVolledig relatie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
        ReflectionTestUtils.setField(relatie, "iD", 1);
        return relatie;
    }
}
