/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * StamtabelGegevensTest.
 */
public class StamtabelGegevensTest {

    @Test
    public void testStamtabelGegevens() {
        final ObjectElement objectElement = ElementHelper.getObjectElement(Element.PERSOON);
        final List<AttribuutElement> attrsInBericht = Lists.newArrayList(ElementHelper.getAttribuutElement(Element
                .PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER));
        final List<AttribuutElement> attrs = Lists.newArrayList(ElementHelper.getAttribuutElement(Element
                .PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER));

        final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(objectElement, attrsInBericht, attrs);
        final List<Map<String, Object>> stamgegevens = new ArrayList<>();
        final StamtabelGegevens stamtabelGegevens = new StamtabelGegevens(stamgegevenTabel, stamgegevens);

        Assert.assertEquals(stamgegevenTabel, stamtabelGegevens.getStamgegevenTabel());
        Assert.assertEquals(stamgegevens, stamtabelGegevens.getStamgegevens());

    }
}
