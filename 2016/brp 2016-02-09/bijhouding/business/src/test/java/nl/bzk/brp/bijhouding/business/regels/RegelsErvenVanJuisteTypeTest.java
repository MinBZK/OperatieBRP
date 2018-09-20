/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import java.util.Set;
import nl.bzk.brp.business.regels.NaActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegel;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;

/**
 * Bedrijfsregels voor/na een actie moeten erven van de momentopname of historisch besef interface.
 * Ze mogen niet direct van de top level interface erven. Dat wordt hier afgedwongen.
 *
 * getSubTypesOf geeft alleen direct implementerende klasses terug en geen implementerende klasses
 * van sub-interfaces, vandaar dat het op onderstaande manier gecheckt kan worden.
 */
public class RegelsErvenVanJuisteTypeTest {

    @Test
    @SuppressWarnings("rawtypes")
    public void testRegelsErvenVanJuisteType() {
        Reflections reflections = new Reflections("nl.bzk.brp.business.regels.impl");

        Set<Class<? extends VoorActieRegel>> voorActieRegelKlasses = reflections.getSubTypesOf(VoorActieRegel.class);
        Assert.assertEquals(0, voorActieRegelKlasses.size());

        Set<Class<? extends NaActieRegel>> naActieRegelKlasses = reflections.getSubTypesOf(NaActieRegel.class);
        Assert.assertEquals(0, naActieRegelKlasses.size());
    }

}
