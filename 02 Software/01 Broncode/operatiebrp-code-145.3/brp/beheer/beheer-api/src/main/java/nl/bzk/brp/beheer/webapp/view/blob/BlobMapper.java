/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view.blob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Blob mapper.
 */
public final class BlobMapper {

    private final EnumMap<Element, Map<Long, BlobViewObject>> objecten = new EnumMap<>(Element.class);

    /**
     * Comparator voor element obv volgnummer.
     * @param o1 element 1
     * @param o2 element 2
     * @return o1.getVolgnummer().compareTo(o2.getVolgnummer())
     */
    public static int compareElementOnVolgnummer(final Element o1, final Element o2) {
        return o1.getVolgnummer().compareTo(o2.getVolgnummer());
    }

    /**
     * Map de gegeven blob records.
     * @param records records
     */
    public void map(final List<BlobRecord> records) {
        records.forEach(this::mapRecord);
    }

    /**
     * Maak de hierarchie van objecten tbv inzien persoon.
     */
    public void maakHierarchieVoorInzienPersoon() {
        objecten.values().forEach(map -> map.values().stream().filter(BlobViewObject::hasParentReference).forEach(object ->
                objecten.get(object.getParentElement()).get(object.getParentObjectsleutel()).addObject(object)
        ));
    }

    private void filterOpActie(final long actieId) {
        for (final Map.Entry<Element, Map<Long, BlobViewObject>> objectenMap : objecten.entrySet()) {
            for (final Map.Entry<Long, BlobViewObject> object : objectenMap.getValue().entrySet()) {
                filterOpActie(object.getValue(), actieId);
            }
        }
    }

    private void filterOpActie(final BlobViewObject value, final long actieId) {
        for (final Iterator<Map.Entry<Element, BlobViewGroep>> groepIterator = value.getGroepen().entrySet().iterator(); groepIterator.hasNext(); ) {
            final Map.Entry<Element, BlobViewGroep> groepEntry = groepIterator.next();
            final BlobViewGroep groep = groepEntry.getValue();
            if (!groep.getElement().getNaam().endsWith("Identiteit")) {
                filterOpActie(groep, actieId);
            }

            if (groep.getRecords().isEmpty()) {
                groepIterator.remove();
            }
        }
    }

    private void filterOpActie(final BlobViewGroep value, final long actieId) {
        for (final Iterator<BlobViewRecord> recordIterator = value.getRecords().iterator(); recordIterator.hasNext(); ) {
            final BlobViewRecord record = recordIterator.next();

            if (!isActie(record.getActieInhoud(), actieId)
                    && !isActie(record.getActieVerval(), actieId)
                    && !isActie(record.getActieAanpassingGeldigheid(), actieId)) {
                recordIterator.remove();
            }
        }
    }

    private boolean isActie(final BlobViewObject actie, final long actieId) {
        return actie != null && actie.getObjectsleutel().equals(actieId);
    }

    /**
     * Maak de hierarchie van objecten tbv inzien actie.
     * @param actieId actie id
     */
    public void maakHierarchieVoorInzienActie(final long actieId) {
        filterOpActie(actieId);

        maakHierarchieVoorInzienPersoon();

        omhangenOnderzoeken();
        omhangenRelaties();

        verwijderenOngerelateerdeObjecten(actieId);
    }

    private void verwijderenOngerelateerdeObjecten(final long actieId) {
        for (final Map<Long, BlobViewObject> objectenMap : objecten.values()) {
            verwijderOngerelateerdeObjecten(objectenMap.values().iterator(), actieId);
        }
    }

    private void verwijderOngerelateerdeObjecten(final Iterator<BlobViewObject> objectIterator, final long actieId) {
        while (objectIterator.hasNext()) {
            final BlobViewObject object = objectIterator.next();

            for (final Iterator<Collection<BlobViewObject>> childrenIterator = object.getObjecten().values().iterator(); childrenIterator.hasNext(); ) {
                final Collection<BlobViewObject> children = childrenIterator.next();

                verwijderOngerelateerdeObjecten(children.iterator(), actieId);
                if (children.isEmpty()) {
                    childrenIterator.remove();
                }
            }

            if (object.getObjecten().isEmpty() && !heeftGerelateerdeGroepen(object, actieId)) {
                objectIterator.remove();
            }
        }

    }

    private boolean heeftGerelateerdeGroepen(final BlobViewObject object, final long actieId) {
        for (final BlobViewGroep groep : object.getGroepen().values()) {
            for (final BlobViewRecord record : groep.getRecords()) {
                if (isActie(record.getActieInhoud(), actieId)
                        || isActie(record.getActieVerval(), actieId)
                        || isActie(record.getActieAanpassingGeldigheid(), actieId)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void omhangenOnderzoeken() {
        if (!objecten.containsKey(Element.ONDERZOEK)) {
            return;
        }
        // Alle onderzoeken aflopen
        for (final BlobViewObject onderzoek : objecten.get(Element.ONDERZOEK).values()) {
            // Oude parent
            final BlobViewObject persoon = onderzoek.getParent();
            persoon.getObjecten().remove(Element.ONDERZOEK);

            onderzoek.addObject(persoon);
        }
    }

    private void omhangenRelaties() {
        omhangenRelaties(Element.FAMILIERECHTELIJKEBETREKKING);
        omhangenRelaties(Element.HUWELIJK);
        omhangenRelaties(Element.GEREGISTREERDPARTNERSCHAP);
    }

    private void omhangenRelaties(final Element elementRelatie) {
        if (!objecten.containsKey(elementRelatie)) {
            return;
        }
        for (final BlobViewObject familierechtelijkeBetrekking : objecten.get(elementRelatie).values()) {
            final BlobViewObject ikBetrokkenheid = familierechtelijkeBetrekking.getParent();
            final BlobViewObject persoon = ikBetrokkenheid.getParent();

            ikBetrokkenheid.getObjecten().remove(elementRelatie);

            final BlobViewObject kopiePersoon = kopieerPersoonVoorRelatie(persoon);
            persoon.getObjecten().remove(ikBetrokkenheid.getElement());

            ikBetrokkenheid.addObject(kopiePersoon);
            familierechtelijkeBetrekking.addObject(ikBetrokkenheid);
            familierechtelijkeBetrekking.setParent(null);
        }
    }

    private BlobViewObject kopieerPersoonVoorRelatie(final BlobViewObject persoon) {
        final BlobViewObject result = new BlobViewObject(persoon.getElement(), persoon.getObjectsleutel(), null, null);
        result.getGroepen().put(Element.PERSOON_IDENTITEIT, persoon.getGroep(Element.PERSOON_IDENTITEIT));

        return result;
    }

    /**
     * Geef de voorkomens van een specifiek element.
     * @param objectElement element
     * @return voorkomens
     */
    public List<BlobViewObject> getObjecten(final Element objectElement) {
        final List<BlobViewObject> result = new ArrayList<>();
        objecten.values().stream().forEach(
                map -> map.values().stream().filter(object -> object.getElement().equals(objectElement)).filter(object -> !object.hasParent()).forEach(
                        result::add));
        return result;
    }

    /**
     * Geef het eerste voorkomen van een specifiek element.
     * @param objectElement element
     * @return eerste voorkomen
     */
    public BlobViewObject getObject(final Element objectElement) {
        Optional<BlobViewObject> optional = getObjecten(objectElement).stream().findFirst();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    private BlobViewObject getBlobViewObject(
            final Integer objectElementId,
            final Long objectSleutel,
            final Integer parentObjectElementId,
            final Long parentObjectSleutel) {
        final Element object = Element.parseId(objectElementId);
        if (!objecten.containsKey(object)) {
            objecten.put(object, new HashMap<>());
        }

        if (!objecten.get(object).containsKey(objectSleutel)) {
            objecten.get(object)
                    .put(objectSleutel, new BlobViewObject(object, objectSleutel, Element.parseId(parentObjectElementId), parentObjectSleutel));
        }

        return objecten.get(object).get(objectSleutel);
    }

    private void mapRecord(final BlobRecord record) {
        final BlobViewObject blobViewObject =
                getBlobViewObject(
                        record.getObjectElementId(),
                        record.getObjectSleutel(),
                        record.getParentObjectElementId(),
                        record.getParentObjectSleutel());
        final Element groepElement = Element.parseId(record.getGroepElementId());
        final BlobViewGroep blobViewGroep = blobViewObject.getGroep(groepElement);
        blobViewGroep.addRecord(new BlobViewRecord(record, this::getActie));
    }

    private BlobViewObject getActie(final Long id) {
        if (id == null) {
            return null;
        }

        return objecten.get(Element.ACTIE).get(id);
    }

}
