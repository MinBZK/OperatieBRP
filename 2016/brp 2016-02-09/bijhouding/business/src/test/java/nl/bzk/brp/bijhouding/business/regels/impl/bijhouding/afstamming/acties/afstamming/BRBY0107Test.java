/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0107Test {

    private static final String GESLACHTSNAAM = "Kindersen";
    private static final String GESLACHTSNAAM_AFWIJKEND = "Andersen";
    private BRBY0107 brby0107;

    private final ActieModel actieModel =
        new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                       new DatumEvtDeelsOnbekendAttribuut(
                           20120101), null, new DatumTijdAttribuut(new Date()), null);

    @Before
    public void init() {
        brby0107 = new BRBY0107();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0107, brby0107.getRegel());
    }

    @Test
    public void testKindAndereNaamDanBroerEnZus() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, true, false);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM_AFWIJKEND, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM_AFWIJKEND, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);
        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertTrue(((PersoonBericht) resultaat.get(0)).getSamengesteldeNaam().getGeslachtsnaamstam().getWaarde()
                                  .equals(GESLACHTSNAAM));
    }

    @Test
    public void testGeenMeldingOmdatKindZelfdeNaamHeeftAlsBroerEnZus() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, true, false);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);
        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Hier wordt een familie getest met een vader die niet ingeschreven is. Een niet ingeschreven persoon aan een
     * betrokkenheid is altijd een andere persoon object. Maar zolang de objecten dezelfde informatie bevat wordt
     * dat als eenzelfde persoon gezien. Dus de kinderen hebben een gezamenlijke vader.
     */
    @Test
    public void testKindAndereNaamDanBroerOfZusVaderNietIngeschreven() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, true, false);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM_AFWIJKEND, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM_AFWIJKEND, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);
        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertTrue(((PersoonBericht) resultaat.get(0)).getSamengesteldeNaam().getGeslachtsnaamstam().getWaarde()
                                  .equals(GESLACHTSNAAM));
    }

    /**
     * Hier wordt een familie getest met een vader die niet ingeschreven is. Een niet ingeschreven persoon aan een
     * betrokkenheid is altijd een andere persoon object. Maar zolang de objecten dezelfde informatie bevat wordt
     * dat als eenzelfde persoon gezien. Dus de kinderen hebben een gezamenlijke vader.
     */
    @Test
    public void testGeenMeldingOmdatKindZelfdeNaamHeeftAlsBroerEnZusVaderNietIngeschreven() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, true, false);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);
        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Hier wordt een familie getest met een vader die niet ingeschreven is. Een niet ingeschreven persoon aan een
     * betrokkenheid is altijd een andere persoon object. Maar zolang de objecten dezelfde informatie bevat wordt
     * dat als eenzelfde persoon gezien. Dus de kinderen hebben een gezamenlijke vader. Nu wijken de geslachtsnamen af
     * en dient er dus een melding op te treden.
     */
    @Test
    public void testWelMeldingOmdatKindNietZelfdeNaamHeeftAlsBroerEnZusVaderNietIngeschreven() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, true, false);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM_AFWIJKEND, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM_AFWIJKEND, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);
        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertTrue(((PersoonBericht) resultaat.get(0)).getSamengesteldeNaam().getGeslachtsnaamstam().getWaarde()
                                  .equals(GESLACHTSNAAM));
    }

    /**
     * Test een moeder kinderen van een niet gezamelijke vader. Dus kind heeft geen namen waarvan die kan afwijken.
     */
    @Test
    public void testGeenMeldingKinderenMetNietGezamelijkeVader() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder3 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, true, false);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM_AFWIJKEND, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM_AFWIJKEND, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder3, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder3, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);
        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGeenMeldingOmdatKindGeenNLNationaliteitHeeft() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, false, false);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM_AFWIJKEND, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM_AFWIJKEND, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);
        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGeenMeldingKindHeeftIndicatieNamenReeks() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, true, true);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM_AFWIJKEND, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM_AFWIJKEND, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);
        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testKindHeeftMeerDanTweeOuders() {
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonHisVolledigImpl ouder3 = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final PersoonHisVolledigImpl kind1 = maakKind(GESLACHTSNAAM, 1, true, false);
        final PersoonHisVolledigImpl kind2 = maakKind(GESLACHTSNAAM_AFWIJKEND, 2, true, false);
        final PersoonHisVolledigImpl kind3 = maakKind(GESLACHTSNAAM_AFWIJKEND, 3, true, false);

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind1, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind2, actieModel);
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind3, actieModel);

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind1);

        // Voegt extra ouder toe aan familierechtelijke betrekking
        final BetrokkenheidHisVolledigImpl extraOuder = new OuderHisVolledigImplBuilder(familie, ouder3)
            .nieuwOuderschapRecord(DatumAttribuut.vandaag().getWaarde(), null, DatumAttribuut.vandaag().getWaarde())
            .eindeRecord().build();
        familie.getBetrokkenheden().add(extraOuder);

        final FamilierechtelijkeBetrekkingView familieView
            = new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());

        final List<BerichtEntiteit> resultaat =
                brby0107.voerRegelUit(familieView, maakFamilierechtelijkeBetrekkingBericht());

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt een familierechtelijke betrekking bericht.
     *
     * @return familierechtelijke betrekking bericht
     */
    private FamilierechtelijkeBetrekkingBericht maakFamilierechtelijkeBetrekkingBericht() {
        final KindBericht kindBericht = new KindBericht();
        kindBericht.setCommunicatieID("kind.persoon");
        final PersoonBericht kindPersoonBericht = new PersoonBericht();
        final PersoonSamengesteldeNaamGroepBericht samengesteldenaam = new PersoonSamengesteldeNaamGroepBericht();
        final GeslachtsnaamstamAttribuut geslachtsnaam = new GeslachtsnaamstamAttribuut(GESLACHTSNAAM);
        samengesteldenaam.setGeslachtsnaamstam(geslachtsnaam);
        kindPersoonBericht.setSamengesteldeNaam(samengesteldenaam);
        kindBericht.setPersoon(kindPersoonBericht);

        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                new FamilierechtelijkeBetrekkingBericht();
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(kindBericht);

        return familierechtelijkeBetrekkingBericht;
    }

    /**
     * Maakt een kind persoon his volledig impl.
     *
     * @param geslachtsnaam the geslachtsnaam
     * @return the persoon his volledig impl
     */
    private PersoonHisVolledigImpl maakKind(final String geslachtsnaam, final Integer id,
                                            final boolean nederlandseNationaliteit, final boolean indicatieNamenreeks)
    {
        final PersoonNationaliteitHisVolledigImpl nationaliteit;

        if (nederlandseNationaliteit) {
            nationaliteit = new PersoonNationaliteitHisVolledigImplBuilder(
                    new Nationaliteit(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, null, new DatumEvtDeelsOnbekendAttribuut(20110101), null))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .eindeRecord().build();
        } else {
            nationaliteit = new PersoonNationaliteitHisVolledigImplBuilder(
                    new Nationaliteit(new NationaliteitcodeAttribuut("0002"), null, new DatumEvtDeelsOnbekendAttribuut(20110101), null))
                .nieuwStandaardRecord(20110101, null, 20110101)
                .eindeRecord().build();
        }

        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(20110101, null, 20110101)
                .geslachtsnaamstam(geslachtsnaam)
                .indicatieNamenreeks(indicatieNamenreeks)
                .eindeRecord()
                .voegPersoonNationaliteitToe(nationaliteit)
                .build();

        // Id wordt gebruikt voor vergelijken kinderen.
        ReflectionTestUtils.setField(kind, "iD", id);

        return kind;
    }
}
