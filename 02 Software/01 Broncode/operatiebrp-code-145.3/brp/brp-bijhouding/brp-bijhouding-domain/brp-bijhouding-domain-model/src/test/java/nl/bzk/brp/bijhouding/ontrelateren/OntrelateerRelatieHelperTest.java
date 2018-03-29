/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ontrelateren;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingRelatie;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * Testen voor {@link OntrelateerRelatieHelper}.
 */
@RunWith(MockitoJUnitRunner.class)
public class OntrelateerRelatieHelperTest {

    @Mock
    private ApplicationContext context;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    private BRPActie actie;
    private ActieBron actieBron;
    private Document document;
    private Timestamp nu;
    private Timestamp ontrelateerMoment;
    private Partij brpVoorziening;

    private long persoonIdTeller;
    private long relatieIdTeller;
    private long betrokkenheidIdTeller;

    @Before
    public void setup() {
        persoonIdTeller = 1;
        relatieIdTeller = 1;
        betrokkenheidIdTeller = 1;
        nu = new Timestamp(System.currentTimeMillis());
        ontrelateerMoment = new Timestamp(nu.getTime() - 1);
        brpVoorziening = new Partij("BRP Voorziening", Partij.PARTIJ_CODE_BRP);

        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(new Partij("IV", "000000"),
                SoortAdministratieveHandeling.GBA_INITIELE_VULLING, nu);
        actie = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(), nu);

        actieBron = new ActieBron(actie);
        actieBron.setRechtsgrondOmschrijving("rechtsgrond omschrijving");
        document = new Document(new SoortDocument("S", "soort"), actie.getPartij());
        actieBron.setDocument(document);
        actie.addActieBron(actieBron);
        document.addActieBron(actieBron);

        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);

        when(context.getBean(DynamischeStamtabelRepository.class)).thenReturn(dynamischeStamtabelRepository);
        when(dynamischeStamtabelRepository.getPartijByCode(Partij.PARTIJ_CODE_BRP)).thenReturn(brpVoorziening);
    }


    @Test
    public void testOntrelateerHuwelijk() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final int datumAanvangGeldigheidRelatie = 20170101;

        //setup
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final Onderzoek onderzoek = new Onderzoek(actie.getPartij(), hoofdPersoon);
        hoofdPersoon.addOnderzoek(onderzoek);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, onderzoek);
        final BijhoudingRelatie huwelijk = maakRelatie(SoortRelatie.HUWELIJK, datumAanvangGeldigheidRelatie, actie, onderzoek);
        final Betrokkenheid betrokkenheidHoofdPersoon = maakBetrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk, actie, null, null);
        final Betrokkenheid betrokkenheidGerelateerdePersoon = maakBetrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk, actie, null, onderzoek);
        hoofdPersoon.addBetrokkenheid(betrokkenheidHoofdPersoon);
        gerelateerdePersoon.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        huwelijk.addBetrokkenheid(betrokkenheidHoofdPersoon);
        huwelijk.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        huwelijk.setId(1L);
        long relatieHistorieId = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(huwelijk.getRelatieHistorieSet()).getId();
        //excute maak helper
        final OntrelateerRelatieHelper
                ontrelateerHelper =
                OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, huwelijk);
        //validate maak helper
        assertNotNull(ontrelateerHelper);
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(huwelijk.getRelatieHistorieSet()));
        assertNotNull(huwelijk.zoekRelatieHistorieVoorVoorkomenSleutel("" + relatieHistorieId).getId());
        assertEquals(6, onderzoek.getGegevenInOnderzoekSet().size());
        //execute ontrelateer
        ontrelateerHelper.ontrelateer(ontrelateerMoment);
        //valideer ontrelateer resultaat
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(huwelijk.getRelatieHistorieSet()));
        final BRPActie ontrelateerActie = huwelijk.getRelatieHistorieSet().iterator().next().getActieVerval();
        assertNotNull(ontrelateerActie);
        assertEquals(ontrelateerMoment, ontrelateerActie.getDatumTijdRegistratie());
        assertEquals(SoortActie.ONTRELATEREN, ontrelateerActie.getSoortActie());
        assertEquals(ontrelateerActie, betrokkenheidHoofdPersoon.getBetrokkenheidHistorieSet().iterator().next().getActieVerval());
        assertEquals(ontrelateerActie, betrokkenheidGerelateerdePersoon.getBetrokkenheidHistorieSet().iterator().next().getActieVerval());
        assertEquals(1, ontrelateerActie.getActieBronSet().size());
        assertNotSame(actieBron, ontrelateerActie.getActieBronSet().iterator().next());
        assertSame(document, ontrelateerActie.getActieBronSet().iterator().next().getDocument());
        assertEquals(actieBron.getRechtsgrondOmschrijving(), ontrelateerActie.getActieBronSet().iterator().next().getRechtsgrondOmschrijving());
        final BijhoudingRelatie relatieVoorVerdereVerwerking = ontrelateerHelper.getRelatieVoorVerdereVerwerking();
        assertTrue(ontrelateerHelper.isNieuweRelatieNodigVoorVerdereVerwerking());
        assertNotNull(relatieVoorVerdereVerwerking);
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieVoorVerdereVerwerking.getRelatieHistorieSet()));
        assertEquals(ontrelateerActie, relatieVoorVerdereVerwerking.getRelatieHistorieSet().iterator().next().getActieInhoud());
        assertEquals(2, hoofdPersoon.getBetrokkenheidSet(SoortBetrokkenheid.PARTNER).size());
        assertEquals(1, hoofdPersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.PARTNER).size());
        assertEquals(ontrelateerActie,
                hoofdPersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.PARTNER).iterator().next().getBetrokkenheidHistorieSet().iterator().next()
                        .getActieInhoud());
        assertEquals(relatieVoorVerdereVerwerking.getDelegate(),
                hoofdPersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.PARTNER).iterator().next().getRelatie());
        assertEquals(1, hoofdPersoon.getActuelePartners().size());
        final Betrokkenheid betrokkenheidPseudoPersoon = hoofdPersoon.getActuelePartners().iterator().next();
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidPseudoPersoon.getBetrokkenheidHistorieSet()));
        assertEquals(ontrelateerActie, betrokkenheidPseudoPersoon.getBetrokkenheidHistorieSet().iterator().next().getActieInhoud());
        final Persoon pseudoPersoon = betrokkenheidPseudoPersoon.getPersoon();
        assertNotNull(pseudoPersoon);
        assertEquals(SoortPersoon.PSEUDO_PERSOON, pseudoPersoon.getSoortPersoon());
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet()));
        assertEquals(datumAanvangGeldigheidRelatie,
                pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getDatumAanvangGeldigheid().intValue());
        assertNull(pseudoPersoon.getId());
        //valideer dat voorkomensleutel nu naar kopie relatie (daarvan is ID null) historie verwijst
        assertNotNull(relatieVoorVerdereVerwerking.zoekRelatieHistorieVoorVoorkomenSleutel("" + relatieHistorieId));
        assertNull(relatieVoorVerdereVerwerking.zoekRelatieHistorieVoorVoorkomenSleutel("" + relatieHistorieId).getId());
        //gegevens die extra in onderzoek moeten staan na ontrelateren
        assertEquals(12, onderzoek.getGegevenInOnderzoekSet().size());
        assertInOnderzoek(onderzoek, relatieVoorVerdereVerwerking.getDelegate(),
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieVoorVerdereVerwerking.getRelatieHistorieSet()),
                betrokkenheidPseudoPersoon,
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidPseudoPersoon.getBetrokkenheidHistorieSet()), pseudoPersoon,
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet()));
        //gegevens die niet in onderzoek mogen staan
        assertNietInOnderzoek(onderzoek, gerelateerdePersoon.getActuelePartners().iterator().next(),
                gerelateerdePersoon.getActuelePartners().iterator().next().getRelatie());
        //verifieer dat kopie voorkomen samengestelde naam gevonden kan worden met oud id
        final PersoonSamengesteldeNaamHistorie
                oudVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(gerelateerdePersoon.getPersoonSamengesteldeNaamHistorieSet());
        final PersoonSamengesteldeNaamHistorie
                nieuwVoorkomen =
                FormeleHistorieZonderVerantwoording
                        .getActueelHistorieVoorkomen(ontrelateerHelper.getGerelateerdePersoonVoorVerdereVerwerking().getPersoonSamengesteldeNaamHistorieSet());
        assertSame(nieuwVoorkomen, ontrelateerHelper.getGerelateerdePersoonVoorVerdereVerwerking()
                .zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(oudVoorkomen.getId()), oudVoorkomen.getClass()));
    }

    @Test
    public void ontrelateerOuderKindRelatie() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final int datumAanvangGeldigheidRelatie = 20170101;

        //setup
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final Onderzoek onderzoek = new Onderzoek(actie.getPartij(), hoofdPersoon);
        hoofdPersoon.addOnderzoek(onderzoek);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, onderzoek);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, onderzoek);
        final Betrokkenheid betrokkenheidHoofdPersoon = maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, actie, datumAanvangGeldigheidRelatie, onderzoek);
        final Betrokkenheid betrokkenheidGerelateerdePersoon = maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, actie, null, onderzoek);
        hoofdPersoon.addBetrokkenheid(betrokkenheidHoofdPersoon);
        gerelateerdePersoon.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        familie.addBetrokkenheid(betrokkenheidHoofdPersoon);
        familie.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);

        //excute maak helper
        final OntrelateerRelatieHelper
                ontrelateerHelper =
                OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
        //validate maak helper
        assertNotNull(ontrelateerHelper);
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(familie.getRelatieHistorieSet()));
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidHoofdPersoon.getBetrokkenheidHistorieSet()));
        assertEquals(10, onderzoek.getGegevenInOnderzoekSet().size());
        //execute ontrelateer
        ontrelateerHelper.ontrelateer(ontrelateerMoment);
        //valideer ontrelateer resultaat
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(familie.getRelatieHistorieSet()));
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidHoofdPersoon.getBetrokkenheidHistorieSet()));
        final BRPActie ontrelateerActie = betrokkenheidHoofdPersoon.getBetrokkenheidHistorieSet().iterator().next().getActieVerval();
        assertNotNull(ontrelateerActie);
        assertEquals(1, hoofdPersoon.getActueleKinderen().size());
        assertNotSame(betrokkenheidGerelateerdePersoon, hoofdPersoon.getActueleKinderen().iterator().next());
        final BijhoudingRelatie relatieVoorVerdereVerwerking = ontrelateerHelper.getRelatieVoorVerdereVerwerking();
        assertTrue(ontrelateerHelper.isNieuweRelatieNodigVoorVerdereVerwerking());
        assertNotSame(familie, relatieVoorVerdereVerwerking);
        assertEquals(1, hoofdPersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).size());
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(
                hoofdPersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).iterator().next().getBetrokkenheidOuderlijkGezagHistorieSet()));
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(
                hoofdPersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).iterator().next().getBetrokkenheidOuderHistorieSet()));
        assertEquals(Integer.valueOf(datumAanvangGeldigheidRelatie), FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(
                hoofdPersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).iterator().next().getBetrokkenheidOuderHistorieSet())
                .getDatumAanvangGeldigheid());
        //verify gemaakte pseudo persoon
        final Betrokkenheid betrokkenheidPseudoPersoon = hoofdPersoon.getActueleKinderen().iterator().next();
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidPseudoPersoon.getBetrokkenheidHistorieSet()));
        assertSame(ontrelateerActie, betrokkenheidPseudoPersoon.getBetrokkenheidHistorieSet().iterator().next().getActieInhoud());
        final Persoon pseudoPersoon = betrokkenheidPseudoPersoon.getPersoon();
        assertNotNull(pseudoPersoon);
        assertEquals(SoortPersoon.PSEUDO_PERSOON, pseudoPersoon.getSoortPersoon());
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet()));
        assertEquals(datumAanvangGeldigheidRelatie,
                pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getDatumAanvangGeldigheid().intValue());
        assertNull(pseudoPersoon.getId());
        //gegevens die in onderzoek moeten staan
        assertEquals(20, onderzoek.getGegevenInOnderzoekSet().size());
        final Betrokkenheid nieuweOuderBetrokkenheid = relatieVoorVerdereVerwerking.getBetrokkenheidSet(SoortBetrokkenheid.OUDER).iterator().next();
        final Betrokkenheid nieuweKindBetrokkenheid = relatieVoorVerdereVerwerking.getBetrokkenheidSet(SoortBetrokkenheid.KIND).iterator().next();
        assertInOnderzoek(onderzoek, relatieVoorVerdereVerwerking.getDelegate(),
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieVoorVerdereVerwerking.getRelatieHistorieSet()), nieuweOuderBetrokkenheid,
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuweOuderBetrokkenheid.getBetrokkenheidHistorieSet()),
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuweOuderBetrokkenheid.getBetrokkenheidOuderHistorieSet()),
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuweOuderBetrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet()),
                nieuweKindBetrokkenheid,
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuweKindBetrokkenheid.getBetrokkenheidHistorieSet()), pseudoPersoon,
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet()));
        //gegevens die niet in onderzoek mogen staan
        assertNietInOnderzoek(onderzoek, gerelateerdePersoon.getActueleOuders().iterator().next());
    }

    @Test
    public void ontrelateerKindOuderRelatie() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final int datumAanvangGeldigheidRelatie = 20170101;

        //setup
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final Onderzoek onderzoek = new Onderzoek(actie.getPartij(), hoofdPersoon);
        hoofdPersoon.addOnderzoek(onderzoek);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, onderzoek);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, onderzoek);
        final Betrokkenheid betrokkenheidHoofdPersoon = maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, actie, null, onderzoek);
        final Betrokkenheid
                betrokkenheidGerelateerdePersoon =
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, actie, datumAanvangGeldigheidRelatie, onderzoek);
        hoofdPersoon.addBetrokkenheid(betrokkenheidHoofdPersoon);
        gerelateerdePersoon.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        familie.addBetrokkenheid(betrokkenheidHoofdPersoon);
        familie.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);

        //excute maak helper
        final OntrelateerRelatieHelper
                ontrelateerHelper =
                OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
        //validate maak helper
        assertNotNull(ontrelateerHelper);
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(familie.getRelatieHistorieSet()));
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidGerelateerdePersoon.getBetrokkenheidHistorieSet()));
        assertEquals(10, onderzoek.getGegevenInOnderzoekSet().size());
        //execute ontrelateer
        ontrelateerHelper.ontrelateer(ontrelateerMoment);
        //valideer ontrelateer resultaat
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(familie.getRelatieHistorieSet()));
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidHoofdPersoon.getBetrokkenheidHistorieSet()));
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidGerelateerdePersoon.getBetrokkenheidHistorieSet()));
        final BRPActie ontrelateerActie = betrokkenheidGerelateerdePersoon.getBetrokkenheidHistorieSet().iterator().next().getActieVerval();
        assertNotNull(ontrelateerActie);
        assertEquals(1, hoofdPersoon.getActueleOuders().size());
        assertNotSame(betrokkenheidGerelateerdePersoon, hoofdPersoon.getActueleOuders().iterator().next());
        final BijhoudingRelatie relatieVoorVerdereVerwerking = ontrelateerHelper.getRelatieVoorVerdereVerwerking();
        assertFalse(ontrelateerHelper.isNieuweRelatieNodigVoorVerdereVerwerking());
        assertNull(relatieVoorVerdereVerwerking);
        assertEquals(1, gerelateerdePersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).size());
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(
                gerelateerdePersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).iterator().next().getBetrokkenheidOuderlijkGezagHistorieSet()));
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(
                gerelateerdePersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).iterator().next().getBetrokkenheidOuderHistorieSet()));
        assertEquals(Integer.valueOf(datumAanvangGeldigheidRelatie), FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(
                gerelateerdePersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).iterator().next().getBetrokkenheidOuderHistorieSet())
                .getDatumAanvangGeldigheid());
        //verify gemaakte pseudo persoon
        final Betrokkenheid betrokkenheidPseudoPersoon = hoofdPersoon.getActueleOuders().iterator().next();
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidPseudoPersoon.getBetrokkenheidHistorieSet()));
        assertSame(ontrelateerActie, betrokkenheidPseudoPersoon.getBetrokkenheidHistorieSet().iterator().next().getActieInhoud());
        final Persoon pseudoPersoon = betrokkenheidPseudoPersoon.getPersoon();
        assertNotNull(pseudoPersoon);
        assertEquals(SoortPersoon.PSEUDO_PERSOON, pseudoPersoon.getSoortPersoon());
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet()));
        assertEquals(datumAanvangGeldigheidRelatie,
                pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getDatumAanvangGeldigheid().intValue());
        assertNull(pseudoPersoon.getId());

        //valideer dat ingeschreven personen zijn gemarkeerd als bijgehouden
        valideerAfgeleidAdministratief(hoofdPersoon);
        valideerAfgeleidAdministratief(gerelateerdePersoon);
        //gegevens die in onderzoek moeten staan
        assertEquals(16, onderzoek.getGegevenInOnderzoekSet().size());
        final Betrokkenheid nieuweOuderBetrokkenheid = familie.getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER).iterator().next();
        assertInOnderzoek(onderzoek, nieuweOuderBetrokkenheid,
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuweOuderBetrokkenheid.getBetrokkenheidHistorieSet()),
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuweOuderBetrokkenheid.getBetrokkenheidOuderHistorieSet()),
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuweOuderBetrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet()),
                pseudoPersoon,
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(pseudoPersoon.getPersoonSamengesteldeNaamHistorieSet()));
        //gegevens die niet in onderzoek mogen staan
        assertNietInOnderzoek(onderzoek, gerelateerdePersoon.getActueleKinderen().iterator().next(),
                gerelateerdePersoon.getActueleKinderen().iterator().next().getRelatie());
    }

    private void valideerAfgeleidAdministratief(final BijhoudingPersoon persoon) {
        final PersoonAfgeleidAdministratiefHistorie
                afgeleidAdministratiefHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAfgeleidAdministratiefHistorieSet());
        assertNotNull(afgeleidAdministratiefHistorie);
        assertEquals(SoortAdministratieveHandeling.ONTRELATEREN, afgeleidAdministratiefHistorie.getAdministratieveHandeling().getSoort());
        assertEquals(ontrelateerMoment, afgeleidAdministratiefHistorie.getAdministratieveHandeling().getDatumTijdRegistratie());
        assertEquals(1, afgeleidAdministratiefHistorie.getSorteervolgorde());
    }

    @Test
    public void testFoutieveCombinatieSoortBetrokkenheid() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, null);
        final Betrokkenheid betrokkenheidHoofdPersoon = maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, actie, null, null);
        //foute betrokkenheid
        final Betrokkenheid betrokkenheidGerelateerdePersoon = maakBetrokkenheid(SoortBetrokkenheid.PARTNER, familie, actie, null, null);
        hoofdPersoon.addBetrokkenheid(betrokkenheidHoofdPersoon);
        gerelateerdePersoon.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        familie.addBetrokkenheid(betrokkenheidHoofdPersoon);
        familie.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("De combinatie van soort betrokkenheden (A:KIND met B:PARTNER) is niet mogelijk.", e.getMessage());
        }
    }

    @Test
    public void testFoutOntbrekendeBetrokkenheid() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, null);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Er kan geen actuele betrokkenheid gevonden worden tussen relatie (id:1) en persoon (id:1).", e.getMessage());
        }
    }

    @Test
    public void testFoutPersoonNietIngeschreven() {
        final String expectedFoutMelding = "Alleen relaties tussen ingeschreven personen kunnen ontrelateerd worden.";
        final int datumAanvangGeldigheidPersoon = 19800101;
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, null);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);
        hoofdPersoon.setSoortPersoon(SoortPersoon.PSEUDO_PERSOON);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedFoutMelding, e.getMessage());
        }
        hoofdPersoon.setSoortPersoon(SoortPersoon.INGESCHREVENE);
        gerelateerdePersoon.setSoortPersoon(SoortPersoon.PSEUDO_PERSOON);
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedFoutMelding, e.getMessage());
        }
    }

    @Test
    public void testFoutPersoonNietVerwerkbaar() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, null);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);
        hoofdPersoon.setBijhoudingSituatie(BijhoudingSituatie.OPNIEUW_INDIENEN);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("De hoofdpersoon met verwerkbaar zijn.", e.getMessage());
        }
        hoofdPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        gerelateerdePersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("De gerelateerde persoon mag niet verwerkbaar zijn.", e.getMessage());
        }
    }

    @Test
    public void testFoutHuwelijkZonderHistorie() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, null, actie, null);
        final Betrokkenheid betrokkenheidHoofdPersoon = maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, actie, null, null);
        final Betrokkenheid betrokkenheidGerelateerdePersoon = maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, actie, datumAanvangGeldigheidPersoon, null);
        hoofdPersoon.addBetrokkenheid(betrokkenheidHoofdPersoon);
        gerelateerdePersoon.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        familie.addBetrokkenheid(betrokkenheidHoofdPersoon);
        familie.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(familie.getRelatieHistorieSet()).setDatumAanvang(null);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Een HGP relatie moet een datum aanvang bevatten.", e.getMessage());
        }
        FormeleHistorie.laatActueelVoorkomenVervallen(familie.getRelatieHistorieSet(), actie);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Een relatie moet een actueel historie voorkomen bevatten.", e.getMessage());
        }
    }

    @Test
    public void testFoutObjectenZonderId() {
        final String expectedFoutMelding = "Alleen relaties tussen opgeslagen personen kunnen ontrelateerd worden.";
        final int datumAanvangGeldigheidPersoon = 19800101;
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, null);
        hoofdPersoon.setId(null);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedFoutMelding, e.getMessage());
        }
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(null);
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedFoutMelding, e.getMessage());
        }
        gerelateerdePersoon.setId(2L);
        familie.setId(null);
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Alleen opgeslagen relaties kunnen ontrelateerd worden.", e.getMessage());
        }
    }

    @Test
    public void testFoutOuderBetrokkenheidZonderOuderschap() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, null);
        final Betrokkenheid betrokkenheidHoofdPersoon = maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, actie, null, null);
        final Betrokkenheid betrokkenheidGerelateerdePersoon = maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, actie, datumAanvangGeldigheidPersoon, null);
        hoofdPersoon.addBetrokkenheid(betrokkenheidHoofdPersoon);
        gerelateerdePersoon.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        familie.addBetrokkenheid(betrokkenheidHoofdPersoon);
        familie.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);
        FormeleHistorie.laatActueelVoorkomenVervallen(betrokkenheidGerelateerdePersoon.getBetrokkenheidOuderHistorieSet(), actie);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Een ouder betrokkenheid moet een actueel ouderschap historie voorkomen bevatten.", e.getMessage());
        }
    }

    @Test
    public void testFoutOuderBetrokkenheidZonderDatumAanvang() {
        final int datumAanvangGeldigheidPersoon = 19800101;
        final BijhoudingPersoon hoofdPersoon = maakPersoon(BijhoudingSituatie.AUTOMATISCHE_FIAT, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingPersoon gerelateerdePersoon = maakPersoon(BijhoudingSituatie.OPNIEUW_INDIENEN, datumAanvangGeldigheidPersoon, actie, null);
        final BijhoudingRelatie familie = maakRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, null, actie, null);
        final Betrokkenheid betrokkenheidHoofdPersoon = maakBetrokkenheid(SoortBetrokkenheid.KIND, familie, actie, null, null);
        final Betrokkenheid betrokkenheidGerelateerdePersoon = maakBetrokkenheid(SoortBetrokkenheid.OUDER, familie, actie, datumAanvangGeldigheidPersoon, null);
        hoofdPersoon.addBetrokkenheid(betrokkenheidHoofdPersoon);
        gerelateerdePersoon.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        familie.addBetrokkenheid(betrokkenheidHoofdPersoon);
        familie.addBetrokkenheid(betrokkenheidGerelateerdePersoon);
        hoofdPersoon.setId(1L);
        gerelateerdePersoon.setId(2L);
        familie.setId(1L);
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidGerelateerdePersoon.getBetrokkenheidOuderHistorieSet())
                .setDatumAanvangGeldigheid(null);
        //execute & verify
        try {
            OntrelateerRelatieHelper.getInstance(new OntrelateerContext(), hoofdPersoon, gerelateerdePersoon, familie);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Een ouder betrokkenheid moet een datum aanvang ouderschap bevatten.", e.getMessage());
        }
    }

    private void assertNietInOnderzoek(final Onderzoek onderzoek, final Entiteit... gegevens) {
        final List<Entiteit> controleGegevensInOnderzoek = new ArrayList<>(Arrays.asList(gegevens));
        controleGegevensInOnderzoek.removeIf(gegeven -> bevatGegevenInOnderzoek(onderzoek, gegeven));
        assertTrue("(Sommige) gegevens staan in onderzoek.", gegevens.length == controleGegevensInOnderzoek.size());
    }

    private void assertInOnderzoek(final Onderzoek onderzoek, final Entiteit... gegevens) {
        final List<Entiteit> controleGegevensInOnderzoek = new ArrayList<>(Arrays.asList(gegevens));
        controleGegevensInOnderzoek.removeIf(gegeven -> bevatGegevenInOnderzoek(onderzoek, gegeven));
        assertTrue("Niet alle gegevens staan in onderzoek.", controleGegevensInOnderzoek.isEmpty());
    }

    private boolean bevatGegevenInOnderzoek(final Onderzoek onderzoek, final Entiteit gegeven) {
        for (final GegevenInOnderzoek gegevenInOnderzoek : onderzoek.getGegevenInOnderzoekSet()) {
            if (gegevenInOnderzoek.getEntiteitOfVoorkomen().equals(gegeven)) {
                return true;
            }
        }
        return false;
    }

    private Betrokkenheid maakBetrokkenheid(final SoortBetrokkenheid soortBetrokkenheid, final BijhoudingRelatie relatie, final BRPActie actie,
                                            final Integer aanvangOuderschap, final Onderzoek onderzoek) {
        final Betrokkenheid result = new Betrokkenheid(soortBetrokkenheid, relatie);
        result.setId(betrokkenheidIdTeller++);
        final BetrokkenheidHistorie betrokkenheidHistorie = new BetrokkenheidHistorie(result);
        betrokkenheidHistorie.setId(100 + betrokkenheidIdTeller);
        betrokkenheidHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        betrokkenheidHistorie.setActieInhoud(actie);
        result.addBetrokkenheidHistorie(betrokkenheidHistorie);
        if (onderzoek != null) {
            voegGegevenInOnderzoekToe(result, onderzoek, Element.BETROKKENHEID, actie);
            voegGegevenInOnderzoekToe(betrokkenheidHistorie, onderzoek, Element.BETROKKENHEID_ACTIEINHOUD, actie);
        }
        if (aanvangOuderschap != null) {
            final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(result);
            ouderHistorie.setId(200 + betrokkenheidIdTeller);
            ouderHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
            ouderHistorie.setActieInhoud(actie);
            ouderHistorie.setDatumAanvangGeldigheid(aanvangOuderschap);
            result.addBetrokkenheidOuderHistorie(ouderHistorie);
            final BetrokkenheidOuderlijkGezagHistorie ouderlijkGezagHistorie = new BetrokkenheidOuderlijkGezagHistorie(result, false);
            ouderlijkGezagHistorie.setId(300 + betrokkenheidIdTeller);
            ouderlijkGezagHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
            ouderlijkGezagHistorie.setActieInhoud(actie);
            ouderlijkGezagHistorie.setDatumAanvangGeldigheid(aanvangOuderschap);
            result.addBetrokkenheidOuderlijkGezagHistorie(ouderlijkGezagHistorie);
            if (onderzoek != null) {
                voegGegevenInOnderzoekToe(ouderHistorie, onderzoek, Element.OUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID, actie);
                voegGegevenInOnderzoekToe(ouderlijkGezagHistorie, onderzoek, Element.OUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG, actie);
            }
        }
        return result;
    }

    private BijhoudingRelatie maakRelatie(final SoortRelatie soortRelatie, final Integer datumAanvangGeldigheid, final BRPActie actie,
                                          final Onderzoek onderzoek) {
        final BijhoudingRelatie result = BijhoudingRelatie.decorate(new Relatie(soortRelatie));
        result.setId(relatieIdTeller++);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(result);
        relatieHistorie.setId(100 + relatieIdTeller);
        relatieHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        relatieHistorie.setActieInhoud(actie);
        relatieHistorie.setDatumAanvang(datumAanvangGeldigheid);
        result.addRelatieHistorie(relatieHistorie);

        if (onderzoek != null) {
            voegGegevenInOnderzoekToe(result.getDelegate(), onderzoek, Element.RELATIE, actie);
            voegGegevenInOnderzoekToe(relatieHistorie, onderzoek, Element.RELATIE_DATUMAANVANG, actie);
        }

        return result;
    }

    private BijhoudingPersoon maakPersoon(final BijhoudingSituatie bijhoudingSituatie, final int datumAanvangGeldigheid, final BRPActie actie,
                                          final Onderzoek onderzoek) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId(persoonIdTeller++);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(persoon, "Jansen", false, false);
        samengesteldeNaamHistorie.setId(100 + persoonIdTeller);
        samengesteldeNaamHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        samengesteldeNaamHistorie.setActieInhoud(actie);
        samengesteldeNaamHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);
        final BijhoudingPersoon result = BijhoudingPersoon.decorate(persoon);
        result.setBijhoudingSituatie(bijhoudingSituatie);

        if (onderzoek != null) {
            voegGegevenInOnderzoekToe(persoon, onderzoek, Element.PERSOON, actie);
            voegGegevenInOnderzoekToe(samengesteldeNaamHistorie, onderzoek, Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, actie);
        }

        return result;
    }

    private void voegGegevenInOnderzoekToe(final Entiteit entiteit, final Onderzoek onderzoek, final Element element, final BRPActie actie) {
        final GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek, element);
        gio.setEntiteitOfVoorkomen(entiteit);
        final GegevenInOnderzoekHistorie gioHistorie = new GegevenInOnderzoekHistorie(gio, actie);
        gioHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        gio.addGegevenInOnderzoekHistorie(gioHistorie);
        onderzoek.addGegevenInOnderzoek(gio);
    }
}
