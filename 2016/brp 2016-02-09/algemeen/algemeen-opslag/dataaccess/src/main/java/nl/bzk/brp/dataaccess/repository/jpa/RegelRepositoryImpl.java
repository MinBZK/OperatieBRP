/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.dataaccess.repository.RegelRepository;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DatabaseObjectAutAut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.springframework.stereotype.Repository;

/**
 * Repository voor het ophalen van regels voor bijhouding.
 * Repository doet momenteel niks met de database, maar bevat een interne data structuur met alle regels en hun
 * parameters. Op den duur zullen regels uit de database moeten worden gehaald.
 */
@Repository
public final class RegelRepositoryImpl implements RegelRepository {

    private static final Map<RegelCodeAttribuut, RegelParameters> REPOSITORY =
            new HashMap<RegelCodeAttribuut, RegelParameters>();

    private static final String BRAL_9003 = "BRAL9003";

    private static final String BRPUC_00120 = "BRPUC00120";

    static {

        // *********************
        // * Begin BRAL Regels *
        // *********************

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0001"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0001.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0001,
                        DatabaseObjectKern.PERSOON__ADMINISTRATIENUMMER,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0004"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0004.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0004,
                        DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0012"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0012.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0012,
                        DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0013"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0013.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0013,
                        DatabaseObjectKern.PERSOON__ADMINISTRATIENUMMER,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0014"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0014.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0014,
                DatabaseObjectKern.PERSOON,
                SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0015"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0015.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0015,
                DatabaseObjectKern.PERSOON,
                SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0016"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0016.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0016,
                DatabaseObjectKern.PERSOON,
                SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0102"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0102.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0102,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0181"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0181.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0181,
                        DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0202"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0202.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0202,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0205"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0205.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0205,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0207"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0207.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0207,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0208"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0208.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0208,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0209"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0209.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0209,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0210"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0210.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0210,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0211"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0211.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0211,
                DatabaseObjectKern.PERSOON,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0212"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0212.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0212,
                DatabaseObjectKern.PERSOON_GESLACHTSNAAMCOMPONENT,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0216"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0216.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0216,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0218"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0218.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0218,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0220"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0220.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0220,
                DatabaseObjectKern.PERSOON_VERSTREKKINGSBEPERKING__PARTIJ,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0221"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0221.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0221,
                DatabaseObjectKern.PERSOON_VERSTREKKINGSBEPERKING__GEMEENTE_VERORDENING,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0213"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0213.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0213,
                        null,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0173"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0173.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0173,
                DatabaseObjectKern.PERSOON_NATIONALITEIT__NATIONALITEIT));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0219"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0219.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRAL0219,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0443"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0443.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0443,
                DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0445"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0445.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0445,
                DatabaseObjectKern.ACTIE));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0451"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0451.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0451,
                DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0452"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0452.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0452,
                DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0454"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0454.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0454,
                DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBER00121"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBER00121.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBER00121,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY2012"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY2012.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY2012,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY2013"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY2013.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY2013,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY2014"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY2014.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY2014,
                DatabaseObjectKern.PERSOON));


        REPOSITORY.put(new RegelCodeAttribuut("BRBY2015"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY2015.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY2015,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY2016"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY2016.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY2016,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY2017"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY2017.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY2017,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY2018"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY2018.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY2018,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY2019"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY2019.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY2019,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0455"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0455.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0455,
                DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0501"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0501.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0501,
                        DatabaseObjectKern.PERSOON_VOORNAAM__NAAM,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0502"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL0502.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL0502,
                DatabaseObjectKern.PERSOON_VOORNAAM__NAAM,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0503"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0503.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0503,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0504"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0504.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0504,
                        DatabaseObjectKern.PERSOON_GESLACHTSNAAMCOMPONENT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0505"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0505.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0505,
                        DatabaseObjectKern.PERSOON_GESLACHTSNAAMCOMPONENT__VOORVOEGSEL,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1001"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1001.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1001,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1002"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1002.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1002,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1002A"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1002A.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1002A,
                        DatabaseObjectKern.ADMINISTRATIEVE_HANDELING__PARTIJ,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1002B"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1002B.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1002B,
                        DatabaseObjectAutAut.PERSOON_AFNEMERINDICATIE__AFNEMER,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1007"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1007.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1007,
                        DatabaseObjectKern.PERSOON_ADRES__REDEN_WIJZIGING,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1008"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1008.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1008,
                        DatabaseObjectKern.PERSOON_ADRES__AANGEVER_ADRESHOUDING,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1012"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1012.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1012,
                        DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1015"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1015.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1015,
                        DatabaseObjectKern.PERSOON_GESLACHTSNAAMCOMPONENT__ADELLIJKE_TITEL,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1017"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1017.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1017,
                        DatabaseObjectKern.PERSOON_NATIONALITEIT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1018"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1018.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1018,
                        DatabaseObjectKern.PERSOON_GESLACHTSNAAMCOMPONENT__PREDICAAT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1019"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1019.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1019,
                        DatabaseObjectKern.RELATIE__REDEN_EINDE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1020"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1020.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1020,
                        DatabaseObjectKern.PERSOON_ADRES__WOONPLAATSNAAM,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1021"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1021.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1021,
                        DatabaseObjectKern.PERSOON_NATIONALITEIT__REDEN_VERKRIJGING,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1022"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1022.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1022,
                        DatabaseObjectKern.PERSOON_NATIONALITEIT__REDEN_VERLIES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1023"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL1023.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL1023,
                DatabaseObjectKern.PERSOON_REISDOCUMENT__AANDUIDING_INHOUDING_VERMISSING,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1025"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL1025.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL1025,
                DatabaseObjectKern.PERSOON_REISDOCUMENT__SOORT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1026"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1026.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1026,
                        DatabaseObjectKern.ACTIE_BRON__DOCUMENT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1030"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1030.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1030,
                        DatabaseObjectKern.DOCUMENT__PARTIJ,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1031"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1031.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1031,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL1118"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL1118.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL1118,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2010"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2010.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL2010,
                DatabaseObjectKern.BETROKKENHEID__PERSOON,
                SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2011"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2011.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2011,
                        DatabaseObjectKern.PERSOON_NATIONALITEIT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2013"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2013.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL2013,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2017"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2017.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL2017,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2018"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2018.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL2018,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2024"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2024.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2024,
                        DatabaseObjectKern.PERSOON_ADRES__POSTCODE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2025"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2025.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2025,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2027"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2027.getOmschrijving()),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL2027,
                        DatabaseObjectKern.PERSOON_ADRES));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2031"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2031.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2031,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2032"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2032.getOmschrijving()),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL2032,
                        DatabaseObjectKern.PERSOON_ADRES));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2033"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2033.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2033,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2035"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2035.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2035,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2038"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2038.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2038,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2039"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2039.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2039,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2083"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2083.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2083,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2084"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2084.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2084,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2085"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2085.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2085,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2086"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2086.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2086,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2094"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2094.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2094,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2095"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2095.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2095,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2102"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2102.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2102,
                        DatabaseObjectKern.RELATIE__DATUM_AANVANG,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2103"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2103.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL2103,
                DatabaseObjectKern.RELATIE__DATUM_EINDE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2104"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2104.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL2104,
                DatabaseObjectKern.ADMINISTRATIEVE_HANDELING__PARTIJ,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2110"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2110.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRAL2110,
                DatabaseObjectKern.RELATIE__GEMEENTE_AANVANG));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2111"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2111.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL2111,
                        DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2112"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2112.getOmschrijving()),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL2112,
                        DatabaseObjectKern.RELATIE));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2113"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2113.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL2113,
                DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL2203"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL2203.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL2203,
                DatabaseObjectKern.ACTIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0512"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0512.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0512,
                        DatabaseObjectKern.PERSOON__INDICATIE_NAAMGEBRUIK_AFGELEID,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL0516"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL0516.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL0516,
                        DatabaseObjectKern.PERSOON__GESLACHTSNAAMSTAM_NAAMGEBRUIK,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut(BRAL_9003), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL9003.getOmschrijving()),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL9003,
                        DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL9025"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL9025.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL9025,
                        DatabaseObjectKern.PERSOON_ADRES,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRAL9027"), new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL9027.getOmschrijving()),
                        SoortMelding.FOUT, Regel.BRAL9027,
                        DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        // *********************
        // * Begin BRBY Regels *
        // *********************

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0011"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0011.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0011,
                DatabaseObjectKern.ACTIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0012"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0012.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0012,
                DatabaseObjectKern.ACTIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0014"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0014.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0014,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0024"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0024.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0024,
                DatabaseObjectKern.ACTIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0032"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0032.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0032,
                DatabaseObjectKern.ACTIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0033"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0033.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0033,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0036"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0036.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0036,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0103"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0103.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0103,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY01032"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY01032.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY01032,
                DatabaseObjectKern.PERSOON__GEMEENTE_GEBOORTE));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY01037"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY01037.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY01037,
                DatabaseObjectKern.PERSOON__LAND_GEBIED_GEBOORTE));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0106"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0106.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0106,
                DatabaseObjectKern.PERSOON_GESLACHTSNAAMCOMPONENT));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0107"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0107.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0107,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0126"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0126.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0126,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0129"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0129.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0129,
                DatabaseObjectKern.PERSOON__DATUM_GEBOORTE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0131"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0131.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0131,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0132"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0132.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0132,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0134"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0134.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0134,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0133"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0133.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0133,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0135"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0135.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0135,
                DatabaseObjectKern.PERSOON__DATUM_VOORZIEN_EINDE_UITSLUITING_E_U_VERKIEZINGEN,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0136"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0136.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0136,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0137"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0137.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0137,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0141"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0141.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0141,
                DatabaseObjectKern.PERSOON_NATIONALITEIT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0143"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0143.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0143,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0148"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0148.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0148,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0163"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0163.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0163,
                DatabaseObjectKern.PERSOON_NATIONALITEIT__NATIONALITEIT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0164"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0164.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0164,
                DatabaseObjectKern.PERSOON_NATIONALITEIT__NATIONALITEIT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0166"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0166.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0166,
                DatabaseObjectKern.PERSOON__DATUM_GEBOORTE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0167"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0167.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0167,
                DatabaseObjectKern.PERSOON_VERSTREKKINGSBEPERKING__OMSCHRIJVING_DERDE,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0168"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0168.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0168,
                DatabaseObjectKern.RELATIE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        // Verwerking kan niet doorgaan, omdat moeder wellicht niet ingeschrevene is
        // en daardoor bijvoorbeeld adres afleiding zal crashen.
        REPOSITORY.put(new RegelCodeAttribuut("BRBY0169"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0169.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0169,
                DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0170"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0170.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0170,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0172"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0172.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0172,
                DatabaseObjectKern.PERSOON_ADRES__LAND_GEBIED,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0175"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0175.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0175,
                DatabaseObjectKern.PERSOON_ADRES__GEMEENTE));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0176"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0176.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0176,
                DatabaseObjectKern.PERSOON_NATIONALITEIT__REDEN_VERKRIJGING));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0177"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0177.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0177,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0178"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0178.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0178,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0179"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0179.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0179,
                DatabaseObjectKern.PERSOON_VERSTREKKINGSBEPERKING__GEMEENTE_VERORDENING,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0180"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0180.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0180,
                DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0182"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0182.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0182,
                DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0183"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0183.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0183,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0187"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0187.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0187,
                DatabaseObjectKern.PERSOON__DATUM_GEBOORTE));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0189"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0189.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0189,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0191"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0191.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0191,
                DatabaseObjectKern.PERSOON_VERSTREKKINGSBEPERKING__PARTIJ,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0192"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0192.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0192,
                DatabaseObjectKern.PERSOON_VERSTREKKINGSBEPERKING__PARTIJ,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0193"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0193.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0193,
                DatabaseObjectKern.PERSOON_VERSTREKKINGSBEPERKING__GEMEENTE_VERORDENING,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0194"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0194.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0194,
                DatabaseObjectKern.PERSOON_VERSTREKKINGSBEPERKING__GEMEENTE_VERORDENING,
                SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0401"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0401.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0401,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0402"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0402.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0402,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0403"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0403.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0403,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0409"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0409.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0409,
                DatabaseObjectKern.RELATIE));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0417"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0417.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0417,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0429"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0429.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0429,
                DatabaseObjectKern.RELATIE__GEMEENTE_AANVANG,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0430"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0430.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0430,
                DatabaseObjectKern.RELATIE__LAND_GEBIED_AANVANG,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0437"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0437.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0437,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0438"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0438.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0438,
                DatabaseObjectKern.RELATIE__DATUM_AANVANG,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0442"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0442.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0442,
                DatabaseObjectKern.RELATIE__LAND_GEBIED_AANVANG,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0444"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0444.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0444,
                DatabaseObjectKern.RELATIE__DATUM_EINDE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0446"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0446.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0446,
                DatabaseObjectKern.RELATIE__LAND_GEBIED_EINDE,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0502"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0502.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0502,
                DatabaseObjectKern.PERSOON_ADRES));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0521"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0521.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0521,
                DatabaseObjectKern.PERSOON_ADRES__DATUM_AANVANG_ADRESHOUDING));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0525"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0525.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0525,
                DatabaseObjectKern.PERSOON_ADRES));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0532"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0532.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0532,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0540"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0540.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0540,
                DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0593"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0593.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0593,
                DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0543"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0543.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0543,
                DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0902"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0902.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0902,
                DatabaseObjectKern.PERSOON__DATUM_OVERLIJDEN,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0903"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0903.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0903,
                DatabaseObjectKern.PERSOON__GEMEENTE_OVERLIJDEN,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0904"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0904.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0904,
                DatabaseObjectKern.PERSOON__LAND_GEBIED_OVERLIJDEN,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0906"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0906.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0906,
                DatabaseObjectKern.PERSOON__LAND_GEBIED_OVERLIJDEN,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0907"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0907.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0907,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0908"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0908.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0908,
                DatabaseObjectKern.PERSOON__DATUM_OVERLIJDEN,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0909"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0909.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0909,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0913"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0913.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0913,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRPUC00112"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRPUC00112.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRPUC00112,
                DatabaseObjectKern.PERSOON__GESLACHTSAANDUIDING,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut(BRPUC_00120), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRPUC00120.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRPUC00120,
                DatabaseObjectKern.PERSOON__DATUM_GEBOORTE));

        REPOSITORY.put(new RegelCodeAttribuut("BRPUC50110"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRPUC50110.getOmschrijving()),
                SoortMelding.INFORMATIE, Regel.BRPUC50110,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut(BRAL_9003), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL9003.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRAL9003,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY9901"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY9901.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY9901,
                null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY9902"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY9902.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY9902,
                null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY9903"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY9903.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY9903,
                null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY9904"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY9904.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY9904,
                null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY9905"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY9905.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY9905,
                null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY9906"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY9906.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY9906,
                null, SoortFout.VERWERKING_KAN_DOORGAAN));

        // Verwerking kan niet doorgaan, omdat de connectie tussen de objecten incorrect is
        // en verdere verwerking dus onvoorspelbare fouten kan opleveren door ontbrekende of onverwachte data.
        REPOSITORY.put(new RegelCodeAttribuut("BRBY9910"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY9910.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY9910,
                null, SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut(BRPUC_00120), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRPUC00120.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRPUC00120,
                DatabaseObjectKern.PERSOON__DATUM_GEBOORTE));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0105M"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0105M.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRBY0105M,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0151"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0151.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0151,
                DatabaseObjectKern.PERSOON__INDICATIE_NAMENREEKS));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0152"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0152.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0152,
                DatabaseObjectKern.PERSOON,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0153"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0153.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0153,
                DatabaseObjectKern.PERSOON_VOORNAAM));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0157"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0157.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0157,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0158"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0158.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0158,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0162"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0162.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0162,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY1026"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY1026.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY1026,
                DatabaseObjectKern.PERSOON_REISDOCUMENT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0037"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0037.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0037,
                DatabaseObjectKern.PERSOON_REISDOCUMENT));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0040"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0040.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0040,
                DatabaseObjectKern.PERSOON));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0042"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0042.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY0042,
                DatabaseObjectKern.PERSOON_REISDOCUMENT));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0044"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0044.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0044,
                DatabaseObjectKern.PERSOON_REISDOCUMENT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY0045"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY0045.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBY0045,
                DatabaseObjectKern.PERSOON_REISDOCUMENT,
                        SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBY05111"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBY05111.getOmschrijving()),
                SoortMelding.DEBLOKKEERBAAR, Regel.BRBY05111,
                DatabaseObjectKern.PERSOON_ADRES));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0001"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0001.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0001, DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0003"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0003.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0003, DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0006"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0006.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0006, null, SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0007"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0007.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0007, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0008"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0008.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0008, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0009"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0009.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0009, DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0011"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0011.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0011, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0013"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0013.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0013, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0014"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0014.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0014, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0015"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0015.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0015, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut(Regel.R1260.name()), new RegelParameters(
                new MeldingtekstAttribuut(Regel.R1260.getOmschrijving()),
                SoortMelding.FOUT, Regel.R1260, null, SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut(Regel.R2055.name()), new RegelParameters(
                new MeldingtekstAttribuut(Regel.R2055.getOmschrijving()),
                SoortMelding.FOUT, Regel.R2055, null, SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0018"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0018.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0018, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut(Regel.R1262.name()), new RegelParameters(
                new MeldingtekstAttribuut(Regel.R1262.getOmschrijving()),
                SoortMelding.FOUT, Regel.R1262, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("R1263"), new RegelParameters(
            new MeldingtekstAttribuut(Regel.R1263.getOmschrijving()),
            SoortMelding.FOUT, Regel.R1263, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("R1264"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.R1264.getOmschrijving()),
                SoortMelding.FOUT, Regel.R1264, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0022"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0022.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0022, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0023"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0023.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0023, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0024"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0024.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0024, null, SoortFout.VERWERKING_VERHINDERD));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0027"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0027.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRLV0027, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0028"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0028.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRLV0028, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0029"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0029.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0029, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0031"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0031.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0031, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0032"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0032.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRLV0032, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0038"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0038.getOmschrijving()),
                SoortMelding.WAARSCHUWING, Regel.BRLV0038, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0040"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0040.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0040, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0041"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0041.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0041, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0042"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0042.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0042, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBV0043"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBV0043.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBV0043, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRLV0048"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRLV0048.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRLV0048, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBV0001"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBV0001.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBV0001, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBV0006"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBV0006.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBV0006, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("BRBV0007"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRBV0007.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRBV0007, null, SoortFout.VERWERKING_KAN_DOORGAAN));

        REPOSITORY.put(new RegelCodeAttribuut("R2052"), new RegelParameters(
            new MeldingtekstAttribuut(Regel.R2052.getOmschrijving()),
            SoortMelding.FOUT, Regel.R2052, null, SoortFout.VERWERKING_KAN_DOORGAAN));


        REPOSITORY.put(new RegelCodeAttribuut("R2055"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.R2055.getOmschrijving()),
                SoortMelding.FOUT, Regel.R2055, null, SoortFout.VERWERKING_VERHINDERD));


        REPOSITORY.put(new RegelCodeAttribuut("R2056"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.R2056.getOmschrijving()),
                SoortMelding.FOUT, Regel.R2056, null, SoortFout.VERWERKING_VERHINDERD));


        REPOSITORY.put(new RegelCodeAttribuut("R2130"), new RegelParameters(
                new MeldingtekstAttribuut(Regel.R2130.getOmschrijving()),
                SoortMelding.FOUT, Regel.R2130, null, SoortFout.VERWERKING_VERHINDERD));

    }

    @Override
    public RegelParameters getRegelParametersVoorRegel(final RegelCodeAttribuut regelCodeAttribuut) {
        return REPOSITORY.get(regelCodeAttribuut);
    }
}
