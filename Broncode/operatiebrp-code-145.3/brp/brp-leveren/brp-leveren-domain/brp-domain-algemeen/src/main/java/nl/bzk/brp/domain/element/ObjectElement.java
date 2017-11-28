/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import java.util.List;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;

/**
 * ObjectElement.
 */
public final class ObjectElement implements ElementObject {

    private List<GroepElement> groepenInObject;
    private ObjectElement typeObjectElement = this;
    private ObjectElement alias;
    private ElementObject typeObject;
    private List<AttribuutElement> sorteerAttributen;
    private GroepElement sorteerGroep;
    private Element elementRecord;

    /**
     * Constructor.
     * @param elementRecord het record met element data
     */
    public ObjectElement(final Element elementRecord) {
        this.elementRecord = elementRecord;
    }

    @Override
    public void postCreate(final ElementLocator locator) {

        if (!(getElement().getElementWaarde().getTypeAlias() == null || getElement().getElementWaarde().getTypeAlias() < 1)) {
            alias = locator.getObject(getElement().getElementWaarde().getTypeAlias());
        }

        if (getType() != null) {
            typeObjectElement = locator.getObject(getType());
        }
        groepenInObject = locator.getGroepenInObject(getId());

        typeObject = getTypeObject(locator);
        sorteerAttributen = locator.getSorteerElementen(typeObjectElement);
        sorteerGroep = locator.getSorteerGroep(typeObjectElement);
    }

    @Override
    public <T extends ElementObject> T getTypeObject() {
        return (T) typeObject;
    }

    @Override
    public Element getElement() {
        return elementRecord;
    }

    /**
     * @return het object alias, of null indien het object geen alias heeft.
     */
    public ObjectElement getAliasVan() {
        return alias;
    }

    /**
     * Geeft indicatie of het objectType het supertype is van dit type object. <br> Bijvoorbeeld, Kind / Partner / Ouder hebben het supertype
     * Betrokkenheid. Voor indirecte supertypes, zoals huwelijkgeregistreerdpartnerschap wordt recursief gecontroleeerd.
     * @param superType het supertype
     * @return indicatie of het objectType het supertype is van dit type object.
     */
    public boolean isVanType(final ObjectElement superType) {
        final ObjectElement type = getTypeObject();
        final boolean rootTypeBereikt = this.equals(type);
        final boolean typeGelijk = superType.equals(type);
        if (!rootTypeBereikt && !typeGelijk && type != null) {
            return type.<ObjectElement>getTypeObject().isVanType(superType);
        }
        return typeGelijk;
    }

    /**
     * Geeft indicatie of het vergelijkObjectElement een alias is van dit type object. <br> GerelateerdeKind is alias van Kind <br>
     * GerelateerdeKind.Persoon is alias van Persoon
     * @param vergelijkObjectElement het vergelijkObjectElement om te vergelijken
     * @return booolean indicatie of het vergelijkObjectElement een alias is van dit type object.
     */
    public boolean isAliasVan(final ObjectElement vergelijkObjectElement) {
        return alias != null && vergelijkObjectElement.getId().equals(alias.getId());
    }

    /**
     * @return het type object element
     */
    public ObjectElement getTypeObjectElement() {
        return typeObjectElement;
    }

    /**
     * @return de groepen van dit object
     */
    public List<GroepElement> getGroepen() {
        return groepenInObject;
    }

    /**
     * @return de sorteer attributen als aanwezig.
     */
    public List<AttribuutElement> getSorteerAttributen() {
        return sorteerAttributen;
    }

    /**
     * @return de groep waarin de sorteer attributen zich bevinden.
     */
    public GroepElement getSorteerGroep() {
        return sorteerGroep;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(elementRecord.getId());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {

            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ObjectElement elem = (ObjectElement) o;
        return this.elementRecord.getId() == elem.getElement().getId();

    }

    @Override
    public String toString() {
        return String.format("%s [%d]", elementRecord.getNaam(), elementRecord.getId());
    }
}
