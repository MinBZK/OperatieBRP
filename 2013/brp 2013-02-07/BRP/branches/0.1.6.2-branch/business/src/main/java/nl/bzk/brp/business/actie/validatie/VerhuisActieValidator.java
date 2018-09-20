/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.bzk.brp.model.groep.logisch.PersoonIdentificatieNummersGroep;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
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
        meldingen.addAll(valideerPersoon(persoon));
        if (persoon.getAdressen() == null || persoon.getAdressen().isEmpty()) {
            meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), "adres")));
            // TODO # adressen = 0 !!
        } else {
            for (final PersoonAdres persoonAdres : persoon.getAdressen()) {
                meldingen.addAll(valideerVerhuisAdres(persoonAdres));
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
    private Collection<? extends Melding> valideerVerhuisAdres(final PersoonAdres persoonAdres) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        // Als binnelands adres, zijn 2 vormen of (LocatieOmschrijving+Land of structurele adres)
        if (AttribuutTypeUtil.isNotBlank(persoonAdres.getGegevens().getLocatieOmschrijving())) {
            ValidatieUtil.controleerVerplichtVeld(meldingen,
                    persoonAdres.getGegevens().getLocatieOmschrijving(), "locatieOmschrijving");
            ValidatieUtil.controleerVerplichtVeld(meldingen, persoonAdres.getGegevens().getLand(), "land");
        } else {
            //structurele adres: Naam openbare ruimte, Huisnummer, Land
            ValidatieUtil.controleerVerplichtVeld(meldingen,
                    persoonAdres.getGegevens().getNaamOpenbareRuimte(), "naamOpenbareRuimte");
            ValidatieUtil.controleerVerplichtVeld(meldingen,
                    persoonAdres.getGegevens().getHuisnummer(), "huisnummer");
            ValidatieUtil.controleerVerplichtVeld(meldingen,
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
    private Collection<? extends Melding> valideerPersoon(final Persoon persoon) {
        final List<Melding> meldingen = new ArrayList<Melding>();
        PersoonIdentificatieNummersGroep persoonIdentificatienummers = persoon.getIdentificatieNummers();
        ValidatieUtil.controleerVerplichtVeld(meldingen, persoonIdentificatienummers, "Identificatienummers");
        if (persoonIdentificatienummers != null) {
            ValidatieUtil.controleerVerplichtVeld(meldingen, persoonIdentificatienummers.getBurgerServiceNummer(),
                    "Burgerservicenummer");
        }
        return meldingen;
    }
}
