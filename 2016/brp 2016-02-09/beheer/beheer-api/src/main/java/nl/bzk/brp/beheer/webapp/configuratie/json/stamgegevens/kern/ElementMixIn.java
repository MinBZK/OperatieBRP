/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StringStamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentifierLangAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElementAutorisatie;

/**
 * Mix-in for {@link nl.bzk.brp.model.algemeen.stamgegeven.kern.Element}.
 */
public interface ElementMixIn extends StringStamgegevenMixIn {

    /** @return id */
    @JsonProperty
    Integer getID();

    /** @return soort */
    @JsonProperty
    SoortElement getSoort();

    /** @return element naam */
    @JsonProperty
    NaamEnumeratiewaardeAttribuut getElementNaam();

    /** @return object type */
    @JsonProperty
    Element getObjecttype();

    /** @return groep */
    @JsonProperty
    Element getGroep();

    /** @return volgnummer */
    @JsonProperty
    VolgnummerAttribuut getVolgnummer();

    /** @return alias van */
    @JsonProperty
    Element getAliasVan();

    /** @return expressie */
    @JsonProperty
    ExpressietekstAttribuut getExpressie();

    /** @return autorisatie */
    @JsonProperty
    SoortElementAutorisatie getAutorisatie();

    /** @return tabel */
    Element getTabel();

    /** @return identificatie database */
    IdentifierLangAttribuut getIdentificatieDatabase();

    /** @return his tabel */
    @JsonProperty
    Element getHisTabel();

    /** @return his identificatie database */
    @JsonProperty
    IdentifierLangAttribuut getHisIdentifierDatabase();

    /** @return leveren als stamgegeven */
    @JsonProperty
    JaAttribuut getLeverenAlsStamgegeven();

}
