/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.List;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class GeslachtsnaamcomponentenMapperTest {

    private static final String STAM = "Baronnetje";

    private final GeslachtsnaamcomponentenMapper mapper = new GeslachtsnaamcomponentenMapper();

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        final GeslachtsnaamcomponentMapper singleMapper = new GeslachtsnaamcomponentMapper();
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
        ReflectionTestUtils.setField(mapper, "geslachtsnaamcomponentMapper", singleMapper);
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertTrue(brpInhoud.size() == 0);
    }

    /**
     * Verwacht NPE vanwege hardgecodeerde controle op conversie model
     */
    @Test(expected = NullPointerException.class)
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.voegPersoonGeslachtsnaamcomponentToe(new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1)).nieuwStandaardRecord(
                                                                                                                                            MapperTestUtil.maakActieModel(
                                                                                                                                                20131211000000L,
                                                                                                                                                20131212,
                                                                                                                                                20131213))
                                                                                                                                        .eindeRecord()
                                                                                                                                        .build());
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        maak(builder).voegPersoonGeslachtsnaamcomponentToe(
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(2)).nieuwStandaardRecord(
                                                                                                   MapperTestUtil.maakActieModel(
                                                                                                       20131211000000L,
                                                                                                       20131212,
                                                                                                       20121213))
                                                                                               .stam("Bastaard")
                                                                                               .eindeRecord()
                                                                                               .build());
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 2);
        Assert.assertTrue(brpInhoud.get(0).size() == 1);
        Assert.assertEquals(Integer.valueOf(1), brpInhoud.get(0).get(0).getInhoud().getVolgnummer().getWaarde());
        Assert.assertEquals(STAM, brpInhoud.get(0).get(0).getInhoud().getStam().getWaarde());
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.voegPersoonGeslachtsnaamcomponentToe(
                new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                        .nieuwStandaardRecord(MapperTestUtil.maakActieModel(20131211000000L, 20131212, 20121213))
                        .adellijkeTitel("B")
                        .stam(STAM)
                        .predicaat("J")
                        .scheidingsteken("-")
                        .voorvoegsel("van der")
                        .eindeRecord().build());
        // @formatter:on
    }
}
