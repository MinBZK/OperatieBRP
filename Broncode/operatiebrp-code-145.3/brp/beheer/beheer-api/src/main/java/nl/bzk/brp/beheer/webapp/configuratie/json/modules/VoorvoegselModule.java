/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
import org.springframework.stereotype.Component;

/**
 * Abonnement module voor serializen en deserializen van een toegang.
 */
@Component
public class VoorvoegselModule extends SimpleModule {

    /** veld id. */
    public static final String ID = "id";
    /** veld voorvoegsel. */
    public static final String VOORVOEGSEL = "voorvoegsel";
    /** veld scheidingsteken. */
    public static final String SCHEIDINGSTEKEN = "scheidingsteken";

    private static final long serialVersionUID = 1L;

    /**
     * contructor voor nieuwe ToegangLeveringsautorisatieModule.
     *
     * @param voorvoegselDeserializer
     *            deserializer
     * @param voorvoegselSerializer
     *            serializer
     */
    @Inject
    public VoorvoegselModule(final VoorvoegselDeserializer voorvoegselDeserializer, final VoorvoegselSerializer voorvoegselSerializer) {
        addDeserializer(Voorvoegsel.class, voorvoegselDeserializer);
        addSerializer(Voorvoegsel.class, voorvoegselSerializer);
    }

    @Override
    public final String getModuleName() {
        return "VoorvoegselModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
