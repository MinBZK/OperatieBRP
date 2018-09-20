/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.business.expressietaal.MagGeleverdWordenVlaggenZetter;
import nl.bzk.brp.levering.business.expressietaal.impl.MagGeleverdWordenVlaggenZetterImpl;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import org.springframework.stereotype.Service;


/**
 * Implementatie van de AttributenFilterService.
 *
 * @brp.bedrijfsregel VR00052
 */
@Regels(Regel.VR00052)
@Service
public class AttributenFilterServiceImpl implements AttributenFilterService {

    private static final Logger LOGGER                                                   = LoggerFactory.getLogger();
    private static final String AANTAL_GERAAKTE_ATTRIBUTEN_VOOR_LEVERINGSAUTORISATIENAAM
                                                                                         = "Aantal geraakte attributen voor leveringsautorisatie naam {}: {}";

    private final MagGeleverdWordenVlaggenZetter magGeleverdWordenVlaggenZetter = new MagGeleverdWordenVlaggenZetterImpl();

    @Inject
    private ExpressieService expressieService;

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Override
    @Regels(beschrijving = "VRLV0001")
    public final List<Attribuut> zetMagGeleverdWordenVlaggen(final List<PersoonHisVolledigView> persoonHisVolledigViews, final Dienst dienst,
        final Rol rol) throws ExpressieExceptie
    {
        final Expressie expressie = expressieService.geefAttributenFilterExpressie(dienst, rol);
        final List<Attribuut> lijstVanGeraakteAttributenAllePersonen = new ArrayList<>();

        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            final Expressie expressieResultaat;
            try {
                expressieResultaat = expressieService.evalueer(expressie, persoonHisVolledigView);
            } catch (final ExpressieExceptie expressieExceptie) {
                throw new ExpressieExceptie("Fout bij evalueren van expressie voor persoon met id: "
                    + persoonHisVolledigView.getID() + ", dienst met id: " + dienst.getID(), expressieExceptie);
            }

            final List<Attribuut> geraakteAtrributenDezePersoon =
                magGeleverdWordenVlaggenZetter.zetMagGeleverdWordenVlaggenOpWaarde(expressieResultaat, true);

            LOGGER.debug(AANTAL_GERAAKTE_ATTRIBUTEN_VOOR_LEVERINGSAUTORISATIENAAM, dienst.getID(),
                    geraakteAtrributenDezePersoon.size());

            lijstVanGeraakteAttributenAllePersonen.addAll(geraakteAtrributenDezePersoon);
        }

        return lijstVanGeraakteAttributenAllePersonen;
    }

    @Override
    @Regels(Regel.VR00052)
    public final List<Attribuut> zetMagGeleverdWordenVlaggen(
            final List<PersoonHisVolledigView> persoonHisVolledigViews, final Dienst dienst, final Rol rol,
            final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap)
            throws ExpressieExceptie
    {
        final List<String> leveringsautorisatieExpressieStrings = dienst.geefAttributenFilterExpressieLijst(rol);

        final List<Attribuut> lijstVanGeraakteAttributenAllePersonen = new ArrayList<>();

        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            final List<Attribuut> geraakteAtrributenDezePersoon = new ArrayList<>();
            for (final String expressieStr : leveringsautorisatieExpressieStrings) {
                final List<Attribuut> attributen = persoonAttributenMap.get(persoonHisVolledigView.getID()).get(expressieStr);
                if (attributen != null) {
                    for (final Attribuut attribuut : attributen) {
                        attribuut.setMagGeleverdWorden(true);
                        geraakteAtrributenDezePersoon.add(attribuut);
                    }
                }
            }

            LOGGER.debug(AANTAL_GERAAKTE_ATTRIBUTEN_VOOR_LEVERINGSAUTORISATIENAAM, dienst.getDienstbundel().getNaam(),
                    geraakteAtrributenDezePersoon.size());

            lijstVanGeraakteAttributenAllePersonen.addAll(geraakteAtrributenDezePersoon);
        }

        return lijstVanGeraakteAttributenAllePersonen;
    }


    /**
     * Zet mag geleverd worden vlaggen.
     *
     * @param persoonHisVolledigView persoon his volledig view
     * @param dienst dienst
     * @param rol rol
     * @return lijst met attributen
     * @throws ExpressieExceptie expressie exceptie
     *
     * @brp.bedrijfsregel R2002
     * @see AttributenFilterService#zetMagGeleverdWordenVlaggen(PersoonHisVolledigView, Dienst, Rol)
     */
    @Regels(Regel.R2002)
    @Override
    public final List<Attribuut> zetMagGeleverdWordenVlaggen(final PersoonHisVolledigView persoonHisVolledigView, final Dienst dienst,
                                                             final Rol rol) throws ExpressieExceptie
    {
        return zetMagGeleverdWordenVlaggen(Collections.singletonList(persoonHisVolledigView), dienst, rol);
    }

    @Override
    public final void resetMagGeleverdWordenVlaggen(final List<Attribuut> attributen) {
        for (final Attribuut attribuut : attributen) {
            attribuut.setMagGeleverdWorden(false);
        }
        LOGGER.info("{} attributen ge-reset naar false.", attributen.size());
    }

}
