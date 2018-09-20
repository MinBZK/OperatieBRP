/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

public class PersoonIds {

    private static Logger log = Logger.getLogger(PersoonIds.class);
    private final int batchBlockSize;
    private final int[] ontbrekendePersoonIdBlokken;
    private final int ontbrekendePersoonIdMin, ontbrekendePersoonIdMax;
    private final int min, max, range;

    public PersoonIds(final int batchBlockSize, final int... ontbrekendePersoonIdBlokken) {
        this.batchBlockSize = batchBlockSize;
        this.ontbrekendePersoonIdBlokken = ontbrekendePersoonIdBlokken;
        ontbrekendePersoonIdMin = heeftGeenOntbrekendeBlokken()? Integer.MAX_VALUE: ontbrekendePersoonIdBlokken[0];
        ontbrekendePersoonIdMax = heeftGeenOntbrekendeBlokken()? 0: batchBlockSize + ontbrekendePersoonIdBlokken[ontbrekendePersoonIdBlokken.length-1];

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            min = (int)(Integer) kernSession.createQuery("select min(id) from nl.bzk.brp.testdatageneratie.domain.kern.Pers").uniqueResult();
            max = (int) (Integer) kernSession.createQuery("select max(id) from nl.bzk.brp.testdatageneratie.domain.kern.Pers").uniqueResult();
            range = (int) (max - min);

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null) try {
                kernSession.close();
            } catch (RuntimeException e) {
                log.error("", e);
            }
        }
    }

    public boolean heeftGeenOntbrekendeBlokken() {
        return ontbrekendePersoonIdBlokken == null || ontbrekendePersoonIdBlokken.length == 0;
    }

    public long selecteerPersoon() {
        int persoonId;
        do {
            persoonId = RandomService.random.nextInt(range) + min;
        } while (!isAanwezig((int) persoonId));
        return persoonId;
    }

    public long selecteerPersoonBehalve(final Long... uitgezonderdePersoonIds) {
        List<Long> uitgezonderdePersoonIdLijst = Arrays.asList(uitgezonderdePersoonIds);
        long resultaat = 0;
        do {
            resultaat = selecteerPersoon();
        } while (uitgezonderdePersoonIdLijst.contains(resultaat));
        return resultaat;
    }

    public boolean isAanwezig(final int persoonId) {
        if (persoonId > ontbrekendePersoonIdMax || persoonId < ontbrekendePersoonIdMin) {
            return true;
        }
        else {
            for (int i = 0; i < ontbrekendePersoonIdBlokken.length; i++) {
                if (persoonId >= ontbrekendePersoonIdBlokken[i] && persoonId <= (ontbrekendePersoonIdBlokken[i] + batchBlockSize)) {
                    return false;
                }
            }
            return true;
        }
    }


    public int getBatchBlockSize() {
        return batchBlockSize;
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
