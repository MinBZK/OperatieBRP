/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.BurgerzakenModule;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.EnumSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamEnumMixIn;

/**
 * Mix-in for {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling}.
 */
public abstract class AbstractSoortAdministratieveHandelingMixIn extends AbstractIdNaamEnumMixIn {

    /** @return categorie administratieve handeling */
    @JsonProperty("categorieAdministratieveHandeling")
    @JsonSerialize(using = EnumSerializer.class)
    abstract Short getCategorie();

    /** @return burgerzaken module */
    @JsonProperty
    abstract BurgerzakenModule getModule();

    /** @return alias */
    @JsonProperty
    abstract String getAlias();

}
