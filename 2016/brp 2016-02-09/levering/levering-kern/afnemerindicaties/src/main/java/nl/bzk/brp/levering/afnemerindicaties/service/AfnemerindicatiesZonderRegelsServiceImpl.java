/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.springframework.stereotype.Service;


/**
 * Implementatie van de {@link nl.bzk.brp.levering.afnemerindicaties.service.AfnemerindicatiesZonderRegelsService}.
 *
 * @brp.bedrijfsregel VR00063
 * @brp.bedrijfsregel R1408
 */
@Service
@Regels({ Regel.VR00063, Regel.R1408 })
public class AfnemerindicatiesZonderRegelsServiceImpl extends AbstractAfnemerindicatiesService implements AfnemerindicatiesZonderRegelsService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Inject
    private PartijService partijService;

    @Override
    public final void plaatsAfnemerindicatie(final int toegangLeveringautorisatieId,
        final int persoonId, final int verantwoordingDienstId,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode, final DatumAttribuut datumEindeVolgen,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = toegangLeveringsautorisatieService.geefToegangleveringautorisatieZonderControle(toegangLeveringautorisatieId);
        plaatsAfnemerindicatie(toegangLeveringsautorisatie, persoonId, verantwoordingDienstId, datumAanvangMaterielePeriode, datumEindeVolgen, tijdstipRegistratie);
    }

    @Override
    @Regels(Regel.R1408)
    public final PersoonAfnemerindicatieHisVolledigImpl plaatsAfnemerindicatie(final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        final int persoonId,
        final int verantwoordingDienstId,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
        final Dienst verantwoordingDienst = haalDienstUitDeLijst(leveringsautorisatie.geefDiensten(), verantwoordingDienstId);
        final PersoonAfnemerindicatieHisVolledigImpl persoonAfnemerindicatieHisVolledig = plaatsAfnemerindicatie(toegangLeveringsautorisatie, persoonId,
                verantwoordingDienst,
                datumAanvangMaterielePeriode,
                datumEindeVolgen, tijdstipRegistratie);

        LOGGER.info(FunctioneleMelding.LEVERING_AFNEMERINDICATIE_GEPLAATST,
                "Afnemerindicatie geplaatst2. Leveringsautorisatie {}, persoonId {}, dienstId {}, partijCode {}", leveringsautorisatie.getID(), verantwoordingDienstId,
                verantwoordingDienstId,
                toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode());
        return persoonAfnemerindicatieHisVolledig;
    }


    @Override
    public final void verwijderAfnemerindicatie(final int toegangLeveringautorisatieId,
        final int persoonId, final int verantwoordingDienstId)
    {
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = toegangLeveringsautorisatieService.geefToegangleveringautorisatieZonderControle(toegangLeveringautorisatieId);
        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
        final Dienst verantwoordingDienst = haalDienstUitDeLijst(leveringsautorisatie.geefDiensten(), verantwoordingDienstId);
        final Partij partij = toegangLeveringsautorisatie.getGeautoriseerde().getPartij();
        final PersoonHisVolledigImpl persoonhisVolledig = geefPersoon(persoonId);
        verwijderAfnemerindicatie(partij, persoonhisVolledig, verantwoordingDienst, leveringsautorisatie);

        LOGGER.info(FunctioneleMelding.LEVERING_AFNEMERINDICATIE_VERWIJDERD,
            "Afnemerindicatie verwijderd. Leveringautorisatie {}, persoonId {}, dienstId {}, partijCode {}", leveringsautorisatie.getID(), persoonId, verantwoordingDienstId,
            partij.getCode());
    }


    private Dienst haalDienstUitDeLijst(final Collection<Dienst> diensten, final int dienstId) {
        final Dienst dienst = super.haalDienstUitLijst(diensten, dienstId);
        if (dienst == null) {
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.DIENST_ID, dienstId);
        }
        return dienst;
    }
}
