/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Klasse om versie nummer van een model bij te houden.
 */
public abstract class AbstractVersie {
    /** De huidige versie van het model. */
    public static final int HUIDIGE_VERSIE = 1;

    private int versie;

    @JsonProperty
    public int getVersie() {
        return versie;
    }

    public void setVersie(final int versie) {
        this.versie = versie;
    }

    @Override
    public String toString() {
        return "AbstractVersie: " + getVersie();
    }
}
