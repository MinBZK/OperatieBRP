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
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.basis.PersoonGeslachtsnaamcomponentBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.annotations.Type;


/**
 * Component van de Geslachtsnaam van een Persoon
 *
 * De geslachtsnaam van een Persoon kan uit meerdere delen bestaan, bijvoorbeeld ten gevolge van een namenreeks. Ook kan
 * er sprake zijn van het voorkomen van meerdere geslachten, die in de geslachtsnaam terugkomen. In dat geval valt de
 * Geslachtsnaam uiteen in meerdere Geslachtsnaamcomponenten. Een Geslachtsnaamcomponent bestaat vervolgens
 * mogelijkerwijs uit meerdere onderdelen, waaronder Voorvoegsel en Naamdeel. Zie verder toelichting bij Geslachtsnaam.
 *
 * 1. Vooruitlopend op liberalisering namenwet, waarbij het waarschijnlijk mogelijk wordt om de (volledige)
 * geslachtsnaam van een kind te vormen door delen van de geslachtsnaam van beide ouders samen te voegen, is het alvast
 * mogelijk gemaakt om deze delen apart te 'kennen'. Deze beslissing is genomen na raadpleging van ministerie van
 * Justitie, in de persoon van Jet Lenters.
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
public abstract class AbstractPersoonGeslachtsnaamcomponentModel extends AbstractDynamischObjectType implements
        PersoonGeslachtsnaamcomponentBasis
{

    @Id
    @SequenceGenerator(name = "PERSOONGESLACHTSNAAMCOMPONENT", sequenceName = "Kern.seq_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONGESLACHTSNAAMCOMPONENT")
    @JsonProperty
    private Integer                                          iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel                                     persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Volgnr"))
    @JsonProperty
    private Volgnummer                                       volgnummer;

    @Embedded
    @JsonProperty
    private PersoonGeslachtsnaamcomponentStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "PersGeslnaamcompStatusHis")
    @JsonProperty
    private StatusHistorie                                   persoonGeslachtsnaamcomponentStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonGeslachtsnaamcomponentModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Geslachtsnaamcomponent.
     * @param volgnummer volgnummer van Persoon \ Geslachtsnaamcomponent.
     */
    public AbstractPersoonGeslachtsnaamcomponentModel(final PersoonModel persoon, final Volgnummer volgnummer) {
        this();
        this.persoon = persoon;
        this.volgnummer = volgnummer;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsnaamcomponent Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonGeslachtsnaamcomponentModel(
            final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent, final PersoonModel persoon)
    {
        this();
        this.persoon = persoon;
        this.volgnummer = persoonGeslachtsnaamcomponent.getVolgnummer();
        if (persoonGeslachtsnaamcomponent.getStandaard() != null) {
            this.standaard =
                new PersoonGeslachtsnaamcomponentStandaardGroepModel(persoonGeslachtsnaamcomponent.getStandaard());
        }

    }

    /**
     * Retourneert ID van Persoon \ Geslachtsnaamcomponent.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Volgnummer van Persoon \ Geslachtsnaamcomponent.
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
    public PersoonGeslachtsnaamcomponentStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Persoon \ Geslachtsnaamcomponent StatusHis van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Persoon \ Geslachtsnaamcomponent StatusHis.
     */
    public StatusHistorie getPersoonGeslachtsnaamcomponentStatusHis() {
        return persoonGeslachtsnaamcomponentStatusHis;
    }

    /**
     * Zet Standaard van Persoon \ Geslachtsnaamcomponent.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonGeslachtsnaamcomponentStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Persoon \ Geslachtsnaamcomponent StatusHis van Persoon \ Geslachtsnaamcomponent.
     *
     * @param persoonGeslachtsnaamcomponentStatusHis Persoon \ Geslachtsnaamcomponent StatusHis.
     */
    public void setPersoonGeslachtsnaamcomponentStatusHis(final StatusHistorie persoonGeslachtsnaamcomponentStatusHis) {
        this.persoonGeslachtsnaamcomponentStatusHis = persoonGeslachtsnaamcomponentStatusHis;
    }

}
