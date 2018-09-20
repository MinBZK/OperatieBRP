/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * JAX-RS core component.
 */
public final class ReportApplication extends Application {
    private final Set<Object> singletons = new HashSet<Object>();
    private final Set<Class<?>> classes = new HashSet<Class<?>>();

    /**
     * Default constructor.
     */
    public ReportApplication() {
        singletons.add(new ReportFacade());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
