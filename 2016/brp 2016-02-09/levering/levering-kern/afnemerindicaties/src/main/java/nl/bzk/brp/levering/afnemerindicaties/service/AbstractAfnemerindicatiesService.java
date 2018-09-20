/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisAfnemerindicatieBlobRepository;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisPersTabelRepository;
import nl.bzk.brp.blobifier.service.AfnemerIndicatieBlobifierService;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.levering.afnemerindicaties.model.AfnemerindicatieNietGevondenExceptie;
import nl.bzk.brp.levering.afnemerindicaties.model.AfnemerindicatieReedsAanwezigExceptie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import org.springframework.beans.factory.annotation.Value;


/**
 * De abstracte klasse voor afnemenerindicaties service. Deze bevat de gedeelde logica tussen wel en niet uitvoeren van bedrijfsregels.
 */
public abstract class AbstractAfnemerindicatiesService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Value("${brp.locking.timeout.seconden}")
    private Integer lockingTimeOut;

    @Inject
    private BlobifierService blobifierService;

    @Inject
    private AfnemerIndicatieBlobifierService afnemerIndicatieBlobifierService;

    @Inject
    private HisAfnemerindicatieBlobRepository hisAfnemerindicatieBlobRepository;

    @Inject
    private nl.bzk.brp.dataaccess.repository.HisAfnemerindicatieTabelRepository hisAfnemerindicatieTabelRepository;

    @Inject
    private HisPersTabelRepository hisPersTabelRepository;

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    /**
     * Zoekt een bestaande afnemerindicatie voor de leveringsautorisatie en partij.
     *
     * @param huidigeAfnemerIndicaties De huidige lijst van afnemerindicaties.
     * @param leveringAutorisatieId    Het id van leveringautorisatie.
     * @param partijCodeWaarde         De code van de partij.
     * @return De afnemerindicatie, of null als deze niet gevonden is.
     */
    protected final PersoonAfnemerindicatieHisVolledigImpl vindBestaandeAfnemerindicatie(
        final Set<PersoonAfnemerindicatieHisVolledigImpl> huidigeAfnemerIndicaties,
        final int leveringAutorisatieId, final Integer partijCodeWaarde)
    {
        for (final PersoonAfnemerindicatieHisVolledigImpl huidigeAfnemerIndicatie : huidigeAfnemerIndicaties) {

            final int afnemerindicatieLeveringautorisatieId =
                huidigeAfnemerIndicatie.getLeveringsautorisatie().getWaarde().getID();
            final Integer afnemerindicatiePartijCodeWaarde =
                huidigeAfnemerIndicatie.getAfnemer().getWaarde().getCode().getWaarde();

            if (afnemerindicatieLeveringautorisatieId == leveringAutorisatieId
                && afnemerindicatiePartijCodeWaarde.equals(partijCodeWaarde))
            {
                return huidigeAfnemerIndicatie;
            }
        }
        return null;
    }

    /**
     * Voegt een afnemerindicatie toe aan een persoon.
     * <p/>
     * Zie TEAMBRP-4516: beschrijft de gekozen implementatie van tijdstip registratie van de afnemerindicatie.
     *
     * @param persoonId                    De persoonId.
     * @param bestaandeAfnemerIndicatie    De huidige afnemerindicatie, indien null moet deze nog worden aangemaakt
     * @param leveringsautorisatie         De leveringsautorisatie.
     * @param partij                       De partij.
     * @param dienst                       De dienst .
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen             De datum einde volgen.
     * @param tijdstipRegistratie          het tijdstip registratie.
     * @return the persoon afnemerindicatie his volledig impl
     */
    protected final PersoonAfnemerindicatieHisVolledigImpl voegAfnemerindicatieToe(final Integer persoonId,
        final PersoonAfnemerindicatieHisVolledigImpl bestaandeAfnemerIndicatie,
        final Leveringsautorisatie leveringsautorisatie,
        final Partij partij,
        final Dienst dienst,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        final PersoonAfnemerindicatieHisVolledigImpl relevanteAfnemerIndicatie;

        // Wanneer er al een A-laag record is wordt deze hergebruikt, anders wordt er een nieuw record aangemaakt.
        if (bestaandeAfnemerIndicatie != null) {
            relevanteAfnemerIndicatie = bestaandeAfnemerIndicatie;
        } else {
            relevanteAfnemerIndicatie = hisAfnemerindicatieTabelRepository.maakNieuweAfnemerIndicatie(persoonId, new PartijAttribuut(partij),
                new LeveringsautorisatieAttribuut(leveringsautorisatie));
        }

        voegHisRecordToe(dienst, relevanteAfnemerIndicatie, datumAanvangMaterielePeriode, datumEindeVolgen, tijdstipRegistratie);
        return relevanteAfnemerIndicatie;
    }

    /**
     * Voegt een his record toe.
     *
     * @param dienstInhoud                 De dienst inhoud.
     * @param nieuweAfnemerIndicatie       De afnemerindicatie his volledig.
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen             De datum einde volgen.
     * @param tijdstipRegistratie          Het tijdstip registratie.
     */
    protected final void voegHisRecordToe(final Dienst dienstInhoud,
        final PersoonAfnemerindicatieHisVolledigImpl nieuweAfnemerIndicatie,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen, final DatumTijdAttribuut tijdstipRegistratie)
    {
        final DatumTijdAttribuut tsReg;
        if (tijdstipRegistratie != null) {
            tsReg = tijdstipRegistratie;
        } else {
            tsReg = DatumTijdAttribuut.nu();
        }
        final HisPersoonAfnemerindicatieModel hisPersoonAfnemerindicatieModel =
            new HisPersoonAfnemerindicatieModel(nieuweAfnemerIndicatie, datumAanvangMaterielePeriode, datumEindeVolgen, dienstInhoud, tsReg);

        nieuweAfnemerIndicatie.getPersoonAfnemerindicatieHistorie().voegToe(hisPersoonAfnemerindicatieModel);
    }

    /**
     * Maakt een nieuwe blob voor de persoon.
     *
     * @param persoonId         De persoonId.
     * @param afnemerindicaties De afnemerIndicaties
     */
    protected final void blobifyPersoon(final Integer persoonId, final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties) {
        LOGGER.info(FunctioneleMelding.ALGEMEEN_BLOBIFICEER_PERSOON, "Blobify persoon:{}", persoonId);
        afnemerIndicatieBlobifierService.blobify(persoonId, afnemerindicaties);
    }

    /**
     * Zoekt dienst in lijst
     *
     * @param diensten De diensten.
     * @param dienstId De dienst id.
     * @return De dienst.
     */
    protected final Dienst haalDienstUitLijst(final Iterable<Dienst> diensten, final Integer dienstId) {
        for (final Dienst dienst : diensten) {
            if (dienst.getID().equals(dienstId)) {
                return dienst;
            }
        }

        LOGGER.warn("De opgegeven dienst met id '%s' is niet aanwezig in lijst.", dienstId);
        return null;
    }

    /**
     * Plaatst een afnemerindicatie.
     *
     * @param toegangLeveringsautorisatie De toegangLeveringsautorisatie.
     * @param persoonId                    De persoon his volledig.
     * @param dienst                       De dienst.
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen             De datum tijd einde volgen.
     * @param tijdstipRegistratie          Het tijdstip registratie.
     * @return De nieuwe afnemerIndicatie
     */
    protected final PersoonAfnemerindicatieHisVolledigImpl plaatsAfnemerindicatie(final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        final Integer persoonId, final Dienst dienst, final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen, final DatumTijdAttribuut tijdstipRegistratie)
    {

        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
        final Partij partij = toegangLeveringsautorisatie.getGeautoriseerde().getPartij();

        if (!hisPersTabelRepository.bestaatPersoonMetId(persoonId)) {
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PERSOON_ID, persoonId);
        }

        final Integer partijCodeWaarde = bepaalPartijCodeWaarde(partij);

        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerIndicaties =
            hisAfnemerindicatieBlobRepository.leesGenormaliseerdModelVoorNieuweBlob(persoonId);

        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledig =
            vindBestaandeAfnemerindicatie(afnemerIndicaties, leveringsautorisatie.getID(), partijCodeWaarde);

        final FormeleHistorieSet<HisPersoonAfnemerindicatieModel> bestaandeAfnemerindicatieHistorie =
            vindBestaandeAfnemerindicatieHistorie(afnemerindicatieHisVolledig);

        if (bestaandeAfnemerindicatieHistorie != null && bestaandeAfnemerindicatieHistorie.getActueleRecord() != null) {
            final String foutBericht = String.format("Er is al een afnemerindicatie voor persoon %d, partij %d en leveringsautorisatie %d.", persoonId,
                partijCodeWaarde, leveringsautorisatie.getID());
            LOGGER.debug(foutBericht);
            throw new AfnemerindicatieReedsAanwezigExceptie(foutBericht);
        }
        final PersoonAfnemerindicatieHisVolledigImpl nieuwePersoonAfnemerIndicatie =
            voegAfnemerindicatieToe(persoonId, afnemerindicatieHisVolledig, leveringsautorisatie, partij, dienst, datumAanvangMaterielePeriode,
                datumEindeVolgen, tijdstipRegistratie);

        afnemerIndicaties.add(nieuwePersoonAfnemerIndicatie);

        blobifyPersoon(persoonId, afnemerIndicaties);

        LOGGER.debug("Afnemerindicatie geplaatst op persoon {}, leveringsautorisatie {}, partij {}.", persoonId, leveringsautorisatie.getID(),
            partijCodeWaarde);
        return nieuwePersoonAfnemerIndicatie;
    }

    /**
     * Verwijdert een afnemerindicatie.
     *
     * @param partij              De partij.
     * @param persoonhisVolledig  De persoonhis volledig.
     * @param dienst              De dienst .
     * @param leveringAutorisatie De leveringAutorisatie.
     */
    protected final void verwijderAfnemerindicatie(final Partij partij, final PersoonHisVolledigImpl persoonhisVolledig, final Dienst dienst,
        final Leveringsautorisatie leveringAutorisatie)
    {
        final Integer partijCodeWaarde = bepaalPartijCodeWaarde(partij);
        final Integer persoonId = bepaalPersoonId(persoonhisVolledig);

        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledig =
            vindBestaandeAfnemerindicatie(persoonhisVolledig.getAfnemerindicaties(), leveringAutorisatie.getID(), partijCodeWaarde);

        final FormeleHistorieSet<HisPersoonAfnemerindicatieModel> bestaandeAfnemerindicatieHistorie =
            vindBestaandeAfnemerindicatieHistorie(afnemerindicatieHisVolledig);

        if (bestaandeAfnemerindicatieHistorie == null || bestaandeAfnemerindicatieHistorie.getActueleRecord() == null) {
            final String foutMelding = String.format("Het verwijderen van de afnemerindicatie is mislukt, er is geen afnemerindicatie"
                    + " aangetroffen met deze gegevens: persoonId %d, leveringsautorisatieId %s, partijCode %d", persoonId, leveringAutorisatie.getID(),
                partijCodeWaarde);
            LOGGER.debug(foutMelding);
            throw new AfnemerindicatieNietGevondenExceptie(foutMelding);
        }

        bestaandeAfnemerindicatieHistorie.verval(dienst, DatumTijdAttribuut.nu());

        blobifyPersoon(persoonId, persoonhisVolledig.getAfnemerindicaties());

        LOGGER.debug("Afnemerindicatie verwijderd van persoon {}, leveringsautorisatie {}, partij {}.",
            persoonId, leveringAutorisatie.getID(), partijCodeWaarde);
    }

    /**
     * Vind de bestaande afnemerindicatie historie.
     *
     * @param afnemerindicatieHisVolledig afnemerindicatie his volledig
     * @return de formele historie set, als deze er niet is, dan null
     */
    private FormeleHistorieSet<HisPersoonAfnemerindicatieModel> vindBestaandeAfnemerindicatieHistorie(
        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatieHisVolledig)
    {
        if (afnemerindicatieHisVolledig != null) {
            return afnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie();
        } else {
            return null;
        }
    }

    /**
     * Bepaalt de persoon id.
     *
     * @param persoonhisVolledig de persoonhis volledig.
     * @return integer waarde van de persoon identifier.
     */
    private Integer bepaalPersoonId(final PersoonHisVolledigImpl persoonhisVolledig) {
        if (persoonhisVolledig != null) {
            return persoonhisVolledig.getID();
        } else {
            final String foutmelding = geefFoutmeldingLegeWaarde("persoonhisvolledig");
            throw new IllegalArgumentException(foutmelding);
        }
    }

    /**
     * Bepaalt de waarde van de code van de partij.
     *
     * @param partij de partij.
     * @return integer waarde van de partijcode.
     */
    private Integer bepaalPartijCodeWaarde(final Partij partij) {
        if (partij != null) {
            return partij.getCode().getWaarde();
        } else {
            final String foutmelding = geefFoutmeldingLegeWaarde("partij");
            throw new IllegalArgumentException(foutmelding);
        }
    }

    /**
     * Geeft een foutmelding wanneer een verplicht object een lege waarde heeft.
     *
     * @param objectNaam naam van het object.
     * @return string met de foutmelding voor het type object.
     */
    private String geefFoutmeldingLegeWaarde(final String objectNaam) {
        final String foutmelding =
            String.format("Bij het verwijderen en plaatsen van afnemerindicaties dient %s gevuld te zijn.",
                objectNaam);
        LOGGER.error(foutmelding);
        return foutmelding;
    }

    /**
     * Geef een persoon wanneer deze bestaat op basis van persoon id.
     *
     * @param persoonId persoon id
     * @return de persoon his volledig impl als deze bestaat, anders wordt een OnbekendeReferentieExceptie gegooid.
     */
    protected final PersoonHisVolledigImpl geefPersoon(final Integer persoonId) {
        final PersoonHisVolledigImpl persoonHisVolledig = (PersoonHisVolledigImpl) persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);
        if (persoonHisVolledig != null) {
            return persoonHisVolledig;
        } else {
            LOGGER.warn("Persoon met id '{}' niet gevonden.", persoonId);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PERSOON_ID, persoonId);
        }
    }
}
