/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.verconv;

import javax.annotation.Generated;

import nl.bzk.brp.model.basis.VasteAttribuutWaarde;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.AttribuutTypenGenerator")
public enum LO3AanduidingOuder implements VasteAttribuutWaarde<Short> {

    /**
     * Ouder 1.
     */
    OUDER1((short) 1, "Ouder 1"),
    /**
     * Ouder 2.
     */
    OUDER2((short) 2, "Ouder 2");

    private final Short vasteWaarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param vasteWaarde VasteWaarde voor LO3AanduidingOuder
     * @param omschrijving Omschrijving voor LO3AanduidingOuder
     */
    private LO3AanduidingOuder(final Short vasteWaarde, final String omschrijving) {
        this.vasteWaarde = vasteWaarde;
        this.omschrijving = omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short getVasteWaarde() {
        return vasteWaarde;
    }

    /**
     * Retourneert omschrijving. voor LO3AanduidingOuder
     *
     * @return omschrijving voor LO3AanduidingOuder
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
