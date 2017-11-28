/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Service voor het wegfilteren van betrokkenheden en gerelateerde betrokkenheden volgens regel
 * {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel#R1385}.
 */
@FunctionalInterface
interface RelatiefilterService {

    /**
     * Filtert relaties uit de gegeven persoonslijsten, indien deze niet getoond mogen worden.
     * @param persoonslijstList lijst van {@link Persoonslijst}en
     * @param peilmomentMaterieel materieel peilmoment
     * @return een lijst getransformeerde {@link Persoonslijst}en
     */
    List<Persoonslijst> filterRelaties(List<Persoonslijst> persoonslijstList, final int peilmomentMaterieel);
}
