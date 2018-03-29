/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * StatischePersoonGegevens.
 */
public final class StatischePersoongegevens {

    private Set<MetaRecord> deltaRecords = new HashSet<>();
    private Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();
    private Set<MetaRecord> preRelatieRecords = new HashSet<>();
    private Map<MetaObject, Integer> volgnummerMap = new HashMap<>();

    /**
     * Gets delta records.
     * @return the delta records
     */
    public Set<MetaRecord> getDeltaRecords() {
        return deltaRecords;
    }

    /**
     * Sets delta records.
     * @param deltaRecords the delta records
     */
    public void setDeltaRecords(final Set<MetaRecord> deltaRecords) {
        this.deltaRecords = deltaRecords;
    }

    /**
     * Gets verwerkingssoort map.
     * @return the verwerkingssoort map
     */
    public Map<MetaModel, Verwerkingssoort> getVerwerkingssoortMap() {
        return verwerkingssoortMap;
    }

    /**
     * Sets verwerkingssoort map.
     * @param verwerkingssoortMap the verwerkingssoort map
     */
    public void setVerwerkingssoortMap(
            final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap) {
        this.verwerkingssoortMap = verwerkingssoortMap;
    }

    /**
     * Gets pre relatie records.
     * @return the pre relatie records
     */
    public Set<MetaRecord> getPreRelatieRecords() {
        return preRelatieRecords;
    }

    /**
     * Sets pre relatie records.
     * @param preRelatieRecords the pre relatie records
     */
    public void setPreRelatieRecords(final Set<MetaRecord> preRelatieRecords) {
        this.preRelatieRecords = preRelatieRecords;
    }

    /**
     * Gets volgnummer map.
     * @return the volgnummer map
     */
    public Map<MetaObject, Integer> getVolgnummerMap() {
        return volgnummerMap;
    }

    /**
     * Sets volgnummer map.
     * @param volgnummerMap the volgnummer map
     */
    public void setVolgnummerMap(final Map<MetaObject, Integer> volgnummerMap) {
        this.volgnummerMap = volgnummerMap;
    }
}
