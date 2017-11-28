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
import nl.bzk.brp.beheer.webapp.view.BerichtDetailView;
import org.springframework.stereotype.Component;

/**
 * Bericht detail serializer.
 */
@Component
public final class BerichtDetailSerializer extends JsonSerializer<BerichtDetailView> {

    private final SimpleDateFormat sdf = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);

    @Override
    public void serialize(final BerichtDetailView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (value.getBericht() != null) {

            schrijfBerichtStuurgegevens(jgen, value);
            schrijfBerichtVerwerking(jgen, value);
            schrijfBerichtOverigeMetadata(jgen, value);

            JsonUtils.writeAsString(jgen, BerichtModule.DATA, value.getBericht().getData());
        }
        jgen.writeEndObject();

    }

    private void schrijfBerichtStuurgegevens(final JsonGenerator jgen, final BerichtDetailView value) throws IOException {
        JsonUtils.writeAsInteger(jgen, BerichtModule.ID, ObjectUtils.getWaarde(value, "bericht.id"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.SOORT, ObjectUtils.getWaarde(value, "bericht.soortBericht.id"));
        JsonUtils.writeAsString(jgen, BerichtModule.SOORT_NAAM, ObjectUtils.getWaarde(value, "bericht.soortBericht.identifier"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.ZENDENDE_PARTIJ, ObjectUtils.getWaarde(value, "bericht.zendendePartij"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.ONTVANGENDE_PARTIJ, ObjectUtils.getWaarde(value, "bericht.ontvangendePartij"));
        JsonUtils.writeAsString(jgen, BerichtModule.REFERENTIENUMMER, value.getBericht().getReferentieNummer());
        JsonUtils.writeAsString(jgen, BerichtModule.CROSS_REFERENTIENUMMER, value.getBericht().getCrossReferentieNummer());
        JsonUtils.writeAsString(jgen, BerichtModule.VERZENDDATUM, ObjectUtils.getGeformateerdeWaarde(value, "bericht.datumTijdVerzending", sdf));
        JsonUtils.writeAsString(jgen, BerichtModule.ONTVANGSTDATUM, ObjectUtils.getGeformateerdeWaarde(value, "bericht.datumTijdOntvangst", sdf));
    }

    private void schrijfBerichtVerwerking(final JsonGenerator jgen, final BerichtDetailView value) throws IOException {
        JsonUtils.writeAsInteger(jgen, BerichtModule.SOORT_SYNCHRONISATIE, ObjectUtils.getWaarde(value, "bericht.soortSynchronisatie.id"));
        JsonUtils.writeAsString(jgen, BerichtModule.SOORT_SYNCHRONISATIE_NAAM, ObjectUtils.getWaarde(value, "bericht.soortSynchronisatie.naam"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.VERWERKINGSWIJZE, ObjectUtils.getWaarde(value, "bericht.verwerkingswijze.id"));
        JsonUtils.writeAsString(jgen, BerichtModule.VERWERKINGSWIJZE_NAAM, ObjectUtils.getWaarde(value, "bericht.verwerkingswijze.naam"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.BIJHOUDING, ObjectUtils.getWaarde(value, "bericht.bijhoudingResultaat.id"));
        JsonUtils.writeAsString(jgen, BerichtModule.BIJHOUDING_NAAM, ObjectUtils.getWaarde(value, "bericht.bijhoudingResultaat.naam"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.ADMINISTRATIEVE_HANDELING_ID, ObjectUtils.getWaarde(value, "administratieveHandeling.id"));
        JsonUtils.writeAsString(
                jgen,
                BerichtModule.ADMINISTRATIEVE_HANDELING_SOORT_NAAM,
                ObjectUtils.getWaarde(value, "administratieveHandeling.soort.naam"));
    }

    private void schrijfBerichtOverigeMetadata(final JsonGenerator jgen, final BerichtDetailView value) throws IOException {
        JsonUtils.writeAsInteger(jgen, BerichtModule.LEVERINGSAUTORISATIE, ObjectUtils.getWaarde(value, "bericht.leveringsAutorisatie"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.DIENST, ObjectUtils.getWaarde(value, "bericht.dienst"));

        JsonUtils.writeAsString(jgen, BerichtModule.RICHTING, ObjectUtils.getWaarde(value, "bericht.richting.naam"));
        JsonUtils.writeAsString(jgen, BerichtModule.ZENDENDE_SYSTEEM, ObjectUtils.getWaarde(value, "bericht.zendendeSysteem"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.VERWERKING, ObjectUtils.getWaarde(value, "bericht.verwerkingsResultaat.id"));
        JsonUtils.writeAsString(jgen, BerichtModule.VERWERKING_NAAM, ObjectUtils.getWaarde(value, "bericht.verwerkingsResultaat.naam"));
        JsonUtils.writeAsString(jgen, BerichtModule.HOOGSTE_MELDINGSNIVEAU, ObjectUtils.getWaarde(value, "bericht.hoogsteMeldingsNiveau.naam"));
    }
}
