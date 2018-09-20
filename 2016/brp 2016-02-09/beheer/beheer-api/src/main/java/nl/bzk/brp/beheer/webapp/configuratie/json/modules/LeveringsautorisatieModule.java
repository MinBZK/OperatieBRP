/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.model.beheer.autaut.Dienst;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;

/**
 * Abonnement module voor serializen en deserializen van Abonnement.
 */
public class LeveringsautorisatieModule extends SimpleModule {

    /** veld id. */
    public static final String ID = "iD";
    /** veld naam. */
    public static final String NAAM = "naam";
    /** veld populatiebeperking. */
    public static final String POPULATIE_BEPERKING = "populatieBeperking";
    /** veld protocolleringsniveau. */
    public static final String PROTOCOLLERINGSNIVEAU = "protocolleringsniveau";
    /** veld datum ingang. */
    public static final String DATUM_INGANG = "datumIngang";
    /** veld datum einde. */
    public static final String DATUM_EINDE = "datumEinde";
    /** veld toestand. */
    public static final String TOESTAND = "toestand";
    /** veld indicatie alias soort administratieve handeling leveren. */
    public static final String INDICATIE_ALIAS_LEVEREN = "indicatieAliasLeveren";
    /** veld diensten. */
    public static final String DIENSTEN = "diensten";
    /** veld catalogusoptie. */
    public static final String CATALOGUSOPTIE = "catalogusoptie";
    /** veld abonnement. */
    public static final String ABONNEMENT = "abonnement";
    /** veld nadere populatie beperking. */
    public static final String NADERE_POPULATIE_BEPERKING = "naderePopulatieBeperking";
    /** veld attenderingscriterium. */
    public static final String ATTENDERINGSCRITERIUM = "attenderingscriterium";
    /** veld eerste selectiedatum. */
    public static final String EERSTE_SELECTIEDATUM = "eersteSelectiedatum";
    /** veld selectieperiode in maanden. */
    public static final String SELECTIEPERIODE_IN_MAANDEN = "selectieperiodeInMaanden";
    /** veld indicatie populatiebeperking volledig geconverteerd. */
    public static final String INDICATIE_POPULATIEBEPERKING_VOLLEDIG_GECONVERTEERD = "indicatiePopulatieBeperkingVolledigGeconverteerd";
    /** veld indicatie nadere populatiebeperking volledig geconverteerd. */
    public static final String INDICATIE_NADERE_POPULATIEBEPERKING_VOLLEDIG_GECONVERTEERD = "indicatieNaderePopulatieBeperkingVolledigGeconverteerd";
    /** veld toelichting. */
    public static final String TOELICHTING = "toelichting";

    /** waarde ja. */
    public static final String WAARDE_JA = "Ja";
    /** waarde nee. */
    public static final String WAARDE_NEE = "Nee";

    private static final long serialVersionUID = 1L;

    /**
     * contructor voor nieuwe LeveringsautorisatieModule.
     */
    public LeveringsautorisatieModule() {
        addDeserializer(Leveringsautorisatie.class, new LeveringsautorisatieDeserializer());
        addDeserializer(Dienst.class, new DienstDeserializer());
        addSerializer(Leveringsautorisatie.class, new LeveringsautorisatieSerializer());
        addSerializer(Dienst.class, new DienstSerializer());
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
