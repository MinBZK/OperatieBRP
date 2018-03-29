/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import org.springframework.stereotype.Component;

/**
 * Dienst module voor serializen en deserializen van Dienst.
 */
@Component
public class DienstModule extends SimpleModule {

    /** veld id. */
    public static final String ID = "id";
    /** veld dienstbundel. */
    public static final String DIENSTBUNDEL = "dienstbundel";
    /** veld leveringsautorisatie. */
    public static final String LEVERINGSAUTORISATIE = "leveringsautorisatie";
    /** veld soort. */
    public static final String SOORT = "soort";
    /** veld effect afnemerindicaties. */
    public static final String EFFECT_AFNEMERINDICATIES = "effectAfnemerindicaties";
    /** veld datum ingang. */
    public static final String DATUM_INGANG = "datumIngang";
    /** veld datum einde. */
    public static final String DATUM_EINDE = "datumEinde";
    /** veld indicatie geblokkeerd. */
    public static final String INDICATIE_GEBLOKKEERD = "indicatieGeblokkeerd";
    /** veld attenderingscriterium. */
    public static final String ATTENDERINGSCRITERIUM = "attenderingscriterium";
    /** veld eerste selectiedatum. */
    public static final String EERSTE_SELECTIEDATUM = "eersteSelectiedatum";
    /** veld soort selectie. */
    public static final String SOORT_SELECTIE = "soortSelectie";
    /** veld historie vorm selectie. */
    public static final String HISTORIE_VORM = "historieVorm";
    /** veld indicatie selectie resultaat controleren. */
    public static final String INDICATIE_RESULTAAT_CONTROLEREN = "indicatieResultaatControleren";
    /** veld selectie interval. */
    public static final String SELECTIE_INTERVAL = "selectieInterval";
    /** veld eenheid selectie interval. */
    public static final String EENHEID_SELECTIE_INTERVAL = "eenheidSelectieInterval";
    /** veld nadere selectie criterium. */
    public static final String NADERE_SELECTIE_CRITERIUM = "nadereSelectieCriterium";
    /** veld selectie peilmoment materieel resultaat. */
    public static final String SELECTIE_PEILMOMENT_MATERIEEL_RESULTAAT = "selectiePeilmomentMaterieelResultaat";
    /** veld selectie peilmoment formeel resultaat. */
    public static final String SELECTIE_PEILMOMENT_FORMEEL_RESULTAAT = "selectiePeilmomentFormeelResultaat";
    /** veld soort max personen per selectie. */
    public static final String MAX_PERSONEN_PER_SELECTIE = "maxPersonenPerSelectie";
    /** veld soort max grootte selectie. */
    public static final String MAX_GROOTTE_SELECTIE = "maxGrootteSelectie";
    /** veld indicatie volledig bericht selectie. */
    public static final String INDICATIE_VOLLEDIG_BERICHT_BIJ_AFNEMERINDICATIE_NA_SELECTIE = "indicatieVolledigBerichtBijAfnemerindicatieNaSelectie";
    /** veld soort leverwijze selectie. */
    public static final String LEVERWIJZE_SELECTIE = "leverwijzeSelectie";

    /** waarde ja. */
    public static final String WAARDE_JA = "Ja";
    /** waarde nee. */
    public static final String WAARDE_NEE = "Nee";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param dienstDeserializer
     *            specifieke deserializer voor diensten
     * @param dienstSerializer
     *            specifieke serializer voor diensten
     */
    @Inject
    public DienstModule(final DienstDeserializer dienstDeserializer, final DienstSerializer dienstSerializer) {
        addDeserializer(Dienst.class, dienstDeserializer);
        addSerializer(Dienst.class, dienstSerializer);
    }

    @Override
    public final String getModuleName() {
        return "DienstModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
