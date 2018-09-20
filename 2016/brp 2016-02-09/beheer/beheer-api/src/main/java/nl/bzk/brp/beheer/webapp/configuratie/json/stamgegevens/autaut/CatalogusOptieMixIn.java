/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.autaut;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.AanduidingMedium;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;

/**
 * Mix-in voor {@link nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst}.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface CatalogusOptieMixIn {

    /** @return ordinal */
    @JsonProperty
    int ordinal();

    /** @return naam */
    @JsonProperty("naam")
    String toString();

    /** @return categorie dienst */
    @JsonProperty
    SoortDienst getCategorieDienst();

    /** @return effect afnemersindicaties */
    @JsonProperty
    EffectAfnemerindicaties getEffectAfnemerindicaties();

    /** @return aanduiding medium */
    @JsonProperty
    AanduidingMedium getAanduidingMedium();
}
