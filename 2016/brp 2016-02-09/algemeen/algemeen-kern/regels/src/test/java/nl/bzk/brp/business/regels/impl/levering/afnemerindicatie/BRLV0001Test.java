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


public class BRLV0001Test {

    private static final Integer INW = 999;

    private BRLV0001 brlv0001;

    @Before
    public void init() {
        brlv0001 = new BRLV0001();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0001, brlv0001.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AfnemerindicatieRegelContext.class, brlv0001.getContextType());
    }

    @Test
    public void nieuweSituatieStoptBestaandeIndicatie() {
        final Leveringsautorisatie la =
            TestLeveringsautorisatieBuilder.maker().metId(INW).maak();
        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE.getWaarde(), INW);
        final AfnemerindicatieRegelContext context =
            new AfnemerindicatieRegelContext(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE.getWaarde(), bestaandeSituatie,
                SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE, la, null, null);

        final boolean resultaat = brlv0001.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieStoptBestaandeIndicatieZonderLeveringsautorisatie() {
        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), null);

        final AfnemerindicatieRegelContext context =
            new AfnemerindicatieRegelContext(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), bestaandeSituatie,
                SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE, null, null, null);

        final boolean resultaat = brlv0001.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieStoptNietBestaandeIndicatie() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metId(INW).maak();

        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE.getWaarde(), INW);

        final AfnemerindicatieRegelContext context =
            new AfnemerindicatieRegelContext(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), bestaandeSituatie,
                SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE, la, null, null);

        final boolean resultaat = brlv0001.valideer(context);
        Assert.assertEquals(BRLV0001.INVALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieStoptNietBestaandeIndicatieZonderLeveringsautorisaties() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metId(INW).maak();
        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE.getWaarde(), null);

        final AfnemerindicatieRegelContext context =
            new AfnemerindicatieRegelContext(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), bestaandeSituatie,
                SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE, la, null, null);

        final boolean resultaat = brlv0001.valideer(context);
        Assert.assertEquals(BRLV0001.INVALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieMetAboStoptBestaandeIndicatieZonderLeveringsautorisatie() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metId(INW).maak();
        final PersoonView bestaandeSituatie = maakHuidigeSituatie(
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), null);

        final AfnemerindicatieRegelContext context =
            new AfnemerindicatieRegelContext(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), bestaandeSituatie,
                SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE, la, null, null);

        final boolean resultaat = brlv0001.valideer(context);
        Assert.assertEquals(BRLV0001.INVALIDE, resultaat);
    }

    @Test
    public void contextZonderSoortAdministratieveHandeling() {
        final AfnemerindicatieRegelContext context =
            new AfnemerindicatieRegelContext(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), null,
                null, null, null, null);

        final boolean resultaat = brlv0001.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    private PersoonView maakHuidigeSituatie(final Partij afnemer, final Integer leveringsautorisatieId) {
        final PersoonHisVolledigImpl persoonHisVolledig =
            new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        Leveringsautorisatie la = null;
        if (leveringsautorisatieId != null) {
            la = TestLeveringsautorisatieBuilder.maker().metId(leveringsautorisatieId).maak();
        }
        Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.VERWIJDEREN_AFNEMERINDICATIE).maak();

        final PersoonAfnemerindicatieHisVolledigImpl afnemerIndicatie =
            new PersoonAfnemerindicatieHisVolledigImplBuilder(persoonHisVolledig, afnemer, la)
                .build();
        if (la == null) {
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
