/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.logisch.kern.Relatie;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * De Relatie tussen personen.
 * <p/>
 * Een Relatie tussen twee of meer Personen is als aparte object opgenomen. Het relatie-object beschrijft om wat voor soort relatie het gaat, en waar en
 * wanneer deze begonnen en/of be�indigd is. Het koppelen van een Persoon aan een Relatie gebeurt via een object van het type Betrokkenheid.
 * <p/>
 * 1. Naast de nu onderkende relatievormen (Huwelijk, geregistreerd partnerschap en familierechtelijkebetrekking) is er lange tijd sprake geweest van nog
 * een aantal binaire relatievormen: erkenning ongeboren vrucht, ontkenning ouderschap en naamskeuze ongeboren vrucht. Deze relatievormen zijn in een laat
 * stadium alsnog geschrapt uit de gegevensset. De gekozen constructie van o.a. Relatie is echter nog steeds gebaseerd op mogelijke toevoegingen. De keuze
 * om NIET terug te komen op de constructie is gebaseerd op enerzijds het late stadium waarin het schrappen van de verschillende relatievormen is
 * doorgevoerd, en anderzijds de mogelijkheid om in de toekomst eventuele nieuwe (binaire) relatievormen eenvoudig te kunnen toevoegen. RvdP, 5 augustus
 * 2011
 */
@Table(schema = "Kern", name = "Relatie")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Srt", discriminatorType = DiscriminatorType.INTEGER)
@Entity
public abstract class RelatieModel extends AbstractRelatieModel implements Relatie, Comparable<RelatieModel> {

    private static final int HASHCODE_GETAL1 = 13;

    private static final int HASHCODE_GETAL2 = 17;

    @Transient
    private String objectSleutel;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected RelatieModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Relatie.
     */
    public RelatieModel(final SoortRelatieAttribuut soort) {
        super(soort);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param relatie Te kopieren object type.
     */
    public RelatieModel(final Relatie relatie) {
        super(relatie);
    }

    /**
     * Geeft de object sleutel terug.
     *
     * @return "brp"[id]
     */
    public final String getObjectSleutel() {
        final String ts;
        if (null != this.objectSleutel) {
            ts = this.objectSleutel;
        } else {
            if (null == getID()) {
                ts = "X";
            } else {
                ts = getID().toString();
            }
        }

        return ts;
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param objectSleutel String.
     */
    public final void setObjectSleutel(final String objectSleutel) {
        this.objectSleutel = objectSleutel;
    }

    @Override
    public final int compareTo(final RelatieModel relatie) {
        if (relatie == null) {
            return -1;
        } else {
            return new CompareToBuilder().append(this.getSoort(), relatie.getSoort())
                .append(this.getID(), relatie.getID()).toComparison();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final RelatieModel relatie = (RelatieModel) obj;
            isGelijk =
                new EqualsBuilder().append(this.getSoort(), relatie.getSoort())
                    .append(this.getID(), relatie.getID())
                    .isEquals();
        }
        return isGelijk;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2).append(this.getSoort()).append(this.getID()).hashCode();
    }

}
