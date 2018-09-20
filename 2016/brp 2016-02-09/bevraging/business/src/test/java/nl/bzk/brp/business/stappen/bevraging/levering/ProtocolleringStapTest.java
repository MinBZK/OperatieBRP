/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.HistorievormAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.BevragingHistoriePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieService;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class ProtocolleringStapTest {

    public static final String             ID                 = "iD";
    @InjectMocks
    private             ProtocolleringStap protocolleringStap = new ProtocolleringStap();

    @Mock
    private ProtocolleringPublicatieService protocolleringPublicatieService;
    @Mock
    private BevragingBerichtContextBasis    berichtContext;

    @Mock
    private BevragingResultaat resultaat;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);


        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, la.geefDiensten().iterator().next());
        Mockito.when(berichtContext.getLeveringinformatie()).thenReturn(leveringinformatie);
    }

    @Test
    public final void testProtocolleringGeheimProtocolleringsNiveau() {

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metProtocolleringsniveau(Protocolleringsniveau.GEHEIM).maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, TestDienstBuilder.dummy());
        Mockito.when(berichtContext.getLeveringinformatie()).thenReturn(leveringinformatie);

        final BevragingsBericht inkBericht = new GeefDetailsPersoonBericht();
        protocolleringStap.voerStapUit(inkBericht, berichtContext, resultaat);

        Mockito.verify(protocolleringPublicatieService, Mockito.never()).publiceerProtocolleringGegevens(
            Mockito.<ProtocolleringOpdracht>any());
    }

    //Happy flow
    @Test
    public final void testProtocollering() {
        final Set<PersoonHisVolledigView> gevondenPersonen = new HashSet<>();
        final PersoonHisVolledigImpl persoonHisVolledig = maakPersoonHisVolledig(123);
        final DatumTijdAttribuut formeelPeilMoment = DatumTijdAttribuut.bouwDatumTijd(2012, 1, 2, 10, 30, 331);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20120102);
        gevondenPersonen.add(new PersoonHisVolledigView(
            persoonHisVolledig,
            new BevragingHistoriePredikaat(materieelPeilmoment, formeelPeilMoment, Historievorm.GEEN)));
        Mockito.when(resultaat.getGevondenPersonen()).thenReturn(gevondenPersonen);

        final BevragingsBericht inkBericht = new GeefDetailsPersoonBericht();
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setHistorievorm(new HistorievormAttribuut(Historievorm.GEEN));
        parameters.setPeilmomentFormeelResultaat(formeelPeilMoment);
        parameters.setPeilmomentMaterieelResultaat(materieelPeilmoment);
        inkBericht.setParameters(parameters);

        protocolleringStap.voerStapUit(inkBericht, berichtContext, resultaat);

        final ArgumentCaptor<ProtocolleringOpdracht> argument = ArgumentCaptor.forClass(ProtocolleringOpdracht.class);
        Mockito.verify(protocolleringPublicatieService, Mockito.times(1))
            .publiceerProtocolleringGegevens(argument.capture());

        final LeveringModel levering = argument.getValue().getLevering();
        Assert.assertEquals(SoortDienst.GEEF_DETAILS_PERSOON, argument.getValue().getSoortDienst());
        Assert.assertEquals(Historievorm.GEEN, argument.getValue().getHistorievorm());
        Assert.assertEquals(berichtContext.getLeveringinformatie().getDienst().getID().intValue(), levering.getDienstId().intValue());
        Assert.assertEquals(berichtContext.getLeveringinformatie().getToegangLeveringsautorisatie().getID().intValue(),
            levering.getToegangLeveringsautorisatieId().intValue());
        Assert.assertEquals(formeelPeilMoment, levering.getDatumTijdAanvangFormelePeriodeResultaat());
        Assert.assertEquals(materieelPeilmoment, levering.getDatumAanvangMaterielePeriodeResultaat());
        materieelPeilmoment.voegDagToe(1);
        Assert.assertEquals(materieelPeilmoment, levering.getDatumEindeMaterielePeriodeResultaat());
        Assert.assertNull(levering.getAdministratieveHandelingId());
        Assert.assertNull(levering.getSoortSynchronisatie());

        final Set<LeveringPersoonModel> personen = argument.getValue().getPersonen();
        Assert.assertEquals(1, personen.size());
        final LeveringPersoonModel levPers = personen.iterator().next();
        Assert.assertEquals(123, levPers.getPersoonId().intValue());
        Assert.assertNull(levPers.getLevering());
    }

    @Test
    public final void testProtocolleringGeenGevondenPersonen() {
        final BevragingsBericht inkBericht = new GeefDetailsPersoonBericht();
        Mockito.when(resultaat.getGevondenPersonen()).thenReturn(new HashSet<PersoonHisVolledigView>());

        protocolleringStap.voerStapUit(inkBericht, berichtContext, resultaat);

        Mockito.verify(protocolleringPublicatieService, Mockito.never()).publiceerProtocolleringGegevens(
            Mockito.<ProtocolleringOpdracht>any());
    }

    private static PersoonHisVolledigImpl maakPersoonHisVolledig(final Integer id) {
        final PersoonHisVolledigImpl persoon =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19800101)
                .datumGeboorte(19800101)
                .eindeRecord()
                .nieuwIdentificatienummersRecord(19800101, null, 19800101)
                .burgerservicenummer(1234)
                .administratienummer(1235L)
                .eindeRecord()
                .voegPersoonAdresToe(
                    new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(19800101, null, 19800101)
                        .postcode("1234AB").huisnummer(12).huisletter("A")
                        .identificatiecodeNummeraanduiding("abcd").eindeRecord().build()).build();
        ReflectionTestUtils.setField(persoon, ID, id);
        return persoon;
    }
}
