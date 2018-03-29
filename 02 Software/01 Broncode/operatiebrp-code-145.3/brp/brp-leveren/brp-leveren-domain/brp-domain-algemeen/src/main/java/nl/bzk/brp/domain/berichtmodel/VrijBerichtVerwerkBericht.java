/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

/**
 * VrijBerichtVerwerkBericht.
 */
public final class VrijBerichtVerwerkBericht extends Bericht {
    private BerichtVrijBericht berichtVrijBericht;
    private VrijBerichtParameters vrijBerichtParameters;

    /**
     * Constructor.
     * @param basisBerichtGegevens de basis berichtgegevens
     * @param berichtVrijBericht de specifieke berichtgegevens
     * @param vrijBerichtParameters de vrij bericht parameters
     */
    public VrijBerichtVerwerkBericht(final BasisBerichtGegevens basisBerichtGegevens,
                                     final BerichtVrijBericht berichtVrijBericht,
                                     final VrijBerichtParameters vrijBerichtParameters) {
        super(basisBerichtGegevens);
        this.berichtVrijBericht = berichtVrijBericht;
        this.vrijBerichtParameters = vrijBerichtParameters;
    }

    public BerichtVrijBericht getBerichtVrijBericht() {
        return berichtVrijBericht;
    }

    public VrijBerichtParameters getVrijBerichtParameters() {
        return vrijBerichtParameters;
    }

}

