/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import nl.bzk.brp.beheer.webapp.view.BerichtListView;

/**
 * Bericht list serializer.
 */
public final class BerichtListSerializer extends JsonSerializer<BerichtListView> {

    private final SimpleDateFormat sdf = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);

    @Override
    public void serialize(final BerichtListView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        //
        jgen.writeStartObject();

        //
        JsonUtils.writeAsInteger(jgen, BerichtModule.ID, ObjectUtils.<Integer>getWaarde(value, "bericht.iD"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.SOORT, ObjectUtils.<Integer>getWaarde(value, "bericht.soort.waarde.ordinal"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.ZENDENDE_PARTIJ, ObjectUtils.<Short>getWaarde(value, "bericht.stuurgegevens.zendendePartijId"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.ONTVANGENDE_PARTIJ, ObjectUtils.<Short>getWaarde(value, "bericht.stuurgegevens.ontvangendePartijId"));
        JsonUtils.writeAsString(
            jgen,
            BerichtModule.REFERENTIENUMMER,
            ObjectUtils.<String>getWaarde(value, "bericht.stuurgegevens.referentienummer.waarde"));
        JsonUtils.writeAsString(
            jgen,
            BerichtModule.CROSS_REFERENTIENUMMER,
            ObjectUtils.<String>getWaarde(value, "bericht.stuurgegevens.crossReferentienummer.waarde"));
        JsonUtils.writeAsString(
            jgen,
            BerichtModule.VERZENDDATUM,
            ObjectUtils.getGeformateerdeWaarde(value, "bericht.stuurgegevens.datumTijdVerzending.waarde", sdf));
        JsonUtils.writeAsString(
            jgen,
            BerichtModule.ONTVANGSTDATUM,
            ObjectUtils.getGeformateerdeWaarde(value, "bericht.stuurgegevens.datumTijdOntvangst.waarde", sdf));
        JsonUtils.writeAsInteger(
            jgen,
            BerichtModule.SOORT_SYNCHRONISATIE,
            ObjectUtils.<Integer>getWaarde(value, "bericht.parameters.soortSynchronisatie.waarde.ordinal"));
        JsonUtils.writeAsInteger(
            jgen,
            BerichtModule.VERWERKINGSWIJZE,
            ObjectUtils.<Integer>getWaarde(value, "bericht.parameters.verwerkingswijze.waarde.ordinal"));
        JsonUtils.writeAsInteger(jgen, BerichtModule.BIJHOUDING, ObjectUtils.<Integer>getWaarde(value, "bericht.resultaat.bijhouding.waarde.ordinal"));

        //
        jgen.writeEndObject();

    }
}
