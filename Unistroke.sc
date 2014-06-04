Unistroke {
	var <>name,<>points,<>vector;
	*new { arg nname, ppoints;
		var radians;
		name = nname;
		this.points = DollarRecognizer.resample(ppoints, DollarRecognizer.numpoints);
		radians     = DollarRecognizer.indicativeAngle(this.points);
		this.points = DollarRecognizer.rotateBy(this.points,  -1 * (DollarRecognizer.radians) );
		this.points = DollarRecognizer.scaleTo(this.points, DollarRecognizer.squareSize);
		this.points = DollarRecognizer.translateTo(this.points, DollarRecognizer.origin);
		this.vector = DollarRecognizer.vectorize(this.points);
	}
}