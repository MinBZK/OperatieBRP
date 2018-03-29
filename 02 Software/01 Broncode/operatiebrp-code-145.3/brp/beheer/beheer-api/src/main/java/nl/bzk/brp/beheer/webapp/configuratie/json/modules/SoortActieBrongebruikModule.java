/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import org.springframework.stereotype.Component;

/**
 * Abonnement module voor serializen en deserializen van een toegang.
 */
@Component
public class SoortActieBrongebruikModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld soortActie.
     */
    public static final String SOORT_ACTIE = "soortActie";
    /**
     * veld soortAdministratieveHandeling.
     */
    public static final String SOORT_ADMINISTRATIEVE_HANDELING = "soortAdministratieveHandeling";
    /**
     * veld soortDocument.
     */
    public static final String SOORT_DOCUMENT = "soortDocument";
    /**
     * veld datumAanvangGeldigheid.
     */
    public static final String DATUM_AANVANG_GELDIGHEID = "datumAanvangGeldigheid";
    /**
     * veld datumEindeGeldigheid.
     */
    public static final String DATUM_EINDE_GELDIGHEID = "datumEindeGeldigheid";

    private static final long serialVersionUID = 1L;

    /**
     * contructor voor nieuwe ToegangLeveringsautorisatieModule.
     * @param soortActieBrongebruikDeserializer deserializer
     * @param soortActieBrongebruikSerializer serializer
     */
    @Inject
    public SoortActieBrongebruikModule(
            final SoortActieBrongebruikDeserializer soortActieBrongebruikDeserializer,
            final SoortActieBrongebruikSerializer soortActieBrongebruikSerializer) {
        addDeserializer(SoortActieBrongebruik.class, soortActieBrongebruikDeserializer);
        addSerializer(SoortActieBrongebruik.class, soortActieBrongebruikSerializer);
    }

    @Override
    public final String getModuleName() {
        return "SoortActieBrongebruikModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
