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
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.beheer.webapp.view.PersoonDetailView;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewGroep;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewObject;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewRecord;
import org.springframework.stereotype.Component;

/**
 * Maakt json object aan voor een PersoonListView object.
 */
@Component
public final class PersoonDetailSerializer extends JsonSerializer<PersoonDetailView> {

    private static final String VELD_TYPE = "$type";
    private final ObjectSerializer objectSerializer;
    private final AttribuutSerializer attribuutSerializer;

    /**
     * Constructor.
     * @param objectSerializer object serializer
     * @param attribuutSerializer attribuut serializer
     */
    @Inject
    public PersoonDetailSerializer(final ObjectSerializer objectSerializer, final AttribuutSerializer attribuutSerializer) {
        this.objectSerializer = objectSerializer;
        this.attribuutSerializer = attribuutSerializer;
    }

    @Override
    public void serialize(final PersoonDetailView persoonDetails, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        // Persoon
        objectSerializer.writeObject(persoonDetails.getPersoon(), jgen);

        // Afnemersindicaties
        writeAfnemersindicaties(persoonDetails.getAfnemerindicaties(), jgen);

        // Administratieve handelingen
        writeAdministratieveHandelingen(persoonDetails.getVerantwoording(), jgen);

        jgen.writeEndObject();

    }

    private void writeAdministratieveHandelingen(final List<BlobViewObject> administratieveHandelingen, final JsonGenerator jgen) throws IOException {
        if (administratieveHandelingen != null && !administratieveHandelingen.isEmpty()) {
            jgen.writeObjectFieldStart(Element.ADMINISTRATIEVEHANDELING.getElementNaam());
            jgen.writeStringField(VELD_TYPE, "tabelGroep");
            jgen.writeArrayFieldStart("kolomtitels");
            jgen.writeString("Technisch ID");
            jgen.writeString(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getElementNaam());
            jgen.writeString(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getElementNaam());
            jgen.writeString(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getElementNaam());
            jgen.writeString("Mutaties");
            jgen.writeString("$css");
            jgen.writeEndArray();
            jgen.writeArrayFieldStart("data");
            for (final BlobViewObject administratieveHandeling : administratieveHandelingen) {
                writeAdministratieveHandeling(administratieveHandeling, jgen);
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }

    private void writeAdministratieveHandeling(final BlobViewObject administratieveHandeling, final JsonGenerator jgen) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectFieldStart("Technisch ID");
        jgen.writeStringField(VELD_TYPE, "link");
        jgen.writeStringField("linkTekst", String.valueOf(administratieveHandeling.getObjectsleutel()));
        jgen.writeStringField("link", "administratievehandelingen/" + administratieveHandeling.getObjectsleutel());
        jgen.writeStringField("linkId", UUID.randomUUID().toString());
        jgen.writeEndObject();
        final BlobViewGroep identiteitGroep = administratieveHandeling.getGroep(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT);
        final BlobViewRecord identiteit = identiteitGroep.getRecords().iterator().next();

        attribuutSerializer.writeAttribuut(
                Element.ADMINISTRATIEVEHANDELING_SOORTNAAM,
                identiteit.getAttributen().get(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM),
                jgen);
        attribuutSerializer.writeAttribuut(
                Element.ADMINISTRATIEVEHANDELING_PARTIJCODE,
                identiteit.getAttributen().get(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE),
                jgen);
        attribuutSerializer.writeAttribuut(
                Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE,
                identiteit.getAttributen().get(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE),
                jgen);

        jgen.writeObjectFieldStart("Mutaties");
        jgen.writeStringField(VELD_TYPE, "cssLink");
        jgen.writeStringField("linkTekst", "Zie wijzigingen");
        jgen.writeStringField("link", "brp-markeer-" + administratieveHandeling.getObjectsleutel());
        jgen.writeEndObject();
        final StringBuilder cssBuilder = new StringBuilder("brp-markeer-");
        cssBuilder.append(administratieveHandeling.getObjectsleutel());
        jgen.writeStringField("$css", cssBuilder.toString());

        jgen.writeEndObject();
    }

    private void writeAfnemersindicaties(final List<BlobViewObject> geldendeAfnemerindicaties, final JsonGenerator jgen) throws IOException {
        if (geldendeAfnemerindicaties != null && !geldendeAfnemerindicaties.isEmpty()) {
            jgen.writeObjectFieldStart(Element.PERSOON_AFNEMERINDICATIE.getElementNaam());
            JsonUtils.writeAsString(jgen, VELD_TYPE, "groep");
            jgen.writeArrayFieldStart(Element.PERSOON_AFNEMERINDICATIE.getElementNaam());
            for (final BlobViewObject afnemersindicatie : geldendeAfnemerindicaties) {
                jgen.writeStartObject();
                objectSerializer.writeObject(afnemersindicatie, jgen);
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        }
    }
}
