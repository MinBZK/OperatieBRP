/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import nl.bzk.brp.model.beheer.autaut.Dienstbundel;
import nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek;
import nl.bzk.brp.model.beheer.conv.ConversieLO3Rubriek;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek}. Wordt via
 * de {@link nl.bzk.brp.beheer.webapp.configuratie.json.modules.DienstbundelLO3RubriekModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
public class DienstbundelLO3RubriekDeserializer extends JsonDeserializer<DienstbundelLO3Rubriek> {

    @Override
    public final DienstbundelLO3Rubriek deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);
        final DienstbundelLO3Rubriek dienstbundelLO3Rubriek = new DienstbundelLO3Rubriek();
        dienstbundelLO3Rubriek.setID(JsonUtils.getAsInteger(node, DienstbundelLO3RubriekModule.ID));

        final Dienstbundel dienstbundel = new Dienstbundel();
        dienstbundel.setID(JsonUtils.getAsInteger(node, DienstbundelLO3RubriekModule.DIENSTBUNDEL));
        if (dienstbundel.getID() != null) {
            dienstbundelLO3Rubriek.setDienstbundel(dienstbundel);
        }

        final ConversieLO3Rubriek rubriek = new ConversieLO3Rubriek();
        rubriek.setID(JsonUtils.getAsInteger(node, DienstbundelLO3RubriekModule.RUBRIEK));
        if (rubriek.getID() != null) {
            dienstbundelLO3Rubriek.setRubriek(rubriek);
        }
        return dienstbundelLO3Rubriek;
    }
}
