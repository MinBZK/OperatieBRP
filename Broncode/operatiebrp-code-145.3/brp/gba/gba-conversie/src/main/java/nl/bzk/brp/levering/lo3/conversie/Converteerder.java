/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Converteerder.
 */
//
/*
 * squid:S1609 @FunctionalInterface annotation should be used to flag Single Abstract Method
 * interfaces
 *
 * False positive, dit hoort niet een functional interface te zijn. Dit is een gewone interface met
 * slechts 1 methode.
 */
public interface Converteerder {

    /**
     * Converteer de BRP gegevens naar LO3 gegevens.
     * @param persoon persoon
     * @param istStapels ist stapels
     * @param administratieveHandeling administratieveHandeling
     * @param identificatienummerMutatie identificatienummer mutatie resultaat
     * @param conversieCache cache
     * @return LO3 categorieen
     */
    List<Lo3CategorieWaarde> converteer(
            Persoonslijst persoon,
            List<Stapel> istStapels,
            AdministratieveHandeling administratieveHandeling,
            IdentificatienummerMutatie identificatienummerMutatie,
            ConversieCache conversieCache);

}
