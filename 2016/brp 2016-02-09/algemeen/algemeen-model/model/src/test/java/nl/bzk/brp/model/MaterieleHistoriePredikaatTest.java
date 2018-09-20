/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import static nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut.datumTijd;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.VolgnummerComparator;
import nl.bzk.brp.model.hisvolledig.GlazenbolException;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.util.hisvolledig.kern.HisPersoonVoornaamModelBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class MaterieleHistoriePredikaatTest {

    private Set<HisPersoonVoornaamModel> naamHistorie;

    @Before
    public void bouwVoornaamHistorie() {
        HisPersoonVoornaamModel piet =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Piet").datumTijdRegistratie(datumTijd(1960, 1, 1))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19600101))
                        .datumTijdVerval(datumTijd(2000, 1, 15)).build();

        HisPersoonVoornaamModel petra =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Petra")
                        .datumTijdRegistratie(datumTijd(2000, 1, 15))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19600101))
                        .datumTijdVerval(datumTijd(2010, 1, 1)).build();

        HisPersoonVoornaamModel pete =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Pete").datumTijdRegistratie(datumTijd(2010, 1, 1))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800108))
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19980110)).build();

        HisPersoonVoornaamModel petraOud =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Petra").datumTijdRegistratie(datumTijd(2010, 1, 1))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19600101))
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800108)).build();

        HisPersoonVoornaamModel petraRecent =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Petra").datumTijdRegistratie(datumTijd(2010, 1, 1))
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19980110)).build();

        naamHistorie = new HashSet<>(Arrays.asList(piet, petra, petraOud, pete, petraRecent));

        PersoonVoornaamHisVolledigImpl namen =
                new PersoonVoornaamHisVolledigImpl(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(
                        SoortPersoon.INGESCHREVENE)), new VolgnummerAttribuut(1));

        // Zet direct de interne set, aangezien we alle datum / tijd velden hierboven al goed gezet hebben.
        Set<HisPersoonVoornaamModel> interneSet =
                new HashSet<>(Arrays.asList(piet, petra, petraOud, pete, petraRecent));
        ReflectionTestUtils.setField(namen.getPersoonVoornaamHistorie(), "interneSet", interneSet);

        PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final SortedSet<PersoonVoornaamHisVolledigImpl> gesorteerdeSet = new TreeSet<>(new VolgnummerComparator());
        gesorteerdeSet.addAll(Arrays.asList(namen));
        persoon.setVoornamen(gesorteerdeSet);
    }

    @Test
    public void watWetenWeInNegentienHonderdNegentig() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(1990, 1, 1);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19900101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Piet"));
    }

    @Test
    public void watWetenWeIn2001Over1970() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2001, 1, 1, 12);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19700101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }

    @Test
    public void watWetenWeIn2011Over1970() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2011, 1, 1, 12);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19700101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }

    @Test
    public void watWetenWeIn2011Over1985() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2011, 1, 1, 12);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19850101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Pete"));
    }

    @Test
    public void watWetenWeIn2011Over1999() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2011, 1, 1, 12);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19990101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }

    @Test
    public void eindDatumOpPeilDatum() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2000, 1, 15);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19700101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }

    @Test
    public void aanvangDatumOpPeilDatum() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2000, 1, 16);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19600101);

        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);

        // when
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Petra"));
    }

    @Test(expected = GlazenbolException.class)
    public void doorsnedeInDeToekomstKanNiet() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(2050, 1, 15);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(19700101);

        // when, exception expected
        MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);
    }

    @Test
    public void vanuitDoorsnedeKijkenNaarDeToekomstKanWel() {
        // given
        final DatumTijdAttribuut formeelPeilmoment = datumTijd(1990, 1, 1);
        final DatumAttribuut materieelPeilmoment = new DatumAttribuut(20300101);

        // when
        final Predicate geldigOp = MaterieleHistoriePredikaat.geldigOp(formeelPeilmoment, materieelPeilmoment);
        final HisPersoonVoornaamModel voornaam = (HisPersoonVoornaamModel) CollectionUtils.find(naamHistorie, geldigOp);

        // then
        assertThat(voornaam.getNaam().getWaarde(), equalTo("Piet"));
    }
}
