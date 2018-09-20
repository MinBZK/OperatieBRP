/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0002;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.RelatieUtils;
import org.apache.commons.lang.StringUtils;


/**
 * Basis uitvoering van de kandidaat vader regel. Deze abstract voert geen controller uit, maar haalt alleen de
 * benodigde data op.
 *
 * @brp.bedrijfsregel BRBY0033
 */
public abstract class AbstractKandidaatVaderRegel implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BRBY0002 brby0002;

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
        final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final PersoonBericht moeder = (PersoonBericht) RelatieUtils.haalMoederUitRelatie(nieuweSituatie);

        // bij geboorte, MOET de moeder een ingezetene zijn ==> identificerende sleutel.
        if (moeder != null && StringUtils.isNotBlank(moeder.getIdentificerendeSleutel())) {
            final PersoonView moederModel = bestaandeBetrokkenen.get(moeder.getIdentificerendeSleutel());

            final PersoonBericht vader = (PersoonBericht) RelatieUtils.haalNietMoederUitRelatie(nieuweSituatie);

            final List<PersoonView> kandidatenVaders =
                brby0002.bepaalKandidatenVader(moederModel, nieuweSituatie.getKindBetrokkenheid().getPersoon()
                    .getGeboorte().getDatumGeboorte());

            if (overtreedtKandidaatVaderGesteldeVoorwaarden(kandidatenVaders, vader)) {
                if (vader != null) {
                    objectenDieDeRegelOvertreden.add(vader);
                } else {
                    objectenDieDeRegelOvertreden.add(nieuweSituatie);
                }
            }
        } else {
            LOGGER.warn("Persoon moeder niet aanwezig of heeft geen technische sleutel. Bedrijfsregel {} "
                + "wordt dus niet uitgevoerd.", getRegel().name());
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Uitvoering van de daadwerkelijke controle of de vader uit het bericht voldoet aan de regels die gesteld worden
     * voor kandidaat vader.
     *
     * @param kandidatenVaders de gevonden kandidaten uit de database
     * @param vader de opgegeven vader uit het bericht
     * @return true als de controle een fout heeft opgeleverd.
     */
    abstract boolean overtreedtKandidaatVaderGesteldeVoorwaarden(List<PersoonView> kandidatenVaders,
            PersoonBericht vader);
}
