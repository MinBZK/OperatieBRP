/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.VasteAttribuutWaarde;

/**
 * Uit migratieoogpunt kiezen we NIET voor een stamtabel.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.AttribuutTypenGenerator")
public enum LocatieTenOpzichteVanAdres implements VasteAttribuutWaarde<String> {

    /**
     * bij.
     */
    BY("by", "bij"),
    /**
     * tegen over.
     */
    TO("to", "tegen over");

    private final String vasteWaarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param vasteWaarde VasteWaarde voor LocatieTenOpzichteVanAdres
     * @param omschrijving Omschrijving voor LocatieTenOpzichteVanAdres
     */
    private LocatieTenOpzichteVanAdres(final String vasteWaarde, final String omschrijving) {
        this.vasteWaarde = vasteWaarde;
        this.omschrijving = omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVasteWaarde() {
        return vasteWaarde;
    }

    /**
     * Retourneert omschrijving. voor LocatieTenOpzichteVanAdres
     *
     * @return omschrijving voor LocatieTenOpzichteVanAdres
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s - %s", vasteWaarde, omschrijving);
    }

}
