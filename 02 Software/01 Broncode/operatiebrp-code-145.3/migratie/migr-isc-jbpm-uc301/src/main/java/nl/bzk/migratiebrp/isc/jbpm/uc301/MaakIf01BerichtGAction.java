/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import org.springframework.stereotype.Component;

/**
 * Maak een if01 bericht omdat er geen persoon is gevonden.
 */
@Component("uc301MaakIf01BerichtGAction")
public final class MaakIf01BerichtGAction extends AbstractMaakIf01BerichtAction {

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakIf01BerichtGAction(final BerichtenDao berichtenDao) {
        super(berichtenDao);
    }

    @Override
    protected void aanvullenIf01(final Map<String, Object> parameters, final If01Bericht if01Bericht) {
        if01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, "G");
    }

}
