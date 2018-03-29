/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
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
 * Deze class test het gebruik van ObjectSleutels in bijhoudingberichten.
 */
@RunWith(MockitoJUnitRunner.class)
public class ObjectSleutelTest {

    @Mock
    private ApplicationContext context;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    @Mock
    private PersoonRepository persoonRepository;
    @Mock
    private BetrokkenheidRepository betrokkenheidRepository;
    @Mock
    private RelatieRepository relatieRepository;
    @Mock
    private ObjectSleutelService objectSleutelService;

    private Persoon persoon;
    private Betrokkenheid betrokkenheid;
    private Relatie relatie;

    @Before
    public void setup() throws OngeldigeObjectSleutelException {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId(1L);
        ReflectionTestUtils.setField(persoon, "lockVersie", 1L);
        relatie = new Relatie(SoortRelatie.HUWELIJK);
        relatie.setId(1L);
        betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        betrokkenheid.setId(121212L);
        relatie.addBetrokkenheid(betrokkenheid);

        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        when(context.getBean(DynamischeStamtabelRepository.class)).thenReturn(dynamischeStamtabelRepository);
        when(context.getBean(ObjectSleutelService.class)).thenReturn(objectSleutelService);
        when(context.getBean(PersoonRepository.class)).thenReturn(persoonRepository);
        when(context.getBean(BetrokkenheidRepository.class)).thenReturn(betrokkenheidRepository);
        when(context.getBean(RelatieRepository.class)).thenReturn(relatieRepository);

        final ObjectSleutel persoonObjectSleutel = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(persoon.getId(), 1);
        final ObjectSleutel betrokkenheidObjectSleutel = new ObjectSleutelServiceImpl().maakBetrokkenheidObjectSleutel(betrokkenheid.getId());
        final ObjectSleutel relatieObjectSleutel = new ObjectSleutelServiceImpl().maakRelatieObjectSleutel(relatie.getId());
        when(objectSleutelService.maakPersoonObjectSleutel("212121")).thenReturn(persoonObjectSleutel);
        when(objectSleutelService.maakBetrokkenheidObjectSleutel("121212")).thenReturn(betrokkenheidObjectSleutel);
        when(objectSleutelService.maakRelatieObjectSleutel("1")).thenReturn(relatieObjectSleutel);
        when(persoonRepository.findById((long) persoon.getId())).thenReturn(persoon);
        when(betrokkenheidRepository.findById((long) betrokkenheid.getId())).thenReturn(betrokkenheid);
        when(relatieRepository.findById((long) relatie.getId())).thenReturn(relatie);
    }

    @Test
    @Bedrijfsregel(Regel.R2511)
    public void testSucces() {
        //setup
        final BijhoudingVerzoekBericht bericht = VerzoekBerichtBuilder.maakSuccesEindeHuwelijkInNederlandBericht();
        //execute
        final List<MeldingElement> meldingen = bericht.laadEntiteitenVoorObjectSleutels();
        //valideer
        assertEquals(0, meldingen.size());
    }

    @Test
    @Bedrijfsregel(Regel.R2511)
    public void testRegel2511() {
        //setup
        relatie.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final BijhoudingVerzoekBericht bericht = VerzoekBerichtBuilder.maakSuccesEindeHuwelijkInNederlandBericht();
        //execute
        final List<MeldingElement> meldingen = bericht.laadEntiteitenVoorObjectSleutels();
        //valideer
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2511, meldingen.iterator().next().getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R2512)
    public void testRegel2512() {
        //setup
        betrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        final BijhoudingVerzoekBericht bericht = VerzoekBerichtBuilder.maakSuccesEindeHuwelijkInNederlandBericht();
        //execute
        final List<MeldingElement> meldingen = bericht.laadEntiteitenVoorObjectSleutels();
        //valideer
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2512, meldingen.iterator().next().getRegel());
    }
}
