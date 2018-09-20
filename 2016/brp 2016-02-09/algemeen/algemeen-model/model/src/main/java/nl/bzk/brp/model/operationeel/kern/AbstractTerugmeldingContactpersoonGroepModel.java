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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.EmailadresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TelefoonnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.TerugmeldingContactpersoonGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractTerugmeldingContactpersoonGroepModel implements TerugmeldingContactpersoonGroepBasis {

    @Embedded
    @AttributeOverride(name = EmailadresAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Email"))
    @JsonProperty
    private EmailadresAttribuut email;

    @Embedded
    @AttributeOverride(name = TelefoonnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Telefoonnr"))
    @JsonProperty
    private TelefoonnummerAttribuut telefoonnummer;

    @Embedded
    @AttributeOverride(name = VoornamenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voornamen"))
    @JsonProperty
    private VoornamenAttribuut voornamen;

    @Embedded
    @AttributeOverride(name = VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private VoorvoegselAttribuut voorvoegsel;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private ScheidingstekenAttribuut scheidingsteken;

    @Embedded
    @AttributeOverride(name = GeslachtsnaamstamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Geslnaamstam"))
    @JsonProperty
    private GeslachtsnaamstamAttribuut geslachtsnaamstam;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractTerugmeldingContactpersoonGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param email email van Contactpersoon.
     * @param telefoonnummer telefoonnummer van Contactpersoon.
     * @param voornamen voornamen van Contactpersoon.
     * @param voorvoegsel voorvoegsel van Contactpersoon.
     * @param scheidingsteken scheidingsteken van Contactpersoon.
     * @param geslachtsnaamstam geslachtsnaamstam van Contactpersoon.
     */
    public AbstractTerugmeldingContactpersoonGroepModel(
        final EmailadresAttribuut email,
        final TelefoonnummerAttribuut telefoonnummer,
        final VoornamenAttribuut voornamen,
        final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam)
    {
        this.email = email;
        this.telefoonnummer = telefoonnummer;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaamstam = geslachtsnaamstam;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailadresAttribuut getEmail() {
        return email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TelefoonnummerAttribuut getTelefoonnummer() {
        return telefoonnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoornamenAttribuut getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoorvoegselAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (email != null) {
            attributen.add(email);
        }
        if (telefoonnummer != null) {
            attributen.add(telefoonnummer);
        }
        if (voornamen != null) {
            attributen.add(voornamen);
        }
        if (voorvoegsel != null) {
            attributen.add(voorvoegsel);
        }
        if (scheidingsteken != null) {
            attributen.add(scheidingsteken);
        }
        if (geslachtsnaamstam != null) {
            attributen.add(geslachtsnaamstam);
        }
        return attributen;
    }

}
