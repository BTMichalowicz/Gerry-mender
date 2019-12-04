   /*Universal Functions*/
   function moveZoomControl() {
    if (sliderState === 1) { 
        container = map.zoomControl.getContainer(),
            containerTop = 350 + 'px';
        container.style.position = 'absolute';
        container.style.left = containerTop;

    } else {
        container = map.zoomControl.getContainer(),
            containerTop = 0 + 'px';
        container.style.position = 'absolute';
        container.style.left = containerTop;
        }
    }

    function show_value(x, id){
        document.getElementById(id).innerHTML=x;
    }
    
   /*Homepage*/

function dropdown() {
    document.getElementById("stateDropdown").classList.toggle("show");
  }
  
  window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
      var dropdowns = document.getElementsByClassName("dropdown-content");
      var i;
      for (i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
          openDropdown.classList.remove('show');
        }
      }
    }
  }

   /*State Pages*/
  var currentState;
  function setCurrentState(stateCode){
        currentState = stateCode;
  }

   function onEachStateFeature(feature, layer) {
    //bind click
        layer.on('click', function (e) {
            //alert(feature.properties.name);
            if (feature.properties.name == "Texas"){
                window.location.href = "GM-Texas.html";
                setCurrentState("TX");
            }
            else if (feature.properties.name == "North Carolina"){
                window.location.href = "GM-NC.html";
                setCurrentState("NC");
            }
            else if (feature.properties.name == "Florida"){
                window.location.href = "GM-Florida.html";
                setCurrentState("FL");
            }
        });
    }

    //Layer functions
    var pLayer;
    //called when layer is precincts
    function setPLayer(){
        pLayer = true;
    }

    //called when layer is districts
    function setDLayer(){
        pLayer = false;
    }

    //Mouseover                                                                                                                        
    function onEachFeature(feature, layer) {
        var currentColor;
        
        layer.on("mouseover", function (e) {    
            if(pLayer){
                currentColor = "red";
            }
            else currentColor = district_color.get(feature.properties.DISTRICT);
            layer.setStyle({fillColor : "white"});
            document.getElementById("small-info-window").style.width = "220px";
            if(sliderState == 1)
                document.getElementById("small-info-window").style.left = "370px";
            else
                document.getElementById("small-info-window").style.left = "50px";
            $(fillOutSmallWindow(feature));
        });
        layer.on("mouseout", function (e) {
            layer.setStyle({color:"black", fillColor: currentColor, weight:1, opacity: 0.8, fillOpacity: 0.5});
            //$(popup1.remove());
            document.getElementById("small-info-window").style.width = "0";
        });
        layer.on("click", function (e) {
            $(toggleInfoSlider( feature ));
        });
    }

    /*Sliding Menus*/
    function toggleSlider() {
        if (sliderState === 0) { //Open slider
            document.getElementById("slide-menu").style.width = "360px";
            sliderState = 1;
            setTimeout(moveZoomControl, 0);
            document.getElementsByClassName('tablinks')[1].click();
        } else {
            document.getElementById("slide-menu").style.width = "0";
            sliderState = 0;
            setTimeout(moveZoomControl, 200);
        }
    }

    function toggleInfoSlider( feature ) {
            document.getElementById("slide-info").style.width = "350px";
            infoStat = feature.properties.id;
            fillOutTable(feature);
    }

    function closeInfo() {
        document.getElementById("slide-info").style.width = "0";
        infoStat = "null";
    }


    /*Menu Tabs*/
    function openTab(evt, tabName) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
          tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
          tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        document.getElementById(tabName).style.display = "block";
        evt.currentTarget.className += " active";
      }

      //State Data Tabs
      function show2018Data(stateCode){
        if (stateCode == 'FL'){
            
        }
        else if (stateCode == 'TX'){

        }
        
      }

      function show2016CData(stateCode){
        if (stateCode == 'FL'){
            
        }
        else if (stateCode == 'TX'){

        }
      }

      function show2016PData(stateCode){
        if (stateCode == 'FL'){
            
        }
        else if (stateCode == 'TX'){

        }
      }

/***********************************************************************************/
      function fillOutSmallWindow(feature){
        $(document).ready(function () {
            $("#small-info-table tr").remove();
                if (map.hasLayer(precincts)){
                    var year;
                    if($('#2016C').is(':checked') || $('#2016P').is(':checked'))
                        year = 2016;
                    else
                        year = 2018;
                    var etype;
                    if($('#2016C').is(':checked') || $('#2018C').is(':checked'))
                        etype = "CONGRESSIONAL";
                    else
                        etype = "PRESIDENT";
                    var formData = new FormData();
                    formData.append("id", feature.properties.countypct);
                    formData.append("mapLevel", "precinct");
                    formData.append("year", year);
                    formData.append("electionType", etype);
                    var result = $.parseJSON($.ajax({
                        url: "http://localhost:8080/getSelectArea",
                        type: "POST",
                        data:formData,
                        processData : false,
                        contentType : false,
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            console.log(data);
                        },
                        error: function (result) {
                            alert(result);
                        }
                    }).responseText);
                    // test output
                    console.log(result[0]);
                    $("#small-info-table tr").remove();
                    var items = [
                        {Attr: "Name", Amout: result[0].nameID},
                        {Attr: "population", Amout: result[0].totalPop},
                        {Attr: "White", Amout: result[0].white_pop},
                        {Attr: "Hispanic", Amout: result[0].hispanic_pop},
                        {Attr: "Asian", Amout: result[0].asian_pop},
                        {Attr: "Republican", Amout: result[1].numrepub},
                        {Attr: "Democratic", Amout: result[1].numdemocrat},
                    ];
                }
                else{
                    var year;
                    if($('#2016C').is(':checked') || $('#2016P').is(':checked'))
                        year = 2016;
                    else
                        year = 2018;
                    var etype;
                    if($('#2016C').is(':checked') || $('#2018C').is(':checked'))
                        etype = "CONGRESSIONAL";
                    else
                        etype = "PRESIDENT";
                    var formData = new FormData();
                    formData.append("id", feature.properties.DISTRICT);
                    formData.append("mapLevel", "district");
                    formData.append("year", year);
                    formData.append("electionType", etype);
                    var result = $.parseJSON($.ajax({
                        url: "http://localhost:8080/getSelectArea",
                        type: "POST",
                        data:formData,
                        processData : false,
                        contentType : false,
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            console.log(data);
                        },
                        error: function (result) {
                            alert(result);
                        }
                    }).responseText);
                    // test output
                    console.log(result[0]);
                    $("#small-info-table tr").remove();
                    var items = [
                        {Attr: "Name", Amout: result[0].nameID},
                        {Attr: "population", Amout: result[0].totalPop},
                        {Attr: "White", Amout: result[0].white_pop},
                        {Attr: "Hispanic", Amout: result[0].hispanic_pop},
                        {Attr: "Asian", Amout: result[0].asian_pop},
                        {Attr: "Republican", Amout: result[1].numrepub},
                        {Attr: "Democratic", Amout: result[1].numdemocrat},
                    ];
        }

            $("#smallInfoTemplate").tmpl(items).appendTo("#small-info-table tbody");
        });
      }

    function fillOutTable(feature) {
        $(document).ready(function () {
            $("#itemList tr").remove();
            var year;
            if($('#2016C').is(':checked') || $('#2016P').is(':checked'))
                year = 2016;
            else
                year = 2018;
            var etype;
            if($('#2016C').is(':checked') || $('#2018C').is(':checked'))
                etype = "CONGRESSIONAL";
            else
                etype = "Presidential";
            var formData = new FormData();
            formData.append("id", feature.properties.DISTRICT);
            formData.append("mapLevel", "district");
            formData.append("year", year);
            formData.append("electionType", etype);
            var result = $.parseJSON($.ajax({
                url: "http://localhost:8080/getSelectArea",
                type: "POST",
                data:formData,
                processData : false,
                contentType : false,
                dataType: "json",
                async: false,
                success: function (data) {
                    console.log(data);
                },
                error: function (result) {
                    alert(result);
                }
            }).responseText);
            // test output
            console.log(result[0]);
            $("#small-info-table tr").remove();
            var items = [
                {Attr: "Name", Amout: result[0].nameID},
                {Attr: "population", Amout: result[0].totalPop},
                {Attr: "White", Amout: result[0].white_pop},
                {Attr: "Hispanic", Amout: result[0].hispanic_pop},
                {Attr: "Asian", Amout: result[0].asian_pop},
                {Attr: "Republican", Amout: result[1].numrepub},
                {Attr: "Democratic", Amout: result[1].numdemocrat},
            ];
            $("#itemTemplate").tmpl(items).appendTo("#itemList tbody");
        });
    }



    var district_color = new Map();
district_color.set(1, '#ff6d3a');
district_color.set(2, '#1531ff');
district_color.set(3, '#B22222');
district_color.set(4, '#1E90FF');
district_color.set(5, '#FFD700');
district_color.set(6, '#ADFF2F');
district_color.set(7, '#4B0082');
district_color.set(8, '#CD853F');
district_color.set(9, '#40E0D0');
district_color.set(10, '#FF6347');
district_color.set(11, '#6A5ACD');
district_color.set(12, '#0FFD70');
district_color.set(13, '#B0C4DE');
district_color.set(14, '#FF0000');
district_color.set(15, '#50b2ff');
district_color.set(16, '#5effc2');
district_color.set(17, '#20f8ff');
district_color.set(18, '#ffa71d');
district_color.set(19, '#ff2fab');
district_color.set(20, '#faffbd');
district_color.set(21, '#cbcdff');
district_color.set(22, '#ffb7b9');
district_color.set(23, '#adff8f');
district_color.set(24, '#eaff78');
district_color.set(25, '#6fff6b');
district_color.set(26, '#e584ff');
district_color.set(27, '#51caff');
district_color.set(28, '#ffe46e');
district_color.set(29, '#57b8ff');
district_color.set(30, '#ff71c7');
district_color.set(31, '#90ff83');
district_color.set(32, '#ffd48f');
district_color.set(33, '#58c3ff');
district_color.set(34, '#ff9de8');
district_color.set(35, '#a7ff74');
district_color.set(36, '#ff726f');
