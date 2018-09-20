/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroep;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonAfnemerindicatieStandaardGroepModel implements PersoonAfnemerindicatieStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvMaterielePeriode"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeVolgen"))
    @JsonProperty
    private DatumAttribuut datumEindeVolgen;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonAfnemerindicatieStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumAanvangMaterielePeriode datumAanvangMaterielePeriode van Standaard.
     * @param datumEindeVolgen datumEindeVolgen van Standaard.
     */
    public AbstractPersoonAfnemerindicatieStandaardGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen)
    {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
        this.datumEindeVolgen = datumEindeVolgen;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAfnemerindicatieStandaardGroep te kopieren groep.
     */
    public AbstractPersoonAfnemerindicatieStandaardGroepModel(final PersoonAfnemerindicatieStandaardGroep persoonAfnemerindicatieStandaardGroep) {
        this.datumAanvangMaterielePeriode = persoonAfnemerindicatieStandaardGroep.getDatumAanvangMaterielePeriode();
        this.datumEindeVolgen = persoonAfnemerindicatieStandaardGroep.getDatumEindeVolgen();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumAanvangMaterielePeriode != null) {
            attributen.add(datumAanvangMaterielePeriode);
        }
        if (datumEindeVolgen != null) {
            attributen.add(datumEindeVolgen);
        }
        return attributen;
    }

}
