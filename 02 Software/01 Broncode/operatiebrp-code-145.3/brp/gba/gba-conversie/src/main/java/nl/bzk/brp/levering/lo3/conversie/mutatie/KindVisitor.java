/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.KindGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.KindIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.KindOuderschapMapper;
import nl.bzk.brp.levering.lo3.mapper.KindSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Kind visitor.
 */
@Component
public final class KindVisitor extends AbstractRelatieVisitor {

    @Autowired
    private KindOuderschapMutatieVerwerker kindOuderschapMutatieVerwerker;
    @Autowired
    private KindGeboorteMutatieVerwerker geboorteMutatieVerwerker;
    @Autowired
    private KindIdentificatienummersMutatieVerwerker identificatienummersMutatieVerwerker;
    @Autowired
    private KindSamengesteldeNaamMutatieVerwerker samengesteldeNaamMutatieVerwerker;

    /**
     * Verwerk wijzigingen in het kind.
     * @param wijziging lo3 wijzigingen (output)
     * @param acties acties
     * @param onderzoekMapper onderzoek mapper
     * @param mijnOuderBetrokkenheid (mijn) ouder betrokkenheid
     * @param relatie de familierechtelijke betrekking relatie
     * @param gerelateerdeKindBetrokkenheid gerelateerde (kind) betrokkenheid
     */
    public void verwerk(
            final Lo3Wijzigingen<Lo3KindInhoud> wijziging,
            final List<Long> acties,
            final OnderzoekMapper onderzoekMapper,
            final MetaObject mijnOuderBetrokkenheid,
            final MetaObject relatie,
            final MetaObject gerelateerdeKindBetrokkenheid) {
        final MetaRecord actueleEntiteit =
                bepaalActueel(MetaModelUtil.getRecords(mijnOuderBetrokkenheid, PersoonslijstMapper.OUDER_GROEP_ELEMENT), acties);
        final MetaRecord historieEntiteit =
                bepaalVerval(MetaModelUtil.getRecords(mijnOuderBetrokkenheid, PersoonslijstMapper.OUDER_GROEP_ELEMENT), acties);

        final MetaObject gerelateerdeKindPersoon =
                MetaModelUtil.getObject(gerelateerdeKindBetrokkenheid, PersoonslijstMapper.GERELATEERDE_KIND_PERSOON_ELEMENT);

        final MetaRecord mijnOuderBetrokkenheidIdentiteit =
                MetaModelUtil.getIdentiteitRecord(mijnOuderBetrokkenheid, PersoonslijstMapper.OUDER_IDENTITEIT_GROEP_ELEMENT);
        final MetaRecord gerelateerdeKindPersoonIdentiteit =
                MetaModelUtil.getIdentiteitRecord(gerelateerdeKindPersoon, PersoonslijstMapper.GERELATEERDE_KIND_PERSOON_IDENTITEIT_GROEP_ELEMENT);

        if (historieEntiteit != null) {
            verwerkHistorieEntiteit(
                    wijziging,
                    mijnOuderBetrokkenheidIdentiteit,
                    mijnOuderBetrokkenheid,
                    acties,
                    onderzoekMapper,
                    gerelateerdeKindPersoonIdentiteit,
                    gerelateerdeKindPersoon);
        } else if (actueleEntiteit != null) {
            verwerkActueleEntiteit(
                    wijziging,
                    mijnOuderBetrokkenheidIdentiteit,
                    mijnOuderBetrokkenheid,
                    acties,
                    onderzoekMapper,
                    gerelateerdeKindPersoonIdentiteit,
                    gerelateerdeKindPersoon);

        } else {
            verwerkOverig(
                    wijziging,
                    mijnOuderBetrokkenheidIdentiteit,
                    mijnOuderBetrokkenheid,
                    acties,
                    onderzoekMapper,
                    gerelateerdeKindPersoonIdentiteit,
                    gerelateerdeKindPersoon);

        }
    }

    private void verwerkHistorieEntiteit(
            final Lo3Wijzigingen<Lo3KindInhoud> wijziging,
            final MetaRecord mijnOuderBetrokkenheidIdentiteit,
            final MetaObject mijnOuderBetrokkenheid,
            final List<Long> acties,
            final OnderzoekMapper onderzoekMapper,
            final MetaRecord gerelateerdeKindPersoonIdentiteit,
            final MetaObject gerelateerdeKindPersoon) {
        // 'Mijn' ouder betrokkenheid is vervallen, alle actuele groepen van relatie en gerelateerde persoon als
        // historisch opnemen
        kindOuderschapMutatieVerwerker.verwerk(
                wijziging,
                mijnOuderBetrokkenheidIdentiteit,
                null,
                null,
                null,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(mijnOuderBetrokkenheid, KindOuderschapMapper.GROEP_ELEMENT)),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper);

        verwerkVervallenOfBeeindigdOuderschap(wijziging, gerelateerdeKindPersoonIdentiteit, gerelateerdeKindPersoon, acties, onderzoekMapper);
    }

    private void verwerkActueleEntiteit(
            final Lo3Wijzigingen<Lo3KindInhoud> wijziging,
            final MetaRecord mijnOuderBetrokkenheidIdentiteit,
            final MetaObject mijnOuderBetrokkenheid,
            final List<Long> acties,
            final OnderzoekMapper onderzoekMapper,
            final MetaRecord gerelateerdeKindPersoonIdentiteit,
            final MetaObject gerelateerdeKindPersoon) {
        // 'Mijn' ouder betrokkenheid is 'nieuw', alle actuele groepen van relatie en gerelateerde persoon als
        // actueel opnemen
        kindOuderschapMutatieVerwerker.verwerk(
                wijziging,
                mijnOuderBetrokkenheidIdentiteit,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(mijnOuderBetrokkenheid, KindOuderschapMapper.GROEP_ELEMENT)),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper);

        verwerkNieuwOuderschap(wijziging, gerelateerdeKindPersoonIdentiteit, gerelateerdeKindPersoon, acties, onderzoekMapper);
    }

    private void verwerkOverig(
            final Lo3Wijzigingen<Lo3KindInhoud> wijziging,
            final MetaRecord mijnOuderBetrokkenheidIdentiteit,
            final MetaObject mijnOuderBetrokkenheid,
            final List<Long> acties,
            final OnderzoekMapper onderzoekMapper,
            final MetaRecord gerelateerdeKindPersoonIdentiteit,
            final MetaObject gerelateerdeKindPersoon) {
        // De betrokkenheid is niet geraakt

        // Ouderschap verwerken
        kindOuderschapMutatieVerwerker.verwerk(
                wijziging,
                mijnOuderBetrokkenheidIdentiteit,
                MetaModelUtil.getRecords(mijnOuderBetrokkenheid, KindOuderschapMapper.GROEP_ELEMENT),
                acties,
                Collections.<Long>emptyList(),
                onderzoekMapper);

        // Persoonsgegevens verwerken is afhankelijk van wat er is gebeurd met het ouderschap
        final MetaRecord actueleOuderschapEntiteit =
                bepaalActueel(MetaModelUtil.getRecords(mijnOuderBetrokkenheid, KindOuderschapMapper.GROEP_ELEMENT), acties);
        final MetaRecord beeindigingOuderschapEntiteit =
                bepaalBeeindiging(MetaModelUtil.getRecords(mijnOuderBetrokkenheid, KindOuderschapMapper.GROEP_ELEMENT), acties);
        final MetaRecord historieOuderschapEntiteit =
                bepaalVerval(MetaModelUtil.getRecords(mijnOuderBetrokkenheid, KindOuderschapMapper.GROEP_ELEMENT), acties);

        if (beeindigingOuderschapEntiteit != null || historieOuderschapEntiteit != null) {
            verwerkVervallenOfBeeindigdOuderschap(wijziging, gerelateerdeKindPersoonIdentiteit, gerelateerdeKindPersoon, acties, onderzoekMapper);
        } else if (actueleOuderschapEntiteit != null) {
            verwerkNieuwOuderschap(wijziging, gerelateerdeKindPersoonIdentiteit, gerelateerdeKindPersoon, acties, onderzoekMapper);
        } else {
            verwerkNietGeraaktOuderschap(gerelateerdeKindPersoon, wijziging, gerelateerdeKindPersoonIdentiteit, acties, onderzoekMapper);
        }
    }

    private void verwerkVervallenOfBeeindigdOuderschap(
            final Lo3Wijzigingen<Lo3KindInhoud> wijziging,
            final MetaRecord gerelateerdeKindPersoonIdentiteit,
            final MetaObject gerelateerdeKindPersoon,
            final List<Long> acties,
            final OnderzoekMapper onderzoekMapper) {
        // 'Mijn ouderschap' is beeindigd of vervallen, alle actuele groepen van relatie en gerelateerde
        // persoon als historisch opnemen
        geboorteMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                null,
                null,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindGeboorteMapper.GROEP_ELEMENT)),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper);
        identificatienummersMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                null,
                null,
                null,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindIdentificatienummersMapper.GROEP_ELEMENT)),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper);
        samengesteldeNaamMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                null,
                null,
                null,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindSamengesteldeNaamMapper.GROEP_ELEMENT)),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper);
    }

    private void verwerkNieuwOuderschap(
            final Lo3Wijzigingen<Lo3KindInhoud> wijziging,
            final MetaRecord gerelateerdeKindPersoonIdentiteit,
            final MetaObject gerelateerdeKindPersoon,
            final List<Long> acties,
            final OnderzoekMapper onderzoekMapper) {
        // 'Mijn' ouderschap is nieuw, alle actuele groepen van relatie en gerelateerde persoon als
        // actueel opnemen
        geboorteMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindGeboorteMapper.GROEP_ELEMENT)),
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper);
        identificatienummersMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindIdentificatienummersMapper.GROEP_ELEMENT)),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper);
        samengesteldeNaamMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindSamengesteldeNaamMapper.GROEP_ELEMENT)),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper);
    }

    private void verwerkNietGeraaktOuderschap(
            final MetaObject gerelateerdeKindPersoon,
            final Lo3Wijzigingen<Lo3KindInhoud> wijziging,
            final MetaRecord gerelateerdeKindPersoonIdentiteit,
            final List<Long> acties,
            final OnderzoekMapper onderzoekMapper) {
        // Ouderschap is niet geraakt
        final List<Long> kindObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(gerelateerdeKindPersoon);

        geboorteMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindGeboorteMapper.GROEP_ELEMENT),
                acties,
                kindObjectSleutels,
                onderzoekMapper);
        identificatienummersMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindIdentificatienummersMapper.GROEP_ELEMENT),
                acties,
                kindObjectSleutels,
                onderzoekMapper);
        samengesteldeNaamMutatieVerwerker.verwerk(
                wijziging,
                gerelateerdeKindPersoonIdentiteit,
                MetaModelUtil.getRecords(gerelateerdeKindPersoon, KindSamengesteldeNaamMapper.GROEP_ELEMENT),
                acties,
                kindObjectSleutels,
                onderzoekMapper);
    }

}
