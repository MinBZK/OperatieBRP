/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.dsl;

import nl.bzk.brp.testrunner.component.services.datatoegang.DatabaseVerzoek;
import org.springframework.core.io.Resource;

/**
 * Service om een DSL verzoek aan te maken
 */
public interface DslVerzoekFactory {

    /**
     * Maak een verzoek op basis van een DSL string waarde
     *
     * @param dsl een DSL string
     * @return een Verzoek welke op de database uitgevoerd kan worden
     */
    DatabaseVerzoek maakDSLVerzoek(String dsl);

    /**
     * Maak een verzoek op basis van een DSL Resource waarde
     *
     * @param dsl een Resource
     * @return een Verzoek welke op de database uitgevoerd kan worden
     */
    DatabaseVerzoek maakDSLVerzoek(Resource dsl);
}
