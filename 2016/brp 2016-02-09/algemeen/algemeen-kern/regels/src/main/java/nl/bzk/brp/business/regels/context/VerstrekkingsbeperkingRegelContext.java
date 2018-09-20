/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * De interface voor de context voor bedrijfsregels die betrekking hebben op verstrekkingsbeperkingen.
 */
public class VerstrekkingsbeperkingRegelContext extends AbstractRegelContext implements RegelContext {

    private final PersoonView huidigeSituatie;
    private final Partij      partij;

    /**
     * Instantieert een nieuwe autorisatie regel context.
     *
     * @param huidigeSituatie de huidige situatie als PersoonView.
     * @param partij          de partij.
     */
    public VerstrekkingsbeperkingRegelContext(final PersoonView huidigeSituatie, final Partij partij) {
        this.huidigeSituatie = huidigeSituatie;
        this.partij = partij;
    }

    public final PersoonView getHuidigeSituatie() {
        return huidigeSituatie;
    }

    public final Partij getPartij() {
        return partij;
    }

    @Override
    public final BrpObject getSituatie() {
        return huidigeSituatie;
    }
}
