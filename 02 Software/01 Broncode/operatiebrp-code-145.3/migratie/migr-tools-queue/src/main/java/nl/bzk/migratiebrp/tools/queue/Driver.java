/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.queue;

/**
 * Jms functions.
 */
public interface Driver {

    /**
     * Set quiet.
     * @param quiet true or false
     */
    void setQuiet(boolean quiet);

    /**
     * Count messages.
     * @param queue queue
     */
    void count(String queue);

    /**
     * List message ids.
     * @param queue queue
     */
    void list(String queue);

    /**
     * Show message contents.
     * @param queue queue
     * @param jmsId message id
     */
    void show(String queue, String jmsId);
}
