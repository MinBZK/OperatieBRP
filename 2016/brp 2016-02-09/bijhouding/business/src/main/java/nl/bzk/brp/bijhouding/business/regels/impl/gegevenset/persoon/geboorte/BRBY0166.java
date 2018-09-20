/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Geboortedatum mag niet onbekend zijn bij geboorte in Nederland.
 *
 * @brp.bedrijfsregel BRBY0166
 */
@Named("BRBY0166")
public class BRBY0166 implements VoorActieRegelMetMomentopname<
        FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0166;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
                                              final FamilierechtelijkeBetrekkingBericht nieuweSituatie,
                                              final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie != null) {
            final PersoonBericht kind = (PersoonBericht) RelatieUtils.haalKindUitRelatie(nieuweSituatie);
            final PersoonGeboorteGroepBericht kindGeboorte = getKindGeboorte(kind);
            if (isLandGevuldEnNederland(kindGeboorte) && isDatumGeboorteGevuldEnOngeldig(kindGeboorte)) {
                objectenDieDeRegelOvertreden.add(kind);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    private boolean isDatumGeboorteGevuldEnOngeldig(final PersoonGeboorteGroepBericht kindGeboorte) {
        return kindGeboorte != null
            && kindGeboorte.getDatumGeboorte() != null
            && !kindGeboorte.getDatumGeboorte().isVolledigDatumWaarde();
    }

    private boolean isLandGevuldEnNederland(final PersoonGeboorteGroepBericht kindGeboorte) {
        return kindGeboorte != null
            && kindGeboorte.getLandGebiedGeboorte() != null
            && kindGeboorte.getLandGebiedGeboorte().getWaarde().getCode().getWaarde().equals(LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT);
    }

    private PersoonGeboorteGroepBericht getKindGeboorte(final PersoonBericht kind) {
        if (kind == null) {
            return null;
        }
        return kind.getGeboorte();
    }

}
