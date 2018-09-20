/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.NaamskeuzeOngeborenVrucht;
import nl.bzk.brp.model.logisch.kern.basis.NaamskeuzeOngeborenVruchtBasis;
import nl.bzk.brp.model.operationeel.kern.NaamskeuzeOngeborenVruchtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;


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
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractNaamskeuzeOngeborenVruchtModel extends RelatieModel implements
        NaamskeuzeOngeborenVruchtBasis
{

    @Embedded
    @JsonProperty
    private NaamskeuzeOngeborenVruchtStandaardGroepModel standaard;

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public AbstractNaamskeuzeOngeborenVruchtModel() {
        super(SoortRelatie.NAAMSKEUZE_ONGEBOREN_VRUCHT);
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

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamskeuzeOngeborenVruchtStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Zet Standaard van Naamskeuze ongeboren vrucht.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final NaamskeuzeOngeborenVruchtStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

}
