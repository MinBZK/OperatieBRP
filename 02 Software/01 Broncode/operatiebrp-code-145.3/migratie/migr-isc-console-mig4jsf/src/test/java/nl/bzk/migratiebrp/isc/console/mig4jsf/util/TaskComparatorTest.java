/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Before;
import org.junit.Test;

public class TaskComparatorTest {

    private static final String ACTOR_BEHEERDER = "beheerder";
    private static final String ACTOR_MANAGER = "manager";
    private static final TaskComparator TASK_COMPARATOR = new TaskComparator();

    @Before
    public void setup() {

        TASK_COMPARATOR.setCurrentActorId(null);

    }

    @Test
    public void testVergelijkingMetGezetteActor() {

        final TaskInstance taskInstance1 = new TaskInstance();
        final TaskInstance taskInstance2 = new TaskInstance();

        taskInstance1.setActorId(ACTOR_BEHEERDER);
        taskInstance1.setStart(new Date());

        int result = TASK_COMPARATOR.compare(taskInstance1, taskInstance2);
        assertEquals("Vergelijking niet juist.", 1, result);

        result = TASK_COMPARATOR.compare(taskInstance2, taskInstance1);
        assertEquals("Vergelijking niet juist.", -1, result);

        result = TASK_COMPARATOR.compare(taskInstance1, taskInstance1);
        assertEquals("Vergelijking niet juist.", 0, result);

        TASK_COMPARATOR.setCurrentActorId(ACTOR_BEHEERDER);

        result = TASK_COMPARATOR.compare(taskInstance1, taskInstance2);
        assertEquals("Vergelijking niet juist.", -1, result);

        result = TASK_COMPARATOR.compare(taskInstance2, taskInstance1);
        assertEquals("Vergelijking niet juist.", 1, result);

        result = TASK_COMPARATOR.compare(taskInstance1, taskInstance1);
        assertEquals("Vergelijking niet juist.", 0, result);

        TASK_COMPARATOR.setCurrentActorId(ACTOR_MANAGER);

        result = TASK_COMPARATOR.compare(taskInstance1, taskInstance2);
        assertEquals("Vergelijking niet juist.", 1, result);

        result = TASK_COMPARATOR.compare(taskInstance2, taskInstance1);
        assertEquals("Vergelijking niet juist.", -1, result);

        result = TASK_COMPARATOR.compare(taskInstance2, taskInstance2);
        assertEquals("Vergelijking niet juist.", 0, result);
    }

    @Test
    public void testNullSafeString() {

        final String nullString = null;
        final String emptyString = "";

        int result = TASK_COMPARATOR.nullSafeStringComparator(nullString, nullString);
        assertEquals("Vergelijking mislukt op null-waarden.", 0, result);

        result = TASK_COMPARATOR.nullSafeStringComparator(nullString, emptyString);
        assertEquals("Vergelijking mislukt op null- en lege-waarden.", -1, result);

        result = TASK_COMPARATOR.nullSafeStringComparator(emptyString, nullString);
        assertEquals("Vergelijking mislukt op null- en lege-waarden.", 1, result);

        result = TASK_COMPARATOR.nullSafeStringComparator(emptyString, emptyString);
        assertEquals("Vergelijking mislukt op lege-waarden.", 0, result);

        result = TASK_COMPARATOR.nullSafeStringComparator(nullString, ACTOR_BEHEERDER);
        assertEquals("Vergelijking mislukt op null- en gevulde-waarden.", -1, result);

        result = TASK_COMPARATOR.nullSafeStringComparator(ACTOR_BEHEERDER, nullString);
        assertEquals("Vergelijking mislukt op null- en gevulde-waarden.", 1, result);

        result = TASK_COMPARATOR.nullSafeStringComparator(ACTOR_BEHEERDER, ACTOR_MANAGER);
        assertEquals("Vergelijking mislukt op null- en gevulde-waarden.", -11, result);

        result = TASK_COMPARATOR.nullSafeStringComparator(ACTOR_MANAGER, ACTOR_BEHEERDER);
        assertEquals("Vergelijking mislukt op null- en gevulde-waarden.", 11, result);
    }

    @Test
    public void testNullSafeDate() throws InterruptedException {
        final Date nullDate = null;
        final Date olderCurrentDate = new Date();
        TimeUnit.MILLISECONDS.sleep(100l);
        final Date newerCurrentDate = new Date();

        int result = TASK_COMPARATOR.nullSafeDateComparator(nullDate, nullDate);
        assertEquals("Vergelijking mislukt op null-waarden.", 0, result);

        result = TASK_COMPARATOR.nullSafeDateComparator(nullDate, olderCurrentDate);
        assertEquals("Vergelijking mislukt op null- en lege-waarden.", -1, result);

        result = TASK_COMPARATOR.nullSafeDateComparator(olderCurrentDate, nullDate);
        assertEquals("Vergelijking mislukt op null- en lege-waarden.", 1, result);

        result = TASK_COMPARATOR.nullSafeDateComparator(olderCurrentDate, olderCurrentDate);
        assertEquals("Vergelijking mislukt op lege-waarden.", 0, result);

        result = TASK_COMPARATOR.nullSafeDateComparator(olderCurrentDate, newerCurrentDate);
        assertEquals("Vergelijking mislukt op lege-waarden.", -1, result);

        result = TASK_COMPARATOR.nullSafeDateComparator(newerCurrentDate, olderCurrentDate);
        assertEquals("Vergelijking mislukt op lege-waarden.", 1, result);

    }

}
