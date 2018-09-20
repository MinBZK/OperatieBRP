/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview;

import static nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut.datumTijd;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.util.hisvolledig.kern.HisPersoonVoornaamModelBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonViewTest {

    private PersoonHisVolledigImpl persoon;

    @Before
    public void creeerScenario() {
        SortedSet<PersoonVoornaamHisVolledigImpl> voornamen = getPersoonVoornaamHisVolledigLijst();

        persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setVoornamen(voornamen);
    }

    private SortedSet<PersoonVoornaamHisVolledigImpl> getPersoonVoornaamHisVolledigLijst() {
        SortedSet<PersoonVoornaamHisVolledigImpl> voornamen =
                new TreeSet<PersoonVoornaamHisVolledigImpl>(new VolgnummerComparator());

        HisPersoonVoornaamModel naamPeter =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Peter")
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19700421))
                        .datumTijdRegistratie(datumTijd(1970, 4, 21)).datumTijdVerval(datumTijd(1991, 1, 1)).build();

        HisPersoonVoornaamModel naamPetra =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Petra")
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19700421))
                        .datumTijdRegistratie(datumTijd(1991, 1, 1)).datumTijdVerval(datumTijd(2010, 1, 1)).build();

        HisPersoonVoornaamModel naamPetraVroeger =
                HisPersoonVoornaamModelBuilder.kopieer(naamPetra)
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19950101))
                        .datumTijdRegistratie(datumTijd(2010, 1, 1)).build();

        HisPersoonVoornaamModel naamPete =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Pete")
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19950101))
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20050101))
                        .datumTijdRegistratie(datumTijd(2010, 1, 1)).build();

        HisPersoonVoornaamModel naamPetraNu =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Petra")
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20050101))
                        .datumTijdRegistratie(datumTijd(2010, 1, 1)).build();

        PersoonVoornaamHisVolledigImpl naam1 =
                new PersoonVoornaamHisVolledigImpl(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(
                        SoortPersoon.INGESCHREVENE)), new VolgnummerAttribuut(1));

        // Zet direct de interne set, aangezien we alle datum / tijd velden hierboven al goed gezet hebben.
        Set<HisPersoonVoornaamModel> interneSet =
                new HashSet<HisPersoonVoornaamModel>(Arrays.asList(naamPeter, naamPetra, naamPetraVroeger, naamPete,
                        naamPetraNu));
        ReflectionTestUtils.setField(naam1.getPersoonVoornaamHistorie(), "interneSet", interneSet);

        HisPersoonVoornaamModel naamKelly =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Kelly")
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19700421))
                        .datumTijdRegistratie(datumTijd(1970, 4, 21)).datumTijdVerval(datumTijd(2000, 1, 1)).build();

        HisPersoonVoornaamModel naamKellyOud =
                HisPersoonVoornaamModelBuilder.kopieer(naamKelly)
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20000101))
                        .datumTijdRegistratie(datumTijd(2000, 1, 1)).build();

        HisPersoonVoornaamModel naamKevin =
                HisPersoonVoornaamModelBuilder.defaultValues().naam("Kevin")
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20000101))
                        .datumTijdRegistratie(datumTijd(2000, 1, 1)).build();

        PersoonVoornaamHisVolledigImpl naam2 =
                new PersoonVoornaamHisVolledigImpl(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(
                        SoortPersoon.INGESCHREVENE)), new VolgnummerAttribuut(2));

        // Zet direct de interne set, aangezien we alle datum / tijd velden hierboven al goed gezet hebben.
        Set<HisPersoonVoornaamModel> interneSet2 =
                new HashSet<HisPersoonVoornaamModel>(Arrays.asList(naamKelly, naamKellyOud, naamKevin));
        ReflectionTestUtils.setField(naam2.getPersoonVoornaamHistorie(), "interneSet", interneSet2);

        voornamen.add(naam1);
        voornamen.add(naam2);
        return voornamen;
    }

    @Test
    public void watWetenWeIn1980Over1971() {
        // given
        DatumTijdAttribuut formeleMoment = datumTijd(1980, 1, 1, 8, 0);
        DatumAttribuut materieleMoment = new DatumAttribuut(19710101);

        // when
        PersoonView view = new PersoonView(persoon, formeleMoment, materieleMoment);
        Collection<? extends PersoonVoornaam> voornamen = view.getVoornamen();

        // then
        List<String> namen = new ArrayList<String>();
        for (PersoonVoornaam voornaam : voornamen) {
            namen.add(voornaam.getStandaard().getNaam().getWaarde());
        }

        assertThat(namen, hasItems("Peter", "Kelly"));
    }

    @Test
    public void watWetenWeIn1992Over1971() {
        // given
        DatumTijdAttribuut formeleMoment = datumTijd(1992, 1, 1, 8, 0);
        DatumAttribuut materieleMoment = new DatumAttribuut(19710101);

        // when
        PersoonView view = new PersoonView(persoon, formeleMoment, materieleMoment);
        Collection<? extends PersoonVoornaam> voornamen = view.getVoornamen();

        // then
        List<String> namen = new ArrayList<String>();
        for (PersoonVoornaam voornaam : voornamen) {
            namen.add(voornaam.getStandaard().getNaam().getWaarde());
        }

        assertThat(namen, hasItems("Petra", "Kelly"));
    }

    @Test
    public void watWetenWeIn2011Over2001() {
        // given
        DatumTijdAttribuut formeleMoment = datumTijd(2011, 1, 1, 8, 0);
        DatumAttribuut materieleMoment = new DatumAttribuut(20010101);

        // when
        PersoonView view = new PersoonView(persoon, formeleMoment, materieleMoment);
        Collection<? extends PersoonVoornaam> voornamen = view.getVoornamen();

        // then
        List<String> namen = new ArrayList<String>();
        for (PersoonVoornaam voornaam : voornamen) {
            namen.add(voornaam.getStandaard().getNaam().getWaarde());
        }

        assertThat(namen, hasItems("Pete", "Kevin"));
    }

    @Test
    public void watWetenWeIn2011Over2006() {
        // given
        DatumTijdAttribuut formeleMoment = datumTijd(2011, 1, 1, 8, 0);
        DatumAttribuut materieleMoment = new DatumAttribuut(20060101);

        // when
        PersoonView view = new PersoonView(persoon, formeleMoment, materieleMoment);
        Collection<? extends PersoonVoornaam> voornamen = view.getVoornamen();

        // then
        List<String> namen = new ArrayList<String>();
        for (PersoonVoornaam voornaam : voornamen) {
            namen.add(voornaam.getStandaard().getNaam().getWaarde());
        }

        assertThat(namen, hasItems("Petra", "Kevin"));
    }

    @Test
    public void heeftVerstrekkingsbeperkingAlsIndicatieVolledigeVerstrekkingsbeperkingEnSpecifiekeBeperking() {
        PersoonHisVolledig persoon = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        PersoonView view = new PersoonView(persoon, DatumTijdAttribuut.nu());

        assertThat(view.heeftVerstrekkingsbeperking(), is(true));
    }
}
