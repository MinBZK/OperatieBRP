/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Administratieve handeling.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class AdministratieveHandelingHisVolledigImplBuilder {

    private AdministratieveHandelingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param soort soort van Administratieve handeling.
     * @param partij partij van Administratieve handeling.
     * @param toelichtingOntlening toelichtingOntlening van Administratieve handeling.
     * @param tijdstipRegistratie tijdstipRegistratie van Administratieve handeling.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public AdministratieveHandelingHisVolledigImplBuilder(
        final SoortAdministratieveHandeling soort,
        final Partij partij,
        final OntleningstoelichtingAttribuut toelichtingOntlening,
        final DatumTijdAttribuut tijdstipRegistratie,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new AdministratieveHandelingHisVolledigImpl(
                    new SoortAdministratieveHandelingAttribuut(soort),
                    new PartijAttribuut(partij),
                    toelichtingOntlening,
                    tijdstipRegistratie);
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getToelichtingOntlening() != null) {
            hisVolledigImpl.getToelichtingOntlening().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getTijdstipRegistratie() != null) {
            hisVolledigImpl.getTijdstipRegistratie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Administratieve handeling.
     * @param partij partij van Administratieve handeling.
     * @param toelichtingOntlening toelichtingOntlening van Administratieve handeling.
     * @param tijdstipRegistratie tijdstipRegistratie van Administratieve handeling.
     */
    public AdministratieveHandelingHisVolledigImplBuilder(
        final SoortAdministratieveHandeling soort,
        final Partij partij,
        final OntleningstoelichtingAttribuut toelichtingOntlening,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this(soort, partij, toelichtingOntlening, tijdstipRegistratie, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public AdministratieveHandelingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Voeg een Administratieve handeling \ Gedeblokkeerde melding toe. Zet tevens de back-reference van Administratieve
     * handeling \ Gedeblokkeerde melding.
     *
     * @param administratieveHandelingGedeblokkeerdeMelding een Administratieve handeling \ Gedeblokkeerde melding
     * @return his volledig builder
     */
    public AdministratieveHandelingHisVolledigImplBuilder voegAdministratieveHandelingGedeblokkeerdeMeldingToe(
        final AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl administratieveHandelingGedeblokkeerdeMelding)
    {
        this.hisVolledigImpl.getGedeblokkeerdeMeldingen().add(administratieveHandelingGedeblokkeerdeMelding);
        ReflectionTestUtils.setField(administratieveHandelingGedeblokkeerdeMelding, "administratieveHandeling", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Actie toe. Zet tevens de back-reference van Actie.
     *
     * @param actie een Actie
     * @return his volledig builder
     */
    public AdministratieveHandelingHisVolledigImplBuilder voegActieToe(final ActieHisVolledigImpl actie) {
        this.hisVolledigImpl.getActies().add(actie);
        ReflectionTestUtils.setField(actie, "administratieveHandeling", this.hisVolledigImpl);
        return this;
    }

}
