/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import nl.bzk.brp.dataaccess.repository.jpa.RegelRepositoryImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test klasse die controleert of er voor elke enum waarde in de Regel enum ook een entry in
 * de regel repository bestaat.
 */
public class RegelEnumAndRepositoryMatchTest {

    @Test
    public void testMatchRegelEnumAndRepository() {
        RegelRepositoryImpl regelRepository = new RegelRepositoryImpl();
        for (Regel regel : Regel.values()) {
            // Neem alleen 'officiele' bedrijfsregels mee.
            if (regel.getCode().startsWith("BR")) {
                if (regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut(regel.getCode())) == null) {
                    Assert.fail("Geen entry in de regel repository gevonden voor Regel." + regel.getCode());
                }
            }
        }
    }

}
