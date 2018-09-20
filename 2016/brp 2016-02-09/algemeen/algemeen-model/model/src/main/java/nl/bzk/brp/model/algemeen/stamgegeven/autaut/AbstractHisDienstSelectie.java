/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

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
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.SelectieperiodeInMaandenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisDienstSelectie extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer> {

    @Id
    @SequenceGenerator(name = "HIS_DIENSTSELECTIE", sequenceName = "AutAut.seq_His_DienstSelectie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DIENSTSELECTIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Dienst")
    private Dienst dienst;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "EersteSelectiedat"))
    @JsonProperty
    private DatumAttribuut eersteSelectiedatum;

    @Embedded
    @AttributeOverride(name = SelectieperiodeInMaandenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SelectieperiodeInMaanden"))
    @JsonProperty
    private SelectieperiodeInMaandenAttribuut selectieperiodeInMaanden;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisDienstSelectie() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param dienst dienst van HisDienstSelectie
     * @param eersteSelectiedatum eersteSelectiedatum van HisDienstSelectie
     * @param selectieperiodeInMaanden selectieperiodeInMaanden van HisDienstSelectie
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisDienstSelectie(
        final Dienst dienst,
        final DatumAttribuut eersteSelectiedatum,
        final SelectieperiodeInMaandenAttribuut selectieperiodeInMaanden,
        final ActieModel actieInhoud)
    {
        this.dienst = dienst;
        this.eersteSelectiedatum = eersteSelectiedatum;
        this.selectieperiodeInMaanden = selectieperiodeInMaanden;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisDienstSelectie(final AbstractHisDienstSelectie kopie) {
        super(kopie);
        dienst = kopie.getDienst();
        eersteSelectiedatum = kopie.getEersteSelectiedatum();
        selectieperiodeInMaanden = kopie.getSelectieperiodeInMaanden();

    }

    /**
     * Retourneert ID van His Dienst Selectie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienst van His Dienst Selectie.
     *
     * @return Dienst.
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Retourneert Eerste selectiedatum van His Dienst Selectie.
     *
     * @return Eerste selectiedatum.
     */
    public DatumAttribuut getEersteSelectiedatum() {
        return eersteSelectiedatum;
    }

    /**
     * Retourneert Selectieperiode in maanden van His Dienst Selectie.
     *
     * @return Selectieperiode in maanden.
     */
    public SelectieperiodeInMaandenAttribuut getSelectieperiodeInMaanden() {
        return selectieperiodeInMaanden;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (eersteSelectiedatum != null) {
            attributen.add(eersteSelectiedatum);
        }
        if (selectieperiodeInMaanden != null) {
            attributen.add(selectieperiodeInMaanden);
        }
        return attributen;
    }

}
