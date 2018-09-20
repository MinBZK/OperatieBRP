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
import nl.bzk.brp.beheer.webapp.util.AttribuutUtils;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.SelectieperiodeInMaandenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AttenderingscriteriumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.beheer.autaut.Dienst;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;

/**
 * Dienst deserializer.
 */
public class DienstDeserializer extends JsonDeserializer<Dienst> {

    @Override
    public final Dienst deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Dienst dienst = new Dienst();
        dienst.setID(JsonUtils.getAsInteger(node, LeveringsautorisatieModule.ID));
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie();
        leveringsautorisatie.setID(JsonUtils.getAsInteger(node, LeveringsautorisatieModule.ABONNEMENT));
        dienst.setAttenderingscriterium(AttribuutUtils.getAsAttribuut(
                AttenderingscriteriumAttribuut.class,
                JsonUtils.getAsString(node, LeveringsautorisatieModule.ATTENDERINGSCRITERIUM)));
        dienst.setDatumIngang(AttribuutUtils.getAsAttribuut(DatumAttribuut.class, JsonUtils.getAsInteger(node, LeveringsautorisatieModule.DATUM_INGANG)));
        dienst.setDatumEinde(AttribuutUtils.getAsAttribuut(DatumAttribuut.class, JsonUtils.getAsInteger(node, LeveringsautorisatieModule.DATUM_EINDE)));
        dienst.setEersteSelectiedatum(AttribuutUtils.getAsAttribuut(
                DatumAttribuut.class,
                JsonUtils.getAsInteger(node, LeveringsautorisatieModule.EERSTE_SELECTIEDATUM)));
        dienst.setSelectieperiodeInMaanden(AttribuutUtils.getAsAttribuut(
                SelectieperiodeInMaandenAttribuut.class,
                JsonUtils.getAsShort(node, LeveringsautorisatieModule.SELECTIEPERIODE_IN_MAANDEN)));

        return dienst;
    }

}
