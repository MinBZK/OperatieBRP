/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractAttribuutEnumConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractEnumConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.AttribuutEnumSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.EnumSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.ShortStamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRechtsgrond;

/**
 * Mix-in for {@link nl.bzk.brp.model.beheer.kern.Rechtsgrond}.
 */
public interface RechtsgrondMixIn extends ShortStamgegevenMixIn {

    /** @return soort */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = SoortRechtsgrondConverter.class)
    SoortRechtsgrond getSoort();

    /** @return indicatie leidt tot strijdigheid */
    @JsonProperty
    @JsonSerialize(using = AttribuutEnumSerializer.class)
    @JsonDeserialize(converter = JaAttribuutConverter.class)
    JaAttribuut getIndicatieLeidtTotStrijdigheid();

    /**
     * SoortRechtsgrond converter.
     */
    class SoortRechtsgrondConverter extends AbstractEnumConverter<SoortRechtsgrond> {
        /**
         * Constructor.
         */
        protected SoortRechtsgrondConverter() {
            super(SoortRechtsgrond.class);
        }
    }

    /**
     * JaAttribuut Converter.
     */
    class JaAttribuutConverter extends AbstractAttribuutEnumConverter<JaAttribuut> {

        /**
         * Constructor.
         */
        protected JaAttribuutConverter() {
            super(JaAttribuut.class, Ja.class);
        }
    }
}
