/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.basis;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nl.bzk.copy.model.basis.AbstractDynamischObjectType;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonIndicatieStandaardGroepModel;
import nl.bzk.copy.model.objecttype.logisch.basis.PersoonIndicatiesBasis;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * Implementatie voor objecttype Persoon voornaam.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonIndicatieModel extends AbstractDynamischObjectType
        implements PersoonIndicatiesBasis
{

    @Id
    @SequenceGenerator(name = "PERSOONINDICATIE", sequenceName = "Kern.seq_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOONINDICATIE")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @NotNull
    private PersoonModel persoon;

    @Embedded
    @NotNull
    private PersoonIndicatieStandaardGroepModel gegevens;

    @Column(name = "persindicatiestatushis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie statusHistorie;


    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param persInd Object type dat gekopieerd dient te worden.
     */
    protected AbstractPersoonIndicatieModel(final PersoonIndicatiesBasis persInd) {
        super(persInd);
        initLegeStatusHistorie();
        gegevens = new PersoonIndicatieStandaardGroepModel(persInd.getGegevens());
        statusHistorie = StatusHistorie.A;
    }

    /**
     * default cons.
     */
    protected AbstractPersoonIndicatieModel() {
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
    public PersoonIndicatieStandaardGroepModel getGegevens() {
        return gegevens;
    }

    public void setGegevens(final PersoonIndicatieStandaardGroepModel gegevens) {
        this.gegevens = gegevens;
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

    public void setStatusHistorie(final StatusHistorie statusHistorie) {
        this.statusHistorie = statusHistorie;
    }
}
