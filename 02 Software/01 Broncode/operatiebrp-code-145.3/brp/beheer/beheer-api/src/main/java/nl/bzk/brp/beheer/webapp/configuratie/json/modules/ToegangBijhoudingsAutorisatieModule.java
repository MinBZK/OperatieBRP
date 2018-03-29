/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import org.springframework.stereotype.Component;

/**
 * Abonnement module voor serializen en deserializen van een toegang.
 */
@Component
public class ToegangBijhoudingsAutorisatieModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld leveringsautorisatie.
     */
    public static final String BIJHOUDINGSAUTORISATIE = "bijhoudingsautorisatie";
    /**
     * veld geautoriseerde.
     */
    public static final String GEAUTORISEERDE = "geautoriseerde";
    /**
     * veld ondertekenaar.
     */
    public static final String ONDERTEKENAAR = "ondertekenaar";
    /**
     * veld transporteur.
     */
    public static final String TRANSPORTEUR = "transporteur";
    /**
     * veld datum ingang.
     */
    public static final String DATUM_INGANG = "datumIngang";
    /**
     * veld datum einde.
     */
    public static final String DATUM_EINDE = "datumEinde";
    /**
     * veld afleverpunt.
     */
    public static final String AFLEVERPUNT = "afleverpunt";
    /**
     * veld indicatie geblokkeerd.
     */
    public static final String INDICATIE_GEBLOKKEERD = "indicatieGeblokkeerd";

    /**
     * waarde ja.
     */
    public static final String WAARDE_JA = "Ja";
    /**
     * waarde nee.
     */
    public static final String WAARDE_NEE = "Nee";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param toegangBijhoudingsAutorisatieDeserializer specifieke deserializer voor toegang leverings autorisaties
     * @param toegangBijhoudingsAutorisatieSerializer specifieke serializer voor toegang leverings autorisaties
     */
    @Inject
    public ToegangBijhoudingsAutorisatieModule(
            final ToegangBijhoudingsAutorisatieDeserializer toegangBijhoudingsAutorisatieDeserializer,
            final ToegangBijhoudingsAutorisatieSerializer toegangBijhoudingsAutorisatieSerializer) {
        addDeserializer(ToegangBijhoudingsautorisatie.class, toegangBijhoudingsAutorisatieDeserializer);
        addSerializer(ToegangBijhoudingsautorisatie.class, toegangBijhoudingsAutorisatieSerializer);
    }

    @Override
    public final String getModuleName() {
        return "ToegangBijhoudingsautorisatieModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
