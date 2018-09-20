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
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsgemeenteGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.springframework.stereotype.Component;


/** Actie uitvoerder voor verhuizing. */
@Component
public class VerhuizingUitvoerder extends AbstractActieUitvoerder {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private VerhuisActieValidator verhuisActieValidator;

    @Override
    List<Melding> valideerActieGegevens(final Actie actie) {
        return verhuisActieValidator.valideerActie(actie);
    }

    @Override
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext) {
        List<Melding> meldingen = null;
        final Persoon pers = (Persoon) actie.getRootObjecten().get(0);
        final PersoonAdres nieuwAdres = pers.getAdressen().iterator().next();

        try {
            // Opslag van de actie
            ActieModel persistentActie = persisteerActie(actie, berichtContext);

            // Haal persoon op
            final PersoonModel pPersoon =
                persoonRepository.findByBurgerservicenummer(nieuwAdres.getPersoon().getIdentificatienummers()
                                                                      .getBurgerservicenummer());

            // Opslag nieuw adres
            persoonAdresRepository.opslaanNieuwPersoonAdres(new PersoonAdresModel(nieuwAdres, pPersoon),
                persistentActie, actie.getDatumAanvangGeldigheid());

            // BRPUC05108:
            PersoonBijhoudingsgemeenteGroepBericht bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepBericht();
            bijhoudingsgemeente.setDatumInschrijvingInGemeente(actie.getDatumAanvangGeldigheid());
            bijhoudingsgemeente.setBijhoudingsgemeente(nieuwAdres.getGegevens().getGemeente());
            // TODO nog niet in scope, tijdelijk op false gezet.
            // LET OP: Wordt ook false gezet in de bijhoudingsgemeenteafleiding klasse.
            bijhoudingsgemeente.setIndOnverwerktDocumentAanwezig(JaNee.Nee);

            //Opslag bijhoudingsgemeente.
            persoonRepository.werkbijBijhoudingsgemeente(
                nieuwAdres.getPersoon().getIdentificatienummers().getBurgerservicenummer(),
                bijhoudingsgemeente,
                persistentActie,
                actie.getDatumAanvangGeldigheid());
        } catch (OnbekendeReferentieExceptie e) {
            Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.REF0001, String.format(
                    "%s. Foutieve code/referentie '%s' voor veld '%s'.", MeldingCode.REF0001.getOmschrijving(),
                    e.getReferentieWaarde(), e.getReferentieVeldNaam()));
            meldingen = Arrays.asList(melding);
        }

        return meldingen;
    }

    @Override
    List<Melding> bereidActieVerwerkingVoor(final Actie actie, final BerichtContext berichtContext) {
        // Verhuizing actie heeft maar een persoon in de verhuizing
        final PersoonBericht pers = (PersoonBericht) actie.getRootObjecten().get(0);
        // In de verhuizing actie zit maar een adres voor de persoon
        final PersoonAdresBericht nieuwAdres = pers.getAdressen().iterator().next();
        // Adres dient wel van persoon te zijn voorzien.
        nieuwAdres.setPersoon(pers);

        List<Melding> meldingen = null;
        if (persoonRepository.findByBurgerservicenummer(nieuwAdres.getPersoon().getIdentificatienummers()
                                                                  .getBurgerservicenummer()) == null)
        {
            Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.ALG0001, String.format(
                        "Persoon met BSN: %s is niet bekend.", nieuwAdres.getPersoon().getIdentificatienummers()
                                .getBurgerservicenummer().getWaarde()),
                        nieuwAdres.getPersoon().getIdentificatienummers(),
                        "burgerservicenummer");
            meldingen = Arrays.asList(melding);
        } else {
            // Administratie spul: houdt bij de hoofdpersoon
            berichtContext.voegHoofdPersoonToe(pers);
        }
        return meldingen;
    }

}
