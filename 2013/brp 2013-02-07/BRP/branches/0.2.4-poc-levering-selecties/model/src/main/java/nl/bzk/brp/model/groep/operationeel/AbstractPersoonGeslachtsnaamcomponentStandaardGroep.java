/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsnaamcomponentStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Implementatie standaard groep object type Persoon Geslachtsnaam component.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaardGroep extends AbstractGroep implements
    PersoonGeslachtsnaamcomponentStandaardGroepBasis, Externalizable
{

    @ManyToOne
    @JoinColumn(name = "predikaat")
    @JsonProperty
    @Fetch(FetchMode.JOIN)
    private Predikaat predikaat;

    @ManyToOne
    @JoinColumn(name = "adellijketitel")
    @JsonProperty
    @Fetch(FetchMode.JOIN)
    private AdellijkeTitel         adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voorvoegsel"))
    @JsonProperty
    private Voorvoegsel            voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "scheidingsteken"))
    @JsonProperty
    private Scheidingsteken scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    @JsonProperty
    private Geslachtsnaamcomponent geslachtsnaamcomponent;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeslachtsnaamcomponentStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonGeslachtsnaamcomponentStandaardGroepBasis PersoonGeslachtsnaamcomponentStandaardGroepBasis
     */
    protected AbstractPersoonGeslachtsnaamcomponentStandaardGroep(
        final PersoonGeslachtsnaamcomponentStandaardGroepBasis persoonGeslachtsnaamcomponentStandaardGroepBasis)
    {
        predikaat = persoonGeslachtsnaamcomponentStandaardGroepBasis.getPredikaat();
        adellijkeTitel = persoonGeslachtsnaamcomponentStandaardGroepBasis.getAdellijkeTitel();
        voorvoegsel = persoonGeslachtsnaamcomponentStandaardGroepBasis.getVoorvoegsel();
        scheidingsteken = persoonGeslachtsnaamcomponentStandaardGroepBasis.getScheidingsteken();
        geslachtsnaamcomponent = persoonGeslachtsnaamcomponentStandaardGroepBasis.getNaam();
    }

    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    @Override
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public Geslachtsnaamcomponent getNaam() {
        return geslachtsnaamcomponent;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        ExternalWriterService.schrijfNullableObject(out, predikaat);
        ExternalWriterService.schrijfNullableObject(out, adellijkeTitel);
        ExternalWriterService.schrijfWaarde(out, voorvoegsel);
        ExternalWriterService.schrijfWaarde(out, scheidingsteken);
        ExternalWriterService.schrijfWaarde(out, geslachtsnaamcomponent);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.predikaat = (Predikaat) ExternalReaderService.leesNullableObject(in, Predikaat.class);
        this.adellijkeTitel = (AdellijkeTitel) ExternalReaderService.leesNullableObject(in, AdellijkeTitel.class);
        this.voorvoegsel = (Voorvoegsel) ExternalReaderService.leesWaarde(in, Voorvoegsel.class);
        this.scheidingsteken = (Scheidingsteken) ExternalReaderService.leesWaarde(in, Scheidingsteken.class);
        this.geslachtsnaamcomponent = (Geslachtsnaamcomponent) ExternalReaderService.leesWaarde(in, Geslachtsnaamcomponent.class);
    }
}
