/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortBevoegdheid;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Toestand;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.CategoriePersonen;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
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
public abstract class AbstractHisBijhoudingsautorisatie {

    @Id
    @SequenceGenerator(name = "HIS_BIJHOUDINGSAUTORISATIE", sequenceName = "AutAut.seq_His_Bijhautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_BIJHOUDINGSAUTORISATIE")
    @JsonProperty
    private Integer                      iD;

    @ManyToOne
    @JoinColumn(name = "Bijhautorisatie")
    @Fetch(value = FetchMode.JOIN)
    private Bijhoudingsautorisatie       bijhoudingsautorisatie;

    @Enumerated
    @Column(name = "SrtBevoegdheid")
    @JsonProperty
    private SoortBevoegdheid             soortBevoegdheid;

    @Enumerated
    @Column(name = "GeautoriseerdeSrtPartij")
    @JsonProperty
    private SoortPartij                  geautoriseerdeSoortPartij;

    @ManyToOne
    @JoinColumn(name = "GeautoriseerdePartij")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij                       geautoriseerdePartij;

    @Enumerated
    @Column(name = "Toestand")
    @JsonProperty
    private Toestand                     toestand;

    @Enumerated
    @Column(name = "CategoriePersonen")
    @JsonProperty
    private CategoriePersonen            categoriePersonen;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    @JsonProperty
    private OmschrijvingEnumeratiewaarde omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatIngang"))
    @JsonProperty
    private Datum                        datumIngang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    @JsonProperty
    private Datum                        datumEinde;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisBijhoudingsautorisatie() {
    }

    /**
     * Retourneert ID van His Bijhoudingsautorisatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Bijhoudingsautorisatie van His Bijhoudingsautorisatie.
     *
     * @return Bijhoudingsautorisatie.
     */
    public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
        return bijhoudingsautorisatie;
    }

    /**
     * Retourneert Soort bevoegdheid van His Bijhoudingsautorisatie.
     *
     * @return Soort bevoegdheid.
     */
    public SoortBevoegdheid getSoortBevoegdheid() {
        return soortBevoegdheid;
    }

    /**
     * Retourneert Geautoriseerde soort partij van His Bijhoudingsautorisatie.
     *
     * @return Geautoriseerde soort partij.
     */
    public SoortPartij getGeautoriseerdeSoortPartij() {
        return geautoriseerdeSoortPartij;
    }

    /**
     * Retourneert Geautoriseerde partij van His Bijhoudingsautorisatie.
     *
     * @return Geautoriseerde partij.
     */
    public Partij getGeautoriseerdePartij() {
        return geautoriseerdePartij;
    }

    /**
     * Retourneert Toestand van His Bijhoudingsautorisatie.
     *
     * @return Toestand.
     */
    public Toestand getToestand() {
        return toestand;
    }

    /**
     * Retourneert Categorie personen van His Bijhoudingsautorisatie.
     *
     * @return Categorie personen.
     */
    public CategoriePersonen getCategoriePersonen() {
        return categoriePersonen;
    }

    /**
     * Retourneert Omschrijving van His Bijhoudingsautorisatie.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Datum ingang van His Bijhoudingsautorisatie.
     *
     * @return Datum ingang.
     */
    public Datum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Bijhoudingsautorisatie.
     *
     * @return Datum einde.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

}
