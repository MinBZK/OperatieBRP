/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import org.springframework.stereotype.Component;

/**
 * Extensie van Historie Conversie Variant LB21 specifiek voor Bijhouding. Aangezien Bijhouding zowel uit Categorie 07
 * als Categorie 08 gegevens krijgt, is er een uitbreiding nodig op het bepalen of een groep actueel is.
 */
@Component
public class Lo3HistorieConversieVariantLB21Bijhouding extends Lo3HistorieConversieVariantLB21 {

    @Override
    protected final <T extends BrpGroepInhoud> boolean isActueleGroep(final TussenGroep<T> huidigeLo3Groep, final List<TussenGroep<T>> lo3Groepen) {
        final boolean resultaat;

        if (bevatCat07enCat08(lo3Groepen) && huidigeLo3Groep.getLo3Herkomst().getVoorkomen() == 0) {
            if (isCat08Actueler(lo3Groepen)) {
                resultaat = Lo3CategorieEnum.CATEGORIE_08.equals(huidigeLo3Groep.getLo3Herkomst().getCategorie());
            } else {
                resultaat = Lo3CategorieEnum.CATEGORIE_07.equals(huidigeLo3Groep.getLo3Herkomst().getCategorie());
            }
        } else {
            resultaat = super.isActueleGroep(huidigeLo3Groep, lo3Groepen);
        }

        return resultaat;
    }

    private <T extends BrpGroepInhoud> boolean isCat08Actueler(final List<TussenGroep<T>> lo3Groepen) {
        final TussenGroep<T> cat07 = bepaalActueelvoorCategorie(lo3Groepen, Lo3CategorieEnum.CATEGORIE_07);
        final TussenGroep<T> cat08 = bepaalActueelvoorCategorie(lo3Groepen, Lo3CategorieEnum.CATEGORIE_08);

        return cat08.getHistorie().getIngangsdatumGeldigheid().compareTo(cat07.getHistorie().getIngangsdatumGeldigheid()) > 0;
    }

    private <T extends BrpGroepInhoud> boolean bevatCat07enCat08(final List<TussenGroep<T>> lo3Groepen) {
        final boolean bevatCat07 = bepaalActueelvoorCategorie(lo3Groepen, Lo3CategorieEnum.CATEGORIE_07) != null;
        final boolean bevatCat08 = bepaalActueelvoorCategorie(lo3Groepen, Lo3CategorieEnum.CATEGORIE_08) != null;

        return bevatCat07 && bevatCat08;
    }

    private <T extends BrpGroepInhoud> TussenGroep<T> bepaalActueelvoorCategorie(final List<TussenGroep<T>> lo3Groepen, final Lo3CategorieEnum categorie) {
        for (final TussenGroep<T> lo3Groep : lo3Groepen) {
            if (lo3Groep.getLo3Herkomst().getVoorkomen() == 0 && categorie.equals(lo3Groep.getLo3Herkomst().getCategorie())) {
                return lo3Groep;
            }
        }
        return null;
    }
}
