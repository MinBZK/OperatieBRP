/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.util.RelatieUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementatie van bedrijfsregel BRAL0505.
 * <p/>
 * Bedrijfsregel die controleert of voorvoegsel niet gevuld is als er sprake is van een Namenreeks binnen
 * samengestelde naam. Deze regel moet dus uitgevoerd worden na afleiding.
 *
 * @brp.bedrijfsregel BRAL0505
 */
@Named("BRAL0505")
public class BRAL0505 implements NaActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView,
        FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRAL0505;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekking,
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final Persoon kind = RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekking);
        if (kind != null && kind.getSamengesteldeNaam() != null) {
            final PersoonSamengesteldeNaamGroep samengesteldeNaam = kind.getSamengesteldeNaam();
            if (heeftNamenreeks(samengesteldeNaam) && heeftNietLeegVoorvoegsel(samengesteldeNaam)) {
                objectenDieDeRegelOvertreden
                    .add((PersoonBericht) RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekkingBericht));
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Geeft aan of de opgegeven samengestelde naam een namenreeks heeft of niet.
     *
     * @param samengesteldeNaam de samengestelde naam die gecontroleerd dient te worden.
     * @return of de opgegeven samengestelde naam een namenreeks heeft of niet.
     */
    private boolean heeftNamenreeks(final PersoonSamengesteldeNaamGroep samengesteldeNaam) {
        return JaNeeAttribuut.JA.equals(samengesteldeNaam.getIndicatieNamenreeks());
    }

    /**
     * Geeft aan of de opgegeven samengestelde naam een 'niet leeg' voorvoegsel heeft.
     *
     * @param samengesteldeNaam de samengestelde naam die gecontroleerd dient te worden.
     * @return of de opgegeven samengestelde naam een 'niet leeg' voorvoegsel heeft of niet.
     */
    private boolean heeftNietLeegVoorvoegsel(final PersoonSamengesteldeNaamGroep samengesteldeNaam) {
        return samengesteldeNaam.getVoorvoegsel() != null && StringUtils
            .isNotEmpty(samengesteldeNaam.getVoorvoegsel().getWaarde());
    }

}
