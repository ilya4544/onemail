'use strict';

app.controller('TaskController', function($scope, $firebase, $http, FURL, $location, $routeParams, toaster) {

	var ref = new Firebase(FURL);
	var fbTasks = $firebase(ref.child('tasks')).$asArray();
	var taskId = $routeParams.taskId;
	var mails;
	$http.get('getEmails?email=test').success(function (data) {
    	mails = data;
    	$scope.tasks = data; // response data 
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

	$scope.updateTask = function(task) {
		$scope.selectedTask.$save(task);
		toaster.pop('success', "Task is updated.");
		$location.path('/');
	};

});