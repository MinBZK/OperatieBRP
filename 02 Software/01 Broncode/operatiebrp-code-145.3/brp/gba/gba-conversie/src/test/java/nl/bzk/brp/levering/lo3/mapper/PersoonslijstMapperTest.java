/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.actie;
import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.logMetaObject;
import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.maakIngeschrevene;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import javax.inject.Inject;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration("/test-mapper-beans.xml")
public class PersoonslijstMapperTest extends AbstractMapperTestBasis {

    @Inject
    private PersoonslijstMapper mapper;

    @Test
    public void testSucces() throws ReflectiveOperationException {
        final MetaObject.Builder builder =
                maakIngeschrevene(
                        Arrays.asList(
                                () -> MetaObjectUtil.maakPersoonAdres(actie),
                                () -> MetaObjectUtil.maakPersoonGeslachtsnaamcomponent(1, "Bastaard"),
                                () -> MetaObjectUtil.maakPersoonGeslachtsnaamcomponent(2, "Pekela"),
                                () -> MetaObjectUtil.maakPersoonNationaliteit(actie, "0001", "016", null),
                                () -> MetaObjectUtil.maakPersoonReisdocument("P", "V", "autoriteitVanAfgifte", 20140101, 20130111, 20140101, 20131010, "1234"),
                                MetaObjectUtil::maakPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument,
                                MetaObjectUtil::maakPersoonIndicatieOnderCuratele,
                                MetaObjectUtil::maakPersoonIndicatieStaatloos,
                                MetaObjectUtil::maakPersoonIndicatieVastgesteldNietNederlander,
                                MetaObjectUtil::maakPersoonIndicatieBijzondereVerblijfsrechtelijkePositie,
                                MetaObjectUtil::maakPersoonIndicatieVerstrekkingsbeperking,
                                MetaObjectUtil::maakPersoonIndicatieBehandeldAlsNederlander,
                                MetaObjectUtil::maakPersoonIndicatieDerdeHeeftGezag,
                                () -> MetaObjectUtil.maakPersoonVerificatie(MetaObjectUtil.actie, "998556", "Verificatie1", 20130513),
                                () -> MetaObjectUtil.maakPersoonVoornaam(1, "voornaam")),
                        Arrays.asList(
                                MetaObjectUtil::voegPersoonBijhoudingGroepToe,
                                MetaObjectUtil::voegPersoonDeelnameEUVerkiezingenGroepToe,
                                b -> MetaObjectUtil.voegPersoonGeboorteGroepToe(b, actie, 20130101, "0518", "6030"),
                                b -> MetaObjectUtil.voegPersoonIdentificatienummersGroepToe(b, "1234567890", "123456789"),
                                b -> MetaObjectUtil.voegPersoonInschrijvingGroepToe(b, 20131212, new Date(123), 1L),
                                b -> MetaObjectUtil.voegPersoonMigratieGroepToe(
                                        b,
                                        "I",
                                        "regel1",
                                        "regel2",
                                        "regel3",
                                        "regel4",
                                        "regel5",
                                        "regel6",
                                        "0542",
                                        "R",
                                        "E"),
                                b -> MetaObjectUtil.voegPersoonNaamgebruikGroepToe(b, "B", "E", "Baronnetje", false, "J", "-", "Piet", "van der"),
                                MetaObjectUtil::voegPersoonGeslachtsAanduidingGroepToe,
                                b -> MetaObjectUtil.voegPersoonNummerverwijzingGroepToe(b, "1234567890", "9876543210"),
                                b -> MetaObjectUtil.voegPersoonOverlijdenGroepToe(
                                        b,
                                        actie,
                                        "BuitenlandsePlaats",
                                        "BuitenlandseRegio",
                                        20131212,
                                        "0518",
                                        "6030",
                                        "Achter de bosjes",
                                        "Rotterdam"),
                                MetaObjectUtil::voegPersoonAfgeleidAdministratiefGroepToe,
                                b -> MetaObjectUtil.voegPersoonPersoonskaartGroepToe(b, true, "051801"),
                                b -> MetaObjectUtil.voegPersoonSamengesteldeNaamGroepToe(
                                        b,
                                        "H",
                                        "geslachtsnaam",
                                        false,
                                        true,
                                        "H",
                                        " ",
                                        "Voornaam1 Voornaam2",
                                        "de"),
                                b -> MetaObjectUtil.voegPersoonUitsluitingKiesrechtGroepToe(b, 20131212, true),
                                b -> MetaObjectUtil.voegPersoonVerblijfsrechtGroepToe(b, "01", 20131211, 20141211)));

        final MetaObject persoon = builder.build();

        logMetaObject(persoon);

        final BrpPersoonslijst brpPersoon =
                mapper.map(new Persoonslijst(persoon, 0L), BrpIstTestUtils.maakSimpeleStapelAlleCategorien());

        Assert.assertNotNull(brpPersoon);
        Assert.assertNotNull(brpPersoon.getActueelAdministratienummer());
        Assert.assertNotNull(brpPersoon.getAdresStapel());
        Assert.assertNotNull(brpPersoon.getBehandeldAlsNederlanderIndicatieStapel());
        Assert.assertNotNull(brpPersoon.getBijhoudingStapel());
        Assert.assertNotNull(brpPersoon.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel());
        Assert.assertNotNull(brpPersoon.getDeelnameEuVerkiezingenStapel());
        Assert.assertNotNull(brpPersoon.getDerdeHeeftGezagIndicatieStapel());
        Assert.assertNotNull(brpPersoon.getGeboorteStapel());
        Assert.assertNotNull(brpPersoon.getGeslachtsaanduidingStapel());
        Assert.assertFalse(brpPersoon.getGeslachtsnaamcomponentStapels().isEmpty());
        Assert.assertNotNull(brpPersoon.getIdentificatienummerStapel());
        Assert.assertNotNull(brpPersoon.getInschrijvingStapel());
        Assert.assertNotNull(brpPersoon.getMigratieStapel());
        Assert.assertNotNull(brpPersoon.getNaamgebruikStapel());
        Assert.assertFalse(brpPersoon.getNationaliteitStapels().isEmpty());
        Assert.assertNotNull(brpPersoon.getNummerverwijzingStapel());
        Assert.assertNotNull(brpPersoon.getOnderCurateleIndicatieStapel());
        Assert.assertNotNull(brpPersoon.getOverlijdenStapel());
        Assert.assertNotNull(brpPersoon.getPersoonAfgeleidAdministratiefStapel());
        Assert.assertNotNull(brpPersoon.getPersoonskaartStapel());
        Assert.assertFalse(brpPersoon.getReisdocumentStapels().isEmpty());
        Assert.assertNotNull(brpPersoon.getSamengesteldeNaamStapel());
        Assert.assertNotNull(brpPersoon.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
        Assert.assertNotNull(brpPersoon.getStaatloosIndicatieStapel());
        Assert.assertNotNull(brpPersoon.getUitsluitingKiesrechtStapel());
        Assert.assertNotNull(brpPersoon.getVastgesteldNietNederlanderIndicatieStapel());
        Assert.assertNotNull(brpPersoon.getVerblijfsrechtStapel());
        Assert.assertNotNull(brpPersoon.getVerblijfsrechtStapel());
        Assert.assertFalse(brpPersoon.getVerificatieStapels().isEmpty());
        Assert.assertNotNull(brpPersoon.getVerstrekkingsbeperkingIndicatieStapel());
        Assert.assertFalse(brpPersoon.getVoornaamStapels().isEmpty());

        // Relaties
        // Assert.assertFalse(brpPersoon.getRelaties().isEmpty());

        // IST
        Assert.assertNotNull(brpPersoon.getIstGezagsverhoudingsStapel());
        Assert.assertFalse(brpPersoon.getIstHuwelijkOfGpStapels().isEmpty());
        Assert.assertFalse(brpPersoon.getIstKindStapels().isEmpty());
        Assert.assertNotNull(brpPersoon.getIstOuder1Stapel());
        Assert.assertNotNull(brpPersoon.getIstOuder2Stapel());
    }

    @Test
    public void testLeeg() {
        final MetaObject persoon = maakIngeschrevene().build();
        final BrpPersoonslijst brpPersoon = mapper.map(new Persoonslijst(persoon, 0L), new HashSet<>());

        Assert.assertNotNull(brpPersoon);
        Assert.assertNull(brpPersoon.getActueelAdministratienummer());
        Assert.assertNull(brpPersoon.getAdresStapel());
        Assert.assertNull(brpPersoon.getBehandeldAlsNederlanderIndicatieStapel());
        Assert.assertNull(brpPersoon.getBijhoudingStapel());
        Assert.assertNull(brpPersoon.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel());
        Assert.assertNull(brpPersoon.getDeelnameEuVerkiezingenStapel());
        Assert.assertNull(brpPersoon.getDerdeHeeftGezagIndicatieStapel());
        Assert.assertNull(brpPersoon.getGeboorteStapel());
        Assert.assertNull(brpPersoon.getGeslachtsaanduidingStapel());
        Assert.assertTrue(brpPersoon.getGeslachtsnaamcomponentStapels().isEmpty());
        Assert.assertNull(brpPersoon.getIdentificatienummerStapel());
        Assert.assertNull(brpPersoon.getInschrijvingStapel());
        Assert.assertNull(brpPersoon.getIstGezagsverhoudingsStapel());
        Assert.assertTrue(brpPersoon.getIstHuwelijkOfGpStapels().isEmpty());
        Assert.assertTrue(brpPersoon.getIstKindStapels().isEmpty());
        Assert.assertNull(brpPersoon.getIstOuder1Stapel());
        Assert.assertNull(brpPersoon.getIstOuder2Stapel());
        Assert.assertNull(brpPersoon.getMigratieStapel());
        Assert.assertNull(brpPersoon.getNaamgebruikStapel());
        Assert.assertTrue(brpPersoon.getNationaliteitStapels().isEmpty());
        Assert.assertNull(brpPersoon.getNummerverwijzingStapel());
        Assert.assertNull(brpPersoon.getOnderCurateleIndicatieStapel());
        Assert.assertNull(brpPersoon.getOverlijdenStapel());
        Assert.assertNull(brpPersoon.getPersoonAfgeleidAdministratiefStapel());
        Assert.assertNull(brpPersoon.getPersoonskaartStapel());
        Assert.assertTrue(brpPersoon.getReisdocumentStapels().isEmpty());
        Assert.assertTrue(brpPersoon.getRelaties().isEmpty());
        Assert.assertNull(brpPersoon.getSamengesteldeNaamStapel());
        Assert.assertNull(brpPersoon.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
        Assert.assertNull(brpPersoon.getStaatloosIndicatieStapel());
        Assert.assertNull(brpPersoon.getUitsluitingKiesrechtStapel());
        Assert.assertNull(brpPersoon.getVastgesteldNietNederlanderIndicatieStapel());
        Assert.assertNull(brpPersoon.getVerblijfsrechtStapel());
        Assert.assertNull(brpPersoon.getVerblijfsrechtStapel());
        Assert.assertTrue(brpPersoon.getVerificatieStapels().isEmpty());
        Assert.assertNull(brpPersoon.getVerstrekkingsbeperkingIndicatieStapel());
        Assert.assertTrue(brpPersoon.getVoornaamStapels().isEmpty());
    }
}
