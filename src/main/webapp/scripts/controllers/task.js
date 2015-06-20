'use strict';

app.controller('TaskController', function($scope, $firebase, $http, FURL, $location, $routeParams, toaster) {

	var ref = new Firebase(FURL);
	var fbTasks = $firebase(ref.child('tasks')).$asArray();
	var taskId = $routeParams.taskId;
	var token = $routeParams.token;
	var mails;
	$http.get('getEmails?token=test').success(function (data) {
    	mails = data;
    	$scope.tasks = data;
    	var arrayLength = Object.keys($scope.tasks).length;
		for (var i = 0; i < arrayLength; i++) {
			var d = new Date($scope.tasks[i].date);
    		$scope.tasks[i].date = d.getTime();
		}
    	if(taskId) {
			$scope.selectedTask = getTask(taskId, $scope.tasks);
		}	
	}).error(function (data) {
		console.log(data);
    	console.log("BLOG failed");
	});
	


	function getTask(taskId, mails) {
		var arrayLength = Object.keys(mails).length;
		for (var i = 0; i < arrayLength; i++) {
			if (mails[i]._id == taskId)
				return mails[i];
		}
		return null;
	};

	$scope.getNumber = function(num) {
    	return new Array(num);   
	}

	$scope.openMail = function(_id) {
		$http.get('openEmail?id='+_id).success(function (data) {
    		console.log("Yeah");	
		});
	};

	$scope.postTask = function(task) {
		$scope.tasks.$add(task);
		toaster.pop('success', 'Task created successfully.');
		$location.path('/');
	};	

	$scope.login = function() {
		$http.get('login').success(function (data) {
    		console.log("Yeah");	
		});
	};

	$scope.updateProfile = function(email) {
		//$scope.selectedTask.$save(task);
		console.log(email);
		toaster.pop('success', "Настройки успешно применены.");
		$location.path('/browse');
	};

	$scope.updateTask = function(task) {
		$scope.selectedTask.$save(task);
		toaster.pop('success', "Task is updated.");
		$location.path('/');
	};

});