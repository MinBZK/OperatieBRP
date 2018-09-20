/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class PersoonSamengesteldeNaamMapperTest {

    private static final String H = "H";

    private final PersoonSamengesteldeNaamMapper mapper = new PersoonSamengesteldeNaamMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void test() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(maak(builder).build(), null);
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(samengesteldeNaam);
        Assert.assertTrue(samengesteldeNaam.size() == 1);
    }

    /**
     * NPE vanwege hardcoded controle in conversie model (boolean primitive).
     */
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.nieuwSamengesteldeNaamRecord(MapperTestUtil.maakActieModel(20131210000000L, 20131211, 20141212)).eindeRecord();
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(samengesteldeNaam);
        Assert.assertTrue(samengesteldeNaam.size() == 1);
        Assert.assertNull(samengesteldeNaam.get(0).getInhoud().getGeslachtsnaamstam());
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNull(samengesteldeNaam);
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.nieuwSamengesteldeNaamRecord(MapperTestUtil.maakActieModel(20131210000000L, 20131211, 20141212))
                .geslachtsnaamstam("geslachtsnaam")
                .voorvoegsel("de")
                .voornamen("Voornaam1 Voornaam2")
                .scheidingsteken(" ")
                .predicaat(H)
                .indicatieNamenreeks(Boolean.TRUE)
                .indicatieAfgeleid(Boolean.FALSE)
                .adellijkeTitel(H)
                .eindeRecord();
        // @formatter:on
    }
}
