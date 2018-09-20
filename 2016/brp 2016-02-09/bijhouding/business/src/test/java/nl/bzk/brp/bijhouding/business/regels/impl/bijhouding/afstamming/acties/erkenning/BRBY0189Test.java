/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.erkenning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0105M;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0189Test {

    private static final int DATUM = 20130101;
    public Integer datumGeboorteKindJongerDanZevenInt;
    public Integer datumGeboorteKindOuderDanZevenInt;

    private BRBY0189 brby0189;

    @Mock
    private BRBY0105M brby0105M;

    private PersoonHisVolledigImpl ouder;
    private FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht;

    private final String communicatieIdKind = "99";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        final DatumAttribuut datumGeboorteKindJongerDanZeven = DatumAttribuut.vandaag();
        datumGeboorteKindJongerDanZeven.voegJaarToe(-4);
        datumGeboorteKindJongerDanZevenInt = datumGeboorteKindJongerDanZeven.getWaarde();

        final DatumAttribuut datumGeboorteKindOuderDanZeven = DatumAttribuut.vandaag();
        datumGeboorteKindOuderDanZeven.voegJaarToe(-10);
        datumGeboorteKindOuderDanZevenInt = datumGeboorteKindOuderDanZeven.getWaarde();

        brby0189 = new BRBY0189();
        ReflectionTestUtils.setField(brby0189, "brby0105M", brby0105M);

        familierechtelijkeBetrekkingBericht = new FamilierechtelijkeBetrekkingBericht();

        final List<BetrokkenheidBericht> betrokkenheden = maakBetrokkenhedenBericht();
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(betrokkenheden);

        //BRBY0105 moet afgaan:
        Mockito.when(brby0105M.voerRegelUit(
                Matchers.any(FamilierechtelijkeBetrekkingView.class),
                Matchers.any(FamilierechtelijkeBetrekkingBericht.class))).thenReturn(
                Arrays.<BerichtEntiteit>asList(new PersoonBericht()));

        ouder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0189, brby0189.getRegel());
    }

    @Test
    public void testKindJongerDanZeven() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               DATUM), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(DATUM)
                .datumGeboorte(datumGeboorteKindJongerDanZevenInt)
                .eindeRecord().build();

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder, null, kind, DATUM, actie);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                new FamilierechtelijkeBetrekkingView(
                        RelatieTestUtil
                                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                        DatumTijdAttribuut.nu(),
                        DatumAttribuut.vandaag()
            );


        final List<BerichtEntiteit> resultaat = brby0189.voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                                                familierechtelijkeBetrekkingBericht);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertEquals("99", resultaat.get(0).getCommunicatieID());
    }

    @Test
    public void testKindOuderDanZeven() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               DATUM), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(DATUM)
                .datumGeboorte(datumGeboorteKindOuderDanZevenInt)
                .eindeRecord().build();

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder, null, kind, DATUM, actie);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                new FamilierechtelijkeBetrekkingView(
                        RelatieTestUtil
                                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                        DatumTijdAttribuut.nu(),
                        DatumAttribuut.vandaag()
            );

        final List<BerichtEntiteit> resultaat = brby0189.voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                                                familierechtelijkeBetrekkingBericht);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testBrby0105mGaatNietAf() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               DATUM), null, new DatumTijdAttribuut(new Date()), null);

        Mockito.when(brby0105M.voerRegelUit(
                Matchers.any(FamilierechtelijkeBetrekkingView.class),
                Matchers.any(FamilierechtelijkeBetrekkingBericht.class))).thenReturn(
                Collections.<BerichtEntiteit>emptyList());

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(DATUM)
                .datumGeboorte(datumGeboorteKindJongerDanZevenInt)
                .eindeRecord().build();

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder, null, kind, DATUM, actie);

        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekkingHisVolledigView =
                new FamilierechtelijkeBetrekkingView(
                        RelatieTestUtil
                                .haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                        DatumTijdAttribuut.nu(),
                        DatumAttribuut.vandaag()
            );

        final List<BerichtEntiteit> resultaat = brby0189.voerRegelUit(familierechtelijkeBetrekkingHisVolledigView,
                                                                familierechtelijkeBetrekkingBericht);
        Assert.assertEquals(0, resultaat.size());
    }

    private List<BetrokkenheidBericht> maakBetrokkenhedenBericht() {
        final List<BetrokkenheidBericht> betrokkenheden = new ArrayList<>();
        final KindBericht kindBericht = new KindBericht();
        final PersoonBericht kindPersoonBericht = new PersoonBericht();
        kindPersoonBericht.setCommunicatieID(communicatieIdKind);
        kindBericht.setPersoon(kindPersoonBericht);
        betrokkenheden.add(kindBericht);
        return betrokkenheden;
    }

}
