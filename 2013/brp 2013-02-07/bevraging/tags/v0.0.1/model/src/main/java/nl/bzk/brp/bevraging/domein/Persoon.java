/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import java.util.UUID;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 * 
 * De populatie personen bestaat uit ingeschrevenen. Het betreft zowel ingezetenen
 * als niet-ingezetenen. Zie ook groep "Bijhoudingsverantwoordelijkheid" en objecttype
 * "Betrokkenheid".
 * 
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor
 * dit objecttype de naam "Natuurlijk persoon" te gebruiken. Binnen de context van
 * BRP hebben we het bij het hanteren van de term Persoon echter nooit over
 * niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon
 * is verder dermate gebruikelijk binnen de context van BRP, dat ervoor gekozen is
 * deze naam te blijven hanteren. We spreken dus over Persoon en niet over "Natuurlijk persoon".
 * 
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken
 * we in het logisch & operationeel model (maar dus NIET in de gegevensset) het
 * construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die wellicht
 * wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.RvdP 27 juni 2011
 */
@Entity
@Table(name = "Pers", schema = "Kern")
@Access(AccessType.FIELD)
public class Persoon {

    @SequenceGenerator(name = "PERSOON_SEQUENCE_GENERATOR", sequenceName = "Kern.seq_Pers")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOON_SEQUENCE_GENERATOR")
    private Long                id;
    @Column(name = "IndMaterieel")
    private Boolean             materieel                                = true;
    @Column(name = "IndIDsMaterieel")
    private Boolean             idsMaterieel                             = true;
    @Column(name = "IndGeslachtsaandMaterieel")
    private Boolean             geslachtsaanduidingMaterieel             = false;
    @Column(name = "IndSamengesteldeNaamMateriee")
    private Boolean             samengesteldeNaamMaterieel               = false;
    @Column(name = "IndAanschrMaterieel")
    private Boolean             aanschrijvingMaterieel                   = false;
    @Column(name = "IndGeboorteMaterieel")
    private Boolean             geboorteMaterieel                        = false;
    @Column(name = "IndOverlijdenMaterieel")
    private Boolean             overlijdenMaterieel                      = false;
    @Column(name = "IndVerblijfsrMaterieel")
    private Boolean             verblijfsrechtMaterieel                  = false;
    @Column(name = "IndUitslNLKiesrMaterieel")
    private Boolean             uitsluitingNLMaterieel                   = false;
    @Column(name = "IndEUVerkiezingenMaterieel")
    private Boolean             euVerkiezingenMaterieel                  = false;
    @Column(name = "IndBijhverantwoordelijkheidM")
    private Boolean             bijhoudingsVerantwoordelijkheidMaterieel = false;
    @Column(name = "IndOpschortingMaterieel")
    private Boolean             opschortingMaterieel                     = false;
    @Column(name = "IndBijhgemMaterieel")
    private Boolean             bijhoudingsGemeenteMaterieel             = false;
    @Column(name = "IndPKMaterieel")
    private Boolean             persoonsKaartMaterieel                   = false;
    @Column(name = "IndImmigratieMaterieel")
    private Boolean             immigratieMaterieel                      = false;
    @Column(name = "IndInschrMaterieel")
    private Boolean             inschrijvingMaterieel                    = false;
    @Column(name = "IndAfgeleidAdministratiefMat")
    private Boolean             afgeleidAdministratieMaterieel           = false;

    @Enumerated
    @Column(name = "srt")
    private SoortPersoon        soortPersoon;
    @Column(name = "bsn")
    private Integer             bsn;
    @Column(name = "anr")
    private Integer             anr;
    @Column(name = "voornamen")
    private String              voornamen;
    @Column(name = "voorvoegsel")
    private String              voorvoegsel;
    @Column(name = "scheidingsteken")
    private String              scheidingsTeken;
    @Column(name = "geslnaam")
    private String              geslachtsnaam;
    @Column(name = "geslachtsaand")
    private GeslachtsAanduiding geslachtsAanduiding;
    @Column(name = "datgeboorte")
    private Integer             geboorteDatum;

    /**
     * No-arg constructor voor JPA.
     */
    protected Persoon() {
    }

    /**
     * Constructor voor nieuwe instanties.
     * 
     * @param soort het soort persoon dat wordt gecreeerd.
     */
    public Persoon(final SoortPersoon soort) {
        this.soortPersoon = soort;
    }

    public final Long getId() {
        return id;
    }

    public final SoortPersoon getSoortPersoon() {
        return soortPersoon;
    }

    public final Integer getBsn() {
        return bsn;
    }

    public final void setBsn(final Integer bsn) {
        this.bsn = bsn;
    }

    public final Integer getAnr() {
        return anr;
    }

    public final String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    public final void setGeslachtsnaam(final String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public final String getVoornamen() {
        return voornamen;
    }

    public final String getVoorvoegsel() {
        return voorvoegsel;
    }

    public final String getScheidingsTeken() {
        return scheidingsTeken;
    }

    public final GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    public final Integer getGeboorteDatum() {
        return geboorteDatum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

}
