/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.adres;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import org.junit.Test;

public class AdresvraagOphalenPersoonServiceImplTest {

    private AdresvraagOphalenPersoonServiceImpl subject = new AdresvraagOphalenPersoonServiceImpl();

    @Test
    public void enkelResultaatMetBag() throws StapMeldingException {
        subject.valideerAantalZoekResultaten(Collections.singletonList(persoonslijst("1234567890abcdef")), null, null);
    }

    @Test
    public void enkelResultaatZonderBag() throws StapMeldingException {
        subject.valideerAantalZoekResultaten(Collections.singletonList(persoonslijst(null)), null, null);
    }

    @Test
    public void meerdereResultatenGelijkeBag() throws StapMeldingException {
        subject.valideerAantalZoekResultaten(Arrays.asList(
                persoonslijst("1234567890abcdef"),
                persoonslijst("1234567890abcdef")), null, null);
    }

    @Test(expected = StapMeldingException.class)
    public void meerdereResultatenOngelijkeBag() throws StapMeldingException {
        subject.valideerAantalZoekResultaten(Arrays.asList(
                persoonslijst("1234567890abcdef"),
                persoonslijst("1234567890zzzzzz")), null, null);
    }

    @Test(expected = StapMeldingException.class)
    public void meerdereResultatenMetNullBag() throws StapMeldingException {
        subject.valideerAantalZoekResultaten(Arrays.asList(
                persoonslijst("1234567890abcdef"),
                persoonslijst(null)), null, null);
    }

    @Test(expected = StapMeldingException.class)
    public void meerdereResultatenAlleenNullBag() throws StapMeldingException {
        subject.valideerAantalZoekResultaten(Arrays.asList(
                persoonslijst(null),
                persoonslijst(null)), null, null);
    }

    private Persoonslijst persoonslijst(final String bag) {
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(3, "000123", ZonedDateTime.now(), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, ZonedDateTime.now(), "000123", 0)).build());

        MetaObject.Builder builder = TestBuilders.maakIngeschrevenPersoon();
        //@formatter:off
        builder
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId())
                        .metRecord()
                            .metId(0)
                        .eindeRecord()
                    .eindeGroep()
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metId(5)
                            .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                            .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), bag)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject();
        //@formatter:on

        return new Persoonslijst(builder.build(), 1L);
    }
}
