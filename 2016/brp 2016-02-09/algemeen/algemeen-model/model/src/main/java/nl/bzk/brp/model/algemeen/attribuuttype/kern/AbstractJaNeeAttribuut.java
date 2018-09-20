/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonValue;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.basis.AbstractAttribuut;

/**
 * Attribuut wrapper klasse voor Ja/Nee.
 *
 */
@Access(value = AccessType.PROPERTY)
@Generated(value = "nl.bzk.brp.generatoren.java.AttribuutTypenGenerator")
@MappedSuperclass
public abstract class AbstractJaNeeAttribuut extends AbstractAttribuut<Boolean> {

    /**
     * Lege private constructor voor JaNeeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     *
     */
    protected AbstractJaNeeAttribuut() {
        super(null);
    }

    /**
     * Constructor voor JaNeeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    public AbstractJaNeeAttribuut(final Boolean waarde) {
        super(waarde);
    }

    /**
     * Retourneert de waarde van het attribuut. Bevat de specifieke configuratie voor het soort gewrapte object.
     *
     * @return de waarde van het attribuut
     */
    @Override
    @JsonValue
    public Boolean getWaarde() {
        return super.getWaarde();
    }

}
