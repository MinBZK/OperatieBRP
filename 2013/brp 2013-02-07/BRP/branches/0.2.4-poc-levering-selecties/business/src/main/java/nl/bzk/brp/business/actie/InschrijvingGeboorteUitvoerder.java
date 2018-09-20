/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.actie.validatie.GeboorteActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.groep.bericht.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/** Actie uitvoerder voor verhuizing. */
@Component
public class InschrijvingGeboorteUitvoerder extends AbstractActieUitvoerder {
    private static final Logger LOGGER = LoggerFactory.getLogger(InschrijvingGeboorteUitvoerder.class);

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Inject
    private GeboorteActieValidator geboorteActieValidator;

    @Override
    List<Melding> valideerActieGegevens(final Actie actie) {
        return geboorteActieValidator.valideerActie(actie);
    }

    @Override
    List<Melding> verwerkActie(final Actie actie, final BerichtContext berichtContext) {
        return voerInschrijvingGeboorteDoor(actie, berichtContext);
    }

    @Override
    List<Melding> bereidActieVerwerkingVoor(final Actie actie, final BerichtContext berichtContext) {
        if (actie == null) {
            throw new IllegalArgumentException("Actie kan niet leeg/null zijn bij uitvoering van de actie.");
        }

        List<Melding> meldingen;
        Relatie nieuweFamilieRechtelijkeBetrekking = haalRelatieUitActie(actie);
        if (nieuweFamilieRechtelijkeBetrekking == null) {
            meldingen = Arrays.asList(new Melding(Soortmelding.FOUT, MeldingCode.ALG0002,
                MeldingCode.ALG0002.getOmschrijving() + ": Geen relatie gevonden"));
        } else {
            meldingen = new ArrayList<Melding>();
            if (nieuweFamilieRechtelijkeBetrekking.getBetrokkenheden() == null) {
                meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.ALG0002,
                    "Er zijn geen betrokkenheden (kind en/of ouder) opgenomen terwijl deze wel verwacht worden."));
            } else {
                for (Betrokkenheid betrokkenheid : nieuweFamilieRechtelijkeBetrekking.getBetrokkenheden()) {
                    if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol()) {
                        // ToDo: Indicatie ouder wordt hier gezet, maar mogelijk moet dat ergens anders!
                        ((BetrokkenheidBericht) betrokkenheid).getBetrokkenheidOuderschap().setIndOuder(Ja.Ja);

                        if (betrokkenheid.getBetrokkene() == null) {
                            meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.ALG0002,
                                "Er is geen ouder opgegeven"));
                        } else {
                            Persoon ouder = betrokkenheid.getBetrokkene();
                            if (ouder.getIdentificatienummers() == null
                                || ouder.getIdentificatienummers().getBurgerservicenummer() == null)
                            {
                                meldingen.add(new Melding(Soortmelding.FOUT, MeldingCode.ALG0002,
                                    "Het is verplicht om een BSN op te geven bij een ouder."));
                            }
                        }
                    }
                }
            }
        }
        return meldingen;
    }

    /**
     * Haalt de (eerste) relatie uit de actie en retourneert deze. Indien er geen Relatie als root object in de actie
     * is opgenomen dan zal <code>null</code> worden geretourneerd.
     *
     * @param actie de actie waaruit de relatie zal worden opgehaald.
     * @return de relatie die als root object in de actie is opgenomen of <code>null</code> indien die er niet is.
     */
    private Relatie haalRelatieUitActie(final Actie actie) {
        Relatie relatie = null;
        if (actie.getRootObjecten() != null) {
            for (RootObject rootObject : actie.getRootObjecten()) {
                if (rootObject instanceof Relatie) {
                    relatie = (Relatie) rootObject;
                }
            }
        }
        return relatie;
    }

    /**
     * Voert de werkelijke inschrijving door geboorte door middels het opslaan van het kind en de relatie.
     * <p/>
     * moet worden ingeschreven.
     *
     * @param actie de actie waarbinnen de inschrijving plaatsvindt.
     * @param berichtContext de context waarbinnen het bericht en dus de actie uitgevoerd dient te worden.
     * @return eventueel opgetreden meldingen/fouten, welke <code>null</code> kan zijn indien er geen
     *         meldingen/fouten zijn.
     */
    private List<Melding> voerInschrijvingGeboorteDoor(final Actie actie, final BerichtContext berichtContext) {
        List<Melding> meldingen;
        final Relatie nieuwFamilieRechtelijkeBetrekking = haalRelatieUitActie(actie);
        PersoonBericht kind = (PersoonBericht) nieuwFamilieRechtelijkeBetrekking.getKindBetrokkenheid().getBetrokkene();
        kind.setSoort(SoortPersoon.INGESCHREVENE);
        PersoonModel nieuwGeborene = null;

        // Sla op actie
        final ActieModel persistentActie = persisteerActie(actie, berichtContext);
        try {
            // eehm, afleidingsregel misschien ?
            kind.setInschrijving(new PersoonInschrijvingGroepBericht());
            kind.getInschrijving().setDatumInschrijving(actie.getDatumAanvangGeldigheid());
            kind.getInschrijving().setVersienummer(new Versienummer(1L));
            kind.setSoort(SoortPersoon.INGESCHREVENE);

            // eehm
            // Ook deze groep is eigenlijk verplicht voor elk persoon (voor laatsGewizigd)
            kind.setAfgeleidAdministratief(new PersoonAfgeleidAdministratiefGroepBericht());
            kind.getAfgeleidAdministratief().setTijdstipLaatsteWijziging(persistentActie.getTijdstipRegistratie());

            PersoonModel nieuwKind = new PersoonModel(kind);
            if (kind.getGeslachtsnaamcomponenten() != null) {
                for (PersoonGeslachtsnaamcomponentBericht geslCompBer : kind.getGeslachtsnaamcomponenten()) {
                    nieuwKind.getGeslachtsnaamcomponenten().add(
                        new PersoonGeslachtsnaamcomponentModel(geslCompBer, nieuwKind));
                }
            }
            if (kind.getPersoonVoornaam() != null) {
                for (PersoonVoornaamBericht persoonVoornaamBericht : kind.getPersoonVoornaam()) {
                    nieuwKind.getPersoonVoornaam().add(new PersoonVoornaamModel(persoonVoornaamBericht, nieuwKind));
                }
            }
            if (kind.getAdressen() != null) {
                for (PersoonAdresBericht persoonAdresBericht : kind.getAdressen()) {
                    nieuwKind.getAdressen().add(new PersoonAdresModel(persoonAdresBericht, nieuwKind));
                }
            }
            nieuwGeborene = persoonRepository.opslaanNieuwPersoon(nieuwKind, persistentActie,
                actie.getDatumAanvangGeldigheid());
            LOGGER.debug("Kind is opgeslagen");
            // kind.setId(nieuwGeborene.getId()); TODO waarom was dit?
            meldingen = null;
        } catch (ObjectReedsBestaandExceptie e) {
            meldingen = Arrays.asList(new Melding(Soortmelding.FOUT, MeldingCode.BRBER00121));
        } catch (OnbekendeReferentieExceptie e) {
            Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.REF0001, String.format(
                    "%s. Foutieve code/referentie '%s' voor veld '%s'.", MeldingCode.REF0001.getOmschrijving(),
                    e.getReferentieWaarde(), e.getReferentieVeldNaam()), kind, null);
            meldingen = Arrays.asList(melding);
        }

        if (meldingen == null) {
            RelatieModel relatie = new RelatieModel(nieuwFamilieRechtelijkeBetrekking);
            // Voeg kind toe als betrokkenheid
            relatie.getBetrokkenheden().add(
                new BetrokkenheidModel(nieuwFamilieRechtelijkeBetrekking.getKindBetrokkenheid(), nieuwGeborene,
                    relatie));
            // Voeg ouders toe als betrokkenheden
            List<String> bekendeOuders = new ArrayList<String>();
            for (Betrokkenheid ouder : nieuwFamilieRechtelijkeBetrekking.getOuderBetrokkenheden()) {
                Burgerservicenummer bsn = ouder.getBetrokkene().getIdentificatienummers().getBurgerservicenummer();

                // ToDo: Hier hoort ook het tijdstiplaatstewijziging gebruikt te worden, maar nu nog niet
                PersoonModel opgehaaldeOuder = persoonRepository.findByBurgerservicenummer(bsn);
                if (opgehaaldeOuder != null) {

                    // TODO: bolie: tijdelijke validatie (2x ouders, zelfde persoon =>FOUT_ONOVERRULEBAAR
                    if (bekendeOuders.contains(bsn.getWaarde())) {
                        meldingen = Arrays.asList(new Melding(Soortmelding.FOUT, MeldingCode.REF0001,
                            "Het bericht bevat 2 ouders met hetzelfde BSN.", (Identificeerbaar) ouder, bsn
                            .getWaarde()));
                    } else {
                        relatie.getBetrokkenheden().add(new BetrokkenheidModel(ouder, opgehaaldeOuder, relatie));
                        bekendeOuders.add(bsn.getWaarde());
                    }
                } else {
                    meldingen = Arrays.asList(new Melding(Soortmelding.FOUT, MeldingCode.REF0001,
                        String.format("Ouder (bsn: %s) is niet bekend", bsn), (Identificeerbaar) ouder,
                        "burgerservicenummer"));
                    break;
                }
            }

            relatieRepository.opslaanNieuweRelatie(relatie, persistentActie, actie.getDatumAanvangGeldigheid());
        }
        if (meldingen == null) {
            registreerBijgehoudenPersonen(berichtContext, nieuwFamilieRechtelijkeBetrekking);
        }


        return meldingen;
    }

    /**
     * Werkt de bericht context bij met de bijgehouden personen.
     *
     * @param berichtContext de bericht context die bijgewerkt dient te worden.
     * @param relatie de nieuwe familierechtelijke betrekking.
     */
    private void registreerBijgehoudenPersonen(final BerichtContext berichtContext, final Relatie relatie)
    {
        // Werk de bericht context bij met de bijgehouden personen.
        if (null != relatie && null != relatie.getKindBetrokkenheid()
            && null != relatie.getKindBetrokkenheid().getBetrokkene())
        {
            Persoon ingeschrevenKind = relatie.getKindBetrokkenheid().getBetrokkene();
            // Administratie spul: houdt bij de hoofdpersoon
            berichtContext.voegHoofdPersoonToe(ingeschrevenKind);

            // houdt nu bij alle personen die geraakt zijn.
            if (relatie.getBetrokkenheden() != null) {
                for (Betrokkenheid betr : relatie.getBetrokkenheden()) {
                    Persoon persoon = betr.getBetrokkene();
                    Burgerservicenummer bsn = persoon.getIdentificatienummers().getBurgerservicenummer();
                    if (AttribuutTypeUtil.isNotBlank(bsn)
                        && !bsn.equals(ingeschrevenKind.getIdentificatienummers().getBurgerservicenummer()))
                    {
                        berichtContext.voegBijPersoonToe(betr.getBetrokkene());
                    }
                }
            }
        }
    }

}
