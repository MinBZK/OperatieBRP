/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import nl.bzk.brp.testdatageneratie.domain.kern.Gem;

public class AkteRegisterGemeenteCode extends Bron {

    private short gemeenteCode;
    private Gem   kernGemeente;

    public short getGemeenteCode() {
        return gemeenteCode;
    }

    public void setGemeenteCode(final short gemeenteCode) {
        this.gemeenteCode = gemeenteCode;
    }

    public Gem getKernGemeente() {
        return kernGemeente;
    }

    public void setKernGemeente(Gem kernGemeente) {
        this.kernGemeente = kernGemeente;
    }
}
