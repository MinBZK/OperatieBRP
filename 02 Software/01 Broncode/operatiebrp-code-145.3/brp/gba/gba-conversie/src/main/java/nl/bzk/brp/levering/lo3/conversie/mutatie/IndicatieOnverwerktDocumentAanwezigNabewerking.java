/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import org.springframework.stereotype.Component;

/**
 * Nabewerking voor verblijfsplaats.
 */
@Component
public final class IndicatieOnverwerktDocumentAanwezigNabewerking {

    /**
     * Bepaal data voor bewerking die nodig is voor de nabewerking.
     * @param wijzigingen wijzigingen
     * @return data van voor de bewerking
     */
    public Data bepaalData(final Lo3Wijzigingen<Lo3VerblijfplaatsInhoud> wijzigingen) {
        final Lo3Datum actueel = wijzigingen.getActueleInhoud() == null ? null : wijzigingen.getActueleInhoud().getHistorie().getIngangsdatumGeldigheid();
        final Lo3Datum historisch =
                wijzigingen.getHistorischeInhoud() == null ? null : wijzigingen.getHistorischeInhoud().getHistorie().getIngangsdatumGeldigheid();
        return new Data(actueel, historisch);
    }

    /**
     * Voor indicatie onverwerkt document aanwezig moeten we een nabewerking doen.
     * @param wijzigingen wijzigingen
     * @param data data van voor de bewerking
     * @see #bepaalData(Lo3Wijzigingen)
     */
    public void voerNabewerkingUit(final Lo3Wijzigingen<Lo3VerblijfplaatsInhoud> wijzigingen, final Data data) {
        wijzigingen.setActueleInhoud(voorNabewerkingUit(wijzigingen.getActueleInhoud(), data.getActueelIngangsdatumGeldigheid()));
        wijzigingen.setHistorischeInhoud(voorNabewerkingUit(wijzigingen.getHistorischeInhoud(), data.getHistorischeIngangsdatumGeldigheid()));
    }

    private Lo3Categorie<Lo3VerblijfplaatsInhoud> voorNabewerkingUit(final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorie,
                                                                     final Lo3Datum ingangsdatumGeldigheid) {
        if (categorie == null) {
            return null;
        }

        return new Lo3Categorie<>(categorie.getInhoud(), categorie.getDocumentatie(),
                new Lo3Historie(categorie.getHistorie().getIndicatieOnjuist(), ingangsdatumGeldigheid, categorie.getHistorie().getDatumVanOpneming()),
                categorie.getLo3Herkomst());
    }


    /**
     * Data benodigd voor na-bewerking die opgehaald moet worden voor bewerking.
     */
    public static final class Data {
        private final Lo3Datum actueelIngangsdatumGeldigheid;
        private final Lo3Datum historischeIngangsdatumGeldigheid;

        private Data(final Lo3Datum actueelIngangsdatumGeldigheid, final Lo3Datum historischeIngangsdatumGeldigheid) {
            this.actueelIngangsdatumGeldigheid = actueelIngangsdatumGeldigheid;
            this.historischeIngangsdatumGeldigheid = historischeIngangsdatumGeldigheid;
        }

        /**
         * Geef de actuele ingang geldigheid.
         * @return Datum van de ingang geldigheid
         */
        public Lo3Datum getActueelIngangsdatumGeldigheid() {
            return actueelIngangsdatumGeldigheid;
        }

        /**
         * Geef de historische ingang geldigheid.
         * @return datum van de ingang geldigheid
         */
        public Lo3Datum getHistorischeIngangsdatumGeldigheid() {
            return historischeIngangsdatumGeldigheid;
        }

    }
}
