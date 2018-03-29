/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persbijhgem database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persbijhouding", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel"}))
public class PersoonBijhoudingHistorie extends AbstractMaterieleHistorie implements Serializable {

    /**
     * Veldnaam van bijhoudingsaardId tbv verschil verwerking.
     */
    public static final String BIJHOUDINGSAARD_ID = "bijhoudingsaardId";
    /**
     * Veldnaam van nadereBijhoudingsaardId tbv verschil verwerking.
     */
    public static final String NADERE_BIJHOUDINGSAARD_ID = "nadereBijhoudingsaardId";

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persbijhouding_id_generator", sequenceName = "kern.seq_his_persbijhouding", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persbijhouding_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "bijhaard", nullable = false)
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private int bijhoudingsaardId;

    @Column(name = "naderebijhaard", nullable = false)
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private int nadereBijhoudingsaardId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bijhpartij", nullable = false)
    private Partij bijhoudingspartij;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonBijhoudingHistorie() {}

    /**
     * Maak een nieuwe persoon bijhouding historie.
     *
     * @param persoon persoon
     * @param partij partij
     * @param bijhoudingsaard bijhoudingsaard
     * @param nadereBijhoudingsaard nadere bijhoudingsaard
     */
    public PersoonBijhoudingHistorie(final Persoon persoon, final Partij partij, final Bijhoudingsaard bijhoudingsaard,
            final NadereBijhoudingsaard nadereBijhoudingsaard) {
        setPersoon(persoon);
        setPartij(partij);
        setBijhoudingsaard(bijhoudingsaard);
        setNadereBijhoudingsaard(nadereBijhoudingsaard);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public PersoonBijhoudingHistorie(final PersoonBijhoudingHistorie ander) {
        super(ander);
        bijhoudingsaardId = ander.getBijhoudingsaard().getId();
        nadereBijhoudingsaardId = ander.getNadereBijhoudingsaard().getId();
        bijhoudingspartij = ander.getPartij();
        persoon = ander.getPersoon();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonBijhoudingHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonBijhoudingHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van bijhoudingsaard van PersoonBijhoudingHistorie.
     *
     * @return de waarde van bijhoudingsaard van PersoonBijhoudingHistorie
     */
    public Bijhoudingsaard getBijhoudingsaard() {
        return Bijhoudingsaard.parseId(bijhoudingsaardId);
    }

    /**
     * Zet de waarden voor bijhoudingsaard van PersoonBijhoudingHistorie.
     *
     * @param bijhoudingsaard de nieuwe waarde voor bijhoudingsaard van PersoonBijhoudingHistorie
     */
    public void setBijhoudingsaard(final Bijhoudingsaard bijhoudingsaard) {
        ValidationUtils.controleerOpNullWaarden("bijhoudingsaard mag niet null zijn", bijhoudingsaard);
        bijhoudingsaardId = bijhoudingsaard.getId();
    }

    /**
     * Geef de waarde van nadere bijhoudingsaard van PersoonBijhoudingHistorie.
     *
     * @return de waarde van nadere bijhoudingsaard van PersoonBijhoudingHistorie
     */
    public NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return NadereBijhoudingsaard.parseId(nadereBijhoudingsaardId);
    }

    /**
     * Zet de waarden voor nadere bijhoudingsaard van PersoonBijhoudingHistorie.
     *
     * @param nadereBijhoudingsaard de nieuwe waarde voor nadere bijhoudingsaard van
     *        PersoonBijhoudingHistorie
     */
    public void setNadereBijhoudingsaard(final NadereBijhoudingsaard nadereBijhoudingsaard) {
        ValidationUtils.controleerOpNullWaarden("nadereBijhoudingsaard mag niet null zijn", nadereBijhoudingsaard);
        nadereBijhoudingsaardId = nadereBijhoudingsaard.getId();
    }

    /**
     * Geef de waarde van partij van PersoonBijhoudingHistorie.
     *
     * @return de waarde van partij van PersoonBijhoudingHistorie
     */
    public Partij getPartij() {
        return bijhoudingspartij;
    }

    /**
     * Zet de waarden voor partij van PersoonBijhoudingHistorie.
     *
     * @param partij de nieuwe waarde voor partij van PersoonBijhoudingHistorie
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        bijhoudingspartij = partij;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonBijhoudingHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonBijhoudingHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    @Override
    public final PersoonBijhoudingHistorie kopieer() {
        return new PersoonBijhoudingHistorie(this);
    }

    @Override
    public boolean isDegGelijkAanDagToegestaan() {
        return true;
    }

    @Override
    public boolean moetHistorieAaneengeslotenZijn() {
        return true;
    }
}
