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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.beheer.webapp.view.PersoonListView;

import org.springframework.stereotype.Component;

/**
 * Maakt json object aan voor een PersoonListView object.
 */
@Component
public final class PersoonListSerializer extends JsonSerializer<PersoonListView> {

    private static final String SCHEIDINGSTEKEN = "scheidingsteken";

    @Override
    public void serialize(final PersoonListView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        //
        jgen.writeStartObject();
        final Persoon persoon = value.getPersoon();

        JsonUtils.writeAsInteger(jgen, "id", persoon.getId());
        JsonUtils.writeAsString(jgen, Element.PERSOON_SOORTCODE.getElementNaam(), ObjectUtils.<String>getWaarde(persoon, "soortPersoon.naam"));
        JsonUtils.writeAsString(
            jgen,
            Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getElementNaam(),
            ObjectUtils.getWaarde(persoon, "burgerservicenummer"));
        JsonUtils.writeAsString(
            jgen,
            Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getElementNaam(),
            ObjectUtils.getWaarde(persoon, "administratienummer"));

        JsonUtils.writeAsString(jgen, Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getElementNaam(), ObjectUtils.<String>getWaarde(persoon, "voornamen"));
        JsonUtils.writeAsString(
            jgen,
            Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getElementNaam(),
            ObjectUtils.<String>getWaarde(persoon, "geslachtsnaamstam"));
        JsonUtils.writeAsString(
            jgen,
            Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getElementNaam(),
            ObjectUtils.<String>getWaarde(persoon, "voorvoegsel"));
        JsonUtils.writeAsString(
            jgen,
            Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getElementNaam(),
            ObjectUtils.<Character>getWaarde(persoon, SCHEIDINGSTEKEN) != null ? String.valueOf(ObjectUtils.<Character>getWaarde(persoon, SCHEIDINGSTEKEN))
                                                                               : null);

        JsonUtils.writeAsInteger(jgen, Element.PERSOON_GEBOORTE_DATUM.getElementNaam(), ObjectUtils.<Integer>getWaarde(persoon, "datumGeboorte"));
        JsonUtils.writeAsString(
            jgen,
            Element.PERSOON_GESLACHTSAANDUIDING.getElementNaam(),
            ObjectUtils.<String>getWaarde(persoon, "geslachtsaanduiding.naam"));

        jgen.writeEndObject();
    }

}
