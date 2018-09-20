/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.validatie.Melding;
import org.springframework.stereotype.Component;


/**
 * Validator die geboorte (eerste inschrijving) acties valideert.
 */
@Component
public class GeboorteActieValidator implements ActieValidator {

    @Override
    public List<Melding> valideerActie(final Actie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        if (null == actie.getRootObjecten() || 0 == actie.getRootObjecten().size()) {
            ValidatieUtil.voegMeldingVerplichtVeld(meldingen, (ActieBericht) actie, "familierechtelijkeBetrekking");
        } else {
            Relatie relatie = (Relatie) actie.getRootObjecten().get(0);
            Betrokkenheid kindBetrokkenheid = relatie.getKindBetrokkenheid();
            if (null == kindBetrokkenheid || null == kindBetrokkenheid.getBetrokkene()) {
                ValidatieUtil.voegMeldingVerplichtVeld(meldingen, (RelatieBericht) relatie, "kind");
            } else {
                Persoon kind = kindBetrokkenheid.getBetrokkene();
                if (kind != null) {
                    meldingen.addAll(valideerKind((PersoonBericht) kind));
                }
            }
        }
        return meldingen;
    }

    /**
     * Valideert de gegevens van het kind.
     *
     * @param kind Het te valideren kind.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerKind(final PersoonBericht kind) {
        final List<Melding> meldingen = new ArrayList<Melding>();

        // identificatie nrs zijn verplicht.
        PersoonIdentificatienummersGroepBericht persoonIdentificatienummers = kind.getIdentificatienummers();
        if (ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, kind, persoonIdentificatienummers,
                "Identificatienummers"))
        {
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, persoonIdentificatienummers,
                    persoonIdentificatienummers.getBurgerservicenummer(), "Burgerservicenummer");
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, persoonIdentificatienummers,
                    persoonIdentificatienummers.getANummer(), "administratienummer");
        }

        // geslachtsaanduiding is verplicht
        if (ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, kind, kind.getGeslachtsaanduiding(),
            "geslachtsaanduiding"))
        {
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, kind.getGeslachtsaanduiding(),
                    kind.getGeslachtsaanduiding().getGeslachtsaanduiding(), "geslachtsaanduiding");
        }

        // geboorteDatum is verplicht
        if (ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, kind, kind.getGeboorte(), "geboorte")) {
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, kind.getGeboorte(),
                    kind.getGeboorte().getDatumGeboorte(), "datumGeboorte");
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, kind.getGeboorte(),
                    kind.getGeboorte().getGemeenteGeboorte(), "gemeenteGeboorte");
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, kind.getGeboorte(),
                    kind.getGeboorte().getLandGeboorte(), "landGeboorte");
        }

        // Groep voornamen is niet verplicht, maar als er een ingevuld is, moet deze voornaamen bevatten.
        if (null != kind.getPersoonVoornaam() && kind.getPersoonVoornaam().size() > 0) {
            for (PersoonVoornaamBericht persoonVoornaam : kind.getPersoonVoornaam()) {
                ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen,
                        persoonVoornaam, persoonVoornaam.getVolgnummer(), "volgnummer");
                ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, persoonVoornaam,
                        persoonVoornaam.getGegevens(), "naam");
                ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, persoonVoornaam,
                        persoonVoornaam.getGegevens().getVoornaam(), "naam");
            }
        }

        // Groep geslachtsnaam is niet verplicht, maar als er iets is in gevuld, moet het wel de geode waarde hebben.
        // is geslachtsnaam verplicht ?
        if (null != kind.getGeslachtsnaamcomponenten() && kind.getGeslachtsnaamcomponenten().size() > 0) {
            for (PersoonGeslachtsnaamcomponentBericht geslComp : kind.getGeslachtsnaamcomponenten()) {
                ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, geslComp,
                        geslComp.getVolgnummer(), "volgnummer");
                ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, geslComp,
                        geslComp.getGegevens(), "naam");
                ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, geslComp,
                        geslComp.getGegevens().getNaam(), "naam");
            }
        }
        return meldingen;
    }
}
