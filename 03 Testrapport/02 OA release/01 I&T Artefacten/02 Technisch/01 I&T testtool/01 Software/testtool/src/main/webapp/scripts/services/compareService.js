myApp
	.factory('compareService', ['$http', 'tidyService', '_', function($http, tidyService, _) {
		
		var groups = {};
		var groupObj = {};
		var keys = [];
		var currentGroup = '';

		function groupHead(group) {
			groupObj={};
		}
		
		function row(group, key, value) {
			if (keys.indexOf(key) == -1) {
				keys.push(key);
			}
			groupObj[key] = value;
		}
		
		function groupEnd(group) {
			if (!groups[group]) {
				groups[group] = [];
			}
			groups[group].push(groupObj);
			groups[group].sort(function(a, b) {
				var first, second;
				// create comparison data
				for (var key in a) {
					first += a[key];
					second += b[key]
				}
				
				if (first > second) {
					return 1;
				} else if (first == second) {
					return 0;
				} else {
					return -1;
				}
			});
		}
		
		function parseLO3 (berichtnummer, data) {
			var returnData = {};
			for (var property in data) {
				if (data.hasOwnProperty(property)) {
					if (!returnData[property]) {
						returnData[property] = {};
					}
					// parse messages
					for (var key in data[property]) {
						if (data[property].hasOwnProperty(key)) {
							tidyService.parse(data[property][key], tidyService.getHeaderLength(berichtnummer), 
											groupHead, row, groupEnd);
							returnData[property] = groups;
							groups = {};
						}
					}
				}
			}
			
			return returnData;
		}

		function fixBRPResult(data, headers, script) {
			var returnData = [];
			for (var i = 0; i < 2; i ++) {		
				var key;
				for (key in data.data[i]["Resultaat"]) { break; };
				if (key && data.data[i]["Resultaat"]) {
					if (angular.isArray(data.data[i]["Resultaat"][key])) {
						returnData[i] = data.data[i]["Resultaat"][key];
					} else {
						var element = data.data[i]["Resultaat"][key];
						data.data[i]["Resultaat"][key] = [];
						data.data[i]["Resultaat"][key].push(element);
						
						returnData[i] = data.data[i]["Resultaat"][key];
					}
				} else {
					returnData[i] = [];
				}
			}

			var newData=[];
			for (var i = 0; i < returnData.length; i ++) {
				headers[i]=[];
				newData[i]=[];
				for (var j = 0; j < returnData[i].length; j ++) {
					var keys=_.keys(returnData[i][j]);

					// puntjes tellen
					var count=0;
					for (var k = 0; k < keys.length; k ++) {
						var newCount=keys[k].replace(/[^.]*/g, '').length;
						if (count == 0 || newCount < count) {
							count=newCount;
						}
					}

					// maak replacement string voor keys
					var replaceKeys="";
					for (var k = 0; k < count; k ++) {
						replaceKeys+=("[^.]*\.");
					}

					// maak replacement string voor headers
					var replaceHeaders="";
					for (var k = 0; k < count - 1; k ++) {
						replaceHeaders+=("[^.]*\.");
					}

					var keyUniq={};
					for (var k = 0; k < keys.length; k ++) {
						var val=keys[k].replace(new RegExp(replaceHeaders), '').replace(/\.[^.]*$/, '');
						
						// groepering van de output met nummertje achter
						var newKey=BRP_LEVERING_HEADERS[keys[k].replace(/\.[^.]*$/, '').replace(/\d*\./g, '.').replace(/\d*$/, '')] 
							+ " " + keys[k].replace(new RegExp(replaceHeaders), '').replace(/^[^.]*?(\d*)\..*/, "$1");
						// eerste branch
						val = val.replace(/([^.]*)\.?.*/, '$1');
						if (!keyUniq[newKey]) {
							keyUniq[newKey]=[];	
						} else if (keyUniq[newKey].indexOf(val) == -1) {
							keyUniq[newKey].push(val.replace(/([^.]*)\.?.*/, '$1'));
						}
					}
					
					newData[i][j]={};
					for (var k = 0; k < keys.length; k ++) {
						var key=keys[k];
						// laatste leaf eruit, alle nummers (+ laatste) eraf + eerste nummers
						var newKey=BRP_LEVERING_HEADERS[key.replace(/\.[^.]*$/, '').replace(/\d*\./g, '.').replace(/\d*$/, '')] 
							+ " " + key.replace(new RegExp(replaceHeaders), '').replace(/^[^.]*?(\d*)\..*/, "$1");
						
						// undefined key komt doordat een path dubbel geconfigureerd is (bijv betrokkingheid huwelijk en 
						// betrokkenheid partnerschap: .partner.huwelijk of .partner.geregistreerdpartnerschap
						// pak dan maar de laatste niet "undefined " key, welke er op dat moment al inzit
						if (/undefined \d*/.test(newKey)) {
							for (var tempKey in keyUniq) {
								if (newKey == "undefined ") {
									newKey=tempKey;
								} else if (/undefined \d*/.test(newKey)) {
									var count=newKey.replace(/.* (\d*)$/, '$1');
									// nu met het correcte getalletje
									if (new RegExp(" " + count + "$").test(tempKey)) {
										newKey=tempKey;
									}
								}
							}
						}
						
						headers[i].push(newKey);
						if (!newData[i][j][newKey]) {
							newData[i][j][newKey]={};
						}

						if (script.match(/^99/) != null) {
							newData[i][j][newKey][key]=returnData[i][j][key];
						} else {
							
							var newSubKey=key.replace(new RegExp(replaceKeys), '');
							// toon vanaf de een na laatste punt of vanaf de laatste punt
							if (keyUniq[newKey].length != 1) {								
								newSubKey=key.replace(new RegExp(replaceHeaders), '')
							}

							for (property in BRP_LEVERING_KEYS) {
								newSubKey=newSubKey.replace(new RegExp(property), BRP_LEVERING_KEYS[property]);	
							}
							
							newData[i][j][newKey][newSubKey]=(returnData[i][j][key]);
						}
					}
				}

				headers[i]=_.uniq(headers[i]);
			}

			returnData = newData;
			return returnData;
		}
		
		return {
			parseLO3 : parseLO3,
			fixBRPResult: fixBRPResult
		};
	}]);
