/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import nl.bzk.brp.model.basis.AbstractAttribuut;
import org.hibernate.annotations.Type;

/**
 * Attribuut wrapper klasse voor LO3 aanduiding Ouder.
 *
 */
@Embeddable
@Access(value = AccessType.PROPERTY)
@Generated(value = "nl.bzk.brp.generatoren.java.AttribuutTypenGenerator")
public class LO3AanduidingOuderAttribuut extends AbstractAttribuut<LO3AanduidingOuder> {

    /**
     * Lege private constructor voor LO3AanduidingOuderAttribuut, om te voorkomen dat wrappers zonder waarde worden
     * geïnstantieerd.
     *
     */
    private LO3AanduidingOuderAttribuut() {
        super(null);
    }

    /**
     * Constructor voor LO3AanduidingOuderAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    public LO3AanduidingOuderAttribuut(final LO3AanduidingOuder waarde) {
        super(waarde);
    }

    /**
     * Retourneert de waarde van het attribuut. Bevat de specifieke configuratie voor het soort gewrapte object.
     *
     * @return de waarde van het attribuut
     */
    @Override
    @JsonProperty
    @Type(type = "LO3AanduidingOuder")
    public LO3AanduidingOuder getWaarde() {
        return super.getWaarde();
    }

}
