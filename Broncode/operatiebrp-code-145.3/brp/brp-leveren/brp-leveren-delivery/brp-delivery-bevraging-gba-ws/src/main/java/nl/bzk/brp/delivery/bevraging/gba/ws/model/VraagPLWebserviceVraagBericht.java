/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.VraagPL;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Util klasse voor conversie/formattering van de webservice vraag.
 */
public class VraagPLWebserviceVraagBericht extends AbstractWebserviceVraagBericht {

    private final SoortDienst soortDienst;
    private final List<String> zoekRubrieken;
    private final List<Lo3CategorieWaarde> zoekCriteria;
    private List<String> lo3Rubrieken;

    /**
     * Default constructor.
     * @param vraag de initiele vraag
     */
    public VraagPLWebserviceVraagBericht(final VraagPL vraag) {
        super();
        this.soortDienst = SoortDienst.ZOEK_PERSOON;
        this.zoekRubrieken = bepaalZoekRubrieken(vraag.getParameters());
        this.zoekCriteria = bepaalZoekCriteria(vraag.getParameters());
    }

    @Override
    public List<String> getGevraagdeRubrieken() {
        if (lo3Rubrieken == null) {
            lo3Rubrieken = Collections.emptyList();
        }
        return lo3Rubrieken;
    }

    @Override
    public List<String> getZoekRubrieken() {
        return zoekRubrieken;
    }

    @Override
    public List<Lo3CategorieWaarde> getZoekCriteria() {
        return zoekCriteria;
    }

    @Override
    public SoortDienst getSoortDienst() {
        return soortDienst;
    }
}
