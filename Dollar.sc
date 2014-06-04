Dollar {
	classvar <numUnistrokes = 16;
	classvar <numPoints = 64;
	classvar <squareSize = 250.0;
	*origin { ^Point.new(0,0);}
	*diagonal { ^sqrt(squareSize * squareSize + squareSize * squareSize);}
	*halfDiagonal { ^0.5 * this.diagonal();}
	*angleRange { ^deg2Rad(45.0);}
	*anglePrecision {^deg2Rad(2.0);}
	*phi { ^0.5 * (-1.0 + sqrt(5.0));}
	*resample { arg points, n;
		var iI = PathLength(points) / (n - 1);
		var dD = 0.0;
		var newpoints = Array.new(points[0]);
		points.do ({ arg point, i;
			var d = distance(points[i - 1], points[i]);
			if ((dD + d) >= iI,
			{
				var qx = points[i - 1].x + ((iI - dD) / d) * (points[i].x - points[i - 1].x);
				var qy = points[i - 1].y + ((iI - dD) / d) * (points[i].y - points[i - 1].y);
				var q = Point.new(qx, qy);
				newpoints[newpoints.size] = q;
				points.splice(i, 0, q);
				dD = 0.0;
			}, {
				dD = dD + d;
			});
		});
		if (newpoints.size == n - 1, {
			newpoints[newpoints.size] = Point.new(points[points.size - 1].x, points[points.size - 1].y);
		});
		^newpoints;
	}
	*indicativeAngle { arg points;
		var c = centroid(points);
		^atan2(c.y - points[0].y, c.x - points[0].x);
	}
	*rotateBy { arg points, radians;
		var c = centroid(points);
		var cos = cos(radians);
		var sin = sin(radians);
		var newpoints = Array.new(points.size);
		points.do ({ arg point, i;
		// for (var i = 0; i < points.size; i++) {
			var qx = (points[i].x - c.x) * cos - (points[i].y - c.y) * sin + c.x;
			var qy = (points[i].x - c.x) * sin + (points[i].y - c.y) * cos + c.y;
			newpoints[newpoints.size] = Point.new(qx, qy);
		});
		^newpoints;
	}
	*scaleTo { arg points, size;
		var bB = boundingBox(points);
		var newpoints = Array.new();
		// for (var i = 0; i < points.size; i++) {
		points.do ({ arg point, i;
			var qx = points[i].x * (size / bB.width);
			var qy = points[i].y * (size / bB.height);
			newpoints[newpoints.size] = Point.new(qx, qy);
		});
		^newpoints;
	}
	*translateTo { arg points, pt;
		var c = centroid(points);
		var newpoints = Array.new();
		points.do ({ arg point, i;
		// for (var i = 0; i < points.size; i++) {
			var qx = points[i].x + pt.x - c.x;
			var qy = points[i].y + pt.y - c.y;
			newpoints[newpoints.size] = Point.new(qx, qy);
		});
		^newpoints;
	}
	*vectorize { arg points;
		var sum = 0.0;
		var vector = Array.new();
		var magnitude;
		points.do ({ arg point, i;
		// for (var i = 0; i < points.size; i++) {
			vector[vector.size] = points[i].x;
			vector[vector.size] = points[i].y;
			sum = sum + points[i].x * points[i].x + points[i].y * points[i].y;
		});
		magnitude = sqrt(sum);
		vector.do { arg i;
			vector[i] = vector[i] / magnitude;
		};
		^vector;
	}
	*optimalCosineDistance { arg v1, v2;
		var a = 0.0;
		var b = 0.0;
		var angle;
		v1.do ({ arg v1elem, n;
	        var i = n * 2;
			a = a + v1[i] * v2[i] + v1[i + 1] * v2[i + 1];
	                b = b + v1[i] * v2[i + 1] - v1[i + 1] * v2[i];
		});
		angle = atan(b / a);
		^acos(a * cos(angle) + b * sin(angle));
	}
	*distanceAtBestAngle { arg points, tT, a, b, threshold;
		var x1 = Phi * a + (1.0 - Phi) * b;
		var f1 = distanceAtAngle(points, tT, x1);
		var x2 = (1.0 - Phi) * a + Phi * b;
		var f2 = distanceAtAngle(points, tT, x2);
		while ( {abs(b - a) > threshold},
		{
			if (f1 < f2, {
				b = x2;
				x2 = x1;
				f2 = f1;
				x1 = Phi * a + (1.0 - Phi) * b;
				f1 = distanceAtAngle(points, tT, x1);
			} , {
				a = x1;
				x1 = x2;
				f1 = f2;
				x2 = (1.0 - Phi) * a + Phi * b;
				f2 = distanceAtAngle(points, tT, x2);
			})
		});
		^min(f1, f2);
	}
	*distanceAtAngle { arg points, tT, radians;
		var newpoints = rotateBy(points, radians);
		^pathDistance(newpoints, tT.points);
	}
	*centroid { arg points;
		var x = 0.0, y = 0.0;
		points.do ({ arg point, i;
			x = x +  points[i].x;
			y = y +  points[i].y;
		});
		x = x / points.size;
		y = y / points.size;
		^Point.new(x, y);
	}
	*boundingBox { arg points;
		var minX = (inf);
		var maxX = (-inf);
		var minY = (inf);
		var maxY = (-inf);
		points.do ({ arg point, i;
			minX = min(minX, points[i].x);
			minY = min(minY, points[i].y);
			maxX = max(maxX, points[i].x);
			maxY = max(maxY, points[i].y);
		});
		^Rect.new(minX, minY, maxX - minX, maxY - minY);
	}
	*pathDistance { arg pts1, pts2;
		var d = 0.0;
		pts1.do ({ arg pt1, i;
			d = d + distance(pts1[i], pts2[i]);});
		^d / pts1.size;
	}
	*pathLength { arg points;
		var d = 0.0;
		points.do ({ arg point, i;
			d = d + distance(points[i - 1], points[i]);});
		^d;
	}
	*distance { arg p1, p2;
		var dx = p2.x - p1.x;
		var dy = p2.y - p1.y;
		^sqrt(dx * dx + dy * dy);
	}
	*deg2Rad {arg d; ^(d * pi / 180.0); }
}
