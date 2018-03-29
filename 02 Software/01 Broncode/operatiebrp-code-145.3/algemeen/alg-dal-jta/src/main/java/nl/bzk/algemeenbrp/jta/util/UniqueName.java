/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.jta.util;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.FactoryBean;

/**
 * Generates a unique name.
 */
public final class UniqueName implements FactoryBean<String> {

    private static final AtomicLong COUNTER = new AtomicLong(1L);
    private String baseName = "default";

    /**
     * Zet de basis naam.
     *
     * @param baseName de te zetten basis naam
     */
    public void setBaseName(final String baseName) {
        this.baseName = baseName;
    }

    @Override
    public String getObject() {
        return baseName + COUNTER.getAndIncrement();
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
