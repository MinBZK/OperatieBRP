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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.NaamskeuzeOngeborenVruchtModel;
import nl.bzk.brp.model.operationeel.kern.NaamskeuzeOngeborenVruchtStandaardGroepModel;


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
public abstract class AbstractHisNaamskeuzeOngeborenVruchtModel extends AbstractFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_NAAMSKEUZEONGEBORENVRUCHT", sequenceName = "Kern.seq_His_NaamskeuzeOngeborenVruch")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_NAAMSKEUZEONGEBORENVRUCHT")
    @JsonProperty
    private Integer                        iD;

    @ManyToOne
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private NaamskeuzeOngeborenVruchtModel relatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatNaamskeuzeOngeborenVrucht"))
    @JsonProperty
    private Datum                          datumNaamskeuzeOngeborenVrucht;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisNaamskeuzeOngeborenVruchtModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param naamskeuzeOngeborenVruchtModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisNaamskeuzeOngeborenVruchtModel(
            final NaamskeuzeOngeborenVruchtModel naamskeuzeOngeborenVruchtModel,
            final NaamskeuzeOngeborenVruchtStandaardGroepModel groep)
    {
        this.relatie = naamskeuzeOngeborenVruchtModel;
        this.datumNaamskeuzeOngeborenVrucht = groep.getDatumNaamskeuzeOngeborenVrucht();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisNaamskeuzeOngeborenVruchtModel(final AbstractHisNaamskeuzeOngeborenVruchtModel kopie) {
        super(kopie);
        relatie = kopie.getRelatie();
        datumNaamskeuzeOngeborenVrucht = kopie.getDatumNaamskeuzeOngeborenVrucht();

    }

    /**
     * Retourneert ID van His Naamskeuze ongeboren vrucht.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Relatie van His Naamskeuze ongeboren vrucht.
     *
     * @return Relatie.
     */
    public NaamskeuzeOngeborenVruchtModel getRelatie() {
        return relatie;
    }

    /**
     * Retourneert Datum naamskeuze ongeboren vrucht van His Naamskeuze ongeboren vrucht.
     *
     * @return Datum naamskeuze ongeboren vrucht.
     */
    public Datum getDatumNaamskeuzeOngeborenVrucht() {
        return datumNaamskeuzeOngeborenVrucht;
    }

}
