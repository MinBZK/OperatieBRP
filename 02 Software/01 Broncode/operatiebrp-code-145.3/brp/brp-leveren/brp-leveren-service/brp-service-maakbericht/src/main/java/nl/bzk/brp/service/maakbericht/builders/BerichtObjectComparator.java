/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.ChildFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;


/**
 * Comparator om objecten te ordenen.
 */
@Bedrijfsregel(Regel.R1805)
final class BerichtObjectComparator implements Comparator<MetaObject>, Serializable {

    private static final long serialVersionUID = 1L;
    private final transient Berichtgegevens berichtgegevens;

    /**
     * @param berichtgegevens berichtgegevens
     */
    BerichtObjectComparator(final Berichtgegevens berichtgegevens) {
        this.berichtgegevens = berichtgegevens;
    }

    @Override
    public int compare(final MetaObject o1, final MetaObject o2) {
        final int sortering;
        if (o1.getObjectElement().isVanType(ElementConstants.BETROKKENHEID)) {
            sortering = sorteerBetrokkenheidOpPersoon(o1, o2);
        } else {
            sortering = defaultSortering(o1, o2);
        }
        return sortering;
    }

    private int defaultSortering(final MetaObject o1, final MetaObject o2) {
        //als objecten gelijk en bevatten specifieke sorteer attributen gebruiken we deze voor sortering.
        // Anders sorteren we op volgummer,objectsleutel.
        int sortering = 0;
        final List<AttribuutElement> sorteerElementen = o1.getObjectElement().getSorteerAttributen();
        if (o1.getObjectElement().equals(o2.getObjectElement()) && !sorteerElementen.isEmpty()) {
            for (AttribuutElement attribuutElement : sorteerElementen) {
                sortering = sorteerOpAttribuut(o1, o2, attribuutElement);
                if (sortering != 0) {
                    break;
                }
            }
        } else {
            sortering = Integer.compare(o1.getObjectElement().getVolgnummer(), o2.getObjectElement()
                    .getVolgnummer());
            if (sortering == 0) {
                sortering = Long.compare(o2.getObjectsleutel(), o1.getObjectsleutel());
            }
        }
        return sortering;
    }

    private int sorteerOpAttribuut(MetaObject o1, MetaObject o2, AttribuutElement attribuutElement) {
        final GroepElement groepElement = o1.getObjectElement().getSorteerGroep();
        final Comparable waarde1 = (Comparable) getActueleWaarde(o1, groepElement, attribuutElement);
        final Comparable waarde2 = (Comparable) getActueleWaarde(o2, groepElement, attribuutElement);
        if (waarde1 == null) {
            return -1;
        }
        if (waarde2 == null) {
            return 1;
        }
        return waarde1.compareTo(waarde2);

    }

    private Object getActueleWaarde(final MetaObject o, final GroepElement groepElement, final AttribuutElement attribuutElement) {
        final Object waarde;
        final MetaGroep groep = o.getGroep(groepElement);
        final MetaRecord sorteerRecord;
        if (groep.getGroepElement().isIdentiteitGroep()) {
            sorteerRecord = Iterables.getOnlyElement(groep.getRecords());
        } else {
            final List<MetaRecord> tempRecordList = new ArrayList<>(groep.getRecords());
            tempRecordList.sort(BerichtRecordComparatorFactory.maakComparator(berichtgegevens));
            sorteerRecord = Iterables.getFirst(tempRecordList, null);
        }
        if (sorteerRecord == null) {
            waarde = null;
        } else {
            final MetaAttribuut attribuut = sorteerRecord.getAttribuut(attribuutElement);
            waarde = attribuut == null ? null : attribuut.getWaarde();
        }
        return waarde;
    }


    private int sorteerBetrokkenheidOpPersoon(final MetaObject o1, final MetaObject o2) {

        final ObjectElement links = o1.getObjectElement();
        final ObjectElement rechts = o2.getObjectElement();

        //sorteer eerst op volgnummer, volgorde wordt kind, ouder, partner
        int sortering = Integer.compare(links.getVolgnummer(), rechts.getVolgnummer());
        if (sortering == 0) {

            final MetaObject persoonLinks = new PersoonGetter(o1).getBetrokkenPersoon();
            final MetaObject persoonRechts = new PersoonGetter(o2).getBetrokkenPersoon();
            //persoonId oplopend
            if (persoonLinks != null && persoonRechts != null) {
                sortering = Long.compare(persoonLinks.getObjectsleutel(), persoonRechts.getObjectsleutel());
            } else {
                //sorteer op betrokkenheid id
                sortering = Long.compare(o1.getObjectsleutel(), o2.getObjectsleutel());
            }
        }
        return sortering;
    }

    /**
     * Persoongetter.
     */
    private static final class PersoonGetter extends ChildFirstModelVisitor {

        private MetaObject betrokkenPersoon;

        PersoonGetter(final MetaObject object) {
            visit(object);
        }

        MetaObject getBetrokkenPersoon() {
            return betrokkenPersoon;
        }

        @Override
        protected void doVisit(final MetaObject object) {
            if (object.getObjectElement().isAliasVan(ElementConstants.PERSOON)) {
                betrokkenPersoon = object;
            }
        }
    }
}
