/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.conversie.zoekvraag.xml;

/**
 */
public class ParameterItem {

    private String rubrieknummer;
    private String zoekwaarde;

    public String getRubrieknummer() {
        return rubrieknummer;
    }

    public void setRubrieknummer(final String rubrieknummer) {
        this.rubrieknummer = rubrieknummer;
    }

    public String getZoekwaarde() {
        return zoekwaarde;
    }

    public void setZoekwaarde(final String zoekwaarde) {
        this.zoekwaarde = zoekwaarde;
    }
}
