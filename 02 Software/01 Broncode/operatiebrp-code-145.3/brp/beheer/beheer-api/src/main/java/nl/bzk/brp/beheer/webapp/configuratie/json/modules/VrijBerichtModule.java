/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBerichtPartij;
import org.springframework.stereotype.Component;

/**
 * Vrij bericht Module.
 */
@Component
public class VrijBerichtModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param vrijBerichtSerializer serializer voor vrije berichten
     * @param vrijBerichtDeserializer deserializer voor vrije berichten
     * @param vrijBerichtPartijSerializer serializer voor vrij bericht partijen
     */
    @Inject
    public VrijBerichtModule(final VrijBerichtSerializer vrijBerichtSerializer, final VrijBerichtDeserializer vrijBerichtDeserializer,
                             final VrijBerichtPartijSerializer vrijBerichtPartijSerializer) {
        addSerializer(VrijBericht.class, vrijBerichtSerializer);
        addDeserializer(VrijBericht.class, vrijBerichtDeserializer);
        addSerializer(VrijBerichtPartij.class, vrijBerichtPartijSerializer);
    }

    @Override
    public final String getModuleName() {
        return "VrijBerichtModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
