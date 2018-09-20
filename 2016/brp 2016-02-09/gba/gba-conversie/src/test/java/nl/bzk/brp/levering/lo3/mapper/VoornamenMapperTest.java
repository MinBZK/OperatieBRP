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
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class VoornamenMapperTest {

    private final VoornamenMapper mapper = new VoornamenMapper();

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        final VoornaamMapper singleMapper = new VoornaamMapper();
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
        ReflectionTestUtils.setField(mapper, "voornaamMapper", singleMapper);
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpVoornaamInhoud>> brpVoornamen =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertTrue(brpVoornamen.size() == 0);
    }

    @Test
    public void testGeenVoornaam() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.voegPersoonVoornaamToe(new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1)).nieuwStandaardRecord(
                                                                                                                MapperTestUtil.maakActieModel(
                                                                                                                    20131211000000L,
                                                                                                                    20131212,
                                                                                                                    20131213))
                                                                                                            .eindeRecord()
                                                                                                            .build());

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpVoornaamInhoud>> brpVoornamen =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertTrue(brpVoornamen.size() == 1);
        Assert.assertTrue(brpVoornamen.get(0).size() == 1);
        Assert.assertNull(brpVoornamen.get(0).get(0).getInhoud().getVoornaam());
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(maak(builder).build(), null);
        final List<BrpStapel<BrpVoornaamInhoud>> brpVoornamen =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpVoornamen);
        Assert.assertTrue(brpVoornamen.size() == 1);
        Assert.assertTrue(brpVoornamen.get(0).size() == 1);
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {

        // @formatter:off
        return builder.voegPersoonVoornaamToe(
                new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                        .nieuwStandaardRecord(MapperTestUtil.maakActieModel(20131211000000L, 20131212, 20131213))
                        .naam("voornaam")
                        .eindeRecord().build());
        // @formatter:on
    }
}
