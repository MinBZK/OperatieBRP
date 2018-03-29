/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * Stub voor het afvangen van alle archiveringopdrachten.
 */
public interface ArchiveringStubService extends Stateful {
    /**
     * @return indicatie of er gearchiveerd is.
     */
    boolean erIsGearchiveerd();

    /**
     * @return alle archiveringopdrachten.
     */
    List<Bericht> getBerichten();
}
