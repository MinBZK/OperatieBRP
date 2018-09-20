/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.afnemerindicatie;

import com.fasterxml.jackson.core.JsonFactory;


/**
 * Serializer die Afnemerindicaties in het JSON formaat kan serializeren.
 */
public final class AfnemerIndicatieStringSerializer extends AfnemerIndicatieSmileSerializer implements
    AfnemerIndicatieSerializer
{

    /**
     * Default constructor.
     */
    public AfnemerIndicatieStringSerializer() {
        super(new JsonFactory(), new AfnemerIndicatieMappingConfiguratieModule());
    }
}
