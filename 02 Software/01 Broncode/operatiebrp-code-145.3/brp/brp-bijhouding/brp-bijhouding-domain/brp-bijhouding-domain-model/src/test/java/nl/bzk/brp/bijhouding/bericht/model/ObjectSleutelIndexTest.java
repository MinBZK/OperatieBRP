/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.BetrokkenheidRepository;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;
import nl.bzk.brp.bijhouding.dal.RelatieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Testen voor {@link ObjectSleutelIndex}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ObjectSleutelIndexTest {

    private static final String PERSOON_SLEUTEL_1 = "persoon.sleutel.1";
    private static final String PERSOON_SLEUTEL_1_VARIANT = "persoon.sleutel.1.variant";
    private static final String PERSOON_SLEUTEL_2 = "persoon.sleutel.2";
    private static final String PERSOON_SLEUTEL_3 = "persoon.sleutel.3";
    private static final String RELATIE_SLEUTEL_1 = "relatie.sleutel.1";
    private static final String RELATIE_SLEUTEL_2 = "relatie.sleutel.2";
    private static final String BETROKKENHEID_SLEUTEL_1 = "betrokkenheid.sleutel.2";
    private static final String ONGELDIGE_SLEUTEL = "ongeldige.sleutel";
    private static final String ONBEKENDE_SLEUTEL = "ongeldige.sleutel";

    private static final String PERSOON_COMMUNICATIE_ID_1 = "persoon.cid.1";
    private static final String PERSOON_COMMUNICATIE_ID_1_VARIANT = "persoon.cid.1.variant";
    private static final String PERSOON_COMMUNICATIE_ID_2 = "persoon.cid.2";
    private static final String PERSOON_COMMUNICATIE_ID_3 = "persoon.cid.3";
    private static final String RELATIE_COMMUNICATIE_ID_1 = "relatie.cid.1";
    private static final String RELATIE_COMMUNICATIE_ID_2 = "relatie.cid.2";

    private static final Persoon PERSOON_1 = new Persoon(SoortPersoon.INGESCHREVENE);
    private static final Persoon PERSOON_2 = new Persoon(SoortPersoon.INGESCHREVENE);
    private static final Persoon PERSOON_3 = new Persoon(SoortPersoon.INGESCHREVENE);
    private static final Relatie RELATIE_1 = new Relatie(SoortRelatie.HUWELIJK);
    private static final Relatie RELATIE_2 = new Relatie(SoortRelatie.HUWELIJK);
    private static final Betrokkenheid BETROKKENHEID_1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER,
            RELATIE_1);

    @Mock
    private ApplicationContext context;
    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private RelatieRepository relatieRepository;
    @Mock
    private BetrokkenheidRepository betrokkenheidRepository;
    @Mock
    private ObjectSleutelService objectSleutelService;

    private PersoonGegevensElement persoonElement1;
    private PersoonGegevensElement persoonElement1Variant;
    private PersoonGegevensElement persoonElement2;
    private PersoonGegevensElement persoonElement3;
    private PersoonGegevensElement ongeldigElement;
    private PersoonGegevensElement onbekendElement;
    private HuwelijkOfGpElement relatieElement1;
    private HuwelijkOfGpElement relatieElement2;

    @Before
    public void setup() throws OngeldigeObjectSleutelException {
        PERSOON_1.setId(1L);
        PERSOON_2.setId(2L);
        PERSOON_3.setId(3L);
        ReflectionTestUtils.setField(PERSOON_1, "lockVersie", 1L);
        ReflectionTestUtils.setField(PERSOON_2, "lockVersie", 1L);
        ReflectionTestUtils.setField(PERSOON_3, "lockVersie", 2L);
        RELATIE_1.setId(1L);
        RELATIE_2.setId(2L);
        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        when(context.getBean(PersoonRepository.class)).thenReturn(persoonRepository);
        when(context.getBean(RelatieRepository.class)).thenReturn(relatieRepository);
        when(context.getBean(ObjectSleutelService.class)).thenReturn(objectSleutelService);

        final ObjectSleutel persoonSleutel1 = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(1, 1);
        final ObjectSleutel persoonSleutel2 = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(2, 1);
        final ObjectSleutel persoonSleutel3Fout = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(3, 1);
        final ObjectSleutel persoonSleutel999 = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(999, 1);
        final ObjectSleutel relatieSleutel1 = new ObjectSleutelServiceImpl().maakRelatieObjectSleutel(1);
        final ObjectSleutel relatieSleutel2 = new ObjectSleutelServiceImpl().maakRelatieObjectSleutel(2);
        final ObjectSleutel betrokkenheidSleutel1 = new ObjectSleutelServiceImpl().maakBetrokkenheidObjectSleutel(2);

        when(objectSleutelService.maakPersoonObjectSleutel(PERSOON_SLEUTEL_1)).thenReturn(persoonSleutel1);

        when(objectSleutelService.maakPersoonObjectSleutel(PERSOON_SLEUTEL_1_VARIANT)).thenReturn(persoonSleutel1);
        when(objectSleutelService.maakPersoonObjectSleutel(PERSOON_SLEUTEL_2)).thenReturn(persoonSleutel2);
        when(objectSleutelService.maakPersoonObjectSleutel(PERSOON_SLEUTEL_3)).thenReturn(persoonSleutel3Fout);
        when(objectSleutelService.maakPersoonObjectSleutel(ONBEKENDE_SLEUTEL)).thenReturn(persoonSleutel999);
        when(objectSleutelService.maakPersoonObjectSleutel(ONGELDIGE_SLEUTEL)).thenThrow(new OngeldigeObjectSleutelException(Regel.ALG0001));
        when(objectSleutelService.maakRelatieObjectSleutel(RELATIE_SLEUTEL_1)).thenReturn(relatieSleutel1);
        when(objectSleutelService.maakRelatieObjectSleutel(RELATIE_SLEUTEL_2)).thenReturn(relatieSleutel2);
        when(objectSleutelService.maakBetrokkenheidObjectSleutel(BETROKKENHEID_SLEUTEL_1)).thenReturn(betrokkenheidSleutel1);

        when(persoonRepository.findById(1L)).thenReturn(PERSOON_1);
        when(persoonRepository.findById(2L)).thenReturn(PERSOON_2);
        when(persoonRepository.findById(3L)).thenReturn(PERSOON_3);
        when(relatieRepository.findById(1L)).thenReturn(RELATIE_1);
        when(relatieRepository.findById(2L)).thenReturn(RELATIE_2);
        when(betrokkenheidRepository.findById(1L)).thenReturn(BETROKKENHEID_1);

        persoonElement1 = maakPersoonElement(PERSOON_SLEUTEL_1, PERSOON_COMMUNICATIE_ID_1);
        persoonElement1Variant = maakPersoonElement(PERSOON_SLEUTEL_1_VARIANT, PERSOON_COMMUNICATIE_ID_1_VARIANT);
        persoonElement2 = maakPersoonElement(PERSOON_SLEUTEL_2, PERSOON_COMMUNICATIE_ID_2);
        persoonElement3 = maakPersoonElement(PERSOON_SLEUTEL_3, PERSOON_COMMUNICATIE_ID_3);
        ongeldigElement = maakPersoonElement(ONGELDIGE_SLEUTEL, "ci_ongeldige");
        onbekendElement = maakPersoonElement(ONBEKENDE_SLEUTEL, "ci_onbekend");
        relatieElement1 = maakHuwelijkOfGpElement(RELATIE_SLEUTEL_1, RELATIE_COMMUNICATIE_ID_1);
        relatieElement2 = maakHuwelijkOfGpElement(RELATIE_SLEUTEL_2, RELATIE_COMMUNICATIE_ID_2);
    }

    @Test
    public void testVoegElementToe() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.voegToe(persoonElement1);
        index.voegToe(persoonElement1Variant);
        index.voegToe(persoonElement2);
        index.voegToe(relatieElement1);
        index.voegToe(relatieElement2);
        // execute
        final List<MeldingElement> meldingen = index.initialize();
        // verify
        assertTrue(meldingen.isEmpty());
        assertTrue(index.isInitialized());
        assertEquals(4, index.size());
        assertEquals(PERSOON_1, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1).getDelegates().iterator().next());
        assertEquals(PERSOON_1, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1_VARIANT).getDelegates().iterator().next());
        assertEquals(PERSOON_2, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_2).getDelegates().iterator().next());
        assertEquals(RELATIE_1, index.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL_1).getDelegate());
        assertEquals(RELATIE_2, index.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL_2).getDelegate());

        assertNull(index.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, PERSOON_SLEUTEL_1));
        assertNull(index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, RELATIE_SLEUTEL_1));
        assertNull(index.getEntiteitVoorObjectSleutel(PersoonNationaliteit.class, RELATIE_SLEUTEL_1));
    }

    @Test
    public void testVervangEntiteitMetId() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.voegToe(persoonElement1);
        // execute
        final List<MeldingElement> meldingen = index.initialize();
        // verify
        assertTrue(meldingen.isEmpty());
        assertTrue(index.isInitialized());
        assertEquals(1, index.size());
        assertEquals(PERSOON_1, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1).getDelegates().iterator().next());
        // additional setup
        final BijhoudingPersoon nieuwePersoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON));
        index.vervangEntiteitMetId(BijhoudingPersoon.class, PERSOON_1.getId(), nieuwePersoon);
        assertNotEquals(PERSOON_1, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1).getDelegates().iterator().next());
        assertEquals(
                nieuwePersoon.getDelegates().iterator().next(),
                index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1).getDelegates().iterator().next());
    }

    @Test
    public void testVoegRootEntiteitToe() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.voegToe(persoonElement1);
        // execute
        final List<MeldingElement> meldingen = index.initialize();
        // verify
        assertTrue(meldingen.isEmpty());
        assertTrue(index.isInitialized());
        assertEquals(1, index.size());
        assertEquals(PERSOON_1, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1).getDelegates().iterator().next());
        // additional setup
        final Persoon nieuwePersoon = new Persoon(PERSOON_1.getSoortPersoon());
        nieuwePersoon.setId(PERSOON_1.getId());
        // additional execution
        index.voegToe(BijhoudingPersoon.decorate(nieuwePersoon));
        assertNotEquals(PERSOON_1, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1).getDelegates().iterator().next());
        assertEquals(nieuwePersoon, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1).getDelegates().iterator().next());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoegRootEntiteitToeFoutNotInitialized() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        final Persoon nieuwePersoon = new Persoon(PERSOON_1.getSoortPersoon());
        nieuwePersoon.setId(PERSOON_1.getId());
        // execute
        index.voegToe(BijhoudingPersoon.decorate(nieuwePersoon));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoegRootEntiteitToeFoutGeenId() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.initialize();
        final Persoon nieuwePersoon = new Persoon(PERSOON_1.getSoortPersoon());
        // execute
        index.voegToe(BijhoudingPersoon.decorate(nieuwePersoon));
    }

    @Test
    public void testIsInitialized() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.voegToe(persoonElement1);
        assertFalse(index.isInitialized());
        // execute
        final List<MeldingElement> meldingen = index.initialize();
        // verify
        assertTrue(meldingen.isEmpty());
        assertTrue(index.isInitialized());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsInitializedFailure() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        // execute
        index.initialize();
        // verify
        index.voegToe(persoonElement1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNietGeinitialiseerdeIndex() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        // execute
        assertFalse(index.isInitialized());
        assertNull(index.getEntiteitVoorObjectSleutel(Persoon.class, PERSOON_SLEUTEL_1));
    }

    @Test
    public void testOngeldigeSleutel() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.voegToe(ongeldigElement);
        // execute
        final List<MeldingElement> meldingen = index.initialize();
        // verify
        assertEquals(1, meldingen.size());
        assertEquals(ongeldigElement, meldingen.get(0).getReferentie());
    }

    @Test
    public void testVerlopenSleutel() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.voegToe(persoonElement3);
        // execute
        final List<MeldingElement> meldingen = index.initialize();
        // verify
        assertEquals(1, meldingen.size());
        assertEquals(persoonElement3, meldingen.get(0).getReferentie());
    }

    @Test
    public void testOnbekendeSleutel() {
        // setup
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.voegToe(onbekendElement);
        // execute
        final List<MeldingElement> meldingen = index.initialize();
        // verify
        assertNull(index.getEntiteitVoorObjectSleutel(Persoon.class, ONBEKENDE_SLEUTEL));
        assertEquals(1, meldingen.size());
        assertEquals(onbekendElement, meldingen.get(0).getReferentie());
    }

    private HuwelijkOfGpElement maakHuwelijkOfGpElement(final String objectSleutel, final String communicatieId) {
        final RelatieGroepElement
                relatieGroep =
                new RelatieGroepElement(new AbstractBmrGroep.AttributenBuilder().build(), null, null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null);
        return new HuwelijkElement(new AbstractBmrGroep.AttributenBuilder().communicatieId(communicatieId)
                .objectSleutel(objectSleutel)
                .objecttype("ot")
                .build(), relatieGroep, Collections.emptyList());
    }

    @Test
    public void testGetEntiteiten() {
        final ObjectSleutelIndex index = new ObjectSleutelIndexImpl();
        index.voegToe(persoonElement1);
        index.voegToe(relatieElement2);

        // execute
        final List<MeldingElement> meldingen = index.initialize();
        assertTrue(meldingen.isEmpty());

        final List<BijhoudingPersoon> entiteiten = index.getEntiteiten(BijhoudingPersoon.class);
        assertEquals(1, entiteiten.size());
        assertEquals(PERSOON_1, index.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL_1).getDelegates().iterator().next());

        assertTrue(index.getEntiteiten(PersoonAdres.class).isEmpty());
    }

    private PersoonGegevensElement maakPersoonElement(final String objectSleutel, final String communicatieId) {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        return builder.maakPersoonGegevensElement(new AbstractBmrGroep.AttributenBuilder().communicatieId(communicatieId)
                .objectSleutel(objectSleutel)
                .objecttype("ot")
                .build(), params);
    }
}
