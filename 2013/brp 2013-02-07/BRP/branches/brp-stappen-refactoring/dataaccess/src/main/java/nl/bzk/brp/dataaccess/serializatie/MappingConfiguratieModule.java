/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.serializatie;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;

/**
 * Implementatie van een Jackson {@link com.fasterxml.jackson.databind.Module}, om extra
 * configuratie van de mapping te kunnen registreren.
 */
public class MappingConfiguratieModule extends SimpleModule {

    /**
     * Default constructor, registreert deze module.
     */
    public MappingConfiguratieModule() {
        super("MappingConfiguratieModule", new Version(0, 1, 0, null, "nl.bzk.brp", "dataaccess"));
    }

    @Override
    public void setupModule(final SetupContext context) {
        context.setMixInAnnotations(RelatieModel.class, PlatteBetrokkenheidMixin.class);
    }
}
