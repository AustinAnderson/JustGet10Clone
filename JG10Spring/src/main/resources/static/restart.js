function Globals(sideTileCount){

    this.cellSize=document.getElementById("display").getBoundingClientRect().width/(sideTileCount*1.125);
    this.borderWidth=this.cellSize/20;
    this.marginTop=this.borderWidth*2;
    this.marginLeft=this.borderWidth*2;
    this.indexes2Coords=function(row,col){
        return {"x":((this.borderWidth*2+this.cellSize)*row),
                "y":((this.borderWidth*2+this.cellSize)*col)};
    }
    this.indexes2Transform=function(row,col){
        return "translate("+((this.borderWidth*2+this.cellSize)*row)+"px,"+((this.borderWidth*2+this.cellSize)*col)+"px)";
    }
}

function Tile(number){
    var styleHandle=undefined;
    var numberStyleHandle=null;
    var numberTextHandle=null;
    var tileHandle=null;


    this.getDom=function(){
        return tileHandle;
    }
    this.setOpacity=function(value){
        styleHandle.opacity=value;
    }
    this.setTransform=function(value){
        styleHandle.transform=value;
    }
    this.animateToZero=function(){
        tileHandle.addEventListener("transitionend",function(){
            tileHandle.setAttribute("class","tile");            
        });
        this.setTransform("translate(0px,0px)");
    }


    this.listenForMovementTurn=function(movementChainFunction){
        tileHandle.addEventListener("transitionend",movementChainFunction);
    }
    this.removeMovementListener=function(movementChainFunction){
        tileHandle.removeEventListener("transitionend",movementChainFunction);
    }
    this.getNumber=function(){
        return number;
    }

    var setNumber=function(fnumber){
        numberTextHandle.innerHTML=fnumber;
        numberStyleHandle.classList=["n"+fnumber];
    }

    this.remove=function(){
        tileHandle.remove();
    }
    this.increment=function(){
        number=number+1;
        setNumber(number);
    }

    var render=function(){
        var margins="transform: translate("+globals.marginLeft+"px,"+globals.marginTop+"px)";

        var tile=document.createElementNS('http://www.w3.org/2000/svg',"g");
        tile.classList=["tile"];
        this.classList=tile.classList;
        styleHandle=tile.style;


        var border=document.createElementNS('http://www.w3.org/2000/svg',"g");
        border.setAttribute("class","border");
        border.style=margins;
        var edgeStrs=[["bottom","top"],
                      ["right","left"]];
        for(var x=0;x<2;x++){
            for(var y=0;y<2;y++){
                var cornerCircle=document.createElementNS('http://www.w3.org/2000/svg',"circle");
                cornerCircle.setAttribute("r",""+globals.borderWidth+"px");
                cornerCircle.setAttribute("cx",""+(x*globals.cellSize)+"px");
                cornerCircle.setAttribute("cy",""+(y*globals.cellSize)+"px");
                var edge=document.createElementNS('http://www.w3.org/2000/svg',"rect");
                var h=globals.cellSize;
                var w=globals.borderWidth;
                var attr="x";
                if(x==0){
                    var tmp=h;
                    h=w;
                    w=tmp;
                    attr="y";
                }
                var pos=-globals.borderWidth;
                if(y==0){
                    pos=globals.cellSize;
                }		
                edge.setAttribute("height",""+h+"px");
                edge.setAttribute("width",""+w+"px");
                edge.setAttribute(attr,""+pos+"px");
                edge.setAttribute("class",edgeStrs[x][y]+"Edge");
                border.appendChild(cornerCircle);
                border.appendChild(edge);
            }
        }
        var inner=document.createElementNS('http://www.w3.org/2000/svg',"g");
        inner.setAttribute("class","n"+number);
        numberStyleHandle=inner;
        inner.style=margins;
        var box=document.createElementNS('http://www.w3.org/2000/svg',"rect");
        box.setAttribute("height",""+globals.cellSize+"px");
        box.setAttribute("width",""+globals.cellSize+"px");
        box.style="stroke: black";
        var text=document.createElementNS('http://www.w3.org/2000/svg',"text");
        text.setAttribute("y",""+globals.cellSize*(1/2)+"px");
        text.setAttribute("x",""+globals.cellSize*(1/2)+"px");
        text.setAttribute("font-size",""+globals.cellSize/2+"px");
        text.setAttribute("text-anchor","middle");
        text.setAttribute("dominant-baseline","central");
        text.setAttribute("class","cellText");
        numberTextHandle=text;
        inner.appendChild(box); 
        inner.appendChild(text);
        tile.appendChild(border);
        tile.appendChild(inner);
        tileHandle=tile;
    }	

    render();
    setNumber(number);

    
}
function TileHolder(col,row,number,svg,tileGrid){
   
    var tileHolderDom=document.createElementNS('http://www.w3.org/2000/svg',"g");
    var translateX=((globals.borderWidth*2+globals.cellSize)*col);
    var translateY=((globals.borderWidth*2+globals.cellSize)*row);
    tileHolderDom.style.transform="translate("+translateX+"px,"+translateY+"px)";
    tileHolderDom.setAttribute("id",""+row+col);
    var shapeHolder=document.createElementNS('http://www.w3.org/2000/svg',"rect");
    var sideLength=(globals.borderWidth*2+globals.cellSize);
    (function(){
        var shapeHolderSideLength=""+sideLength+"px";
        shapeHolder.setAttribute("height",shapeHolderSideLength);
        shapeHolder.setAttribute("width",shapeHolderSideLength);
    }())
    shapeHolder.style.transform="translate("+globals.borderWidth+"px,"+globals.borderWidth+"px)";
    shapeHolder.style.opacity=0;

    var tile=new Tile(number);
    tileHolderDom.appendChild(tile.getDom());
    tileHolderDom.appendChild(shapeHolder);

    svg.appendChild(tileHolderDom);

    var markedForDeletion=false;

    var clickListener=function(){
        tileGrid.doAjax(row,col);
        
    }

    this.enableClickListening=function(){
        tileHolderDom.addEventListener("click",clickListener);
    }
    this.disableClickListening=function(){
        tileHolderDom.removeEventListener("click",clickListener);
    }
    this.enableClickListening();

    this.getId=function(){
        return tileHolderDom.id;
    }
    //this is the downside of using scope alone to emulate access modifiers;
    //in java/c/c++/c#, instances of the same class can acess each other's
    //private members, which isn't the case here
    this.getTile=function(){
        return tile;
    }
    this.setTile=function(newTile){
        tile=newTile;
    }

    this.getTranslateX=function(){
        return translateX;
    }
    this.getTranslateY=function(){
        return translateY;
    }

    this.isEmpty=function(){
        //return tileHolderDom.children.length>1;//only the click rectangle if empty
        return tile===null;//only the click rectangle if empty
    }

    this.pullNewTile=function(nextNumber){
        tile=new Tile(nextNumber);
        var y=-translateY-sideLength;
        tile.setTransform("translate(0px,"+y+"px)");
        tileHolderDom.appendChild(tile.getDom());
    }

    this.pullTile=function(fromTileHolder){

        
        var y=fromTileHolder.getTranslateY()-translateY;
        tile=new Tile(fromTileHolder.getTile().getNumber());
        tile.setTransform("translate(0px,"+y+"px)");
        fromTileHolder.delete();
        tileHolderDom.appendChild(tile.getDom());

    }
    this.move=function(toTileHolder,shouldIncrement){
        var x=toTileHolder.getTranslateX()-translateX;
        var y=toTileHolder.getTranslateY()-translateY;
        tile.setOpacity(0);
        tile.setTransform("translate("+x+"px,"+y+"px)");
        tile.listenForMovementTurn(
            function(e){
                if(e.propertyName=="opacity"&&shouldIncrement){
                    tileGrid.runNext();
                }
            }
        );
        markedForDeletion=true;
    }
    this.delete=function(){
        tile.remove();
        tile=null;
    }
    this.deleteIfNeeded=function(){
        if(markedForDeletion){
            tile.remove();
            tile=null;
            markedForDeletion=false;
        }
    }
}

function TileGrid(rows,cols,svg,numbers){

    var hasLost=false;
    
    var grid=[];//of arrays of tileholders
    this.debugGetGridDeleteMe=function(){
        return grid;
    }
    for(var i=0;i<cols;i++){
        var row=[];
        for(var j=0;j<rows;j++){
            row.push(new TileHolder(j,i,numbers[i][j],svg,this));
        }
        grid.push(row);
    }
    this.doAjax=function(row,col){
    	var that=this;
    	var numberGrid=[];
    	for(i=0;i<grid.length;i++){
    		var numberRow=[];
    		for(j=0;j<grid[i].length;j++){
    			numberRow.push(grid[i][j].getTile().getNumber());
    		}
    		numberGrid.push(numberRow);
    	}
console.log("new move");
console.log("on "+row+", "+col);
console.log(JSON.stringify(numberGrid).replace(/],/g,"],\n ").replace(/[\[]/g,"{").replace(/]/g,"}"));
        $.ajax({ type:"POST",
        url: "/moveOn/"+row+"/"+col,
        data: JSON.stringify(numberGrid),
        success: function(response){
        	if(response.transitionList!==null&&response.transitionList.transitionList.length!=0&&response.replaceList.length!=0){
console.log(JSON.stringify(response.replaceList).replace(/[\[]/g,"{").replace(/]/g,"}"));
				var num=numberGrid[row][col]+1;
				if(num>window.bestThisRound){
					window.bestThisRound=num;
				}
        		that.resetWith(response.transitionList.transitionList,response.replaceList);
        		that.RunTransitions();
        		hasLost=response.hasLost;
        	}
        },
        dataType:"json",
        contentType: "application/json"});
    }
    this.resetWith=function(newTransitionList,newReplaceList){
        this.transitionsList=newTransitionList;
        this.replaceList=newReplaceList;
        this.currentTransition=0;
    }

    var pendingCallBackCount=0;
    var enableAllClickListenersIfDone=function(){
        pendingCallBackCount--;
        if(pendingCallBackCount<=0){
            if(hasLost){
                (function(){
                	var highScoreSpot=document.getElementById('highScoreSquare');
                	highScoreSquare.style.width=globals.cellSize+globals.marginLeft*2;
                	highScoreSquare.appendChild(new Tile(bestThisRound).getDom());
					var displayContainer=document.getElementById('messageDisplayContainer');
					var focusOverlay=document.getElementById('focusOverlay')
					focusOverlay.style.display='inline';
					displayContainer.style.display='inline';
					window.setTimeout(function(){
						focusOverlay.style.opacity=.75;
						displayContainer.style.opacity=1;
					},50);
				}());
            }else{
                for(i=0;i<grid.length;i++){
                    for(j=0;j<grid[i].length;j++){
                        grid[i][j].enableClickListening();
                    }
                }
            }
        }
    }
    var firstInRun=true;
    this.runNext=function(){
        if(this.currentTransition<this.transitionsList.length){
            var concurrentTransitionList=this.transitionsList[this.currentTransition];
            var frow=concurrentTransitionList[0].fromNdxs.row;
            var fcol=concurrentTransitionList[0].fromNdxs.col;
            var trow=concurrentTransitionList[0].toNdxs.row;
            var tcol=concurrentTransitionList[0].toNdxs.col;
            grid[frow][fcol].move(grid[trow][tcol],true);
            for(var i=1;i<concurrentTransitionList.length;i++){
                var frow=concurrentTransitionList[i].fromNdxs.row;
                var fcol=concurrentTransitionList[i].fromNdxs.col;
                var trow=concurrentTransitionList[i].toNdxs.row;
                var tcol=concurrentTransitionList[i].toNdxs.col;
                grid[frow][fcol].move(grid[trow][tcol],false);
            }
            this.currentTransition++;
        }else{//done
            var finalGridTile=this.transitionsList[this.transitionsList.length-1][0].toNdxs;
            grid[finalGridTile.row][finalGridTile.col].getTile().increment();
            for(i=0;i<grid.length;i++){
                for(j=0;j<grid.length;j++){
                    grid[i][j].deleteIfNeeded();
                }
            }
            for(j=grid.length-1;j>=0;j--){
                for(i=grid.length-1;i>=0;i--){
                    if(grid[i][j].isEmpty()){
                        rowNum=i-1;
                        while(rowNum>=0&&grid[rowNum][j].isEmpty()){
                            rowNum--;
                        }
                        if(rowNum>=0){
                            grid[i][j].pullTile(grid[rowNum][j]);
                        }else{
                            grid[i][j].pullNewTile(this.replaceList.pop());
                            //new tile from the top with number from list
                        }
                        grid[i][j].getTile().getDom().setAttribute("class","droppingTile");
                        pendingCallBackCount++;
                        //closure captures a reference to vars outside, so when the function is called, grid[i][j] would be grid[0][0]
                        //so we need to capture grid[i][j] on each iteration in a new variable
                        //the immediately executing function expression forces currentTileHolder variable to go out of scope 
                        //and be recreated on the next iteration of the loop
                        (function(){
                            var currentTileHolder=grid[i][j];
                            var selfRemovingCallback=function(){
                                enableAllClickListenersIfDone();
                                //set a call back to listen for the last tile to finish moving and enable all the tileholders to listen for clicks
                                currentTileHolder.getTile().removeMovementListener(selfRemovingCallback);
                            }
                            grid[i][j].getTile().listenForMovementTurn(selfRemovingCallback);
                        }());
                    }
                }
            }
            window.setTimeout(function(){//animate the drop down, requires timeout for browser to realize it needs to transition
                for(i=0;i<grid.length;i++){
                    for(j=0;j<grid.length;j++){
                        if(!grid[i][j].isEmpty()){
                            grid[i][j].getTile().animateToZero();
                        }
                    }
                }  
            },50);
            firstInRun=true;
        }
    }
    this.resetWith(null,null);
    this.RunTransitions=function(){
        if(firstInRun){
            disableAllClickListeners();//to be enabled by call back after all transition end's fire
            firstInRun=false
            this.runNext();
        }
    }

    var disableAllClickListeners=function(){

        for(i=0;i<grid.length;i++){
            for(j=0;j<grid[i].length;j++){
                grid[i][j].disableClickListening();
            }
        }
    }
}


var main=function(){
    var displayContainer=document.getElementById("container");
    var display=document.getElementById("display");
    if (!(/Mobi/.test(navigator.userAgent))) {
        display.style.width="300px";
        display.style.height="300px";
        displayContainer.style.width="400px";
        displayContainer.style.setProperty("font-size","100%");
    }
    var tilesPerSide=buildTemplate.length;
    globals=new Globals(tilesPerSide);
                              //{ cy      cx      r}* globals.cellSize
	var circleSizesPattern10=[[0.45000,0.05625,0.03750],
		      	 	 	 	  [0.13125,0.39375,0.03750],
		      	 	 	 	  [0.22500,0.09375,0.05625],
		      	 	 	 	  [0.93750,1.03125,1.03125]];
	var pattern10Circles=document.getElementById("Pattern10").children;
	//background fill rect
	pattern10Circles[0].setAttribute("height",globals.cellSize);
	pattern10Circles[0].setAttribute("width",globals.cellSize);
	for(i=1;(i-1)<circleSizesPattern10.length&&i<pattern10Circles.length;i++){
		pattern10Circles[i].setAttribute("cy",""+(circleSizesPattern10[i-1][0]*globals.cellSize));
		pattern10Circles[i].setAttribute("cx",""+(circleSizesPattern10[i-1][1]*globals.cellSize));
		pattern10Circles[i].setAttribute("r",""+(circleSizesPattern10[i-1][2]*globals.cellSize));
	}
	
	
	var circlePattern12=document.getElementById("Pattern12Circle");
	circlePattern12.setAttribute("cx",globals.cellSize/2);
	circlePattern12.setAttribute("cy",globals.cellSize/2);
	circlePattern12.setAttribute("r",globals.cellSize);
	
	var circlePattern11=document.getElementById("Pattern11Circle");
	circlePattern11.setAttribute("cx",globals.cellSize/2);
	circlePattern11.setAttribute("cy",globals.cellSize/2);
	circlePattern11.setAttribute("r",globals.cellSize);
	

    window.bestThisRound=1;
    console.log(buildTemplate);
    for(i=0;i<buildTemplate.length;i++){
    	for(j=0;j<buildTemplate[i].length;j++){
    		if(buildTemplate[i][j]>bestThisRound){
    			bestThisRound=buildTemplate[i][j];
    		}
    	}
    }
    grid=new TileGrid(tilesPerSide,tilesPerSide,display,buildTemplate);

}

