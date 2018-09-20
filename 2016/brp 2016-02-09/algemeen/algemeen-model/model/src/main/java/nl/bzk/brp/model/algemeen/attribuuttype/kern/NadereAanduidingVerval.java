/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.VasteAttribuutWaarde;

/**
 * Dit attribuuttype wordt gebruikt tijdens het genereren van het logisch model. Alle historische groepen krijgen een
 * attribuut Nadere aanduiding verval.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.AttribuutTypenGenerator")
public enum NadereAanduidingVerval implements VasteAttribuutWaarde<String> {

    /**
     * Onjuist.
     */
    O("O", "Onjuist"),
    /**
     * In strijd met openbare orde.
     */
    S("S", "In strijd met openbare orde");

    private final String vasteWaarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param vasteWaarde VasteWaarde voor NadereAanduidingVerval
     * @param omschrijving Omschrijving voor NadereAanduidingVerval
     */
    private NadereAanduidingVerval(final String vasteWaarde, final String omschrijving) {
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
     * Retourneert omschrijving. voor NadereAanduidingVerval
     *
     * @return omschrijving voor NadereAanduidingVerval
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
