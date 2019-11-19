    /*Functions*/    
    function onEachFeature(feature, layer) {

        layer.on("mouseover", function (e) {

            document.getElementById("small-info-window").style.width = "220px";
            if(sliderState == 1)
                document.getElementById("small-info-window").style.left = "370px";
            else
                document.getElementById("small-info-window").style.left = "50px";

            $(fillOutSmallWindow(feature));


        });

        layer.on("mouseout", function (e) {
            //$(popup1.remove());
            document.getElementById("small-info-window").style.width = "0";

        });

        layer.on("click", function (e) {
            $(toggleInfoSlider( feature ));



        });
    }

    // active slider menu
    function toggleSlider() {
        if (sliderState === 0) { //Open slider
            document.getElementById("slide-menu").style.width = "350px";
            sliderState = 1;
            setTimeout(moveZoomControl, 0);

        } else {
            document.getElementById("slide-menu").style.width = "0";
            sliderState = 0;
            setTimeout(moveZoomControl, 200);
        }
    }

    function moveZoomControl() {
        if (sliderState === 1) { //Open slider
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

    function toggleInfoSlider( feature ) {
        if (infoStat === feature.properties.DISTRICT) { //Open slider

            document.getElementById("slide-info").style.width = "0";
            infoStat = "null";

        } else {

            document.getElementById("slide-info").style.width = "350px";
            infoStat = feature.properties.DISTRICT;
            fillOutTable(feature);

        }
    }

    function closeInfo() {
        document.getElementById("slide-info").style.width = "0";
        infoStat = "null";
    }

    function fillOutSmallWindow(feature) {
        $(document).ready(function () {

            $("#small-info-table tr").remove();
            var year;
            if($('#2016D').is(':checked'))
                year = 2016;
            else
                year = 2018;

                var items = [
                {Attr: "Name", Amout: "District "+feature.properties.DISTRICT},
                {Attr: "population", Amout: "50"},
                {Attr: "White", Amout: "50"},
                {Attr: "Minority", Amout: "50"},
                {Attr: "Hispanic", Amout: "50"},
                {Attr: "Asian", Amout: "50"},
                {Attr: "Republican", Amout: "50"},
                {Attr: "Democratic", Amout: "50"},
                {Attr: "Year", Amout: year},

            ];

            $("#smallInfoTemplate").tmpl(items).appendTo("#small-info-table tbody");
        });
    }

    function fillOutTable(feature) {
        $(document).ready(function () {

            $("#itemList tr").remove();

            var items = [
                {Attr: "Name", Amout: "District "+feature.properties.DISTRICT},
                {Attr: "population", Amout: "50"},
                {Attr: "White", Amout: "50"},
                {Attr: "Minority", Amout: "50"},
                {Attr: "Hispanic", Amout: "50"},
                {Attr: "Asian", Amout: "50"},
                {Attr: "Republican", Amout: "50"},
                {Attr: "Democratic", Amout: "50"},

            ];

            $("#itemTemplate").tmpl(items).appendTo("#itemList tbody");
        });
    }

    function show_value(x, id){
    document.getElementById(id).innerHTML=x;
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
    district_color.set(12, '#87CEEB');
    district_color.set(13, '#B0C4DE');
    district_color.set(14, '#FF0000');
    district_color.set(15, '#50b2ff');
    district_color.set(16, '#6effa1');
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
