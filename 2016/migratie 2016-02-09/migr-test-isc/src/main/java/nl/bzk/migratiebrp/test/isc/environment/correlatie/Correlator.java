/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.correlatie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Correlator.
 */
@Component
public final class Correlator {

    private static final String ERROR_GEEN_UITGAAND_BERICHT = "Er is geen uitgaand bericht geweest met volgnummer %s op kanaal %s.";
    private static final String ERROR_GEEN_INKOMEND_BERICHT = "Er is geen inkomend bericht geweest met volgnummer %s op kanaal %s.";

    private final Map<String, String> correlatieMap = new HashMap<>();
    private final Map<String, Set<String>> uitgaand = new HashMap<>();
    private final Map<String, Set<String>> inkomend = new HashMap<>();

    /**
     * Registreer een uitgaand bericht referentie.
     *
     * @param volgnummer
     *            volgnummer (uit de test files)
     * @param kanaal
     *            kanaal
     * @param berichtReferentie
     *            'echte' bericht referentie
     */
    public void registreerUitgaand(final String volgnummer, final String kanaal, final String berichtReferentie) {
        correlatieMap.put(volgnummer, berichtReferentie);

        if (!uitgaand.containsKey(kanaal)) {
            uitgaand.put(kanaal, new HashSet<String>());
        }
        uitgaand.get(kanaal).add(volgnummer);
    }

    /**
     * Controleer of een uitgaand volgnummer geregistreerd is voor een kanaal.
     *
     * @param volgnummer
     *            volgnummer
     * @param kanaal
     *            kanaal
     */
    public void controleerUitgaand(final String volgnummer, final String kanaal) {
        if (!(uitgaand.containsKey(kanaal) && uitgaand.get(kanaal).contains(volgnummer))) {
            throw new IllegalArgumentException(String.format(ERROR_GEEN_UITGAAND_BERICHT, volgnummer, kanaal));
        }
    }

    /**
     * Registreer een inkomende bericht referentie.
     *
     * @param volgnummer
     *            volgnummer (uit de test files)
     * @param kanaal
     *            kanaal
     * @param berichtReferentie
     *            'echte' bericht referentie
     */
    public void registreerInkomend(final String volgnummer, final String kanaal, final String berichtReferentie) {
        correlatieMap.put(volgnummer, berichtReferentie);

        if (!inkomend.containsKey(kanaal)) {
            inkomend.put(kanaal, new HashSet<String>());
        }
        inkomend.get(kanaal).add(volgnummer);
    }

    /**
     * Controleer of een inkomend volgnummer geregistreerd is voor een kanaal.
     *
     * @param volgnummer
     *            volgnummer
     * @param kanaal
     *            kanaal
     */
    public void controleerInkomend(final String volgnummer, final String kanaal) {
        if (!(inkomend.containsKey(kanaal) && inkomend.get(kanaal).contains(volgnummer))) {
            throw new IllegalArgumentException(String.format(ERROR_GEEN_INKOMEND_BERICHT, volgnummer, kanaal));
        }
    }

    /**
     * Geef de bericht referentie die bij een volgnummer is geregistreerd.
     *
     * @param volgnummer
     *            volgnummer
     * @return bericht referentie, null als niet gevonden
     */
    public String getBerichtReferentie(final String volgnummer) {
        return correlatieMap.get(volgnummer);
    }

    /**
     * Geef een ingekomen bericht referentie die bij een volgnummer is geregistreerd.
     *
     * @param volgnummer
     *            volgnummer
     * @return bericht referentie, null als niet gevonden
     */
    public String getInkomendBerichtReferentie(final String volgnummer) {
        for (final Set<String> referenties : inkomend.values()) {
            if (referenties.contains(volgnummer)) {
                return correlatieMap.get(volgnummer);
            }
        }
        return null;
    }

    /**
     * Geef een ingekomen bericht referentie die bij een volgnummer is geregistreerd.
     *
     * @param volgnummer
     *            volgnummer
     * @return bericht referentie, null als niet gevonden
     */
    public String getUitgaandBerichtReferentie(final String volgnummer) {
        for (final Set<String> referenties : uitgaand.values()) {
            if (referenties.contains(volgnummer)) {
                return correlatieMap.get(volgnummer);
            }
        }
        return null;
    }

    /**
     * Geef de eerste geregistreerde bericht referenties.
     *
     * @return bericht referentie
     */
    public String getFirstBerichtReferentie() {
        final List<String> keys = new ArrayList<>(correlatieMap.keySet());
        return correlatieMap.get(keys.get(0));
    }

    /**
     * Geef de laatste geregistreerde bericht referenties.
     *
     * @return bericht referentie
     */
    public String getLastBerichtReferentie() {
        final List<String> keys = new ArrayList<>(correlatieMap.keySet());
        return correlatieMap.get(keys.get(keys.size() - 1));
    }

    /**
     * Reset.
     */
    public void reset() {
        correlatieMap.clear();
        uitgaand.clear();
        inkomend.clear();
    }

    /**
     * Geef alle geregistreerde bericht referenties.
     *
     * @return alle geregistreerde bericht referenties
     */
    public List<String> getAlleBerichtReferenties() {
        return new ArrayList<>(correlatieMap.values());
    }

}
