/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Model voor een Brp Actie met Documenten.
 */
public class GgoBrpActie implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, String> inhoud;
    private Map<String, String> administratieveHandeling;
    private List<Map<String, String>> actieBronnen;
    private List<GgoStapel> documenten;

    /**
     * Constructor.
     */
    public GgoBrpActie() {
        super();
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return the inhoud
     */
    public final Map<String, String> getInhoud() {
        return inhoud;
    }

    /**
     * Sets the inhoud.
     *
     * @param inhoud
     *            the inhoud to set
     */
    public final void setInhoud(final Map<String, String> inhoud) {
        this.inhoud = inhoud;
    }

    /**
     * Geef de waarde van administratieve handeling.
     *
     * @return de administratieve handeling
     */
    public final Map<String, String> getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Sets the administratieve handeling.
     *
     * @param administratieveHandeling
     *            De administratieve handeling
     */
    public final void setAdministratieveHandeling(final Map<String, String> administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Geef de waarde van actie bronnen.
     *
     * @return De actieBronnen.
     */
    public final List<Map<String, String>> getActieBronnen() {
        return actieBronnen;
    }

    /**
     * Sets the actie bronnen.
     *
     * @param actieBronnen
     *            De actieBronnen.
     */
    public final void setActieBronnen(final List<Map<String, String>> actieBronnen) {
        this.actieBronnen = actieBronnen;
    }

    /**
     * Geef de waarde van documenten.
     *
     * @return the documenten
     */
    public final List<GgoStapel> getDocumenten() {
        return documenten;
    }

    /**
     * Zet de waarde van documenten.
     *
     * @param documenten
     *            the documenten to set
     */
    public final void setDocumenten(final List<GgoStapel> documenten) {
        this.documenten = documenten;
    }

}
