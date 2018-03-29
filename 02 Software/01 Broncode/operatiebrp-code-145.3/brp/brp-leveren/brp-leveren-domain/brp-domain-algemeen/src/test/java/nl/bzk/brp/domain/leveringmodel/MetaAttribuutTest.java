/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.Iterables;
import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * MetaAttribuutTest
 */
public class MetaAttribuutTest {

    /**
     * Een attribuut is uniek binnen een groep, maar niet over groepen heen
     */
    @Test
    public void testAttribuutInDezelfdeGroep() {
        //@formatter:off
        MetaObject metaObject = MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                    .metGroep().metGroepElement(Element.PERSOON_GEBOORTE.getId())
                        .metRecord().metId(1)
                            .metAttribuut(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM.getId(),"")
                            .metAttribuut(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM.getId(),"")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject().build();
        //@formatter:on
        final MetaRecord actueleRecord = Iterables.getOnlyElement(metaObject.getGroepen()).getRecords().iterator().next();
        Assert.assertEquals(1, actueleRecord.getAttributen().size());
    }

    /**
     * Attributen over groepen heen zijn niet uniek
     */
    @Test
    public void testAttribuutInVerschillendeObjecten() {
        //@formatter:off
        MetaObject metaObject = MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON)
                    .metObject().metId(1)
                        .metObjectElement(Element.PERSOON_ADRES.getId())
                        .metGroep()
                            .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                            .metRecord().metId(1)
                                .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 1)
                            .eindeRecord()
                        .eindeGroep()
                     .eindeObject()
                    .metObject().metId(2)
                        .metObjectElement(Element.PERSOON_ADRES.getId())
                        .metGroep()
                            .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                                .metRecord().metId(2)
                                    .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 1)
                                .eindeRecord()
                            .eindeGroep()
                    .eindeObject()
                .build();
        //@formatter:on

        Collection<MetaAttribuut> uniekeAttributen = new ModelIndex(metaObject).geefAttributen(
                ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()));
        Assert.assertEquals(2, uniekeAttributen.size());
    }

}
