/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.toegangsbewaking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.AbonnementSoortBericht;
import nl.bzk.brp.bevraging.domein.repository.HorizontaleAutorisatieRepository;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import org.springframework.stereotype.Service;


/**
 * Standaard implementatie van de {@link ToegangsBewakingService} welke op basis van een opgegeven abonnement en het
 * bericht de autorisatie kan uitvoeren.
 */
@Service
public class StandaardToegangsBewakingService implements ToegangsBewakingService {

    @Inject
    private HorizontaleAutorisatieRepository horizontaleAutorisatieRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFunctioneelGeautoriseerd(final Abonnement abonnement,
            final BerichtVerzoek<? extends BerichtAntwoord> verzoek)
    {
        if (abonnement == null || verzoek == null) {
            throw new IllegalArgumentException("Abonnement en verzoek parameters mogen niet null zijn.");
        }

        SoortBericht soortBericht = verzoek.getSoortBericht();
        return bevatSoortBerichtenSetSpecifiekSoortBericht(abonnement.getSoortBerichten(), soortBericht);
    }

    /**
     * Controleert of de opgegeven {@link AbonnementSoortBericht} set de opgegeven {@link SoortBericht} bevat.
     *
     * @param soortBerichten de set aan abonnement specifieke soort berichten
     * @param soortBericht het soort bericht dat wordt gecontroleerd.
     * @return indicatie of het opgegeven soort bericht in de opgegeven set aanwezig is.
     */
    private boolean bevatSoortBerichtenSetSpecifiekSoortBericht(final Set<AbonnementSoortBericht> soortBerichten,
            final SoortBericht soortBericht)
    {
        if (soortBerichten == null) {
            return false;
        }

        boolean resultaat = false;
        for (AbonnementSoortBericht abonnementSoortBericht : soortBerichten) {
            if (abonnementSoortBericht.getSoortBericht() == soortBericht) {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     * @brp.bedrijfsregel BRAU0046
     */
    @Override
    public Map<Long, Boolean> controleerLijstVanPersonenVoorAbonnement(final Abonnement abonnement,
            final List<Long> persoonIds) throws ParserException
    {
        if (abonnement == null || persoonIds == null) {
            throw new IllegalArgumentException("Abonnement en persoonIds parameters mogen niet null zijn.");
        }

        Map<Long, Boolean> resultaat = new HashMap<Long, Boolean>();
        for (Long persoonId : persoonIds) {
            resultaat.put(persoonId, Boolean.FALSE);
        }

        String hoofdPopulatieCriterium = abonnement.getDoelBinding().getPopulatieCriterium();
        String subPopulatieCriterium = abonnement.getPopulatieCriterium();

        if (!(hoofdPopulatieCriterium == null || hoofdPopulatieCriterium.isEmpty())
                || !(subPopulatieCriterium == null || subPopulatieCriterium.isEmpty()))
        {
            List<Long> personen =
                horizontaleAutorisatieRepository.filterPersonenBijFilter(persoonIds, hoofdPopulatieCriterium,
                        subPopulatieCriterium);
            for (Entry<Long, Boolean> persoonMapping : resultaat.entrySet()) {
                persoonMapping.setValue(personen.contains(persoonMapping.getKey()));
            }
        } else {
            for (Entry<Long, Boolean> persoonMapping : resultaat.entrySet()) {
                persoonMapping.setValue(Boolean.TRUE);
            }
        }

        return resultaat;
    }

}
