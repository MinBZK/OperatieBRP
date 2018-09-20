/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen;

import java.util.Map;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.uitvoering.GeneratorConfiguratieFactory;
import org.junit.Assert;


/** Basis klasse voor alle Generator tests. */
public abstract class AbstractGeneratorTest {

    /**
     * Retourneert de {@link GeneratorConfiguratie} uitgelezen uit het opgegeven configuratie bestand. Merk op dat er
     * van uitgegaan wordt dat het configuratie bestand slechts een enkele generator configuratie bevat.
     *
     * @param configuratieBestand de naam/pad van het configuratiebestand.
     * @return de uit het configuratie bestand gelezen configuratie.
     */
    protected GeneratorConfiguratie getGeneratorConfiguratie(final String configuratieBestand) {
        Map<String, GeneratorConfiguratie> configuratieMap =
            GeneratorConfiguratieFactory.leesGeneratorenConfiguratie(configuratieBestand);

        final GeneratorConfiguratie configuratie;
        if (configuratieMap.size() != 1) {
            Assert.fail("Onverwacht aantal generator configuraties gevonden: " + configuratieMap.size());
            configuratie = null;
        } else {
            configuratie = configuratieMap.values().iterator().next();
        }
        return configuratie;
    }
}
