/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonIndicatieStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonIndicatiesBasis;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * Implementatie voor objecttype Persoon voornaam.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonIndicatieModel extends AbstractDynamischObjectType implements PersoonIndicatiesBasis {

    @Id
    @SequenceGenerator(name = "PERSOONINDICATIE", sequenceName = "Kern.seq_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONINDICATIE")
    private Integer                             id;

    @ManyToOne
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


}
