/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.pager;

import javax.faces.event.ActionListener;
import nl.bzk.migratiebrp.isc.console.mig4jsf.AbstractTagTest;
import org.junit.Assert;
import org.junit.Test;

public class PagerTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        addTagAttribute("pageSize", 10);
        addTagAttribute("page", 1);
        addTagAttribute("target", null);

        // Execute
        final ActionListener subject = initializeBasicSubject(PagerHandler.class);
        Assert.assertEquals(PagerActionListener.class, subject.getClass());
        subject.processAction(actionEvent);

        final PagerBean pagerBean = (PagerBean) getExpressionValues().get("target");
        Assert.assertNotNull(pagerBean);

        pagerBean.setNumberOfResults(22);

        Assert.assertEquals(0, pagerBean.getFirst());
        Assert.assertEquals(10, pagerBean.getLimit());
        Assert.assertEquals(1, pagerBean.getPage());
        Assert.assertEquals(10, pagerBean.getPageSize());
        Assert.assertEquals(3, pagerBean.getTotalPages());
    }
}
