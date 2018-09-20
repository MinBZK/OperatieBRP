/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;

/** Repository voor de {@link nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig} class. */
public interface RelatieHisVolledigRepository {

    /**
     * Leest een {@link nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig} direct uit de database.
     *
     * @param id technische sleutel van relatieHisVolledig
     * @return een instantie van RelatieHisVolledig
     */
    RelatieHisVolledig leesGenormalizeerdModel(final Integer id);

    /**
     * Schrijft een {@link nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig} direct in de database.
     * Er wordt dus een update gedaan van alle gewijzigde data van deze relatie in de C/D laag.
     *
     * @param relatie de relatieHisVolledig
     */
    void schrijfGenormalizeerdModel(final RelatieHisVolledig relatie);
}
