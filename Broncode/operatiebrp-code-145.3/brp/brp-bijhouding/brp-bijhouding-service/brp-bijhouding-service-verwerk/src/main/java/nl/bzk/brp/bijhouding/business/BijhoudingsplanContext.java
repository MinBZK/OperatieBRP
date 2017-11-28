/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.brp.bijhouding.bericht.model.AfgeleidAdministratiefElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingsplanElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingsplanPersoonElement;
import nl.bzk.brp.bijhouding.bericht.model.DatumTijdElement;
import nl.bzk.brp.bijhouding.bericht.model.IdentificatienummersElement;
import nl.bzk.brp.bijhouding.bericht.model.NotificatieBijhoudingsplanElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonGegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;

/**
 * Wrapper om het bijhoudingsplan te kunnen verwerken en het
 * {@link nl.bzk.brp.bijhouding.bericht.model.BijhoudingsplanElement} te vullen.
 */
public final class BijhoudingsplanContext {

    private final BijhoudingVerzoekBericht bijhoudingVerzoekBericht;
    private final Set<BijhoudingPersoon> personenUitHetBijhoudingsplan;
    private final Set<BijhoudingPersoon> personenDieNietVerwerktMaarWelOntrelateerdZijn;
    private Partij gbaGeboortePartij;

    /**
     * Constructor voor het bijhoudingsplan.
     * @param bijhoudingVerzoekBericht het bijhouding verzoekbericht
     */
    public BijhoudingsplanContext(final BijhoudingVerzoekBericht bijhoudingVerzoekBericht) {
        this.bijhoudingVerzoekBericht = bijhoudingVerzoekBericht;
        this.personenUitHetBijhoudingsplan =
                new TreeSet<>(Comparator.comparing(BijhoudingPersoon::getBsnOrNull, Comparator.nullsFirst(Comparator.naturalOrder())));
        personenDieNietVerwerktMaarWelOntrelateerdZijn = new LinkedHashSet<>();
    }

    /**
     * Add bijhouding situatie voor persoon.
     * @param persoon the persoon
     * @param situatie the situatie
     */
    public void addBijhoudingSituatieVoorPersoon(final BijhoudingPersoon persoon, final BijhoudingSituatie situatie) {
        persoon.setBijhoudingSituatie(situatie);
        this.personenUitHetBijhoudingsplan.add(persoon);
    }

    /**
     * Geeft een unmodifiable set terug van betrokken {@link Persoon}.
     * @return een unmodifiable set.
     */
    public Set<BijhoudingPersoon> getPersonenUitHetBijhoudingsplan() {
        return Collections.unmodifiableSet(personenUitHetBijhoudingsplan);
    }

    /**
     * Maakt een @{link {@link BijhoudingsplanElement} voor een
     * {@link nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht}.
     * @return een {@link BijhoudingsplanElement}
     */
    public BijhoudingsplanElement maakBijhoudingsplanElementVoorBijhoudingAntwoordBericht() {
        return BijhoudingsplanElement.getInstance(bijhoudingVerzoekBericht.getStuurgegevens().getZendendePartij(),
                maakPartijElement(gbaGeboortePartij),
                maakBijhoudingsplanPersoonElementen());
    }

    /**
     * Maakt een @{link {@link BijhoudingsplanElement} voor een BijhoudingsNotificatieBericht.
     * @param bijhoudingAntwoordBericht het {@link BijhoudingAntwoordBericht}
     * @return een {@link BijhoudingsplanElement}
     */
    public NotificatieBijhoudingsplanElement maakBijhoudingsplanElementVoorBijhoudingNotificatieBericht(
            final BijhoudingAntwoordBericht bijhoudingAntwoordBericht) {
        return NotificatieBijhoudingsplanElement.getInstance(
                bijhoudingVerzoekBericht.getStuurgegevens().getZendendePartij().getWaarde(),
                maakPartijElement(gbaGeboortePartij),
                bijhoudingVerzoekBericht,
                bijhoudingAntwoordBericht,
                maakBijhoudingsplanPersoonElementen());
    }

    /**
     * Geeft een lijst van bijgehouden personen terug waarvoor geldt dat de bijhoudingssituatie verwerkbaarbaar is en er
     * geen sprake van prevalidatie is. Van deze personen mag men ervan uitgaan dat deze worden verwerkt in de BRP
     * database.
     * @return een lijst van bijgehouden personen.
     */
    public List<BijhoudingPersoon> getPersonenUitHetBijhoudingsplanDieVerwerktMoetenWorden() {
        final List<BijhoudingPersoon> results = new ArrayList<>();
        if (!bijhoudingVerzoekBericht.isPrevalidatie()) {
            for (final BijhoudingPersoon persoon : this.personenUitHetBijhoudingsplan) {
                if (persoon.isVerwerkbaar()) {
                    results.add(persoon);
                }
            }
        }
        return results;
    }

    /**
     * Bepaalt het bijhoudingsresultaat o.b.v. de bijhoudingssituatie van de personen uit het bijhoudingsbericht.
     * @return {@link BijhoudingResultaat#VERWERKT} als alle bijhoudingssituaties uit het bijhoudingsplan verwerkbaar zijn, anders {@link
     * BijhoudingResultaat#DEELS_UITGESTELD}
     */
    public BijhoudingResultaat bepaalBijhoudingResultaat() {
        BijhoudingResultaat result = null;
        for (final BijhoudingPersoon persoon : getPersonenUitHetBijhoudingsplan()) {
            if (!persoon.getBijhoudingSituatie().isTeNegerenVoorBijhoudingResultaat() && !persoon.isVerwerkbaar()) {
                result = BijhoudingResultaat.DEELS_UITGESTELD;
                break;
            }
            result = BijhoudingResultaat.VERWERKT;
        }
        return result;
    }

    /**
     * Geef de set van personen die verwerkbaar zijn maar wel moeten worden opgeslagen omdat de groep afgeleid administratief is bijgewerkt omdat er
     * relaties van deze persoon zijn ontrelateerd.
     * @return personenDieNietVerwerktMaarWelOntrelateerdZijn
     */
    public Set<BijhoudingPersoon> getPersonenDieNietVerwerktMaarWelOntrelateerdZijn() {
        return Collections.unmodifiableSet(personenDieNietVerwerktMaarWelOntrelateerdZijn);
    }

    /**
     * Voegt een persoon toe die niet verwerkbaar is maar die wel moet worden opgeslagen omdat er relates van deze persoon zijn ontrelateerd.
     * @param persoon persoonDieNietVerwerktMaarWelOntrelateerdIs
     */
    public void addPersoonDieNietVerwerktMaarWelOntrelateerdIs(final BijhoudingPersoon persoon) {
        personenDieNietVerwerktMaarWelOntrelateerdZijn.add(persoon);
    }

    /**
     * Zet de GBA partij van de geboorte.
     * @param partij GBA partij waar de geboorte is geweest.
     */
    public void setGbaGeboortePartij(final Partij partij) {
        this.gbaGeboortePartij = partij;
    }

    /**
     * Geeft de GBA partij van de geboorte
     * @return de GBA patij
     */
    public Partij getGbaGeboortePartij() {
        return gbaGeboortePartij;
    }

    private List<BijhoudingsplanPersoonElement> maakBijhoudingsplanPersoonElementen() {
        final List<BijhoudingsplanPersoonElement> bijhoudingsplanPersonen = new ArrayList<>();

        for (final BijhoudingPersoon persoon : personenUitHetBijhoudingsplan) {
            final PersoonGegevensElement persoonElement = maakPersoonElement(persoon);
            final BijhoudingsplanPersoonElement bijhoudingsplanPersoon =
                    BijhoudingsplanPersoonElement.getInstance(persoonElement, persoon.getBijhoudingSituatie().getNaam());
            bijhoudingsplanPersonen.add(bijhoudingsplanPersoon);
        }
        return bijhoudingsplanPersonen;
    }

    private PersoonGegevensElement maakPersoonElement(final BijhoudingPersoon persoon) {
        final Map<String, String> attributen = new HashMap<>();
        attributen.put(OBJECTTYPE_ATTRIBUUT.toString(), "Persoon");

        final AfgeleidAdministratiefElement afgeleidAdministratief = maakAfgeleidAdministratiefElement(persoon);
        final IdentificatienummersElement identificatienummers = maakIdentificatienummersElement(persoon);
        final StringElement partijElement = maakPartijElement(persoon.getBijhoudingspartijVoorBijhoudingsplan());
        final BijhoudingElement bijhouding = new BijhoudingElement(Collections.emptyMap(), partijElement);

        return new PersoonGegevensElement(attributen, afgeleidAdministratief, identificatienummers, null, null, null, bijhouding, null, null, null, null, null,
                null,
                null,
                null, null, null);
    }

    private StringElement maakPartijElement(final Partij partij) {
        return partij == null ? null : new StringElement(partij.getCode());
    }

    private AfgeleidAdministratiefElement maakAfgeleidAdministratiefElement(final BijhoudingPersoon persoon) {
        final Timestamp tijdstipVoorlaatsteWijziging = persoon.getTijdstipVoorlaatsteWijziging();
        return tijdstipVoorlaatsteWijziging == null ? null
                : new AfgeleidAdministratiefElement(Collections.emptyMap(), new DatumTijdElement(tijdstipVoorlaatsteWijziging));
    }

    private IdentificatienummersElement maakIdentificatienummersElement(final BijhoudingPersoon persoon) {
        final String actueelBurgerservicenummer = persoon.getActueelBurgerservicenummer();
        return actueelBurgerservicenummer == null ? null
                : new IdentificatienummersElement(Collections.emptyMap(), new StringElement(actueelBurgerservicenummer), null);
    }
}
