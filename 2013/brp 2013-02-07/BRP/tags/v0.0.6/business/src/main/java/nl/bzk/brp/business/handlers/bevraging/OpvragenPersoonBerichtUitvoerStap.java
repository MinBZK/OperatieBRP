/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bevraging;

import java.util.HashSet;
import javax.inject.Inject;

import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonBericht;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;

/** Uitvoer stap die het opvragen van een persoon afhandelt. De persoon wordt via de DAL laag opgehaald. */
public class OpvragenPersoonBerichtUitvoerStap
    extends AbstractBerichtVerwerkingsStap<OpvragenPersoonBericht, OpvragenPersoonResultaat>
{

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final OpvragenPersoonBericht bericht, final BerichtContext context,
        final OpvragenPersoonResultaat resultaat)
    {
        final PersistentPersoon gevondenPersoon =
            persoonRepository.findByBurgerservicenummer(
                bericht.getOpvragenPersoonCriteria().getIdentificatienummers().getBurgerservicenummer());

        if (gevondenPersoon != null) {
            resultaat.setAantal(1);
            resultaat.setGevondenPersonen(new HashSet<Persoon>());

            //@Todo oussama: model laag (LGM, OGM) is nog niet goed gebouwd, voorlopig maar even set/get constructies.
            final Persoon persoon = new Persoon();
            persoon.setIdentificatienummers(new PersoonIdentificatienummers());
            persoon.getIdentificatienummers().setBurgerservicenummer(gevondenPersoon.getBurgerservicenummer());
            persoon.getIdentificatienummers().setAdministratienummer(gevondenPersoon.getANummer());
            persoon.setGeslachtsNaam(gevondenPersoon.getGeslachtsNaam());
            persoon.setVoornamen(gevondenPersoon.getVoornaam());
            persoon.setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
            persoon.getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(gevondenPersoon.getGeslachtsAanduiding());
            persoon.setPersoonGeboorte(new PersoonGeboorte());
            persoon.getPersoonGeboorte().setDatumGeboorte(gevondenPersoon.getDatumGeboorte());
            persoon.setAdressen(new HashSet<PersoonAdres>());
            for (PersistentPersoonAdres persistentPersoonAdres : gevondenPersoon.getAdressen()) {
                final PersoonAdres adres = new PersoonAdres();
                adres.setSoort(persistentPersoonAdres.getSoort());
                adres.setDatumAanvangAdreshouding(persistentPersoonAdres.getDatumAanvangAdreshouding());
                adres.setAdresseerbaarObject(persistentPersoonAdres.getAdresseerbaarObject());
                adres.setIdentificatiecodeNummeraanduiding(
                    persistentPersoonAdres.getIdentificatiecodeNummeraanduiding());
                adres.setGemeente(persistentPersoonAdres.getGemeente());
                adres.setNaamOpenbareRuimte(persistentPersoonAdres.getNaamOpenbareRuimte());
                adres.setAfgekorteNaamOpenbareRuimte(persistentPersoonAdres.getAfgekorteNaamOpenbareRuimte());
                adres.setGemeentedeel(persistentPersoonAdres.getGemeentedeel());
                adres.setHuisnummer(persistentPersoonAdres.getHuisnummer());
                adres.setHuisletter(persistentPersoonAdres.getHuisletter());
                adres.setHuisnummertoevoeging(persistentPersoonAdres.getHuisnummertoevoeging());
                adres.setPostcode(persistentPersoonAdres.getPostcode());
                adres.setWoonplaats(persistentPersoonAdres.getWoonplaats());
                adres.setLocatieTovAdres(persistentPersoonAdres.getLocatietovAdres());
                adres.setLocatieOmschrijving(persistentPersoonAdres.getLocatieOmschrijving());
                persoon.getAdressen().add(adres);
            }
            resultaat.getGevondenPersonen().add(persoon);
        } else {
            resultaat.setAantal(0);
            resultaat.voegMeldingToe(new Melding(SoortMelding.INFO, MeldingCode.ALG0001,
                "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria."));
        }
        return AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
    }
}
