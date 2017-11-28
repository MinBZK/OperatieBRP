/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;

/**
 * GeefDetailsPersoonRepository.
 */
public interface GeefDetailsPersoonRepository {

    /**
     * Zoek technisch id's van een ingeschreven persoon a.h.v. bsn.
     * @param bsn bsn
     * @return lijst met id's
     */
    List<Long> zoekIdsPersoonMetBsn(String bsn);

    /**
     * Zoek technisch id's van een ingeschreven persoon a.h.v. Anummer.
     * @param anr anr
     * @return lijst met id's
     */
    List<Long> zoekIdsPersoonMetAnummer(String anr);
}
