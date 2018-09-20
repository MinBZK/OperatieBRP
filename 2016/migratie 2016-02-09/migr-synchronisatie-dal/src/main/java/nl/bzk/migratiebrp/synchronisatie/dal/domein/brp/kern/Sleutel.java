/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern;

import java.util.Map;

/**
 * Interface voor de sleutel classes die bij delta worden gebruikt.
 */
public interface Sleutel {

    /**
     * Voeg een veld/deel toe aan de sleutel.
     *
     * @param naam
     *            de naam van het sleuteldeel/veld
     * @param sleuteldeel
     *            de waarde van het sleuteldeel/veld
     */
    void addSleuteldeel(final String naam, final Object sleuteldeel);

    /**
     * Vraagt de delen van een sleutel op.
     *
     * @return de delen van de sleutel
     */
    Map<String, Object> getDelen();

    /**
     * Zet het optionele veld ID. Wordt gebruikt om acties te onderscheiden.
     *
     * @param id
     *            het ID van bv een {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie}
     */
    void setId(final Long id);

    /**
     * Geeft het optionele ID veld van bv een
     * {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie} terug.
     *
     * @return het ID van bv een {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie}
     */
    Long getId();

    /**
     * Geef de waarde van veld.
     *
     * @return veld
     */
    String getVeld();

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those
     * provided by {@link java.util.HashMap}.
     *
     * @return a hash code value for this object.
     * @see java.lang.Object#equals(java.lang.Object)
     * @see java.lang.System#identityHashCode
     */
    @Override
    int hashCode();

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj
     *            the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     * @see Object#hashCode()
     * @see java.util.HashMap
     */
    @Override
    boolean equals(final Object obj);

    /**
     * @return Een korte omschrijving van de sleutel.
     */
    String toShortString();
}
