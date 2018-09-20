/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.logisch.ber.BerichtBasis;

/**
 * Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 *
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die
 * verzonden zijn. Hierbij geldt dat; Uitsluitend berichten aan en van 'de buitenwereld' gearchiveerd worden. Dus niet
 * de interne berichtenstroom tussen de diverse modules. De definitie van 'verzonden' is; door de BRP op de queue gezet.
 * De verzending aan de afnemer valt hier buiten scope.
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtModel extends AbstractDynamischObject implements BerichtBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = SoortBerichtAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private SoortBerichtAttribuut soort;

    @Embedded
    @AttributeOverride(name = RichtingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Richting"))
    @JsonProperty
    private RichtingAttribuut richting;

    @Embedded
    @JsonProperty
    private BerichtStuurgegevensGroepModel stuurgegevens;

    @Embedded
    @JsonProperty
    private BerichtParametersGroepModel parameters;

    @Embedded
    @JsonProperty
    private BerichtResultaatGroepModel resultaat;

    @Embedded
    @JsonProperty
    private BerichtStandaardGroepModel standaard;

    @Embedded
    @JsonProperty
    private BerichtZoekcriteriaPersoonGroepModel zoekcriteriaPersoon;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Ber")
    @JsonProperty
    private Set<BerichtPersoonModel> personen;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBerichtModel() {
        personen = new HashSet<BerichtPersoonModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Bericht.
     * @param richting richting van Bericht.
     */
    public AbstractBerichtModel(final SoortBerichtAttribuut soort, final RichtingAttribuut richting) {
        this();
        this.soort = soort;
        this.richting = richting;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param bericht Te kopieren object type.
     */
    public AbstractBerichtModel(final Bericht bericht) {
        this();
        this.soort = bericht.getSoort();
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
        if (bericht.getStandaard() != null) {
            this.standaard = new BerichtStandaardGroepModel(bericht.getStandaard());
        }
        if (bericht.getZoekcriteriaPersoon() != null) {
            this.zoekcriteriaPersoon = new BerichtZoekcriteriaPersoonGroepModel(bericht.getZoekcriteriaPersoon());
        }

    }

    /**
     * Retourneert ID van Bericht.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "BERICHT", sequenceName = "Ber.seq_Ber")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BERICHT")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortBerichtAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RichtingAttribuut getRichting() {
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
    public BerichtStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtZoekcriteriaPersoonGroepModel getZoekcriteriaPersoon() {
        return zoekcriteriaPersoon;
    }

    /**
     * Retourneert Bericht \ Personen van Bericht.
     *
     * @return Bericht \ Personen van Bericht.
     */
    public Set<BerichtPersoonModel> getPersonen() {
        return personen;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

    /**
     * Zet Soort van Bericht.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortBerichtAttribuut soort) {
        this.soort = soort;
    }

    /**
     * Zet Richting van Bericht.
     *
     * @param richting Richting.
     */
    public void setRichting(final RichtingAttribuut richting) {
        this.richting = richting;
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

    /**
     * Zet Standaard van Bericht.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final BerichtStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Zoekcriteria persoon van Bericht.
     *
     * @param zoekcriteriaPersoon Zoekcriteria persoon.
     */
    public void setZoekcriteriaPersoon(final BerichtZoekcriteriaPersoonGroepModel zoekcriteriaPersoon) {
        this.zoekcriteriaPersoon = zoekcriteriaPersoon;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (stuurgegevens != null) {
            groepen.add(stuurgegevens);
        }
        if (parameters != null) {
            groepen.add(parameters);
        }
        if (resultaat != null) {
            groepen.add(resultaat);
        }
        if (standaard != null) {
            groepen.add(standaard);
        }
        if (zoekcriteriaPersoon != null) {
            groepen.add(zoekcriteriaPersoon);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soort != null) {
            attributen.add(soort);
        }
        if (richting != null) {
            attributen.add(richting);
        }
        return attributen;
    }

}
