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
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.beheer.webapp.view.ActieDetailView;
import org.springframework.stereotype.Component;

/**
 * Serializer voor detail view van actie.
 */
@Component
public class ActieDetailViewSerializer extends JsonSerializer<ActieDetailView> {


    private final ObjectSerializer objectSerializer;
    private final AttribuutSerializer attribuutSerializer;

    /**
     * Contructor.
     * @param objectSerializer object serializer
     * @param attribuutSerializer attribuut serializer
     */
    @Inject
    public ActieDetailViewSerializer(final ObjectSerializer objectSerializer, final AttribuutSerializer attribuutSerializer) {
        this.objectSerializer = objectSerializer;
        this.attribuutSerializer = attribuutSerializer;
    }

    @Override
    public final void serialize(final ActieDetailView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, "id", value.getActie().getId());
        attribuutSerializer.writeAttribuut(Element.ACTIE_SOORTNAAM, value.getActie().getSoortActie().getId(), jgen);
        attribuutSerializer.writeAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE, value.getActie().getDatumTijdRegistratie(), jgen);
        attribuutSerializer.writeAttribuut(Element.ACTIE_DATUMONTLENING, value.getActie().getDatumOntlening(), jgen);

        objectSerializer.writeObjecten(Element.PERSOON, value.getPersonen(), jgen);
        objectSerializer.writeObjecten(Element.RELATIE, value.getRelaties(), jgen);
        objectSerializer.writeObjecten(Element.ONDERZOEK, value.getOnderzoeken(), jgen);

        jgen.writeEndObject();
    }
}
