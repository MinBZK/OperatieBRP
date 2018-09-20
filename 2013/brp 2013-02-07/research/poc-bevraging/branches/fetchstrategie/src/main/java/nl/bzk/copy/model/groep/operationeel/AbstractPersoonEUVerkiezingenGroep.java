/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonEUVerkiezingenGroepBasis;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor groep persoon EU Verkiezingen.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonEUVerkiezingenGroep extends AbstractGroep implements
        PersoonEUVerkiezingenGroepBasis
{
    @Column(name = "inddeelneuverkiezingen")
    @Type(type = "JaNee")
    private JaNee indicatieDeelnameEUVerkiezingen;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanlaanpdeelneuverkiezing"))
    private Datum datumAanleidingAanpassingDeelnameEUVerkiezing;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindeuitsleukiesr"))
    private Datum datumEindeUitsluitingEUKiesrecht;

    /**
     * Copy constructor voor groep.
     *
     * @param groep De te kopieren groep.
     */
    protected AbstractPersoonEUVerkiezingenGroep(final PersoonEUVerkiezingenGroepBasis groep) {
        super(groep);
        indicatieDeelnameEUVerkiezingen = groep.getIndicatieDeelnameEUVerkiezingen();
        datumAanleidingAanpassingDeelnameEUVerkiezing = groep.getDatumAanleidingAanpassingDeelnameEUVerkiezing();
        datumEindeUitsluitingEUKiesrecht = groep.getDatumEindeUitsluitingEUKiesrecht();
    }

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonEUVerkiezingenGroep() {
        super();
    }

    @Override
    public JaNee getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    @Override
    public Datum getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
        return datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    @Override
    public Datum getDatumEindeUitsluitingEUKiesrecht() {
        return datumEindeUitsluitingEUKiesrecht;
    }

    public void setIndicatieDeelnameEUVerkiezingen(final JaNee indicatieDeelnameEUVerkiezingen) {
        this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
    }

    public void setDatumAanleidingAanpassingDeelnameEUVerkiezing(
            final Datum datumAanleidingAanpassingDeelnameEUVerkiezing)
    {
        this.datumAanleidingAanpassingDeelnameEUVerkiezing = datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    public void setDatumEindeUitsluitingEUKiesrecht(final Datum datumEindeUitsluitingEUKiesrecht) {
        this.datumEindeUitsluitingEUKiesrecht = datumEindeUitsluitingEUKiesrecht;
    }
}
