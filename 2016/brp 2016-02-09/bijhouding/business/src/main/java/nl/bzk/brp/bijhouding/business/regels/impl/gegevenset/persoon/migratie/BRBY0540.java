/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * BRBY0540: Land/gebied migratie mag niet Nederland zijn.
 * Het attribuut Land/gebied van Migratie mag niet "Nederland" zijn, tenzij de Persoon een
 * BijzondereVerblijfsrechtelijkePositie heeft.
 *
 * @brp.bedrijfsregel BRBY0540
 */
@Named("BRBY0540")
public class BRBY0540 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView persoonView, final PersoonBericht persoonBericht,
                                              final Actie actie,
                                              final Map<String, PersoonView> stringPersoonViewMap)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (persoonBericht.getMigratie().getLandGebiedMigratie() != null
            && persoonBericht.getMigratie().getLandGebiedMigratie().getWaarde().getCode().equals(LandGebiedCodeAttribuut.NEDERLAND)
            && !persoonHeeftBvp(persoonView))
        {
            objectenDieDeRegelOvertreden.add(persoonBericht);
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of de persoon een BVP heeft.
     * @param persoon de te controleren persoon
     * @return true indien niet vervallen BVP voor de persoon geregistreerd staat anders false.
     */
    private boolean persoonHeeftBvp(final PersoonView persoon) {
        return persoon.getIndicatieBijzondereVerblijfsrechtelijkePositie() != null
                && persoon.getIndicatieBijzondereVerblijfsrechtelijkePositie().heeftActueleGegevens();
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0540;
    }
}
