/*
MultiTouchPad.stop;
*/
// general load, MTP startup
/*(
q = q ? ();
q.paths = Dictionary.new;
s.latency = 0.03;
q.dollar = DollarRecognizer.new;

MultiTouchPad.start;

MultiTouchPad.touchAction = {|curID, xys|
	q.paths[curID] = Array.new;
};
MultiTouchPad.setAction = {|curID, xys|
	xys = xys.round(0.0001);

	if(q.paths[curID].size > 1, {
		var lasPoint = q.paths[curID][q.paths[curID].size-2];
		// Make shure not to add the same Points multiple times
		if(lasPoint.x != xys[0] && lasPoint.y != xys[1],{
			q.paths[curID] = q.paths[curID].add(Point.new(xys[0],xys[1]));
		});
	},{
		q.paths[curID] = q.paths[curID].add(Point.new(xys[0],xys[1]));
	})
};
MultiTouchPad.untouchAction = {|curID|
	var paths = q.paths[curID].asArray;
	var pathSize = paths.size;
	var result;
	if(paths.size > 5, {
		q.lastPaths = paths.copy;
		// Recognize stroke from points,
		// second argument true means faster algorythm (Protractor)
		result = q.dollar.recognize(paths, false);
		result = [curID, result.name, result.score, pathSize];
	},{
		result = [curID, nil, nil, pathSize];
	});

	q.paths[curID].clear;
	q.paths[curID] = result;
	result.postln;
};

q.mtGui = MultiTouchPad.gui;
MultiTouchPad.guiWin.view.children[0].background_(Color.grey(1.0, 0.5));
MultiTouchPad.guiWin.alpha_(0.5);
MultiTouchPad.guiWin.view.keyDownAction = { |view, char|
	if (char == $.) { MultiTouchPad.stop };
	if (char == $ ) { MultiTouchPad.start };
	if (char == $x) { MultiTouchPad.guiWin.fullScreen };
	if (char == $m) { MultiTouchPad.guiWin.endFullScreen };
};
)*/