var editNew = 0;
var contactid = 0;
var dataRec;
var locations = [];
var geocoder;
var map;
var timeout = 600;

$(function() {
	initialize();
});

function initialize() {
	$(".div2").hide();
	$("#map").hide();
	var table = document.getElementById('contactTab');
	for (var r = 1, n = table.rows.length; r < n; r++) {
		var textboxVal = table.rows[r].cells[3].innerHTML;
		locations.push(textboxVal);
	}
	for (var x = 0; x < locations.length; x++) {
		codeAddress(locations[x]);
	}
	geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(30.3753, 69.3451);
	var mapOptions = {
		zoom : 5,
		center : latlng
	}
	map = new google.maps.Map(document.getElementById('map'), mapOptions);

}

function codeAddress(address) {
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode({
		'address' : address
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			var marker = new google.maps.Marker({
				map : map,
				position : results[0].geometry.location,
				title : address
			});
		}
	});
}

function loadEvents() {
	$(".deletecontact").click(function(event) {
		deleteContact(event.target.id);
		event.preventDefault();
	});
	$(".edit_link").click(function(event) {
		event.preventDefault();
		$('#editform').find(':text').val("");
		populateForm(event.target.id);
		contactid = event.target.id;
		window.setInterval($(".div2").fadeToggle("slow"), 1000);
	});
}

$(document).ready(function() {
	$(".comment_link").click(function(event) {
		event.preventDefault();
		editNew = 1;
		$('#editform').find(':text').val("");
		$(".div2").fadeToggle("slow");
	});
	$(".mapt").click(function(event) {
		event.preventDefault();
		$('#dataDiv').fadeToggle();
		$("#map").fadeToggle();
	});

	$('#usearch').keyup(function() {
		var searchText = $(this).val().toLowerCase();
		$.each($("#contactTab tbody tr"), function() {
			if ($(this).text().toLowerCase().indexOf(searchText) === -1)
				$(this).hide();
			else
				$(this).show();
		});
	});
});

var saverigger;

function cancelForm() {
	$(".div2").hide();
	$('#editform').find(':text').val("");
	editNew = 0;
	contactid = 0;
}