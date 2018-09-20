/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;

/** . */
public final class RelatieUtils {
    /** . */
    private RelatieUtils() {

    }

    /**
     * Haal de moeder uit een 'geboorte persoon bericht'.
     * LET OP: Dit geldt alleen voor Berichten, niet modellen, omdat deze flag transient is.
     * We misbruiken op dit ogenblik deze flag als de moeder waaruit het kind geboren is. (!!)
     * <p/>
     * Theoritisch kunnen meerdere personen gevonden worden.
     * <p/>
     * De 'moeder' is gedefinieerd als de ouder die de indicatie xxx heeft.
     *
     * @param kind het 'kind'
     * @return de moeder in de vorm van persoonBericht (of null als niet gevonden omdat geen relatie of
     *         deze flag nergens aanstaat).
     */
    public static Persoon haalMoederUitPersoon(final Persoon kind) {
        List<Persoon> moeders = haalAlleMoedersUitPersoon(kind);
        if (moeders.size() > 0) {
            // let op: we gaan vanuit dat er al een validatie plaatsvindt dat er slechts een moeder gevonden kan worden.
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
        List<Persoon> moeders = new ArrayList<Persoon>();

        if (null != kind.getBetrokkenheden()) {
            for (Betrokkenheid betr : kind.getBetrokkenheden()) {
                if (betr.getRelatie().getSoort() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
                    for (Betrokkenheid deelnemer : betr.getRelatie().getBetrokkenheden()) {
                        if (SoortBetrokkenheid.OUDER == deelnemer.getRol()) {
                            final Ouder ouder = (Ouder) deelnemer;
                            if (null != ouder.getOuderschap()
                                && null != ouder.getOuderschap().getIndicatieOuderUitWieKindIsVoortgekomen())
                            {
                                moeders.add(ouder.getPersoon());
                            }
                        }
                    }
                }
            }
        }
        return moeders;
    }

    /**
     * .
     *
     * @param relatie .
     * @return .
     */
    public static List<Persoon> haalAlleMoedersUitRelatie(final Relatie relatie) {
        List<Persoon> moeders = new ArrayList<Persoon>();
        if (relatie.getSoort() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == deelnemer.getRol()) {
                    final Ouder ouder = (Ouder) deelnemer;
                    if (null != ouder.getOuderschap()
                        && null != ouder.getOuderschap().getIndicatieOuderUitWieKindIsVoortgekomen())
                    {
                        moeders.add(ouder.getPersoon());
                    }
                }
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
        List<Persoon> nietMoeders = new ArrayList<Persoon>();
        if (relatie.getSoort() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == deelnemer.getRol()) {
                    final Ouder ouder = (Ouder) deelnemer;
                    if (null != ouder.getOuderschap()
                        && null == ouder.getOuderschap().getIndicatieOuderUitWieKindIsVoortgekomen())
                    {
                        nietMoeders.add(ouder.getPersoon());
                    }
                }
            }
        }
        return nietMoeders;
    }

    /**
     * .
     *
     * @param relatie .
     * @return .
     */
    public static Persoon haalMoederUitRelatie(final Relatie relatie) {
        List<Persoon> moeders = haalAlleMoedersUitRelatie(relatie);
        if (moeders.size() > 0) {
            // let op: we gaan vanuit dat er al een validatie plaatsvindt dat er slechts een moeder gevonden kan worden.
            // Om die te testen kan men de methode haalAlleMoedersUitPersoon aanroepen.
            return moeders.get(0);
        }
        return null;
    }

    /**
     * .
     *
     * @param relatie .
     * @return .
     */
    public static Persoon haalNietMoederUitRelatie(final Relatie relatie) {
        List<Persoon> nietMoeders = haalAlleNietMoedersUitRelatie(relatie);
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
        if (relatie.getSoort() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (deelnemer.getRol() == SoortBetrokkenheid.KIND) {
                    // er is EEN kind binnen een fam.rech.betr.
                    kind = deelnemer.getPersoon();
                    break;
                }
            }
        }
        return kind;
    }

    /**
     * Haal op de lijst van ouders in een familirechtelijke relatie.
     *
     * @param relatie de relatie
     * @return een list van ouder(s) of lege lijst als het verkeerde relatie of geen ouders gevonden is.
     */
    public static List<Persoon> haalOudersUitRelatie(final Relatie relatie) {
        List<Persoon> ouders = new ArrayList<Persoon>();
        if (relatie.getSoort() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (deelnemer.getRol() == SoortBetrokkenheid.OUDER) {
                    // er is EEN kind binnen een fam.rech.betr.
                    ouders.add(deelnemer.getPersoon());
                }
            }
        }
        return ouders;
    }

    /**
     * Haal op de lijst van partners uit een relatie.
     *
     * @param relatie de relatie
     * @return een list van partner(s) of lege lijst als het verkeerde relatie of geen partners gevonden is.
     */
    public static List<Persoon> haalPartnersUitRelatie(final Relatie relatie) {
        List<Persoon> partners = new ArrayList<Persoon>();
        if (relatie.getSoort() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP
            || relatie.getSoort() == SoortRelatie.HUWELIJK)
        {
            for (Betrokkenheid deelnemer : relatie.getBetrokkenheden()) {
                if (deelnemer.getRol() == SoortBetrokkenheid.PARTNER) {
                    // er is EEN kind binnen een fam.rech.betr.
                    partners.add(deelnemer.getPersoon());
                }
            }
        }
        return partners;
    }

    /**
     * Haal alle kinderen op van een persoon via de familie rechtelijke betrekking relaties.
     *
     * @param ouder de ouder
     * @return de lijst met kinderen
     */
    public static List<Persoon> haalAlleKinderenUitPersoon(final Persoon ouder) {
        List<Persoon> kinderen = new ArrayList<Persoon>();
        if (null != ouder.getBetrokkenheden()) {
            for (Betrokkenheid betrokkenheid : ouder.getBetrokkenheden()) {
                if (betrokkenheid.getRelatie().getSoort() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING
                    && betrokkenheid.getRol() == SoortBetrokkenheid.OUDER)
                {
                    for (Betrokkenheid deelnemer : betrokkenheid.getRelatie().getBetrokkenheden()) {
                        if (deelnemer.getRol() == SoortBetrokkenheid.KIND) {
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
        final List<Betrokkenheid> partners = new ArrayList<Betrokkenheid>();
        if (null != persoon.getBetrokkenheden()) {
            for (Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
                if (SoortBetrokkenheid.PARTNER == betrokkenheid.getRol()) {
                    partners.add(betrokkenheid);
                }
            }
        }
        return partners;
    }
}

