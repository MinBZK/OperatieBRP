/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.basis.OuderBasis;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderlijkGezagGroepModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderschapGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import org.hibernate.annotations.Type;


/**
 * De betrokkenheid van een persoon in de rol van Ouder in een Familierechtelijke betrekking.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractOuderModel extends BetrokkenheidModel implements OuderBasis {

    @Embedded
    @JsonProperty
    private OuderOuderschapGroepModel     ouderschap;

    @Embedded
    @JsonProperty
    private OuderOuderlijkGezagGroepModel ouderlijkGezag;

    @Type(type = "StatusHistorie")
    @Column(name = "OuderlijkGezagStatusHis")
    @JsonProperty
    private StatusHistorie                ouderlijkGezagStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "OuderschapStatusHis")
    @JsonProperty
    private StatusHistorie                ouderschapStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractOuderModel() {
        this.ouderlijkGezagStatusHis = StatusHistorie.X;
        this.ouderschapStatusHis = StatusHistorie.X;

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Ouder.
     * @param persoon persoon van Ouder.
     */
    public AbstractOuderModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, SoortBetrokkenheid.OUDER, persoon);
        this.ouderlijkGezagStatusHis = StatusHistorie.X;
        this.ouderschapStatusHis = StatusHistorie.X;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouder Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractOuderModel(final Ouder ouder, final RelatieModel relatie, final PersoonModel persoon) {
        super(ouder, relatie, persoon);
        if (ouder.getOuderschap() != null) {
            this.ouderschap = new OuderOuderschapGroepModel(ouder.getOuderschap());
        }
        if (ouder.getOuderlijkGezag() != null) {
            this.ouderlijkGezag = new OuderOuderlijkGezagGroepModel(ouder.getOuderlijkGezag());
        }
        this.ouderlijkGezagStatusHis = StatusHistorie.X;
        this.ouderschapStatusHis = StatusHistorie.X;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderOuderschapGroepModel getOuderschap() {
        return ouderschap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderOuderlijkGezagGroepModel getOuderlijkGezag() {
        return ouderlijkGezag;
    }

    /**
     * Retourneert Ouderlijk gezag StatusHis van Ouder.
     *
     * @return Ouderlijk gezag StatusHis.
     */
    public StatusHistorie getOuderlijkGezagStatusHis() {
        return ouderlijkGezagStatusHis;
    }

    /**
     * Retourneert Ouderschap StatusHis van Ouder.
     *
     * @return Ouderschap StatusHis.
     */
    public StatusHistorie getOuderschapStatusHis() {
        return ouderschapStatusHis;
    }

    /**
     * Zet Ouderschap van Ouder. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param ouderschap Ouderschap.
     */
    public void setOuderschap(final OuderOuderschapGroepModel ouderschap) {
        this.ouderschap = ouderschap;
        if (ouderschap != null) {
            ouderschapStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Ouderlijk gezag van Ouder. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param ouderlijkGezag Ouderlijk gezag.
     */
    public void setOuderlijkGezag(final OuderOuderlijkGezagGroepModel ouderlijkGezag) {
        this.ouderlijkGezag = ouderlijkGezag;
        if (ouderlijkGezag != null) {
            ouderlijkGezagStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Ouderlijk gezag StatusHis van Ouder.
     *
     * @param ouderlijkGezagStatusHis Ouderlijk gezag StatusHis.
     */
    public void setOuderlijkGezagStatusHis(final StatusHistorie ouderlijkGezagStatusHis) {
        this.ouderlijkGezagStatusHis = ouderlijkGezagStatusHis;
    }

    /**
     * Zet Ouderschap StatusHis van Ouder.
     *
     * @param ouderschapStatusHis Ouderschap StatusHis.
     */
    public void setOuderschapStatusHis(final StatusHistorie ouderschapStatusHis) {
        this.ouderschapStatusHis = ouderschapStatusHis;
    }

}
