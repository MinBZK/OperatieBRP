/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer.formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Collectie klasse waarin Eclipse code formatter profielen in opgeslagen kunnen worden.
 */
public class FormatterProfielen {

    /**
     * Naam van het Eclipse formatter profiel dat in de formatter XML configuratie staat.
     */
    public static final String CODE_FORMATTER_PROFILE = "CodeFormatterProfile";

    private List<Map<String, String>> profiles = new ArrayList<>();

    /**
     * Voegt een Eclipse formatter profiel toe.
     *
     * @param profile Het toe te voegen profiel.
     */
    public void addProfile(final FormatterProfiel profile) {
        if (CODE_FORMATTER_PROFILE.equals(profile.getKind())) {
            profiles.add(profile.getSettings());
        }
    }

    public List<Map<String, String>> getProfiles() {
        return profiles;
    }

}
