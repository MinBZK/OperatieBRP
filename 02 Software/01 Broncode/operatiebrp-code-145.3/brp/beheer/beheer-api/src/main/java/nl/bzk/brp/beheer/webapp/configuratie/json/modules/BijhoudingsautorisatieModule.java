/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import org.springframework.stereotype.Component;

/**
 * Bijhoudingsautorisatie module voor serializen en deserializen van Bijhoudingsautorisatie.
 */
@Component
public class BijhoudingsautorisatieModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld indicatie model autorisatie.
     */
    public static final String INDICATIE_MODEL_AUTORISATIE = "indicatieModelAutorisatie";
    /**
     * veld naam.
     */
    public static final String NAAM = "naam";
    /**
     * veld datum ingang.
     */
    public static final String DATUM_INGANG = "datumIngang";
    /**
     * veld datum einde.
     */
    public static final String DATUM_EINDE = "datumEinde";
    /**
     * veld indicatie geblokkeerd.
     */
    public static final String INDICATIE_GEBLOKKEERD = "indicatieGeblokkeerd";
    /**
     * veld soortAdministratieveHandeling.
     */
    public static final String SOORT_ADMINISTRATIEVE_HANDELING = "soortAdministratieveHandeling";
    /**
     * veld soortAdministratieveHandelingSet.
     */
    public static final String SOORT_ADMINISTRATIEVE_HANDELING_SET = "soortAdministratieveHandelingSet";
    /**
     * veld actief
     **/
    public static final String ACTIEF = "actief";
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
     * @param bijhoudingsautorisatieDeserializer specifieke deserializer voor Bijhoudingsautorisaties
     * @param bijhoudingsautorisatieSerializer specifieke serializer voor Bijhoudingsautorisaties
     */
    @Inject
    public BijhoudingsautorisatieModule(
            final BijhoudingsautorisatieDeserializer bijhoudingsautorisatieDeserializer,
            final BijhoudingsautorisatieSerializer bijhoudingsautorisatieSerializer) {
        addDeserializer(Bijhoudingsautorisatie.class, bijhoudingsautorisatieDeserializer);
        addSerializer(Bijhoudingsautorisatie.class, bijhoudingsautorisatieSerializer);

    }

    @Override
    public final String getModuleName() {
        return "BijhoudingsautorisatieModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
