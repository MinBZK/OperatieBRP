/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;

import javax.persistence.*;


/**
 *
 */
@Embeddable
@Access(AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractMaterieelHistorischMetDienstVerantwoording extends AbstractGerelateerdIdentificeerbaar implements
    MaterieelVerantwoordbaar<Dienst>, MaterieelHistorisch, MaterieleHistorie
{

    @Embedded
    private MaterieleHistorieImpl materieleHistorie = new MaterieleHistorieImpl();

    @Transient
    private Verwerkingssoort verwerkingssoort;

    @JsonProperty
    @Transient
    private Dienst dienstInhoud;

    @JsonProperty
    @Transient
    private Dienst dienstVerval;

    @JsonProperty
    @Transient
    private Dienst dienstAanpassingGeldigheid;

    /**
     * Copy constructor.
     *
     * @param kopie de kopie
     */
    protected AbstractMaterieelHistorischMetDienstVerantwoording(
        final AbstractMaterieelHistorischMetDienstVerantwoording kopie)
    {
        materieleHistorie = new MaterieleHistorieImpl((MaterieleHistorieImpl) kopie.getMaterieleHistorie());
        dienstInhoud = kopie.getVerantwoordingInhoud();
        dienstVerval = kopie.getVerantwoordingVerval();
        dienstAanpassingGeldigheid = kopie.getVerantwoordingAanpassingGeldigheid();
    }

    /**
     * Default constructor.
     */
    protected AbstractMaterieelHistorischMetDienstVerantwoording() {

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

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstaanpgel")
    @Override
    public Dienst getVerantwoordingAanpassingGeldigheid() {
        return dienstAanpassingGeldigheid;
    }

    @Override
    public void setVerantwoordingAanpassingGeldigheid(final Dienst verantwoodingAanpassingGeldigheid) {
        this.dienstAanpassingGeldigheid = verantwoodingAanpassingGeldigheid;
    }

    @Override
    @JsonProperty
    public MaterieleHistorieModel getMaterieleHistorie() {
        return materieleHistorie;
    }

    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return this.materieleHistorie.getTijdstipRegistratie();
    }

    @Override
    public DatumTijdAttribuut getDatumTijdVerval() {
        return this.materieleHistorie.getDatumTijdVerval();
    }

    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return this.materieleHistorie.getDatumAanvangGeldigheid();
    }

    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return this.materieleHistorie.getDatumEindeGeldigheid();
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
