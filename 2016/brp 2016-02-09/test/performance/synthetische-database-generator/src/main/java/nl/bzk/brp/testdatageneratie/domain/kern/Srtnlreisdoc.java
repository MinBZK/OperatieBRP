/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

public enum Srtnlreisdoc {
    DUMMY(0,"XX", "Dummy"),
    DD(  1,"..","Onbekend"),
    BJ(  2,"BJ","Identiteitskaart (toeristenkaart) BJ"),
    EK(  3,"EK","Europese identiteitskaart"),
    IA(  4,"IA","Identiteitskaart (toeristenkaart) A"),
    IB(  5,"IB","Identiteitskaart (toeristenkaart) B"),
    IC(  6,"IC","Identiteitskaart (toeristenkaart) C"),
    ID(  7,"ID","Gemeentelijke Identiteitskaart"),
    KE(  8,"KE","Kaart met paspoortboekje, 64 pag."),
    KN(  9,"KN","Kaart met paspoortboekje, 32 pag."),
    KZ( 10,"KZ","Kaart zonder paspoortboekje"),
    LP( 11,"LP","Laissez-passer"),
    NB( 12,"NB","Nooddocument (model reisdocument vreemdelingen)"),
    NI( 13,"NI","Nederlandse identiteitskaart"),
    NN( 14,"NN","Noodpaspoort (model nationaal paspoort)"),
    NP( 15,"NP","Noodpaspoort"),
    NV( 16,"NV","Nooddocument (model reisdocument vluchtelingen)"),
    PB( 17,"PB","Reisdocument voor vreemdelingen"),
    PD( 18,"PD","Diplomatiek paspoort"),
    PF( 19,"PF","Faciliteitenpaspoort"),
    PN( 20,"PN","Nationaal paspoort"),
    PV( 21,"PV","Reisdocument voor vluchtelingen"),
    PZ( 22,"PZ","Dienstpaspoort"),
    R1( 23,"R1","Reisdocument ouder1"),
    R2( 24,"R2","Reisdocument ouder2"),
    RD( 25,"RD","Reisdocument voogd"),
    RM( 26,"RM","Reisdocument moeder"),
    RV( 27,"RV","Reisdocument vader"),
    TE( 28,"TE","Tweede paspoort (zakenpaspoort)"),
    TN( 29,"TN","Tweede paspoort"),
    ZN( 30,"ZN","Nationaal paspoort (zakenpaspoort)");


    private final short id;
    private final String code;
    private final String omschrijving;

    private Srtnlreisdoc(final int id, final String code, final String omschrijving) {
        this.id = (short)id; this.code = code; this.omschrijving = omschrijving;
    }

    public short getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
