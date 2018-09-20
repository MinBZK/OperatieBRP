/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import support.PersoonHisVolledigUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration("/test-mapper-beans.xml")
public class PersoonslijstMapperTest {

    @Inject
    private PersoonslijstMapper mapper;

    @Test
    public void testSucces() throws ReflectiveOperationException {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigImpl persoonHisVolledig = maak(builder).build();
        final ActieModel[] alleActies = PersoonHisVolledigUtil.geefAlleActies(persoonHisVolledig);
        System.out.println(Arrays.asList(alleActies));
        PersoonHisVolledigUtil.maakVerantwoording(persoonHisVolledig, alleActies);
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, null);

        final BrpPersoonslijst brpPersoon = mapper.map(persoonHisVolledigView, BrpIstTestUtils.maakSimpeleStapelAlleCategorien());

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
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final Set<Stapel> istStapels = new HashSet<>();

        final BrpPersoonslijst brpPersoon = mapper.map(persoonHisVolledig, istStapels);

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

    public PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) throws ReflectiveOperationException {
        AdresMapperTest.maak(builder);
        BehandeldAlsNederlanderIndicatieMapperTest.maak(builder);
        BijhoudingMapperTest.maak(builder);
        BijzondereVerblijfsrechtelijkePositieIndicatieMapperTest.maak(builder);
        DeelnameEuVerkiezingenMapperTest.maak(builder);
        DerdeHeeftGezagIndicatieMapperTest.maak(builder);
        PersoonGeboorteMapperTest.maak(builder);
        PersoonGeslachtsaanduidingMapperTest.maak(builder);
        GeslachtsnaamcomponentenMapperTest.maak(builder);
        PersoonIdentificatieNummersMapperTest.maak(builder);
        InschrijvingMapperTest.maak(builder);
        MigratieMapperTest.maak(builder);
        NaamgebruikMapperTest.maak(builder);
        NationaliteitenMapperTest.maak(builder);
        NummerverwijzingMapperTest.maak(builder);
        OnderCurateleIndicatieMapperTest.maak(builder);
        OverlijdenMapperTest.maak(builder);
        PersoonAfgeleidAdministratiefMapperTest.maak(builder);
        PersoonskaartMapperTest.maak(builder);
        ReisdocumentenMapperTest.maak(builder);
        PersoonSamengesteldeNaamMapperTest.maak(builder);
        SignaleringMetBetrekkingTotVerstrekkenReisdocumentMapperTest.maak(builder);
        StaatloosIndicatieMapperTest.maak(builder);
        UitsluitingKiesrechtMapperTest.maak(builder);
        VastgesteldNietNederlanderIndicatieMapperTest.maak(builder);
        VerblijfsrechtMapperTest.maak(builder);
        VerificatiesMapperTest.maak(builder);
        VerstrekkingsbeperkingIndicatieMapperTest.maak(builder);
        VoornamenMapperTest.maak(builder);
        return builder;
    }
}
