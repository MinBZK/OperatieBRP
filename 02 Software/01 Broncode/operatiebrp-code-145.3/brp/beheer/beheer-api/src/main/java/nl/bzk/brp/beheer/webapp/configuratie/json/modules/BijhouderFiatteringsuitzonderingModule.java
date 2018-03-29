/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import org.springframework.stereotype.Component;

/**
 * BijhouderFiatteringsuitzondering module voor serializen en deserializen van BijhouderFiatteringsuitzonderingen.
 */
@Component
public class BijhouderFiatteringsuitzonderingModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld indicatie model autorisatie.
     */
    public static final String BIJHOUDER = "bijhouder";
    /**
     * veld naam.
     */
    public static final String BIJHOUDER_BIJHOUDINGSVOORSTEL = "bijhouderBijhoudingsvoorstel";
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
     * veld soortDocument.
     */
    public static final String SOORT_DOCUMENT = "soortDocument";

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
     * @param bijhouderFiatteringsuitzonderingDeserializer specifieke deserializer voor BijhouderFiatteringsuitzondering
     * @param bijhouderFiatteringsuitzonderingSerializer specifieke serializer voor BijhouderFiatteringsuitzondering
     */
    @Inject
    public BijhouderFiatteringsuitzonderingModule(
            final BijhouderFiatteringsuitzonderingDeserializer bijhouderFiatteringsuitzonderingDeserializer,
            final BijhouderFiatteringsuitzonderingSerializer bijhouderFiatteringsuitzonderingSerializer) {
        addDeserializer(BijhouderFiatteringsuitzondering.class, bijhouderFiatteringsuitzonderingDeserializer);
        addSerializer(BijhouderFiatteringsuitzondering.class, bijhouderFiatteringsuitzonderingSerializer);

    }

    @Override
    public final String getModuleName() {
        return "BijhouderFiatteringsuitzonderingModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
