/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractEnumConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.EnumSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AdellijkeTitelPredikaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.beheer.kern.AdellijkeTitel;
import nl.bzk.brp.model.beheer.kern.Predicaat;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieAdellijkeTitelPredikaat}.
 */
public interface ConversieAdellijkeTitelPredikaatMixIn extends StamgegevenMixIn {

    /**
     * @return rubriek 02.20 adellijke titel predikaat
     */
    @JsonProperty
    LO3AdellijkeTitelPredikaatAttribuut getRubriek0221AdellijkeTitelPredikaat();

    /**
     * @return geslachtsaanduiding
     */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = GeslachtsaanduidingConverter.class)
    Geslachtsaanduiding getGeslachtsaanduiding();

    /**
     * @return adellijke titel
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = AdellijkeTitelConverter.class)
    AdellijkeTitel getAdellijkeTitel();

    /**
     * @return predicaat
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = PredicaatConverter.class)
    Predicaat getPredicaat();

    /**
     * Geslachtsaanduiding converter.
     */
    class GeslachtsaanduidingConverter extends AbstractEnumConverter<Geslachtsaanduiding> {
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
    class AdellijkeTitelConverter extends AbstractIdConverter<AdellijkeTitel> {
        /**
         * Constructor.
         */
        public AdellijkeTitelConverter() {
            super(AdellijkeTitel.class);
        }
    }

    /**
     * Predicaat converter.
     */
    class PredicaatConverter extends AbstractIdConverter<Predicaat> {
        /**
         * Constructor.
         */
        public PredicaatConverter() {
            super(Predicaat.class);
        }
    }
}
