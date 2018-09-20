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
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.springframework.stereotype.Component;


/**
 * Validator die verhuis acties valideert.
 *
 * Verplichte velden en groepen staan beschreven in BRP_GAG_RegistrerenVerhuizingBinnenNederland
 * Persoon -
 * Bijhoudingverantwoordelijkheid -
 * Bijhoudingsgemeente O
 * Adres (Soort, Redenwijziging, Datum aanvang adreshouding, Gemeente,
 * [[Naam openbare ruimte, Huisnummer, Land] | [Locatie, Land]] | [Buitenlandadres1..6, Datum vertrek uit NL, Land])
 */
@Component
public class VerhuisActieValidator implements ActieValidator {

    @Override
    public List<Melding> valideerActie(final Actie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
        meldingen.addAll(valideerPersoon((PersoonBericht) persoon));
        if (persoon.getAdressen() == null || persoon.getAdressen().isEmpty()) {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), "adres")));
            // TODO # adressen = 0 !!
        } else {
            for (final PersoonAdres persoonAdres : persoon.getAdressen()) {
                meldingen.addAll(valideerVerhuisAdres((PersoonAdresBericht) persoonAdres));
            }
        }
        // TODO: check of de BSN+alle andere een geldig persoon in het systeem bekend is.
        // TODO: Het gaat hier om een verhuis bericht -> test of de persoon al een adres heeft.
        return meldingen;
    }

    /**
     * Valideert het adres waarnaar verhuist word.
     *
     * @param persoonAdres Het te valideren adres.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerVerhuisAdres(final PersoonAdresBericht persoonAdres) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        // Als binnelands adres, zijn 2 vormen of (LocatieOmschrijving+Land of structurele adres)
        if (AttribuutTypeUtil.isNotBlank(persoonAdres.getGegevens().getLocatieOmschrijving())) {
            ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, persoonAdres,
                    persoonAdres.getGegevens().getLocatieOmschrijving(), "locatieOmschrijving");
            ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen,
                    persoonAdres, persoonAdres.getGegevens().getLand(), "land");
        } else {
            //structurele adres: Naam openbare ruimte, Huisnummer, Land
            ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, persoonAdres,
                    persoonAdres.getGegevens().getNaamOpenbareRuimte(), "naamOpenbareRuimte");
            ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, persoonAdres,
                    persoonAdres.getGegevens().getHuisnummer(), "huisnummer");
            ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, persoonAdres,
                    persoonAdres.getGegevens().getLand(), "land");
        }

        // in geval van buitelands adres: TODO (buitenalnds adres supporten we voorlopig niet)
        return meldingen;
    }

    /**
     * Valideert de persoon in de verhuis actie.
     *
     * @param persoon De te valideren persoon.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerPersoon(final PersoonBericht persoon) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        PersoonIdentificatienummersGroepBericht persoonIdentificatienummers = persoon.getIdentificatienummers();
        ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, persoon, persoonIdentificatienummers,
                "Identificatienummers");
        if (persoonIdentificatienummers != null) {
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, persoonIdentificatienummers,
                    persoonIdentificatienummers.getBurgerservicenummer(), "Burgerservicenummer");
        }
        return meldingen;
    }
}
