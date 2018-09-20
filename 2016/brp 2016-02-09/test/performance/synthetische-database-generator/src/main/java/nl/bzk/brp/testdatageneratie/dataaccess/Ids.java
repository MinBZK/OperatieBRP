/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.dataaccess;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.testdatageneratie.utils.RandomUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Ids.
 */
@SuppressWarnings("all")
public class Ids {

    private static Logger log = Logger.getLogger(Ids.class);
    private final Class<? extends Serializable> entiteit;
    private int batchBlockSize;
    protected long min, max, range;

    /**
     * Instantieert Ids.
     *
     * @param entiteit entiteit
     */
    public Ids(final Class<? extends Serializable> entiteit) {
        this.entiteit = entiteit;
    }

    /**
     * Initialiseer.
     *
     * @param batchBlockSize batch block size
     */
    public void init(final int batchBlockSize) {
        this.batchBlockSize = batchBlockSize;

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();
            final String entiteitNaam = entiteit.getName();
            Number nMin = (Number) kernSession.createQuery("select min(id) from " + entiteitNaam).uniqueResult();
            Number nMax = (Number) kernSession.createQuery("select max(id) from " + entiteitNaam).uniqueResult();
            min = null == nMin ? 0L : nMin.longValue();
            max = null == nMax ? 0L : nMax.longValue();
            if (min == max) {
                String msg = "entiteit " + entiteitNaam + " is leeg !!!, program einde.";
                log.error(msg);
                throw new RuntimeException(msg);
            }
            range = (long) (max - min);

            kernSession.getTransaction().rollback();
        } finally {
            if (kernSession != null) {
                try {
                    kernSession.close();
                } catch (RuntimeException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * Selecteert een id.
     *
     * @return long id
     */
    public long selecteerId() {
        long id;
        do {
            id = RandomUtil.nextLong(range) + min;
        } while (!isAanwezig(id));
        return id;
    }

    /**
     * Selecteer id behalve opgegeven ids.
     *
     * @param uitgezonderdeIds uitgezonderde ids
     * @return long id
     */
    public long selecteerIdBehalve(final Long... uitgezonderdeIds) {
        List<Long> uitgezonderdeIdLijst = Arrays.asList(uitgezonderdeIds);
        long resultaat = 0;
        do {
            resultaat = selecteerId();
        } while (uitgezonderdeIdLijst.contains(resultaat));
        return resultaat;
    }

    /**
     * Controleert of id aanwezig is.
     *
     * @param id id
     * @return true
     */
    public boolean isAanwezig(final long id) {
        return id >= min && id <= max;
    }

    public int getBatchBlockSize() {
        return batchBlockSize;
    }

    public long getMin() {
        return min;
    }

    public long getVolgende(long offset) {
        return offset+1;
    }

    public long getMax() {
        return max;
    }

    public long getRangeSize() {
        return range + 1;
    }

}
