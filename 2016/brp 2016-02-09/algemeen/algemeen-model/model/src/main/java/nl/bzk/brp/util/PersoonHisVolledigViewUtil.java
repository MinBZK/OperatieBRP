/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.Set;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.GerelateerdIdentificeerbaar;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import org.apache.commons.lang.StringUtils;

/**
 * Persoon his volledig view util klasse die specifieke utility methodes bevat voor PersoonHisVolledigView objecten.
 */
public final class PersoonHisVolledigViewUtil {
    private static final Logger LOGGER       = LoggerFactory.getLogger();
    private static final String GERELATEERDE = "GERELATEERDE";

    private PersoonHisVolledigViewUtil() {
        // Private constructor
    }

    /**
     * Initialiseert een persoonHisVolledigView, dwz. voert alle noodzakelijke handelingen uit om een persoonHisVolledigView zo goed mogelijk te
     * initialiseren.
     *
     * @param persoonHisVolledigView persoon his volledig view
     */
    public static void initialiseerView(final PersoonHisVolledigView persoonHisVolledigView) {
        zetGroepenOpAttributen(persoonHisVolledigView);
        corrigeerEnumWaardenVoorGerelateerden(persoonHisVolledigView);
    }

    /**
     * Zet groep-referentie op attributen. Dit dient aangeroepen te worden na de constructor. Anders treedt er recursieve loop op bij het itereren over de
     * betrokkenheden en aanmaak van nieuwe PersoonHisVolledig views.
     * @param persoonHisVolledigView persoon his volledig view
     */
    public static void zetGroepenOpAttributen(final PersoonHisVolledigView persoonHisVolledigView) {
        for (final HistorieEntiteit historieEntiteit : persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst()) {
            if (historieEntiteit instanceof Groep) {
                final Groep groep = (Groep) historieEntiteit;
                plaatsGroepOpAttributen(groep);
            }
        }
    }

    /**
     * Plaatst de groep op elk van de attributen van de groep.
     *
     * @param groep de groep
     */
    private static void plaatsGroepOpAttributen(final Groep groep) {
        for (final Attribuut attribuut : groep.getAttributen()) {
            attribuut.setGroep(groep);
        }
    }

    /**
     * Corrigeert de enum waarden voor gerelateerde personen op de PersoonHisVolledigView.
     *
     * @param persoonHisVolledigView de persoonHisVolledigView
     */
    public static void corrigeerEnumWaardenVoorGerelateerden(final PersoonHisVolledigView persoonHisVolledigView) {
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonHisVolledigView.getBetrokkenheden()) {
            // Corrigeren binnen betrokken personen
            final Set<? extends BetrokkenheidHisVolledig> betrokkenheden =
                betrokkenheidHisVolledigView.getRelatie().getBetrokkenheden();
            for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : betrokkenheden) {
                final BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheid = (BetrokkenheidHisVolledigView) betrokkenheidHisVolledig;
                final SoortBetrokkenheid soortBetrokkenheid = betrokkenPersoonBetrokkenheid.getRol().getWaarde();
                final SoortRelatieAttribuut soortRelatie = betrokkenPersoonBetrokkenheid.getRelatie().getSoort();

                // pas enums op groepen van de gerelateerde persoon aan
                if (betrokkenheidHisVolledig.getPersoon() != null
                    && !betrokkenheidHisVolledig.getPersoon().getID().equals(persoonHisVolledigView.getID()))
                {
                    final PersoonHisVolledigView betrokkenPersoonView = (PersoonHisVolledigView) betrokkenPersoonBetrokkenheid.getPersoon();
                    final Set<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst = betrokkenPersoonView.getIdentificerendeHistorieRecords();
                    for (final HistorieEntiteit historieEntiteit : totaleLijstVanHisElementenOpPersoonsLijst) {
                        zetGerelateerdObjectTypeVoorBetrokkenPersoon(betrokkenPersoonBetrokkenheid, soortRelatie.getWaarde(), soortBetrokkenheid,
                                                                     historieEntiteit, BepalingOp.PERSOON);
                    }
                }
                // pas enums op groepen van de betrokkenheid aan
                for (final HistorieEntiteit historieEntiteit : betrokkenPersoonBetrokkenheid.getAlleHistorieRecords()) {
                    zetGerelateerdObjectTypeVoorBetrokkenPersoon(betrokkenPersoonBetrokkenheid, soortRelatie.getWaarde(), soortBetrokkenheid,
                                                                 historieEntiteit, BepalingOp.BETROKKENHEID);
                }
            }
        }
    }

    /**
     * helper klasse voor enum naam bepaling
     */
    private enum BepalingOp {
        PERSOON, BETROKKENHEID
    }

    /**
     * Zet gerelateerd object type voor betrokken persoon.
     *
     * @param betrokkenPersoonBetrokkenheid betrokken persoon betrokkenheid
     * @param relatie relatie
     * @param betrokkenheid betrokkenheid
     * @param historieEntiteit historie entiteit
     * @param bepaling bepaling van objecttype
     */
    private static void zetGerelateerdObjectTypeVoorBetrokkenPersoon(final BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheid,
                                                                     final SoortRelatie relatie, final SoortBetrokkenheid betrokkenheid,
                                                                     final HistorieEntiteit historieEntiteit, final BepalingOp bepaling)
    {
        if (historieEntiteit instanceof GerelateerdIdentificeerbaar) {
            final ElementIdentificeerbaar elementIdentificeerbaar = (ElementIdentificeerbaar) historieEntiteit;
            final ElementEnum elementIdentificatie = elementIdentificeerbaar.getElementIdentificatie();
            final String prefix = geefPrefixVoorBetrokkenheid(relatie, betrokkenheid);
            if (!StringUtils.isBlank(prefix)) {
                String enumNaam = null;
                switch (bepaling) {
                    case BETROKKENHEID:
                        enumNaam = GERELATEERDE + elementIdentificatie.name();
                        break;
                    case PERSOON:
                        enumNaam = prefix + "_" + elementIdentificatie.name();
                        break;
                    default:
                }

                try {
                    final ElementEnum nieuweEnum = ElementEnum.valueOf(enumNaam);
                    final GerelateerdIdentificeerbaar gerelateerd = (GerelateerdIdentificeerbaar) historieEntiteit;
                    gerelateerd.setGerelateerdeObjectType(nieuweEnum);
                    gerelateerd.setBetrokkenPersoonBetrokkenheidView(betrokkenPersoonBetrokkenheid);
                } catch (final IllegalArgumentException e) {
                    LOGGER.info("Enum waarde kan niet gevonden worden {}", enumNaam);
                }
            }
        }
    }

    /**
     * Geeft prefix voor betrokkenheid.
     *
     * @param relatie relatie
     * @param betrokkenheid betrokkenheid
     * @return prefix als string
     */
    private static String geefPrefixVoorBetrokkenheid(final SoortRelatie relatie, final SoortBetrokkenheid betrokkenheid) {
        final String prefix;
        switch (betrokkenheid) {
            case KIND:
            case OUDER:
            case ERKENNER:
            case NAAMGEVER:
            case INSTEMMER:
                prefix = GERELATEERDE + betrokkenheid.name();
                break;
            case PARTNER:
                prefix = geefPrefixVoorRelatie(relatie);
                break;
            default:
                prefix = null;
                break;
        }
        return prefix;
    }

    /**
     * Geeft prefix voor relatie.
     *
     * @param relatie relatie
     * @return prefix als string
     */
    private static String geefPrefixVoorRelatie(final SoortRelatie relatie) {
        final String prefix;
        switch (relatie) {
            case HUWELIJK:
                prefix = "GERELATEERDEHUWELIJKSPARTNER";
                break;
            case GEREGISTREERD_PARTNERSCHAP:
                prefix = "GERELATEERDEGEREGISTREERDEPARTNER";
                break;
            case NAAMSKEUZE_ONGEBOREN_VRUCHT:
                prefix = "GERELATEERDENAAMSKEUZEPARTNER";
                break;
            default:
                prefix = null;
                break;
        }
        return prefix;
    }
}
