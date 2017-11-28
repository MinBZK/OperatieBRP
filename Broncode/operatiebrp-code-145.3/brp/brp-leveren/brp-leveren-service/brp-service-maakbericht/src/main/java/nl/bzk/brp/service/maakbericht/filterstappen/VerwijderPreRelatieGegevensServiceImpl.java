/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Deze service zorgt ervoor dat uitsluitend gegevens van gerelateerde personen (ouders, kinderen partners) worden geleverd die niet voor aanvang van de
 * relatie zijn beëindigd of vervallen. Een bericht aan een Partij met Rol = "Afnemer" mag geen voorkomens van groepen van de gerelateerde Persoon
 * bevatten, die een geldigheidsperiode hebben die geheel voor de aanvangsdatum van de Relatie met die Persoon ligt. Dit is enkel van toepassing op
 * berichten geleverd aan afnemers.
 * <p>
 * Verwijdert enkel autorisatie op records en attributen. Zodoende kan een autorisatieboom overblijven met enkel het object nog zichtbaar.
 * <p>
 * Onderstaande PL bevat een record binnen de groep <tt>GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam</tt> dat een geldigheidsperiode heeft voor
 * aanvangsdatum van het huwelijk.
 * <pre><code>&nbsp;&nbsp;&nbsp;[o] Huwelijk id=90435
 * &nbsp;&nbsp;&nbsp;&nbsp;[g] Huwelijk.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90157
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.ActieInhoud, '90572'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.DatumAanvang, '20150101'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.GemeenteAanvangCode, '174'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.LandGebiedAanvangCode, '6030'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.LandGebiedEindeCode, '6030'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.TijdstipRegistratie, 'Thu Jan 01 02:00:00 CET 2015'
 * &nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeHuwelijkspartner id=90777
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeHuwelijkspartner.Persoon id=90330
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90360
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieInhoud, '91131'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.ActieVerval, '91140'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.DatumAanvangGeldigheid, '19650707'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.Geslachtsnaamstam, 'Burch'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.TijdstipRegistratie, '7 jul 1965'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam.TijdstipVerval, '4 Aug 2014'
 * </code></pre>
 * <p>
 * Record <tt>[r] id=90360</tt> zal worden weggefilterd wat resulteert in:
 * <pre><code>&nbsp;&nbsp;&nbsp;[o] Huwelijk id=90435
 * &nbsp;&nbsp;&nbsp;&nbsp;[g] Huwelijk.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90157
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.ActieInhoud, '90572'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.DatumAanvang, '20150101'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.GemeenteAanvangCode, '174'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.LandGebiedAanvangCode, '6030'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.LandGebiedEindeCode, '6030'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Huwelijk.TijdstipRegistratie, 'Thu Jan 01 02:00:00 CET 2015'
 * &nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeHuwelijkspartner id=90777
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[o] GerelateerdeHuwelijkspartner.Persoon id=90330
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] GerelateerdeHuwelijkspartner.Persoon.SamengesteldeNaam
 * </code></pre>
 */
@Component
@Bedrijfsregel(Regel.R1328)
final class VerwijderPreRelatieGegevensServiceImpl implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final Rol rol = berichtgegevens.getAutorisatiebundel().getRol();
        if (rol == Rol.AFNEMER) {
            final Set<MetaRecord> preRelatieRecords = berichtgegevens.getStatischePersoongegevens().getPreRelatieRecords();
            for (MetaRecord preRelatieRecord : preRelatieRecords) {
                berichtgegevens.verwijderAutorisatie(preRelatieRecord);
            }
        }
    }
}
