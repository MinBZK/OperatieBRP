/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpAttribuutReferentieExpressie;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.util.PersoonHisVolledigViewUtil;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

/**
 * Deze stap zorgt voor voorbereiding voor de verdere verwerking. Hierbij worden bijvoorbeeld cache maps gemaakt die in verdere verwerking benaderd kunnen
 * worden voor een snelle afhandeling.
 */
public class VoorbereidVerwerkingStap extends AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht, OnderhoudAfnemerindicatiesBerichtContext,
    OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private StamTabelService stamTabelService;

    @Inject
    private ExpressieService expressieService;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp,
        final OnderhoudAfnemerindicatiesBerichtContext context,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        final List<PersoonHisVolledig> persoonHisVolledigs = Collections.singletonList(context.getPersoonHisVolledig());

        final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap = bouwPersoonAttributenMap(persoonHisVolledigs);
        context.setBijgehoudenPersonenAttributenMap(persoonAttributenMap);

        final Map<Integer, Map<Integer, List<Attribuut>>> elementAttributenMap = bouwElementAttributenMap(persoonAttributenMap);

        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = bouwPersoonOnderzoekenMap(persoonHisVolledigs,
            persoonAttributenMap, elementAttributenMap);
        context.setPersoonOnderzoekenMap(persoonOnderzoekenMap);

        return DOORGAAN;
    }

    /**
     * Bouwt een map voor persoon attributen.
     *
     * @param bijgehoudenPersonenVolledig bijgehouden personen volledig
     * @return map met attributen per persoon
     */
    private Map<Integer, Map<String, List<Attribuut>>> bouwPersoonAttributenMap(final List<PersoonHisVolledig> bijgehoudenPersonenVolledig) {
        final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap = new HashMap<>();
        final Collection<Element> alleElementen = stamTabelService.geefAlleElementen();
        final Map<String, ParserResultaat> parserResultaatMap = stamTabelService.geefAlleExpressieParserResultatenVanAttribuutElementenPerExpressie();
        for (final PersoonHisVolledig bijgehoudenPersoon : bijgehoudenPersonenVolledig) {
            // Verrijk de attributen met groepreferenties op de persoon dmv een tijdelijke persoonview.
            final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(bijgehoudenPersoon, null);
            PersoonHisVolledigViewUtil.initialiseerView(persoonHisVolledigView);

            final Map<String, List<Attribuut>> attributenMap = new HashMap<>();
            persoonAttributenMap.put(bijgehoudenPersoon.getID(), attributenMap);
            for (final Element element : alleElementen) {
                if (element.getSoort() == SoortElement.ATTRIBUUT && element.getExpressie() != null) {
                    final ParserResultaat geparsdeTotaleExpressie = parserResultaatMap.get(element.getExpressie().getWaarde());
                    if (geparsdeTotaleExpressie == null) {
                        continue;
                    }
                    try {
                        final Expressie expressie = expressieService.evalueer(geparsdeTotaleExpressie.getExpressie(), bijgehoudenPersoon);
                        final List<Attribuut> attributen = maakAttributenLijst(expressie);
                        attributenMap.put(element.getExpressie().getWaarde(), attributen);
                    } catch (final ExpressieExceptie expressieExceptie) {
                        LOGGER.error("Fout in het parsen van expressie", expressieExceptie);
                    }
                }
            }
        }
        return persoonAttributenMap;
    }

    /**
     * Maak een attributen lijst op basis van een element expressie.
     *
     * @param element element
     * @return lijst van attributen die door expressie geraakt zijn
     */
    private List<Attribuut> maakAttributenLijst(final Expressie element) {
        final List<Attribuut> lijstVanGeraakteAttributen = new ArrayList<>();
        if (element instanceof LijstExpressie) {
            final LijstExpressie historieElementen = (LijstExpressie) element;

            for (final Expressie subElement : historieElementen.getElementen()) {
                lijstVanGeraakteAttributen.addAll(maakAttributenLijst(subElement));
            }
        } else if (element instanceof BrpAttribuutReferentieExpressie) {
            final Attribuut attribuut = element.getAttribuut();
            if (attribuut != null) {
                lijstVanGeraakteAttributen.add(attribuut);
            }
        }
        return lijstVanGeraakteAttributen;
    }

    private Map<Integer, Map<Integer, List<Attribuut>>> bouwElementAttributenMap(final Map<Integer, Map<String, List<Attribuut>>> personenAttributenMap) {
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonElementAttributenMap = new HashMap<>();
        final Collection<Element> alleElementen = stamTabelService.geefAlleElementen();
        for (final Integer persoonId : personenAttributenMap.keySet()) {
            final Map<Integer, List<Attribuut>> elementAttributenMap = new HashMap<>();
            persoonElementAttributenMap.put(persoonId, elementAttributenMap);
            final Map<String, List<Attribuut>> persoonAttributenMap = personenAttributenMap.get(persoonId);
            for (final Element element : alleElementen) {
                if (SoortElement.ATTRIBUUT == element.getSoort()) {
                    final Integer objectId = element.getObjecttype().getID();
                    final Integer groepId = element.getGroep().getID();
                    final ExpressietekstAttribuut expressie = element.getExpressie();
                    if (expressie == null || expressie.getWaarde() == null) {
                        continue;
                    }
                    final List<Attribuut> attributen = persoonAttributenMap.get(expressie.getWaarde());
                    List<Attribuut> attributenVoorGroep = elementAttributenMap.get(groepId);
                    if (attributenVoorGroep == null) {
                        attributenVoorGroep = new ArrayList<>();
                        elementAttributenMap.put(groepId, attributenVoorGroep);
                    }
                    if (attributen != null) {
                        attributenVoorGroep.addAll(attributen);
                        List<Attribuut> attributenVoorObject = elementAttributenMap.get(objectId);
                        if (attributenVoorObject == null) {
                            attributenVoorObject = new ArrayList<>();
                            elementAttributenMap.put(objectId, attributenVoorObject);
                        }
                        attributenVoorObject.addAll(attributen);
                    }
                }
            }
        }
        return persoonElementAttributenMap;
    }

    /**
     * Bouw een map waarin per persoon onderzoeken zijn gemapped naar de desbetreffende attributen.
     *
     * @param bijgehoudenPersonenVolledig bijgehouden personen volledig
     * @param persoonAttributenMap        persoon attributen map
     * @return map met mapping tussen personen, onderzoeken en attributen
     */
    private Map<Integer, Map<Integer, List<Attribuut>>> bouwPersoonOnderzoekenMap(final List<PersoonHisVolledig> bijgehoudenPersonenVolledig,
        final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap,
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonElementAttributenMap)
    {
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = new HashMap<>();
        for (final PersoonHisVolledig bijgehoudenPersoon : bijgehoudenPersonenVolledig) {
            final Map<Integer, List<Attribuut>> attributenMap = new HashMap<>();
            final Map<Integer, List<Attribuut>> elementAttributenMap = persoonElementAttributenMap.get(bijgehoudenPersoon.getID());
            for (final PersoonOnderzoekHisVolledig persoonOnderzoek : bijgehoudenPersoon.getOnderzoeken()) {
                for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoek : persoonOnderzoek.getOnderzoek().getGegevensInOnderzoek()) {
                    // Enkel voor gegevens in onderzoek waarbij de voorkomensleutel gevuld is (attributen en groepen in onderzoek).
                    final Element elementInOnderzoek = gegevenInOnderzoek.getElement().getWaarde();
                    final Element elementUitStamtabel = stamTabelService.geefElementById(elementInOnderzoek.getID());
                    if (gegevenInOnderzoek.getVoorkomenSleutelGegeven() != null) {
                        final Long voorkomenSleutel = gegevenInOnderzoek.getVoorkomenSleutelGegeven().getWaarde();

                        if (elementUitStamtabel.getSoort() == SoortElement.ATTRIBUUT) {
                            final Map<String, List<Attribuut>> expressieAttributenMap = persoonAttributenMap.get(bijgehoudenPersoon.getID());

                            if (elementUitStamtabel.getExpressie() != null) {
                                final List<Attribuut> attributenVoorExpressie = geefAttributenLijst(elementUitStamtabel, expressieAttributenMap);
                                final List<Attribuut> attributen = geefAttributenDieInOnderzoekStaan(voorkomenSleutel, attributenVoorExpressie);
                                if (!attributen.isEmpty()) {
                                    attributenMap.put(gegevenInOnderzoek.getID(), attributen);
                                }
                            } else {
                                LOGGER.warn("Er is een onderzoek geplaatst op een element dat geen expressie heeft: {}",
                                    elementUitStamtabel.getNaam().getWaarde());
                            }
                        } else if (elementUitStamtabel.getSoort() == SoortElement.GROEP) {
                            final List<Attribuut> groepAttributen = elementAttributenMap.get(gegevenInOnderzoek.getElement().getWaarde().getID());
                            if (groepAttributen != null) {
                                final List<Attribuut> attributenInOnderzoek = geefAttributenDieInOnderzoekStaan(voorkomenSleutel, groepAttributen);
                                if (!attributenInOnderzoek.isEmpty()) {
                                    attributenMap.put(gegevenInOnderzoek.getID(), attributenInOnderzoek);
                                }
                            }
                        }
                    } else if (gegevenInOnderzoek.getObjectSleutelGegeven() != null) {
                        if (elementUitStamtabel.getSoort() == SoortElement.OBJECTTYPE) {
                            final List<Attribuut> objectAttributen = elementAttributenMap.get(gegevenInOnderzoek.getElement().getWaarde().getID());
                            if (objectAttributen != null) {
                                attributenMap.put(gegevenInOnderzoek.getID(), objectAttributen);
                            }
                        } else if (elementUitStamtabel.getSoort() == SoortElement.ATTRIBUUT) {
                            final Map<String, List<Attribuut>> expressieAttributenMap = persoonAttributenMap.get(bijgehoudenPersoon.getID());

                            if (elementUitStamtabel.getExpressie() != null) {
                                final List<Attribuut> attributenVoorExpressie = geefAttributenLijst(elementUitStamtabel, expressieAttributenMap);
                                if (attributenVoorExpressie != null) {
                                    attributenMap.put(gegevenInOnderzoek.getID(), attributenVoorExpressie);
                                }
                            }
                        }
                    }
                }
            }

            persoonOnderzoekenMap.put(bijgehoudenPersoon.getID(), attributenMap);

        }

        return persoonOnderzoekenMap;
    }

    /**
     * Geef het attribuut dat in onderzoek staat.
     *
     * @param voorkomenSleutel de voorkomen sleutel van het attribuut dat in onderzoek staat.
     * @param attributen       lijst met attributen
     * @return het attribuut dat in onderzoek staat
     */
    private List<Attribuut> geefAttributenDieInOnderzoekStaan(final Long voorkomenSleutel, final List<Attribuut> attributen) {
        final List<Attribuut> attributenInOnderzoek = new ArrayList<>();
        for (final Attribuut attribuut : attributen) {
            final Groep groep = attribuut.getGroep();
            if (groep instanceof ModelIdentificeerbaar) {
                final ModelIdentificeerbaar<?> modelIdentificeerbaar = (ModelIdentificeerbaar<?>) groep;
                if (modelIdentificeerbaar.getID().longValue() == voorkomenSleutel) {
                    attributenInOnderzoek.add(attribuut);
                }
            }
        }
        return attributenInOnderzoek;
    }

    /**
     * Geef attributen lijst uit expressieAttributenMap.
     *
     * @param elementUitStamtabel    element uit stamtabel
     * @param expressieAttributenMap expressie attributen map
     * @return lijst met attributen, anders lege lijst
     */
    private List<Attribuut> geefAttributenLijst(final Element elementUitStamtabel, final Map<String, List<Attribuut>> expressieAttributenMap) {
        final List<Attribuut> attributen = expressieAttributenMap.get(elementUitStamtabel.getExpressie().getWaarde());
        if (attributen != null) {
            return attributen;
        } else {
            return Collections.emptyList();
        }
    }
}
