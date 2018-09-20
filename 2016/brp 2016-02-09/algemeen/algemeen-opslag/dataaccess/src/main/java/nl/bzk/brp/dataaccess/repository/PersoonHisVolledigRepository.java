/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/** Repository voor de {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} class. */
public interface PersoonHisVolledigRepository {

    /**
     * Leest een {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} direct uit de database.
     *
     * @param id technische sleutel van persoonHisVolledig
     * @return een instantie van PersoonHisVolledig
     */
    PersoonHisVolledig leesGenormalizeerdModel(final Integer id);

    /**
     * Schrijft een {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} direct in de database.
     * Er wordt dus een update gedaan van alle gewijzigde data van deze persoon in de C/D laag.
     *
     * @param persoon de persoonHisVolledig
     */
    void schrijfGenormalizeerdModel(final PersoonHisVolledigImpl persoon);

    /**
     * Sla een nieuw persoon op in de database. Dit is een wrapper rond schrijfGenormalizeerdModel,
     * waarbij nog als extra check gekeken wordt of het BSN niet is in gebruik is.
     *
     * @param persoon de nieuwe persoon
     * @return de nieuwe, opgslagen persoon
     */
    PersoonHisVolledig opslaanNieuwPersoon(final PersoonHisVolledigImpl persoon);

}
