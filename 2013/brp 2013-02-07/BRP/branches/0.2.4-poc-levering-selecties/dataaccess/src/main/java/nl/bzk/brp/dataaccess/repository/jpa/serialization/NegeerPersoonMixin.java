/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;

/**
 * Mapping die persoon/betrokkene negeert.
 */
public abstract class NegeerPersoonMixin implements GebruikAttributenMixin {
    @JsonIgnore
    private PersoonModel persoon;

    @JsonIgnore
    private PersoonModel betrokkene;
}
