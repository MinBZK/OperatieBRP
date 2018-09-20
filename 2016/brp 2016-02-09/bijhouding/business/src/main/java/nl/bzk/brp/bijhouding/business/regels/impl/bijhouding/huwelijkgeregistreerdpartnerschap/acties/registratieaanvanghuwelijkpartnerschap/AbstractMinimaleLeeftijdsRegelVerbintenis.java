/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding
        .huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.util.PersoonUtil;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;

/**
 * De abstracte regel-klasse die een minimale leeftijd controleert. Voor deze regel dient een minimale leeftijd te
 * worden opgegeven en er kunnen nog extra voorwaarden meegegeven worden die gecontroleerd dienen te worden.
 */
public abstract class AbstractMinimaleLeeftijdsRegelVerbintenis implements
        VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView, HuwelijkGeregistreerdPartnerschapBericht>
{

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final List<Persoon> partners = RelatieUtils.haalPartnersUitRelatie(nieuweSituatie, SoortPersoon.INGESCHREVENE);
        for (Persoon partnerInterface : partners) {
            final PersoonBericht partnerBericht = (PersoonBericht) partnerInterface;
            final PersoonView partnerModel = bestaandeBetrokkenen.get(partnerBericht.getIdentificerendeSleutel());

            final DatumEvtDeelsOnbekendAttribuut huwelijkDatum = nieuweSituatie.getStandaard().getDatumAanvang();

            if (overtreedtExtraVoorwaarden(nieuweSituatie, partnerModel)
                    && !PersoonUtil.isLeeftijdOfOuderOpDatum(partnerModel, getMinimaleLeeftijd(),
                                                             new DatumAttribuut(huwelijkDatum)))
            {
                objectenDieDeRegelOvertreden.add(partnerBericht);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controle van eventuele extra voorwaarden.
     *
     * @param nieuweSituatie nieuwe situatie opgegeven in het bericht
     * @param persoon de opgeslagen persoon
     *
     * @return true als aan de extra voorwaarden is voldaan
     */
    protected boolean overtreedtExtraVoorwaarden(
            final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie, final PersoonView persoon)
    {
        // Indien geen override, dan is altijd aan de extra voorwaarden voldaan.
        return true;
    }

    /**
     * Haalt de minimale leeftijd op voor de regel.
     *
     * @return minimale leeftijd
     */
    protected abstract Integer getMinimaleLeeftijd();
}
