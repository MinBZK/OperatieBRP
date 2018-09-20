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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Splitsen ouders.
 */
@Component
public final class Lo3SplitsenGerelateerdeOuders {

    private static final Logger LOG = LoggerFactory.getLogger();

    private Lo3SplitsenGerelateerdeOuders() {
    }

    /**
     * Splits ouders.
     *
     * @param stapel
     *            de Lo3 ouder stapel.
     * @return de in afzonderlijke ouders gesplitste stapels
     */
    public static List<Lo3Stapel<Lo3OuderInhoud>> splitsOuders(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        LOG.debug("splitsOuders(stapel={})", stapel);
        if (stapel == null) {
            return new ArrayList<>();
        }

        // We nemen eerst de juiste gevulde rijen uit de stapel (sorteren op datum ingang geldigheid)
        final List<Lo3Categorie<Lo3OuderInhoud>> juisteRijen = bepaalJuisteRijen(stapel);
        final List<OuderRelatie> ouderRelaties = verwerkJuisteRijen(juisteRijen);

        beeindigOuderRelaties(ouderRelaties);

        controleerBijzondereSituatie(juisteRijen, ouderRelaties);

        final List<Lo3Stapel<Lo3OuderInhoud>> result = new ArrayList<>();
        for (final OuderRelatie ouderRelatie : ouderRelaties) {
            result.add(ouderRelatie.getStapel());
        }

        return result;
    }

    private static void beeindigOuderRelaties(final List<OuderRelatie> ouderRelaties) {
        for (int index = ouderRelaties.size() - 2; index >= 0; index--) {
            final OuderRelatie dezeOuderRelatie = ouderRelaties.get(index);
            if (!dezeOuderRelatie.isBeeindigd()) {
                final OuderRelatie volgendeOuderRelatie = ouderRelaties.get(index + 1);
                dezeOuderRelatie.beeindig(volgendeOuderRelatie.getRecords().get(0));
            }
        }
    }

    private static List<OuderRelatie> verwerkJuisteRijen(final List<Lo3Categorie<Lo3OuderInhoud>> juisteRijen) {
        final List<OuderRelatie> ouderRelaties = new ArrayList<>();

        // Nu bepalen we de verschillende periodes en omzettingen
        Lo3Datum laatsteFamilierechtelijkBetrekking = null;
        OuderRelatie laatsteOuderRelatie = null;

        for (final Lo3Categorie<Lo3OuderInhoud> juisteRij : juisteRijen) {

            final Lo3Datum familierechtelijkeBetrekking = juisteRij.getInhoud().getFamilierechtelijkeBetrekking();

            if (laatsteOuderRelatie == null) {
                laatsteOuderRelatie = new OuderRelatie(juisteRij);
                ouderRelaties.add(laatsteOuderRelatie);
                laatsteFamilierechtelijkBetrekking = familierechtelijkeBetrekking;
                continue;
            }

            // voeg lege rij of rij van dezelfde ouder relatie toe
            if (Lo3Datum.equalsWaarde(familierechtelijkeBetrekking, laatsteFamilierechtelijkBetrekking)) {
                laatsteOuderRelatie.toevoegen(juisteRij);
            } else {
                laatsteOuderRelatie = new OuderRelatie(juisteRij);
                ouderRelaties.add(laatsteOuderRelatie);
                laatsteFamilierechtelijkBetrekking = familierechtelijkeBetrekking;
            }
        }

        return ouderRelaties;
    }

    private static List<Lo3Categorie<Lo3OuderInhoud>> bepaalJuisteRijen(final Lo3Stapel<Lo3OuderInhoud> verbintenis) {
        final List<Lo3Categorie<Lo3OuderInhoud>> result = new ArrayList<>();

        for (final Lo3Categorie<Lo3OuderInhoud> rij : verbintenis) {
            if (!rij.getHistorie().isOnjuist()) {
                result.add(rij);
            }
        }

        Collections.sort(result, new HerkomstComparator());

        return result;
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB016)
    private static void controleerBijzondereSituatie(final List<Lo3Categorie<Lo3OuderInhoud>> juisteRijen, final List<OuderRelatie> ouderRelaties) {
        final List<List<OuderRelatie>> samengevoegdeOuderRelaties = voegOuderRelatiesSamen(ouderRelaties);

        if (samengevoegdeOuderRelaties.size() > 1) {
            Foutmelding.logMeldingFoutInfo(juisteRijen.get(juisteRijen.size() - 1).getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB016, null);
        }
    }

    private static List<List<OuderRelatie>> voegOuderRelatiesSamen(final List<OuderRelatie> ouderRelaties) {
        final List<List<OuderRelatie>> samengevoegdeRelaties = new ArrayList<>();

        for (final OuderRelatie ouderRelatie : ouderRelaties) {
            if (ouderRelatie.getANummers().isEmpty()) {
                final List<OuderRelatie> relaties = new ArrayList<>();
                relaties.add(ouderRelatie);
                samengevoegdeRelaties.add(relaties);
                continue;
            }

            List<OuderRelatie> zelfdeOuderRelatie = null;
            for (final List<OuderRelatie> samengevoegdeRelatie : samengevoegdeRelaties) {
                // verzamel alle a-nummers uit de samengevoegde relaties
                final Set<Long> aNummers = new HashSet<>();
                for (final OuderRelatie relatie : samengevoegdeRelatie) {
                    aNummers.addAll(relatie.getANummers());
                }

                // bepaal of er overlap is tussen de a-nummers van de samengevoegde set relaties en
                // de ouderRelatie die we nu bekijken.
                aNummers.retainAll(ouderRelatie.getANummers());
                if (!aNummers.isEmpty()) {
                    // Overlappende a-nummers, dus zelfde ouder persoon gevonden
                    zelfdeOuderRelatie = samengevoegdeRelatie;
                }
            }

            if (zelfdeOuderRelatie != null) {
                zelfdeOuderRelatie.add(ouderRelatie);
            } else {
                final List<OuderRelatie> relaties = new ArrayList<>();
                relaties.add(ouderRelatie);
                samengevoegdeRelaties.add(relaties);
            }
        }

        return samengevoegdeRelaties;
    }

    /**
     * Sorteer categorieen op herkomst, van oud naar nieuw.
     */
    private static final class HerkomstComparator implements Comparator<Lo3Categorie<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<?> o1, final Lo3Categorie<?> o2) {
            return o2.getLo3Herkomst().getVoorkomen() - o1.getLo3Herkomst().getVoorkomen();
        }
    }

    /**
     * Inner class om een ouder relatie weer te geven en handelingen op uit te voeren.
     */
    private static final class OuderRelatie {
        private final List<Lo3Categorie<Lo3OuderInhoud>> records = new ArrayList<>();
        private final Set<Long> anummers = new HashSet<>();

        private OuderRelatie(final Lo3Categorie<Lo3OuderInhoud> record) {
            toevoegen(record);
        }

        private void beeindig(final Lo3Categorie<Lo3OuderInhoud> beeindiging) {
            // maak eindrecord
            final Lo3OuderInhoud inhoud = new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);
            Lo3Datum ingangsdatumBeindiging = beeindiging.getInhoud().getFamilierechtelijkeBetrekking();
            if (!Validatie.isElementGevuld(ingangsdatumBeindiging)) {
                ingangsdatumBeindiging = beeindiging.getHistorie().getIngangsdatumGeldigheid();
            }

            final Lo3Historie historie = new Lo3Historie(null, ingangsdatumBeindiging, beeindiging.getHistorie().getDatumVanOpneming());
            final Lo3Documentatie documentatie = beeindiging.getDocumentatie();

            final Lo3Herkomst herkomst = beeindiging.getLo3Herkomst();
            records.add(new Lo3Categorie<>(inhoud, documentatie, historie, herkomst, true));
        }

        private void toevoegen(final Lo3Categorie<Lo3OuderInhoud> record) {
            final Long anummer = Lo3Long.unwrap(record.getInhoud().getaNummer());
            if (anummer != null) {
                anummers.add(anummer);
            }
            records.add(record);
        }

        /**
         * Geef de waarde van records.
         *
         * @return records
         */
        private List<Lo3Categorie<Lo3OuderInhoud>> getRecords() {
            return records;
        }

        /**
         * Geef de waarde van a nummers.
         *
         * @return a nummers
         */
        private Set<Long> getANummers() {
            return anummers;
        }

        /**
         * Geef de waarde van stapel.
         *
         * @return stapel
         */
        private Lo3Stapel<Lo3OuderInhoud> getStapel() {
            return new Lo3Stapel<>(records);
        }

        /**
         * Geef de beeindigd.
         *
         * @return beeindigd
         */
        private boolean isBeeindigd() {
            for (final Lo3Categorie<Lo3OuderInhoud> record : records) {
                if (record.getInhoud().isJurischeGeenOuder()) {
                    return true;
                }
            }
            return false;
        }

    }
}
