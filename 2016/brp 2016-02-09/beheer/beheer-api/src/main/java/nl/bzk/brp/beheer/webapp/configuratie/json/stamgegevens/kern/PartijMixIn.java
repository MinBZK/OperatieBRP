/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.DatumAttribuutConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.DatumEvtDeelsOnbekendAttribuutConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.JaNeeAttribuutConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.DatumAttribuutSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.DatumEvtDeelsOnbekendAttribuutSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.JaNeeAttribuutSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.beheer.kern.SoortPartij;

/**
 * Mix-in for {@link nl.bzk.brp.model.beheer.kern.Partij}.
 */
public interface PartijMixIn extends StamgegevenMixIn {

    /**
     * @return code
     */
    @JsonProperty("Code")
    @JsonSerialize(using = PartijCodeAttribuutSerializer.class)
    @JsonDeserialize(converter = PartijCodeAttribuutConverter.class)
    PartijCodeAttribuut getCode();

    /**
     * @return datum aanvang
     */
    @JsonProperty("Datum ingang")
    @JsonSerialize(using = DatumEvtDeelsOnbekendAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumEvtDeelsOnbekendAttribuutConverter.class)
    DatumEvtDeelsOnbekendAttribuut getDatumIngang();

    /**
     * @return datum aanvang
     */
    @JsonProperty("Datum einde")
    @JsonSerialize(using = DatumEvtDeelsOnbekendAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumEvtDeelsOnbekendAttribuutConverter.class)
    DatumEvtDeelsOnbekendAttribuut getDatumEinde();

    /**
     * @return oin
     */
    @JsonProperty("OIN")
    @JsonSerialize(using = OINAttribuutSerializer.class)
    @JsonDeserialize(converter = OINAttribuutConverter.class)
    OINAttribuut getOIN();

    /**
     * @return soort
     */
    @JsonProperty("Soort")
    @JsonSerialize(using = SoortPartijSerializer.class)
    @JsonDeserialize(converter = SoortPartijConverter.class)
    SoortPartij getSoort();

    /**
     * @return indicatie verstrekkingsbeperking mogelijk
     */
    @JsonProperty("Verstrekkingsbeperking mogelijk?")
    @JsonSerialize(using = JaNeeAttribuutSerializer.class)
    @JsonDeserialize(converter = JaNeeAttribuutConverter.class)
    JaNeeAttribuut getIndicatieVerstrekkingsbeperkingMogelijk();

    /**
     * @return automatisch fiatteren
     */
    @JsonProperty("Automatisch fiatteren?")
    @JsonSerialize(using = JaNeeAttribuutSerializer.class)
    @JsonDeserialize(converter = JaNeeAttribuutConverter.class)
    JaNeeAttribuut getIndicatieAutomatischFiatteren();

    /**
     * @return datum overgang naar BRP
     */
    @JsonProperty("Datum overgang naar BRP")
    @JsonSerialize(using = DatumAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumAttribuutConverter.class)
    DatumAttribuut getDatumOvergangNaarBRP();

    /**
     * PartijCodeAttribuut serializer.
     */
    class PartijCodeAttribuutSerializer extends JsonSerializer<PartijCodeAttribuut> {

        @Override
        public void serialize(final PartijCodeAttribuut value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
            jgen.writeObject(value.getWaarde());
        }
    }

    /**
     * PartijCodeAttribuut converter.
     */
    class PartijCodeAttribuutConverter extends AbstractConverter<Integer, PartijCodeAttribuut> {

        @Override
        public PartijCodeAttribuut convert(final Integer value) {
            return new PartijCodeAttribuut(value);
        }
    }

    /**
     * SoortPartij serializer.
     */
    class SoortPartijSerializer extends JsonSerializer<SoortPartij> {

        @Override
        public void serialize(final SoortPartij value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
            jgen.writeObject(value.getID());
        }
    }

    /**
     * SoortPartij converter.
     */
    class SoortPartijConverter extends AbstractConverter<Integer, SoortPartij> {

        @Override
        public SoortPartij convert(final Integer value) {
            final SoortPartij result = new SoortPartij();
            result.setID(value.shortValue());
            return result;
        }
    }

    /**
     * OINAttribuut serializer.
     */
    class OINAttribuutSerializer extends JsonSerializer<OINAttribuut> {

        @Override
        public void serialize(final OINAttribuut value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
            jgen.writeObject(value.getWaarde());
        }
    }

    /**
     * OINAttribuut converter.
     */
    class OINAttribuutConverter extends AbstractConverter<String, OINAttribuut> {

        @Override
        public OINAttribuut convert(final String value) {
            return new OINAttribuut(value);
        }
    }
}
