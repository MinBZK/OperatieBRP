/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * AutoriseerAfnemerindicatiesServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerwijderAfnemerindicatieServiceImplTest {

    @InjectMocks
    private VerwijderAfnemerindicatieServiceImpl autoriseerAfnemerindicatiesService;

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;

    @Test
    public void testFilterGeheimeAfnemerindicatie() {
        final int leveringAutorisatieId = 1;
        Mockito.when(leveringsautorisatieService.geefLeveringautorisatie(leveringAutorisatieId))
                .thenReturn(bouwLeveringsautorisatieGeheim(leveringAutorisatieId));
        final Persoonslijst persoonslijst = maakPersoonMetAfnemerindicatie(leveringAutorisatieId, "000001");
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst
                .GEEF_DETAILS_PERSOON), SoortDienst.GEEF_DETAILS_PERSOON));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());
        //autoriseer alles
        new AutorisatieAlles(berichtgegevens);

        berichtgegevens.setKandidaatRecords(Sets.newHashSet());
        persoonslijst.getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                berichtgegevens.getKandidaatRecords().add(record);
            }
        });
        //test
        autoriseerAfnemerindicatiesService.execute(berichtgegevens);
        //controleer
        final Set<MetaObject> afnemerIndicaties = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(Element.PERSOON_AFNEMERINDICATIE);
        for (MetaObject afnemerIndicatie : afnemerIndicaties) {
            Assert.assertFalse(berichtgegevens.isGeautoriseerd(afnemerIndicatie));
        }
    }

    @Test
    public void testGeenFilterGewoneAfnemerindicatie() {
        final int leveringAutorisatieId = 1;
        Mockito.when(leveringsautorisatieService.geefLeveringautorisatie(leveringAutorisatieId)).thenReturn(bouwLeveringsautorisatieNietGeheim(
                leveringAutorisatieId));
        final Persoonslijst persoonslijst = maakPersoonMetAfnemerindicatie(leveringAutorisatieId, "000001");
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst
                .GEEF_DETAILS_PERSOON), SoortDienst.GEEF_DETAILS_PERSOON));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());
        //autoriseer alles
        new AutorisatieAlles(berichtgegevens);
        berichtgegevens.setKandidaatRecords(Sets.newHashSet());
        persoonslijst.getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                berichtgegevens.getKandidaatRecords().add(record);
            }
        });

        //test
        autoriseerAfnemerindicatiesService.execute(berichtgegevens);

        //controleer
        final Set<MetaObject> afnemerIndicaties = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(Element.PERSOON_AFNEMERINDICATIE);
        for (MetaObject afnemerIndicatie : afnemerIndicaties) {
            Assert.assertTrue(berichtgegevens.isGeautoriseerd(afnemerIndicatie));
        }
    }

    @Test
    public void testFilterGewoneAfnemerindicatieGeenKandidaatRecord() {
        final int leveringAutorisatieId = 1;
        Mockito.when(leveringsautorisatieService.geefLeveringautorisatie(leveringAutorisatieId)).thenReturn(bouwLeveringsautorisatieNietGeheim(
                leveringAutorisatieId));
        final Persoonslijst persoonslijst = maakPersoonMetAfnemerindicatie(leveringAutorisatieId, "000001");
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst
                .GEEF_DETAILS_PERSOON), SoortDienst.GEEF_DETAILS_PERSOON));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());
        //autoriseer alles
        new AutorisatieAlles(berichtgegevens);
        berichtgegevens.setKandidaatRecords(Sets.newHashSet());

        //test
        autoriseerAfnemerindicatiesService.execute(berichtgegevens);

        //controleer
        final Set<MetaObject> afnemerIndicaties = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(Element.PERSOON_AFNEMERINDICATIE);
        for (MetaObject afnemerIndicatie : afnemerIndicaties) {
            Assert.assertFalse(berichtgegevens.isGeautoriseerd(afnemerIndicatie));
        }
    }

    @Test
    public void testGeenGeefDetailsPersoonDienst() {
        final int leveringAutorisatieId = 1;
        Mockito.when(leveringsautorisatieService.geefLeveringautorisatie(leveringAutorisatieId)).thenReturn(bouwLeveringsautorisatieNietGeheim(
                leveringAutorisatieId));
        final Persoonslijst persoonslijst = maakPersoonMetAfnemerindicatie(leveringAutorisatieId, "000001");
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst
                .ZOEK_PERSOON), SoortDienst.ZOEK_PERSOON));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());
        //autoriseer alles
        new AutorisatieAlles(berichtgegevens);
        berichtgegevens.setKandidaatRecords(Sets.newHashSet());
        persoonslijst.getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                berichtgegevens.getKandidaatRecords().add(record);
            }
        });

        //test
        autoriseerAfnemerindicatiesService.execute(berichtgegevens);

        //controleer
        final Set<MetaObject> afnemerIndicaties = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefObjecten(Element.PERSOON_AFNEMERINDICATIE);
        for (MetaObject afnemerIndicatie : afnemerIndicaties) {
            Assert.assertFalse(berichtgegevens.isGeautoriseerd(afnemerIndicatie));
        }
    }

    private Leveringsautorisatie bouwLeveringsautorisatieGeheim(int leveringsAutorisatieId) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEHEIM);
        return leveringsautorisatie;
    }

    private Leveringsautorisatie bouwLeveringsautorisatieNietGeheim(int leveringsAutorisatieId) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        return leveringsautorisatie;
    }

    private Persoonslijst maakPersoonMetAfnemerindicatie(final Integer leveringsautorisatieId, final String afnemerCode) {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
            .metObject(TestBuilders.maakAfnemerindicatie(leveringsautorisatieId, afnemerCode))
            .eindeObject()
        .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }
}
