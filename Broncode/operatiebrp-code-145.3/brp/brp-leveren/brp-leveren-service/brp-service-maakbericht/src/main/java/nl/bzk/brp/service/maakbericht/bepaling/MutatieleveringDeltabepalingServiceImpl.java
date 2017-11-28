/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;

/**
 * Bepaalt de {@link MetaRecord}s welke in de administratieve handeling geraakt zijn. Een {@code MetaRecord} behoort tot de delta als ActieInhoud,
 * ActieAanpassingGeldigheid OF ActieVerval hoort bij de administratievehandeling. Indien ActieVervalTbvLeveringMutaties gevuld is heeft dat voorrang op
 * ActieVerval. Migratie heeft in dat geval historie aangepast tbv van de BRP om correct te kunnen leveren. Het resultaat van de delta bepaling is een
 * {@link java.util.Set} met {@link MetaRecord}s dat bewaard wordt in {@link nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens}
 * <p>
 * <p>
 * Bepaalt de deltarecords in voor mutatieberichten. {@link MutatieleveringDeltabepalingServiceImpl}
 */
@Component
@Bedrijfsregel(Regel.R1973)
class MutatieleveringDeltabepalingServiceImpl implements MutatieleveringDeltabepalingService {

    @Override
    public Set<MetaRecord> execute(final Persoonslijst persoonslijst) {
        final Set<MetaRecord> deltaRecords = new HashSet<>();
        final Deltabepaler deltabepaler = new Deltabepaler(persoonslijst.getAdministratieveHandeling());
        persoonslijst.getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (deltabepaler.inDelta(record)) {
                    deltaRecords.add(record);
                }
            }
        });
        return deltaRecords;
    }

    /**
     * Hulpklasse om te bepalen welke records geraakt zijn door een bepaalde administratieve handeling.
     */
    private static final class Deltabepaler {

        private final Long administratieveHandelingId;

        /**
         * Constructor.
         * @param administratieveHandeling de administratieveHandeling
         */
        Deltabepaler(final AdministratieveHandeling administratieveHandeling) {
            this.administratieveHandelingId = administratieveHandeling.getId();
        }

        private boolean inDelta(final MetaRecord record) {
            return actieInhoudIsOnderdeelVanHandelingEnActieGeldigheidIsLeeg(record)
                    || actieAanpassingGeldigheidIsOnderdeelVanHandeling(record)
                    || actieVervalIsOnderdeelVanHandeling(record);
        }

        /**
         * Controleert of de actieverval onderdeel is van de handeling.
         * @param record Het record.
         * @return True als het record gekoppeld is via de actie verval aan de handeling.
         */
        private boolean actieVervalIsOnderdeelVanHandeling(final MetaRecord record) {
            final Actie actieModel;
            final Actie actieVervalTbvLeveringMutaties = record.getActieVervalTbvLeveringMutaties();
            if (actieVervalTbvLeveringMutaties != null) {
                actieModel = actieVervalTbvLeveringMutaties;
            } else {
                actieModel = record.getActieVerval();
            }
            return actieIsGekoppeldAanAdministratieHandeling(actieModel);
        }

        /**
         * Controleert of de actie geldigheid onderdeel is van de handeling.
         * @param record Het record.
         * @return True als het record gekoppeld is via de actie geldigheid aan de handeling.
         */
        private boolean actieAanpassingGeldigheidIsOnderdeelVanHandeling(final MetaRecord record) {
            final Actie actieAanpassingGeldigheid = record.getActieAanpassingGeldigheid();
            return actieAanpassingGeldigheid != null && actieIsGekoppeldAanAdministratieHandeling(actieAanpassingGeldigheid);
        }

        /**
         * Controleert of de actie inhoud onderdeel is van de handeling.
         * @param record Het record.
         * @return True als het record gekoppeld is via de actie inhoud aan de handeling.
         */
        private boolean actieInhoudIsOnderdeelVanHandelingEnActieGeldigheidIsLeeg(final MetaRecord record) {
            final boolean actieInhoudIsOnderdeelVanHandeling = actieIsGekoppeldAanAdministratieHandeling(record.getActieInhoud());
            return actieInhoudIsOnderdeelVanHandeling && record.getActieAanpassingGeldigheid() == null;
        }

        /**
         * Controleert of de actie gekoppeld is aan de administratieve handeling waarmee dit predikaat is geinstantieerd.
         * @param actie De actie.
         * @return True als de actie gekoppeld is aan de administratieve handeling, anders false.
         */
        private boolean actieIsGekoppeldAanAdministratieHandeling(final Actie actie) {
            return actie != null && actie.getAdministratieveHandeling().getId().equals(administratieveHandelingId);
        }

    }
}
