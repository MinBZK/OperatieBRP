/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsnaamcomponentStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonGeslachtsnaamcomponentBasis;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;


/** Implementatie object type Persoon Geslachtsnaam component. */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamcomponentModel extends AbstractDynamischObjectType implements
    PersoonGeslachtsnaamcomponentBasis, Externalizable
{

    @Id
    @SequenceGenerator(name = "PersGeslnaamcomp", sequenceName = "Kern.seq_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PersGeslnaamcomp")
    @JsonProperty
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pers")
    @NotNull
    @JsonProperty
    private PersoonModel persoon;

    @Embedded
    @NotNull
    @JsonProperty
    private PersoonGeslachtsnaamcomponentStandaardGroepModel geslachtsnaamcomponent;

    @Column(name = "PersGeslnaamcompStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    @JsonProperty
    private StatusHistorie statusHistorie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "volgnr"))
    @NotNull
    @JsonProperty
    private Volgnummer volgnummer;


    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param geslComp Object type dat gekopieerd dient te worden.
     * @param pers Persoon
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

    /** .default cons. */
    protected AbstractPersoonGeslachtsnaamcomponentModel() {
        initLegeStatusHistorie();
    }

    /** initieer alle sttaushistories op waarde X. */
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

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(id);
        ExternalWriterService.schrijfWaarde(out, volgnummer);
        ExternalWriterService.schrijfNullableObject(out, geslachtsnaamcomponent);
        ExternalWriterService.schrijfEnum(out, statusHistorie);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = (Integer) in.readObject();
        this.volgnummer = (Volgnummer) ExternalReaderService.leesWaarde(in, Volgnummer.class);
        this.geslachtsnaamcomponent = (PersoonGeslachtsnaamcomponentStandaardGroepModel) ExternalReaderService
                .leesNullableObject(in, PersoonGeslachtsnaamcomponentStandaardGroepModel.class);
        this.statusHistorie = (StatusHistorie) ExternalReaderService.leesEnum(in, StatusHistorie.class);
    }

}
