/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import javax.inject.Named;
import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * JMX Interface voor afnemer service.
 */
@UseDynamicDomain
@ManagedResource(objectName = "nl.bzk.migratiebrp.register:name=AUTORISATIE", description = "JMX Service voor Autorisatie register client.")
public final class AutorisatieServiceJMX {

    @Autowired
    private AutorisatieService autorisatieService;

    @Autowired
    @Named("autorisatieRegisterListener")
    private DefaultMessageListenerContainer listener;

    /**
     * Initialiseer de service.
     */
    @ManagedOperation(description = "Cache opvragen")
    public void refreshRegister() {
        autorisatieService.refreshRegister();
    }

    /**
     * Cache leeg maken.
     */
    @ManagedOperation(description = "Cache legen")
    public void clearRegister() {
        autorisatieService.clearRegister();
    }

    /**
     * @return true, als de afnemer register berichten worden ontvangen; anders false.
     */
    @ManagedAttribute(description = "Ontvangen van afnemer register berichten")
    public boolean isGestart() {
        return listener.isRunning();
    }

    /**
     * Start ontvangen van afnemer register berichten.
     */
    @ManagedOperation(description = "Start ontvangen van afnemer register berichten")
    public void start() {
        listener.start();
    }

    /**
     * Stop ontvangen van afnemer register berichten.
     */
    @ManagedOperation(description = "Stop ontvangen van afnemer register berichten")
    public void stop() {
        listener.stop();
    }

}
