Unistroke {
	var <>name,<>points,<>vector;
	*new { arg nname, ppoints;
		var radians;
		name = nname;
		this.points = Dollar.resample(ppoints, Dollar.numpoints);
		radians     = Dollar.indicativeAngle(this.points);
		this.points = Dollar.rotateBy(this.points,  -1 * (Dollar.radians) );
		this.points = Dollar.scaleTo(this.points, Dollar.squareSize);
		this.points = Dollar.translateTo(this.points, Dollar.origin);
		this.vector = Dollar.vectorize(this.points);
	}
}