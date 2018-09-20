/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;

/**
 * Mixin voor het (de)serialiseren van {@link nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij} instanties.
 */
public final class PartijMixin {
    @JsonProperty
    private SoortPartij soort;
}
