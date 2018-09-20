/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.basis.AbstractAttribuut;
import org.hibernate.annotations.Type;


/**
 * Attribuut wrapper klasse voor StatusHistorie.
 */
@Embeddable
@Access(value = AccessType.PROPERTY)
public class StatusHistorieAttribuut extends AbstractAttribuut<StatusHistorie> {

    /**
     * Lege private constructor voor StatusHistorieAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private StatusHistorieAttribuut() {
        super(null);
    }

    /**
     * Constructor voor StatusHistorieAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    public StatusHistorieAttribuut(final StatusHistorie waarde) {
        super(waarde);
    }

    /**
     * Retourneert de waarde van het attribuut. Bevat de specifieke configuratie voor het soort gewrapte object.
     *
     * @return de waarde van het attribuut
     */
    @Override
    @JsonProperty
    @Type(type = "StatusHistorie")
    public StatusHistorie getWaarde() {
        return super.getWaarde();
    }

}
