/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstracte class voor de DeltaEntiteit interface. Hierin wordt de gegenereerde {@link EntiteitSleutel} gemaakt en
 * opgeslagen voor de extending class.
 */
@SuppressWarnings("checkstyle:designforextension")
public abstract class AbstractDeltaEntiteit implements DeltaEntiteit {

    /** Veldnaam van gegevenInOnderzoek tbv verschil verwerking. */
    public static final String GEGEVEN_IN_ONDERZOEK = "gegevenInOnderzoek";

    private final Map<Element, GegevenInOnderzoek> gegevenInOnderzoekPerElementMap = new HashMap<>();

    /**
     * Converteer een Integer naar een Long.
     *
     * @param i
     *            de Integer waarde. Mag null zijn.
     * @return De Long waarde, of null als de invoer null was.
     */
    public final Long convertIntegerNaarLong(final Integer i) {
        if (i == null) {
            return null;
        } else {
            return i.longValue();
        }
    }

    /**
     * Converteer een Long naar een Integer.
     *
     * @param l
     *            de Long waarde. Mag null zijn.
     * @return De Integer waarde, of null als de invoer null was.
     * @throws ClassCastException
     *             als de Long waarde niet in een Integer past.
     */
    public final Integer convertLongNaarInteger(final Long l) {
        if (l == null) {
            return null;
        } else if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
            throw new ClassCastException("waarde is buiten bereik van Integer");
        } else {
            return l.intValue();
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public final void setGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        gegevenInOnderzoekPerElementMap.put(gegevenInOnderzoek.getSoortGegeven(), gegevenInOnderzoek);
    }

    @Override
    public final Collection<Element> getElementenInOnderzoek() {
        return gegevenInOnderzoekPerElementMap.keySet();
    }

    /**
     * Geef gegeven in onder per element.
     * 
     * @return map met gegevens in onderzoek per element
     */
    @Override
    public final Map<Element, GegevenInOnderzoek> getGegevenInOnderzoekPerElementMap() {
        return new HashMap<>(gegevenInOnderzoekPerElementMap);
    }

    @Override
    public final void removeGegevenInOnderzoek(final Element gegevenInOnderzoek) {
        gegevenInOnderzoekPerElementMap.remove(gegevenInOnderzoek);
    }
}
