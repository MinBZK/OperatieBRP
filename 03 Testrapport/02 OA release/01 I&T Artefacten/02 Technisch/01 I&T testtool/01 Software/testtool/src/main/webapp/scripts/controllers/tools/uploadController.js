myApp.controller('uploadController', [
	'$scope', 
	'$routeParams',
	'$http',
	'$route',
	'$location',
	'Upload',
	function($scope, $routeParams, $http, $route, $location, Upload) {

		$scope.running=false;
		$scope.$watch('files', function () {
			$scope.upload($scope.files);
		});
		
		$scope.$watch('file', function () {
			if ($scope.file != null) {
				$scope.files = [$scope.file]; 
			}
		});
		
		$scope.upload = function(files) {
			if (files && files.length) {
				for (var i = 0; i < files.length; i++) {
					var file = files[i];
					if (file.error) {
						console.err(file);
					} else {						
						Upload.http({
							url:  URL_CONTEXT_PATH + "/upload?filename=" + file.name,
							headers : {
								'Content-Type': file.type,
								'Content-Length': file.size,
								transformRequest: angular.identity
							},
							data: file
						}).then (
							function (response) {
								$scope.showUploadedFiles();
							}
						);						
					}
				}
			}
		}

		$scope.showUploadedFiles = function () {
			$http.get(URL_CONTEXT_PATH + "/upload_show_files")
				.success (function (data) {
					$scope.uploaded_files=data;
				}
			);
			
			checkAvailable();
		}
		
		function checkAvailable() {
			$http.get(URL_CONTEXT_PATH + "/upload_available")
				.success (function (data) {
					if (data.length > 0) {
						$scope.showResults=true;
					} else {
						$scope.showResults=false;
					}
				}
			);
		}
		
		$scope.deleteFile = function(name) {
			$http.get(URL_CONTEXT_PATH + "/upload_delete_file?name=" + name)
				.success (function (data) {
					$scope.showUploadedFiles();
				}
			);							
		}
		
		$scope.log = '';		

		$scope.execute = function () {
			$scope.running=true;
			$("#execute").addClass('disabled');
			$http.get(URL_CONTEXT_PATH + "/execute")
				.success (function (data) {
					$scope.showUploadedFiles();
					$scope.running=false;
					checkAvailable();
					$("#execute").removeClass('disabled');
				}
			);							
		}

		$scope.openResults = function() {
			$location.path("/Testtool/Tools/Upload/Stages");
		}
		
		$scope.resetAll = function() {
			$http.get(URL_CONTEXT_PATH + '/delete_pls?all=true')
				.success(function(data) {
					checkAvailable();
				});
		}
	}
]);
