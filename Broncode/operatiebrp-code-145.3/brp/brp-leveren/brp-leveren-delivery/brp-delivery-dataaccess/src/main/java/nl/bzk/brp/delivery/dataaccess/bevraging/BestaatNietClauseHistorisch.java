/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.GroepElement;

/**
 * BestaatNietClauseHistorisch.
 */
final class BestaatNietClauseHistorisch {
    private final GroepElement groepElement;
    private final Set<ZoekCriterium> zoekCriteria = new HashSet<>();

    /**
     * @param groepElement groepElement
     */
    BestaatNietClauseHistorisch(GroepElement groepElement) {
        this.groepElement = groepElement;
    }

    /**
     * @return zoekCriteria
     */
    public Set<ZoekCriterium> getZoekCriteria() {
        return zoekCriteria;
    }

    /**
     * @return groepElement
     */
    public GroepElement getGroepElement() {
        return groepElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BestaatNietClauseHistorisch that = (BestaatNietClauseHistorisch) o;

        return groepElement == that.groepElement;
    }

    @Override
    public int hashCode() {
        return groepElement != null ? groepElement.hashCode() : 0;
    }
}
