/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import static junit.framework.TestCase.assertTrue;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * Deze klasse test of alle regels een logger instantie hebben. Dit omdat er vanuit regels altijd naar de log zal
 * moeten worden geschreven.
 */
public class AlleRegelsHebbenEenLoggerTest {

    @Test
    public void test() throws ClassNotFoundException {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(Bedrijfsregel.class));

        final Set<BeanDefinition> components = provider.findCandidateComponents("nl/bzk/brp/business/regels");
        for (final BeanDefinition component : components) {
            final Class cls = Class.forName(component.getBeanClassName());

            final Field[] fields = cls.getDeclaredFields();

            boolean heeftLogger = false;
            for (final Field field : fields) {
                if (field.getType() == Logger.class) {
                    heeftLogger = true;
                }
            }

            assertTrue("Geen logger gevonden voor " + cls.getName(), heeftLogger);
        }
    }

}
