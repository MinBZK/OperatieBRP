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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.*;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisDienst extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer>,
        BestaansperiodeFormeelImplicietMaterieelAutaut
{

    @Id
    @SequenceGenerator(name = "HIS_DIENST", sequenceName = "AutAut.seq_His_Dienst")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DIENST")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Dienst")
    private Dienst dienst;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    @JsonProperty
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisDienst() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param dienst dienst van HisDienst
     * @param datumIngang datumIngang van HisDienst
     * @param datumEinde datumEinde van HisDienst
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisDienst
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisDienst(
        final Dienst dienst,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        this.dienst = dienst;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisDienst(final AbstractHisDienst kopie) {
        super(kopie);
        dienst = kopie.getDienst();
        datumIngang = kopie.getDatumIngang();
        datumEinde = kopie.getDatumEinde();
        indicatieGeblokkeerd = kopie.getIndicatieGeblokkeerd();

    }

    /**
     * Retourneert ID van His Dienst.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienst van His Dienst.
     *
     * @return Dienst.
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Retourneert Datum ingang van His Dienst.
     *
     * @return Datum ingang.
     */
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Dienst.
     *
     * @return Datum einde.
     */
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Geblokkeerd? van His Dienst.
     *
     * @return Geblokkeerd?.
     */
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
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
        if (indicatieGeblokkeerd != null) {
            attributen.add(indicatieGeblokkeerd);
        }
        return attributen;
    }

}
