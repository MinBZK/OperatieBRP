/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;

/**
 * Visitor om de gegeven in onderzoek op te halen.
 */
@Bedrijfsregel(Regel.R1319)
public final class OnderzoekIndex {

    private static final GroepElement GEGEVENINONDERZOEK_IDENTITEIT = ElementHelper.getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT);

    /**
     * Model index
     */
    private final ModelIndex modelIndex;

    /**
     * Lookup voor element naar gegeven in onderzoek.
     */
    private Multimap<ElementObject, Onderzoekbundel> elementNaarGegevenInOnderzoekMap;

    /**
     * Lookup map voor gegevenInOnderzoeken naar alle model object die in onderzoek staan
     */
    private Multimap<Onderzoekbundel, MetaModel> gegevensInOnderzoek;

    /**
     * Lookup map voor gegevenInOnderzoeken metaobject naar alle model object die in onderzoek staan
     */
    private Map<MetaObject, MetaObject> objectenInOnderzoek;

    /**
     * Constructor.
     * @param modelIndex modelindex van een gegeven metamodel
     */
    OnderzoekIndex(final ModelIndex modelIndex) {
        this.modelIndex = modelIndex;
        bouwGegevenInOnderzoekenMap();

        final Indexer indexer = new Indexer();
        indexer.visit(modelIndex.getMetaObject());
        gegevensInOnderzoek = Multimaps.unmodifiableMultimap(indexer.gegevensInOnderzoekTemp);
        objectenInOnderzoek = Collections.unmodifiableMap(indexer.objectenInOnderzoekTemp);
    }

    /**
     * @return multimap van de gegevens in onderzoek
     */
    public Multimap<Onderzoekbundel, MetaModel> getGegevensInOnderzoek() {
        return gegevensInOnderzoek;
    }

    /**
     * @return alle gegevens in onderzoek
     */
    public List<Onderzoekbundel> getAlleGegevensInOnderzoek() {
        return ImmutableList.<Onderzoekbundel>copyOf(elementNaarGegevenInOnderzoekMap.values());
    }

    /**
     * @param gegevenInOnderzoekObject gegevenInOnderzoekObject
     * @return object in onderzoek behorende bij gegeven (kan null zijn)
     */
    public MetaObject geefObjectInOnderzoek(final MetaObject gegevenInOnderzoekObject) {
        return objectenInOnderzoek.get(gegevenInOnderzoekObject);
    }

    private void bouwGegevenInOnderzoekenMap() {
        final Multimap<ElementObject, Onderzoekbundel> tempElementNaarGegevenInOnderzoekMap = LinkedHashMultimap.create();
        final Set<MetaGroep> groepen = modelIndex.geefGroepenVanElement(GEGEVENINONDERZOEK_IDENTITEIT);
        for (final MetaGroep metaGroep : groepen) {
            for (final MetaRecord record : metaGroep.getRecords()) {
                final Onderzoekbundel onderzoekbundel = new Onderzoekbundel(record);

                tempElementNaarGegevenInOnderzoekMap.put(onderzoekbundel.getElement(), onderzoekbundel);
            }
        }
        elementNaarGegevenInOnderzoekMap = Multimaps.unmodifiableMultimap(tempElementNaarGegevenInOnderzoekMap);
    }

    /**
     * Indexer.
     */
    private final class Indexer extends ParentFirstModelVisitor {

        private Multimap<Onderzoekbundel, MetaModel> gegevensInOnderzoekTemp = LinkedHashMultimap.create();
        private Map<MetaObject, MetaObject> objectenInOnderzoekTemp = new HashMap<>();

        @Override
        public void doVisit(final MetaObject ot) {

            final Collection<Onderzoekbundel> onderzoekList = elementNaarGegevenInOnderzoekMap.get(ot.getObjectElement());
            if (onderzoekList == null || onderzoekList.isEmpty()) {
                return;
            }
            for (final Onderzoekbundel onderzoekbundel : onderzoekList) {
                if ((onderzoekbundel.isOntbrekend() && ot.getObjectElement().equals(onderzoekbundel.getElement()))
                        || (onderzoekbundel.getElementObjectsleutel() != null && ot.getObjectsleutel() == onderzoekbundel.getElementObjectsleutel())) {
                    gegevensInOnderzoekTemp.put(onderzoekbundel, ot);
                    objectenInOnderzoekTemp.put(onderzoekbundel.getGegevenInOnderzoek(), ot);
                }
            }
        }

        @Override
        public void doVisit(final MetaGroep groep) {
            final Collection<Onderzoekbundel> onderzoekList = elementNaarGegevenInOnderzoekMap.get(groep.getGroepElement());
            if (onderzoekList == null || onderzoekList.isEmpty()) {
                return;
            }
            for (final Onderzoekbundel onderzoekbundel : onderzoekList) {
                if ((onderzoekbundel.isOntbrekend() && groep.getGroepElement().equals(onderzoekbundel.getElement()))
                        || (onderzoekbundel.getElementObjectsleutel() != null && groep.getParentObject().getObjectsleutel() == onderzoekbundel
                        .getElementObjectsleutel())) {
                    gegevensInOnderzoekTemp.put(onderzoekbundel, groep);
                }
            }
        }

        /**
         * Zet record in onderzoek
         */
        @Override
        public void doVisit(final MetaRecord record) {
            for (final Onderzoekbundel onderzoekbundel : elementNaarGegevenInOnderzoekMap.values()) {
                if ((onderzoekbundel.isOntbrekend() && record.getParentGroep().getGroepElement().equals(onderzoekbundel.getElement()))
                        || (onderzoekbundel.getElementVoorkomensleutel() != null
                        && onderzoekbundel.getElementVoorkomensleutel().equals(record.getVoorkomensleutel()))) {

                    if (onderzoekbundel.getElement() instanceof AttribuutElement) {
                        continue;
                    }

                    /*
                      Als voorkomensleutel overeenkomt en element (van type groep) gelijk is aan de parent groep, dan
                      staat het record in onderzoek
                     */
                    final boolean recordInOnderzoekObvGroep = record.getParentGroep().getGroepElement().equals(onderzoekbundel.getElement());

                    /*
                      Als voorkomensleutel overeenkomt en element (van type object) in de parenthierarchie zit,
                      dan staat het record in onderzoek.
                     */
                    boolean recordInOnderzoekObvObject = isRecordInOnderzoekObvObject(record, onderzoekbundel, recordInOnderzoekObvGroep);

                    if (recordInOnderzoekObvGroep || recordInOnderzoekObvObject) {
                        gegevensInOnderzoekTemp.put(onderzoekbundel, record);
                    }
                }
            }
        }

        /**
         * Het attribuut staat in onderzoek, als het element overeenkomt en de voorkomen OF objectsleutel matched in de parent hierarchy.
         */
        @Override
        public void doVisit(final MetaAttribuut attribuut) {
            final Collection<Onderzoekbundel> onderzoekList = elementNaarGegevenInOnderzoekMap.get(attribuut.getAttribuutElement());
            if (onderzoekList == null || onderzoekList.isEmpty()) {
                return;
            }
            for (final Onderzoekbundel onderzoekbundel : onderzoekList) {
                doVisitAttribuut(attribuut, onderzoekbundel);
            }
        }

        private void doVisitAttribuut(final MetaAttribuut attribuut, final Onderzoekbundel onderzoekbundel) {
            final BooleanSupplier ontbrekendGegeven = () -> onderzoekbundel.isOntbrekend()
                    && attribuut.getAttribuutElement().equals(onderzoekbundel.getElement());
            final BooleanSupplier voorkomenGegeven = () -> onderzoekbundel.getElementVoorkomensleutel() != null
                    && attribuut.getParentRecord().getVoorkomensleutel() == onderzoekbundel.getElementVoorkomensleutel();
            final BooleanSupplier objectGegeven = () -> onderzoekbundel.getElementObjectsleutel() != null
                    && attribuut.getParentRecord().getParentGroep().getParentObject()
                    .getObjectsleutel() == onderzoekbundel.getElementObjectsleutel();

            if (ontbrekendGegeven.getAsBoolean() || voorkomenGegeven.getAsBoolean() || objectGegeven.getAsBoolean()) {
                //voor bepaalde attributen in onderzoek moet het object in onderzoek geplaatst worden
                if (onderzoekbundel.getElementObjectsleutel() != null) {
                    gegevensInOnderzoekTemp.put(onderzoekbundel, attribuut.getParentRecord().getParentGroep().getParentObject());
                } else {
                    gegevensInOnderzoekTemp.put(onderzoekbundel, attribuut);
                }
            }
        }

        private boolean isRecordInOnderzoekObvObject(final MetaRecord record, final Onderzoekbundel onderzoekbundel, final boolean recordInOnderzoekObvGroep) {
            boolean recordInOnderzoekObvObject = false;
            if (!recordInOnderzoekObvGroep) {
                MetaObject parentObject = record.getParentGroep().getParentObject();
                while (parentObject != null) {
                    if (parentObject.getObjectElement().equals(onderzoekbundel.getElement())) {
                        recordInOnderzoekObvObject = true;
                    }
                    parentObject = parentObject.getParentObject();
                }
            }
            return recordInOnderzoekObvObject;
        }
    }
}
