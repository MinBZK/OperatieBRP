/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import org.apache.log4j.Logger;
import org.hibernate.Session;


/**
 * De Class ActieIds.
 */
public class ActieIds {

    /** De log. */
    private static Logger log = Logger.getLogger(ActieIds.class);

    /** De range. */
    private final int     min, max, range;

    /**
     * Instantieert een nieuwe actie ids.
     */
    public ActieIds() {
        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            min =
                    Integer.valueOf("" + kernSession.createQuery("select min(id) from nl.bzk.brp.testdatageneratie.domain.kern.Actie")
                        .uniqueResult());
            max =
                    Integer.valueOf("" + kernSession.createQuery("select max(id) from nl.bzk.brp.testdatageneratie.domain.kern.Actie")
                        .uniqueResult());
            range = (max - min);

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null)
                try {
                    kernSession.close();
                } catch (RuntimeException e) {
                    log.error("", e);
                }
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getRangeSize() {
        return range + 1;
    }

}
