/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.attribuuttype.BerichtData;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.objecttype.logisch.basis.BerichtBasis;
import nl.bzk.brp.model.objecttype.operationeel.BerichtModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Richting;


/**
 * Een bericht voor berichtarchivering.
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBerichtModel extends AbstractDynamischObjectType implements BerichtBasis {

    @Id
    @SequenceGenerator(name = "BERICHT", sequenceName = "Ber.seq_Ber")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BERICHT")
    private Long         id;

    @NotNull
    @AttributeOverride(name = "waarde", column = @Column(name = "data"))
    private BerichtData  data;

    @AttributeOverride(name = "waarde", column = @Column(name = "TsOntv"))
    private DatumTijd    datumTijdOntvangst;

    @AttributeOverride(name = "waarde", column = @Column(name = "TsVerzenden"))
    private DatumTijd    datumTijdVerzenden;

    @ManyToOne
    @JoinColumn(name = "AntwoordOp")
    private BerichtModel antwoordOp;

    @NotNull
    @Column(name = "richting")
    @Enumerated
    private Richting     richting;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractBerichtModel() {
        super();
    }

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param richting Richting
     * @param data BerichtData
     */
    protected AbstractBerichtModel(final Richting richting, final BerichtData data) {
        this.richting = richting;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    @Override
    public BerichtData getData() {
        return data;
    }

    @Override
    public DatumTijd getDatumTijdOntvangst() {
        return datumTijdOntvangst;
    }

    @Override
    public DatumTijd getDatumTijdVerzenden() {
        return datumTijdVerzenden;
    }

    @Override
    public BerichtModel getAntwoordOp() {
        return antwoordOp;
    }

    @Override
    public Richting getRichting() {
        return richting;
    }

    public void setData(final BerichtData data) {
        this.data = data;
    }

    public void setDatumTijdOntvangst(final DatumTijd datumTijdOntvangst) {
        this.datumTijdOntvangst = datumTijdOntvangst;
    }

    public void setDatumTijdVerzenden(final DatumTijd datumTijdVerzenden) {
        this.datumTijdVerzenden = datumTijdVerzenden;
    }

    public void setAntwoordOp(final BerichtModel antwoordOp) {
        this.antwoordOp = antwoordOp;
    }
}
