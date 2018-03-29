/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import java.util.Date;
import javax.inject.Named;
import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * JMX Interface voor partij service.
 */
@UseDynamicDomain
@ManagedResource(objectName = "nl.bzk.migratiebrp.register:name=PARTIJ", description = "JMX Service voor partij register client.")
public class PartijServiceJMX {

    @Autowired
    private PartijService partijService;
    @Autowired
    @Named("partijRegisterListener")
    private DefaultMessageListenerContainer listener;

    /**
     * Initialiseer de service.
     */
    @ManagedOperation(description = "Cache opvragen")
    public void refreshRegister() {
        partijService.refreshRegister();
    }

    /**
     * Cache leeg maken.
     */
    @ManagedOperation(description = "Cache legen")
    public void clearRegister() {
        partijService.clearRegister();
    }

    /**
     * @return laatste ontvangen bericht partij register
     */
    @ManagedAttribute(description = "Laatste ontvangen bericht partij register")
    public String getLaatsteBericht() {
        return partijService.getLaatsteBericht();
    }

    /**
     * @return laatste ontvangst partij register
     */
    @ManagedAttribute(description = "Laatste ontvangst partij register")
    public Date getLaatsteOntvangst() {
        return partijService.getLaatsteOntvangst();
    }

    /**
     * @return register als string
     */
    @ManagedOperation(description = "Partij register als string")
    public String getRegisterAsString() {
        return partijService.getRegisterAsString();
    }

    /**
     * @return true, als de partij register berichten worden ontvangen; anders false.
     */
    @ManagedAttribute(description = "Ontvangen van partij register berichten")
    public boolean isGestart() {
        return listener.isRunning();
    }

    /**
     * Start ontvangen van partij register berichten.
     */
    @ManagedOperation(description = "Start ontvangen van partij register berichten")
    public void start() {
        listener.start();
    }

    /**
     * Stop ontvangen van partij register berichten.
     */
    @ManagedOperation(description = "Stop ontvangen van partij register berichten")
    public void stop() {
        listener.stop();
    }
}
