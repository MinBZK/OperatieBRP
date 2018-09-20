/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.event.ActionListener;
import nl.bzk.migratiebrp.isc.console.mig4jsf.AbstractTagTest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class ProcessInstancesFilterTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        addTagAttribute("key", "sleutel");
        addTagAttribute("startDate", "2004-2-12");
        addTagAttribute("running", true);
        addTagAttribute("suspended", false);
        addTagAttribute("ended", false);
        addTagAttribute("target", null);

        // Execute
        final ActionListener subject = initializeBasicSubject(ProcessInstancesFilterHandler.class);
        Assert.assertEquals(ProcessInstancesFilterActionListener.class, subject.getClass());
        subject.processAction(actionEvent);

        final ProcessInstancesFilter filter = (ProcessInstancesFilter) getExpressionValues().get("target");
        Assert.assertNotNull(filter);

        final String key = getProperty(filter, "key", String.class);
        final Date startDate = getProperty(filter, "startDate", Date.class);
        final Boolean running = getProperty(filter, "running", Boolean.class);
        final Boolean suspended = getProperty(filter, "suspended", Boolean.class);
        final Boolean ended = getProperty(filter, "ended", Boolean.class);

        Assert.assertEquals("sleutel", key);
        Assert.assertEquals("12-02-2004", new SimpleDateFormat("dd-MM-yyyy").format(startDate));
        Assert.assertEquals(Boolean.TRUE, running);
        Assert.assertEquals(Boolean.FALSE, suspended);
        Assert.assertEquals(Boolean.FALSE, ended);

        // Check filter
        final Criteria criteria = Mockito.mock(Criteria.class);
        filter.applyFilter(criteria);

        final ArgumentCaptor<Criterion> criterionCaptor = ArgumentCaptor.forClass(Criterion.class);
        Mockito.verify(criteria, Mockito.times(3)).add(criterionCaptor.capture());

        final Criterion keyCriterion = criterionCaptor.getAllValues().get(0);
        Assert.assertEquals("key=sleutel", keyCriterion.toString());
        final Criterion startDateCriterion = criterionCaptor.getAllValues().get(1);
        Assert.assertEquals("start between Thu Feb 12 00:00:00 CET 2004 and Thu Feb 12 23:59:59 CET 2004", startDateCriterion.toString());
        final Criterion stateCriterion = criterionCaptor.getAllValues().get(2);
        Assert.assertEquals("end is null and isSuspended=false", stateCriterion.toString());
    }

    @Test
    public void testFilterSuspended() throws Exception {
        addTagAttribute("running", false);
        addTagAttribute("suspended", true);
        addTagAttribute("ended", false);
        addTagAttribute("target", null);

        // Execute
        final ActionListener subject = initializeBasicSubject(ProcessInstancesFilterHandler.class);
        Assert.assertEquals(ProcessInstancesFilterActionListener.class, subject.getClass());
        subject.processAction(actionEvent);

        final ProcessInstancesFilter filter = (ProcessInstancesFilter) getExpressionValues().get("target");
        Assert.assertNotNull(filter);

        final Boolean running = getProperty(filter, "running", Boolean.class);
        final Boolean suspended = getProperty(filter, "suspended", Boolean.class);
        final Boolean ended = getProperty(filter, "ended", Boolean.class);

        Assert.assertEquals(Boolean.FALSE, running);
        Assert.assertEquals(Boolean.TRUE, suspended);
        Assert.assertEquals(Boolean.FALSE, ended);

        // Check filter
        final Criteria criteria = Mockito.mock(Criteria.class);
        filter.applyFilter(criteria);

        final ArgumentCaptor<Criterion> criterionCaptor = ArgumentCaptor.forClass(Criterion.class);
        Mockito.verify(criteria, Mockito.times(1)).add(criterionCaptor.capture());

        final Criterion stateCriterion = criterionCaptor.getAllValues().get(0);
        Assert.assertEquals("isSuspended=true and end is null", stateCriterion.toString());
    }

    @SuppressWarnings("unchecked")
    private <T> T getProperty(final ProcessInstancesFilter filter, final String name, final Class<T> resultClazz) throws Exception {
        final Field field = filter.getClass().getDeclaredField(name);
        field.setAccessible(true);

        final Object result = field.get(filter);
        if (result != null) {
            Assert.assertEquals(resultClazz, result.getClass());
        }

        return (T) result;
    }
}
