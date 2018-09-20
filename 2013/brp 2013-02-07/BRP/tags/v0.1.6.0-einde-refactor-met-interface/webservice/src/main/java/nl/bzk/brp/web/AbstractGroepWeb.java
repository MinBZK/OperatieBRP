/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.IdentificerendeGroep;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;

/**
 * Basis klasse voor alle groepen.
 */
public abstract class AbstractGroepWeb implements Groep, IdentificerendeGroep {
    private String verzendendId;

    @Override
    public void setVerzendendId(final String verzendendId) {
        this.verzendendId = verzendendId;
    }

    @Override
    public String getVerzendendId() {
        return verzendendId;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        throw new UnsupportedOperationException(" Weblaag ondersteund de methode getStatusHistorie NIET.");
    }


}
