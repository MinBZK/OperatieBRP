/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.ActieBron;

/**
 * Indien gevuld moet GemeenteAanvang in de groep StandaardHuwelijkPartnerschap naar dezelfde Partij verwijzen als de
 * NederlandseRegisterakte die als Bron fungeerde bij de Actie waarmee de groep is geregistreerd.
 *
 * @brp.bedrijfsregel BRAL2110
 */
@Named("BRAL2110")
public class BRAL2110 implements VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht>
{
    @Override
    public final Regel getRegel() {
        return Regel.BRAL2110;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final GemeenteAttribuut gemeenteAanvang = nieuweSituatie.getStandaard().getGemeenteAanvang();

        if (gemeenteAanvang != null) {
            boolean isOngelijk = false;
            for (ActieBron actieBron : actie.getBronnen()) {
                if (isPartijGemeenteOngelijkAanPartijBronDocument(gemeenteAanvang.getWaarde(), actieBron)) {
                    isOngelijk = true;
                    break;
                }
            }

            if (isOngelijk) {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of de partij van de gemeente ongelijk is aan de partij van het document behorende bij een bron.
     * Indien de gemeente niet gekoppeld is aan een partij en/of het document geen partij bevat, dan wordt dat niet
     * gezien als ongelijk.
     *
     * @param gemeente de gemeente waarvan de partij wordt gecontroleerd.
     * @param actieBron de bron, met bijbehorend document, waarvan de partij wordt gecontroleerd.
     * @return <code>true</code> indien zowel de gemeente als het document in de bron een partij hebben, maar deze
     *    twee partijen ongelijk aan elkaar zijn, anders <code>false</code>.
     */
    private boolean isPartijGemeenteOngelijkAanPartijBronDocument(final Gemeente gemeente, final ActieBron actieBron) {
        return gemeente.getPartij() != null
            && actieBron.getDocument() != null
            && actieBron.getDocument().getStandaard().getPartij() != null
            && !gemeente.getPartij().getCode().equals(
                    actieBron.getDocument().getStandaard().getPartij().getWaarde().getCode());
    }
}
