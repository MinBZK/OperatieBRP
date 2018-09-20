/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview;

import static nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut.datumTijd;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Huwelijk;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.util.hisvolledig.kern.HisRelatieModelBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class HuwelijkViewTest {

    private PersoonHisVolledigImpl persoon;

    @Before
    public void creeerScenario() {
        persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final HuwelijkHisVolledigImpl relatie = new HuwelijkHisVolledigImpl();
        ReflectionTestUtils.setField(relatie, "soort", new SoortRelatieAttribuut(SoortRelatie.HUWELIJK));

        final HisRelatieModel huwelijk =
                HisRelatieModelBuilder.defaultValues()
                        .datumAanvang(new DatumEvtDeelsOnbekendAttribuut(19800101)).woonplaatsAanvang("Amsterdam")
                        .datumTijdRegistratie(datumTijd(1980, 1, 1, 12, 0)).datumTijdVerval(datumTijd(2000, 1, 1))
                        .build();

        final HisRelatieModel huwelijkCorrectie =
            HisRelatieModelBuilder.kopieer(huwelijk).woonplaatsAanvang("Rotterdam")
                        .datumTijdRegistratie(datumTijd(2000, 1, 1)).datumTijdVerval(datumTijd(2001, 7, 1)).build();

        final HisRelatieModel scheiding =
            HisRelatieModelBuilder.kopieer(huwelijkCorrectie).woonplaatsEinde("Rotterdam")
                        .datumEinde(new DatumEvtDeelsOnbekendAttribuut(20010701))
                        .datumTijdRegistratie(datumTijd(2001, 7, 1)).build();

        // Zet direct de interne set, aangezien we alle datum / tijd velden hierboven al goed gezet hebben.
        final Set<HisRelatieModel> interneSet = new HashSet<>(Arrays.asList(huwelijk, huwelijkCorrectie, scheiding));
        ReflectionTestUtils.setField(relatie.getRelatieHistorie(), "interneSet", interneSet);

        relatie.setBetrokkenheden(null);

        final BetrokkenheidHisVolledigImpl partner =
                new PartnerHisVolledigImpl(new HuwelijkHisVolledigImpl(),
                                           new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE)));
        ReflectionTestUtils.setField(partner, "rol", new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.PARTNER));
        ReflectionTestUtils.setField(partner, "persoon", persoon);
        ReflectionTestUtils.setField(partner, "relatie", relatie);

        persoon.setBetrokkenheden(new HashSet<>(Arrays.asList(partner)));
    }

    @Test
    public void kennisIn1999Over1985() {
        // given
        final DatumTijdAttribuut formeleTijdstip = datumTijd(1999, 1, 1);
        final DatumAttribuut materieleTijdstip = new DatumAttribuut(19850101);

        final PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleTijdstip);

        // when
        final Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        final Betrokkenheid betrokkenheid = betrokkenheden.iterator().next();

        assertThat(betrokkenheid.getRelatie().getStandaard().getWoonplaatsnaamAanvang().getWaarde(), is("Amsterdam"));
        assertThat(betrokkenheid.getRelatie().getStandaard().getDatumAanvang().getWaarde(), equalTo(19800101));
        assertThat(betrokkenheid.getRelatie().getStandaard().getDatumEinde(), nullValue());
    }

    @Test
    public void kennisIn2003Over1985() {
        // given
        final DatumTijdAttribuut formeleTijdstip = datumTijd(2003, 1, 1);
        final DatumAttribuut materieleTijdstip = new DatumAttribuut(19850101);

        final PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleTijdstip);

        // when
        final Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        final Betrokkenheid betrokkenheid = betrokkenheden.iterator().next();

        assertThat(betrokkenheid.getRelatie().getStandaard().getWoonplaatsnaamAanvang().getWaarde(), is("Rotterdam"));
        assertThat(betrokkenheid.getRelatie().getStandaard().getDatumAanvang().getWaarde(), equalTo(19800101));
        assertThat(betrokkenheid.getRelatie().getStandaard().getDatumEinde().getWaarde(), equalTo(20010701));
    }

    @Test
    public void kennisInJanuari2001Over1985() {
        // given
        final DatumTijdAttribuut formeleTijdstip = datumTijd(2001, 1, 1);
        final DatumAttribuut materieleTijdstip = new DatumAttribuut(19850101);

        final PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleTijdstip);

        // when
        final Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        final Betrokkenheid betrokkenheid = betrokkenheden.iterator().next();

        assertThat(betrokkenheid.getRelatie().getStandaard().getWoonplaatsnaamAanvang().getWaarde(), is("Rotterdam"));
        assertThat(betrokkenheid.getRelatie().getStandaard().getDatumAanvang().getWaarde(), equalTo(19800101));
        assertThat(betrokkenheid.getRelatie().getStandaard().getDatumEinde(), nullValue());
    }

    @Test
    public void kennisIn2003Over2003() {
        // given
        final DatumTijdAttribuut formeleTijdstip = datumTijd(2003, 1, 1);
        final DatumAttribuut materieleTijdstip = new DatumAttribuut(20030101);

        final PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleTijdstip);

        // when
        final Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        final Betrokkenheid betrokkenheid = betrokkenheden.iterator().next();

        assertThat(betrokkenheid.getRelatie().getStandaard().getWoonplaatsnaamAanvang().getWaarde(), is("Rotterdam"));
        assertThat(betrokkenheid.getRelatie().getStandaard().getDatumAanvang().getWaarde(), equalTo(19800101));
        assertThat(betrokkenheid.getRelatie().getStandaard().getDatumEinde().getWaarde(), equalTo(20010701));
    }

    @Test
    public void eenTweedeHuwelijkWordtGesloten() {
        // huwelijk 2
        final HuwelijkHisVolledigImpl relatie2 = new HuwelijkHisVolledigImpl();
        ReflectionTestUtils.setField(relatie2, "soort", new SoortRelatieAttribuut(SoortRelatie.HUWELIJK));

        final HisRelatieModel huwelijk2 =
            HisRelatieModelBuilder.defaultValues()
                        .datumAanvang(new DatumEvtDeelsOnbekendAttribuut(20100101)).woonplaatsAanvang("Utrecht")
                        .datumTijdRegistratie(datumTijd(2010, 1, 1)).build();

        final Set<HisRelatieModel> historie = (Set<HisRelatieModel>) ReflectionTestUtils.getField(relatie2.getRelatieHistorie(), "interneSet");
        historie.add(huwelijk2);
        relatie2.setBetrokkenheden(null);

        final BetrokkenheidHisVolledigImpl partner2 =
                new PartnerHisVolledigImpl(new HuwelijkHisVolledigImpl(), new PersoonHisVolledigImpl(
                        new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE)));
        ReflectionTestUtils.setField(partner2, "rol", new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.PARTNER));
        ReflectionTestUtils.setField(partner2, "persoon", persoon);
        ReflectionTestUtils.setField(partner2, "relatie", relatie2);

        persoon.getBetrokkenheden().add(partner2);

        // given
        final DatumTijdAttribuut formeleTijdstip = datumTijd(2011, 1, 1);
        final DatumAttribuut materieleTijdstip = new DatumAttribuut(20100801);

        final PersoonView view = new PersoonView(persoon, formeleTijdstip, materieleTijdstip);

        // when
        final Collection<? extends Betrokkenheid> betrokkenheden = view.getBetrokkenheden();

        // then
        assertThat(betrokkenheden.size(), is(2));

        final List<String> data = new ArrayList<>();
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            data.add(print((Huwelijk) betrokkenheid.getRelatie()));
        }

        assertThat(data, hasItems("19800101-20010701 (Rotterdam)", "20100101- (Utrecht)"));
    }

    private String print(final Huwelijk huwelijk) {
        final StringBuilder sb = new StringBuilder();

        final DatumEvtDeelsOnbekendAttribuut datumAanvang = huwelijk.getStandaard().getDatumAanvang();
        final DatumEvtDeelsOnbekendAttribuut datumEinde = huwelijk.getStandaard().getDatumEinde();

        if (datumAanvang != null) {
            sb.append(datumAanvang.getWaarde()).append("-");
        }
        if (datumEinde != null) {
            sb.append(datumEinde.getWaarde());
        }
        sb.append(" (").append(huwelijk.getStandaard().getWoonplaatsnaamAanvang().getWaarde()).append(")");

        return sb.toString();
    }
}
