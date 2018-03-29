/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import org.springframework.stereotype.Component;

/**
 * Dienstbundel module voor serializen en deserializen van Dienstbundel.
 */
@Component
public class PartijRolModule extends SimpleModule {

    /** veld id. */
    public static final String ID = "id";
    /** veld partij. */
    public static final String PARTIJ = "partij";
    /** veld rol. */
    public static final String ROL = "rol";
    /** veld naam. */
    public static final String NAAM = "naam";
    /** veld datum ingang. */
    public static final String DATUM_INGANG = "datumIngang";
    /** veld datum einde. */
    public static final String DATUM_EINDE = "datumEinde";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param partijRolDeserializer
     *            specifieke deserializer voor partij rol
     * @param partijRolSerializer
     *            specifieke serializer voor partij rol
     */
    @Inject
    public PartijRolModule(final PartijRolDeserializer partijRolDeserializer, final PartijRolSerializer partijRolSerializer) {
        addDeserializer(PartijRol.class, partijRolDeserializer);
        addSerializer(PartijRol.class, partijRolSerializer);
    }

    @Override
    public final String getModuleName() {
        return "PartijRolModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
