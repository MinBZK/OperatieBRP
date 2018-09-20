/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.DatumEvtDeelsOnbekendAttribuutConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.DatumEvtDeelsOnbekendAttribuutSerializer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;

/**
 * Standaard mix-in voor stamgegevens aangezien deze veel dezelfde properties hebben (scheelt allerlei specifieke
 * mix-ins).
 */
public interface StamgegevenMixIn {

    /** @return naam */
    @JsonProperty("Naam")
    NaamEnumeratiewaardeAttribuut getNaam();

    /** @return omschrijving */
    @JsonProperty("Omschrijving")
    OmschrijvingEnumeratiewaardeAttribuut getOmschrijving();

    /** @return datum aanvang geldigheid */
    @JsonProperty("Datum ingang")
    @JsonSerialize(using = DatumEvtDeelsOnbekendAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumEvtDeelsOnbekendAttribuutConverter.class)
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid();

    /** @return datum einde geldigheid */
    @JsonProperty("Datum einde")
    @JsonSerialize(using = DatumEvtDeelsOnbekendAttribuutSerializer.class)
    @JsonDeserialize(converter = DatumEvtDeelsOnbekendAttribuutConverter.class)
    DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid();
}
