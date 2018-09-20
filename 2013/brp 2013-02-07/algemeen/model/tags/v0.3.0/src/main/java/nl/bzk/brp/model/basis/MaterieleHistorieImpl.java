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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.operationeel.kern.ActieModel;


/** Deze class bevat velden voor Materiele en Formele Historie. */
@Embeddable
@Access(AccessType.FIELD)
public class MaterieleHistorieImpl extends FormeleHistorieImpl implements MaterieleHistorie {

    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    @JsonProperty
    private Datum datumAanvangGeldigheid;

    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    @JsonProperty
    private Datum datumEindeGeldigheid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actieaanpgel")
    private ActieModel actieAanpassingGeldigheid;

    /** Default constructor. */
    public MaterieleHistorieImpl() {

    }

    /**
     * Copy constructor.
     *
     * @param historie MaterieleHistorieImpl
     */
    public MaterieleHistorieImpl(final MaterieleHistorieImpl historie) {
        super(historie);
        datumAanvangGeldigheid = historie.getDatumAanvangGeldigheid();
        datumEindeGeldigheid = historie.getDatumEindeGeldigheid();
        actieAanpassingGeldigheid = historie.getActieAanpassingGeldigheid();
    }

    @Override
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    @Override
    public void setDatumAanvangGeldigheid(final Datum datum) {
        datumAanvangGeldigheid = datum;

    }

    @Override
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public void setDatumEindeGeldigheid(final Datum datum) {
        datumEindeGeldigheid = datum;
    }

    @Override
    public ActieModel getActieAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    @Override
    public void setActieAanpassingGeldigheid(final ActieModel actie) {
        actieAanpassingGeldigheid = actie;
    }

    @Override
    public MaterieleHistorie kopieer() {
        return new MaterieleHistorieImpl(this);
    }

}
