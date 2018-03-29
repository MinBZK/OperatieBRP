/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.stereotype.Component;

/**
 * Aangepast 'Wa11' bericht wat wordt verstuurd als een PL wordt afgevoerd naar aanleiding van een dubbele inschrijving
 * met verschillende a-nummers. Het oude a-nummer wordt dan bepaald door de waarde van 01.01.10. Het nieuwe a-nummer
 * wordt bepaald door 01.20.20.
 */
@Component
public final class Wa11DubbeleInschrijvingMetVerschillendAnummerFormatter extends AbstractWa11Formatter {

    @Override
    protected String bepaalOudAnummer(final List<Lo3CategorieWaarde> categorieen, final IdentificatienummerMutatie identificatienummerMutatieResultaat) {
        return FormatterUtil.geefElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110);
    }

    @Override
    protected String bepaalNieuwAnummer(final List<Lo3CategorieWaarde> categorieen, final IdentificatienummerMutatie identificatienummerMutatieResultaat) {
        return FormatterUtil.geefElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_2020);
    }

    @Override
    protected String bepaalDatumIngangGeldigheid(
            final List<Lo3CategorieWaarde> categorieen,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat) {
        return FormatterUtil.geefElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_8510);
    }

}
