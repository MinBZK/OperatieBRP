/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.Lists;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;

/**
 * Util klasse om een String representatie van het model maken. De 'afdruk' kan vervolgens gebruikt worden om meta structuren inhoudelijk te vergelijken.
 */
public final class ModelAfdruk {

    private ModelAfdruk() {
    }

    /**
     * Maak een afduk van het metaobject en retourneert dit als String.
     *
     * @param metaObject een metaobject
     * @return de afdruk
     */
    public static String maakAfdruk(final MetaObject metaObject) {
        final StringWriter writer = new StringWriter();
        maakAfdruk(metaObject, writer);
        return writer.toString();
    }

    private static void maakAfdruk(final MetaObject metaObject, final StringWriter writer) {
        metaObject.accept(new AfdrukVisitor(writer));
    }

    /**
     * Visitor welke het model doorloopt en afdrukt.
     */
    private static final class AfdrukVisitor implements ModelVisitor {

        private static final Comparator<MetaGroep> META_GROEP_COMPARATOR = (o1, o2) -> o1.getGroepElement().getId()
                .compareTo(o2.getGroepElement().getId());
        private static final Comparator<MetaObject> META_OBJECT_COMPARATOR = (o1, o2) -> {
            int compare = Long.compare(o1.getObjectElement().getId(), o2.getObjectElement().getId());
            if (compare == 0) {
                compare = Long.compare(o1.getObjectsleutel(), o2.getObjectsleutel());
            }
            return compare;
        };
        private static final Comparator<MetaRecord> META_RECORD_COMPARATOR
                = (r1, r2) -> Long
                .compare(r1.getVoorkomensleutel(), r2.getVoorkomensleutel());
        private static final Comparator<MetaAttribuut> META_ATTRIBUUT_COMPARATOR
                = (a1, a2) -> a1.getAttribuutElement().getNaam()
                .compareTo(a2.getAttribuutElement().getNaam());
        private final StringWriter writer;
        private int indent;

        private AfdrukVisitor(final StringWriter writer) {
            this.writer = writer;
        }

        @Override
        public void visit(final MetaObject ot) {

            doWrite(String.format("%s [o] %s id=%d%n", geefIndent(), ot.getObjectElement().getNaam(), ot.getObjectsleutel()));
            final List<MetaGroep> gesorteerdeGroepen = Lists.newArrayList(ot.getGroepen());
            Collections.sort(gesorteerdeGroepen, META_GROEP_COMPARATOR);
            for (final MetaGroep metaGroep : gesorteerdeGroepen) {
                indent++;
                metaGroep.accept(this);
                indent--;
            }
            final List<MetaObject> gesorteerdeObjecten = Lists.newArrayList(ot.getObjecten());
            Collections.sort(gesorteerdeObjecten, META_OBJECT_COMPARATOR);
            for (final MetaObject metaObject : gesorteerdeObjecten) {
                indent++;
                metaObject.accept(this);
                indent--;
            }

        }

        @Override
        public void visit(final MetaGroep g) {
            doWrite("%s [g] %s%n", geefIndent(), g.getGroepElement().getNaam());
            final List<MetaRecord> recordsTemp = Lists.newArrayList(g.getRecords());
            Collections.sort(recordsTemp, META_RECORD_COMPARATOR);
            for (final MetaRecord metaRecord : recordsTemp) {
                metaRecord.accept(this);
            }
        }

        @Override
        public void visit(final MetaRecord record) {
            indent++;
            doWrite("%s [r] id=%d%n", geefIndent(), record.getVoorkomensleutel());
            printAttributen(record);
            indent--;
        }

        @Override
        public void visit(final MetaAttribuut a) {
            Object waarde = a.getWaarde();
            if (waarde instanceof ZonedDateTime) {
                waarde = ((ZonedDateTime) waarde).format(DatumFormatterUtil.DATE_TIME_FORMATTER);
            }
            doWrite("%s [a] %s, '%s'%n", geefIndent(), a.getAttribuutElement().getNaam(), waarde);
        }

        private void printAttributen(final MetaRecord voorkomen) {
            final List<MetaAttribuut> gesorteerdeRecords = Lists.newArrayList(voorkomen.getAttributen().values());
            Collections.sort(gesorteerdeRecords, META_ATTRIBUUT_COMPARATOR);
            indent++;
            for (final MetaAttribuut attribuut : gesorteerdeRecords) {
                attribuut.accept(this);
            }
            indent--;
        }

        private String geefIndent() {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < indent; i++) {
                sb.append("\t");
            }
            return sb.toString();

        }

        private void doWrite(final String string, final Object... args) {
            writer.write(String.format(string, args));
        }
    }
}
