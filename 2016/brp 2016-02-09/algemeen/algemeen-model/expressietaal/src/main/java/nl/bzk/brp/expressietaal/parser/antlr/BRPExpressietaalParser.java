/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

// Generated from BRPExpressietaal.g4 by ANTLR 4.1

package nl.bzk.brp.expressietaal.parser.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BRPExpressietaalParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__582=1, T__581=2, T__580=3, T__579=4, T__578=5, T__577=6, T__576=7, 
		T__575=8, T__574=9, T__573=10, T__572=11, T__571=12, T__570=13, T__569=14, 
		T__568=15, T__567=16, T__566=17, T__565=18, T__564=19, T__563=20, T__562=21, 
		T__561=22, T__560=23, T__559=24, T__558=25, T__557=26, T__556=27, T__555=28, 
		T__554=29, T__553=30, T__552=31, T__551=32, T__550=33, T__549=34, T__548=35, 
		T__547=36, T__546=37, T__545=38, T__544=39, T__543=40, T__542=41, T__541=42, 
		T__540=43, T__539=44, T__538=45, T__537=46, T__536=47, T__535=48, T__534=49, 
		T__533=50, T__532=51, T__531=52, T__530=53, T__529=54, T__528=55, T__527=56, 
		T__526=57, T__525=58, T__524=59, T__523=60, T__522=61, T__521=62, T__520=63, 
		T__519=64, T__518=65, T__517=66, T__516=67, T__515=68, T__514=69, T__513=70, 
		T__512=71, T__511=72, T__510=73, T__509=74, T__508=75, T__507=76, T__506=77, 
		T__505=78, T__504=79, T__503=80, T__502=81, T__501=82, T__500=83, T__499=84, 
		T__498=85, T__497=86, T__496=87, T__495=88, T__494=89, T__493=90, T__492=91, 
		T__491=92, T__490=93, T__489=94, T__488=95, T__487=96, T__486=97, T__485=98, 
		T__484=99, T__483=100, T__482=101, T__481=102, T__480=103, T__479=104, 
		T__478=105, T__477=106, T__476=107, T__475=108, T__474=109, T__473=110, 
		T__472=111, T__471=112, T__470=113, T__469=114, T__468=115, T__467=116, 
		T__466=117, T__465=118, T__464=119, T__463=120, T__462=121, T__461=122, 
		T__460=123, T__459=124, T__458=125, T__457=126, T__456=127, T__455=128, 
		T__454=129, T__453=130, T__452=131, T__451=132, T__450=133, T__449=134, 
		T__448=135, T__447=136, T__446=137, T__445=138, T__444=139, T__443=140, 
		T__442=141, T__441=142, T__440=143, T__439=144, T__438=145, T__437=146, 
		T__436=147, T__435=148, T__434=149, T__433=150, T__432=151, T__431=152, 
		T__430=153, T__429=154, T__428=155, T__427=156, T__426=157, T__425=158, 
		T__424=159, T__423=160, T__422=161, T__421=162, T__420=163, T__419=164, 
		T__418=165, T__417=166, T__416=167, T__415=168, T__414=169, T__413=170, 
		T__412=171, T__411=172, T__410=173, T__409=174, T__408=175, T__407=176, 
		T__406=177, T__405=178, T__404=179, T__403=180, T__402=181, T__401=182, 
		T__400=183, T__399=184, T__398=185, T__397=186, T__396=187, T__395=188, 
		T__394=189, T__393=190, T__392=191, T__391=192, T__390=193, T__389=194, 
		T__388=195, T__387=196, T__386=197, T__385=198, T__384=199, T__383=200, 
		T__382=201, T__381=202, T__380=203, T__379=204, T__378=205, T__377=206, 
		T__376=207, T__375=208, T__374=209, T__373=210, T__372=211, T__371=212, 
		T__370=213, T__369=214, T__368=215, T__367=216, T__366=217, T__365=218, 
		T__364=219, T__363=220, T__362=221, T__361=222, T__360=223, T__359=224, 
		T__358=225, T__357=226, T__356=227, T__355=228, T__354=229, T__353=230, 
		T__352=231, T__351=232, T__350=233, T__349=234, T__348=235, T__347=236, 
		T__346=237, T__345=238, T__344=239, T__343=240, T__342=241, T__341=242, 
		T__340=243, T__339=244, T__338=245, T__337=246, T__336=247, T__335=248, 
		T__334=249, T__333=250, T__332=251, T__331=252, T__330=253, T__329=254, 
		T__328=255, T__327=256, T__326=257, T__325=258, T__324=259, T__323=260, 
		T__322=261, T__321=262, T__320=263, T__319=264, T__318=265, T__317=266, 
		T__316=267, T__315=268, T__314=269, T__313=270, T__312=271, T__311=272, 
		T__310=273, T__309=274, T__308=275, T__307=276, T__306=277, T__305=278, 
		T__304=279, T__303=280, T__302=281, T__301=282, T__300=283, T__299=284, 
		T__298=285, T__297=286, T__296=287, T__295=288, T__294=289, T__293=290, 
		T__292=291, T__291=292, T__290=293, T__289=294, T__288=295, T__287=296, 
		T__286=297, T__285=298, T__284=299, T__283=300, T__282=301, T__281=302, 
		T__280=303, T__279=304, T__278=305, T__277=306, T__276=307, T__275=308, 
		T__274=309, T__273=310, T__272=311, T__271=312, T__270=313, T__269=314, 
		T__268=315, T__267=316, T__266=317, T__265=318, T__264=319, T__263=320, 
		T__262=321, T__261=322, T__260=323, T__259=324, T__258=325, T__257=326, 
		T__256=327, T__255=328, T__254=329, T__253=330, T__252=331, T__251=332, 
		T__250=333, T__249=334, T__248=335, T__247=336, T__246=337, T__245=338, 
		T__244=339, T__243=340, T__242=341, T__241=342, T__240=343, T__239=344, 
		T__238=345, T__237=346, T__236=347, T__235=348, T__234=349, T__233=350, 
		T__232=351, T__231=352, T__230=353, T__229=354, T__228=355, T__227=356, 
		T__226=357, T__225=358, T__224=359, T__223=360, T__222=361, T__221=362, 
		T__220=363, T__219=364, T__218=365, T__217=366, T__216=367, T__215=368, 
		T__214=369, T__213=370, T__212=371, T__211=372, T__210=373, T__209=374, 
		T__208=375, T__207=376, T__206=377, T__205=378, T__204=379, T__203=380, 
		T__202=381, T__201=382, T__200=383, T__199=384, T__198=385, T__197=386, 
		T__196=387, T__195=388, T__194=389, T__193=390, T__192=391, T__191=392, 
		T__190=393, T__189=394, T__188=395, T__187=396, T__186=397, T__185=398, 
		T__184=399, T__183=400, T__182=401, T__181=402, T__180=403, T__179=404, 
		T__178=405, T__177=406, T__176=407, T__175=408, T__174=409, T__173=410, 
		T__172=411, T__171=412, T__170=413, T__169=414, T__168=415, T__167=416, 
		T__166=417, T__165=418, T__164=419, T__163=420, T__162=421, T__161=422, 
		T__160=423, T__159=424, T__158=425, T__157=426, T__156=427, T__155=428, 
		T__154=429, T__153=430, T__152=431, T__151=432, T__150=433, T__149=434, 
		T__148=435, T__147=436, T__146=437, T__145=438, T__144=439, T__143=440, 
		T__142=441, T__141=442, T__140=443, T__139=444, T__138=445, T__137=446, 
		T__136=447, T__135=448, T__134=449, T__133=450, T__132=451, T__131=452, 
		T__130=453, T__129=454, T__128=455, T__127=456, T__126=457, T__125=458, 
		T__124=459, T__123=460, T__122=461, T__121=462, T__120=463, T__119=464, 
		T__118=465, T__117=466, T__116=467, T__115=468, T__114=469, T__113=470, 
		T__112=471, T__111=472, T__110=473, T__109=474, T__108=475, T__107=476, 
		T__106=477, T__105=478, T__104=479, T__103=480, T__102=481, T__101=482, 
		T__100=483, T__99=484, T__98=485, T__97=486, T__96=487, T__95=488, T__94=489, 
		T__93=490, T__92=491, T__91=492, T__90=493, T__89=494, T__88=495, T__87=496, 
		T__86=497, T__85=498, T__84=499, T__83=500, T__82=501, T__81=502, T__80=503, 
		T__79=504, T__78=505, T__77=506, T__76=507, T__75=508, T__74=509, T__73=510, 
		T__72=511, T__71=512, T__70=513, T__69=514, T__68=515, T__67=516, T__66=517, 
		T__65=518, T__64=519, T__63=520, T__62=521, T__61=522, T__60=523, T__59=524, 
		T__58=525, T__57=526, T__56=527, T__55=528, T__54=529, T__53=530, T__52=531, 
		T__51=532, T__50=533, T__49=534, T__48=535, T__47=536, T__46=537, T__45=538, 
		T__44=539, T__43=540, T__42=541, T__41=542, T__40=543, T__39=544, T__38=545, 
		T__37=546, T__36=547, T__35=548, T__34=549, T__33=550, T__32=551, T__31=552, 
		T__30=553, T__29=554, T__28=555, T__27=556, T__26=557, T__25=558, T__24=559, 
		T__23=560, T__22=561, T__21=562, T__20=563, T__19=564, T__18=565, T__17=566, 
		T__16=567, T__15=568, T__14=569, T__13=570, T__12=571, T__11=572, T__10=573, 
		T__9=574, T__8=575, T__7=576, T__6=577, T__5=578, T__4=579, T__3=580, 
		T__2=581, T__1=582, T__0=583, STRING=584, IDENTIFIER=585, INTEGER=586, 
		WS=587, LP=588, RP=589, LL=590, RL=591, COMMA=592, DOT=593, UNKNOWN_VALUE=594, 
		OP_EQUAL=595, OP_NOT_EQUAL=596, OP_LIKE=597, OP_LESS=598, OP_GREATER=599, 
		OP_LESS_EQUAL=600, OP_GREATER_EQUAL=601, OP_IN=602, OP_IN_WILDCARD=603, 
		OP_PLUS=604, OP_MINUS=605, OP_OR=606, OP_AND=607, OP_NOT=608, OP_REF=609, 
		OP_WAARBIJ=610, TRUE_CONSTANT=611, FALSE_CONSTANT=612, NULL_CONSTANT=613, 
		MAAND_JAN=614, MAAND_FEB=615, MAAND_MRT=616, MAAND_APR=617, MAAND_MEI=618, 
		MAAND_JUN=619, MAAND_JUL=620, MAAND_AUG=621, MAAND_SEP=622, MAAND_OKT=623, 
		MAAND_NOV=624, MAAND_DEC=625;
	public static final String[] tokenNames = {
		"<INVALID>", "'indicatie.staatloos.verantwoordingInhoud.tijdstip_registratie'", 
		"'samengestelde_naam.verantwoordingAanpassingGeldigheid.soort'", "'nummerverwijzing.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'uitsluiting_kiesrecht.datum_tijd_registratie'", "'nummerverwijzing.verantwoordingInhoud.datum_ontlening'", 
		"'verblijfsrecht.verantwoordingVerval.partij'", "'bijhouding_beeindigd'", 
		"'ouderschap.datum_aanvang_geldigheid'", "'buitenlands_adres_regel_3'", 
		"'DATUM'", "'persoonskaart.verantwoordingVerval.soort'", "'buitenlands_adres_regel_2'", 
		"'inschrijving.verantwoordingInhoud.soort'", "'indicatie.volledige_verstrekkingsbeperking.verantwoordingInhoud.partij'", 
		"'verantwoordingAanpassingGeldigheid.datum_ontlening'", "'verantwoordingInhoud.partij'", 
		"'persoononderzoek.datum_tijd_verval'", "'waarde'", "'naamgebruik.verantwoordingInhoud.datum_ontlening'", 
		"'geslachtsaanduiding.geslachtsaanduiding'", "'indicatie.onder_curatele.verantwoordingVerval.soort'", 
		"'administratief.tijdstip_laatste_wijziging_gbasystematiek'", "'indicatie.staatloos.verantwoordingVerval.tijdstip_registratie'", 
		"'deelname_eu_verkiezingen.verantwoordingInhoud.tijdstip_registratie'", 
		"'administratief.sorteervolgorde'", "'administratief.datum_tijd_registratie'", 
		"'adressen'", "'behandeld_als_nederlander'", "'uitsluiting_kiesrecht.verantwoordingInhoud.soort'", 
		"'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingInhoud.partij'", 
		"'indicatie.volledige_verstrekkingsbeperking.verantwoordingVerval.soort'", 
		"'migratie_reden_beeindigen_nationaliteit'", "'nummerverwijzing.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'verblijfsrecht.datum_mededeling'", "'PARTNERS'", "'samengestelde_naam.verantwoordingInhoud.partij'", 
		"'samengestelde_naam.datum_aanvang_geldigheid'", "'persoonskaart.verantwoordingInhoud.datum_ontlening'", 
		"'nummer'", "'geboorte.omschrijving_locatie'", "'uitsluiting_kiesrecht.verantwoordingInhoud.tijdstip_registratie'", 
		"'indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.partij'", 
		"'buitenlands_adres_regel_1'", "'volgnummer'", "'indicatie.behandeld_als_nederlander.verantwoordingVerval.datum_ontlening'", 
		"'identificatienummers.verantwoordingVerval.tijdstip_registratie'", "'reden_verkrijging'", 
		"'GERELATEERDE_BETROKKENHEDEN'", "'naamgebruik.verantwoordingInhoud.partij'", 
		"'datum_aanvang_materiele_periode'", "'datum_aanvang'", "'bijhouding.datum_einde_geldigheid'", 
		"'nummerverwijzing.datum_aanvang_geldigheid'", "'datum_aanvang_adreshouding'", 
		"'MAP'", "'GEWIJZIGD'", "'buitenlands_adres_regel_5'", "'naamgebruik'", 
		"'status'", "'ouderlijk_gezag.verantwoordingVerval.partij'", "'naamgebruik.predicaat'", 
		"'indicatie.staatloos'", "'verantwoordingAanpassingGeldigheid.partij'", 
		"'samengestelde_naam.geslachtsnaamstam'", "'indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.partij'", 
		"'nummerverwijzing.datum_tijd_verval'", "'migratie.datum_tijd_verval'", 
		"'identificatienummers.verantwoordingInhoud.tijdstip_registratie'", "'bijzondere_verblijfsrechtelijke_positie'", 
		"'indicatie.derde_heeft_gezag.datum_einde_geldigheid'", "'inschrijving.verantwoordingInhoud.tijdstip_registratie'", 
		"'samengestelde_naam.afgeleid'", "'betrokkenheden'", "'buitenlands_adres_regel_4'", 
		"'indicatie.onder_curatele.datum_tijd_verval'", "'indicatie.derde_heeft_gezag.verantwoordingVerval.tijdstip_registratie'", 
		"'inschrijving.versienummer'", "'ouderlijk_gezag.verantwoordingAanpassingGeldigheid.soort'", 
		"'onder.curatele'", "'nummerverwijzing'", "'gemeente_einde'", "'persoon_aangetroffen_op_adres'", 
		"'administratief.verantwoordingInhoud.tijdstip_registratie'", "'indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'uitsluiting_kiesrecht.verantwoordingVerval.datum_ontlening'", "'naamgebruik.verantwoordingVerval.partij'", 
		"'vastgesteld_niet_nederlander'", "'migratie.verantwoordingInhoud.soort'", 
		"'verstrekkingsbeperkingen'", "'naamgebruik.afgeleid'", "'administratief.verantwoordingInhoud.datum_ontlening'", 
		"'KINDEREN'", "'datum_inhouding_vermissing'", "'bijhouding'", "'aangever_adreshouding'", 
		"'NAAMGEVERS'", "'buitenlandse_plaats_einde'", "'verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'indicatie.staatloos.verantwoordingInhoud.datum_ontlening'", "'AANTAL_DAGEN'", 
		"'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.soort'", 
		"'anummer'", "'inschrijving.datum_tijd_verval'", "'bsn'", "'overlijden.omschrijving_locatie'", 
		"'identificatienummers.administratienummer'", "'identificatienummers.verantwoordingInhoud.datum_ontlening'", 
		"'bijhouding.verantwoordingVerval.soort'", "'uitsluiting_kiesrecht.datum_voorzien_einde_uitsluiting_kiesrecht'", 
		"'indicatie.behandeld_als_nederlander.datum_aanvang_geldigheid'", "'overlijden.verantwoordingVerval.tijdstip_registratie'", 
		"'verblijfsrecht.verantwoordingVerval.soort'", "'indicatie.staatloos.verantwoordingAanpassingGeldigheid.soort'", 
		"'buitenlands_adres_regel_6'", "'identificatienummers.datum_tijd_verval'", 
		"'indicatie.onder_curatele.verantwoordingInhoud.partij'", "'signalering_met_betrekking_tot_verstrekken_reisdocument'", 
		"'geslachtsaanduiding.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'deelname_eu_verkiezingen.verantwoordingVerval.partij'", "'identificatienummers.verantwoordingAanpassingGeldigheid.soort'", 
		"'ouderschap.verantwoordingVerval.soort'", "'samengestelde_naam.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'geboorte.buitenlandse_regio'", "'indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'indicatie.derde_heeft_gezag.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'naamgebruik.naamgebruik'", "'verantwoordingVerval.datum_ontlening'", 
		"'nationaliteit'", "'samengestelde_naam.voorvoegsel'", "'VIEW'", "'indicatie.staatloos.verantwoordingVerval.partij'", 
		"'bijhouding.verantwoordingInhoud.tijdstip_registratie'", "'identificatiecode_nummeraanduiding'", 
		"'indicatie.volledige_verstrekkingsbeperking.verantwoordingVerval.tijdstip_registratie'", 
		"'naamgebruik.verantwoordingInhoud.tijdstip_registratie'", "'indicatie.staatloos.datum_tijd_verval'", 
		"'aanduiding_inhouding_vermissing'", "'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingInhoud.soort'", 
		"'verificaties'", "'overlijden.verantwoordingInhoud.partij'", "'geboorte.verantwoordingInhoud.datum_ontlening'", 
		"'geboorte.verantwoordingVerval.tijdstip_registratie'", "'afgekorte_naam_openbare_ruimte'", 
		"'geboorte.datum'", "'indicatie.behandeld_als_nederlander.datum_tijd_verval'", 
		"'geslachtsaanduiding.datum_aanvang_geldigheid'", "'ouderlijk_gezag.verantwoordingInhoud.tijdstip_registratie'", 
		"'indicatie.derde_heeft_gezag.datum_tijd_verval'", "'GEREGISTREERD_PARTNERS'", 
		"'geslachtsaanduiding'", "'ouderlijk_gezag.verantwoordingVerval.datum_ontlening'", 
		"'identificatienummers.verantwoordingVerval.datum_ontlening'", "'geboorte.verantwoordingInhoud.tijdstip_registratie'", 
		"'INSTEMMERS'", "'overlijden.datum'", "'buitenlandse_regio_einde'", "'staatloos'", 
		"'geslachtsaanduiding.verantwoordingInhoud.soort'", "'naamgebruik.datum_tijd_verval'", 
		"'samengestelde_naam.verantwoordingAanpassingGeldigheid.partij'", "'datum_einde_document'", 
		"'indicatie.derde_heeft_gezag.datum_aanvang_geldigheid'", "'adellijke_titel'", 
		"'naamgebruik.voornamen'", "'indicatie.bijzondere_verblijfsrechtelijke_positie.datum_tijd_verval'", 
		"'migratie'", "'persoonskaart.verantwoordingVerval.datum_ontlening'", 
		"'migratie.buitenlands_adres_regel_2'", "'deelname_eu_verkiezingen.verantwoordingInhoud.partij'", 
		"'migratie.land_gebied'", "'datum_einde_volgen'", "'gemeente_verordening'", 
		"'geslachtsaanduiding.verantwoordingAanpassingGeldigheid.soort'", "'inschrijving.verantwoordingVerval.partij'", 
		"'geboorte.land_gebied'", "'nummerverwijzing.verantwoordingVerval.datum_ontlening'", 
		"'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.partij'", 
		"'persoonskaart.gemeente'", "'inschrijving.verantwoordingVerval.tijdstip_registratie'", 
		"'migratie.buitenlands_adres_regel_3'", "'naamgebruik.geslachtsnaamstam'", 
		"'ouderschap.verantwoordingVerval.tijdstip_registratie'", "'AANTAL'", 
		"'identificatienummers.verantwoordingInhoud.partij'", "'indicatie.volledige_verstrekkingsbeperking.datum_tijd_verval'", 
		"'indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'indicatie.derde_heeft_gezag'", "'omschrijving_locatie_aanvang'", "'indicatie.onder_curatele.verantwoordingInhoud.datum_ontlening'", 
		"'ouderschap.verantwoordingVerval.partij'", "'ouderlijk_gezag.ouder_heeft_gezag'", 
		"'geslachtsaanduiding.datum_tijd_verval'", "'migratie_datum_einde_bijhouding'", 
		"'migratie.verantwoordingAanpassingGeldigheid.partij'", "'migratie.buitenlands_adres_regel_4'", 
		"'uitsluiting_kiesrecht.verantwoordingInhoud.datum_ontlening'", "'indicatie.derde_heeft_gezag.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'indicatie.staatloos.verantwoordingVerval.soort'", "'verblijfsrecht.datum_aanvang'", 
		"'overlijden.gemeente'", "'verblijfsrecht.datum_voorzien_einde'", "'indicatie.derde_heeft_gezag.verantwoordingVerval.datum_ontlening'", 
		"'indicatie.onder_curatele.verantwoordingInhoud.tijdstip_registratie'", 
		"'woonplaatsnaam_einde'", "'migratie.buitenlands_adres_regel_5'", "'overlijden.buitenlandse_plaats'", 
		"'overlijden.verantwoordingVerval.soort'", "'samengestelde_naam.verantwoordingInhoud.tijdstip_registratie'", 
		"'datum_einde'", "'indicatie.behandeld_als_nederlander.verantwoordingInhoud.datum_ontlening'", 
		"'indicatie.behandeld_als_nederlander.verantwoordingInhoud.partij'", "'identificatiecode_adresseerbaar_object'", 
		"'deelname_eu_verkiezingen.datum_voorzien_einde_uitsluiting_eu_verkiezingen'", 
		"'geboorte.verantwoordingVerval.partij'", "'naamgebruik.scheidingsteken'", 
		"'indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.datum_ontlening'", 
		"'overlijden.verantwoordingInhoud.soort'", "'samengestelde_naam.verantwoordingVerval.datum_ontlening'", 
		"'indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'scheidingsteken'", "'ouderlijk_gezag.datum_einde_geldigheid'", "'indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'nummerverwijzing.verantwoordingVerval.soort'", "'ouderlijk_gezag.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'administratief.tijdstip_laatste_wijziging'", "'administratief.verantwoordingVerval.partij'", 
		"'identificatienummers.bsn'", "'ouderlijk_gezag.datum_tijd_verval'", "'nummerverwijzing.verantwoordingInhoud.soort'", 
		"'bijhouding.bijhoudingsaard'", "'indicatie.behandeld_als_nederlander.datum_einde_geldigheid'", 
		"'indicatie.vastgesteld_niet_nederlander.datum_aanvang_geldigheid'", "'datum_aanvang_geldigheid'", 
		"'geslachtsaanduiding.verantwoordingInhoud.tijdstip_registratie'", "'ouderschap.datum_tijd_verval'", 
		"'indicatie.vastgesteld_niet_nederlander.verantwoordingVerval.soort'", 
		"'bijhouding.onverwerkt_document_aanwezig'", "'indicatie.onder_curatele.verantwoordingVerval.tijdstip_registratie'", 
		"'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.datum_ontlening'", 
		"'woonplaatsnaam_aanvang'", "'verantwoordingAanpassingGeldigheid.soort'", 
		"'ouderschap.datum_einde_geldigheid'", "'NAAMSKEUZEPARTNERS'", "'nummerverwijzing.datum_einde_geldigheid'", 
		"'indicatie.derde_heeft_gezag.verantwoordingInhoud.datum_ontlening'", 
		"'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingVerval.datum_ontlening'", 
		"'inschrijving.verantwoordingVerval.soort'", "'nummerverwijzing.volgende_burgerservicenummer'", 
		"'ouderschap.datum_tijd_registratie'", "'reden_einde'", "'ALS'", "'verblijfsrecht.datum_tijd_verval'", 
		"'samengestelde_naam.datum_tijd_registratie'", "'geboorte.gemeente'", 
		"'naam_openbare_ruimte'", "'geboorte.verantwoordingInhoud.partij'", "'indicatie.onder_curatele.datum_tijd_registratie'", 
		"'ouderlijk_gezag.verantwoordingVerval.soort'", "'reden_wijziging'", "'verantwoordingInhoud.tijdstip_registratie'", 
		"'migratie.verantwoordingVerval.soort'", "'administratief.verantwoordingInhoud.partij'", 
		"'ERKENNERS'", "'geboorte'", "'geboorte.verantwoordingVerval.datum_ontlening'", 
		"'ouderschap.verantwoordingInhoud.tijdstip_registratie'", "'verantwoordingInhoud.soort'", 
		"'administratief.verantwoordingVerval.datum_ontlening'", "'nummerverwijzing.vorige_administratienummer'", 
		"'datum_tijd_verval'", "'HUWELIJKSPARTNERS'", "'deelname_eu_verkiezingen.verantwoordingVerval.tijdstip_registratie'", 
		"'migratie.buitenlands_adres_regel_1'", "'overlijden.verantwoordingInhoud.datum_ontlening'", 
		"'afnemerindicaties'", "'persoononderzoek.verantwoordingVerval.datum_ontlening'", 
		"'persoonskaart'", "'persoonskaart.verantwoordingInhoud.tijdstip_registratie'", 
		"'identificatienummers.verantwoordingVerval.soort'", "'ouderschap.verantwoordingInhoud.datum_ontlening'", 
		"'identificatienummers.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'indicatie.onder_curatele.verantwoordingInhoud.soort'", "'indicatie.derde_heeft_gezag.verantwoordingVerval.partij'", 
		"'verantwoordingInhoud.datum_ontlening'", "'uitsluiting_kiesrecht.verantwoordingInhoud.partij'", 
		"'ouderschap.verantwoordingVerval.datum_ontlening'", "'deelname_eu_verkiezingen.datum_aanleiding_aanpassing_deelname_eu_verkiezingen'", 
		"'land_gebied_aanvang'", "'geslachtsaanduiding.verantwoordingInhoud.datum_ontlening'", 
		"'overlijden.buitenlandse_regio'", "'LAATSTE_DAG'", "'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingVerval.partij'", 
		"'overlijden.woonplaatsnaam'", "'ouderlijk_gezag.datum_aanvang_geldigheid'", 
		"'geslachtsaanduiding.verantwoordingVerval.datum_ontlening'", "'indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.soort'", 
		"'naamgebruik.verantwoordingVerval.datum_ontlening'", "'geboorte.verantwoordingVerval.soort'", 
		"'indicatie.derde_heeft_gezag.verantwoordingInhoud.partij'", "'inschrijving.datumtijdstempel'", 
		"'JAAR'", "'indicatie.vastgesteld_niet_nederlander.verantwoordingVerval.tijdstip_registratie'", 
		"'migratie.reden_wijziging'", "'partij'", "'indicatie.volledige_verstrekkingsbeperking.verantwoordingVerval.datum_ontlening'", 
		"'indicatie.volledige_verstrekkingsbeperking.verantwoordingInhoud.tijdstip_registratie'", 
		"'persoononderzoek.verantwoordingInhoud.soort'", "'migratie.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'indicatie.staatloos.datum_einde_geldigheid'", "'ouderschap.verantwoordingAanpassingGeldigheid.soort'", 
		"'nummerverwijzing.verantwoordingAanpassingGeldigheid.partij'", "'VANDAAG'", 
		"'datum_tijd_registratie'", "'geboorte.woonplaatsnaam'", "'migratie.verantwoordingInhoud.partij'", 
		"'indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.tijdstip_registratie'", 
		"'administratief.onverwerkt_bijhoudingsvoorstel_nietingezetene_aanwezig'", 
		"'indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.soort'", 
		"'indicatie.onder_curatele.datum_aanvang_geldigheid'", "'huisnummertoevoeging'", 
		"'administratief.verantwoordingVerval.soort'", "'geslachtsaanduiding.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'indicatie.derde_heeft_gezag.verantwoordingInhoud.soort'", "'overlijden.land_gebied'", 
		"'land_gebied'", "'samengestelde_naam.adellijke_titel'", "'administratief.verantwoordingVerval.tijdstip_registratie'", 
		"'FAMILIERECHTELIJKEBETREKKINGEN'", "'persoononderzoek.verantwoordingInhoud.tijdstip_registratie'", 
		"'datum_uitgifte'", "'uitsluiting_kiesrecht.uitsluiting_kiesrecht'", "'identificatienummers.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingVerval.soort'", 
		"'indicatie.behandeld_als_nederlander.datum_tijd_registratie'", "'stam'", 
		"'bijhouding.nadere_bijhoudingsaard'", "'indicatie.staatloos.verantwoordingInhoud.soort'", 
		"'samengestelde_naam.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'bijhouding.verantwoordingInhoud.datum_ontlening'", "'nummerverwijzing.verantwoordingAanpassingGeldigheid.soort'", 
		"'bijhouding.verantwoordingVerval.tijdstip_registratie'", "'indicaties'", 
		"'indicatie.vastgesteld_niet_nederlander.datum_tijd_verval'", "'identificatienummers.burgerservicenummer'", 
		"'migratie.verantwoordingInhoud.tijdstip_registratie'", "'nummerverwijzing.datum_tijd_registratie'", 
		"'bijhouding.verantwoordingInhoud.partij'", "'indicatie.volledige_verstrekkingsbeperking.verantwoordingVerval.partij'", 
		"'DAG'", "'woonplaatsnaam'", "'persoonskaart.verantwoordingVerval.tijdstip_registratie'", 
		"'ER_IS'", "'indicatie.volledige_verstrekkingsbeperking'", "'huisletter'", 
		"'overlijden.verantwoordingVerval.datum_ontlening'", "'geboorte.verantwoordingInhoud.soort'", 
		"'indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.soort'", 
		"'['", "'predicaat'", "'FILTER'", "'bijhouding.datum_tijd_verval'", "'indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.partij'", 
		"'deelname_eu_verkiezingen.deelname_eu_verkiezingen'", "'migratie.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'migratie.datum_einde_geldigheid'", "'migratie.verantwoordingInhoud.datum_ontlening'", 
		"'HUWELIJKEN'", "'afnemer'", "'samengestelde_naam.verantwoordingVerval.soort'", 
		"'indicatie.staatloos.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'naamgebruik.adellijke_titel'", "'verblijfsrecht.verantwoordingVerval.tijdstip_registratie'", 
		"'soort'", "'inschrijving.datum_tijd_registratie'", "'huisnummer'", "'voorvoegsel'", 
		"'indicatie.derde_heeft_gezag.datum_tijd_registratie'", "'administratief.verantwoordingInhoud.soort'", 
		"'indicatie.volledige_verstrekkingsbeperking.verantwoordingInhoud.soort'", 
		"'indicatie.staatloos.verantwoordingAanpassingGeldigheid.partij'", "'ouderlijk_gezag.verantwoordingInhoud.partij'", 
		"'bijhouding.verantwoordingVerval.partij'", "'indicatie.behandeld_als_nederlander.verantwoordingVerval.tijdstip_registratie'", 
		"'naam'", "'geboorte.datum_tijd_registratie'", "'OUDERS'", "'migratie.verantwoordingVerval.tijdstip_registratie'", 
		"'omschrijving_derde'", "'verantwoordingVerval.tijdstip_registratie'", 
		"'reisdocumenten'", "'omschrijving'", "'migratie.buitenlands_adres_regel_6'", 
		"'voornamen'", "'migratie.datum_aanvang_geldigheid'", "'ouderlijk_gezag.verantwoordingAanpassingGeldigheid.partij'", 
		"'verblijfsrecht.verantwoordingVerval.datum_ontlening'", "'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.soort'", 
		"'indicatie.vastgesteld_niet_nederlander.verantwoordingVerval.partij'", 
		"']'", "'inschrijving.datum'", "'derde.heeft.gezag'", "'verblijfsrecht.verantwoordingInhoud.tijdstip_registratie'", 
		"'samengestelde_naam.datum_einde_geldigheid'", "'bijhouding.verantwoordingInhoud.soort'", 
		"'persoonskaart.volledig_geconverteerd'", "'geboorte.datum_tijd_verval'", 
		"'indicatie.vastgesteld_niet_nederlander'", "'samengestelde_naam.verantwoordingVerval.partij'", 
		"'administratief.datum_tijd_verval'", "'indicatie.bijzondere_verblijfsrechtelijke_positie'", 
		"'samengestelde_naam.scheidingsteken'", "'^'", "'bijhouding.datum_tijd_registratie'", 
		"'nationaliteiten'", "'overlijden.verantwoordingVerval.partij'", "'ouderlijk_gezag.verantwoordingInhoud.soort'", 
		"'overlijden'", "'indicatie.staatloos.verantwoordingVerval.datum_ontlening'", 
		"'verblijfsrecht.verantwoordingInhoud.partij'", "'indicatie.behandeld_als_nederlander'", 
		"'uitsluiting_kiesrecht'", "'indicatie.derde_heeft_gezag.verantwoordingAanpassingGeldigheid.partij'", 
		"'deelname_eu_verkiezingen.verantwoordingInhoud.datum_ontlening'", "'indicatie.staatloos.datum_aanvang_geldigheid'", 
		"'datum'", "'ONDERZOEKEN'", "'ouderschap.verantwoordingInhoud.soort'", 
		"'migratie.verantwoordingAanpassingGeldigheid.soort'", "'persoonskaart.datum_tijd_registratie'", 
		"'nummerverwijzing.verantwoordingVerval.tijdstip_registratie'", "'indicatie.vastgesteld_niet_nederlander.verantwoordingVerval.datum_ontlening'", 
		"'datum_ingang_document'", "'bijhouding.datum_aanvang_geldigheid'", "'naamgebruik.verantwoordingVerval.tijdstip_registratie'", 
		"'samengestelde_naam.verantwoordingInhoud.soort'", "'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument'", 
		"'locatieomschrijving'", "'deelname_eu_verkiezingen.verantwoordingVerval.datum_ontlening'", 
		"'samengestelde_naam'", "'indicatie.staatloos.verantwoordingInhoud.partij'", 
		"'overlijden.verantwoordingInhoud.tijdstip_registratie'", "'uitsluiting_kiesrecht.datum_tijd_verval'", 
		"'nummerverwijzing.verantwoordingInhoud.tijdstip_registratie'", "'ouderschap.ouder_uit_wie_het_kind_is_geboren'", 
		"'ALLE'", "'uitsluiting_kiesrecht.verantwoordingVerval.tijdstip_registratie'", 
		"'ouderschap.verantwoordingInhoud.partij'", "'indicatie.derde_heeft_gezag.verantwoordingInhoud.tijdstip_registratie'", 
		"'migratie.soort'", "'persoononderzoek.datum_tijd_registratie'", "'samengestelde_naam.verantwoordingVerval.tijdstip_registratie'", 
		"'ouderlijk_gezag.verantwoordingVerval.tijdstip_registratie'", "'geslachtsaanduiding.verantwoordingVerval.tijdstip_registratie'", 
		"'bijhouding.verantwoordingVerval.datum_ontlening'", "'persoononderzoek.verantwoordingInhoud.datum_ontlening'", 
		"'identificatienummers.verantwoordingInhoud.soort'", "'buitenlandse_plaats_aanvang'", 
		"'buitenlandse_regio_aanvang'", "'bijhouding.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'overlijden.datum_tijd_verval'", "'indicatie.onder_curatele'", "'geboorte.buitenlandse_plaats'", 
		"'verantwoordingVerval.soort'", "'identificatienummers.datum_einde_geldigheid'", 
		"'deelname_eu_verkiezingen.verantwoordingVerval.soort'", "'ouderschap.verantwoordingAanpassingGeldigheid.partij'", 
		"'identificatienummers.datum_aanvang_geldigheid'", "'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingInhoud.tijdstip_registratie'", 
		"'nummerverwijzing.vorige_burgerservicenummer'", "'land_gebied_einde'", 
		"'bijhouding.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'identificatienummers.verantwoordingVerval.partij'", "'PLATTE_LIJST'", 
		"'nummerverwijzing.verantwoordingInhoud.partij'", "'indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.soort'", 
		"'indicatie.volledige_verstrekkingsbeperking.verantwoordingInhoud.datum_ontlening'", 
		"'verwachte_afhandeldatum'", "'deelname_eu_verkiezingen.verantwoordingInhoud.soort'", 
		"'indicatie.derde_heeft_gezag.verantwoordingVerval.soort'", "'verblijfsrecht.verantwoordingInhoud.datum_ontlening'", 
		"'migratie.verantwoordingVerval.datum_ontlening'", "'persoononderzoek.verantwoordingVerval.partij'", 
		"'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.datum_ontlening'", 
		"'gemeente_aanvang'", "'ouderlijk_gezag.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'persoononderzoek.verantwoordingInhoud.partij'", "'inschrijving.verantwoordingVerval.datum_ontlening'", 
		"'migratie.verantwoordingVerval.partij'", "'identificatienummers.datum_tijd_registratie'", 
		"'/'", "'gemeentedeel'", "'omschrijving_locatie_einde'", "'deelname_eu_verkiezingen.datum_tijd_verval'", 
		"'naamgebruik.verantwoordingVerval.soort'", "'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.datum_tijd_registratie'", 
		"'geslachtsaanduiding.verantwoordingAanpassingGeldigheid.partij'", "'inschrijving.verantwoordingInhoud.datum_ontlening'", 
		"'geslachtsaanduiding.verantwoordingInhoud.partij'", "'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.partij'", 
		"'persoonskaart.verantwoordingInhoud.partij'", "'indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.partij'", 
		"'PARTNERSCHAPPEN'", "'samengestelde_naam.voornamen'", "'indicatie.behandeld_als_nederlander.verantwoordingInhoud.tijdstip_registratie'", 
		"'ouderschap.verantwoordingAanpassingGeldigheid.tijdstip_registratie'", 
		"'indicatie.derde_heeft_gezag.verantwoordingAanpassingGeldigheid.soort'", 
		"'nummerverwijzing.verantwoordingVerval.partij'", "'ouderschap.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'postcode'", "'indicatie.onder_curatele.datum_einde_geldigheid'", "'verblijfsrecht'", 
		"'indicatie.behandeld_als_nederlander.verantwoordingVerval.partij'", "'indicatie.vastgesteld_niet_nederlander.datum_einde_geldigheid'", 
		"'datum_einde_geldigheid'", "'migratie_reden_opname_nationaliteit'", "'indicatie.onder_curatele.verantwoordingVerval.datum_ontlening'", 
		"'geslachtsaanduiding.datum_tijd_registratie'", "'nummerverwijzing.volgende_administratienummer'", 
		"'bijhouding.verantwoordingAanpassingGeldigheid.partij'", "'verblijfsrecht.verantwoordingInhoud.soort'", 
		"'inschrijving'", "'geslachtsaanduiding.verantwoordingVerval.soort'", 
		"'uitsluiting_kiesrecht.verantwoordingVerval.soort'", "'persoonskaart.verantwoordingInhoud.soort'", 
		"'gemeente'", "'identificatienummers'", "'verblijfsrecht.datum_tijd_registratie'", 
		"'IS_OPGESCHORT'", "'abonnement'", "'indicatie.behandeld_als_nederlander.verantwoordingVerval.soort'", 
		"'bijhouding.verantwoordingAanpassingGeldigheid.soort'", "'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingVerval.tijdstip_registratie'", 
		"'rol'", "'geslachtsaanduiding.verantwoordingVerval.partij'", "'samengestelde_naam.predicaat'", 
		"'ouderschap.ouder'", "'persoonskaart.datum_tijd_verval'", "'naamgebruik.voorvoegsel'", 
		"'samengestelde_naam.namenreeks'", "'persoononderzoek.rol'", "'administratief'", 
		"'deelname_eu_verkiezingen.datum_tijd_registratie'", "'migratie.aangever'", 
		"'MAAND'", "'geslachtsaanduiding.datum_einde_geldigheid'", "'identificatienummers.anummer'", 
		"'geslachtsnaamcomponenten'", "'inschrijving.verantwoordingInhoud.partij'", 
		"'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.datum_tijd_verval'", 
		"'reden_verlies'", "'bijhouding.bijhoudingspartij'", "'indicatie.bijzondere_verblijfsrechtelijke_positie.datum_tijd_registratie'", 
		"'migratie.datum_tijd_registratie'", "'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.tijdstip_registratie'", 
		"'indicatie.vastgesteld_niet_nederlander.datum_tijd_registratie'", "'verantwoordingVerval.partij'", 
		"'identificatienummers.verantwoordingAanpassingGeldigheid.partij'", "'indicatie.staatloos.datum_tijd_registratie'", 
		"'naamgebruik.datum_tijd_registratie'", "'indicatie.staatloos.verantwoordingAanpassingGeldigheid.datum_ontlening'", 
		"'uitsluiting_kiesrecht.verantwoordingVerval.partij'", "'IS_NULL'", "'persoononderzoek.verantwoordingVerval.tijdstip_registratie'", 
		"'locatie_ten_opzichte_van_adres'", "'BETROKKENHEDEN'", "'indicatie.volledige_verstrekkingsbeperking.datum_tijd_registratie'", 
		"'verblijfsrecht.aanduiding'", "'ouderlijk_gezag.verantwoordingInhoud.datum_ontlening'", 
		"'indicatie.behandeld_als_nederlander.verantwoordingInhoud.soort'", "'indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingInhoud.datum_ontlening'", 
		"'autoriteit_van_afgifte'", "'overlijden.datum_tijd_registratie'", "'samengestelde_naam.verantwoordingInhoud.datum_ontlening'", 
		"'indicatie.onder_curatele.verantwoordingVerval.partij'", "'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.tijdstip_registratie'", 
		"'naamgebruik.verantwoordingInhoud.soort'", "'deelname_eu_verkiezingen'", 
		"'RMAP'", "'samengestelde_naam.datum_tijd_verval'", "'ouderlijk_gezag.datum_tijd_registratie'", 
		"'persoonskaart.verantwoordingVerval.partij'", "'persoononderzoek.verantwoordingVerval.soort'", 
		"STRING", "IDENTIFIER", "INTEGER", "WS", "'('", "')'", "'{'", "'}'", "','", 
		"'.'", "'?'", "'='", "'<>'", "'%='", "'<'", "'>'", "'<='", "'>='", "'IN'", 
		"'IN%'", "'+'", "'-'", "'OF'", "'EN'", "'NIET'", "'$'", "'WAARBIJ'", "TRUE_CONSTANT", 
		"FALSE_CONSTANT", "'NULL'", "MAAND_JAN", "MAAND_FEB", "MAAND_MRT", "MAAND_APR", 
		"MAAND_MEI", "MAAND_JUN", "MAAND_JUL", "MAAND_AUG", "MAAND_SEP", "MAAND_OKT", 
		"MAAND_NOV", "MAAND_DEC"
	};
	public static final int
		RULE_brp_expressie = 0, RULE_exp = 1, RULE_closure = 2, RULE_assignments = 3, 
		RULE_assignment = 4, RULE_booleanExp = 5, RULE_booleanTerm = 6, RULE_equalityExpression = 7, 
		RULE_equalityOp = 8, RULE_relationalExpression = 9, RULE_relationalOp = 10, 
		RULE_ordinalExpression = 11, RULE_ordinalOp = 12, RULE_negatableExpression = 13, 
		RULE_negationOperator = 14, RULE_unaryExpression = 15, RULE_bracketedExp = 16, 
		RULE_expressionList = 17, RULE_emptyList = 18, RULE_nonEmptyList = 19, 
		RULE_attribute = 20, RULE_attributeReference = 21, RULE_groep = 22, RULE_groepReference = 23, 
		RULE_objectIdentifier = 24, RULE_variable = 25, RULE_function = 26, RULE_functionName = 27, 
		RULE_existFunction = 28, RULE_existFunctionName = 29, RULE_literal = 30, 
		RULE_stringLiteral = 31, RULE_booleanLiteral = 32, RULE_numericLiteral = 33, 
		RULE_dateTimeLiteral = 34, RULE_dateLiteral = 35, RULE_year = 36, RULE_month = 37, 
		RULE_monthName = 38, RULE_day = 39, RULE_hour = 40, RULE_minute = 41, 
		RULE_second = 42, RULE_periodLiteral = 43, RULE_relativeYear = 44, RULE_relativeMonth = 45, 
		RULE_relativeDay = 46, RULE_periodPart = 47, RULE_attributeCodeLiteral = 48, 
		RULE_nullLiteral = 49, RULE_attribute_path = 50, RULE_groep_path = 51;
	public static final String[] ruleNames = {
		"brp_expressie", "exp", "closure", "assignments", "assignment", "booleanExp", 
		"booleanTerm", "equalityExpression", "equalityOp", "relationalExpression", 
		"relationalOp", "ordinalExpression", "ordinalOp", "negatableExpression", 
		"negationOperator", "unaryExpression", "bracketedExp", "expressionList", 
		"emptyList", "nonEmptyList", "attribute", "attributeReference", "groep", 
		"groepReference", "objectIdentifier", "variable", "function", "functionName", 
		"existFunction", "existFunctionName", "literal", "stringLiteral", "booleanLiteral", 
		"numericLiteral", "dateTimeLiteral", "dateLiteral", "year", "month", "monthName", 
		"day", "hour", "minute", "second", "periodLiteral", "relativeYear", "relativeMonth", 
		"relativeDay", "periodPart", "attributeCodeLiteral", "nullLiteral", "attribute_path", 
		"groep_path"
	};

	@Override
	public String getGrammarFileName() { return "BRPExpressietaal.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public BRPExpressietaalParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Brp_expressieContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public Brp_expressieContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_brp_expressie; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBrp_expressie(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Brp_expressieContext brp_expressie() throws RecognitionException {
		Brp_expressieContext _localctx = new Brp_expressieContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_brp_expressie);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104); exp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ClosureContext closure() {
			return getRuleContext(ClosureContext.class,0);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106); closure();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClosureContext extends ParserRuleContext {
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public AssignmentsContext assignments() {
			return getRuleContext(AssignmentsContext.class,0);
		}
		public ClosureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closure; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitClosure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClosureContext closure() throws RecognitionException {
		ClosureContext _localctx = new ClosureContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_closure);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108); booleanExp();
			setState(110);
			_la = _input.LA(1);
			if (_la==OP_WAARBIJ) {
				{
				setState(109); assignments();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentsContext extends ParserRuleContext {
		public AssignmentContext assignment(int i) {
			return getRuleContext(AssignmentContext.class,i);
		}
		public List<AssignmentContext> assignment() {
			return getRuleContexts(AssignmentContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(BRPExpressietaalParser.COMMA); }
		public TerminalNode OP_WAARBIJ() { return getToken(BRPExpressietaalParser.OP_WAARBIJ, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(BRPExpressietaalParser.COMMA, i);
		}
		public AssignmentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitAssignments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentsContext assignments() throws RecognitionException {
		AssignmentsContext _localctx = new AssignmentsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_assignments);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(112); match(OP_WAARBIJ);
			setState(113); assignment();
			setState(118);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(114); match(COMMA);
					setState(115); assignment();
					}
					} 
				}
				setState(120);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode OP_EQUAL() { return getToken(BRPExpressietaalParser.OP_EQUAL, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121); variable();
			setState(122); match(OP_EQUAL);
			setState(123); exp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpContext extends ParserRuleContext {
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public TerminalNode OP_OR() { return getToken(BRPExpressietaalParser.OP_OR, 0); }
		public BooleanTermContext booleanTerm() {
			return getRuleContext(BooleanTermContext.class,0);
		}
		public BooleanExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBooleanExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpContext booleanExp() throws RecognitionException {
		BooleanExpContext _localctx = new BooleanExpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_booleanExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125); booleanTerm();
			setState(128);
			_la = _input.LA(1);
			if (_la==OP_OR) {
				{
				setState(126); match(OP_OR);
				setState(127); booleanExp();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanTermContext extends ParserRuleContext {
		public TerminalNode OP_AND() { return getToken(BRPExpressietaalParser.OP_AND, 0); }
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public BooleanTermContext booleanTerm() {
			return getRuleContext(BooleanTermContext.class,0);
		}
		public BooleanTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanTerm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBooleanTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanTermContext booleanTerm() throws RecognitionException {
		BooleanTermContext _localctx = new BooleanTermContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_booleanTerm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130); equalityExpression();
			setState(133);
			_la = _input.LA(1);
			if (_la==OP_AND) {
				{
				setState(131); match(OP_AND);
				setState(132); booleanTerm();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityExpressionContext extends ParserRuleContext {
		public EqualityOpContext equalityOp() {
			return getRuleContext(EqualityOpContext.class,0);
		}
		public List<RelationalExpressionContext> relationalExpression() {
			return getRuleContexts(RelationalExpressionContext.class);
		}
		public RelationalExpressionContext relationalExpression(int i) {
			return getRuleContext(RelationalExpressionContext.class,i);
		}
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_equalityExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135); relationalExpression();
			setState(139);
			_la = _input.LA(1);
			if (((((_la - 595)) & ~0x3f) == 0 && ((1L << (_la - 595)) & ((1L << (OP_EQUAL - 595)) | (1L << (OP_NOT_EQUAL - 595)) | (1L << (OP_LIKE - 595)))) != 0)) {
				{
				setState(136); equalityOp();
				setState(137); relationalExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityOpContext extends ParserRuleContext {
		public TerminalNode OP_EQUAL() { return getToken(BRPExpressietaalParser.OP_EQUAL, 0); }
		public TerminalNode OP_NOT_EQUAL() { return getToken(BRPExpressietaalParser.OP_NOT_EQUAL, 0); }
		public TerminalNode OP_LIKE() { return getToken(BRPExpressietaalParser.OP_LIKE, 0); }
		public EqualityOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitEqualityOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityOpContext equalityOp() throws RecognitionException {
		EqualityOpContext _localctx = new EqualityOpContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_equalityOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_la = _input.LA(1);
			if ( !(((((_la - 595)) & ~0x3f) == 0 && ((1L << (_la - 595)) & ((1L << (OP_EQUAL - 595)) | (1L << (OP_NOT_EQUAL - 595)) | (1L << (OP_LIKE - 595)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationalExpressionContext extends ParserRuleContext {
		public OrdinalExpressionContext ordinalExpression(int i) {
			return getRuleContext(OrdinalExpressionContext.class,i);
		}
		public List<OrdinalExpressionContext> ordinalExpression() {
			return getRuleContexts(OrdinalExpressionContext.class);
		}
		public RelationalOpContext relationalOp() {
			return getRuleContext(RelationalOpContext.class,0);
		}
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelationalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_relationalExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143); ordinalExpression();
			setState(147);
			_la = _input.LA(1);
			if (((((_la - 598)) & ~0x3f) == 0 && ((1L << (_la - 598)) & ((1L << (OP_LESS - 598)) | (1L << (OP_GREATER - 598)) | (1L << (OP_LESS_EQUAL - 598)) | (1L << (OP_GREATER_EQUAL - 598)) | (1L << (OP_IN - 598)) | (1L << (OP_IN_WILDCARD - 598)))) != 0)) {
				{
				setState(144); relationalOp();
				setState(145); ordinalExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationalOpContext extends ParserRuleContext {
		public TerminalNode OP_LESS() { return getToken(BRPExpressietaalParser.OP_LESS, 0); }
		public TerminalNode OP_GREATER() { return getToken(BRPExpressietaalParser.OP_GREATER, 0); }
		public TerminalNode OP_IN() { return getToken(BRPExpressietaalParser.OP_IN, 0); }
		public TerminalNode OP_IN_WILDCARD() { return getToken(BRPExpressietaalParser.OP_IN_WILDCARD, 0); }
		public TerminalNode OP_LESS_EQUAL() { return getToken(BRPExpressietaalParser.OP_LESS_EQUAL, 0); }
		public TerminalNode OP_GREATER_EQUAL() { return getToken(BRPExpressietaalParser.OP_GREATER_EQUAL, 0); }
		public RelationalOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelationalOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalOpContext relationalOp() throws RecognitionException {
		RelationalOpContext _localctx = new RelationalOpContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_relationalOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			_la = _input.LA(1);
			if ( !(((((_la - 598)) & ~0x3f) == 0 && ((1L << (_la - 598)) & ((1L << (OP_LESS - 598)) | (1L << (OP_GREATER - 598)) | (1L << (OP_LESS_EQUAL - 598)) | (1L << (OP_GREATER_EQUAL - 598)) | (1L << (OP_IN - 598)) | (1L << (OP_IN_WILDCARD - 598)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrdinalExpressionContext extends ParserRuleContext {
		public OrdinalOpContext ordinalOp() {
			return getRuleContext(OrdinalOpContext.class,0);
		}
		public NegatableExpressionContext negatableExpression() {
			return getRuleContext(NegatableExpressionContext.class,0);
		}
		public OrdinalExpressionContext ordinalExpression() {
			return getRuleContext(OrdinalExpressionContext.class,0);
		}
		public OrdinalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ordinalExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitOrdinalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrdinalExpressionContext ordinalExpression() throws RecognitionException {
		OrdinalExpressionContext _localctx = new OrdinalExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ordinalExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151); negatableExpression();
			setState(155);
			_la = _input.LA(1);
			if (_la==OP_PLUS || _la==OP_MINUS) {
				{
				setState(152); ordinalOp();
				setState(153); ordinalExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrdinalOpContext extends ParserRuleContext {
		public TerminalNode OP_MINUS() { return getToken(BRPExpressietaalParser.OP_MINUS, 0); }
		public TerminalNode OP_PLUS() { return getToken(BRPExpressietaalParser.OP_PLUS, 0); }
		public OrdinalOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ordinalOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitOrdinalOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrdinalOpContext ordinalOp() throws RecognitionException {
		OrdinalOpContext _localctx = new OrdinalOpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ordinalOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			_la = _input.LA(1);
			if ( !(_la==OP_PLUS || _la==OP_MINUS) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NegatableExpressionContext extends ParserRuleContext {
		public NegationOperatorContext negationOperator() {
			return getRuleContext(NegationOperatorContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public NegatableExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negatableExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNegatableExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegatableExpressionContext negatableExpression() throws RecognitionException {
		NegatableExpressionContext _localctx = new NegatableExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_negatableExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(159); negationOperator();
				}
				break;
			}
			setState(162); unaryExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NegationOperatorContext extends ParserRuleContext {
		public TerminalNode OP_MINUS() { return getToken(BRPExpressietaalParser.OP_MINUS, 0); }
		public TerminalNode OP_NOT() { return getToken(BRPExpressietaalParser.OP_NOT, 0); }
		public NegationOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negationOperator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNegationOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegationOperatorContext negationOperator() throws RecognitionException {
		NegationOperatorContext _localctx = new NegationOperatorContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_negationOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			_la = _input.LA(1);
			if ( !(_la==OP_MINUS || _la==OP_NOT) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryExpressionContext extends ParserRuleContext {
		public GroepReferenceContext groepReference() {
			return getRuleContext(GroepReferenceContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public BracketedExpContext bracketedExp() {
			return getRuleContext(BracketedExpContext.class,0);
		}
		public AttributeReferenceContext attributeReference() {
			return getRuleContext(AttributeReferenceContext.class,0);
		}
		public ExistFunctionContext existFunction() {
			return getRuleContext(ExistFunctionContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_unaryExpression);
		try {
			setState(175);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(166); expressionList();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(167); function();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(168); existFunction();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(169); groepReference();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(170); attribute();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(171); attributeReference();
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(172); literal();
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(173); variable();
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(174); bracketedExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BracketedExpContext extends ParserRuleContext {
		public TerminalNode LP() { return getToken(BRPExpressietaalParser.LP, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RP() { return getToken(BRPExpressietaalParser.RP, 0); }
		public BracketedExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bracketedExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBracketedExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BracketedExpContext bracketedExp() throws RecognitionException {
		BracketedExpContext _localctx = new BracketedExpContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_bracketedExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177); match(LP);
			setState(178); exp();
			setState(179); match(RP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public EmptyListContext emptyList() {
			return getRuleContext(EmptyListContext.class,0);
		}
		public NonEmptyListContext nonEmptyList() {
			return getRuleContext(NonEmptyListContext.class,0);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_expressionList);
		try {
			setState(183);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(181); emptyList();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(182); nonEmptyList();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmptyListContext extends ParserRuleContext {
		public TerminalNode RL() { return getToken(BRPExpressietaalParser.RL, 0); }
		public TerminalNode LL() { return getToken(BRPExpressietaalParser.LL, 0); }
		public EmptyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitEmptyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyListContext emptyList() throws RecognitionException {
		EmptyListContext _localctx = new EmptyListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_emptyList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185); match(LL);
			setState(186); match(RL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonEmptyListContext extends ParserRuleContext {
		public TerminalNode RL() { return getToken(BRPExpressietaalParser.RL, 0); }
		public TerminalNode LL() { return getToken(BRPExpressietaalParser.LL, 0); }
		public List<TerminalNode> COMMA() { return getTokens(BRPExpressietaalParser.COMMA); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(BRPExpressietaalParser.COMMA, i);
		}
		public NonEmptyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonEmptyList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNonEmptyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonEmptyListContext nonEmptyList() throws RecognitionException {
		NonEmptyListContext _localctx = new NonEmptyListContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_nonEmptyList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188); match(LL);
			setState(189); exp();
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(190); match(COMMA);
				setState(191); exp();
				}
				}
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(197); match(RL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(BRPExpressietaalParser.DOT, 0); }
		public ObjectIdentifierContext objectIdentifier() {
			return getRuleContext(ObjectIdentifierContext.class,0);
		}
		public Attribute_pathContext attribute_path() {
			return getRuleContext(Attribute_pathContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_attribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(199); objectIdentifier();
				setState(200); match(DOT);
				}
			}

			setState(204); attribute_path();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeReferenceContext extends ParserRuleContext {
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public TerminalNode OP_REF() { return getToken(BRPExpressietaalParser.OP_REF, 0); }
		public AttributeReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeReference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitAttributeReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeReferenceContext attributeReference() throws RecognitionException {
		AttributeReferenceContext _localctx = new AttributeReferenceContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_attributeReference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206); match(OP_REF);
			setState(207); attribute();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroepContext extends ParserRuleContext {
		public Groep_pathContext groep_path() {
			return getRuleContext(Groep_pathContext.class,0);
		}
		public TerminalNode DOT() { return getToken(BRPExpressietaalParser.DOT, 0); }
		public ObjectIdentifierContext objectIdentifier() {
			return getRuleContext(ObjectIdentifierContext.class,0);
		}
		public GroepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groep; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitGroep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroepContext groep() throws RecognitionException {
		GroepContext _localctx = new GroepContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_groep);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(209); objectIdentifier();
				setState(210); match(DOT);
				}
			}

			setState(214); groep_path();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroepReferenceContext extends ParserRuleContext {
		public GroepContext groep() {
			return getRuleContext(GroepContext.class,0);
		}
		public TerminalNode OP_REF() { return getToken(BRPExpressietaalParser.OP_REF, 0); }
		public GroepReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groepReference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitGroepReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroepReferenceContext groepReference() throws RecognitionException {
		GroepReferenceContext _localctx = new GroepReferenceContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_groepReference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216); match(OP_REF);
			setState(217); groep();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectIdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(BRPExpressietaalParser.IDENTIFIER, 0); }
		public ObjectIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectIdentifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitObjectIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectIdentifierContext objectIdentifier() throws RecognitionException {
		ObjectIdentifierContext _localctx = new ObjectIdentifierContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_objectIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219); match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(BRPExpressietaalParser.IDENTIFIER, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221); match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode LP() { return getToken(BRPExpressietaalParser.LP, 0); }
		public List<TerminalNode> COMMA() { return getTokens(BRPExpressietaalParser.COMMA); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public TerminalNode RP() { return getToken(BRPExpressietaalParser.RP, 0); }
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public FunctionNameContext functionName() {
			return getRuleContext(FunctionNameContext.class,0);
		}
		public TerminalNode COMMA(int i) {
			return getToken(BRPExpressietaalParser.COMMA, i);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223); functionName();
			setState(224); match(LP);
			setState(233);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 2) | (1L << 3) | (1L << 4) | (1L << 5) | (1L << 6) | (1L << 7) | (1L << 8) | (1L << 9) | (1L << 10) | (1L << 11) | (1L << 12) | (1L << 13) | (1L << 14) | (1L << 15) | (1L << 16) | (1L << 17) | (1L << 18) | (1L << 19) | (1L << 20) | (1L << 21) | (1L << 22) | (1L << 23) | (1L << 24) | (1L << 25) | (1L << 26) | (1L << 27) | (1L << 29) | (1L << 30) | (1L << 31) | (1L << 32) | (1L << 33) | (1L << 34) | (1L << 35) | (1L << 36) | (1L << 37) | (1L << 38) | (1L << 39) | (1L << 40) | (1L << 41) | (1L << 42) | (1L << 43) | (1L << 44) | (1L << 45) | (1L << 46) | (1L << 47) | (1L << 48) | (1L << 49) | (1L << 50) | (1L << 51) | (1L << 52) | (1L << 53) | (1L << 54) | (1L << 55) | (1L << 56) | (1L << 57) | (1L << 59) | (1L << 60) | (1L << 61) | (1L << 62) | (1L << 63))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (64 - 64)) | (1L << (65 - 64)) | (1L << (66 - 64)) | (1L << (67 - 64)) | (1L << (68 - 64)) | (1L << (70 - 64)) | (1L << (71 - 64)) | (1L << (72 - 64)) | (1L << (73 - 64)) | (1L << (74 - 64)) | (1L << (75 - 64)) | (1L << (76 - 64)) | (1L << (77 - 64)) | (1L << (78 - 64)) | (1L << (81 - 64)) | (1L << (82 - 64)) | (1L << (83 - 64)) | (1L << (84 - 64)) | (1L << (85 - 64)) | (1L << (86 - 64)) | (1L << (88 - 64)) | (1L << (89 - 64)) | (1L << (90 - 64)) | (1L << (91 - 64)) | (1L << (92 - 64)) | (1L << (93 - 64)) | (1L << (95 - 64)) | (1L << (96 - 64)) | (1L << (97 - 64)) | (1L << (98 - 64)) | (1L << (99 - 64)) | (1L << (100 - 64)) | (1L << (101 - 64)) | (1L << (102 - 64)) | (1L << (103 - 64)) | (1L << (104 - 64)) | (1L << (105 - 64)) | (1L << (106 - 64)) | (1L << (107 - 64)) | (1L << (108 - 64)) | (1L << (109 - 64)) | (1L << (110 - 64)) | (1L << (111 - 64)) | (1L << (112 - 64)) | (1L << (113 - 64)) | (1L << (114 - 64)) | (1L << (115 - 64)) | (1L << (116 - 64)) | (1L << (118 - 64)) | (1L << (119 - 64)) | (1L << (120 - 64)) | (1L << (121 - 64)) | (1L << (122 - 64)) | (1L << (123 - 64)) | (1L << (124 - 64)) | (1L << (125 - 64)) | (1L << (126 - 64)) | (1L << (127 - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (128 - 128)) | (1L << (129 - 128)) | (1L << (130 - 128)) | (1L << (131 - 128)) | (1L << (132 - 128)) | (1L << (133 - 128)) | (1L << (134 - 128)) | (1L << (135 - 128)) | (1L << (136 - 128)) | (1L << (137 - 128)) | (1L << (138 - 128)) | (1L << (139 - 128)) | (1L << (140 - 128)) | (1L << (141 - 128)) | (1L << (142 - 128)) | (1L << (143 - 128)) | (1L << (144 - 128)) | (1L << (145 - 128)) | (1L << (146 - 128)) | (1L << (147 - 128)) | (1L << (148 - 128)) | (1L << (149 - 128)) | (1L << (151 - 128)) | (1L << (152 - 128)) | (1L << (153 - 128)) | (1L << (154 - 128)) | (1L << (155 - 128)) | (1L << (156 - 128)) | (1L << (158 - 128)) | (1L << (159 - 128)) | (1L << (160 - 128)) | (1L << (161 - 128)) | (1L << (162 - 128)) | (1L << (163 - 128)) | (1L << (164 - 128)) | (1L << (165 - 128)) | (1L << (167 - 128)) | (1L << (168 - 128)) | (1L << (169 - 128)) | (1L << (170 - 128)) | (1L << (171 - 128)) | (1L << (172 - 128)) | (1L << (173 - 128)) | (1L << (174 - 128)) | (1L << (175 - 128)) | (1L << (176 - 128)) | (1L << (177 - 128)) | (1L << (178 - 128)) | (1L << (179 - 128)) | (1L << (180 - 128)) | (1L << (181 - 128)) | (1L << (182 - 128)) | (1L << (183 - 128)) | (1L << (184 - 128)) | (1L << (185 - 128)) | (1L << (186 - 128)) | (1L << (187 - 128)) | (1L << (188 - 128)) | (1L << (189 - 128)) | (1L << (190 - 128)) | (1L << (191 - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (192 - 192)) | (1L << (193 - 192)) | (1L << (194 - 192)) | (1L << (195 - 192)) | (1L << (196 - 192)) | (1L << (197 - 192)) | (1L << (198 - 192)) | (1L << (199 - 192)) | (1L << (200 - 192)) | (1L << (201 - 192)) | (1L << (202 - 192)) | (1L << (203 - 192)) | (1L << (204 - 192)) | (1L << (205 - 192)) | (1L << (206 - 192)) | (1L << (207 - 192)) | (1L << (208 - 192)) | (1L << (209 - 192)) | (1L << (210 - 192)) | (1L << (211 - 192)) | (1L << (212 - 192)) | (1L << (213 - 192)) | (1L << (214 - 192)) | (1L << (215 - 192)) | (1L << (216 - 192)) | (1L << (217 - 192)) | (1L << (218 - 192)) | (1L << (219 - 192)) | (1L << (220 - 192)) | (1L << (221 - 192)) | (1L << (222 - 192)) | (1L << (223 - 192)) | (1L << (224 - 192)) | (1L << (225 - 192)) | (1L << (226 - 192)) | (1L << (227 - 192)) | (1L << (228 - 192)) | (1L << (229 - 192)) | (1L << (230 - 192)) | (1L << (231 - 192)) | (1L << (232 - 192)) | (1L << (233 - 192)) | (1L << (234 - 192)) | (1L << (235 - 192)) | (1L << (236 - 192)) | (1L << (237 - 192)) | (1L << (238 - 192)) | (1L << (239 - 192)) | (1L << (240 - 192)) | (1L << (241 - 192)) | (1L << (242 - 192)) | (1L << (243 - 192)) | (1L << (244 - 192)) | (1L << (245 - 192)) | (1L << (246 - 192)) | (1L << (247 - 192)) | (1L << (248 - 192)) | (1L << (249 - 192)) | (1L << (250 - 192)) | (1L << (251 - 192)) | (1L << (252 - 192)) | (1L << (253 - 192)) | (1L << (254 - 192)) | (1L << (255 - 192)))) != 0) || ((((_la - 256)) & ~0x3f) == 0 && ((1L << (_la - 256)) & ((1L << (256 - 256)) | (1L << (257 - 256)) | (1L << (258 - 256)) | (1L << (259 - 256)) | (1L << (260 - 256)) | (1L << (261 - 256)) | (1L << (262 - 256)) | (1L << (263 - 256)) | (1L << (265 - 256)) | (1L << (266 - 256)) | (1L << (267 - 256)) | (1L << (268 - 256)) | (1L << (269 - 256)) | (1L << (270 - 256)) | (1L << (271 - 256)) | (1L << (272 - 256)) | (1L << (273 - 256)) | (1L << (274 - 256)) | (1L << (275 - 256)) | (1L << (276 - 256)) | (1L << (278 - 256)) | (1L << (279 - 256)) | (1L << (280 - 256)) | (1L << (281 - 256)) | (1L << (282 - 256)) | (1L << (283 - 256)) | (1L << (284 - 256)) | (1L << (285 - 256)) | (1L << (286 - 256)) | (1L << (287 - 256)) | (1L << (288 - 256)) | (1L << (289 - 256)) | (1L << (290 - 256)) | (1L << (291 - 256)) | (1L << (292 - 256)) | (1L << (293 - 256)) | (1L << (294 - 256)) | (1L << (295 - 256)) | (1L << (296 - 256)) | (1L << (297 - 256)) | (1L << (298 - 256)) | (1L << (299 - 256)) | (1L << (300 - 256)) | (1L << (301 - 256)) | (1L << (302 - 256)) | (1L << (303 - 256)) | (1L << (304 - 256)) | (1L << (305 - 256)) | (1L << (306 - 256)) | (1L << (307 - 256)) | (1L << (308 - 256)) | (1L << (309 - 256)) | (1L << (310 - 256)) | (1L << (311 - 256)) | (1L << (312 - 256)) | (1L << (313 - 256)) | (1L << (314 - 256)) | (1L << (315 - 256)) | (1L << (316 - 256)) | (1L << (317 - 256)) | (1L << (318 - 256)) | (1L << (319 - 256)))) != 0) || ((((_la - 320)) & ~0x3f) == 0 && ((1L << (_la - 320)) & ((1L << (320 - 320)) | (1L << (321 - 320)) | (1L << (322 - 320)) | (1L << (323 - 320)) | (1L << (324 - 320)) | (1L << (325 - 320)) | (1L << (326 - 320)) | (1L << (327 - 320)) | (1L << (328 - 320)) | (1L << (329 - 320)) | (1L << (330 - 320)) | (1L << (331 - 320)) | (1L << (332 - 320)) | (1L << (333 - 320)) | (1L << (334 - 320)) | (1L << (335 - 320)) | (1L << (336 - 320)) | (1L << (337 - 320)) | (1L << (338 - 320)) | (1L << (339 - 320)) | (1L << (340 - 320)) | (1L << (341 - 320)) | (1L << (342 - 320)) | (1L << (343 - 320)) | (1L << (344 - 320)) | (1L << (345 - 320)) | (1L << (346 - 320)) | (1L << (347 - 320)) | (1L << (348 - 320)) | (1L << (349 - 320)) | (1L << (350 - 320)) | (1L << (351 - 320)) | (1L << (352 - 320)) | (1L << (353 - 320)) | (1L << (354 - 320)) | (1L << (355 - 320)) | (1L << (356 - 320)) | (1L << (357 - 320)) | (1L << (358 - 320)) | (1L << (359 - 320)) | (1L << (360 - 320)) | (1L << (361 - 320)) | (1L << (362 - 320)) | (1L << (363 - 320)) | (1L << (364 - 320)) | (1L << (365 - 320)) | (1L << (366 - 320)) | (1L << (367 - 320)) | (1L << (368 - 320)) | (1L << (369 - 320)) | (1L << (370 - 320)) | (1L << (371 - 320)) | (1L << (372 - 320)) | (1L << (373 - 320)) | (1L << (374 - 320)) | (1L << (375 - 320)) | (1L << (376 - 320)) | (1L << (377 - 320)) | (1L << (378 - 320)) | (1L << (379 - 320)) | (1L << (380 - 320)) | (1L << (381 - 320)) | (1L << (382 - 320)) | (1L << (383 - 320)))) != 0) || ((((_la - 384)) & ~0x3f) == 0 && ((1L << (_la - 384)) & ((1L << (384 - 384)) | (1L << (385 - 384)) | (1L << (386 - 384)) | (1L << (387 - 384)) | (1L << (388 - 384)) | (1L << (389 - 384)) | (1L << (390 - 384)) | (1L << (391 - 384)) | (1L << (392 - 384)) | (1L << (393 - 384)) | (1L << (394 - 384)) | (1L << (395 - 384)) | (1L << (396 - 384)) | (1L << (397 - 384)) | (1L << (398 - 384)) | (1L << (400 - 384)) | (1L << (402 - 384)) | (1L << (403 - 384)) | (1L << (404 - 384)) | (1L << (405 - 384)) | (1L << (406 - 384)) | (1L << (407 - 384)) | (1L << (408 - 384)) | (1L << (409 - 384)) | (1L << (410 - 384)) | (1L << (411 - 384)) | (1L << (412 - 384)) | (1L << (413 - 384)) | (1L << (414 - 384)) | (1L << (415 - 384)) | (1L << (416 - 384)) | (1L << (418 - 384)) | (1L << (419 - 384)) | (1L << (420 - 384)) | (1L << (422 - 384)) | (1L << (423 - 384)) | (1L << (424 - 384)) | (1L << (425 - 384)) | (1L << (426 - 384)) | (1L << (427 - 384)) | (1L << (428 - 384)) | (1L << (429 - 384)) | (1L << (430 - 384)) | (1L << (431 - 384)) | (1L << (432 - 384)) | (1L << (433 - 384)) | (1L << (434 - 384)) | (1L << (435 - 384)) | (1L << (436 - 384)) | (1L << (437 - 384)) | (1L << (438 - 384)) | (1L << (440 - 384)) | (1L << (441 - 384)) | (1L << (442 - 384)) | (1L << (443 - 384)) | (1L << (444 - 384)) | (1L << (445 - 384)) | (1L << (446 - 384)) | (1L << (447 - 384)))) != 0) || ((((_la - 448)) & ~0x3f) == 0 && ((1L << (_la - 448)) & ((1L << (448 - 448)) | (1L << (449 - 448)) | (1L << (450 - 448)) | (1L << (451 - 448)) | (1L << (452 - 448)) | (1L << (453 - 448)) | (1L << (454 - 448)) | (1L << (455 - 448)) | (1L << (456 - 448)) | (1L << (457 - 448)) | (1L << (458 - 448)) | (1L << (459 - 448)) | (1L << (460 - 448)) | (1L << (461 - 448)) | (1L << (462 - 448)) | (1L << (463 - 448)) | (1L << (464 - 448)) | (1L << (465 - 448)) | (1L << (466 - 448)) | (1L << (467 - 448)) | (1L << (468 - 448)) | (1L << (469 - 448)) | (1L << (470 - 448)) | (1L << (471 - 448)) | (1L << (472 - 448)) | (1L << (473 - 448)) | (1L << (474 - 448)) | (1L << (475 - 448)) | (1L << (476 - 448)) | (1L << (477 - 448)) | (1L << (478 - 448)) | (1L << (479 - 448)) | (1L << (480 - 448)) | (1L << (481 - 448)) | (1L << (482 - 448)) | (1L << (483 - 448)) | (1L << (484 - 448)) | (1L << (485 - 448)) | (1L << (486 - 448)) | (1L << (487 - 448)) | (1L << (488 - 448)) | (1L << (489 - 448)) | (1L << (490 - 448)) | (1L << (492 - 448)) | (1L << (493 - 448)) | (1L << (494 - 448)) | (1L << (495 - 448)) | (1L << (496 - 448)) | (1L << (497 - 448)) | (1L << (498 - 448)) | (1L << (499 - 448)) | (1L << (500 - 448)) | (1L << (501 - 448)) | (1L << (502 - 448)) | (1L << (503 - 448)) | (1L << (504 - 448)) | (1L << (505 - 448)) | (1L << (506 - 448)) | (1L << (507 - 448)) | (1L << (508 - 448)) | (1L << (509 - 448)) | (1L << (510 - 448)) | (1L << (511 - 448)))) != 0) || ((((_la - 513)) & ~0x3f) == 0 && ((1L << (_la - 513)) & ((1L << (513 - 513)) | (1L << (514 - 513)) | (1L << (515 - 513)) | (1L << (516 - 513)) | (1L << (517 - 513)) | (1L << (518 - 513)) | (1L << (519 - 513)) | (1L << (520 - 513)) | (1L << (521 - 513)) | (1L << (523 - 513)) | (1L << (524 - 513)) | (1L << (525 - 513)) | (1L << (526 - 513)) | (1L << (528 - 513)) | (1L << (529 - 513)) | (1L << (530 - 513)) | (1L << (531 - 513)) | (1L << (532 - 513)) | (1L << (533 - 513)) | (1L << (534 - 513)) | (1L << (535 - 513)) | (1L << (536 - 513)) | (1L << (537 - 513)) | (1L << (538 - 513)) | (1L << (539 - 513)) | (1L << (540 - 513)) | (1L << (541 - 513)) | (1L << (543 - 513)) | (1L << (544 - 513)) | (1L << (545 - 513)) | (1L << (546 - 513)) | (1L << (547 - 513)) | (1L << (548 - 513)) | (1L << (549 - 513)) | (1L << (550 - 513)) | (1L << (551 - 513)) | (1L << (552 - 513)) | (1L << (553 - 513)) | (1L << (554 - 513)) | (1L << (555 - 513)) | (1L << (556 - 513)) | (1L << (557 - 513)) | (1L << (558 - 513)) | (1L << (559 - 513)) | (1L << (560 - 513)) | (1L << (561 - 513)) | (1L << (562 - 513)) | (1L << (563 - 513)) | (1L << (564 - 513)) | (1L << (565 - 513)) | (1L << (566 - 513)) | (1L << (567 - 513)) | (1L << (568 - 513)) | (1L << (569 - 513)) | (1L << (570 - 513)) | (1L << (571 - 513)) | (1L << (572 - 513)) | (1L << (573 - 513)) | (1L << (574 - 513)) | (1L << (575 - 513)) | (1L << (576 - 513)))) != 0) || ((((_la - 577)) & ~0x3f) == 0 && ((1L << (_la - 577)) & ((1L << (577 - 577)) | (1L << (579 - 577)) | (1L << (580 - 577)) | (1L << (581 - 577)) | (1L << (582 - 577)) | (1L << (583 - 577)) | (1L << (STRING - 577)) | (1L << (IDENTIFIER - 577)) | (1L << (INTEGER - 577)) | (1L << (LP - 577)) | (1L << (LL - 577)) | (1L << (UNKNOWN_VALUE - 577)) | (1L << (OP_MINUS - 577)) | (1L << (OP_NOT - 577)) | (1L << (OP_REF - 577)) | (1L << (TRUE_CONSTANT - 577)) | (1L << (FALSE_CONSTANT - 577)) | (1L << (NULL_CONSTANT - 577)))) != 0)) {
				{
				setState(225); exp();
				setState(230);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(226); match(COMMA);
					setState(227); exp();
					}
					}
					setState(232);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(235); match(RP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionNameContext extends ParserRuleContext {
		public FunctionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitFunctionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionNameContext functionName() throws RecognitionException {
		FunctionNameContext _localctx = new FunctionNameContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_functionName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 10) | (1L << 35) | (1L << 48) | (1L << 56))) != 0) || ((((_la - 92)) & ~0x3f) == 0 && ((1L << (_la - 92)) & ((1L << (92 - 92)) | (1L << (96 - 92)) | (1L << (100 - 92)) | (1L << (130 - 92)) | (1L << (149 - 92)) | (1L << (154 - 92)))) != 0) || _la==183 || _la==243 || ((((_la - 251)) & ~0x3f) == 0 && ((1L << (_la - 251)) & ((1L << (251 - 251)) | (1L << (263 - 251)) | (1L << (271 - 251)) | (1L << (291 - 251)) | (1L << (301 - 251)) | (1L << (312 - 251)))) != 0) || ((((_la - 328)) & ~0x3f) == 0 && ((1L << (_la - 328)) & ((1L << (328 - 328)) | (1L << (349 - 328)) | (1L << (367 - 328)) | (1L << (386 - 328)))) != 0) || _la==426 || _la==474 || ((((_la - 503)) & ~0x3f) == 0 && ((1L << (_la - 503)) & ((1L << (503 - 503)) | (1L << (529 - 503)) | (1L << (545 - 503)) | (1L << (563 - 503)) | (1L << (566 - 503)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExistFunctionContext extends ParserRuleContext {
		public TerminalNode LP() { return getToken(BRPExpressietaalParser.LP, 0); }
		public ExistFunctionNameContext existFunctionName() {
			return getRuleContext(ExistFunctionNameContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(BRPExpressietaalParser.COMMA); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode RP() { return getToken(BRPExpressietaalParser.RP, 0); }
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(BRPExpressietaalParser.COMMA, i);
		}
		public ExistFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existFunction; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitExistFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExistFunctionContext existFunction() throws RecognitionException {
		ExistFunctionContext _localctx = new ExistFunctionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_existFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239); existFunctionName();
			setState(240); match(LP);
			setState(241); exp();
			setState(242); match(COMMA);
			setState(243); variable();
			setState(244); match(COMMA);
			setState(245); exp();
			setState(246); match(RP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExistFunctionNameContext extends ParserRuleContext {
		public ExistFunctionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existFunctionName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitExistFunctionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExistFunctionNameContext existFunctionName() throws RecognitionException {
		ExistFunctionNameContext _localctx = new ExistFunctionNameContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_existFunctionName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			_la = _input.LA(1);
			if ( !(_la==55 || _la==352 || _la==360 || _la==445 || _la==579) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public DateLiteralContext dateLiteral() {
			return getRuleContext(DateLiteralContext.class,0);
		}
		public AttributeCodeLiteralContext attributeCodeLiteral() {
			return getRuleContext(AttributeCodeLiteralContext.class,0);
		}
		public StringLiteralContext stringLiteral() {
			return getRuleContext(StringLiteralContext.class,0);
		}
		public NullLiteralContext nullLiteral() {
			return getRuleContext(NullLiteralContext.class,0);
		}
		public DateTimeLiteralContext dateTimeLiteral() {
			return getRuleContext(DateTimeLiteralContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public PeriodLiteralContext periodLiteral() {
			return getRuleContext(PeriodLiteralContext.class,0);
		}
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_literal);
		try {
			setState(258);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(250); stringLiteral();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(251); booleanLiteral();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(252); numericLiteral();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(253); dateLiteral();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(254); dateTimeLiteral();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(255); periodLiteral();
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(256); attributeCodeLiteral();
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(257); nullLiteral();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringLiteralContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(BRPExpressietaalParser.STRING, 0); }
		public StringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringLiteralContext stringLiteral() throws RecognitionException {
		StringLiteralContext _localctx = new StringLiteralContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_stringLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260); match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanLiteralContext extends ParserRuleContext {
		public TerminalNode FALSE_CONSTANT() { return getToken(BRPExpressietaalParser.FALSE_CONSTANT, 0); }
		public TerminalNode TRUE_CONSTANT() { return getToken(BRPExpressietaalParser.TRUE_CONSTANT, 0); }
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			_la = _input.LA(1);
			if ( !(_la==TRUE_CONSTANT || _la==FALSE_CONSTANT) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericLiteralContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(BRPExpressietaalParser.INTEGER, 0); }
		public NumericLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralContext numericLiteral() throws RecognitionException {
		NumericLiteralContext _localctx = new NumericLiteralContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_numericLiteral);
		try {
			setState(267);
			switch (_input.LA(1)) {
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(264); match(INTEGER);
				}
				break;
			case OP_MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(265); match(OP_MINUS);
				setState(266); match(INTEGER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DateTimeLiteralContext extends ParserRuleContext {
		public DayContext day() {
			return getRuleContext(DayContext.class,0);
		}
		public SecondContext second() {
			return getRuleContext(SecondContext.class,0);
		}
		public HourContext hour() {
			return getRuleContext(HourContext.class,0);
		}
		public MinuteContext minute() {
			return getRuleContext(MinuteContext.class,0);
		}
		public YearContext year() {
			return getRuleContext(YearContext.class,0);
		}
		public MonthContext month() {
			return getRuleContext(MonthContext.class,0);
		}
		public DateTimeLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dateTimeLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitDateTimeLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateTimeLiteralContext dateTimeLiteral() throws RecognitionException {
		DateTimeLiteralContext _localctx = new DateTimeLiteralContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_dateTimeLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269); year();
			setState(270); match(491);
			setState(271); month();
			setState(272); match(491);
			setState(273); day();
			setState(274); match(491);
			setState(275); hour();
			setState(276); match(491);
			setState(277); minute();
			setState(278); match(491);
			setState(279); second();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DateLiteralContext extends ParserRuleContext {
		public TerminalNode UNKNOWN_VALUE(int i) {
			return getToken(BRPExpressietaalParser.UNKNOWN_VALUE, i);
		}
		public List<TerminalNode> UNKNOWN_VALUE() { return getTokens(BRPExpressietaalParser.UNKNOWN_VALUE); }
		public DayContext day() {
			return getRuleContext(DayContext.class,0);
		}
		public YearContext year() {
			return getRuleContext(YearContext.class,0);
		}
		public MonthContext month() {
			return getRuleContext(MonthContext.class,0);
		}
		public DateLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dateLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitDateLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateLiteralContext dateLiteral() throws RecognitionException {
		DateLiteralContext _localctx = new DateLiteralContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_dateLiteral);
		try {
			setState(304);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(281); year();
				setState(282); match(491);
				setState(283); month();
				setState(284); match(491);
				setState(285); day();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(287); year();
				setState(288); match(491);
				setState(289); month();
				setState(290); match(491);
				setState(291); match(UNKNOWN_VALUE);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(293); year();
				setState(294); match(491);
				setState(295); match(UNKNOWN_VALUE);
				setState(296); match(491);
				setState(297); match(UNKNOWN_VALUE);
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(299); match(UNKNOWN_VALUE);
				setState(300); match(491);
				setState(301); match(UNKNOWN_VALUE);
				setState(302); match(491);
				setState(303); match(UNKNOWN_VALUE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YearContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public YearContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_year; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitYear(this);
			else return visitor.visitChildren(this);
		}
	}

	public final YearContext year() throws RecognitionException {
		YearContext _localctx = new YearContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_year);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306); numericLiteral();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MonthContext extends ParserRuleContext {
		public MonthNameContext monthName() {
			return getRuleContext(MonthNameContext.class,0);
		}
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public MonthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_month; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitMonth(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MonthContext month() throws RecognitionException {
		MonthContext _localctx = new MonthContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_month);
		try {
			setState(310);
			switch (_input.LA(1)) {
			case INTEGER:
			case OP_MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(308); numericLiteral();
				}
				break;
			case MAAND_JAN:
			case MAAND_FEB:
			case MAAND_MRT:
			case MAAND_APR:
			case MAAND_MEI:
			case MAAND_JUN:
			case MAAND_JUL:
			case MAAND_AUG:
			case MAAND_SEP:
			case MAAND_OKT:
			case MAAND_NOV:
			case MAAND_DEC:
				enterOuterAlt(_localctx, 2);
				{
				setState(309); monthName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MonthNameContext extends ParserRuleContext {
		public TerminalNode MAAND_JUL() { return getToken(BRPExpressietaalParser.MAAND_JUL, 0); }
		public TerminalNode MAAND_SEP() { return getToken(BRPExpressietaalParser.MAAND_SEP, 0); }
		public TerminalNode MAAND_JUN() { return getToken(BRPExpressietaalParser.MAAND_JUN, 0); }
		public TerminalNode MAAND_FEB() { return getToken(BRPExpressietaalParser.MAAND_FEB, 0); }
		public TerminalNode MAAND_APR() { return getToken(BRPExpressietaalParser.MAAND_APR, 0); }
		public TerminalNode MAAND_JAN() { return getToken(BRPExpressietaalParser.MAAND_JAN, 0); }
		public TerminalNode MAAND_DEC() { return getToken(BRPExpressietaalParser.MAAND_DEC, 0); }
		public TerminalNode MAAND_AUG() { return getToken(BRPExpressietaalParser.MAAND_AUG, 0); }
		public TerminalNode MAAND_NOV() { return getToken(BRPExpressietaalParser.MAAND_NOV, 0); }
		public TerminalNode MAAND_OKT() { return getToken(BRPExpressietaalParser.MAAND_OKT, 0); }
		public TerminalNode MAAND_MEI() { return getToken(BRPExpressietaalParser.MAAND_MEI, 0); }
		public TerminalNode MAAND_MRT() { return getToken(BRPExpressietaalParser.MAAND_MRT, 0); }
		public MonthNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_monthName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitMonthName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MonthNameContext monthName() throws RecognitionException {
		MonthNameContext _localctx = new MonthNameContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_monthName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			_la = _input.LA(1);
			if ( !(((((_la - 614)) & ~0x3f) == 0 && ((1L << (_la - 614)) & ((1L << (MAAND_JAN - 614)) | (1L << (MAAND_FEB - 614)) | (1L << (MAAND_MRT - 614)) | (1L << (MAAND_APR - 614)) | (1L << (MAAND_MEI - 614)) | (1L << (MAAND_JUN - 614)) | (1L << (MAAND_JUL - 614)) | (1L << (MAAND_AUG - 614)) | (1L << (MAAND_SEP - 614)) | (1L << (MAAND_OKT - 614)) | (1L << (MAAND_NOV - 614)) | (1L << (MAAND_DEC - 614)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DayContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public DayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_day; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitDay(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DayContext day() throws RecognitionException {
		DayContext _localctx = new DayContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_day);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314); numericLiteral();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HourContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public HourContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hour; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitHour(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HourContext hour() throws RecognitionException {
		HourContext _localctx = new HourContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_hour);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316); numericLiteral();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MinuteContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public MinuteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_minute; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitMinute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MinuteContext minute() throws RecognitionException {
		MinuteContext _localctx = new MinuteContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_minute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318); numericLiteral();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SecondContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public SecondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_second; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitSecond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SecondContext second() throws RecognitionException {
		SecondContext _localctx = new SecondContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_second);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320); numericLiteral();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PeriodLiteralContext extends ParserRuleContext {
		public RelativeMonthContext relativeMonth() {
			return getRuleContext(RelativeMonthContext.class,0);
		}
		public RelativeDayContext relativeDay() {
			return getRuleContext(RelativeDayContext.class,0);
		}
		public RelativeYearContext relativeYear() {
			return getRuleContext(RelativeYearContext.class,0);
		}
		public PeriodLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitPeriodLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodLiteralContext periodLiteral() throws RecognitionException {
		PeriodLiteralContext _localctx = new PeriodLiteralContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_periodLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322); match(412);
			setState(323); relativeYear();
			setState(330);
			_la = _input.LA(1);
			if (_la==491) {
				{
				setState(324); match(491);
				setState(325); relativeMonth();
				setState(328);
				_la = _input.LA(1);
				if (_la==491) {
					{
					setState(326); match(491);
					setState(327); relativeDay();
					}
				}

				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelativeYearContext extends ParserRuleContext {
		public PeriodPartContext periodPart() {
			return getRuleContext(PeriodPartContext.class,0);
		}
		public RelativeYearContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relativeYear; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelativeYear(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelativeYearContext relativeYear() throws RecognitionException {
		RelativeYearContext _localctx = new RelativeYearContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_relativeYear);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332); periodPart();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelativeMonthContext extends ParserRuleContext {
		public PeriodPartContext periodPart() {
			return getRuleContext(PeriodPartContext.class,0);
		}
		public RelativeMonthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relativeMonth; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelativeMonth(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelativeMonthContext relativeMonth() throws RecognitionException {
		RelativeMonthContext _localctx = new RelativeMonthContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_relativeMonth);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334); periodPart();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelativeDayContext extends ParserRuleContext {
		public PeriodPartContext periodPart() {
			return getRuleContext(PeriodPartContext.class,0);
		}
		public RelativeDayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relativeDay; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelativeDay(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelativeDayContext relativeDay() throws RecognitionException {
		RelativeDayContext _localctx = new RelativeDayContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_relativeDay);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336); periodPart();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PeriodPartContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public PeriodPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitPeriodPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodPartContext periodPart() throws RecognitionException {
		PeriodPartContext _localctx = new PeriodPartContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_periodPart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(338); match(OP_MINUS);
				}
				break;

			case 2:
				{
				}
				break;
			}
			setState(342); numericLiteral();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeCodeLiteralContext extends ParserRuleContext {
		public Attribute_pathContext attribute_path() {
			return getRuleContext(Attribute_pathContext.class,0);
		}
		public AttributeCodeLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeCodeLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitAttributeCodeLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeCodeLiteralContext attributeCodeLiteral() throws RecognitionException {
		AttributeCodeLiteralContext _localctx = new AttributeCodeLiteralContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_attributeCodeLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344); match(358);
			setState(345); attribute_path();
			setState(346); match(399);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NullLiteralContext extends ParserRuleContext {
		public TerminalNode NULL_CONSTANT() { return getToken(BRPExpressietaalParser.NULL_CONSTANT, 0); }
		public NullLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nullLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NullLiteralContext nullLiteral() throws RecognitionException {
		NullLiteralContext _localctx = new NullLiteralContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_nullLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(348); match(NULL_CONSTANT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Attribute_pathContext extends ParserRuleContext {
		public Attribute_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute_path; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitAttribute_path(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Attribute_pathContext attribute_path() throws RecognitionException {
		Attribute_pathContext _localctx = new Attribute_pathContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_attribute_path);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(350);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 2) | (1L << 3) | (1L << 4) | (1L << 5) | (1L << 6) | (1L << 7) | (1L << 8) | (1L << 9) | (1L << 11) | (1L << 12) | (1L << 13) | (1L << 14) | (1L << 15) | (1L << 16) | (1L << 17) | (1L << 18) | (1L << 19) | (1L << 20) | (1L << 21) | (1L << 22) | (1L << 23) | (1L << 24) | (1L << 25) | (1L << 26) | (1L << 27) | (1L << 29) | (1L << 30) | (1L << 31) | (1L << 32) | (1L << 33) | (1L << 34) | (1L << 36) | (1L << 37) | (1L << 38) | (1L << 39) | (1L << 40) | (1L << 41) | (1L << 42) | (1L << 43) | (1L << 44) | (1L << 45) | (1L << 46) | (1L << 47) | (1L << 49) | (1L << 50) | (1L << 51) | (1L << 52) | (1L << 53) | (1L << 54) | (1L << 57) | (1L << 59) | (1L << 60) | (1L << 61) | (1L << 62) | (1L << 63))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (64 - 64)) | (1L << (65 - 64)) | (1L << (66 - 64)) | (1L << (67 - 64)) | (1L << (68 - 64)) | (1L << (70 - 64)) | (1L << (71 - 64)) | (1L << (72 - 64)) | (1L << (73 - 64)) | (1L << (74 - 64)) | (1L << (75 - 64)) | (1L << (76 - 64)) | (1L << (77 - 64)) | (1L << (78 - 64)) | (1L << (81 - 64)) | (1L << (82 - 64)) | (1L << (83 - 64)) | (1L << (84 - 64)) | (1L << (85 - 64)) | (1L << (86 - 64)) | (1L << (88 - 64)) | (1L << (89 - 64)) | (1L << (90 - 64)) | (1L << (91 - 64)) | (1L << (93 - 64)) | (1L << (95 - 64)) | (1L << (97 - 64)) | (1L << (98 - 64)) | (1L << (99 - 64)) | (1L << (101 - 64)) | (1L << (102 - 64)) | (1L << (103 - 64)) | (1L << (104 - 64)) | (1L << (105 - 64)) | (1L << (106 - 64)) | (1L << (107 - 64)) | (1L << (108 - 64)) | (1L << (109 - 64)) | (1L << (110 - 64)) | (1L << (111 - 64)) | (1L << (112 - 64)) | (1L << (113 - 64)) | (1L << (114 - 64)) | (1L << (115 - 64)) | (1L << (116 - 64)) | (1L << (118 - 64)) | (1L << (119 - 64)) | (1L << (120 - 64)) | (1L << (121 - 64)) | (1L << (122 - 64)) | (1L << (123 - 64)) | (1L << (124 - 64)) | (1L << (125 - 64)) | (1L << (126 - 64)) | (1L << (127 - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (128 - 128)) | (1L << (129 - 128)) | (1L << (131 - 128)) | (1L << (132 - 128)) | (1L << (133 - 128)) | (1L << (134 - 128)) | (1L << (135 - 128)) | (1L << (136 - 128)) | (1L << (137 - 128)) | (1L << (138 - 128)) | (1L << (139 - 128)) | (1L << (140 - 128)) | (1L << (141 - 128)) | (1L << (142 - 128)) | (1L << (143 - 128)) | (1L << (144 - 128)) | (1L << (145 - 128)) | (1L << (146 - 128)) | (1L << (147 - 128)) | (1L << (148 - 128)) | (1L << (151 - 128)) | (1L << (152 - 128)) | (1L << (153 - 128)) | (1L << (155 - 128)) | (1L << (156 - 128)) | (1L << (158 - 128)) | (1L << (159 - 128)) | (1L << (160 - 128)) | (1L << (161 - 128)) | (1L << (162 - 128)) | (1L << (163 - 128)) | (1L << (164 - 128)) | (1L << (165 - 128)) | (1L << (167 - 128)) | (1L << (168 - 128)) | (1L << (169 - 128)) | (1L << (170 - 128)) | (1L << (171 - 128)) | (1L << (172 - 128)) | (1L << (173 - 128)) | (1L << (174 - 128)) | (1L << (175 - 128)) | (1L << (176 - 128)) | (1L << (177 - 128)) | (1L << (178 - 128)) | (1L << (179 - 128)) | (1L << (180 - 128)) | (1L << (181 - 128)) | (1L << (182 - 128)) | (1L << (184 - 128)) | (1L << (185 - 128)) | (1L << (186 - 128)) | (1L << (187 - 128)) | (1L << (188 - 128)) | (1L << (189 - 128)) | (1L << (190 - 128)) | (1L << (191 - 128)))) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & ((1L << (192 - 192)) | (1L << (193 - 192)) | (1L << (194 - 192)) | (1L << (195 - 192)) | (1L << (196 - 192)) | (1L << (197 - 192)) | (1L << (198 - 192)) | (1L << (199 - 192)) | (1L << (200 - 192)) | (1L << (201 - 192)) | (1L << (202 - 192)) | (1L << (203 - 192)) | (1L << (204 - 192)) | (1L << (205 - 192)) | (1L << (206 - 192)) | (1L << (207 - 192)) | (1L << (208 - 192)) | (1L << (209 - 192)) | (1L << (210 - 192)) | (1L << (211 - 192)) | (1L << (212 - 192)) | (1L << (213 - 192)) | (1L << (214 - 192)) | (1L << (215 - 192)) | (1L << (216 - 192)) | (1L << (217 - 192)) | (1L << (218 - 192)) | (1L << (219 - 192)) | (1L << (220 - 192)) | (1L << (221 - 192)) | (1L << (222 - 192)) | (1L << (223 - 192)) | (1L << (224 - 192)) | (1L << (225 - 192)) | (1L << (226 - 192)) | (1L << (227 - 192)) | (1L << (228 - 192)) | (1L << (229 - 192)) | (1L << (230 - 192)) | (1L << (231 - 192)) | (1L << (232 - 192)) | (1L << (233 - 192)) | (1L << (234 - 192)) | (1L << (235 - 192)) | (1L << (236 - 192)) | (1L << (237 - 192)) | (1L << (238 - 192)) | (1L << (239 - 192)) | (1L << (240 - 192)) | (1L << (241 - 192)) | (1L << (242 - 192)) | (1L << (244 - 192)) | (1L << (245 - 192)) | (1L << (246 - 192)) | (1L << (247 - 192)) | (1L << (248 - 192)) | (1L << (249 - 192)) | (1L << (250 - 192)) | (1L << (252 - 192)) | (1L << (253 - 192)) | (1L << (254 - 192)) | (1L << (255 - 192)))) != 0) || ((((_la - 256)) & ~0x3f) == 0 && ((1L << (_la - 256)) & ((1L << (256 - 256)) | (1L << (257 - 256)) | (1L << (258 - 256)) | (1L << (259 - 256)) | (1L << (260 - 256)) | (1L << (261 - 256)) | (1L << (262 - 256)) | (1L << (265 - 256)) | (1L << (266 - 256)) | (1L << (267 - 256)) | (1L << (268 - 256)) | (1L << (269 - 256)) | (1L << (270 - 256)) | (1L << (272 - 256)) | (1L << (273 - 256)) | (1L << (274 - 256)) | (1L << (275 - 256)) | (1L << (276 - 256)) | (1L << (278 - 256)) | (1L << (279 - 256)) | (1L << (280 - 256)) | (1L << (281 - 256)) | (1L << (282 - 256)) | (1L << (283 - 256)) | (1L << (284 - 256)) | (1L << (285 - 256)) | (1L << (286 - 256)) | (1L << (287 - 256)) | (1L << (288 - 256)) | (1L << (289 - 256)) | (1L << (290 - 256)) | (1L << (292 - 256)) | (1L << (293 - 256)) | (1L << (294 - 256)) | (1L << (295 - 256)) | (1L << (296 - 256)) | (1L << (297 - 256)) | (1L << (298 - 256)) | (1L << (299 - 256)) | (1L << (300 - 256)) | (1L << (302 - 256)) | (1L << (303 - 256)) | (1L << (304 - 256)) | (1L << (305 - 256)) | (1L << (306 - 256)) | (1L << (307 - 256)) | (1L << (308 - 256)) | (1L << (309 - 256)) | (1L << (310 - 256)) | (1L << (311 - 256)) | (1L << (313 - 256)) | (1L << (314 - 256)) | (1L << (315 - 256)) | (1L << (316 - 256)) | (1L << (317 - 256)) | (1L << (318 - 256)) | (1L << (319 - 256)))) != 0) || ((((_la - 320)) & ~0x3f) == 0 && ((1L << (_la - 320)) & ((1L << (320 - 320)) | (1L << (321 - 320)) | (1L << (322 - 320)) | (1L << (323 - 320)) | (1L << (324 - 320)) | (1L << (325 - 320)) | (1L << (326 - 320)) | (1L << (327 - 320)) | (1L << (329 - 320)) | (1L << (330 - 320)) | (1L << (331 - 320)) | (1L << (332 - 320)) | (1L << (333 - 320)) | (1L << (334 - 320)) | (1L << (335 - 320)) | (1L << (336 - 320)) | (1L << (337 - 320)) | (1L << (338 - 320)) | (1L << (339 - 320)) | (1L << (340 - 320)) | (1L << (341 - 320)) | (1L << (342 - 320)) | (1L << (343 - 320)) | (1L << (344 - 320)) | (1L << (345 - 320)) | (1L << (346 - 320)) | (1L << (347 - 320)) | (1L << (348 - 320)) | (1L << (350 - 320)) | (1L << (351 - 320)) | (1L << (353 - 320)) | (1L << (354 - 320)) | (1L << (355 - 320)) | (1L << (356 - 320)) | (1L << (357 - 320)) | (1L << (359 - 320)) | (1L << (361 - 320)) | (1L << (362 - 320)) | (1L << (363 - 320)) | (1L << (364 - 320)) | (1L << (365 - 320)) | (1L << (366 - 320)) | (1L << (368 - 320)) | (1L << (369 - 320)) | (1L << (370 - 320)) | (1L << (371 - 320)) | (1L << (372 - 320)) | (1L << (373 - 320)) | (1L << (374 - 320)) | (1L << (375 - 320)) | (1L << (376 - 320)) | (1L << (377 - 320)) | (1L << (378 - 320)) | (1L << (379 - 320)) | (1L << (380 - 320)) | (1L << (381 - 320)) | (1L << (382 - 320)) | (1L << (383 - 320)))) != 0) || ((((_la - 384)) & ~0x3f) == 0 && ((1L << (_la - 384)) & ((1L << (384 - 384)) | (1L << (385 - 384)) | (1L << (387 - 384)) | (1L << (388 - 384)) | (1L << (389 - 384)) | (1L << (390 - 384)) | (1L << (391 - 384)) | (1L << (392 - 384)) | (1L << (393 - 384)) | (1L << (394 - 384)) | (1L << (395 - 384)) | (1L << (396 - 384)) | (1L << (397 - 384)) | (1L << (398 - 384)) | (1L << (400 - 384)) | (1L << (402 - 384)) | (1L << (403 - 384)) | (1L << (404 - 384)) | (1L << (405 - 384)) | (1L << (406 - 384)) | (1L << (407 - 384)) | (1L << (408 - 384)) | (1L << (409 - 384)) | (1L << (410 - 384)) | (1L << (411 - 384)) | (1L << (413 - 384)) | (1L << (414 - 384)) | (1L << (415 - 384)) | (1L << (416 - 384)) | (1L << (418 - 384)) | (1L << (419 - 384)) | (1L << (420 - 384)) | (1L << (422 - 384)) | (1L << (423 - 384)) | (1L << (424 - 384)) | (1L << (425 - 384)) | (1L << (427 - 384)) | (1L << (428 - 384)) | (1L << (429 - 384)) | (1L << (430 - 384)) | (1L << (431 - 384)) | (1L << (432 - 384)) | (1L << (433 - 384)) | (1L << (434 - 384)) | (1L << (435 - 384)) | (1L << (436 - 384)) | (1L << (437 - 384)) | (1L << (438 - 384)) | (1L << (440 - 384)) | (1L << (441 - 384)) | (1L << (442 - 384)) | (1L << (443 - 384)) | (1L << (444 - 384)) | (1L << (446 - 384)) | (1L << (447 - 384)))) != 0) || ((((_la - 448)) & ~0x3f) == 0 && ((1L << (_la - 448)) & ((1L << (448 - 448)) | (1L << (449 - 448)) | (1L << (450 - 448)) | (1L << (451 - 448)) | (1L << (452 - 448)) | (1L << (453 - 448)) | (1L << (454 - 448)) | (1L << (455 - 448)) | (1L << (456 - 448)) | (1L << (457 - 448)) | (1L << (458 - 448)) | (1L << (459 - 448)) | (1L << (460 - 448)) | (1L << (461 - 448)) | (1L << (462 - 448)) | (1L << (463 - 448)) | (1L << (464 - 448)) | (1L << (465 - 448)) | (1L << (466 - 448)) | (1L << (467 - 448)) | (1L << (468 - 448)) | (1L << (469 - 448)) | (1L << (470 - 448)) | (1L << (471 - 448)) | (1L << (472 - 448)) | (1L << (473 - 448)) | (1L << (475 - 448)) | (1L << (476 - 448)) | (1L << (477 - 448)) | (1L << (478 - 448)) | (1L << (479 - 448)) | (1L << (480 - 448)) | (1L << (481 - 448)) | (1L << (482 - 448)) | (1L << (483 - 448)) | (1L << (484 - 448)) | (1L << (485 - 448)) | (1L << (486 - 448)) | (1L << (487 - 448)) | (1L << (488 - 448)) | (1L << (489 - 448)) | (1L << (490 - 448)) | (1L << (492 - 448)) | (1L << (493 - 448)) | (1L << (494 - 448)) | (1L << (495 - 448)) | (1L << (496 - 448)) | (1L << (497 - 448)) | (1L << (498 - 448)) | (1L << (499 - 448)) | (1L << (500 - 448)) | (1L << (501 - 448)) | (1L << (502 - 448)) | (1L << (504 - 448)) | (1L << (505 - 448)) | (1L << (506 - 448)) | (1L << (507 - 448)) | (1L << (508 - 448)) | (1L << (509 - 448)) | (1L << (510 - 448)) | (1L << (511 - 448)))) != 0) || ((((_la - 513)) & ~0x3f) == 0 && ((1L << (_la - 513)) & ((1L << (513 - 513)) | (1L << (514 - 513)) | (1L << (515 - 513)) | (1L << (516 - 513)) | (1L << (517 - 513)) | (1L << (518 - 513)) | (1L << (519 - 513)) | (1L << (520 - 513)) | (1L << (521 - 513)) | (1L << (523 - 513)) | (1L << (524 - 513)) | (1L << (525 - 513)) | (1L << (526 - 513)) | (1L << (528 - 513)) | (1L << (530 - 513)) | (1L << (531 - 513)) | (1L << (532 - 513)) | (1L << (533 - 513)) | (1L << (534 - 513)) | (1L << (535 - 513)) | (1L << (536 - 513)) | (1L << (537 - 513)) | (1L << (538 - 513)) | (1L << (539 - 513)) | (1L << (540 - 513)) | (1L << (541 - 513)) | (1L << (543 - 513)) | (1L << (544 - 513)) | (1L << (546 - 513)) | (1L << (547 - 513)) | (1L << (548 - 513)) | (1L << (549 - 513)) | (1L << (550 - 513)) | (1L << (551 - 513)) | (1L << (552 - 513)) | (1L << (553 - 513)) | (1L << (554 - 513)) | (1L << (555 - 513)) | (1L << (556 - 513)) | (1L << (557 - 513)) | (1L << (558 - 513)) | (1L << (559 - 513)) | (1L << (560 - 513)) | (1L << (561 - 513)) | (1L << (562 - 513)) | (1L << (564 - 513)) | (1L << (565 - 513)) | (1L << (567 - 513)) | (1L << (568 - 513)) | (1L << (569 - 513)) | (1L << (570 - 513)) | (1L << (571 - 513)) | (1L << (572 - 513)) | (1L << (573 - 513)) | (1L << (574 - 513)) | (1L << (575 - 513)) | (1L << (576 - 513)))) != 0) || ((((_la - 577)) & ~0x3f) == 0 && ((1L << (_la - 577)) & ((1L << (577 - 577)) | (1L << (580 - 577)) | (1L << (581 - 577)) | (1L << (582 - 577)) | (1L << (583 - 577)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Groep_pathContext extends ParserRuleContext {
		public Groep_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groep_path; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitGroep_path(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Groep_pathContext groep_path() throws RecognitionException {
		Groep_pathContext _localctx = new Groep_pathContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_groep_path);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			_la = _input.LA(1);
			if ( !(_la==28 || _la==58 || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (69 - 69)) | (1L << (79 - 69)) | (1L << (80 - 69)) | (1L << (87 - 69)) | (1L << (94 - 69)) | (1L << (117 - 69)))) != 0) || ((((_la - 150)) & ~0x3f) == 0 && ((1L << (_la - 150)) & ((1L << (150 - 150)) | (1L << (157 - 150)) | (1L << (166 - 150)))) != 0) || _la==264 || _la==277 || ((((_la - 401)) & ~0x3f) == 0 && ((1L << (_la - 401)) & ((1L << (401 - 401)) | (1L << (417 - 401)) | (1L << (421 - 401)) | (1L << (439 - 401)))) != 0) || ((((_la - 512)) & ~0x3f) == 0 && ((1L << (_la - 512)) & ((1L << (512 - 512)) | (1L << (522 - 512)) | (1L << (527 - 512)) | (1L << (542 - 512)))) != 0) || _la==578) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\u0273\u0165\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\3\2\3\2\3\3\3\3\3\4\3\4\5\4q\n\4\3\5\3\5\3\5\3\5\7\5w\n"+
		"\5\f\5\16\5z\13\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\5\7\u0083\n\7\3\b\3\b\3"+
		"\b\5\b\u0088\n\b\3\t\3\t\3\t\3\t\5\t\u008e\n\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\5\13\u0096\n\13\3\f\3\f\3\r\3\r\3\r\3\r\5\r\u009e\n\r\3\16\3\16"+
		"\3\17\5\17\u00a3\n\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\5\21\u00b2\n\21\3\22\3\22\3\22\3\22\3\23\3\23\5\23\u00ba"+
		"\n\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u00c3\n\25\f\25\16\25\u00c6"+
		"\13\25\3\25\3\25\3\26\3\26\3\26\5\26\u00cd\n\26\3\26\3\26\3\27\3\27\3"+
		"\27\3\30\3\30\3\30\5\30\u00d7\n\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32"+
		"\3\33\3\33\3\34\3\34\3\34\3\34\3\34\7\34\u00e7\n\34\f\34\16\34\u00ea\13"+
		"\34\5\34\u00ec\n\34\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \5 \u0105\n \3!\3!\3"+
		"\"\3\"\3#\3#\3#\5#\u010e\n#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3%\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\5%\u0133"+
		"\n%\3&\3&\3\'\3\'\5\'\u0139\n\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3"+
		"-\3-\3-\3-\5-\u014b\n-\5-\u014d\n-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\5\61"+
		"\u0157\n\61\3\61\3\61\3\62\3\62\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65"+
		"\3\65\2\66\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66"+
		"8:<>@BDFHJLNPRTVXZ\\^`bdfh\2\f\3\2\u0255\u0257\3\2\u0258\u025d\3\2\u025e"+
		"\u025f\4\2\u025f\u025f\u0262\u0262\37\2\f\f%%\62\62::^^bbff\u0084\u0084"+
		"\u0097\u0097\u009c\u009c\u00b9\u00b9\u00f5\u00f5\u00fd\u00fd\u0109\u0109"+
		"\u0111\u0111\u0125\u0125\u012f\u012f\u013a\u013a\u014a\u014a\u015f\u015f"+
		"\u0171\u0171\u0184\u0184\u01ac\u01ac\u01dc\u01dc\u01f9\u01f9\u0213\u0213"+
		"\u0223\u0223\u0235\u0235\u0238\u0238\7\299\u0162\u0162\u016a\u016a\u01bf"+
		"\u01bf\u0245\u0245\3\2\u0265\u0266\3\2\u0268\u0273:\2\3\13\r\35\37$&\61"+
		"\638;;=FHPSXZ]__aacegvx\u0083\u0085\u0096\u0099\u009b\u009d\u009e\u00a0"+
		"\u00a7\u00a9\u00b8\u00ba\u00f4\u00f6\u00fc\u00fe\u0108\u010b\u0110\u0112"+
		"\u0116\u0118\u0124\u0126\u012e\u0130\u0139\u013b\u0149\u014b\u015e\u0160"+
		"\u0161\u0163\u0167\u0169\u0169\u016b\u0170\u0172\u0183\u0185\u0190\u0192"+
		"\u0192\u0194\u019d\u019f\u01a2\u01a4\u01a6\u01a8\u01ab\u01ad\u01b8\u01ba"+
		"\u01be\u01c0\u01db\u01dd\u01ec\u01ee\u01f8\u01fa\u0201\u0203\u020b\u020d"+
		"\u0210\u0212\u0212\u0214\u021f\u0221\u0222\u0224\u0234\u0236\u0237\u0239"+
		"\u0243\u0246\u0249\27\2\36\36<<GGQRYY``ww\u0098\u0098\u009f\u009f\u00a8"+
		"\u00a8\u010a\u010a\u0117\u0117\u0193\u0193\u01a3\u01a3\u01a7\u01a7\u01b9"+
		"\u01b9\u0202\u0202\u020c\u020c\u0211\u0211\u0220\u0220\u0244\u0244\u0155"+
		"\2j\3\2\2\2\4l\3\2\2\2\6n\3\2\2\2\br\3\2\2\2\n{\3\2\2\2\f\177\3\2\2\2"+
		"\16\u0084\3\2\2\2\20\u0089\3\2\2\2\22\u008f\3\2\2\2\24\u0091\3\2\2\2\26"+
		"\u0097\3\2\2\2\30\u0099\3\2\2\2\32\u009f\3\2\2\2\34\u00a2\3\2\2\2\36\u00a6"+
		"\3\2\2\2 \u00b1\3\2\2\2\"\u00b3\3\2\2\2$\u00b9\3\2\2\2&\u00bb\3\2\2\2"+
		"(\u00be\3\2\2\2*\u00cc\3\2\2\2,\u00d0\3\2\2\2.\u00d6\3\2\2\2\60\u00da"+
		"\3\2\2\2\62\u00dd\3\2\2\2\64\u00df\3\2\2\2\66\u00e1\3\2\2\28\u00ef\3\2"+
		"\2\2:\u00f1\3\2\2\2<\u00fa\3\2\2\2>\u0104\3\2\2\2@\u0106\3\2\2\2B\u0108"+
		"\3\2\2\2D\u010d\3\2\2\2F\u010f\3\2\2\2H\u0132\3\2\2\2J\u0134\3\2\2\2L"+
		"\u0138\3\2\2\2N\u013a\3\2\2\2P\u013c\3\2\2\2R\u013e\3\2\2\2T\u0140\3\2"+
		"\2\2V\u0142\3\2\2\2X\u0144\3\2\2\2Z\u014e\3\2\2\2\\\u0150\3\2\2\2^\u0152"+
		"\3\2\2\2`\u0156\3\2\2\2b\u015a\3\2\2\2d\u015e\3\2\2\2f\u0160\3\2\2\2h"+
		"\u0162\3\2\2\2jk\5\4\3\2k\3\3\2\2\2lm\5\6\4\2m\5\3\2\2\2np\5\f\7\2oq\5"+
		"\b\5\2po\3\2\2\2pq\3\2\2\2q\7\3\2\2\2rs\7\u0264\2\2sx\5\n\6\2tu\7\u0252"+
		"\2\2uw\5\n\6\2vt\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y\t\3\2\2\2zx\3"+
		"\2\2\2{|\5\64\33\2|}\7\u0255\2\2}~\5\4\3\2~\13\3\2\2\2\177\u0082\5\16"+
		"\b\2\u0080\u0081\7\u0260\2\2\u0081\u0083\5\f\7\2\u0082\u0080\3\2\2\2\u0082"+
		"\u0083\3\2\2\2\u0083\r\3\2\2\2\u0084\u0087\5\20\t\2\u0085\u0086\7\u0261"+
		"\2\2\u0086\u0088\5\16\b\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\17\3\2\2\2\u0089\u008d\5\24\13\2\u008a\u008b\5\22\n\2\u008b\u008c\5\24"+
		"\13\2\u008c\u008e\3\2\2\2\u008d\u008a\3\2\2\2\u008d\u008e\3\2\2\2\u008e"+
		"\21\3\2\2\2\u008f\u0090\t\2\2\2\u0090\23\3\2\2\2\u0091\u0095\5\30\r\2"+
		"\u0092\u0093\5\26\f\2\u0093\u0094\5\30\r\2\u0094\u0096\3\2\2\2\u0095\u0092"+
		"\3\2\2\2\u0095\u0096\3\2\2\2\u0096\25\3\2\2\2\u0097\u0098\t\3\2\2\u0098"+
		"\27\3\2\2\2\u0099\u009d\5\34\17\2\u009a\u009b\5\32\16\2\u009b\u009c\5"+
		"\30\r\2\u009c\u009e\3\2\2\2\u009d\u009a\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\31\3\2\2\2\u009f\u00a0\t\4\2\2\u00a0\33\3\2\2\2\u00a1\u00a3\5\36\20\2"+
		"\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5"+
		"\5 \21\2\u00a5\35\3\2\2\2\u00a6\u00a7\t\5\2\2\u00a7\37\3\2\2\2\u00a8\u00b2"+
		"\5$\23\2\u00a9\u00b2\5\66\34\2\u00aa\u00b2\5:\36\2\u00ab\u00b2\5\60\31"+
		"\2\u00ac\u00b2\5*\26\2\u00ad\u00b2\5,\27\2\u00ae\u00b2\5> \2\u00af\u00b2"+
		"\5\64\33\2\u00b0\u00b2\5\"\22\2\u00b1\u00a8\3\2\2\2\u00b1\u00a9\3\2\2"+
		"\2\u00b1\u00aa\3\2\2\2\u00b1\u00ab\3\2\2\2\u00b1\u00ac\3\2\2\2\u00b1\u00ad"+
		"\3\2\2\2\u00b1\u00ae\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b0\3\2\2\2\u00b2"+
		"!\3\2\2\2\u00b3\u00b4\7\u024e\2\2\u00b4\u00b5\5\4\3\2\u00b5\u00b6\7\u024f"+
		"\2\2\u00b6#\3\2\2\2\u00b7\u00ba\5&\24\2\u00b8\u00ba\5(\25\2\u00b9\u00b7"+
		"\3\2\2\2\u00b9\u00b8\3\2\2\2\u00ba%\3\2\2\2\u00bb\u00bc\7\u0250\2\2\u00bc"+
		"\u00bd\7\u0251\2\2\u00bd\'\3\2\2\2\u00be\u00bf\7\u0250\2\2\u00bf\u00c4"+
		"\5\4\3\2\u00c0\u00c1\7\u0252\2\2\u00c1\u00c3\5\4\3\2\u00c2\u00c0\3\2\2"+
		"\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c7"+
		"\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7\u00c8\7\u0251\2\2\u00c8)\3\2\2\2\u00c9"+
		"\u00ca\5\62\32\2\u00ca\u00cb\7\u0253\2\2\u00cb\u00cd\3\2\2\2\u00cc\u00c9"+
		"\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00cf\5f\64\2\u00cf"+
		"+\3\2\2\2\u00d0\u00d1\7\u0263\2\2\u00d1\u00d2\5*\26\2\u00d2-\3\2\2\2\u00d3"+
		"\u00d4\5\62\32\2\u00d4\u00d5\7\u0253\2\2\u00d5\u00d7\3\2\2\2\u00d6\u00d3"+
		"\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\5h\65\2\u00d9"+
		"/\3\2\2\2\u00da\u00db\7\u0263\2\2\u00db\u00dc\5.\30\2\u00dc\61\3\2\2\2"+
		"\u00dd\u00de\7\u024b\2\2\u00de\63\3\2\2\2\u00df\u00e0\7\u024b\2\2\u00e0"+
		"\65\3\2\2\2\u00e1\u00e2\58\35\2\u00e2\u00eb\7\u024e\2\2\u00e3\u00e8\5"+
		"\4\3\2\u00e4\u00e5\7\u0252\2\2\u00e5\u00e7\5\4\3\2\u00e6\u00e4\3\2\2\2"+
		"\u00e7\u00ea\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ec"+
		"\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00e3\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec"+
		"\u00ed\3\2\2\2\u00ed\u00ee\7\u024f\2\2\u00ee\67\3\2\2\2\u00ef\u00f0\t"+
		"\6\2\2\u00f09\3\2\2\2\u00f1\u00f2\5<\37\2\u00f2\u00f3\7\u024e\2\2\u00f3"+
		"\u00f4\5\4\3\2\u00f4\u00f5\7\u0252\2\2\u00f5\u00f6\5\64\33\2\u00f6\u00f7"+
		"\7\u0252\2\2\u00f7\u00f8\5\4\3\2\u00f8\u00f9\7\u024f\2\2\u00f9;\3\2\2"+
		"\2\u00fa\u00fb\t\7\2\2\u00fb=\3\2\2\2\u00fc\u0105\5@!\2\u00fd\u0105\5"+
		"B\"\2\u00fe\u0105\5D#\2\u00ff\u0105\5H%\2\u0100\u0105\5F$\2\u0101\u0105"+
		"\5X-\2\u0102\u0105\5b\62\2\u0103\u0105\5d\63\2\u0104\u00fc\3\2\2\2\u0104"+
		"\u00fd\3\2\2\2\u0104\u00fe\3\2\2\2\u0104\u00ff\3\2\2\2\u0104\u0100\3\2"+
		"\2\2\u0104\u0101\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0103\3\2\2\2\u0105"+
		"?\3\2\2\2\u0106\u0107\7\u024a\2\2\u0107A\3\2\2\2\u0108\u0109\t\b\2\2\u0109"+
		"C\3\2\2\2\u010a\u010e\7\u024c\2\2\u010b\u010c\7\u025f\2\2\u010c\u010e"+
		"\7\u024c\2\2\u010d\u010a\3\2\2\2\u010d\u010b\3\2\2\2\u010eE\3\2\2\2\u010f"+
		"\u0110\5J&\2\u0110\u0111\7\u01ed\2\2\u0111\u0112\5L\'\2\u0112\u0113\7"+
		"\u01ed\2\2\u0113\u0114\5P)\2\u0114\u0115\7\u01ed\2\2\u0115\u0116\5R*\2"+
		"\u0116\u0117\7\u01ed\2\2\u0117\u0118\5T+\2\u0118\u0119\7\u01ed\2\2\u0119"+
		"\u011a\5V,\2\u011aG\3\2\2\2\u011b\u011c\5J&\2\u011c\u011d\7\u01ed\2\2"+
		"\u011d\u011e\5L\'\2\u011e\u011f\7\u01ed\2\2\u011f\u0120\5P)\2\u0120\u0133"+
		"\3\2\2\2\u0121\u0122\5J&\2\u0122\u0123\7\u01ed\2\2\u0123\u0124\5L\'\2"+
		"\u0124\u0125\7\u01ed\2\2\u0125\u0126\7\u0254\2\2\u0126\u0133\3\2\2\2\u0127"+
		"\u0128\5J&\2\u0128\u0129\7\u01ed\2\2\u0129\u012a\7\u0254\2\2\u012a\u012b"+
		"\7\u01ed\2\2\u012b\u012c\7\u0254\2\2\u012c\u0133\3\2\2\2\u012d\u012e\7"+
		"\u0254\2\2\u012e\u012f\7\u01ed\2\2\u012f\u0130\7\u0254\2\2\u0130\u0131"+
		"\7\u01ed\2\2\u0131\u0133\7\u0254\2\2\u0132\u011b\3\2\2\2\u0132\u0121\3"+
		"\2\2\2\u0132\u0127\3\2\2\2\u0132\u012d\3\2\2\2\u0133I\3\2\2\2\u0134\u0135"+
		"\5D#\2\u0135K\3\2\2\2\u0136\u0139\5D#\2\u0137\u0139\5N(\2\u0138\u0136"+
		"\3\2\2\2\u0138\u0137\3\2\2\2\u0139M\3\2\2\2\u013a\u013b\t\t\2\2\u013b"+
		"O\3\2\2\2\u013c\u013d\5D#\2\u013dQ\3\2\2\2\u013e\u013f\5D#\2\u013fS\3"+
		"\2\2\2\u0140\u0141\5D#\2\u0141U\3\2\2\2\u0142\u0143\5D#\2\u0143W\3\2\2"+
		"\2\u0144\u0145\7\u019e\2\2\u0145\u014c\5Z.\2\u0146\u0147\7\u01ed\2\2\u0147"+
		"\u014a\5\\/\2\u0148\u0149\7\u01ed\2\2\u0149\u014b\5^\60\2\u014a\u0148"+
		"\3\2\2\2\u014a\u014b\3\2\2\2\u014b\u014d\3\2\2\2\u014c\u0146\3\2\2\2\u014c"+
		"\u014d\3\2\2\2\u014dY\3\2\2\2\u014e\u014f\5`\61\2\u014f[\3\2\2\2\u0150"+
		"\u0151\5`\61\2\u0151]\3\2\2\2\u0152\u0153\5`\61\2\u0153_\3\2\2\2\u0154"+
		"\u0157\7\u025f\2\2\u0155\u0157\3\2\2\2\u0156\u0154\3\2\2\2\u0156\u0155"+
		"\3\2\2\2\u0157\u0158\3\2\2\2\u0158\u0159\5D#\2\u0159a\3\2\2\2\u015a\u015b"+
		"\7\u0168\2\2\u015b\u015c\5f\64\2\u015c\u015d\7\u0191\2\2\u015dc\3\2\2"+
		"\2\u015e\u015f\7\u0267\2\2\u015fe\3\2\2\2\u0160\u0161\t\n\2\2\u0161g\3"+
		"\2\2\2\u0162\u0163\t\13\2\2\u0163i\3\2\2\2\30px\u0082\u0087\u008d\u0095"+
		"\u009d\u00a2\u00b1\u00b9\u00c4\u00cc\u00d6\u00e8\u00eb\u0104\u010d\u0132"+
		"\u0138\u014a\u014c\u0156";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}