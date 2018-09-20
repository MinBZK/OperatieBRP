/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import javax.faces.event.ActionListener;
import nl.bzk.migratiebrp.isc.console.mig4jsf.AbstractTagTest;
import org.junit.Assert;
import org.junit.Test;

public class BerichtenFilterTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        addTagAttribute("target", null);

        // Execute
        final ActionListener subject = initializeBasicSubject(BerichtenFilterHandler.class);
        Assert.assertEquals(BerichtenFilterActionListener.class, subject.getClass());
        subject.processAction(actionEvent);

        final BerichtenFilter filter = (BerichtenFilter) getExpressionValues().get("target");
        Assert.assertNotNull(filter);
    }
}
