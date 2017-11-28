/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.util;

/**
 * Diverse constanten nodig in de diverse levering projecten.
 */
public final class LeveringConstanten {

    /**
     * Header voor message group mechanisme van ActiveMq.
     * <p>
     * Message groups garanderen:
     * - guarranteed ordering of the processing of related messages across a single queue
     * - load balancing of the processing of messages across multiple consumers
     * - high availability / auto-failover to other consumers if a JVM goes down
     */
    public static final String JMS_MESSAGEGROUP_HEADER = "JMSXGroupID";

    /**
     * Prive constructor voor deze constante klasse, zodat hij niet geinitieerd kan worden.
     */
    private LeveringConstanten() {

    }

}
