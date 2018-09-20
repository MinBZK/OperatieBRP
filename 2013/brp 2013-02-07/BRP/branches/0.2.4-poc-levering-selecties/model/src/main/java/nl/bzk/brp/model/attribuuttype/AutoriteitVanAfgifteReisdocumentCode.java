/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import nl.bzk.brp.model.basis.AbstractStatischAttribuutType;


/**
 * Autoriteit van afgifte reisdocument code.
 */
@Embeddable
public final class AutoriteitVanAfgifteReisdocumentCode extends AbstractStatischAttribuutType<String> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private AutoriteitVanAfgifteReisdocumentCode() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     * @param waarde de waarde
     */
    @JsonCreator
    public AutoriteitVanAfgifteReisdocumentCode(final String waarde) {
        super(waarde);
    }
}
