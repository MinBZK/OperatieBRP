/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;

/**
 *
 */
@MappedSuperclass
@Embeddable
@Access(AccessType.FIELD)
public abstract class AbstractFormeelHistorischMetDienstVerantwoording extends AbstractGerelateerdIdentificeerbaar implements
    FormeelVerantwoordbaar<Dienst>, FormeelHistorisch, FormeleHistorie
{

    @Embedded
    private FormeleHistorieImpl formeleHistorie = new FormeleHistorieImpl();

    @Transient
    private Verwerkingssoort verwerkingssoort;

    @JsonProperty
    @Transient
    private Dienst dienstInhoud;

    @JsonProperty
    @Transient
    private Dienst dienstVerval;

    /**
     * Copy constructor.
     *
     * @param kopie de kopie
     */
    protected AbstractFormeelHistorischMetDienstVerantwoording(
        final AbstractFormeelHistorischMetDienstVerantwoording kopie)
    {
        formeleHistorie = new FormeleHistorieImpl((FormeleHistorieImpl) kopie.getFormeleHistorie());
        dienstInhoud = kopie.getVerantwoordingInhoud();
        dienstVerval = kopie.getVerantwoordingVerval();
    }

    /**
     * Default constructor.
     */
    protected AbstractFormeelHistorischMetDienstVerantwoording() {

    }

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstinh")
    @Override
    public Dienst getVerantwoordingInhoud() {
        return dienstInhoud;
    }

    @Override
    public void setVerantwoordingInhoud(final Dienst verantwoodingInhoud) {
        this.dienstInhoud = verantwoodingInhoud;
    }

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstverval")
    @Override
    public Dienst getVerantwoordingVerval() {
        return dienstVerval;
    }

    @Override
    public void setVerantwoordingVerval(final Dienst verantwoodingVerval) {
        this.dienstVerval = verantwoodingVerval;
    }

    @Override
    @JsonProperty
    public FormeleHistorieModel getFormeleHistorie() {
        return formeleHistorie;
    }

    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return this.formeleHistorie.getTijdstipRegistratie();
    }

    @Override
    public DatumTijdAttribuut getDatumTijdVerval() {
        return this.formeleHistorie.getDatumTijdVerval();
    }

    @Override
    public Verwerkingssoort getVerwerkingssoort() {
        return verwerkingssoort;
    }

    @Override
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {
        this.verwerkingssoort = verwerkingsSoort;
    }

    @Override
    public boolean isMagGeleverdWorden() {
        return false;
    }
}
