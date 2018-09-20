/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.test.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import nl.bzk.brp.toegangsbewaking.test.controller.editor.FunctieadresEditor;
import nl.bzk.brp.toegangsbewaking.test.controller.editor.GemeenteEditor;
import nl.bzk.brp.toegangsbewaking.test.controller.editor.GeslachtsAanduidingEditor;
import nl.bzk.brp.toegangsbewaking.test.controller.editor.LandEditor;
import nl.bzk.brp.toegangsbewaking.test.controller.editor.PlaatsEditor;
import nl.bzk.brp.toegangsbewaking.test.controller.editor.VerantwoordelijkeEditor;
import nl.bzk.brp.toegangsbewaking.test.dao.PersoonDAO;
import nl.bzk.brp.toegangsbewaking.test.model.Functieadres;
import nl.bzk.brp.toegangsbewaking.test.model.Gem;
import nl.bzk.brp.toegangsbewaking.test.model.Geslachtsaand;
import nl.bzk.brp.toegangsbewaking.test.model.Land;
import nl.bzk.brp.toegangsbewaking.test.model.Pers;
import nl.bzk.brp.toegangsbewaking.test.model.Persadres;
import nl.bzk.brp.toegangsbewaking.test.model.Plaats;
import nl.bzk.brp.toegangsbewaking.test.model.Rdnopschorting;
import nl.bzk.brp.toegangsbewaking.test.model.Rdnwijzadres;
import nl.bzk.brp.toegangsbewaking.test.model.Srtpers;
import nl.bzk.brp.toegangsbewaking.test.model.Verantwoordelijke;
import nl.bzk.brp.toegangsbewaking.test.model.Verblijfsr;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PersoonController implements InitializingBean {

    @Autowired
    private PersoonDAO              persoonDao;

    private List<Geslachtsaand>     geslachtsAanduidingen;
    private List<Plaats>            plaatsen;
    private List<Gem>               gemeentes;
    private List<Land>              landen;
    private List<Verantwoordelijke> verantwoordelijken;
    private List<Functieadres>      adresFuncties;

    /**
     * For every request for this controller, this will
     * create a person instance for the form.
     */
    @ModelAttribute(value = "persoon")
    public Pers newRequest(@RequestParam(required = false, value = "id") Long id) {
        Pers persoon;
        try {
            persoon = ((id != null && id > 0) ? persoonDao.vindPersMiddelsId(id) : nieuwPersoon());
        } catch (NoResultException e) {
            persoon = nieuwPersoon();
        }
        return persoon;
    }
    
    private Pers nieuwPersoon() {
        Pers persoon = new Pers();
        Persadres adres = new Persadres();
        adres.setPers(persoon);
        
        Set<Persadres> adressen = new HashSet<Persadres>();
        adressen.add(adres);
        persoon.setPersadreses(adressen);

        return persoon;
    }

    /**
     * <p>
     * Person form request.
     * </p>
     * 
     * <p>
     * Expected HTTP GET and request '/person/form'.
     * </p>
     */
    @RequestMapping(value = "/persoon/form", method = RequestMethod.GET)
    public void form() {
    }

    /**
     * <p>
     * Saves a person.
     * </p>
     * 
     * <p>
     * Expected HTTP POST and request '/person/form'.
     * </p>
     */
    @RequestMapping(value = "/persoon/form", method = RequestMethod.POST)
    public String form(@ModelAttribute(value = "persoon") @Valid final Pers persoon, final Errors errors,
            final Model model)
    {
        if (persoon != null && !errors.hasErrors()) {
            persoon.setSrtpers(new Srtpers((short) 1, "d", "default"));
            persoon.setVerblijfsr(new Verblijfsr(1, "default"));
            persoon.setRdnopschorting(new Rdnopschorting((short) 4, "?", "Onbekend"));
            persoon.setTijdstiplaatstewijz(new Date());
            persoon.setDataanvverblijfsr(0);
            persoon.setLandByLandoverlijden(new Land(1, "Nederland"));
            persoon.setDatoverlijden(0);
            persoon.setVersienr(1);

            if (!persoon.getPersadreses().isEmpty()) {
                Persadres adres = (Persadres) persoon.getPersadreses().toArray()[0];
                adres.setRdnwijzadres(new Rdnwijzadres((short) 1, "P", "Aangifte door persoon"));
                adres.setDataanvadresh(20111003);
                adres.setDatvertrekuitnederland(0);
            }

            Pers newPers = persoonDao.opslaan(persoon);

            // set id from create
            if (persoon.getId() == 0) {
                persoon.setId(newPers.getId());
            }

            model.addAttribute("statusMessageKey", "persoon.form.msg.success");
        }
        return "/persoon/form";
    }

    /**
     * <p>
     * Deletes a person.
     * </p>
     * 
     * <p>
     * Expected HTTP POST and request '/person/delete'.
     * </p>
     */
    @RequestMapping(value = "/persoon/delete", method = RequestMethod.POST)
    public String delete(final Pers persoon) {
        persoonDao.verwijderen(persoon);

        return "redirect:zoek.html";
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Geslachtsaand.class, new GeslachtsAanduidingEditor(getGeslachtsAanduidingen()));
        binder.registerCustomEditor(Gem.class, new GemeenteEditor(getGemeentes()));
        binder.registerCustomEditor(Land.class, new LandEditor(getLanden()));
        binder.registerCustomEditor(Verantwoordelijke.class, new VerantwoordelijkeEditor(getVerantwoordelijken()));
        binder.registerCustomEditor(Functieadres.class, new FunctieadresEditor(getAdresFuncties()));
        binder.registerCustomEditor(Plaats.class, new PlaatsEditor(getPlaatsen()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        geslachtsAanduidingen = persoonDao.getAlleGeslachtsAanduidingen();
        gemeentes = persoonDao.getAlleGemeentes();
        landen = persoonDao.getAlleLanden();
        verantwoordelijken = persoonDao.getAlleVerantwoordelijken();
        adresFuncties = persoonDao.getAlleAdresFuncties();
        plaatsen = persoonDao.getAllePlaatsen();
    }

    @ModelAttribute("geslachtsAanduidingen")
    public List<Geslachtsaand> getGeslachtsAanduidingen() {
        return geslachtsAanduidingen;
    }

    @ModelAttribute("gemeentes")
    public List<Gem> getGemeentes() {
        return gemeentes;
    }

    @ModelAttribute("plaatsen")
    public List<Plaats> getPlaatsen() {
        return plaatsen;
    }

    @ModelAttribute("landen")
    public List<Land> getLanden() {
        return landen;
    }

    @ModelAttribute("verantwoordelijken")
    public List<Verantwoordelijke> getVerantwoordelijken() {
        return verantwoordelijken;
    }

    @ModelAttribute("adresFuncties")
    public List<Functieadres> getAdresFuncties() {
        return adresFuncties;
    }

}
