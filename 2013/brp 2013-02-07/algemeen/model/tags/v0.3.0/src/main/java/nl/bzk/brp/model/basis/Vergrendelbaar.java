/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.Collection;

/**
 * Een Verzoek leidt tot applicatieve locking van BSN's. Afhankelijk van het type Verzoek wordt dit een Read lock of een Write Lock.
 */
public interface Vergrendelbaar extends ObjectType {

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief READ locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een read lock nodig heeft.
     */
    Collection<String> getReadBsnLocks();

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief WRITE locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een write lock nodig heeft.
     */
    Collection<String> getWriteBsnLocks();
}
