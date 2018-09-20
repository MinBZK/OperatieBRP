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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.logisch.kern.basis.PersoonVoornaamBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamStandaardGroepModel;
import org.hibernate.annotations.Type;


/**
 * Voornaam van een Persoon
 *
 * Voornamen worden in de BRP los van elkaar geregistreerd. Het LO BRP is voorbereid op het kunnen vastleggen van
 * voornamen zoals 'Jan Peter', 'Aberto di Maria' of 'Wonder op aarde' als ��n enkele voornaam. In de BRP is het
 * namelijk niet noodzakelijk (conform LO 3.x) om de verschillende woorden aan elkaar te plakken met een koppelteken.
 *
 * Het gebruik van de spatie als koppelteken is echter (nog) niet toegestaan.
 *
 * Indien er sprake is van een namenreeks wordt dit opgenomen als geslachtsnaam; er is dan geen sprake van een Voornaam.
 *
 * Een voornaam mag voorlopig nog geen spatie bevatten.
 * Hiertoe dient eerst de akten van burgerlijke stand aangepast te worden (zodat voornamen individueel kunnen worden
 * vastgelegd, en er geen interpretatie meer nodig is van de ambtenaar over waar de ene voornaam eindigt en een tweede
 * begint).
 * Daarnaast is er ook nog geen duidelijkheid over de wijze waarop bestaande namen aangepast kunnen worden: kan de
 * burger hier simpelweg om verzoeken en wordt het dan aangepast?
 *
 * De BRP is wel al voorbereid op het kunnen bevatten van spaties.
 * RvdP 5 augustus 2011
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
public abstract class AbstractPersoonVoornaamModel extends AbstractDynamischObjectType implements PersoonVoornaamBasis {

    @Id
    @SequenceGenerator(name = "PERSOONVOORNAAM", sequenceName = "Kern.seq_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONVOORNAAM")
    @JsonProperty
    private Integer                            iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel                       persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Volgnr"))
    @JsonProperty
    private Volgnummer                         volgnummer;

    @Embedded
    @JsonProperty
    private PersoonVoornaamStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "PersVoornaamStatusHis")
    @JsonProperty
    private StatusHistorie                     persoonVoornaamStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonVoornaamModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Voornaam.
     * @param volgnummer volgnummer van Persoon \ Voornaam.
     */
    public AbstractPersoonVoornaamModel(final PersoonModel persoon, final Volgnummer volgnummer) {
        this();
        this.persoon = persoon;
        this.volgnummer = volgnummer;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVoornaam Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonVoornaamModel(final PersoonVoornaam persoonVoornaam, final PersoonModel persoon) {
        this();
        this.persoon = persoon;
        this.volgnummer = persoonVoornaam.getVolgnummer();
        if (persoonVoornaam.getStandaard() != null) {
            this.standaard = new PersoonVoornaamStandaardGroepModel(persoonVoornaam.getStandaard());
        }

    }

    /**
     * Retourneert ID van Persoon \ Voornaam.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Voornaam.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Volgnummer van Persoon \ Voornaam.
     *
     * @return Volgnummer.
     */
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonVoornaamStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Persoon \ Voornaam StatusHis van Persoon \ Voornaam.
     *
     * @return Persoon \ Voornaam StatusHis.
     */
    public StatusHistorie getPersoonVoornaamStatusHis() {
        return persoonVoornaamStatusHis;
    }

    /**
     * Zet Standaard van Persoon \ Voornaam.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonVoornaamStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Persoon \ Voornaam StatusHis van Persoon \ Voornaam.
     *
     * @param persoonVoornaamStatusHis Persoon \ Voornaam StatusHis.
     */
    public void setPersoonVoornaamStatusHis(final StatusHistorie persoonVoornaamStatusHis) {
        this.persoonVoornaamStatusHis = persoonVoornaamStatusHis;
    }

}
