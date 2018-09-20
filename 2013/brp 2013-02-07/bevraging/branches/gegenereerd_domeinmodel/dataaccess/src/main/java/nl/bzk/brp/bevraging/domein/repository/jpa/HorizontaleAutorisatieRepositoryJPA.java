/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import org.springframework.stereotype.Repository;


/**
 * Standaard JPA implementatie van de {@link HorizontaleAutorisatieRepository} interface.
 */
@Repository
public class HorizontaleAutorisatieRepositoryJPA implements HorizontaleAutorisatieRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorizontaleAutorisatieRepositoryJPA.class);

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> filterPersonenBijFilter(final List<Long> persoonIds, final String... populatieCriteria)
        throws ParserException
    {
        String[] filters = new String[populatieCriteria.length];
        for (int i = 0; i < filters.length; i++) {
            filters[i] = getFilterVoorCriteriumRegel(populatieCriteria[i]);
        }

        List<Long> resultaat = null;
        List<String> valideFilters = filterValideFilters(filters);

        if (valideFilters.isEmpty()) {
            resultaat = persoonIds;
        } else {
            StringBuilder queryString = new StringBuilder("SELECT p.id FROM Persoon p WHERE p.id IN :persoonIds");
            for (String filter : valideFilters) {
                queryString.append(" AND (").append(filter).append(")");
            }
            Query query = em.createQuery(queryString.toString());
            query.setParameter("persoonIds", persoonIds);

            resultaat = query.getResultList();
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


    /**
     * Retourneert een lijst van de valide opgegeven populatieCriteria, waarin alleen de niet <code>null</code> en niet lege
     * populatieCriteria worden opgenomen.
     *
     * @param filters de populatieCriteria die moeten worden gefilterd.
     * @return een lijst van de valide populatieCriteria.
     */
    private List<String> filterValideFilters(final String[] filters) {
        List<String> valideFilters = new ArrayList<String>();
        for (String filter : filters) {
            if (filter != null && !filter.trim().isEmpty()) {
                valideFilters.add(filter);
            }
        }
        return valideFilters;
    }

}
