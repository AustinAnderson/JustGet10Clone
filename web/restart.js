function Globals(){
    this.borderWidth=2;
    this.cellSize=40;
    this.indexes2Coords=function(row,col){
        return {"x":((this.borderWidth*2+this.cellSize)*row),
                "y":((this.borderWidth*2+this.cellSize)*col)};
    }
    this.indexes2Transform=function(row,col){
        return "translate("+((this.borderWidth*2+this.cellSize)*row)+"px,"+((this.borderWidth*2+this.cellSize)*col)+"px)";
    }
}






function Tile(number,parentDom){
    var styleHandle=undefined;
    var numberStyleHandle=null;
    var numberTextHandle=null;
    var tileHandle=null;


    this.setOpacity=function(value){
        styleHandle.opacity=value;
    }
    this.setTransform=function(value){
        styleHandle.transform=value;
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

    var render=function(){


        var tile=document.createElementNS('http://www.w3.org/2000/svg',"g");
        tile.classList=["tile"];
        this.classList=tile.classList;
        styleHandle=tile.style;


        var border=document.createElementNS('http://www.w3.org/2000/svg',"g");
        border.setAttribute("class","border");
        border.style="transform: translate(5px,5px)";
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
                edge.style="height: "+h+"px; width: "+w+"px;";
                edge.setAttribute(attr,""+pos+"px");
                border.appendChild(cornerCircle);
                border.appendChild(edge);
            }
        }
        var inner=document.createElementNS('http://www.w3.org/2000/svg',"g");
        inner.setAttribute("class","n"+number);
        numberStyleHandle=inner;
        inner.style="transform: translate(5px,5px)";
        var box=document.createElementNS('http://www.w3.org/2000/svg',"rect");
        box.style="height: "+globals.cellSize+"px; width: "+globals.cellSize+"px; stroke: black";
        var text=document.createElementNS('http://www.w3.org/2000/svg',"text");
        text.setAttribute("dy","25px");
        text.setAttribute("dx","15px");
        numberTextHandle=text;
        inner.appendChild(box); 
        inner.appendChild(text);
        tile.appendChild(border);
        tile.appendChild(inner);
        tileHandle=tile;
        parentDom.appendChild(tile);
    }	

    render();
    setNumber(number);

    
}
function TileHolder(row,col,number,svg,tileGrid){
    var tileHolderDom=document.createElementNS('http://www.w3.org/2000/svg',"g");
    var translateX=((globals.borderWidth*2+globals.cellSize)*row);
    var translateY=((globals.borderWidth*2+globals.cellSize)*col);
    tileHolderDom.style.transform="translate("+translateX+"px,"+translateY+"px)";
    tileHolderDom.addEventListener("click",function(){console.log(""+row+col+"clicked");},true);
    tileHolderDom.setAttribute("class","bob");
    var shapeHolder=document.createElementNS('http://www.w3.org/2000/svg',"rect");
    shapeHolder.style.height=""+(globals.borderWidth*2+globals.cellSize)+"px";
    shapeHolder.style.width=""+(globals.borderWidth*2+globals.cellSize)+"px";
    shapeHolder.style.transform="translate("+globals.borderWidth+"px,"+globals.borderWidth+"px)";
    shapeHolder.style.opacity=0;


    tileHolderDom.appendChild(shapeHolder);
    var tile=new Tile(number,tileHolderDom);

    svg.appendChild(tileHolderDom);

    var markedForDeletion=false;

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
    this.isMarked=function(){
        return markedForDeletion;
    }
    this.markForDeletion=function(){
        markedForDeletion=true;
    }

    this.pullTilePermenant=function(fromTileHolder){
        fromTileHolder.moveNoOpacity(this,function(e){
            fromTileHolder.markForDeletion();
            //fromTileHolder.deleteIfNeeded();
        });
        var num=fromTileHolder.getTile().getNumber();
        tile=new Tile(num,tileHolderDom);
    }
    var newFallingTile=function(number){
        tile=new Tile(number,tileHolderDom);
        //tile.setTransform("translate(0px,"+-translateY*2+"px)");
        //tile.setTransform("translate(0px,0px)");
    }
    this.pullNewTile=function(nextNumber){
        //*
        if(tile!==null){

            tile.listenForMovementTurn(function(){
                tile.remove();
                newFallingTile(nextNumber);
            });
        }else{
            newFallingTile(nextNumber);
        }
        //*/
        //closure will delete old tile when ready
    }

    this.moveNoOpacity=function(toTileHolder,callback){

        var x=toTileHolder.getTranslateX()-translateX;
        var y=toTileHolder.getTranslateY()-translateY;
        tile.setTransform("translate("+x+"px,"+y+"px)");
        markedForDeletion=true;
        tile.listenForMovementTurn(callback);
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
    this.deleteIfNeeded=function(){
        if(markedForDeletion){
            tile.remove();
            tile=null;
            markedForDeletion=false;
        }
    }
}

function TileGrid(rows,cols,svg,numbers,transitionsList,replaceList){//TODO: make singleton
    this.testUpdate=function(id){
        grid[0][0].setId("2");
    }
    var grid=[];//of arrays of tileholders
    for(var i=0;i<cols;i++){
        var row=[];
        for(var j=0;j<rows;j++){
            row.push(new TileHolder(j,i,numbers[i][j],svg,this));
        }
        grid.push(row);
    }
    this.resetWith=function(newTransitionList,newReplaceList){
        this.transitionsList=newTransitionList;
        this.replaceList=newReplaceList;
        this.currentTransition=0;
    },
    this.runNext=function(){
        if(this.currentTransition<this.transitionsList.length){
            var concurrentTransitionList=this.transitionsList[this.currentTransition];
            var frow=concurrentTransitionList[0].fromNdxs.x;
            var fcol=concurrentTransitionList[0].fromNdxs.y;
            var trow=concurrentTransitionList[0].toNdxs.x;
            var tcol=concurrentTransitionList[0].toNdxs.y;
            grid[frow][fcol].move(grid[trow][tcol],true);
            for(var i=1;i<concurrentTransitionList.length;i++){
                var frow=concurrentTransitionList[i].fromNdxs.x;
                var fcol=concurrentTransitionList[i].fromNdxs.y;
                var trow=concurrentTransitionList[i].toNdxs.x;
                var tcol=concurrentTransitionList[i].toNdxs.y;
                grid[frow][fcol].move(grid[trow][tcol],false);
            }
            this.currentTransition++;
        }else{//done
            for(i=0;i<grid.length;i++){
                for(j=0;j<grid.length;j++){
                    grid[i][j].deleteIfNeeded();
                }
            }
            for(i=grid.length-1;i>=0;i--){
            //if(true){i=grid.length-1;
                for(j=grid.length-1;j>=0;j--){
                    if(grid[i][j].isEmpty()||grid[i][j].isMarked()){
                        rowNum=i-1;
                        while(rowNum>=0&&(grid[rowNum][j].isEmpty()||grid[rowNum][j].isMarked())){
                            rowNum--;
                        }
                        if(rowNum>=0){
                            grid[i][j].pullTilePermenant(grid[rowNum][j]);
                        }else{
                            grid[i][j].pullNewTile(replaceList.pop());
                            //new tile from the top with number from list
                        }
                    }
                }
            }
        }
    }
    this.resetWith(transitionsList,replaceList);
}


var main=function(){
    globals=new Globals();
    var display=document.getElementById("display");

    //*
    var buildTemplate=[[1,2,3,2,4],
                       [2,4,3,1,1],
                       [1,1,1,1,1],
                       [1,2,3,1,1],
                       [1,1,1,1,1]];

    //server bfs should return these after tile is clicked

    var replaceList=[1,4,2,1,3,1,3,4,1,1,1,1,2,2];
    //*/

    var sampleTransitionList=[
        [
            {'fromNdxs': {'x':2,'y':0},'toNdxs': {'x':2,'y':1}}
        ],
        [
            {'fromNdxs': {'x':3,'y':0},'toNdxs': {'x':4,'y':0}},
            {'fromNdxs': {'x':2,'y':1},'toNdxs': {'x':2,'y':2}}
        ],
        [
            {'fromNdxs': {'x':4,'y':0},'toNdxs': {'x':4,'y':1}},
            {'fromNdxs': {'x':2,'y':2},'toNdxs': {'x':2,'y':3}},
            {'fromNdxs': {'x':1,'y':3},'toNdxs': {'x':2,'y':3}}
        ],
        [
            {'fromNdxs': {'x':4,'y':1},'toNdxs': {'x':4,'y':2}},
            {'fromNdxs': {'x':1,'y':4},'toNdxs': {'x':2,'y':4}},
            {'fromNdxs': {'x':2,'y':3},'toNdxs': {'x':3,'y':3}}
        ],
        [
            {'fromNdxs': {'x':4,'y':2},'toNdxs': {'x':4,'y':3}},
            {'fromNdxs': {'x':2,'y':4},'toNdxs': {'x':3,'y':4}},
            {'fromNdxs': {'x':3,'y':3},'toNdxs': {'x':3,'y':4}}
        ],
        [
            {'fromNdxs': {'x':4,'y':3},'toNdxs': {'x':4,'y':4}},
            {'fromNdxs': {'x':3,'y':4},'toNdxs': {'x':4,'y':4}}
        ]
    ];
    grid=new TileGrid(5,5,display,buildTemplate,sampleTransitionList,replaceList);

}

