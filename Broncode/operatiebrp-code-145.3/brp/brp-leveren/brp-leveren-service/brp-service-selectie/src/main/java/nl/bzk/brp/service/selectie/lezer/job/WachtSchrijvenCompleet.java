/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;

/**
 * WachtSchrijvenCompleet. Wacht tot alle schrijf taken klaar zijn.
 * Exclusief de laatste schrijf actie waarin de totalen en steekproef worden gemaakt.
 */
final class WachtSchrijvenCompleet implements Callable<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private CountDownLatch countDownLatch;

    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Override
    public Object call() throws Exception {
        BrpNu.set();
        final int slaapTijd = 10;
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        while (!status.moetStoppen()) {
            if (status.schrijvenKlaar()) {
                break;
            }
            TimeUnit.SECONDS.sleep(slaapTijd);
        }
        LOGGER.info("klaar met wachten op schrijf taken");
        countDownLatch.countDown();
        return null;
    }

    /**
     * @param selectieJobRunStatusService selectieJobRunStatusService
     */
    public void setSelectieJobRunStatusService(SelectieJobRunStatusService selectieJobRunStatusService) {
        this.selectieJobRunStatusService = selectieJobRunStatusService;
    }

    /**
     * @param countDownLatch countDownLatch
     */
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

}
