/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.Actiebron;

/**
 * Comparator voor bronnen binnen een administratieve handeling.
 */
@Bedrijfsregel(Regel.R1804)
final class ActiebronComparator implements Comparator<Actiebron>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Herbruikbare threadsafe instantie van deze comparator.
     */
    public static final ActiebronComparator INSTANCE = new ActiebronComparator();

    private ActiebronComparator() {
    }

    @Override
    public int compare(final Actiebron bron1, final Actiebron bron2) {
        valideerBeideObjectenHebbenWaarde(bron1, bron2);
        Integer docVergelijking = vergelijkObvDocument(bron1, bron2);
        return docVergelijking != null ? docVergelijking : vergelijkObvRechtsgrond(bron1, bron2);
    }

    private void valideerBeideObjectenHebbenWaarde(final Actiebron bron1, final Actiebron bron2) {
        if (bron1 == null || bron2 == null) {
            throw new IllegalArgumentException("Missend object waardoor deze niet te vergelijken is.");
        }
    }

    private Integer vergelijkObvDocument(final Actiebron bron1, final Actiebron bron2) {
        Integer resultaat = null;
        if (bron1.getDocument() != null && bron2.getDocument() != null) {
            resultaat = Long.compare(bron1.getDocument().getiD(), bron2.getDocument().getiD());
        } else if (bron1.getDocument() != null) {
            resultaat = 1;
        } else if (bron2.getDocument() != null) {
            resultaat = -1;
        }
        return resultaat;
    }

    private int vergelijkObvRechtsgrond(final Actiebron bron1, final Actiebron bron2) {
        final Integer resultaat;
        if (bron1.getRechtsgrond() != null && bron2.getRechtsgrond() != null) {
            resultaat = bron1.getRechtsgrond().compareTo(bron2.getRechtsgrond());
        } else if (bron1.getRechtsgrond() != null) {
            resultaat = 1;
        } else if (bron2.getRechtsgrond() != null) {
            resultaat = -1;
        } else if (bron1.getRechtsgrondomschrijving() != null && bron2.getRechtsgrondomschrijving() != null) {
            resultaat = bron1.getRechtsgrondomschrijving().compareTo(bron2.getRechtsgrondomschrijving());
        } else {
            resultaat = 0;
        }
        return resultaat;
    }

}
