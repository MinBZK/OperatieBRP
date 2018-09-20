/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AdresMapperTest {

    private static final String A = "A";

    private final AdresMapper mapper = new AdresMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(maak(builder).build(), null);
        final BrpStapel<BrpAdresInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals(Character.valueOf('I'), brpInhoud.get(0).getInhoud().getAangeverAdreshoudingCode().getWaarde());
    }

    @Test
    public void testTweeAdressenAlleenEersteGemapt() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);

        final PersoonAdresHisVolledigImpl persoonAdres1 = maakDefaultAdres();
        final PersoonAdresHisVolledigImpl persoonAdres2 = maakDefaultAdres();
        builder.voegPersoonAdresToe(persoonAdres1);
        builder.voegPersoonAdresToe(persoonAdres2);

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);

        final BrpStapel<BrpAdresInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());
        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals(Character.valueOf('I'), brpInhoud.get(0).getInhoud().getAangeverAdreshoudingCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpAdresInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNull(brpInhoud);
    }

    @Test
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.voegPersoonAdresToe(
            new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(MapperTestUtil.maakActieModel(20130101000000L, 20130101, null))
                                                    .eindeRecord()
                                                    .build());

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpAdresInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).getInhoud().getDatumAanvangAdreshouding());
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.voegPersoonAdresToe(maakDefaultAdres());
        // @formatter:on
    }

    private static PersoonAdresHisVolledigImpl maakDefaultAdres() {
        return new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(MapperTestUtil.maakActieModel(20130101000000L, 20130101, null))
                                                       .aangeverAdreshouding("I")
                                                       .identificatiecodeAdresseerbaarObject("adresseerbaarObject")
                                                       .afgekorteNaamOpenbareRuimte("afgekorteNaam")
                                                       .buitenlandsAdresRegel1("buitenland1")
                                                       .buitenlandsAdresRegel2("buitenland2")
                                                       .buitenlandsAdresRegel3("buitenland3")
                                                       .buitenlandsAdresRegel4("buitenland4")
                                                       .buitenlandsAdresRegel5("buitenland5")
                                                       .buitenlandsAdresRegel6("buitenland6")
                                                       .datumAanvangAdreshouding(20130101)
                                                       .gemeente(Short.valueOf("518"))
                                                       .gemeentedeel("deel vd gemeente")
                                                       .huisletter(A)
                                                       .huisnummer(10)
                                                       .huisnummertoevoeging(A)
                                                       .identificatiecodeNummeraanduiding("nummerAanduiding")
                                                       .indicatiePersoonAangetroffenOpAdres(null)
                                                       .landGebied((short) 6030)
                                                       .locatieomschrijving("omschrijving locatie")
                                                       .locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.TO)
                                                       .naamOpenbareRuimte("naam")
                                                       .postcode("2245HJ")
                                                       .redenWijziging("P")
                                                       .soort(FunctieAdres.WOONADRES)
                                                       .woonplaatsnaam("Rotterdam")
                                                       .eindeRecord()
                                                       .build();
    }
}
