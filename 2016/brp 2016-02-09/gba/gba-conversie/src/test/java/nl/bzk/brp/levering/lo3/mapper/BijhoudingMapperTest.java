/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class BijhoudingMapperTest {

    private final BijhoudingMapper mapper = new BijhoudingMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(maak(builder).build(), null);
        final BrpStapel<BrpBijhoudingInhoud> brpBijhoudingsaard =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpBijhoudingsaard);
        Assert.assertTrue(brpBijhoudingsaard.size() == 1);
        Assert.assertEquals(Bijhoudingsaard.INGEZETENE.getCode(), brpBijhoudingsaard.get(0).getInhoud().getBijhoudingsaardCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpBijhoudingInhoud> brpBijhoudingsaard =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNull(brpBijhoudingsaard);
    }

    @Test
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.nieuwBijhoudingRecord(MapperTestUtil.maakActieModel(20131211000000L, 20131212, 20121213)).eindeRecord();

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpBijhoudingInhoud> brpBijhoudingsaard =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpBijhoudingsaard);
        Assert.assertTrue(brpBijhoudingsaard.size() == 1);
        Assert.assertNull(brpBijhoudingsaard.get(0).getInhoud().getBijhoudingsaardCode());
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.nieuwBijhoudingRecord(MapperTestUtil.maakActieModel(20131212000000L, 20131212, null))
                .bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
                .eindeRecord();
        // @formatter:on
    }
}
