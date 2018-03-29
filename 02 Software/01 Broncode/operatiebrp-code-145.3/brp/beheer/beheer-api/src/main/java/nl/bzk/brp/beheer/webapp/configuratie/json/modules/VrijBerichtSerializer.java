/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.text.SimpleDateFormat;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBerichtPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import org.springframework.stereotype.Component;

/**
 * Maakt json object aan voor een VrijBerichtListView object.
 */
@Component
public final class VrijBerichtSerializer extends JsonSerializer<VrijBericht> {
    private static final String MEERDERE_PARTIJEN = "Meerdere partijen";
    private final SimpleDateFormat sdf = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);

    @Override
    public void serialize(final VrijBericht vrijBericht, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        JsonUtils.writeAsInteger(jgen, "id", ObjectUtils.getWaarde(vrijBericht, "id"));
        JsonUtils.writeAsInteger(jgen, "soortBericht", ObjectUtils.getWaarde(vrijBericht, "soortBerichtVrijBericht.id"));
        if (vrijBericht.getSoortBerichtVrijBericht() == SoortBerichtVrijBericht.VERWERK_VRIJ_BERICHT) {
            final Boolean indicatieGelezen = ObjectUtils.getWaarde(vrijBericht, "indicatieGelezen");
            boolean ongelezen = indicatieGelezen == null || !indicatieGelezen;
            JsonUtils.writeAsString(jgen, "ongelezen", ongelezen ? "Ja" : "Nee");
        }
        JsonUtils.writeAsString(jgen, "datumTijdRegistratie", ObjectUtils.getGeformateerdeWaarde(vrijBericht, "tijdstipRegistratie", sdf));
        if (vrijBericht.getVrijBerichtPartijen().size() == 1) {
            final VrijBerichtPartij partij = Iterables.getOnlyElement(vrijBericht.getVrijBerichtPartijen());
            JsonUtils.writeAsString(jgen, "partijCode", ObjectUtils.getWaarde(partij, "partij.code"));
            JsonUtils.writeAsString(jgen, "partijNaam", ObjectUtils.getWaarde(partij, "partij.naam"));
            JsonUtils.writeAsString(jgen, "soortPartij", ObjectUtils.getWaarde(partij, "partij.soortPartij.naam"));
        } else if (vrijBericht.getVrijBerichtPartijen().size() > 1) {
            JsonUtils.writeAsString(jgen, "partijCode", MEERDERE_PARTIJEN);
            JsonUtils.writeAsString(jgen, "partijNaam", MEERDERE_PARTIJEN);
            JsonUtils.writeAsString(jgen, "soortPartij", MEERDERE_PARTIJEN);
        }
        JsonUtils.writeAsInteger(jgen, "soortVrijBericht", ObjectUtils.getWaarde(vrijBericht, "soortVrijBericht.id"));
        JsonUtils.writeAsString(jgen, "data", ObjectUtils.getWaarde(vrijBericht, "data"));

        jgen.writeEndObject();
    }

}
