/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractEnumConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.EnumSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat}.
 */
public abstract class AbstractAdellijkeTitelPredikaatMixIn extends AbstractStamgegevenMixIn {

    /**
     * @return rubriek 02.20 adellijke titel predikaat
     */
    @JsonProperty("rubriek0221AdellijkeTitelPredikaat")
    abstract String getLo3AdellijkeTitelPredikaat();

    /**
     * @return geslachtsaanduiding
     */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = GeslachtsaanduidingConverter.class)
    abstract Geslachtsaanduiding getGeslachtsaanduiding();

    /**
     * @return adellijke titel
     */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = AdellijkeTitelConverter.class)
    abstract AdellijkeTitel getAdellijkeTitel();

    /**
     * @return predicaat
     */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = PredicaatConverter.class)
    abstract Predicaat getPredikaat();

    /**
     * AdellijkeTitelPredikaat converter.
     */
    private static class AdellijkeTitelPredikaatConverter extends AbstractIdConverter<AdellijkeTitelPredikaat> {
        /**
         * Constructor.
         */
        protected AdellijkeTitelPredikaatConverter() {
            super(AdellijkeTitelPredikaat.class);
        }
    }

    /**
     * Geslachtsaanduiding converter.
     */
    private static class GeslachtsaanduidingConverter extends AbstractEnumConverter<Geslachtsaanduiding> {
        /**
         * Constructor.
         */
        protected GeslachtsaanduidingConverter() {
            super(Geslachtsaanduiding.class);
        }
    }

    /**
     * AdellijkeTitel converter.
     */
    private static class AdellijkeTitelConverter extends AbstractEnumConverter<AdellijkeTitel> {
        /**
         * Constructor.
         */
        AdellijkeTitelConverter() {
            super(AdellijkeTitel.class);
        }
    }

    /**
     * Predicaat converter.
     */
    private static class PredicaatConverter extends AbstractEnumConverter<Predicaat> {
        /**
         * Constructor.
         */
        PredicaatConverter() {
            super(Predicaat.class);
        }
    }
}
