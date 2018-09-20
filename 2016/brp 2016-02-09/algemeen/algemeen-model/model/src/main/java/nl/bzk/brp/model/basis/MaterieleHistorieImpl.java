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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;


/**
 * Deze class bevat velden voor Materiele en Formele Historie.
 */
@Embeddable
@Access(AccessType.FIELD)
public class MaterieleHistorieImpl extends FormeleHistorieImpl implements MaterieleHistorieModel {

    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dataanvgel"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dateindegel"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Default constructor.
     */
    public MaterieleHistorieImpl() {

    }

    /**
     * Constructor voor enkel dag/deg gegevens. Geschikt voor gebruik van deze klasse als historie DTO.
     *
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     * @param datumEindeGeldigheid   de datum einde geldigheid
     */
    public MaterieleHistorieImpl(final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super();
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
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
    }

    @Override
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    @Override
    public final void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut datum) {
        datumAanvangGeldigheid = datum;

    }

    @Override
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public final void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut datum) {
        datumEindeGeldigheid = datum;
    }

    @Override
    public final MaterieleHistorieModel kopieer() {
        return new MaterieleHistorieImpl(this);
    }

}
