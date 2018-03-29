/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.element.AttribuutElement;

/**
 * ZoekCriterium.
 */
public final class ZoekCriterium {
    private final AttribuutElement element;
    private final Object waarde;
    private final Zoekoptie zoekOptie;
    private ZoekCriterium additioneel;
    private ZoekCriterium of;


    /**
     * Instantiates a new Zoek criterium.
     * @param element element
     * @param zoekOptie zoekOptie
     * @param waarde waarde
     */
    public ZoekCriterium(final AttribuutElement element, final Zoekoptie zoekOptie, final Object waarde) {
        this.element = element;
        this.waarde = waarde;
        this.zoekOptie = zoekOptie;
    }

    /**
     * Instantiates a new Zoek criterium.
     * @param element element
     * @param zoekOptie zoekOptie
     * @param waarde waarde
     * @param of of
     */
    public ZoekCriterium(final AttribuutElement element, final Zoekoptie zoekOptie, final Object waarde, final ZoekCriterium of) {
        this(element, zoekOptie, waarde);
        this.of = of;
    }

    /**
     * Gets element.
     * @return the element
     */
    public AttribuutElement getElement() {
        return element;
    }

    /**
     * Gets waarde.
     * @return the waarde
     */
    public Object getWaarde() {
        return waarde;
    }

    /**
     * Gets zoek optie.
     * @return the zoek optie
     */
    public Zoekoptie getZoekOptie() {
        return zoekOptie;
    }

    /**
     * Gets of.
     * @return the of
     */
    public ZoekCriterium getOf() {
        return of;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ZoekCriterium that = (ZoekCriterium) o;

        return !(element != null ? !element.equals(that.element) : that.element != null);

    }

    public ZoekCriterium getAdditioneel() {
        return additioneel;
    }

    public void setAdditioneel(ZoekCriterium additioneel) {
        this.additioneel = additioneel;
    }

    @Override
    public int hashCode() {
        return element != null ? element.hashCode() : 0;
    }
}
