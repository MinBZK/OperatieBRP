/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.util;

import nl.bzk.brp.testrunner.component.BrpOmgeving;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 */
public class PersoonDslHelper {

    private final BrpOmgeving omgeving;


    public PersoonDslHelper(BrpOmgeving omgeving) {
        this.omgeving = omgeving;
    }

    public void uitBestand(String dslBestand) {
        uitResource(new ClassPathResource(dslBestand));
    }

    public void uitString(String string) {
        uitResource(new ByteArrayResource(string.getBytes()));
    }

    public void uitResource(Resource resource) {
        omgeving.database().voerUitMetTransactie(omgeving.database().geefDslVerzoekFactory().maakDSLVerzoek(resource));
    }
}
