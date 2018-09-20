/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Kind;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;


/**
 * .
 */
public final class RelatieUtils {

    /**
     * .
     */
    private RelatieUtils() {

    }

    /**
     * Haal de moeder uit een 'geboorte persoon bericht'. LET OP: Dit geldt alleen voor Berichten, niet modellen, omdat deze flag transient is.
     * We gebruiken op dit ogenblik deze flag als de moeder waaruit het kind geboren is.
     * <p/>
     * Theoretisch kunnen meerdere personen gevonden worden.
     * <p/>
     * De 'moeder' is gedefinieerd als de ouder die de indicatie xxx heeft.
     *
     * @param kind het 'kind'
     * @return de moeder in de vorm van persoonBericht (of null als niet gevonden omdat geen relatie of deze flag nergens aanstaat).
     */
    public static Persoon haalMoederUitPersoon(final Persoon kind) {
        final List<Persoon> moeders = haalAlleMoedersUitPersoon(kind);
        if (moeders.size() > 0) {
            // let op: we gaan ervan uit dat er al een validatie plaatsvindt dat er slechts een moeder gevonden kan worden.
            // Om die te testen kan men de methode haalAlleMoedersUitPersoon aanroepen.
            return moeders.get(0);
        }
        return null;
    }

    /**
     * .
     *
     * @param kind .
     * @return .
     */
    public static List<Persoon> haalAlleMoedersUitPersoon(final Persoon kind) {
        final List<Persoon> moeders = new ArrayList<>();

        if (null != kind.getBetrokkenheden()) {
            for (final Betrokkenheid betr : kind.getBetrokkenheden()) {
                if (betr.getRelatie().getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
                    for (final Betrokkenheid deelnemer : betr.getRelatie().getBetrokkenheden()) {
                        voegMoederToe(moeders, deelnemer);
                    }
                }
            }
        }
        return moeders;
    }

    /**
     * Voegt een model toe.
     *
     * @param moeders   De lijst van moeders.
     * @param deelnemer De potentiele moeder.
     */
    private static void voegMoederToe(final List<Persoon> moeders, final Betrokkenheid deelnemer) {
        if (SoortBetrokkenheid.OUDER == deelnemer.getRol().getWaarde()) {
            final Ouder ouder = (Ouder) deelnemer;
            if (null != ouder.getOuderschap()
                && null != ouder.getOuderschap().getIndicatieOuderUitWieKindIsGeboren())
            {
                moeders.add(ouder.getPersoon());
            }
        }
    }

    /**
     * .
     *
     * @param relatie .
     * @return .
     */
    public static List<Persoon> haalAlleMoedersUitRelatie(final Relatie relatie) {
        final List<Persoon> moeders = new ArrayList<>();
        if (relatie.getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (final Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                voegMoederToe(moeders, deelnemer);
            }
        }
        return moeders;
    }

    /**
     * Haal alle personen uit de RelatieBericht dat NIET de indicatie 'indAdresGevend' heeft.
     *
     * @param relatie het relatie bericht
     * @return de lijst van personen (0,1,2) die de indicatie NIET heeft.
     */
    public static List<Persoon> haalAlleNietMoedersUitRelatie(final Relatie relatie) {
        final List<Persoon> nietMoeders = new ArrayList<>();
        if (relatie.getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (final Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == deelnemer.getRol().getWaarde()) {
                    final Ouder ouder = (Ouder) deelnemer;
                    // nieuwe model, als indicatie is niet gezet, dan bestaat de groep ook niet.
                    // oude model: groep bestond altijd, alleen de indicatie kan Ja of null zijn.
                    if (null == ouder.getOuderschap() || null == ouder.getOuderschap()
                        .getIndicatieOuderUitWieKindIsGeboren())
                    {
                        nietMoeders.add(ouder.getPersoon());
                    }
                }
            }
        }
        return nietMoeders;
    }

    /**
     * Haalt de moeder uit een relatie.
     *
     * @param relatie
     * @return
     */
    public static Persoon haalMoederUitRelatie(final Relatie relatie) {
        final List<Persoon> moeders = haalAlleMoedersUitRelatie(relatie);
        if (moeders.size() > 0) {
            // let op: we gaan vanuit dat er al een validatie plaatsvindt dat er slechts een moeder gevonden kan worden.
            // Om die te testen kan men de methode haalAlleMoedersUitPersoon aanroepen.
            return moeders.get(0);
        }
        return null;
    }

    /**
     * Haalt de niet-moeder uit een relatie.
     *
     * @param relatie .
     * @return .
     */
    public static Persoon haalNietMoederUitRelatie(final Relatie relatie) {
        final List<Persoon> nietMoeders = haalAlleNietMoedersUitRelatie(relatie);
        if (nietMoeders.size() > 0) {
            // let op: we gaan vanuit dat er al een validatie plaatsvindt dat er slechts een moeder gevonden kan worden.
            // Om die te testen kan men de methode haalAlleMoedersUitPersoon aanroepen.
            return nietMoeders.get(0);
        }
        return null;
    }

    /**
     * .
     *
     * @param relatie .
     * @return .
     */
    public static Persoon haalKindUitRelatie(final Relatie relatie) {
        Persoon kind = null;
        if (relatie.getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (final Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (deelnemer.getRol().getWaarde() == SoortBetrokkenheid.KIND) {
                    // er is EEN kind binnen een fam.rech.betr.
                    kind = deelnemer.getPersoon();
                    break;
                }
            }
        }
        return kind;
    }

    /**
     * Haalt de lijst van ouders op in een familierechtelijke relatie.
     *
     * @param relatie de relatie
     * @return een list van ouder(s) of lege lijst als het verkeerde relatie of geen ouders gevonden is.
     */
    public static List<Persoon> haalOudersUitRelatie(final Relatie relatie) {
        final List<Persoon> ouders = new ArrayList<>();
        if (relatie.getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (final Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (deelnemer.getRol().getWaarde() == SoortBetrokkenheid.OUDER) {
                    // er is EEN kind binnen een fam.rech.betr.
                    ouders.add(deelnemer.getPersoon());
                }
            }
        }
        return ouders;
    }

    /**
     * Haal op de lijst van ouders (Betrokkenheid) in een familirechtelijke relatie.
     *
     * @param relatie de relatie
     * @return een list van ouder(s) of lege lijst als het verkeerde relatie of geen ouders gevonden is.
     */
    public static List<Ouder> haalOuderBetrokkenhedenUitRelatie(final Relatie relatie) {
        final List<Ouder> ouders = new ArrayList<>();
        if (relatie.getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (final Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (deelnemer.getRol().getWaarde() == SoortBetrokkenheid.OUDER) {
                    ouders.add((Ouder) deelnemer);
                }
            }
        }
        return ouders;
    }

    /**
     * Haal op de lijst van ouders in de familierechtelijke relatie waar deze persoon het kind is.
     *
     * @param kind kind van wie ouders gevonden moeten worden.
     * @return collectie van ouders.
     */
    public static List<Persoon> haalOudersUitKind(final Persoon kind) {
        for (final Betrokkenheid betrokkenheidVanKind : kind.getBetrokkenheden()) {
            if (betrokkenheidVanKind.getRol().getWaarde() == SoortBetrokkenheid.KIND) {
                return haalOudersUitRelatie(betrokkenheidVanKind.getRelatie());
            }

        }
        return new ArrayList<Persoon>();
    }

    /**
     * Haal op de lijst van ouderschappen in de familierechtelijke relatie waar deze persoon het kind is.
     *
     * @param kind kind van wie ouders-ouderschappen gevonden moeten worden.
     * @return collectie van ouders.
     */
    public static List<Ouder> haalOuderschappenUitKind(final Persoon kind) {
        for (final Betrokkenheid betrokkenheidVanKind : kind.getBetrokkenheden()) {
            if (betrokkenheidVanKind.getRol().getWaarde() == SoortBetrokkenheid.KIND) {
                return haalOuderBetrokkenhedenUitRelatie(betrokkenheidVanKind.getRelatie());
            }

        }
        return new ArrayList<>();
    }

    /**
     * Haal op de lijst van partners uit een relatie.
     *
     * @param relatie      de relatie
     * @param soortPersoon de soort persoon om op te filteren (null voor allen)
     * @return een lijst van partner(s) of lege lijst als het verkeerde relatie of geen partners gevonden is.
     */
    public static List<Persoon> haalPartnersUitRelatie(final Relatie relatie, final SoortPersoon soortPersoon) {
        final List<Persoon> partners = new ArrayList<>();
        if (relatie.getSoort().getWaarde() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP
            || relatie.getSoort().getWaarde() == SoortRelatie.HUWELIJK)
        {
            for (final Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (deelnemer.getRol().getWaarde() == SoortBetrokkenheid.PARTNER
                    && (soortPersoon == null || deelnemer.getPersoon().getSoort().getWaarde() == soortPersoon))
                {
                    partners.add(deelnemer.getPersoon());
                }
            }
        }
        return partners;
    }

    /**
     * Haal op de lijst van partners uit een relatie.
     *
     * @param relatie de relatie
     * @return een lijst van partner(s) of lege lijst als het verkeerde relatie of geen partners gevonden is.
     */
    public static List<Persoon> haalPartnersUitRelatie(final Relatie relatie) {
        return haalPartnersUitRelatie(relatie, null);
    }

    /**
     * Haal alle kinderen op van een persoon via de familie rechtelijke betrekking relaties.
     *
     * @param ouder de ouder
     * @return de lijst met kinderen
     */
    public static List<Persoon> haalAlleKinderenUitPersoon(final Persoon ouder) {
        final List<Persoon> kinderen = new ArrayList<>();
        if (null != ouder.getBetrokkenheden()) {
            for (final Betrokkenheid betrokkenheid : ouder.getBetrokkenheden()) {
                if (betrokkenheid.getRelatie().getSoort().getWaarde() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING
                    && betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.OUDER)
                {
                    for (final Betrokkenheid deelnemer : betrokkenheid.getRelatie().getBetrokkenheden()) {
                        if (deelnemer.getRol().getWaarde() == SoortBetrokkenheid.KIND) {
                            // er is EEN kind binnen een fam.rech.betr.
                            kinderen.add(deelnemer.getPersoon());
                        }
                    }
                }
            }
        }
        return kinderen;
    }

    /**
     * Haal alle betrokkenheden van het type Partner op uit een persoon.
     *
     * @param persoon de persoon
     * @return lijst met betrokkenheden
     */
    public static List<Betrokkenheid> haalAllePartnerBetrokkenhedenUitPersoon(final Persoon persoon) {
        final List<Betrokkenheid> partners = new ArrayList<>();
        if (null != persoon.getBetrokkenheden()) {
            for (final Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
                if (SoortBetrokkenheid.PARTNER == betrokkenheid.getRol().getWaarde()) {
                    partners.add(betrokkenheid);
                }
            }
        }
        return partners;
    }

    /**
     * Haal de betrokkenheid van het type Kind op uit een persoon. Kan null terug geven als er geen kind betrokkenheid bestaat voor deze persoon.
     *
     * @param persoon de persoon
     * @return de betrokkenheid
     */
    public static Kind haalKindBetrokkenheidUitPersoon(final Persoon persoon) {
        Kind kindBetrokkenheid = null;
        if (null != persoon.getBetrokkenheden()) {
            for (final Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
                if (betrokkenheid instanceof Kind) {
                    kindBetrokkenheid = (Kind) betrokkenheid;
                    break;
                }
            }
        }
        return kindBetrokkenheid;
    }

    /**
     * Controleert of persoon1 en persoon2 met elkaar gehuwd zijn.
     *
     * @param persoon1 persoon 1
     * @param persoon2 persoon 2
     * @return true indien gehuwd, anders false.
     */
    public static boolean zijnPersonenGehuwd(final Persoon persoon1, final Persoon persoon2) {
        return hebbenPersonenRelatieVanSoort(persoon1, persoon2, SoortRelatie.HUWELIJK);
    }

    /**
     * Controleert of persoon1 en persoon2 met elkaar geregistreerd partner zijn.
     *
     * @param persoon1 persoon 1
     * @param persoon2 persoon 2
     * @return true indien gehuwd, anders false.
     */
    public static boolean zijnPersonenGeregistreerdPartner(final Persoon persoon1, final Persoon persoon2) {
        return hebbenPersonenRelatieVanSoort(persoon1, persoon2, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
    }

    /**
     * Controleert of twee personen een relatie van soortRelatie hebben. Dit kan alleen met de soorten huwelijk en geregistreerd partnerschap.
     *
     * @param persoon1     een willekeurig persoon die mogelijk partner is van persoon 2
     * @param persoon2     een willekeurig persoon die mogelijk partner is van persoon 1
     * @param soortRelatie de soort relatie die gecontroleerd wordt huwelijk of geregistreerd partnerschap
     * @return true indien persoon 1 een relatie heeft met persoon 2
     */
    public static boolean hebbenPersonenRelatieVanSoort(final Persoon persoon1, final Persoon persoon2, final SoortRelatie soortRelatie) {
        if (SoortRelatie.HUWELIJK != soortRelatie && SoortRelatie.GEREGISTREERD_PARTNERSCHAP != soortRelatie) {
            throw new IllegalArgumentException("Invalide soort relatie voor functie: " + soortRelatie);
        }

        boolean resultaat = false;
        final List<Betrokkenheid> partnerBetrokkenheden = RelatieUtils.haalAllePartnerBetrokkenhedenUitPersoon(persoon1);

        for (final Betrokkenheid partnerBetrokkenheid : partnerBetrokkenheden) {
            final Relatie hgp = partnerBetrokkenheid.getRelatie();
            if (soortRelatie == hgp.getSoort().getWaarde()) {
                for (final Betrokkenheid partnerBetr : hgp.getBetrokkenheden()) {
                    final Persoon partnerBetrPersoon = partnerBetr.getPersoon();
                    if (!partnerBetrPersoon.isPersoonGelijkAan(persoon1) && persoon2.isPersoonGelijkAan(partnerBetrPersoon)) {
                        resultaat = true;
                        break;
                    }
                }
            }
        }
        return resultaat;
    }
}
