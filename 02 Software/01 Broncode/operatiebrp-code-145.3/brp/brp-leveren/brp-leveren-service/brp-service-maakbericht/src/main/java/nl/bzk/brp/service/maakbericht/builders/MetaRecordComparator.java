/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 * Deze klasse wordt gebruikt om materiele historie instanties te sorteren.
 * <p>
 * Dit is volgens de volgende specs:
 * <p>
 * <p>
 * Als sorteervolgorde van actuele/historische verschijningen van een gegevensgroep: a) verwerkingssoort - Volgens logica sorteren verwerkingssoort b)
 * tijdstipVervallen – Aflopend c) datumAanvangGeldigheid – Aflopend d) datumEindeGeldigheid – Aflopend e) tijdstipRegistratie – Aflopend
 * <p>
 * Dit met volgende sorteerregels: <ul> <li>tijdstipVervallen als eerste sorteerelement om zo de materiële en formele historie te scheiden</li>
 * <li>‘aflopend’ om meest actuele situatie vooraan te hebben</li> <li>indien tijdstipVervallen en datumEindeGeldigheid niet zijn gevuld, dan wordt
 * hiervoor ten behoeve van de sortering de ‘eeuwigheidswaarde’ genomen (voor datumEindeGeldigheid dus 99991231)</li> <li>tijdstipRegistratie speelt in de
 * sortering alleen maar een rol bij groepen met alleen formele historie</li> </ul>
 */
final class MetaRecordComparator implements Comparator<MetaRecord>, Serializable {

    private static final long serialVersionUID = 1L;

    private transient Berichtgegevens berichtgegevens;

    /**
     * @param berichtgegevens berichtgegevens
     */
    MetaRecordComparator(final Berichtgegevens berichtgegevens) {
        this.berichtgegevens = berichtgegevens;
    }

    @Override
    public int compare(final MetaRecord record1, final MetaRecord record2) {
        int vergelijking = 0;

        // a) verwerkingssoort - Volgens logica sorteren verwerkingssoort
        // (vergelijking == 0 check alvast erbij, zodat het makkelijk schuiven is)
        if (berichtgegevens.isMutatiebericht()) {
            final Verwerkingssoort verwerkingssoort1 = berichtgegevens.getVerwerkingssoort(record1);
            final Verwerkingssoort verwerkingssoort2 = berichtgegevens.getVerwerkingssoort(record2);
            if (verwerkingssoort1 != null && verwerkingssoort2 != null) {
                vergelijking = VerwerkingssoortComparator.INSTANCE.compare(verwerkingssoort1, verwerkingssoort2);
            }
        }

        // b) tijdstipVervallen – Aflopend
        if (vergelijking == 0) {
            vergelijking =
                    BerichtRecordComparatorFactory.vergelijk(BerichtRecordComparatorFactory.datumTijdOfEeuwigheid(record1.getDatumTijdVervalAttribuut()),
                            BerichtRecordComparatorFactory.datumTijdOfEeuwigheid(record2.getDatumTijdVervalAttribuut()));
        }

        if (record1.getParentGroep().getGroepElement().isMaterieel() && record2.getParentGroep().getGroepElement().isMaterieel()) {
            // c) datumAanvangGeldigheid – Aflopend
            if (vergelijking == 0) {
                // Datum aanvang geldigheid mag nooit leeg zijn, dus geen 'eeuwigheid' hier.
                vergelijking = BerichtRecordComparatorFactory.vergelijk(record1.getDatumAanvangGeldigheid(), record2
                        .getDatumAanvangGeldigheid());
            }

            // d) datumEindeGeldigheid – Aflopend
            if (vergelijking == 0) {
                vergelijking =
                        BerichtRecordComparatorFactory.vergelijk(BerichtRecordComparatorFactory.datumOfEeuwigheid(record1.getDatumEindeGeldigheid()),
                                BerichtRecordComparatorFactory.datumOfEeuwigheid(record2.getDatumEindeGeldigheid()));
            }
        }

        // e) tijdstipRegistratie – Aflopend
        if (vergelijking == 0) {
            // Datum tijd registratie mag nooit leeg zijn, dus geen 'eeuwigheid' hier.
            vergelijking = BerichtRecordComparatorFactory.vergelijk(record1.getTijdstipRegistratie(), record2
                    .getTijdstipRegistratie());
        }

        return vergelijking;
    }
}
