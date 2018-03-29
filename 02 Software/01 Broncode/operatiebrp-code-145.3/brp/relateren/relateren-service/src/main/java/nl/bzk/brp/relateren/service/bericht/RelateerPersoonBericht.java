/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.service.bericht;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simpele POJO waarin de persoon ids staan waarvoor gerelateerd moet worden. Deze class is geannoteerd middels JSON
 * properties zodat objecten voor deze class geserialiseerd en gedeserialiseerd kunnen worden.
 *
 * Deze class is immutable.
 */
public final class RelateerPersoonBericht {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final List<Integer> teRelaterenPersoonIds;

    /**
     * Maakt een nieuw RelateerPersoonBericht object.
     *
     * @param teRelaterenPersoonIds
     *            De lijst met te relateren personen ids.
     */
    @JsonCreator
    public RelateerPersoonBericht(@JsonProperty("teRelaterenPersoonIds") final List<Integer> teRelaterenPersoonIds) {
        if (teRelaterenPersoonIds == null || teRelaterenPersoonIds.isEmpty()) {
            throw new IllegalArgumentException("Er dient minimaal één persoon id meegegeven te worden.");
        }
        this.teRelaterenPersoonIds = new ArrayList<>(teRelaterenPersoonIds);
    }

    /**
     * @return bijgehouden personen ids
     */
    public List<Integer> getTeRelaterenPersoonIds() {
        return Collections.unmodifiableList(teRelaterenPersoonIds);
    }

    /**
     * Geeft de JSON string voor dit object, zie {@link ObjectMapper#writeValueAsString(Object)}.
     * 
     * @return de JSON string voor dit object.
     */
    public String writeValueAsString() {
        try {
            return JSON_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Kan niet serialiseren naar JSON string.", e);

        }
    }

    /**
     * Parsen van bericht in RelateerPersoonBericht, zie {@link ObjectMapper#readValue(String, Class)}.
     * 
     * @param bericht
     *            het JSON bericht als string, mag niet null zijn.
     * @return een RelateerPersoonBericht
     */
    public static RelateerPersoonBericht readValue(final String bericht) {
        try {
            return JSON_MAPPER.readValue(bericht, RelateerPersoonBericht.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Kan bericht niet deserialiseren naar RelateerPersoonBericht.", e);
        }
    }
}
