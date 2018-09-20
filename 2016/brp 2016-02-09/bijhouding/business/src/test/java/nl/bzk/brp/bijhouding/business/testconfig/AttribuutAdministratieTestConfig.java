/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.testconfig;

import nl.bzk.brp.hismodelattribuutaccess.HisModelAttribuutAccessAdministratie;
import nl.bzk.brp.hismodelattribuutaccess.HisModelAttribuutAccessAdministratieImpl;
import nl.bzk.brp.hismodelattribuutaccess.HisModelAttribuutAccessAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Spring context configuratie ten behoeve van tests die attributen raken. Dit is nodig daar middels AOP
 * de getters op attributen worden gecontroleerd en de Aspect die dit controleert wel een 'administratie' object
 * verwacht. Dit wordt dan ook automatisch middels Spring configuratie voor een Test gezet indien deze klasse wordt
 * gebruikt voor de Spring context configuratie.
 */
@Configuration
public class AttribuutAdministratieTestConfig {

    @Bean
    @Scope(proxyMode = ScopedProxyMode.INTERFACES)
    public HisModelAttribuutAccessAdministratie myBean() {
        return new HisModelAttribuutAccessAdministratieImpl();
    }

    @Bean
    public HisModelAttribuutAccessAspect getAttribuutAspect() {
        final HisModelAttribuutAccessAspect aspect = new HisModelAttribuutAccessAspect();
        aspect.setAdministratie(myBean());
        return aspect;
    }

}
