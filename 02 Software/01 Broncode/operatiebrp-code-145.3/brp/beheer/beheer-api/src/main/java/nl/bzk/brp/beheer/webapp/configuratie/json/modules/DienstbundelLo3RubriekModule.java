/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.view.DienstbundelLo3RubriekView;
import org.springframework.stereotype.Component;

/**
 * AbonnementLO3Rubriek module voor serializen en deserializen van AbonnementLO3Rubrieken.
 */
@Component
public class DienstbundelLo3RubriekModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld rubriek.
     */
    public static final String RUBRIEK = "rubriek";
    /**
     * veld rubrieknaam.
     */
    public static final String RUBRIEK_NAAM = "rubrieknaam";
    /**
     * veld actief.
     */
    public static final String ACTIEF = "actief";
    /**
     * veld abonnement.
     */
    public static final String DIENSTBUNDEL = "dienstbundel";

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
     * contructor voor nieuwe LeveringsautorisatieModule.
     * @param dienstbundelLo3RubriekDeserializer Deserializer
     * @param dienstbundelLo3RubriekSerializer Serializer
     */
    @Inject
    public DienstbundelLo3RubriekModule(
            final DienstbundelLo3RubriekDeserializer dienstbundelLo3RubriekDeserializer,
            final DienstbundelLo3RubriekSerializer dienstbundelLo3RubriekSerializer) {
        addDeserializer(DienstbundelLo3RubriekView.class, dienstbundelLo3RubriekDeserializer);
        addSerializer(DienstbundelLo3RubriekView.class, dienstbundelLo3RubriekSerializer);
    }

    @Override
    public final String getModuleName() {
        return "DienstbundelLo3RubriekModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
