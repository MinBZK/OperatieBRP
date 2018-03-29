/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.Collections;
import java.util.function.Predicate;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.HistorieVanafPredikaat;
import nl.bzk.brp.domain.leveringmodel.persoon.PeilmomentHistorievormPredicate;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtHistorieFilterInformatie;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class MetaRecordFilterFactoryImplTest {

    private static final Integer DATUM_AANVANG_MATERIELE_PERIODE = 20140101;
    private final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 1L);

    @InjectMocks
    private MetaRecordFilterFactoryImpl metaRecordFilterFactory;


    @Test
    public void testMaakRecordFilters() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonList -> Collections.emptyList(),
                null,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                null);
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie =
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(null);
        final Berichtgegevens berichtgegevens =
                new Berichtgegevens(maakBerichtParameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel, new StatischePersoongegevens());

        final Predicate<MetaRecord> metaRecordPredicate = metaRecordFilterFactory.maakRecordfilters(berichtgegevens);

        Assert.assertNotNull(metaRecordPredicate);
    }


    @Test
    public void testMaakRecordFilters__MutatieberichtMetVerstrekkingsbeperking() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonList -> Collections.emptyList(),
                null,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                null);
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie =
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(null);
        final Berichtgegevens berichtgegevens =
                new Berichtgegevens(maakBerichtParameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel, new StatischePersoongegevens());
        berichtgegevens.setMutatieberichtMetMeldingVerstrekkingsbeperking(true);

        final Predicate<MetaRecord> metaRecordPredicate = metaRecordFilterFactory.maakRecordfilters(berichtgegevens);

        Assert.assertNotNull(metaRecordPredicate);
        Assert.assertTrue(ReflectionTestUtils.getField(metaRecordPredicate, "arg$2") instanceof ActueelEnIdentificerendMetaRecordPredicate);
    }


    @Test
    public void testMaakRecordFilters__SoortSyncVolledigBericht() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonList -> Collections.emptyList(),
                null,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                null);
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie =
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(null);
        final Berichtgegevens berichtgegevens =
                new Berichtgegevens(maakBerichtParameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel, new StatischePersoongegevens());

        final Predicate<MetaRecord> metaRecordPredicate = metaRecordFilterFactory.maakRecordfilters(berichtgegevens);

        Assert.assertNotNull(metaRecordPredicate);
        Assert.assertTrue(ReflectionTestUtils.getField(metaRecordPredicate, "arg$2") instanceof ConversieHistoriecorrectiePredicate);
    }


    @Test
    public void testMaakRecordFilters__DatumAanvangMaterielePeriodeGevuld() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonList -> Collections.emptyList(),
                null,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                null);
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie =
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(DATUM_AANVANG_MATERIELE_PERIODE);
        final Berichtgegevens berichtgegevens =
                new Berichtgegevens(maakBerichtParameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel, new StatischePersoongegevens());

        final Predicate<MetaRecord> metaRecordPredicate = metaRecordFilterFactory.maakRecordfilters(berichtgegevens);

        Assert.assertNotNull(metaRecordPredicate);
        Assert.assertTrue(ReflectionTestUtils.getField(metaRecordPredicate, "arg$2") instanceof HistorieVanafPredikaat);
    }


    @Test
    public void testMaakRecordFilters__HistoriefilterToegepast() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonList -> Collections.emptyList(),
                null,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                null);
        final MaakBerichtHistorieFilterInformatie
                historieFilterInformatie = new MaakBerichtHistorieFilterInformatie(HistorieVorm.GEEN, null, null);
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie =
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(DATUM_AANVANG_MATERIELE_PERIODE);
        maakBerichtPersoonInformatie.setHistorieFilterInformatie(historieFilterInformatie);
        final Berichtgegevens berichtgegevens =
                new Berichtgegevens(maakBerichtParameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel, new StatischePersoongegevens());

        final Predicate<MetaRecord> metaRecordPredicate = metaRecordFilterFactory.maakRecordfilters(berichtgegevens);

        Assert.assertNotNull(metaRecordPredicate);
        Assert.assertTrue(ReflectionTestUtils.getField(metaRecordPredicate, "arg$2") instanceof PeilmomentHistorievormPredicate);
    }

    
    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie
                tla =
                new ToegangLeveringsAutorisatie(new PartijRol(TestPartijBuilder.maakBuilder().metCode("000000").build(), Rol.AFNEMER),
                        leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}
