$(document).ready(function(){
    
   
   var agency = new Bloodhound({
		datumTokenizer: function (datum) {
			return Bloodhound.tokenizers.whitespace(datum.value);
		},
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		remote: {
			url: '../agency/agencies?name=%QUERY',
			filter: function (data) {
				return $.map(data, function (ct) {
					return {
						name: ct.name,
						value: ct.id
					};
				});
			}
		}
	});
	
   agency.initialize(); // Instantiate the Typeahead UI
	
	$('.typeahead').typeahead({
		  hint: true,
		  highlight: true,
		  minLength: 1
		}, {
		displayKey: 'name',
		source: agency.ttAdapter()
	}).on('typeahead:selected typeahead:autocompleted typeahead:matched', function(event, data){
		$("#agencyId").val(data.value);    
   });
   
   $('.add-attachment').click(function(){
       console.log('came');
       $(this).parent().before('<div class="col-sm-3 add-margin"> <input type="file" class="form-control" required> </div>');
   });
   
   $('#measurement').change(function(){
	   calculateTax();
	});
   $('#subCategory').change(function(){
	   calculateTax();
	});
   $('#unitOfMeasure').change(function(){
	   calculateTax();
	});
   $('#rateClass').change(function(){
	   calculateTax();
	});
  
   
   $('#category').change(function(){
	   
	   if (this.value === '') {
			return;
		} else {
			$.ajax({
				type: "GET",
				url: "/adtax/hoarding/subcategories",
				cache: true,
				dataType: "json",
				data:{'categoryId' : this.value}
			}).done(function(value) {
				$('#subCategory option:gt(0)').remove();
				$.each(value, function(index, val) {
				     $('#subCategory').append($('<option>').text(val.description).attr('value', val.id));
				});
				if(subcategory !== '') {
					$("select#subCategory").val(subcategory); 
					subcategory = '';
				}
			});
		}
	});
   
   $('#adminBoundryParent').change(function(){
		if (this.value === '') {
			return;
		} else {
			$.ajax({
				type: "GET",
				url: "/adtax/hoarding/child-boundaries",
				cache: true,
				dataType: "json",
				data:{'parentBoundaryId' : this.value}
			}).done(function(value) {
				$('#adminBoundry option:gt(0)').remove();
				$.each(value, function(index, val) {
				     $('#adminBoundry').append($('<option>').text(val.name).attr('value', val.id));
				});
				if(adminBoundry !== '') {
					$("select#adminBoundry").val(adminBoundry); 
					adminBoundry = '';
				}
			});
		}
	});
   
   $('#revenueBoundaryParent').change(function(){
		if (this.value === '') {
			return;
		} else {
			$.ajax({
				type: "GET",
				url: "/adtax/hoarding/child-boundaries",
				cache: true,
				dataType: "json",
				data:{'parentBoundaryId' : this.value}
			}).done(function(value) {
				$('#revenueBoundary option:gt(0)').remove();
				$.each(value, function(index, val) {
				     $('#revenueBoundary').append($('<option>').text(val.name).attr('value', val.id));
				});
				if(revenueBoundary !== '') {
					$("select#revenueBoundary").val(revenueBoundary); 
					revenueBoundary = '';
				}
			});
		}
	});
   
   $('input[type=file]').on('change.bs.fileinput',function(e) {
	   EXIF.getData(e.target.files[0], function() {
		   if (EXIF.getTag(this, "GPSLatitude")) {
				var imagelat = EXIF.getTag(this, "GPSLatitude"),
				imagelongt = EXIF.getTag(this, "GPSLongitude");
				var formatted_lat = format_lat_long(imagelat.toString());
				var formatted_long = format_lat_long(imagelongt.toString());
				$.ajax({
					type: "POST",
					url: 'https://maps.googleapis.com/maps/api/geocode/json?latlng='+formatted_lat+','+formatted_long+'&sensor=true',
					dataType: 'json',
					success : function(data){
						$('#latitude').val(formatted_lat);
						$('#longitude').val(formatted_long);
					}
				});
		   }
	   });
   });
   
  
   function format_lat_long(latorlong) {
		var loc_arry = latorlong.split(",");
		var degree= parseFloat(loc_arry[0]);
		var minutes= parseFloat(loc_arry[1]);
		var seconds= parseFloat(loc_arry[2]);
		var formatted = degree+((minutes*60)+seconds)/3600;
		
		return formatted;
	}
   
   function calculateTax()
   {
	      
	   if ($('#rateClass').val() === '' || $('#unitOfMeasure').val() === '' ||  $('#subCategory').val() === '' || $('#measurement').val() === '') {
			return;
		} else {
		
		//	alert('All fields are entered');	
			$.ajax({
				type: "GET",
				url: "/adtax/hoarding/calculateTaxAmount",
				cache: true,
				dataType: "json",
				data:{
					'unitOfMeasureId' : $('#unitOfMeasure').val(),
					'measurement' : $('#measurement').val(),
					'subCategoryId' : $('#subCategory').val(),
					'rateClassId' : $('#rateClass').val()
					}
			}).done(function(value) {
				//alert(value);
				$('#taxAmount').val(value); 
			
			});
		}
	
   }
   
   $('#category').trigger('change');
   $('#adminBoundryParent').trigger('change');
   $('#revenueBoundryParent').trigger('change');
});