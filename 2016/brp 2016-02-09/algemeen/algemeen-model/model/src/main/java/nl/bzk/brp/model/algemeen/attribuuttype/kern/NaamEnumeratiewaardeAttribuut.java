/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;


/**
 * Attribuut wrapper klasse voor Naam enumeratiewaarde.
 */
@Embeddable
public class NaamEnumeratiewaardeAttribuut extends AbstractNaamEnumeratiewaardeAttribuut {

    /**
     * constanten voor soort document naam.
     */
    public static final NaamEnumeratiewaardeAttribuut DOCUMENT_NAAM_KONINKLIJK_BESLUIT = new NaamEnumeratiewaardeAttribuut(
        "Koninklijk besluit");

    /**
     * Ministerieel besluit.
     */
    public static final NaamEnumeratiewaardeAttribuut DOCUMENT_NAAM_MINISTERIEEL_BESLUIT = new NaamEnumeratiewaardeAttribuut(
        "Ministerieel besluit");

    /**
     * constanten voor soort document naam.
     */
    public static final NaamEnumeratiewaardeAttribuut DOCUMENT_NAAM_AMBTSHALVE_BESLUIT_VERBLIJFPLAATS = new NaamEnumeratiewaardeAttribuut(
        "Ambtshalve besluit met betrekking tot verblijfplaats");

    /**
     * Lege private constructor voor NaamEnumeratiewaardeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private NaamEnumeratiewaardeAttribuut() {
        super();
    }

    /**
     * Constructor voor NaamEnumeratiewaardeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public NaamEnumeratiewaardeAttribuut(final String waarde) {
        super(waarde);
    }

}
