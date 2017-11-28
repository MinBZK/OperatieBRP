/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.function.Predicate;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;


/**
 * Bepaalt een set {@link MetaRecord}s welke voldoen aan ALLE record predikaten. De predikaten verschillen per dienst en soortbericht. Voor deze van
 * KandidaatRecordBepaling is de implementatie de predikaten irrelevant, het wordt verborgen achter een facade die gemaakt wordt door {@link
 * MetaRecordFilterFactory} De facade is simpelweg een {@link Predicate} welke een boolean teruggeeft.
 * <p>
 * De kandidaatrecords vormen in de vervolgstappen de kapstok voor het tonen van attributen, groepen en objecten. Indien een record geen deel uitmaakt van
 * de kandidaatrecords dan speelt het geen verdere rol, het kan niet geautoriseerd worden of deel uitmaken van de berichtstructuur.
 * <p>
 * Onderstaand een metamodel van een adresstructuur met een actueel, gewijzigd en vervallen adres:
 * <pre><code>&nbsp;[o] Persoon.Adres id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Identiteit
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90133
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[g] Persoon.Adres.Standaard
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90232
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90777'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieVerval, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20101231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '16'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Fri Dec 31 21:00:55 CET 2010'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipVerval, 'Thu Dec 31 21:00:57 CET 2015'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90233
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20151231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.GemeenteCode, '344'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '11'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Thu Dec 31 21:00:57 CET 2015'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[r] id=90234
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieAanpassingGeldigheid, '90778'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.ActieInhoud, '90777'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumAanvangGeldigheid, '20101231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.DatumEindeGeldigheid, '20151231'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.Huisnummer, '16'
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Adres.TijdstipRegistratie, 'Fri Dec 31 21:00:55 CET 2010'
 * </code></pre>
 *
 * Voorbeeld:
 * <br>Het toepassen van het {@link MetaRecordAutorisatiePredicate} met autorisatie enkel op actuele records geeft <tt>[r] id=90233</tt>
 */
@Component
@Bedrijfsregel(Regel.R2218)
@Bedrijfsregel(Regel.R2279)
final class KandidaatRecordBepaling implements MaakBerichtStap {

    @Inject
    private MetaRecordFilterFactory metaRecordFilterFactory;

    private KandidaatRecordBepaling() {
    }

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final Predicate<MetaRecord> predicates = metaRecordFilterFactory.maakRecordfilters(berichtgegevens);
        final Set<MetaRecord> alleHisRecords = Sets.newHashSet();
        berichtgegevens.getPersoonslijst().getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                if (predicates.test(record)) {
                    alleHisRecords.add(record);
                }
            }
        });
        berichtgegevens.setKandidaatRecords(alleHisRecords);
    }
}

