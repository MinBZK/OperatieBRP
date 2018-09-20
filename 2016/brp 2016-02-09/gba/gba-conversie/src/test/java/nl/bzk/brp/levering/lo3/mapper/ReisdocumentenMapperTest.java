/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.List;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class ReisdocumentenMapperTest {

    private final ReisdocumentenMapper mapper = new ReisdocumentenMapper();

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        final ReisdocumentMapper singleMapper = new ReisdocumentMapper();
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
        ReflectionTestUtils.setField(mapper, "reisdocumentMapper", singleMapper);
    }

    @Test
    public void test() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(maak(builder).build(), null);
        final List<BrpStapel<BrpReisdocumentInhoud>> reisdocument =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(reisdocument);
        Assert.assertTrue(reisdocument.size() == 1);
    }

    @Test
    public void testAlleenData() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.voegPersoonReisdocumentToe(new PersoonReisdocumentHisVolledigImplBuilder(new SoortNederlandsReisdocument(
            new SoortNederlandsReisdocumentCodeAttribuut("P"),
            new OmschrijvingEnumeratiewaardeAttribuut("Pass"),
            new DatumEvtDeelsOnbekendAttribuut(20130110),
            new DatumEvtDeelsOnbekendAttribuut(20140110))).nieuwStandaardRecord(MapperTestUtil.maakActieModel(20130110000000L))
                                                          .autoriteitVanAfgifte("autoriteitVanAfgifte")
                                                          .datumIngangDocument(20130111)
                                                          .datumUitgifte(20131010)
                                                          .datumEindeDocument(20140101)
                                                          .nummer("1234")
                                                          .eindeRecord()
                                                          .build());

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpReisdocumentInhoud>> reisdocument =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(reisdocument);
        Assert.assertTrue(reisdocument.size() == 1);
        Assert.assertNull(reisdocument.get(0).get(0).getInhoud().getDatumInhoudingOfVermissing());
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpReisdocumentInhoud>> reisdocument =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(reisdocument);
        Assert.assertTrue(reisdocument.size() == 0);
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.voegPersoonReisdocumentToe(
                new PersoonReisdocumentHisVolledigImplBuilder(
                        new SoortNederlandsReisdocument(new SoortNederlandsReisdocumentCodeAttribuut("P"),
                                                        new OmschrijvingEnumeratiewaardeAttribuut("Pass"),
                                                        new DatumEvtDeelsOnbekendAttribuut(20130110),
                                                        new DatumEvtDeelsOnbekendAttribuut(20140110)))
                        .nieuwStandaardRecord(MapperTestUtil.maakActieModel(20130110000000L))
                        .aanduidingInhoudingVermissing("V")
                        .autoriteitVanAfgifte("autoriteitVanAfgifte")
                        .datumEindeDocument(20140101)
                        .datumIngangDocument(20130111)
                        .datumInhoudingVermissing(20140101)
                        .datumUitgifte(20131010)
                        .nummer("1234")
                        .eindeRecord().build());
        // @formatter:on
    }
}
