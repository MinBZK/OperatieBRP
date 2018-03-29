/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.migblok;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering;
import nl.bzk.brp.beheer.webapp.configuratie.json.modules.AttribuutSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.EnumSerializer;

/**
 * Mix-in for {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering}.
 */
public interface BlokkeringMixIn {

    /** @return id */
    @JsonProperty("id")
    Integer getId();

    /** @return aNummer */
    @JsonProperty("aNummer")
    Long getaNummer();

    /** @return redenBlokkering */
    @JsonProperty("redenBlokkering")
    @JsonSerialize(using = EnumSerializer.class)
    RedenBlokkering getRedenBlokkering();

    /** @return procesInstantieId */
    @JsonProperty("procesInstantieId")
    Long getProcessId();

    /** @return codeGemeenteVestiging */
    @JsonProperty("codeGemeenteVestiging")
    String getGemeenteCodeNaar();

    /** @return codeGemeenteRegistratie */
    @JsonProperty("codeGemeenteRegistratie")
    String getRegistratieGemeente();

    /** @return tijdstipRegistratie */
    @JsonProperty("tijdstipRegistratie")
    @JsonSerialize(using = TimestampSerializer.class)
    Timestamp getTijdstip();

    /**
     * Serializer voor timestamp.
     */
    class TimestampSerializer extends JsonSerializer<Timestamp> {

        @Override
        public void serialize(final Timestamp value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
            jgen.writeObject(AttribuutSerializer.formatDatum(value));
        }

    }

}
