/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.operationeel.kern.ActieModel;


/** Deze class bevat velden voor Formele Historie. */
@MappedSuperclass
@Embeddable
@Access(AccessType.FIELD)
public class FormeleHistorieImpl implements FormeleHistorie {

    @AttributeOverride(name = "waarde", column = @Column(name = "tsreg"))
    @JsonProperty
    private DatumTijd datumTijdRegistratie;

    @AttributeOverride(name = "waarde", column = @Column(name = "tsverval"))
    @JsonProperty
    private DatumTijd datumTijdVerval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actieinh")
    private ActieModel actieInhoud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actieverval")
    private ActieModel actieVerval;

    /** Constructor. */
    public FormeleHistorieImpl() {

    }

    /**
     * Copy constructor.
     *
     * @param historie FormeleHistorieImpl
     */
    public FormeleHistorieImpl(final FormeleHistorieImpl historie) {
        datumTijdRegistratie = historie.getDatumTijdRegistratie();
        datumTijdVerval = historie.getDatumTijdVerval();
        actieInhoud = historie.getActieInhoud();
        actieVerval = historie.getActieVerval();
    }

    @Override
    public DatumTijd getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    @Override
    public void setDatumTijdRegistratie(final DatumTijd datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    @Override
    public DatumTijd getDatumTijdVerval() {
        return datumTijdVerval;
    }

    @Override
    public void setDatumTijdVerval(final DatumTijd datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

    @Override
    public ActieModel getActieInhoud() {
        return actieInhoud;
    }

    @Override
    public void setActieInhoud(final ActieModel actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    @Override
    public ActieModel getActieVerval() {
        return actieVerval;
    }

    @Override
    public void setActieVerval(final ActieModel actieVerval) {
        this.actieVerval = actieVerval;
    }
}
