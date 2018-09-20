/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.basis.GegevenInOnderzoekBasis;
import nl.bzk.brp.model.operationeel.kern.OnderzoekModel;


/**
 * Gegevens waar onderzoek naar gedaan wordt/naar gedaan is.
 *
 * Bij de naam '(Gegevens)Element' is de vraag welke elementen worden bedoeld: zijn dit de LOGISCHE elementen, of de
 * representanten hiervan in de database, de DATABASE OBJECTEN.
 * Voor een aantal objecttypen is de hypothese waaronder gewerkt wordt dat het de DATABASE OBJECTEN zijn. Om deze
 * duidelijk te kunnen scheiden van (andere) Elementen, hebben deze een aparte naam gekregen: Databaseobject.
 * In de verwijzing van het attribuut gebruiken we echter nog de naam 'Element': een Databaseobject zou immers kunnen
 * worden beschouwd als een specialisatie van Element. Alleen is die specialisatie in het model niet uitgemodelleerd.
 * RvdP 16 november 2011.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractGegevenInOnderzoekModel extends AbstractDynamischObjectType implements
        GegevenInOnderzoekBasis
{

    @ManyToOne
    @JoinColumn(name = "Onderzoek")
    @JsonProperty
    private OnderzoekModel onderzoek;

    @Enumerated
    @Column(name = "SrtGegeven")
    @JsonProperty
    private DatabaseObject soortGegeven;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Ident"))
    @JsonProperty
    private Sleutelwaarde  identificatie;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractGegevenInOnderzoekModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param onderzoek onderzoek van Gegeven in onderzoek.
     * @param soortGegeven soortGegeven van Gegeven in onderzoek.
     * @param identificatie identificatie van Gegeven in onderzoek.
     */
    public AbstractGegevenInOnderzoekModel(final OnderzoekModel onderzoek, final DatabaseObject soortGegeven,
            final Sleutelwaarde identificatie)
    {
        this();
        this.onderzoek = onderzoek;
        this.soortGegeven = soortGegeven;
        this.identificatie = identificatie;

    }

    /**
     * Retourneert Onderzoek van Gegeven in onderzoek.
     *
     * @return Onderzoek.
     */
    public OnderzoekModel getOnderzoek() {
        return onderzoek;
    }

    /**
     * Retourneert Soort gegeven van Gegeven in onderzoek.
     *
     * @return Soort gegeven.
     */
    public DatabaseObject getSoortGegeven() {
        return soortGegeven;
    }

    /**
     * Retourneert Identificatie van Gegeven in onderzoek.
     *
     * @return Identificatie.
     */
    public Sleutelwaarde getIdentificatie() {
        return identificatie;
    }

}
