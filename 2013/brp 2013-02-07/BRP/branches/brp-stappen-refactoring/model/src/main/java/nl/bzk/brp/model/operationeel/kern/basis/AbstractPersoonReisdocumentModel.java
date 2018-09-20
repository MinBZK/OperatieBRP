/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocument;
import nl.bzk.brp.model.logisch.kern.basis.PersoonReisdocumentBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonReisdocumentStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Document dat vereist is voor het reizen naar het buitenland.
 *
 * 1. De opname van deze gegevens is noodzakelijk tot uitfasering LO 3.7 en/of ingebruikname ORRA.
 * RvdP 5 september 2011
 * 2. De modellering rondom autoriteit van afgifte kent vele opties, zoals ��n codetabel (waarin alle mogelijke codes
 * staan zoals benoemd in de LO3.x tabel), maar ook het splitsen in een 'typering' van autoriteit, en dan afhankelijk
 * van de 'type' of een verwijzing naar gemeente, provincie of land, of bijvoorbeeld verschillende verwijzingen naar de
 * autoriteit a-la 'provincie van autoriteit afgifte commissaris' en 'gemeente van autoriteit afgifte burgermeester' en
 * 'gemeente van autoriteit van afgifte college B&W'.
 * De keuze is uiteindelijk op de middelste gevallen op basis van de volgende overwegingen:
 * - bij beheer van de landentabel of gemeentetabel (partijentabel) moet het niet noodzakelijk zijn om ook (extra)
 * beheer te doen op de stamgegevens rondom autoriteit: hierdoor viel de optie '��n codetabel' af.
 * - de verschillende attributen moeten een relatief eenvoudige naam hebben, en de definitie moet hierbij goed
 * aansluiten. De derde optie - met een verwijzing naar gemeenten voor verschillende typen autoriteiten [namelijk bij
 * o.a. commandant Maurechausse, Burgermeester �n Burgermeester-en-Wethouders) - is minder begrijpelijk en valt door dit
 * criterium af.
 * RvdP 2 oktober 2012.
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
public abstract class AbstractPersoonReisdocumentModel extends AbstractDynamischObjectType implements
        PersoonReisdocumentBasis
{

    @Id
    @SequenceGenerator(name = "PERSOONREISDOCUMENT", sequenceName = "Kern.seq_PersReisdoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONREISDOCUMENT")
    @JsonProperty
    private Integer                                iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel                           persoon;

    @ManyToOne
    @JoinColumn(name = "Srt")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private SoortNederlandsReisdocument            soort;

    @Embedded
    @JsonProperty
    private PersoonReisdocumentStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "PersReisdocStatusHis")
    @JsonProperty
    private StatusHistorie                         persoonReisdocumentStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonReisdocumentModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Reisdocument.
     * @param soort soort van Persoon \ Reisdocument.
     */
    public AbstractPersoonReisdocumentModel(final PersoonModel persoon, final SoortNederlandsReisdocument soort) {
        this();
        this.persoon = persoon;
        this.soort = soort;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonReisdocument Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonReisdocumentModel(final PersoonReisdocument persoonReisdocument, final PersoonModel persoon) {
        this();
        this.persoon = persoon;
        this.soort = persoonReisdocument.getSoort();
        if (persoonReisdocument.getStandaard() != null) {
            this.standaard = new PersoonReisdocumentStandaardGroepModel(persoonReisdocument.getStandaard());
        }

    }

    /**
     * Retourneert ID van Persoon \ Reisdocument.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Reisdocument.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Soort van Persoon \ Reisdocument.
     *
     * @return Soort.
     */
    public SoortNederlandsReisdocument getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonReisdocumentStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Persoon \ Reisdocument StatusHis van Persoon \ Reisdocument.
     *
     * @return Persoon \ Reisdocument StatusHis.
     */
    public StatusHistorie getPersoonReisdocumentStatusHis() {
        return persoonReisdocumentStatusHis;
    }

    /**
     * Zet Standaard van Persoon \ Reisdocument.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonReisdocumentStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Persoon \ Reisdocument StatusHis van Persoon \ Reisdocument.
     *
     * @param persoonReisdocumentStatusHis Persoon \ Reisdocument StatusHis.
     */
    public void setPersoonReisdocumentStatusHis(final StatusHistorie persoonReisdocumentStatusHis) {
        this.persoonReisdocumentStatusHis = persoonReisdocumentStatusHis;
    }

}
