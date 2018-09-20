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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 * <p/>
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is anderzijds. De koppeling van een
 * Persoon en een Relatie gebeurt via Betrokkenheid. Zie ook overkoepelend verhaal.
 */
@Table(schema = "Kern", name = "Betr")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Rol", discriminatorType = DiscriminatorType.INTEGER)
@Entity
public abstract class BetrokkenheidModel extends AbstractBetrokkenheidModel implements Betrokkenheid,
    Comparable<BetrokkenheidModel>
{

    private static final int HASHCODE_GETAL1 = 13;
    private static final int HASHCODE_GETAL2 = 17;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected BetrokkenheidModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Betrokkenheid.
     * @param rol     rol van Betrokkenheid.
     * @param persoon persoon van Betrokkenheid.
     */
    public BetrokkenheidModel(final RelatieModel relatie, final SoortBetrokkenheidAttribuut rol, final PersoonModel persoon) {
        super(relatie, rol, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param betrokkenheid Te kopieren object type.
     * @param relatie       Bijbehorende Relatie.
     * @param persoon       Bijbehorende Persoon.
     */
    public BetrokkenheidModel(final Betrokkenheid betrokkenheid, final RelatieModel relatie, final PersoonModel persoon) {
        super(betrokkenheid, relatie, persoon);
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param persoon PersoonModel.
     */
    protected void setPersoon(final PersoonModel persoon) {
    }

    @Override
    public final int compareTo(final BetrokkenheidModel betrokkenheid) {
        if (betrokkenheid == null) {
            return -1;
        } else {
            return new CompareToBuilder().append(this.getRol(), betrokkenheid.getRol())
                .append(this.getPersoon(), betrokkenheid.getPersoon())
                .append(this.getRelatie(), betrokkenheid.getRelatie()).append(this.getID(), betrokkenheid.getID())
                .toComparison();
        }
    }

    @Override
    public final boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final BetrokkenheidModel betrokkenheid = (BetrokkenheidModel) obj;
            isGelijk =
                new EqualsBuilder().append(this.getRol(), betrokkenheid.getRol())
                    .append(this.getPersoon(), betrokkenheid.getPersoon())
                    .append(this.getRelatie(), betrokkenheid.getRelatie())
                    .append(this.getID(), betrokkenheid.getID()).isEquals();
        }
        return isGelijk;
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2).append(this.getRol()).append(this.getPersoon())
            .append(this.getRelatie()).append(this.getID()).hashCode();
    }

    /**
     * Geeft de objectsleutel van deze betrokkenheid.
     * @return de objectsleutel
     */
    public final String getObjectSleutel() {
        if (null == getID()) {
            return "X";
        } else {
            return getID().toString();
        }
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param objectSleutel de objectSleutel
     */
    protected void setObjectSleutel(final String objectSleutel) {
    }

}
