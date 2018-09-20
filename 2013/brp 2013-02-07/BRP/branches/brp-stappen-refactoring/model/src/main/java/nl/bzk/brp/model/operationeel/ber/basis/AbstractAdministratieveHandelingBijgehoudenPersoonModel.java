/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingBijgehoudenPersoon;
import nl.bzk.brp.model.logisch.ber.basis.AdministratieveHandelingBijgehoudenPersoonBasis;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
 * De bijhouding van gegevens over een persoon door middel van een administratieve handeling.
 *
 * Bijhoudingen gebeuren doordat een administratieve handeling wordt verwerkt dat tot wijzigingen leid van
 * persoonsgegevens. Daar waar een administratieve handeling leidt tot een aanpassing van het veld datumtijdstip laatste
 * wijziging van die persoon, is er sprake van een "Administratieve handeling\Bijgehouden persoon". Meer informatie is
 * te vinden bij de beschrijving van de verwerkingsregel voor datumtijd laatste wijziging. Kort gezegd komt het neer op:
 * - 0e graads: dit zijn wijzigingen in het desbetreffende record van de tabel Kern.Pers zelf.
 * - 1e graads: dit zijn wijzigingen in een record van een tabel die verwijst naar �het desbetreffende record van de
 * tabel Kern.Pers�.
 * - 2e graads: dit zijn wijzigingen in een record van een tabel�
 * o � die verwijst naar �een record van een tabel die verwijst naar het desbetreffende record van de tabel Kern.Pers�
 * o � of waarnaar wordt verwezen door �een record van een tabel die verwijst naar het desbetreffende record van de
 * tabel Kern.Pers�.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractAdministratieveHandelingBijgehoudenPersoonModel extends AbstractDynamischObjectType
        implements AdministratieveHandelingBijgehoudenPersoonBasis
{

    @Id
    @SequenceGenerator(name = "ADMINISTRATIEVEHANDELINGBIJGEHOUDENPERSOON",
                       sequenceName = "Ber.seq_AdmHndBijgehoudenPers")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADMINISTRATIEVEHANDELINGBIJGEHOUDENPERSOON")
    @JsonProperty
    private Long                          iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    private AdministratieveHandelingModel administratieveHandeling;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonModel                  persoon;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractAdministratieveHandelingBijgehoudenPersoonModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Bijgehouden persoon.
     * @param persoon persoon van Administratieve handeling \ Bijgehouden persoon.
     */
    public AbstractAdministratieveHandelingBijgehoudenPersoonModel(
            final AdministratieveHandelingModel administratieveHandeling, final PersoonModel persoon)
    {
        this();
        this.administratieveHandeling = administratieveHandeling;
        this.persoon = persoon;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param administratieveHandelingBijgehoudenPersoon Te kopieren object type.
     * @param administratieveHandeling Bijbehorende Administratieve handeling.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractAdministratieveHandelingBijgehoudenPersoonModel(
            final AdministratieveHandelingBijgehoudenPersoon administratieveHandelingBijgehoudenPersoon,
            final AdministratieveHandelingModel administratieveHandeling, final PersoonModel persoon)
    {
        this();
        this.administratieveHandeling = administratieveHandeling;
        this.persoon = persoon;

    }

    /**
     * Retourneert ID van Administratieve handeling \ Bijgehouden persoon.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Bijgehouden persoon.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Persoon van Administratieve handeling \ Bijgehouden persoon.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

}
