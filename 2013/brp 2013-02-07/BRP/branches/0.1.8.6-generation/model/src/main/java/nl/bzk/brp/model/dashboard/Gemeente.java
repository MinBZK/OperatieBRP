/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;


/**
 * Woon- of registratiegemeente.
 * @refactor: refactor, waarom een Gemeente class die alleen maar een wrapper is? Indien het voor JSON doeleinden is, dan gewoon waardes meegeven in de constructor ipv Partij meegeven.
 */
public class Gemeente {

    private String code;

    private String naam;

    /**
     * Constructor voor een dashboard gemeente op basis van een Partij.
     * @param gemeente Partij van de gemeente
     */
    public Gemeente(final Partij gemeente) {
        if (gemeente.getGemeentecode() != null) {
            code = gemeente.getGemeentecode().toString();
        }
        if (gemeente.getNaam() != null) {
            naam = gemeente.getNaam().getWaarde();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

}
