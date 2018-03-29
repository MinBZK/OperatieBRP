/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.beheer.webapp.view.ActieListView;
import org.springframework.stereotype.Component;

/**
 * Actie list view serializer.
 */
@Component
public class ActieListViewSerializer extends JsonSerializer<ActieListView> {

    @Override
    public final void serialize(final ActieListView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, "id", value.getActie().getId());
        JsonUtils.writeAsInteger(jgen, Element.ACTIE_SOORTNAAM.getElementNaam(), value.getActie().getSoortActie().getId());

        JsonUtils.writeAsString(
                jgen,
                Element.ACTIE_TIJDSTIPREGISTRATIE.getElementNaam(),
                AttribuutSerializer.formatDatum(value.getActie().getDatumTijdRegistratie()));
        JsonUtils.writeAsString(
                jgen,
                Element.ACTIE_DATUMONTLENING.getElementNaam(),
                AttribuutSerializer.formatDatum(value.getActie().getDatumOntlening()));
        jgen.writeEndObject();
    }
}
