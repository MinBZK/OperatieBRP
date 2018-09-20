/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.VasteAttribuutWaarde;

/**
 * Waarde van dit veld is ALTIJD gelijk aan JA.
 *
 * Het Ja datatype heeft, uitzonderlijk, in Postgres GEEN waardebeperking: daar is het namelijk geen char(1). Om die
 * reden is bij de regel (handmatig) de waarde "IN_OGM" op N gezet. De code houdt hier rekening mee, zodat er geen
 * waarderegel wordt geïmplementeerd in Postgres.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.AttribuutTypenGenerator")
public enum Ja implements VasteAttribuutWaarde<Boolean> {

    /**
     * Ja.
     */
    J(true, "Ja");

    private final Boolean vasteWaarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param vasteWaarde VasteWaarde voor Ja
     * @param omschrijving Omschrijving voor Ja
     */
    private Ja(final Boolean vasteWaarde, final String omschrijving) {
        this.vasteWaarde = vasteWaarde;
        this.omschrijving = omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getVasteWaarde() {
        return vasteWaarde;
    }

    /**
     * Retourneert omschrijving. voor Ja
     *
     * @return omschrijving voor Ja
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
