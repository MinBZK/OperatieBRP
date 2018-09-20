/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model.annotatie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.java.model.JavaType;

/**
 * Deze klasse is een representatie van een annotatie parameter.
 * Voorbeeld: @Column(name = "Srt"), name is hier een annotatie parameter. Indien
 * er geen parameterwaarde wordt meegegeven dan wordt de value gevuld.
 * Voorbeeld: @Suppresswarnings("EenWaarde")
 */
public class AnnotatieWaardeParameter implements AnnotatieParameter, Cloneable {

    private static final int PRIEM_GETAL_VOOR_HASHCODE = 31;

    private String naam;
    private String waarde;
    // Het eventuele enum type. Wordt opgeslagen voor latere imports.
    private JavaType enumType;

    /**
     * Boolean die aangeeft of de waarde van de annotatie parameter een
     * constante is zoals in: @OneToMany(fetch = FetchType.LAZY).
     */
    private boolean waardeIsConstante = false;

    /**
     * Constructor.
     *
     * @param naam Naam van de annotatie parameter.
     * @param waarde Waarde van de annotatie parameter.
     */
    public AnnotatieWaardeParameter(final String naam, final String waarde) {
        this(naam, waarde, false);
    }

    /**
     * Gebruik deze constructor als er sprake is van een enum waarde in de annotatie
     * parameter. Zo kan dat type bij de imports getrokken worden.
     *
     * @param naam Naam van de annotatie parameter.
     * @param enumType het enum type van de constante
     * @param enumWaarde de enum waarde van de constante
     */
    public AnnotatieWaardeParameter(final String naam, final JavaType enumType, final String enumWaarde) {
        this(naam, enumType.getNaam() + "." + enumWaarde, true);
        this.enumType = enumType;
    }

    /**
     * Constructor.
     *
     * @param naam Naam van de annotatie parameter.
     * @param waarde Waarde van de annotatie parameter.
     * @param waardeIsConstante Boolean die aangeeft of de waarde van de parameter een constante is.
     */
    public AnnotatieWaardeParameter(final String naam, final String waarde, final boolean waardeIsConstante) {
        this.naam = naam;
        this.waarde = waarde;
        this.waardeIsConstante = waardeIsConstante;
    }

    public final String getNaam() {
        return naam;
    }

    public final String getWaarde() {
        return waarde;
    }

    public final boolean isWaardeIsConstante() {
        return waardeIsConstante;
    }

    @Override
    public List<JavaType> getGebruikteTypes() {
        List<JavaType> gebruikteTypes = new ArrayList<>();
        if (this.enumType != null) {
            gebruikteTypes.add(this.enumType);
        }
        return gebruikteTypes;
    }

    @Override
    public final String getParameterString() {
        final StringBuilder sb = new StringBuilder();
        if (naam != null) {
            sb.append(naam).append(" = ");
        }
        if (!waardeIsConstante) {
            sb.append("\"");
        }
        sb.append(waarde);
        if (!waardeIsConstante) {
            sb.append("\"");
        }
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        final AnnotatieWaardeParameter clone = (AnnotatieWaardeParameter) super.clone();
        clone.naam = naam;
        clone.waarde = waarde;
        return clone;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnotatieWaardeParameter)) {
            return false;
        }

        final AnnotatieWaardeParameter that = (AnnotatieWaardeParameter) o;

        return waardeIsConstante == that.waardeIsConstante
               && naam.equals(that.naam)
               && waarde.equals(that.waarde);
    }

    @Override
    public final int hashCode() {
        int result = naam.hashCode();
        result = PRIEM_GETAL_VOOR_HASHCODE * result + waarde.hashCode();
        if (waardeIsConstante) {
            result = PRIEM_GETAL_VOOR_HASHCODE * result + 1;
        } else {
            result = PRIEM_GETAL_VOOR_HASHCODE * result;
        }
        return result;
    }
}
