/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling;

/**
 * Wrapper class voor SoortAdministratieveHandeling, benodigd voor de 'lijst' van soort administratieve handelingen.
 */
public final class BijhoudingsautorisatieSoortAdministratieveHandelingView extends BijhoudingsautorisatieSoortAdministratieveHandeling {

    private static final long serialVersionUID = 1L;
    private Boolean actief;

    /**
     * Default constructor.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandeling De soortAdministratieveHandeling
     * @param actief                                              De indicatie of het attribuut actief is
     */
    public BijhoudingsautorisatieSoortAdministratieveHandelingView(
            final BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling,
            final Boolean actief) {
        super(bijhoudingsautorisatieSoortAdministratieveHandeling.getBijhoudingsautorisatie(),
                bijhoudingsautorisatieSoortAdministratieveHandeling.getSoortAdministratievehandeling());
        setId(bijhoudingsautorisatieSoortAdministratieveHandeling.getId());
        this.actief = actief;
    }

    /**
     * Zet de actief status.
     *
     * @param actief Indicatie of deze soortAdministratieveHandeling actief is.
     */
    public void setActief(final Boolean actief) {
        this.actief = actief;
    }

    /**
     * Geeft de actief status.
     *
     * @return Indicatie of deze soortAdministratieveHandeling actief is.
     */
    public Boolean isActief() {
        return this.actief;
    }

    /**
     * Convenience methode voor conversie naar JPA entity.
     *
     * @return De JPA entity
     */
    public BijhoudingsautorisatieSoortAdministratieveHandeling getEntityObject() {
        final BijhoudingsautorisatieSoortAdministratieveHandeling resultaat =
                new BijhoudingsautorisatieSoortAdministratieveHandeling(getBijhoudingsautorisatie(), getSoortAdministratievehandeling());
        resultaat.setId(getId());
        return resultaat;
    }
}
