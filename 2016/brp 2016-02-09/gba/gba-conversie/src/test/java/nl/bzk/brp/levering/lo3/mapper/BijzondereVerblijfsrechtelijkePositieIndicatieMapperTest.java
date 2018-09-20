/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class BijzondereVerblijfsrechtelijkePositieIndicatieMapperTest {

    private final BijzondereVerblijfsrechtelijkePositieIndicatieMapper mapper = new BijzondereVerblijfsrechtelijkePositieIndicatieMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(maak(builder).build(), null);
        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> stapel =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(stapel);
        Assert.assertTrue(stapel.size() == 1);
        Assert.assertEquals(Boolean.TRUE, stapel.get(0).getInhoud().getIndicatie().getWaarde());
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> brpBijhoudingsaard =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNull(brpBijhoudingsaard);
    }

    @Test
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.voegPersoonIndicatieBijzondereVerblijfsrechtelijkePositieToe(new PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder().nieuwStandaardRecord(
                                                                                                                                                                  MapperTestUtil.maakActieModel(
                                                                                                                                                                      20131211000000L,
                                                                                                                                                                      20131212,
                                                                                                                                                                      20121213))
                                                                                                                                                              .eindeRecord()
                                                                                                                                                              .build());

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> stapel =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(stapel);
        Assert.assertTrue(stapel.size() == 1);
        Assert.assertEquals(Boolean.FALSE, stapel.get(0).getInhoud().getIndicatie().getWaarde());
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        return builder.voegPersoonIndicatieBijzondereVerblijfsrechtelijkePositieToe(new PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder().nieuwStandaardRecord(
                                                                                                                                                                         MapperTestUtil.maakActieModel(
                                                                                                                                                                             20131212000000L,
                                                                                                                                                                             20131212,
                                                                                                                                                                             null))
                                                                                                                                                                     .waarde(
                                                                                                                                                                         Ja.J)
                                                                                                                                                                     .eindeRecord()
                                                                                                                                                                     .build());
    }
}
