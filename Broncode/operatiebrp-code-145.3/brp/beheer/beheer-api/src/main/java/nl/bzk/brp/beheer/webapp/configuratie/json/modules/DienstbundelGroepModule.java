/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import org.springframework.stereotype.Component;

/**
 * Dienstbundelgroep module voor serializen en deserializen van DienstbundelGroep.
 */
@Component
public class DienstbundelGroepModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld leveringsautorisatie.
     */
    public static final String DIENSTBUNDEL = "dienstbundel";
    /**
     * veld soort.
     */
    public static final String GROEP = "Naam";
    /**
     * veld soort.
     */
    public static final String GROEP_ID = "groepId";
    /**
     * veld indicatie geblokkeerd.
     */
    public static final String INDICATIE_FORMELE_HISTORIE = "indicatieFormeleHistorie";
    /**
     * veld indicatie geblokkeerd.
     */
    public static final String INDICATIE_MATERIELE_HISTORIE = "indicatieMaterieleHistorie";
    /**
     * veld indicatie geblokkeerd.
     */
    public static final String INDICATIE_VERANTWOORDING = "indicatieVerantwoording";

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
     * @param dienstbundelGroepDeserializer specifieke deserializer voor dienstbundel groepen
     * @param dienstbundelGroepSerializer specifieke serializer voor dienstbundel groepen
     */
    @Inject
    public DienstbundelGroepModule(
            final DienstbundelGroepDeserializer dienstbundelGroepDeserializer,
            final DienstbundelGroepSerializer dienstbundelGroepSerializer) {
        addDeserializer(DienstbundelGroep.class, dienstbundelGroepDeserializer);
        addSerializer(DienstbundelGroep.class, dienstbundelGroepSerializer);
    }

    @Override
    public final String getModuleName() {
        return "DienstbundelGroepModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
