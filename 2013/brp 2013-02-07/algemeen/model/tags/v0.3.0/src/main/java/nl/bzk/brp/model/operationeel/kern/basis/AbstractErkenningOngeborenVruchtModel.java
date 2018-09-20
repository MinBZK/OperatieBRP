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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.ErkenningOngeborenVrucht;
import nl.bzk.brp.model.logisch.kern.basis.ErkenningOngeborenVruchtBasis;
import nl.bzk.brp.model.operationeel.kern.ErkenningOngeborenVruchtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import org.hibernate.annotations.Type;


/**
 * De erkenning ongeboren vrucht zoals bedoeld in artikel 5, BW boek 1.
 *
 * Een erkenning ongeboren vrucht is een relatie tussen twee personen, de toekomstige ouders van het kind of de kinderen
 * waarvan ��n van de twee in verwachting is. Hierbij is er sprake van enerzijds een erkenner, en anderzijds een
 * instemmer. De laatst is de (toekomstig) ouder die thans de drager van het ongeboren vrucht is, zonder diens
 * toestemming is erkenning niet mogelijk.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractErkenningOngeborenVruchtModel extends RelatieModel implements
        ErkenningOngeborenVruchtBasis
{

    @Embedded
    @JsonProperty
    private ErkenningOngeborenVruchtStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "ErkenningOngeborenVruchtStat")
    @JsonProperty
    private StatusHistorie                              erkenningOngeborenVruchtStatusHis;

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public AbstractErkenningOngeborenVruchtModel() {
        super(SoortRelatie.ERKENNING_ONGEBOREN_VRUCHT);
        this.erkenningOngeborenVruchtStatusHis = StatusHistorie.X;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param erkenningOngeborenVrucht Te kopieren object type.
     */
    public AbstractErkenningOngeborenVruchtModel(final ErkenningOngeborenVrucht erkenningOngeborenVrucht) {
        super(erkenningOngeborenVrucht);
        if (erkenningOngeborenVrucht.getStandaard() != null) {
            this.standaard = new ErkenningOngeborenVruchtStandaardGroepModel(erkenningOngeborenVrucht.getStandaard());
        }
        this.erkenningOngeborenVruchtStatusHis = StatusHistorie.X;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ErkenningOngeborenVruchtStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Erkenning ongeboren vrucht StatusHis van Erkenning ongeboren vrucht.
     *
     * @return Erkenning ongeboren vrucht StatusHis.
     */
    public StatusHistorie getErkenningOngeborenVruchtStatusHis() {
        return erkenningOngeborenVruchtStatusHis;
    }

    /**
     * Zet Standaard van Erkenning ongeboren vrucht. Zet tevens het bijbehorende status his veld op 'A' als het argument
     * niet null is.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final ErkenningOngeborenVruchtStandaardGroepModel standaard) {
        this.standaard = standaard;
        if (standaard != null) {
            erkenningOngeborenVruchtStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Erkenning ongeboren vrucht StatusHis van Erkenning ongeboren vrucht.
     *
     * @param erkenningOngeborenVruchtStatusHis Erkenning ongeboren vrucht StatusHis.
     */
    public void setErkenningOngeborenVruchtStatusHis(final StatusHistorie erkenningOngeborenVruchtStatusHis) {
        this.erkenningOngeborenVruchtStatusHis = erkenningOngeborenVruchtStatusHis;
    }

}
