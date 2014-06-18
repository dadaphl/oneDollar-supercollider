(
var drawFunc, mouseDownFunc, mouseUpFunc, mouseMoveFunc;
q = q ? ();
q.paths = Dictionary.new;
q.dollar = DollarRecognizer.new;
q.curID = 0;

mouseDownFunc = {|me, x, y, mod|
	q.paths[q.curID ] = Array.new;
};

mouseUpFunc = {|me, x, y, mod|
	var paths = q.paths[q.curID].asArray;
	var pathSize = paths.size;
	var result;
	if(paths.size > 5, {
		q.lastPaths = paths.copy;
		// Recognize stroke from points,
		// second argument true means faster algorythm (Protractor)
		result = q.dollar.recognize(paths, false);
		result = [q.curID, result.name, result.score, pathSize];
	},{
		result = [q.curID, nil, nil, pathSize];
	});
	q.paths[q.curID].clear;
	q.paths[q.curID] = result;
	result.postln;
	q.curID = q.curID +1;
};

mouseMoveFunc = {|me, x, y, mod|
	if(q.paths[q.curID].size > 1, {
		var lasPoint = q.paths[q.curID][q.paths[q.curID].size-2];
		// Make shure not to add the same Points multiple times
		if(lasPoint.x != x && lasPoint.y != y,{
			q.paths[q.curID] = q.paths[q.curID].add(x@y);
		});
	},{
		q.paths[q.curID] = q.paths[q.curID].add(x@y);
	});
    me.refresh;
};

w = Window.new('Dollar One Example',Rect(100,400,300,300)).front;
w.view.background_(Color.white);
v = UserView(w, Rect(0, 0, 300, 300));
v.drawFunc = drawFunc;
v.mouseDownAction = mouseDownFunc;
v.mouseUpAction = mouseUpFunc;
v.mouseMoveAction = mouseMoveFunc;

w.refresh;
)