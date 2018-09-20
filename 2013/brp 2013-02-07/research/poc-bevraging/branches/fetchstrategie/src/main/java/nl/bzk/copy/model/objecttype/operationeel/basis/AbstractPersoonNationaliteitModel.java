/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.basis;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nl.bzk.copy.model.basis.AbstractDynamischObjectType;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonNationaliteitStandaardGroepModel;
import nl.bzk.copy.model.objecttype.logisch.basis.PersoonNationaliteitBasis;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Implementatie voor objecttype Persoon Nationaliteit.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonNationaliteitModel extends AbstractDynamischObjectType implements
        PersoonNationaliteitBasis
{

    @Id
    @SequenceGenerator(name = "PERSNATION", sequenceName = "Kern.seq_PersNation")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSNATION")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pers")
    @NotNull
    private PersoonModel persoon;

    @ManyToOne
    @JoinColumn(name = "nation")
    @NotNull
    @Fetch(FetchMode.JOIN)
    private Nationaliteit nationaliteit;

    @Embedded
    @NotNull
    private PersoonNationaliteitStandaardGroepModel gegevens;

    @Column(name = "persnationstatushis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie statusHistorie;

    /**
     * .
     *
     * @param persNation .
     * @param pers       .
     */
    protected AbstractPersoonNationaliteitModel(final PersoonNationaliteitBasis persNation,
                                                final PersoonModel pers)
    {
        super(persNation);
        nationaliteit = persNation.getNationaliteit();
        initLegeStatusHistorie();
        gegevens = new PersoonNationaliteitStandaardGroepModel(persNation.getGegevens());
        statusHistorie = StatusHistorie.A;
        persoon = pers;
    }

    /**
     * .
     */
    protected AbstractPersoonNationaliteitModel() {
        initLegeStatusHistorie();
    }

    /**
     * initieer alle sttaushistories op waarde X.
     */
    private void initLegeStatusHistorie() {
        statusHistorie = StatusHistorie.X;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public PersoonModel getPersoon() {
        return persoon;
    }

    @Override
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    @Override
    public PersoonNationaliteitStandaardGroepModel getGegevens() {
        return gegevens;
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setPersoon(final PersoonModel persoon) {
        this.persoon = persoon;
    }

    public void setNationaliteit(final Nationaliteit nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public void setGegevens(final PersoonNationaliteitStandaardGroepModel gegevens) {
        this.gegevens = gegevens;
    }

    public void setStatusHistorie(final StatusHistorie statusHistorie) {
        this.statusHistorie = statusHistorie;
    }
}
