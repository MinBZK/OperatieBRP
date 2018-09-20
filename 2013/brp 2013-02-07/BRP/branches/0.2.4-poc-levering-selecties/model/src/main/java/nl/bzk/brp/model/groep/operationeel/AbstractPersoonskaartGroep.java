/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonskaartGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor groep persoonskaart.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonskaartGroep extends AbstractGroep implements
        PersoonskaartGroepBasis
{
    @ManyToOne
    @JoinColumn(name = "gempk")
    @Fetch(FetchMode.JOIN)
    private Partij gemeentePersoonskaart;

    @Column(name = "indpkvollediggeconv")
    @Type(type = "JaNee")
    private JaNee indicatiePersoonskaartVolledigGeconverteerd;

    /**
     * Copy constructor voor groep.
     *
     * @param groep De te kopieren groep.
     */
    protected AbstractPersoonskaartGroep(final PersoonskaartGroepBasis groep) {
        super(groep);
        gemeentePersoonskaart = groep.getGemeentePersoonskaart();
        indicatiePersoonskaartVolledigGeconverteerd = groep.getIndicatiePersoonskaartVolledigGeconverteerd();
    }

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonskaartGroep() {
        super();
    }


    @Override
    public Partij getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    @Override
    public JaNee getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }
}
