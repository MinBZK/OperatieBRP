/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.conversie.mutatie.MutatieConverteerder;
import nl.bzk.brp.levering.lo3.conversie.persoon.PersoonConverteerder;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.brp.levering.lo3.filter.MutatieBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.Ng01BerichtFilter;
import nl.bzk.brp.levering.lo3.filter.ResyncBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.Wa11BerichtFilter;
import nl.bzk.brp.levering.lo3.format.Ag01Formatter;
import nl.bzk.brp.levering.lo3.format.Ag11Formatter;
import nl.bzk.brp.levering.lo3.format.Ag21Formatter;
import nl.bzk.brp.levering.lo3.format.Ag31Formatter;
import nl.bzk.brp.levering.lo3.format.Formatter;
import nl.bzk.brp.levering.lo3.format.Gv01Formatter;
import nl.bzk.brp.levering.lo3.format.Gv02Formatter;
import nl.bzk.brp.levering.lo3.format.Ng01Formatter;
import nl.bzk.brp.levering.lo3.format.Wa11Formatter;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BerichtFactoryImplTest {
    @Mock
    private IstTabelRepository istTabelRepository;

    private final PersoonConverteerder persoonConverteerder = new PersoonConverteerder();
    private final MutatieConverteerder mutatieConverteerder = new MutatieConverteerder();

    private final VulBerichtFilter vulFilter = new VulBerichtFilter();
    private final ResyncBerichtFilter resyncFilter = new ResyncBerichtFilter();
    private final MutatieBerichtFilter mutatieFilter = new MutatieBerichtFilter();
    private final Ng01BerichtFilter ng01Filter = new Ng01BerichtFilter();
    private final Wa11BerichtFilter wa11Filter = new Wa11BerichtFilter();

    private final Ag01Formatter ag01Formatter = new Ag01Formatter();
    private final Ag11Formatter ag11Formatter = new Ag11Formatter();
    private final Ag21Formatter ag21Formatter = new Ag21Formatter();
    private final Ag31Formatter ag31Formatter = new Ag31Formatter();
    private final Gv01Formatter gv01Formatter = new Gv01Formatter();
    private final Gv02Formatter gv02Formatter = new Gv02Formatter();
    private final Ng01Formatter ng01Formatter = new Ng01Formatter();
    private final Wa11Formatter wa11Formatter = new Wa11Formatter();

    @InjectMocks
    private BerichtFactoryImpl subject;

    @Mock
    private PersoonHisVolledig persoon;

    @Before
    public void injectDependencies() {
        ReflectionTestUtils.setField(subject, "persoonConverteerder", persoonConverteerder);
        ReflectionTestUtils.setField(subject, "mutatieConverteerder", mutatieConverteerder);
        ReflectionTestUtils.setField(subject, "vulFilter", vulFilter);
        ReflectionTestUtils.setField(subject, "resyncFilter", resyncFilter);
        ReflectionTestUtils.setField(subject, "mutatieFilter", mutatieFilter);
        ReflectionTestUtils.setField(subject, "ng01Filter", ng01Filter);
        ReflectionTestUtils.setField(subject, "wa11Filter", wa11Filter);
        ReflectionTestUtils.setField(subject, "ag01Formatter", ag01Formatter);
        ReflectionTestUtils.setField(subject, "ag11Formatter", ag11Formatter);
        ReflectionTestUtils.setField(subject, "ag21Formatter", ag21Formatter);
        ReflectionTestUtils.setField(subject, "ag31Formatter", ag31Formatter);
        ReflectionTestUtils.setField(subject, "gv01Formatter", gv01Formatter);
        ReflectionTestUtils.setField(subject, "gv02Formatter", gv02Formatter);
        ReflectionTestUtils.setField(subject, "ng01Formatter", ng01Formatter);
        ReflectionTestUtils.setField(subject, "wa11Formatter", wa11Formatter);
    }

    @Test
    public void testLeeg() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.ZOEK_PERSOON, null);
        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandeling(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE);

        final List<SynchronisatieBericht> berichten = subject.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, null, administratieveHandeling);

        Assert.assertEquals(0, berichten.size());
    }

    @Test
    public void testAg01() {
        final Bericht bericht = subject.maakAg01Bericht(persoon);
        assertBerichtImpl(bericht, SoortBericht.AG01, persoonConverteerder, vulFilter, ag01Formatter, persoon, null);
    }

    @Test
    public void testAg11() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.ATTENDERING, EffectAfnemerindicaties.PLAATSING);
        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandeling(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE);

        final List<SynchronisatieBericht> berichten = subject.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, null, administratieveHandeling);

        Assert.assertEquals(1, berichten.size());
        final SynchronisatieBericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.AG11, persoonConverteerder, vulFilter, ag11Formatter, persoon, administratieveHandeling);
    }

    @Test
    public void testAg21() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.ATTENDERING, null);
        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandeling(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE);

        final List<SynchronisatieBericht> berichten = subject.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, null, administratieveHandeling);

        Assert.assertEquals(1, berichten.size());
        final SynchronisatieBericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.AG21, persoonConverteerder, vulFilter, ag21Formatter, persoon, administratieveHandeling);
    }

    @Test
    public void testAg31() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandelingModel administratieveHandeling =
                maakAdministratieveHandeling(SoortAdministratieveHandeling.CORRECTIE_GESLACHTSAANDUIDING);

        final List<SynchronisatieBericht> berichten = subject.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, null, administratieveHandeling);

        Assert.assertEquals(1, berichten.size());
        final SynchronisatieBericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.AG31, persoonConverteerder, resyncFilter, ag31Formatter, persoon, administratieveHandeling);
    }

    @Test
    public void testGv01() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandeling(SoortAdministratieveHandeling.WIJZIGING_GESLACHTSNAAM);

        final List<SynchronisatieBericht> berichten = subject.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, null, administratieveHandeling);

        Assert.assertEquals(1, berichten.size());
        final SynchronisatieBericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.GV01, mutatieConverteerder, mutatieFilter, gv01Formatter, persoon, administratieveHandeling);
    }

    @Test
    public void testGv02() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandelingModel administratieveHandeling =
                maakAdministratieveHandeling(SoortAdministratieveHandeling.WIJZIGING_ADRES_INFRASTRUCTUREEL);

        final List<SynchronisatieBericht> berichten = subject.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, null, administratieveHandeling);

        Assert.assertEquals(1, berichten.size());
        final SynchronisatieBericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.GV02, mutatieConverteerder, mutatieFilter, gv02Formatter, persoon, administratieveHandeling);
    }

    @Test
    public void testNg01() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandeling(SoortAdministratieveHandeling.G_B_A_AFVOEREN_P_L);

        final List<SynchronisatieBericht> berichten = subject.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, null, administratieveHandeling);

        Assert.assertEquals(1, berichten.size());
        final SynchronisatieBericht bericht = berichten.get(0);
        assertBerichtImpl(bericht, SoortBericht.NG01, persoonConverteerder, ng01Filter, ng01Formatter, persoon, administratieveHandeling);
    }

    @Test
    public void testWa11() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, null);
        final AdministratieveHandelingModel administratieveHandeling =
                maakAdministratieveHandeling(SoortAdministratieveHandeling.G_B_A_A_NUMMER_WIJZIGING);

        final List<SynchronisatieBericht> berichten = subject.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, null, administratieveHandeling);

        Assert.assertEquals(1, berichten.size());
        final SynchronisatieBericht berichtWa11 = berichten.get(0);
        assertBerichtImpl(berichtWa11, SoortBericht.WA11, persoonConverteerder, wa11Filter, wa11Formatter, persoon, administratieveHandeling);
        // final SynchronisatieBericht berichtResync = berichten.get(1);
        // Assert.assertNotNull(berichtResync);
        // assertBerichtImpl(berichtResync, SoortSynchronisatie.MUTATIEBERICHT, persoonConverteerder, wa11Filter,
        // wa11Formatter, persoon, administratieveHandeling);
    }

    private void assertBerichtImpl(
        final SynchronisatieBericht bericht,
        final SoortBericht expectedSoortBericht,
        final Converteerder expectedConverteerder,
        final Filter expectedFilter,
        final Formatter expectedFormatter,
        final PersoonHisVolledig expectedPersoon,
        final AdministratieveHandelingModel expectedAdministratieveHandeling)
    {
        Assert.assertTrue(bericht instanceof BerichtImpl);

        final Object actualSoortBericht = ReflectionTestUtils.getField(bericht, "soortBericht");
        final Object actualConverteerder = ReflectionTestUtils.getField(bericht, "converteerder");
        final Object actualFilter = ReflectionTestUtils.getField(bericht, "filter");
        final Object actuelFormatter = ReflectionTestUtils.getField(bericht, "formatter");
        final Object actualPersoon = ReflectionTestUtils.getField(bericht, "persoon");
        final Object actualAdministratieveHandeling = ReflectionTestUtils.getField(bericht, "administratieveHandeling");

        Assert.assertSame(actualSoortBericht, expectedSoortBericht);
        Assert.assertSame(expectedConverteerder, actualConverteerder);
        Assert.assertSame(expectedFilter, actualFilter);
        Assert.assertSame(expectedFormatter, actuelFormatter);
        Assert.assertSame(expectedPersoon, actualPersoon);
        if (expectedAdministratieveHandeling != null) {
            Assert.assertSame(expectedAdministratieveHandeling, actualAdministratieveHandeling);
        }

    }

    private Leveringinformatie maakLeveringinformatie(final SoortDienst soortDienst, final EffectAfnemerindicaties effectAfnemerindicaties) {
        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(soortDienst).metEffectAfnemerindicaties(effectAfnemerindicaties).maak();
        return new Leveringinformatie(null, dienst);
    }

    private AdministratieveHandelingModel maakAdministratieveHandeling(final SoortAdministratieveHandeling soort) {
        return new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(soort), new PartijAttribuut(null), null, null);
    }
}
