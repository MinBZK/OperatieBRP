/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bevraging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.ObjectUtil;
import org.apache.commons.lang.StringUtils;


/** Uitvoer stap die het opvragen van een persoon afhandelt. De persoon wordt via de DAL laag opgehaald. */
public class OpvragenPersoonBerichtUitvoerStap extends
        AbstractBerichtVerwerkingsStap<BRPBericht, OpvragenPersoonResultaat>
{

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BRPBericht bericht, final BerichtContext context,
            final OpvragenPersoonResultaat resultaat)
    {
        boolean verwerkingsResultaat;

        if (bericht instanceof VraagDetailsPersoonBericht) {
            verwerkingsResultaat = vraagOpDetailPersoon((VraagDetailsPersoonBericht) bericht, resultaat);
        } else if (bericht instanceof VraagPersonenOpAdresInclusiefBetrokkenhedenBericht) {
            verwerkingsResultaat =
                vraagOpPersonenOpAdresInclusiefBetrokkenheden(
                        (VraagPersonenOpAdresInclusiefBetrokkenhedenBericht) bericht, resultaat);
        } else if (bericht instanceof VraagKandidaatVaderBericht) {
            verwerkingsResultaat = vraagOpKandidaatVader((VraagKandidaatVaderBericht) bericht, resultaat);
        } else {
            verwerkingsResultaat = AbstractBerichtVerwerkingsStap.STOP_VERWERKING;
        }

        return verwerkingsResultaat;
    }

    /**
     * Methode om persoon details op te halen.
     *
     * @param bericht het VraagDetailsPersoonBericht.
     * @param resultaat een set met gevonden personen.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    private boolean vraagOpDetailPersoon(final VraagDetailsPersoonBericht bericht,
            final OpvragenPersoonResultaat resultaat)
    {
        final Persoon gevondenPersoon =
            persoonRepository.haalPersoonOpMetBurgerservicenummer(bericht.getVraag().getBurgerservicenummer());

        if (gevondenPersoon != null) {
            // TODO: bolie: eigenlijk moet nog getest/gevalideerd worden of deze data voldoet aan de xsd
            // (bv. not null etc.) De reden is dat JIBx dadelijk over zijn nek gaat als het verplicht is
            // deze gevondenPersoon deze waarden niet hebben.

            resultaat.setGevondenPersonen(new HashSet<Persoon>());
            resultaat.getGevondenPersonen().add(gevondenPersoon);
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.INFO, MeldingCode.ALG0001,
                    "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria."));
        }
        return AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
    }

    /**
     * Methode om alle personen op te halen die op het adres zijn ingeschreven op basis van de vraag in het bericht.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht.
     * @param resultaat een set met gevonden personen.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    private boolean vraagOpPersonenOpAdresInclusiefBetrokkenheden(
            final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht, final OpvragenPersoonResultaat resultaat)
    {
        final List<Persoon> gevondenPersonen;

        if (isBsnCriteria(bericht)) {
            gevondenPersonen =
                persoonRepository.haalPersonenMetAdresOpViaBurgerservicenummer(bericht.getVraag()
                        .getBurgerservicenummer());
        } else if (isPostcodeCriteria(bericht)) {
            gevondenPersonen =
                persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(bericht.getVraag().getPostcode(), bericht
                        .getVraag().getHuisnummer(), bericht.getVraag().getHuisletter(), bericht.getVraag()
                        .getHuisnummertoevoeging());
        } else if (isGemCodeCriteria(bericht)) {
            // TODO implementeer aanroep naar juiste methode om te zoeken met gemeente adres
            gevondenPersonen = new ArrayList<Persoon>();
        } else {
            gevondenPersonen = new ArrayList<Persoon>();
        }

        if ((gevondenPersonen != null) && !gevondenPersonen.isEmpty()) {
            resultaat.setGevondenPersonen(new HashSet<Persoon>(gevondenPersonen));
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.INFO, MeldingCode.ALG0001,
                    "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria."));
        }

        return AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
    }

    /**
     * Methode om alle personen op te halen die mogelijk de vader zou kunnen zijn .
     *
     * @param bericht het bericht
     * @param resultaat de lijst van personen die potentieel vader kunnen zijn.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    private boolean vraagOpKandidaatVader(final VraagKandidaatVaderBericht bericht,
            final OpvragenPersoonResultaat resultaat)
    {
        final List<Persoon> gevondenPersonen =
            persoonRepository.haalPotentieleVaderViaBsnMoeder(bericht.getVraag().getBurgerservicenummer());

        if ((gevondenPersonen != null) && !gevondenPersonen.isEmpty()) {
            resultaat.setGevondenPersonen(new HashSet<Persoon>(gevondenPersonen));
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.INFO, MeldingCode.ALG0001,
                    "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria."));
        }

        return AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
    }

    /**
     * Controlleer of het om een bsn zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als alleen de BSN is ingevuld in het zoek bericht
     */
    private boolean isBsnCriteria(final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht) {
        return StringUtils.isNotBlank(bericht.getVraag().getBurgerservicenummer())
            && ObjectUtil.isAllEmpty(bericht.getVraag().getPostcode(), bericht.getVraag().getHuisnummer(), bericht
                    .getVraag().getHuisletter(), bericht.getVraag().getHuisnummertoevoeging(), bericht.getVraag()
                    .getNaamOpenbareRuimte(), bericht.getVraag().getGemeenteCode());
    }

    /**
     * Controlleer of het om een postcode zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als het gaat om postcode huisnummer zoek criteria gaat
     */
    private boolean isPostcodeCriteria(final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht) {
        return StringUtils.isNotBlank(bericht.getVraag().getPostcode())
            && StringUtils.isNotBlank(bericht.getVraag().getHuisnummer())
            && ObjectUtil.isAllEmpty(bericht.getVraag().getBurgerservicenummer(), bericht.getVraag()
                    .getNaamOpenbareRuimte(), bericht.getVraag().getGemeenteCode());
    }

    /**
     * Controlleer of het om een gemeente code zoek criteria gaat.
     *
     * @param bericht het VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
     * @return true als het gaat om plaats adres huisnummer zoek criteria gaat
     */
    private boolean isGemCodeCriteria(final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht bericht) {
        return StringUtils.isNotBlank(bericht.getVraag().getHuisnummer())
            && StringUtils.isNotBlank(bericht.getVraag().getNaamOpenbareRuimte())
            && StringUtils.isNotBlank(bericht.getVraag().getGemeenteCode())
            && ObjectUtil.isAllEmpty(bericht.getVraag().getBurgerservicenummer(), bericht.getVraag().getPostcode());
    }

}
