/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.job;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import org.hibernate.ObjectNotFoundException;
import org.jbpm.job.Job;
import org.jbpm.job.Timer;
import org.jbpm.persistence.JbpmPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementatie.
 */
@Component
public final class ExecuteServiceImpl implements ExecuteService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Autowired
    private JbpmInvoker invoker;

    @Override
    @Transactional(value = "iscTransactionManager", propagation = Propagation.REQUIRED)
    
    public void executeTimer(final long timerId) {
        invoker.executeInContext(jbpmContext -> {
            final Timer timer = jbpmContext.getJobSession().loadTimer(timerId);
            if (timer != null) {
                try {
                    jbpmContext.addAutoSaveToken(timer.getToken());
                    timer.execute(jbpmContext);
                } catch (final
                JbpmPersistenceException
                        | ObjectNotFoundException e) {
                    // Timer not found, ignoring
                    LOG.info("Timer {} kan niet geladen worden. Timer wordt genegeerd...", timerId, e);
                } catch (final Exception e /* Catch exception wegens robusheid van Job */) {
                    LOG.debug("Unexpected exception caught during timer.execute", e);
                    throw new JobException(e);
                }
            }
            return null;
        });
    }

    @Override
    @Transactional(value = "iscTransactionManager", propagation = Propagation.REQUIRED)
    
    public void executeJob(final long jobId) {
        LOG.info("Executing job {} in JBPM context", jobId);
        invoker.executeInContext(jbpmContext -> {
            LOG.info("<In JBPM> Loading job {}", jobId);
            final Job job = jbpmContext.getJobSession().loadJob(jobId);
            LOG.info("<In JBPM> Job {} loaded", jobId);

            try {
                LOG.info("<In JBPM> Executing job {}", jobId);
                jbpmContext.addAutoSaveToken(job.getToken());
                job.execute(jbpmContext);
                LOG.info("<In JBPM> Job {} executed", jobId);
            } catch (final Exception e /* Catch exception wegens robuustheid van Job */) {
                LOG.debug("Unexpected exception caught during job.execute", e);
                throw new JobException(e);
            }

            return null;
        });
        LOG.info("Job {} executed in JBPM context", jobId);
    }

    /**
     * Job execution exception.
     */
    public static final class JobException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        /**
         * Inner klasse voor excepties die kunnen optreden bij het uitvoeren van een Job.
         * @param e De oorspronkelijke exceptie.
         */
        public JobException(final Exception e) {
            super(e);
        }
    }
}
