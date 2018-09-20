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
import nl.bzk.brp.beheer.webapp.view.PersoonListView;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;

/**
 * Maakt json object aan voor een PersoonListView object.
 */
public final class PersoonListSerializer extends JsonSerializer<PersoonListView> {

    @Override
    public void serialize(
            final PersoonListView value,
            final JsonGenerator jgen,
            final SerializerProvider provider)
        throws IOException
    {
        //
        jgen.writeStartObject();
        SerializerUtils.writeId(jgen, ObjectUtils.<Integer>getWaarde(value, "persoon.iD"));
        JsonUtils.writeAsString(jgen, ElementEnum.PERSOON_SOORTCODE.getElementNaam(),
                ObjectUtils.<String>getWaarde(value, "persoon.soort.waarde.naam"));
        JsonUtils.writeAsInteger(jgen, ElementEnum.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getElementNaam(),
                ObjectUtils.<Integer>getWaarde(value, "persoon.identificatienummers.burgerservicenummer.waarde"));
        JsonUtils.writeAsInteger(jgen, ElementEnum.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getElementNaam(),
                ObjectUtils.<Long>getWaarde(value, "persoon.identificatienummers.administratienummer.waarde"));

        JsonUtils.writeAsString(jgen, ElementEnum.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getElementNaam(),
                ObjectUtils.<String>getWaarde(value, "persoon.samengesteldeNaam.voornamen.waarde"));
        JsonUtils.writeAsString(jgen, ElementEnum.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getElementNaam(),
                ObjectUtils.<String>getWaarde(value, "persoon.samengesteldeNaam.geslachtsnaamstam.waarde"));
        JsonUtils.writeAsString(jgen, ElementEnum.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getElementNaam(),
                ObjectUtils.<String>getWaarde(value, "persoon.samengesteldeNaam.voorvoegsel.waarde"));
        JsonUtils.writeAsString(jgen, ElementEnum.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getElementNaam(),
                ObjectUtils.<String>getWaarde(value, "persoon.samengesteldeNaam.scheidingsteken.waarde"));

        JsonUtils.writeAsInteger(jgen, ElementEnum.PERSOON_GEBOORTE_DATUM.getElementNaam(), ObjectUtils.<Integer>getWaarde(value, "persoon.geboorte.datumGeboorte.waarde"));
        JsonUtils.writeAsString(jgen, ElementEnum.PERSOON_GESLACHTSAANDUIDING.getElementNaam(),
                ObjectUtils.<String>getWaarde(value, "persoon.geslachtsaanduiding.geslachtsaanduiding.waarde.naam"));

        jgen.writeEndObject();
    }

}
