/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingRelatie;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.DatumTijdElement;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.ontrelateren.OntrelateerRelatieHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * Testen voor {@link OntrelateerServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class OntrelateerServiceImplTest {

    @Mock
    private BijhoudingVerzoekBericht verzoekBericht;
    @Mock
    private ApplicationContext context;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    private BijhoudingsplanContext bijhoudingsplanContext;
    private BRPActie actie;
    private int datumAanvangPersoon;
    private OntrelateerService ontrelateerService;
    private Timestamp ontrelateerMoment;

    @Before
    public void setup() {
        bijhoudingsplanContext = new BijhoudingsplanContext(verzoekBericht);
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test partij", "000000"), SoortAdministratieveHandeling.GBA_INITIELE_VULLING,
                        new Timestamp(System.currentTimeMillis()));
        actie =
                new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(),
                        administratieveHandeling.getDatumTijdRegistratie());
        final Document document = new Document(new SoortDocument("testdoc", "Test Document"), actie.getPartij());
        final ActieBron actieBron = new ActieBron(actie);
        actieBron.setDocument(document);
        actie.addActieBron(actieBron);
        datumAanvangPersoon = 20000101;
        ontrelateerService = new OntrelateerServiceImpl();
        ontrelateerMoment = new Timestamp(System.currentTimeMillis());

        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);

        when(context.getBean(DynamischeStamtabelRepository.class)).thenReturn(dynamischeStamtabelRepository);
        when(dynamischeStamtabelRepository.getPartijByCode(Partij.PARTIJ_CODE_BRP)).thenReturn(new Partij("BRP Voorziening", Partij.PARTIJ_CODE_BRP));
        when(verzoekBericht.getTijdstipOntvangst()).thenReturn(new DatumTijdElement(ontrelateerMoment));
    }

    @Test
    public void testOntrelateerService() {
        //setup relaties
        final BijhoudingPersoon ik = maakPersoon(1, "123456789", "ik");
        final BijhoudingPersoon kees = maakPersoon(2, "123456790", "kees");
        final BijhoudingPersoon marie = maakPersoon(3, "123456791", "marie");
        final BijhoudingPersoon pietje = maakPersoon(4, "123456792", "pietje");
        final BijhoudingPersoon truusje = maakPersoon(5, "123456793", "truusje");
        final BijhoudingPersoon kim = maakPersoon(6, "123456794", "kim");
        final BijhoudingPersoon karel = maakPersoon(7, "123456795", "karel");
        final BijhoudingRelatie ouders = maakRelatie(1, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final BijhoudingRelatie kinderen1 = maakRelatie(2, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final BijhoudingRelatie kinderen2 = maakRelatie(3, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final BijhoudingRelatie huwelijk = maakRelatie(4, SoortRelatie.HUWELIJK);
        final BijhoudingRelatie geregistreerdPartnerschap = maakRelatie(5, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        final Betrokkenheid ikAlsKind = maakBetrokkenheid(1, SoortBetrokkenheid.KIND, ik, ouders);
        final Betrokkenheid ouder1 = maakBetrokkenheid(2, SoortBetrokkenheid.OUDER, kees, ouders);
        final Betrokkenheid ouder2 = maakBetrokkenheid(3, SoortBetrokkenheid.OUDER, marie, ouders);
        final Betrokkenheid ikAlsOuder1 = maakBetrokkenheid(4, SoortBetrokkenheid.OUDER, ik, kinderen1);
        final Betrokkenheid kind1 = maakBetrokkenheid(5, SoortBetrokkenheid.KIND, pietje, kinderen1);
        final Betrokkenheid ikAlsOuder2 = maakBetrokkenheid(6, SoortBetrokkenheid.OUDER, ik, kinderen2);
        final Betrokkenheid kind2 = maakBetrokkenheid(7, SoortBetrokkenheid.KIND, truusje, kinderen2);
        final Betrokkenheid ikAlsPartner1 = maakBetrokkenheid(8, SoortBetrokkenheid.PARTNER, ik, huwelijk);
        final Betrokkenheid partner1 = maakBetrokkenheid(9, SoortBetrokkenheid.PARTNER, kim, huwelijk);
        final Betrokkenheid ikAlsPartner2 = maakBetrokkenheid(10, SoortBetrokkenheid.PARTNER, ik, geregistreerdPartnerschap);
        final Betrokkenheid partner2 = maakBetrokkenheid(11, SoortBetrokkenheid.PARTNER, karel, geregistreerdPartnerschap);
        //setup personen toevoegen aan context
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(ik, BijhoudingSituatie.AUTOMATISCHE_FIAT);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(kees, BijhoudingSituatie.OPNIEUW_INDIENEN);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(marie, BijhoudingSituatie.OPNIEUW_INDIENEN);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(pietje, BijhoudingSituatie.OPNIEUW_INDIENEN);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(truusje, BijhoudingSituatie.OPNIEUW_INDIENEN);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(kim, BijhoudingSituatie.AUTOMATISCHE_FIAT);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(karel, BijhoudingSituatie.OPNIEUW_INDIENEN);
        //execute bepaalTeOntrelaterenRelaties
        final List<OntrelateerRelatieHelper> teOntrelaterenRelaties = ontrelateerService.bepaalTeOntrelaterenRelaties(bijhoudingsplanContext);
        //verify bepaalTeOntrelaterenRelaties
        assertEquals(5, teOntrelaterenRelaties.size());
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsKind, kees, ouder1, ouders));
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsKind, marie, ouder2, ouders));
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsOuder1, pietje, kind1, kinderen1));
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsOuder2, truusje, kind2, kinderen2));
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsPartner2, karel, partner2, geregistreerdPartnerschap));
        assertEquals(0, verzamelOntrelateerAhTijdstippen(ik).size());
        //execute ontrelateren
        ontrelateerService.ontrelateerRelaties(ontrelateerMoment, teOntrelaterenRelaties, bijhoudingsplanContext, verzoekBericht);
        //verify ontrelateren
        final Set<Timestamp> ahTijdstippen = verzamelOntrelateerAhTijdstippen(ik);
        assertEquals(4, ahTijdstippen.size());
        for (final Timestamp ahTijdstip : ahTijdstippen) {
            if (ontrelateerMoment.compareTo(ahTijdstip) < 0) {
                fail("Het tijdstip van de ontrelateer handeling moet kleiner zijn dan het tijdstip waarmee de ontrelateer service gestart wordt.");
            }
        }
        assertEquals(5, bijhoudingsplanContext.getPersonenDieNietVerwerktMaarWelOntrelateerdZijn().size());
        for (final BijhoudingPersoon persoonDieNietVerwerktMaarWelOpgeslagenMoetWorden : bijhoudingsplanContext
                .getPersonenDieNietVerwerktMaarWelOntrelateerdZijn()) {
            assertFalse(persoonDieNietVerwerktMaarWelOpgeslagenMoetWorden.getBijhoudingSituatie().isVerwerkbaar());
            assertEquals(1, verzamelOntrelateerAhTijdstippen(persoonDieNietVerwerktMaarWelOpgeslagenMoetWorden).size());
        }
        verify(verzoekBericht, times(8)).vervangEntiteitMetId(any(), any(), any());
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingRelatie.class, 2L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsOuder1, pietje, kind1, kinderen1).getRelatieVoorVerdereVerwerking());
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingRelatie.class, 3L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsOuder2, truusje, kind2, kinderen2).getRelatieVoorVerdereVerwerking());
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingRelatie.class, 5L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsPartner2, karel, partner2, geregistreerdPartnerschap)
                        .getRelatieVoorVerdereVerwerking());

        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingPersoon.class, 2L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsKind, kees, ouder1, ouders).getGerelateerdePersoonVoorVerdereVerwerking());
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingPersoon.class, 3L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsKind, marie, ouder2, ouders).getGerelateerdePersoonVoorVerdereVerwerking());
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingPersoon.class, 4L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsOuder1, pietje, kind1, kinderen1).getGerelateerdePersoonVoorVerdereVerwerking());
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingPersoon.class, 5L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsOuder2, truusje, kind2, kinderen2).getGerelateerdePersoonVoorVerdereVerwerking());
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingPersoon.class, 7L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, ik, ikAlsPartner2, karel, partner2, geregistreerdPartnerschap)
                        .getGerelateerdePersoonVoorVerdereVerwerking());
    }

    @Test
    public void testOntrelateer() {
        //setup relaties
        final BijhoudingPersoon ik = maakPersoon(1, "123456789", "ik");
        final BijhoudingPersoon karel = maakPersoon(7, "123456795", "karel");
        final BijhoudingRelatie geregistreerdPartnerschap = maakRelatie(5, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        maakBetrokkenheid(10, SoortBetrokkenheid.PARTNER, ik, geregistreerdPartnerschap);
        maakBetrokkenheid(11, SoortBetrokkenheid.PARTNER, karel, geregistreerdPartnerschap);
        //setup personen toevoegen aan context
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(ik, BijhoudingSituatie.AUTOMATISCHE_FIAT);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(karel, BijhoudingSituatie.OPNIEUW_INDIENEN);
        //execute ontrelateer
        ontrelateerService.ontrelateer(bijhoudingsplanContext, verzoekBericht);
        //verify ontrelateren
        final Set<Timestamp> ahTijdstippen = verzamelOntrelateerAhTijdstippen(ik);
        assertEquals(1, ahTijdstippen.size());
        assertEquals(1, bijhoudingsplanContext.getPersonenDieNietVerwerktMaarWelOntrelateerdZijn().size());
        verify(verzoekBericht, times(2)).vervangEntiteitMetId(any(), any(), any());
    }

    @Test
    public void testOntrelateerEnVervangPersoonVoorObjectSleutel() {
        //setup relaties
        final BijhoudingPersoon moeder = maakPersoon(1, "954614537", "moeder");
        final BijhoudingPersoon vader = maakPersoon(2, "676907337", "vader");
        final BijhoudingPersoon zoon = maakPersoon(3, "306706921", "zoon");
        final BijhoudingRelatie familie = maakRelatie(1, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid zoonBetrokkenheid = maakBetrokkenheid(1, SoortBetrokkenheid.KIND, zoon, familie);
        final Betrokkenheid moederBetrokkenheid = maakBetrokkenheid(2, SoortBetrokkenheid.OUDER, moeder, familie);
        final Betrokkenheid vaderBetrokkenheid = maakBetrokkenheid(3, SoortBetrokkenheid.OUDER, vader, familie);
        final BijhoudingRelatie huwelijk = maakRelatie(2, SoortRelatie.HUWELIJK);
        final Betrokkenheid manBetrokkenheid = maakBetrokkenheid(4, SoortBetrokkenheid.PARTNER, vader, huwelijk);
        final Betrokkenheid vrouwBetrokkenheid = maakBetrokkenheid(5, SoortBetrokkenheid.PARTNER, moeder, huwelijk);
        //setup personen toevoegen aan context
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(moeder, BijhoudingSituatie.AUTOMATISCHE_FIAT);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(zoon, BijhoudingSituatie.AUTOMATISCHE_FIAT);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(vader, BijhoudingSituatie.OPNIEUW_INDIENEN);
        //execute bepaalTeOntrelaterenRelaties
        final List<OntrelateerRelatieHelper> teOntrelaterenRelaties = ontrelateerService.bepaalTeOntrelaterenRelaties(bijhoudingsplanContext);
        //verify bepaalTeOntrelaterenRelaties
        assertEquals(2, teOntrelaterenRelaties.size());
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, moeder, vrouwBetrokkenheid, vader, manBetrokkenheid, huwelijk));
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, zoon, zoonBetrokkenheid, vader, vaderBetrokkenheid, familie));
        assertEquals(0, verzamelOntrelateerAhTijdstippen(moeder).size());
        assertEquals(0, verzamelOntrelateerAhTijdstippen(zoon).size());
        assertEquals(0, verzamelOntrelateerAhTijdstippen(vader).size());
        //execute ontrelateren
        ontrelateerService.ontrelateerRelaties(ontrelateerMoment, teOntrelaterenRelaties, bijhoudingsplanContext, verzoekBericht);
        //verify ontrelateren
        assertEquals(1, verzamelOntrelateerAhTijdstippen(moeder).size());
        assertEquals(1, verzamelOntrelateerAhTijdstippen(zoon).size());
        assertEquals(2, verzamelOntrelateerAhTijdstippen(vader).size());

        assertEquals(1, bijhoudingsplanContext.getPersonenDieNietVerwerktMaarWelOntrelateerdZijn().size());
        verify(verzoekBericht, times(2)).vervangEntiteitMetId(any(), any(), any());
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingRelatie.class, 2L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, moeder, vrouwBetrokkenheid, vader, manBetrokkenheid, huwelijk)
                        .getRelatieVoorVerdereVerwerking());
        //verify aantal vervangen personen in context (vader wordt twee keer ontrelateerd)
        verify(verzoekBericht).vervangEntiteitMetId(BijhoudingPersoon.class, 2L,
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, zoon, zoonBetrokkenheid, vader, vaderBetrokkenheid, familie)
                        .getGerelateerdePersoonVoorVerdereVerwerking());
        final BijhoudingPersoon
                gerelateerdePersoonVoorVerdereVerwerking =
                zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, zoon, zoonBetrokkenheid, vader, vaderBetrokkenheid, familie)
                        .getGerelateerdePersoonVoorVerdereVerwerking();
        assertNotNull(gerelateerdePersoonVoorVerdereVerwerking);
        assertEquals(2, gerelateerdePersoonVoorVerdereVerwerking.getDelegates().size());
        final Set<String> bsnLijstVanOntrelateerden = new HashSet<>();
        for (final Persoon persoon : gerelateerdePersoonVoorVerdereVerwerking.getDelegates()) {
            assertEquals("676907337",
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonIDHistorieSet()).getBurgerservicenummer());
            assertEquals(SoortPersoon.PSEUDO_PERSOON, persoon.getSoortPersoon());
        }
    }

    @Test
    public void testOntrelateerBeideOudersVoorKind() {
        //setup relaties
        final BijhoudingPersoon moeder = maakPersoon(1, "954614537", "moeder");
        final BijhoudingPersoon vader = maakPersoon(2, "676907337", "vader");
        final BijhoudingPersoon zoon = maakPersoon(3, "306706921", "zoon");
        final BijhoudingRelatie familie = maakRelatie(1, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid zoonBetrokkenheid = maakBetrokkenheid(1, SoortBetrokkenheid.KIND, zoon, familie);
        final Betrokkenheid moederBetrokkenheid = maakBetrokkenheid(2, SoortBetrokkenheid.OUDER, moeder, familie);
        final Betrokkenheid vaderBetrokkenheid = maakBetrokkenheid(3, SoortBetrokkenheid.OUDER, vader, familie);
        //setup personen toevoegen aan context
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(moeder, BijhoudingSituatie.AUTOMATISCHE_FIAT);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(zoon, BijhoudingSituatie.OPNIEUW_INDIENEN);
        bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(vader, BijhoudingSituatie.AUTOMATISCHE_FIAT);
        //execute bepaalTeOntrelaterenRelaties
        final List<OntrelateerRelatieHelper> teOntrelaterenRelaties = ontrelateerService.bepaalTeOntrelaterenRelaties(bijhoudingsplanContext);
        //verify bepaalTeOntrelaterenRelaties
        assertEquals(2, teOntrelaterenRelaties.size());
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, moeder, moederBetrokkenheid, zoon, zoonBetrokkenheid, familie));
        assertNotNull(zoekOntrelateerRelatieHelper(teOntrelaterenRelaties, vader, vaderBetrokkenheid, zoon, zoonBetrokkenheid, familie));
        assertEquals(0, verzamelOntrelateerAhTijdstippen(moeder).size());
        assertEquals(0, verzamelOntrelateerAhTijdstippen(zoon).size());
        assertEquals(0, verzamelOntrelateerAhTijdstippen(vader).size());
        //execute ontrelateren
        ontrelateerService.ontrelateerRelaties(ontrelateerMoment, teOntrelaterenRelaties, bijhoudingsplanContext, verzoekBericht);
        //verify ontrelateren
        assertEquals(1, verzamelOntrelateerAhTijdstippen(moeder).size());
        assertEquals(1, verzamelOntrelateerAhTijdstippen(zoon).size());
        assertEquals(1, verzamelOntrelateerAhTijdstippen(vader).size());

        assertEquals(1, bijhoudingsplanContext.getPersonenDieNietVerwerktMaarWelOntrelateerdZijn().size());
        assertEquals(2, zoon.getActueleOuders().size());
        final BRPActie
                actieInActueleOuderBetrokkenheid =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(zoon.getActueleOuders().iterator().next().getBetrokkenheidHistorieSet())
                        .getActieInhoud();
        assertNotNull(actieInActueleOuderBetrokkenheid);
        assertEquals(1, actieInActueleOuderBetrokkenheid.getActieBronSet().size());
        assertEquals(actie.getDocumentSet().iterator().next(), actieInActueleOuderBetrokkenheid.getDocumentSet().iterator().next());
    }

    private Set<Timestamp> verzamelOntrelateerAhTijdstippen(final Persoon persoon) {
        final Set<Timestamp> results = new HashSet<>();
        for (final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie : persoon.getPersoonAfgeleidAdministratiefHistorieSet()) {
            if (SoortAdministratieveHandeling.ONTRELATEREN.equals(afgeleidAdministratiefHistorie.getAdministratieveHandeling().getSoort())) {
                results.add(afgeleidAdministratiefHistorie.getAdministratieveHandeling().getDatumTijdRegistratie());
            }
        }
        return results;
    }

    private OntrelateerRelatieHelper zoekOntrelateerRelatieHelper(List<OntrelateerRelatieHelper> teOntrelaterenRelaties,
                                                                  final BijhoudingPersoon expectedHoofdPersoon,
                                                                  final Betrokkenheid expectedBetrokkenheidHoofdpersoon,
                                                                  final BijhoudingPersoon expectedGerelateerdePersoon,
                                                                  final Betrokkenheid expectedGerelateerdePersoonBetrokkenheid,
                                                                  final BijhoudingRelatie expectedRelatie) {
        for (final OntrelateerRelatieHelper ontrelateerRelatieHelper : teOntrelaterenRelaties) {
            if (same(expectedHoofdPersoon, ontrelateerRelatieHelper.getHoofdPersoon()) && same(expectedBetrokkenheidHoofdpersoon,
                    ontrelateerRelatieHelper.getBetrokkenheidHoofdPersoon()) && same(expectedGerelateerdePersoon,
                    ontrelateerRelatieHelper.getGerelateerdePersoon()) && same(expectedGerelateerdePersoonBetrokkenheid,
                    ontrelateerRelatieHelper.getBetrokkenheidGerelateerdePersoon()) && same(expectedRelatie.getDelegate(),
                    ontrelateerRelatieHelper.getRelatie().getDelegate())) {
                return ontrelateerRelatieHelper;
            }
        }
        return null;
    }

    private boolean same(final Object object1, final Object object2) {
        return object1 == object2;
    }

    private BijhoudingRelatie maakRelatie(final long id, final SoortRelatie soortRelatie) {
        final Relatie result = new Relatie(soortRelatie);
        result.setId(id);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(result);
        relatieHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        relatieHistorie.setActieInhoud(actie);
        relatieHistorie.setDatumAanvang(datumAanvangPersoon);
        result.addRelatieHistorie(relatieHistorie);
        return BijhoudingRelatie.decorate(result);
    }

    private Betrokkenheid maakBetrokkenheid(final long id, final SoortBetrokkenheid soortBetrokkenheid, final Persoon persoon, final Relatie relatie) {
        final Betrokkenheid result = new Betrokkenheid(soortBetrokkenheid, relatie);
        result.setId(id);
        final BetrokkenheidHistorie betrokkenheidHistorie = new BetrokkenheidHistorie(result);
        betrokkenheidHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        betrokkenheidHistorie.setActieInhoud(actie);
        result.addBetrokkenheidHistorie(betrokkenheidHistorie);
        if (SoortBetrokkenheid.OUDER.equals(soortBetrokkenheid)) {
            final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(result);
            ouderHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
            ouderHistorie.setActieInhoud(actie);
            ouderHistorie.setDatumAanvangGeldigheid(datumAanvangPersoon);
            result.addBetrokkenheidOuderHistorie(ouderHistorie);
        }
        persoon.addBetrokkenheid(result);
        relatie.addBetrokkenheid(result);
        return result;
    }

    private BijhoudingPersoon maakPersoon(final long id, final String bsn, final String naam) {
        final Persoon result = new Persoon(SoortPersoon.INGESCHREVENE);
        result.setId(id);
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(result);
        idHistorie.setId(1L);
        idHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        idHistorie.setActieInhoud(actie);
        idHistorie.setBurgerservicenummer(bsn);
        result.addPersoonIDHistorie(idHistorie);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(result, naam, false, false);
        samengesteldeNaamHistorie.setId(1L);
        samengesteldeNaamHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        samengesteldeNaamHistorie.setActieInhoud(actie);
        result.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);
        return BijhoudingPersoon.decorate(result);
    }
}
