/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import org.springframework.stereotype.Component;

/**
 * In het resultaatbericht van Zoek persoon, Zoek persoon op adresgegevens en Geef medebewoners van persoon mag de groep Persoon.Migratie alleen worden
 * opgenomen als Soort migratie.Code gelijk is aan "E" (Emigratie).
 * <p>
 * Onderstaande structuur blijft staan:
 * <pre><code>&nbsp;[o] Persoon id=90806
 * &nbsp;&nbsp;[g] Persoon.Migratie
 * &nbsp;&nbsp;&nbsp;[r] id=90001
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.AangeverCode, 'I'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.ActieInhoud, '91794'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.DatumAanvangGeldigheid, '20160201'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.LandGebiedCode, '5001'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.RedenWijzigingCode, 'P'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.SoortCode, 'E'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.TijdstipRegistratie, 'Mon Feb 01 14:27:03 CET 2016'</code></pre>
 * <p>
 * Onderstaande structuur:
 * <pre><code>&nbsp;[o] Persoon id=90806
 * &nbsp;&nbsp;[g] Persoon.Migratie
 * &nbsp;&nbsp;&nbsp;[r] id=90001
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.AangeverCode, 'I'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.ActieInhoud, '91794'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.DatumAanvangGeldigheid, '20160201'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.LandGebiedCode, '5001'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.RedenWijzigingCode, 'P'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.SoortCode, 'I'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Migratie.TijdstipRegistratie, 'Mon Feb 01 14:27:03 CET 2016'</code></pre>
 * <p>
 * wordt:
 * <pre><code>&nbsp;[o] Persoon id=90806
 * </code></pre>
 */
@Component
@Bedrijfsregel(Regel.R2298)
final class VerwijderMigratieGroepServiceImpl implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {

        final MaakBerichtParameters parameters = berichtgegevens.getParameters();
        if (!parameters.migratieGroepEnkelOpnemenBijEmigratie()) {
            return;
        }

        final Set<MetaAttribuut> attribuutSet = berichtgegevens.getPersoonslijst().getModelIndex()
                .geefAttributen(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE));
        final Persoonslijst persoonslijst = berichtgegevens.getPersoonslijst();
        for (final MetaAttribuut metaAttribuut : attribuutSet) {
            if (persoonslijst.isActueel(metaAttribuut.getParentRecord())) {
                if (SoortMigratie.EMIGRATIE != SoortMigratie.parseCode(metaAttribuut.getWaarde())) {
                    berichtgegevens.verwijderAutorisatie(metaAttribuut.getParentRecord().getParentGroep());
                }
                break;
            }
        }
    }
}
