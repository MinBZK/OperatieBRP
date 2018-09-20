/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import javax.validation.Valid;

import nl.bzk.brp.business.dto.bevraging.zoekcriteria.ZoekCriteriaPersoonOpAdres;

/**
 *
 * Vraag voor opvragen van de personen die op hetzelfde adres wonen en hun onderlinge relaties.
 *
 */
public class PersonenOpAdresInclusiefBetrokkenhedenVraag extends AbstractVraag/*<ZoekCriteriaPersoonOpAdres>*/ {
    @Valid
    private ZoekCriteriaPersoonOpAdres zoekCriteria = new ZoekCriteriaPersoonOpAdres();

    @Override
    public ZoekCriteriaPersoonOpAdres getZoekCriteria() {
        return zoekCriteria;
    }

    public void setZoekCriteria(final ZoekCriteriaPersoonOpAdres zoekCriteria) {
        this.zoekCriteria = zoekCriteria;
    }

    @Override
    public String getBurgerServiceNummerForLocks() {
        if (getZoekCriteria() != null) {
            return getZoekCriteria().getBurgerservicenummer();
        }
        return null;
    }


}
