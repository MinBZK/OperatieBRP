/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.business.toegangsbewaking.ToegangsBewakingService;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De stap in de uitvoering van een bericht waarin (achteraf) wordt gecontroleerd of de afnemer (op basis van
 * betreffende abonnement) gerechtigd is de persoon/personen in het resultaat te zien. Personen in het resultaat
 * waartoe een afnemer niet gerechtigd is, worden in deze stap dan ook uit het antwoord gefilterd. Dit daar
 * uitsluitend personen aan de afnemer mogen worden geleverd of door de afnemer mogen worden opgevraagd die
 * binnen de op dat moment geldige populatie vallen van het abonnement waarvoor opgevraagd of geleverd wordt.
 * @brp.bedrijfsregel BRAU0046, FTAU0035
 */
public class HorizontaleAutorisatieAchterafStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger     LOGGER = LoggerFactory.getLogger(HorizontaleAutorisatieAchterafStap.class);

    @Inject
    private ToegangsBewakingService toegangsBewakingService;

    /**
     * {@inheritDoc}
     *
     * @brp.bedrijfsregel BRAU0018, BRAU0046
     */
    @Override
    public final <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        List<Long> persoonIds = new ArrayList<Long>();
        for (Persoon pers : antwoord.getPersonen()) {
            persoonIds.add(pers.getId());
        }

        boolean resultaat = DOORGAAN_MET_VERWERKING;
        try {
            Map<Long, Boolean> personenToegangMap =
                toegangsBewakingService.controleerLijstVanPersonenVoorAbonnement(context.getAbonnement(), persoonIds);
            Iterator<Persoon> persoonIterator = antwoord.getPersonen().iterator();
            while (persoonIterator.hasNext()) {
                Long persoonId = persoonIterator.next().getId();
                if (personenToegangMap.get(persoonId) == Boolean.FALSE) {
                    persoonIterator.remove();
                    LOGGER.info(String.format("Persoon %s is vanwege horizontaleautorisatie uit het antwoord "
                            + "gefiltert voor bericht %d en partij %s.", persoonId, context.getIngaandBerichtId(),
                            context.getPartijId()));
                }
            }
        } catch (ParserException e) {
            LOGGER.error(String.format(
                    "Verwerking van bericht %s gestopt vanwege parseer fouten van populatie criteria.",
                    context.getIngaandBerichtId()), e);
            antwoord.voegFoutToe(new BerichtVerwerkingsFout(
                    BerichtVerwerkingsFoutCode.BRAU0046_01_POPULATIE_CRITERIA_PARSEER_FOUT,
                    BerichtVerwerkingsFoutZwaarte.FOUT));
            resultaat = STOP_VERWERKING;
        }

        return resultaat;
    }

}
