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

public class Ids {

    private static Logger log = Logger.getLogger(Ids.class);
    private final Class<? extends Serializable> entiteit;
    private int batchBlockSize;
    private long min, max, range;

    public Ids(final Class<? extends Serializable> entiteit) {
        this.entiteit = entiteit;
    }

    public void init(final int batchBlockSize) {
        this.batchBlockSize = batchBlockSize;

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            min = ((Number) kernSession.createQuery("select min(id) from " + entiteit.getName()).uniqueResult()).longValue();
            max = ((Number) kernSession.createQuery("select max(id) from " + entiteit.getName()).uniqueResult()).longValue();
            range = (long) (max - min);

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null) try {
                kernSession.close();
            } catch (RuntimeException e) {
                log.error("", e);
            }
        }
    }

    public long selecteerId() {
        long id;
        do {
            id = RandomUtil.nextLong(range) + min;
        } while (!isAanwezig((int) id));
        return id;
    }

    public long selecteerIdBehalve(final Long... uitgezonderdeIds) {
        List<Long> uitgezonderdeIdLijst = Arrays.asList(uitgezonderdeIds);
        long resultaat = 0;
        do {
            resultaat = selecteerId();
        } while (uitgezonderdeIdLijst.contains(resultaat));
        return resultaat;
    }

    public boolean isAanwezig(final int id) {
        return id >= min && id <= max;
    }


    public int getBatchBlockSize() {
        return batchBlockSize;
    }


    public long getMin() {
        return min;
    }


    public long getMax() {
        return max;
    }


    public long getRangeSize() {
        return range + 1;
    }

}
