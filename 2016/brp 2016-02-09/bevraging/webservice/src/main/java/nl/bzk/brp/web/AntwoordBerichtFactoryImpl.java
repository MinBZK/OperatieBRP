/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bevraging.BevragingAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.ZoekPersoonAntwoordBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.webservice.business.service.AbstractAntwoordBerichtFactory;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import org.springframework.stereotype.Component;


/**
 * .
 */
@Component
public class AntwoordBerichtFactoryImpl extends AbstractAntwoordBerichtFactory {

    @Override
    protected void vulAntwoordBerichtAan(final BerichtVerwerkingsResultaat resultaat,
        final Bericht bericht,
        final AntwoordBericht antwoord)
    {
        final BevragingAntwoordBericht bevragingAntwoord = (BevragingAntwoordBericht) antwoord;
        final BevragingResultaat opvragenPersoonResultaat = (BevragingResultaat) resultaat;
        if (!opvragenPersoonResultaat.bevatVerwerkingStoppendeFouten()) {
            for (final PersoonHisVolledigView gevondenPersoon : opvragenPersoonResultaat.getGevondenPersonen()) {
                bevragingAntwoord.voegGevondenPersoonToe(gevondenPersoon);
            }
        }
    }

    @Override
    protected BerichtResultaatGroepBericht maakInitieelBerichtResultaatGroepBericht(
        final Bericht ingaandBericht,
        final BerichtVerwerkingsResultaat resultaat)
    {
        return new BerichtResultaatGroepBericht();
    }

    /**
     * Bepaalt op basis van het ingaande bericht het antwoord bericht dat geretourneerd moet worden.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @return Het antwoord bericht behorende bij het ingaande bericht.
     */
    @Override
    protected BevragingAntwoordBericht maakInitieelAntwoordBerichtVoorInkomendBericht(
        final Bericht ingaandBericht)
    {
        final BevragingAntwoordBericht antwoordBericht;
        switch (ingaandBericht.getSoort().getWaarde()) {
            case BHG_BVG_GEEF_DETAILS_PERSOON:
                antwoordBericht = new GeefDetailsPersoonAntwoordBericht();
                break;
            case BHG_BVG_BEPAAL_KANDIDAAT_VADER:
                antwoordBericht = new BepaalKandidaatVaderAntwoordBericht();
                break;
            case BHG_BVG_GEEF_PERSONEN_OP_ADRES_MET_BETROKKENHEDEN:
                antwoordBericht = new GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht();
                break;
            case BHG_BVG_ZOEK_PERSOON:
                antwoordBericht = new ZoekPersoonAntwoordBericht();
                break;
            case LVG_BVG_GEEF_DETAILS_PERSOON:
                antwoordBericht = new nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonAntwoordBericht();
                break;
            default:
                throw new IllegalStateException("Mapping van ingaande en uitgaande bericht soorten is niet compleet.");
        }
        return antwoordBericht;
    }

}
