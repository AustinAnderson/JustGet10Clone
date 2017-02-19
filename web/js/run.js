
var sampleTransitionList=[
//server bfs should return this after tile is clicked
	[
		{'fromId':'t20','toId': 't21'}
	],
	[
		{'fromId':'t30','toId': 't40'},
		{'fromId':'t21','toId': 't22'}
	],
	[
		{'fromId':'t40','toId': 't41'},
		{'fromId':'t22','toId': 't23'},
		{'fromId':'t13','toId': 't23'}
	],
	[
		{'fromId':'t41','toId': 't42'},
		{'fromId':'t14','toId': 't24'},
		{'fromId':'t23','toId': 't33'}
	],
	[
		{'fromId':'t42','toId': 't43'},
		{'fromId':'t24','toId': 't34'},
		{'fromId':'t33','toId': 't34'}
	],
	[
		{'fromId':'t43','toId': 't44'},
		{'fromId':'t34','toId': 't44'}
	]
];

function TransitionRunner(transitionsList){
	this.transitionsList=transitionsList;
	this.currentTransition=0;
}
TransitionRunner.prototype={
	constructor:TransitionRunner,

	resetWith: function(newList){
		this.transitionsList=newList;
		this.currentTransition=0;
	},
	runNext:function(){
		if(this.currentTransition<this.transitionsList.length){
			var concurrentTransitionList=this.transitionsList[this.currentTransition];
			moveTile(concurrentTransitionList[0].fromId,concurrentTransitionList[0].toId,this,true);
			for(var i=1;i<concurrentTransitionList.length;i++){
				moveTile(concurrentTransitionList[i].fromId,concurrentTransitionList[i].toId,this,false);
			}
			this.currentTransition++;
		}
	}
}
var moveTile=function(fromId,toId,TransitionRunner,shouldIncrement){
    var i=document.getElementById(fromId).style;
    i.opacity=0;
    i.transform=document.getElementById(toId).style.transform;
	document.getElementById(fromId).addEventListener("transitionend",
		function(e){
			if(e.propertyName=="opacity"&&shouldIncrement){
				TransitionRunner.runNext();
			}
		}
	);
}


var createTile=function(row,col,number){
	var width=2;
	var space=40;
	var tile=document.createElementNS('http://www.w3.org/2000/svg',"g");
	tile.style="transform: translate("+((width*2+space)*row)+"px,"+((width*2+space)*col)+"px)";
	tile.id="t"+col+row;
	tile.setAttribute("class","tile");
    var border=document.createElementNS('http://www.w3.org/2000/svg',"g");
    border.setAttribute("class","border");
    border.style="transform: translate(5px,5px)";
    for(var x=0;x<2;x++){
        for(var y=0;y<2;y++){
            var cornerCircle=document.createElementNS('http://www.w3.org/2000/svg',"circle");
            cornerCircle.setAttribute("r",""+width+"px");
            cornerCircle.setAttribute("cx",""+(x*space)+"px");
            cornerCircle.setAttribute("cy",""+(y*space)+"px");
			var edge=document.createElementNS('http://www.w3.org/2000/svg',"rect");
			var h=space;
			var w=width;
			var attr="x";
			if(x==0){
				var tmp=h;
				h=w;
				w=tmp;
				attr="y";
            }
			var pos=-width;
			if(y==0){
				pos=space;
            }		
			edge.style="height: "+h+"px; width: "+w+"px;";
			edge.setAttribute(attr,""+pos+"px");
			border.appendChild(cornerCircle);
			border.appendChild(edge);
        }
    }
	var inner=document.createElementNS('http://www.w3.org/2000/svg',"g");
	inner.setAttribute("class","n"+number);
	inner.style="transform: translate(5px,5px)";
	var box=document.createElementNS('http://www.w3.org/2000/svg',"rect");
	box.style="height: 40px; width: 40px; stroke: black";
	var text=document.createElementNS('http://www.w3.org/2000/svg',"text");
	text.setAttribute("dy","25px");
	text.setAttribute("dx","15px");
	text.innerHTML=""+number;
	//text.innerHTML="t"+col+row;
	inner.appendChild(box);
	inner.appendChild(text);
	tile.appendChild(border);
	tile.appendChild(inner);
	tile.addEventListener("click",function(){console.log(this.id+" clicked");});
	
	////why web dev is sketchy: exibit A
	//tile.addEventListener("click",(new TransitionRunner(sampleTransitionList)).runNext()); //doesnt work due to loading times
	//tile.setAttribute("onclick","(new TransitionRunner(sampleTransitionList)).runNext()");
	return tile;
}	


var fillBoard=function(tileMat){
	for(var i=0;i<tileMat.length;i++){
		for(var j=0;j<tileMat[i].length;j++){
			document.getElementById("test").appendChild(createTile(j,i,tileMat[i][j]));
		}
	}
}
