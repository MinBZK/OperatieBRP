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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
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
public abstract class AbstractHisDienstbundelGroep extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer> {

    @Id
    @SequenceGenerator(name = "HIS_DIENSTBUNDELGROEP", sequenceName = "AutAut.seq_His_DienstbundelGroep")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DIENSTBUNDELGROEP")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "DienstbundelGroep")
    private DienstbundelGroep dienstbundelGroep;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndFormeleHistorie"))
    @JsonProperty
    private JaNeeAttribuut indicatieFormeleHistorie;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndMaterieleHistorie"))
    @JsonProperty
    private JaNeeAttribuut indicatieMaterieleHistorie;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndVerantwoording"))
    @JsonProperty
    private JaNeeAttribuut indicatieVerantwoording;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisDienstbundelGroep() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param dienstbundelGroep dienstbundelGroep van HisDienstbundelGroep
     * @param indicatieFormeleHistorie indicatieFormeleHistorie van HisDienstbundelGroep
     * @param indicatieMaterieleHistorie indicatieMaterieleHistorie van HisDienstbundelGroep
     * @param indicatieVerantwoording indicatieVerantwoording van HisDienstbundelGroep
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisDienstbundelGroep(
        final DienstbundelGroep dienstbundelGroep,
        final JaNeeAttribuut indicatieFormeleHistorie,
        final JaNeeAttribuut indicatieMaterieleHistorie,
        final JaNeeAttribuut indicatieVerantwoording,
        final ActieModel actieInhoud)
    {
        this.dienstbundelGroep = dienstbundelGroep;
        this.indicatieFormeleHistorie = indicatieFormeleHistorie;
        this.indicatieMaterieleHistorie = indicatieMaterieleHistorie;
        this.indicatieVerantwoording = indicatieVerantwoording;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisDienstbundelGroep(final AbstractHisDienstbundelGroep kopie) {
        super(kopie);
        dienstbundelGroep = kopie.getDienstbundelGroep();
        indicatieFormeleHistorie = kopie.getIndicatieFormeleHistorie();
        indicatieMaterieleHistorie = kopie.getIndicatieMaterieleHistorie();
        indicatieVerantwoording = kopie.getIndicatieVerantwoording();

    }

    /**
     * Retourneert ID van His Dienstbundel \ Groep.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel \ Groep van His Dienstbundel \ Groep.
     *
     * @return Dienstbundel \ Groep.
     */
    public DienstbundelGroep getDienstbundelGroep() {
        return dienstbundelGroep;
    }

    /**
     * Retourneert Formele historie? van His Dienstbundel \ Groep.
     *
     * @return Formele historie?.
     */
    public JaNeeAttribuut getIndicatieFormeleHistorie() {
        return indicatieFormeleHistorie;
    }

    /**
     * Retourneert Materiële historie? van His Dienstbundel \ Groep.
     *
     * @return Materiële historie?.
     */
    public JaNeeAttribuut getIndicatieMaterieleHistorie() {
        return indicatieMaterieleHistorie;
    }

    /**
     * Retourneert Verantwoording? van His Dienstbundel \ Groep.
     *
     * @return Verantwoording?.
     */
    public JaNeeAttribuut getIndicatieVerantwoording() {
        return indicatieVerantwoording;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieFormeleHistorie != null) {
            attributen.add(indicatieFormeleHistorie);
        }
        if (indicatieMaterieleHistorie != null) {
            attributen.add(indicatieMaterieleHistorie);
        }
        if (indicatieVerantwoording != null) {
            attributen.add(indicatieVerantwoording);
        }
        return attributen;
    }

}
