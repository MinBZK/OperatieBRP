/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.List;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerificatieHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
public class VerificatiesMapperTest {

    private final VerificatiesMapper mapper = new VerificatiesMapper();

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        final VerificatieMapper singleMapper = new VerificatieMapper();
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
        ReflectionTestUtils.setField(mapper, "verificatieMapper", singleMapper);
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpVerificatieInhoud>> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertTrue(brpInhoud.size() == 0);
    }

    @Test
    // @Ignore("Vanwege NullPointerException in PersoonVerificatieComparator")
    public void testGeenWaarde() throws ReflectiveOperationException {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.voegPersoonVerificatieToe(new PersoonVerificatieHisVolledigImplBuilder(new Partij(
            new NaamEnumeratiewaardeAttribuut("Partij Y"),
            new SoortPartijAttribuut(SoortPartij.B_R_P_VOORZIENING),
            new PartijCodeAttribuut(998555),
            new DatumEvtDeelsOnbekendAttribuut(19770101),
            null, null,
            new JaNeeAttribuut(Boolean.TRUE), null, null), new NaamEnumeratiewaardeAttribuut("Verificatie2")).nieuwStandaardRecord(
                                                                                                     MapperTestUtil.maakActieModel(
                                                                                                         20131211000000L,
                                                                                                         20131212,
                                                                                                         null))
                                                                                                 .eindeRecord()
                                                                                                 .build());
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpVerificatieInhoud>> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).get(0).getInhoud().getDatum());
    }

    @Test
    public void testSucces() throws ReflectiveOperationException {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        maak(builder).voegPersoonVerificatieToe(
            new PersoonVerificatieHisVolledigImplBuilder(new Partij(
                new NaamEnumeratiewaardeAttribuut("Partij Y"),
                new SoortPartijAttribuut(SoortPartij.B_R_P_VOORZIENING),
                new PartijCodeAttribuut(998555),
                new DatumEvtDeelsOnbekendAttribuut(19770101),
                null,null,
                new JaNeeAttribuut(Boolean.TRUE), null, null), new NaamEnumeratiewaardeAttribuut("Verificatie2")).nieuwStandaardRecord(
                                                                                                         MapperTestUtil.maakActieModel(
                                                                                                             20131211000000L,
                                                                                                             20131212,
                                                                                                             null))
                                                                                                     .datum(20130514)
                                                                                                     .eindeRecord()
                                                                                                     .build());
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final List<BrpStapel<BrpVerificatieInhoud>> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertEquals(2, brpInhoud.size());
        Assert.assertEquals(1, brpInhoud.get(0).size());
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) throws ReflectiveOperationException {
        return builder.voegPersoonVerificatieToe(new PersoonVerificatieHisVolledigImplBuilder(new Partij(
            new NaamEnumeratiewaardeAttribuut("Partij X"),
            new SoortPartijAttribuut(SoortPartij.B_R_P_VOORZIENING),
            new PartijCodeAttribuut(998556),
            new DatumEvtDeelsOnbekendAttribuut(19770101),
            null, null,
            new JaNeeAttribuut(Boolean.TRUE), null,null), new NaamEnumeratiewaardeAttribuut("Verificatie1")).nieuwStandaardRecord(
                                                                                                     MapperTestUtil.maakActieModel(
                                                                                                         20131211000000L,
                                                                                                         20131212,
                                                                                                         null))
                                                                                                 .datum(20130513)
                                                                                                 .eindeRecord()
                                                                                                 .build());
    }
}
