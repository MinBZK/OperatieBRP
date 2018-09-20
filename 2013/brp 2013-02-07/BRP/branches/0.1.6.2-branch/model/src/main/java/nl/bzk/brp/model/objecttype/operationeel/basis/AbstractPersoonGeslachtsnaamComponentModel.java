/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
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

import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsnaamCompStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonGeslachtsnaamComponentBasis;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * Implementatie object type Persoon Geslachtsnaam component.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamComponentModel extends AbstractDynamischObjectType implements
        PersoonGeslachtsnaamComponentBasis
{

    @Id
    @SequenceGenerator(name = "PersGeslnaamcomp", sequenceName = "Kern.seq_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersGeslnaamcomp")
    private Long                                      id;

    @ManyToOne
    @JoinColumn(name = "pers")
    @NotNull
    private PersoonModel persoon;

    @Embedded
    @NotNull
    private PersoonGeslachtsnaamCompStandaardGroepModel persoonGeslachtsnaamCompStandaardGroep;

    @Column(name = "PersGeslnaamcompStatusHis")
    @Enumerated(value =  EnumType.STRING)
    @NotNull
    private StatusHistorie statusHistorie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "volgnr"))
    @NotNull
    private Volgnummer                                volgnummer;


    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param geslComp Object type dat gekopieerd dient te worden.
     * @param pers Persoon
     */
    protected AbstractPersoonGeslachtsnaamComponentModel(final PersoonGeslachtsnaamComponentBasis geslComp,
                                                       final PersoonModel pers)
    {
        super(geslComp);
        initLegeStatusHistorie();
        volgnummer = geslComp.getVolgnummer();
        persoonGeslachtsnaamCompStandaardGroep = new PersoonGeslachtsnaamCompStandaardGroepModel(
                geslComp.getGegevens());
        statusHistorie = StatusHistorie.A;
        persoon = pers;
    }

    /**
     * .default cons.
     */
    protected AbstractPersoonGeslachtsnaamComponentModel() {
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
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonGeslachtsnaamCompStandaardGroepModel getGegevens() {
        return persoonGeslachtsnaamCompStandaardGroep;
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

}
