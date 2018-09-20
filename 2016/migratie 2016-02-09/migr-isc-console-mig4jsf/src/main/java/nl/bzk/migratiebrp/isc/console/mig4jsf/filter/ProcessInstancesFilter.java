/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import nl.bzk.migratiebrp.util.common.Kopieer;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

/**
 * Filter om process instances te filteren.
 */
public final class ProcessInstancesFilter implements Filter, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int EINDE_VAN_DE_DAG_HOUR = 23;
    private static final int EINDE_VAN_DE_DAG_MINUTE = 59;
    private static final int EINDE_VAN_DE_DAG_SECOND = 59;
    private static final int EINDE_VAN_DE_DAG_MILLISECOND = 999;

    private static final String COLUMN_KEY = "key";
    private static final String COLUMN_START = "start";
    private static final String COLUMN_END = "end";
    private static final String COLUMN_SUSPENDED = "isSuspended";

    private static final String SQL_RESTRICTION_GERELATEERDE_INFORMATIE = "{alias}.id_ in (select process_instance_id from mig_proces_gerelateerd "
                                                                          + "where soort_gegeven = ? and gegeven = ?)";

    private final String key;
    private final Date startDate;
    private final Boolean running;
    private final Boolean suspended;
    private final Boolean ended;
    private final String anummer;
    private final String partij;
    private final String ahId;
    private final Boolean root;

    /**
     * Constructor.
     *
     * @param key
     *            key
     * @param startDate
     *            start datum
     * @param running
     *            indicatie running
     * @param suspended
     *            indicatie suspended
     * @param ended
     *            indicatie ended
     * @param anummer
     *            a-nummer
     * @param partij
     *            partij
     * @param ahId
     *            administratieve handeling id
     * @param root
     *            indicatie alleen root processen
     */
    public ProcessInstancesFilter(
        final String key,
        final Date startDate,
        final Boolean running,
        final Boolean suspended,
        final Boolean ended,
        final String anummer,
        final String partij,
        final String ahId,
        final Boolean root)
    {
        this.key = key;
        this.startDate = Kopieer.utilDate(startDate);
        this.running = running;
        this.suspended = suspended;
        this.ended = ended;
        this.anummer = anummer;
        this.partij = partij;
        this.ahId = ahId;
        this.root = root;
    }

    @Override
    public void applyFilter(final Criteria criteria) {
        if (key != null && !"".equals(key)) {
            criteria.add(Restrictions.eq(COLUMN_KEY, key));
        }

        applyStartDateCriteria(criteria);
        applyStateCritria(criteria);

        if (anummer != null && !"".equals(anummer)) {
            // Add anummer criterion
            criteria.add(Restrictions.sqlRestriction(
                SQL_RESTRICTION_GERELATEERDE_INFORMATIE,
                new Object[] {"ANR", anummer, },
                new Type[] {StringType.INSTANCE, StringType.INSTANCE, }));
        }

        if (partij != null && !"".equals(partij)) {
            // Add partij criterion
            criteria.add(Restrictions.sqlRestriction(
                SQL_RESTRICTION_GERELATEERDE_INFORMATIE,
                new Object[] {"PTY", partij, },
                new Type[] {StringType.INSTANCE, StringType.INSTANCE, }));
        }

        if (ahId != null && !"".equals(ahId)) {
            // Add administratieve handeling criterion
            criteria.add(Restrictions.sqlRestriction(
                SQL_RESTRICTION_GERELATEERDE_INFORMATIE,
                new Object[] {"ADH", ahId, },
                new Type[] {StringType.INSTANCE, StringType.INSTANCE, }));
        }

        if (Boolean.TRUE.equals(root)) {
            criteria.add(Restrictions.isNull("superProcessToken"));
        }
    }

    private void applyStartDateCriteria(final Criteria criteria) {
        if (startDate != null) {

            final Calendar fromDate = Calendar.getInstance();
            fromDate.setTime(startDate);
            fromDate.set(Calendar.HOUR_OF_DAY, 0);
            fromDate.set(Calendar.MINUTE, 0);
            fromDate.set(Calendar.SECOND, 0);
            fromDate.set(Calendar.MILLISECOND, 0);

            final Calendar toDate = Calendar.getInstance();
            toDate.setTime(startDate);
            toDate.set(Calendar.HOUR_OF_DAY, EINDE_VAN_DE_DAG_HOUR);
            toDate.set(Calendar.MINUTE, EINDE_VAN_DE_DAG_MINUTE);
            toDate.set(Calendar.SECOND, EINDE_VAN_DE_DAG_SECOND);
            toDate.set(Calendar.MILLISECOND, EINDE_VAN_DE_DAG_MILLISECOND);

            criteria.add(Restrictions.between(COLUMN_START, fromDate.getTime(), toDate.getTime()));
        }
    }

    private void applyStateCritria(final Criteria criteria) {
        Criterion stateCriterion = null;

        if (Boolean.TRUE.equals(ended)) {
            stateCriterion = addCriterion(stateCriterion, Restrictions.isNotNull(COLUMN_END));
        }
        if (Boolean.TRUE.equals(suspended)) {
            stateCriterion = addCriterion(stateCriterion, Restrictions.eq(COLUMN_SUSPENDED, true));
            if (Boolean.FALSE.equals(ended)) {
                stateCriterion = Restrictions.and(stateCriterion, Restrictions.isNull(COLUMN_END));
            }
        }
        if (Boolean.TRUE.equals(running)) {
            stateCriterion = addCriterion(stateCriterion, Restrictions.isNull(COLUMN_END));
            if (Boolean.FALSE.equals(suspended)) {
                stateCriterion = Restrictions.and(stateCriterion, Restrictions.eq(COLUMN_SUSPENDED, false));
            }
        }

        if (stateCriterion != null) {
            criteria.add(stateCriterion);
        }
    }

    private Criterion addCriterion(final Criterion base, final Criterion toAdd) {
        return base == null ? toAdd : Restrictions.or(base, toAdd);
    }

    @Override
    public String getWhereClause(final StartType startType) {
        throw new UnsupportedOperationException("ProcessInstancesFilter.getWhereClause niet geimplementeerd voor sql where clause.");
    }

    @Override
    public void setWhereClause(final PreparedStatement statement, final int startingIndex) {
        throw new UnsupportedOperationException("ProcessInstancesFilter.setWhereClause niet geimplementeerd voor sql where clause.");
    }

    @Override
    public String toString() {
        return "ProcessInstancesFilter [key="
               + key
               + ", startDate="
               + startDate
               + ", running="
               + running
               + ", suspended="
               + suspended
               + ", ended="
               + ended
               + ", anummer="
               + anummer
               + ", partij="
               + partij
               + ", ahId="
               + ahId
               + "]";
    }

}
