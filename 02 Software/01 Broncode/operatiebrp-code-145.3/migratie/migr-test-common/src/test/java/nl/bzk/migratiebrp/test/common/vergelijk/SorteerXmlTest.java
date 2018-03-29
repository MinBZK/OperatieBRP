/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import org.junit.Assert;
import org.junit.Test;


public class SorteerXmlTest {

    @Test
    public void testSimple() {
        final String xml1 = "<bla><x>1</x><x>2</x></bla>";
        final String xml2 = "<bla><x>2</x><x>1</x></bla>";

        Assert.assertEquals(SorteerXml.sorteer(xml1), SorteerXml.sorteer(xml2));
    }

    @Test
    public void testListAlleenLijstAnders(){
        final String xml1 = "<resultaat>"
                + "<persoon>"
                + "<attr1>1.1</attr1><attr2>1.2</attr2>"
                + "</persoon>"
                + "<persoon>"
                + "<attr1>2.1</attr1><attr2>2.2</attr2>"
                + "</persoon>"
                + "<persoon>"
                + "<attr1>3.1</attr1><attr2>3.2</attr2>"
                + "</persoon>"
                + "</resultaat>";

        final String xml2 = "<resultaat>"
                + "<persoon>"
                + "<attr1>2.1</attr1><attr2>2.2</attr2>"
                + "</persoon>"
                + "<persoon>"
                + "<attr1>3.1</attr1><attr2>3.2</attr2>"
                + "</persoon>"
                + "<persoon>"
                + "<attr1>1.1</attr1><attr2>1.2</attr2>"
                + "</persoon>"
                + "</resultaat>";

        Assert.assertEquals(SorteerXml.sorteer(xml1), SorteerXml.sorteer(xml2));
    }

    @Test
    public void testLijstAndersEnChildrenAnders()  {
        final String xml1 = "<resultaat>"
                + "<persoon>"
                + "<attr1>1.1</attr1><attr2>1.2</attr2>"
                + "</persoon>"
                + "<persoon>"
                + "<attr1>2.1</attr1><attr2>2.2</attr2>"
                + "</persoon>"
                + "<persoon>"
                + "<attr1>3.1</attr1><attr2>3.2</attr2>"
                + "</persoon>"
                + "</resultaat>";

        final String xml2 = "<resultaat>"
                + "<persoon>"
                + "<attr2>2.2</attr2><attr1>2.1</attr1>"
                + "</persoon>"
                + "<persoon>"
                + "<attr2>3.2</attr2><attr1>3.1</attr1>"
                + "</persoon>"
                + "<persoon>"
                + "<attr1>1.1</attr1><attr2>1.2</attr2>"
                + "</persoon>"
                + "</resultaat>";

        Assert.assertEquals(SorteerXml.sorteer(xml1), SorteerXml.sorteer(xml2));
    }
}
