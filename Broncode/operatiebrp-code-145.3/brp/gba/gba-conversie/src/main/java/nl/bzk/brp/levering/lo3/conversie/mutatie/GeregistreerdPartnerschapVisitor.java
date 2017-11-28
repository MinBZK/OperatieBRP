/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapRelatieMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapVerbintenisMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Geregistreerd partnerschap visitor.
 */
@Component
public final class GeregistreerdPartnerschapVisitor extends AbstractRelatieVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private GeregistreerdPartnerschapRelatieMutatieVerwerker relatieMutatieVerwerker;
    @Autowired
    private GeregistreerdPartnerschapVerbintenisMutatieVerwerker verbintenisMutatieVerwerker;

    @Autowired
    private GeregistreerdPartnerschapGeboorteMutatieVerwerker geboorteMutatieVerwerker;
    @Autowired
    private GeregistreerdPartnerschapIdentificatienummersMutatieVerwerker identificatienummersMutatieVerwerker;
    @Autowired
    private GeregistreerdPartnerschapSamengesteldeNaamMutatieVerwerker samengesteldeNaamMutatieVerwerker;
    @Autowired
    private GeregistreerdPartnerschapGeslachtsaanduidingMutatieVerwerker geslachtsaanduidingMutatieVerwerker;

    @Autowired
    private HuwelijkGeslachtAdellijkeTitelPredikaatNabewerking geslachtAdellijkeTitelPredikaatNabewerking;

    /**
     * Verwerk het geregistreerd partnerschap.
     * @param wijziging lo3 wijzigingen (output)
     * @param acties acties
     * @param onderzoekMapper onderzoek mapper
     * @param relatie relatie van het huwelijk
     * @param partner partner van het huwelijk
     */
    public void verwerk(
            final Lo3Wijzigingen<Lo3HuwelijkOfGpInhoud> wijziging,
            final List<Long> acties,
            final OnderzoekMapper onderzoekMapper,
            final MetaObject relatie,
            final MetaObject partner) {
        final MetaRecord actueleEntiteit = bepaalActueel(MetaModelUtil.getRecords(relatie, GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT), acties);
        final MetaRecord historieEntiteit = bepaalVerval(MetaModelUtil.getRecords(relatie, GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT), acties);

        final MetaRecord relatieIdentiteit = MetaModelUtil.getIdentiteitRecord(relatie, GeregistreerdPartnerschapRelatieMapper.IDENTITEIT_GROEP_ELEMENT);
        final MetaRecord partnerIdentiteit =
                MetaModelUtil.getIdentiteitRecord(partner, PersoonslijstMapper.GERELATEERDE_GEREGISTREERDEPARTNER_PERSOON_IDENTITEIT_GROEP_ELEMENT);

        if (actueleEntiteit != null && historieEntiteit == null) {
            // Nieuwe relatie, alles van relatie en gerelateerde persoon opnemen als nieuw
            LOGGER.debug("Nieuw partnerschap: id={}; actieInhoud={}", relatie.getObjectsleutel(), actueleEntiteit.getVoorkomensleutel());

            relatieMutatieVerwerker.verwerk(
                    wijziging,
                    relatieIdentiteit,
                    actueleEntiteit,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
            verbintenisMutatieVerwerker.verwerk(
                    wijziging,
                    relatieIdentiteit,
                    actueleEntiteit,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);

            geboorteMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapGeboorteMapper.GROEP_ELEMENT)),
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
            identificatienummersMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapIdentificatienummersMapper.GROEP_ELEMENT)),
                    null,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
            samengesteldeNaamMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapSamengesteldeNaamMapper.GROEP_ELEMENT)),
                    null,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
            geslachtsaanduidingMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapGeslachtsaanduidingMapper.GROEP_ELEMENT)),
                    null,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
        } else if (actueleEntiteit == null && historieEntiteit != null) {
            // Relatie vervallen, alles van relatie en gerelateerde persoon opnemen als oud
            LOGGER.debug("Vervallen partnerschap: id={}; actieVerval={}", relatie.getObjectsleutel(), historieEntiteit.getVoorkomensleutel());

            relatieMutatieVerwerker.verwerk(
                    wijziging,
                    relatieIdentiteit,
                    null,
                    historieEntiteit,
                    historieEntiteit,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
            verbintenisMutatieVerwerker.verwerk(
                    wijziging,
                    relatieIdentiteit,
                    null,
                    null,
                    historieEntiteit,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);

            geboorteMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    null,
                    null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapGeboorteMapper.GROEP_ELEMENT)),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
            identificatienummersMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    null,
                    null,
                    null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapIdentificatienummersMapper.GROEP_ELEMENT)),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
            samengesteldeNaamMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    null,
                    null,
                    null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapSamengesteldeNaamMapper.GROEP_ELEMENT)),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);

            geslachtsaanduidingMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    null,
                    null,
                    null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapGeslachtsaanduidingMapper.GROEP_ELEMENT)),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper);
        } else {
            LOGGER.debug("Verwerk partnerschap: id={}", relatie.getObjectsleutel());

            final List<Long> relatieObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(relatie);
            relatieMutatieVerwerker.verwerk(
                    wijziging,
                    relatieIdentiteit,
                    MetaModelUtil.getRecords(relatie, GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT),
                    acties,
                    relatieObjectSleutels,
                    onderzoekMapper);
            verbintenisMutatieVerwerker.verwerk(
                    wijziging,
                    relatieIdentiteit,
                    MetaModelUtil.getRecords(relatie, GeregistreerdPartnerschapVerbintenisMapper.GROEP_ELEMENT),
                    acties,
                    relatieObjectSleutels,
                    onderzoekMapper);

            final List<Long> partnerObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(partner);
            geboorteMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapGeboorteMapper.GROEP_ELEMENT),
                    acties,
                    partnerObjectSleutels,
                    onderzoekMapper);
            identificatienummersMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapIdentificatienummersMapper.GROEP_ELEMENT),
                    acties,
                    partnerObjectSleutels,
                    onderzoekMapper);
            samengesteldeNaamMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapSamengesteldeNaamMapper.GROEP_ELEMENT),
                    acties,
                    partnerObjectSleutels,
                    onderzoekMapper);
            geslachtsaanduidingMutatieVerwerker.verwerk(
                    wijziging,
                    partnerIdentiteit,
                    MetaModelUtil.getRecords(partner, GeregistreerdPartnerschapGeslachtsaanduidingMapper.GROEP_ELEMENT),
                    acties,
                    partnerObjectSleutels,
                    onderzoekMapper);
        }

        geslachtAdellijkeTitelPredikaatNabewerking.voerNabewerkingUit(wijziging);
    }
}
