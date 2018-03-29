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
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
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
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GeefDetailsPersoonOpschonenObjectenTest {

    private static final Actie actieInhoud = TestVerantwoording.maakActie(1, LocalDate.of(2005, 1, 1).atStartOfDay(DatumUtil.BRP_ZONE_ID));

    private Berichtgegevens berichtgegevens;
    private MaakBerichtParameters maakBerichtParameters;

    private final GeefDetailsPersoonOpschonenObjecten geefDetailsPersoonOpschonenObjecten = new GeefDetailsPersoonOpschonenObjecten();

    @Before
    public void voorTest() {
        maakBerichtParameters = new MaakBerichtParameters();
        maakBerichtParameters.setVerantwoordingLeveren(true);
        maakBerichtParameters.setGevraagdeElementen(Sets.newHashSet());
    }


    @Test
    public void testFilteringZonderHistoriefilter() {
        List<Autorisatiebundel> autorisatiebundels = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        final Persoonslijst persoonslijst = maakPersoon();
        maakBerichtParameters.setMaakBerichtPersoonMap(maakPersInfoMapZonderHistorieFilterInfo(persoonslijst, autorisatiebundels.get(0), SoortDienst.GEEF_DETAILS_PERSOON));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters,persoonslijst);
        verwijderAutorisaties();

        geefDetailsPersoonOpschonenObjecten.execute(berichtgegevens);

        //er mag niets gefilterd zijn
        Assert.assertTrue(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
        Assert.assertTrue(isObjectGeautoriseerd(Element.PERSOON_ADRES));
        Assert.assertTrue(isObjectGeautoriseerd(Element.PERSOON_NATIONALITEIT));
    }

    @Test
    public void testFilteringZonderHistoriefilter_DienstSelectie() {
        List<Autorisatiebundel> autorisatiebundels = maakAutorisatiebundel(SoortDienst.SELECTIE);
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        final Persoonslijst persoonslijst = maakPersoon();
        maakBerichtParameters.setMaakBerichtPersoonMap(maakPersInfoMapZonderHistorieFilterInfo(persoonslijst, autorisatiebundels.get(0), SoortDienst.SELECTIE));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters,persoonslijst);
        verwijderAutorisaties();

        geefDetailsPersoonOpschonenObjecten.execute(berichtgegevens);

        //er mag niets gefilterd zijn
        Assert.assertTrue(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
        Assert.assertTrue(isObjectGeautoriseerd(Element.PERSOON_ADRES));
        Assert.assertTrue(isObjectGeautoriseerd(Element.PERSOON_NATIONALITEIT));
    }


    @Test
    public void testFilteringMetHistoriefilter() {
        List<Autorisatiebundel> autorisatiebundels = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        final Persoonslijst persoon = maakPersoon();
        maakBerichtParameters.setMaakBerichtPersoonMap(maakPersInfoMap(persoon, autorisatiebundels.get(0), SoortDienst.GEEF_DETAILS_PERSOON));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);
        verwijderAutorisaties();

        geefDetailsPersoonOpschonenObjecten.execute(berichtgegevens);

        //object niet gefilterd want bevat onderliggende autorisaties
        Assert.assertTrue(isObjectGeautoriseerd(Element.PERSOON_ADRES));
        //object gefilterd want bevat geen onderliggende autorisaties meer
        Assert.assertFalse(isObjectGeautoriseerd(Element.PERSOON_NATIONALITEIT));
        //groepen worden niet geraakt
        Assert.assertTrue(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
    }


    @Test
    public void testFilteringMetHistoriefilter_DienstSelectie() {
        List<Autorisatiebundel> autorisatiebundels = maakAutorisatiebundel(SoortDienst.SELECTIE);
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        final Persoonslijst persoon = maakPersoon();
        maakBerichtParameters.setMaakBerichtPersoonMap(maakPersInfoMap(persoon, autorisatiebundels.get(0), SoortDienst.SELECTIE));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);
        verwijderAutorisaties();

        geefDetailsPersoonOpschonenObjecten.execute(berichtgegevens);

        //object niet gefilterd want bevat onderliggende autorisaties
        Assert.assertTrue(isObjectGeautoriseerd(Element.PERSOON_ADRES));
        //object gefilterd want bevat geen onderliggende autorisaties meer
        Assert.assertFalse(isObjectGeautoriseerd(Element.PERSOON_NATIONALITEIT));
        //groepen worden niet geraakt
        Assert.assertTrue(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
    }


    @Test
    public void testFilteringMetHistoriefilterAndereDienst() {
        final List<Autorisatiebundel> autorisatiebundels = maakAutorisatiebundel(SoortDienst.SYNCHRONISATIE_PERSOON);
        final Persoonslijst persoon = maakPersoon();
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        maakBerichtParameters.setMaakBerichtPersoonMap(maakPersInfoMap(persoon, autorisatiebundels.get(0), SoortDienst.SYNCHRONISATIE_PERSOON));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoon);
        verwijderAutorisaties();

        geefDetailsPersoonOpschonenObjecten.execute(berichtgegevens);

        //object niet gefilterd want bevat onderliggende autorisaties
        Assert.assertTrue(isObjectGeautoriseerd(Element.PERSOON_ADRES));
        //object gefilterd want bevat geen onderliggende autorisaties meer
        Assert.assertTrue(isObjectGeautoriseerd(Element.PERSOON_NATIONALITEIT));
        //groepen worden niet geraakt
        Assert.assertTrue(isGroepGeautoriseerd(Element.PERSOON_GEBOORTE));
    }

    @Test
    public void testFilterBetrokkenheden() {
        //filter partner (heeft geen identiteit) en toon kind
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
            .metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord()
                    .metActieInhoud(actieInhoud)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()), "12345569")
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_OUDER))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_OUDER_IDENTITEIT))
                    .metRecord().metId(1).metActieInhoud(actieInhoud).eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_OUDER_OUDERSCHAP))
                    .metRecord().metId(1).metActieInhoud(actieInhoud).eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(getObjectElement(Element.GERELATEERDEKIND))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEKIND_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTITEIT))
                            .eindeGroep()
                            .eindeObject()
                        .eindeObject()
                .eindeObject()
            .eindeObject()
            .metObject()
                .metId(5)
                .metObjectElement(getObjectElement(Element.PERSOON_PARTNER.getId()))
                .metObject()
                    .metObjectElement(getObjectElement(Element.HUWELIJK.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.HUWELIJK_STANDAARD.getId()))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId()))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT.getId()))
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()))
                            .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT.getId()))
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        List<Autorisatiebundel> autorisatiebundels = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        maakBerichtParameters.setMaakBerichtPersoonMap(maakPersInfoMap(persoonslijst, autorisatiebundels.get(0), SoortDienst.GEEF_DETAILS_PERSOON));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoonslijst);

        geefDetailsPersoonOpschonenObjecten.execute(berichtgegevens);

        //kind heeft een geautoriseerde identiteitsgroep
        Assert.assertTrue(isObjectGeautoriseerd(Element.GERELATEERDEKIND));
        //gerelateerde kindepersoon heeft een geautoriseerde identiteitsgroep
        Assert.assertTrue(isObjectGeautoriseerd(Element.GERELATEERDEKIND_PERSOON));
        //gerelateerde huwelijkspartner heeft geen geautoriseerde identiteitsgroep
        Assert.assertFalse(isObjectGeautoriseerd(Element.GERELATEERDEHUWELIJKSPARTNER));

    }

    @Test
    public void testObjectIsGegevenInOnderzoek() {

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)

                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                        .metRecord()
                            .metId(4)
                            .metActieInhoud(actieInhoud)
                        .eindeRecord()
                .eindeGroep()

                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.ONDERZOEK_IDENTITEIT.getId()))
                        .metRecord()
                            .metId(1)
                        .eindeRecord()
                    .eindeGroep()
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD.getId()))
                        .metRecord()
                            .metId(1)
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(getAttribuutElement(Element.ONDERZOEK_DATUMAANVANG.getId()), 20101010)
                            .metAttribuut(getAttribuutElement(Element.ONDERZOEK_OMSCHRIJVING.getId()), "een omschrijving")
                        .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(getObjectElement(Element.GEGEVENINONDERZOEK))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT))
                            .metRecord()
                                .metId(1)
                                .metActieInhoud(actieInhoud)
                                .metAttribuut(getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM), "Persoon.Voornaam")
                                .metAttribuut(getAttribuutElement(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN), 1)
                            .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
                .eindeObject()
        .build();
        //@formatter:on

        List<Autorisatiebundel> autorisatiebundels = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        maakBerichtParameters.setMaakBerichtPersoonMap(maakPersInfoMap(persoonslijst, autorisatiebundels.get(0), SoortDienst.GEEF_DETAILS_PERSOON));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoonslijst);

        geefDetailsPersoonOpschonenObjecten.execute(berichtgegevens);

        Assert.assertTrue(isObjectGeautoriseerd(Element.ONDERZOEK));
        Assert.assertTrue(isObjectGeautoriseerd(Element.GEGEVENINONDERZOEK));
    }

    @Test
    public void testObjectOnderliggendeAutorisatiesZijnNietGeautoriseerdeObjecten() {
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK.getId()))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.ONDERZOEK_IDENTITEIT.getId()))
                            .metRecord()
                                .metId(1)
                            .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        List<Autorisatiebundel> autorisatiebundels = maakAutorisatiebundel(SoortDienst.GEEF_DETAILS_PERSOON);
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        maakBerichtParameters.setMaakBerichtPersoonMap(maakPersInfoMap(persoonslijst, autorisatiebundels.get(0), SoortDienst.GEEF_DETAILS_PERSOON));
        berichtgegevens = maakBerichtgegevens(maakBerichtParameters, persoonslijst);
        berichtgegevens.verwijderAutorisatie(Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(
                getGroepElement(Element.ONDERZOEK_IDENTITEIT))));

        geefDetailsPersoonOpschonenObjecten.execute(berichtgegevens);

        Assert.assertFalse(isObjectGeautoriseerd(Element.ONDERZOEK));
        Assert.assertFalse(isGroepGeautoriseerd(Element.ONDERZOEK_IDENTITEIT));
    }

    private void verwijderAutorisatieOpRecordBinnenGroepBinnenObject(Element objectElement, Element groepElement, int id) {
        MetaGroep groep = Iterables.getOnlyElement(berichtgegevens.getPersoonslijst()
                .getMetaObject().getObjecten(getObjectElement(objectElement.getId()))).getGroep(getGroepElement(groepElement));
        final MetaRecord metaRecord = Iterables.find(groep.getRecords(), record -> id == record.getVoorkomensleutel());
        berichtgegevens.verwijderAutorisatie(metaRecord);
    }

    private void verwijderAutorisatieOpRecordBinnenGroep(Element groepElement, int id) {
        MetaGroep groep = berichtgegevens.getPersoonslijst()
                .getMetaObject().getGroep(getGroepElement(groepElement));
        final MetaRecord metaRecord = Iterables.find(groep.getRecords(), record -> id == record.getVoorkomensleutel());
        berichtgegevens.verwijderAutorisatie(metaRecord);
    }

    private void verwijderAutorisatieOpGroep(Element objectElement, Element groepElement) {
        MetaGroep groep = Iterables.getOnlyElement(berichtgegevens.getPersoonslijst()
                .getMetaObject().getObjecten(getObjectElement(objectElement.getId()))).getGroep(getGroepElement(groepElement));
        berichtgegevens.verwijderAutorisatie(groep);
    }

    private boolean isGroepGeautoriseerd(Element groepElement) {
        return berichtgegevens.isGeautoriseerd(Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement
                (getGroepElement(groepElement))));
    }


    private boolean isObjectGeautoriseerd(Element objectElement) {
        return berichtgegevens.isGeautoriseerd(Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex()
                .geefObjecten(getObjectElement(objectElement))));
    }

    private Berichtgegevens maakBerichtgegevens(final MaakBerichtParameters parameters, final Persoonslijst persoonslijst) {
        Autorisatiebundel autorisatiebundel = parameters.getAutorisatiebundels().get(0);
        MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = parameters.getMaakBerichtPersoonMap().get(autorisatiebundel).get(persoonslijst);
        berichtgegevens = new Berichtgegevens(parameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel, new StatischePersoongegevens());
        new AutorisatieAlles(berichtgegevens);
        return berichtgegevens;
    }

    private Persoonslijst maakPersoon() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
                .metObject()
                    .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId()))
                        .metRecord()
                            .metId(1)
                        .eindeRecord()
                    .eindeGroep()
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                            .metId(2)
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0123")
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 1)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING.getId()), "dummy")
                        .eindeRecord()
                        .metRecord()
                            .metId(3)
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0123")
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 1)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING.getId()), "dummy")
                            .metDatumAanvangGeldigheid(19980201)
                            .metDatumEindeGeldigheid(20010201)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()

                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()

                .metObject()
                    .metObjectElement(getObjectElement(Element.PERSOON_NATIONALITEIT.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId()))
                        .metRecord()
                            .metId(6)
                        .eindeRecord()
                    .eindeGroep()
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId()))
                        .metRecord()
                            .metId(7)
                            .metActieInhoud(actieInhoud)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
            .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }

    private List<Autorisatiebundel> maakAutorisatiebundel(final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie
                tla =
                new ToegangLeveringsAutorisatie(new PartijRol(TestPartijBuilder.maakBuilder().metCode("000000").build(), Rol.AFNEMER),
                        leveringsautorisatie);
        return Lists.newArrayList(new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst)));
    }


    private void verwijderAutorisaties() {
        verwijderAutorisatieOpRecordBinnenGroepBinnenObject(Element.PERSOON_NATIONALITEIT, Element.PERSOON_NATIONALITEIT_STANDAARD, 7);
        verwijderAutorisatieOpGroep(Element.PERSOON_NATIONALITEIT, Element.PERSOON_NATIONALITEIT_IDENTITEIT);
        verwijderAutorisatieOpGroep(Element.PERSOON_NATIONALITEIT, Element.PERSOON_NATIONALITEIT_STANDAARD);
        verwijderAutorisatieOpRecordBinnenGroep(Element.PERSOON_GEBOORTE, 4);
    }

    private Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakPersInfoMap(final Persoonslijst persoon, final Autorisatiebundel
            autorisatiebundel, final SoortDienst soortDienst) {
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        maakBerichtPersoonInformatie.setHistorieFilterInformatie(
                new MaakBerichtHistorieFilterInformatie(HistorieVorm.GEEN, DatumUtil.vandaag(), ZonedDateTime.now()));
        final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap = new HashMap<>();
        final Map<Persoonslijst, MaakBerichtPersoonInformatie> persInfoMap = new HashMap<>();
        persInfoMap.put(persoon, maakBerichtPersoonInformatie);
        maakBerichtPersoonMap.put(autorisatiebundel, persInfoMap);
        return maakBerichtPersoonMap;
    }

    private Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakPersInfoMapZonderHistorieFilterInfo(final Persoonslijst persoon, final
    Autorisatiebundel autorisatiebundel, final SoortDienst soortDienst) {
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap = new HashMap<>();
        final Map<Persoonslijst, MaakBerichtPersoonInformatie> persInfoMap = new HashMap<>();
        persInfoMap.put(persoon, maakBerichtPersoonInformatie);
        maakBerichtPersoonMap.put(autorisatiebundel, persInfoMap);
        return maakBerichtPersoonMap;
    }
}
