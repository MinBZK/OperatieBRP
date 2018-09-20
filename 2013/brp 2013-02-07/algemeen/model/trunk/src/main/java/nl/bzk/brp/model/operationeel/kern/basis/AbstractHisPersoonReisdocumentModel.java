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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LengteInCm;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AutoriteitVanAfgifteReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVervallenReisdocument;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonReisdocumentStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonReisdocumentStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonReisdocumentModel extends AbstractFormeleHistorieEntiteit implements
        PersoonReisdocumentStandaardGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONREISDOCUMENT", sequenceName = "Kern.seq_His_PersReisdoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONREISDOCUMENT")
    @JsonProperty
    private Integer                          iD;

    @ManyToOne
    @JoinColumn(name = "PersReisdoc")
    private PersoonReisdocumentModel         persoonReisdocument;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Nr"))
    @JsonProperty
    private ReisdocumentNummer               nummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LengteHouder"))
    @JsonProperty
    private LengteInCm                       lengteHouder;

    @ManyToOne
    @JoinColumn(name = "AutVanAfgifte")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatIngangDoc"))
    @JsonProperty
    private Datum                            datumIngangDocument;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatUitgifte"))
    @JsonProperty
    private Datum                            datumUitgifte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzeEindeGel"))
    @JsonProperty
    private Datum                            datumVoorzieneEindeGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatInhingVermissing"))
    @JsonProperty
    private Datum                            datumInhoudingVermissing;

    @ManyToOne
    @JoinColumn(name = "RdnVervallen")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private RedenVervallenReisdocument       redenVervallen;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonReisdocumentModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonReisdocumentModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonReisdocumentModel(final PersoonReisdocumentModel persoonReisdocumentModel,
            final PersoonReisdocumentStandaardGroepModel groep)
    {
        this.persoonReisdocument = persoonReisdocumentModel;
        this.nummer = groep.getNummer();
        this.lengteHouder = groep.getLengteHouder();
        this.autoriteitVanAfgifte = groep.getAutoriteitVanAfgifte();
        this.datumIngangDocument = groep.getDatumIngangDocument();
        this.datumUitgifte = groep.getDatumUitgifte();
        this.datumVoorzieneEindeGeldigheid = groep.getDatumVoorzieneEindeGeldigheid();
        this.datumInhoudingVermissing = groep.getDatumInhoudingVermissing();
        this.redenVervallen = groep.getRedenVervallen();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonReisdocumentModel(final AbstractHisPersoonReisdocumentModel kopie) {
        super(kopie);
        persoonReisdocument = kopie.getPersoonReisdocument();
        nummer = kopie.getNummer();
        lengteHouder = kopie.getLengteHouder();
        autoriteitVanAfgifte = kopie.getAutoriteitVanAfgifte();
        datumIngangDocument = kopie.getDatumIngangDocument();
        datumUitgifte = kopie.getDatumUitgifte();
        datumVoorzieneEindeGeldigheid = kopie.getDatumVoorzieneEindeGeldigheid();
        datumInhoudingVermissing = kopie.getDatumInhoudingVermissing();
        redenVervallen = kopie.getRedenVervallen();

    }

    /**
     * Retourneert ID van His Persoon \ Reisdocument.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Reisdocument van His Persoon \ Reisdocument.
     *
     * @return Persoon \ Reisdocument.
     */
    public PersoonReisdocumentModel getPersoonReisdocument() {
        return persoonReisdocument;
    }

    /**
     * Retourneert Nummer van His Persoon \ Reisdocument.
     *
     * @return Nummer.
     */
    public ReisdocumentNummer getNummer() {
        return nummer;
    }

    /**
     * Retourneert Lengte houder van His Persoon \ Reisdocument.
     *
     * @return Lengte houder.
     */
    public LengteInCm getLengteHouder() {
        return lengteHouder;
    }

    /**
     * Retourneert Autoriteit van afgifte van His Persoon \ Reisdocument.
     *
     * @return Autoriteit van afgifte.
     */
    public AutoriteitVanAfgifteReisdocument getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * Retourneert Datum ingang document van His Persoon \ Reisdocument.
     *
     * @return Datum ingang document.
     */
    public Datum getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * Retourneert Datum uitgifte van His Persoon \ Reisdocument.
     *
     * @return Datum uitgifte.
     */
    public Datum getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * Retourneert Datum voorziene einde geldigheid van His Persoon \ Reisdocument.
     *
     * @return Datum voorziene einde geldigheid.
     */
    public Datum getDatumVoorzieneEindeGeldigheid() {
        return datumVoorzieneEindeGeldigheid;
    }

    /**
     * Retourneert Datum inhouding/vermissing van His Persoon \ Reisdocument.
     *
     * @return Datum inhouding/vermissing.
     */
    public Datum getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    /**
     * Retourneert Reden vervallen van His Persoon \ Reisdocument.
     *
     * @return Reden vervallen.
     */
    public RedenVervallenReisdocument getRedenVervallen() {
        return redenVervallen;
    }

}
