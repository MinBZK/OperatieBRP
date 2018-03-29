/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import org.springframework.stereotype.Component;

/**
 * Dienstbundel module voor serializen en deserializen van Dienstbundel.
 */
@Component
public class DienstbundelModule extends SimpleModule {

    /** veld id. */
    public static final String ID = "id";
    /** veld leveringsautorisatie. */
    public static final String LEVERINGSAUTORISATIE = "leveringsautorisatie";
    /** veld naam. */
    public static final String NAAM = "naam";
    /** veld datum ingang. */
    public static final String DATUM_INGANG = "datumIngang";
    /** veld datum einde. */
    public static final String DATUM_EINDE = "datumEinde";
    /** veld populatiebeperking. */
    public static final String NADERE_POPULATIE_BEPERKING = "naderePopulatiebeperking";
    /** veld indicatie nadere populatiebeperking volledig geconverteerd. */
    public static final String INDICATIE_NADERE_POPULATIE_BEPERKING_GECONVERTEERD = "indicatieNaderePopulatieBeperkingVolledigGeconverteerd";
    /** veld toelichting. */
    public static final String TOELICHTING = "toelichting";
    /** veld indicatie geblokkeerd. */
    public static final String INDICATIE_GEBLOKKEERD = "indicatieGeblokkeerd";

    /** waarde ja. */
    public static final String WAARDE_JA = "Ja";
    /** waarde nee. */
    public static final String WAARDE_NEE = "Nee";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param dienstbundelDeserializer
     *            specifieke deserializer voor dienstbundels
     * @param dienstbundelSerializer
     *            specifieke serializer voor dienstbundels
     */
    @Inject
    public DienstbundelModule(final DienstbundelDeserializer dienstbundelDeserializer, final DienstbundelSerializer dienstbundelSerializer) {
        addDeserializer(Dienstbundel.class, dienstbundelDeserializer);
        addSerializer(Dienstbundel.class, dienstbundelSerializer);
    }

    @Override
    public final String getModuleName() {
        return "DienstbundelModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
