/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.basis;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.objecttype.operationeel.ActieModel;


/**
 * Deze class bevat velden voor Materiele en Formele Historie.
 */
@Embeddable
@Access(AccessType.FIELD)
public class MaterieleHistorieImpl extends nl.bzk.copy.model.basis.FormeleHistorieImpl
        implements nl.bzk.copy.model.basis.MaterieleHistorie
{

    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum datumAanvangGeldigheid;

    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum datumEindeGeldigheid;

    @ManyToOne
    @JoinColumn(name = "actieaanpgel")
    private ActieModel actieAanpassingGeldigheid;

    /**
     * Default constructor.
     */
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
