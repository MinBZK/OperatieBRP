/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;

import java.util.Arrays;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import org.junit.Test;

/**
 * Test voor ophalen persoon service.
 */
public class PersoonsvraagWebserviceOphalenPersoonServiceImplTest {

    final PersoonsvraagWebserviceOphalenPersoonServiceImpl subject = new PersoonsvraagWebserviceOphalenPersoonServiceImpl();

    @Test(expected = StapMeldingException.class)
    public void testGeenPersoonslijsten() throws StapMeldingException {
        subject.valideerAantalZoekResultaten(Collections.emptyList(), null, null);
    }

    @Test
    public void testEenPersoonslijst() throws StapMeldingException {
        final MetaObject persoon = maakPersoon();

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 1L);
        subject.valideerAantalZoekResultaten(Collections.singletonList(persoonslijst), null, null);
    }

    @Test
    public void testMeerderePersoonslijsten() throws StapMeldingException {

        final MetaObject persoon = maakPersoon();
        final Persoonslijst persoonslijst1 = new Persoonslijst(persoon, 1L);
        final Persoonslijst persoonslijst2 = new Persoonslijst(persoon, 2L);
        subject.valideerAantalZoekResultaten(Arrays.asList(persoonslijst1, persoonslijst2), null, null);
    }

    private MetaObject maakPersoon() {
        //@formatter:off
        return TestBuilders.maakIngeschrevenPersoon()
                .metGroep()
                    .metGroepElement(Element.PERSOON_BIJHOUDING.getId())
                    .metRecord()
                        .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.van(2015, 1, 1)))
                        .metAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId(), "A")
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.van(2015, 1, 1)))
                            .metDatumAanvangGeldigheid(20150101)
                            .metAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId(), "BAG")
                            .metAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId(),  6030)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId(), null)
                            .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId(), null)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build();
        //@formatter:on
    }
}
