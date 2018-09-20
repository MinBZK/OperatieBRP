/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.bericht.ber.BijhoudingsplanBericht;

/**
 *
 *
 */
public class HandelingNotificatieBijhoudingsplanBericht extends AbstractHandelingNotificatieBijhoudingsplanBericht {

    private String code;

    private String naam;

    private String categorie;

    private BijhoudingsplanBericht bijhoudingsplan;

    public HandelingNotificatieBijhoudingsplanBericht() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(final String categorie) {
        this.categorie = categorie;
    }

    public BijhoudingsplanBericht getBijhoudingsplan() {
        return bijhoudingsplan;
    }

    public void setBijhoudingsplan(final BijhoudingsplanBericht bijhoudingsplan) {
        this.bijhoudingsplan = bijhoudingsplan;
    }

    @Override
    public String getObjecttype() {
        return super.getObjecttype();
    }

    @Override
    public void setObjecttype(final String objecttype) {
        super.setObjecttype(objecttype);
    }
}
