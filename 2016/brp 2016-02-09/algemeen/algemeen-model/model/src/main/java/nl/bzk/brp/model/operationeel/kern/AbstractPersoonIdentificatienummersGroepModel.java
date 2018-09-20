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
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroepBasis;

/**
 * Groep identificerende nummers
 *
 * De groep ""identificatienummers"" bevat het burgerservicenummer, en het Administratienummer, waarmee de persoon uniek
 * wordt aangeduid.
 *
 * Verplicht aanwezig bij persoon
 *
 * 1. Omdat in uitzonderingssituaties het nodig kan zijn om het burgerservicenummer en/of Administratienummer van een
 * persoon te wijzigen, is ervoor gekozen om burgerservicenummer en/of Administratienummer NIET als technische sleutel
 * te hanteren binnen de BRP.
 *
 * 2. Keuze van historie: LO 3.x onderkend beide vormen van historie voor BSN en A nummer: óók materiële. Er is een
 * discussie geweest wat precies die materiële historie zou zijn: is er niet simpelweg sprake van alleen een formele
 * tijdslijn voor het BSN en Anummer. Conclusie was: neen, er is ook een materiële tijdslijn. En ja, die komt meestal
 * overeen met de formele tijdslijn. NB: geldigheid gegeven wordt wel (mogelijk) geleverd aan afnemer.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonIdentificatienummersGroepModel implements PersoonIdentificatienummersGroepBasis {

    @Embedded
    @AttributeOverride(name = BurgerservicenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BSN"))
    @JsonProperty
    private BurgerservicenummerAttribuut burgerservicenummer;

    @Embedded
    @AttributeOverride(name = AdministratienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ANr"))
    @JsonProperty
    private AdministratienummerAttribuut administratienummer;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonIdentificatienummersGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param burgerservicenummer burgerservicenummer van Identificatienummers.
     * @param administratienummer administratienummer van Identificatienummers.
     */
    public AbstractPersoonIdentificatienummersGroepModel(
        final BurgerservicenummerAttribuut burgerservicenummer,
        final AdministratienummerAttribuut administratienummer)
    {
        this.burgerservicenummer = burgerservicenummer;
        this.administratienummer = administratienummer;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonIdentificatienummersGroep te kopieren groep.
     */
    public AbstractPersoonIdentificatienummersGroepModel(final PersoonIdentificatienummersGroep persoonIdentificatienummersGroep) {
        this.burgerservicenummer = persoonIdentificatienummersGroep.getBurgerservicenummer();
        this.administratienummer = persoonIdentificatienummersGroep.getAdministratienummer();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurgerservicenummerAttribuut getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratienummerAttribuut getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (burgerservicenummer != null) {
            attributen.add(burgerservicenummer);
        }
        if (administratienummer != null) {
            attributen.add(administratienummer);
        }
        return attributen;
    }

}
