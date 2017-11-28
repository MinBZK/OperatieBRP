/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.view.BijhoudingsautorisatieSoortAdministratieveHandelingView;
import org.springframework.stereotype.Component;

/**
 * SoortAdministratieveHandeling module voor serializen en deserializen van SoortAdministratieveHandeling.
 */
@Component
public class BijhoudingsautorisatieSoortAdministratieveHandelingModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld bijhoudingsautorisatie
     */
    public static final String BIJHOUDINGSAUTORISATIE = "bijhoudingsautorisatie";
    /**
     * veld actief.
     */
    public static final String ACTIEF = "actief";
    /**
     * veld attribuut.
     */
    public static final String SOORT_ADMINISTRATIEVE_HANDELING = "soortAdministratieHandeling";
    /**
     * veld naam.
     */
    public static final String NAAM = "naam";
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
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingDeserializer specifieke deserializer voor BijhoudingsautorisatieSoortAdministratieveHandeling
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingSerializer specifieke serializer voor BijhoudingsautorisatieSoortAdministratieveHandeling
     */
    @Inject
    public BijhoudingsautorisatieSoortAdministratieveHandelingModule(
            final BijhoudingsautorisatieSoortAdministratieveHandelingDeserializer bijhoudingsautorisatieSoortAdministratieveHandelingDeserializer,
            final BijhoudingsautorisatieSoortAdministratieveHandelingSerializer bijhoudingsautorisatieSoortAdministratieveHandelingSerializer) {
        addDeserializer(BijhoudingsautorisatieSoortAdministratieveHandelingView.class, bijhoudingsautorisatieSoortAdministratieveHandelingDeserializer);
        addSerializer(BijhoudingsautorisatieSoortAdministratieveHandelingView.class, bijhoudingsautorisatieSoortAdministratieveHandelingSerializer);
    }

    @Override
    public final String getModuleName() {
        return "BijhoudingsautorisatieSoortAdministratieveHandelingModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
