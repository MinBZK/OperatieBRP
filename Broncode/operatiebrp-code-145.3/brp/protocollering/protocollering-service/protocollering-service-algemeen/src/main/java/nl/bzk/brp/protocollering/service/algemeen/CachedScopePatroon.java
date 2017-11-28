/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;


import com.google.common.collect.Sets;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroonElement;

/**
 * ScopePatroon wrapper voor hash en equals bepaling.
 */
final class CachedScopePatroon {

    private final ScopePatroon scopePatroon;
    private final Set<Integer> elementAttribuutSet;

    /**
     * Constructor.
     * @param scopePatroon een patroon
     */
    CachedScopePatroon(final ScopePatroon scopePatroon) {
        this.scopePatroon = scopePatroon;

        elementAttribuutSet = Sets.newHashSetWithExpectedSize(scopePatroon.getScopePatroonElementSet().size());
        for (final ScopePatroonElement scopePatroonElement : scopePatroon.getScopePatroonElementSet()) {
            elementAttribuutSet.add(scopePatroonElement.getElementId());
        }
    }

    /**
     * @return scopePatroon
     */
    ScopePatroon getScopePatroon() {
        return scopePatroon;
    }

    /**
     * @return elementAttribuutSet
     */
    public Set<Integer> getElementAttribuutSet() {
        return elementAttribuutSet;
    }

    @Override
    public int hashCode() {
        return elementAttribuutSet.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CachedScopePatroon that = (CachedScopePatroon) o;

        return elementAttribuutSet.equals(that.elementAttribuutSet);

    }
}
