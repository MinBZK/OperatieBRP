/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.BepaalVolgnummerServiceImpl;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtBuilderTest.
 */
public class BerichtBuilderTest {
    final AdministratieveHandeling administratieveHandeling =
            TestVerantwoording.maakAdministratievehandelingMetActies(1, ZonedDateTime.of(2010, 3, 22, 4, 10, 30, 17000000, DatumUtil.BRP_ZONE_ID));
    final Actie actieInhoud = administratieveHandeling.getActie(1);

    @Test
    public void testWrapper() {
        final MetaObject persoon = TestBuilders.LEEG_PERSOON;
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, maakAutorisatieBundel(), SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final BerichtBuilder berichtBuilder = new BerichtBuilder(berichtgegevens, 1);
        final BerichtElement berichtElement = berichtBuilder.build(persoon);
        Assert.assertNotNull(berichtElement);
    }

    @Test
    public void testBerichtBuilder() throws IOException {
        final MetaObject persoon = maakPersoon();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, maakAutorisatieBundel(), SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final BerichtBuilder berichtBuilder = new BerichtBuilder(berichtgegevens, 1);
        final BerichtElement berichtElement = berichtBuilder.build(persoon);

        final InputStream resourceAsStream = BerichtAdministratieveHandelingBuilderTest.class.getResourceAsStream("/compleetbericht");
        final String expected = IOUtils.toString(resourceAsStream, "UTF-8");

        final String afdruk = new BerichtElementPrinter().maakAfdruk(berichtElement);
        Assert.assertEquals(BerichtTestUtil.removeLineEndings(expected), BerichtTestUtil.removeLineEndings(afdruk));
    }


    @Test
    public void testBerichtBuilder_Mutatiebericht() throws IOException {
        final MetaObject persoon = maakPersoon();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, maakAutorisatieBundel(), SoortSynchronisatie.MUTATIE_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final BerichtBuilder berichtBuilder = new BerichtBuilder(berichtgegevens, 1);
        final BerichtElement berichtElement = berichtBuilder.build(persoon);

        final InputStream resourceAsStream = BerichtAdministratieveHandelingBuilderTest.class.getResourceAsStream("/mutatiebericht");
        final String expected = IOUtils.toString(resourceAsStream, "UTF-8");

        final String afdruk = new BerichtElementPrinter().maakAfdruk(berichtElement);
        Assert.assertEquals(BerichtTestUtil.removeLineEndings(expected), BerichtTestUtil.removeLineEndings(afdruk));
    }

    private Autorisatiebundel maakAutorisatieBundel() {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.PLAATSING_AFNEMERINDICATIE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }

    private Berichtgegevens maakBerichtgegevens(final MetaObject persoon,
                                                final Autorisatiebundel autorisatieBundel,
                                                final SoortSynchronisatie soortSynchronisatie) {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final StatischePersoongegevens statischePersoongegevens = new StatischePersoongegevens();
        statischePersoongegevens.setVolgnummerMap(new BepaalVolgnummerServiceImpl().bepaalVolgnummers(persoonslijst));
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(soortSynchronisatie), autorisatieBundel, statischePersoongegevens);
        berichtgegevens.setGeautoriseerdeHandelingen(Sets.newHashSet(administratieveHandeling));

        return berichtgegevens;
    }

    private MetaObject maakPersoon() {
        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                    .metRecord()
                        .metId(4)
                        .metAttribuut(getAttribuutElement(Element.SOORTPERSOON_CODE.getId()), SoortPersoon.INGESCHREVENE.getCode())
                    .eindeRecord()
                .eindeGroep()
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_NAAMGEBRUIK.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .metObject()
                    .metObjectElement(getObjectElement(Element.PERSOON_VOORNAAM.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_VOORNAAM_STANDAARD.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                    .metObjectElement(getObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId()))
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
            .metObject()
                .metId(111)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metAttribuut(Element.ONDERZOEK_PARTIJCODE.getId(), 1)
                        .eindeRecord()
                    .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.ONDERZOEK_DATUMEINDE.getId(), 20000101)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_ADRES_HUISNUMMER.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 456)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
            .metObject()
                .metId(0)
                .metObjectElement(getObjectElement(Element.PERSOON_OUDER))
                .metObject()
                    .metId(0)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metObjecten(Lists.newArrayList(
                        MetaObject.maakBuilder()
                            .metId(0)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEKIND)),
                        MetaObject.maakBuilder()
                            .metId(10)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                    ))
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return metaObject;
    }
}
