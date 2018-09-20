/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Basis mapper voor BRP naar Conversie model (voor meerdere stapels).
 *
 * @param <B> BRP Basis type
 * @param <H> BRP Historie type
 * @param <G> Conversie model groep inhoud type
 */
public abstract class AbstractMultipleMapper<B, H extends ModelIdentificeerbaar<?>, G extends BrpGroepInhoud> {

    /**
     * Map.
     *
     * @param persoonHisVolledig persoon volledig
     * @param onderzoekMapper onderzoek mapper
     * @param actieHisVolledigLocator actieHisVolledig locator
     * @return lijst van stapels
     */
    public final List<BrpStapel<G>> map(
        final PersoonHisVolledig persoonHisVolledig,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        final List<BrpStapel<G>> result = new ArrayList<>();

        for (final B single : getSet(persoonHisVolledig)) {
            final BrpStapel<G> stapel = getSingleMapper().map(single, onderzoekMapper, actieHisVolledigLocator);
            if (stapel != null) {
                result.add(stapel);
            }
        }

        return result;
    }

    /**
     * Geef de set die naar stapels geconverteerd moet worden.
     *
     * @param persoonHisVolledig persoon volledig
     * @return set
     */
    protected abstract Set<? extends B> getSet(final PersoonHisVolledig persoonHisVolledig);

    /**
     * Geef de mapper die voor een enkele stapel gebruikt wordt.
     *
     * @return mapper
     */
    protected abstract AbstractMapper<B, H, G> getSingleMapper();

}
