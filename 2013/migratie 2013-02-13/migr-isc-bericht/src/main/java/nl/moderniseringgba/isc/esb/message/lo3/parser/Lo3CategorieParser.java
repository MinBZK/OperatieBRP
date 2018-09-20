/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Interface voor LO3 categorie parsers.
 * 
 * 
 * 
 * @param <T>
 *            de LO3 categorie die geparsed wordt.
 */
public abstract class Lo3CategorieParser<T extends Lo3CategorieInhoud> {
    /**
     * Parse een categorie.
     * 
     * @param categorieen
     *            de lijst met categorie waarden uit de kolom van de persoonslijst
     * @return een lo3 stapel
     */
    public abstract Lo3Stapel<T> parse(List<Lo3CategorieWaarde> categorieen);

    /**
     * Parsed de LO3 historie elementen voor de historie en verwijderd deze uit de meegegeven lijst van elementen.
     * 
     * @param elementen
     *            de elementen
     * @return het LO3 historie object
     */
    protected final Lo3Historie parseLo3Historie(final Map<Lo3ElementEnum, String> elementen) {
        final Lo3IndicatieOnjuist indicatieOnjuist =
                Parser.parseLo3IndicatieOnjuist(elementen.remove(Lo3ElementEnum.ELEMENT_8410));
        final Lo3Datum ingangsdatumGeldigheid = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8510));
        final Lo3Datum datumVanOpneming = Parser.parseLo3Datum(elementen.remove(Lo3ElementEnum.ELEMENT_8610));

        if (indicatieOnjuist == null && ingangsdatumGeldigheid == null && datumVanOpneming == null) {
            return Lo3Historie.NULL_HISTORIE;
        }

        return new Lo3Historie(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming);
    }
}
