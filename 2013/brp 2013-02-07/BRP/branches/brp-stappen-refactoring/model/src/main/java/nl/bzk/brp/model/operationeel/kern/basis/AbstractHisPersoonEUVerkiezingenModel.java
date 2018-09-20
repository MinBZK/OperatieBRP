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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.PersoonEUVerkiezingenGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


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
public abstract class AbstractHisPersoonEUVerkiezingenModel extends AbstractFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_PERSOONEUVERKIEZINGEN", sequenceName = "Kern.seq_His_PersEUVerkiezingen")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONEUVERKIEZINGEN")
    @JsonProperty
    private Integer      iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndDeelnEUVerkiezingen"))
    @JsonProperty
    private JaNee        indicatieDeelnameEUVerkiezingen;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanlAanpDeelnEUVerkiezing"))
    @JsonProperty
    private Datum        datumAanleidingAanpassingDeelnameEUVerkiezing;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeUitslEUKiesr"))
    @JsonProperty
    private Datum        datumEindeUitsluitingEUKiesrecht;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonEUVerkiezingenModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonEUVerkiezingenModel(final PersoonModel persoonModel,
            final PersoonEUVerkiezingenGroepModel groep)
    {
        this.persoon = persoonModel;
        this.indicatieDeelnameEUVerkiezingen = groep.getIndicatieDeelnameEUVerkiezingen();
        this.datumAanleidingAanpassingDeelnameEUVerkiezing = groep.getDatumAanleidingAanpassingDeelnameEUVerkiezing();
        this.datumEindeUitsluitingEUKiesrecht = groep.getDatumEindeUitsluitingEUKiesrecht();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonEUVerkiezingenModel(final AbstractHisPersoonEUVerkiezingenModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        indicatieDeelnameEUVerkiezingen = kopie.getIndicatieDeelnameEUVerkiezingen();
        datumAanleidingAanpassingDeelnameEUVerkiezing = kopie.getDatumAanleidingAanpassingDeelnameEUVerkiezing();
        datumEindeUitsluitingEUKiesrecht = kopie.getDatumEindeUitsluitingEUKiesrecht();

    }

    /**
     * Retourneert ID van His Persoon EU verkiezingen.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon EU verkiezingen.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Deelname EU verkiezingen? van His Persoon EU verkiezingen.
     *
     * @return Deelname EU verkiezingen?.
     */
    public JaNee getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    /**
     * Retourneert Datum aanleiding aanpassing deelname EU verkiezing van His Persoon EU verkiezingen.
     *
     * @return Datum aanleiding aanpassing deelname EU verkiezing.
     */
    public Datum getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
        return datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    /**
     * Retourneert Datum einde uitsluiting EU kiesrecht van His Persoon EU verkiezingen.
     *
     * @return Datum einde uitsluiting EU kiesrecht.
     */
    public Datum getDatumEindeUitsluitingEUKiesrecht() {
        return datumEindeUitsluitingEUKiesrecht;
    }

}
