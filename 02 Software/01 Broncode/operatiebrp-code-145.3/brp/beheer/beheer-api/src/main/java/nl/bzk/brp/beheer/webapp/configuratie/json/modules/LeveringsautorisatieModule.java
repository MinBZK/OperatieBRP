/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import org.springframework.stereotype.Component;

/**
 * Abonnement module voor serializen en deserializen van Abonnement.
 */
@Component
public class LeveringsautorisatieModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld stelsel.
     */
    public static final String STELSEL = "stelsel";
    /**
     * veld indicatie model autorisatie.
     */
    public static final String INDICATIE_MODEL_AUTORISATIE = "indicatieModelAutorisatie";
    /**
     * veld naam.
     */
    public static final String NAAM = "naam";
    /**
     * veld protocolleringsniveau.
     */
    public static final String PROTOCOLLERINGSNIVEAU = "protocolleringsniveau";
    /**
     * veld indicatie alias soort administratieve handeling leveren.
     */
    public static final String INDICATIE_ALIAS_LEVEREN = "indicatieAliasLeveren";
    /**
     * veld datum ingang.
     */
    public static final String DATUM_INGANG = "datumIngang";
    /**
     * veld datum einde.
     */
    public static final String DATUM_EINDE = "datumEinde";
    /**
     * veld populatiebeperking.
     */
    public static final String POPULATIE_BEPERKING = "populatieBeperking";
    /**
     * veld indicatie populatiebeperking volledig geconverteerd.
     */
    public static final String INDICATIE_POPULATIEBEPERKING_VOLLEDIG_GECONVERTEERD = "indicatiePopulatieBeperkingVolledigGeconverteerd";
    /**
     * veld toelichting.
     */
    public static final String TOELICHTING = "toelichting";
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
     * @param leveringsautorisatieDeserializer specifieke deserializer voor leveringsautorisaties
     * @param leveringsautorisatieSerializer specifieke serializer voor leveringsautorisaties
     */
    @Inject
    public LeveringsautorisatieModule(
            final LeveringsautorisatieDeserializer leveringsautorisatieDeserializer,
            final LeveringsautorisatieSerializer leveringsautorisatieSerializer) {
        addDeserializer(Leveringsautorisatie.class, leveringsautorisatieDeserializer);
        addSerializer(Leveringsautorisatie.class, leveringsautorisatieSerializer);

    }

    @Override
    public final String getModuleName() {
        return "LeveringsautorisatieModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
