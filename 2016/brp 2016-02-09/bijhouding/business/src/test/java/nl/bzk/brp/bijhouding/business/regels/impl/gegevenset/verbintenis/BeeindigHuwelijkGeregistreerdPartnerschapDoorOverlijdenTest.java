/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.util.hisvolledig.kern.HisRelatieModelBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijdenTest {

    private HuwelijkGeregistreerdPartnerschapHisVolledig hgpModel   = mock(HuwelijkGeregistreerdPartnerschapHisVolledig.class);
    private ActieModel                                   actie      = mock(ActieModel.class);
    private HisPersoonOverlijdenModel                    overlijden = mock(HisPersoonOverlijdenModel.class);

    @Mock
    private ReferentieDataRepository repository;

    @InjectMocks
    private TestBeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden model =
        new TestBeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden(hgpModel, actie, overlijden);;

    @Mock
    private PersoonHisVolledigImpl partner;


    @Before
    public void before() {
        final HisRelatieModel hisRelatieModel = new HisRelatieModelBuilder()
            .datumAanvang(new DatumEvtDeelsOnbekendAttribuut(20010101))
            .datumTijdRegistratie(DatumTijdAttribuut.datumTijd(2010, 1, 1))
            .build();

        final RedenEindeRelatie redenEindeRelatie = new TestRedenEindeRelatie(new RedenEindeRelatieCodeAttribuut("WIL EEN ANDER"), new
            OmschrijvingEnumeratiewaardeAttribuut("BEN ER KLAAR MEE"));

        when(repository.vindRedenEindeRelatieOpCode(RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OVERLIJDEN_CODE)).thenReturn(redenEindeRelatie);

        when(hgpModel.getRelatieHistorie()).thenReturn(new FormeleHistorieSetImpl<>(new HashSet<>(Collections.singletonList(hisRelatieModel))));
        final Set<PartnerHisVolledig> betrokkenheden = new HashSet<>();
        betrokkenheden.add(new PartnerHisVolledigImpl(null, partner));
        doReturn(betrokkenheden).when(hgpModel).getBetrokkenheden();

        when(actie.getTijdstipRegistratie()).thenReturn(new DatumTijdAttribuut(new Date()));

        when(overlijden.getDatumOverlijden()).thenReturn(new DatumEvtDeelsOnbekendAttribuut(20151125));
        when(overlijden.getGemeenteOverlijden()).thenReturn(new GemeenteAttribuut(null));
        when(overlijden.getPersoon()).thenReturn(TestPersoonJohnnyJordaan.maak());
    }

    @Test
    public void testGetRegel() {
        assertThat(model.getRegel(), is(Regel.VR02002a));
    }

    @Test
    public void testLeidAf() {
        when(partner.getID()).thenReturn(10);

        final AfleidingResultaat afleidingResultaat = model.leidAf();

        assertThat(afleidingResultaat, is(model.getVerwachtAfleidingsResultaat()));
        assertThat(hgpModel.getRelatieHistorie().getAantal(), is(2));
        assertThat(model.getExtraBijgehoudenPersonen(), hasItem(partner));
    }

    @Test
    public void testLeidAfZonderExtraBijgehoudenPersonen() {
        when(partner.getID()).thenReturn(1);

        model.leidAf();

        assertThat(model.getExtraBijgehoudenPersonen().size(), is(0));
    }

    private static class TestRedenEindeRelatie extends RedenEindeRelatie {
        private TestRedenEindeRelatie(RedenEindeRelatieCodeAttribuut reden, OmschrijvingEnumeratiewaardeAttribuut omschrijving) {
            super(reden, omschrijving);
        }
    }

    private static class TestBeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden extends BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden {
        private TestBeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden(
            final HuwelijkGeregistreerdPartnerschapHisVolledig hgpModel,
            final ActieModel actie, final HisPersoonOverlijdenModel overlijden)
        {
            super(hgpModel, actie, overlijden);
        }

        private AfleidingResultaat getVerwachtAfleidingsResultaat() {
            return GEEN_VERDERE_AFLEIDINGEN;
        }
    }
}
