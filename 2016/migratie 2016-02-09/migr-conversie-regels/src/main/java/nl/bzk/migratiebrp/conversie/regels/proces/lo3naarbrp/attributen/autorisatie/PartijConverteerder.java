/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.AbstractConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;

import org.springframework.stereotype.Component;

/**
 * PartijConverteerder.
 */
@Component
public class PartijConverteerder extends AbstractConverteerder {

    @Inject
    private Lo3AttribuutConverteerder lo3AttribuutConverteerder;

    /**
     * Converteer van Lo3 model naar Migratie model.
     *
     * @param stapel
     *            {@link Lo3Stapel} van {@link Lo3AutorisatieInhoud}
     * @return {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel} van {@link BrpPartijInhoud}
     */
    public final TussenStapel<BrpPartijInhoud> converteerPartijStapel(final Lo3Stapel<Lo3AutorisatieInhoud> stapel) {
        final List<TussenGroep<BrpPartijInhoud>> groepen = new ArrayList<>();

        // Aangezien de inhoud op datum is gesorteerd, is de eerste van de stapel de jongste en die hebben we nodig.
        final Lo3Categorie<Lo3AutorisatieInhoud> bovensteCategorie = stapel.get(0);
        final BrpDatum datumIngang = lo3AttribuutConverteerder.converteerDatum(bepaalOudsteIngangsdatum(stapel));

        groepen.add(converteer(bovensteCategorie, datumIngang));

        return new TussenStapel<>(groepen);
    }

    private TussenGroep<BrpPartijInhoud> converteer(final Lo3Categorie<Lo3AutorisatieInhoud> bovensteCategorie, final BrpDatum datumIngang) {
        final BrpBoolean indVerstrekkingsbeperking =
                lo3AttribuutConverteerder.converteerLo3IndicatieGeheimCode(bovensteCategorie.getInhoud().getIndicatieGeheimhouding());

        final BrpPartijInhoud inhoud =
                new BrpPartijInhoud(datumIngang, null, indVerstrekkingsbeperking.getWaarde(), !bovensteCategorie.getInhoud().isLeeg());

        return new TussenGroep<>(inhoud, bovensteCategorie.getHistorie(), bovensteCategorie.getDocumentatie(), bovensteCategorie.getLo3Herkomst());

    }

    private Lo3Datum bepaalOudsteIngangsdatum(final Lo3Stapel<Lo3AutorisatieInhoud> stapel) {
        // Lijst voor alle ingangsdatums binnen de stapel.
        final List<Lo3Datum> ingangsdatumLijst = new ArrayList<>();

        // Vul de lijst met alle ingangsdatums.
        for (final Lo3Categorie<Lo3AutorisatieInhoud> inhoud : stapel) {
            ingangsdatumLijst.add(inhoud.getInhoud().getDatumIngang());
        }

        // Sorteer de lijst op datum, de oudste bovenaan.
        Collections.sort(ingangsdatumLijst, new DatumComparator());

        // Geef de eerst datum uit de lijst terug.
        return ingangsdatumLijst.get(0);
    }

    /**
     * Vergelijker voor Lo3Datums die kan omgaan met null waarden.
     */
    private static class DatumComparator implements Comparator<Lo3Datum>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Datum o1, final Lo3Datum o2) {

            final int resultaat;

            if (o1 == null) {
                if (o2 == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else if (o2 == null) {
                resultaat = -1;
            } else {
                resultaat = o1.compareTo(o2);
            }

            return resultaat;
        }

    }
}
