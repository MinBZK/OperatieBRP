/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ontrelateren;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingRelatie;

/**
 * Deze helper class wordt gemaakt o.b.v. een relatie in de BRP tussen TWEE INGESCHREVEN personen. Deze class biedt functionaliteit voor het ontrelateren
 * van deze symmetrische relatie.
 */
public final class OntrelateerRelatieHelper {

    private enum SoortRelatieHelper {
        PARTNER, KIND_OUDER, OUDER_KIND
    }

    /* Input voor het ontrelateren. */
    private final OntrelateerContext context;
    private final BijhoudingPersoon hoofdPersoon;
    private final Betrokkenheid betrokkenheidHoofdPersoon;
    private final BijhoudingPersoon gerelateerdePersoon;
    private final Betrokkenheid betrokkenheidGerelateerdePersoon;
    private final BijhoudingRelatie relatie;
    private final SoortRelatieHelper soort;

    /* Output van het ontrelateren. */
    private BijhoudingRelatie relatieVoorVerdereVerwerking;
    private BijhoudingPersoon gerelateerdePersoonVoorVerdereVerwerking;


    private OntrelateerRelatieHelper(final OntrelateerContext context, final BijhoudingPersoon hoofdPersoon, final Betrokkenheid betrokkenheidHoofdPersoon,
                                     final BijhoudingPersoon gerelateerdePersoon,
                                     final Betrokkenheid betrokkenheidGerelateerdePersoon,
                                     final BijhoudingRelatie relatie, SoortRelatieHelper soort) {
        this.context = context;
        this.hoofdPersoon = hoofdPersoon;
        this.betrokkenheidHoofdPersoon = betrokkenheidHoofdPersoon;
        this.gerelateerdePersoon = gerelateerdePersoon;
        this.betrokkenheidGerelateerdePersoon = betrokkenheidGerelateerdePersoon;
        this.relatie = relatie;
        this.soort = soort;
    }

    /**
     * Geef de waarde van hoofdPersoon.
     * @return hoofdPersoon
     */
    public BijhoudingPersoon getHoofdPersoon() {
        return hoofdPersoon;
    }

    /**
     * Geef de waarde van betrokkenheidHoofdPersoon.
     * @return betrokkenheidHoofdPersoon
     */
    public Betrokkenheid getBetrokkenheidHoofdPersoon() {
        return betrokkenheidHoofdPersoon;
    }

    /**
     * Geef de waarde van gerelateerdePersoon.
     * @return gerelateerdePersoon
     */
    public BijhoudingPersoon getGerelateerdePersoon() {
        return gerelateerdePersoon;
    }

    /**
     * Geef de waarde van betrokkenheidGerelateerdePersoon.
     * @return betrokkenheidGerelateerdePersoon
     */
    public Betrokkenheid getBetrokkenheidGerelateerdePersoon() {
        return betrokkenheidGerelateerdePersoon;
    }

    /**
     * Geef de waarde van relatie.
     * @return relatie
     */
    public BijhoudingRelatie getRelatie() {
        return relatie;
    }

    /**
     * Deze methode zal de gegeven relatie ontrelateren. Het resultaat kan door getter methodes van deze class wordt opgevraagd.
     * @param datumTijdRegistratie datum/tijd die gebruikt wordt voor het maken van de verantwoordingsgegegevens
     */
    public void ontrelateer(final Timestamp datumTijdRegistratie) {
        final BRPActie actie = context.maakOntrelateerActie(datumTijdRegistratie, relatie);
        switch (soort) {
            case PARTNER:
                ontrelateerPartnerRelatie(actie);
                break;
            case OUDER_KIND:
                ontrelateerOuderKindRelatie(actie);
                break;
            case KIND_OUDER:
                ontrelateerKindOuderRelatie(actie);
                break;
            default:
                //kan niet voorkomen want dit wordt al afgevangen bij het maken van de OntrelateerRelatieHelper.
        }
    }

    /**
     * De relatie de gebruikt moet worden voor de verdere verwerking van het bijhoudingsbericht.
     * @return de relatie
     */
    public BijhoudingRelatie getRelatieVoorVerdereVerwerking() {
        return relatieVoorVerdereVerwerking;
    }

    /**
     * De ontrelateerde variant van de gerelateerde persoon de gebruikt moet worden voor de verdere verwerking van het bijhoudingsbericht.
     * @return de gerelateerde persoon
     */
    public BijhoudingPersoon getGerelateerdePersoonVoorVerdereVerwerking() {
        return gerelateerdePersoonVoorVerdereVerwerking;
    }

    /**
     * Bepaald of na het relateren de verdere verwerking moet verder gaan met een nieuwe relatie die ontstaan is als gevolg van het ontrelateren.
     * @return true als de nieuwe relatie gebruikt moet worden anders false
     * @see #getRelatieVoorVerdereVerwerking()
     */
    public boolean isNieuweRelatieNodigVoorVerdereVerwerking() {
        return relatieVoorVerdereVerwerking != null;
    }

    private void ontrelateerPartnerRelatie(final BRPActie actie) {
        final RelatieHistorie bronRelatieHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet());
        // Stap 1. Opzetten verantwoordingsinformatie
        kopieerActieBronnen(bronRelatieHistorie.getActieInhoud(), actie);
        // Stap 2. Nieuwe relaties aanmaken
        final BijhoudingRelatie kopieRelatieVoorHoofdPersoon = kopieerRelatie(relatie, actie, true, hoofdPersoon.getOnderzoeken());
        final BijhoudingRelatie kopieRelatieVoorGerelateerde = kopieerRelatie(relatie, actie);
        // Stap 3. Nieuwe personen aanmaken
        final BijhoudingPersoon kopieHoofdPersoon = hoofdPersoon.kopieer(bronRelatieHistorie.getDatumAanvang(), false);
        final BijhoudingPersoon
                kopieGerelateerdePersoon =
                gerelateerdePersoon.kopieer(bronRelatieHistorie.getDatumAanvang(), false, hoofdPersoon.getOnderzoeken(), actie);
        // Stap 4. Personen aan nieuwe relaties koppelen
        koppelPersonenAanRelatie(hoofdPersoon, kopieGerelateerdePersoon, kopieRelatieVoorHoofdPersoon);
        koppelPersonenAanRelatie(gerelateerdePersoon, kopieHoofdPersoon, kopieRelatieVoorGerelateerde);
        // Stap 5. Bestaande relatie laten vervallen
        relatie.laatVervallen(actie);
        // Stap 6. Personen markeren als bijgehouden
        hoofdPersoon.werkGroepAfgeleidAdministratiefBij(actie);
        gerelateerdePersoon.werkGroepAfgeleidAdministratiefBij(actie);

        // Het resultaat.
        relatieVoorVerdereVerwerking = kopieRelatieVoorHoofdPersoon;
        gerelateerdePersoonVoorVerdereVerwerking = kopieGerelateerdePersoon;
    }

    private void ontrelateerOuderKindRelatie(final BRPActie actie) {
        // Het resultaat.
        relatieVoorVerdereVerwerking =
                ontrelateerFamilierechtelijkeBetrekking(actie, betrokkenheidHoofdPersoon, hoofdPersoon, betrokkenheidGerelateerdePersoon, gerelateerdePersoon,
                        false);
    }

    private void ontrelateerKindOuderRelatie(final BRPActie actie) {
        ontrelateerFamilierechtelijkeBetrekking(actie, betrokkenheidGerelateerdePersoon, gerelateerdePersoon, betrokkenheidHoofdPersoon, hoofdPersoon, true);
        // Het resultaat.
    }

    private BijhoudingRelatie ontrelateerFamilierechtelijkeBetrekking(final BRPActie actie, final Betrokkenheid betrokkenheidOuder,
                                                                      final BijhoudingPersoon ouder,
                                                                      final Betrokkenheid betrokkenheidKind, final BijhoudingPersoon kind,
                                                                      final boolean isHoofdPersoonKind) {
        final Set<Onderzoek> kindOnderzoeken = isHoofdPersoonKind ? kind.getOnderzoeken() : Collections.emptySet();
        final Set<Onderzoek> ouderOnderzoeken = isHoofdPersoonKind ? Collections.emptySet() : ouder.getOnderzoeken();
        final RelatieHistorie bronRelatieHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet());
        final BetrokkenheidOuderHistorie
                bronOuderschapHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheidOuder.getBetrokkenheidOuderHistorieSet());
        // Stap 1. Opzetten verantwoordingsinformatie
        kopieerActieBronnen(bronRelatieHistorie.getActieInhoud(), actie);
        // Stap 2. Nieuwe persoon (kopie ouder) aanmaken
        final BijhoudingPersoon kopieOuder = ouder.kopieer(bronOuderschapHistorie.getDatumAanvangGeldigheid(), false, kindOnderzoeken, actie);
        // Stap 3. Nieuwe ouder betrokkenheid maken en aan bestaande relatie koppelen
        final Betrokkenheid kopieOuderBetrokkenheidVoorBestaandeRelatie = kopieerBetrokkenheid(betrokkenheidOuder, relatie, actie, kindOnderzoeken);
        relatie.addBetrokkenheid(kopieOuderBetrokkenheidVoorBestaandeRelatie);
        kopieOuder.addBetrokkenheid(kopieOuderBetrokkenheidVoorBestaandeRelatie);
        // Stap 4. Nieuwe persoon (kopie kind) aanmaken
        final BijhoudingPersoon kopieKind = kind.kopieer(bronOuderschapHistorie.getDatumAanvangGeldigheid(), true, ouderOnderzoeken, actie);
        // Stap 5. Nieuwe relatie aanmaken
        final BijhoudingRelatie kopieRelatieVoorOuder = kopieerRelatie(relatie, actie, false, ouderOnderzoeken);
        // Stap 6. Nieuwe kind betrokkenheid maken en aan nieuwe relatie koppelen
        final Betrokkenheid kopieKindBetrokkenheid = kopieerBetrokkenheid(betrokkenheidKind, kopieRelatieVoorOuder, actie, ouderOnderzoeken);
        kopieRelatieVoorOuder.addBetrokkenheid(kopieKindBetrokkenheid);
        kopieKind.addBetrokkenheid(kopieKindBetrokkenheid);
        // Stap 7. Nieuwe ouder betrokkenheid aanmaken en aan nieuwe relatie koppelen
        final Betrokkenheid kopieOuderBetrokkenheidVoorNieuweRelatie = kopieerBetrokkenheid(betrokkenheidOuder, kopieRelatieVoorOuder, actie, ouderOnderzoeken);
        kopieRelatieVoorOuder.addBetrokkenheid(kopieOuderBetrokkenheidVoorNieuweRelatie);
        ouder.addBetrokkenheid(kopieOuderBetrokkenheidVoorNieuweRelatie);
        // Stap 8. Bestaande Ouder betrokkenheid laten vervallen
        FormeleHistorie.laatActueelVoorkomenVervallen(betrokkenheidOuder.getBetrokkenheidHistorieSet(), actie);
        // Stap 9. Personen markeren als bijgehouden
        kind.werkGroepAfgeleidAdministratiefBij(actie);
        ouder.werkGroepAfgeleidAdministratiefBij(actie);
        // Het resultaat
        gerelateerdePersoonVoorVerdereVerwerking = isHoofdPersoonKind ? kopieOuder : kopieKind;
        return kopieRelatieVoorOuder;
    }

    private static void kopieerActieBronnen(final BRPActie bronActie, final BRPActie doelActie) {
        for (final ActieBron bronActieBron : bronActie.getActieBronSet()) {
            doelActie.koppelDocumentViaActieBron(bronActieBron.getDocument(), bronActieBron.getRechtsgrond(), bronActieBron.getRechtsgrondOmschrijving());
        }
    }

    private static BijhoudingRelatie kopieerRelatie(final BijhoudingRelatie bronRelatie, final BRPActie actie) {
        return kopieerRelatie(bronRelatie, actie, true, Collections.emptySet());
    }

    private static BijhoudingRelatie kopieerRelatie(final BijhoudingRelatie bronRelatie, final BRPActie actie, boolean ookBetrokkenenKopieren,
                                                    final Set<Onderzoek> onderzoeken) {
        final Relatie relatieEntiteit = new Relatie(bronRelatie.getSoortRelatie());
        Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, bronRelatie.getDelegate(), relatieEntiteit, actie);
        final RelatieHistorie origineleRelatieHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bronRelatie.getRelatieHistorieSet());
        final RelatieHistorie kopieRelatieHistorie = origineleRelatieHistorie.maakKopie();
        Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, origineleRelatieHistorie, kopieRelatieHistorie, actie);
        werkVerantwoordingBijVoorNieuwVoorkomen(kopieRelatieHistorie, actie);
        relatieEntiteit.addRelatieHistorie(kopieRelatieHistorie);
        if (ookBetrokkenenKopieren) {
            for (final Betrokkenheid bronBetrokkenheid : bronRelatie.getBetrokkenheidSet()) {
                relatieEntiteit.addBetrokkenheid(kopieerBetrokkenheid(bronBetrokkenheid, relatieEntiteit, actie, onderzoeken));
            }
        }
        final BijhoudingRelatie result = BijhoudingRelatie.decorate(relatieEntiteit);
        result.registreerKopieHistorie(origineleRelatieHistorie.getId(), kopieRelatieHistorie);
        return result;
    }

    private static Betrokkenheid kopieerBetrokkenheid(final Betrokkenheid bronBetrokkenheid, final Relatie nieuweRelatie, final BRPActie actie,
                                                      final Set<Onderzoek> onderzoeken) {
        final Betrokkenheid result = new Betrokkenheid(bronBetrokkenheid.getSoortBetrokkenheid(), nieuweRelatie);
        Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, bronBetrokkenheid, result, actie);
        final BetrokkenheidHistorie
                bronIdentiteitVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bronBetrokkenheid.getBetrokkenheidHistorieSet());
        final BetrokkenheidOuderHistorie
                bronOuderschapVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bronBetrokkenheid.getBetrokkenheidOuderHistorieSet());
        final BetrokkenheidOuderlijkGezagHistorie
                bronOuderlijkGezagVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bronBetrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet());

        final BetrokkenheidHistorie kopieIdentiteitHistorie = new BetrokkenheidHistorie(result);
        werkVerantwoordingBijVoorNieuwVoorkomen(kopieIdentiteitHistorie, actie);
        result.addBetrokkenheidHistorie(kopieIdentiteitHistorie);
        Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, bronIdentiteitVoorkomen, kopieIdentiteitHistorie, actie);
        if (bronOuderschapVoorkomen != null) {
            final BetrokkenheidOuderHistorie kopieOuderHistorie = bronOuderschapVoorkomen.kopieer();
            werkVerantwoordingBijVoorNieuwVoorkomen(kopieOuderHistorie, actie);
            result.addBetrokkenheidOuderHistorie(kopieOuderHistorie);
            Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, bronOuderschapVoorkomen, kopieOuderHistorie, actie);
        }
        if (bronOuderlijkGezagVoorkomen != null) {
            final BetrokkenheidOuderlijkGezagHistorie kopieOuderlijkGezagHistorie = bronOuderlijkGezagVoorkomen.kopieer();
            werkVerantwoordingBijVoorNieuwVoorkomen(kopieOuderlijkGezagHistorie, actie);
            result.addBetrokkenheidOuderlijkGezagHistorie(kopieOuderlijkGezagHistorie);
            Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, bronOuderlijkGezagVoorkomen, kopieOuderlijkGezagHistorie, actie);
        }
        return result;
    }

    private static void koppelPersonenAanRelatie(final BijhoudingPersoon persoonA, final BijhoudingPersoon persoonB,
                                                 final BijhoudingRelatie relatie) {
        persoonA.addBetrokkenheid(new ArrayList<>(relatie.getBetrokkenheidSet()).get(0));
        persoonB.addBetrokkenheid(new ArrayList<>(relatie.getBetrokkenheidSet()).get(1));
    }

    private static <T extends FormeleHistorie> void werkVerantwoordingBijVoorNieuwVoorkomen(final T historie, final BRPActie actie) {
        historie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        historie.setActieInhoud(actie);
    }

    /**
     * Maakt een OntrelateerRelatieHelper o.b.v. de gegeven personen en relatie.
     * @param context de ontrelateer context
     * @param hoofdPersoon hoofd persoon
     * @param gerelateerdePersoon gerelateerde persoon
     * @param relatie relatie
     * @return een nieuw OntrelateerRelatieHelper object
     */
    public static OntrelateerRelatieHelper getInstance(final OntrelateerContext context, final BijhoudingPersoon hoofdPersoon,
                                                       final BijhoudingPersoon gerelateerdePersoon,
                                                       final BijhoudingRelatie relatie) {
        valideerPersonen(hoofdPersoon, gerelateerdePersoon);
        valideerRelatie(relatie);

        final Betrokkenheid betrokkenheidHoofdPersoon = zoekActueleBetrokkenheidVoorPersoon(hoofdPersoon, relatie);
        final Betrokkenheid betrokkenheidGerelateerdePersoon = zoekActueleBetrokkenheidVoorPersoon(gerelateerdePersoon, relatie);

        valideerBetrokkenheden(betrokkenheidHoofdPersoon, betrokkenheidGerelateerdePersoon);

        final SoortRelatieHelper soort = bepaalSoortRelatieHelper(betrokkenheidHoofdPersoon, betrokkenheidGerelateerdePersoon);

        return new OntrelateerRelatieHelper(context, hoofdPersoon, betrokkenheidHoofdPersoon, gerelateerdePersoon, betrokkenheidGerelateerdePersoon, relatie,
                soort);
    }

    private static SoortRelatieHelper bepaalSoortRelatieHelper(final Betrokkenheid betrokkenheidHoofdPersoon,
                                                               final Betrokkenheid betrokkenheidGerelateerdePersoon) {
        if (isPartnerRelatieHelper(betrokkenheidHoofdPersoon, betrokkenheidGerelateerdePersoon)) {
            return SoortRelatieHelper.PARTNER;
        } else if (isKindOuderRelatieHelper(betrokkenheidHoofdPersoon, betrokkenheidGerelateerdePersoon)) {
            return SoortRelatieHelper.KIND_OUDER;
        } else if (isOuderKindRelatieHelper(betrokkenheidHoofdPersoon, betrokkenheidGerelateerdePersoon)) {
            return SoortRelatieHelper.OUDER_KIND;
        }
        throw new IllegalArgumentException(
                String.format("De combinatie van soort betrokkenheden (A:%s met B:%s) is niet mogelijk.", betrokkenheidHoofdPersoon.getSoortBetrokkenheid(),
                        betrokkenheidGerelateerdePersoon.getSoortBetrokkenheid()));
    }

    private static boolean isPartnerRelatieHelper(final Betrokkenheid betrokkenheidHoofdPersoon, final Betrokkenheid betrokkenheidGerelateerdePersoon) {
        return SoortBetrokkenheid.PARTNER.equals(betrokkenheidHoofdPersoon.getSoortBetrokkenheid()) && SoortBetrokkenheid.PARTNER
                .equals(betrokkenheidGerelateerdePersoon.getSoortBetrokkenheid());
    }

    private static boolean isKindOuderRelatieHelper(final Betrokkenheid betrokkenheidHoofdPersoon, final Betrokkenheid betrokkenheidGerelateerdePersoon) {
        return SoortBetrokkenheid.KIND.equals(betrokkenheidHoofdPersoon.getSoortBetrokkenheid()) && SoortBetrokkenheid.OUDER
                .equals(betrokkenheidGerelateerdePersoon.getSoortBetrokkenheid());
    }

    private static boolean isOuderKindRelatieHelper(final Betrokkenheid betrokkenheidHoofdPersoon, final Betrokkenheid betrokkenheidGerelateerdePersoon) {
        return SoortBetrokkenheid.OUDER.equals(betrokkenheidHoofdPersoon.getSoortBetrokkenheid()) && SoortBetrokkenheid.KIND
                .equals(betrokkenheidGerelateerdePersoon.getSoortBetrokkenheid());
    }

    private static Betrokkenheid zoekActueleBetrokkenheidVoorPersoon(final Persoon persoon, final Relatie relatie) {
        for (final Betrokkenheid actueleBetrokkenheidVoorRelatie : relatie.getActueleBetrokkenheidSet()) {
            if (actueleBetrokkenheidVoorRelatie.getPersoon() != null && actueleBetrokkenheidVoorRelatie.getPersoon().getId() != null
                    && actueleBetrokkenheidVoorRelatie.getPersoon().getId().equals(persoon.getId())) {
                return actueleBetrokkenheidVoorRelatie;
            }
        }
        throw new IllegalArgumentException(
                String.format("Er kan geen actuele betrokkenheid gevonden worden tussen relatie (id:%s) en persoon (id:%s).", relatie.getId(),
                        persoon.getId()));
    }

    private static void valideerRelatie(final Relatie relatie) {
        if (FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet()) == null) {
            throw new IllegalArgumentException("Een relatie moet een actueel historie voorkomen bevatten.");
        }
        if ((SoortRelatie.HUWELIJK.equals(relatie.getSoortRelatie()) || SoortRelatie.GEREGISTREERD_PARTNERSCHAP.equals(relatie.getSoortRelatie()))
                && FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet()).getDatumAanvang() == null) {
            throw new IllegalArgumentException("Een HGP relatie moet een datum aanvang bevatten.");
        }
        if (relatie.getId() == null) {
            throw new IllegalArgumentException("Alleen opgeslagen relaties kunnen ontrelateerd worden.");
        }
    }

    private static void valideerPersonen(final BijhoudingPersoon hoofdPersoon, final BijhoudingPersoon gerelateerdePersoon) {
        for (final Persoon persoon : new Persoon[]{hoofdPersoon, gerelateerdePersoon}) {
            if (!persoon.isPersoonIngeschrevene()) {
                throw new IllegalArgumentException("Alleen relaties tussen ingeschreven personen kunnen ontrelateerd worden.");
            }
            if (persoon.getId() == null) {
                throw new IllegalArgumentException("Alleen relaties tussen opgeslagen personen kunnen ontrelateerd worden.");
            }
        }
        if (!hoofdPersoon.isVerwerkbaar()) {
            throw new IllegalArgumentException("De hoofdpersoon met verwerkbaar zijn.");
        }
        if (gerelateerdePersoon.isVerwerkbaar()) {
            throw new IllegalArgumentException("De gerelateerde persoon mag niet verwerkbaar zijn.");
        }
    }

    private static void valideerBetrokkenheden(final Betrokkenheid... betrokkenheden) {
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            if (SoortBetrokkenheid.OUDER.equals(betrokkenheid.getSoortBetrokkenheid()) && FormeleHistorieZonderVerantwoording
                    .getActueelHistorieVoorkomen(betrokkenheid.getBetrokkenheidOuderHistorieSet()) == null) {
                throw new IllegalArgumentException("Een ouder betrokkenheid moet een actueel ouderschap historie voorkomen bevatten.");
            }
            if (SoortBetrokkenheid.OUDER.equals(betrokkenheid.getSoortBetrokkenheid()) && FormeleHistorieZonderVerantwoording
                    .getActueelHistorieVoorkomen(betrokkenheid.getBetrokkenheidOuderHistorieSet()).getDatumAanvangGeldigheid() == null) {
                throw new IllegalArgumentException("Een ouder betrokkenheid moet een datum aanvang ouderschap bevatten.");
            }
        }
    }
}
