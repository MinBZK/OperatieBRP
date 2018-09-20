/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import static java.beans.Introspector.decapitalize;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.BedrijfsRegel;
import nl.bzk.brp.ecore.bmr.SoortBedrijfsRegel;


public class BeanProperty extends DomeinModelElement {

    /**
     * Maak van een lijst van {@link BeanProperty}'s een String met de parameter definies, gescheiden door komma's.
     *
     * @param properties de properties waarvan een string gemaakt wordt.
     * @return de string met de parameter definities.
     */
    public static String alsParameterLijst(final Collection<BeanProperty> properties) {
        String resultaat = Joiner.on(", ").join(Collections2.transform(properties, new Function<BeanProperty, String>()
        {

            @Override
            public String apply(final BeanProperty property) {
                return "final " + property.getTypeNaam() + " " + property.getNaam();
            }
        }));
        return resultaat;
    }

    private AbstractType type;
    private boolean      collectie = false;
    private AbstractType containingType;
    private Attribuut    attribuut;
    private BeanProperty opposite;

    /**
     * Constructor voor gedefiniëerde enkelvoudige properties. Ze komen uit het bronmodel en hebben het
     * {@link Attribuut} daarvan als argument.
     *
     * @param attribuut Het attribuut uit het bronmodel.
     */
    public BeanProperty(final Attribuut attribuut, final AbstractType type) {
        super(attribuut.getIdentifierCode());
        this.attribuut = attribuut;
        this.type = type;
    }

    /**
     * Constructor voor extra collection properties.
     *
     * @param type Het datatype van het nieuwe property.
     */
    public BeanProperty(final Interface type, final BeanProperty opposite) {
        super(type.getMeervoudsNaam());
        this.type = type;
        this.collectie = true;
        this.opposite = opposite;
        opposite.setOpposite(this);
    }

    /**
     * Constructor voor extra enkelvoudige properties.
     *
     * @param naam De naam van het nieuwe property.
     * @param type Het datatype van het nieuwe property.
     */
    public BeanProperty(final String naam, final AbstractType type) {
        super(naam);
        this.type = type;
    }

    public AbstractType getContainingType() {
        return containingType;
    }

    public String getDatabaseNaam() {
        if (attribuut == null) {
            return null;
        }
        return attribuut.getIdentifierDB();
    }

    /**
     * De naam van de "getter" voor dit property.
     *
     * @return De naam van de "getter" voor dit property.
     */
    public String getGetter() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("get").append(super.getNaam());
        return resultaat.toString();
    }

    /**
     * De naam van de "adder" voor dit property. Alleen van toepassing op collectie properties.
     *
     * @return De naam van de "adder" voor dit property.
     */
    public String getAdder() {
        if (!collectie) {
            throw new UnsupportedOperationException("Alleen collectie properties hebben een 'adder'");
        }
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("add").append(getType().getNaam());
        return resultaat.toString();
    }

    /**
     * De naam van de "remover" voor dit property. Alleen van toepassing op collectie properties.
     *
     * @return De naam van de "remover" voor dit property.
     */
    public String getRemover() {
        if (!collectie) {
            throw new UnsupportedOperationException("Alleen collectie properties hebben een 'remover'");
        }
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("remove").append(getType().getNaam());
        return resultaat.toString();
    }

    /**
     * De naam van dit property, zoals het in Java classes gebruikt wordt.
     *
     * @return De naam van dit property, zoals het in Java classes gebruikt wordt.
     */
    @Override
    public String getNaam() {
        String naam = super.getNaam();
        return decapitalize(isIdentifier() ? naam.toLowerCase() : naam);
    }

    public BeanProperty getOpposite() {
        return opposite;
    }

    /**
     * De naam van de "setter" voor dit property. Collectie properties hebben geen setter.
     *
     * @return De naam van de "setter" voor dit property.
     */
    public String getSetter() {
        if (collectie) {
            throw new UnsupportedOperationException("Alleen collectie properties hebben een 'remover'");
        }
        StringBuilder resultaat = new StringBuilder();
        resultaat.append("set").append(super.getNaam());
        return resultaat.toString();
    }

    public AbstractType getType() {
        return type;
    }

    /**
     * De naam van het Java type van deze property. Voor collection attributen is dit de naam van het type van het
     * collectie element.
     *
     * @return De naam van het Java type van deze property.
     */
    public String getTypeNaam() {
        StringBuilder naam = new StringBuilder(getType().getNaam());
        if (collectie) {
            naam.insert(0, "List<");
            naam.append(">");
        }
        return naam.toString();
    }

    /**
     * De naam van het package van het Java type van deze property. Voor collection attributen is dit de naam van het
     * type van het collectie element.
     *
     * @return De naam van het package van het Java type van deze property.
     */
    public String getTypePackage() {
        return getType().getPackageNaam();
    }

    /**
     * Voor enkelvoudige attributen is dit gelijk aan de {@link #getType()}, voor collectie attributen is dit het
     * collectie-type.
     *
     * @return
     */
    public String getTypeSpecificatie() {
        if (collectie) {
            StringBuilder resultaat = new StringBuilder();
            resultaat.append("List<").append(getTypeNaam()).append(">");
            return resultaat.toString();
        } else {
            return getTypeNaam();
        }
    }

    /**
     * Of dit een collectie property is.
     *
     * @return <code>true</code> als dit een collectie property is, en anders <code>false</code>.
     */
    public Boolean isCollectie() {
        return collectie;
    }

    public boolean isIdentifier() {
        if (attribuut == null) {
            return false;
        }
        return Iterables.any(attribuut.getGebruiktInBedrijfsRegels(), new Predicate<BedrijfsRegel>() {

            @Override
            public boolean apply(final BedrijfsRegel bedrijfsRegel) {
                return SoortBedrijfsRegel.ID == bedrijfsRegel.getSoortBedrijfsRegel();
            }
        });
    }

    public boolean isManyToOne() {
        return !isCollectie() && getType() instanceof Interface;
    }

    public boolean isStatusHistorieIndicator() {
        if (attribuut == null) {
            return false;
        }
        return "StatusHistorie".equals(attribuut.getType().getIdentifierCode());
    }

    public boolean isVerplicht() {
        if (attribuut == null) {
            return false;
        }
        return attribuut.getVerplicht();
    }

    public void setCollection(final Boolean collection) {
        this.collectie = collection;
    }

    public void setContainingType(final AbstractType containingType) {
        this.containingType = containingType;
    }

    public void setOpposite(final BeanProperty opposite) {
        this.opposite = opposite;
    }

    public void setType(final AbstractType type) {
        this.type = type;
    }

    public ConstraintDefinitie getConstraintDefinitie() {
        ConstraintDefinitie resultaat = null;
        if (type instanceof StandardJavaType) {
            resultaat = ((StandardJavaType) type).getConstraintDefinitie();
        }
        return resultaat;
    }
}
