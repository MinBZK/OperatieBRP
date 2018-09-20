/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HelperClass om teksten tijdelijk te vervangen door 'veilige' waarden (zonder haakjes en zonder operatoren, etc).
 */
public final class TekstHelper {

    private static final String TEKST_PATTERN = "(?<![/])\\\"(.*?)(?<![/])\\\"";

    private final Map<String, String> teksten = new HashMap<String, String>();
    private String veiligeRegel;

    /**
     * Constructor.
     *
     * @param gbaVoorwaardeRegel
     *            De te gebruiken voorwaarderegel.
     */
    public TekstHelper(final String gbaVoorwaardeRegel) {

        final StringBuilder result = new StringBuilder(gbaVoorwaardeRegel);

        final Matcher matcher = Pattern.compile(TEKST_PATTERN).matcher(gbaVoorwaardeRegel);
        int offset = 0;
        int teller = 0;
        while (matcher.find()) {
            final String identifier = "T" + new DecimalFormat("00").format(teller++);
            final String value = matcher.group();
            teksten.put(identifier, value);
            result.replace(matcher.start() - offset, matcher.end() - offset, identifier);
            offset += value.length() - identifier.length();
        }

        veiligeRegel = result.toString();
    }

    /**
     * Geeft de 'veilige' regel terug.
     *
     * @return De 'veilige' regel.
     */
    public String getVeiligeRegel() {
        return veiligeRegel;
    }

    /**
     * Zet de 'veilige' regel terug.
     *
     * @param veiligeRegel
     *            De te zetten 'veilige' regel.
     */
    public void setVeiligeRegel(final String veiligeRegel) {
        this.veiligeRegel = veiligeRegel;
    }

    /**
     * Geeft de GBA voorwaarderegel.
     *
     * @return De GBA voorwaarderegel.
     */
    public String getGbaVoorwaardeRegel() {
        String result = veiligeRegel;
        for (final Map.Entry<String, String> tekst : teksten.entrySet()) {
            result = result.replaceAll(tekst.getKey(), tekst.getValue());
        }
        return result;
    }

}
