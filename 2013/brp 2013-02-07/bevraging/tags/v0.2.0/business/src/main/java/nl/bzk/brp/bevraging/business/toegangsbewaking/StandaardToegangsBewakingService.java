/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.toegangsbewaking;

import java.util.Arrays;
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
import nl.bzk.brp.toegangsbewaking.parser.ParseTree;
import nl.bzk.brp.toegangsbewaking.parser.Parser;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.grammar.DefaultGrammar;
import nl.bzk.brp.toegangsbewaking.parser.processer.JPQLFilterProcesser;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.AbstractTokenizer;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.DefaultTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Standaard implementatie van de {@link ToegangsBewakingService} welke op basis van een opgegeven abonnement en het
 * bericht de autorisatie kan uitvoeren.
 */
@Service
public class StandaardToegangsBewakingService implements ToegangsBewakingService {

    private static final Logger            LOGGER = LoggerFactory.getLogger(StandaardToegangsBewakingService.class);

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

        boolean result = false;
        for (AbonnementSoortBericht abonnementSoortBericht : soortBerichten) {
            if (abonnementSoortBericht.getSoortBericht() == soortBericht) {
                result = true;
                break;
            }
        }
        return result;
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

        String hoofdFilter = getFilterVoorCriteriumRegel(abonnement.getDoelBinding().getPopulatieCriterium());
        String subFilter = getFilterVoorCriteriumRegel(abonnement.getPopulatieCriterium());

        if (!hoofdFilter.isEmpty() || !subFilter.isEmpty()) {
            List<Long> personen =
                horizontaleAutorisatieRepository.filterPersonenBijFilter(persoonIds, hoofdFilter, subFilter);
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

    /**
     * Genereert de query filter op basis van een populatiecriterium.
     *
     * @param criteriumRegel het populatiecriterium dat moet worden omgezet.
     * @return de specifieke query filter voor het opgegeven populatiecriterium.
     * @throws ParserException indien het parsen van de criteriumregel fout gaat.
     */
    private String getFilterVoorCriteriumRegel(final String criteriumRegel) throws ParserException {
        String filter = "";

        if (criteriumRegel != null && !criteriumRegel.trim().isEmpty()) {
            AbstractTokenizer tokenizer = new DefaultTokenizer(Arrays.asList(criteriumRegel));
            ParseTree parseTree = null;
            tokenizer.execute();

            Parser parser = new Parser(new DefaultGrammar());
            parseTree = parser.bouwParseTree(tokenizer.getTokens());

            filter = getFilterByParseTree(parseTree);
            LOGGER.debug("FILTER: " + filter);
        }
        return filter;
    }

    /**
     * Genereert de filter op basis van een {@link ParseTree}.
     *
     * @param parseTree de parsetree die moet leiden tot de filter.
     * @return de JPQL filter.
     * @throws ParserException indien er problemen zijn bij het processen van de parse tree.
     */
    private String getFilterByParseTree(final ParseTree parseTree) throws ParserException {
        // TODO: Processor dient via Spring geconfigureerd te worden; via Inject
        JPQLFilterProcesser proc = new JPQLFilterProcesser();
        return proc.process(parseTree);
    }

}
