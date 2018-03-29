/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.ObjectElement;

/**
 * BestaatNietClauseActueel.
 */
final class BestaatNietClauseActueel {
    private final ObjectElement objectElement;
    private final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

    /**
     * @param objectElement objectElement
     */
    BestaatNietClauseActueel(ObjectElement objectElement) {
        this.objectElement = objectElement;
    }

    /**
     * @return zoekCriteria
     */
    public Set<ZoekCriterium> getZoekCriteria() {
        return zoekCriteria;
    }

    /**
     * @return objectElement
     */
    public ObjectElement getObjectElement() {
        return objectElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BestaatNietClauseActueel that = (BestaatNietClauseActueel) o;

        return objectElement == that.objectElement;
    }

    @Override
    public int hashCode() {
        return objectElement != null ? objectElement.hashCode() : 0;
    }
}
