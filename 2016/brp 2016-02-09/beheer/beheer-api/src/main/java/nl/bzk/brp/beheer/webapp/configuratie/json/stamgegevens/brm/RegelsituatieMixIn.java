/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.brm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractEnumConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.EnumSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.RegelSoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.Regeleffect;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;

/**
 * Mix-in voor {@link nl.bzk.brp.model.algemeen.stamgegeven.brm.Regelsituatie}.
 */
public interface RegelsituatieMixIn extends StamgegevenMixIn {
    /**
     * @return ID.
     */
    @JsonProperty
    Integer getID();

    /**
     * @return Regel \ Soort bericht.
     */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = RegelSoortBerichtConverter.class)
    RegelSoortBericht getRegelSoortBericht();

    /**
     * @return Bijhoudingsaard.
     */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = BijhoudingsaardConverter.class)
    Bijhoudingsaard getBijhoudingsaard();

    /**
     * @return Nadere bijhoudingsaard.
     */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = NadereBijhoudingsaardConverter.class)
    NadereBijhoudingsaard getNadereBijhoudingsaard();

    /**
     * @return Effect.
     */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = RegeleffectConverter.class)
    Regeleffect getEffect();

    /**
     * @return Actief?.
     */
    @JsonProperty
    JaNeeAttribuut getIndicatieActief();

    /**
     * RegelSoortBericht converter.
     */
    class RegelSoortBerichtConverter extends AbstractEnumConverter<RegelSoortBericht> {
        /**
         * Constructor.
         */
        protected RegelSoortBerichtConverter() {
            super(RegelSoortBericht.class);
        }
    }

    /**
     * Bijhoudingsaard converter.
     */
    class BijhoudingsaardConverter extends AbstractEnumConverter<Bijhoudingsaard> {
        /**
         * Constructor.
         */
        protected BijhoudingsaardConverter() {
            super(Bijhoudingsaard.class);
        }
    }

    /**
     * NadereBijhoudingsaard converter.
     */
    class NadereBijhoudingsaardConverter extends AbstractEnumConverter<NadereBijhoudingsaard> {
        /**
         * Constructor.
         */
        protected NadereBijhoudingsaardConverter() {
            super(NadereBijhoudingsaard.class);
        }
    }

    /**
     * Regeleffect converter.
     */
    class RegeleffectConverter extends AbstractEnumConverter<Regeleffect> {
        /**
         * Constructor.
         */
        protected RegeleffectConverter() {
            super(Regeleffect.class);
        }
    }
}
