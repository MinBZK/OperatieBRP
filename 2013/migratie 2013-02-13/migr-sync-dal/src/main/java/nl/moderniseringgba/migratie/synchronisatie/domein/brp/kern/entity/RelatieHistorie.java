/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the his_relatie database table.
 * 
 */
@Entity
@Table(name = "his_relatie", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class RelatieHistorie implements Serializable, FormeleHistorie {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_RELATIE_ID_GENERATOR", sequenceName = "KERN.SEQ_HIS_RELATIE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_RELATIE_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "blplaatsaanv", length = 40)
    private String buitenlandsePlaatsAanvang;

    @Column(name = "blplaatseinde", length = 40)
    private String buitenlandsePlaatsEinde;

    @Column(name = "blregioaanv", length = 35)
    private String buitenlandseRegioAanvang;

    @Column(name = "blregioeinde", length = 35)
    private String buitenlandseRegioEinde;

    @Column(name = "dataanv", precision = 8)
    private BigDecimal datumAanvang;

    @Column(name = "dateinde", precision = 8)
    private BigDecimal datumEinde;

    @Column(name = "omslocaanv", length = 40)
    private String omschrijvingLocatieAanvang;

    @Column(name = "omsloceinde", length = 40)
    private String omschrijvingLocatieEinde;

    @Column(name = "tsreg")
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieverval")
    private BRPActie actieVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieinh")
    private BRPActie actieInhoud;

    // bi-directional many-to-one association to Land
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landaanv")
    private Land landAanvang;

    // bi-directional many-to-one association to Land
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landeinde")
    private Land landEinde;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemaanv")
    private Partij gemeenteAanvang;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemeinde")
    private Partij gemeenteEinde;

    // bi-directional many-to-one association to Plaats
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "wplaanv")
    private Plaats woonplaatsAanvang;

    // bi-directional many-to-one association to Plaats
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "wpleinde")
    private Plaats woonplaatsEinde;

    // bi-directional many-to-one association to RedenBeeindigingRelatie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    // bi-directional many-to-one association to Relatie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "relatie")
    private Relatie relatie;

    public RelatieHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    public BigDecimal getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final BigDecimal datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public BigDecimal getDatumEinde() {
        return datumEinde;
    }

    public void setDatumEinde(final BigDecimal datumEinde) {
        this.datumEinde = datumEinde;
    }

    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    @Override
    public Timestamp getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    @Override
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    @Override
    public Timestamp getDatumTijdVerval() {
        return datumTijdVerval;
    }

    @Override
    public void setDatumTijdVerval(final Timestamp datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

    @Override
    public BRPActie getActieVerval() {
        return actieVerval;
    }

    @Override
    public void setActieVerval(final BRPActie actieVerval) {
        this.actieVerval = actieVerval;
    }

    @Override
    public BRPActie getActieInhoud() {
        return actieInhoud;
    }

    @Override
    public void setActieInhoud(final BRPActie actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    public Land getLandAanvang() {
        return landAanvang;
    }

    public void setLandAanvang(final Land landAanvang) {
        this.landAanvang = landAanvang;
    }

    public Land getLandEinde() {
        return landEinde;
    }

    public void setLandEinde(final Land landEinde) {
        this.landEinde = landEinde;
    }

    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    public void setGemeenteAanvang(final Partij gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    public void setGemeenteEinde(final Partij gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    public Plaats getWoonplaatsAanvang() {
        return woonplaatsAanvang;
    }

    public void setWoonplaatsAanvang(final Plaats woonplaatsAanvang) {
        this.woonplaatsAanvang = woonplaatsAanvang;
    }

    public Plaats getWoonplaatsEinde() {
        return woonplaatsEinde;
    }

    public void setWoonplaatsEinde(final Plaats woonplaatsEinde) {
        this.woonplaatsEinde = woonplaatsEinde;
    }

    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(final Relatie relatie) {
        this.relatie = relatie;
    }

    /**
     * @param andereRelatieHistorie
     * @return
     */
    public boolean isInhoudelijkGelijkAan(final RelatieHistorie andereRelatieHistorie) {
        if (this == andereRelatieHistorie) {
            return true;
        }
        if (andereRelatieHistorie == null) {
            return false;
        }
        if (actieInhoud == null) {
            if (andereRelatieHistorie.actieInhoud != null) {
                return false;
            }
        } else if (!actieInhoud.isInhoudelijkGelijkAan(andereRelatieHistorie.actieInhoud)) {
            return false;
        }
        if (actieVerval == null) {
            if (andereRelatieHistorie.actieVerval != null) {
                return false;
            }
        } else if (!actieVerval.isInhoudelijkGelijkAan(andereRelatieHistorie.actieVerval)) {
            return false;
        }
        if (buitenlandsePlaatsAanvang == null) {
            if (andereRelatieHistorie.buitenlandsePlaatsAanvang != null) {
                return false;
            }
        } else if (!buitenlandsePlaatsAanvang.equals(andereRelatieHistorie.buitenlandsePlaatsAanvang)) {
            return false;
        }
        if (buitenlandsePlaatsEinde == null) {
            if (andereRelatieHistorie.buitenlandsePlaatsEinde != null) {
                return false;
            }
        } else if (!buitenlandsePlaatsEinde.equals(andereRelatieHistorie.buitenlandsePlaatsEinde)) {
            return false;
        }
        if (buitenlandseRegioAanvang == null) {
            if (andereRelatieHistorie.buitenlandseRegioAanvang != null) {
                return false;
            }
        } else if (!buitenlandseRegioAanvang.equals(andereRelatieHistorie.buitenlandseRegioAanvang)) {
            return false;
        }
        if (buitenlandseRegioEinde == null) {
            if (andereRelatieHistorie.buitenlandseRegioEinde != null) {
                return false;
            }
        } else if (!buitenlandseRegioEinde.equals(andereRelatieHistorie.buitenlandseRegioEinde)) {
            return false;
        }
        if (datumAanvang == null) {
            if (andereRelatieHistorie.datumAanvang != null) {
                return false;
            }
        } else if (!datumAanvang.equals(andereRelatieHistorie.datumAanvang)) {
            return false;
        }
        if (datumEinde == null) {
            if (andereRelatieHistorie.datumEinde != null) {
                return false;
            }
        } else if (!datumEinde.equals(andereRelatieHistorie.datumEinde)) {
            return false;
        }
        if (datumTijdRegistratie == null) {
            if (andereRelatieHistorie.datumTijdRegistratie != null) {
                return false;
            }
        } else if (!datumTijdRegistratie.equals(andereRelatieHistorie.datumTijdRegistratie)) {
            return false;
        }
        if (datumTijdVerval == null) {
            if (andereRelatieHistorie.datumTijdVerval != null) {
                return false;
            }
        } else if (!datumTijdVerval.equals(andereRelatieHistorie.datumTijdVerval)) {
            return false;
        }
        if (gemeenteAanvang == null) {
            if (andereRelatieHistorie.gemeenteAanvang != null) {
                return false;
            }
        } else if (!gemeenteAanvang.isInhoudelijkGelijkAan(andereRelatieHistorie.gemeenteAanvang)) {
            return false;
        }
        if (gemeenteEinde == null) {
            if (andereRelatieHistorie.gemeenteEinde != null) {
                return false;
            }
        } else if (!gemeenteEinde.isInhoudelijkGelijkAan(andereRelatieHistorie.gemeenteEinde)) {
            return false;
        }
        if (landAanvang == null) {
            if (andereRelatieHistorie.landAanvang != null) {
                return false;
            }
        } else if (!landAanvang.isInhoudelijkGelijkAan(andereRelatieHistorie.landAanvang)) {
            return false;
        }
        if (landEinde == null) {
            if (andereRelatieHistorie.landEinde != null) {
                return false;
            }
        } else if (!landEinde.isInhoudelijkGelijkAan(andereRelatieHistorie.landEinde)) {
            return false;
        }
        if (omschrijvingLocatieAanvang == null) {
            if (andereRelatieHistorie.omschrijvingLocatieAanvang != null) {
                return false;
            }
        } else if (!omschrijvingLocatieAanvang.equals(andereRelatieHistorie.omschrijvingLocatieAanvang)) {
            return false;
        }
        if (omschrijvingLocatieEinde == null) {
            if (andereRelatieHistorie.omschrijvingLocatieEinde != null) {
                return false;
            }
        } else if (!omschrijvingLocatieEinde.equals(andereRelatieHistorie.omschrijvingLocatieEinde)) {
            return false;
        }
        if (redenBeeindigingRelatie == null) {
            if (andereRelatieHistorie.redenBeeindigingRelatie != null) {
                return false;
            }
        } else if (!redenBeeindigingRelatie.isInhoudelijkGelijkAan(andereRelatieHistorie.redenBeeindigingRelatie)) {
            return false;
        }
        if (woonplaatsAanvang == null) {
            if (andereRelatieHistorie.woonplaatsAanvang != null) {
                return false;
            }
        } else if (!woonplaatsAanvang.isInhoudelijkGelijkAan(andereRelatieHistorie.woonplaatsAanvang)) {
            return false;
        }
        if (woonplaatsEinde == null) {
            if (andereRelatieHistorie.woonplaatsEinde != null) {
                return false;
            }
        } else if (!woonplaatsEinde.isInhoudelijkGelijkAan(andereRelatieHistorie.woonplaatsEinde)) {
            return false;
        }
        return true;
    }
}
