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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AttenderingscriteriumAttribuut;
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
public abstract class AbstractHisDienstAttendering extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer> {

    @Id
    @SequenceGenerator(name = "HIS_DIENSTATTENDERING", sequenceName = "AutAut.seq_His_DienstAttendering")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DIENSTATTENDERING")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Dienst")
    private Dienst dienst;

    @Embedded
    @AttributeOverride(name = AttenderingscriteriumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Attenderingscriterium"))
    @JsonProperty
    private AttenderingscriteriumAttribuut attenderingscriterium;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisDienstAttendering() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param dienst dienst van HisDienstAttendering
     * @param attenderingscriterium attenderingscriterium van HisDienstAttendering
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisDienstAttendering(final Dienst dienst, final AttenderingscriteriumAttribuut attenderingscriterium, final ActieModel actieInhoud) {
        this.dienst = dienst;
        this.attenderingscriterium = attenderingscriterium;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisDienstAttendering(final AbstractHisDienstAttendering kopie) {
        super(kopie);
        dienst = kopie.getDienst();
        attenderingscriterium = kopie.getAttenderingscriterium();

    }

    /**
     * Retourneert ID van His Dienst Attendering.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienst van His Dienst Attendering.
     *
     * @return Dienst.
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Retourneert Attenderingscriterium van His Dienst Attendering.
     *
     * @return Attenderingscriterium.
     */
    public AttenderingscriteriumAttribuut getAttenderingscriterium() {
        return attenderingscriterium;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (attenderingscriterium != null) {
            attributen.add(attenderingscriterium);
        }
        return attributen;
    }

}
