//create the container for the d3 art
var svgContainer = d3.select("#court")
                        .append("svg")
                        .attr("width", 500)
                        .attr("height", 470);

var name = '';
var game = '';

$("#getData").on('click',function(){
    game = $("#games :selected").text();
    $("#intro").text(game);
    $("#home").text(game.substring(9,12));
    $("#away").text(game.substring(12,15));
    $("#players").show();
    d3.json("shots.json", function(data){
      var shots = data["shots"];
      for (var i = 0; i < shots.length; i++) {
        var obj = shots[i];
        if (obj.made == "made") {
          svgContainer.append("circle")
                            .attr("cx", obj.x*10)
                            .attr("cy", obj.y*10)
                            .attr("r", 7)
                            .attr("fill", "green")
        } else {
          svgContainer.append("circle")
                            .attr("cx", obj.x*10)
                            .attr("cy", obj.y*10)
                            .attr("r", 7)
                            .attr("fill", "red")
        }
      }
    });
});

$(".nav").on('click','li',function(){
    name = $(this).text(); //get the selected name
    svgContainer.selectAll("circle").remove(); //remove all the shots of previous selected player
    render();
});

//function that does the drawing and sets player info for bottom
function render(){    
    d3.json("data.json", function(data){
       var team = data[name];
        //green for made baskets, red for missed
        for (var i = 0; i < team.length; i++) {
            var obj = team[i]
            if (obj.made == 1) {
                svgContainer.append("circle")
                            .attr("cx", obj.x*10)
                            .attr("cy", obj.y*10)
                            .attr("r", 7)
                            .attr("fill", "green")
            } else {
                svgContainer.append("circle")
                            .attr("cx", obj.x*10)
                            .attr("cy", obj.y*10)
                            .attr("r", 7)
                            .attr("fill", "red")
            }
        }
    });
}

function csvJSON(csv){ 
  var lines=csv.split("\n");
  var result = [];
  var headers=lines[0].split(",");
  for(var i=1;i<lines.length;i++){
    var obj = {};
    var currentline=lines[i].split(",");
    for(var j=0;j<headers.length;j++){
      obj[headers[j]] = currentline[j];
    }
    result.push(obj);
  }
  //return result; //JavaScript object
  console.log(JSON.stringify(result)); //JSON
}