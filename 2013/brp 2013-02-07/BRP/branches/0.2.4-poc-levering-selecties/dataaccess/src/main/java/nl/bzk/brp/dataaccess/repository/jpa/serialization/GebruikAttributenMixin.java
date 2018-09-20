/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Mapping die specificeert dat de attributen worden gebruikt voor de mapping en niet de getter/setter methodes.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
                isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility
        .NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public interface GebruikAttributenMixin {
}
