/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractEnumConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.DatumAttribuutConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.PartijIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.DatumAttribuutSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.EnumSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.beheer.kern.Partij;

/**
 * Mixin voor PartijRol.
 */
public interface PartijRolMixIn {

    /**
     * @return partij.
     */
    @JsonProperty("Partij")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = PartijIdConverter.class)
    Partij getPartij();

    /**
     * @return rol
     */
    @JsonProperty("Rol")
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = RolConverter.class)
    Rol getRol();

    /**
     * @return datum aanvang geldigheid
     */
    @JsonProperty("Datum ingang")
    @JsonSerialize(using = DatumAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumAttribuutConverter.class)
    DatumAttribuut getDatumIngang();

    /**
     * @return datum einde geldigheid
     */
    @JsonProperty("Datum einde")
    @JsonSerialize(using = DatumAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumAttribuutConverter.class)
    DatumAttribuut getDatumEinde();

    /**
     * Rol converter.
     */
    class RolConverter extends AbstractEnumConverter<Rol> {
        /**
         * Constructor.
         */
        protected RolConverter() {
            super(Rol.class);
        }
    }
}
