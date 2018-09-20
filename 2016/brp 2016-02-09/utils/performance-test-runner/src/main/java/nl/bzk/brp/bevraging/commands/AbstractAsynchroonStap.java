/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstracte class die een taskExecutor heeft om asynchroon taken uit te voeren.
 */
public abstract class AbstractAsynchroonStap implements Command {
    private static final int PERCENT = 10;
    protected ThreadPoolTaskExecutor taskExecutor;

    /**
     * Te implementeren door subclassen.
     *
     * @param context de context voor het uitvoeren van de stap
     * @return <code>true</code> als verwerken klaar is, <code>false</code> als een
     * volgend {@link Command} werk moet uitvoeren
     * @throws Exception als er iets fout gaat
     */
    public abstract boolean doExecute(Context context) throws Exception;

    @Override
    @Transactional
    public boolean execute(final Context context) throws Exception {
        this.configureTaskExecutor(context);

        return this.doExecute(context);
    }

    /**
     * Configureer de {@link #taskExecutor}.
     *
     * @param context context voor de settings van de taskExecutor
     */
    private void configureTaskExecutor(final Context context) {
        // configureer volgens de settings in context
        this.taskExecutor = new ThreadPoolTaskExecutor();

        Integer queueSize = (Integer) context.get(ContextParameterNames.AANTAL_PERSOONSLIJSTEN);
        this.taskExecutor.setQueueCapacity(queueSize + queueSize / PERCENT);
        this.taskExecutor.setCorePoolSize((Integer) context.get(ContextParameterNames.AANTAL_THREADS));
        this.taskExecutor.setMaxPoolSize((Integer) context.get(ContextParameterNames.AANTAL_THREADS));

        this.taskExecutor.afterPropertiesSet();
    }
}
