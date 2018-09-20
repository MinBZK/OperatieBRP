/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.*;

import nl.bzk.brp.model.basis.AbstractAttribuut;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Attribuut wrapper klasse voor Soort partij.
 *
 */
@Embeddable
@Access(value = AccessType.PROPERTY)
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
public class SoortPartijAttribuut extends AbstractAttribuut<SoortPartij> {

    /**
     * Lege private constructor voor SoortPartijAttribuut, om te voorkomen dat wrappers zonder waarde worden
     * geïnstantieerd.
     *
     */
    private SoortPartijAttribuut() {
        super(null);
    }

    /**
     * Constructor voor SoortPartijAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    public SoortPartijAttribuut(final SoortPartij waarde) {
        super(waarde);
    }

    /**
     * Retourneert de waarde van het attribuut. Bevat de specifieke configuratie voor het soort gewrapte object.
     *
     * @return de waarde van het attribuut
     */
    @Override
    @JsonProperty
    @Enumerated //handmatige wijziging
    public SoortPartij getWaarde() {
        return super.getWaarde();
    }

}
