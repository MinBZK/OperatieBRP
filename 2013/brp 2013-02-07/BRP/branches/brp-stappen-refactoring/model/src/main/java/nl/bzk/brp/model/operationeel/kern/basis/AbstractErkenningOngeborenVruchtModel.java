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
import nl.bzk.brp.model.logisch.kern.ErkenningOngeborenVrucht;
import nl.bzk.brp.model.logisch.kern.basis.ErkenningOngeborenVruchtBasis;
import nl.bzk.brp.model.operationeel.kern.ErkenningOngeborenVruchtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;


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
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractErkenningOngeborenVruchtModel extends RelatieModel implements
        ErkenningOngeborenVruchtBasis
{

    @Embedded
    @JsonProperty
    private ErkenningOngeborenVruchtStandaardGroepModel standaard;

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public AbstractErkenningOngeborenVruchtModel() {
        super(SoortRelatie.ERKENNING_ONGEBOREN_VRUCHT);
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

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ErkenningOngeborenVruchtStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Zet Standaard van Erkenning ongeboren vrucht.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final ErkenningOngeborenVruchtStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

}
