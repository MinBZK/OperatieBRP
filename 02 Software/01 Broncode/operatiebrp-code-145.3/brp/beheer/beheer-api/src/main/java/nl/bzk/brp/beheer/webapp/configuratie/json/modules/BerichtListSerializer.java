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
import java.text.SimpleDateFormat;

import nl.bzk.brp.beheer.webapp.view.BerichtListView;

import org.springframework.stereotype.Component;

/**
 * Bericht list serializer.
 */
@Component
public final class BerichtListSerializer extends JsonSerializer<BerichtListView> {

    private final SimpleDateFormat sdf = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);

    @Override
    public void serialize(final BerichtListView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        if (value.getBericht() != null) {
            JsonUtils.writeAsInteger(jgen, BerichtModule.ID, ObjectUtils.getWaarde(value, "bericht.id"));
            JsonUtils.writeAsInteger(jgen, BerichtModule.SOORT, ObjectUtils.getWaarde(value, "bericht.soortBericht.id"));
            JsonUtils.writeAsInteger(jgen, BerichtModule.ZENDENDE_PARTIJ, ObjectUtils.getWaarde(value, "bericht.zendendePartij"));
            JsonUtils.writeAsInteger(jgen, BerichtModule.ONTVANGENDE_PARTIJ, ObjectUtils.getWaarde(value, "bericht.ontvangendePartij"));
            JsonUtils.writeAsString(jgen, BerichtModule.REFERENTIENUMMER, value.getBericht().getReferentieNummer());
            JsonUtils.writeAsString(jgen, BerichtModule.CROSS_REFERENTIENUMMER, value.getBericht().getCrossReferentieNummer());
            JsonUtils.writeAsString(jgen, BerichtModule.VERZENDDATUM, ObjectUtils.getGeformateerdeWaarde(value, "bericht.datumTijdVerzending", sdf));
            JsonUtils.writeAsString(jgen, BerichtModule.ONTVANGSTDATUM, ObjectUtils.getGeformateerdeWaarde(value, "bericht.datumTijdOntvangst", sdf));
            JsonUtils.writeAsInteger(jgen, BerichtModule.SOORT_SYNCHRONISATIE, ObjectUtils.getWaarde(value, "bericht.soortSynchronisatie.id"));
            JsonUtils.writeAsInteger(jgen, BerichtModule.VERWERKINGSWIJZE, ObjectUtils.getWaarde(value, "bericht.verwerkingswijze.id"));
            JsonUtils.writeAsInteger(jgen, BerichtModule.BIJHOUDING, ObjectUtils.getWaarde(value, "bericht.bijhoudingResultaat.id"));
        }
        jgen.writeEndObject();

    }
}
