$("#scriptr").change(function() {
    let file = $("#scriptr").val().split("\\");
    if (file != "") {
        let ext = file[file.length - 1];
        if (ext.split(".")[1] == "JSON" || ext.split(".")[1] == "json") {
        	$("#lf").html(file[file.length - 1]);
        } else {
        	 $("#scriptr").val("");
            alert("archivo no valido");
        }
    }
});

$("#fromfile").on("submit", function(e){
	if($("#scriptr").val()!=""){
	
		e.preventDefault();
		var form = $('#fromfile')[0];
	    
	    var formData = new FormData(form);
	   
	    
	    $.ajax({
	        url: "subir",
	        type: "post",
	        enctype: 'multipart/form-data',
	        data:formData,
	        processData: false,
	        contentType: false,
	        cache: false,
	        timeout: 600000,
	        success: function(response){
	        	 $("#repuestafile").html(response);
	        }
	    })
	}else{
		alert("Archivo no seleccionado")
		return false;
	}
	
});

$("#listScrips").click(function(){
	$.post( "scripts", function( data ) {
		  $( ".container" ).html( data );
	});
	
	
})
//.prev()
$(".container").on("click",".buttonExecScript",function(){
	let pa=$(this).parents();
	//alert($(pa).prev().html());
	
	data = {
		file : $(pa).prev().html()
	}
	
	 $.ajax({
	        url: "ejecuta",
	        type: "post",
	        data:data,
	        beforeSend: function(){
	        	
	        	 $("#ejecutando").html("<img src='https://cdn.dribbble.com/users/172519/screenshots/3520576/dribbble-spinner-800x600.gif' width='200' alt'Cargando'/>");
	        },
	        success: function(response){
	        	 $("#ejecutando").html(response);
	        }
	    })
})