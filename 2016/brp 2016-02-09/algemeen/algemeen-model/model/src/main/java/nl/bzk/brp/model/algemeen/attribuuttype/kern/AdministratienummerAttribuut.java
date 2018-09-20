/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;
import org.apache.commons.lang.StringUtils;


/**
 * Attribuut wrapper klasse voor Administratienummer.
 */
@Embeddable
public class AdministratienummerAttribuut extends AbstractAdministratienummerAttribuut {

    private static final int MAX_LENGTH = 10;

    /**
     * Lege private constructor voor AdministratienummerAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private AdministratienummerAttribuut() {
        super();
    }

    /**
     * Constructor voor AdministratienummerAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public AdministratienummerAttribuut(final Long waarde) {
        super(waarde);
    }

    /**
     * Extra constructor met een string als argument.
     *
     * @param waarde de waarde.
     */
    public AdministratienummerAttribuut(final String waarde) {
        super(Long.valueOf(waarde));
    }

    @Override
    public String toString() {
        String resultaat;
        if (this.getWaarde() == null) {
            resultaat = StringUtils.EMPTY;
        } else {
            resultaat = this.getWaarde().toString();
            resultaat = StringUtils.leftPad(resultaat, MAX_LENGTH, '0');
        }
        return resultaat;
    }

}
