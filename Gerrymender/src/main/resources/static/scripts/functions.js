/*Universal Functions*/
function numberWithCommas(x) {
    var parts = x.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
}
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
function show_value(x, id) {
    document.getElementById(id).innerHTML = x;
}
function enable(id, ID) {
    document.getElementById(id).style.color = 'black';
    $(ID).prop('disabled', false);
}
function disable(id, ID) {
    document.getElementById(id).style.color = 'lightgrey';
    $(ID).prop('disabled', true);
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
function toggleInfoSlider(feature) {
    document.getElementById("slide-info").style.width = "350px";
    infoStat = feature.properties.id;
    getInfo(feature, true);
}
function closeInfo() {
    document.getElementById("slide-info").style.width = "0";
    infoStat = "null";
}

/*Homepage*/
function dropdown() {
    document.getElementById("stateDropdown").classList.toggle("show");
}
window.onclick = function (event) {
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
};
function toggleHomeHelp() {
    if (sliderState === 0) { //Open slider
        document.getElementById("slide-menu").style.width = "360px";
        sliderState = 1;
        setTimeout(moveZoomControl, 200);
    } else {
        document.getElementById("slide-menu").style.width = "0";
        sliderState = 0;
        setTimeout(moveZoomControl, 0);
    }
}

/*State Pages*/
window.onload = function () {
    //Threshold Sliders
    var popSlider = document.getElementById('popSlider');

    noUiSlider.create(popSlider, {
        start: [60],
        connect: 'lower',
        range: {
            'min': [51],
            'max': [100]
        },
    });

    popSlider.noUiSlider.on('update', function (values, handle) {
        document.getElementById('popThresh_value').innerHTML = values[handle];
        popUpdated(values[handle]);
    });

    var voteSlider = document.getElementById('voteSlider');

    noUiSlider.create(voteSlider, {
        start: [60],
        connect: 'lower',
        range: {
            'min': [51],
            'max': [100]
        },
    });

    voteSlider.noUiSlider.on('update', function (values, handle) {
        document.getElementById('voteThresh_value').innerHTML = values[handle];
        voteUpdated(values[handle]);
    });

    //Range Slider
    var slider = document.getElementById('percentage-slider');

    noUiSlider.create(slider, {
        start: [20, 80],
        range: {
            'min': [0],
            'max': [100]
        },
        connect: true
    });
    var sliderValues = [
        document.getElementById('lower-value'),
        document.getElementById('upper-value')
    ];

    slider.noUiSlider.on('update', function (values, handle) {
        sliderValues[handle].innerHTML = values[handle];
    });
};
function updateState(id) {
    var formData = new FormData();
    formData.append("id", id);
    $.ajax({
        url: "http://localhost:8080/updateState",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
        async: true
    });
}
var currentState;
function setCurrentState(stateCode) {
    currentState = stateCode;
}
function getCurrentState() {
    if (currentState == 'FL') return "Florida";
    if (currentState == 'NC') return "North Carolina";
    if (currentState == 'TX') return "Texas";
}
function onEachStateFeature(feature, layer) {

    //bind click
    layer.on('click', function (e) {
        //alert(feature.properties.name);
        if (feature.properties.name == "Texas") {
            window.location.href = "GM-Texas.html";
            setCurrentState('TX');
        } else if (feature.properties.name == "North Carolina") {
            window.location.href = "GM-NC.html";
            setCurrentState('NC');
        } else if (feature.properties.name == "Florida") {
            window.location.href = "GM-Florida.html";
            setCurrentState('FL');
        }
    });
}

//Layer functions
var pLayer;
function setPLayer() {
    pLayer = true;
}
function setDLayer() {
    pLayer = false;
}
function onEachPFeature(feature, layer) {
    precinctLayer.addLayer(layer);
    var currentColor;
    layer.on("mouseover", function (e) {
        currentColor = "red";
        layer.setStyle({fillColor: "white"});
        document.getElementById("small-info-window").style.width = "220px";
        if (sliderState == 1)
            document.getElementById("small-info-window").style.left = "370px";
        else
            document.getElementById("small-info-window").style.left = "50px";
        $(getInfo(feature, false));
    });
    layer.on("mouseout", function (e) {
        layer.setStyle({color: "black", fillColor: currentColor, weight: 1, opacity: 0.8, fillOpacity: 0.5});
        //$(popup1.remove());
        document.getElementById("small-info-window").style.width = "0";
    });
    layer.on("click", function (e) {
        $(toggleInfoSlider(feature));
    });
}
function onEachDFeature(feature, layer) {
    districtLayer.addLayer(layer);
    var currentColor;
    layer.on("mouseover", function (e) {
        currentColor = district_color.get(feature.properties.DISTRICT);
        layer.setStyle({fillColor: "white"});
        document.getElementById("small-info-window").style.width = "220px";
        if (sliderState == 1)
            document.getElementById("small-info-window").style.left = "370px";
        else
            document.getElementById("small-info-window").style.left = "50px";
        $(getInfo(feature, false));
    });
    layer.on("mouseout", function (e) {
        layer.setStyle({color: "black", fillColor: currentColor, weight: 1, opacity: 0.8, fillOpacity: 0.5});
        //$(popup1.remove());
        document.getElementById("small-info-window").style.width = "0";
    });
    layer.on("click", function (e) {
        $(toggleInfoSlider(feature));
    });
}

//Menu Tabs
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

//Info Panels
function getYear() {
    if ($('#2016C').is(':checked') || $('#2016P').is(':checked')) return 2016;
    else return 2018;
}
function getEType() {
    if ($('#2016C').is(':checked') || $('#2018C').is(':checked')) return "CONGRESSIONAL";
    else return "PRESIDENT";
}
function getInfo(feature, clicked) {
    $(document).ready(function () {
        $("#small-info-table tr").remove();
        if (clicked) $("#itemList tr").remove();
        var stateName = getCurrentState();
        var year = getYear();
        var etype = getEType();
        var formData = new FormData();
        if (pLayer) {
            formData.append("stateName", stateName);
            formData.append("id", feature.properties.countypct);
            formData.append("mapLevel", "precinct");
        } else {
            formData.append("stateName", stateName);
            formData.append("id", feature.properties.DISTRICT);
            formData.append("mapLevel", "district");
        }
        formData.append("year", year);
        formData.append("electionType", etype);
        var result = $.parseJSON($.ajax({
            url: "http://localhost:8080/getSelectArea",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            async: false,
            success: function (data) {
                console.log(data);
            },
            error: function (result) {
                alert("Error sending request: " + formData);
            }
        }).responseText);
        var items;
        if (pLayer) items = precinctItems(result);
        else items = districtItems(result);

        $("#small-info-table tr").remove();
        if (clicked) $("#itemTemplate").tmpl(items).appendTo("#itemList tbody");
        $("#smallInfoTemplate").tmpl(items).appendTo("#small-info-table tbody");
    });
}
function precinctItems(result) {
    var items = [
        {Attr: "Code", Amount: result[0].nameID},
        {Attr: "Population", Amount: numberWithCommas(result[0].totalPop)},
        {Attr: "White", Amount: numberWithCommas(result[0].white_pop)},
        {Attr: "Hispanic", Amount: numberWithCommas(result[0].hispanic_pop)},
        {Attr: "Asian", Amount: numberWithCommas(result[0].asian_pop)},
        {Attr: "Republican", Amount: numberWithCommas(result[1].numrepub)},
        {Attr: "Democratic", Amount: numberWithCommas(result[1].numdemocrat)},
    ];
    return items;
}
function districtItems(result) {
    var items = [
        {Attr: "District #", Amount: result[0].nameID},
        {Attr: "Population", Amount: numberWithCommas(result[0].totalPop)},
        {Attr: "White", Amount: numberWithCommas(result[0].white_pop)},
        {Attr: "Hispanic", Amount: numberWithCommas(result[0].hispanic_pop)},
        {Attr: "Asian", Amount: numberWithCommas(result[0].asian_pop)},
        {Attr: "Republican", Amount: numberWithCommas(result[1].numrepub)},
        {Attr: "Democratic", Amount: numberWithCommas(result[1].numdemocrat)},
    ];
    return items;
}

// Data Tab
function show2018Data(stateCode) {
    if (stateCode === 'FL') {
        document.getElementById("votesRepublican").innerHTML = "3,675,417";
        document.getElementById("percentRepublican").innerHTML = "52.35%";
        document.getElementById("votesDemocrat").innerHTML = "3,307,228";
        document.getElementById("percentDemocrat").innerHTML = "47.10%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 7,021,195";
    } else if (stateCode === 'TX') {
        document.getElementById("votesRepublican").innerHTML = "4,135,359";
        document.getElementById("percentRepublican").innerHTML = "50.40%";
        document.getElementById("votesDemocrat").innerHTML = "3,852,752";
        document.getElementById("percentDemocrat").innerHTML = "47.00%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 7,988,111";
    } else if (stateCode === 'NC') {
        document.getElementById("votesRepublican").innerHTML = "1,846,041";
        document.getElementById("percentRepublican").innerHTML = "50.39%";
        document.getElementById("votesDemocrat").innerHTML = "1,771,061";
        document.getElementById("percentDemocrat").innerHTML = "48.35%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 3,617,102";
    } else {
        alert("An error has occurred.");
    }
}
function show2016CData(stateCode) {
    if (stateCode === 'FL') {
        document.getElementById("votesRepublican").innerHTML = "4,733,630";
        document.getElementById("percentRepublican").innerHTML = "54.71%";
        document.getElementById("votesDemocrat").innerHTML = "3,985,050";
        document.getElementById("percentDemocrat").innerHTML = "45.21%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 8,947,423";
    } else if (stateCode === 'TX') {
        document.getElementById("votesRepublican").innerHTML = "4,877,605";
        document.getElementById("percentRepublican").innerHTML = "57.19%";
        document.getElementById("votesDemocrat").innerHTML = "3,160,535";
        document.getElementById("percentDemocrat").innerHTML = "37.06%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 8,038,140";
    } else if (stateCode === 'NC') {
        document.getElementById("votesRepublican").innerHTML = "2,447,326";
        document.getElementById("percentRepublican").innerHTML = "53.22%";
        document.getElementById("votesDemocrat").innerHTML = "2,142,661";
        document.getElementById("percentDemocrat").innerHTML = "46.60%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 4,589,987";
    } else {
        alert("An error has occurred.");
    }
}
function show2016PData(stateCode) {
    if (stateCode === 'FL') {
        document.getElementById("votesRepublican").innerHTML = "4,617,886";
        document.getElementById("percentRepublican").innerHTML = "48.60%";
        document.getElementById("votesDemocrat").innerHTML = "4,504,975";
        document.getElementById("percentDemocrat").innerHTML = "47.40%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 9,204,592";
    } else if (stateCode === 'TX') {
        document.getElementById("votesRepublican").innerHTML = "4,685,047";
        document.getElementById("percentRepublican").innerHTML = "52.23%";
        document.getElementById("votesDemocrat").innerHTML = "3,877,868";
        document.getElementById("percentDemocrat").innerHTML = "43.24%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 8,562,915";
    } else if (stateCode === 'NC') {
        document.getElementById("votesRepublican").innerHTML = "2,362,631";
        document.getElementById("percentRepublican").innerHTML = "49.83%";
        document.getElementById("votesDemocrat").innerHTML = "2,189,316";
        document.getElementById("percentDemocrat").innerHTML = "46.17%";
        document.getElementById("winParty").innerHTML = "Winning Party: Republican";
        document.getElementById("totalVotes").innerHTML = "Total Votes: 4,551,947";
    } else {
        alert("An error has occurred.");
    }
}

//Bloc Tab
var popSelected = false;
var voteSelected = false;
function setPopSelected() {
    popSelected = true;
}
function setVoteSelected() {
    voteSelected = true;
}
function popUpdated(value) {
    setPopSelected();
    show_value(value, 'popThresh_value');
    if (popSelected && voteSelected) {
        enable("phase0Button", "#phase0Button");
    }
}
function voteUpdated(value) {
    setVoteSelected();
    show_value(value, 'voteThresh_value');
    if (popSelected && voteSelected) {
        enable("phase0Button", "#phase0Button");
    }
}
function phase0() {
    var year = getYear();
    var eType = getEType();
    var popThresh = document.getElementById('popThresh_value').innerHTML;
    var voteThresh = document.getElementById('voteThresh_value').innerHTML;

    var formData = new FormData();
    formData.append("electionYear", year);
    formData.append("electionType", eType);
    formData.append("popThreshold", popThresh);
    formData.append("voteThreshold", voteThresh);

    var result = $.parseJSON($.ajax({
        url: "http://localhost:8080/phase0",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
        async: false,
        success: function (results) {
            phase0Formatting(results);
        },
        error: function (e) {
            alert("Error in phase 0." + e);
        }
    })).responseText;
}
function phase0Formatting(results) {
    var demBlocs = fillOutDemBlocs(results);
    var repBlocs = fillOutRepBlocs(results);
    document.getElementById("demBlocsNum").innerHTML = demBlocs;
    document.getElementById("repBlocsNum").innerHTML = repBlocs;
    document.getElementById("totalBlocs").innerHTML = (repBlocs + demBlocs);
}
function adjustRace(race) {
    if (race == "AFRICAN_AMERICAN") return "BLACK";
    else return race;
}
function fillOutDemBlocs(result) {
    var i = 0, d = 0;
    $("#demBlocTable tr").remove();
    while (result[i] != null) {
        if (result[i].party == "DEMOCRAT") {
            var demItem = [{
                pID: result[i].precinctId,
                demo: adjustRace(result[i].majorityRace),
                partyV: numberWithCommas(result[i].partyVotes),
                totalV: numberWithCommas(result[i].totalVotes),
            }];
            $("#blocTemplate").tmpl(demItem).appendTo("#demBlocTable tbody");
            d++;
        }
        i++;
    }
    return d;
}
function fillOutRepBlocs(result) {
    var i = 0, r = 0;
    $("#repBlocTable tr").remove();
    while (result[i] != null) {
        if (result[i].party == "REPUBLICAN") {
            var repItem = [{
                pID: result[i].precinctId,
                demo: adjustRace(result[i].majorityRace),
                partyV: numberWithCommas(result[i].partyVotes),
                totalV: numberWithCommas(result[i].totalVotes),
            }];
            $("#blocTemplate").tmpl(repItem).appendTo("#repBlocTable tbody");
            r++;
        }
        i++;
    }
    return r;
}

//Phase 1
var numDis;
function setNumDis(value){
    numDis = value;
}
var iterative = false;
function setIterative() {
    iterative = true;
}
function setEndOnly() {
    iterative = false;
}
function iterateMode() {
    enable("phase1Button", "#phase1Button");
    setIterative();
}
function endOnly() {
    enable("phase1Button", "#phase1Button");
    setEndOnly();
}
//indices refer to race to be used: [WHITE,AFRICAN_AMERICAN,HISPANIC,ASIAN,NATIVE_AMERICAN]
var raceList = [false, false, true, false, false];
function addToRaceList(raceIndex){
    if( raceList[raceIndex] == false) { raceList[raceIndex] = true; }
    else { raceList[raceIndex] = false; }
}
function formatRL(){
    var races = [];
    if (raceList[0] === true){ races.push("WHITE"); }
    if (raceList[1] === true){ races.push("AFRICAN_AMERICAN"); }
    if (raceList[2] === true){ races.push("HISPANIC"); }
    if (raceList[3] === true){ races.push("ASIAN"); }
    if (raceList[4] === true){ races.push("NATIVE_AMERICAN"); }
    return races;
}
function beginPhase1() {
    //hide user inputs
    document.getElementById("minorityPopSection").style.display = 'none';
    document.getElementById("popPercentSection").style.display = 'none';
    document.getElementById("desiredNumDisSection").style.display = 'none';
    document.getElementById("phase1Button").style.display = 'none';
    if (!iterative) {
        document.getElementById("iterateOrEndSection").style.display = 'none';
    }
    if (iterative) {
        enable("iterateButton", "#iterateButton");
    }
    disable("iterate", "#iterate");
    disable("endUpdate", "#endUpdate");
    disable("phase1Button", "#phase1Button");
    if(!iterative){ loopPhase1(); }
    phase1Iterate();
}

function loopPhase1(){
   alert("soon i make happen");
}
function phase1Iterate(){
    var min = document.getElementById("lower-value").innerText;
    var max = document.getElementById("upper-value").innerText;
    var races = formatRL();
    var numDis = document.getElementById("numDistrictsInput").value;
    var formData = new FormData();
    formData.append("whichRaces", races);
    formData.append("minPopPerc", min);
    formData.append("maxPopPerc", max);
    formData.append("numDistricts", numDis);
    var result = $.parseJSON($.ajax({
        url: "http://localhost:8080/phase1",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
        async: false,
        success: function(results){
            processPhase1(results);
        },
        error: function(error) {
            alert("Error: " + error);
    }
    })).responseText;
    return result;
}
var clusters = [];
function processPhase1(result){
    alert("Precinct " + result[0].t1 + " added to cluster " + result[0].t2 + ".");
}

//Phase 2
function phase2() {
    alert("how did you do this it's not even IMPLEMENTED");
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
district_color.set(37, '#2680c8');
district_color.set(38, '#268fc8');
district_color.set(39, '#ffd6bc');
district_color.set(40, '#13113f');
district_color.set(41, '#57b9ff');
district_color.set(42, '#6230ff');
district_color.set(43, '#623998');
district_color.set(44, '#bec413');
district_color.set(45, '#413bec');
