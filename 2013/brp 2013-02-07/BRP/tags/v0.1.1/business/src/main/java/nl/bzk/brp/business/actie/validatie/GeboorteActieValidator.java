/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import org.springframework.stereotype.Component;


/**
 * Validator die geboorte (eerste inschrijving) acties valideert.
 */
@Component
public class GeboorteActieValidator implements ActieValidator {

    @Override
    public List<Melding> valideerActie(final BRPActie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        if (null == actie.getRootObjecten() || 0 == actie.getRootObjecten().size()) {
            ValidatieUtil.voegMeldingVerplichtVeld(meldingen, "kind");
        } else {
            final Persoon kind = (Persoon) actie.getRootObjecten().get(0);
            meldingen.addAll(valideerKind(kind));
        }
        return meldingen;
    }

    /**
     * Valideert de gegevens van het kind.
     *
     * @param kind Het te valideren kind.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerKind(final Persoon kind) {
        final List<Melding> meldingen = new ArrayList<Melding>();

        // identificatie nrs zijn verplicht.
        PersoonIdentificatienummers persoonIdentificatienummers = kind.getIdentificatienummers();
        if (ValidatieUtil.controleerVerplichtVeld(meldingen, persoonIdentificatienummers, "Identificatienummers")) {
            ValidatieUtil.controleerVerplichtVeld(meldingen, persoonIdentificatienummers.getBurgerservicenummer(),
                    "Burgerservicenummer");
            ValidatieUtil.controleerVerplichtVeld(meldingen, persoonIdentificatienummers.getAdministratienummer(),
                    "administratienummer");
        }

        // geslachtsAanduiding is verplicht
        if (ValidatieUtil.controleerVerplichtVeld(meldingen, kind.getPersoonGeslachtsAanduiding(),
                "geslachtsaanduiding"))
        {
            ValidatieUtil.controleerVerplichtVeld(meldingen, kind.getPersoonGeslachtsAanduiding()
                    .getGeslachtsAanduiding(), "geslachtsaanduiding");
        }

        // geboorteDatum is verplicht
        if (ValidatieUtil.controleerVerplichtVeld(meldingen, kind.getGeboorte(), "geboorte")) {
            ValidatieUtil.controleerVerplichtVeld(meldingen, kind.getGeboorte().getDatumGeboorte(),
                    "datumGeboorte");
            ValidatieUtil.controleerVerplichtVeld(meldingen, kind.getGeboorte().getGemeenteGeboorte(),
                    "gemeenteGeboorte");
            ValidatieUtil.controleerVerplichtVeld(meldingen, kind.getGeboorte().getLandGeboorte(),
                    "landGeboorte");
        }

        // Groep voornamen is niet verplicht, maar als er een ingevuld is, moet deze voornaamen bevatten.
        if (null != kind.getPersoonVoornamen() && kind.getPersoonVoornamen().size() > 0) {
            for (PersoonVoornaam persoonVoornaam : kind.getPersoonVoornamen()) {
                ValidatieUtil.controleerVerplichtVeld(meldingen, persoonVoornaam.getVolgnummer(), "volgnummer");
                ValidatieUtil.controleerVerplichtVeld(meldingen, persoonVoornaam.getNaam(), "naam");
            }
        }

        // Groep geslachtsnaam is niet verplicht, maar als er iets is in gevuld, moet het wel de geode waarde hebben.
        // is geslachtsnaam verplicht ?
        if (null != kind.getGeslachtsnaamcomponenten() && kind.getGeslachtsnaamcomponenten().size() > 0) {
            for (PersoonGeslachtsnaamcomponent geslComp : kind.getGeslachtsnaamcomponenten()) {
                ValidatieUtil.controleerVerplichtVeld(meldingen, geslComp.getVolgnummer(), "volgnummer");
                ValidatieUtil.controleerVerplichtVeld(meldingen, geslComp.getNaam(), "naam");
            }
        }
        return meldingen;
    }
}
