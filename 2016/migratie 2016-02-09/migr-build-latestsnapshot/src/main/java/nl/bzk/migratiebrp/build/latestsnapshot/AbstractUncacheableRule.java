/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.build.latestsnapshot;

import org.apache.maven.enforcer.rule.api.EnforcerRule;

/**
 * Niet cache-bare rule.
 */
public abstract class AbstractUncacheableRule implements EnforcerRule {

    /**
     * @return 'doesNotCache'
     */
    @Override
    public final String getCacheId() {
        return "doesNotCache";
    }

    /**
     * @return false
     */
    @Override
    public final boolean isCacheable() {
        return false;
    }

    /**
     * @param rule
     *            rule
     * @return false
     */
    @Override
    public final boolean isResultValid(final EnforcerRule rule) {
        return false;
    }
}
