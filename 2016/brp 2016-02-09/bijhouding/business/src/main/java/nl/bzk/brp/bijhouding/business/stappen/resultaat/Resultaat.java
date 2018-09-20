/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.resultaat;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.util.Assert;

/**
 * Value object voor het bijhouden het resultaat van stappen. Dit is een immutable object.
 */
public final class Resultaat {

    /**
     * Leeg resultaat, handig om te gebruiken als constante.
     */
    public static final Resultaat LEEG = builder().build();

    private SortedSet<ResultaatMelding>                                meldingen;
    private List<Integer>                                              teArchiverenPersoonIdsIngaandBericht;
    private AdministratieveHandelingModel                              administratieveHandeling;
    private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> toegepasteDeblokkeerverzoeken;

    private Resultaat() {
    }

    public Set<ResultaatMelding> getMeldingen() {
        return Collections.unmodifiableSet(meldingen);
    }

    public List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> getToegepasteDeblokkeerverzoeken() {
        return Collections.unmodifiableList(toegepasteDeblokkeerverzoeken);
    }

    public List<Integer> getTeArchiverenPersoonIdsIngaandBericht() {
        return Collections.unmodifiableList(teArchiverenPersoonIdsIngaandBericht);
    }

    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Maakt een nieuwe Builder.
     * @return de Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Geeft een builder met het gegeven resultaat als standaard vulling.
     *
     * @param resultaat resultaat
     * @return builder
     */
    public static Builder builder(final Resultaat resultaat) {
        return builder()
            .withMeldingen(resultaat.meldingen)
            .withTeArchiverenPersoonIdsIngaandBericht(resultaat.teArchiverenPersoonIdsIngaandBericht)
            .withAdministratieveHandeling(resultaat.administratieveHandeling)
            .withToegepasteDeblokkeermeldingen(resultaat.toegepasteDeblokkeerverzoeken);
    }

    /**
     * Voegt het gegeven resultaat toe aan het huidige, en geeft dit terug in een nieuw Resultaat.
     *
     * @param that het resultaat dat toegevoegd moet worden
     * @return het resultaat
     */
    public Resultaat voegToe(final Resultaat that) {
        return Resultaat.builder()
            .withMeldingen(
                Sets.newHashSet(Iterables.concat(this.meldingen, that.meldingen)))
            .withToegepasteDeblokkeermeldingen(
                Lists.newArrayList(Iterables.concat(this.toegepasteDeblokkeerverzoeken, that.toegepasteDeblokkeerverzoeken)))
            .withTeArchiverenPersoonIdsIngaandBericht(
                Lists.newArrayList(Iterables.concat(this.teArchiverenPersoonIdsIngaandBericht, that.teArchiverenPersoonIdsIngaandBericht)))
            .withAdministratieveHandeling(that.administratieveHandeling)
            .build();
    }

    /**
     * @param resultaatMelding resultaatMelding
     * @return resultaat
     */
    public Resultaat voegToe(final ResultaatMelding resultaatMelding) {
        final Resultaat resultaat = Resultaat.builder().withMeldingen(Collections.singleton(resultaatMelding)).build();
        return voegToe(resultaat);
    }

    /**
     * Zoekt uit of het resultaat minstens een verwerking stoppende melding bevat.
     *
     * @return true als dat zo is
     */
    public boolean bevatVerwerkingStoppendeMelding() {
        for (ResultaatMelding melding : getMeldingen()) {
            if (melding.isVerwerkingStoppend()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deblokkeer de gegeven meldingen.
     * @param teDeblokkerenMeldingen de te deblokkeren meldingen; de gegeven meldingen moeten zich ook in de meldingen van het resultaat bevinden.
     * @return een nieuw Resultaat waarbij de gegeven meldingen gedeblokkeerd zijn.
     */
    public Resultaat deblokkeer(final List<ResultaatMelding> teDeblokkerenMeldingen) {
        final Set<ResultaatMelding> gefilterdeMeldingen = new HashSet<>();
        for (ResultaatMelding resultaatMelding : meldingen) {
            if (teDeblokkerenMeldingen.contains(resultaatMelding)) {
                gefilterdeMeldingen.add(resultaatMelding.deblokkeer());
            } else {
                gefilterdeMeldingen.add(resultaatMelding);
            }
        }
        return builder(this).withMeldingen(gefilterdeMeldingen).build();

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Resultaat resultaat = (Resultaat) o;
        return Objects.equals(meldingen, resultaat.meldingen)
            && Objects.equals(teArchiverenPersoonIdsIngaandBericht, resultaat.teArchiverenPersoonIdsIngaandBericht)
            && Objects.equals(toegepasteDeblokkeerverzoeken, resultaat.toegepasteDeblokkeerverzoeken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meldingen, teArchiverenPersoonIdsIngaandBericht, toegepasteDeblokkeerverzoeken);
    }

    /**
     * Builder.
     */
    public static final class Builder {

        private SortedSet<ResultaatMelding>                                meldingen                            = new TreeSet<>();
        private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> toegepasteDeblokkeerverzoeken        = new ArrayList<>();
        private List<Integer>                                              teArchiverenPersoonIdsIngaandBericht = new ArrayList<>();
        private AdministratieveHandelingModel                              administratieveHandeling;

        /**
         * Bouw een resultaat.
         * @return het resultaat.
         */
        public Resultaat build() {
            final Resultaat resultaat = new Resultaat();

            resultaat.meldingen = meldingen;
            resultaat.toegepasteDeblokkeerverzoeken = toegepasteDeblokkeerverzoeken;
            resultaat.teArchiverenPersoonIdsIngaandBericht = teArchiverenPersoonIdsIngaandBericht;
            resultaat.administratieveHandeling = administratieveHandeling;

            Assert.notNull(meldingen);
            Assert.notNull(toegepasteDeblokkeerverzoeken);
            Assert.notNull(teArchiverenPersoonIdsIngaandBericht);

            return resultaat;
        }

        /**
         * Voegt meldingen toe aan de Builder.
         * @param withMeldingen de meldingen
         * @return de Builder
         */
        public Builder withMeldingen(final Set<ResultaatMelding> withMeldingen) {
            meldingen = new TreeSet<>(withMeldingen);
            return this;
        }

        /**
         * Voegt toegepaste deblokkeerverzoeken toe aan de Builder.
         * @param withToegepasteDeblokkeerverzoeken de toegepaste deblokkeerverzoeken
         * @return de Builder
         */
        public Builder withToegepasteDeblokkeermeldingen(final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht>
            withToegepasteDeblokkeerverzoeken)
        {
            toegepasteDeblokkeerverzoeken = withToegepasteDeblokkeerverzoeken;
            return this;
        }

        /**
         * Voegt te achiveren persoonIDs toe aan de Builder.
         * @param withTeArchiverenPersoonIdsIngaandBericht de te archiveren persoonIDs
         * @return de Builder
         */
        public Builder withTeArchiverenPersoonIdsIngaandBericht(final List<Integer> withTeArchiverenPersoonIdsIngaandBericht) {
            teArchiverenPersoonIdsIngaandBericht = withTeArchiverenPersoonIdsIngaandBericht;
            return this;
        }

        /**
         * Voegt een administratieve handeling toe aan de Builder.
         * @param withAdministratieveHandeling de administratieve handeling
         * @return de Builder
         */
        public Builder withAdministratieveHandeling(final AdministratieveHandelingModel withAdministratieveHandeling) {
            administratieveHandeling = withAdministratieveHandeling;
            return this;
        }
    }
}
