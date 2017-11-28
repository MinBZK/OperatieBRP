/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

/**
 * SelectieResultaatBericht.
 */
public final class SelectieResultaatBericht extends Bericht {

    private SelectieKenmerken selectieKenmerken;

    /**
     * Constructor.
     *
     * @param basisBerichtGegevens de basis berichtgegevens
     * @param selectieKenmerken de selectie kenmerken
     */
    public SelectieResultaatBericht(final BasisBerichtGegevens basisBerichtGegevens,
                                    final SelectieKenmerken selectieKenmerken) {
        super(basisBerichtGegevens);
        this.selectieKenmerken = selectieKenmerken;
    }

    public SelectieKenmerken getSelectieKenmerken() {
        return selectieKenmerken;
    }

}
