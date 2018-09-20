/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.actie.validatie.VerhuisActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


/**
 * Actie uitvoerder voor verhuizing.
 */
@Component
public class VerhuizingUitvoerder extends AbstractActieUitvoerder {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Inject
    private PersoonRepository      persoonRepository;

    @Inject
    private VerhuisActieValidator  verhuisActieValidator;

    @Override
    List<Melding> valideerActieGegevens(final BRPActie actie) {
        return verhuisActieValidator.valideerActie(actie);
    }

    @Override
    List<Melding> verwerkActie(final BRPActie actie, final BerichtContext berichtContext) {
        // Verhuizing actie heeft maar een persoon in de verhuizing
        final Persoon pers = (Persoon) actie.getRootObjecten().get(0);
        // In de verhuizing actie zit maar een adres voor de persoon
        final PersoonAdres nieuwAdres = pers.getAdressen().iterator().next();
        // Adres dient wel van persoon te zijn voorzien.
        nieuwAdres.setPersoon(pers);

        List<Melding> meldingen = null;
        try {
            if (persoonRepository.findByBurgerservicenummer(nieuwAdres.getPersoon().getIdentificatienummers()
                    .getBurgerservicenummer()) == null)
            {
                Melding melding =
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001, String.format(
                            "Persoon met BSN: %s is niet bekend.", nieuwAdres.getPersoon().getIdentificatienummers()
                                    .getBurgerservicenummer()));
                meldingen = Arrays.asList(melding);
            } else {
                // TODO tijdelijke fix, als er geen land opgegeven is dan tijdelijk land op NL zetten
                if (nieuwAdres.getLand() == null || StringUtils.isBlank(nieuwAdres.getLand().getLandcode())) {
                    nieuwAdres.setLand(new Land());
                    nieuwAdres.getLand().setLandcode("6030");
                }
                // BRPUC05108:
                persoonAdresRepository.opslaanNieuwPersoonAdres(nieuwAdres, actie.getDatumAanvangGeldigheid(), null,
                        berichtContext.getTijdstipVerwerking());

                PersoonBijhoudingsGemeente bijhoudingsGemeente = new PersoonBijhoudingsGemeente();
                bijhoudingsGemeente.setDatumInschrijving(actie.getDatumAanvangGeldigheid());
                bijhoudingsGemeente.setGemeente(nieuwAdres.getGemeente());
                // TODO nog niet in scope, tijdelijk of false gezet.
                bijhoudingsGemeente.setIndOnverwerktDocumentAanwezig(false);

                persoonRepository.werkbijBijhoudingsGemeente(nieuwAdres.getPersoon().getIdentificatienummers()
                        .getBurgerservicenummer(), bijhoudingsGemeente, actie.getDatumAanvangGeldigheid(),
                        berichtContext.getTijdstipVerwerking());

                // Administratie spul: houdt bij de hoofdpersoon
                berichtContext.voegHoofdPersoonToe(pers);
            }
        } catch (OnbekendeReferentieExceptie e) {
            Melding melding =
                new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001, String.format(
                        "%s. Foutieve code/referentie '%s' voor veld '%s'.", MeldingCode.REF0001.getOmschrijving(),
                        e.getReferentieWaarde(), e.getReferentieVeldNaam()));
            meldingen = Arrays.asList(melding);
        }

        return meldingen;
    }

}
