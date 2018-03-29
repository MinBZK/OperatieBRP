/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;


/**
 * Specifieke filtering van objecten indien historiefilter is toegepast (specifiek voor geef details persoon (en indirect ook voor selecties)).
 * <p>
 * Deze stap moet zorgen voor het opruimen van 'loze' objecten in het resultaat van Geef Details Persoon na het toepassen van scoping en/of het
 * historiefilter. Hiermee willen we bijvoorbeeld het volgende bereiken: Als een afnemer/bijhouder in de scoping niets meegeeft van een bepaalde relatie,
 * dan willen we dat die hele relatie wegvalt uit het bericht. Als een historiefilter alleen maar kijkt naar zaken op/voor een formeel peilmoment en een
 * bepaalde relatie is pas later ontstaan, dan mag die hele relatie uit het bericht blijven. Idem als een historiefilter alleen maar kijkt naar een bepaald
 * formeel peilmoment en de relatiestructuur (of een ander object) was toen al vervallen, dan moet deze ook niet in het bericht komen (dit kan o.a. als het
 * een relatie naar een pseudo persoon was die vervangen is door de relatie naar een ingeschrevene of omgekeerd).
 * <p>
 * BELANGRIJK: Als de afnemer/bijhouder geen scoping en historiefilter heeft meegegeven,dan moeten we dus (a) niet filteren maar ook (b) niet deze
 * opschoonacties op objecten uitvoeren.
 */
@Component
@Bedrijfsregel(Regel.R2218)
@Bedrijfsregel(Regel.R2279)
final class GeefDetailsPersoonOpschonenObjecten implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final SoortDienst soortDienst = berichtgegevens.getAutorisatiebundel().getSoortDienst();
        if ((soortDienst == SoortDienst.GEEF_DETAILS_PERSOON || soortDienst == SoortDienst.SELECTIE)
                && berichtgegevens.getMaakBerichtPersoon().getHistorieFilterInformatie() != null) {
            final MetaObject metaObject = berichtgegevens.getPersoonslijst().getMetaObject();
            schoonOp(metaObject, berichtgegevens);
        }
    }

    private void schoonOp(final MetaObject metaObject, final Berichtgegevens berichtgegevens) {
        if (!berichtgegevens.isGeautoriseerd(metaObject)) {
            return;
        }
        final Collection<MetaModel> onderliggendeAutorisaties = berichtgegevens.getOnderliggendeAutorisaties(metaObject);

        final boolean bevatGeautoriseerdeGroep = bevatGeautoriseerdeGroep(onderliggendeAutorisaties);
        if (!bevatGeautoriseerdeGroep) {
            berichtgegevens.verwijderAutorisatie(metaObject);
            return;
        }

        for (MetaObject object : metaObject.getObjecten()) {
            schoonOp(object, berichtgegevens);
        }
    }


    private boolean bevatGeautoriseerdeGroep(final Collection<MetaModel> onderliggendeAutorisaties) {
        for (MetaModel metaModel : onderliggendeAutorisaties) {
            if (metaModel instanceof MetaGroep) {
                return true;
            }
        }
        return false;
    }
}

