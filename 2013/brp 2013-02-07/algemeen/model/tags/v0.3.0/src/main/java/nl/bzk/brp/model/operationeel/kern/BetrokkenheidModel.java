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

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractBetrokkenheidModel;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 *
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is
 * anderzijds. De koppeling van een Persoon en een Relatie gebeurt via Betrokkenheid. Zie ook overkoepelend verhaal.
 *
 *
 *
 */
@Table(schema = "Kern", name = "Betr")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Rol", discriminatorType = DiscriminatorType.INTEGER)
@Entity
public abstract class BetrokkenheidModel extends AbstractBetrokkenheidModel
    implements Betrokkenheid, Comparable<BetrokkenheidModel>
{

    private static final int HASHCODE_GETAL1 = 13;
    private static final int HASHCODE_GETAL2 = 17;

    /** Standaard constructor (t.b.v. Hibernate/JPA). */
    protected BetrokkenheidModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Betrokkenheid.
     * @param rol rol van Betrokkenheid.
     * @param persoon persoon van Betrokkenheid.
     */
    public BetrokkenheidModel(final RelatieModel relatie, final SoortBetrokkenheid rol, final PersoonModel persoon) {
        super(relatie, rol, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param betrokkenheid Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public BetrokkenheidModel(final Betrokkenheid betrokkenheid, final RelatieModel relatie, final PersoonModel persoon)
    {
        super(betrokkenheid, relatie, persoon);
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param persoon PersoonModel.
     */
    protected void setPersoon(final PersoonModel persoon) {
    }


    /**
     * Vergelijkt dit object met het opgegeven object voor volgorde. Deze methode retourneert een negatief getal, nul,
     * of een positief getal indien dit object 'kleiner', gelijk of 'groter' dan het opgegeven object is. Hierbij wordt
     * voor {@link nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel} speficiek gekeken naar de waardes voor
     * de soort van de betrokkenheid en de betrokkene zelf.
     *
     * @param betrokkenheid het object dat wordt vergeleken.
     * @return een negatief getal, nul of een positief getal indien dit object 'kleiner', gelijk aan of 'groter' is
     *         dan het opgegeven object.
     */
    @Override
    // TODO: Herimplementatie compare methode; nog onbekend is hoe werkelijk gesorteerd dient te worden.
    // TODO: Mogelijk dient deze methode hier verwijderd te worden en dient sortering op bericht gedefinieerd te worden.
    public int compareTo(final BetrokkenheidModel betrokkenheid) {
        if (betrokkenheid == null) {
            return -1;
        } else {
            return new CompareToBuilder()
                .append(this.getRol(), betrokkenheid.getRol())
                .append(this.getPersoon(), betrokkenheid.getPersoon()).toComparison();
        }
    }

    /** {@inheritDoc} */
    @Override
    // TODO: Herimplementatie equals methode; vanwege afhankelijkheid van onduidelijke rond compare methode.
    public boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            BetrokkenheidModel betrokkenheid = (BetrokkenheidModel) obj;
            isGelijk = new EqualsBuilder()
                .append(this.getRol(), betrokkenheid.getRol())
                .append(this.getPersoon(), betrokkenheid.getPersoon()).isEquals();
        }
        return isGelijk;
    }

    /** {@inheritDoc} */
    @Override
    // TODO: Herimplementatie hashcode methode; vanwege afhankelijkheid van onduidelijke rond compare methode.
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2)
            .append(this.getRol())
            .append(this.getPersoon()).hashCode();
    }

}
