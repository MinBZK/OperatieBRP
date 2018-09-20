/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 9347)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonVerstrekkingsbeperkingModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONVERSTREKKINGSBEPERKING", sequenceName = "Kern.seq_His_PersVerstrbeperking")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONVERSTREKKINGSBEPERKING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonVerstrekkingsbeperkingHisVolledigImpl.class)
    @JoinColumn(name = "PersVerstrbeperking")
    @JsonBackReference
    private PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonVerstrekkingsbeperkingModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoonVerstrekkingsbeperking persoonVerstrekkingsbeperking van HisPersoonVerstrekkingsbeperkingModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonVerstrekkingsbeperkingModel(
        final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking,
        final ActieModel actieInhoud)
    {
        this.persoonVerstrekkingsbeperking = persoonVerstrekkingsbeperking;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonVerstrekkingsbeperkingModel(final AbstractHisPersoonVerstrekkingsbeperkingModel kopie) {
        super(kopie);
        persoonVerstrekkingsbeperking = kopie.getPersoonVerstrekkingsbeperking();

    }

    /**
     * Retourneert ID van His Persoon \ Verstrekkingsbeperking.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Verstrekkingsbeperking van His Persoon \ Verstrekkingsbeperking.
     *
     * @return Persoon \ Verstrekkingsbeperking.
     */
    public PersoonVerstrekkingsbeperkingHisVolledig getPersoonVerstrekkingsbeperking() {
        return persoonVerstrekkingsbeperking;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT;
    }

}
