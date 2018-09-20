/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;

/**
 * Regel context voor regels die naar de huidige situatie 'kijken', oftewel de persoon in de BRP waar de regel
 * betrekking op heeft.
 */
public final class HuidigeSituatieRegelContext extends AbstractRegelContext {

    /**
     * De persoon zoals nu bekend in de BRP.
     */
    private final PersoonView huidigeSituatie;

    /**
     * Constructor.
     *
     * @param huidigeSituatie de huidige situatie zoals nu in de BRP van de persoon.
     */
    public HuidigeSituatieRegelContext(final PersoonView huidigeSituatie) {
        this.huidigeSituatie = huidigeSituatie;
    }

    /**
     * Getter voor de huidige situatie.
     * @return de huidige situatie van de persoon zoals nu bekend in de BRP.
     */
    public PersoonView getHuidigeSituatie() {
        return huidigeSituatie;
    }

    @Override
    public BrpObject getSituatie() {
        return huidigeSituatie;
    }
}
