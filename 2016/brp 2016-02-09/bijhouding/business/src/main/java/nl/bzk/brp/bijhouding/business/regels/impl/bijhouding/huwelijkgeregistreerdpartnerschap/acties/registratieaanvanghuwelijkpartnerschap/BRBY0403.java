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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;

import org.apache.commons.lang.StringUtils;


/**
 * Een Persoon die als Partner betrokken is bij een Actie waarmee een Huwelijk of Geregistreerd partnerschap wordt
 * geregistreerd, mag op de datum aanvang geldigheid van de Actie niet onder curatele staan.
 *
 * Opmerkingen:
 * 1. Als datum aanvang geldigheid in de toekomst ligt, dient in plaats daarvan de systeemdatum als peildatum te
 * worden genomen.
 * 2. De peildatum is dus het minimum van datum aanvang geldigheid van de Actie en de systeemdatum.
 * 3. Op deze wijze is de regel ook bruikbaar tijdens de voorbereidende fase (prevalidatie).
 * 4. Deze controle is niet van toepassing als de Persoon een NietIngeschrevene is.
 * 5. Een Persoon staat onder curatele als het attribuut Onder curatele in de groep Curatele naar de waarde "Ja"
 * verwijst.
 *
 * @brp.bedrijfsregel BRBY0403
 */
@Named("BRBY0403")
public class BRBY0403 implements
        VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView, HuwelijkGeregistreerdPartnerschapBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0403;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
            final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        for (final Betrokkenheid betrokkenheidBericht : nieuweSituatie.getBetrokkenheden()) {

            if (betrokkenheidBericht.getPersoon() != null) {
                final PersoonBericht partner = (PersoonBericht) betrokkenheidBericht.getPersoon();

                if (StringUtils.isNotBlank(partner.getIdentificerendeSleutel())) {
                    final PersoonView persoonHisVolledigView =
                            bestaandeBetrokkenen.get(partner.getIdentificerendeSleutel());

                    for (final PersoonIndicatie persoonIndicatie : persoonHisVolledigView.getIndicaties()) {

                        if (SoortIndicatie.INDICATIE_ONDER_CURATELE == persoonIndicatie.getSoort().getWaarde()
                                && persoonIndicatie.getStandaard() != null
                                && Ja.J == persoonIndicatie.getStandaard().getWaarde().getWaarde())
                        {
                            // Er is een actuele indicatie aanwezig.
                            objectenDieDeRegelOvertreden.add(partner);
                        }
                    }
                }
            }
        }
        return objectenDieDeRegelOvertreden;
    }
}
