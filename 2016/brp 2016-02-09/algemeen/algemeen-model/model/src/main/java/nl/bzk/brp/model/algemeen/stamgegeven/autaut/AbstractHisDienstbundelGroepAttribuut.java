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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
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
public abstract class AbstractHisDienstbundelGroepAttribuut extends AbstractFormeelHistorischMetActieVerantwoording implements
        ModelIdentificeerbaar<Integer>
{

    @Id
    @SequenceGenerator(name = "HIS_DIENSTBUNDELGROEPATTRIBUUT", sequenceName = "AutAut.seq_His_DienstbundelGroepAttr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DIENSTBUNDELGROEPATTRIBUUT")
    @JsonProperty
    private Integer iD;

    //handmatige wijziging
    @ManyToOne
    @JoinColumn(name = "DienstbundelGroepAttr")
    private DienstbundelGroep dienstbundelGroep;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisDienstbundelGroepAttribuut() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param dienstbundelGroepAttribuut dienstbundelGroepAttribuut van HisDienstbundelGroepAttribuut
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisDienstbundelGroepAttribuut(final DienstbundelGroep dienstbundelGroepAttribuut, final ActieModel actieInhoud) {
        this.dienstbundelGroep = dienstbundelGroepAttribuut;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisDienstbundelGroepAttribuut(final AbstractHisDienstbundelGroepAttribuut kopie) {
        super(kopie);
        dienstbundelGroep = kopie.getDienstbundelGroepAttribuut();

    }

    /**
     * Retourneert ID van His Dienstbundel \ Groep \ Attribuut.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel \ Groep \ Attribuut van His Dienstbundel \ Groep \ Attribuut.
     *
     * @return Dienstbundel \ Groep \ Attribuut.
     */
    public DienstbundelGroep getDienstbundelGroepAttribuut() {
        return dienstbundelGroep;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

}
