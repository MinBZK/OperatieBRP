/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.BooleanStringDeserializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.BooleanStringSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.JaAttribuutStringDeserializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.JaAttribuutStringSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamEnumMixIn;

/**
 * Mix-in for {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij}.
 */
public abstract class AbstractPartijMixIn extends AbstractIdNaamEnumMixIn {

    /**
     * @return code
     */
    @JsonProperty("code")
    abstract int getCode();

    /**
     * @return datum aanvang
     */
    @JsonProperty("datumIngang")
    abstract Integer getDatumIngang();

    /**
     * @return datum aanvang
     */
    @JsonProperty("datumEinde")
    abstract Integer getDatumEinde();

    /**
     * @return oin
     */
    @JsonProperty("oin")
    abstract String getOin();

    /**
     * @return soort
     */
    @JsonProperty("soort")
    @JsonSerialize(using = SoortPartijSerializer.class)
    @JsonDeserialize(converter = SoortPartijConverter.class)
    abstract SoortPartij getSoortPartij();

    /**
     * @return indicatie verstrekkingsbeperking mogelijk
     */
    @JsonProperty("verstrekkingsbeperkingMogelijk")
    @JsonSerialize(using = BooleanStringSerializer.class)
    @JsonDeserialize(using = BooleanStringDeserializer.class)
    abstract Boolean isIndicatieVerstrekkingsbeperkingMogelijk();

    /**
     * @return automatisch fiatteren
     */
    @JsonProperty("automatischFiatteren")
    @JsonSerialize(using = BooleanStringSerializer.class)
    @JsonDeserialize(using = BooleanStringDeserializer.class)
    abstract Boolean getIndicatieAutomatischFiatteren();

    /**
     * @return datum overgang naar BRP
     */
    @JsonProperty("datumOvergangNaarBrp")
    abstract Integer getDatumOvergangNaarBrp();

    /**
     * @return soort
     */
    @JsonProperty("ondertekenaarVrijBericht")
    @JsonSerialize(using = VrijBerichtPartijSerializer.class)
    @JsonDeserialize(converter = VrijBerichtPartijConverter.class)
    abstract Partij getOndertekenaarVrijBericht();

    /**
     * @return soort
     */
    @JsonProperty("transporteurVrijBericht")
    @JsonSerialize(using = VrijBerichtPartijSerializer.class)
    @JsonDeserialize(converter = VrijBerichtPartijConverter.class)
    abstract Partij getTransporteurVrijBericht();

    /**
     * @return datum ingang vrij bericht
     */
    @JsonProperty("datumIngangVrijBericht")
    abstract Integer getDatumIngangVrijBericht();

    /**
     * @return datum einde vrij bericht
     */
    @JsonProperty("datumEindeVrijBericht")
    abstract Integer getDatumEindeVrijBericht();

    /**
     * @return afleverpunt vrij bericht
     */
    @JsonProperty("afleverpuntVrijBericht")
    abstract String getAfleverpuntVrijBericht();

    /**
     * @return vrij bericht geblokkeerd ?
     */
    @JsonProperty("isVrijBerichtGeblokkeerd")
    @JsonSerialize(using = JaAttribuutStringSerializer.class)
    @JsonDeserialize(using = JaAttribuutStringDeserializer.class)
    abstract Boolean isVrijBerichtGeblokkeerd();

    /**
     * SoortPartij serializer.
     */
    private static class SoortPartijSerializer extends JsonSerializer<SoortPartij> {

        @Override
        public void serialize(final SoortPartij value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
            jgen.writeObject(value.getId());
        }
    }

    /**
     * SoortPartij converter.
     */
    private static class SoortPartijConverter extends AbstractIdConverter<SoortPartij> {

        /**
         * Constructor.
         */
        SoortPartijConverter() {
            super(SoortPartij.class);
        }
    }

    /**
     * VrijBericht partij serializer.
     */
    private static class VrijBerichtPartijSerializer extends JsonSerializer<Partij> {

        @Override
        public void serialize(final Partij value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
            jgen.writeObject(value.getId());
        }
    }

    /**
     * VrijBericht partij converter.
     */
    private static class VrijBerichtPartijConverter extends AbstractIdConverter<Partij> {

        /**
         * Constructor.
         */
        VrijBerichtPartijConverter() {
            super(Partij.class);
        }
    }

}
