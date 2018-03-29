/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegateRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;

/**
 * Deze decorator class voegt bijhoudingsfunctionaliteit toe aan de relatie entiteit.
 */
public class BijhoudingRelatie extends AbstractDelegateRelatie implements BijhoudingEntiteit {
    private static final long serialVersionUID = 1L;

    private Map<Long, RelatieHistorie> idOpKopieHistorieMap;

    /**
     * Maakt een nieuwe BijhoudingRelatie object.
     * @param delegate de relatie die moet worden uigebreid met bijhoudingsfunctionaliteit
     */
    public BijhoudingRelatie(final Relatie delegate) {
        super(delegate);
        idOpKopieHistorieMap = new HashMap<>();
    }

    /**
     * Deze methode wordt gebruikt om o.b.v. de id's van een oud voorkomen het nieuwe (kopie) voorkomen te achterhalen. Dit wordt
     * gebruikt om m.b.v. voorkomensleutels die naar oude voorkomens verwijzen toch het nieuwe voorkomen te vinden die nog geen ID heeft.
     * @param idOrigineleHistorie de id van de {@link RelatieHistorie} die is gekopieerd
     * @param kopieHistorie de kopie
     */
    public void registreerKopieHistorie(final Long idOrigineleHistorie, final RelatieHistorie kopieHistorie) {
        idOpKopieHistorieMap.put(idOrigineleHistorie, kopieHistorie);
    }

    /**
     * De methode zoekt voor voorkomensleutel uit het bijhoudingsbericht naar een bijbehorende relatie historie voorkomen voor deze entiteit. Deze methode
     * geeft als resultaat het kopie van een voorkomen als het voorkomen waar de sleutel naar verwijst inmiddels is gekopieerd (bijv. tijdens ontrelateren).
     * @param voorkomenSleutel de voorkomen sleutel
     * @return het bijbehorende voorkomen
     */
    public RelatieHistorie zoekRelatieHistorieVoorVoorkomenSleutel(final String voorkomenSleutel) {
        RelatieHistorie result = null;
        try {
            final Long relatieHistorieId = Long.valueOf(voorkomenSleutel);
            if (idOpKopieHistorieMap.containsKey(relatieHistorieId)) {
                result = idOpKopieHistorieMap.get(relatieHistorieId);
            } else {
                result = zoekInHistorieSet(relatieHistorieId);
            }
        } catch (NumberFormatException nfe) {
            result = null;
        }
        return result;
    }

    private RelatieHistorie zoekInHistorieSet(final Long relatieHistorieId) {
        for (final RelatieHistorie relatieHistorie : getRelatieHistorieSet()) {
            if (relatieHistorieId.equals(relatieHistorie.getId())) {
                return relatieHistorie;
            }
        }
        return null;
    }

    /**
     * Zoekt naar een Pseudo persoon binnen deze relatie met de juiste betrokkenheid id en persoon id.
     * @param betrokkenheidId de betrokkenheid id
     * @param persoonId de persoon id
     * @return de pseudo persoon met het gegeven betrokkenheid id en persoon id, of null als deze niet gevonden kan worden
     */
    final BijhoudingPersoon getPseudoPersoonVoorIds(final long betrokkenheidId, final long persoonId) {
        BijhoudingPersoon result = null;
        for (final Betrokkenheid betrokkenheid : getBetrokkenheidSet()) {
            if (betrokkenheidId == betrokkenheid.getId()
                    && persoonId == betrokkenheid.getPersoon().getId()
                    && SoortPersoon.PSEUDO_PERSOON.equals(betrokkenheid.getPersoon().getSoortPersoon())) {
                result = BijhoudingPersoon.decorate(betrokkenheid.getPersoon());
                break;
            }
        }
        return result;
    }

    /**
     * Voegt een nieuw relatie voorkomen toe met einde relatie gegevens.
     * @param relatieElement het huwelijk of GP element uit het bijhoudingverzoekbericht
     * @param actie de actie inhoud
     * @param eindDatum de einddatum van de relatie
     */
    final void voegVoorkomenMetEindeRelatieToe(final RelatieElement relatieElement, final BRPActie actie, final DatumElement eindDatum) {
        final RelatieHistorie actueleRelatieHistorie = getActueleRelatieHistorie();
        final RelatieHistorie relatieEindeVoorkomen = actueleRelatieHistorie.maakKopieVanAanvang();
        vulEindeGegevens(relatieEindeVoorkomen, relatieElement.getRelatieGroep(), false);
        relatieEindeVoorkomen.setDatumEinde(BmrAttribuut.getWaardeOfNull(eindDatum));
        BijhoudingEntiteit.voegFormeleHistorieToe(relatieEindeVoorkomen, actie, getRelatieHistorieSet());
        kopieerGegevenInOnderzoekVoorNieuwVoorkomen(actueleRelatieHistorie, relatieEindeVoorkomen, actie);
    }

    private void kopieerGegevenInOnderzoekVoorNieuwVoorkomen(final RelatieHistorie oudVoorkomen, final RelatieHistorie nieuwVoorkomen, final BRPActie actie) {
        final Set<Persoon> personenInRelatie =
                oudVoorkomen.getRelatie().getBetrokkenheidSet().stream().map(Betrokkenheid::getPersoon).collect(Collectors.toSet());
        for (final Persoon persoon : personenInRelatie) {
            for (final Onderzoek onderzoek : persoon.getOnderzoeken()) {
                onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(oudVoorkomen, nieuwVoorkomen, actie);
            }
        }
    }

    /**
     * Voegt een nieuw relatie voorkomen en de betrokkenen toe aan deze relatie entiteit.
     * @param relatieElement het huwelijk of GP element dat de informatie bevat om het relatie voorkomen en betrokkenen te maken
     * @param actie de actie inhoud
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     */
    final void voegVoorkomenMetAanvangRelatieHistorieEnBetrokkenenToe(
            final RelatieElement relatieElement,
            final BRPActie actie,
            final int datumAanvangGeldigheid) {
        final RelatieHistorie relatieHistorie = new RelatieHistorie(getDelegate());
        if (relatieElement.getRelatieGroep() != null) {
            vulAanvangGegevens(relatieHistorie, relatieElement.getRelatieGroep(), false);
        }
        BijhoudingEntiteit.voegFormeleHistorieToe(relatieHistorie, actie, getRelatieHistorieSet());
        for (final BetrokkenheidElement betrokkenheidElement : relatieElement.getBetrokkenheden()) {
            betrokkenheidElement.maakBetrokkenheidEntiteit(this, actie, datumAanvangGeldigheid);
        }
    }

    /**
     * Maakt een nieuwe relatie historie voorkomen voor deze relatie (maar voegt het voorkomen niet toe aan de historie set). Dit voorkomen wordt
     * gevuld op basis van de gegevens in de gegeven {@link RelatieGroepElement}.
     * @param relatieGroepElement de gegevens die moet worden gebruikt om het nieuwe relatie voorkomen te maken
     * @return het nieuwe relatie voorkomen
     */
    final RelatieHistorie maakNieuweRelatieHistorieVoorCorrectie(final RelatieGroepElement relatieGroepElement) {
        final RelatieHistorie result = new RelatieHistorie(getDelegate());
        vulAanvangGegevens(result, relatieGroepElement, true);
        vulEindeGegevens(result, relatieGroepElement, true);
        return result;
    }

    private void vulAanvangGegevens(final RelatieHistorie relatieGroepEntiteit, final RelatieGroepElement relatieGroepElement, final boolean isCorrectie) {
        relatieGroepEntiteit.setDatumAanvang(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getDatumAanvang()));
        if (relatieGroepElement.getGemeenteAanvangCode() != null) {
            relatieGroepEntiteit.setGemeenteAanvang(
                    BijhoudingEntiteit.getDynamischeStamtabelRepository()
                            .getGemeenteByGemeentecode(relatieGroepElement.getGemeenteAanvangCode().getWaarde()));
        }
        if (relatieGroepElement.getLandGebiedAanvangCode() != null) {
            relatieGroepEntiteit.setLandOfGebiedAanvang(
                    BijhoudingEntiteit.getDynamischeStamtabelRepository()
                            .getLandOfGebiedByCode(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getLandGebiedAanvangCode())));
        } else if (!isCorrectie) {
            relatieGroepEntiteit.setLandOfGebiedAanvang(
                    BijhoudingEntiteit.getDynamischeStamtabelRepository().getLandOfGebiedByCode(LandOfGebied.CODE_NEDERLAND));
        }
        relatieGroepEntiteit.setWoonplaatsnaamAanvang(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getWoonplaatsnaamAanvang()));
        // buitenland
        relatieGroepEntiteit.setBuitenlandseRegioAanvang(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getBuitenlandseRegioAanvang()));
        relatieGroepEntiteit.setBuitenlandsePlaatsAanvang(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getBuitenlandsePlaatsAanvang()));
        relatieGroepEntiteit.setOmschrijvingLocatieAanvang(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getOmschrijvingLocatieAanvang()));
    }

    private void vulEindeGegevens(final RelatieHistorie relatieGroepEntiteit, final RelatieGroepElement relatieGroepElement, final boolean isCorrectie) {
        relatieGroepEntiteit.setDatumEinde(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getDatumEinde()));
        if (relatieGroepElement.getRedenEindeCode() != null) {
            relatieGroepEntiteit.setRedenBeeindigingRelatie(
                    BijhoudingEntiteit.getDynamischeStamtabelRepository()
                            .getRedenBeeindigingRelatieByCode(relatieGroepElement.getRedenEindeCode().getWaarde()));
        }
        if (relatieGroepElement.getGemeenteEindeCode() != null) {
            relatieGroepEntiteit.setGemeenteEinde(
                    BijhoudingEntiteit.getDynamischeStamtabelRepository()
                            .getGemeenteByGemeentecode(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getGemeenteEindeCode())));
        }
        if (relatieGroepElement.getLandGebiedEindeCode() != null) {
            relatieGroepEntiteit.setLandOfGebiedEinde(
                    BijhoudingEntiteit.getDynamischeStamtabelRepository()
                            .getLandOfGebiedByCode(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getLandGebiedEindeCode())));
        } else if (!isCorrectie) {
            relatieGroepEntiteit.setLandOfGebiedEinde(
                    BijhoudingEntiteit.getDynamischeStamtabelRepository().getLandOfGebiedByCode(LandOfGebied.CODE_NEDERLAND));

        }
        relatieGroepEntiteit.setWoonplaatsnaamEinde(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getWoonplaatsnaamEinde()));
        // Buitenland
        relatieGroepEntiteit.setBuitenlandseRegioEinde(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getBuitenlandseRegioEinde()));
        relatieGroepEntiteit.setBuitenlandsePlaatsEinde(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getBuitenlandsePlaatsEinde()));
        relatieGroepEntiteit.setOmschrijvingLocatieEinde(BmrAttribuut.getWaardeOfNull(relatieGroepElement.getOmschrijvingLocatieEinde()));
    }

    /**
     * Bepaalt de hoofdpersonen voor deze relatie entiteit.
     * @param verzoekBericht het bijhouding verzoek bericht
     * @return de lijst met hoofdpersonen
     */
    public final List<BijhoudingPersoon> getHoofdPersonen(final BijhoudingVerzoekBericht verzoekBericht) {
        final List<BijhoudingPersoon> result = new ArrayList<>();
        for (final BijhoudingPersoon bijhoudingPersoon : getPersonen(verzoekBericht)) {
            if (bijhoudingPersoon.isPersoonIngeschrevene()) {
                result.add(bijhoudingPersoon);
            }
        }
        return result;
    }

    /**
     * Bepaalt de personen voor deze relatie entiteit.
     * @param verzoekBericht het bijhouding verzoek bericht
     * @return de lijst met personen
     */
    final List<BijhoudingPersoon> getPersonen(final BijhoudingVerzoekBericht verzoekBericht) {
        final List<BijhoudingPersoon> result = new ArrayList<>();
        for (final Betrokkenheid betrokkenheid : getBetrokkenheidSet()) {
            result.add(zoekBijhoudingPersoonInIndexOfVoegToe(verzoekBericht, betrokkenheid.getPersoon()));
        }
        return result;
    }

    private BijhoudingPersoon zoekBijhoudingPersoonInIndexOfVoegToe(final BijhoudingVerzoekBericht verzoekBericht, final Persoon persoon) {
        if (persoon.getId() != null) {
            if (verzoekBericht.getEntiteitVoorId(BijhoudingPersoon.class, persoon.getId()) == null) {
                verzoekBericht.voegToeAanObjectSleutelIndex(BijhoudingPersoon.decorate(persoon));
            }
            return verzoekBericht.getEntiteitVoorId(BijhoudingPersoon.class, persoon.getId());
        }
        return BijhoudingPersoon.decorate(persoon);
    }

    /**
     * Maakt een nieuwe BijhoudingRelatie object.
     * @param delegate de relatie die moet worden uigebreid met bijhoudingsfunctionaliteit
     * @return een relatie met bijhoudingsfunctionaliteit
     */
    public static BijhoudingRelatie decorate(final Relatie delegate) {
        if (delegate == null) {
            return null;
        }
        return new BijhoudingRelatie(delegate);
    }

    /**
     * Laat voor deze relatie de groep Relatie.Standaard vervallen en voor alle betrokkenheden van deze relatie de groep Betrokkenheid.Identiteit vervallen.
     * @param actie de actie die dient ter verantwoording van het vervallen van voorkomens
     */
    public void laatVervallen(final BRPActie actie) {
        FormeleHistorie.laatActueelVoorkomenVervallen(getRelatieHistorieSet(), actie);
        for (final Betrokkenheid betrokkenheid : getBetrokkenheidSet()) {
            FormeleHistorie.laatActueelVoorkomenVervallen(betrokkenheid.getBetrokkenheidHistorieSet(), actie);
        }
    }
}
