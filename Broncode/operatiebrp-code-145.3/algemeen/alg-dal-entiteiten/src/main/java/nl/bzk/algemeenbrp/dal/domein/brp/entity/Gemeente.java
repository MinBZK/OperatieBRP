/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the gem database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "gem", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "Gemeente" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from Gemeente g join fetch g.partij p left join fetch p.hisPartijen "
        + "left join fetch p.partijBijhoudingHistorieSet left join fetch p.partijRolSet")
public class Gemeente extends AbstractEntiteit implements Serializable, DynamischeStamtabelMetGeldigheid {
    private static final long serialVersionUID = 1L;
    private static final String NAAM_MAG_NIET_NULL_ZIJN = "naam mag niet null zijn";
    private static final String NAAM_MAG_GEEN_LEGE_STRING_ZIJN = "naam mag geen lege string zijn";
    private static final String PARTIJ_MAG_NIET_NULL_ZIJN = "partij mag niet null zijn";
    private static final int CODE_LENGTE = 4;

    @Id
    @SequenceGenerator(name = "gemeente_id_generator", sequenceName = "kern.seq_gem", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gemeente_id_generator")
    @Column(nullable = false, updatable = false)
    private Short id;

    @Column(nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;

    @Column(nullable = false)
    private String naam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voortzettendegem")
    private Gemeente voortzettendeGemeente;

    /**
     * JPA default constructor.
     */
    protected Gemeente() {
    }

    /**
     * Maak een nieuwe gemeente.
     * @param id id
     * @param naam naam
     * @param code code
     * @param partij partij
     */
    public Gemeente(final Short id, final String naam, final String code, final Partij partij) {
        this.id = id;
        setNaam(naam);
        setCode(code);
        setPartij(partij);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Gemeente.
     * @param id de nieuwe waarde voor id van Gemeente
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code van Gemeente.
     * @return de waarde van code van Gemeente
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van Gemeente.
     * @param code de nieuwe waarde voor code van Gemeente
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLengte("code moet een lengte van 4 hebben", code, CODE_LENGTE);
        this.code = code;
    }

    /**
     * Geef de waarde van naam van Gemeente.
     * @return de waarde van naam van Gemeente
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van Gemeente.
     * @param naam de nieuwe waarde voor naam van Gemeente
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        ValidationUtils.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van partij van Gemeente.
     * @return de waarde van partij van Gemeente
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van Gemeente.
     * @param partij de nieuwe waarde voor partij van Gemeente
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid van Gemeente.
     * @return de waarde van datum aanvang geldigheid van Gemeente
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarden voor datum aanvang geldigheid van Gemeente.
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van Gemeente
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid van Gemeente.
     * @return de waarde van datum einde geldigheid van Gemeente
     */
    @Override
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarden voor datum einde geldigheid van Gemeente.
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van Gemeente
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van voortzettende gemeente van Gemeente.
     * @return de waarde van voortzettende gemeente van Gemeente
     */
    public Gemeente getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    /**
     * Zet de waarden voor voortzettende gemeente van Gemeente.
     * @param voortzettendeGemeente de nieuwe waarde voor voortzettende gemeente van Gemeente
     */
    public void setVoortzettendeGemeente(final Gemeente voortzettendeGemeente) {
        this.voortzettendeGemeente = voortzettendeGemeente;
    }

    /**
     * Als de naam van twee partijen gelijk zijn dan worden ze als inhoudelijk gelijk beschouwd.
     * @param andereGemeente de andere gemeente waaarme vergeleken wordt
     * @return true als de code van deze gemeente gelijk is aan de andere gemeente, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Gemeente andereGemeente) {
        return this == andereGemeente || andereGemeente != null && getCode().equals(andereGemeente.getCode());
    }

    /**
     * Geeft aan of de meegegeven partij van een opvolgende gemeente is.
     * Hij controleert net zolang tot een gemeente geen opvolgende gemeente meer heeft
     * @param partij de partij
     * @return true als partij overeenkomt met een opvolgende gemeente.
     */
    public boolean isPartijVoortzettendeGemeentePartij(final Partij partij) {
        return voortzettendeGemeente != null && (voortzettendeGemeente.getPartij().getCode().equals(partij.getCode()) || voortzettendeGemeente
                .isPartijVoortzettendeGemeentePartij(partij));
    }
}
