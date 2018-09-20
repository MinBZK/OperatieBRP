/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Onderzoek;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoek;
import nl.bzk.brp.model.logisch.kern.Relatie;

/**
 * Utilityfuncties voor het filteren van betrokkenheden en relaties.
 */
public final class FilterUtils {

    /**
     * Constructor. Private voor utility class.
     */
    private FilterUtils() {
    }

    /**
     * Geeft alle betrokkenheden van een persoon, waarin die persoon de gegeven rol speelt. Als rol == NULL dan is het resultaat alle betrokkenheden van de
     * persoon.
     *
     * @param persoon Persoon waarvan betrokkenheden worden verzameld.
     * @param rol     Rol die de persoon in de geselecteerde betrokkenheden moet hebben of NULL.
     * @return Alle geselecteerde betrokkenheden van een persoon.
     */
    public static Collection<Betrokkenheid> getBetrokkenheden(final Persoon persoon, final SoortBetrokkenheid rol) {
        final Collection<? extends Betrokkenheid> betrokkenheden = persoon.getBetrokkenheden();
        final Collection<Betrokkenheid> selectie = new ArrayList<>();
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            if (rol == null || betrokkenheid.getRol().getWaarde().equals(rol)) {
                selectie.add(betrokkenheid);
            }
        }
        return selectie;
    }

    /**
     * Geeft alle betrokkenheden van een persoon, waarin die persoon de gegeven rol speelt. Als rol == NULL dan is het resultaat alle betrokkenheden van de
     * persoon.
     *
     * @param persoon Persoon waarvan betrokkenheden worden verzameld.
     * @param rol     Rol die de persoon in de geselecteerde betrokkenheden moet hebben of NULL.
     * @return Alle geselecteerde betrokkenheden van een persoon.
     */
    public static Collection<BetrokkenheidHisVolledig> getBetrokkenheden(final PersoonHisVolledig persoon,
        final SoortBetrokkenheid rol)
    {
        final Collection<? extends BetrokkenheidHisVolledig> betrokkenheden = persoon.getBetrokkenheden();
        final Collection<BetrokkenheidHisVolledig> selectie = new ArrayList<>();
        for (final BetrokkenheidHisVolledig betrokkenheid : betrokkenheden) {
            if (rol == null || betrokkenheid.getRol().getWaarde().equals(rol)) {
                selectie.add(betrokkenheid);
            }
        }
        return selectie;
    }

    /**
     * Geeft alle relaties van het gegeven soort waar de persoon in betrokken is met de gegeven rol. Als soortRelatie == NULL worden alle relaties
     * verzameld. Als rol == NULL worden alle relaties verzameld ongeacht de rol van de persoon.
     *
     * @param persoon      Persoon waarvan de relaties worden verzameld.
     * @param rol          Rol die de persoon in de geselecteerde relaties moet hebben of NULL.
     * @param soortRelatie Soort relaties dat wordt verzameld.
     * @return Alle geselecteerde relaties van een persoon.
     */
    public static Collection<Relatie> getRelaties(final Persoon persoon, final SoortBetrokkenheid rol,
        final SoortRelatie soortRelatie)
    {
        final Collection<Betrokkenheid> betrokkenheden = getBetrokkenheden(persoon, rol);
        final Collection<Relatie> selectie = new ArrayList<>();
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            final Relatie relatie = betrokkenheid.getRelatie();
            if (soortRelatie == null || relatie.getSoort().getWaarde().equals(soortRelatie)) {
                selectie.add(relatie);
            }
        }
        return selectie;
    }

    /**
     * Geeft alle relaties van het gegeven soort waar de persoon in betrokken is met de gegeven rol. Als soortRelatie == NULL worden alle relaties
     * verzameld. Als rol == NULL worden alle relaties verzameld ongeacht de rol van de persoon.
     *
     * @param persoon      Persoon waarvan de relaties worden verzameld.
     * @param rol          Rol die de persoon in de geselecteerde relaties moet hebben of NULL.
     * @param soortRelatie Soort relaties dat wordt verzameld.
     * @return Alle geselecteerde relaties van een persoon.
     */
    public static Collection<RelatieHisVolledig> getRelaties(final PersoonHisVolledig persoon,
        final SoortBetrokkenheid rol,
        final SoortRelatie soortRelatie)
    {
        final Collection<BetrokkenheidHisVolledig> betrokkenheden = getBetrokkenheden(persoon, rol);
        final Collection<RelatieHisVolledig> selectie = new ArrayList<>();
        for (final BetrokkenheidHisVolledig betrokkenheid : betrokkenheden) {
            final RelatieHisVolledig relatie = betrokkenheid.getRelatie();
            if (soortRelatie == null || relatie.getSoort().getWaarde().equals(soortRelatie)) {
                selectie.add(relatie);
            }
        }
        return selectie;
    }

    /**
     * Geeft alle betrokkenheden van aan de persoon gerelateerde personen, waarin die personen een bepaalde rol hebben. Als soortRelatie == NULL worden
     * alle relaties meegenomen, anders alleen die van het gegeven soort. Als rol == NULL worden alle relaties meegenomen, anders alleen die waarin de
     * persoon de gegeven rol heeft. Als rolGerelateerde == NULL worden alle betrokkenheden van de gerelateerde meegenomen, anders alleen die waarin de
     * gerelateerde de gegeven rol heeft.
     *
     * @param persoon         Persoon waarvoor gerelateerde betrokkenheden worden verzameld.
     * @param rol             Rol die de persoon in de relaties moet hebben of NULL.
     * @param soortRelatie    Soort relatie dat moet worden beschouwd of NULL.
     * @param rolGerelateerde Rol die de gerelateerde in de relaties moet hebben of NULL.
     * @return Geselecteerde betrokkenheden.
     */
    public static Collection<Betrokkenheid> getGerelateerdeBetrokkenheden(final Persoon persoon,
        final SoortBetrokkenheid rol,
        final SoortRelatie soortRelatie,
        final SoortBetrokkenheid rolGerelateerde)
    {
        final Collection<Relatie> relaties = getRelaties(persoon, rol, soortRelatie);
        final Collection<Betrokkenheid> selectie = new ArrayList<>();
        for (final Relatie relatie : relaties) {
            final Collection<? extends Betrokkenheid> betrokkenheden = relatie.getBetrokkenheden();
            for (final Betrokkenheid betrokkenheid : betrokkenheden) {
                if (!persoon.equals(betrokkenheid.getPersoon())
                    && (rolGerelateerde == null || betrokkenheid.getRol().getWaarde().equals(rolGerelateerde)))
                {
                    selectie.add(betrokkenheid);
                }
            }
        }
        return selectie;
    }

    /**
     * Geeft alle betrokkenheden van aan de persoon gerelateerde personen, waarin die personen een bepaalde rol hebben. Als soortRelatie == NULL worden
     * alle relaties meegenomen, anders alleen die van het gegeven soort. Als rol == NULL worden alle relaties meegenomen, anders alleen die waarin de
     * persoon de gegeven rol heeft. Als rolGerelateerde == NULL worden alle betrokkenheden van de gerelateerde meegenomen, anders alleen die waarin de
     * gerelateerde de gegeven rol heeft.
     *
     * @param persoon         Persoon waarvoor gerelateerde betrokkenheden worden verzameld.
     * @param rol             Rol die de persoon in de relaties moet hebben of NULL.
     * @param soortRelatie    Soort relatie dat moet worden beschouwd of NULL.
     * @param rolGerelateerde Rol die de gerelateerde in de relaties moet hebben of NULL.
     * @return Geselecteerde betrokkenheden.
     */
    public static Collection<BetrokkenheidHisVolledig> getGerelateerdeBetrokkenheden(final PersoonHisVolledig persoon,
        final SoortBetrokkenheid rol,
        final SoortRelatie soortRelatie,
        final SoortBetrokkenheid
            rolGerelateerde)
    {
        final Collection<RelatieHisVolledig> relaties = getRelaties(persoon, rol, soortRelatie);
        final Collection<BetrokkenheidHisVolledig> selectie = new ArrayList<>();
        for (final RelatieHisVolledig relatie : relaties) {
            final Collection<? extends BetrokkenheidHisVolledig> betrokkenheden = relatie.getBetrokkenheden();
            for (final BetrokkenheidHisVolledig betrokkenheid : betrokkenheden) {
                if (!persoon.equals(betrokkenheid.getPersoon())
                    && (rolGerelateerde == null || betrokkenheid.getRol().getWaarde().equals(rolGerelateerde)))
                {
                    selectie.add(betrokkenheid);
                }
            }
        }
        return selectie;
    }

    public static Collection<Onderzoek> getOnderzoeken(final Persoon persoon) {
        final Collection<? extends PersoonOnderzoek> onderzoeken = persoon.getOnderzoeken();
        final Collection<Onderzoek> alleOnderzoeken = new ArrayList<>();
        for (PersoonOnderzoek po : onderzoeken) {
            alleOnderzoeken.add(po.getOnderzoek());
        }
        return alleOnderzoeken;
    }

    public static Collection<OnderzoekHisVolledig> getOnderzoeken(final PersoonHisVolledig persoon) {
        final Set<? extends PersoonOnderzoekHisVolledig> onderzoeken = persoon.getOnderzoeken();
        Collection<OnderzoekHisVolledig> alleOnderzoeken = new ArrayList<>();
        for (PersoonOnderzoekHisVolledig po : onderzoeken) {
            alleOnderzoeken.add(po.getOnderzoek());
        }
        return alleOnderzoeken;
    }
}
