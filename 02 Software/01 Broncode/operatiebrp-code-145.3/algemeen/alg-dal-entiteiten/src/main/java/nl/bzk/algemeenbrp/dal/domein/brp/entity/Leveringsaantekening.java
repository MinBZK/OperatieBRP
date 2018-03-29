/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLInsert;

/**
 * Leveringsaantekening entiteit uit het prot-schema.
 */
@Entity
@Table(name = "levsaantek", schema = "prot")
@SQLInsert(sql = "insert into prot.levsaantek (admhnd, dataanvmaterieleperioderes, dateindematerieleperioderes, "
        + "tsaanvformeleperioderes, tseindeformeleperioderes, tsklaarzettenlev, dienst, scopepatroon, srtsynchronisatie, toeganglevsautorisatie, id) values "
        + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", check = ResultCheckStyle.NONE)
public class Leveringsaantekening {

    @Id
    @SequenceGenerator(name = "levsaantek_id_generator", sequenceName = "prot.seq_levsaantek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "levsaantek_id_generator")
    @Column(updatable = false)
    private Long id;
    @Column(name = "toeganglevsautorisatie")
    private Integer toegangLeveringsautorisatie;
    @Column(name = "dienst")
    private Integer dienst;
    @Column(name = "tsklaarzettenlev")
    private Timestamp datumTijdKlaarzettenLevering;
    @Column(name = "dataanvmaterieleperioderes")
    private Integer datumAanvangMaterielePeriodeResultaat;
    @Column(name = "dateindematerieleperioderes")
    private Integer datumEindeMaterielePeriodeResultaat;
    @Column(name = "tsaanvformeleperioderes")
    private Timestamp datumTijdAanvangFormelePeriodeResultaat;
    @Column(name = "tseindeformeleperioderes")
    private Timestamp datumTijdEindeFormelePeriodeResultaat;
    @Column(name = "admhnd")
    private Long administratieveHandeling;
    @Column(name = "srtsynchronisatie")
    private Integer soortSynchronisatieId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "scopepatroon")
    private ScopePatroon scopePatroon;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leveringsaantekening", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<LeveringsaantekeningPersoon> leveringsaantekeningPersoonSet = new LinkedHashSet<>(0);

    /**
     * JPA Default constructor.
     */
    protected Leveringsaantekening() {
    }

    /**
     * Maak een nieuw leveringsaantekening aan met verplichte velden.
     * @param toegangLeveringautorisatie de levering autorisatie
     * @param dienst de dienst
     * @param datumTijdKlaarzettenLevering datum/tijd klaarzetten levering
     * @param datumTijdEindeFormelePeriodeResultaat datum/tijd einde formele periode resultaat
     */
    public Leveringsaantekening(final Integer toegangLeveringautorisatie, final Integer dienst, final Timestamp datumTijdKlaarzettenLevering,
                                final Timestamp datumTijdEindeFormelePeriodeResultaat) {
        toegangLeveringsautorisatie = toegangLeveringautorisatie;
        this.dienst = dienst;
        this.datumTijdKlaarzettenLevering = Entiteit.timestamp(datumTijdKlaarzettenLevering);
        this.datumTijdEindeFormelePeriodeResultaat = Entiteit.timestamp(datumTijdEindeFormelePeriodeResultaat);
    }

    /**
     * @return het ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet het ID.
     * @param id het ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return de toegang leveringsautorisatie
     */
    public Integer getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    /**
     * Zet de toegang leveringsautorisatie.
     * @param toegangLeveringsautorisatie de toegang leveringsautorisatie
     */
    public void setToegangLeveringsautorisatie(final Integer toegangLeveringsautorisatie) {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
    }

    /**
     * @return de dienst
     */
    public Integer getDienst() {
        return dienst;
    }

    /**
     * Zet de dienst.
     * @param dienst de dienst
     */
    public void setDienst(final Integer dienst) {
        this.dienst = dienst;
    }

    /**
     * @return de datum/tijd klaarzetten levering
     */
    public Timestamp getDatumTijdKlaarzettenLevering() {
        return Entiteit.timestamp(datumTijdKlaarzettenLevering);
    }

    /**
     * Zet de datum/tijd klaarzetten levering.
     * @param datumTijdKlaarzettenLevering de datum/tijd klaarzetten levering
     */
    public void setDatumTijdKlaarzettenLevering(final Timestamp datumTijdKlaarzettenLevering) {
        this.datumTijdKlaarzettenLevering = Entiteit.timestamp(datumTijdKlaarzettenLevering);
    }

    /**
     * @return datum aanvang materiele periode resultaat
     */
    public Integer getDatumAanvangMaterielePeriodeResultaat() {
        return datumAanvangMaterielePeriodeResultaat;
    }

    /**
     * Zet de datum aanvang materiele periode resultaat.
     * @param datumAanvangMaterielePeriodeResultaat datum aanvang materiele periode resultaat
     */
    public void setDatumAanvangMaterielePeriodeResultaat(final Integer datumAanvangMaterielePeriodeResultaat) {
        this.datumAanvangMaterielePeriodeResultaat = datumAanvangMaterielePeriodeResultaat;
    }

    /**
     * @return de datum einde materiele periode resultaat
     */
    public Integer getDatumEindeMaterielePeriodeResultaat() {
        return datumEindeMaterielePeriodeResultaat;
    }

    /**
     * Zet de datum einde materiele periode resultaat.
     * @param datumEindeMaterielePeriodeResultaat de datum einde materiele periode resultaat
     */
    public void setDatumEindeMaterielePeriodeResultaat(final Integer datumEindeMaterielePeriodeResultaat) {
        this.datumEindeMaterielePeriodeResultaat = datumEindeMaterielePeriodeResultaat;
    }

    /**
     * @return de datum/tijd aanvang formele periode resultaat
     */
    public Timestamp getDatumTijdAanvangFormelePeriodeResultaat() {
        return Entiteit.timestamp(datumTijdAanvangFormelePeriodeResultaat);
    }

    /**
     * Zet de datum/tijd aanvang formele periode resultaat.
     * @param datumTijdAanvangFormelePeriodeResultaat de datum/tijd aanvang formele periode resultaat
     */
    public void setDatumTijdAanvangFormelePeriodeResultaat(final Timestamp datumTijdAanvangFormelePeriodeResultaat) {
        this.datumTijdAanvangFormelePeriodeResultaat = Entiteit.timestamp(datumTijdAanvangFormelePeriodeResultaat);
    }

    /**
     * @return de datum/tijd einde formele periode resultaat
     */
    public Timestamp getDatumTijdEindeFormelePeriodeResultaat() {
        return Entiteit.timestamp(datumTijdEindeFormelePeriodeResultaat);
    }

    /**
     * Zet de datum/tijd einde formele periode resultaat.
     * @param datumTijdEindeFormelePeriodeResultaat de datum/tijd einde formele periode resultaat
     */
    public void setDatumTijdEindeFormelePeriodeResultaat(final Timestamp datumTijdEindeFormelePeriodeResultaat) {
        this.datumTijdEindeFormelePeriodeResultaat = Entiteit.timestamp(datumTijdEindeFormelePeriodeResultaat);
    }

    /**
     * @return de administratieve handeling
     */
    public Long getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de administratieve handeling.
     * @param administratieveHandeling de administratieve handeling
     */
    public void setAdministratieveHandeling(final Long administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * @return het soort synchronisatie
     */
    public SoortSynchronisatie getSoortSynchronisatie() {
        return SoortSynchronisatie.parseId(soortSynchronisatieId);
    }

    /**
     * Zet het soort synchronisatie.
     * @param soortSynchronisatie het soort synchronisatie
     */
    public void setSoortSynchronisatie(final SoortSynchronisatie soortSynchronisatie) {
        if (soortSynchronisatie != null) {
            soortSynchronisatieId = soortSynchronisatie.getId();
        }
    }

    /**
     * Geef de waarde van scopePatroon.
     * @return scopePatroon
     */
    public ScopePatroon getScopePatroon() {
        return scopePatroon;
    }

    /**
     * Zet de waarde van scopePatroon.
     * @param scopePatroon scopePatroon
     */
    public void setScopePatroon(final ScopePatroon scopePatroon) {
        this.scopePatroon = scopePatroon;
    }

    /**
     * Geef de waarde van leveringsaantekeningPersoonSet.
     * @return leveringsaantekeningPersoonSet
     */
    public Set<LeveringsaantekeningPersoon> getLeveringsaantekeningPersoonSet() {
        return leveringsaantekeningPersoonSet;
    }

    /**
     * Zet de waarde van leveringsaantekeningPersoonSet.
     * @param leveringsaantekeningPersoonSet leveringsaantekeningPersoonSet
     */
    public void setLeveringsaantekeningPersoonSet(final Set<LeveringsaantekeningPersoon> leveringsaantekeningPersoonSet) {
        this.leveringsaantekeningPersoonSet = leveringsaantekeningPersoonSet;
    }

    /**
     * Voegt een LeveringsaantekeningPersoon toe aan deze Leveringsaantekening.
     * @param leveringsaantekeningPersoon wat moet worden toegevoegd
     */
    public void addLeveringsaantekeningPersoon(final LeveringsaantekeningPersoon leveringsaantekeningPersoon) {
        leveringsaantekeningPersoon.setLeveringsaantekening(this);
        leveringsaantekeningPersoonSet.add(leveringsaantekeningPersoon);
    }

    /**
     * Verwijderd een LeveringsaantekeningPersoon uit deze Leveringsaantekening.
     * @param leveringsaantekeningPersoon wat moet worden verwijderd
     * @return true, als de leveringsaantekening is verwijderd
     */
    public boolean removeLeveringsaantekeningPersoon(final LeveringsaantekeningPersoon leveringsaantekeningPersoon) {
        return leveringsaantekeningPersoonSet.remove(leveringsaantekeningPersoon);
    }
}
