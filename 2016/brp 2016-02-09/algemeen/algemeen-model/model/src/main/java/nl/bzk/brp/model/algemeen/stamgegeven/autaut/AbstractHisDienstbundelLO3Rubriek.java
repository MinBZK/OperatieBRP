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
public abstract class AbstractHisDienstbundelLO3Rubriek extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer> {

    @Id
    @SequenceGenerator(name = "HIS_DIENSTBUNDELLO3RUBRIEK", sequenceName = "AutAut.seq_His_DienstbundelLO3Rubriek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DIENSTBUNDELLO3RUBRIEK")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "DienstbundelLO3Rubriek")
    private DienstbundelLO3Rubriek dienstbundelLO3Rubriek;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisDienstbundelLO3Rubriek() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param dienstbundelLO3Rubriek dienstbundelLO3Rubriek van HisDienstbundelLO3Rubriek
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisDienstbundelLO3Rubriek(final DienstbundelLO3Rubriek dienstbundelLO3Rubriek, final ActieModel actieInhoud) {
        this.dienstbundelLO3Rubriek = dienstbundelLO3Rubriek;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisDienstbundelLO3Rubriek(final AbstractHisDienstbundelLO3Rubriek kopie) {
        super(kopie);
        dienstbundelLO3Rubriek = kopie.getDienstbundelLO3Rubriek();

    }

    /**
     * Retourneert ID van His Dienstbundel \ LO3 Rubriek.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel \ LO3 Rubriek van His Dienstbundel \ LO3 Rubriek.
     *
     * @return Dienstbundel \ LO3 Rubriek.
     */
    public DienstbundelLO3Rubriek getDienstbundelLO3Rubriek() {
        return dienstbundelLO3Rubriek;
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
