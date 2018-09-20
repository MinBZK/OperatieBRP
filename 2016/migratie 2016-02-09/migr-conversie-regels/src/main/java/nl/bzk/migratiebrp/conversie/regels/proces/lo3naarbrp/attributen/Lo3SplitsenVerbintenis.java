/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Splitst verbintenissen (categorie 5) op naar aparte stapels per soort verbintenis.
 */
@Component
public final class Lo3SplitsenVerbintenis {

    private static final Logger LOG = LoggerFactory.getLogger();

    private Lo3SplitsenVerbintenis() {
    }

    /**
     * Splits de verbintenissen in één HuwelijkOfGp Stapel. Onjuiste rijen worden niet meegenomen.
     * 
     * @param stapel
     *            de HuwelijkOfGp stapel
     * @return de gesplitste verbintenissen
     */
    public static List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> splitsVerbintenissen(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        LOG.debug("splitsVerbintenissen(stapel={})", stapel);

        // We nemen eerst de juiste gevulde rijen uit de stapel (sorteren op datum ingang geldigheid)
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> juisteRijen = bepaalJuisteRijen(stapel);
        final List<Verbintenis> verbintenissen = verwerkJuisteRijen(juisteRijen);

        // Nu de verbintenissen beeindigen als dat nodig is
        beeindigVerbintenissen(verbintenissen);

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<>();
        for (final Verbintenis verbintenis : verbintenissen) {
            result.add(verbintenis.getStapel());
        }

        return result;
    }

    private static void beeindigVerbintenissen(final List<Verbintenis> juisteVerbintenissen) {
        for (int index = juisteVerbintenissen.size() - 2; index >= 0; index--) {
            final Verbintenis dezeVerbintenis = juisteVerbintenissen.get(index);
            if (!dezeVerbintenis.isBeeindigd()) {
                final Verbintenis volgendeVerbintenis = juisteVerbintenissen.get(index + 1);
                dezeVerbintenis.beeindig(volgendeVerbintenis.getRecords().get(0));
            }
        }
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB015)
    private static List<Verbintenis> verwerkJuisteRijen(final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> juisteRijen) {
        final List<Verbintenis> verbintenissen = new ArrayList<>();

        // Nu bepalen we de verschillende periodes en omzettingen
        Lo3SoortVerbintenis laatsteSoortVerbintenis = null;
        Verbintenis laatsteVerbintenis = null;

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> juisteRij : juisteRijen) {

            if (laatsteVerbintenis == null) {
                laatsteVerbintenis = new Verbintenis(juisteRij);
                verbintenissen.add(laatsteVerbintenis);
                laatsteSoortVerbintenis = juisteRij.getInhoud().getSoortVerbintenis();
                continue;
            }

            // voeg lege rij of rij van dezelfde soort verbintenis toe
            if (!Validatie.isElementGevuld(juisteRij.getInhoud().getSoortVerbintenis())
                || !Validatie.isElementGevuld(laatsteSoortVerbintenis)
                || AbstractLo3Element.equalsWaarde(juisteRij.getInhoud().getSoortVerbintenis(), laatsteSoortVerbintenis))
            {
                laatsteVerbintenis.toevoegen(juisteRij);
            } else {
                if (!verbintenissen.isEmpty()) {
                    Foutmelding.logMeldingFoutInfo(juisteRij.getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB015, null);
                }
                laatsteVerbintenis = new Verbintenis(juisteRij);
                verbintenissen.add(laatsteVerbintenis);
                laatsteSoortVerbintenis = juisteRij.getInhoud().getSoortVerbintenis();
            }
        }

        return verbintenissen;
    }

    private static List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> bepaalJuisteRijen(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> rij : verbintenis) {
            if (!Validatie.isElementGevuld(rij.getHistorie().getIndicatieOnjuist())) {
                result.add(rij);
            }
        }

        Collections.sort(result, new IngangsdatumGeldigheidComparator());

        return result;
    }

    /**
     * Sorteer categorieen obv ingangsdatum geldigheid (en daarbinnen op datum opneming).
     */
    private static final class IngangsdatumGeldigheidComparator implements Comparator<Lo3Categorie<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<?> o1, final Lo3Categorie<?> o2) {
            int result = o1.getHistorie().getIngangsdatumGeldigheid().compareTo(o2.getHistorie().getIngangsdatumGeldigheid());

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
        private final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> records = new ArrayList<>();

        private Verbintenis(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            records.add(record);
        }

        private void beeindig(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            // maak eindrecord
            final Lo3HuwelijkOfGpInhoud sluiting = record.getInhoud();
            final Lo3HuwelijkOfGpInhoud.Builder omzetting = new Lo3HuwelijkOfGpInhoud.Builder();
            omzetting.datumSluitingHuwelijkOfAangaanGp(null);
            omzetting.gemeenteCodeSluitingHuwelijkOfAangaanGp(null);
            omzetting.landCodeSluitingHuwelijkOfAangaanGp(null);
            omzetting.datumOntbindingHuwelijkOfGp(sluiting.getDatumSluitingHuwelijkOfAangaanGp());
            omzetting.gemeenteCodeOntbindingHuwelijkOfGp(sluiting.getGemeenteCodeSluitingHuwelijkOfAangaanGp());
            omzetting.landCodeOntbindingHuwelijkOfGp(sluiting.getLandCodeSluitingHuwelijkOfAangaanGp());
            omzetting.redenOntbindingHuwelijkOfGpCode(Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS);
            omzetting.soortVerbintenis(records.get(0).getInhoud().getSoortVerbintenis());

            records.add(new Lo3Categorie<>(omzetting.build(), record.getDocumentatie(), record.getHistorie(), record.getLo3Herkomst()));
        }

        private void toevoegen(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record) {
            records.add(record);
        }

        /**
         * Geef de waarde van records.
         *
         * @return records
         */
        private List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> getRecords() {
            return records;
        }

        /**
         * Geef de waarde van stapel.
         *
         * @return stapel
         */
        private Lo3Stapel<Lo3HuwelijkOfGpInhoud> getStapel() {
            return new Lo3Stapel<>(records);
        }

        /**
         * Geef de beeindigd.
         *
         * @return beeindigd
         */
        private boolean isBeeindigd() {
            boolean laatsteIsCorrectie = false;
            for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> record : records) {
                laatsteIsCorrectie = false;
                if (record.getInhoud().isOntbinding()) {
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
