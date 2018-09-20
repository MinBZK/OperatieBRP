/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.AbstractBestaansPeriodeStamgegevenRegel;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Implementatie van bedrijfsregel BRBY0430.
 * <p/>
 * Land aanvang huwelijk (of geregistreerd partnerschap) moet op de datum aanvang huwelijk (of geregistreerd
 * partnerschap) een geldige landcode zijn in stamgegeven "Land" voor de corresponderende periode.
 * Als de datum aanvang huwelijk  (of geregistreerd partnerschap) in de toekomst ligt
 * (datum aanvang huwelijk  > systeemdatum) dan is deze controle op basis van systeemdatum.
 * <p/>
 *
 * @brp.bedrijfsregel BRBY0430
 */
@Named("BRBY0430")
public class BRBY0430 extends AbstractBestaansPeriodeStamgegevenRegel<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht, HuwelijkGeregistreerdPartnerschapBericht>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0430;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Map<BestaansPeriodeStamgegeven, HuwelijkGeregistreerdPartnerschapBericht> getStamgegevensEnEntiteiten(
            final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie)
    {
        final Map<BestaansPeriodeStamgegeven, HuwelijkGeregistreerdPartnerschapBericht> stamgegevensEnEntiteiten =
                new HashMap<>();

        if (nieuweSituatie.getStandaard() != null && nieuweSituatie.getStandaard().getLandGebiedAanvang() != null) {
            stamgegevensEnEntiteiten.put(nieuweSituatie.getStandaard().getLandGebiedAanvang().getWaarde(),
                    nieuweSituatie);
        }

        return stamgegevensEnEntiteiten;
    }

    @Override
    protected DatumBasisAttribuut getPeilDatum(final Actie actie,
            final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie)
    {
        return nieuweSituatie.getStandaard().getDatumAanvang();
    }
}
