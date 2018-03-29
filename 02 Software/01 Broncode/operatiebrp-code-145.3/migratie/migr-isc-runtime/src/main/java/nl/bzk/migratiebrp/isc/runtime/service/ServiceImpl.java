/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import java.util.List;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.runtime.message.Message;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;

import org.springframework.beans.factory.InitializingBean;

/**
 * Service implementatie obv {@link Action}s.
 */
public final class ServiceImpl implements Service, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final String kanaal;
    private final List<Action> actions;
    private final FunctioneleMelding melding;

    /**
     * Constructor.
     * @param kanaal kanaal
     * @param actions actions
     * @param melding melding;
     */
    protected ServiceImpl(final String kanaal, final List<Action> actions, final FunctioneleMelding melding) {
        this.kanaal = kanaal;
        this.actions = actions;
        this.melding = melding;
    }

    @Override
    public void afterPropertiesSet() {
        for (final Action action : actions) {
            action.setKanaal(kanaal);
        }

    }

    @Override
    public void verwerk(final Message message) {
        for (final Action action : actions) {
            if (!action.verwerk(message)) {
                break;
            }
        }
        LOGGER.info(melding);
    }

}
