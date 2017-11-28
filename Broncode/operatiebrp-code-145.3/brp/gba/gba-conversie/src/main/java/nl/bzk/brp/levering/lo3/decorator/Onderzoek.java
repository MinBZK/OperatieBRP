/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.decorator;

import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaObject;

/**
 * Onderzoek.
 */
public final class Onderzoek extends ObjectDecorator {

    private final Persoon persoon;

    /**
     * Constructor.
     * @param persoon persoon
     * @param metaObject object
     */
    Onderzoek(final Persoon persoon, final MetaObject metaObject) {
        super(metaObject);
        this.persoon = persoon;
    }

    /**
     * Geef de persoon.
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef de standaard records.
     * @return standaard records.
     */
    public Collection<OnderzoekStandaardRecord> getStandaard() {
        return getRecords(ElementHelper.getGroepElement(Element.ONDERZOEK_STANDAARD.getId()), OnderzoekStandaardRecord::new);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef de gegevens in onderzoek.
     * @return gegevens in onderzoek
     */
    public Collection<GegevenInOnderzoek> getGegevensInOnderzoek() {
        return getObjecten(ElementHelper.getObjectElement(Element.GEGEVENINONDERZOEK.getId()), metaObject -> new GegevenInOnderzoek(this, metaObject));
    }

}
