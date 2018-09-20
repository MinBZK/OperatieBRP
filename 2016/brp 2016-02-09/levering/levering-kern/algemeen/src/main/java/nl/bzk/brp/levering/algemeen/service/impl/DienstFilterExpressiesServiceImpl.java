/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.algemeen.service.DienstFilterExpressiesService;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.business.cache.CacheVerversEvent;
import nl.bzk.brp.levering.business.expressietaal.LijstExpressieBouwer;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de DienstFilterExpressiesService.
 */
@Service
@Regels({ Regel.VR00081, Regel.VR00082, Regel.VR00083 })
public class DienstFilterExpressiesServiceImpl implements DienstFilterExpressiesService,
    ApplicationListener<CacheVerversEvent>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private StamTabelService stamTabelService;


    @Override
    @Cacheable(value = "ExpressiesVoorHistorieEnVerantwoordingAttributenLijstCache", key = "#dienst.iD")
    public final List<String> geefExpressiesVoorHistorieEnVerantwoordingAttributenLijst(final Dienst dienst) throws ExpressieExceptie {
        final Collection<Element> alleElementen = stamTabelService.geefAlleElementen();
        final List<String> expressies = new ArrayList<>();
        for (final DienstbundelGroep dienstbundelGroep : dienst.getDienstbundel().getDienstbundelGroepen()) {
            if (dienstbundelGroep.getIndicatieFormeleHistorie().getWaarde()) {
                final List<ExpressietekstAttribuut> expressiesVoorDezeGroep =
                    haalExpressiesVoorGroep(alleElementen, dienstbundelGroep, DATUM_TIJD_REGISTRATIE, DATUM_TIJD_VERVAL);
                for (final ExpressietekstAttribuut expressietekstAttribuut : expressiesVoorDezeGroep) {
                    expressies.add(expressietekstAttribuut.getWaarde());
                }
            }

            if (dienstbundelGroep.getIndicatieMaterieleHistorie().getWaarde()) {

                final List<ExpressietekstAttribuut> expressiesVoorDezeGroep = haalExpressiesVoorGroep(alleElementen, dienstbundelGroep,
                    DATUM_EINDE_GELDIGHEID);
                for (final ExpressietekstAttribuut expressietekstAttribuut : expressiesVoorDezeGroep) {
                    expressies.add(expressietekstAttribuut.getWaarde());
                }
            }

            if (dienstbundelGroep.getIndicatieVerantwoording().getWaarde()) {
                final List<ExpressietekstAttribuut> expressiesVoorDezeGroep =
                    haalExpressiesVoorGroep(alleElementen, dienstbundelGroep, BRP_ACTIE_INHOUD, BRP_ACTIE_VERVAL, BRP_ACTIE_AANPASSING_GELDIGHEID);
                for (final ExpressietekstAttribuut expressietekstAttribuut : expressiesVoorDezeGroep) {
                    expressies.add(expressietekstAttribuut.getWaarde());
                }
            }
        }
        return expressies;
    }

    @Override
    @Cacheable(value = "ExpressiesVoorHistorieEnVerantwoordingAttributenCache", key = "#dienst.iD")
    public final Expressie geefExpressiesVoorHistorieEnVerantwoordingAttributen(final Dienst dienst) throws ExpressieExceptie {
        final LijstExpressieBouwer lijstExpressieBouwer = new LijstExpressieBouwer();
        final Collection<Element> alleElementen = stamTabelService.geefAlleElementen();
        for (final DienstbundelGroep dienstbundelGroep : dienst.getDienstbundel().getDienstbundelGroepen()) {
            if (dienstbundelGroep.getIndicatieFormeleHistorie().getWaarde()) {
                voegExpressiesToeVoorFormeleHistorie(alleElementen, lijstExpressieBouwer, dienstbundelGroep);
            }

            if (dienstbundelGroep.getIndicatieMaterieleHistorie().getWaarde()) {
                voegExpressiesToeVoorMaterieleHistorie(alleElementen, lijstExpressieBouwer, dienstbundelGroep);
            }

            if (dienstbundelGroep.getIndicatieVerantwoording().getWaarde()) {
                voegExpressiesToeVoorVerantwoordingsinfo(alleElementen, lijstExpressieBouwer, dienstbundelGroep);
            }
        }

        return lijstExpressieBouwer.geefTotaleGeparsdeExpressie();
    }

    @Override
    @Cacheable(value = "AllExpressiesVoorHistorieEnVerantwoordingAttributenCache")
    public final Expressie geefAllExpressiesVoorHistorieEnVerantwoordingAttributen() throws ExpressieExceptie {
        final LijstExpressieBouwer lijstExpressieBouwer = new LijstExpressieBouwer();
        final Collection<Element> alleElementen = stamTabelService.geefAlleElementen();
        final List<ExpressietekstAttribuut> expressieDelen = haalAlleExpressiesVoorAlleGroepen(alleElementen,
            DATUM_TIJD_REGISTRATIE,
            DATUM_TIJD_VERVAL,
            DATUM_EINDE_GELDIGHEID,
            BRP_ACTIE_INHOUD,
            BRP_ACTIE_VERVAL,
            BRP_ACTIE_AANPASSING_GELDIGHEID);
        lijstExpressieBouwer.voegExpressietekstDelenToe(expressieDelen);
        return lijstExpressieBouwer.geefTotaleGeparsdeExpressie();
    }


    @Override
    @Cacheable(value = "AllExpressiesVoorHistorieEnVerantwoordingAttributenLijstCache")
    public final List<String> geefAllExpressiesVoorHistorieEnVerantwoordingAttributenLijst() throws ExpressieExceptie {
        final Collection<Element> alleElementen = stamTabelService.geefAlleElementen();
        final List<ExpressietekstAttribuut> expressieDelen = haalAlleExpressiesVoorAlleGroepen(alleElementen,
            DATUM_TIJD_REGISTRATIE,
            DATUM_TIJD_VERVAL,
            DATUM_EINDE_GELDIGHEID,
            BRP_ACTIE_INHOUD,
            BRP_ACTIE_VERVAL,
            BRP_ACTIE_AANPASSING_GELDIGHEID);
        final List<String> expressies = new ArrayList<>();
        for (final ExpressietekstAttribuut expressietekstAttribuut : expressieDelen) {
            expressies.add(expressietekstAttribuut.getWaarde());
        }
        return expressies;
    }


    @Override
    @CacheEvict(value = {
        "AllExpressiesVoorHistorieEnVerantwoordingAttributenCache",
        "AllExpressiesVoorHistorieEnVerantwoordingAttributenLijstCache",
        "ExpressiesVoorHistorieEnVerantwoordingAttributenCache",
        "ExpressiesVoorHistorieEnVerantwoordingAttributenLijstCache" }, allEntries = true)
    public final void onApplicationEvent(final CacheVerversEvent cacheVerversEvent) {
        //evict
    }

    /**
     * Voegt expressies toe voor elementen met formele historie.
     *
     * @param lijstExpressieBouwer de lijst expressie bouwer
     * @param groepModel           het groep element
     */
    private void voegExpressiesToeVoorFormeleHistorie(final Collection<Element> alleElementen,
        final LijstExpressieBouwer lijstExpressieBouwer,
        final DienstbundelGroep groepModel)
    {
        final List<ExpressietekstAttribuut> expressiesVoorDezeGroep = haalExpressiesVoorGroep(alleElementen,
            groepModel,
            DATUM_TIJD_REGISTRATIE,
            DATUM_TIJD_VERVAL);
        lijstExpressieBouwer.voegExpressietekstDelenToe(expressiesVoorDezeGroep);
    }


    /**
     * Voegt expressies toe voor elementen met materiele historie.
     *
     * @param lijstExpressieBouwer de lijst expressie bouwer
     * @param groepModel           het groep element
     */
    private void voegExpressiesToeVoorMaterieleHistorie(final Collection<Element> alleElementen,
        final LijstExpressieBouwer lijstExpressieBouwer,
        final DienstbundelGroep groepModel)
    {
        final List<ExpressietekstAttribuut> expressiesVoorDezeGroep = haalExpressiesVoorGroep(alleElementen, groepModel,
            DATUM_EINDE_GELDIGHEID);
        lijstExpressieBouwer.voegExpressietekstDelenToe(expressiesVoorDezeGroep);
    }

    /**
     * Voegt expressies toe voor elementen die verantwoordingsinfo voorstellen.
     *
     * @param lijstExpressieBouwer the lijst expressie bouwer
     * @param groepModel           the groep element
     */
    private void voegExpressiesToeVoorVerantwoordingsinfo(final Collection<Element> alleElementen,
        final LijstExpressieBouwer lijstExpressieBouwer,
        final DienstbundelGroep groepModel)
    {
        final List<ExpressietekstAttribuut> expressiesVoorDezeGroep = haalExpressiesVoorGroep(alleElementen,
            groepModel,
            BRP_ACTIE_INHOUD,
            BRP_ACTIE_VERVAL,
            BRP_ACTIE_AANPASSING_GELDIGHEID);
        lijstExpressieBouwer.voegExpressietekstDelenToe(expressiesVoorDezeGroep);
    }

    /**
     * Haalt alle expressies voor alle groepen.
     *
     * @param elementNamen element namen
     * @return lijst met expressies
     */
    private List<ExpressietekstAttribuut> haalAlleExpressiesVoorAlleGroepen(final Collection<Element> alleElementen,
        final String... elementNamen)
    {
        final List<ExpressietekstAttribuut> expressietekstAttribuutList = new LinkedList<>();
        expressietekstAttribuutList.addAll(haalExpressiesVoorGroep(alleElementen, null, elementNamen));
        return expressietekstAttribuutList;
    }

    /**
     * Haalt expressies op voor elementen die behoren tot de optioneel gegeven groep en voldoen aan de gegeven lijst van namen.
     *
     * @param groepModelBeperking geef enkel expressies van elementen die behoren tot de gegeven groep
     * @param elementNamen        filter voor element namen
     * @return lijst met elementen
     */
    private List<ExpressietekstAttribuut> haalExpressiesVoorGroep(final Collection<Element> alleElementen,
        final DienstbundelGroep groepModelBeperking,
        final String... elementNamen)
    {
        final Set<String> elementNaamSet = new HashSet<>(Arrays.asList(elementNamen));
        final List<ExpressietekstAttribuut> expressietekstAttribuutList = new LinkedList<>();
        for (final Element element : alleElementen) {
            if (groepModelBeperking != null && element.getGroep() != null && !element.getGroep().getNaam().
                getWaarde().equals(
                groepModelBeperking
                    .getGroep().getNaam().getWaarde()))
            {
                continue;
            }

            if (elementNaamSet.contains(element.getElementNaam().getWaarde()) && element.getExpressie() != null
                && StringUtils.isNotBlank(element.getExpressie().getWaarde()))
            {
                expressietekstAttribuutList.add(element.getExpressie());
            }
        }
        return expressietekstAttribuutList;
    }


}
