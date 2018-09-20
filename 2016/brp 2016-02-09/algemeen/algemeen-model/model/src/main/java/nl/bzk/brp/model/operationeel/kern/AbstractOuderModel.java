/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.OuderBasis;

/**
 * De OT:Betrokkenheid van een OT:Persoon in de _rol_ van "_Ouder_" in een OT:"Familierechtelijke Betrekking".
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractOuderModel extends BetrokkenheidModel implements OuderBasis {

    @Embedded
    @JsonProperty
    private OuderOuderschapGroepModel ouderschap;

    @Embedded
    @JsonProperty
    private OuderOuderlijkGezagGroepModel ouderlijkGezag;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractOuderModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Ouder.
     * @param persoon persoon van Ouder.
     */
    public AbstractOuderModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, new SoortBetrokkenheidAttribuut(SoortBetrokkenheid.OUDER), persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouder Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractOuderModel(final Ouder ouder, final RelatieModel relatie, final PersoonModel persoon) {
        super(ouder, relatie, persoon);
        if (ouder.getOuderschap() != null) {
            this.ouderschap = new OuderOuderschapGroepModel(ouder.getOuderschap());
        }
        if (ouder.getOuderlijkGezag() != null) {
            this.ouderlijkGezag = new OuderOuderlijkGezagGroepModel(ouder.getOuderlijkGezag());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderOuderschapGroepModel getOuderschap() {
        return ouderschap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderOuderlijkGezagGroepModel getOuderlijkGezag() {
        return ouderlijkGezag;
    }

    /**
     * Zet Ouderschap van Ouder.
     *
     * @param ouderschap Ouderschap.
     */
    public void setOuderschap(final OuderOuderschapGroepModel ouderschap) {
        this.ouderschap = ouderschap;
    }

    /**
     * Zet Ouderlijk gezag van Ouder.
     *
     * @param ouderlijkGezag Ouderlijk gezag.
     */
    public void setOuderlijkGezag(final OuderOuderlijkGezagGroepModel ouderlijkGezag) {
        this.ouderlijkGezag = ouderlijkGezag;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (ouderschap != null) {
            groepen.add(ouderschap);
        }
        if (ouderlijkGezag != null) {
            groepen.add(ouderlijkGezag);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

}
