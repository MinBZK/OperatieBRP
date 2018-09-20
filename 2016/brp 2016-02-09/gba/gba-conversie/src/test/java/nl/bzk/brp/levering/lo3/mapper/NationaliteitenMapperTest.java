/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.List;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class NationaliteitenMapperTest {

    private final NationaliteitenMapper mapper = new NationaliteitenMapper();

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        final NationaliteitMapper singleMapper = new NationaliteitMapper();
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
        ReflectionTestUtils.setField(mapper, "nationaliteitMapper", singleMapper);
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpNationaliteitInhoud>> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertTrue(brpInhoud.size() == 0);
    }

    @Test
    @Ignore("Vanwege NullPointerException in PersoonNationaliteitComparator")
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.voegPersoonNationaliteitToe(new PersoonNationaliteitHisVolledigImplBuilder(new Nationaliteit(null, null, null, null)).nieuwStandaardRecord(
                                                                                                                                         MapperTestUtil.maakActieModel(
                                                                                                                                             20131211000000L,
                                                                                                                                             20131212,
                                                                                                                                             20131213))
                                                                                                                                     .eindeRecord()
                                                                                                                                     .build());
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpNationaliteitInhoud>> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).get(0).getInhoud().getNationaliteitCode());
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        maak(builder).voegPersoonNationaliteitToe(
                         new PersoonNationaliteitHisVolledigImplBuilder(new Nationaliteit(new NationaliteitcodeAttribuut("77"), null, null, null)).nieuwStandaardRecord(
                                                                                                                                                      MapperTestUtil.maakActieModel(
                                                                                                                                                          20131211000000L,
                                                                                                                                                          20131212,
                                                                                                                                                          null))
                                                                                                                                                  .redenVerlies(
                                                                                                                                                      (short) 66)
                                                                                                                                                  .eindeRecord()
                                                                                                                                                  .build());
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpNationaliteitInhoud>> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 2);
        Assert.assertTrue(brpInhoud.get(0).size() == 1);

        // Assert.assertEquals((short) 1, brpInhoud.get(0).get(0).getInhoud().getNationaliteitCode().getCode());
        // Assert.assertEquals((short) 16, brpInhoud.get(0).get(0).getInhoud().getRedenVerkrijgingNederlandschapCode()
        // .getCode());
        // Assert.assertNull(brpInhoud.get(0).get(0).getInhoud().getRedenVerliesNederlandschapCode());
        //
        // Assert.assertEquals((short) 77, brpInhoud.get(1).get(0).getInhoud().getNationaliteitCode().getCode());
        // Assert.assertEquals((short) 66, brpInhoud.get(1).get(0).getInhoud().getRedenVerliesNederlandschapCode()
        // .getCode());
        // Assert.assertNull(brpInhoud.get(1).get(0).getInhoud().getRedenVerkrijgingNederlandschapCode());
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.voegPersoonNationaliteitToe(
                new PersoonNationaliteitHisVolledigImplBuilder(
                        new Nationaliteit(new NationaliteitcodeAttribuut("1"), null, null, null))
                        .nieuwStandaardRecord(MapperTestUtil.maakActieModel(20131211000000L, 20131212, null))
                        .redenVerkrijging((short) 16)
                        .eindeRecord().build());
        // @formatter:on
    }
}
