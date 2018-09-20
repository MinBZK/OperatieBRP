/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.expressietaal.impl;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.levering.business.cache.CacheVerversEvent;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.business.expressietaal.LijstExpressieBouwer;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;


/**
 * De implementatie van de expressieservice. Deze service biedt methoden die te maken hebben met expressies, zoals het evalueren van een expressie en het
 * opvragen van expressies bij een leveringsautorisatie en het combineren van populatiebperkingen voor specifieke diensten.
 */
@Service
public class ExpressieServiceImpl implements ExpressieService, ApplicationListener<CacheVerversEvent> {

    private static final Logger LOGGER                              = LoggerFactory.getLogger();
    private static final String TOTALE_POPULATIE_BEPERKING_TEMPLATE = "({0}) EN ({1}) EN ({2})";
    private static final String FOUT_BIJ_PARSEN_VAN_EXPRESSIE       = "Fout bij parsen van expressie: ";


    @Override
    public final Expressie evalueer(final Expressie expressie, final Persoon persoon) throws ExpressieExceptie {
        final Expressie resultaat = BRPExpressies.evalueer(expressie, persoon);
        return geefExpressieResultaat(expressie, resultaat);
    }

    @Override
    public final Expressie evalueer(final Expressie expressie, final PersoonHisVolledig persoon) throws
        ExpressieExceptie
    {
        final Expressie resultaat = BRPExpressies.evalueer(expressie, persoon);
        return geefExpressieResultaat(expressie, resultaat);
    }

    @Override
    @Regels(Regel.VR00052)
    @Cacheable(value = "AttributenFilterExpressieCache", key = "{ #dienst.iD, #rol.naam }")
    public final Expressie geefAttributenFilterExpressie(final Dienst dienst, final Rol rol) throws ExpressieExceptie {

        final Dienstbundel dienstbundel = dienst.getDienstbundel();
        final List<ExpressietekstAttribuut> expressies = dienstbundel.geefGeldigeExpressies(rol);
        final Iterator<ExpressietekstAttribuut> expressiesIterator = expressies.iterator();
        final LijstExpressieBouwer lijstExpressieBouwer = new LijstExpressieBouwer();

        if (expressiesIterator.hasNext()) {
            while (expressiesIterator.hasNext()) {
                final ExpressietekstAttribuut expressie = expressiesIterator.next();
                if (expressie != null) {
                    lijstExpressieBouwer.voegExpressieDeelToe(expressie.getWaarde());
                } else {
                    LOGGER.warn("Er bevindt zich een lege expressie in het dienstbundel met id: {}", dienstbundel);
                }
            }
        } else {
            LOGGER.warn("Het dienstbundel met id: {} bevat geen expressies", dienstbundel);
        }

        final String totaleExpressie = lijstExpressieBouwer.geefTotaleExpressie();
        LOGGER.debug("Totale expressie voor dienstbundel met id {}: {}", dienstbundel, totaleExpressie);

        final ParserResultaat geparsdeTotaleExpressie = BRPExpressies.parse(totaleExpressie);

        if (!geparsdeTotaleExpressie.succes()) {
            LOGGER.error(FunctioneleMelding.LEVERING_AUTORISATIE_PARSEN_EXPRESSIE_MISLUKT,
                "Het parsen van de expressie is mislukt voor dienstbundel met id {}. Foutmelding: {} "
                    + "Expressie: {}", String.valueOf(dienstbundel),
                geparsdeTotaleExpressie.getFoutmelding(),
                totaleExpressie);
            throw new ExpressieExceptie(FOUT_BIJ_PARSEN_VAN_EXPRESSIE + geparsdeTotaleExpressie.getFoutmelding());
        } else {
            return geparsdeTotaleExpressie.getExpressie();
        }
    }




    @Override
    @Cacheable(value = "TotalePopulatiebeperkingExpressieCache", key = "{ #populatieBeperking, #naderePopulatieBepalingDienstbundel, "
        + "#naderePopulatieBepalingToegangleveringsautoriatie }")
    public final Expressie geefPopulatiebeperking(final String populatieBeperking, final String naderePopulatieBepalingDienstbundel,
        final String naderePopulatieBepalingToegangleveringsautoriatie) throws ExpressieExceptie
    {

        final String totalePopulatieBeperking = MessageFormat
            .format(TOTALE_POPULATIE_BEPERKING_TEMPLATE, populatieBeperking, naderePopulatieBepalingDienstbundel,
                naderePopulatieBepalingToegangleveringsautoriatie);
        return parseStringNaarExpressie(totalePopulatieBeperking);
    }

    /**
     * Geef het attenderings criterium op basis van de levering autorisatie.
     *
     * @param leveringinformatie de levering autorisatie
     * @return de expressie
     * @throws ExpressieExceptie de expressie exceptie
     */
    @Override
    @Cacheable(value = "AttenderingsCriteriumExpressieCache", key = "#leveringinformatie.dienst.iD")
    public final Expressie geefAttenderingsCriterium(final Leveringinformatie leveringinformatie) throws ExpressieExceptie {
        final Dienst dienst = leveringinformatie.getDienst();
        final SoortDienst soortDienst = dienst.getSoort();

        final Expressie attenderingCriterium;
        if (SoortDienst.ATTENDERING == soortDienst && isAttenderingsCriteriumGevuld(dienst)) {
            attenderingCriterium = parseStringNaarExpressie(dienst.getAttenderingscriterium().getWaarde());
        } else if (SoortDienst.ATTENDERING == soortDienst) {
            LOGGER.error(FunctioneleMelding.LEVERING_AUTORISATIE_ATTENDERINGSCRITERIUM_LEEG,
                "Het attenderingscriterium dient gevuld te zijn voor leveringsautorisatie: {}",
                leveringinformatie.getToegangLeveringsautorisatie().getID());
            attenderingCriterium = null;
        } else {
            LOGGER.debug("Het attenderingscriterium kan alleen worden opgevraagd bij een dienst met de categorie Attendering");
            attenderingCriterium = null;
        }

        return attenderingCriterium;
    }

    @Override
    @CacheEvict(value = {
        "AttributenFilterExpressieCache",
        "TotalePopulatiebeperkingExpressieCache",
        "AttenderingsCriteriumExpressieCache" }, allEntries = true)
    public final void onApplicationEvent(final CacheVerversEvent cacheVerversEvent) {
        //evict
    }

    /**
     * Controleert of het attenderingscriterium gevuld is.
     *
     * @param dienst De dienst waarop het attenderingscriterium is gezet.
     * @return true als het attenderiumscriterium gevuld is, anders false
     */
    private boolean isAttenderingsCriteriumGevuld(final Dienst dienst) {
        return dienst.getAttenderingscriterium() != null && dienst.getAttenderingscriterium().getWaarde() != null;
    }


    /**
     * Parse een String naar een BRP expressie.
     *
     * @param expressieString de expressie als string
     * @return de geparsde expressie
     * @throws ExpressieExceptie de expressie exceptie
     */
    private Expressie parseStringNaarExpressie(final String expressieString) throws ExpressieExceptie {
        // Voor het parsen van attenderingscriterium zijn de volgende declaraties nodig. Eventueel dient er een
        // aparte methode te komen voor het parsen
        // van een attenderingscriterium.
        final Context context = new Context();
        context.declareer("oud", ExpressieType.PERSOON);
        context.declareer("nieuw", ExpressieType.PERSOON);

        final ParserResultaat parserResultaat = BRPExpressies.parse(expressieString, context);

        if (!parserResultaat.succes()) {
            throw new ExpressieExceptie(
                "Het is niet gelukt de expressie te parsen: " + expressieString + " Foutmelding: " + parserResultaat.getFoutmelding());
        }

        return parserResultaat.getExpressie();
    }

    /**
     * Geeft het expressie resultaat terug.
     *
     * @param expressieResultaat het expressie resultaat
     * @return het resultaat van de expressie
     */
    private Expressie geefExpressieResultaat(final Expressie expressie, final Expressie expressieResultaat) throws ExpressieExceptie {
        if (expressieResultaat.isFout()) {
            throw new ExpressieExceptie(FOUT_BIJ_PARSEN_VAN_EXPRESSIE + expressie.alsString() + ", foutmelding: " + expressieResultaat.alsString());
        }
        return expressieResultaat;
    }

}
