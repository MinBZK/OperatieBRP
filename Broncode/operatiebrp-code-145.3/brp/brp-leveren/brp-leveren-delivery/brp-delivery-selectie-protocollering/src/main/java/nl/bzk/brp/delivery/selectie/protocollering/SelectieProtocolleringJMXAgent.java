/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.protocollering;

import javax.inject.Inject;
import nl.bzk.brp.service.selectie.protocollering.SelectieProtocolleringService;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Jmx support.
 */
@Component
@ManagedResource(objectName = "selectie:name=SelectieProtocolleringJob",
        description = "Het starten en stoppen van de SelectieProtocolleringJob")
public final class SelectieProtocolleringJMXAgent {

    @Inject
    private SelectieProtocolleringService selectieProtocolleringService;

    private SelectieProtocolleringJMXAgent() {
    }

    /**
     * Start SelectieProtocolleringJob.
     */
    @ManagedOperation(description = "Start de Protocolleer Selectie routine")
    public void startProtocolleren() {
        selectieProtocolleringService.start();
    }

    /**
     * Stop SelectieProtocolleringJob.
     */
    @ManagedOperation(description = "Stopt de Protocolleer Selectie routine")
    public void stopProtocolleren() {
        selectieProtocolleringService.stop();
    }

    /**
     *  check running SelectieProtocolleringJob.
     * @return true indien protocollering is gestopt.
     */
    @ManagedOperation(description = "Wacht tot de Protocolleer Selectie routine gestopt is")
    public Boolean wachtTotProtocollerenGestopt() {
        return selectieProtocolleringService.isRunning();
    }

}
