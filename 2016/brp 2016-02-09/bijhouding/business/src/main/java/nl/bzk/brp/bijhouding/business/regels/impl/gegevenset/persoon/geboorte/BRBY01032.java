/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.AbstractBestaansPeriodeStamgegevenRegel;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Gemeente geboorte moet geldig zijn op geboortedatum.
 *
 * @brp.bedrijfsregel BRBY01032
 */
@Named("BRBY01032")
public class BRBY01032 extends AbstractBestaansPeriodeStamgegevenRegel<
        FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht, PersoonBericht>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final Regel getRegel() {
        return Regel.BRBY01032;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Map<BestaansPeriodeStamgegeven, PersoonBericht> getStamgegevensEnEntiteiten(
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie)
    {
        final Map<BestaansPeriodeStamgegeven, PersoonBericht> stamgegevensEnEntiteiten =
                new HashMap<>();

        final PersoonBericht kind = (PersoonBericht) RelatieUtils.haalKindUitRelatie(nieuweSituatie);
        if (kind.getGeboorte() != null && kind.getGeboorte().getGemeenteGeboorte() != null) {
            stamgegevensEnEntiteiten.put(kind.getGeboorte().getGemeenteGeboorte().getWaarde(), kind);
        }

        final PersoonBericht vader = (PersoonBericht) RelatieUtils.haalNietMoederUitRelatie(nieuweSituatie);
        if (vader != null && vader.getGeboorte() != null && vader.getGeboorte().getGemeenteGeboorte() != null) {
            stamgegevensEnEntiteiten.put(vader.getGeboorte().getGemeenteGeboorte().getWaarde(), vader);
        }

        return stamgegevensEnEntiteiten;
    }

    @Override
    protected DatumBasisAttribuut getPeilDatum(final Actie actie, final PersoonBericht persoon) {
        return persoon.getGeboorte().getDatumGeboorte();
    }

}
