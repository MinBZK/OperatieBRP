/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * Process instance helper.
 */
public final class ProcessInstanceUtil {

    private ProcessInstanceUtil() {
        // Niet instantieerbaar
    }

    /**
     * Geef de 'root' process instance voor een process instance.
     * 
     * @param processInstance
     *            process instance
     * @return 'root' process instance
     */
    public static ProcessInstance getRootProcessInstance(final ProcessInstance processInstance) {
        if (processInstance == null) {
            return null;
        }

        ProcessInstance result = processInstance;
        while (result.getSuperProcessToken() != null) {
            Token superToken = result.getSuperProcessToken();
            while (superToken.getParent() != null) {
                superToken = superToken.getParent();
            }
            result = superToken.getProcessInstance();
        }
        return result;
    }

}
