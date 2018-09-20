/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.logging.Logger;

import org.springframework.stereotype.Component;

/**
 * Converteerder met de logica om een brp stapel om te zetten naar (gedeelten van) een lo3 stapel.
 * 
 * @param <B>
 *            brp groep inhoud type
 * @param <L>
 *            lo3 categorie inhoud type
 */
// CHECKSTYLE:OFF - Fan-out complexity - geaccepteerd
@Component
public abstract class BrpGroepConverteerder<B extends BrpGroepInhoud, L extends Lo3CategorieInhoud> {
    // CHECKSTYLE:ON

    private static final DecimalFormat GEMEENTE_CODE_FORMAT = new DecimalFormat("0000");

    /**
     * Logger (zodat subclasses hun eigen logger krijgen).
     * 
     * @return logger
     */
    protected abstract Logger getLogger();

    /**
     * Converteer de groep naar categorieen.
     * 
     * @param actie
     *            actie die nu verwerkt wordt
     * @param groepen
     *            brp groepen
     * @param categorieen
     *            lo3 categorieen
     */
    public final void converteer(
            final BrpActie actie,
            final List<BrpGroep<B>> groepen,
            final List<Lo3Categorie<L>> categorieen) {

        if (BrpGroepConverteerder.checkSituatie3(actie, groepen)) {
            getLogger().debug("is conversie uitzondering: Situatie 3");

            final Lo3Datum ingang = groepen.get(0).getHistorie().getDatumEindeGeldigheid().converteerNaarLo3Datum();
            final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
            final List<BrpGroep<B>> gecreeerdeRijen = BrpGroepConverteerder.bepaalGecreeerdeRijen(actie, groepen);
            final BrpGroep<B> rijTotPeildatum =
                    bepaalGeldigeRijTotPeildatum(gecreeerdeRijen, ingang.converteerNaarBrpDatum());
            final B inhoudTotPeildatum = rijTotPeildatum != null ? rijTotPeildatum.getInhoud() : null;
            if (BrpGroepConverteerder.checkSituatie3a(groepen)) {
                getLogger().debug("is Situatie 3 a");
                if (BrpGroepConverteerder.checkSituatie3a1(groepen)) {
                    getLogger().debug("is Situatie 3 a 1: maak juiste lege lo3 rij aan");

                    // Maak juiste lege lo3-rij aan
                    final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);
                    Lo3Categorie<L> nieuweRij =
                            bepaalNieuweRij(actie, categorieen, BrpGroepConverteerder.maakDocumentatie(actie),
                                    historie);
                    nieuweRij = vulInhoud(nieuweRij, null, inhoudTotPeildatum);
                    categorieen.add(nieuweRij);
                } else {
                    getLogger().debug("is Situatie 3 a 1: maak onjuiste lege lo3 rij aan");
                    // Maak onjuiste lege lo3-rij aan
                    final Lo3Historie historie =
                            new Lo3Historie(Lo3IndicatieOnjuistEnum.ONJUIST.asElement(), ingang, opneming);
                    Lo3Categorie<L> nieuweRij =
                            bepaalNieuweRij(actie, categorieen, BrpGroepConverteerder.maakDocumentatie(actie),
                                    historie);
                    nieuweRij = vulInhoud(nieuweRij, null, inhoudTotPeildatum);
                    categorieen.add(nieuweRij);
                }
            } else {
                getLogger().debug("is Situatie 3 b: maak juist lege lo3 rij aan");
                // Maak juiste lege lo3-rij aan
                final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);
                Lo3Categorie<L> nieuweRij =
                        bepaalNieuweRij(actie, categorieen, BrpGroepConverteerder.maakDocumentatie(actie), historie);
                nieuweRij = vulInhoud(nieuweRij, null, inhoudTotPeildatum);
                categorieen.add(nieuweRij);
            }
        } else {
            verwerkBzmAlgoritme(actie, groepen, categorieen);
        }
    }

    // CHECKSTYLE:OFF - Cyclomatic complexity. De code wordt hier duidelijker, juist door deze uit te schrijven
    private void verwerkBzmAlgoritme(
    // CHECKSTYLE:ON
            final BrpActie actie,
            final List<BrpGroep<B>> groepen,
            final List<Lo3Categorie<L>> categorieen) {
        getLogger().debug("Verwerk BZM algoritme");
        getLogger().debug("categorieen voor verwerkBzmAlgoritme ({})", categorieen.size());

        final List<BrpGroep<B>> gecreeerdeRijen = BrpGroepConverteerder.bepaalGecreeerdeRijen(actie, groepen);
        final List<BrpGroep<B>> vervallenRijen = BrpGroepConverteerder.bepaalVervallenRijen(actie, groepen);

        getLogger().debug("Gecreeerde rijen ({})", gecreeerdeRijen.size());
        getLogger().debug("Vervallen rijen ({})", vervallenRijen.size());

        final List<BrpDatum> geldigheidsdatums =
                new ArrayList<BrpDatum>(
                        BrpGroepConverteerder.bepaalGeldigheidsdatums(gecreeerdeRijen, vervallenRijen));

        getLogger().debug("Geldigheidsdatums ({})", geldigheidsdatums.size());

        for (int datumIndex = 0; datumIndex < geldigheidsdatums.size(); datumIndex++) {
            final BrpDatum datum = geldigheidsdatums.get(datumIndex);
            final BrpDatum volgendeDatum =
                    datumIndex == geldigheidsdatums.size() - 1 ? null : geldigheidsdatums.get(datumIndex + 1);

            getLogger().debug("Te verwerken datum: " + datum);
            final BrpGroep<B> gecreeerdeRij = BrpGroepConverteerder.bepaalGeldigeRij(gecreeerdeRijen, datum);
            final BrpGroep<B> vervallenRij = BrpGroepConverteerder.bepaalGeldigeRij(vervallenRijen, datum);
            final BrpGroep<B> vorigeRij = bepaalGeldigeRijTotPeildatum(gecreeerdeRijen, datum);
            final B vorigeInhoud = vorigeRij == null ? null : vorigeRij.getInhoud();

            if (vervallenRij == null && gecreeerdeRij != null) {
                // Situatie 1: Inhoudelijke gegevens worden gevuld
                verwerkBzmWordtGevuld(actie, datum, volgendeDatum, categorieen, gecreeerdeRij, vorigeInhoud);
            }

            if (vervallenRij != null && gecreeerdeRij == null) {
                // Situatie 2: Inhoudelijke gegevens worden leeggemaakt
                verwerkBzmWordtLeeg(actie, datum, volgendeDatum, categorieen, vorigeInhoud);
            }

            if (vervallenRij != null && gecreeerdeRij != null) {
                if (vervallenRij.getInhoud().equals(gecreeerdeRij.getInhoud())) {
                    // Situatie 3: Inhoudelijke gegevens zijn gelijk
                    verwerkBzmBeideGevuldEnGelijk(actie, datum, categorieen, gecreeerdeRij, vervallenRij,
                            vorigeInhoud);
                } else {
                    // Situatie 4: Inhoudelijke gegevens zijn ongelijk
                    verwerkBzmBeideGevuldEnOngelijk(actie, datum, volgendeDatum, categorieen, gecreeerdeRij,
                            vorigeInhoud);
                }
            }

            if (vervallenRij == null && gecreeerdeRij == null) {
                // Situatie 5: Inhoudelijke gegevens zijn leeg
                verwerkBzmBeideLeeg(actie, datum, categorieen, gecreeerdeRijen, vervallenRijen);
            }
        }

        getLogger().debug("categorieen na verwerkBzmAlgoritme ({})", categorieen.size());
    }

    /** Situatie 1: Inhoudelijke gegevens worden gevuld. */
    private void verwerkBzmWordtGevuld(
            final BrpActie actie,
            final BrpDatum datum,
            final BrpDatum volgendeDatum,
            final List<Lo3Categorie<L>> categorieen,
            final BrpGroep<B> gecreeerdeRij,
            final B vorigeRijInhoud) {
        getLogger().debug("verwerkBzmWordtGevuld");

        // als de soort conversie is, en de datum aanvang geldigheid van de gecreeerde rij komt niet overeen met de
        // datum: doe niets
        final boolean isConversieActie = BrpSoortActieCode.CONVERSIE_GBA.equals(actie.getSoortActieCode());
        final boolean datumKomtOvereenMetGeldigheid =
                datum.equals(gecreeerdeRij.getHistorie().getDatumAanvangGeldigheid());
        if (isConversieActie && !datumKomtOvereenMetGeldigheid) {
            // doe niets
            return;
        }

        final Long actieInhoudId = gecreeerdeRij.getActieInhoud().getId();
        final Long actieVervalId =
                gecreeerdeRij.getActieVerval() == null ? null : gecreeerdeRij.getActieVerval().getId();

        final Lo3IndicatieOnjuist onjuist =
                actieInhoudId.equals(actieVervalId) ? Lo3IndicatieOnjuistEnum.ONJUIST.asElement() : null;
        final Lo3Datum ingang = datum.converteerNaarLo3Datum();
        final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
        final Lo3Historie historie = new Lo3Historie(onjuist, ingang, opneming);

        // Maak een LO3 rij aan, als er al een juiste LO3 rij was waarbij 85.10 ingangsdatum geldigheid
        // overeenkomt, maak die LO3-rij onjuist.
        Lo3Categorie<L> nieuweRij =
                bepaalNieuweRij(actie, categorieen, BrpGroepConverteerder.maakDocumentatie(actie), historie);
        nieuweRij = vulInhoud(nieuweRij, gecreeerdeRij, vorigeRijInhoud);
        categorieen.add(nieuweRij);

        // Maak volgende bestaande juiste LO3-rijen onjuist: 85.10 ingangsdatum geldigheid is groter dan dit
        // overgangsmoment en kleiner dan het eerstvolgende overgangsmoment.
        // Dit hoeft alleen gedaan te worden als onjuist indicatie niet is gezet
        if (onjuist == null) {
            maakRijenOnjuist(actie, categorieen, datum, volgendeDatum, gecreeerdeRij);
        }
    }

    /** Situatie 2: Inhoudelijke gegevens worden leeggemaakt. */
    private void verwerkBzmWordtLeeg(
            final BrpActie actie,
            final BrpDatum datum,
            final BrpDatum volgendeDatum,
            final List<Lo3Categorie<L>> categorieen,
            final B vorigeRijInhoud) {
        getLogger().debug("verwerkBzmWordtLeeg");

        final Lo3Datum ingang = datum.converteerNaarLo3Datum();
        final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
        final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);

        // Maak een LO3 rij aan, als er al een juiste LO3 rij was waarbij 85.10 ingangsdatum geldigheid
        // overeenkomt, maak die LO3-rij onjuist.
        Lo3Categorie<L> nieuweRij =
                bepaalNieuweRij(actie, categorieen, BrpGroepConverteerder.maakDocumentatie(actie), historie);
        nieuweRij = vulInhoud(nieuweRij, null, vorigeRijInhoud);
        categorieen.add(nieuweRij);

        // Maak volgende bestaande juiste LO3-rijen onjuist: 85.10 ingangsdatum geldigheid is groter dan dit
        // overgangsmoment en kleiner dan het eerstvolgende overgangsmoment.
        maakRijenOnjuist(actie, categorieen, datum, volgendeDatum, null);
    }

    /** Situatie 3: Inhoudelijke gegevens zijn gelijk. */
    private void verwerkBzmBeideGevuldEnGelijk(
            final BrpActie actie,
            final BrpDatum datum,
            final List<Lo3Categorie<L>> categorieen,
            final BrpGroep<B> gecreeerdeRij,
            final BrpGroep<B> vervallenRij,
            final B vorigeRijInhoud) {
        getLogger().debug("verwerkBzmBeideGevuldEnGelijk");
        final boolean gecreerdeBegintNu = datum.equals(gecreeerdeRij.getHistorie().getDatumAanvangGeldigheid());
        final boolean vervallenBegintNu = datum.equals(vervallenRij.getHistorie().getDatumAanvangGeldigheid());

        if (gecreerdeBegintNu && !vervallenBegintNu) {
            // 1. Als de gecreerde rij begint op overgangsmoment, en de vervallen rij was al eerder begonnen:

            // Als er nog geen juiste LO3-rij is, waarvan de 85.10 ingangsdatum geldigheid gelijk is aan overgangsmoment
            // en die dezelfde inhoud heeft als de gecreerde rij: maak een juiste gevuld LO3-rij aan:
            final Lo3Categorie<L> juisteRijOpOvergangsmoment = zoekCategorieOpIngangsdatum(categorieen, datum);
            final boolean inhoudGelijk = isInhoudGelijk(juisteRijOpOvergangsmoment, gecreeerdeRij.getInhoud());

            if (!inhoudGelijk) {
                final Lo3Datum ingang = datum.converteerNaarLo3Datum();
                final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
                final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);

                Lo3Categorie<L> nieuweRij =
                        bepaalNieuweRij(actie, categorieen, BrpGroepConverteerder.maakDocumentatie(actie), historie);
                nieuweRij = vulInhoud(nieuweRij, null, vorigeRijInhoud);
                categorieen.add(nieuweRij);
            }
        }

        // 2. Als zowel de geldigheid van de gecreeerde rij als de vervallen rij beginnen op overgangsmoment: niks doen
        // 3. Als de gecreeerde rij al eerder begonnen was en de vervallen rji begint op overgangsmoment: niks doen
    }

    /** Situatie 4: Inhoudelijke gegevens zijn ongelijk. */
    private void verwerkBzmBeideGevuldEnOngelijk(
            final BrpActie actie,
            final BrpDatum datum,
            final BrpDatum volgendeDatum,
            final List<Lo3Categorie<L>> categorieen,
            final BrpGroep<B> gecreeerdeRij,
            final B vorigeRijInhoud) {
        getLogger().debug("verwerkBzmBeideGevuldEnOngelijk");

        final Lo3Datum ingang = datum.converteerNaarLo3Datum();
        final Lo3Datum opneming = gecreeerdeRij.getHistorie().getDatumTijdRegistratie().converteerNaarLo3Datum();
        final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);

        final Lo3Documentatie documentatie;

        if (datum.equals(gecreeerdeRij.getHistorie().getDatumAanvangGeldigheid())) {
            getLogger().debug("Gebruik akte/doc uit actie");
            documentatie = BrpGroepConverteerder.maakDocumentatie(actie);
        } else {
            getLogger().debug("Gebruik akte/doc uit onjuist te maken rij");
            documentatie = null;
        }

        // Maak een LO3 rij aan, als er al een juiste LO3 rij was waarbij 85.10 ingangsdatum geldigheid
        // overeenkomt, maak die LO3-rij onjuist.
        Lo3Categorie<L> nieuweRij = bepaalNieuweRij(actie, categorieen, documentatie, historie);
        nieuweRij = vulInhoud(nieuweRij, gecreeerdeRij, vorigeRijInhoud);
        categorieen.add(nieuweRij);

        // Maak volgende bestaande juiste LO3-rijen onjuist: 85.10 ingangsdatum geldigheid is groter dan dit
        // overgangsmoment en kleiner dan het eerstvolgende overgangsmoment.
        maakRijenOnjuist(actie, categorieen, datum, volgendeDatum, gecreeerdeRij);
    }

    /** Situatie 5: Inhoudelijke gegevens zijn leeg. */
    private void verwerkBzmBeideLeeg(
            final BrpActie actie,
            final BrpDatum datum,
            final List<Lo3Categorie<L>> categorieen,
            final List<BrpGroep<B>> gecreeerdeRijen,
            final List<BrpGroep<B>> vervallenRijen) {
        getLogger().debug("verwerkBzmBeideLeeg(datum={})", datum);
        final BrpGroep<B> gecreerdeRijMetEinddatum = bepaalGeldigeRijTotPeildatum(gecreeerdeRijen, datum);
        final boolean gecreeerdeRijStopt = gecreerdeRijMetEinddatum != null;
        final boolean vervallenRijStopt = bepaalGeldigeRijTotPeildatum(vervallenRijen, datum) != null;

        // 1. Als een gecreeerde rij stopt op het overgangsmoment en niet een vervallen rij.
        if (gecreeerdeRijStopt && !vervallenRijStopt) {
            final boolean conditie1a =
                    actie.equals(gecreerdeRijMetEinddatum.getActieInhoud())
                            && actie.getSoortActieCode().equals(BrpSoortActieCode.CONVERSIE_GBA);
            // Als er nog geen juiste LO3-rij is, waarvan ingangsdatum geldigheid gelijk is aan overgangsmoment
            if (!conditie1a && zoekCategorieOpIngangsdatum(categorieen, datum) == null) { // 1b
                // Maak een juiste lege LO3-rij aan
                final Lo3Datum ingang = datum.converteerNaarLo3Datum();
                final Lo3Datum opneming = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
                final Lo3Historie historie = new Lo3Historie(null, ingang, opneming);

                Lo3Categorie<L> nieuweRij =
                        bepaalNieuweRij(actie, categorieen, BrpGroepConverteerder.maakDocumentatie(actie), historie);
                final BrpGroep<B> vorigeRij = bepaalGeldigeRijTotPeildatum(gecreeerdeRijen, datum);
                final B vorigeRijInhoud = vorigeRij == null ? null : vorigeRij.getInhoud();
                nieuweRij = vulInhoud(nieuweRij, null, vorigeRijInhoud);
                categorieen.add(nieuweRij);
            }

        }

        // 2. Als zowel een gecreerde rij als een vervallen rij stoppen: niks doen
        // 3. Als een vervallen rij stopt en niet een gecreeerde rij: niks doen
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Maak een nieuwe (lege) lo3 rij.
     * 
     * @return nieuwe (lege) lo3 rij
     */
    protected abstract L maakNieuweInhoud();

    /**
     * Vul de gegevens lo3 inhoud met de brp inhoud. Maak de lo3 inhoud leeg als brp inhoud null is.
     * 
     * @param lo3Inhoud
     *            lo3 inhoud
     * @param brpInhoud
     *            brp inhoud
     * @param brpVorige
     *            brp inhoud van vorige record
     * @return de gevulde lo3 inhoud
     */
    protected abstract L vulInhoud(final L lo3Inhoud, final B brpInhoud, final B brpVorige);

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Lo3Categorie<L>
            zoekCategorieOpIngangsdatum(final List<Lo3Categorie<L>> categorieen, final BrpDatum datum) {
        final Lo3Datum ingang = datum.converteerNaarLo3Datum();
        for (final Lo3Categorie<L> categorie : categorieen) {
            if (categorie.getHistorie().getIndicatieOnjuist() != null) {
                continue;
            }

            if (ingang.equals(categorie.getHistorie().getIngangsdatumGeldigheid())) {
                return categorie;
            }
        }

        return null;
    }

    private boolean isInhoudGelijk(final Lo3Categorie<L> categorie, final B groep) {
        if (categorie == null) {
            return false;
        }

        final L oudeInhoud = categorie.getInhoud();
        final L nieuweInhoud = vulInhoud(oudeInhoud, groep, null);

        return nieuweInhoud.equals(oudeInhoud);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Bepaal de rij die tot de peildatum geldig was
     * 
     * @param groepen
     *            de rijen waarin gezocht moet worden
     * @param datum
     *            de peildatum
     * @return de geldige rij, of null indien geen geldige rij is gevonden.
     */
    private BrpGroep<B> bepaalGeldigeRijTotPeildatum(final List<BrpGroep<B>> groepen, final BrpDatum datum) {
        for (final BrpGroep<B> groep : groepen) {
            if (datum.equals(groep.getHistorie().getDatumEindeGeldigheid())) {
                return groep;
            }
        }
        return null;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Maak een nieuwe rij. Maar alleen als er op deze datum aanvang voor deze actie nog geen rij bestaat. Als er een
     * nieuwe rij wordt gemaakt dan als er een rij was met deze datum aanvang die dan onjuist maken.
     */
    // CHECKSTYLE:OFF - Cyclomatic complexity. De code wordt hier duidelijker, juist door deze uit te schrijven
    private Lo3Categorie<L> bepaalNieuweRij(
    // CHECKSTYLE:ON
            final BrpActie actie,
            final List<Lo3Categorie<L>> categorieen,
            final Lo3Documentatie documentatie,
            final Lo3Historie historie) {
        getLogger().debug("bepaalNieuweRij(actie, categorieen=<notshown>, documentatie=<notshown>, historie");
        final Long actieId = actie.getId();
        getLogger().debug("actieId: {}", actieId);
        final Lo3Datum ingang = historie.getIngangsdatumGeldigheid();
        getLogger().debug("ingang: {}", ingang);

        for (final Lo3Categorie<L> categorie : categorieen) {
            getLogger().debug("controleer");
            final Long documentatieId =
                    categorie.getDocumentatie() == null ? null : categorie.getDocumentatie().getId();
            if (actieId.equals(documentatieId) && ingang.equals(categorie.getHistorie().getIngangsdatumGeldigheid())) {
                getLogger().debug("er is voor deze ingangsdatum en akte al een record aangemaakt!");
                categorieen.remove(categorie); // verwijderen want we voegen deze straks weer toe
                return categorie;
            }
        }

        // We moeten een nieuwe rij maken. Deze maken we door de niet-onjuiste rij met de meest recent ingangdatum voor
        // (inclusief) de gegeven ingangsdatum als basis te nemen.
        Lo3Categorie<L> basis = null;
        for (final Lo3Categorie<L> categorie : categorieen) {
            if (categorie.getHistorie().getIndicatieOnjuist() != null) {
                continue;
            }

            if (categorie.getHistorie().getIngangsdatumGeldigheid().compareTo(ingang) <= 0) {
                if (basis == null
                        || categorie.getHistorie().getIngangsdatumGeldigheid()
                                .compareTo(basis.getHistorie().getIngangsdatumGeldigheid()) > 0) {
                    basis = categorie;
                }
            }
        }

        final Lo3Documentatie teGebruikenDocumentatie;
        if (basis != null && ingang.equals(basis.getHistorie().getIngangsdatumGeldigheid())) {
            // Deze gevonden rij moeten we onjuist maken
            getLogger().debug("basis rij is op zelfde ingangsdatum en moet dus onjuist gemaakt worden.");
            categorieen.remove(basis);
            teGebruikenDocumentatie = documentatie == null ? basis.getDocumentatie() : documentatie;
            categorieen.add(new Lo3Categorie<L>(basis.getInhoud(), basis.getDocumentatie(), new Lo3Historie(
                    Lo3IndicatieOnjuistEnum.ONJUIST.asElement(), basis.getHistorie().getIngangsdatumGeldigheid(),
                    basis.getHistorie().getDatumVanOpneming()), basis.getLo3Herkomst()));
        } else {
            teGebruikenDocumentatie = documentatie;
        }

        final L nieuweInhoud = basis == null ? maakNieuweInhoud() : basis.getInhoud();
        final Lo3Herkomst nieuweLo3Herkomst = basis == null ? null : basis.getLo3Herkomst();

        return new Lo3Categorie<L>(nieuweInhoud, teGebruikenDocumentatie, historie, nieuweLo3Herkomst);
    }

    private Lo3Categorie<L> vulInhoud(final Lo3Categorie<L> categorie, final BrpGroep<B> brp, final B brpVorige) {
        return new Lo3Categorie<L>(vulInhoud(categorie.getInhoud(), brp == null ? null : brp.getInhoud(), brpVorige),
                categorie.getDocumentatie(), categorie.getHistorie(), categorie.getLo3Herkomst());
    }

    /**
     * Maak de JUISTE rijen in de lijst, die een ingangsdatum hebben tussen vanaf en tot, ONJUIST, TENZIJ de rij door
     * deze actie is aangemaakt.
     * 
     * Het ONJUIST maken van een rij bestaat uit (in volgende): het kopieren van de rij, het overschrijven van de INHOUD
     * en DATUM OPNEMING in de kopieerde rij en het zetten van de INDICATIE ONJUIST in de gevonden rij.
     */
    private void maakRijenOnjuist(
            final BrpActie actie,
            final List<Lo3Categorie<L>> categorieen,
            final BrpDatum brpVanaf,
            final BrpDatum brpTot,
            final BrpGroep<B> brpGroep) {
        final Long actieId = actie.getId();
        final Lo3Datum vanaf = brpVanaf.converteerNaarLo3Datum();
        final Lo3Datum tot = brpTot == null ? null : brpTot.converteerNaarLo3Datum();
        getLogger().debug("maakRijenOnjuist");

        final int size = categorieen.size(); // Categorieen die later zijn toegevoegd hoeven niet nog een keer te worden
                                             // gecontroleerd.
        for (int index = 0; index < size; index++) {
            final Lo3Categorie<L> categorie = categorieen.get(index);

            final Long documentatieId =
                    categorie.getDocumentatie() == null ? null : categorie.getDocumentatie().getId();

            // Een onjuiste rij kan geskipped worden
            if (categorie.getHistorie().getIndicatieOnjuist() != null) {
                continue;
            }

            // Een rij die geldig is op of voor de vanaf datum kan geskipped worden.
            if (vanaf.compareTo(categorie.getHistorie().getIngangsdatumGeldigheid()) >= 0) {
                continue;
            }

            // Een rij die geldig is op of na de tot datum kan geskipped worden.
            if (tot != null && tot.compareTo(categorie.getHistorie().getIngangsdatumGeldigheid()) <= 0) {
                continue;
            }

            if (actieId.equals(documentatieId)) {
                // Als de rij door deze actie is aangemaakt, dan updaten we de inhoud ervan.
                categorieen.set(index, vulInhoud(categorie, brpGroep, null));
                // en skipped
                continue;
            }

            // Maak een nieuwe rij aan
            categorieen.add(bubbelNieuweRij(categorie, actie, brpGroep));

            // Maak de huidige rij onjuist
            categorieen.set(index, bubbelMaakOnjuist(categorie));
        }
    }

    private Lo3Categorie<L> bubbelNieuweRij(
            final Lo3Categorie<L> categorie,
            final BrpActie actie,
            final BrpGroep<B> brpGroep) {
        final L inhoud = vulInhoud(categorie.getInhoud(), brpGroep == null ? null : brpGroep.getInhoud(), null);

        final Lo3Documentatie oud = categorie.getDocumentatie();
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(actie.getId(), oud.getGemeenteAkte(), oud.getNummerAkte(),
                        oud.getGemeenteDocument(), oud.getDatumDocument(), oud.getBeschrijvingDocument(), null, null);

        final Lo3Historie historie =
                new Lo3Historie(null, categorie.getHistorie().getIngangsdatumGeldigheid(), actie
                        .getDatumTijdRegistratie().converteerNaarLo3Datum());

        return new Lo3Categorie<L>(inhoud, documentatie, historie, categorie.getLo3Herkomst());
    }

    private Lo3Categorie<L> bubbelMaakOnjuist(final Lo3Categorie<L> categorie) {
        final L inhoud = categorie.getInhoud();
        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final Lo3Historie oud = categorie.getHistorie();
        final Lo3Historie historie =
                new Lo3Historie(Lo3IndicatieOnjuistEnum.ONJUIST.asElement(), oud.getIngangsdatumGeldigheid(),
                        oud.getDatumVanOpneming());

        return new Lo3Categorie<L>(inhoud, documentatie, historie, categorie.getLo3Herkomst());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    // CHECKSTYLE:OFF - Cyclomatic complexity. De code wordt hier duidelijker, juist door deze uit te schrijven
    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> bepaalGecreeerdeRijen(
            final BrpActie actie,
            final List<BrpGroep<T>> groepen) {
        // CHECKSTYLE:ON
        final Long actieId = actie.getId();
        final List<BrpGroep<T>> gecreeerdeRijen = new ArrayList<BrpGroep<T>>();

        for (final BrpGroep<T> groep : groepen) {

            if (groep.getActieInhoud() != null && actieId.equals(groep.getActieInhoud().getId())
                    && groep.getActieGeldigheid() == null) {
                // OF deze rij heeft de aktie als aktie inhoud en geen actie aanpassing geldigheid
                gecreeerdeRijen.add(groep);
            } else if (groep.getActieGeldigheid() != null && actieId.equals(groep.getActieGeldigheid().getId())) {
                // OF deze rij heeft de aktie als aktie aanpassing geldigheid
                gecreeerdeRijen.add(groep);
            } else if (groep.getActieGeldigheid() != null && groep.getActieInhoud() != null
                    && actieId.equals(groep.getActieInhoud().getId())
                    && BrpSoortActieCode.CONVERSIE_GBA.equals(groep.getActieGeldigheid().getSoortActieCode())) {
                // OF deze rij heeft de aktie als aktie inhoud en de actie geldigheid is aangemaakt door conversie
                gecreeerdeRijen.add(groep);
            }
        }

        return gecreeerdeRijen;
    }

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> bepaalVervallenRijen(
            final BrpActie actie,
            final Collection<BrpGroep<T>> groepen) {
        final Long actieId = actie.getId();
        final List<BrpGroep<T>> vervallenRijen = new ArrayList<BrpGroep<T>>();

        for (final BrpGroep<T> groep : groepen) {
            // Als:
            // OF deze rij heeft de aktie als aktie verval en niet als akte inhoud
            // dan toevoegen

            if (groep.getActieVerval() != null && actieId.equals(groep.getActieVerval().getId())) {
                if (groep.getActieInhoud() == null || !actieId.equals(groep.getActieInhoud().getId())) {
                    vervallenRijen.add(groep);
                }
            }

        }

        return vervallenRijen;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T extends BrpGroepInhoud> SortedSet<BrpDatum> bepaalGeldigheidsdatums(
            final Collection<BrpGroep<T>> gecreeerdeRijen,
            final Collection<BrpGroep<T>> vervallenRijen) {
        final SortedSet<BrpDatum> datums = new TreeSet<BrpDatum>();

        for (final BrpGroep<T> rij : gecreeerdeRijen) {
            BrpGroepConverteerder.voegDatumToe(datums, rij.getHistorie().getDatumAanvangGeldigheid());
            BrpGroepConverteerder.voegDatumToe(datums, rij.getHistorie().getDatumEindeGeldigheid());
        }

        for (final BrpGroep<T> rij : vervallenRijen) {
            BrpGroepConverteerder.voegDatumToe(datums, rij.getHistorie().getDatumAanvangGeldigheid());
            BrpGroepConverteerder.voegDatumToe(datums, rij.getHistorie().getDatumEindeGeldigheid());
        }

        return datums;
    }

    private static void voegDatumToe(final Set<BrpDatum> datums, final BrpDatum datum) {
        if (datum == null) {
            return;
        }

        datums.add(datum);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @SuppressWarnings("unchecked")
    private static <T extends BrpGroepInhoud> BrpGroep<T> bepaalGeldigeRij(
            final Collection<BrpGroep<T>> rijen,
            final BrpDatum datum) {
        BrpGroep<T> result = null;
        for (final BrpGroep<T> rij : rijen) {
            final BrpDatum aanvang = rij.getHistorie().getDatumAanvangGeldigheid();
            final BrpDatum einde = rij.getHistorie().getDatumEindeGeldigheid();

            if (datum.compareTo(aanvang) >= 0) {
                if (einde == null || datum.compareTo(einde) < 0) {
                    result = rij;
                    if (BrpGroepConverteerder.isPrevalerend((BrpGroep<BrpGroepInhoud>) rij)) {
                        break;
                    }
                } else if (datum.compareTo(aanvang) == 0 && datum.compareTo(einde) == 0) {
                    result = rij;
                    if (BrpGroepConverteerder.isPrevalerend((BrpGroep<BrpGroepInhoud>) rij)) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * @param rij
     *            de rij
     * @return true als actieverval leeg is of als actieverval ongelijk is aan actieinhoud
     */
    private static boolean isPrevalerend(final BrpGroep<BrpGroepInhoud> rij) {
        return rij.getActieVerval() == null || rij.getActieVerval().equals(rij.getActieInhoud());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Maak Lo3 documentatie obv een actie.
     * 
     * @param actie
     *            actie
     * @return documentatie
     */
    // CHECKSTYLE:OFF - Cyclomatic complexity - Complex, maar ononderhoudbaar als we het opknippen
    @Requirement(Requirements.CBA001_BL01)
    @Definitie({ Definities.DEF047, Definities.DEF048, Definities.DEF049, Definities.DEF050 })
    public static Lo3Documentatie maakDocumentatie(final BrpActie actie) {
        // CHECKSTYLE:ON
        if (actie == null || actie.getDocumentStapels() == null || actie.getDocumentStapels().isEmpty()) {
            return actie == null ? null
                    : new Lo3Documentatie(actie.getId(), null, null, null, null, null, null, null);
        }

        // Behoud alleen documenten met soort 'Document' en 'Akte'
        final List<BrpStapel<BrpDocumentInhoud>> brpDocumentStapels =
                BrpGroepConverteerder.filterDocumenten(actie.getDocumentStapels());

        // alleen eerste document in de actie wordt gebruikt DEF050
        final Lo3Documentatie documentatie;
        if (brpDocumentStapels.isEmpty()) {
            // DEF049
            documentatie = new Lo3Documentatie(actie.getId(), null, null, null, null, null, null, null);
        } else {

            if (brpDocumentStapels.size() == 1) {
                final BrpDocumentInhoud brpDocument = brpDocumentStapels.get(0).getMeestRecenteElement().getInhoud();

                if (BrpSoortDocumentCode.DOCUMENT.equals(brpDocument.getSoortDocumentCode())) {
                    // DEF047
                    final Lo3Datum lo3Datum =
                            actie.getDatumTijdOntlening() == null ? Lo3Datum.NULL_DATUM : actie
                                    .getDatumTijdOntlening().converteerNaarLo3Datum();
                    documentatie =
                            new Lo3Documentatie(actie.getId(), null, null, brpDocument.getPartijCode() == null ? null
                                    : new Lo3GemeenteCode(GEMEENTE_CODE_FORMAT.format(brpDocument.getPartijCode()
                                            .getGemeenteCode())), lo3Datum, brpDocument.getOmschrijving(), null, null);
                } else if (BrpSoortDocumentCode.AKTE.equals(brpDocument.getSoortDocumentCode())) {
                    // DEF048
                    documentatie =
                            new Lo3Documentatie(actie.getId(), brpDocument.getPartijCode() == null ? null
                                    : new Lo3GemeenteCode(GEMEENTE_CODE_FORMAT.format(brpDocument.getPartijCode()
                                            .getGemeenteCode())), brpDocument.getAktenummer(), null, null, null,
                                    null, null);
                } else {
                    // Kan niet voorkomen want filterDocumenten zorgt ervoor dat alleen akten en documenten aanwezig
                    // zijn
                    throw new AssertionError();
                }
            } else {
                // DEF050
                final BrpStapel<BrpDocumentInhoud> brpDocumentStapel = actie.getDocumentStapels().get(0);
                final BrpDocumentInhoud brpDocument = brpDocumentStapel.getMeestRecenteElement().getInhoud();
                final Lo3GemeenteCode partij =
                        brpDocument.getPartijCode() == null ? null : new Lo3GemeenteCode(
                                GEMEENTE_CODE_FORMAT.format(brpDocument.getPartijCode().getGemeenteCode()));
                final Lo3Datum datumDocument =
                        actie.getDatumTijdOntlening() == null ? null : actie.getDatumTijdOntlening()
                                .converteerNaarLo3Datum();

                final StringBuilder omschrijving = new StringBuilder();
                for (final BrpStapel<BrpDocumentInhoud> documentStapel : brpDocumentStapels) {
                    if (omschrijving.length() > 0) {
                        omschrijving.append(", ");
                    }
                    omschrijving.append(documentStapel.getMeestRecenteElement().getInhoud().getOmschrijving());
                }

                documentatie =
                        new Lo3Documentatie(actie.getId(), null, null, partij, datumDocument,
                                omschrijving.toString(), null, null);
            }

        }
        return documentatie;

    }

    private static List<BrpStapel<BrpDocumentInhoud>> filterDocumenten(
            final List<BrpStapel<BrpDocumentInhoud>> documentStapels) {
        final List<BrpStapel<BrpDocumentInhoud>> result = new ArrayList<BrpStapel<BrpDocumentInhoud>>();

        for (final BrpStapel<BrpDocumentInhoud> documentStapel : documentStapels) {
            if (BrpGroepConverteerder.isDocumentOfAkte(documentStapel)) {
                result.add(documentStapel);
            }
        }

        return result;
    }

    private static boolean isDocumentOfAkte(final BrpStapel<BrpDocumentInhoud> documentStapel) {

        final BrpDocumentInhoud brpDocument = documentStapel.getMeestRecenteElement().getInhoud();
        final BrpSoortDocumentCode soort = brpDocument.getSoortDocumentCode();

        return BrpSoortDocumentCode.DOCUMENT.equals(soort) || BrpSoortDocumentCode.AKTE.equals(soort);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Als het BRP actie.soort = 'Conversie'. en alle gekoppelde groep-rijen zijn uitsluitend gekoppeld als Actie
     * aanpassing geldigheid.
     */
    // CHECKSTYLE:OFF - Return count - het is leesbaarder en onderhoudbaarder met meer returns
    private static boolean checkSituatie3(final BrpActie actie, final List<? extends BrpGroep<?>> groepen) {
        // CHECKSTYLE:ON
        if (!BrpSoortActieCode.CONVERSIE_GBA.equals(actie.getSoortActieCode())) {
            return false;
        }

        final Long actieId = actie.getId();
        for (final BrpGroep<?> groep : groepen) {
            // Gekoppeld als inhoud
            if (groep.getActieInhoud() != null && actieId.equals(groep.getActieInhoud().getId())) {
                return false;
            }

            // Niet gekoppeld als geldig
            if (groep.getActieGeldigheid() == null || !actieId.equals(groep.getActieGeldigheid().getId())) {
                return false;
            }

            // Gekoppeld als verval
            if (groep.getActieVerval() != null && actieId.equals(groep.getActieVerval().getId())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Als bij alle gevonden rijen actie verval gevuld is.
     */
    private static boolean checkSituatie3a(final List<? extends BrpGroep<?>> groepen) {
        for (final BrpGroep<?> groep : groepen) {
            if (groep.getActieVerval() == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Als bij alle gevonden rijen datum aanvang geldigheid gelijk is aan datum einde geldigheid.
     */
    private static boolean checkSituatie3a1(final List<? extends BrpGroep<?>> groepen) {
        for (final BrpGroep<?> groep : groepen) {
            final BrpDatum aanvang = groep.getHistorie().getDatumAanvangGeldigheid();
            final BrpDatum einde = groep.getHistorie().getDatumEindeGeldigheid();

            if (aanvang == null || einde == null || !aanvang.equals(einde)) {
                return false;
            }
        }

        return true;
    }
}
