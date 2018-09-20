/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.util.hisvolledig.kern.HisOuderOuderlijkGezagBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class FamilieRechtelijkeBetrekkingViewTest {

    private PersoonHisVolledigImpl persoon;

    @Before
    public void creeerScenario() {
        persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        RelatieHisVolledigImpl relatie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
        relatie.setBetrokkenheden(null);

        HisOuderOuderlijkGezagModel gezag1 =
                HisOuderOuderlijkGezagBuilder.defaultValues().heeftGezag(JaNeeAttribuut.JA)
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19700101))
                        .datumTijdRegistratie(DatumTijdAttribuut.datumTijd(1970, 1, 1))
                        .datumTijdVerval(DatumTijdAttribuut.datumTijd(1980, 1, 1)).build();

        HisOuderOuderlijkGezagModel gezag2 =
                HisOuderOuderlijkGezagBuilder.defaultValues().heeftGezag(JaNeeAttribuut.JA)
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19700101))
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800101))
                        .datumTijdRegistratie(DatumTijdAttribuut.datumTijd(1980, 1, 1)).build();

        HisOuderOuderlijkGezagModel gezag3 =
                HisOuderOuderlijkGezagBuilder.defaultValues().heeftGezag(JaNeeAttribuut.NEE)
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800101))
                        .datumTijdRegistratie(DatumTijdAttribuut.datumTijd(1980, 1, 1))
                        .datumTijdVerval(DatumTijdAttribuut.datumTijd(1990, 1, 1)).build();

        HisOuderOuderlijkGezagModel gezag4 =
                HisOuderOuderlijkGezagBuilder.defaultValues().heeftGezag(JaNeeAttribuut.NEE)
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19800101))
                        .eindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19900101))
                        .datumTijdRegistratie(DatumTijdAttribuut.datumTijd(1990, 1, 1)).build();

        HisOuderOuderlijkGezagModel gezag5 =
                HisOuderOuderlijkGezagBuilder.defaultValues().heeftGezag(JaNeeAttribuut.JA)
                        .aanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(19900101))
                        .datumTijdRegistratie(DatumTijdAttribuut.datumTijd(1990, 1, 1)).build();

        OuderHisVolledigImpl betrokkenheid = new OuderHisVolledigImpl(relatie, persoon);

        // Zet direct de interne set, aangezien we alle datum / tijd velden hierboven al goed gezet hebben.
        Set<HisOuderOuderlijkGezagModel> interneSet =
                new HashSet<HisOuderOuderlijkGezagModel>(Arrays.asList(gezag1, gezag2, gezag3, gezag4, gezag5));
        ReflectionTestUtils.setField(betrokkenheid.getOuderOuderlijkGezagHistorie(), "interneSet", interneSet);

        persoon.setBetrokkenheden(new HashSet<BetrokkenheidHisVolledigImpl>(Arrays.asList(betrokkenheid)));
    }

    @Test
    public void kennisIn1971Over1971() {
        // given
        DatumTijdAttribuut formeleTijdstip = DatumTijdAttribuut.datumTijd(1971, 1, 1);
        DatumAttribuut materieleDatum = new DatumAttribuut(19710101);

        PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleDatum);

        // when
        Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        Ouder betrokkenheid = (Ouder) betrokkenheden.iterator().next();

        // TODO Tijdelijk niet onderstuend in views.
        // assertThat(betrokkenheid.getRol(), is(SoortBetrokkenheid.OUDER));
        assertThat(betrokkenheid.getOuderlijkGezag().getIndicatieOuderHeeftGezag(), is(JaNeeAttribuut.JA));
    }

    @Test
    public void kennisIn1981Over1971() {
        // given
        DatumTijdAttribuut formeleTijdstip = DatumTijdAttribuut.datumTijd(1981, 1, 1);
        DatumAttribuut materieleTijdstip = new DatumAttribuut(19710101);

        PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleTijdstip);

        // when
        Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        Ouder betrokkenheid = (Ouder) betrokkenheden.iterator().next();

        assertThat(betrokkenheid.getOuderlijkGezag().getIndicatieOuderHeeftGezag(), is(JaNeeAttribuut.JA));
    }

    @Test
    public void kennisIn1981Over1981() {
        // given
        DatumTijdAttribuut formeleTijdstip = DatumTijdAttribuut.datumTijd(1981, 1, 1);
        DatumAttribuut materieleDatum = new DatumAttribuut(19810101);

        PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleDatum);

        // when
        Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        Ouder betrokkenheid = (Ouder) betrokkenheden.iterator().next();

        assertThat(betrokkenheid.getOuderlijkGezag().getIndicatieOuderHeeftGezag(), is(JaNeeAttribuut.NEE));
    }

    @Test
    public void kennisIn1991Over1971() {
        // given
        DatumTijdAttribuut formeleTijdstip = DatumTijdAttribuut.datumTijd(1991, 1, 1);
        DatumAttribuut materieleTijdstip = new DatumAttribuut(19710101);

        PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleTijdstip);

        // when
        Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        Ouder betrokkenheid = (Ouder) betrokkenheden.iterator().next();
        assertThat(betrokkenheid.getOuderlijkGezag().getIndicatieOuderHeeftGezag(), is(JaNeeAttribuut.JA));
    }

    @Test
    public void kennisIn1991Over1991() {
        // given
        DatumTijdAttribuut formeleTijdstip = DatumTijdAttribuut.datumTijd(1991, 1, 1);
        DatumAttribuut materieleDatum = new DatumAttribuut(19910101);

        PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleDatum);

        // when
        Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        Ouder betrokkenheid = (Ouder) betrokkenheden.iterator().next();
        assertThat(betrokkenheid.getOuderlijkGezag().getIndicatieOuderHeeftGezag(), is(JaNeeAttribuut.JA));
    }

    @Test
    public void kennisInNuOverDitMoment() {
        // given
        PersoonView view = new PersoonView(persoon);

        // when
        Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        Ouder betrokkenheid = (Ouder) betrokkenheden.iterator().next();
        assertThat(betrokkenheid.getOuderlijkGezag().getIndicatieOuderHeeftGezag(), is(JaNeeAttribuut.JA));
    }
}
