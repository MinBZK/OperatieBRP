/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import nl.bzk.brp.model.validatie.constraint.Bsn;

/**
 * Base class voor een vraag.
 *
 */
public abstract class AbstractVraag {

    private String historieMaterieel;
    private String historieFormeel;
    private String aanschouwerBsn;

    /**
     * Geeft de bsn nummer terug.
     *
     * @return bsn nummer
     */
    @Bsn
    abstract String getBurgerservicenummer();

    /**
     * Zet de bsn nummer.
     *
     * @param burgerservicenummer de burgerservicenummer
     *
     */
    abstract void setBurgerservicenummer(String burgerservicenummer);

    public String getHistorieMaterieel() {
        return historieMaterieel;
    }

    public void setHistorieMaterieel(final String historieMaterieel) {
        this.historieMaterieel = historieMaterieel;
    }

    public String getHistorieFormeel() {
        return historieFormeel;
    }

    public void setHistorieFormeel(final String historieFormeel) {
        this.historieFormeel = historieFormeel;
    }

    public String getAanschouwerBsn() {
        return aanschouwerBsn;
    }

    public void setAanschouwerBsn(final String aanschouwerBsn) {
        this.aanschouwerBsn = aanschouwerBsn;
    }

}
