/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.HashSet;
import java.util.List;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.mapper.BijhoudingMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.logisch.verconv.LO3Voorkomen;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder.BijhoudingConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/bijhouding (voor inschrijving).
 */
@Component
public final class PersoonBijhoudingInschrijvingMutatieVerwerker
        extends AbstractMaterieelMutatieVerwerker<Lo3InschrijvingInhoud, BrpBijhoudingInhoud, HisPersoonBijhoudingModel>
{
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final MaterieleHistorieSet<HisPersoonBijhoudingModel> EMPTY_SET =
            new MaterieleHistorieSetImpl<>(new HashSet<HisPersoonBijhoudingModel>());

    @Autowired
    private VerConvRepository verConvRepository;

    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     */
    @Autowired
    protected PersoonBijhoudingInschrijvingMutatieVerwerker(final BijhoudingMapper mapper, final BijhoudingConverteerder converteerder) {
        super(mapper, converteerder, ElementEnum.PERSOON_INSCHRIJVING);
    }

    /**
     * Bekijk of het actuele record van deze historie set via de lo3 herkomst is gekoppeld aan categorie 08. Als dit zo
     * is, dan bevat deze set geen wijzigingen voor deze mutatieverwerker (cat 07) en moet de hele set worden genegeerd.
     */
    @Override
    protected MaterieleHistorieSet<HisPersoonBijhoudingModel> filterHistorieSet(
        final MaterieleHistorieSet<HisPersoonBijhoudingModel> historieSet,
        final List<Long> acties)
    {
        // Bepaal actie inhoud die aan administratieve handeling hangt.
        ActieModel actueel = null;
        for (final HisPersoonBijhoudingModel historie : historieSet) {
            final ActieModel actieInhoud = historie.getVerantwoordingInhoud();
            if (actieInhoud != null && acties.contains(actieInhoud.getID())) {
                actueel = actieInhoud;
                break;
            }
        }

        // Bepaal of deze via lo3 herkomst uit cat 8 komt
        final LO3Voorkomen lo3Voorkomen = verConvRepository.zoekLo3VoorkomenVoorActie(actueel);
        if (lo3Voorkomen != null && lo3Voorkomen.getLO3Categorie() != null && "08".equals(lo3Voorkomen.getLO3Categorie().getWaarde())) {
            // Zo ja, hele set niet gebruiken
            LOGGER.info("Historie negeren; actuele actie inhoud is gekoppeld aan categorie 08");
            return EMPTY_SET;
        } else {
            // Zo nee, hele set gebruiken
            return historieSet;
        }
    }

    @Override
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final HisPersoonBijhoudingModel brpHistorie, final Lo3Documentatie documentatie) {
        // Geen documentatie voor categorie 07 (anders gaat groep 88 fout)
        return null;
    }

    @Override
    protected Lo3InschrijvingInhoud verwerkInhoud(
        final Lo3InschrijvingInhoud lo3Inhoud,
        final BrpBijhoudingInhoud brpInhoud,
        final HisPersoonBijhoudingModel brpHistorie,
        final OnderzoekMapper onderzoekMapper)
    {
        final Lo3InschrijvingInhoud result = super.verwerkInhoud(lo3Inhoud, brpInhoud, brpHistorie, onderzoekMapper);

        if (heeftRedenOpschorting(brpInhoud)) {
            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(result);
            if (brpHistorie.getDatumAanvangGeldigheid() != null) {
                // Cat 07 heeft geen onderzoek, dus null is ok
                final BrpDatum datumAanvangGeldigheid = new BrpDatum(brpHistorie.getDatumAanvangGeldigheid().getWaarde(), null);
                builder.setDatumOpschortingBijhouding(datumAanvangGeldigheid.converteerNaarLo3Datum());
            }
            return builder.build();
        } else {
            return result;
        }
    }

    private boolean heeftRedenOpschorting(final BrpBijhoudingInhoud brpInhoud) {
        return brpInhoud.getNadereBijhoudingsaardCode() != null
               && brpInhoud.getNadereBijhoudingsaardCode().getWaarde() != null
               && (BrpNadereBijhoudingsaardCode.VERTROKKEN_ONBEKEND_WAARHEEN.getWaarde().equals(brpInhoud.getNadereBijhoudingsaardCode().getWaarde())
                   || BrpNadereBijhoudingsaardCode.RECHTSTREEKS_NIET_INGEZETENE.getWaarde().equals(brpInhoud.getNadereBijhoudingsaardCode().getWaarde())
                   || BrpNadereBijhoudingsaardCode.EMIGRATIE.getWaarde().equals(brpInhoud.getNadereBijhoudingsaardCode().getWaarde())
                   || BrpNadereBijhoudingsaardCode.OVERLEDEN.getWaarde().equals(brpInhoud.getNadereBijhoudingsaardCode().getWaarde())
                   || BrpNadereBijhoudingsaardCode.MINISTERIEEL_BESLUIT.getWaarde().equals(brpInhoud.getNadereBijhoudingsaardCode().getWaarde())
                   || BrpNadereBijhoudingsaardCode.FOUT.getWaarde().equals(brpInhoud.getNadereBijhoudingsaardCode().getWaarde()));
    }
}
