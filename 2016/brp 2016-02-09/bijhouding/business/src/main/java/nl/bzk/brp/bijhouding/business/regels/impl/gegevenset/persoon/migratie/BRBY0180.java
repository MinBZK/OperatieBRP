/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.AbstractBestaansPeriodeStamgegevenRegel;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * BRBY0180: Land/gebied migratie moet verwijzen naar geldig stamgegeven.
 * @brp.bedrijfsregel BRBY0180
 */
@Named("BRBY0180")
public class BRBY0180 extends AbstractBestaansPeriodeStamgegevenRegel<PersoonView, PersoonBericht, PersoonBericht> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Map<BestaansPeriodeStamgegeven, PersoonBericht> getStamgegevensEnEntiteiten(
            final PersoonBericht nieuweSituatie)
    {
        final Map<BestaansPeriodeStamgegeven, PersoonBericht> stamgegevensEnEntiteiten =
                new HashMap<>();
        if (nieuweSituatie.getMigratie() != null && nieuweSituatie.getMigratie().getLandGebiedMigratie() != null) {
            stamgegevensEnEntiteiten.put(
                    nieuweSituatie.getMigratie().getLandGebiedMigratie().getWaarde(), nieuweSituatie);
        }

        return stamgegevensEnEntiteiten;
    }

    @Override
    protected DatumBasisAttribuut getPeilDatum(final Actie actie, final PersoonBericht entiteit) {
        return actie.getDatumAanvangGeldigheid();
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0180;
    }
}
