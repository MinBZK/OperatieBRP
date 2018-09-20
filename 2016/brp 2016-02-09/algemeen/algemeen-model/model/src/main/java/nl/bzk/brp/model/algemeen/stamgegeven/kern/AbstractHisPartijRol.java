/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BestaansperiodeFormeelImplicietMaterieelAutaut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPartijRol extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer>,
    BestaansperiodeFormeelImplicietMaterieelAutaut, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PARTIJROL", sequenceName = "Kern.seq_His_PartijRol")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJROL")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "PartijRol")
    private PartijRol partijRol;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumAttribuut datumEinde;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPartijRol() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param partijRol partijRol van HisPartijRol
     * @param datumIngang datumIngang van HisPartijRol
     * @param datumEinde datumEinde van HisPartijRol
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPartijRol(final PartijRol partijRol, final DatumAttribuut datumIngang, final DatumAttribuut datumEinde, final ActieModel actieInhoud)
    {
        this.partijRol = partijRol;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPartijRol(final AbstractHisPartijRol kopie) {
        super(kopie);
        partijRol = kopie.getPartijRol();
        datumIngang = kopie.getDatumIngang();
        datumEinde = kopie.getDatumEinde();

    }

    /**
     * Retourneert ID van His Partij \ Rol.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij \ Rol van His Partij \ Rol.
     *
     * @return Partij \ Rol.
     */
    public PartijRol getPartijRol() {
        return partijRol;
    }

    /**
     * Retourneert Datum ingang van His Partij \ Rol.
     *
     * @return Datum ingang.
     */
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Partij \ Rol.
     *
     * @return Datum einde.
     */
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumIngang != null) {
            attributen.add(datumIngang);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PARTIJROL_STANDAARD;
    }

}
