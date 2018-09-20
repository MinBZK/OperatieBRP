/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;

/**
 * Wrapper voor PersoonHisVolledigImpl ten behoeve van serialisatie.
 * <p/>
 * Bedoeld om het serialiseren te vereenvoudigen van een persoon, zijn betrokkenheden, relaties, en gerelateerde betrokkenheden/personen (maar niet verder
 * dan dat). Door gebruik te maken van deze wrapper hoeven we geen custom serializers te gebruiken. We serialiseren <ul> <li>de persoon zonder
 * betrokkenheden</li> <li>de relaties (waarin de persoon betrokken is) met referenties naar alle betrokkenheden</li> <li>de betrokkenheden (zonder
 * relatie, maar met persoon), zodat we daaraan kunnen refereren vanuit de relaties</li> </ul>
 */
@JsonPropertyOrder(value = { "persoon", "betrokkenheden", "relaties", "persoonOnderzoeken", "gerelateerdePersoonOnderzoeken" }, alphabetic = true)
public class PersoonHisVolledigImplWrapper {

    /**
     * De persoon die geserialiseerd moet worden. Deze wordt geserialiseerd zonder zijn betrokkenheden.
     */
    @JsonProperty
    private PersoonHisVolledigImpl persoon;

    /**
     * De lijst van persoononderzoeken uit de persoon
     */
    @JsonProperty
    private List<PersoonOnderzoekHisVolledigImpl> persoonOnderzoeken;

    /**
     * De lijst van alle personenInOnderzoek die horen bij de aan de persoon gerelateerde onderzoeken.
     */
    @JsonProperty
    private List<PersoonOnderzoekHisVolledigImpl> gerelateerdePersoonOnderzoeken;

    /**
     * Alle betrokkenheden van de persoon, en van de relaties waarin deze betrokken is. Het zou eenvoudiger zijn om deze betrokkenheden impliciet op te
     * nemen in relaties, ware het niet dat we dan geen controle hebben over de volgorde van de betrokkenheden-array in json. De structuur is een List om
     * deze eenvoudig te kunnen sorteren, om een consistente sortering in Json te krijgen.
     */
    @JsonProperty
    private List<BetrokkenheidHisVolledigImpl> betrokkenheden;

    /**
     * De relaties waarin de persoon is betrokken. Deze worden geserialiseerd inclusief de betrokkenheden, en (de id's van) de betrokken personen. De
     * structuur is een List om deze eenvoudig te kunnen sorteren, om een consistente sortering in Json te krijgen.
     */
    @JsonProperty
    private List<RelatieHisVolledigImpl> relaties;

    private boolean persoonCompleet;

    /**
     * No-arg constructor, tbv het deserialiseren.
     */
    public PersoonHisVolledigImplWrapper() {
    }

    /**
     * Maakt een Wrapper aan op basis van de gegeven persoon. Bouwt meteen de interne structuur op, zodat de wrapper succesvol geserialiseerd kan worden.
     *
     * @param persoon de persoon die gewrapped wordt
     */
    public PersoonHisVolledigImplWrapper(final PersoonHisVolledigImpl persoon) {
        persoonCompleet = true;
        this.persoon = persoon;

        bepaalPersoonOnderzoeken();

        bepaalBetrokkenhedenEnRelaties();
    }

    /**
     * Bepaalt de onderzoeken van een persoon en de gerelateerde personen.
     */
    private void bepaalPersoonOnderzoeken() {
        persoonOnderzoeken = new ArrayList<>();
        gerelateerdePersoonOnderzoeken = new ArrayList<>();
        for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoek : persoon.getOnderzoeken()) {
            persoonOnderzoeken.add(persoonOnderzoek);
            for (final PersoonOnderzoekHisVolledigImpl gerelateerdPersoonOnderzoek : persoonOnderzoek.getOnderzoek().getPersonenInOnderzoek()) {
                if (!gerelateerdePersoonOnderzoeken.contains(gerelateerdPersoonOnderzoek)) {
                    gerelateerdePersoonOnderzoeken.add(gerelateerdPersoonOnderzoek);
                }
            }
        }
    }

    /**
     * Bepaalt de betrokkenheden en relaties van een persoon.
     */
    private void bepaalBetrokkenhedenEnRelaties() {
        betrokkenheden = new ArrayList<>();
        relaties = new ArrayList<>();

        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoon.getBetrokkenheden()) {
            if (!betrokkenheden.contains(betrokkenheid)) {
                betrokkenheden.add(betrokkenheid);
            }
            final RelatieHisVolledigImpl relatie = betrokkenheid.getRelatie();
            if (!relaties.contains(relatie)) {
                relaties.add(relatie);
            }
            for (final BetrokkenheidHisVolledigImpl relatieBetrokkenheid : relatie.getBetrokkenheden()) {
                if (!betrokkenheden.contains(relatieBetrokkenheid)) {
                    betrokkenheden.add(relatieBetrokkenheid);
                }
            }
        }
        // betrokkenheden sorteren om consistente volgorde binnen de json te krijgen
        Collections.sort(betrokkenheden, new Comparator<BetrokkenheidHisVolledigImpl>() {
            @Override
            public int compare(final BetrokkenheidHisVolledigImpl o1, final BetrokkenheidHisVolledigImpl o2) {
                return o1.getID().compareTo(o2.getID());
            }
        });

    }

    /**
     * Retourneert de persoon die door dit object gewrapt is. Na deserialisatie wordt eerst de hele persoon weer samengesteld uit zijn betrokkenheden en
     * relaties.
     *
     * @return een persoon als PersoonHisVolledigImpl
     */
    public final PersoonHisVolledigImpl unwrap() {
        if (!persoonCompleet) {
            vulPersoonBetrokkenheden();
            vulPersoonPersoonOnderzoek();
            persoonCompleet = true;
        }
        return persoon;
    }

    /**
     * Vult de betrokkenheden van de persoon op basis van de relaties.
     */
    private void vulPersoonBetrokkenheden() {
        if (relaties != null) {
            for (final RelatieHisVolledigImpl relatie : relaties) {
                for (final BetrokkenheidHisVolledigImpl betrokkenheid : relatie.getBetrokkenheden()) {
                    betrokkenheid.setRelatie(relatie);
                    if (betrokkenheidHoortBijPersoon(betrokkenheid)) {
                        persoon.getBetrokkenheden().add(betrokkenheid);
                    }
                }
            }
        }
    }

    /**
     * Vult de onderzoeken van de persoon.
     */
    private void vulPersoonPersoonOnderzoek() {
        if (persoonOnderzoeken != null) {
            for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoekHisVolledig : persoonOnderzoeken) {
                vulOnderzoekPersonenInOnderzoek(persoonOnderzoekHisVolledig);
                persoon.getOnderzoeken().add(persoonOnderzoekHisVolledig);
            }
        }
    }

    /**
     * Vul de personen  in deonderzoek personen in onderzoek.
     *
     * @param persoonOnderzoek the persoon onderzoek
     */
    private void vulOnderzoekPersonenInOnderzoek(final PersoonOnderzoekHisVolledigImpl persoonOnderzoek) {
        if (gerelateerdePersoonOnderzoeken != null) {
            for (final PersoonOnderzoekHisVolledigImpl gerel : gerelateerdePersoonOnderzoeken) {
                if (gerel.getOnderzoek().getID().equals(persoonOnderzoek.getOnderzoek().getID())) {
                    persoonOnderzoek.getOnderzoek().getPersonenInOnderzoek().add(gerel);
                }
            }
        }
    }

    private boolean betrokkenheidHoortBijPersoon(final BetrokkenheidHisVolledigImpl betrokkenheid) {
        return betrokkenheid.getPersoon() != null && betrokkenheid.getPersoon().getID().equals(persoon.getID());
    }
}
