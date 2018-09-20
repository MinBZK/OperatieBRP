/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonUitsluitingNLKiesrechtGroepBasis;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor groep persoon uitsluiting kiesrecht.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonUitsluitingNLKiesrechtGroep extends AbstractGroep implements
        PersoonUitsluitingNLKiesrechtGroepBasis
{
    @Column(name = "induitslnlkiesr")
    @Type(type = "JaNee")
    private JaNee indicatieUitsluitingNLKiesrecht;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindeuitslnlkiesr"))
    private Datum datumEindeUitsluitingNLKiesrecht;

    /**
     * Copy constructor voor groep.
     *
     * @param groep De te kopieren groep.
     */
    protected AbstractPersoonUitsluitingNLKiesrechtGroep(final PersoonUitsluitingNLKiesrechtGroepBasis groep) {
        super(groep);
        indicatieUitsluitingNLKiesrecht = groep.getIndicatieUitsluitingNLKiesrecht();
        datumEindeUitsluitingNLKiesrecht = groep.getDatumEindeUitsluitingNLKiesrecht();
    }

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonUitsluitingNLKiesrechtGroep() {
        super();
    }



    @Override
    public JaNee getIndicatieUitsluitingNLKiesrecht() {
        return indicatieUitsluitingNLKiesrecht;
    }

    @Override
    public Datum getDatumEindeUitsluitingNLKiesrecht() {
        return datumEindeUitsluitingNLKiesrecht;
    }
}
