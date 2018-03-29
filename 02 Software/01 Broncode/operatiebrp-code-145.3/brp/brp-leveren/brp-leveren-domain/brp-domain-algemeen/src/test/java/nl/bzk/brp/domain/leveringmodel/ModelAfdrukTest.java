/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import java.io.IOException;
import java.io.InputStream;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ModelAfdrukTest {

    @Test
    public void maakAfdruk() throws IOException {
        //@formatter:off
        final MetaObject object = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            // TODO outlining issues in ModelAfdrukTest.maakAfdruk.txt
//            .metGroep()
//                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
//                .metRecord()
//                    .metId(10)
//                    .metAttribuut(getAttribuutElement(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId()),
//                        ZonedDateTime.of(2016, 10, 3, 16, 19, 45, 12, ZoneId.of("CET")))
//                .eindeRecord()
//            .eindeGroep()
            .metObject()
                .metId(2)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId()))
                .eindeGroep()
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(3)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), "abc")
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID.getId()), 20100203)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metId(1)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(2)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), "abc")
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID.getId()), 20100203)
                    .eindeRecord()
                    .metRecord()
                        .metId(1)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), "abc")
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID.getId()), 20100203)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        final String afdruk = removeLineEndings(ModelAfdruk.maakAfdruk(object));
        System.out.println(ModelAfdruk.maakAfdruk(object));
        final InputStream inputStream = ModelAfdrukTest.class.getResourceAsStream("/ModelAfdrukTest.maakAfdruk.txt");
        Assert.assertEquals(removeLineEndings(IOUtils.toString(inputStream)), afdruk);
    }

    private String removeLineEndings(String value) {
        return value.replace("\n", "").replace("\r", "");
    }
}
