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
 * The persistent class for the his_betrouderlijkgezag database table.
 * 
 */
@Entity
@Table(name = "his_betrouderlijkgezag", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class BetrokkenheidOuderlijkGezagHistorie implements Serializable, FormeleHistorie, MaterieleHistorie {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_BETROUDERLIJKGEZAG_ID_GENERATOR",
            sequenceName = "KERN.SEQ_HIS_BETROUDERLIJKGEZAG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_BETROUDERLIJKGEZAG_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dataanvgel", precision = 8)
    private BigDecimal datumAanvangGeldigheid;

    @Column(name = "dateindegel", precision = 8)
    private BigDecimal datumEindeGeldigheid;

    @Column(name = "indouderheeftgezag", nullable = false)
    private Boolean indicatieOuderHeeftGezag;

    @Column(name = "tsreg")
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieaanpgel")
    private BRPActie actieAanpassingGeldigheid;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieverval")
    private BRPActie actieVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieinh")
    private BRPActie actieInhoud;

    // bi-directional many-to-one association to Betrokkenheid
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "betr")
    private Betrokkenheid betrokkenheid;

    public BetrokkenheidOuderlijkGezagHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public BigDecimal getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    @Override
    public void setDatumAanvangGeldigheid(final BigDecimal datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    @Override
    public BigDecimal getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public void setDatumEindeGeldigheid(final BigDecimal datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public Boolean getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    public void setIndicatieOuderHeeftGezag(final Boolean indicatieOuderHeeftGezag) {
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
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
    public BRPActie getActieAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    @Override
    public void setActieAanpassingGeldigheid(final BRPActie actieAanpassingGeldigheid) {
        this.actieAanpassingGeldigheid = actieAanpassingGeldigheid;
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

    public Betrokkenheid getBetrokkenheid() {
        return betrokkenheid;
    }

    public void setBetrokkenheid(final Betrokkenheid betrokkenheid) {
        this.betrokkenheid = betrokkenheid;
    }

    /**
     * @param andereBetrokkenheidOuderlijkGezagHistorie
     * @return
     */
    public boolean isInhoudelijkGelijkAan(
            final BetrokkenheidOuderlijkGezagHistorie andereBetrokkenheidOuderlijkGezagHistorie) {
        if (this == andereBetrokkenheidOuderlijkGezagHistorie) {
            return true;
        }
        if (andereBetrokkenheidOuderlijkGezagHistorie == null) {
            return false;
        }
        if (actieAanpassingGeldigheid == null) {
            if (andereBetrokkenheidOuderlijkGezagHistorie.actieAanpassingGeldigheid != null) {
                return false;
            }
        } else if (!actieAanpassingGeldigheid
                .isInhoudelijkGelijkAan(andereBetrokkenheidOuderlijkGezagHistorie.actieAanpassingGeldigheid)) {
            return false;
        }
        if (actieInhoud == null) {
            if (andereBetrokkenheidOuderlijkGezagHistorie.actieInhoud != null) {
                return false;
            }
        } else if (!actieInhoud.isInhoudelijkGelijkAan(andereBetrokkenheidOuderlijkGezagHistorie.actieInhoud)) {
            return false;
        }
        if (actieVerval == null) {
            if (andereBetrokkenheidOuderlijkGezagHistorie.actieVerval != null) {
                return false;
            }
        } else if (!actieVerval.isInhoudelijkGelijkAan(andereBetrokkenheidOuderlijkGezagHistorie.actieVerval)) {
            return false;
        }
        if (datumAanvangGeldigheid == null) {
            if (andereBetrokkenheidOuderlijkGezagHistorie.datumAanvangGeldigheid != null) {
                return false;
            }
        } else if (!datumAanvangGeldigheid.equals(andereBetrokkenheidOuderlijkGezagHistorie.datumAanvangGeldigheid)) {
            return false;
        }
        if (datumEindeGeldigheid == null) {
            if (andereBetrokkenheidOuderlijkGezagHistorie.datumEindeGeldigheid != null) {
                return false;
            }
        } else if (!datumEindeGeldigheid.equals(andereBetrokkenheidOuderlijkGezagHistorie.datumEindeGeldigheid)) {
            return false;
        }
        if (datumTijdRegistratie == null) {
            if (andereBetrokkenheidOuderlijkGezagHistorie.datumTijdRegistratie != null) {
                return false;
            }
        } else if (!datumTijdRegistratie.equals(andereBetrokkenheidOuderlijkGezagHistorie.datumTijdRegistratie)) {
            return false;
        }
        if (datumTijdVerval == null) {
            if (andereBetrokkenheidOuderlijkGezagHistorie.datumTijdVerval != null) {
                return false;
            }
        } else if (!datumTijdVerval.equals(andereBetrokkenheidOuderlijkGezagHistorie.datumTijdVerval)) {
            return false;
        }
        if (indicatieOuderHeeftGezag == null) {
            if (andereBetrokkenheidOuderlijkGezagHistorie.indicatieOuderHeeftGezag != null) {
                return false;
            }
        } else if (!indicatieOuderHeeftGezag
                .equals(andereBetrokkenheidOuderlijkGezagHistorie.indicatieOuderHeeftGezag)) {
            return false;
        }
        return true;
    }
}
