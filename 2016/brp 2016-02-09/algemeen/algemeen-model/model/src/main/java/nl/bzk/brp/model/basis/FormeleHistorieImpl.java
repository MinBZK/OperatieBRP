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
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;


/**
 * Deze class bevat velden voor Formele Historie.
 */
@MappedSuperclass
@Embeddable
@Access(AccessType.FIELD)
public class FormeleHistorieImpl implements FormeleHistorieModel {

    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "tsreg"))
    @JsonProperty
    private DatumTijdAttribuut datumTijdRegistratie;

    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "tsverval"))
    @JsonProperty
    private DatumTijdAttribuut datumTijdVerval;

    /**
     * Constructor.
     */
    public FormeleHistorieImpl() {

    }

    /**
     * Copy constructor.
     *
     * @param historie FormeleHistorieImpl
     */
    public FormeleHistorieImpl(final FormeleHistorieImpl historie) {
        datumTijdRegistratie = historie.getTijdstipRegistratie();
        datumTijdVerval = historie.getDatumTijdVerval();
    }

    @Override
    public final DatumTijdAttribuut getTijdstipRegistratie() {
        return datumTijdRegistratie;
    }

    @Override
    public final void setDatumTijdRegistratie(final DatumTijdAttribuut datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    @Override
    public final DatumTijdAttribuut getDatumTijdVerval() {
        return datumTijdVerval;
    }

    @Override
    public final void setDatumTijdVerval(final DatumTijdAttribuut datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }
}
