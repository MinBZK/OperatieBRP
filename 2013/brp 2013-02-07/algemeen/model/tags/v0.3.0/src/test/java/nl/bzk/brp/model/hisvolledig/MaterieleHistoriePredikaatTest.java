/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import static nl.bzk.brp.model.hisvolledig.util.DatumTijdBuilder.bouwDatumTijd;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.util.HisPersoonVoornaamModelBuilder;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;

public class MaterieleHistoriePredikaatTest {

    private Set<HisPersoonVoornaamModel> naamHistorie;

    @Before
    public void bouwVoornaamHistorie() {


        HisPersoonVoornaamModel piet =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Piet")
                        .datumTijdRegistratie(bouwDatumTijd(1980, 1, 1, 0, 0, 0))
                        .aanvangGeldigheid(new Datum(19800101))
                        .datumTijdVerval(bouwDatumTijd(2020, 1, 15, 0, 0, 0))
                        .build();

        HisPersoonVoornaamModel petra =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Petra")
                        .datumTijdRegistratie(bouwDatumTijd(2020, 1, 15, 0, 0, 0))
                        .aanvangGeldigheid(new Datum(19800101))
                        .datumTijdVerval(bouwDatumTijd(2030, 1, 1, 0, 0, 0))
                        .build();

        HisPersoonVoornaamModel pete =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Pete")
                        .datumTijdRegistratie(bouwDatumTijd(2030, 1, 1, 0, 0, 0))
                        .aanvangGeldigheid(new Datum(20000108))
                        .eindeGeldigheid(new Datum(20180110))
                        .build();

        HisPersoonVoornaamModel petraOud =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Petra")
                        .datumTijdRegistratie(bouwDatumTijd(2030, 1, 1, 0, 0, 0))
                        .aanvangGeldigheid(new Datum(19800101))
                        .eindeGeldigheid(new Datum(20000108))
                        .build();

        HisPersoonVoornaamModel petraRecent =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Petra")
                        .datumTijdRegistratie(bouwDatumTijd(2030, 1, 1, 0, 0, 0))
                        .aanvangGeldigheid(new Datum(20180110))
                        .build();


        naamHistorie = new HashSet<HisPersoonVoornaamModel>(Arrays.asList(piet, petra, petraOud, pete, petraRecent));

        PersoonVoornaamHisVolledig namen = new PersoonVoornaamHisVolledig();
        namen.setHisPersoonVoornaamLijst(naamHistorie);

        PersoonHisVolledig persoon = new PersoonHisVolledig();
        persoon.setVoornamen(new HashSet<PersoonVoornaamHisVolledig>(Arrays.asList(namen)));
    }


    @Test
    public void watWetenWeInTweeDuizendTien() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2010, 1, 1, 0, 0, 0);
        final Datum materieelPeilmoment = new Datum(20100101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Piet"));
    }

    @Test
    public void watWetenWeIn2021Over1990() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2021, 1, 1, 12, 0, 0);
        final Datum materieelPeilmoment = new Datum(19900101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }

    @Test
    public void watWetenWeIn2031Over1990() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2031, 1, 1, 12, 0, 0);
        final Datum materieelPeilmoment = new Datum(19900101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }

    @Test
    public void watWetenWeIn2031Over2005() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2031, 1, 1, 12, 0, 0);
        final Datum materieelPeilmoment = new Datum(20050101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Pete"));
    }

    @Test
    public void watWetenWeIn2031Over2019() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2031, 1, 1, 12, 0, 0);
        final Datum materieelPeilmoment = new Datum(20190101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }

    @Test
    public void eindDatumOpPeilDatum() {
        // given
        final DatumTijd formeelPeilmoment = bouwDatumTijd(2020, 1, 15, 0, 0, 0);
        final Datum materieelPeilmoment = new Datum(19900101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }
}
