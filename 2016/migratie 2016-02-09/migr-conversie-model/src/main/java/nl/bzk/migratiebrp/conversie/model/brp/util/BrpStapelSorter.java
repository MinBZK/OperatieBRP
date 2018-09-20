/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;

/**
 * Sorteren van BRP structuren.
 */
public final class BrpStapelSorter {

    private static final Comparator<BrpGroep<? extends BrpGroepInhoud>> GROEPEN_COMPARATOR = new BrpGroepenComparator();
    private static final Comparator<BrpRelatie> RELATIES_COMPARATOR = new BrpRelatiesComparator();
    private static final Comparator<BrpBetrokkenheid> BETROKKENHEDEN_COMPARATOR = new BrpBetrokkenhedenComparator();
    private static final Comparator<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> GESLACHTSNAAM_COMPARATOR = new BrpGeslachtsnaamStapelsComparator();
    private static final Comparator<BrpStapel<BrpNationaliteitInhoud>> NATIONALITEITEN_COMPARATOR = new BrpNationaliteitStapelsComparator();
    private static final Comparator<BrpStapel<BrpReisdocumentInhoud>> REISDOCUMENTEN_COMPARATOR = new BrpReisdocumentStapelsComparator();
    private static final Comparator<BrpStapel<BrpVerificatieInhoud>> VERIFICATIES_COMPARATOR = new BrpVerificatieComparator();
    private static final Comparator<BrpStapel<BrpVoornaamInhoud>> VOORNAMEN_COMPARATOR = new BrpVoornaamComparator();
    private static final Comparator<BrpActieBron> ACTIEBRON_COMPARATOR = new BrpActieBronComparator();
    private static final Comparator<BrpActie> ACTIE_COMAPARATOR = new BrpActieComparator();

    private BrpStapelSorter() {
        // for test purposes
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Sorteer de inhoud van een persoonslijst op een logische volgorde.
     *
     * @param persoonslijst
     *            De persoonslijst waarvan de inhoud gesorteerd wordt.
     * @return De gesorteerde persoonslijst (nieuw persoonslijst object).
     */
    public static BrpPersoonslijst sorteerPersoonslijst(final BrpPersoonslijst persoonslijst) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(persoonslijst);

        builder.adresStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getAdresStapel()));
        builder.persoonAfgeleidAdministratiefStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getPersoonAfgeleidAdministratiefStapel()));
        builder.behandeldAlsNederlanderIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getBehandeldAlsNederlanderIndicatieStapel()));
        builder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(
            BrpStapelSorter.sorteerStapel(persoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel()));
        builder.bijhoudingStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getBijhoudingStapel()));
        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(
            BrpStapelSorter.sorteerStapel(persoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel()));
        builder.derdeHeeftGezagIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getDerdeHeeftGezagIndicatieStapel()));
        builder.deelnameEuVerkiezingenStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getDeelnameEuVerkiezingenStapel()));
        builder.geboorteStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getGeboorteStapel()));
        sorteerNaamStapelsPersoonslijst(persoonslijst, builder);
        builder.identificatienummersStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getIdentificatienummerStapel()));
        builder.migratieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getMigratieStapel()));
        builder.inschrijvingStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getInschrijvingStapel()));
        builder.nationaliteitStapels(BrpStapelSorter.sorteerNationaliteitStapels(persoonslijst.getNationaliteitStapels()));
        builder.nummerverwijzingStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getNummerverwijzingStapel()));
        builder.onderCurateleIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getOnderCurateleIndicatieStapel()));
        builder.overlijdenStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getOverlijdenStapel()));
        builder.persoonskaartStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getPersoonskaartStapel()));
        builder.reisdocumentStapels(BrpStapelSorter.sorteerReisdocumentStapels(persoonslijst.getReisdocumentStapels()));
        builder.relaties(BrpStapelSorter.sorteerRelaties(persoonslijst.getRelaties()));
        builder.staatloosIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getStaatloosIndicatieStapel()));
        builder.uitsluitingKiesrechtStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getUitsluitingKiesrechtStapel()));
        builder.vastgesteldNietNederlanderIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getVastgesteldNietNederlanderIndicatieStapel()));
        builder.verblijfsrechtStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getVerblijfsrechtStapel()));
        builder.verificatieStapels(BrpStapelSorter.sorteerVerificatieStapels(persoonslijst.getVerificatieStapels()));
        builder.verstrekkingsbeperkingIndicatieStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getVerstrekkingsbeperkingIndicatieStapel()));

        sorteerIstStapelsPersoonslijst(persoonslijst, builder);

        return builder.build();
    }

    private static void sorteerNaamStapelsPersoonslijst(final BrpPersoonslijst persoonslijst, final BrpPersoonslijstBuilder builder) {
        builder.naamgebruikStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getNaamgebruikStapel()));
        builder.geslachtsaanduidingStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getGeslachtsaanduidingStapel()));
        builder.geslachtsnaamcomponentStapels(BrpStapelSorter.sorteerGeslachtsnaamStapels(persoonslijst.getGeslachtsnaamcomponentStapels()));
        builder.samengesteldeNaamStapel(BrpStapelSorter.sorteerStapel(persoonslijst.getSamengesteldeNaamStapel()));
        builder.voornaamStapels(BrpStapelSorter.sorteerVoornaamStapels(persoonslijst.getVoornaamStapels()));
    }

    private static void sorteerIstStapelsPersoonslijst(final BrpPersoonslijst persoonslijst, final BrpPersoonslijstBuilder builder) {
        builder.istOuder1Stapel(BrpStapelSorter.sorteerIstStapel(persoonslijst.getIstOuder1Stapel()));
        builder.istOuder2Stapel(BrpStapelSorter.sorteerIstStapel(persoonslijst.getIstOuder2Stapel()));
        builder.istHuwelijkOfGpStapels(BrpStapelSorter.sorteerIstStapels(persoonslijst.getIstHuwelijkOfGpStapels()));
        builder.istKindStapels(BrpStapelSorter.sorteerIstStapels(persoonslijst.getIstKindStapels()));
        builder.istGezagsverhoudingStapel(BrpStapelSorter.sorteerIstStapel(persoonslijst.getIstGezagsverhoudingsStapel()));
    }

    /**
     * Sorteer de inhoud van een IST stapel.
     *
     * @param stapel
     *            De te sorteren IST stapel.
     * @param <T>
     *            Type dat een subklasse is van de AbstractBrpIstGroepInhoud.
     * @return De gesorteerde inhoud van de IST stapel.
     */
    public static <T extends AbstractBrpIstGroepInhoud> BrpStapel<T> sorteerIstStapel(final BrpStapel<T> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return stapel;
        }
        final List<BrpGroep<T>> groepen = stapel.getGroepen();
        BrpStapelSorter.sorteerIstGroepLijst(groepen);

        return new BrpStapel<>(groepen);
    }

    /**
     * Sorteer de inhoud van een stapel.
     *
     * @param stapel
     *            De te sorteren stapel.
     * @param <T>
     *            Type dat een subklasse is van de AbstractBrpGroepInhoud.
     * @return De gesorteerde inhoud van de stapel.
     */
    public static <T extends BrpGroepInhoud> BrpStapel<T> sorteerStapel(final BrpStapel<T> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return stapel;
        }
        final List<BrpGroep<T>> groepen = stapel.getGroepen();

        BrpStapelSorter.sorteerGroepLijst(groepen);

        return new BrpStapel<>(groepen);
    }

    /**
     * Sorteer een lijst van betrokkenheden.
     *
     * @param ongesorteerdeBetrokkenheden
     *            De lijst met te sorteren betrokkenheden.
     * @return Een gesorteerde lijst met betrokkenheden.
     */
    public static List<BrpBetrokkenheid> sorteerBetrokkenheden(final List<BrpBetrokkenheid> ongesorteerdeBetrokkenheden) {
        if (ongesorteerdeBetrokkenheden == null || ongesorteerdeBetrokkenheden.isEmpty()) {
            return ongesorteerdeBetrokkenheden;
        }

        final List<BrpBetrokkenheid> gesorteerdeBetrokkenheden = new ArrayList<>();

        for (final BrpBetrokkenheid betrokkenheid : ongesorteerdeBetrokkenheden) {
            gesorteerdeBetrokkenheden.add(BrpStapelSorter.sorteerBetrokkenheid(betrokkenheid));
        }

        BrpStapelSorter.sorteerBetrokkenheidLijst(gesorteerdeBetrokkenheden);

        return gesorteerdeBetrokkenheden;
    }

    /**
     * Sorteer een lijst van IST stapels.
     *
     * @param ongesorteerdeStapels
     *            De lijst met te sorteren IST stapels.
     * @param <T>
     *            Subklasse van AbstractBrpIstGroepInhoud.
     * @return Een gesorteerde lijst met IST stapels.
     */
    public static <T extends AbstractBrpIstGroepInhoud> List<BrpStapel<T>> sorteerIstStapels(final List<BrpStapel<T>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }

        final List<BrpStapel<T>> gesorteerdeStapels = new ArrayList<>();
        for (final BrpStapel<T> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(BrpStapelSorter.sorteerIstStapel(stapel));
        }

        BrpStapelSorter.sorteerIstLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    /**
     * Sorteer een lijst van geslachtsnaamstapels.
     *
     * @param ongesorteerdeStapels
     *            De lijst met te sorteren geslachtsnaamstapels.
     * @return Een gesorteerde lijst met geslachtsnaamstapels.
     */
    public static List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> sorteerGeslachtsnaamStapels(
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> ongesorteerdeStapels)
    {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> gesorteerdeStapels = new ArrayList<>();

        for (final BrpStapel<BrpGeslachtsnaamcomponentInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(BrpStapelSorter.sorteerStapel(stapel));
        }

        BrpStapelSorter.sorteerGeslachtsnaamLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    /**
     * Sorteer een lijst van nationaliteitstapels.
     *
     * @param ongesorteerdeStapels
     *            De lijst met te sorteren nationaliteitstapels.
     * @return Een gesorteerde lijst met nationaliteitstapels.
     */
    public static List<BrpStapel<BrpNationaliteitInhoud>> sorteerNationaliteitStapels(final List<BrpStapel<BrpNationaliteitInhoud>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpNationaliteitInhoud>> gesorteerdeStapels = new ArrayList<>();

        for (final BrpStapel<BrpNationaliteitInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(BrpStapelSorter.sorteerStapel(stapel));
        }

        BrpStapelSorter.sorteerNationaliteitLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    /**
     * Sorteer een lijst van reisdocumentstapels.
     *
     * @param ongesorteerdeStapels
     *            De lijst met te sorteren reisdocumentstapels.
     * @return Een gesorteerde lijst met reisdocumentstapels.
     */
    public static List<BrpStapel<BrpReisdocumentInhoud>> sorteerReisdocumentStapels(final List<BrpStapel<BrpReisdocumentInhoud>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpReisdocumentInhoud>> gesorteerdeStapels = new ArrayList<>();

        for (final BrpStapel<BrpReisdocumentInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(BrpStapelSorter.sorteerStapel(stapel));
        }

        BrpStapelSorter.sorteerReisdocumentLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    /**
     * Sorteer een lijst relaties.
     *
     * @param ongesorteerdeRelaties
     *            De lijst met te sorteren relaties.
     * @return Een gesorteerde lijst met relaties.
     */
    public static List<BrpRelatie> sorteerRelaties(final List<BrpRelatie> ongesorteerdeRelaties) {
        if (ongesorteerdeRelaties == null || ongesorteerdeRelaties.isEmpty()) {
            return ongesorteerdeRelaties;
        }
        final List<BrpRelatie> gesorteerdeRelaties = new ArrayList<>();

        for (final BrpRelatie relatie : ongesorteerdeRelaties) {
            gesorteerdeRelaties.add(BrpStapelSorter.sorteerRelatie(relatie));
        }

        BrpStapelSorter.sorteerRelatieLijst(gesorteerdeRelaties);

        return gesorteerdeRelaties;
    }

    /**
     * Sorteer een lijst van verificatiestapels.
     *
     * @param ongesorteerdeStapels
     *            De lijst met te sorteren verificatiestapels.
     * @return Een gesorteerde lijst met verificatiestapels.
     */
    public static List<BrpStapel<BrpVerificatieInhoud>> sorteerVerificatieStapels(final List<BrpStapel<BrpVerificatieInhoud>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpVerificatieInhoud>> gesorteerdeStapels = new ArrayList<>();

        for (final BrpStapel<BrpVerificatieInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(sorteerStapel(stapel));
        }

        BrpStapelSorter.sorteerVerificatieLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    /**
     * Sorteer een lijst van voornaamstapels.
     *
     * @param ongesorteerdeStapels
     *            De lijst met te sorteren voornaamstapels.
     * @return Een gesorteerde lijst met voornaamstapels.
     */
    public static List<BrpStapel<BrpVoornaamInhoud>> sorteerVoornaamStapels(final List<BrpStapel<BrpVoornaamInhoud>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpVoornaamInhoud>> gesorteerdeStapels = new ArrayList<>();

        for (final BrpStapel<BrpVoornaamInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(BrpStapelSorter.sorteerStapel(stapel));
        }

        BrpStapelSorter.sorteerVoornaamLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static BrpBetrokkenheid sorteerBetrokkenheid(final BrpBetrokkenheid betrokkenheid) {
        return new BrpBetrokkenheid(
            betrokkenheid.getRol(),
            BrpStapelSorter.sorteerStapel(betrokkenheid.getIdentificatienummersStapel()),
            BrpStapelSorter.sorteerStapel(betrokkenheid.getGeslachtsaanduidingStapel()),
            BrpStapelSorter.sorteerStapel(betrokkenheid.getGeboorteStapel()),
            BrpStapelSorter.sorteerStapel(betrokkenheid.getOuderlijkGezagStapel()),
            BrpStapelSorter.sorteerStapel(betrokkenheid.getSamengesteldeNaamStapel()),
            BrpStapelSorter.sorteerStapel(betrokkenheid.getOuderStapel()),
            BrpStapelSorter.sorteerStapel(betrokkenheid.getIdentiteitStapel()));
    }

    /**
     * Sorteer een relatie.
     *
     * @param relatie
     *            De te sorteren relatie.
     * @return Een gesorteerde relatie.
     */
    public static BrpRelatie sorteerRelatie(final BrpRelatie relatie) {
        final BrpRelatie.Builder relatieBuilder = new BrpRelatie.Builder(relatie, new LinkedHashMap<Long, BrpActie>());
        relatieBuilder.betrokkenheden(BrpStapelSorter.sorteerBetrokkenheden(relatie.getBetrokkenheden()));
        relatieBuilder.relatieStapel(BrpStapelSorter.sorteerStapel(relatie.getRelatieStapel()));
        return relatieBuilder.buildZonderBijwerkenIdentiteit();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Sorteer een lijst van IST groepen.
     *
     * @param groepen
     *            De lijst met te sorteren IST groepen.
     * @param <T>
     *            Subklasse van AbstractBrpIstGroepInhoud.
     */
    public static <T extends AbstractBrpIstGroepInhoud> void sorteerIstGroepLijst(final List<BrpGroep<T>> groepen) {
        if (groepen == null || groepen.isEmpty()) {
            return;
        }

        Collections.sort(groepen, new BrpIstGroepenComparator());
    }

    /**
     * Sorteer een lijst van groepen.
     *
     * @param groepen
     *            De lijst met te sorteren groepen.
     * @param <T>
     *            Subklasse van BrpGroepInhoud.
     */
    public static <T extends BrpGroepInhoud> void sorteerGroepLijst(final List<BrpGroep<T>> groepen) {
        if (groepen == null || groepen.isEmpty()) {
            return;
        }
        Collections.sort(groepen, GROEPEN_COMPARATOR);
    }

    /**
     * Sorteer een lijst van relaties.
     *
     * @param relaties
     *            De lijst met te sorteren relaties.
     */
    public static void sorteerRelatieLijst(final List<BrpRelatie> relaties) {
        if (relaties == null || relaties.isEmpty()) {
            return;
        }
        Collections.sort(relaties, RELATIES_COMPARATOR);
    }

    /**
     * Sorteer een lijst van betrokkenheden.
     *
     * @param betrokkenheden
     *            De lijst met te sorteren betrokkenheden.
     */
    public static void sorteerBetrokkenheidLijst(final List<BrpBetrokkenheid> betrokkenheden) {
        if (betrokkenheden == null || betrokkenheden.isEmpty()) {
            return;
        }
        Collections.sort(betrokkenheden, BETROKKENHEDEN_COMPARATOR);
    }

    /**
     * Sorteer een lijst van IST stapels.
     *
     * @param istStapels
     *            De lijst met te sorteren IST stapels.
     * @param <T>
     *            Subklasse van AbstractBrpIstGroepInhoud.
     */
    public static <T extends AbstractBrpIstGroepInhoud> void sorteerIstLijst(final List<BrpStapel<T>> istStapels) {
        if (istStapels == null || istStapels.isEmpty()) {
            return;
        }
        Collections.sort(istStapels, new BrpIstStapelComparator<T>());
    }

    /**
     * Sorteer een lijst van geslachtsnaam stapels.
     *
     * @param geslachtsnaamcomponentStapels
     *            De lijst met te sorteren geslachtsnaam stapels.
     */
    public static void sorteerGeslachtsnaamLijst(final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels) {
        if (geslachtsnaamcomponentStapels == null || geslachtsnaamcomponentStapels.isEmpty()) {
            return;
        }
        Collections.sort(geslachtsnaamcomponentStapels, GESLACHTSNAAM_COMPARATOR);
    }

    /**
     * Sorteer een lijst van nationaliteit stapels.
     *
     * @param nationaliteitStapels
     *            De lijst met te sorteren nationaliteit stapels.
     */
    public static void sorteerNationaliteitLijst(final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        if (nationaliteitStapels == null || nationaliteitStapels.isEmpty()) {
            return;
        }
        Collections.sort(nationaliteitStapels, NATIONALITEITEN_COMPARATOR);
    }

    /**
     * Sorteer een lijst van reisdocument stapels.
     *
     * @param reisdocumentStapels
     *            De lijst met te sorteren reisdocument stapels.
     */
    public static void sorteerReisdocumentLijst(final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels) {
        if (reisdocumentStapels == null || reisdocumentStapels.isEmpty()) {
            return;
        }
        Collections.sort(reisdocumentStapels, REISDOCUMENTEN_COMPARATOR);
    }

    private static void sorteerVerificatieLijst(final List<BrpStapel<BrpVerificatieInhoud>> verificatieStapels) {
        if (verificatieStapels == null || verificatieStapels.isEmpty()) {
            return;
        }
        Collections.sort(verificatieStapels, VERIFICATIES_COMPARATOR);
    }

    /**
     * Sorteer een lijst van voornaam stapels.
     *
     * @param voornaamStapels
     *            De lijst met te sorteren voornaam stapels.
     */
    public static void sorteerVoornaamLijst(final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels) {
        if (voornaamStapels == null || voornaamStapels.isEmpty()) {
            return;
        }
        Collections.sort(voornaamStapels, VOORNAMEN_COMPARATOR);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T extends Comparable<T>> int compareNulls(final T o1, final T o2) {
        if (o1 == null) {
            return o2 == null ? 0 : -1;
        } else {
            return o2 == null ? 1 : o1.compareTo(o2);
        }
    }

    /**
     * Sorteer een lijst van actiebronnen.
     *
     * @param actieBronnen
     *            De lijst met te sorteren actiebronnen.
     * @return De gesorteeerde lijst met actiebronnen.
     */
    public static List<BrpActieBron> sorteerActieBronnen(final List<BrpActieBron> actieBronnen) {
        if (actieBronnen == null || actieBronnen.isEmpty()) {
            return actieBronnen;
        }

        final List<BrpActieBron> gesorteerdeActieBronnen = new ArrayList<>(actieBronnen);
        Collections.sort(gesorteerdeActieBronnen, ACTIEBRON_COMPARATOR);
        return gesorteerdeActieBronnen;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * BrpBetrokkenhedenComparator, vergelijkt betrokkenheden.
     */
    public static class BrpBetrokkenhedenComparator implements Comparator<BrpBetrokkenheid>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpBetrokkenheid o1, final BrpBetrokkenheid o2) {
            int result = compareNulls(o1.getRol(), o2.getRol());

            if (result == 0) {
                result = BrpStapelSorter.compareNulls(getGeslachtsnaam(o1), getGeslachtsnaam(o2));
            }

            return result;
        }

        private String getGeslachtsnaam(final BrpBetrokkenheid betrokkenheid) {
            if (betrokkenheid.getSamengesteldeNaamStapel() == null) {
                return null;
            } else {
                final BrpSamengesteldeNaamInhoud inhoud = betrokkenheid.getSamengesteldeNaamStapel().get(0).getInhoud();
                return BrpString.unwrap(inhoud.getGeslachtsnaamstam()) + BrpString.unwrap(inhoud.getVoornamen());
            }
        }
    }

    /**
     * BrpVerificatieComparator, vergelijkt verificatieInhoud stapels.
     */
    private static class BrpVerificatieComparator implements Comparator<BrpStapel<BrpVerificatieInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpStapel<BrpVerificatieInhoud> o1, final BrpStapel<BrpVerificatieInhoud> o2) {
            final int partij1 = o1.get(0).getInhoud().getPartij().getWaarde();
            final int partij2 = o2.get(0).getInhoud().getPartij().getWaarde();
            final BrpString soort1 = o1.get(0).getInhoud().getSoort();
            final BrpString soort2 = o2.get(0).getInhoud().getSoort();
            return partij1 > partij2 ? 1 : partij2 > partij1 ? -1 : compareNulls(soort1, soort2);
        }
    }

    /**
     * BrpVoornaamComparator, vergelijkt voornaamInhoud stapels.
     */
    public static class BrpVoornaamComparator implements Comparator<BrpStapel<BrpVoornaamInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpStapel<BrpVoornaamInhoud> o1, final BrpStapel<BrpVoornaamInhoud> o2) {
            final int volg1 = o1 == null ? -1 : BrpInteger.unwrap(o1.get(0).getInhoud().getVolgnummer());
            final int volg2 = o2 == null ? 1 : BrpInteger.unwrap(o2.get(0).getInhoud().getVolgnummer());
            return volg1 > volg2 ? 1 : volg2 > volg1 ? -1 : 0;
        }
    }

    /**
     * BrpReisdocumentStapelsComparator, vergelijkt reisdocumentInhoud stapels.
     */
    public static class BrpReisdocumentStapelsComparator implements Comparator<BrpStapel<BrpReisdocumentInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpStapel<BrpReisdocumentInhoud> o1, final BrpStapel<BrpReisdocumentInhoud> o2) {
            return BrpStapelSorter.compareNulls(getNummer(o1), getNummer(o2));
        }

        private String getNummer(final BrpStapel<BrpReisdocumentInhoud> o) {
            return BrpString.unwrap(o.get(0).getInhoud().getNummer());
        }
    }

    /**
     * BrpNationaliteitStapelsComparator, vergelijkt nationaliteitInhoud stapels.
     */
    public static class BrpNationaliteitStapelsComparator implements Comparator<BrpStapel<BrpNationaliteitInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpStapel<BrpNationaliteitInhoud> o1, final BrpStapel<BrpNationaliteitInhoud> o2) {
            return BrpStapelSorter.compareNulls(getNationaliteit(o1), getNationaliteit(o2));

        }

        private short getNationaliteit(final BrpStapel<BrpNationaliteitInhoud> o2) {
            return o2.get(0).getInhoud().getNationaliteitCode().getWaarde();
        }

    }

    /**
     * BrpIstGroepenComparator, vergelijkt IST groepen.
     */
    public static class BrpIstGroepenComparator implements Comparator<BrpGroep<? extends AbstractBrpIstGroepInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpGroep<? extends AbstractBrpIstGroepInhoud> o1, final BrpGroep<? extends AbstractBrpIstGroepInhoud> o2) {
            final int result;
            final BrpIstGroepInhoud i1 = o1.getInhoud();
            final BrpIstGroepInhoud i2 = o2.getInhoud();

            final int cat1 = i1.getCategorie().getCategorieAsInt();
            final int cat2 = i2.getCategorie().getCategorieAsInt();
            final int catResult = cat1 - cat2;
            if (catResult == 0) {
                final int stapel1 = i1.getStapel();
                final int stapel2 = i2.getStapel();
                final int stapelResult = stapel1 - stapel2;
                if (stapelResult == 0) {
                    final int voorkomen1 = i1.getVoorkomen();
                    final int voorkomen2 = i2.getVoorkomen();
                    return voorkomen1 - voorkomen2;
                } else {
                    result = stapelResult;
                }
            } else {
                result = catResult;
            }
            return result;
        }
    }

    /**
     * BrpIstStapelComparator, vergelijkt IST stapel.
     *
     * @param <T>
     *            inhoud aanduiding
     */
    public static class BrpIstStapelComparator<T extends AbstractBrpIstGroepInhoud> implements Comparator<BrpStapel<T>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpStapel<T> o1, final BrpStapel<T> o2) {
            final int result;
            final BrpIstGroepInhoud i1 = o1.get(0).getInhoud();
            final BrpIstGroepInhoud i2 = o2.get(0).getInhoud();

            final int cat1 = i1.getCategorie().getCategorieAsInt();
            final int cat2 = i2.getCategorie().getCategorieAsInt();
            final int catResult = cat1 - cat2;
            if (catResult == 0) {
                final int stapel1 = i1.getStapel();
                final int stapel2 = i2.getStapel();
                final int stapelResult = stapel1 - stapel2;
                if (stapelResult == 0) {
                    final int voorkomen1 = i1.getVoorkomen();
                    final int voorkomen2 = i2.getVoorkomen();
                    return voorkomen1 - voorkomen2;
                } else {
                    result = stapelResult;
                }
            } else {
                result = catResult;
            }
            return result;
        }
    }

    /**
     * BrpGeslachtsnaamStapelsComparator, vergelijkt geslachtsnaamcomponentInhoud stapels.
     */
    public static class BrpGeslachtsnaamStapelsComparator implements Comparator<BrpStapel<BrpGeslachtsnaamcomponentInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpStapel<BrpGeslachtsnaamcomponentInhoud> o1, final BrpStapel<BrpGeslachtsnaamcomponentInhoud> o2) {
            final int volg1 = BrpInteger.unwrap(o1.get(0).getInhoud().getVolgnummer());
            final int volg2 = BrpInteger.unwrap(o2.get(0).getInhoud().getVolgnummer());
            return volg1 > volg2 ? 1 : volg2 > volg1 ? -1 : 0;
        }

    }

    /**
     * BrpRelatiesComparator, vergelijkt relaties.
     */
    public static class BrpRelatiesComparator implements Comparator<BrpRelatie>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpRelatie o1, final BrpRelatie o2) {
            int result = o1.getSoortRelatieCode().compareTo(o2.getSoortRelatieCode());

            if (result == 0) {
                result = o1.getRolCode().compareTo(o2.getRolCode());
            }

            if (result == 0) {
                result = BrpStapelSorter.compareNulls(getAnummer(o1), getAnummer(o2));
            }
            if (result == 0) {
                result = BrpStapelSorter.compareNulls(getNaam(o1), getNaam(o2));
            }

            return result;
        }

        private String getNaam(final BrpRelatie o) {
            String result = null;
            if (o.getBetrokkenheden() != null) {
                for (final BrpBetrokkenheid betrokkenheid : o.getBetrokkenheden()) {
                    if (betrokkenheid.getIdentificatienummersStapel() != null) {
                        result = bepaalNaamUItBetrokkenheid(betrokkenheid);
                    }
                }
            }
            return result;
        }

        private String bepaalNaamUItBetrokkenheid(final BrpBetrokkenheid betrokkenheid) {
            String result = null;
            for (final BrpGroep<BrpSamengesteldeNaamInhoud> groep : betrokkenheid.getSamengesteldeNaamStapel()) {
                final String geslachtsnaamstam = BrpString.unwrap(groep.getInhoud().getGeslachtsnaamstam());
                final String voornaam = BrpString.unwrap(groep.getInhoud().getVoornamen());

                final String naam;
                if (geslachtsnaamstam == null && voornaam == null) {
                    naam = null;
                } else if (geslachtsnaamstam == null) {
                    naam = voornaam;
                } else if (voornaam == null) {
                    naam = geslachtsnaamstam;
                } else {
                    naam = geslachtsnaamstam + ", " + voornaam;
                }

                if (result == null || naam != null && result.compareTo(naam) < 0) {
                    result = naam;
                }
            }
            return result;
        }

        private Long getAnummer(final BrpRelatie o) {
            Long result = null;
            if (o.getBetrokkenheden() != null) {
                for (final BrpBetrokkenheid betrokkenheid : o.getBetrokkenheden()) {
                    if (betrokkenheid.getIdentificatienummersStapel() != null) {
                        for (final BrpGroep<BrpIdentificatienummersInhoud> groep : betrokkenheid.getIdentificatienummersStapel()) {
                            final Long anummer = BrpLong.unwrap(groep.getInhoud().getAdministratienummer());

                            if (result == null || anummer != null && result.compareTo(anummer) < 0) {
                                result = anummer;
                            }
                        }
                    }
                }
            }
            return result;
        }

    }

    /**
     * BrpGroepenComparator, vergelijkt groepInhoud.
     */
    public static class BrpGroepenComparator implements Comparator<BrpGroep<? extends BrpGroepInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpGroep<? extends BrpGroepInhoud> o1, final BrpGroep<? extends BrpGroepInhoud> o2) {
            int result = o2.getHistorie().getDatumTijdRegistratie().compareTo(o1.getHistorie().getDatumTijdRegistratie());

            if (result == 0) {
                result = -BrpStapelSorter.compareNulls(o1.getHistorie().getDatumAanvangGeldigheid(), o2.getHistorie().getDatumAanvangGeldigheid());
            }

            if (result == 0) {
                result = BrpStapelSorter.compareNulls(o1.getHistorie().getDatumTijdVerval(), o2.getHistorie().getDatumTijdVerval());
            }

            if (result == 0) {
                result = BrpStapelSorter.compareNulls(o1.getHistorie().getDatumEindeGeldigheid(), o2.getHistorie().getDatumEindeGeldigheid());
            }

            if (result == 0 && o1.getInhoud() instanceof AbstractBrpIstGroepInhoud && o2.getInhoud() instanceof AbstractBrpIstGroepInhoud) {
                result = ((AbstractBrpIstGroepInhoud) o2.getInhoud()).getVoorkomen() - ((AbstractBrpIstGroepInhoud) o1.getInhoud()).getVoorkomen();
            }

            if (result == 0 && o1.getActieInhoud() != null && o2.getActieInhoud() != null) {
                result = ACTIE_COMAPARATOR.compare(o1.getActieInhoud(), o2.getActieInhoud());
            }

            return result;
        }
    }

    /**
     * BrpActieComparator, vergelijkt acties.
     */
    public static class BrpActieComparator implements Comparator<BrpActie>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpActie o1, final BrpActie o2) {

            final int result;

            if (o1.getLo3Herkomst() == null) {
                result = o2.getLo3Herkomst() == null ? 0 : -1;
            } else if (o2.getLo3Herkomst() == null) {
                result = 1;
            } else {
                result = o2.getLo3Herkomst().getVoorkomen() - o1.getLo3Herkomst().getVoorkomen();
            }

            return result;
        }

    }

    /**
     * BrpActieBronComparator, vergelijkt actiebronnen.
     */
    public static class BrpActieBronComparator implements Comparator<BrpActieBron>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public final int compare(final BrpActieBron o1, final BrpActieBron o2) {
            int result = BrpStapelSorter.compareNulls(o1.getRechtsgrondOmschrijving(), o2.getRechtsgrondOmschrijving());

            if (result == 0) {
                if (o1.getDocumentStapel() == null && o2.getDocumentStapel() != null) {
                    result = -1;
                } else if (o1.getDocumentStapel() == null && o2.getDocumentStapel() == null) {
                    result = 0;
                } else if (o1.getDocumentStapel() != null && o2.getDocumentStapel() == null) {
                    result = 1;
                } else {
                    result = Integer.compare(o1.getDocumentStapel().hashCode(), o2.getDocumentStapel().hashCode());
                }
            }

            return result;
        }
    }
}
