/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.conv;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * Attribuut wrapper klasse voor Conversie LO3 Rubriek Naam.
 *
 */
@Embeddable
@Access(value = AccessType.PROPERTY)
public class ConversieLO3RubriekNaamAttribuut extends AbstractConversieLO3RubriekNaamAttribuut {

    /**
     * Lege private constructor voor ConversieLO3RubriekNaamAttribuut, om te voorkomen dat wrappers zonder waarde worden
     * geïnstantieerd.
     *
     */
    private ConversieLO3RubriekNaamAttribuut() {
        super();
    }

    /**
     * Constructor voor ConversieLO3RubriekNaamAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public ConversieLO3RubriekNaamAttribuut(final String waarde) {
        super(waarde);
    }

}
