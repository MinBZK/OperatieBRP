/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.utils.PerformanceLogger;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;

public abstract class AbstractBasicGenerator implements Callable<GeneratorAdminData> {
    private static Logger log = Logger.getLogger(AbstractBasicGenerator.class);
    private final GeneratorAdminData adminData;
    private final int batchBlockSize;
    private final int commitTreshols = 1 + RandomUtil.random.nextInt(5);
    private final int commitCounter = 0;

    @Override
    public abstract GeneratorAdminData call() throws Exception;

    protected AbstractBasicGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final int threadIndex) {
        adminData = new GeneratorAdminData(
                getClass().getSimpleName(), numberOfRecordsToProcess, batchBlockSize, threadIndex);
        adminData.setStartTime(System.currentTimeMillis());
        this.batchBlockSize = batchBlockSize;
        PerformanceLogger.register(this);
        SequenceUtil.init();
    }

    protected void updateCurrentCount(final Session kernSession, final int count) {
        adminData.setCurrentCount(count+1);
        final String mode;
        if (count % batchBlockSize == 0) {
            long duur_commit = System.currentTimeMillis();
            kernSession.getTransaction().commit();
            kernSession.clear();
            kernSession.getTransaction().begin();
            mode = "commit";
            duur_commit = System.currentTimeMillis() - duur_commit;
            if (duur_commit > 2000) {
                log.debug("["+adminData.getThreadIndex()+ "]: " + mode + " cost " + duur_commit + " ms.");
            }
        }
    }

    public String getStatus(final int secondsPeriod) {
        long perSec = (adminData.getCurrentCount() - adminData.getLastCount()) / secondsPeriod;
        String msg = new StringBuffer(adminData.getClassName())
            .append("[").append(adminData.getThreadIndex()).append("]")
            .append(" @ ").append(adminData.getCurrentCount()).append("/")
            .append(adminData.getMaxCount())
            .append(" speed ")
            .append(perSec)
            .append("/sec")
            .toString();
        adminData.setLastCount(adminData.getCurrentCount());
        if (adminData.isFinished()) {
            finish();
        }
        return msg;
    }

    protected void finish() {
        PerformanceLogger.unregister(this);
        if (adminData.getEndTime() == 0) {
            adminData.setEndTime(System.currentTimeMillis());
        }
    }

    protected final long getNumberOfRecordsToProcess() {
        return adminData.getMaxCount();
    }

    protected final long getBatchBlockSize() {
        return adminData.getBatchBlockSize();
    }

    protected final GeneratorAdminData getAdminData() {
        return adminData;
    }
}
