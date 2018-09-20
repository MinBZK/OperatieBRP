/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.testconfig;

import nl.bzk.brp.configuratie.BrpBusinessConfiguratie;
import nl.bzk.brp.configuratie.BrpBusinessConfiguratieImpl;
import nl.bzk.brp.locking.BrpLocker;
import nl.bzk.brp.locking.BrpLockerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring context configuratie die een context opzet waarin een BRP locker beschikbaar is die werkelijk gebruik
 * maakt van een database.
 * <p>
 * Hoewel het lijkt alsof er altijd een nieuwe {@link BrpLockerImpl} wordt geinstantieerd en
 * altijd een nieuwe {@link BrpBusinessConfiguratieImpl}, is dit in werkelijkheid niet zo. Spring zorgt er namelijk voor
 * dat deze echt als Singletons worden gebruikt en dus maar eenmalig worden geinstantieerd, onafhankelijk
 * van het aantal aanroepen.
 * </p>
 */
@Configuration
@ImportResource({ "classpath:business-datasource-context-test.xml", "classpath:dataaccess-beans.xml" })
public class BrpLockerTestConfig {

    @Bean
    public BrpLocker getBrpLocker() {
        return new BrpLockerImpl();
    }

    @Bean
    public BrpBusinessConfiguratie getBrpBusinessConfiguratie() {
        return new BrpBusinessConfiguratieImpl();
    }

}
