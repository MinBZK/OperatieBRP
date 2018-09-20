/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek;

/**
 * AbonnementLO3Rubriek module voor serializen en deserializen van AbonnementLO3Rubrieken.
 */
public class DienstbundelLO3RubriekModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "iD";
    /**
     * veld rubriek.
     */
    public static final String RUBRIEK = "rubriek";
    /**
     * veld actief.
     */
    public static final String ACTIEF = "actief";
    /**
     * veld abonnement.
     */
    public static final String DIENSTBUNDEL = "dienstbundel";

    private static final long serialVersionUID = 1L;

    /**
     * contructor voor nieuwe LeveringsautorisatieModule.
     */
    public DienstbundelLO3RubriekModule() {
        addDeserializer(DienstbundelLO3Rubriek.class, new DienstbundelLO3RubriekDeserializer());
        addSerializer(DienstbundelLO3Rubriek.class, new DienstbundelLO3RubriekSerializer());
    }

    @Override
    public final String getModuleName() {
        return "DienstbundelLO3RubriekModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
