/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Berichtdata;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.logisch.ber.basis.BerichtBasis;
import nl.bzk.brp.model.operationeel.ber.BerichtMeldingModel;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtParametersGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtResultaatGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * (Toekomstig) Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 *
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die
 * verzonden gaan worden.
 *
 * 1. Soort bericht (weer) verwijderd uit model als eigenschap van Bericht: reden is dat het op het moment van
 * archiveren nog niet bekend zal zijn. RvdP 8 november 2011.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractBerichtModel extends AbstractDynamischObjectType implements BerichtBasis {

    @Id
    @SequenceGenerator(name = "BERICHT", sequenceName = "Ber.seq_Ber")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BERICHT")
    @JsonProperty
    private Long                           iD;

    @Enumerated
    @Column(name = "Srt")
    @JsonProperty
    private SoortBericht                   soort;

    @ManyToOne
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private AdministratieveHandelingModel  administratieveHandeling;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Data"))
    @JsonProperty
    private Berichtdata                    data;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TsOntv"))
    @JsonProperty
    private DatumTijd                      datumTijdOntvangst;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TsVerzenden"))
    @JsonProperty
    private DatumTijd                      datumTijdVerzenden;

    @ManyToOne
    @JoinColumn(name = "AntwoordOp")
    @JsonProperty
    private BerichtModel                   antwoordOp;

    @Enumerated
    @Column(name = "Richting")
    @JsonProperty
    private Richting                       richting;

    @Embedded
    @JsonProperty
    private BerichtStuurgegevensGroepModel stuurgegevens;

    @Embedded
    @JsonProperty
    private BerichtParametersGroepModel    parameters;

    @Embedded
    @JsonProperty
    private BerichtResultaatGroepModel     resultaat;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Ber")
    @JsonProperty
    private Set<BerichtMeldingModel>       meldingen;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractBerichtModel() {
        meldingen = new HashSet<BerichtMeldingModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Bericht.
     * @param administratieveHandeling administratieveHandeling van Bericht.
     * @param data data van Bericht.
     * @param datumTijdOntvangst datumTijdOntvangst van Bericht.
     * @param datumTijdVerzenden datumTijdVerzenden van Bericht.
     * @param antwoordOp antwoordOp van Bericht.
     * @param richting richting van Bericht.
     */
    public AbstractBerichtModel(final SoortBericht soort, final AdministratieveHandelingModel administratieveHandeling,
            final Berichtdata data, final DatumTijd datumTijdOntvangst, final DatumTijd datumTijdVerzenden,
            final BerichtModel antwoordOp, final Richting richting)
    {
        this();
        this.soort = soort;
        this.administratieveHandeling = administratieveHandeling;
        this.data = data;
        this.datumTijdOntvangst = datumTijdOntvangst;
        this.datumTijdVerzenden = datumTijdVerzenden;
        this.antwoordOp = antwoordOp;
        this.richting = richting;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param bericht Te kopieren object type.
     * @param administratieveHandeling Bijbehorende Administratieve handeling.
     * @param antwoordOp Bijbehorende Bericht.
     */
    public AbstractBerichtModel(final Bericht bericht, final AdministratieveHandelingModel administratieveHandeling,
            final BerichtModel antwoordOp)
    {
        this();
        this.soort = bericht.getSoort();
        this.administratieveHandeling = administratieveHandeling;
        this.data = bericht.getData();
        this.datumTijdOntvangst = bericht.getDatumTijdOntvangst();
        this.datumTijdVerzenden = bericht.getDatumTijdVerzenden();
        this.antwoordOp = antwoordOp;
        this.richting = bericht.getRichting();
        if (bericht.getStuurgegevens() != null) {
            this.stuurgegevens = new BerichtStuurgegevensGroepModel(bericht.getStuurgegevens());
        }
        if (bericht.getParameters() != null) {
            this.parameters = new BerichtParametersGroepModel(bericht.getParameters());
        }
        if (bericht.getResultaat() != null) {
            this.resultaat = new BerichtResultaatGroepModel(bericht.getResultaat());
        }

    }

    /**
     * Retourneert ID van Bericht.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Bericht.
     *
     * @return Soort.
     */
    public SoortBericht getSoort() {
        return soort;
    }

    /**
     * Retourneert Administratieve Handeling van Bericht.
     *
     * @return Administratieve Handeling.
     */
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Data van Bericht.
     *
     * @return Data.
     */
    public Berichtdata getData() {
        return data;
    }

    /**
     * Retourneert Datum/tijd ontvangst van Bericht.
     *
     * @return Datum/tijd ontvangst.
     */
    public DatumTijd getDatumTijdOntvangst() {
        return datumTijdOntvangst;
    }

    /**
     * Retourneert Datum/tijd verzenden van Bericht.
     *
     * @return Datum/tijd verzenden.
     */
    public DatumTijd getDatumTijdVerzenden() {
        return datumTijdVerzenden;
    }

    /**
     * Retourneert Antwoord op van Bericht.
     *
     * @return Antwoord op.
     */
    public BerichtModel getAntwoordOp() {
        return antwoordOp;
    }

    /**
     * Retourneert Richting van Bericht.
     *
     * @return Richting.
     */
    public Richting getRichting() {
        return richting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtStuurgegevensGroepModel getStuurgegevens() {
        return stuurgegevens;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtParametersGroepModel getParameters() {
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtResultaatGroepModel getResultaat() {
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BerichtMeldingModel> getMeldingen() {
        return meldingen;
    }

    /**
     * Zet Stuurgegevens van Bericht.
     *
     * @param stuurgegevens Stuurgegevens.
     */
    public void setStuurgegevens(final BerichtStuurgegevensGroepModel stuurgegevens) {
        this.stuurgegevens = stuurgegevens;
    }

    /**
     * Zet Parameters van Bericht.
     *
     * @param parameters Parameters.
     */
    public void setParameters(final BerichtParametersGroepModel parameters) {
        this.parameters = parameters;
    }

    /**
     * Zet Resultaat van Bericht.
     *
     * @param resultaat Resultaat.
     */
    public void setResultaat(final BerichtResultaatGroepModel resultaat) {
        this.resultaat = resultaat;
    }

}
