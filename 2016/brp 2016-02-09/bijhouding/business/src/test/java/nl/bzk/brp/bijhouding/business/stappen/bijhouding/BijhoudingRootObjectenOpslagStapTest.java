/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.repository.RelatieHisVolledigRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class BijhoudingRootObjectenOpslagStapTest {

    private BijhoudingRootObjectenOpslagStap stap;

    private final PersoonHisVolledigImpl testPersoon           =
        new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testPersoonVader      =
        new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testPersoonMoeder     =
        new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private final PersoonHisVolledigImpl testNietIngeschrevene =
        new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
    private final RelatieHisVolledigImpl nieuweRelatie         = new FamilierechtelijkeBetrekkingHisVolledigImpl();
    private final RelatieHisVolledigImpl bijgehoudenRelatie    = new FamilierechtelijkeBetrekkingHisVolledigImpl();

    private BijhoudingBerichtContext context;
    @Mock
    private PersoonHisVolledigRepository persoonRepository;
    @Mock
    private RelatieHisVolledigRepository relatieRepository;

    @Before
    public void initialiseer() {
        MockitoAnnotations.initMocks(this);
        stap = new BijhoudingRootObjectenOpslagStap();
        ReflectionTestUtils.setField(stap, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(stap, "relatieRepository", relatieRepository);

        ReflectionTestUtils.setField(testPersoon, "iD", 1);
        ReflectionTestUtils.setField(testPersoonVader, "iD", 2);
        ReflectionTestUtils.setField(testPersoonMoeder, "iD", 3);

        context = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, "ref", null);
    }

    @Test
    public void testVoerStapUit() {

        final ActieModel actie = new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY),
            null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null, DatumTijdAttribuut.nu(), null);
        final AdministratieveHandelingModel administratieveHandelingModel =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.DUMMY),
                null, null, new DatumTijdAttribuut(new Date()));
        administratieveHandelingModel.getActies().add(actie);
        context.setAdministratieveHandeling(administratieveHandelingModel);

        context.voegAangemaaktBijhoudingsRootObjectToe("5", nieuweRelatie);
        context.voegAangemaaktBijhoudingsRootObjectToe("1", testPersoon);
        context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(testNietIngeschrevene);

        context.voegBestaandBijhoudingsRootObjectToe("6", bijgehoudenRelatie);
        context.voegBestaandBijhoudingsRootObjectToe("2", testPersoonVader);
        context.voegBestaandBijhoudingsRootObjectToe("3", testPersoonMoeder);

        final BijhoudingsBericht bericht = maakBerichtMetActie("A", "B", "C");
        context.voegActieMappingToe(bericht.getAdministratieveHandeling().getActies().get(0), actie);
        stap.voerStapUit(bericht, context);

        // Test dat eerst nieuwe root objecten worden opgeslagen, dan nieuwe niet ingeschrevenen en dan de gewijzigde
        // root objecten.
        final InOrder inOrder = Mockito.inOrder(persoonRepository, relatieRepository);
        inOrder.verify(persoonRepository).opslaanNieuwPersoon(testPersoon);
        inOrder.verify(relatieRepository).schrijfGenormalizeerdModel(nieuweRelatie);
        inOrder.verify(persoonRepository).opslaanNieuwPersoon(testNietIngeschrevene);
        // gewijzigde root-objecten
        inOrder.verify(persoonRepository).schrijfGenormalizeerdModel(testPersoonMoeder);
        inOrder.verify(relatieRepository).schrijfGenormalizeerdModel(bijgehoudenRelatie);

        verify(persoonRepository, Mockito.times(1)).opslaanNieuwPersoon(testPersoon);
        verify(persoonRepository, Mockito.times(1)).opslaanNieuwPersoon(testNietIngeschrevene);
        verify(relatieRepository, Mockito.times(1)).schrijfGenormalizeerdModel(nieuweRelatie);
        verify(persoonRepository, Mockito.times(1)).schrijfGenormalizeerdModel(testPersoonMoeder);
        verify(persoonRepository, Mockito.times(1)).schrijfGenormalizeerdModel(testPersoonVader);
        verify(relatieRepository, Mockito.times(1)).schrijfGenormalizeerdModel(bijgehoudenRelatie);
    }

    private BijhoudingsBericht maakBerichtMetActie(final String sleutelKind,
                                                           final String sleutelOuder1,
                                                           final String sleutelOuder2)
    {
        final BijhoudingsBericht bericht = new RegistreerGeboorteBericht();
        final List<ActieBericht> acties = new ArrayList<>();
        final AdministratieveHandelingBericht administratieveHandeling = new HandelingGeboorteInNederlandBericht();
        bericht.getStandaard().setAdministratieveHandeling(administratieveHandeling);
        administratieveHandeling.setActies(acties);

        final PersoonBericht ouder1 = new PersoonBericht();
        ouder1.setIdentificerendeSleutel(sleutelOuder1);
        ouder1.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBericht ouder2 = new PersoonBericht();
        ouder2.setIdentificerendeSleutel(sleutelOuder2);
        ouder2.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBericht kind = new PersoonBericht();
        kind.setCommunicatieID(sleutelKind);
        kind.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final OuderBericht ouder1Betr = new OuderBericht();
        ouder1Betr.setPersoon(ouder1);
        final OuderBericht ouder2Betr = new OuderBericht();
        ouder2Betr.setPersoon(ouder2);
        final KindBericht kindBetr = new KindBericht();
        kindBetr.setPersoon(kind);

        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                new FamilierechtelijkeBetrekkingBericht();
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(kindBetr);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(ouder1Betr);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(ouder2Betr);

        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setRootObject(familierechtelijkeBetrekkingBericht);
        acties.add(actie);
        return bericht;
    }

    @Test
    public void testAfgeleidAdministratiefAfleiding() {
        final ActieModel actie = new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY),
            null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null, DatumTijdAttribuut.nu(), null);
        final PersoonHisVolledigImpl ouder1 =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final HisPersoonAfgeleidAdministratiefModel hisPersoonAfgeleidAdministratiefModel =
            new HisPersoonAfgeleidAdministratiefModel(ouder1, null, new DatumTijdAttribuut(new Date()),
                null, JaNeeAttribuut.NEE, null, actie);
        hisPersoonAfgeleidAdministratiefModel.getFormeleHistorie()
                                             .setDatumTijdRegistratie(new DatumTijdAttribuut(new Date()));
        ouder1.getPersoonAfgeleidAdministratiefHistorie().voegToe(hisPersoonAfgeleidAdministratiefModel);


        final PersoonHisVolledigImpl kind =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final DatumTijdAttribuut tijdstipRegistratie = new DatumTijdAttribuut(context.getTijdstipVerwerking());
        final AdministratieveHandelingModel administratieveHandelingModel =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.DUMMY), null, null, tijdstipRegistratie);
        final ActieModel testActie = new ActieModel(
            null, administratieveHandelingModel, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
            tijdstipRegistratie, null);
        administratieveHandelingModel.getActies().add(testActie);
        context.setAdministratieveHandeling(administratieveHandelingModel);

        context.voegBestaandBijhoudingsRootObjectToe("ouder1", ouder1);
        context.voegAangemaaktBijhoudingsRootObjectToe("kind", kind);

        final Integer aantalRecordsOuder =
                ((PersoonHisVolledigImpl) context.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("ouder1"))
                        .getPersoonAfgeleidAdministratiefHistorie().getAantal();

        final Integer aantalRecordsKind =
                ((PersoonHisVolledigImpl) context.getAangemaakteBijhoudingsRootObjectMetCommunicatieID("kind"))
                        .getPersoonAfgeleidAdministratiefHistorie().getAantal();

        assertEquals(1, aantalRecordsOuder.intValue());
        assertEquals(0, aantalRecordsKind.intValue());

        final BijhoudingsBericht bericht = maakBerichtMetActie("kind", "ouder1", "ouder2");
        context.voegActieMappingToe(bericht.getAdministratieveHandeling().getActies().get(0), testActie);
        stap.voerStapUit(bericht, context);

        assertEquals(2, context.getBijgehoudenPersonen().size());

        // Test de ouder
        final HisPersoonAfgeleidAdministratiefModel ouderAfgeleidAdministratie =
            ((PersoonHisVolledigImpl) context.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel("ouder1"))
                .getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();
        assertNotNull(ouderAfgeleidAdministratie);
        assertEquals(tijdstipRegistratie.getWaarde(),
            ouderAfgeleidAdministratie.getTijdstipLaatsteWijziging().getWaarde());
        //Ouder is niet hoofdpersoon dus sorteervolgorde 2
        assertEquals(2, ouderAfgeleidAdministratie.getSorteervolgorde().getWaarde().intValue());
        assertEquals(administratieveHandelingModel, ouderAfgeleidAdministratie.getAdministratieveHandeling());

        // Test het kind
        final HisPersoonAfgeleidAdministratiefModel afgeleidAdministratiefKind =
            ((PersoonHisVolledigImpl) context.getAangemaakteBijhoudingsRootObjectMetCommunicatieID("kind"))
                .getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();
        assertNotNull(afgeleidAdministratiefKind);
        //Kind is hoofdpersoon dus sorteervolgorde 1
        assertEquals(1, afgeleidAdministratiefKind.getSorteervolgorde().getWaarde().intValue());
        assertEquals(tijdstipRegistratie, afgeleidAdministratiefKind.getTijdstipLaatsteWijziging());
        assertEquals(administratieveHandelingModel, afgeleidAdministratiefKind.getAdministratieveHandeling());
    }

}
