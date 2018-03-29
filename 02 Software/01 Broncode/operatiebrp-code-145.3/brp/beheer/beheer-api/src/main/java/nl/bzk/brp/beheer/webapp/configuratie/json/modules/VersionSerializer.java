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
import nl.bzk.algemeenbrp.util.common.Version;
import nl.bzk.algemeenbrp.util.common.Version.VersionLine;
import org.springframework.stereotype.Component;

/**
 * Versie serializer.
 */
@Component
public final class VersionSerializer extends JsonSerializer<Version> {

    @Override
    public void serialize(final Version value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        writeVersieRegel(jgen, value.getMainVersion());

        jgen.writeArrayFieldStart(VersieModule.COMPONENTEN);
        for (final VersionLine component : value.getComponentVersions()) {
            jgen.writeStartObject();
            writeVersieRegel(jgen, component);
            jgen.writeEndObject();
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }

    private void writeVersieRegel(final JsonGenerator jgen, final VersionLine versieRegel) throws IOException {
        JsonUtils.writeAsString(jgen, VersieModule.ARTIFACT, versieRegel.getArtifact());
        JsonUtils.writeAsString(jgen, VersieModule.GROUP, versieRegel.getGroup());
        JsonUtils.writeAsString(jgen, VersieModule.NAME, versieRegel.getName());
        JsonUtils.writeAsString(jgen, VersieModule.VERSIE, versieRegel.getVersion());
    }

}
