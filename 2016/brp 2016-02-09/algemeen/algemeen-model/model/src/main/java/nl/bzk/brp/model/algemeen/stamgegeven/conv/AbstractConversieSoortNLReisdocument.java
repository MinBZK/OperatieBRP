/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3NederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de conversie tussen Nederlands reisdocument (LO3) enerzijds, en soort Nederlands
 * reisdocument (BRP) anderzijds.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieSoortNLReisdocument {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3NederlandsReisdocumentAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr3511NLReisdoc"))
    @JsonProperty
    private LO3NederlandsReisdocumentAttribuut rubriek3511NederlandsReisdocument;

    @ManyToOne
    @JoinColumn(name = "SrtNLReisdoc")
    @Fetch(value = FetchMode.JOIN)
    private SoortNederlandsReisdocument soortNederlandsReisdocument;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieSoortNLReisdocument() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek3511NederlandsReisdocument rubriek3511NederlandsReisdocument van ConversieSoortNLReisdocument.
     * @param soortNederlandsReisdocument soortNederlandsReisdocument van ConversieSoortNLReisdocument.
     */
    protected AbstractConversieSoortNLReisdocument(
        final LO3NederlandsReisdocumentAttribuut rubriek3511NederlandsReisdocument,
        final SoortNederlandsReisdocument soortNederlandsReisdocument)
    {
        this.rubriek3511NederlandsReisdocument = rubriek3511NederlandsReisdocument;
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;

    }

    /**
     * Retourneert ID van Conversie soort NL reisdocument.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 3511 Nederlands reisdocument van Conversie soort NL reisdocument.
     *
     * @return Rubriek 3511 Nederlands reisdocument.
     */
    public final LO3NederlandsReisdocumentAttribuut getRubriek3511NederlandsReisdocument() {
        return rubriek3511NederlandsReisdocument;
    }

    /**
     * Retourneert Soort Nederlands reisdocument van Conversie soort NL reisdocument.
     *
     * @return Soort Nederlands reisdocument.
     */
    public final SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

}
