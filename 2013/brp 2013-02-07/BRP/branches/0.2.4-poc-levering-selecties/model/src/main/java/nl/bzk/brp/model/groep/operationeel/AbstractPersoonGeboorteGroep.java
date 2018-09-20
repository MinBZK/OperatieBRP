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
import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeboorteGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeboorteGroep extends AbstractGroep implements PersoonGeboorteGroepBasis,
        Externalizable
{

    @AttributeOverride(name = "waarde", column = @Column(name = "datgeboorte"))
    @Embedded
    @JsonProperty
    private Datum              datumGeboorte;

    @ManyToOne
    @JoinColumn(name = "GemGeboorte")
    @JsonProperty
    @Fetch(FetchMode.JOIN)
    private Partij             gemeenteGeboorte;

    @ManyToOne
    @JoinColumn(name = "WplGeboorte")
    @JsonProperty
    @Fetch(FetchMode.JOIN)
    private Plaats             woonplaatsGeboorte;

    @AttributeOverride(name = "waarde", column = @Column(name = "blgeboorteplaats"))
    @Embedded
    @JsonProperty
    private BuitenlandsePlaats buitenlandseGeboortePlaats;

    @AttributeOverride(name = "waarde", column = @Column(name = "blregiogeboorte"))
    @Embedded
    @JsonProperty
    private BuitenlandseRegio  buitenlandseRegioGeboorte;

    @ManyToOne
    @JoinColumn(name = "LandGeboorte")
    @JsonProperty
    @Fetch(FetchMode.JOIN)
    private Land               landGeboorte;

    @AttributeOverride(name = "waarde", column = @Column(name = "omsgeboorteloc"))
    @Embedded
    @JsonProperty
    private Omschrijving       omschrijvingGeboorteLocatie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeboorteGroep() {

    }

    /**
     * .
     *
     * @param persoonGeboorteGroepBasis PersoonGeboorteGroepBasis
     */
    protected AbstractPersoonGeboorteGroep(final PersoonGeboorteGroepBasis persoonGeboorteGroepBasis) {
        datumGeboorte = persoonGeboorteGroepBasis.getDatumGeboorte();
        gemeenteGeboorte = persoonGeboorteGroepBasis.getGemeenteGeboorte();
        woonplaatsGeboorte = persoonGeboorteGroepBasis.getWoonplaatsGeboorte();
        buitenlandseGeboortePlaats = persoonGeboorteGroepBasis.getBuitenlandseGeboortePlaats();
        buitenlandseRegioGeboorte = persoonGeboorteGroepBasis.getBuitenlandseRegioGeboorte();
        landGeboorte = persoonGeboorteGroepBasis.getLandGeboorte();
        omschrijvingGeboorteLocatie = persoonGeboorteGroepBasis.getOmschrijvingGeboorteLocatie();
    }

    @Override
    public Datum getDatumGeboorte() {
        return datumGeboorte;
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandseGeboortePlaats() {
        return buitenlandseGeboortePlaats;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    @Override
    public Land getLandGeboorte() {
        return landGeboorte;
    }

    @Override
    public Omschrijving getOmschrijvingGeboorteLocatie() {
        return omschrijvingGeboorteLocatie;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        ExternalWriterService.schrijfWaarde(out, datumGeboorte);
        ExternalWriterService.schrijfNullableObject(out, gemeenteGeboorte);
        ExternalWriterService.schrijfNullableObject(out, woonplaatsGeboorte);
        ExternalWriterService.schrijfWaarde(out, buitenlandseGeboortePlaats);
        ExternalWriterService.schrijfWaarde(out, buitenlandseRegioGeboorte);
        ExternalWriterService.schrijfNullableObject(out, landGeboorte);
        ExternalWriterService.schrijfWaarde(out, omschrijvingGeboorteLocatie);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.datumGeboorte = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
        this.gemeenteGeboorte = (Partij) ExternalReaderService.leesNullableObject(in, Partij.class);
        this.woonplaatsGeboorte = (Plaats) ExternalReaderService.leesNullableObject(in, Plaats.class);
        this.buitenlandseGeboortePlaats =
                (BuitenlandsePlaats) ExternalReaderService.leesWaarde(in, BuitenlandsePlaats.class);
        this.buitenlandseRegioGeboorte =
                (BuitenlandseRegio) ExternalReaderService.leesWaarde(in, BuitenlandseRegio.class);
        this.landGeboorte = (Land) ExternalReaderService.leesNullableObject(in, Land.class);
        this.omschrijvingGeboorteLocatie = (Omschrijving) ExternalReaderService.leesWaarde(in, Omschrijving.class);
    }

}
