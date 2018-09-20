/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;

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
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Reden verkrijging moet verwijzen naar geldig stamgegeven.
 *
 * @brp.bedrijfsregel BRBY0176
 */
@Named("BRBY0176")
public class BRBY0176 extends AbstractBestaansPeriodeStamgegevenRegel<
        PersoonView, PersoonBericht, PersoonNationaliteitBericht>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0176;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Map<BestaansPeriodeStamgegeven, PersoonNationaliteitBericht> getStamgegevensEnEntiteiten(
            final PersoonBericht nieuweSituatie)
    {
        final Map<BestaansPeriodeStamgegeven, PersoonNationaliteitBericht> stamgegevensEnEntiteiten =
                new HashMap<BestaansPeriodeStamgegeven, PersoonNationaliteitBericht>();

        if (nieuweSituatie.getNationaliteiten() != null) {
            for (final PersoonNationaliteitBericht persoonNationaliteit : nieuweSituatie.getNationaliteiten()) {
                if (persoonNationaliteit.getStandaard() != null
                        && persoonNationaliteit.getStandaard().getRedenVerkrijging() != null)
                {
                    stamgegevensEnEntiteiten.put(persoonNationaliteit.getStandaard().getRedenVerkrijging().getWaarde(),
                            persoonNationaliteit);
                }
            }
        }

        return stamgegevensEnEntiteiten;
    }

    @Override
    protected DatumBasisAttribuut getPeilDatum(final Actie actie, final PersoonNationaliteitBericht nieuweSituatie) {
        return actie.getDatumAanvangGeldigheid();
    }

}
