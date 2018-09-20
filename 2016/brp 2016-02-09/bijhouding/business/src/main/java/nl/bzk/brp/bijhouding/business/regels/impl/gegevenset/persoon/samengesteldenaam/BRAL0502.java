/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.util.BusinessUtils;
import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.Relatie;

/**
 * Implementatie van bedrijfsregel BRAL0502.
 * <p/>
 * Namenreeks en Voornaam sluiten elkaar uit.
 *
 * Opmerking:
 * Dit impliceert dat een Ingeschrevene met een namenreeks geen enkele Voornaam mag hebben omdat de Voornamen in
 * SamengesteldeNaam daarvan zijn afgeleid.
 *
 * @brp.bedrijfsregel BRAL0502
 */
@Named("BRAL0502")
public class BRAL0502 implements NaActieRegelMetMomentopname<ModelRootObject, BerichtRootObject> {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL0502;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final ModelRootObject modelRootObject,
            final BerichtRootObject berichtRootObject)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (modelRootObject instanceof Persoon) {
            if (persoonBevatVoornamenEnNamenreeks((Persoon) modelRootObject)) {
                objectenDieDeRegelOvertreden.add(berichtRootObject);
            }
        } else if (modelRootObject instanceof Relatie) {
            for (final Betrokkenheid betrokkenheid : ((Relatie) modelRootObject).getBetrokkenheden()) {
                final Persoon persoon = betrokkenheid.getPersoon();
                if (persoonBevatVoornamenEnNamenreeks(persoon)) {
                    objectenDieDeRegelOvertreden.add(
                            BusinessUtils.matchPersoonInRelatieBericht((PersoonView) persoon,
                                (RelatieBericht) berichtRootObject));
                }
            }
        } else {
            throw new IllegalArgumentException("RootObject wordt niet ondersteund: "
                                                            + modelRootObject.getClass().getSimpleName());
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Geeft aan of de opgegeven persoon (afgeleide) voornamen heeft in combinatie met de indicatie namen reeks 'Ja'.
     *
     * @param persoon de persoon die gecontroleerd dient te worden.
     * @return of de opgegeven persoon (afgeleide) voornamen heeft in combinatie met de indicatie namen reeks 'Ja' of
     * niet.
     */
    private boolean persoonBevatVoornamenEnNamenreeks(final Persoon persoon) {
        boolean bevat = false;
        if (persoon.getSamengesteldeNaam() != null) {
            final PersoonSamengesteldeNaamGroep samengesteldeNaam = persoon.getSamengesteldeNaam();
            if (JaNeeAttribuut.JA.equals(samengesteldeNaam.getIndicatieNamenreeks())
                    && samengesteldeNaam.getVoornamen() != null)
            {
                bevat = true;
            }
        }
        return bevat;
    }
}
