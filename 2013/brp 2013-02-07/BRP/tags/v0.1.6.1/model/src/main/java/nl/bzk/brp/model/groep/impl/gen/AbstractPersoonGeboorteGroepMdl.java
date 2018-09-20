/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonGeboorteGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeboorteGroepMdl extends AbstractGroep implements PersoonGeboorteGroepBasis {

    @AttributeOverride(name = "waarde", column = @Column(name = "datgeboorte"))
    @Embedded
    private Datum              datumGeboorte;

    @ManyToOne
    @JoinColumn(name = "GemGeboorte")
    private Partij             gemeenteGeboorte;

    @ManyToOne
    @JoinColumn(name = "WplGeboorte")
    private Plaats             woonplaatsGeboorte;

    @AttributeOverride(name = "waarde", column = @Column(name = "blgeboorteplaats"))
    @Embedded
    private BuitenlandsePlaats buitenlandseGeboortePlaats;

    @AttributeOverride(name = "waarde", column = @Column(name = "blregiogeboorte"))
    @Embedded
    private BuitenlandseRegio  buitenlandseRegioGeboorte;

    @ManyToOne
    @JoinColumn(name = "LandGeboorte")
    private Land               landGeboorte;

    @AttributeOverride(name = "waarde", column = @Column(name = "omsgeboorteloc"))
    @Embedded
    private Omschrijving       omschrijvingGeboorteLocatie;

    @Transient
    private StatusHistorie     statusHistorie;

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
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }
}
