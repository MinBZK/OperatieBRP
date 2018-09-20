/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Foutmelding;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Splitsen ouders.
 */
@Component
public class Lo3SplitsenGerelateerdeOuders {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Splits ouders.
     * 
     * @param persoonslijst
     *            persoonlijst
     * @return persoonslijst
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        final Lo3Stapel<Lo3OuderInhoud> ouder1 =
                persoonslijst.getOuder1Stapels() == null || persoonslijst.getOuder1Stapels().isEmpty() ? null
                        : persoonslijst.getOuder1Stapels().get(0);
        builder.ouder1Stapels(splitsOuders(ouder1));

        final Lo3Stapel<Lo3OuderInhoud> ouder2 =
                persoonslijst.getOuder2Stapels() == null || persoonslijst.getOuder2Stapels().isEmpty() ? null
                        : persoonslijst.getOuder2Stapels().get(0);
        builder.ouder2Stapels(splitsOuders(ouder2));

        return builder.build();
    }

    private static List<Lo3Stapel<Lo3OuderInhoud>> splitsOuders(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        LOG.debug("splitsOuders(stapel={})", stapel);
        if (stapel == null) {
            return null;
        }

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = stapel.getCategorieen();
        final List<Lo3Categorie<Lo3OuderInhoud>> legeRijen = verwijderLegeRijen(categorieen);

        final List<Lo3Stapel<Lo3OuderInhoud>> opDatumGesplitsteStapels;
        if (!categorieen.isEmpty()) {
            // Splits op datum
            opDatumGesplitsteStapels = splitsOpDatum(new Lo3Stapel<Lo3OuderInhoud>(categorieen));
            LOG.debug("opDatumGesplitsteStapels ({}): {}", opDatumGesplitsteStapels.size(), opDatumGesplitsteStapels);
        } else {
            opDatumGesplitsteStapels = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
        }

        // Als er twee a-nummers op dezelfde datum familierechtelijke betrekking geldig zijn, dan *MOET* dit wel een
        // verandering van a-nummer van dezelfde persoon zijn. Anders had een van de a-nummers ongeldig moeten zijn.
        final List<List<Long>> gerelateerdeAnummers = bepaalRelateerdeAnummers(stapel);

        // Splits op a-nummer
        final List<Lo3Stapel<Lo3OuderInhoud>> opAnummerGesplitsteStapels = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
        for (final Lo3Stapel<Lo3OuderInhoud> opDatumGesplitsteStapel : opDatumGesplitsteStapels) {
            opAnummerGesplitsteStapels.addAll(splitsOpAnummer(opDatumGesplitsteStapel));
        }

        if (opAnummerGesplitsteStapels.size() > 1) {
            // Als de map meer dan 1 entry bevat, dan zijn er meerdere ouders in dezelfde stapel gevonden.
            Foutmelding.logBijzondereSituatieFout(opAnummerGesplitsteStapels.get(0).getMeestRecenteElement()
                    .getLo3Herkomst(), BijzondereSituaties.BIJZ_CONV_LB016);
        }

        // Lege rijen terug zetten op juiste stapel
        verwerkLegeRijen(legeRijen, opAnummerGesplitsteStapels);

        // Nu alle niet beeindigde periodes beeindigen
        beeindigen(opAnummerGesplitsteStapels);

        // Samenvoegen op a-nummer
        final List<Lo3Stapel<Lo3OuderInhoud>> opAnummerSamengevoegdeStapels =
                samenvoegenOpAnummer(opAnummerGesplitsteStapels, gerelateerdeAnummers);
        LOG.debug("opAnummerSamengevoegdeStapels ({}): {}", opAnummerSamengevoegdeStapels.size(),
                opAnummerSamengevoegdeStapels);

        return opAnummerSamengevoegdeStapels;
    }

    private static List<List<Long>> bepaalRelateerdeAnummers(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        final Map<Lo3Datum, List<Long>> map = new HashMap<Lo3Datum, List<Long>>();

        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            if (categorie.getHistorie().isOnjuist()) {
                continue;
            }

            final Lo3Datum famDatum = categorie.getInhoud().getFamilierechtelijkeBetrekking();
            final Long anummer = categorie.getInhoud().getaNummer();
            if (famDatum == null || anummer == null) {
                continue;
            }

            if (!map.containsKey(famDatum)) {
                map.put(famDatum, new ArrayList<Long>());
            }

            map.get(famDatum).add(anummer);
        }

        final List<List<Long>> result = new ArrayList<List<Long>>();

        anummerLoop: for (final List<Long> anummers : map.values()) {
            for (final List<Long> resultAnummers : result) {
                for (final Long resultAnummer : resultAnummers) {
                    if (anummers.contains(resultAnummer)) {
                        resultAnummers.addAll(anummers);
                        continue anummerLoop;
                    }
                }
            }

            result.add(anummers);
        }

        return result;

    }

    private static void verwerkLegeRijen(
            final List<Lo3Categorie<Lo3OuderInhoud>> legeRijen,
            final List<Lo3Stapel<Lo3OuderInhoud>> stapels) {
        // Voor elke lege rij moeten we de stapel bepalen waarin de grootste ingangsdatum geldigheid zit die kleiner is
        // dan de ingangsdatum geldigheid van de lege rij.

        for (final Lo3Categorie<Lo3OuderInhoud> legeRij : legeRijen) {
            final Lo3Stapel<Lo3OuderInhoud> targetStapel =
                    bepaalStapelVoorLegeRij(stapels, legeRij.getHistorie().getIngangsdatumGeldigheid());

            if (targetStapel != null) {
                final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = targetStapel.getCategorieen();
                categorieen.add(legeRij);
                stapels.set(stapels.indexOf(targetStapel), new Lo3Stapel<Lo3OuderInhoud>(categorieen));
            }
        }
    }

    private static Lo3Stapel<Lo3OuderInhoud> bepaalStapelVoorLegeRij(
            final List<Lo3Stapel<Lo3OuderInhoud>> stapels,
            final Lo3Datum ingangsDatumLegeRij) {
        Lo3Stapel<Lo3OuderInhoud> result = null;

        for (final Lo3Stapel<Lo3OuderInhoud> stapel : stapels) {
            final Lo3Datum datum = bepaalGrootsteDatumKleinerDan(stapel, ingangsDatumLegeRij);
            if (datum != null) {
                if (result == null) {
                    result = stapel;
                } else {
                    if (datum.compareTo(bepaalGrootsteDatumKleinerDan(result, ingangsDatumLegeRij)) > 0) {
                        result = stapel;
                    }
                }
            }
        }

        return result;

    }

    private static Lo3Datum bepaalGrootsteDatumKleinerDan(
            final Lo3Stapel<Lo3OuderInhoud> stapel,
            final Lo3Datum ingangsdatum) {
        LOG.debug("bepaalGrootsteDatumKleinerDan\n   stapel={}\n   datum={}", stapel, ingangsdatum);

        Lo3Datum result = null;
        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            final Lo3Datum datum = categorie.getHistorie().getIngangsdatumGeldigheid();
            if (datum.compareTo(ingangsdatum) <= 0) {
                if (result == null) {
                    result = datum;
                } else {
                    if (datum.compareTo(result) > 0) {
                        result = datum;
                    }
                }
            }
        }

        LOG.debug("result: {}", result);

        return result;
    }

    private static List<Lo3Categorie<Lo3OuderInhoud>> verwijderLegeRijen(
            final List<Lo3Categorie<Lo3OuderInhoud>> categorieen) {
        final List<Lo3Categorie<Lo3OuderInhoud>> result = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();

        final Iterator<Lo3Categorie<Lo3OuderInhoud>> iterator = categorieen.iterator();
        while (iterator.hasNext()) {
            final Lo3Categorie<Lo3OuderInhoud> categorie = iterator.next();
            if (categorie.getInhoud().isJurischeGeenOuder()) {
                iterator.remove();
                result.add(categorie);
            }
        }

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void beeindigen(final List<Lo3Stapel<Lo3OuderInhoud>> stapels) {
        LOG.debug("beeindigen\n   stapels={}", stapels);

        for (int index = 0; index < stapels.size(); index++) {
            final Lo3Stapel<Lo3OuderInhoud> stapel = stapels.get(index);

            LOG.debug("\nstapel={}", stapel);
            if (bevatGeenBeeindiging(stapel)) {
                final Lo3Datum ingang = bepaalIngangsdatum(stapel);
                LOG.debug("\ningang={}", ingang);

                final Lo3Stapel<Lo3OuderInhoud> opvolger = zoekOpvolger(stapels, ingang);
                if (opvolger != null) {
                    LOG.debug("\nopvolger={}", opvolger);
                    stapels.set(index, beeindig(stapel, opvolger));
                }
            }
        }
    }

    private static Lo3Stapel<Lo3OuderInhoud> zoekOpvolger(
            final List<Lo3Stapel<Lo3OuderInhoud>> stapels,
            final Lo3Datum ingang) {
        Lo3Stapel<Lo3OuderInhoud> opvolger = null;

        for (final Lo3Stapel<Lo3OuderInhoud> stapel : stapels) {
            final Lo3Datum datum = bepaalIngangsdatum(stapel);

            if (datum.compareTo(ingang) > 0) {
                if (opvolger == null) {
                    opvolger = stapel;
                } else {
                    if (datum.compareTo(bepaalIngangsdatum(opvolger)) < 0) {
                        opvolger = stapel;
                    }
                }
            }
        }

        return opvolger;
    }

    private static Lo3Datum bepaalIngangsdatum(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            final Lo3Datum datum = categorie.getInhoud().getFamilierechtelijkeBetrekking();
            if (datum != null) {
                return datum;
            }
        }

        return null;
    }

    private static boolean bevatGeenBeeindiging(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            if (categorie.getInhoud().isJurischeGeenOuder()) {
                return false;
            }
        }
        return true;
    }

    private static Lo3Stapel<Lo3OuderInhoud> beeindig(
            final Lo3Stapel<Lo3OuderInhoud> teBeeindigenStapel,
            final Lo3Stapel<Lo3OuderInhoud> volgendeStapel) {
        Lo3Categorie<Lo3OuderInhoud> beeindiging = volgendeStapel.getMeestRecenteElement();
        while (beeindiging.getInhoud().isJurischeGeenOuder()) {
            beeindiging = volgendeStapel.getVorigElement(beeindiging);
        }

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = teBeeindigenStapel.getCategorieen();

        final Lo3OuderInhoud inhoud =
                new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);
        final Lo3Historie historie =
                new Lo3Historie(null, beeindiging.getInhoud().getFamilierechtelijkeBetrekking(), beeindiging
                        .getHistorie().getDatumVanOpneming());
        final Lo3Documentatie documentatie = beeindiging.getDocumentatie();

        categorieen
                .add(new Lo3Categorie<Lo3OuderInhoud>(inhoud, documentatie, historie, beeindiging.getLo3Herkomst()));
        return new Lo3Stapel<Lo3OuderInhoud>(categorieen);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static List<Lo3Stapel<Lo3OuderInhoud>> splitsOpDatum(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        final Map<Lo3Datum, List<Lo3Categorie<Lo3OuderInhoud>>> map =
                new HashMap<Lo3Datum, List<Lo3Categorie<Lo3OuderInhoud>>>();

        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            final Lo3Datum key = categorie.getInhoud().getFamilierechtelijkeBetrekking();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<Lo3Categorie<Lo3OuderInhoud>>());
            }
            map.get(key).add(categorie);
        }

        final List<Lo3Stapel<Lo3OuderInhoud>> result = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
        for (final List<Lo3Categorie<Lo3OuderInhoud>> categorieen : map.values()) {
            result.add(new Lo3Stapel<Lo3OuderInhoud>(categorieen));
        }

        return result;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static List<Lo3Stapel<Lo3OuderInhoud>> splitsOpAnummer(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        LOG.debug("splitsOpAnummer(stapel={})", stapel);
        final Map<Long, List<Lo3Categorie<Lo3OuderInhoud>>> map =
                new HashMap<Long, List<Lo3Categorie<Lo3OuderInhoud>>>();

        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            final Long key = categorie.getInhoud().getaNummer();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<Lo3Categorie<Lo3OuderInhoud>>());
            }
            map.get(key).add(categorie);
        }
        LOG.debug("map: {}", map);

        // Speciale situatie voor splitsen op a-nummer. Als er rijen zijn zonder anummer dan moeten deze gevoegd worden
        // bij het a-nummer met rijen die niet onjuist zijn. Als dat a-nummer niet bestaat dan vormen deze rijen een
        // eigen stapel
        List<Lo3Categorie<Lo3OuderInhoud>> nullEntries = map.get(null);
        LOG.debug("nullEntries: {}", nullEntries);

        final List<Lo3Stapel<Lo3OuderInhoud>> result = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
        for (final Map.Entry<Long, List<Lo3Categorie<Lo3OuderInhoud>>> entry : map.entrySet()) {
            // skip null entries, die behandelen we apart
            if (entry.getKey() == null) {
                continue;
            }

            final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = entry.getValue();

            if (nullEntries != null && bevatJuisteRijen(categorieen)) {
                LOG.debug("voeg nullEntries toe aan lijst met juiste rij: {}", categorieen);
                categorieen.addAll(nullEntries);
                nullEntries = null;
            }
            result.add(new Lo3Stapel<Lo3OuderInhoud>(categorieen));
        }
        if (nullEntries != null) {
            LOG.debug("Geen lijst met juiste rij dus nullEntries is een eigen stapel");
            result.add(new Lo3Stapel<Lo3OuderInhoud>(nullEntries));
        }

        return result;

    }

    private static boolean bevatJuisteRijen(final List<Lo3Categorie<Lo3OuderInhoud>> categorieen) {
        for (final Lo3Categorie<Lo3OuderInhoud> categorie : categorieen) {
            if (categorie.getHistorie().getIndicatieOnjuist() == null) {
                return true;
            }
        }

        return false;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Stapels voor hetzelfde a-nummer dienen we weer samen te voegen. Echter stapels zonder a-nummer dienen wel
     * gesplitst te blijven.
     * 
     * @param stapels
     *            stapels
     * @return stapels
     */
    private static List<Lo3Stapel<Lo3OuderInhoud>> samenvoegenOpAnummer(
            final List<Lo3Stapel<Lo3OuderInhoud>> stapels,
            final List<List<Long>> gerelateerdeAnummers) {
        final List<Lo3Stapel<Lo3OuderInhoud>> result = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();

        final Map<Long, List<Lo3Categorie<Lo3OuderInhoud>>> map =
                new HashMap<Long, List<Lo3Categorie<Lo3OuderInhoud>>>();

        for (final Lo3Stapel<Lo3OuderInhoud> stapel : stapels) {
            final Long basisKey = bepaalAnummer(stapel);

            if (basisKey == null) {
                result.add(stapel);
                continue;
            }

            final Long key = bepaalKey(basisKey, gerelateerdeAnummers);

            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<Lo3Categorie<Lo3OuderInhoud>>());
            }

            map.get(key).addAll(stapel.getCategorieen());
        }

        for (final List<Lo3Categorie<Lo3OuderInhoud>> categorieen : map.values()) {
            result.add(new Lo3Stapel<Lo3OuderInhoud>(categorieen));
        }

        return result;
    }

    private static Long bepaalKey(final Long key, final List<List<Long>> gerelateerdeAnummers) {
        for (final List<Long> anummerList : gerelateerdeAnummers) {
            if (anummerList.contains(key)) {
                return anummerList.get(0);
            }
        }

        return key;
    }

    private static Long bepaalAnummer(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            if (categorie.getInhoud().getaNummer() != null) {
                return categorie.getInhoud().getaNummer();
            }
        }

        return null;
    }

}
