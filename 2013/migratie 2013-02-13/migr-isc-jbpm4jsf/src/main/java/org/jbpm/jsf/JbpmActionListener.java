/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf;

import javax.faces.event.ActionEvent;

/**
 *
 */
public interface JbpmActionListener {
    /**
     * Get the name of this action.  This name may be used
     * by the navigation handler to choose an appropriate navigation
     * outcome.
     *
     * @return the name
     */
    String getName();

    void handleAction(JbpmJsfContext context, ActionEvent event);
}
