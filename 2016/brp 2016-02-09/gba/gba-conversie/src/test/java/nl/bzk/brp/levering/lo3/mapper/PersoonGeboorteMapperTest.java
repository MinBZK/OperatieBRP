/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class PersoonGeboorteMapperTest {

    private final PersoonGeboorteMapper mapper = new PersoonGeboorteMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigImpl persImpl = maak(builder).build();
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(persImpl, null);
        final BrpStapel<BrpGeboorteInhoud> brpGeboorte =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpGeboorte);
        Assert.assertTrue(brpGeboorte.size() == 1);
        Assert.assertEquals(Integer.valueOf(20130101), brpGeboorte.get(0).getInhoud().getGeboortedatum().getWaarde());
        Assert.assertEquals(Short.valueOf((short) 518), brpGeboorte.get(0).getInhoud().getGemeenteCode().getWaarde());
        Assert.assertEquals(Short.valueOf((short) 6030), brpGeboorte.get(0).getInhoud().getLandOfGebiedCode().getWaarde());

        Assert.assertNotNull(brpGeboorte.get(0).getActieInhoud());
        Assert.assertNull(brpGeboorte.get(0).getActieGeldigheid());
        Assert.assertNull(brpGeboorte.get(0).getActieVerval());
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpGeboorteInhoud> brpGeboorte =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNull(brpGeboorte);
    }

    @Test
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.nieuwGeboorteRecord(MapperTestUtil.maakActieModel(20131212000000L)).eindeRecord();

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpGeboorteInhoud> brpGeboorte =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpGeboorte);
        Assert.assertTrue(brpGeboorte.size() == 1);
        Assert.assertNull(brpGeboorte.get(0).getInhoud().getGeboortedatum());
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.nieuwGeboorteRecord(MapperTestUtil.maakActieModel(20131212000000L))
                .datumGeboorte(20130101)
                .gemeenteGeboorte(Short.valueOf("518"))
                .landGebiedGeboorte(Short.valueOf("6030"))
                .eindeRecord();
        // @formatter:on
    }
}
