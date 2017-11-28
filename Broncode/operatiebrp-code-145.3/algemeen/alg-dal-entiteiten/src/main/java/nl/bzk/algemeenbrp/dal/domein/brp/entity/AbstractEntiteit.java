/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;

/**
 * Abstracte class voor de {@link Entiteit} interface.
 */
public abstract class AbstractEntiteit implements Entiteit {

    /** Veldnaam van gegevenInOnderzoek tbv verschil verwerking. */
    public static final String GEGEVEN_IN_ONDERZOEK = "gegevenInOnderzoek";

    private final Map<Element, GegevenInOnderzoek> gegevenInOnderzoekPerElementMap;

    /**
     * Maakt een nieuw abstracte Entiteit.
     */
    //@SuppressWarnings("squid:S1640")
    /*sonar: EnumMap voor Element zeer geheugen inefficient: 12kb per map */
    public AbstractEntiteit() {
        super();
        gegevenInOnderzoekPerElementMap = new HashMap<>();
    }

    /**
     * Kopie constructor voor AbstractEntiteit.
     *
     * @param ander de andere historie waaruit gekopieerd moet worden
     */
    public AbstractEntiteit(final AbstractEntiteit ander) {
        super();
        gegevenInOnderzoekPerElementMap = ander.getGegevenInOnderzoekPerElementMap();
    }

    /**
     * Converteer een Integer naar een Long.
     *
     * @param i de Integer waarde. Mag null zijn.
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
     * @param l de Long waarde. Mag null zijn.
     * @return De Integer waarde, of null als de invoer null was.
     * @throws ClassCastException als de Long waarde niet in een Integer past.
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

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#setGegevenInOnderzoek(nl.bzk.
     * migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek)
     */
    @Override
    public void setGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        gegevenInOnderzoekPerElementMap.put(gegevenInOnderzoek.getSoortGegeven(), gegevenInOnderzoek);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getElementenInOnderzoek()
     */
    @Override
    public Collection<Element> getElementenInOnderzoek() {
        return gegevenInOnderzoekPerElementMap.keySet();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getGegevenInOnderzoekPerElementMap()
     */
    @Override
    public Map<Element, GegevenInOnderzoek> getGegevenInOnderzoekPerElementMap() {
        return new HashMap<>(gegevenInOnderzoekPerElementMap);
    }

    @Override
    public void verwijderGegevenInOnderzoek(final Element gegevenInOnderzoek) {
        gegevenInOnderzoekPerElementMap.remove(gegevenInOnderzoek);
    }

}
