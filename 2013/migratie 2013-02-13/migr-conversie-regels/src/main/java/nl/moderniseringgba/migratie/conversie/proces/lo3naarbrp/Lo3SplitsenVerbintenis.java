/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Foutmelding;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Splitst verbintenissen (categorie 5) op naar aparte stapels per soort verbintenis.
 */
@Component
public class Lo3SplitsenVerbintenis {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Splits de verbintenissen in de persoonslijst.
     * 
     * @param persoonslijst
     *            persoonslijst
     * @return persoonlijst met gesplitste verbintenissen
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        LOG.debug("converteer(persoonslijst=<notshown>)");
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> verbintenissen =
                new ArrayList<Lo3Stapel<Lo3HuwelijkOfGpInhoud>>();
        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis : persoonslijst.getHuwelijkOfGpStapels()) {
            verbintenissen.addAll(splitsVerbintenissen(verbintenis));
        }
        LOG.debug("nieuwe verbintenissen ({}): {}", verbintenissen.size(), verbintenissen);
        builder.huwelijkOfGpStapels(verbintenissen);

        LOG.debug("build");
        return builder.build();
    }

    private static List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> splitsVerbintenissen(
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        LOG.debug("splitsVerbintenissen(stapel={})", stapel);

        // We nemen eerst de juiste gevulde rijen uit de stapel (sorteren op datum ingang geldigheid)
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> juisteGevuldeRijen = bepaalJuisteGevuldeRijen(stapel);
        final List<Verbintenis> verbintenissen = new ArrayList<Verbintenis>();
        verwerkJuisteGevuldeRijen(verbintenissen, juisteGevuldeRijen);
        final List<Verbintenis> juisteVerbintenissen = new ArrayList<Verbintenis>(verbintenissen);

        // We nemen nu alle onjuiste gevulde rijen uit de stapel (sorteren op datum ingang geldigheid)
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> onjuisteGevuldeRijen = bepaalOnjuisteGevuldeRijen(stapel);
        verwerkOnjuisteGevuldeRijen(verbintenissen, onjuisteGevuldeRijen);

        // We nemen nu alle lege rijen uit de stapel
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> legeRijen = bepaalLegeRijen(stapel);
        verwerkLegeRijen(verbintenissen, legeRijen);

        // Nu de verbintenissen beeindigen als dat nodig is
        beeindigVerbintenissen(juisteVerbintenissen);

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Stapel<Lo3HuwelijkOfGpInhoud>>();
        for (final Verbintenis verbintenis : verbintenissen) {
            result.add(verbintenis.getStapel());
        }

        return result;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void beeindigVerbintenissen(final List<Verbintenis> juisteVerbintenissen) {
        for (int index = juisteVerbintenissen.size() - 2; index >= 0; index--) {
            final Verbintenis dezeVerbintenis = juisteVerbintenissen.get(index);
            if (!dezeVerbintenis.isBeeindigd()) {
                final Verbintenis volgendeVerbintenis = juisteVerbintenissen.get(index + 1);
                dezeVerbintenis.beeindig(volgendeVerbintenis.getRecords().get(0));
            }
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void verwerkJuisteGevuldeRijen(
            final List<Verbintenis> verbintenissen,
            final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> juisteGevuldeRijen) {

        // Nu bepalen we de verschillende periodes en omzettingen
        Lo3SoortVerbintenis laatsteSoortVerbintenis = null;
        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juisteGevuldeRij : juisteGevuldeRijen) {
            if (juisteGevuldeRij.getInhoud().getSoortVerbintenis().equals(laatsteSoortVerbintenis)) {
                verbintenissen.get(verbintenissen.size() - 1).toevoegen(juisteGevuldeRij);
            } else {
                if (!verbintenissen.isEmpty()) {
                    Foutmelding.logBijzondereSituatieFout(juisteGevuldeRij.getLo3Herkomst(),
                            BijzondereSituaties.BIJZ_CONV_LB015);

                    verbintenissen.get(verbintenissen.size() - 1).setEinddatum(
                            juisteGevuldeRij.getHistorie().getIngangsdatumGeldigheid());
                }
                verbintenissen.add(new Verbintenis(juisteGevuldeRij));
                laatsteSoortVerbintenis = juisteGevuldeRij.getInhoud().getSoortVerbintenis();
            }
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    // CHECKSTYLE:OFF - Cyclomatic complexity - Als we het opsplitsen wordt het alleen maar onduidelijker.
    private static void verwerkOnjuisteGevuldeRijen(
    // CHECKSTYLE:ON
            final List<Verbintenis> verbintenissenLijst,
            final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> onjuisteGevuldeRijen) {

        // Omvormen naar map
        final Map<Lo3SoortVerbintenis, List<Verbintenis>> verbintenissenMap =
                new HashMap<Lo3SoortVerbintenis, List<Verbintenis>>();

        for (final Lo3SoortVerbintenisEnum soort : Lo3SoortVerbintenisEnum.values()) {
            verbintenissenMap.put(soort.asElement(), new ArrayList<Verbintenis>());
        }

        for (final Verbintenis verbintenis : verbintenissenLijst) {
            verbintenissenMap.get(verbintenis.getSoort()).add(verbintenis);
        }

        final Map<Lo3SoortVerbintenis, List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>> twijfelRijenMap =
                new HashMap<Lo3SoortVerbintenis, List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>>();

        // Verwerken
        onjuisteGevuldeRijenLoop: for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> onjuisteRij : onjuisteGevuldeRijen) {
            final Lo3SoortVerbintenis soort = onjuisteRij.getInhoud().getSoortVerbintenis();
            final List<Verbintenis> verbintenissen = verbintenissenMap.get(soort);

            // Slechts 1 periode beschikbaar -> toevoegen aan die periode
            if (verbintenissen.size() == 1) {
                verbintenissen.get(0).toevoegen(onjuisteRij);
                continue;
            }

            if (verbintenissenMap.get(soort).size() > 1) {
                final Lo3Datum datum = onjuisteRij.getHistorie().getIngangsdatumGeldigheid();
                // Valt binnen een periode -> toevoegen aan die periode
                for (final Verbintenis verbintenis : verbintenissen) {
                    if (verbintenis.valtBinnenPeriode(datum)) {
                        verbintenis.toevoegen(onjuisteRij);
                        continue onjuisteGevuldeRijenLoop;
                    }
                }

                // Valt voor eerste periode -> toevoegen aan eerste periode
                final Verbintenis eersteVerbintenis = verbintenissen.get(0);
                if (datum.compareTo(eersteVerbintenis.getBeginDatum()) < 0) {
                    eersteVerbintenis.toevoegen(onjuisteRij);
                    continue;
                }

                // Valt na laatste periode -> toevoegen aan laatste periode
                final Verbintenis laatsteVerbintenis = verbintenissen.get(verbintenissen.size() - 1);
                if (datum.compareTo(eersteVerbintenis.getBeginDatum()) > 0) {
                    laatsteVerbintenis.toevoegen(onjuisteRij);
                    continue;
                }
            }

            // Laatste redmiddel -> eigen stapel
            if (!twijfelRijenMap.containsKey(soort)) {
                twijfelRijenMap.put(soort, new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>());
            }
            twijfelRijenMap.get(soort).add(onjuisteRij);
        }

        // Twijfel rijen opnemen als eigen stapels
        for (final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> twijfelRijen : twijfelRijenMap.values()) {
            verbintenissenLijst.add(new Verbintenis(twijfelRijen));
        }

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static void verwerkLegeRijen(
            final List<Verbintenis> verbintenissen,
            final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> legeRijen) {
        LOG.debug("verwerkLegeRijen()");
        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> legeRij : legeRijen) {
            LOG.debug("verwerkLegeRij: {}", legeRij);
            final Verbintenis verbintenis =
                    zoekVerbintenisOpIngangsdatum(verbintenissen, legeRij.getHistorie().getIngangsdatumGeldigheid(),
                            legeRij.getHistorie().getDatumVanOpneming());
            LOG.debug("verbintenis: {}", verbintenis);

            if (verbintenis == null) {
                throw new IllegalStateException(
                        "Er kan geen rij gevonden worden om een lege huwelijksrij aan te koppelen");
            }

            verbintenis.toevoegen(legeRij);
        }
    }

    private static Verbintenis zoekVerbintenisOpIngangsdatum(
            final List<Verbintenis> verbintenissen,
            final Lo3Datum ingangsdatumGeldigheid,
            final Lo3Datum datumOpneming) {
        Verbintenis result = null;
        Lo3Datum resultOpneming = null;

        for (final Verbintenis verbintenis : verbintenissen) {
            for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record : verbintenis.getRecords()) {

                if (!record.getHistorie().getIngangsdatumGeldigheid().equals(ingangsdatumGeldigheid)) {
                    continue;
                }

                if (Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS.equals(record.getInhoud()
                        .getRedenOntbindingHuwelijkOfGpCode())) {
                    continue;
                }

                final Lo3Datum recordOpneming = record.getHistorie().getDatumVanOpneming();
                if (datumOpneming.compareTo(recordOpneming) < 0) {
                    continue;
                }

                if (resultOpneming == null || resultOpneming.compareTo(recordOpneming) < 0) {
                    result = verbintenis;
                    resultOpneming = recordOpneming;
                }
            }
        }

        LOG.debug("resultaat: {}", result);
        return result;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> bepaalJuisteGevuldeRijen(
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> rij : verbintenis) {
            if (rij.getHistorie().getIndicatieOnjuist() == null && rij.getInhoud().getSoortVerbintenis() != null) {
                result.add(rij);
            }
        }

        Collections.sort(result, new IngangsdatumGeldigheidComparator());

        return result;
    }

    private static List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> bepaalOnjuisteGevuldeRijen(
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> rij : verbintenis) {
            if (rij.getHistorie().getIndicatieOnjuist() != null && rij.getInhoud().getSoortVerbintenis() != null) {
                result.add(rij);
            }
        }

        Collections.sort(result, new IngangsdatumGeldigheidComparator());

        return result;
    }

    private static List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> bepaalLegeRijen(
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> rij : verbintenis) {
            if (rij.getInhoud().getSoortVerbintenis() == null) {
                result.add(rij);
            }
        }

        Collections.sort(result, new IngangsdatumGeldigheidComparator());

        return result;
    }

    /**
     * Sorteer categorieen obv ingangsdatum geldigheid (en daarbinnen op datum opneming).
     */
    private static final class IngangsdatumGeldigheidComparator implements Comparator<Lo3Categorie<?>> {

        @Override
        public int compare(final Lo3Categorie<?> o1, final Lo3Categorie<?> o2) {
            int result =
                    o1.getHistorie().getIngangsdatumGeldigheid()
                            .compareTo(o2.getHistorie().getIngangsdatumGeldigheid());

            if (result == 0) {
                result = o1.getHistorie().getDatumVanOpneming().compareTo(o2.getHistorie().getDatumVanOpneming());
            }

            return result;
        }

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Inner class om een verbintenis weer te geven en handelingen op uit te voeren.
     */
    private static final class Verbintenis {
        private final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> records =
                new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();
        private final Lo3Datum beginDatum;
        private Lo3Datum eindDatum;
        private final Lo3SoortVerbintenis soort;

        private Verbintenis(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            records.add(record);
            beginDatum = record.getHistorie().getIngangsdatumGeldigheid();
            soort = record.getInhoud().getSoortVerbintenis();
        }

        private Verbintenis(final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> twijfelRijen) {
            records.addAll(twijfelRijen);
            beginDatum = null;
            soort = twijfelRijen.get(0).getInhoud().getSoortVerbintenis();
        }

        private void setEinddatum(final Lo3Datum eindDatum) {
            this.eindDatum = eindDatum;
        }

        private void beeindig(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            // maak eindrecord
            final Lo3HuwelijkOfGpInhoud sluiting = record.getInhoud();
            final Lo3HuwelijkOfGpInhoud.Builder omzetting = new Lo3HuwelijkOfGpInhoud.Builder();
            omzetting.setDatumSluitingHuwelijkOfAangaanGp(null);
            omzetting.setGemeenteCodeSluitingHuwelijkOfAangaanGp(null);
            omzetting.setLandCodeSluitingHuwelijkOfAangaanGp(null);
            omzetting.setDatumOntbindingHuwelijkOfGp(sluiting.getDatumSluitingHuwelijkOfAangaanGp());
            omzetting.setGemeenteCodeOntbindingHuwelijkOfGp(sluiting.getGemeenteCodeSluitingHuwelijkOfAangaanGp());
            omzetting.setLandCodeOntbindingHuwelijkOfGp(sluiting.getLandCodeSluitingHuwelijkOfAangaanGp());
            omzetting.setRedenOntbindingHuwelijkOfGpCode(Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS);
            omzetting.setSoortVerbintenis(records.get(0).getInhoud().getSoortVerbintenis());

            records.add(new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(omzetting.build(), record.getDocumentatie(), record
                    .getHistorie(), record.getLo3Herkomst()));
        }

        private void toevoegen(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            records.add(record);
        }

        private List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> getRecords() {
            return records;
        }

        private Lo3Stapel<Lo3HuwelijkOfGpInhoud> getStapel() {
            return new Lo3Stapel<Lo3HuwelijkOfGpInhoud>(records);
        }

        private Lo3SoortVerbintenis getSoort() {
            return soort;
        }

        private boolean valtBinnenPeriode(final Lo3Datum datum) {
            if (datum.compareTo(beginDatum) < 0 || eindDatum != null && datum.compareTo(eindDatum) >= 0) {
                return false;
            }

            return true;
        }

        /**
         * @return the beginDatum
         */
        private Lo3Datum getBeginDatum() {
            return beginDatum;
        }

        private boolean isBeeindigd() {
            boolean laatsteIsCorrectie = false;
            for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record : records) {
                if (record.getHistorie().isOnjuist()) {
                    continue;
                }
                laatsteIsCorrectie = false;
                if (record.getInhoud().isOntbonden()) {
                    laatsteIsCorrectie = true;
                    break;
                }
                if (record.getInhoud().getSoortVerbintenis() == null) {
                    laatsteIsCorrectie = true;
                }
            }
            return laatsteIsCorrectie;
        }

    }

}
