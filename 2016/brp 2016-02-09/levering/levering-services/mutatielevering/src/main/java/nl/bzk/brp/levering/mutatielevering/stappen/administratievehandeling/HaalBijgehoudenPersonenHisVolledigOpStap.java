/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpAttribuutReferentieExpressie;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.mutatielevering.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.PersoonHisVolledigViewUtil;
import org.perf4j.aop.Profiled;


/**
 * Deze stap haalt de PersoonHisVolledig objecten op voor de bijgehouden personen en plaatst deze op de context.
 */
public class HaalBijgehoudenPersonenHisVolledigOpStap extends AbstractAdministratieveHandelingVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BlobifierService blobifierService;

    @Inject
    private StamTabelService stamTabelService;

    @Inject
    private ExpressieService expressieService;

    @Override
    @Profiled(tag = "HaalBijgehoudenPersonenHisVolledigOpStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
        final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        final List<Integer> bijgehoudenPersoonIds = context.getBijgehoudenPersoonIds();

        final List<PersoonHisVolledig> bijgehoudenPersonenVolledig = haalBijgehoudenPersonenVolledig(bijgehoudenPersoonIds);
        context.setBijgehoudenPersonenVolledig(bijgehoudenPersonenVolledig);

        LOGGER.debug("Bijgehouden personen opgehaald.");

        final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap = bouwPersoonAttributenMap(bijgehoudenPersonenVolledig);
        context.setBijgehoudenPersonenAttributenMap(persoonAttributenMap);

        final Map<Integer, Map<Integer, List<Attribuut>>> elementAttributenMap = bouwElementAttributenMap(persoonAttributenMap);
        LOGGER.debug("Bijgehouden personen element attributen map opgebouwd.");

        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = bouwPersoonOnderzoekenMap(bijgehoudenPersonenVolledig,
            persoonAttributenMap, elementAttributenMap);
        context.setPersoonOnderzoekenMap(persoonOnderzoekenMap);

        LOGGER.debug("Bijgehouden personen attributen map opgebouwd.");

        return DOORGAAN;
    }

    /**
     * Bouw de persoon attributen map waarmee snel naar attributen kan worden gegaan gegroepeerd per persoon.
     *
     * @param bijgehoudenPersonenVolledig bijgehouden personen volledig
     * @return de map
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
                        LOGGER.error("Fout in het evalueren van expressie op persoon: " + element.getExpressie(), expressieExceptie);
                    }
                }
            }
        }
        return persoonAttributenMap;
    }

    private Map<Integer, Map<Integer, List<Attribuut>>> bouwElementAttributenMap(final Map<Integer, Map<String, List<Attribuut>>> personenAttributenMap) {
        //we zouden eigenlijk al een map maet objectsleutel en voorkomensleuten moeten maken. Wat lastig nu.
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonElementAttributenMap = new HashMap<>();
        final Collection<Element> alleElementen = stamTabelService.geefAlleElementen();
        for (final Integer persoonId : personenAttributenMap.keySet()) {
            final Map<Integer, List<Attribuut>> elementAttributenMap = new HashMap<>();
            persoonElementAttributenMap.put(persoonId, elementAttributenMap);
            final Map<String, List<Attribuut>> persoonAttributenMap = personenAttributenMap.get(persoonId);
            for (final Element element : alleElementen) {
                if (SoortElement.ATTRIBUUT == element.getSoort()) {
                    if (element.getExpressie() == null || element.getExpressie().getWaarde() == null) {
                        continue;
                    }
                    final Integer objectId = element.getObjecttype().getID();
                    final Integer groepId = element.getGroep().getID();
                    final List<Attribuut> attributen = persoonAttributenMap.get(element.getExpressie().getWaarde());
                    if (attributen == null) {
                        continue;
                    }
                    List<Attribuut> attributenVoorGroep = elementAttributenMap.get(groepId);
                    if (attributenVoorGroep == null) {
                        attributenVoorGroep = new ArrayList<>();
                        elementAttributenMap.put(groepId, attributenVoorGroep);
                    }
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

    private List<Attribuut> geefAttributenLijst(final Element elementUitStamtabel, final Map<String, List<Attribuut>> expressieAttributenMap) {
        final List<Attribuut> attributen = expressieAttributenMap.get(elementUitStamtabel.getExpressie().getWaarde());
        if (attributen != null) {
            return attributen;
        } else {
            return Collections.emptyList();
        }
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

    /**
     * Haal de bijgehouden personen volledig uit de database.
     *
     * @param bijgehoudenPersonenIds de bijgehouden personen ids
     * @return de lijst met bijgehouden personen
     */
    private List<PersoonHisVolledig> haalBijgehoudenPersonenVolledig(final List<Integer> bijgehoudenPersonenIds) {
        final List<? extends PersoonHisVolledig> bijgehoudenPersonenVolledig = blobifierService.leesBlobs(bijgehoudenPersonenIds);

        if (bijgehoudenPersonenVolledig == null || bijgehoudenPersonenIds.size() != bijgehoudenPersonenVolledig.size()) {
            throw new DataNietAanwezigExceptie("Niet alle persoon volledigs zijn gevonden. Bijgehouden persoon ids: " + bijgehoudenPersonenIds.toString());
        }

        final List<PersoonHisVolledig> resultaat = new ArrayList<>();
        resultaat.addAll(bijgehoudenPersonenVolledig);

        return resultaat;
    }

}
