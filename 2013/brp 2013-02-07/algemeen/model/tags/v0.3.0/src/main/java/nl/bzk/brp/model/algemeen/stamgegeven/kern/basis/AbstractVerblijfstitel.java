/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.CodeVerblijfstitel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * Categorisatie van de Verblijfstitel van Persoon.
 *
 * 1. Naam aangepast: verblijfsrecht gaat verder dan de door de IND medegedeelde verblijfstitel, die overigens niet
 * alleen over verblijf gaat maar ook over het recht om arbeid te verrichten. Door te kiezen voor verblijfstitel (term
 * OOK gebruikt in GBA LO 3.7) en niet voor verblijfsrecht (term komt vooral voor in de HUP), blijft verblijfsrecht als
 * groep ook geschikt voor bijv. de geprivilegieerden en de militairen en anderen die verblijfsrecht hebben. RvdP 27
 * november 2012.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractVerblijfstitel extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Short                        iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private CodeVerblijfstitel           code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum                        datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum                        datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractVerblijfstitel() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Verblijfstitel.
     * @param omschrijving omschrijving van Verblijfstitel.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Verblijfstitel.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Verblijfstitel.
     */
    protected AbstractVerblijfstitel(final CodeVerblijfstitel code, final OmschrijvingEnumeratiewaarde omschrijving,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        this.code = code;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Verblijfstitel.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Verblijfstitel.
     *
     * @return Code.
     */
    public CodeVerblijfstitel getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Verblijfstitel.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Verblijfstitel.
     *
     * @return datum aanvang geldigheid voor Verblijfstitel.
     */
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Verblijfstitel.
     *
     * @return datum einde geldigheid voor Verblijfstitel.
     */
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
