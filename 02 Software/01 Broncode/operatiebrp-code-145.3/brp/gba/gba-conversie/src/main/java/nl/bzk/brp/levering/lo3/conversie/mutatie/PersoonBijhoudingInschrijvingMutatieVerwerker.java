/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonBijhoudingMapper;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder.BijhoudingConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/bijhouding (voor inschrijving).
 */
@Component
public final class PersoonBijhoudingInschrijvingMutatieVerwerker extends AbstractMaterieelMutatieVerwerker<Lo3InschrijvingInhoud, BrpBijhoudingInhoud> {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private VerConvRepository verConvRepository;

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected PersoonBijhoudingInschrijvingMutatieVerwerker(final PersoonBijhoudingMapper mapper, final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new BijhoudingConverteerder(attribuutConverteerder), attribuutConverteerder, null, PersoonBijhoudingMapper.GROEP_ELEMENT, LOGGER);
    }

    /**
     * Bekijk of het actuele record van deze historie set via de lo3 herkomst is gekoppeld aan
     * categorie 08. Als dit zo is, dan bevat deze set geen wijzigingen voor deze mutatieverwerker
     * (cat 07) en moet de hele set worden genegeerd.
     */
    @Override
    protected Collection<MetaRecord> filterHistorieSet(final Collection<MetaRecord> historieSet) {
        // Bepaal actie inhoud die aan administratieve handeling hangt.
        Actie actueel = null;
        for (final MetaRecord historie : historieSet) {
            final Actie actieInhoud = historie.getActieInhoud();
            if ((historie.getActieVerval() == null) && (historie.getActieAanpassingGeldigheid() == null)) {
                actueel = actieInhoud;
                break;
            }
        }

        // Bepaal of deze via lo3 herkomst uit cat 8 komt
        final Lo3Voorkomen lo3Voorkomen = actueel == null ? null : verConvRepository.zoekLo3VoorkomenVoorActie(actueel.getId());
        if ((lo3Voorkomen != null) && (lo3Voorkomen.getCategorie() != null) && "08".equals(lo3Voorkomen.getCategorie())) {
            // Zo ja, hele set niet gebruiken
            LOGGER.info("Historie negeren; actuele actie inhoud is gekoppeld aan categorie 08");
            return Collections.emptySet();
        } else {
            // Zo nee, hele set gebruiken
            return historieSet;
        }
    }

    @Override
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final Lo3Documentatie documentatie) {
        // Geen documentatie voor categorie 07 (anders gaat groep 88 fout)
        return null;
    }

    @Override
    protected Lo3InschrijvingInhoud verwerkInhoud(final Lo3InschrijvingInhoud lo3Inhoud, final BrpBijhoudingInhoud brpInhoud,
                                                  final MetaRecord brpHistorie, final OnderzoekMapper onderzoekMapper) {
        final Lo3InschrijvingInhoud result = super.verwerkInhoud(lo3Inhoud, brpInhoud, brpHistorie, onderzoekMapper);

        if (heeftRedenOpschorting(brpInhoud)) {
            final Lo3InschrijvingInhoud.Builder builder = new Lo3InschrijvingInhoud.Builder(result);
            if (brpHistorie.getDatumAanvangGeldigheid() != null) {
                // Cat 07 heeft geen onderzoek, dus null is ok
                final BrpDatum datumAanvangGeldigheid = new BrpDatum(brpHistorie.getDatumAanvangGeldigheid(), null);
                builder.setDatumOpschortingBijhouding(datumAanvangGeldigheid.converteerNaarLo3Datum());
            }
            return builder.build();
        } else {
            return result;
        }
    }

    private boolean heeftRedenOpschorting(final BrpBijhoudingInhoud brpInhoud) {
        final Set<String> redenOpschortingWaardes =
                Stream.of(BrpNadereBijhoudingsaardCode.VERTROKKEN_ONBEKEND_WAARHEEN, BrpNadereBijhoudingsaardCode.RECHTSTREEKS_NIET_INGEZETENE,
                        BrpNadereBijhoudingsaardCode.EMIGRATIE, BrpNadereBijhoudingsaardCode.OVERLEDEN, BrpNadereBijhoudingsaardCode.BIJZONDERE_STATUS,
                        BrpNadereBijhoudingsaardCode.FOUT).map(BrpNadereBijhoudingsaardCode::getWaarde).collect(Collectors.toSet());

        return (brpInhoud.getNadereBijhoudingsaardCode() != null) && (brpInhoud.getNadereBijhoudingsaardCode().getWaarde() != null)
                && redenOpschortingWaardes.contains(brpInhoud.getNadereBijhoudingsaardCode().getWaarde());
    }
}
