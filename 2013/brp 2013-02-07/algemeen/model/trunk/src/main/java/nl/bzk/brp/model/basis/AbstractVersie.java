/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Klasse om versie nummer van een model bij te houden.
 * TODO: NB: Wordt niet meer gebruikt als super klasse van PersoonHisVolledig,
 * moet in PersoonHisVolledigCache geintegreerd worden.
 */
public abstract class AbstractVersie {
    /** De huidige versie van het model. */
    public static final int HUIDIGE_VERSIE = 1;

    private final Integer versie;

    /**
     * Constructor.
     *
     * @param versie de versie
     */
    public AbstractVersie(final Integer versie) {
        this.versie = versie;
    }

    @JsonProperty
    public int getVersie() {
        return versie;
    }

    @Override
    public String toString() {
        return "AbstractVersie: " + getVersie();
    }
}
