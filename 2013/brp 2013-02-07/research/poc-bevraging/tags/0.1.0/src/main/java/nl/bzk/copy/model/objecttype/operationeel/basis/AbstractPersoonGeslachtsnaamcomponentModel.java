/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.basis;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nl.bzk.copy.model.attribuuttype.Volgnummer;
import nl.bzk.copy.model.basis.AbstractDynamischObjectType;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonGeslachtsnaamcomponentStandaardGroepModel;
import nl.bzk.copy.model.objecttype.logisch.basis.PersoonGeslachtsnaamcomponentBasis;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * Implementatie object type Persoon Geslachtsnaam component.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamcomponentModel extends AbstractDynamischObjectType implements
        PersoonGeslachtsnaamcomponentBasis
{

    @Id
    @SequenceGenerator(name = "PersGeslnaamcomp", sequenceName = "Kern.seq_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersGeslnaamcomp")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pers")
    @NotNull
    private PersoonModel persoon;

    @Embedded
    @NotNull
    private PersoonGeslachtsnaamcomponentStandaardGroepModel geslachtsnaamcomponent;

    @Column(name = "PersGeslnaamcompStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie statusHistorie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "volgnr"))
    @NotNull
    private Volgnummer volgnummer;


    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param geslComp Object type dat gekopieerd dient te worden.
     * @param pers     Persoon
     */
    protected AbstractPersoonGeslachtsnaamcomponentModel(final PersoonGeslachtsnaamcomponentBasis geslComp,
                                                         final PersoonModel pers)
    {
        super(geslComp);
        initLegeStatusHistorie();
        volgnummer = geslComp.getVolgnummer();
        geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentStandaardGroepModel(
                geslComp.getGegevens());
        statusHistorie = StatusHistorie.A;
        persoon = pers;
    }

    /**
     * .default cons.
     */
    protected AbstractPersoonGeslachtsnaamcomponentModel() {
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
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonGeslachtsnaamcomponentStandaardGroepModel getGegevens() {
        return geslachtsnaamcomponent;
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }


    public PersoonGeslachtsnaamcomponentStandaardGroepModel getGeslachtsnaamcomponent() {
        return geslachtsnaamcomponent;
    }


    public void setGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponentStandaardGroepModel geslachtsnaamcomponent) {
        this.geslachtsnaamcomponent = geslachtsnaamcomponent;
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


    public void setVolgnummer(final Volgnummer volgnummer) {
        this.volgnummer = volgnummer;
    }

}
