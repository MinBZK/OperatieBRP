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
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import nl.bzk.brp.beheer.webapp.view.DienstbundelGroepAttribuutView;
import org.springframework.stereotype.Component;

/**
 * Dienst serializer.
 */
@Component
public class DienstbundelGroepAttribuutSerializer extends JsonSerializer<DienstbundelGroepAttribuutView> {

    @Override
    public final void serialize(final DienstbundelGroepAttribuutView value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, DienstbundelGroepAttribuutModule.ID, value.getId());
        JsonUtils.writeAsInteger(jgen, DienstbundelGroepAttribuutModule.DIENSTBUNDEL_GROEP, value.getDienstbundelGroep().getId());
        JsonUtils.writeAsString(
                jgen,
                DienstbundelGroepAttribuutModule.ACTIEF,
                value.isActief(),
                DienstbundelGroepAttribuutModule.WAARDE_JA,
                DienstbundelGroepAttribuutModule.WAARDE_NEE);
        JsonUtils.writeAsString(jgen, DienstbundelGroepAttribuutModule.ATTRIBUUT_NAAM, value.getAttribuut().getNaam());
        JsonUtils.writeAsInteger(jgen, DienstbundelGroepAttribuutModule.ATTRIBUUT, value.getAttribuut().getId());
        JsonUtils.writeAsInteger(jgen, DienstbundelGroepAttribuutModule.SOORT, EnumUtils.getId(value.getAttribuut().getSoortAutorisatie()));
        jgen.writeEndObject();
    }
}
