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
import nl.bzk.brp.model.logisch.kern.PersoonNummerverwijzingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNummerverwijzingGroepBasis;

/**
 * Zowel het Burgerservicenummer als het administratienummer worden in de Wet BRP genoemd en aangemerkt als een Algemeen
 * gegeven. Het is na wijziging van één van de nummers, ongeacht de reden voor de wijziging, noodzakelijk de afnemers
 * die geautoriseerd zijn voor deze gegevens te informeren over deze wijziging. De BRP voorziet in het leveren van deze
 * informatie. Om die reden dienen deze attributen aangemerkt te worden als Administratief gegeven en kunnen, mits de
 * afnemer daarvoor is geautoriseerd, worden geleverd. Het Centraal Bureau voor de Statistiek (CBS) is bijvoorbeeld een
 * voor de wijziging van identificerende nummers geautoriseerde afnemer.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonNummerverwijzingGroepModel implements PersoonNummerverwijzingGroepBasis {

    @Embedded
    @AttributeOverride(name = BurgerservicenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VorigeBSN"))
    @JsonProperty
    private BurgerservicenummerAttribuut vorigeBurgerservicenummer;

    @Embedded
    @AttributeOverride(name = BurgerservicenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VolgendeBSN"))
    @JsonProperty
    private BurgerservicenummerAttribuut volgendeBurgerservicenummer;

    @Embedded
    @AttributeOverride(name = AdministratienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VorigeANr"))
    @JsonProperty
    private AdministratienummerAttribuut vorigeAdministratienummer;

    @Embedded
    @AttributeOverride(name = AdministratienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VolgendeANr"))
    @JsonProperty
    private AdministratienummerAttribuut volgendeAdministratienummer;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonNummerverwijzingGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param vorigeBurgerservicenummer vorigeBurgerservicenummer van Nummerverwijzing.
     * @param volgendeBurgerservicenummer volgendeBurgerservicenummer van Nummerverwijzing.
     * @param vorigeAdministratienummer vorigeAdministratienummer van Nummerverwijzing.
     * @param volgendeAdministratienummer volgendeAdministratienummer van Nummerverwijzing.
     */
    public AbstractPersoonNummerverwijzingGroepModel(
        final BurgerservicenummerAttribuut vorigeBurgerservicenummer,
        final BurgerservicenummerAttribuut volgendeBurgerservicenummer,
        final AdministratienummerAttribuut vorigeAdministratienummer,
        final AdministratienummerAttribuut volgendeAdministratienummer)
    {
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
        this.vorigeAdministratienummer = vorigeAdministratienummer;
        this.volgendeAdministratienummer = volgendeAdministratienummer;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNummerverwijzingGroep te kopieren groep.
     */
    public AbstractPersoonNummerverwijzingGroepModel(final PersoonNummerverwijzingGroep persoonNummerverwijzingGroep) {
        this.vorigeBurgerservicenummer = persoonNummerverwijzingGroep.getVorigeBurgerservicenummer();
        this.volgendeBurgerservicenummer = persoonNummerverwijzingGroep.getVolgendeBurgerservicenummer();
        this.vorigeAdministratienummer = persoonNummerverwijzingGroep.getVorigeAdministratienummer();
        this.volgendeAdministratienummer = persoonNummerverwijzingGroep.getVolgendeAdministratienummer();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurgerservicenummerAttribuut getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurgerservicenummerAttribuut getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratienummerAttribuut getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratienummerAttribuut getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (vorigeBurgerservicenummer != null) {
            attributen.add(vorigeBurgerservicenummer);
        }
        if (volgendeBurgerservicenummer != null) {
            attributen.add(volgendeBurgerservicenummer);
        }
        if (vorigeAdministratienummer != null) {
            attributen.add(vorigeAdministratienummer);
        }
        if (volgendeAdministratienummer != null) {
            attributen.add(volgendeAdministratienummer);
        }
        return attributen;
    }

}
