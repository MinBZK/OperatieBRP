/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.service.algemeen.ProtocolleringService;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link AbstractProtocolleerBerichtServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractProtocolleerBerichtServiceImplTest {

    private static final ZonedDateTime TIJDSTIP_LAATSTE_WIJZIGING = TestDatumUtil.gisteren();

    static {
        BrpNu.set(TIJDSTIP_LAATSTE_WIJZIGING);
    }

    //@formatter:off
     private static final Persoonslijst[] PERSOONSLIJSTEN                           =  new Persoonslijst[] {
           new Persoonslijst(MetaObject.maakBuilder()
               .metObjectElement(Element.PERSOON)
               .metId(1)
               .metGroep()
                   .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                   .metRecord()
                       .metActieInhoud(TestVerantwoording.maakActie(1, TIJDSTIP_LAATSTE_WIJZIGING))
                       .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId(), TIJDSTIP_LAATSTE_WIJZIGING)
                   .eindeRecord()
               .eindeGroep()
               .build(), 0L),
           new Persoonslijst(MetaObject.maakBuilder()
               .metObjectElement(Element.PERSOON)
               .metId(2)
               .metGroep()
                   .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                   .metRecord()
                       .metActieInhoud(TestVerantwoording.maakActie(1, TIJDSTIP_LAATSTE_WIJZIGING))
                       .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId(), TIJDSTIP_LAATSTE_WIJZIGING)
                   .eindeRecord()
               .eindeGroep()
               .build(),0L)
    };
    //@formatter:on
    private static final int DATUM_EINDE_MATERIELE_PERIODE_RESULTAAT = 20110101;
    private static final int DATUM_AANVANG_MATERIELE_PERIODE_RESULTAAT = 20100101;
    private static final ZonedDateTime DATUM_TIJD_AANVANG_FORMELE_PERIODE_RESULTAAT = DatumUtil.nuAlsZonedDateTime();
    private static final ZonedDateTime DATUM_TIJD_EINDE_FORMELE_PERIODE_RESULTAAT = DatumUtil.nuAlsZonedDateTime();
    private static final List<LeveringPersoon> GELEVERDE_PERSONEN = asList(new LeveringPersoon(1L, TIJDSTIP_LAATSTE_WIJZIGING),
            new LeveringPersoon(2L, TIJDSTIP_LAATSTE_WIJZIGING));
    private static final String PARTIJ_CODE = "012345";

    @InjectMocks
    private AbstractProtocolleerBerichtServiceImpl<BevragingVerzoek, BevragingResultaat> protocolleerBerichtService
            = new TestProtocolleerBerichtServiceImpl();

    @Mock
    private ProtocolleringService protocolleringService;

    @Captor
    private ArgumentCaptor<ProtocolleringOpdracht> protocolleringOpdrachtArgumentCaptor;

    @Test
    public void protocolleerHappyFlow() throws Exception {
        final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode(PARTIJ_CODE);
        bevragingVerzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        final BevragingResultaat bevragingResultaat = new BevragingResultaat();
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        leveringsautorisatie.setId(123);
        final Dienst dienst = new Dienst(new Dienstbundel(leveringsautorisatie), SoortDienst.GEEF_DETAILS_PERSOON);
        dienst.setId(456);
        final Partij partij = new Partij("partij", PARTIJ_CODE);
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partij.getPartijRolSet().add(partijRol);
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        toegangLeveringsautorisatie.setId(789);
        toegangLeveringsautorisatie.setGeautoriseerde(partijRol);
        bevragingResultaat.setAutorisatiebundel(new Autorisatiebundel(toegangLeveringsautorisatie, dienst));
        final ZonedDateTime datumTijdVerzending = DatumUtil.nuAlsZonedDateTime();
        final AntwoordBerichtResultaat antwoordBerichtResultaat = new AntwoordBerichtResultaat("xmlUit", datumTijdVerzending,
                "referentienummerAntwoord", Collections.emptyList());
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metResultaat(BerichtVerwerkingsResultaat
                    .builder().metVerwerking(VerwerkingsResultaat.GESLAAGD).build()
                ).build();
        bevragingResultaat.setBericht(new VerwerkPersoonBericht(basisBerichtGegevens, null, asList(
                    new BijgehoudenPersoon.Builder(PERSOONSLIJSTEN[0], null).build(),
                    new BijgehoudenPersoon.Builder(PERSOONSLIJSTEN[1], null).build()
                ))
        );
        //@formatter:on

        protocolleerBerichtService.protocolleer(bevragingVerzoek, bevragingResultaat, antwoordBerichtResultaat);

        verify(protocolleringService).protocolleer(protocolleringOpdrachtArgumentCaptor.capture());
        final ProtocolleringOpdracht protocolleringOpdracht = protocolleringOpdrachtArgumentCaptor.getValue();
        Assert.assertEquals(protocolleringOpdracht.getGeleverdePersonen(), GELEVERDE_PERSONEN);
        Assert.assertEquals(protocolleringOpdracht.getDienstId(), dienst.getId());
        Assert.assertEquals(protocolleringOpdracht.getDatumAanvangMaterielePeriodeResultaat().intValue(), DATUM_AANVANG_MATERIELE_PERIODE_RESULTAAT);
        Assert.assertEquals(protocolleringOpdracht.getDatumEindeMaterielePeriodeResultaat().intValue(), DATUM_EINDE_MATERIELE_PERIODE_RESULTAAT);
        Assert.assertEquals(protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat(), DATUM_TIJD_AANVANG_FORMELE_PERIODE_RESULTAAT);
        Assert.assertEquals(protocolleringOpdracht.getDatumTijdEindeFormelePeriodeResultaat(), DATUM_TIJD_EINDE_FORMELE_PERIODE_RESULTAAT);

        assertThat(protocolleringOpdracht.getGeleverdePersonen(),
                containsInAnyOrder(new LeveringPersoon(1L, TIJDSTIP_LAATSTE_WIJZIGING), new LeveringPersoon(2L, TIJDSTIP_LAATSTE_WIJZIGING)));
    }


    @Test
    public void nietProtocollerenAlsVerwerkingsResultaatFoutief() {
        final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode("000123");
        final BevragingResultaat bevragingResultaat = new BevragingResultaat();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metResultaat(BerichtVerwerkingsResultaat .builder().metVerwerking(VerwerkingsResultaat.FOUTIEF).build()).build();
        //@formatter:on
        bevragingResultaat.setBericht(new VerwerkPersoonBericht(basisBerichtGegevens, null, null));

        protocolleerBerichtService
                .protocolleer(bevragingVerzoek, bevragingResultaat,
                        new AntwoordBerichtResultaat(null, DatumUtil.nuAlsZonedDateTime(), "", Collections.emptyList()));

        verifyZeroInteractions(protocolleringService);
    }

    @Test
    public void nietProtocollerenAlsBerichtIsNull() {
        final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode("000123");
        final BevragingResultaat bevragingResultaat = new BevragingResultaat();
        bevragingResultaat.setBericht(null);

        protocolleerBerichtService
                .protocolleer(bevragingVerzoek, bevragingResultaat,
                        new AntwoordBerichtResultaat(null, DatumUtil.nuAlsZonedDateTime(), "", Collections.emptyList()));

        verifyZeroInteractions(protocolleringService);
    }

    @Test
    public void nietProtocollerenAlsAutorisatiebundelIsNull() {
        final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode("000123");
        final BevragingResultaat bevragingResultaat = new BevragingResultaat();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metResultaat(BerichtVerwerkingsResultaat
                    .builder().metVerwerking(VerwerkingsResultaat.GESLAAGD).build()
                ).build();
        bevragingResultaat.setBericht(new VerwerkPersoonBericht(basisBerichtGegevens, null, null));
        //@formatter:on
        bevragingResultaat.setAutorisatiebundel(null);

        protocolleerBerichtService
                .protocolleer(bevragingVerzoek, bevragingResultaat,
                        new AntwoordBerichtResultaat(null, DatumUtil.nuAlsZonedDateTime(), "", Collections.emptyList()));

        verifyZeroInteractions(protocolleringService);
    }

    @Test
    public void nietProtocollerenAlsToeganglevautIsNull() {
        final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode("000123");
        final BevragingResultaat bevragingResultaat = new BevragingResultaat();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metResultaat(BerichtVerwerkingsResultaat
                    .builder().metVerwerking(VerwerkingsResultaat.GESLAAGD).build()
                ).build();
        bevragingResultaat.setBericht(new VerwerkPersoonBericht(basisBerichtGegevens, null, null));
        //@formatter:on
        bevragingResultaat.setAutorisatiebundel(new Autorisatiebundel(null, null));

        protocolleerBerichtService
                .protocolleer(bevragingVerzoek, bevragingResultaat,
                        new AntwoordBerichtResultaat(null, DatumUtil.nuAlsZonedDateTime(), "", Collections.emptyList()));

        verifyZeroInteractions(protocolleringService);
    }

    @Test
    public void nietProtocollerenAlsPartijGeenAfnemer() {
        final Partij bijhouder = new Partij("bijhouder", "000123");
        bijhouder.getPartijRolSet().add(new PartijRol(bijhouder, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode("000123");
        final BevragingResultaat bevragingResultaat = new BevragingResultaat();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metResultaat(BerichtVerwerkingsResultaat
                    .builder().metVerwerking(VerwerkingsResultaat.GESLAAGD).build()
                ).build();
        bevragingResultaat.setBericht(new VerwerkPersoonBericht(basisBerichtGegevens, null , null));
        //@formatter:on
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setId(123);
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = new ToegangLeveringsAutorisatie(bijhouder.getPartijRolSet().iterator().next(),
                leveringsautorisatie);
        toegangLeveringsautorisatie.setId(789);
        bevragingResultaat.setAutorisatiebundel(new Autorisatiebundel(toegangLeveringsautorisatie, null));

        protocolleerBerichtService
                .protocolleer(bevragingVerzoek, bevragingResultaat,
                        new AntwoordBerichtResultaat(null, DatumUtil.nuAlsZonedDateTime(), "", Collections.emptyList()));

        verifyZeroInteractions(protocolleringService);
    }

    @Test
    public void nietProtocollerenBijProtocolleringsniveauGeheim() {
        final Partij afnemer = new Partij("afnemer", "000123");
        afnemer.getPartijRolSet().add(new PartijRol(afnemer, Rol.AFNEMER));
        final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode("000123");
        final BevragingResultaat bevragingResultaat = new BevragingResultaat();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metResultaat(BerichtVerwerkingsResultaat
                    .builder().metVerwerking(VerwerkingsResultaat.GESLAAGD).build()
                ).build();
        bevragingResultaat.setBericht(new VerwerkPersoonBericht(basisBerichtGegevens, null, null));
        //@formatter:on
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEHEIM);
        leveringsautorisatie.setId(123);
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = new ToegangLeveringsAutorisatie(afnemer.getPartijRolSet().iterator().next(),
                leveringsautorisatie);
        toegangLeveringsautorisatie.setId(789);
        bevragingResultaat.setAutorisatiebundel(new Autorisatiebundel(toegangLeveringsautorisatie, null));

        protocolleerBerichtService
                .protocolleer(bevragingVerzoek, bevragingResultaat,
                        new AntwoordBerichtResultaat(null, DatumUtil.nuAlsZonedDateTime(), "", Collections.emptyList()));

        verifyZeroInteractions(protocolleringService);
    }

    @Test
    public void nietProtocollerenBijNietLeverenPersonen() {
        final Partij afnemer = new Partij("afnemer", "000123");
        afnemer.getPartijRolSet().add(new PartijRol(afnemer, Rol.AFNEMER));
        final GeefDetailsPersoonVerzoek bevragingVerzoek = new GeefDetailsPersoonVerzoek();
        bevragingVerzoek.getStuurgegevens().setZendendePartijCode("000123");
        final BevragingResultaat bevragingResultaat = new BevragingResultaat();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metResultaat(BerichtVerwerkingsResultaat
                    .builder().metVerwerking(VerwerkingsResultaat.GESLAAGD).build()
                ).build();
        bevragingResultaat.setBericht(new VerwerkPersoonBericht(basisBerichtGegevens, null, null) );
        //@formatter:on
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        leveringsautorisatie.setId(123);
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = new ToegangLeveringsAutorisatie(afnemer.getPartijRolSet().iterator().next(),
                leveringsautorisatie);
        toegangLeveringsautorisatie.setId(789);
        bevragingResultaat.setAutorisatiebundel(new Autorisatiebundel(toegangLeveringsautorisatie, null));

        protocolleerBerichtService
                .protocolleer(bevragingVerzoek, bevragingResultaat,
                        new AntwoordBerichtResultaat(null, DatumUtil.nuAlsZonedDateTime(), "", Collections.emptyList()));

        verifyZeroInteractions(protocolleringService);
    }

    private static final class TestProtocolleerBerichtServiceImpl
            extends AbstractProtocolleerBerichtServiceImpl<BevragingVerzoek, BevragingResultaat> {

        @Override
        protected Integer bepaalDatumAanvangMaterielePeriodeResultaat(final BevragingVerzoek bevragingVerzoek) {
            return DATUM_AANVANG_MATERIELE_PERIODE_RESULTAAT;
        }

        @Override
        protected Integer bepaalDatumEindeMaterielePeriodeResultaat(final BevragingVerzoek bevragingVerzoek) {
            return DATUM_EINDE_MATERIELE_PERIODE_RESULTAAT;
        }

        @Override
        protected ZonedDateTime bepaalDatumTijdAanvangFormelePeriodeResultaat(final BevragingVerzoek bevragingVerzoek) {
            return DATUM_TIJD_AANVANG_FORMELE_PERIODE_RESULTAAT;
        }

        @Override
        protected ZonedDateTime bepaalDatumTijdEindeFormelePeriodeResultaat(final BevragingVerzoek bevragingVerzoek,
                                                                            final ZonedDateTime datumTijdKlaarzettenBericht) {
            return DATUM_TIJD_EINDE_FORMELE_PERIODE_RESULTAAT;
        }
    }

}
