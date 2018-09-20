/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut.datumTijd;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.GlazenbolException;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.OnderzoekStandaardGroepModel;
import nl.bzk.brp.util.hisvolledig.kern.HisPersoonVoornaamModelBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;


public class HistorieVanafPredikaatTest {

    private static final String PETRA = "Petra";
    private Set<HisPersoonVoornaamModel> naamHistorie;
    private HisPersoonVoornaamModel      piet;
    private HisPersoonVoornaamModel      petra;
    private HisPersoonVoornaamModel      pete;
    private HisPersoonVoornaamModel      petraOud;
    private HisPersoonVoornaamModel      petraRecent;

    private Set<HisPersoonVoornaamModel> naamHistorieOnbekendeDatums;
    private HisPersoonVoornaamModel      jan;
    private HisPersoonVoornaamModel      janus;
    private HisPersoonVoornaamModel      johannes;
    private HisPersoonVoornaamModel      johan;
    private HisPersoonVoornaamModel      john;

    private final Predicate materieleHistorieVanafPredikaat = HistorieVanafPredikaat.geldigOpEnNa(new DatumAttribuut(19900225));

    @Before
    public void bouwVoornaamHistorie() {
        piet =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Piet").datumTijdRegistratie(datumTijd(1960, 1, 1))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19600101))
                        .datumTijdVerval(datumTijd(2000, 1, 15)).build();

        petra =
                HisPersoonVoornaamModelBuilder.defaultValues().naam(PETRA)
                        .datumTijdRegistratie(datumTijd(2000, 1, 15))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19600101))
                        .datumTijdVerval(datumTijd(2010, 1, 1)).build();

        pete =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Pete").datumTijdRegistratie(datumTijd(2010, 1, 1))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800108))
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19980110)).build();

        petraOud =
                HisPersoonVoornaamModelBuilder.defaultValues().naam(PETRA).datumTijdRegistratie(datumTijd(2010, 1, 1))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19600101))
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800108)).build();

        petraRecent =
                HisPersoonVoornaamModelBuilder.defaultValues().naam(PETRA).datumTijdRegistratie(datumTijd(2010, 1, 1))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19980110)).build();

        naamHistorie = new HashSet<>(asList(piet, petra, petraOud, pete, petraRecent));


        jan =
            HisPersoonVoornaamModelBuilder.defaultValues().naam("Jan").datumTijdRegistratie(datumTijd(1960, 1, 1))
                .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19600101))
                .datumTijdVerval(datumTijd(2000, 1, 15)).build();

        janus =
            HisPersoonVoornaamModelBuilder.defaultValues().naam("Janus").datumTijdRegistratie(datumTijd(1980, 1, 1))
                .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800108))
                .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(Integer.valueOf("00000000"))).build();

        johannes =
            HisPersoonVoornaamModelBuilder.defaultValues().naam("Johannes").datumTijdRegistratie(datumTijd(1990, 1, 1))
                .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19900108))
                .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19900000)).build();

        johan =
            HisPersoonVoornaamModelBuilder.defaultValues().naam("Johan").datumTijdRegistratie(datumTijd(1990, 1, 1))
                .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19900108))
                .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19900100)).build();

        john =
            HisPersoonVoornaamModelBuilder.defaultValues().naam("John").datumTijdRegistratie(datumTijd(1990, 1, 1))
                .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19900108))
                .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19900101)).build();

        naamHistorieOnbekendeDatums = new HashSet<>(asList(jan, janus, johannes, johan, john));
    }

    @Test
    public void historieVanaf1990() {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19900101);
        final Predicate geldigOp = HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);

        // when
        CollectionUtils.filter(naamHistorie, geldigOp);

        // then
        assertThat(naamHistorie.size(), is(4));
    }

    @Test
    public void historieVanaf1970() {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19700101);
        final Predicate geldigOp = HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);

        // when
        CollectionUtils.filter(naamHistorie, geldigOp);

        // then
        assertThat(naamHistorie.size(), is(5));
    }

    @Test
    public void historieZonderDatDatumIsIngevuld() {
        // given
        final Predicate geldigOp = HistorieVanafPredikaat.geldigOpEnNa(null);

        // when
        CollectionUtils.filter(naamHistorie, geldigOp);

        // then
        assertThat(naamHistorie.size(), is(5));
    }

    @Test
    public void historieWaarbijTijdstipGelijkIsAanDAG() {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19800108);
        final Predicate geldigOp = HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);

        // when
        CollectionUtils.filter(naamHistorie, geldigOp);

        // then
        assertThat(pete, isIn(naamHistorie));
        assertThat(petraRecent, isIn(naamHistorie));
        assertThat(petraOud, not(isIn(naamHistorie)));
        assertThat(naamHistorie.size(), is(4));
    }

    @Test
    public void historieWaarbijTijdstipGelijkIsAanDEG() {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19980110);
        final Predicate geldigOp = HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);

        // when
        CollectionUtils.filter(naamHistorie, geldigOp);

        // then
        assertThat(pete, not(isIn(naamHistorie)));
        assertThat(petraRecent, isIn(naamHistorie));
        assertThat(naamHistorie.size(), is(3));
    }

    @Test
    public void historieWaarbijTijdstipGelijkIsAanVandaag() {
        // given
        final DatumAttribuut materieelPeilmoment = DatumAttribuut.vandaag();
        final Predicate geldigOp = HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);

        // when
        CollectionUtils.filter(naamHistorie, geldigOp);

        // then
        assertThat(naamHistorie.size(), is(3));
        assertThat(petraRecent, isIn(naamHistorie));
    }

    @Test(expected = GlazenbolException.class)
    public void doorsnedeInDeToekomstKanNiet() {
        // given
        final DatumAttribuut materieelPeilmoment = DatumAttribuut.morgen();

        // when, exception expected
        HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);
    }

    @Test
    public void geeftMaterieelMomentVanafUitPredikaat() {
        final DatumAttribuut materieelVanafMoment = DatumAttribuut.gisteren();

        final HistorieVanafPredikaat predikaat = HistorieVanafPredikaat.geldigOpEnNa(materieelVanafMoment);

        assertThat(predikaat.getLeverenVanafMoment(), is(materieelVanafMoment));
    }

    @Test
    public void historieWaarbijEindeGeldigheidVolledigOnbekendGeldig() {
        // given
        final DatumAttribuut materieelPeilmoment = DatumAttribuut.vandaag();
        final Predicate predikaat = HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);

        // when
        CollectionUtils.filter(naamHistorieOnbekendeDatums, predikaat);

        // then
        assertThat(naamHistorieOnbekendeDatums.size(), is(2));
        assertThat(janus, isIn(naamHistorieOnbekendeDatums));
    }

    @Test
    public void historieWaarbijEindeGeldigheidDagOnbekendGeldig() {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19900125);
        final Predicate predikaat = HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);

        // when
        CollectionUtils.filter(naamHistorieOnbekendeDatums, predikaat);

        // then
        assertThat(naamHistorieOnbekendeDatums.size(), is(4));
        assertThat(johan, isIn(naamHistorieOnbekendeDatums));
    }

    @Test
    public void historieWaarbijEindeGeldigheidMaandOnbekendGeldig() {
        // given
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19900225);
        final Predicate predikaat = HistorieVanafPredikaat.geldigOpEnNa(materieelPeilmoment);

        // when
        CollectionUtils.filter(naamHistorieOnbekendeDatums, predikaat);

        // then
        assertThat(naamHistorieOnbekendeDatums.size(), is(3));
        assertThat(johannes, isIn(naamHistorieOnbekendeDatums));
    }

    @Test
    public void onderzoekHistorieWordtGefilterdOpDatumEinde() {
        // given
        final HisOnderzoekModel onderzoekModel = bouwHisOnderzoekModelMetRestricties(new DatumEvtDeelsOnbekendAttribuut(19900224), null);
        final Set<HisOnderzoekModel> onderzoeken = new HashSet<>(singletonList(onderzoekModel));

        // when
        CollectionUtils.filter(onderzoeken, materieleHistorieVanafPredikaat);

        // then
        assertThat(onderzoeken.size(), is(0));
    }

    @Test
    public void onderzoekHistorieWordtGefilterdOpDatumTijdVerval() {
        // given
        final HisOnderzoekModel onderzoekModel = bouwHisOnderzoekModelMetRestricties(null, DatumTijdAttribuut.bouwDatumTijd(1990, 1, 1));
        final Set<HisOnderzoekModel> onderzoeken = new HashSet<>(singletonList(onderzoekModel));

        // when
        CollectionUtils.filter(onderzoeken, materieleHistorieVanafPredikaat);

        // then
        assertThat(onderzoeken.size(), is(0));
    }

    @Test
    public void onderzoekHistorieWordtGefilterdOpDatumEindeEnDatumTijdVerval() {
        // given
        final HisOnderzoekModel onderzoekModel = bouwHisOnderzoekModelMetRestricties(new DatumEvtDeelsOnbekendAttribuut(19900224),
            DatumTijdAttribuut.bouwDatumTijd(1990, 1, 1));
        final Set<HisOnderzoekModel> onderzoeken = new HashSet<>(singletonList(onderzoekModel));

        // when
        CollectionUtils.filter(onderzoeken, materieleHistorieVanafPredikaat);

        // then
        assertThat(onderzoeken.size(), is(0));
    }

    @Test
    public void onderzoekHistorieWordtGefilterdOpGeldigeTijdVervalEnOngeldigeDatumEinde() {
        // given
        final HisOnderzoekModel onderzoekModel = bouwHisOnderzoekModelMetRestricties(new DatumEvtDeelsOnbekendAttribuut(19900224),
            DatumTijdAttribuut.bouwDatumTijd(2020, 1, 1));
        final Set<HisOnderzoekModel> onderzoeken = new HashSet<>(singletonList(onderzoekModel));

        // when
        CollectionUtils.filter(onderzoeken, materieleHistorieVanafPredikaat);

        // then
        assertThat(onderzoeken.size(), is(0));
    }

    @Test
    public void onderzoekHistorieWordtGefilterdOpOngeldigeTijdVervalEnGeldigeDatumEinde() {
        // given
        final HisOnderzoekModel onderzoekModel = bouwHisOnderzoekModelMetRestricties(new DatumEvtDeelsOnbekendAttribuut(20200224),
            DatumTijdAttribuut.bouwDatumTijd(1990, 1, 1));
        final Set<HisOnderzoekModel> onderzoeken = new HashSet<>(singletonList(onderzoekModel));

        // when
        CollectionUtils.filter(onderzoeken, materieleHistorieVanafPredikaat);

        // then
        assertThat(onderzoeken.size(), is(0));
    }

    @Test
    public void onderzoekHistorieWordtNietGefilterdAlsGeenRestrictiesAanwezigZijn() {
        // given
        final HisOnderzoekModel onderzoekModel = bouwHisOnderzoekModelMetRestricties(null, null);
        final Set<HisOnderzoekModel> onderzoeken = new HashSet<>(singletonList(onderzoekModel));

        // when
        CollectionUtils.filter(onderzoeken, materieleHistorieVanafPredikaat);

        // then
        assertThat(onderzoeken.size(), is(1));
    }

    private HisOnderzoekModel bouwHisOnderzoekModelMetRestricties(final DatumEvtDeelsOnbekendAttribuut datumEinde,
                                                                  final DatumTijdAttribuut datumTijdVerval)
    {
        final OnderzoekHisVolledig onderzoekHisVolledig = new OnderzoekHisVolledigImpl();
        final OnderzoekStandaardGroepModel groep = new OnderzoekStandaardGroepModel(null, null, datumEinde, null, null);
        final ActieModel actieModelMock = mock(ActieModel.class);
        when(actieModelMock.getTijdstipRegistratie()).thenReturn(new DatumTijdAttribuut(new Date()));
        final HisOnderzoekModel onderzoekModel = new HisOnderzoekModel(onderzoekHisVolledig, groep, actieModelMock);
        onderzoekModel.getFormeleHistorie().setDatumTijdVerval(datumTijdVerval);
        return onderzoekModel;
    }
}
