/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.Set;
import javax.persistence.OptimisticLockException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonAfnemerindicatieService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * TODO Asserts verbeteren
 */
@RunWith(MockitoJUnitRunner.class)
public class AttenderingPlaatsAfnemerindicatieServiceImplTest {

    private long handelingId = 1;

    @InjectMocks
    private AttenderingPlaatsAfnemerindicatieServiceImpl service;

    @Mock
    private PersoonAfnemerindicatieService persoonAfnemerindicatieService;

    @Test
    public void testLegeLijsten() throws BlobException {
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList());
        Mockito.verify(persoonAfnemerindicatieService, Mockito.never())
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Happlyflow, indicatie wordt geplaatst.
     */
    @Test
    public void testPlaatsAfnemerindicatie() throws BlobException {
        final Mutatiebericht levering = maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING),
                maakPersoon());
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(levering));
        Mockito.verify(persoonAfnemerindicatieService, Mockito.times(1))
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Happlyflow, indicatie wordt geplaatst voor meerdere personen in dezelfde autorisatie
     */
    @Test
    public void testPlaatsAfnemerindicatieMeerderePersonenPerAutorisatie() throws BlobException {
        final Mutatiebericht levering = maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING),
                maakPersoon(), maakPersoon());
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(levering));
        Mockito.verify(persoonAfnemerindicatieService, Mockito.times(2))
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Idicatie wordt geplaatst voor meerdere autorisaties, maar niet voor alle
     */
    @Test
    public void testPlaatsAfnemerindicatieVoorMeerdereAutorisatiesMaarNietVoorAlle() throws BlobException {
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(
                maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING), maakPersoon(), maakPersoon()),
                maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, null), maakPersoon()),
                maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING), maakPersoon(), maakPersoon())
        ));
        Mockito.verify(persoonAfnemerindicatieService, Mockito.times(4))
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Happlyflow, indicatie wordt geplaatst voor meerdere autorisaties
     */
    @Test
    public void testPlaatsAfnemerindicatieVoorMeerdereAutorisaties() throws BlobException {
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(
                maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING), maakPersoon()),
                maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING), maakPersoon())
        ));
        Mockito.verify(persoonAfnemerindicatieService, Mockito.times(2))
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Happlyflow, indicatie wordt geplaatst voor meerdere personen in dezelfde autorisatie, maar niet voor alle
     */
    @Test
    public void testPlaatsAfnemerindicatieMeerderePersonenPerAutorisatiePlaatsNietVoorAlle() throws BlobException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING);
        final ToegangLeveringsAutorisatie tla = autorisatiebundel.getToegangLeveringsautorisatie();
        final Mutatiebericht levering = maakMutatiebericht(autorisatiebundel,
                maakPersoon(),
                maakPersoonMetAfnemerindicatie(tla.getLeveringsautorisatie().getId(), tla.getGeautoriseerde().getPartij().getCode()),
                maakPersoon());
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(levering));
        Mockito.verify(persoonAfnemerindicatieService, Mockito.times(2))
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }


    /**
     * Als er reeds een afnemerindicatie bestaat wordt er niet opnieuw geplaatst.
     */
    @Test
    public void testAfnemerindicatieNietOpnieuwPlaatsen() throws BlobException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING,
                EffectAfnemerindicaties.PLAATSING);
        final Mutatiebericht levering = maakMutatiebericht(autorisatiebundel, maakPersoonMetAfnemerindicatie(autorisatiebundel
                .getToegangLeveringsautorisatie().getLeveringsautorisatie().getId(), autorisatiebundel.getToegangLeveringsautorisatie().getGeautoriseerde()
                .getPartij().getCode()));
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(levering));
        Mockito.verify(persoonAfnemerindicatieService, Mockito.never())
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Als er enkel een andere autorisatie een afnemerindicatie bestaat wordt er wel een afnemerindicatie geplaatst
     */
    @Test
    public void testAfnemerindicatieBestaatVoorAndereAutorisatie() throws BlobException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING,
                EffectAfnemerindicaties.PLAATSING);
        final Mutatiebericht levering = maakMutatiebericht(autorisatiebundel, maakPersoonMetAfnemerindicatie(autorisatiebundel
                .getToegangLeveringsautorisatie().getLeveringsautorisatie().getId() + 1, autorisatiebundel.getToegangLeveringsautorisatie()
                .getGeautoriseerde()
                .getPartij().getCode()));
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(levering));
        Mockito.verify(persoonAfnemerindicatieService, Mockito.times(1)).plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(),
                Mockito.anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Diensten ongelijk aan Attendering met effect Plaatsing worden genegeerd
     */
    @Test
    public void testNietPlaatsenVoorDienstOngelijkAanAttenderingMetPlaatsing() throws BlobException {
        final Mutatiebericht levering =
                maakMutatiebericht(maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, null), maakPersoon());
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(levering));
        Mockito.verify(persoonAfnemerindicatieService, Mockito.never())
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Diensten ongelijk aan Attendering met effect Plaatsing worden genegeerd
     */
    @Test
    public void testNietPlaatsenVoorDienstAttenderingZonderPlaatsing() throws BlobException {
        final Mutatiebericht levering =
                maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, null), maakPersoon());

        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(levering));

        Mockito.verify(persoonAfnemerindicatieService, Mockito.never())
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());
    }

    /**
     * Test als er vanuit de service een fout wordt gegooid dat de afnemerindicatie reeds bestaat, dan gaat de code verder voor andere personen / autorisaties
     */
    @Test
    public void testPlaatsAfnemerindicatieMetExceptieVanuitService() throws BlobException {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING);
        service.plaatsAfnemerindicaties(handelingId, Lists.newArrayList(
                maakMutatiebericht(autorisatiebundel, maakPersoon()),
                maakMutatiebericht(maakAutorisatiebundel(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING), maakPersoon())
        ));

        //gooi fout voor de ene autorisatie
        Mockito.doThrow(OptimisticLockException.class)
                .when(persoonAfnemerindicatieService).plaatsAfnemerindicatie(
                any(), anyObject(), eq(autorisatiebundel.getLeveringsautorisatieId()), anyInt(), anyObject(), anyObject(), any());
        //ga door voor de andere
        Mockito.verify(persoonAfnemerindicatieService, Mockito.times(2))
                .plaatsAfnemerindicatie(any(), anyObject(), Mockito.anyInt(), Mockito
                        .anyInt(), anyObject(), anyObject(), any());

    }


    private Mutatiebericht maakMutatiebericht(final Autorisatiebundel autorisatiebundel, final Persoonslijst... personen) {
        final HashMap<Persoonslijst, Populatie> map = Maps.newHashMap();
        final Mutatielevering mutatielevering = new Mutatielevering(autorisatiebundel, map);

        final Set<Persoonslijst> personenInBericht = Sets.newHashSet();
        for (Persoonslijst persoonslijst : personen) {
            map.put(persoonslijst, Populatie.BINNEN);
            personenInBericht.add(persoonslijst);
        }
        return new Mutatiebericht(mutatielevering, personenInBericht, "niet relevant", SynchronisatieBerichtGegevens.builder().build());
    }

    private Autorisatiebundel maakAutorisatiebundel(SoortDienst soortDienst, EffectAfnemerindicaties effectAfnemesrindicaties) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setId(1);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        dienst.setId(1);
        dienst.setEffectAfnemerindicaties(effectAfnemesrindicaties);
        return new Autorisatiebundel(tla, dienst);
    }

    private Persoonslijst maakPersoon() {
        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratieveHandeling(1);
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON).eindeObject()
                .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                .metActieInhoud(administratieveHandeling.getActie(1))
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_VERSIE.getId())
                .metRecord().metAttribuut(Element.PERSOON_VERSIE_LOCK.getId(), 123L).eindeRecord()
                .eindeGroep()
                .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }

    private Persoonslijst maakPersoonMetAfnemerindicatie(final Integer leveringsautorisatieId, final String afnemerCode) {
        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratieveHandeling(1);
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                .metActieInhoud(administratieveHandeling.getActie(1))
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_VERSIE.getId())
                .metRecord().metAttribuut(Element.PERSOON_VERSIE_LOCK.getId(), 123L).eindeRecord()
                .eindeGroep()
                .metObject(TestBuilders.maakAfnemerindicatie(leveringsautorisatieId, afnemerCode))
                .eindeObject()
                .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }
}
