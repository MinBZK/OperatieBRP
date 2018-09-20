/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerKleinAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonCacheStandaardGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonCacheStandaardGroepModel implements PersoonCacheStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = VersienummerKleinAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Versienr"))
    @JsonProperty
    private VersienummerKleinAttribuut versienummer;

    @Embedded
    @AttributeOverride(name = ChecksumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "PersHistorieVolledigChecksum"))
    @JsonProperty
    private ChecksumAttribuut persoonHistorieVolledigChecksum;

    @Embedded
    @AttributeOverride(name = ByteaopslagAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "PersHistorieVolledigGegevens"))
    @JsonProperty
    private ByteaopslagAttribuut persoonHistorieVolledigGegevens;

    @Embedded
    @AttributeOverride(name = ChecksumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "AfnemerindicatieChecksum"))
    @JsonProperty
    private ChecksumAttribuut afnemerindicatieChecksum;

    @Embedded
    @AttributeOverride(name = ByteaopslagAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "AfnemerindicatieGegevens"))
    @JsonProperty
    private ByteaopslagAttribuut afnemerindicatieGegevens;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonCacheStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param versienummer versienummer van Standaard.
     * @param persoonHistorieVolledigChecksum persoonHistorieVolledigChecksum van Standaard.
     * @param persoonHistorieVolledigGegevens persoonHistorieVolledigGegevens van Standaard.
     * @param afnemerindicatieChecksum afnemerindicatieChecksum van Standaard.
     * @param afnemerindicatieGegevens afnemerindicatieGegevens van Standaard.
     */
    public AbstractPersoonCacheStandaardGroepModel(
        final VersienummerKleinAttribuut versienummer,
        final ChecksumAttribuut persoonHistorieVolledigChecksum,
        final ByteaopslagAttribuut persoonHistorieVolledigGegevens,
        final ChecksumAttribuut afnemerindicatieChecksum,
        final ByteaopslagAttribuut afnemerindicatieGegevens)
    {
        this.versienummer = versienummer;
        this.persoonHistorieVolledigChecksum = persoonHistorieVolledigChecksum;
        this.persoonHistorieVolledigGegevens = persoonHistorieVolledigGegevens;
        this.afnemerindicatieChecksum = afnemerindicatieChecksum;
        this.afnemerindicatieGegevens = afnemerindicatieGegevens;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersienummerKleinAttribuut getVersienummer() {
        return versienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChecksumAttribuut getPersoonHistorieVolledigChecksum() {
        return persoonHistorieVolledigChecksum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteaopslagAttribuut getPersoonHistorieVolledigGegevens() {
        return persoonHistorieVolledigGegevens;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChecksumAttribuut getAfnemerindicatieChecksum() {
        return afnemerindicatieChecksum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteaopslagAttribuut getAfnemerindicatieGegevens() {
        return afnemerindicatieGegevens;
    }

    /**
     * Zet Versienummer van Standaard.
     *
     * @param versienummer Versienummer.
     */
    public void setVersienummer(final VersienummerKleinAttribuut versienummer) {
        this.versienummer = versienummer;
    }

    /**
     * Zet Persoon historie volledig checksum van Standaard.
     *
     * @param persoonHistorieVolledigChecksum Persoon historie volledig checksum.
     */
    public void setPersoonHistorieVolledigChecksum(final ChecksumAttribuut persoonHistorieVolledigChecksum) {
        this.persoonHistorieVolledigChecksum = persoonHistorieVolledigChecksum;
    }

    /**
     * Zet Persoon historie volledig gegevens van Standaard.
     *
     * @param persoonHistorieVolledigGegevens Persoon historie volledig gegevens.
     */
    public void setPersoonHistorieVolledigGegevens(final ByteaopslagAttribuut persoonHistorieVolledigGegevens) {
        this.persoonHistorieVolledigGegevens = persoonHistorieVolledigGegevens;
    }

    /**
     * Zet Afnemerindicatie checksum van Standaard.
     *
     * @param afnemerindicatieChecksum Afnemerindicatie checksum.
     */
    public void setAfnemerindicatieChecksum(final ChecksumAttribuut afnemerindicatieChecksum) {
        this.afnemerindicatieChecksum = afnemerindicatieChecksum;
    }

    /**
     * Zet Afnemerindicatie gegevens van Standaard.
     *
     * @param afnemerindicatieGegevens Afnemerindicatie gegevens.
     */
    public void setAfnemerindicatieGegevens(final ByteaopslagAttribuut afnemerindicatieGegevens) {
        this.afnemerindicatieGegevens = afnemerindicatieGegevens;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (versienummer != null) {
            attributen.add(versienummer);
        }
        if (persoonHistorieVolledigChecksum != null) {
            attributen.add(persoonHistorieVolledigChecksum);
        }
        if (persoonHistorieVolledigGegevens != null) {
            attributen.add(persoonHistorieVolledigGegevens);
        }
        if (afnemerindicatieChecksum != null) {
            attributen.add(afnemerindicatieChecksum);
        }
        if (afnemerindicatieGegevens != null) {
            attributen.add(afnemerindicatieGegevens);
        }
        return attributen;
    }

}
