/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 * BerichtAttribuutBuilder.
 */
final class BerichtAttribuutBuilder {
    private BerichtAttribuutBuilder() {
    }

    /**
     * Bouwt het BerichtAttribuut.
     * @param attribuut het meta attribuut
     * @param berichtgegevens de leverpersoon
     * @return bericht element
     */
    public static BerichtElement.Builder build(final MetaAttribuut attribuut, final Berichtgegevens berichtgegevens) {
        final String waarde = bepaalWaarde(attribuut, berichtgegevens);
        return BerichtElement.builder().metNaam(BerichtUtil.lowercaseFirst(attribuut.getAttribuutElement().getXmlNaam())).metWaarde(waarde);
    }

    private static String bepaalWaarde(final MetaAttribuut metaAttribuut, final Berichtgegevens berichtgegevens) {
        final AttribuutElement attribuutElement = metaAttribuut.getAttribuutElement();
        final String versleuteldeWaarde = bepaalOnderzoekObjectsleutel(metaAttribuut, berichtgegevens);
        final String waarde;
        if (versleuteldeWaarde != null) {
            waarde = versleuteldeWaarde;
        } else if (attribuutElement.isVerantwoording()) {
            waarde = metaAttribuut.<Actie>getWaarde().getId().toString();
        } else {
            waarde = metaAttribuut.getGeformatteerdeWaarde();
        }
        return waarde;
    }

    @Bedrijfsregel(Regel.R1567)
    private static String bepaalOnderzoekObjectsleutel(final MetaAttribuut attribuut, final Berichtgegevens berichtgegevens) {
        String waarde = null;
        if (attribuut.getAttribuutElement().getId().equals(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN.getId())) {
            final MetaObject metaObject = berichtgegevens.getPersoonslijst().getOnderzoekIndex()
                    .geefObjectInOnderzoek(attribuut.getParentRecord().getParentGroep().getParentObject());
            final String versleuteldeWaarde = berichtgegevens.getVersleuteldeObjectSleutel(metaObject);
            if (versleuteldeWaarde != null) {
                waarde = versleuteldeWaarde;
            }
        }
        return waarde;
    }
}
