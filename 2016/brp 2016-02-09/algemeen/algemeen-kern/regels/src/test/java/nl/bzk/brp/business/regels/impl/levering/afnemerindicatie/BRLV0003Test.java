/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.afnemerindicatie;

import nl.bzk.brp.business.regels.context.AfnemerindicatieRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroep;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;


public class BRLV0003Test {

    private static final Integer INW = 999;

    private BRLV0003 brlv0003;

    @Before
    public void init() {
        brlv0003 = new BRLV0003();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0003, brlv0003.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AfnemerindicatieRegelContext.class, brlv0003.getContextType());
    }

    @Test
    public void nieuweSituatieHeeftCorrecteIndicatie() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(INW).maak();

        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), INW);

        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE.getWaarde(), bestaandeSituatie,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, leveringsautorisatie, null, null);

        final boolean resultaat = brlv0003.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieBevatBestaandeIndicatie() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(INW).maak();

        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), INW);

        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), bestaandeSituatie,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, leveringsautorisatie, null, null);

        final boolean resultaat = brlv0003.valideer(context);
        Assert.assertEquals(BRLV0001.INVALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieBevatIndicatieZonderLeveringsautorisatie() {
        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), INW);

        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), bestaandeSituatie,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null, null, null);

        final boolean resultaat = brlv0003.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void bestaandeSituatieBevatIndicatieZonderLeveringsautorisatie() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(INW).maak();
        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), null);

        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), bestaandeSituatie,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, leveringsautorisatie, null, null);

        final boolean resultaat = brlv0003.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieEnBestaandeSituatieBevatIndicatieZonderLeveringsautorisatie() {
        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), null);

        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), bestaandeSituatie,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null, null, null);

        final boolean resultaat = brlv0003.valideer(context);
        Assert.assertEquals(BRLV0001.INVALIDE, resultaat);
    }

    @Test
    public void contextZonderSoortAdministratieveHandeling() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), null,
                        SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE, null, null, null);

        final boolean resultaat = brlv0003.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    private PersoonView maakHuidigeSituatie(final Partij afnemer, final Integer leveringsautorisatieId) {
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        Leveringsautorisatie leveringsautorisatie = null;

        if (leveringsautorisatieId != null) {
            leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metId(leveringsautorisatieId).maak();
        }

        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE).maak();
        final PersoonAfnemerindicatieHisVolledigImpl afnemerIndicatie =
                new PersoonAfnemerindicatieHisVolledigImplBuilder(persoonHisVolledig, afnemer, leveringsautorisatie)
                        .build();
        if (leveringsautorisatie == null) {
            // Veld op null zetten, omdat de builder er altijd een wrapper omheen zet.
            ReflectionTestUtils.setField(afnemerIndicatie, "leveringsautorisatie", null);
        }
        final HisPersoonAfnemerindicatieModel hisPersoonAfnemerindicatieModel =
                new HisPersoonAfnemerindicatieModel(afnemerIndicatie,
                        Mockito.mock(PersoonAfnemerindicatieStandaardGroep.class),
                        dienst, DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1));
        afnemerIndicatie.getPersoonAfnemerindicatieHistorie().voegToe(hisPersoonAfnemerindicatieModel);

        persoonHisVolledig.getAfnemerindicaties().add(afnemerIndicatie);

        return new PersoonView(persoonHisVolledig);
    }

}
