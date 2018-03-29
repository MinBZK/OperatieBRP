/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtHistorieFilterInformatie;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeefDetailsPersoonCorrigeerVoorScopingTest {

    private static final Actie actieInhoud = TestVerantwoording.maakActie(1, LocalDate.of(2005, 1, 1).atStartOfDay(DatumUtil.BRP_ZONE_ID));

    private Berichtgegevens berichtgegevens;
    private Set<AttribuutElement> scopingElementen;
    private MaakBerichtParameters maakBerichtParameters;

    private final GeefDetailsPersoonCorrigeerVoorScoping geefDetailsPersoonCorrigeerVoorScoping = new GeefDetailsPersoonCorrigeerVoorScoping();


    @Before
    public void voorTest() {
        maakBerichtParameters = new MaakBerichtParameters();
        maakBerichtParameters.setVerantwoordingLeveren(true);
        maakBerichtParameters.setAutorisatiebundels(maakAutorisatiebundel());
    }

    @Test
    public void testRecordBevatGeenVerplichtAttrGeenAttrUitScoping() {

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE));
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertFalse(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
    }

    @Test
    public void testRecordBevatGeenVerplichtAttrWelGescopedAttr() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_IDENTITEIT))
                        .eindeGroep()
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD))
                                .metRecord()
                                    .metId(1)
                                    .metActieInhoud(actieInhoud)
                                    .metAttribuut(getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING), "Omschrijving")
                                .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING));
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertFalse(isGroepGeautoriseerd(Element.ONDERZOEK_IDENTITEIT));
        Assert.assertTrue(isGroepGeautoriseerd(Element.ONDERZOEK_STANDAARD));
        Assert.assertTrue(isObjectGeautoriseerd(Element.ONDERZOEK));
    }


    @Test
    public void testRecordBevatWelVerplichtAttrGeenGescopedAttr() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_IDENTITEIT))
                        .eindeGroep()
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD))
                                .metRecord()
                                    .metId(1)
                                    .metActieInhoud(actieInhoud)
                                    .metAttribuut(getAttribuutElement(Element.ONDERZOEK_DATUMAANVANG), 20140101)
                                .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM));
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertFalse(isGroepGeautoriseerd(Element.ONDERZOEK_IDENTITEIT));
        Assert.assertTrue(isGroepGeautoriseerd(Element.ONDERZOEK_STANDAARD));
        Assert.assertTrue(isObjectGeautoriseerd(Element.ONDERZOEK));
    }

    @Test
    public void testRecordBinnenGenestObjectBevatGeenVerplichtAttrWelGescopedAttr() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_IDENTITEIT))
                        .eindeGroep()
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD))
                                .metRecord()
                                    .metId(1)
                                    .metActieInhoud(actieInhoud)
                                .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING));
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertFalse(isGroepGeautoriseerd(Element.ONDERZOEK_IDENTITEIT));
        Assert.assertFalse(isGroepGeautoriseerd(Element.ONDERZOEK_STANDAARD));
        Assert.assertFalse(isObjectGeautoriseerd(Element.ONDERZOEK));
    }


    @Test
    public void testRecordBinnenGenestObjectBevatGeenVerplichtAttrWelGescopedAttr_HistoriefilterToegepast() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_IDENTITEIT))
                        .eindeGroep()
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD))
                                .metRecord()
                                    .metId(1)
                                    .metActieInhoud(actieInhoud)
                                .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING));
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        maakBerichtPersoonInformatie.setHistorieFilterInformatie(
                new MaakBerichtHistorieFilterInformatie(HistorieVorm.GEEN, DatumUtil.vandaag(), ZonedDateTime.now()));
        final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap = new HashMap<>();
        final Map<Persoonslijst, MaakBerichtPersoonInformatie> persInfoMap = new HashMap<>();
        persInfoMap.put(persoonslijst, maakBerichtPersoonInformatie);
        maakBerichtPersoonMap.put(maakBerichtParameters.getAutorisatiebundels().get(0), persInfoMap);
        maakBerichtParameters.setMaakBerichtPersoonMap(maakBerichtPersoonMap);

        berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                maakBerichtPersoonInformatie, maakBerichtParameters.getAutorisatiebundels().get(0),
                new StatischePersoongegevens());
        new AutorisatieAlles(berichtgegevens);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertFalse(isGroepGeautoriseerd(Element.ONDERZOEK_IDENTITEIT));
        Assert.assertFalse(isGroepGeautoriseerd(Element.ONDERZOEK_STANDAARD));
        Assert.assertTrue(isObjectGeautoriseerd(Element.ONDERZOEK));
    }

    @Test
    public void testAttrSoortAutorisatieOngeldig_Aanbevolen() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE), "locatie")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE));
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertFalse(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
        Assert.assertFalse(isAttribuutGeautoriseerd(Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE));
    }


    @Test
    public void testAttrSoortAutorisatieOngeldig_Optioneel() {
//@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metObject().metId(111)
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_ADRES_IDENTITEIT))
                    .eindeGroep()
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD))
                          .metRecord()
                              .metId(1)
                              .metActieInhoud(actieInhoud)
                              .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), 1)           //srt aut = Optioneel
                          .eindeRecord()
                        .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM));
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        //niet geautoriseerd want_optioneel
        Assert.assertFalse(isAttribuutGeautoriseerd(Element.PERSOON_ADRES_HUISNUMMER));
    }

    @Test
    public void testAttrSoortAutorisatieOngeldig_Bijhoudingsgegeven() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_IDENTITEIT))
                        .eindeGroep()
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD))
                                .metRecord()
                                    .metId(1)
                                    .metActieInhoud(actieInhoud)
                                    .metAttribuut(getAttribuutElement(Element.ONDERZOEK_DATUMAANVANG), 20140101)    //srt aut = Verplicht
                                    .metAttribuut(getAttribuutElement(Element.ONDERZOEK_STATUSNAAM), "S")           //srt aut = Bijhoudingsgegeven
                                .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM));
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertFalse(isGroepGeautoriseerd(Element.ONDERZOEK_IDENTITEIT));
        Assert.assertTrue(isGroepGeautoriseerd(Element.ONDERZOEK_STANDAARD));
        Assert.assertTrue(isObjectGeautoriseerd(Element.ONDERZOEK));
        //niet geautoriseerd want bijhoudingsgegeven
        Assert.assertFalse(isAttribuutGeautoriseerd(Element.ONDERZOEK_STATUSNAAM));
        //wel geautoriseerd want verplicht
        Assert.assertTrue(isAttribuutGeautoriseerd(Element.ONDERZOEK_DATUMAANVANG));
    }

    @Test
    public void testGeenScopingToegepastGeenVerplichtAttr() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE));
        maakBerichtParameters.setGevraagdeElementen(Sets.newHashSet());
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertTrue(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
    }

    @Test
    public void testGeenGeefDetailsPersoonDienst() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        scopingElementen = Sets.newHashSet(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE));
        final SoortDienst soortDienst = SoortDienst.ATTENDERING;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(new PartijRol(TestPartijBuilder.maakBuilder().metCode("000000").build(), Rol.AFNEMER),
                leveringsautorisatie);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);

        geefDetailsPersoonCorrigeerVoorScoping.execute(berichtgegevens);

        Assert.assertTrue(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
    }

    private boolean isAttribuutGeautoriseerd(Element attribuutElement) {
        return berichtgegevens.isGeautoriseerd(Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex()
                .geefAttributen(getAttribuutElement(attribuutElement))));
    }

    private boolean isGroepGeautoriseerd(Element groepElement) {
        return berichtgegevens.isGeautoriseerd(Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement
                (getGroepElement(groepElement))));
    }

    private boolean isObjectGeautoriseerd(Element objectElement) {
        return berichtgegevens.isGeautoriseerd(Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex()
                .geefObjecten(getObjectElement(objectElement))));
    }

    private Berichtgegevens maakBerichtgegevens(final MaakBerichtParameters parameters, final MetaObject persoon) {
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        berichtgegevens = new Berichtgegevens(parameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), parameters.getAutorisatiebundels().get(0),
                new StatischePersoongegevens());
        new AutorisatieAlles(berichtgegevens);
        return berichtgegevens;
    }

    private List<Autorisatiebundel> maakAutorisatiebundel() {
        final SoortDienst soortDienst = SoortDienst.GEEF_DETAILS_PERSOON;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(new PartijRol(TestPartijBuilder.maakBuilder().metCode("000000").build(), Rol.AFNEMER),
                leveringsautorisatie);
        return Lists.newArrayList(new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst)));
    }
}
