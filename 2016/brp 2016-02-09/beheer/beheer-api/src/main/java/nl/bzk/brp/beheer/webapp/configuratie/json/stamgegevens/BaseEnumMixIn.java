/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.DatumEvtDeelsOnbekendAttribuutConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.DatumEvtDeelsOnbekendAttribuutSerializer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;

/**
 * Basis enum mixin.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnumMixIn {
    /** @return ordinal */
    @JsonProperty
    int ordinal();

    /** @return naam */
    @JsonProperty
    String getNaam();

    /** @return omschrijving */
    @JsonProperty
    String getOmschrijving();

    /** @return datum aanvang geldigheid */
    @JsonProperty
    @JsonSerialize(using = DatumEvtDeelsOnbekendAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumEvtDeelsOnbekendAttribuutConverter.class)
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid();

    /** @return datum einde geldigheid */
    @JsonProperty
    @JsonSerialize(using = DatumEvtDeelsOnbekendAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumEvtDeelsOnbekendAttribuutConverter.class)
    DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid();
}
