/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

/**
 * OnderhoudAfnemerindicatieAntwoordBericht.
 */
public final class OnderhoudAfnemerindicatieAntwoordBericht extends Bericht {

    private BerichtAfnemerindicatie berichtAfnemerindicatie;

    /**
     * Constructor.
     *
     * @param basisBerichtGegevens de basis berichtgegevens
     * @param berichtAfnemerindicatie de specifieke berichtgegevens
     */
    public OnderhoudAfnemerindicatieAntwoordBericht(BasisBerichtGegevens  basisBerichtGegevens, BerichtAfnemerindicatie berichtAfnemerindicatie) {
        super(basisBerichtGegevens);
        this.berichtAfnemerindicatie = berichtAfnemerindicatie;
    }

    public BerichtAfnemerindicatie getBerichtAfnemerindicatie() {
        return berichtAfnemerindicatie;
    }

}

