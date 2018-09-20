/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Entity listener voor alle stamgegevens om te voorkomen dat via de BRP stamgegevens kunnen worden aangemaakt,
 * aangepast of verwijderd.
 */
public final class StamgegevenEntityListener {

    /**
     * Entity lifecycle functie die wordt aangeroepen wanneer een stamgegeven wordt aangemaakt, aangepast of verwijderd.
     *
     * @param stamgegeven stamgegeven dat
     */
    @PrePersist
    @PreRemove
    @PreUpdate
    public void voorverwerking(final AbstractStatischObjectType stamgegeven) {
        throw new UnsupportedOperationException("Stamgegevens mogen niet worden aangemaakt, aangepast of verwijderd!");
    }
}
