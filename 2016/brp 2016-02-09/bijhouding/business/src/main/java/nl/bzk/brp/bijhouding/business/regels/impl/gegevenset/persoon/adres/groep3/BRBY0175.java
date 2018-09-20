/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.groep3;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.AbstractBestaansPeriodeStamgegevenRegel;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Gemeente (adres) moet verwijzen naar geldig stamgegeven.
 *
 * @brp.bedrijfsregel BRBY0175
 */
@Named("BRBY0175")
public class BRBY0175 extends AbstractBestaansPeriodeStamgegevenRegel<
        PersoonView, PersoonBericht, PersoonAdresBericht>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0175;
    }

    @Override
    protected final Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected final DatumEvtDeelsOnbekendAttribuut getPeilDatum(final Actie actie, final PersoonAdresBericht adres) {
        return adres.getStandaard().getDatumAanvangAdreshouding();
    }

    @Override
    protected final Map<BestaansPeriodeStamgegeven, PersoonAdresBericht> getStamgegevensEnEntiteiten(
            final PersoonBericht nieuweSituatie)
    {
        final Map<BestaansPeriodeStamgegeven, PersoonAdresBericht> stamgegevensEnEntiteiten =
                new HashMap<>();

        for (final PersoonAdresBericht persoonAdres : nieuweSituatie.getAdressen()) {
            if (persoonAdres.getStandaard() != null && persoonAdres.getStandaard().getGemeente() != null) {
                stamgegevensEnEntiteiten.put(persoonAdres.getStandaard().getGemeente().getWaarde(), persoonAdres);
            }
        }

        return stamgegevensEnEntiteiten;
    }

}
