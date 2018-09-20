/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.test.controller;

import java.util.Arrays;
import java.util.Collection;

import nl.bzk.brp.toegangsbewaking.parser.ParseTree;
import nl.bzk.brp.toegangsbewaking.parser.Parser;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.grammar.DefaultGrammar;
import nl.bzk.brp.toegangsbewaking.parser.processer.JPQLFilterProcesser;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.AbstractTokenizer;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.DefaultTokenizer;
import nl.bzk.brp.toegangsbewaking.test.controller.form.ZoekForm;
import nl.bzk.brp.toegangsbewaking.test.controller.form.ZoekTimingResult;
import nl.bzk.brp.toegangsbewaking.test.dao.PersoonDAO;
import nl.bzk.brp.toegangsbewaking.test.model.Pers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ZoekController {

    @Autowired
    private PersoonDAO persoonDao;

    /**
     * <p>
     * Searches for all persons and returns them in a <code>Collection</code>.
     * </p>
     * 
     * <p>
     * Expected HTTP GET and request '/person/search'.
     * </p>
     */
    @RequestMapping(value = "/persoon/zoek", method = RequestMethod.GET)
    public @ModelAttribute("zoekForm") ZoekForm search(Model model) {
        Collection<Pers> personen = persoonDao.getAllePersonen();
        model.addAttribute("personen", personen);
        return new ZoekForm();
    }

    /**
     * <p>
     * Searches for all persons and returns them in a <code>Collection</code>.
     * </p>
     * 
     * <p>
     * Expected HTTP GET and request '/person/search'.
     * </p>
     */
    @RequestMapping(value = "/persoon/zoek", method = RequestMethod.POST)
    public void search(@ModelAttribute("zoekForm") ZoekForm zoekForm, BindingResult result, Model model) {
        String constraint = zoekForm.getConstraint();
        String[] lines = constraint.split(System.getProperty("line.separator"));

        AbstractTokenizer tokenizer = new DefaultTokenizer(Arrays.asList(lines));
        String filter = "";

        if (!constraint.trim().isEmpty()) {
            ParseTree parseTree = null;
            ZoekTimingResult timing = new ZoekTimingResult();
            try {
                long start = System.currentTimeMillis();
                tokenizer.execute();
                timing.setTokenizing(System.currentTimeMillis() - start);

                start = System.currentTimeMillis();
                Parser parser = new Parser(new DefaultGrammar());
                parseTree = parser.bouwParseTree(tokenizer.getTokens());
                timing.setParsing(System.currentTimeMillis() - start);
            } catch (ParserException e) {
                e.printStackTrace();
                Object[] args = Arrays.asList(e.getMessage(), e.getLijnNr() + 1, e.getKarakterNr() + 1).toArray();
                result.rejectValue("constraint", "zoek.constraint.error", args, e.getMessage());
                return;
            }

            try {
                filter = getFilterByParseTree(parseTree);
                System.out.println("FILTER: " + filter);
            } catch (ParserException e) {
                e.printStackTrace();
            }

            model.addAttribute("parsetree", parseTree);
            model.addAttribute("timing", timing);
        }
        Collection<Pers> personen = persoonDao.getAllePersonen(filter);
        model.addAttribute("personen", personen);
    }

    private String getFilterByParseTree(final ParseTree parseTree) throws ParserException {
        JPQLFilterProcesser proc = new JPQLFilterProcesser();
        return proc.process(parseTree);
    }

}
