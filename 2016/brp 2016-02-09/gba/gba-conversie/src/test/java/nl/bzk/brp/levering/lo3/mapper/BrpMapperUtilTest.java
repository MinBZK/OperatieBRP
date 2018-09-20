/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieImpl;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpMapperUtilTest {

    private VerConvRepository dummyVerConvRepository;

    @Mock
    private ActieHisVolledigLocator mockActieHisVolledigLocator;

    @Before
    public void setup() {
        dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
    }

    /**
     * test de lege constructor.
     *
     * @throws Throwable
     *             voor de error
     */
    @Test(expected = AssertionError.class)
    public final void testPrivateConstructor() throws Throwable {
        final Constructor<BrpMapperUtil> brpConstructor = BrpMapperUtil.class.getDeclaredConstructor();
        if (brpConstructor.isAccessible()) {
            fail("Zou een private constructor moeten zijn");
        } else {
            brpConstructor.setAccessible(true);
            try {
                brpConstructor.newInstance();
            } catch (final InvocationTargetException ite) {
                throw ite.getCause();
            }
        }
    }

    @Test
    public void testMapActieInhoudFormeel() throws ParseException {
        final ActieModel actieModel =
                MapperTestUtil.maakActieModel(1L, 20140101120000L, null, null, BrpPartijCode.MIGRATIEVOORZIENING_CODE, SoortActie.CONVERSIE_G_B_A);

        final DummyFormeelHistorischMetActieVerantwoording record = new DummyFormeelHistorischMetActieVerantwoording();
        record.setVerantwoordingInhoud(actieModel);

        final BrpActie brpActieInhoud =
                BrpMapperUtil.mapActieInhoud(record, getOnderzoekMapper(), null, dummyVerConvRepository, new TestActieHisVolledigLocator());

        // assert actie waarde
        Assert.assertEquals(BrpSoortActieCode.CONVERSIE_GBA, brpActieInhoud.getSoortActieCode());
        Assert.assertEquals(BrpPartijCode.MIGRATIEVOORZIENING, brpActieInhoud.getPartijCode());
        Assert.assertEquals(Long.valueOf(1L), brpActieInhoud.getId());
        // uurtje eerder
        Assert.assertEquals(20140101110000L, brpActieInhoud.getDatumTijdRegistratie().getDatumTijd());
    }

    @Test
    public void testMapActieInhoudFormeelFoutief() throws ParseException {
        final FormeelHistorisch record = new FormeelHistorisch() {

            @Override
            public FormeleHistorieModel getFormeleHistorie() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Verwerkingssoort getVerwerkingssoort() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isMagGeleverdWorden() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        };

        final BrpActie brpActieInhoud =
                BrpMapperUtil.mapActieInhoud(record, getOnderzoekMapper(), null, dummyVerConvRepository, new TestActieHisVolledigLocator());

        Assert.assertNull(brpActieInhoud);
    }

    @Test
    public void testMapActieVervalFormeel() throws ParseException {
        final ActieModel actieModel =
                MapperTestUtil.maakActieModel(2L, 20140102120000L, null, null, BrpPartijCode.MIGRATIEVOORZIENING_CODE, SoortActie.CONVERSIE_G_B_A);

        final DummyFormeelHistorischMetActieVerantwoording record = new DummyFormeelHistorischMetActieVerantwoording();
        record.setVerantwoordingVerval(actieModel);

        final BrpActie brpActieVerval =
                BrpMapperUtil.mapActieVerval(record, getOnderzoekMapper(), null, dummyVerConvRepository, new TestActieHisVolledigLocator());

        // assert actie waarde
        Assert.assertEquals(BrpSoortActieCode.CONVERSIE_GBA, brpActieVerval.getSoortActieCode());
        Assert.assertEquals(BrpPartijCode.MIGRATIEVOORZIENING, brpActieVerval.getPartijCode());
        Assert.assertEquals(Long.valueOf(2L), brpActieVerval.getId());
        // uurtje eerder
        Assert.assertEquals(20140102110000L, brpActieVerval.getDatumTijdRegistratie().getDatumTijd());
    }

    @Test
    public void testMapActieInhoudMaterieel() throws ParseException {
        final ActieModel actieModel =
                MapperTestUtil.maakActieModel(3L, 20140101120000L, null, null, BrpPartijCode.MIGRATIEVOORZIENING_CODE, SoortActie.CONVERSIE_G_B_A);

        final DummyMaterieelHistorischMetActieVerantwoording record = new DummyMaterieelHistorischMetActieVerantwoording();
        record.setVerantwoordingInhoud(actieModel);

        final BrpActie brpActieInhoud =
                BrpMapperUtil.mapActieInhoud(record, getOnderzoekMapper(), null, dummyVerConvRepository, new TestActieHisVolledigLocator());

        // assert actie waarde
        Assert.assertEquals(BrpSoortActieCode.CONVERSIE_GBA, brpActieInhoud.getSoortActieCode());
        Assert.assertEquals(BrpPartijCode.MIGRATIEVOORZIENING, brpActieInhoud.getPartijCode());
        Assert.assertEquals(Long.valueOf(3L), brpActieInhoud.getId());
        // uurtje eerder
        Assert.assertEquals(20140101110000L, brpActieInhoud.getDatumTijdRegistratie().getDatumTijd());
    }

    @Test
    public void testMapActieInhoudMaterieelFout() throws ParseException {

        final MaterieelHistorisch record = new MaterieelHistorisch() {

            @Override
            public MaterieleHistorieModel getMaterieleHistorie() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public MaterieelHistorisch kopieer() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Verwerkingssoort getVerwerkingssoort() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isMagGeleverdWorden() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        };
        final BrpActie brpActieInhoud =
                BrpMapperUtil.mapActieInhoud(record, getOnderzoekMapper(), null, dummyVerConvRepository, new TestActieHisVolledigLocator());

        Assert.assertNull(brpActieInhoud);
    }

    @Test
    public void testMapActieVervalMaterieel() throws ParseException {
        final ActieModel actieModel =
                MapperTestUtil.maakActieModel(4L, 20140102120000L, null, null, BrpPartijCode.MIGRATIEVOORZIENING_CODE, SoortActie.CONVERSIE_G_B_A);

        final DummyMaterieelHistorischMetActieVerantwoording record = new DummyMaterieelHistorischMetActieVerantwoording();
        record.setVerantwoordingVerval(actieModel);

        final BrpActie brpActieVerval =
                BrpMapperUtil.mapActieVerval(record, getOnderzoekMapper(), null, dummyVerConvRepository, new TestActieHisVolledigLocator());

        // assert actie waarde
        Assert.assertEquals(BrpSoortActieCode.CONVERSIE_GBA, brpActieVerval.getSoortActieCode());
        Assert.assertEquals(BrpPartijCode.MIGRATIEVOORZIENING, brpActieVerval.getPartijCode());
        Assert.assertEquals(Long.valueOf(4L), brpActieVerval.getId());
        // uurtje eerder
        Assert.assertEquals(20140102110000L, brpActieVerval.getDatumTijdRegistratie().getDatumTijd());
    }

    @Test
    public void testMapActieAanpGeldigheid() throws ParseException {
        final ActieModel actieModel =
                MapperTestUtil.maakActieModel(5L, 20140103000000L, null, null, BrpPartijCode.MIGRATIEVOORZIENING_CODE, SoortActie.CONVERSIE_G_B_A);

        final DummyMaterieelHistorischMetActieVerantwoording record = new DummyMaterieelHistorischMetActieVerantwoording();
        record.setVerantwoordingAanpassingGeldigheid(actieModel);

        final BrpActie brpActieAanpGeldigheid =
                BrpMapperUtil.mapActieAanpassingGeldigheid(record, getOnderzoekMapper(), null, dummyVerConvRepository, new TestActieHisVolledigLocator());

        // assert actie waarde
        Assert.assertEquals(BrpSoortActieCode.CONVERSIE_GBA, brpActieAanpGeldigheid.getSoortActieCode());
        Assert.assertEquals(BrpPartijCode.MIGRATIEVOORZIENING, brpActieAanpGeldigheid.getPartijCode());
        Assert.assertEquals(Long.valueOf(5L), brpActieAanpGeldigheid.getId());
        // uurtje eerder
        Assert.assertEquals(20140102230000L, brpActieAanpGeldigheid.getDatumTijdRegistratie().getDatumTijd());
    }

    @Test(expected = IllegalStateException.class)
    public void testMapActieAanpGeldigheidGeenHistory() throws IllegalStateException {
        final ActieModel actieModel =
                MapperTestUtil.maakActieModel(5L, 20140103000000L, null, null, BrpPartijCode.MIGRATIEVOORZIENING_CODE, SoortActie.CONVERSIE_G_B_A);

        final DummyMaterieelHistorischMetActieVerantwoording record = new DummyMaterieelHistorischMetActieVerantwoording();
        record.setVerantwoordingAanpassingGeldigheid(actieModel);

        Mockito.when(mockActieHisVolledigLocator.locate((ActieModel) any())).thenReturn(null);

        BrpMapperUtil.mapActieAanpassingGeldigheid(record, getOnderzoekMapper(), null, dummyVerConvRepository, mockActieHisVolledigLocator);
    }

    // @Test
    // public void testMapHistorieMaterieel() throws ParseException {
    // final BrpHistorie brpHistorie = BrpMapperUtil.mapHistorie(maak(20130101, 20130102, 20130103120000L,
    // 20130104120000L), getOnderzoekMapper());
    // Assert.assertNotNull(brpHistorie);
    // Assert.assertEquals(Integer.valueOf(20130101), brpHistorie.getDatumAanvangGeldigheid().getWaarde());
    // Assert.assertEquals(Integer.valueOf(20130102), brpHistorie.getDatumEindeGeldigheid().getWaarde());
    // // uurtje eerder
    // Assert.assertEquals(20130103110000L, brpHistorie.getDatumTijdRegistratie().getDatumTijd());
    // Assert.assertEquals(20130104110000L, brpHistorie.getDatumTijdVerval().getDatumTijd());
    // }
    //
    // @Test
    // public void testMapHistorieFormeel() throws ParseException {
    // final BrpHistorie brpHistorie = BrpMapperUtil.mapHistorie(maak(null, null, 20130103123000L, 20130104123000L),
    // getOnderzoekMapper());
    // Assert.assertNotNull(brpHistorie);
    // Assert.assertNull(brpHistorie.getDatumAanvangGeldigheid());
    // Assert.assertNull(brpHistorie.getDatumEindeGeldigheid());
    // // uurtje eerder
    // Assert.assertEquals(20130103113000L, brpHistorie.getDatumTijdRegistratie().getDatumTijd());
    // Assert.assertEquals(20130104113000L, brpHistorie.getDatumTijdVerval().getDatumTijd());
    // }

    private OnderzoekMapper getOnderzoekMapper() {
        final PersoonHisVolledigView persoonHisVolledig =
                new PersoonHisVolledigView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build(), null);
        return new OnderzoekMapper(persoonHisVolledig);
    }

    private static DatumTijdAttribuut maakDatumTijdAttribuut(final Long datumTijd) throws ParseException {
        final SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMddHHmmss");
        return new DatumTijdAttribuut(dtf.parse(datumTijd.toString()));
    }

    public static FormeleHistorieModel maak(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Long tijdstipRegistratie,
        final Long datumTijdVerval) throws ParseException
    {
        FormeleHistorieModel historie;
        if (datumAanvangGeldigheid != null) {
            historie =
                    new MaterieleHistorieImpl(
                        new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid),
                        new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid));
        } else {
            historie = new FormeleHistorieImpl();
        }
        historie.setDatumTijdRegistratie(maakDatumTijdAttribuut(tijdstipRegistratie));
        if (datumTijdVerval == null) {
            historie.setDatumTijdVerval(null);
        } else {
            historie.setDatumTijdVerval(maakDatumTijdAttribuut(datumTijdVerval));
        }

        return historie;
    }

    private static final class DummyFormeelHistorischMetActieVerantwoording extends AbstractFormeelHistorischMetActieVerantwoording {
    }

    private static final class DummyMaterieelHistorischMetActieVerantwoording extends AbstractMaterieelHistorischMetActieVerantwoording {

        @Override
        public MaterieelHistorisch kopieer() {
            return null;
        }
    }

}
