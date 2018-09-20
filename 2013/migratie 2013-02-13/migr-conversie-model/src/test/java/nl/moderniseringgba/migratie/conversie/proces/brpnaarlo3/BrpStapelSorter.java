/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;

/**
 * Sorteren van BRP structuren.
 */
public final class BrpStapelSorter {

    private static final Comparator<BrpGroep<? extends BrpGroepInhoud>> GROEPEN_COMPARATOR =
            new BrpGroepenComparator();
    private static final Comparator<BrpRelatie> RELATIES_COMPARATOR = new BrpRelatiesComparator();
    private static final Comparator<BrpBetrokkenheid> BETROKKENHEDEN_COMPARATOR = new BrpBetrokkenhedenComparator();
    private static final Comparator<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> GESLACHTSNAAM_COMPARATOR =
            new BrpGeslachtsnaamStapelsComparator();
    private static final Comparator<BrpStapel<BrpNationaliteitInhoud>> NATIONALITEITEN_COMPARATOR =
            new BrpNationaliteitStapelsComparator();
    private static final Comparator<BrpStapel<BrpReisdocumentInhoud>> REISDOCUMENTEN_COMPARATOR =
            new BrpReisdocumentStapelsComparator();
    private static final Comparator<BrpStapel<BrpVoornaamInhoud>> VOORNAMEN_COMPARATOR = new BrpVoornaamComparator();

    private BrpStapelSorter() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static BrpPersoonslijst sorteerPersoonslijst(final BrpPersoonslijst persoonslijst) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        builder.aanschrijvingStapel(sorteerStapel(persoonslijst.getAanschrijvingStapel()));
        builder.adresStapel(sorteerStapel(persoonslijst.getAdresStapel()));
        builder.afgeleidAdministratiefStapel(sorteerStapel(persoonslijst.getAfgeleidAdministratiefStapel()));
        builder.behandeldAlsNederlanderIndicatieStapel(sorteerStapel(persoonslijst
                .getBehandeldAlsNederlanderIndicatieStapel()));
        builder.belemmeringVerstrekkingReisdocumentIndicatieStapel(sorteerStapel(persoonslijst
                .getBelemmeringVerstrekkingReisdocumentIndicatieStapel()));
        builder.bezitBuitenlandsReisdocumentIndicatieStapel(sorteerStapel(persoonslijst
                .getBezitBuitenlandsReisdocumentIndicatieStapel()));
        builder.bijhoudingsgemeenteStapel(sorteerStapel(persoonslijst.getBijhoudingsgemeenteStapel()));
        builder.bijhoudingsverantwoordelijkheidStapel(sorteerStapel(persoonslijst
                .getBijhoudingsverantwoordelijkheidStapel()));
        builder.derdeHeeftGezagIndicatieStapel(sorteerStapel(persoonslijst.getDerdeHeeftGezagIndicatieStapel()));
        builder.europeseVerkiezingenStapel(sorteerStapel(persoonslijst.getEuropeseVerkiezingenStapel()));
        builder.geboorteStapel(sorteerStapel(persoonslijst.getGeboorteStapel()));
        builder.geprivilegieerdeIndicatieStapel(sorteerStapel(persoonslijst.getGeprivilegieerdeIndicatieStapel()));
        builder.geslachtsaanduidingStapel(sorteerStapel(persoonslijst.getGeslachtsaanduidingStapel()));
        builder.geslachtsnaamcomponentStapels(sorteerGeslachtsnaamStapels(persoonslijst
                .getGeslachtsnaamcomponentStapels()));
        builder.identificatienummersStapel(sorteerStapel(persoonslijst.getIdentificatienummerStapel()));
        builder.immigratieStapel(sorteerStapel(persoonslijst.getImmigratieStapel()));
        builder.inschrijvingStapel(sorteerStapel(persoonslijst.getInschrijvingStapel()));
        builder.nationaliteitStapels(sorteerNationaliteitStapels(persoonslijst.getNationaliteitStapels()));
        builder.onderCurateleIndicatieStapel(sorteerStapel(persoonslijst.getOnderCurateleIndicatieStapel()));
        builder.opschortingStapel(sorteerStapel(persoonslijst.getOpschortingStapel()));
        builder.overlijdenStapel(sorteerStapel(persoonslijst.getOverlijdenStapel()));
        builder.persoonskaartStapel(sorteerStapel(persoonslijst.getPersoonskaartStapel()));
        builder.reisdocumentStapels(sorteerReisdocumentStapels(persoonslijst.getReisdocumentStapels()));
        builder.relaties(sorteerRelaties(persoonslijst.getRelaties()));
        builder.samengesteldeNaamStapel(sorteerStapel(persoonslijst.getSamengesteldeNaamStapel()));
        builder.statenloosIndicatieStapel(sorteerStapel(persoonslijst.getStatenloosIndicatieStapel()));
        builder.uitsluitingNederlandsKiesrechtStapel(sorteerStapel(persoonslijst
                .getUitsluitingNederlandsKiesrechtStapel()));
        builder.vastgesteldNietNederlanderIndicatieStapel(sorteerStapel(persoonslijst
                .getVastgesteldNietNederlanderIndicatieStapel()));
        builder.verblijfsrechtStapel(sorteerStapel(persoonslijst.getVerblijfsrechtStapel()));
        builder.verstrekkingsbeperkingStapel(sorteerStapel(persoonslijst.getVerstrekkingsbeperkingStapel()));
        builder.voornaamStapels(sorteerVoornaamStapels(persoonslijst.getVoornaamStapels()));

        return builder.build();
    }

    public static <T extends BrpGroepInhoud> BrpStapel<T> sorteerStapel(final BrpStapel<T> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return stapel;
        }
        final List<BrpGroep<T>> groepen = stapel.getGroepen();

        sorteerGroepLijst(groepen);

        return new BrpStapel<T>(groepen);
    }

    public static List<BrpBetrokkenheid> sorteerBetrokkenheden(
            final List<BrpBetrokkenheid> ongesorteerdeBetrokkenheden) {
        if (ongesorteerdeBetrokkenheden == null || ongesorteerdeBetrokkenheden.isEmpty()) {
            return ongesorteerdeBetrokkenheden;
        }

        final List<BrpBetrokkenheid> gesorteerdeBetrokkenheden = new ArrayList<BrpBetrokkenheid>();

        for (final BrpBetrokkenheid betrokkenheid : ongesorteerdeBetrokkenheden) {
            gesorteerdeBetrokkenheden.add(sorteerBetrokkenheid(betrokkenheid));
        }

        sorteerBetrokkenheidLijst(gesorteerdeBetrokkenheden);

        return gesorteerdeBetrokkenheden;
    }

    public static List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> sorteerGeslachtsnaamStapels(
            final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> gesorteerdeStapels =
                new ArrayList<BrpStapel<BrpGeslachtsnaamcomponentInhoud>>();

        for (final BrpStapel<BrpGeslachtsnaamcomponentInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(sorteerStapel(stapel));
        }

        sorteerGeslachtsnaamLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    public static List<BrpStapel<BrpNationaliteitInhoud>> sorteerNationaliteitStapels(
            final List<BrpStapel<BrpNationaliteitInhoud>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpNationaliteitInhoud>> gesorteerdeStapels =
                new ArrayList<BrpStapel<BrpNationaliteitInhoud>>();

        for (final BrpStapel<BrpNationaliteitInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(sorteerStapel(stapel));
        }

        sorteerNationaliteitLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    public static List<BrpStapel<BrpReisdocumentInhoud>> sorteerReisdocumentStapels(
            final List<BrpStapel<BrpReisdocumentInhoud>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpReisdocumentInhoud>> gesorteerdeStapels =
                new ArrayList<BrpStapel<BrpReisdocumentInhoud>>();

        for (final BrpStapel<BrpReisdocumentInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(sorteerStapel(stapel));
        }

        sorteerReisdocumentLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    public static List<BrpRelatie> sorteerRelaties(final List<BrpRelatie> ongesorteerdeRelaties) {
        if (ongesorteerdeRelaties == null || ongesorteerdeRelaties.isEmpty()) {
            return ongesorteerdeRelaties;
        }
        final List<BrpRelatie> gesorteerdeRelaties = new ArrayList<BrpRelatie>();

        for (final BrpRelatie relatie : ongesorteerdeRelaties) {
            gesorteerdeRelaties.add(sorteerRelatie(relatie));
        }

        sorteerRelatieLijst(gesorteerdeRelaties);

        return gesorteerdeRelaties;
    }

    public static List<BrpStapel<BrpVoornaamInhoud>> sorteerVoornaamStapels(
            final List<BrpStapel<BrpVoornaamInhoud>> ongesorteerdeStapels) {
        if (ongesorteerdeStapels == null || ongesorteerdeStapels.isEmpty()) {
            return ongesorteerdeStapels;
        }
        final List<BrpStapel<BrpVoornaamInhoud>> gesorteerdeStapels = new ArrayList<BrpStapel<BrpVoornaamInhoud>>();

        for (final BrpStapel<BrpVoornaamInhoud> stapel : ongesorteerdeStapels) {
            gesorteerdeStapels.add(sorteerStapel(stapel));
        }

        sorteerVoornaamLijst(gesorteerdeStapels);

        return gesorteerdeStapels;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static BrpBetrokkenheid sorteerBetrokkenheid(final BrpBetrokkenheid betrokkenheid) {
        return new BrpBetrokkenheid(betrokkenheid.getRol(),
                sorteerStapel(betrokkenheid.getIdentificatienummersStapel()),
                sorteerStapel(betrokkenheid.getGeslachtsaanduidingStapel()),
                sorteerStapel(betrokkenheid.getGeboorteStapel()),
                sorteerStapel(betrokkenheid.getOuderlijkGezagStapel()),
                sorteerStapel(betrokkenheid.getSamengesteldeNaamStapel()),
                sorteerStapel(betrokkenheid.getOuderStapel()));
    }

    public static BrpRelatie sorteerRelatie(final BrpRelatie relatie) {
        return new BrpRelatie(relatie.getSoortRelatieCode(), relatie.getRolCode(),
                sorteerBetrokkenheden(relatie.getBetrokkenheden()), sorteerStapel(relatie.getRelatieStapel()));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static <T extends BrpGroepInhoud> void sorteerGroepLijst(final List<BrpGroep<T>> groepen) {
        if (groepen == null || groepen.isEmpty()) {
            return;
        }
        Collections.sort(groepen, GROEPEN_COMPARATOR);
    }

    public static void sorteerRelatieLijst(final List<BrpRelatie> relaties) {
        if (relaties == null || relaties.isEmpty()) {
            return;
        }
        Collections.sort(relaties, RELATIES_COMPARATOR);
    }

    public static void sorteerBetrokkenheidLijst(final List<BrpBetrokkenheid> betrokkenheden) {
        if (betrokkenheden == null || betrokkenheden.isEmpty()) {
            return;
        }
        Collections.sort(betrokkenheden, BETROKKENHEDEN_COMPARATOR);
    }

    public static void sorteerGeslachtsnaamLijst(
            final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels) {
        if (geslachtsnaamcomponentStapels == null || geslachtsnaamcomponentStapels.isEmpty()) {
            return;
        }
        Collections.sort(geslachtsnaamcomponentStapels, GESLACHTSNAAM_COMPARATOR);
    }

    public static void sorteerNationaliteitLijst(final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        if (nationaliteitStapels == null || nationaliteitStapels.isEmpty()) {
            return;
        }
        Collections.sort(nationaliteitStapels, NATIONALITEITEN_COMPARATOR);
    }

    public static void sorteerReisdocumentLijst(final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels) {
        if (reisdocumentStapels == null || reisdocumentStapels.isEmpty()) {
            return;
        }
        Collections.sort(reisdocumentStapels, REISDOCUMENTEN_COMPARATOR);
    }

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

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static class BrpBetrokkenhedenComparator implements Comparator<BrpBetrokkenheid> {
        @Override
        public int compare(final BrpBetrokkenheid o1, final BrpBetrokkenheid o2) {
            int result = o1.getRol().compareTo(o2.getRol());

            if (result == 0) {
                result = compareNulls(getGeslachtsnaam(o1), getGeslachtsnaam(o2));
            }

            return result;
        }

        private String getGeslachtsnaam(final BrpBetrokkenheid betrokkenheid) {
            if (betrokkenheid.getSamengesteldeNaamStapel() == null) {
                return null;
            } else {
                final BrpSamengesteldeNaamInhoud inhoud =
                        betrokkenheid.getSamengesteldeNaamStapel().get(0).getInhoud();
                return inhoud.getGeslachtsnaam() + inhoud.getVoornamen();
            }
        }
    }

    public static class BrpVoornaamComparator implements Comparator<BrpStapel<BrpVoornaamInhoud>> {
        @Override
        public int compare(final BrpStapel<BrpVoornaamInhoud> o1, final BrpStapel<BrpVoornaamInhoud> o2) {
            final int volg1 = o1.get(0).getInhoud().getVolgnummer();
            final int volg2 = o2.get(0).getInhoud().getVolgnummer();
            return volg1 > volg2 ? 1 : volg2 > volg1 ? -1 : 0;
        }
    }

    public static class BrpReisdocumentStapelsComparator implements Comparator<BrpStapel<BrpReisdocumentInhoud>> {

        @Override
        public int compare(final BrpStapel<BrpReisdocumentInhoud> o1, final BrpStapel<BrpReisdocumentInhoud> o2) {
            return compareNulls(getNummer(o1), getNummer(o2));
        }

        private String getNummer(final BrpStapel<BrpReisdocumentInhoud> o) {
            return o.get(0).getInhoud().getNummer();
        }
    }

    public static class BrpNationaliteitStapelsComparator implements Comparator<BrpStapel<BrpNationaliteitInhoud>> {

        @Override
        public int compare(final BrpStapel<BrpNationaliteitInhoud> o1, final BrpStapel<BrpNationaliteitInhoud> o2) {
            return compareNulls(getNationaliteit(o1), getNationaliteit(o2));

        }

        private Integer getNationaliteit(final BrpStapel<BrpNationaliteitInhoud> o2) {
            return o2.get(0).getInhoud().getNationaliteitCode().getCode();
        }

    }

    public static class BrpGeslachtsnaamStapelsComparator implements
            Comparator<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> {

        @Override
        public int compare(
                final BrpStapel<BrpGeslachtsnaamcomponentInhoud> o1,
                final BrpStapel<BrpGeslachtsnaamcomponentInhoud> o2) {
            final int volg1 = o1.get(0).getInhoud().getVolgnummer();
            final int volg2 = o2.get(0).getInhoud().getVolgnummer();
            return volg1 > volg2 ? 1 : volg2 > volg1 ? -1 : 0;
        }

    }

    public static class BrpRelatiesComparator implements Comparator<BrpRelatie> {

        @Override
        public int compare(final BrpRelatie o1, final BrpRelatie o2) {
            int result = o1.getSoortRelatieCode().compareTo(o2.getSoortRelatieCode());

            if (result == 0) {
                result = o1.getRolCode().compareTo(o2.getRolCode());
            }

            if (result == 0) {
                result = compareNulls(getAnummer(o1), getAnummer(o2));
            }
            if (result == 0) {
                result = compareNulls(getNaam(o1), getNaam(o2));
            }

            return result;
        }

        private String getNaam(final BrpRelatie o) {
            String result = null;
            if (o.getBetrokkenheden() != null) {
                for (final BrpBetrokkenheid betrokkenheid : o.getBetrokkenheden()) {
                    if (betrokkenheid.getIdentificatienummersStapel() != null) {
                        for (final BrpGroep<BrpSamengesteldeNaamInhoud> groep : betrokkenheid
                                .getSamengesteldeNaamStapel()) {
                            final String geslachtsnaam = groep.getInhoud().getGeslachtsnaam();
                            final String voornaam = groep.getInhoud().getVoornamen();

                            final String naam;
                            if (geslachtsnaam == null) {
                                if (voornaam == null) {
                                    naam = null;
                                } else {
                                    naam = voornaam;
                                }
                            } else {
                                if (voornaam == null) {
                                    naam = geslachtsnaam;
                                } else {
                                    naam = geslachtsnaam + ", " + voornaam;
                                }

                            }

                            if (result == null || naam != null && result.compareTo(naam) < 0) {
                                result = naam;
                            }
                        }
                    }
                }
            }
            return result;
        }

        private Long getAnummer(final BrpRelatie o) {
            Long result = null;
            if (o.getBetrokkenheden() != null) {
                for (final BrpBetrokkenheid betrokkenheid : o.getBetrokkenheden()) {
                    if (betrokkenheid.getIdentificatienummersStapel() != null) {
                        for (final BrpGroep<BrpIdentificatienummersInhoud> groep : betrokkenheid
                                .getIdentificatienummersStapel()) {
                            final Long anummer = groep.getInhoud().getAdministratienummer();

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

    public static class BrpGroepenComparator implements Comparator<BrpGroep<? extends BrpGroepInhoud>> {

        @Override
        public int compare(final BrpGroep<? extends BrpGroepInhoud> o1, final BrpGroep<? extends BrpGroepInhoud> o2) {
            int result =
                    -o1.getHistorie().getDatumTijdRegistratie().compareTo(o2.getHistorie().getDatumTijdRegistratie());

            if (result == 0) {
                result =
                        -o1.getHistorie().getDatumAanvangGeldigheid()
                                .compareTo(o2.getHistorie().getDatumAanvangGeldigheid());
            }

            if (result == 0) {
                result = compareNulls(o1.getHistorie().getDatumTijdVerval(), o2.getHistorie().getDatumTijdVerval());
            }

            if (result == 0) {
                result =
                        compareNulls(o1.getHistorie().getDatumEindeGeldigheid(), o2.getHistorie()
                                .getDatumEindeGeldigheid());
            }

            return result;
        }

    }

}
