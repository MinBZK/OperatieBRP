/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.basis.OnderzoekBasis;
import nl.bzk.brp.model.operationeel.kern.OnderzoekStandaardGroepModel;
import org.hibernate.annotations.Type;


/**
 * Onderzoek naar gegevens in de BRP.
 *
 * Normaliter is er geen reden om te twijfelen aan de in de BRP geregistreerde gegevens. Soms echter is dat wel aan de
 * orde. Vanuit verschillende hoeken kan een signaal komen dat bepaalde gegevens niet correct zijn. Dit kan om zowel
 * actuele gegevens als om (materieel) historische gegevens gaan. Met het objecttype Onderzoek wordt vastgelegd dat
 * gegevens in onderzoek zijn, en welke gegevens het betreft.
 *
 * Nog onderzoeken: Relatie met TMV/TMF.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractOnderzoekModel extends AbstractDynamischObjectType implements OnderzoekBasis {

    @Id
    @SequenceGenerator(name = "ONDERZOEK", sequenceName = "Kern.seq_Onderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ONDERZOEK")
    @JsonProperty
    private Integer                      iD;

    @Embedded
    @JsonProperty
    private OnderzoekStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "OnderzoekStatusHis")
    @JsonProperty
    private StatusHistorie               onderzoekStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractOnderzoekModel() {
        this.onderzoekStatusHis = StatusHistorie.X;

    }

    /**
     * Retourneert ID van Onderzoek.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Onderzoek StatusHis van Onderzoek.
     *
     * @return Onderzoek StatusHis.
     */
    public StatusHistorie getOnderzoekStatusHis() {
        return onderzoekStatusHis;
    }

    /**
     * Zet Standaard van Onderzoek. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final OnderzoekStandaardGroepModel standaard) {
        this.standaard = standaard;
        if (standaard != null) {
            onderzoekStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Onderzoek StatusHis van Onderzoek.
     *
     * @param onderzoekStatusHis Onderzoek StatusHis.
     */
    public void setOnderzoekStatusHis(final StatusHistorie onderzoekStatusHis) {
        this.onderzoekStatusHis = onderzoekStatusHis;
    }

}
