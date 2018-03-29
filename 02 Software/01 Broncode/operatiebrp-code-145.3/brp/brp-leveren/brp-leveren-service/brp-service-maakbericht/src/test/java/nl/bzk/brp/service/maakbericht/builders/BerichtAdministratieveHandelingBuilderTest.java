/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
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
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtAdministratieveHandelingBuilderTest.
 */
public class BerichtAdministratieveHandelingBuilderTest {

    @Test
    public void testBerichtAdministratieveHandelingBuilder() throws IOException {
        final Persoonslijst persoonslijst = maakHandeling();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final MaakBerichtParameters parameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(false);
        final Berichtgegevens
                berichtgegevens =
                new Berichtgegevens(parameters, persoonslijst, maakBerichtPersoon, autorisatiebundel, new StatischePersoongegevens());
        berichtgegevens.setGeautoriseerdVoorGedeblokkeerdeMeldingen(true);
        autoriseerActiesBinnenPersoonslijst(persoonslijst, berichtgegevens);

        final BerichtElement
                berichtElement =
                BerichtAdministratieveHandelingBuilder.build(berichtgegevens, persoonslijst.getAdministratieveHandeling()).build();
        final InputStream resourceAsStream = BerichtAdministratieveHandelingBuilderTest.class.getResourceAsStream("/administratievehandeling");
        final String expected = IOUtils.toString(resourceAsStream, "UTF-8");
        final String afdruk = new BerichtElementPrinter().maakAfdruk(berichtElement);

        Assert.assertEquals(BerichtTestUtil.removeLineEndings(expected), BerichtTestUtil.removeLineEndings(afdruk));
    }

    @Test
    public void testBerichtAdministratieveHandelingBuilder_LeverAliasSoortAdmHnd() throws IOException {
        final Persoonslijst persoonslijst = maakHandeling();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final MaakBerichtParameters parameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel(true);
        final Berichtgegevens
                berichtgegevens =
                new Berichtgegevens(parameters, persoonslijst, maakBerichtPersoon, autorisatiebundel, new StatischePersoongegevens());
        berichtgegevens.setGeautoriseerdVoorGedeblokkeerdeMeldingen(true);
        autoriseerActiesBinnenPersoonslijst(persoonslijst, berichtgegevens);

        final BerichtElement
                berichtElement =
                BerichtAdministratieveHandelingBuilder.build(berichtgegevens, persoonslijst.getAdministratieveHandeling()).build();
        final InputStream resourceAsStream = BerichtAdministratieveHandelingBuilderTest.class.getResourceAsStream("/administratievehandeling_srtnaamalias");
        final String expected = IOUtils.toString(resourceAsStream, "UTF-8");
        final String afdruk = new BerichtElementPrinter().maakAfdruk(berichtElement);

        Assert.assertEquals(BerichtTestUtil.removeLineEndings(expected), BerichtTestUtil.removeLineEndings(afdruk));
    }


    private void autoriseerActiesBinnenPersoonslijst(final Persoonslijst persoonslijst, final Berichtgegevens berichtgegevens) {
        for (AdministratieveHandeling administratieveHandeling : persoonslijst.getAdministratieveHandelingen()) {
            for (Actie actie : administratieveHandeling.getActies()) {
                berichtgegevens.autoriseerActie(actie);
            }
        }

    }

    private Autorisatiebundel maakAutorisatieBundel(final boolean indicatieAliasLeveren) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        leveringsautorisatie.setIndicatieAliasSoortAdministratieveHandelingLeveren(indicatieAliasLeveren);
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, SoortDienst.GEEF_DETAILS_PERSOON);
        return new Autorisatiebundel(tla, dienst);
    }

    private Persoonslijst maakHandeling() {
        //@formatter:off
        final ZonedDateTime tsReg = LocalDate.of(1940, 1, 2).atStartOfDay(DatumUtil.BRP_ZONE_ID);
        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(1, "000000", tsReg, SoortAdministratieveHandeling.ERKENNING)
                .metObject(MetaObject.maakBuilder()
                        .metObjectElement(Element.REGEL)
                        .metGroep()
                            .metGroepElement(Element.REGEL_IDENTITEIT)
                            .metRecord()
                                .metAttribuut(Element.REGEL_CODE.getId(), Regel.R1244.getCode())
                                .metAttribuut(Element.REGEL_MELDING.getId(), "meldingtekst")
                            .eindeRecord()
                        .eindeGroep()
                )
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_STAATLOOS, tsReg, "0000001", 20101010)
                        .metObjecten(Lists.newArrayList(
                                TestVerantwoording.maakActiebronBuilder(1, "01", "Y")
                                        .metObject(TestVerantwoording.maakDocumentBuilder(1, "bla", "bla", "bloa", "000123")),
                                TestVerantwoording.maakActiebronBuilder(2, "01", null),
                                TestVerantwoording.maakActiebronBuilder(3, null, "Y")
                        ))
                ).build();

        //@formatter:on

        final AdministratieveHandeling administratieveHandeling = AdministratieveHandeling.converter().converteer(ah);

        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metId(20)
                .metGroep()
                .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                .metRecord()
                .metId(15)
                .metActieInhoud(administratieveHandeling.getActie(1))
                .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 2)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);


    }
}
