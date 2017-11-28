/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Uitgaand bericht 'formatter'.
 */
//
/*
 * squid:S1609 @FunctionalInterface annotation should be used to flag Single Abstract Method
 * interfaces
 *
 * False positive, dit hoort niet een functional interface te zijn. Dit is een gewone interface met
 * slechts 1 methode.
 */
public interface Formatter {

    /**
     * Maak de platte tekst voor dit uitgaand bericht.
     * @param persoon persoon (ongefilterd)
     * @param categorieen categorieen (ongefilterd)
     * @param categorieenGefilterd categorieen (gefilterd)
     * @param identificatienummerMutatie identificatienummer mutatie
     * @return platte tekst
     */
    String maakPlatteTekst(
            final Persoonslijst persoon,
            IdentificatienummerMutatie identificatienummerMutatie,
            List<Lo3CategorieWaarde> categorieen,
            List<Lo3CategorieWaarde> categorieenGefilterd);
}
