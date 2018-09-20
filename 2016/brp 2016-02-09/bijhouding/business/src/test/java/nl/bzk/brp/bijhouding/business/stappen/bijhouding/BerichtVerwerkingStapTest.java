/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.actie.ActieFactory;
import nl.bzk.brp.bijhouding.business.actie.ActieUitvoerder;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.actie.BijhoudingRegelService;
import nl.bzk.brp.bijhouding.business.stappen.AbstractStapTest;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.bijhouding.business.util.Geldigheidsperiode;
import nl.bzk.brp.business.regels.NaActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.hismodelattribuutaccess.HisModelAttribuutAccessAdministratie;
import nl.bzk.brp.hismodelattribuutaccess.HisModelAttribuutAccessAdministratieImpl;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingBinnengemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test klasse waarin tests worden uitgevoerd ten behoeve van de functionaliteit geboden door de
 * {@link BerichtVerwerkingStap} klasse.
 */
@RunWith(MockitoJUnitRunner.class)
public class BerichtVerwerkingStapTest extends AbstractStapTest {

    private static final String PERSOON_TECHISCHE_SLEUTEL = "1234567890";

    @InjectMocks
    private BerichtVerwerkingStap berichtVerwerkingStap;

    @Mock
    private ActieFactory                       actieFactory;
    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;
    @Mock
    private VerantwoordingService              verantwoordingService;
    @Mock
    private ActieRepository                    actieRepository;
    @Mock
    private ActieUitvoerder                    actieUitvoerder;
    @Mock
    private MeldingFactory                     meldingFactory;
    @Mock
    private BijhoudingRegelService             bijhoudingRegelService;

    private HisModelAttribuutAccessAdministratie attribuutAccessAdministratie;


    @Before
    public void init() {
        attribuutAccessAdministratie = new HisModelAttribuutAccessAdministratieImpl();

        when(administratieveHandelingRepository
            .opslaanNieuwAdministratieveHandeling(any(AdministratieveHandelingBericht.class)))
            .thenReturn(bouwAdministratieveHandelingModel());
        when(actieFactory.getActieUitvoerder(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK,
            SoortActie.REGISTRATIE_ADRES)).thenReturn(actieUitvoerder);
        when(actieRepository.opslaanNieuwActie(any(ActieModel.class))).thenAnswer(
            new Answer<ActieModel>() {
                @Override
                public ActieModel answer(final InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    return (ActieModel) args[0];
                }
            }
        );
        when(actieUitvoerder.voerActieUit()).thenReturn(Collections.<Afleidingsregel>emptyList());

        ReflectionTestUtils.setField(berichtVerwerkingStap, "attribuutAccessAdministratie",
            attribuutAccessAdministratie);
    }

    @Test
    public void testVerdeelActiesNaarGeldigheidsperiode() {
        // Given
        ActieBericht actie1 = new ActieRegistratieGeboorteBericht();
        actie1.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20000000));

        ActieBericht actie3 = new ActieRegistratieNationaliteitBericht();
        actie3.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20010000));

        ActieBericht actie4 = new ActieRegistratieAdresBericht();
        actie4.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20010000));

        ActieBericht actie2 = new ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht();
        actie2.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20010000));
        actie2.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20020000));

        ActieBericht actie5 = new ActieRegistratieNaamgebruikBericht();
        actie5.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20040000));

        List<ActieBericht> acties = new ArrayList<ActieBericht>(Arrays.asList(actie1, actie3, actie4, actie2, actie5));

        // When
        Map<Geldigheidsperiode, List<ActieBericht>> gesplitsteActies = berichtVerwerkingStap.verdeelActiesNaarGesorteerdeGeldigheidsperiode(acties);

        // Then
        assertEquals(3, gesplitsteActies.size());
        Iterator<Geldigheidsperiode> iter = gesplitsteActies.keySet().iterator();
        assertEquals(actie1, gesplitsteActies.get(iter.next()).get(0));

        List<ActieBericht> actieGroep = gesplitsteActies.get(iter.next());
        assertEquals(actie3, actieGroep.get(0));
        assertEquals(actie4, actieGroep.get(1));
        assertEquals(actie2, actieGroep.get(2));
        assertEquals(actie5, gesplitsteActies.get(iter.next()).get(0));
    }

    @Test
    public void testOpslaanAdministratieveHandeling() {
        berichtVerwerkingStap.voerStapUit(bouwBericht(null, null), bouwBerichtContext());

        verify(administratieveHandelingRepository,
            times(1)).opslaanNieuwAdministratieveHandeling(any(AdministratieveHandeling.class));
    }

    @Test
    public void testOpslaanVerantwoording() {
        berichtVerwerkingStap.voerStapUit(bouwBericht(Arrays.asList(new DatumEvtDeelsOnbekendAttribuut(20130513)),
                Arrays.asList(new DatumEvtDeelsOnbekendAttribuut(20130613))),
            bouwBerichtContext()
        );
        verify(verantwoordingService).slaActieVerantwoordingenOp(
            anyMap(), any(AdministratieveHandelingBericht.class), any(BijhoudingBerichtContext.class)
        );
    }

    @Test
    public void testOphalenRegels() {
        final DatumEvtDeelsOnbekendAttribuut dag = new DatumEvtDeelsOnbekendAttribuut(20130513);
        final DatumEvtDeelsOnbekendAttribuut deg = null;
        berichtVerwerkingStap.voerStapUit(bouwBericht(Arrays.asList(dag), Arrays.asList(deg)),
            bouwBerichtContext());

        verify(bijhoudingRegelService, atLeastOnce()).getNaActieRegels(
            SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK, SoortActie.REGISTRATIE_ADRES);
    }

    @Test
    public void testGeenSplitsingUitvoerVanwegeZelfdeDagDeg() {
        final DatumEvtDeelsOnbekendAttribuut dag = new DatumEvtDeelsOnbekendAttribuut(20130513);
        final DatumEvtDeelsOnbekendAttribuut deg = null;
        berichtVerwerkingStap.voerStapUit(bouwBericht(Arrays.asList(dag, dag), Arrays.asList(deg, deg)),
            bouwBerichtContext());

        InOrder regelManagerInVolgorde = inOrder(bijhoudingRegelService);
        regelManagerInVolgorde.verify(bijhoudingRegelService, times(2)).getNaActieRegels(
            SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK, SoortActie.REGISTRATIE_ADRES);
    }

    @Test
    public void testMeldingenToevoegenAanResultaatVoorVoorActie() {
        final DatumEvtDeelsOnbekendAttribuut dag = new DatumEvtDeelsOnbekendAttribuut(20130513);
        final DatumEvtDeelsOnbekendAttribuut deg = null;

        // Mock twee 'VoorActieRegels' die, bij uitvoer, een falende entiteit retourneren.
        // Merk op dat de een en communicatie id geeft voor een element en de andere niet.
        final BerichtEntiteit falendeEntiteit1 = mock(BerichtEntiteit.class);
        when(falendeEntiteit1.getCommunicatieID()).thenReturn("entiteit1.id");
        when(falendeEntiteit1.getCommunicatieIdVoorElement(DatabaseObjectKern.PERSOON.getId())).thenReturn("pers1.id");
        final BerichtEntiteit falendeEntiteit2 = mock(BerichtEntiteit.class);
        when(falendeEntiteit2.getCommunicatieID()).thenReturn("entiteit2.id");
        when(falendeEntiteit2.getCommunicatieIdVoorElement(DatabaseObjectKern.PERSOON.getId())).thenReturn(null);

        final List<BerichtEntiteit> regelUitvoer1 = Arrays.asList(falendeEntiteit1);
        final VoorActieRegel voorActieRegel1 = mock(VoorActieRegel.class);
        when(voorActieRegel1.voerRegelUit(any(RootObject.class), any(BerichtRootObject.class),
            any(Actie.class), anyMap())).thenReturn(regelUitvoer1);
        when(voorActieRegel1.getRegel()).thenReturn(Regel.ALG0001);
        final List<BerichtEntiteit> regelUitvoer2 = Arrays.asList(falendeEntiteit2);
        final VoorActieRegel voorActieRegel2 = mock(VoorActieRegel.class);
        when(voorActieRegel2.voerRegelUit(any(RootObject.class), any(BerichtRootObject.class),
            any(Actie.class), anyMap())).thenReturn(regelUitvoer2);
        when(voorActieRegel2.getRegel()).thenReturn(Regel.ALG0002);

        // Mock een niet falende 'NaActieRegel'.
        final NaActieRegel naActieRegel = mock(NaActieRegel.class);

        // Zorg dat de regels netjes worden geretourneerd als de regelmanager wordt bevraagd voor ophalen van regels.
        configureerRegels(Arrays.asList(voorActieRegel1, voorActieRegel2), Arrays.asList(naActieRegel));

        // Zorg dat de juiste 'RegelParameters' worden geretourneerd na bevraging voor falende regel.
        final RegelParameters regelParameters1 = new RegelParameters(new MeldingtekstAttribuut("Test1"),
                SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, DatabaseObjectKern.PERSOON);
        final RegelParameters regelParameters2 = new RegelParameters(new MeldingtekstAttribuut("Test2"),
                SoortMelding.DEBLOKKEERBAAR, Regel.ALG0002, DatabaseObjectKern.PERSOON);
        when(bijhoudingRegelService.getRegelParametersVoorRegel(voorActieRegel1.getRegel())).thenReturn(regelParameters1);
        when(bijhoudingRegelService.getRegelParametersVoorRegel(voorActieRegel2.getRegel())).thenReturn(regelParameters2);

        when(meldingFactory.maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class))).thenReturn
            (ResultaatMelding.builder().build());

        // Voer de berichtverwerking uit.
        berichtVerwerkingStap.voerStapUit(bouwBericht(Arrays.asList(dag), Arrays.asList(deg)),
            bouwBerichtContext());

        // Controleer dat er een melding is toegevoegd aan het resultaat.
        verify(meldingFactory, times(2)).maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class));
    }

    @Test
    public void testMeldingenToevoegenAanResultaatMetParameter() {
        final DatumEvtDeelsOnbekendAttribuut dag = new DatumEvtDeelsOnbekendAttribuut(20130513);
        final DatumEvtDeelsOnbekendAttribuut deg = null;

        // Mock een 'VoorActieRegel' die, bij uitvoer, een falende entiteit retourneert.
        final PersoonAdresBericht falendeEntiteit = new PersoonAdresBericht();
        falendeEntiteit.setCommunicatieID("adres.id");
        falendeEntiteit.setStandaard(new PersoonAdresStandaardGroepBericht());
        falendeEntiteit.getStandaard().setGemeente(new GemeenteAttribuut(new Gemeente(
            new NaamEnumeratiewaardeAttribuut("TestGemeente"),
            new GemeenteCodeAttribuut(Short.valueOf("2")), null, null, null, null)));

        final List<BerichtEntiteit> regelUitvoer = Arrays.asList((BerichtEntiteit) falendeEntiteit);
        final VoorActieRegel voorActieRegel = mock(VoorActieRegel.class);
        when(voorActieRegel.voerRegelUit(any(RootObject.class), any(BerichtRootObject.class),
            any(Actie.class), anyMap())).thenReturn(regelUitvoer);
        when(voorActieRegel.getRegel()).thenReturn(Regel.BRBY0175);

        // Mock een niet falende 'NaActieRegel'.
        final NaActieRegel naActieRegel = mock(NaActieRegel.class);

        // Zorg dat de regels netjes worden geretourneerd als de regelmanager wordt bevraagd voor ophalen van regels.
        configureerRegels(Arrays.asList(voorActieRegel), Arrays.asList(naActieRegel));

        // Zorg dat de juiste 'RegelParameters' worden geretourneerd na bevraging voor falende regel.
        final RegelParameters regelParameters = new RegelParameters(
                new MeldingtekstAttribuut("Test van gemeente parameter: %s"),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0175, DatabaseObjectKern.PERSOON);
        when(bijhoudingRegelService.getRegelParametersVoorRegel(voorActieRegel.getRegel())).thenReturn(regelParameters);

        when(meldingFactory.maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class))).thenReturn
            (ResultaatMelding.builder().build());

        // Voer de berichtverwerking uit.
        berichtVerwerkingStap.voerStapUit(bouwBericht(Arrays.asList(dag), Arrays.asList(deg)),
                bouwBerichtContext());

        // Controleer dat er een melding is toegevoegd aan het resultaat.
        verify(meldingFactory).maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class));
    }

    @Test
    public void testMeldingenToevoegenAanResultaatVoorNaActie() {
        final DatumEvtDeelsOnbekendAttribuut dag = new DatumEvtDeelsOnbekendAttribuut(20130513);
        final DatumEvtDeelsOnbekendAttribuut deg = null;

        // Mock een niet falende 'VoorActieRegel'.
        final VoorActieRegel voorActieRegel = mock(VoorActieRegel.class);

        // Mock twee 'NaActieRegels' die, bij uitvoer, een falende entiteit retourneren.
        // Merk op dat de een en communicatie id geeft voor een element en de andere niet.
        final BerichtEntiteit falendeEntiteit1 = mock(BerichtEntiteit.class);
        when(falendeEntiteit1.getCommunicatieID()).thenReturn("entiteit1.id");
        when(falendeEntiteit1.getCommunicatieIdVoorElement(DatabaseObjectKern.PERSOON.getId())).thenReturn("pers1.id");
        final BerichtEntiteit falendeEntiteit2 = mock(BerichtEntiteit.class);
        when(falendeEntiteit2.getCommunicatieID()).thenReturn("entiteit2.id");
        when(falendeEntiteit2.getCommunicatieIdVoorElement(DatabaseObjectKern.PERSOON.getId())).thenReturn(null);

        final List<BerichtEntiteit> regelUitvoer1 = Arrays.asList(falendeEntiteit1);
        final NaActieRegel naActieRegel1 = mock(NaActieRegel.class);
        when(naActieRegel1.voerRegelUit(any(RootObject.class), any(BerichtRootObject.class)))
                .thenReturn(regelUitvoer1);
        when(naActieRegel1.getRegel()).thenReturn(Regel.ALG0001);
        final List<BerichtEntiteit> regelUitvoer2 = Arrays.asList(falendeEntiteit2);
        final NaActieRegel naActieRegel2 = mock(NaActieRegel.class);
        when(naActieRegel2.voerRegelUit(any(RootObject.class), any(BerichtRootObject.class)))
                .thenReturn(regelUitvoer2);
        when(naActieRegel2.getRegel()).thenReturn(Regel.ALG0002);

        // Zorg dat de regels netjes worden geretourneerd als de regelmanager wordt bevraagd voor ophalen van regels.
        configureerRegels(Arrays.asList(voorActieRegel), Arrays.asList(naActieRegel1, naActieRegel2));

        // Zorg dat de juiste 'RegelParameters' worden geretourneerd na bevraging voor falende regel.
        final RegelParameters regelParameters1 = new RegelParameters(new MeldingtekstAttribuut("Test1"),
                SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, DatabaseObjectKern.PERSOON);
        final RegelParameters regelParameters2 = new RegelParameters(new MeldingtekstAttribuut("Test2"),
                SoortMelding.DEBLOKKEERBAAR, Regel.ALG0002, DatabaseObjectKern.PERSOON);
        when(bijhoudingRegelService.getRegelParametersVoorRegel(naActieRegel1.getRegel())).thenReturn(regelParameters1);
        when(bijhoudingRegelService.getRegelParametersVoorRegel(naActieRegel2.getRegel())).thenReturn(regelParameters2);

        when(meldingFactory.maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class))).thenReturn
            (ResultaatMelding.builder().build());

        // Voer de berichtverwerking uit.
        berichtVerwerkingStap.voerStapUit(bouwBericht(Arrays.asList(dag), Arrays.asList(deg)),
            bouwBerichtContext());

        // Controleer dat er een melding is toegevoegd aan het resultaat.
        verify(meldingFactory, times(2)).maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class));
    }

    @Test
    public void testActieMetNietIngeschrevenen() {
        final DatumEvtDeelsOnbekendAttribuut dag = new DatumEvtDeelsOnbekendAttribuut(20130513);
        final DatumEvtDeelsOnbekendAttribuut deg = null;

        BijhoudingsBericht bijhoudingsBericht = bouwBericht(Arrays.asList(dag), Arrays.asList(deg));
        ActieBericht actie = bijhoudingsBericht.getAdministratieveHandeling().getActies().get(0);

        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setCommunicatieID("pers");
        persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoonBericht.getIdentificatienummers().setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        persoonBericht.getIdentificatienummers().setDatumAanvangGeldigheid(actie.getDatumAanvangGeldigheid());
        persoonBericht.getIdentificatienummers().setDatumEindeGeldigheid(actie.getDatumEindeGeldigheid());

        persoonBericht.setGeboorte(new PersoonGeboorteGroepBericht());

        persoonBericht.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoonBericht.getGeslachtsaanduiding().setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        persoonBericht.getGeslachtsaanduiding().setDatumAanvangGeldigheid(actie.getDatumAanvangGeldigheid());
        persoonBericht.getGeslachtsaanduiding().setDatumEindeGeldigheid(actie.getDatumEindeGeldigheid());

        persoonBericht.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoonBericht.getSamengesteldeNaam().setDatumTijdRegistratie(actie.getTijdstipRegistratie());
        persoonBericht.getSamengesteldeNaam().setDatumAanvangGeldigheid(actie.getDatumAanvangGeldigheid());
        persoonBericht.getSamengesteldeNaam().setDatumEindeGeldigheid(actie.getDatumEindeGeldigheid());

        actie.setRootObject(persoonBericht);

        BijhoudingBerichtContext berichtContext = bouwBerichtContext();

        berichtVerwerkingStap.voerStapUit(bijhoudingsBericht, berichtContext);

        assertEquals(1, berichtContext.getAangemaakteBijhoudingsRootObjecten().size());

        PersoonHisVolledig persHisVolledig =
                (PersoonHisVolledig) berichtContext
                        .getAangemaakteBijhoudingsRootObjectMetCommunicatieID(
                                persoonBericht.getCommunicatieID());

        assertEquals(1, persHisVolledig.getPersoonIdentificatienummersHistorie().getHistorie().size());
        assertEquals(1, persHisVolledig.getPersoonGeboorteHistorie().getHistorie().size());
        assertEquals(1, persHisVolledig.getPersoonGeslachtsaanduidingHistorie().getHistorie().size());
        assertEquals(1, persHisVolledig.getPersoonSamengesteldeNaamHistorie().getHistorie().size());
    }

    @Test
    public void testActieMetRelatieMetNietIngeschrevenen() {
        final DatumEvtDeelsOnbekendAttribuut dag = new DatumEvtDeelsOnbekendAttribuut(20130513);
        final DatumEvtDeelsOnbekendAttribuut deg = null;

        BijhoudingsBericht bijhoudingsBericht = bouwBericht(Arrays.asList(dag), Arrays.asList(deg));
        ActieBericht actie = bijhoudingsBericht.getAdministratieveHandeling().getActies().get(0);

        PersoonBericht persoonBericht1 = new PersoonBericht();
        persoonBericht1.setCommunicatieID("pers1");
        persoonBericht1.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        OuderBericht ouder1 = new OuderBericht();
        ouder1.setPersoon(persoonBericht1);

        PersoonBericht persoonBericht2 = new PersoonBericht();
        persoonBericht2.setCommunicatieID("pers2");
        persoonBericht2.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        OuderBericht ouder2 = new OuderBericht();
        ouder2.setPersoon(persoonBericht2);

        PersoonBericht persoonBericht3 = new PersoonBericht();
        persoonBericht3.setIdentificerendeSleutel("pers3");
        persoonBericht3.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        OuderBericht ouder3 = new OuderBericht();
        ouder3.setPersoon(persoonBericht3);

        FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        familie.setBetrokkenheden(Arrays.<BetrokkenheidBericht>asList(ouder1, ouder2));

        actie.setRootObject(familie);

        BijhoudingBerichtContext berichtContext = bouwBerichtContext();

        berichtVerwerkingStap.voerStapUit(bijhoudingsBericht, berichtContext);

        assertEquals(2, berichtContext.getAangemaakteBijhoudingsRootObjecten().size());
    }

    @Test
    public void testBepalingAfleidingsRegels() {
        final DatumEvtDeelsOnbekendAttribuut dag = new DatumEvtDeelsOnbekendAttribuut(20130513);
        final DatumEvtDeelsOnbekendAttribuut deg = null;

        final Afleidingsregel regelMetExtraAfleiding = mock(Afleidingsregel.class);
        final Afleidingsregel extraAfleidingsRegel = mock(Afleidingsregel.class);
        AfleidingResultaat afleidingResultaat = new AfleidingResultaat();
        afleidingResultaat.voegVervolgAfleidingToe(extraAfleidingsRegel);
        when(regelMetExtraAfleiding.leidAf()).thenReturn(afleidingResultaat);
        when(extraAfleidingsRegel.leidAf()).thenReturn(new AfleidingResultaat());

        final Afleidingsregel regelMetMelding = mock(Afleidingsregel.class);
        final ResultaatMelding melding = ResultaatMelding.builder().build();
        AfleidingResultaat afleidingResultaat2 = new AfleidingResultaat();
        afleidingResultaat.voegMeldingToe(melding);
        when(regelMetMelding.leidAf()).thenReturn(afleidingResultaat2);

        final Set<PersoonHisVolledigImpl> aangemaakteNietIngeschrevenen = new HashSet<>();
        aangemaakteNietIngeschrevenen.add(new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE)));
        final Afleidingsregel regelMetAangemaakteNietIngeschrevene = mock(Afleidingsregel.class);
        when(regelMetAangemaakteNietIngeschrevene.getExtraAangemaakteNietIngeschrevenen()).thenReturn(
                aangemaakteNietIngeschrevenen);
        when(regelMetAangemaakteNietIngeschrevene.leidAf()).thenReturn(new AfleidingResultaat());

        final Set<PersoonHisVolledigImpl> extraBijgehoudenPersonen = new HashSet<>();
        extraBijgehoudenPersonen.add(new PersoonHisVolledigImpl(
                new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE)));
        final Afleidingsregel regelMetExtraBijgehoudenPersoon = mock(Afleidingsregel.class);
        when(regelMetExtraBijgehoudenPersoon.getExtraBijgehoudenPersonen()).thenReturn(
            extraBijgehoudenPersonen);
        when(regelMetExtraBijgehoudenPersoon.leidAf()).thenReturn(new AfleidingResultaat());

        final List<Afleidingsregel> afleidingsregels = Arrays.asList(regelMetExtraAfleiding,
            regelMetAangemaakteNietIngeschrevene, regelMetExtraBijgehoudenPersoon);

        when(actieUitvoerder.voerActieUit()).thenReturn(afleidingsregels);

        final BijhoudingBerichtContext context = bouwBerichtContext();
        final Set<ResultaatMelding> meldingen =
            berichtVerwerkingStap.voerStapUit(bouwBericht(Collections.singletonList(dag), Collections.singletonList(deg)), context).getMeldingen();

        // Test dat de extra afleidingsregel wordt aangeroepen
        verify(extraAfleidingsRegel, times(1)).leidAf();

        // Test dat de melding aan het resultaat is toegevoegd
        assertEquals(1, meldingen.size());
        assertEquals(melding, meldingen.iterator().next());

        // Test dat de extra niet ingeschrevenen wel worden toegevoegd aan de context
        Assert.assertNotNull(context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen());
        assertEquals(1, context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen().size());
        Assert.assertSame(aangemaakteNietIngeschrevenen.iterator().next(),
            context.getNietInBerichtMaarWelAangemaakteNietIngeschrevenen().iterator().next());

        // Test dat de extra bijgehouden personen ook wordt toegevoegd aan de context (naast de persoon in het bericht)
        Assert.assertNotNull(context.getBijgehoudenPersonen());
        assertEquals(2, context.getBijgehoudenPersonen().size());
        boolean extraPersoonGevonden = false;
        for (PersoonHisVolledig extraPersoon : context.getBijgehoudenPersonen()) {
            if (extraBijgehoudenPersonen.contains(extraPersoon)) {
                extraPersoonGevonden = true;
                break;
            }
        }
        Assert.assertTrue(extraPersoonGevonden);
    }

    /**
     * Bouwt een vrijwel leeg bericht met een lege administratieve handeling.
     *
     * @param dags dags
     * @param degs degs
     * @return een bijhoudings bericht.
     */
    private BijhoudingsBericht bouwBericht(final List<DatumEvtDeelsOnbekendAttribuut> dags,
                                                   final List<DatumEvtDeelsOnbekendAttribuut> degs)
    {
        if (dags != null && degs != null && dags.size() != degs.size()) {
            throw new IllegalArgumentException("Dags en Degs hebben niet zelfde aantal entries.");
        }

        final BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());

        final List<ActieBericht> acties = new ArrayList<>();
        bericht.getAdministratieveHandeling().setActies(acties);
        if (dags != null && degs != null && !dags.isEmpty()) {
            for (int i = 0; i < dags.size(); i++) {
                acties.add(bouwActieBericht(dags.get(i), degs.get(i)));
            }
        }

        final BerichtParametersGroepBericht berichtParametersGroepBericht = new BerichtParametersGroepBericht();
        berichtParametersGroepBericht.setLeveringsautorisatieID("123");
        bericht.setParameters(berichtParametersGroepBericht);

        return bericht;
    }

    /**
     * Instantieert en retourneert een nieuwe {@link ActieBericht} instantie met als datum aanvang en datum einde
     * geldigheid de opgegeven datums. De datum einde geldigheid kan hierbij uiteraard <code>null</code> zijn.
     *
     * @param datumAanvang datum aanvang geldigheid van de actie.
     * @param datumEinde   datum einde geldigheid van de actie.
     * @return nieuwe actie isntantie.
     */
    private ActieBericht bouwActieBericht(final DatumEvtDeelsOnbekendAttribuut datumAanvang,
                                          final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        final ActieBericht actie = new ActieRegistratieAdresBericht();
        actie.setTijdstipRegistratie(DatumTijdAttribuut.nu());
        actie.setDatumAanvangGeldigheid(datumAanvang);
        actie.setDatumEindeGeldigheid(datumEinde);
        actie.setCommunicatieID("actie.id");

        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setObjectSleutel(PERSOON_TECHISCHE_SLEUTEL);
        actie.setRootObject(persoon);
        return actie;
    }

    /**
     * Instantieert en retourneert een nieuwe {@link AdministratieveHandelingModel} instantie met een ID van 2 en een
     * soort van {@link SoortAdministratieveHandeling#VERHUIZING_BINNENGEMEENTELIJK}.
     *
     * @return een {@link AdministratieveHandelingModel} instantie.
     */
    private AdministratieveHandelingModel bouwAdministratieveHandelingModel() {
        final AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK),
                null, null, null);
        ReflectionTestUtils.setField(administratieveHandeling, "iD", 2L);
        return administratieveHandeling;
    }

    @Override
    protected BijhoudingBerichtContext bouwBerichtContext() {
        final BijhoudingBerichtContext context = super.bouwBerichtContext();
        final PersoonHisVolledig persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        context.voegBestaandBijhoudingsRootObjectToe(PERSOON_TECHISCHE_SLEUTEL, persoon);
        return context;
    }

    private void configureerRegels(final List<VoorActieRegel> voorActieRegels, final List<NaActieRegel> naActieRegels) {
        when(bijhoudingRegelService.getVoorActieRegels(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK, SoortActie.REGISTRATIE_ADRES))
            .thenReturn(voorActieRegels);
        when(bijhoudingRegelService.getNaActieRegels(SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK, SoortActie.REGISTRATIE_ADRES))
            .thenReturn(naActieRegels);
    }

}
