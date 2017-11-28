/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Service;

/**
 * BepaalPreRelatieGegevensServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R1328)
final class BepaalPreRelatieGegevensServiceImpl implements BepaalPreRelatieGegevensService {

    private static final EnumSet<Element> DATUM_AANVANG_ATTRIBUUT_SET = EnumSet.of(
            Element.HUWELIJK_DATUMAANVANG,
            Element.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG,
            Element.FAMILIERECHTELIJKEBETREKKING_DATUMAANVANG,
            Element.PERSOON_OUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID
    );

    @Override
    public Set<MetaRecord> bepaal(final Persoonslijst persoonslijst) {
        final Set<RelatieGegevens> relatieGegevensSet = verzamelDatumAanvangAttributen(persoonslijst)
                .stream().map(RelatieGegevens::new)
                .collect(Collectors.toSet());
        return new PreRelatieBepaler(relatieGegevensSet).prerelatieGegevens;
    }

    private Set<MetaAttribuut> verzamelDatumAanvangAttributen(final Persoonslijst persoonslijst) {
        final Set<MetaAttribuut> alleAttributen = Sets.newHashSet();
        final ModelIndex modelIndex = persoonslijst.getModelIndex();
        for (Element element : DATUM_AANVANG_ATTRIBUUT_SET) {
            alleAttributen.addAll(modelIndex.geefAttributen(element));
        }
        // Alleen attributen uit actuele records worden meegenomen met pre-relatie filtering.
        alleAttributen.removeIf(attr -> !persoonslijst.isActueel(attr.getParentRecord()));
        return alleAttributen;
    }

    /**
     * Verwijdert recursief alle record-autorisatie op het eigen en onderliggende objecten vanaf het punt waar de pre-relatie data geconstateerd is.
     */
    private static final class PreRelatieBepaler extends ParentFirstModelVisitor {

        private final Set<MetaRecord> prerelatieGegevens = new HashSet<>();
        private Integer datumAanvang;
        private ZonedDateTime tijdstipRegistratie;

        PreRelatieBepaler(final Set<RelatieGegevens> relatieGegevensSet) {
            for (final RelatieGegevens relatieGegevens : relatieGegevensSet) {
                datumAanvang = relatieGegevens.getDatumAanvang().<Integer>getWaarde();
                tijdstipRegistratie = relatieGegevens.getLaatsteTsReg();

                final MetaObject parentObject = relatieGegevens.getDatumAanvang().getParentRecord().getParentGroep().getParentObject();
                visit(parentObject);
            }
        }

        @Override
        protected void doVisit(final MetaRecord metaRecord) {
            if (!metaRecord.getParentGroep().getParentObject().getObjectElement().isAliasVan(ElementConstants.PERSOON)) {
                return;
            }
            if (!isGeldig(metaRecord)) {
                prerelatieGegevens.add(metaRecord);
            }
        }

        private boolean isGeldig(final MetaRecord metaRecord) {
            final boolean recordTonenObvDatumEindeGeldigheid =
                    metaRecord.getDatumEindeGeldigheid() == null
                            || (!datumAanvang.equals(metaRecord.getDatumEindeGeldigheid())
                            && DatumUtil.valtDatumBinnenPeriode(metaRecord.getDatumEindeGeldigheid(), datumAanvang, null));

            final ZonedDateTime datumTijdVerval = metaRecord.getDatumTijdVervalAttribuut();
            final boolean recordTonenObvDatumTijdVerval = datumTijdVerval == null
                    || Boolean.TRUE.equals(metaRecord.isIndicatieTbvLeveringMutaties()) || datumTijdVerval.isAfter(tijdstipRegistratie);
            return recordTonenObvDatumEindeGeldigheid && recordTonenObvDatumTijdVerval;
        }
    }

    private static class RelatieGegevens {
        private MetaAttribuut datumAanvang;
        private MetaObject betrokkenheidObject;
        private ZonedDateTime laatsteTsReg;

        RelatieGegevens(final MetaAttribuut datumAanvangAttribuut) {
            this.datumAanvang = datumAanvangAttribuut;
            //zoek betrokkenheid op de persoon
            betrokkenheidObject = datumAanvangAttribuut.getParentRecord().getParentGroep().getParentObject();
            while (!betrokkenheidObject.getParentObject().getObjectElement().isVanType(ElementConstants.PERSOON)) {
                betrokkenheidObject = betrokkenheidObject.getParentObject();
            }
            final ParentFirstModelVisitor parentFirstModelVisitor = new ParentFirstModelVisitor() {
                @Override
                protected void doVisit(final MetaObject object) {
                    if (!object.getObjectElement().isVanType(ElementConstants.BETROKKENHEID)) {
                        return;
                    }
                    final ZonedDateTime zonedDateTime = bepaalTijdstipRegistratieVanBetrokkenheid(object);
                    if (zonedDateTime != null && (laatsteTsReg == null || zonedDateTime.isAfter(laatsteTsReg))) {
                        laatsteTsReg = zonedDateTime;
                    }

                }
            };
            parentFirstModelVisitor.visit(betrokkenheidObject);
        }

        private ZonedDateTime bepaalTijdstipRegistratieVanBetrokkenheid(MetaObject betrokkenheid) {
            for (MetaGroep metaGroep : betrokkenheid.getGroepen()) {
                if (metaGroep.getGroepElement().isIdentiteitGroep()) {
                    return metaGroep.getRecords().stream().findFirst().orElseThrow(IllegalStateException::new)
                            .getActieInhoud().getTijdstipRegistratie();
                }
            }
            return null;
        }

        private MetaAttribuut getDatumAanvang() {
            return datumAanvang;
        }

        private ZonedDateTime getLaatsteTsReg() {
            return laatsteTsReg;
        }
    }
}
