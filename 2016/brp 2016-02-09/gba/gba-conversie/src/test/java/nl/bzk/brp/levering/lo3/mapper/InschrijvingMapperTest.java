/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Date;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class InschrijvingMapperTest {

    private final InschrijvingMapper mapper = new InschrijvingMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(maak(builder).build(), null);
        final BrpStapel<BrpInschrijvingInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals(Integer.valueOf(20131212), brpInhoud.get(0).getInhoud().getDatumInschrijving().getWaarde());
        Assert.assertEquals(Long.valueOf(1L), brpInhoud.get(0).getInhoud().getVersienummer().getWaarde());
        Assert.assertEquals(123L, brpInhoud.get(0).getInhoud().getDatumtijdstempel().getJavaDate().getTime());
        // Vorig en Volgend Persoon wordt op dit moment niet gevuld in het BRP model, zie {@link
        // AbstractHisPersoonInschrijvingModel}
        // Assert.assertEquals(1234567890L, brpInhoud.get(0).getInhoud().getVolgendAdministratienummer().longValue());
        // Assert.assertEquals(9876543210L, brpInhoud.get(0).getInhoud().getVorigAdministratienummer().longValue());
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpInschrijvingInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNull(brpInhoud);
    }

    /**
     * Verwacht NPE vanwege hardgecodeerde controle op conversie model (versienummer is long primitive)
     */
    @Test(expected = NullPointerException.class)
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.nieuwInschrijvingRecord(MapperTestUtil.maakActieModel(20131212000000L)).eindeRecord();

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpInschrijvingInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNull(brpInhoud);
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.nieuwInschrijvingRecord(MapperTestUtil.maakActieModel(20131212000000L))
                .datumInschrijving(20131212)
                .versienummer(1L)
                .datumtijdstempel(new Date(123))
                .eindeRecord();
        // @formatter:on
    }

}
