/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Vergelijker voor taken.
 */
public final class TaskComparator implements Comparator<TaskInstance>, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int RESULT_NEGATIVE = -1;
    private static final int RESULT_POSITIVE = 1;
    private String currentActorId;
    private boolean oudsteEerst;

    @Override
    public int compare(final TaskInstance o1, final TaskInstance o2) {

        // o1 bevat huidig actor, o2 niet
        // o2 bevat huidig actor, o1 niet
        // o1 en o2 bevatten huidige actor of o1 en o2 bevatten huidige actor niet.

        final int result;

        if (currentActorId != null && currentActorId.equals(o1.getActorId()) && !currentActorId.equals(o2.getActorId())) {
            result = RESULT_NEGATIVE;

        } else if (currentActorId != null && !currentActorId.equals(o1.getActorId()) && currentActorId.equals(o2.getActorId())) {
            result = RESULT_POSITIVE;
        } else {

            final int resultaatEersteVergelijking = nullSafeStringComparator(o1.getActorId(), o2.getActorId());
            if (resultaatEersteVergelijking == 0) {
                result = (oudsteEerst ? 1 : -1) * nullSafeDateComparator(o1.getCreate(), o2.getCreate());
            } else {
                result = resultaatEersteVergelijking;
            }
        }

        return result;
    }

    /**
     * Vergelijk methode voor Strings veilig met elkaar te vergelijken.
     * @param one String één.
     * @param two String twee.
     * @return Zie standaard comparator-gedrag: -1 als één voor twee, 0 als gelijk en 1 als twee voor één.
     */
    public int nullSafeStringComparator(final String one, final String two) {

        final int result;

        if (one == null && two == null) {
            result = 0;
        } else if (one == null ^ two == null) {
            result = one == null ? -1 : 1;
        } else {
            result = one.compareToIgnoreCase(two);
        }
        return result;
    }

    /**
     * Vergelijk methode voor datums veilig met elkaar te vergelijken.
     * @param one Datum één.
     * @param two Datum twee.
     * @return Zie standaard comparator-gedrag: -1 als één voor twee, 0 als gelijk en 1 als twee voor één.
     */
    public int nullSafeDateComparator(final Date one, final Date two) {

        final int result;

        if (one == null && two == null) {
            result = 0;
        } else if (one == null ^ two == null) {
            result = one == null ? -1 : 1;
        } else {
            result = one.compareTo(two);
        }

        return result;
    }

    /**
     * Zet de waarde van current actor id.
     * @param currentActorId current actor id
     */
    public void setCurrentActorId(final String currentActorId) {
        this.currentActorId = currentActorId;
    }

    /**
     * Zet de waarde van oudste eerst.
     * @param oudsteEerst oudste eerst
     */
    public void setOudsteEerst(final boolean oudsteEerst) {
        this.oudsteEerst = oudsteEerst;
    }

}
