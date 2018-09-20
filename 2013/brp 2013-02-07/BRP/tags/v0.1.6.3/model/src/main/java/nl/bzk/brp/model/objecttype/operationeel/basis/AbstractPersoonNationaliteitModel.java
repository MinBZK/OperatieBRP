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
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonNationaliteitStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonNationaliteitBasis;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


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
    private Long                                  id;

    @ManyToOne
    @JoinColumn(name = "pers")
    @NotNull
    private PersoonModel persoon;

    @ManyToOne
    @JoinColumn(name = "nation")
    @NotNull
    private Nationaliteit                         nationaliteit;

    @Embedded
    @NotNull
    private PersoonNationaliteitStandaardGroepModel gegevens;

    @Column(name = "persnationstatushis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie statusHistorie;

    /**
     * .
     * @param persNation .
     * @param pers .
     */
    protected AbstractPersoonNationaliteitModel(final PersoonNationaliteitBasis persNation,
                                              final PersoonModel pers)
    {
        super(persNation);
        nationaliteit = persNation.getNationaliteit();
        initLegeStatusHistorie();
        if (null != persNation.getGegevens()) {
            gegevens = new PersoonNationaliteitStandaardGroepModel(persNation.getGegevens());
            statusHistorie = StatusHistorie.A;
        }
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

    public Long getId() {
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


}
