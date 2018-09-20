/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroep;
import nl.bzk.brp.util.RelatieUtils;

import org.apache.commons.lang.StringUtils;


/**
 * Geen gelijktijdig ander huwelijk / geregistreerd partnerschap.
 *
 * In deze bedrijfsregel wordt nu de PersoonView gebruikt van de betrokkenen zoals deze wordt meegegeven.
 * Deze views worden geraadpleegd voor de controles op actuele partnerschappen.
 *
 * @brp.bedrijfsregel BRBY0417
 */
@Named("BRBY0417")
public class BRBY0417 implements
        VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView, HuwelijkGeregistreerdPartnerschapBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0417;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
            final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final List<Persoon> personen = RelatieUtils.haalPartnersUitRelatie(nieuweSituatie);

        for (final Persoon persoon : personen) {
            final String identificerendeSleutel = ((PersoonBericht) persoon).getIdentificerendeSleutel();

            if (StringUtils.isNotBlank(identificerendeSleutel)) {
                final PersoonView persoonHisVolledigView = bestaandeBetrokkenen.get(identificerendeSleutel);

                if (isIngeschrevene(persoonHisVolledigView) && heeftPersoonAlPartner(persoonHisVolledigView)) {
                    objectenDieDeRegelOvertreden.add((PersoonBericht) persoon);
                }
            }

        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of de persoon al partners heeft.
     *
     * @param persoon persoon
     * @return true als persoon al een of meerdere partners heeft
     */
    private boolean heeftPersoonAlPartner(final PersoonView persoon) {
        boolean heeftPersoonAlPartner = false;

        final List<Betrokkenheid> partnerBetrokkenheden = RelatieUtils.haalAllePartnerBetrokkenhedenUitPersoon(persoon);
        for (final Betrokkenheid betrokkenheid : partnerBetrokkenheden) {
            final HuwelijkGeregistreerdPartnerschap hgp =
                    (HuwelijkGeregistreerdPartnerschap) betrokkenheid.getRelatie();
            final RelatieStandaardGroep hgpStandaard = hgp.getStandaard();

            // Een HuwelijkPartnerschap is niet-beëindigd als zowel DatumEinde als RedenEinde leeg zijn.
            if (hgpStandaard.getDatumEinde() == null && hgpStandaard.getRedenEinde() == null) {
                heeftPersoonAlPartner = true;
            }
        }

        return heeftPersoonAlPartner;
    }

    /**
     * Controleert of persoon van bericht een ingeschrevene is.
     *
     * @param persoon persoon
     * @return true als persoon ingeschrevene is
     */
    private boolean isIngeschrevene(final PersoonView persoon) {
        return SoortPersoon.INGESCHREVENE == persoon.getSoort().getWaarde();
    }
}
