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
import nl.bzk.brp.model.logisch.kern.NaamskeuzeOngeborenVrucht;
import nl.bzk.brp.model.logisch.kern.basis.NaamskeuzeOngeborenVruchtBasis;
import nl.bzk.brp.model.operationeel.kern.NaamskeuzeOngeborenVruchtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import org.hibernate.annotations.Type;


/**
 * De relatie tussen twee ouders-in-spe waarmee zij aangeven welke naamskeuze zij wensen voor het kind/de kinderen
 * waarvan ��n van beide zwanger is.
 *
 * Reeds voor de geboorte kunnen de toekomstig ouders afspraken maken welke geslachtsnaam het kind/de kinderen gaan
 * krijgen. De ouder wiens geslachtnaam zal overgaan op het kind, heeft hier de rol Naamgever; de andere ouder - die
 * hiermee instemt - heeft in deze relatie dan de betrokkenheid in de rol van instemmer.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractNaamskeuzeOngeborenVruchtModel extends RelatieModel implements
        NaamskeuzeOngeborenVruchtBasis
{

    @Embedded
    @JsonProperty
    private NaamskeuzeOngeborenVruchtStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "NaamskeuzeOngeborenVruchtSta")
    @JsonProperty
    private StatusHistorie                               naamskeuzeOngeborenVruchtStatusHis;

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public AbstractNaamskeuzeOngeborenVruchtModel() {
        super(SoortRelatie.NAAMSKEUZE_ONGEBOREN_VRUCHT);
        this.naamskeuzeOngeborenVruchtStatusHis = StatusHistorie.X;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param naamskeuzeOngeborenVrucht Te kopieren object type.
     */
    public AbstractNaamskeuzeOngeborenVruchtModel(final NaamskeuzeOngeborenVrucht naamskeuzeOngeborenVrucht) {
        super(naamskeuzeOngeborenVrucht);
        if (naamskeuzeOngeborenVrucht.getStandaard() != null) {
            this.standaard = new NaamskeuzeOngeborenVruchtStandaardGroepModel(naamskeuzeOngeborenVrucht.getStandaard());
        }
        this.naamskeuzeOngeborenVruchtStatusHis = StatusHistorie.X;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamskeuzeOngeborenVruchtStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Naamskeuze ongeboren vrucht StatusHis van Naamskeuze ongeboren vrucht.
     *
     * @return Naamskeuze ongeboren vrucht StatusHis.
     */
    public StatusHistorie getNaamskeuzeOngeborenVruchtStatusHis() {
        return naamskeuzeOngeborenVruchtStatusHis;
    }

    /**
     * Zet Standaard van Naamskeuze ongeboren vrucht. Zet tevens het bijbehorende status his veld op 'A' als het
     * argument
     * niet null is.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final NaamskeuzeOngeborenVruchtStandaardGroepModel standaard) {
        this.standaard = standaard;
        if (standaard != null) {
            naamskeuzeOngeborenVruchtStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Naamskeuze ongeboren vrucht StatusHis van Naamskeuze ongeboren vrucht.
     *
     * @param naamskeuzeOngeborenVruchtStatusHis Naamskeuze ongeboren vrucht StatusHis.
     */
    public void setNaamskeuzeOngeborenVruchtStatusHis(final StatusHistorie naamskeuzeOngeborenVruchtStatusHis) {
        this.naamskeuzeOngeborenVruchtStatusHis = naamskeuzeOngeborenVruchtStatusHis;
    }

}
