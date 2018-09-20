/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBijhoudingsaardGroepBasis;


/**
 * Groep gegevens over de bijhouding
 *
 * Verplicht aanwezig bij persoon
 *
 * 1. In geval van een opgeschorte bijhouding, is 'ingezetene' en 'niet-ingezetene' minder goed gedefinieerd. In het LO
 * BRP wordt dit punt uitgewerkt.
 * 2. Vorm van historie: Beiden. De bijhoudingsverantwoordelijkheid kan verschuiven door bijvoorbeeld een verhuizing die
 * om technische redenen later wordt geregistreerd (maar wellicht op tijd is doorgegeven). De datum ingang geldigheid
 * van de (nieuwe) bijhoudingsverantwoordelijkheid ligt dus vaak v��r de datumtijd registratie. Een formele tijdslijn
 * alleen is dus onvoldoende.
 * Omdat, in geval van verschuivingen van verantwoordelijkheid tussen Minister en College B&W, ook van belang kan zijn
 * wie over een periode in het verleden 'de verantwoordelijke' was (voor het wijzigen van historische gegevens), is de
 * materi�le tijdslijn ook relevant, en dus wordt deze vastgelegd.
 * 3. De naam is aangepast naar bijhoudingsaard, tegen de term bijhoudingsverantwoordelijkheid was teveel weerstand. 7
 * januari 2013.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractPersoonBijhoudingsaardGroepModel implements PersoonBijhoudingsaardGroepBasis {

    @Enumerated
    @Column(name = "Bijhaard")
    @JsonProperty
    private Bijhoudingsaard bijhoudingsaard;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonBijhoudingsaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param bijhoudingsaard bijhoudingsaard van Bijhoudingsaard.
     */
    public AbstractPersoonBijhoudingsaardGroepModel(final Bijhoudingsaard bijhoudingsaard) {
        this.bijhoudingsaard = bijhoudingsaard;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonBijhoudingsaardGroep te kopieren groep.
     */
    public AbstractPersoonBijhoudingsaardGroepModel(final PersoonBijhoudingsaardGroep persoonBijhoudingsaardGroep) {
        this.bijhoudingsaard = persoonBijhoudingsaardGroep.getBijhoudingsaard();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bijhoudingsaard getBijhoudingsaard() {
        return bijhoudingsaard;
    }

}
