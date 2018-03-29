myApp.controller(
	'tidyController', [
		'$scope',
		'$filter',
		'$http',
		'tidyService',
		'_',
		'$interval',
		'$q',
		function($scope, $filter, $http, tidyService, _, $interval, $q) {
			
			INPUT_TYPE_BERICHT="bericht";
			INPUT_TYPE_LG01="lg01header";
			INPUT_TYPE_AG="agheader";
			INPUT_TYPE_GV="gv01header";
			INPUT_TYPE_NG="ng01header";
			INPUT_TYPE_WA="wa11header";
			INPUT_TYPE_CSV="csv";
			
			var save=[];
			
			$scope.ai=[];
			$scope.errorMessages=[];

			$scope.countPLOK = 0;
			$scope.countPLNotOK = 0;
			$scope.countAIOK = 0;
			$scope.countAINotOK = 0;
			
			/*
			format message:
				lg01header: SECURITY_HEADER{49} TOTAL_LENGTH{5} [GROUP{2} BERICHT_GROUP_LENGTH{3} [SUB{4} SUB_LENGTH{3} SUB_MESSAGE]]
				lg01: TOTAL_LENGTH{5} [GROUP{2} GROUP_LENGTH{3} [SUB{4} SUB_LENGTH{3} SUB_MESSAGE]]
				csv: Berichttype;;Lg01\nXXXXX;XXXXXX;XXXXXX\n[GROUP_NAME;GROUP;\nSUB_NAME;SUB;SUB_MESSAGE]
		
			example1: 01619011910110010720367492401200093019319380210007Mohamed0230002El0240004Rafi0310008194902280320004Suez033000470140410001M6110001E821000418108220008201204248230008pas 7014851000800000000861000819951001020720240001.821000418108220008201204248230001.851000800000000861000819940310030720240001.821000418108220008201204248230001.8510008000000008610008199403100407605100040112821000418108220008199403108230002PK851000819490228861000819940310052060110010795691349201200093008419170210006Michal0240006Jansen0310008197001010320006Odessa033000490490410001M06100081998081506200041810063000460301510001P8110004181081200075 A0025851000819980815861000819980817070776810008199403106910004181070100010801000400038020017201101311315200008710001P08228091000418100920008200002291010001W1030008200002291110009Kokkelhof1115009Kokkelhof11200021311600062492SE1170008Toetsoog1180016181001004001300111900161810200040013001141000470141420008199307017210001T85100082009090686100082009090658151091000418100920008200002291010001W1030008200002291110009Kokkelhof11200021311600062492SE141000470141420008199307017210001A85100082000022986100082000022958156091000418110920008199307011010001W1030008199307011110014Poseidonstraat11200022611600063054PZ141000470141420008199307017210001A851000819930701861000819940310100693910002213920008201506303930008201006308510008201101308610008201101316005439100022639300081998070185100081998070186100082000111512075371000118210004000082200080000000082300047014851000800000000861000819940310140284010006900208851000820030606140284010006900302851000819960115140284010006700302851000819951101140284010006250001851000819941030
			example2: 00000000Lg01201212011530150007934628529000000000002856011830110010793462852901200093009318270210008Brigitte0240006Moulin0310008192312130320007Avignon033000450020410001V6110001E821000418108220008199405018230003PKA851000819231213861000819951001021660210007Lisette0240008BÂeamont0310008190203200320016Clermont-Ferrand033000450020410001V621000819231213821000418108220008199405018230002PK851000819231213861000819940501031580210011GuÃillaumÂe0240006Moulin0310008190303150320006Toulon033000450020410001M621000819231213821000418108220008199405018230002PK851000819231213861000819940501040308510008199406018610008199406055407605100040100821000418108220008199405018230002PK851000819390301861000819940501040510510004000163100030128510008195502018610008199405010407605100040057821000418108220008199405018230002PK851000819231213861000819940501051820210005JÂean0240007Roussñx0310008193001010320005Lille033000450020410001M06100081992091306200041810063000460301510001H821000418108220008199405018230002PK851000819920913861000819940501051930210005Alain0240005Dumas0310008192210170320010AngoulÃeme033000450020410001M07100081990090207200041810073000460300740001O1510001H821000418108220008199405018230002PK851000819900902861000819940501551890210005Alain0240005Dumas0310008192210170320010AngoulÃeme033000450020410001M0610008194804040620008Narbonne063000450021510001H821000418108220008199405018230002PK851000819480404861000819940501071006710008201212016720001E6810008199405016910004181070100010801000400048020017201212011530150008710001P081500910004199509200082012101813100048017132000820121018133003512 corner Baymen Avenue & Calle al 1340011Belize City7210001I85100082012101886100082012120158258091000418100920008200909281010001W1030008200909281110010S vd Oyeln1115038Baron Schimmelpenninck van der Oyelaan11200021611600062252EB1170008Toetsoog1180016181001001001600111900161810200010016001141000450021420008195304067210001I85100082009092886100082009092858251091000418100920008199204061010001W1030008199204061110021Van Wijngaerdenstraat1115021Van Wijngaerdenstraat1120001111600062596TW1170008Toetsoog1180016181001001000100111900161810200010001001141000450021420008195304067210001T85100082009090686100082009090658162091000418100920008199204061010001W1030008199204061110021Van Wijngaerdenstraat1120001111600062596TW141000450021420008195304067210001A851000819920406861000819940501091330210008HÂelÁene0240005Dumas0310008195007230320008Narbonne03300045002821000418108220008199405018230002PK851000819500723861000819940501100693910002393920008201311303930008201211308510008201211308610008201212016006939100022139200082012113039300082011113085100082012113086100082011113012080371000118210004000082200080000000082300095002 604785100080000000086100081994050114015851000820101223640284010006870201851000819960115140284010006900202851000820030710140284010006900208851000819941030140284010006900201851000819941015140284010006900302851000819941015
			*/
			
			function setDefaults() {
				$scope.originator_or_recipient = "0518010";
				$scope.mutatie_dt = $filter('date')(new Date(), 'yyyyMMdd HH:mm:ss');
			}

			function row(group, sub, subMessage) {
				
				if (!save[save.length - 1]) {
					// ! load_all
					save[save.length - 1]={};
				}
				
				if (group == "01" && sub == "0110" || group == "1" && sub == "110") {
					$scope.anr = save[save.length - 1]['anr'] = subMessage;
				}

				if (group == "01" && sub == "0120" || group == "1" && sub == "120") {
					$scope.bsn = save[save.length - 1]['bsn'] = subMessage;
				}

				if (group == "14" || group == "64") {
					if (!save[save.length - 1]['ai']) {
						save[save.length - 1]['ai']=[];
					}

					var index = $scope.ai.length - 1;
					if (!save[save.length - 1]['ai'][index]) {
						save[save.length - 1]['ai'][index]={};
					}

					$scope.ai[index]['group'] = save[save.length - 1]['ai'][index]['group'] = group;

					if (!$scope.ai[index]["ingangsdatum"]) {
						$scope.ai[index]["ingangsdatum"] = save[save.length - 1]['ai'][index]["ingangsdatum"] = "-";
					}
					
					if (!$scope.ai[index]["afnemer"]) {
						$scope.ai[index]["afnemer"] = save[save.length - 1]['ai'][index]["afnemer"] = "-";
					}
					
					switch (sub) {
						case "8510":
							$scope.ai[index]["ingangsdatum"] = save[save.length - 1]['ai'][index]["ingangsdatum"] = "" + subMessage;
							break;
						case "4010":
							$scope.ai[index]["afnemer"] = save[save.length - 1]['ai'][index]["afnemer"] = "" + subMessage;
							break;
					}
				}

				var returnMessage = '<subgroup><div class="form-group form-horizontal row">';
				returnMessage += '<h5 class="control-label col-sm-2">' + (LO3_ELEMENTS[tidyService.zeroFill(sub, PRESENTATION_SUB_LENGTH)] || '<span class="glyphicon glyphicon-alert" style="color:red"></span> <span class="text-danger">' + tidyService.zeroFill(sub, PRESENTATION_SUB_LENGTH)) + '</span>' + ' <small>(' + tidyService.zeroFill(sub, PRESENTATION_SUB_LENGTH) + ')</small></h5>';
				returnMessage += '<div class="col-sm-6">';
				returnMessage += '<input type="text" class="input-md form-control" id="' + group + '_' + sub + '" value="' + subMessage + '" onkeyup="$(this).parent().next().html($(this).val().length);"></input>';
				if (subMessage) {
					returnMessage += '</div><h5 class="col-md-1" style="vertical-align: middle;">' + subMessage.length + '</h5>';
					if (!LO3_ELEMENTS[tidyService.zeroFill(sub, PRESENTATION_SUB_LENGTH)]) {
						returnMessage += '<h5 class="col-md-1"><span class="glyphicon glyphicon-alert" style="color:red"></span></h5>';
					}
				} else {
					returnMessage += '</div><div class="col-md-1">0</div>';
				}					
				returnMessage += '</div>';
				// hack for both single and double quotes in value
				returnMessage += '<script>if ("' + subMessage.replace (/"/g, '\\"') + '".indexOf("\\"") != -1) { $("#' + group + '_' + sub + '").val("' + subMessage.replace(/"/g, '\\"') + '"); }</script></subgroup>';

				return returnMessage;
			}

			function groupHead(group) {
				var returnMessage = '<div class="container form-horizontal"><group id="' + group + '"><h4>' + LO3_GROUPS[tidyService.zeroFill(group, PRESENTATION_GROUP_LENGTH)];
				returnMessage += ' <small>(' + tidyService.zeroFill(group, PRESENTATION_GROUP_LENGTH) + ')</small></h4>';

				if (group == "14") {
					if (!save[save.length - 1]['ai']) {
						save[save.length - 1]['ai']=[];
					}
					
					var index = $scope.ai.length;
					$scope.ai[index] = save[save.length - 1]['ai'][index] = {};
					$scope.ai[index]["stapel"] = save[save.length - 1]['ai'][index]["stapel"] = "" + _.where(save[save.length - 1]['ai'], {"group": "14"}).length;
					$scope.ai[index]["volgnummer"] = save[save.length - 1]['ai'][index]["volgnummer"] = "0";
				}
				
				if (group == "64") {
					if (!save[save.length - 1]['ai']) {
						save[save.length - 1]['ai']=[];
					}
					
					var index = $scope.ai.length;
					$scope.ai[index] = save[save.length - 1]['ai'][index] = {};

					if (!$scope.ai[index-1]) {
						$scope.ai[index]["stapel"] = save[save.length - 1]['ai'][index]["stapel"] = "0";
						$scope.ai[index]["volgnummer"] = save[save.length - 1]['ai'][index]["volgnummer"] = "0";
					} else {
						$scope.ai[index]["stapel"] = save[save.length - 1]['ai'][index]["stapel"] = save[save.length - 1]['ai'][index-1]["stapel"];
						$scope.ai[index]["volgnummer"] = save[save.length - 1]['ai'][index]["volgnummer"] = "" + (Number(save[save.length - 1]['ai'][index-1]["volgnummer"])+1);
					}
				}

				return returnMessage;
			}

			function groupEnd() {
				return '</group></div>';
			}

			function parseCsv(inputMessage) {
				var returnMessage = "";
				var messageTokenized = inputMessage.replace(/%/g, '\n').split('\n');
				messageTokenized = messageTokenized.filter(function(value) {
					return value != '';
				});
				messageTokenized = messageTokenized.slice(CSV_SKIP_LINES);

				var group = "";
				var seperator = ";";
				$.each(messageTokenized, function(index, value) {
					var valueTokenized = value.split(seperator);
					if (valueTokenized.length == 1) {
						valueTokenized = value.split('\t');
						seperator = "\t";
					}

					if (! valueTokenized[2] || valueTokenized[2] == "") {
						if (group != "") {
							returnMessage += groupEnd();
						}

						// new group
						group = valueTokenized[1].replace(/^"/, "").replace(/"$/, "");
						returnMessage += groupHead(group);
					} else {
						// join possible more tokens remove surrounding quotes in value
						returnMessage += row(group, valueTokenized[1]?valueTokenized[1].replace(/^"/, "").replace(/"$/, ""):"",
								valueTokenized.slice(2).join().replace(/^"/, "").replace(/"$/, ""));
					}
				});

				if (messageTokenized.length > 0) {
					returnMessage += groupEnd();
				}

				return returnMessage;
			}

			function parse(inputMessage) {
				var returnMessage = "";
				$("input#headerText").val(""); // init header text
				$scope.ai=[];

				setDefaults();				
				if ($("input[name='radioType']:checked").val() == INPUT_TYPE_CSV) {
					returnMessage += parseCsv(inputMessage)
				} else {
					var skipIndex = 0;
					
					if ($("input[name='radioType']:checked").val() == INPUT_TYPE_LG01) {
						// save header in hidden input
						$("input#headerText").val(inputMessage.substring(0, LG01_HEADER_LENGTH));
						skipIndex = LG01_HEADER_LENGTH;
					} else if ($("input[name='radioType']:checked").val() == INPUT_TYPE_AG) {
						// save header in hidden input
						$("input#headerText").val(inputMessage.substring(0, AG_HEADER_LENGTH));
						skipIndex = AG_HEADER_LENGTH;
					} else if ($("input[name='radioType']:checked").val() == INPUT_TYPE_GV) {
						// save header in hidden input
						$("input#headerText").val(inputMessage.substring(0, GV01_HEADER_LENGTH));
						skipIndex = GV01_HEADER_LENGTH;
					} else if ($("input[name='radioType']:checked").val() == INPUT_TYPE_NG) {
						// save header in hidden input
						$("input#headerText").val(inputMessage.substring(0, NG_HEADER_LENGTH));
						skipIndex = NG_HEADER_LENGTH;
					} else if ($("input[name='radioType']:checked").val() == INPUT_TYPE_WA) {
						// save header in hidden input
						$("input#headerText").val(inputMessage.substring(0, WA_HEADER_LENGTH));
						skipIndex = WA_HEADER_LENGTH;
					}
					
					returnMessage = tidyService.parse(inputMessage, skipIndex, groupHead, row, groupEnd);
				}

				return returnMessage;
			}

			function assemble() {
				var groups = "";
				$("group").each(function(index) {
					var groupLength = 0;
					var group = tidyService.zeroFill($(this).attr('id'), BERICHT_GROUP_LENGTH);
					var subs = "";

					$(this).find("input").each(function(index) {
						var id = $(this).attr('id').split("_")[1]
						var sub = tidyService.zeroFill(id, BERICHT_SUB_LENGTH);
						var conv = tidyService.translateTeletex($(this).val());
						
						sub += tidyService.zeroFill(conv.length, BERICHT_LENGTH_LENGTH);
						sub += conv;

						groupLength += sub.length;
						subs += sub;
					});

					group += tidyService.zeroFill(groupLength, BERICHT_LENGTH_LENGTH);
					group += subs;

					groups += group;
				});

				var header;
				if ($("input[name='radioType']:checked").val() == INPUT_TYPE_LG01) {
					header = tidyService.assembleHeader('Lg01', { 'anr' : $scope.anr });
				} else if ($("input[name='radioType']:checked").val() == INPUT_TYPE_GV) {
					header = tidyService.assembleHeader('Gv01', { 'anr' : $scope.anr });
				} else if ($("input[name='radioType']:checked").val() == INPUT_TYPE_AG) {
					header = tidyService.assembleHeader('Ag11', { 'anr' : $scope.anr });
				} else if ($("input[name='radioType']:checked").val() == INPUT_TYPE_NG) {
					header = tidyService.assembleHeader('Ng01');
				} else if ($("input[name='radioType']:checked").val() == INPUT_TYPE_WA) {
					header = tidyService.assembleHeader('Wa11');
				}
				
				if ($("input[name='radioType']:checked").val() != 'bericht') {
					groups = header + tidyService.zeroFill(groups.length, BERICHT_TOTAL_LENGTH_LENGTH) + groups;
				} else {
					groups = tidyService.zeroFill(groups.length, BERICHT_TOTAL_LENGTH_LENGTH) + groups;
				}

				return groups;
			}

			$scope.go = function() {
				if ($scope.from == "") {
					return false;
				}

				$("#to").html(parse($("#from").val()));
				return false;
			}

			$scope.back = function() {
				if ($("#to").html() == "") {
					return false;
				}

				if ($("input[name='radioType']:checked").val() == INPUT_TYPE_CSV) {
					return false;
				}

				$scope.from = assemble();
				return false;
			}
			
			$scope.insertPL = function(from, excel, anr, bsn, ai, originator_or_recipient, mutatie_dt) {
				if (!from) {
					from=$scope.from;
				}
				if (!excel) {
					excel=$scope.excel;
				}
				if (!anr) {
					anr=$scope.anr;
				}
				if (!bsn) {
					bsn=$scope.bsn;
				}
				if (!originator_or_recipient) {
					originator_or_recipient=$scope.originator_or_recipient;
				}
				if (!mutatie_dt) {
					mutatie_dt=$scope.mutatie_dt;
				}
				
				$("#insertPL").addClass('disabled');
					$http.post(URL_CONTEXT_PATH + "/save_pl_gba?anr=" + anr + "&bsn=" + bsn + "&excel=" + excel, {
							bericht : from,
							originator_or_recipient : originator_or_recipient,
							mutatie_dt : mutatie_dt
						}
					).success(function(data) {
						if (data.status != "ok") {
							$scope.alert="alert alert-danger"		
							$scope.errorMessages.push("Fout bij opslaan bericht bij PL anr " + anr + ", foutmelding:" + data.error);
							$scope.alertMessage = "Fout: " + data.error;
							console.error("anr: " + anr + ", error: " + data.error + ", data: " + from);
							$scope.countPLNotOK ++;
						} else {
							$scope.alert="alert alert-success"
							$scope.alertMessage = "Bericht bij PL " + anr + " opgeslagen";
							$scope.countPLOK ++;

							if (ai && ai.length>0) {
								$scope.insertAI(anr, bsn, ai);
							}
						}
						$("#insertPL").removeClass('disabled');
					});
			}
			
			$scope.insertAI = function(anr, bsn, ai) {
				if (!ai || ai.length == 0) {
					ai=$scope.ai;
				}
				if (!anr) {
					anr=$scope.anr;
				}
				if (!bsn) {
					bsn=$scope.bsn;
				}
				
				$("#insertAI").addClass('disabled');
				return $http.post(URL_CONTEXT_PATH + "/save_ai_gba?anr=" + anr + "&bsn=" + bsn, {
							ai : ai
						}
					).success(function(data) {
						if (data.status != "ok") {
							$scope.alert="alert alert-danger"
							$scope.alertMessage = "Fout: " + data.error;
							$scope.errorMessages.push ("Fout bij opslaan afnemerindicatie bij PL anr " + anr + ", foutmelding:" + data.error);
							console.error("anr: " + anr + ", error: " + data.error);
							console.error(ai);
							$scope.countAINotOK += ai.length;
						} else {
							$scope.alert="alert alert-success"
							$scope.alertMessage = "Afnemersindicatie(s) bij PL " + anr + " opgeslagen";
							$scope.countAIOK += ai.length;
						}
						$("#insertAI").removeClass('disabled');
					}).error(function(){
						$("#insertAI").removeClass('disabled');
					});
			}
			
			function setValuesForSave(data, i) {
				$("input[value='" + INPUT_TYPE_CSV +  "']").prop('checked', true);
				setDefaults();
				$scope.alert="alert alert-success"
				$scope.alertMessage = data.file + " verwerken";
				
				$scope.ai=[];
				$("#to").html(parseCsv(data.data));
				$("input[value='" + INPUT_TYPE_LG01 +  "']").prop('checked', true);		
				$scope.excel=data.excel;
		
				var promise = $scope.insertPL(assemble(), data.excel, save[i]['anr'], save[i]['bsn'], save[i]['ai']);
			}
			
			$scope.loadAll = function() {
				save=[];
				$scope.ai=[];
				$scope.errorMessages=[];

				$scope.countPLOK = 0;
				$scope.countPLNotOK = 0;
				$scope.countAIOK = 0;
				$scope.countAINotOK = 0;
				
				$("#loadAll").addClass('disabled');
				
				$http.get(URL_CONTEXT_PATH + "/get_csv")
					.success(function(data) {
						$http.get(URL_CONTEXT_PATH + "/clean_gba_db")
							.success(function(data2) {
								var i = 0;
								$interval(function(data) {
									save[i]={};
									setValuesForSave(data[i], i);
									i++;
								}, 500, data.length, true, data);
		
								$("#loadAll").removeClass('disabled');
								var modal = $('#notificationModal')
									.one('show.bs.modal', function(e) {
										$(this).find("#continue").hide();
									});
								modal.modal('show');
							}
						);						
					})
					.error(function() {
						$("#loadAll").removeClass('disabled');
					}
				);
			}
		}
		
	]);
