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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;

/**
 * Indien beide gevuld moet de Gemeente waarnaar GemeenteEinde verwijst gelijk zijn aan die van GemeenteAanvang of aan
 * een VoortzettendeGemeente van Gemeente.
 *
 * Opmerkingen
 * 1.Deze regel geldt niet als RedenBeëindiging gelijk is aan 'O'(verlijden) of 'R'(echtsvermoeden van overlijden).
 * 2.Door ook een VoortzettendeGemeente te accepteren, houdt deze regel rekening met gemeentelijke herindelingen.
 *
 */
@Named("BRAL2112")
public class BRAL2112 implements VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRAL2112;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (preConditiesVoorRegelVoldoen(nieuweSituatie, huidigeSituatie)) {
            final Gemeente gemeenteAanvang = huidigeSituatie.getStandaard().getGemeenteAanvang().getWaarde();
            final Gemeente gemeenteEinde = nieuweSituatie.getStandaard().getGemeenteEinde().getWaarde();
            if (!zijnGemeentesGelijkOfVoortzettendVanElkaar(gemeenteAanvang, gemeenteEinde)) {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Checkt of 2 gemeentes gelijk zijn, of dat de gemeente einde een voortzettende gemeente is van gemeente aanvang.
     *
     * @param gemeenteAanvang gemeente aanvang
     * @param gemeenteEinde gemeente einde
     * @return true indien gemeente einde gelijk is aan gemeente aanvang of gemeente einde is een voortzettende
     * gemeente van gemeente aanvang
     */
    private boolean zijnGemeentesGelijkOfVoortzettendVanElkaar(final Gemeente gemeenteAanvang,
                                                               final Gemeente gemeenteEinde)
    {
        boolean resultaat = false;
        if (gemeenteAanvang.getCode().equals(gemeenteEinde.getCode())) {
            resultaat = true;
        } else {
            Gemeente voortzettendeGemeente = gemeenteAanvang.getVoortzettendeGemeente();
            while (voortzettendeGemeente != null) {
                if (voortzettendeGemeente.getCode().equals(gemeenteEinde.getCode())) {
                    resultaat = true;
                    break;
                } else {
                    voortzettendeGemeente = voortzettendeGemeente.getVoortzettendeGemeente();
                }
            }
        }
        return resultaat;
    }

    /**
     * Checkt of aan de precondities wordt voldaan om de regel uit te voeren.
     *
     * @param nieuweSituatie de nieuwe (bericht) situatie
     * @param huidigeSituatie de huidige relatie in de BRP
     * @return of de huidige en/of nieuwe situatie aan de precondities voldoen
     */
    private boolean preConditiesVoorRegelVoldoen(final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                                 final HuwelijkGeregistreerdPartnerschap huidigeSituatie)
    {
        return  huidigeSituatie.getStandaard().getGemeenteAanvang() != null
                && nieuweSituatie.getStandaard().getGemeenteEinde() != null
                && !RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OVERLIJDEN_CODE.
                equals(nieuweSituatie.getStandaard().getRedenEinde().getWaarde().getCode())
                && !RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_RECHTSVERMOEDEN_OVERLIJDEN_CODE.
                equals(nieuweSituatie.getStandaard().getRedenEinde().getWaarde().getCode());
    }

}
