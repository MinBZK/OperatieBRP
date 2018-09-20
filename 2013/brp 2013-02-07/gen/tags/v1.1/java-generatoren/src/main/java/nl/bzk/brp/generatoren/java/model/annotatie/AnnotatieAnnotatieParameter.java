/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model.annotatie;

import java.util.List;

import nl.bzk.brp.generatoren.java.model.JavaType;

/**
 * Annotaties in Java kunnen als parameter een andere annotatie hebben. Deze klasse is de representatie van zo'n
 * parameter. Voorbeeld: @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeGel"))
 */
public class AnnotatieAnnotatieParameter implements AnnotatieParameter, Cloneable {

    private static final int PRIEM_GETAL_VOOR_HASHCODE = 31;

    private String naam;
    private JavaAnnotatie annotatie;

    /**
     * Constructor.
     * @param naam Naam van de annotatie parameter.
     * @param annotatie De annotatie die de parameter is van een annotatie.
     */
    public AnnotatieAnnotatieParameter(final String naam, final JavaAnnotatie annotatie) {
        this.naam = naam;
        this.annotatie = annotatie;
    }

    public final String getNaam() {
        return naam;
    }

    public final JavaAnnotatie getWaarde() {
        return annotatie;
    }

    @Override
    public List<JavaType> getGebruikteTypes() {
        return this.annotatie.getGebruikteTypes();
    }

    @Override
    public final String getParameterString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(naam).append(" = ");
        sb.append("@").append(annotatie.getType().getNaam());
        sb.append("(");
        for (AnnotatieParameter annotatieParameter : annotatie.getParameters()) {
            sb.append(annotatieParameter.getParameterString());
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        final AnnotatieAnnotatieParameter clone = (AnnotatieAnnotatieParameter) super.clone();
        clone.naam = naam;
        clone.annotatie = (JavaAnnotatie) annotatie.clone();
        return clone;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnotatieAnnotatieParameter)) {
            return false;
        }

        final AnnotatieAnnotatieParameter that = (AnnotatieAnnotatieParameter) o;

        return annotatie.equals(that.annotatie) && naam.equals(that.naam);

    }

    @Override
    public final int hashCode() {
        int result = naam.hashCode();
        result = PRIEM_GETAL_VOOR_HASHCODE * result + annotatie.hashCode();
        return result;
    }
}
