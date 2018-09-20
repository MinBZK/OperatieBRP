/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import java.util.SortedSet;
import nl.bzk.brp.model.operationeel.autaut.PersoonAfnemerindicatieModel;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAfgeleidAdministratiefGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonDeelnameEUVerkiezingenGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonInschrijvingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonMigratieGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNaamgebruikGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNummerverwijzingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOverlijdenGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonPersoonskaartGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonUitsluitingKiesrechtGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVerblijfsrechtGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVerificatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;

/**
 * Mix-in voor ${nl.bzk.brp.model.operationeel.kern.AbstractPersoonModel}.
 */
public interface AbstractPersoonModelMixIn {

    /** @return afgeleid administratief */
    @JsonIgnore
    PersoonAfgeleidAdministratiefGroepModel getAfgeleidAdministratief();

    /** @return geboorte */
    @JsonIgnore
    PersoonGeboorteGroepModel getGeboorte();

    /** @return geslachtsaanduiding */
    @JsonIgnore
    PersoonGeslachtsaanduidingGroepModel getGeslachtsaanduiding();

    /** @return inschrijving */
    @JsonIgnore
    PersoonInschrijvingGroepModel getInschrijving();

    /** @return nummerverwijzing */
    @JsonIgnore
    PersoonNummerverwijzingGroepModel getNummerverwijzing();

    /** @return bijhouding */
    @JsonIgnore
    PersoonBijhoudingGroepModel getBijhouding();

    /** @return overlijden */
    @JsonIgnore
    PersoonOverlijdenGroepModel getOverlijden();

    /** @return naamgebruik */
    @JsonIgnore
    PersoonNaamgebruikGroepModel getNaamgebruik();

    /** @return migratie */
    @JsonIgnore
    PersoonMigratieGroepModel getMigratie();

    /** @return verblijfsrecht */
    @JsonIgnore
    PersoonVerblijfsrechtGroepModel getVerblijfsrecht();

    /** @return uitsluitingkiesrecht */
    @JsonIgnore
    PersoonUitsluitingKiesrechtGroepModel getUitsluitingKiesrecht();

    /** @return deelname eu verkiezingen */
    @JsonIgnore
    PersoonDeelnameEUVerkiezingenGroepModel getDeelnameEUVerkiezingen();

    /** @return persoonskaart */
    @JsonIgnore
    PersoonPersoonskaartGroepModel getPersoonskaart();

    /** @return voornamen */
    @JsonIgnore
    SortedSet<PersoonVoornaamModel> getVoornamen();

    /** @return geslachtsnaamcomponenten */
    @JsonIgnore
    SortedSet<PersoonGeslachtsnaamcomponentModel> getGeslachtsnaamcomponenten();

    /** @return verificaties */
    @JsonIgnore
    Set<PersoonVerificatieModel> getVerificaties();

    /** @return nationaliteiten */
    @JsonIgnore
    Set<PersoonNationaliteitModel> getNationaliteiten();

    /** @return adressen */
    @JsonIgnore
    Set<PersoonAdresModel> getAdressen();

    /** @return indicaties */
    @JsonIgnore
    Set<PersoonIndicatieModel> getIndicaties();

    /** @return reisdocumenten */
    @JsonIgnore
    Set<PersoonReisdocumentModel> getReisdocumenten();

    /** @return betrokkenheden */
    @JsonIgnore
    Set<BetrokkenheidModel> getBetrokkenheden();

    /** @return onderzoeken */
    @JsonIgnore
    Set<PersoonOnderzoekModel> getOnderzoeken();

    /** @return verstrekkingsbeperkingen */
    @JsonIgnore
    Set<PersoonVerstrekkingsbeperkingModel> getVerstrekkingsbeperkingen();

    /** @return afnemerindicaties */
    @JsonIgnore
    Set<PersoonAfnemerindicatieModel> getAfnemerindicaties();

}
