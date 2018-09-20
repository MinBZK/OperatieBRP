/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ber.BerichtStandaardGroep;
import nl.bzk.brp.model.logisch.ber.BerichtStandaardGroepBasis;

/**
 * Logischerwijs zou de standaard-groep volgen na de identiteit. Deze is echter verplaatst vanwege problemen in het
 * (Java) generatieproces omdat er bepaalde verwijzigingen niet lekker liepen. Verplaatsen naar onderen loste dit
 * probleem op.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtStandaardGroepModel implements BerichtStandaardGroepBasis {

    @JsonProperty
    @Column(name = "AdmHnd")
    private Long administratieveHandelingId;

    @Embedded
    @AttributeOverride(name = BerichtdataAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Data"))
    @JsonProperty
    private BerichtdataAttribuut data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AntwoordOp")
    @JsonProperty
    private BerichtModel antwoordOp;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBerichtStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param administratieveHandelingId administratieveHandelingId van Standaard.
     * @param data data van Standaard.
     * @param antwoordOp antwoordOp van Standaard.
     */
    public AbstractBerichtStandaardGroepModel(final Long administratieveHandelingId, final BerichtdataAttribuut data, final BerichtModel antwoordOp) {
        this.administratieveHandelingId = administratieveHandelingId;
        this.data = data;
        this.antwoordOp = antwoordOp;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtStandaardGroep te kopieren groep.
     */
    public AbstractBerichtStandaardGroepModel(final BerichtStandaardGroep berichtStandaardGroep) {
        this.data = berichtStandaardGroep.getData();

    }

    /**
     * Retourneert Administratieve handeling van Standaard.
     *
     * @return Administratieve handeling.
     */
    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtdataAttribuut getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtModel getAntwoordOp() {
        return antwoordOp;
    }

    /**
     * Zet Administratieve handeling van Standaard.
     *
     * @param administratieveHandelingId Administratieve handeling.
     */
    public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    /**
     * Zet Data van Standaard.
     *
     * @param data Data.
     */
    public void setData(final BerichtdataAttribuut data) {
        this.data = data;
    }

    /**
     * Zet Antwoord op van Standaard.
     *
     * @param antwoordOp Antwoord op.
     */
    public void setAntwoordOp(final BerichtModel antwoordOp) {
        this.antwoordOp = antwoordOp;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (data != null) {
            attributen.add(data);
        }
        return attributen;
    }

}
