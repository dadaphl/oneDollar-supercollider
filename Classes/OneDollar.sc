/**
 * The $1 Unistroke Recognizer (sclang version)
 *
 * Orginal JavaScript version by:
 *
 *   Jacob O. Wobbrock, Ph.D.
 *   The Information School
 *	 University of Washington
 *	 Seattle, WA 98195-2840
 *	 wobbrock@uw.edu
 *
 *	 Andrew D. Wilson, Ph.D.
 *	 Microsoft Research
 *	 One Microsoft Way
 *	 Redmond, WA 98052
 *	 awilson@microsoft.com
 *
 *	 Yang Li, Ph.D.
 *	 Department of Computer Science and Engineering
 * 	 University of Washington
 *   Seattle, WA 98195-2840
 *   yangli@cs.washington.edu
 *
 * sclang version by:
 *
 * 	 Philipp Klein
 *   University of Arts Berlin
 *   klein.philipp@gmail.com
 *
 *
 * The academic publication for the $1 recognizer, and what should be
 * used to cite it, is:
 *
 *	Wobbrock, J.O., Wilson, A.D. and Li, Y. (2007). Gestures without
 *	  libraries, toolkits or training: A $1 recognizer for user interface
 *	  prototypes. Proceedings of the ACM Symposium on User Interface
 *	  Software and Technology (UIST '07). Newport, Rhode Island (October
 *	  7-10, 2007). New York: ACM Press, pp. 159-168.
 *
 * The Protractor enhancement was separately published by Yang Li and programmed
 * here by Jacob O. Wobbrock:
 *
 *	Li, Y. (2010). Protractor: A fast and accurate gesture
 *	  recognizer. Proceedings of the ACM Conference on Human
 *	  Factors in Computing Systems (CHI '10). Atlanta, Georgia
 *	  (April 10-15, 2010). New York: ACM Press, pp. 2169-2172.
 *
 * This software is distributed under the "New BSD License" agreement:
 *
 * Copyright (C) 2007-2012, Jacob O. Wobbrock, Andrew D. Wilson and Yang Li.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    * Neither the names of the University of Washington nor Microsoft,
 *      nor the names of its contributors may be used to endorse or promote
 *      products derived from this software without specific prior written
 *      permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Jacob O. Wobbrock OR Andrew D. Wilson
 * OR Yang Li BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/
DollarUnistroke {
	var <>name,<>points,<>vector;

	*new { arg name, points;
		^super.newCopyArgs(name,points).init;
	}

	init {
		var radians;
		this.points = DollarRecognizer.resample(this.points, DollarRecognizer.numPoints);
		radians     = DollarRecognizer.indicativeAngle(this.points);
		this.points = DollarRecognizer.rotateBy(this.points,  neg(radians) );
		this.points = DollarRecognizer.scaleTo(this.points, DollarRecognizer.squareSize);
		this.points = DollarRecognizer.translateTo(this.points, DollarRecognizer.origin);
		this.vector = DollarRecognizer.vectorize(this.points);
	}
}

DollarResult {
	var <>name, <>score;

	*new { arg name, score;
		^super.newCopyArgs(name,score);
	}
}

DollarRecognizer {
	var <>unistrokes;
	classvar <>numPoints, <>squareSize, <>origin, <>diagonal, <>halfDiagonal, <>angleRange, <>anglePrecision, <>phi;

	*new {
		^super.new.init;
	}

	*initClass {
		this.numPoints = 64;
		this.squareSize = 250.0;
		this.origin = Point.new(0,0);
		this.diagonal = sqrt(this.squareSize * this.squareSize + this.squareSize * this.squareSize);
		this.halfDiagonal = 0.5 * this.diagonal;
		this.angleRange = degrad(45.0);
		this.anglePrecision = degrad(2.0);
		this.phi = 0.5 * (-1.0 + sqrt(5.0));
	}
	init {
		this.unistrokes = [
             DollarUnistroke.new("triangle", [Point.new(137,139),Point.new(135,141),Point.new(133,144),Point.new(132,146),Point.new(130,149),Point.new(128,151),Point.new(126,155),Point.new(123,160),Point.new(120,166),Point.new(116,171),Point.new(112,177),Point.new(107,183),Point.new(102,188),Point.new(100,191),Point.new(95,195),Point.new(90,199),Point.new(86,203),Point.new(82,206),Point.new(80,209),Point.new(75,213),Point.new(73,213),Point.new(70,216),Point.new(67,219),Point.new(64,221),Point.new(61,223),Point.new(60,225),Point.new(62,226),Point.new(65,225),Point.new(67,226),Point.new(74,226),Point.new(77,227),Point.new(85,229),Point.new(91,230),Point.new(99,231),Point.new(108,232),Point.new(116,233),Point.new(125,233),Point.new(134,234),Point.new(145,233),Point.new(153,232),Point.new(160,233),Point.new(170,234),Point.new(177,235),Point.new(179,236),Point.new(186,237),Point.new(193,238),Point.new(198,239),Point.new(200,237),Point.new(202,239),Point.new(204,238),Point.new(206,234),Point.new(205,230),Point.new(202,222),Point.new(197,216),Point.new(192,207),Point.new(186,198),Point.new(179,189),Point.new(174,183),Point.new(170,178),Point.new(164,171),Point.new(161,168),Point.new(154,160),Point.new(148,155),Point.new(143,150),Point.new(138,148),Point.new(136,148)])
			,DollarUnistroke.new("x", [Point.new(87,142),Point.new(89,145),Point.new(91,148),Point.new(93,151),Point.new(96,155),Point.new(98,157),Point.new(100,160),Point.new(102,162),Point.new(106,167),Point.new(108,169),Point.new(110,171),Point.new(115,177),Point.new(119,183),Point.new(123,189),Point.new(127,193),Point.new(129,196),Point.new(133,200),Point.new(137,206),Point.new(140,209),Point.new(143,212),Point.new(146,215),Point.new(151,220),Point.new(153,222),Point.new(155,223),Point.new(157,225),Point.new(158,223),Point.new(157,218),Point.new(155,211),Point.new(154,208),Point.new(152,200),Point.new(150,189),Point.new(148,179),Point.new(147,170),Point.new(147,158),Point.new(147,148),Point.new(147,141),Point.new(147,136),Point.new(144,135),Point.new(142,137),Point.new(140,139),Point.new(135,145),Point.new(131,152),Point.new(124,163),Point.new(116,177),Point.new(108,191),Point.new(100,206),Point.new(94,217),Point.new(91,222),Point.new(89,225),Point.new(87,226),Point.new(87,224)])
			,DollarUnistroke.new("rectangle", [Point.new(78,149),Point.new(78,153),Point.new(78,157),Point.new(78,160),Point.new(79,162),Point.new(79,164),Point.new(79,167),Point.new(79,169),Point.new(79,173),Point.new(79,178),Point.new(79,183),Point.new(80,189),Point.new(80,193),Point.new(80,198),Point.new(80,202),Point.new(81,208),Point.new(81,210),Point.new(81,216),Point.new(82,222),Point.new(82,224),Point.new(82,227),Point.new(83,229),Point.new(83,231),Point.new(85,230),Point.new(88,232),Point.new(90,233),Point.new(92,232),Point.new(94,233),Point.new(99,232),Point.new(102,233),Point.new(106,233),Point.new(109,234),Point.new(117,235),Point.new(123,236),Point.new(126,236),Point.new(135,237),Point.new(142,238),Point.new(145,238),Point.new(152,238),Point.new(154,239),Point.new(165,238),Point.new(174,237),Point.new(179,236),Point.new(186,235),Point.new(191,235),Point.new(195,233),Point.new(197,233),Point.new(200,233),Point.new(201,235),Point.new(201,233),Point.new(199,231),Point.new(198,226),Point.new(198,220),Point.new(196,207),Point.new(195,195),Point.new(195,181),Point.new(195,173),Point.new(195,163),Point.new(194,155),Point.new(192,145),Point.new(192,143),Point.new(192,138),Point.new(191,135),Point.new(191,133),Point.new(191,130),Point.new(190,128),Point.new(188,129),Point.new(186,129),Point.new(181,132),Point.new(173,131),Point.new(162,131),Point.new(151,132),Point.new(149,132),Point.new(138,132),Point.new(136,132),Point.new(122,131),Point.new(120,131),Point.new(109,130),Point.new(107,130),Point.new(90,132),Point.new(81,133),Point.new(76,133)])
			,DollarUnistroke.new("circle", [Point.new(127,141),Point.new(124,140),Point.new(120,139),Point.new(118,139),Point.new(116,139),Point.new(111,140),Point.new(109,141),Point.new(104,144),Point.new(100,147),Point.new(96,152),Point.new(93,157),Point.new(90,163),Point.new(87,169),Point.new(85,175),Point.new(83,181),Point.new(82,190),Point.new(82,195),Point.new(83,200),Point.new(84,205),Point.new(88,213),Point.new(91,216),Point.new(96,219),Point.new(103,222),Point.new(108,224),Point.new(111,224),Point.new(120,224),Point.new(133,223),Point.new(142,222),Point.new(152,218),Point.new(160,214),Point.new(167,210),Point.new(173,204),Point.new(178,198),Point.new(179,196),Point.new(182,188),Point.new(182,177),Point.new(178,167),Point.new(170,150),Point.new(163,138),Point.new(152,130),Point.new(143,129),Point.new(140,131),Point.new(129,136),Point.new(126,139)])
			,DollarUnistroke.new("check", [Point.new(91,185),Point.new(93,185),Point.new(95,185),Point.new(97,185),Point.new(100,188),Point.new(102,189),Point.new(104,190),Point.new(106,193),Point.new(108,195),Point.new(110,198),Point.new(112,201),Point.new(114,204),Point.new(115,207),Point.new(117,210),Point.new(118,212),Point.new(120,214),Point.new(121,217),Point.new(122,219),Point.new(123,222),Point.new(124,224),Point.new(126,226),Point.new(127,229),Point.new(129,231),Point.new(130,233),Point.new(129,231),Point.new(129,228),Point.new(129,226),Point.new(129,224),Point.new(129,221),Point.new(129,218),Point.new(129,212),Point.new(129,208),Point.new(130,198),Point.new(132,189),Point.new(134,182),Point.new(137,173),Point.new(143,164),Point.new(147,157),Point.new(151,151),Point.new(155,144),Point.new(161,137),Point.new(165,131),Point.new(171,122),Point.new(174,118),Point.new(176,114),Point.new(177,112),Point.new(177,114),Point.new(175,116),Point.new(173,118)])
			,DollarUnistroke.new("caret", [Point.new(79,245),Point.new(79,242),Point.new(79,239),Point.new(80,237),Point.new(80,234),Point.new(81,232),Point.new(82,230),Point.new(84,224),Point.new(86,220),Point.new(86,218),Point.new(87,216),Point.new(88,213),Point.new(90,207),Point.new(91,202),Point.new(92,200),Point.new(93,194),Point.new(94,192),Point.new(96,189),Point.new(97,186),Point.new(100,179),Point.new(102,173),Point.new(105,165),Point.new(107,160),Point.new(109,158),Point.new(112,151),Point.new(115,144),Point.new(117,139),Point.new(119,136),Point.new(119,134),Point.new(120,132),Point.new(121,129),Point.new(122,127),Point.new(124,125),Point.new(126,124),Point.new(129,125),Point.new(131,127),Point.new(132,130),Point.new(136,139),Point.new(141,154),Point.new(145,166),Point.new(151,182),Point.new(156,193),Point.new(157,196),Point.new(161,209),Point.new(162,211),Point.new(167,223),Point.new(169,229),Point.new(170,231),Point.new(173,237),Point.new(176,242),Point.new(177,244),Point.new(179,250),Point.new(181,255),Point.new(182,257)])
			,DollarUnistroke.new("zig-zag", [Point.new(307,216),Point.new(333,186),Point.new(356,215),Point.new(375,186),Point.new(399,216),Point.new(418,186)])
			,DollarUnistroke.new("arrow", [Point.new(68,222),Point.new(70,220),Point.new(73,218),Point.new(75,217),Point.new(77,215),Point.new(80,213),Point.new(82,212),Point.new(84,210),Point.new(87,209),Point.new(89,208),Point.new(92,206),Point.new(95,204),Point.new(101,201),Point.new(106,198),Point.new(112,194),Point.new(118,191),Point.new(124,187),Point.new(127,186),Point.new(132,183),Point.new(138,181),Point.new(141,180),Point.new(146,178),Point.new(154,173),Point.new(159,171),Point.new(161,170),Point.new(166,167),Point.new(168,167),Point.new(171,166),Point.new(174,164),Point.new(177,162),Point.new(180,160),Point.new(182,158),Point.new(183,156),Point.new(181,154),Point.new(178,153),Point.new(171,153),Point.new(164,153),Point.new(160,153),Point.new(150,154),Point.new(147,155),Point.new(141,157),Point.new(137,158),Point.new(135,158),Point.new(137,158),Point.new(140,157),Point.new(143,156),Point.new(151,154),Point.new(160,152),Point.new(170,149),Point.new(179,147),Point.new(185,145),Point.new(192,144),Point.new(196,144),Point.new(198,144),Point.new(200,144),Point.new(201,147),Point.new(199,149),Point.new(194,157),Point.new(191,160),Point.new(186,167),Point.new(180,176),Point.new(177,179),Point.new(171,187),Point.new(169,189),Point.new(165,194),Point.new(164,196)])
			,DollarUnistroke.new("left square bracket", [Point.new(140,124),Point.new(138,123),Point.new(135,122),Point.new(133,123),Point.new(130,123),Point.new(128,124),Point.new(125,125),Point.new(122,124),Point.new(120,124),Point.new(118,124),Point.new(116,125),Point.new(113,125),Point.new(111,125),Point.new(108,124),Point.new(106,125),Point.new(104,125),Point.new(102,124),Point.new(100,123),Point.new(98,123),Point.new(95,124),Point.new(93,123),Point.new(90,124),Point.new(88,124),Point.new(85,125),Point.new(83,126),Point.new(81,127),Point.new(81,129),Point.new(82,131),Point.new(82,134),Point.new(83,138),Point.new(84,141),Point.new(84,144),Point.new(85,148),Point.new(85,151),Point.new(86,156),Point.new(86,160),Point.new(86,164),Point.new(86,168),Point.new(87,171),Point.new(87,175),Point.new(87,179),Point.new(87,182),Point.new(87,186),Point.new(88,188),Point.new(88,195),Point.new(88,198),Point.new(88,201),Point.new(88,207),Point.new(89,211),Point.new(89,213),Point.new(89,217),Point.new(89,222),Point.new(88,225),Point.new(88,229),Point.new(88,231),Point.new(88,233),Point.new(88,235),Point.new(89,237),Point.new(89,240),Point.new(89,242),Point.new(91,241),Point.new(94,241),Point.new(96,240),Point.new(98,239),Point.new(105,240),Point.new(109,240),Point.new(113,239),Point.new(116,240),Point.new(121,239),Point.new(130,240),Point.new(136,237),Point.new(139,237),Point.new(144,238),Point.new(151,237),Point.new(157,236),Point.new(159,237)])
			,DollarUnistroke.new("right square bracket", [Point.new(112,138),Point.new(112,136),Point.new(115,136),Point.new(118,137),Point.new(120,136),Point.new(123,136),Point.new(125,136),Point.new(128,136),Point.new(131,136),Point.new(134,135),Point.new(137,135),Point.new(140,134),Point.new(143,133),Point.new(145,132),Point.new(147,132),Point.new(149,132),Point.new(152,132),Point.new(153,134),Point.new(154,137),Point.new(155,141),Point.new(156,144),Point.new(157,152),Point.new(158,161),Point.new(160,170),Point.new(162,182),Point.new(164,192),Point.new(166,200),Point.new(167,209),Point.new(168,214),Point.new(168,216),Point.new(169,221),Point.new(169,223),Point.new(169,228),Point.new(169,231),Point.new(166,233),Point.new(164,234),Point.new(161,235),Point.new(155,236),Point.new(147,235),Point.new(140,233),Point.new(131,233),Point.new(124,233),Point.new(117,235),Point.new(114,238),Point.new(112,238)])
			,DollarUnistroke.new("v", [Point.new(89,164),Point.new(90,162),Point.new(92,162),Point.new(94,164),Point.new(95,166),Point.new(96,169),Point.new(97,171),Point.new(99,175),Point.new(101,178),Point.new(103,182),Point.new(106,189),Point.new(108,194),Point.new(111,199),Point.new(114,204),Point.new(117,209),Point.new(119,214),Point.new(122,218),Point.new(124,222),Point.new(126,225),Point.new(128,228),Point.new(130,229),Point.new(133,233),Point.new(134,236),Point.new(136,239),Point.new(138,240),Point.new(139,242),Point.new(140,244),Point.new(142,242),Point.new(142,240),Point.new(142,237),Point.new(143,235),Point.new(143,233),Point.new(145,229),Point.new(146,226),Point.new(148,217),Point.new(149,208),Point.new(149,205),Point.new(151,196),Point.new(151,193),Point.new(153,182),Point.new(155,172),Point.new(157,165),Point.new(159,160),Point.new(162,155),Point.new(164,150),Point.new(165,148),Point.new(166,146)])
			,DollarUnistroke.new("delete", [Point.new(123,129),Point.new(123,131),Point.new(124,133),Point.new(125,136),Point.new(127,140),Point.new(129,142),Point.new(133,148),Point.new(137,154),Point.new(143,158),Point.new(145,161),Point.new(148,164),Point.new(153,170),Point.new(158,176),Point.new(160,178),Point.new(164,183),Point.new(168,188),Point.new(171,191),Point.new(175,196),Point.new(178,200),Point.new(180,202),Point.new(181,205),Point.new(184,208),Point.new(186,210),Point.new(187,213),Point.new(188,215),Point.new(186,212),Point.new(183,211),Point.new(177,208),Point.new(169,206),Point.new(162,205),Point.new(154,207),Point.new(145,209),Point.new(137,210),Point.new(129,214),Point.new(122,217),Point.new(118,218),Point.new(111,221),Point.new(109,222),Point.new(110,219),Point.new(112,217),Point.new(118,209),Point.new(120,207),Point.new(128,196),Point.new(135,187),Point.new(138,183),Point.new(148,167),Point.new(157,153),Point.new(163,145),Point.new(165,142),Point.new(172,133),Point.new(177,127),Point.new(179,127),Point.new(180,125)])
			,DollarUnistroke.new("left curly brace", [Point.new(150,116),Point.new(147,117),Point.new(145,116),Point.new(142,116),Point.new(139,117),Point.new(136,117),Point.new(133,118),Point.new(129,121),Point.new(126,122),Point.new(123,123),Point.new(120,125),Point.new(118,127),Point.new(115,128),Point.new(113,129),Point.new(112,131),Point.new(113,134),Point.new(115,134),Point.new(117,135),Point.new(120,135),Point.new(123,137),Point.new(126,138),Point.new(129,140),Point.new(135,143),Point.new(137,144),Point.new(139,147),Point.new(141,149),Point.new(140,152),Point.new(139,155),Point.new(134,159),Point.new(131,161),Point.new(124,166),Point.new(121,166),Point.new(117,166),Point.new(114,167),Point.new(112,166),Point.new(114,164),Point.new(116,163),Point.new(118,163),Point.new(120,162),Point.new(122,163),Point.new(125,164),Point.new(127,165),Point.new(129,166),Point.new(130,168),Point.new(129,171),Point.new(127,175),Point.new(125,179),Point.new(123,184),Point.new(121,190),Point.new(120,194),Point.new(119,199),Point.new(120,202),Point.new(123,207),Point.new(127,211),Point.new(133,215),Point.new(142,219),Point.new(148,220),Point.new(151,221)])
			,DollarUnistroke.new("right curly brace", [Point.new(117,132),Point.new(115,132),Point.new(115,129),Point.new(117,129),Point.new(119,128),Point.new(122,127),Point.new(125,127),Point.new(127,127),Point.new(130,127),Point.new(133,129),Point.new(136,129),Point.new(138,130),Point.new(140,131),Point.new(143,134),Point.new(144,136),Point.new(145,139),Point.new(145,142),Point.new(145,145),Point.new(145,147),Point.new(145,149),Point.new(144,152),Point.new(142,157),Point.new(141,160),Point.new(139,163),Point.new(137,166),Point.new(135,167),Point.new(133,169),Point.new(131,172),Point.new(128,173),Point.new(126,176),Point.new(125,178),Point.new(125,180),Point.new(125,182),Point.new(126,184),Point.new(128,187),Point.new(130,187),Point.new(132,188),Point.new(135,189),Point.new(140,189),Point.new(145,189),Point.new(150,187),Point.new(155,186),Point.new(157,185),Point.new(159,184),Point.new(156,185),Point.new(154,185),Point.new(149,185),Point.new(145,187),Point.new(141,188),Point.new(136,191),Point.new(134,191),Point.new(131,192),Point.new(129,193),Point.new(129,195),Point.new(129,197),Point.new(131,200),Point.new(133,202),Point.new(136,206),Point.new(139,211),Point.new(142,215),Point.new(145,220),Point.new(147,225),Point.new(148,231),Point.new(147,239),Point.new(144,244),Point.new(139,248),Point.new(134,250),Point.new(126,253),Point.new(119,253),Point.new(115,253)])
			,DollarUnistroke.new("star", [Point.new(75,250),Point.new(75,247),Point.new(77,244),Point.new(78,242),Point.new(79,239),Point.new(80,237),Point.new(82,234),Point.new(82,232),Point.new(84,229),Point.new(85,225),Point.new(87,222),Point.new(88,219),Point.new(89,216),Point.new(91,212),Point.new(92,208),Point.new(94,204),Point.new(95,201),Point.new(96,196),Point.new(97,194),Point.new(98,191),Point.new(100,185),Point.new(102,178),Point.new(104,173),Point.new(104,171),Point.new(105,164),Point.new(106,158),Point.new(107,156),Point.new(107,152),Point.new(108,145),Point.new(109,141),Point.new(110,139),Point.new(112,133),Point.new(113,131),Point.new(116,127),Point.new(117,125),Point.new(119,122),Point.new(121,121),Point.new(123,120),Point.new(125,122),Point.new(125,125),Point.new(127,130),Point.new(128,133),Point.new(131,143),Point.new(136,153),Point.new(140,163),Point.new(144,172),Point.new(145,175),Point.new(151,189),Point.new(156,201),Point.new(161,213),Point.new(166,225),Point.new(169,233),Point.new(171,236),Point.new(174,243),Point.new(177,247),Point.new(178,249),Point.new(179,251),Point.new(180,253),Point.new(180,255),Point.new(179,257),Point.new(177,257),Point.new(174,255),Point.new(169,250),Point.new(164,247),Point.new(160,245),Point.new(149,238),Point.new(138,230),Point.new(127,221),Point.new(124,220),Point.new(112,212),Point.new(110,210),Point.new(96,201),Point.new(84,195),Point.new(74,190),Point.new(64,182),Point.new(55,175),Point.new(51,172),Point.new(49,170),Point.new(51,169),Point.new(56,169),Point.new(66,169),Point.new(78,168),Point.new(92,166),Point.new(107,164),Point.new(123,161),Point.new(140,162),Point.new(156,162),Point.new(171,160),Point.new(173,160),Point.new(186,160),Point.new(195,160),Point.new(198,161),Point.new(203,163),Point.new(208,163),Point.new(206,164),Point.new(200,167),Point.new(187,172),Point.new(174,179),Point.new(172,181),Point.new(153,192),Point.new(137,201),Point.new(123,211),Point.new(112,220),Point.new(99,229),Point.new(90,237),Point.new(80,244),Point.new(73,250),Point.new(69,254),Point.new(69,252)])
			,DollarUnistroke.new("pigtail", [Point.new(81,219),Point.new(84,218),Point.new(86,220),Point.new(88,220),Point.new(90,220),Point.new(92,219),Point.new(95,220),Point.new(97,219),Point.new(99,220),Point.new(102,218),Point.new(105,217),Point.new(107,216),Point.new(110,216),Point.new(113,214),Point.new(116,212),Point.new(118,210),Point.new(121,208),Point.new(124,205),Point.new(126,202),Point.new(129,199),Point.new(132,196),Point.new(136,191),Point.new(139,187),Point.new(142,182),Point.new(144,179),Point.new(146,174),Point.new(148,170),Point.new(149,168),Point.new(151,162),Point.new(152,160),Point.new(152,157),Point.new(152,155),Point.new(152,151),Point.new(152,149),Point.new(152,146),Point.new(149,142),Point.new(148,139),Point.new(145,137),Point.new(141,135),Point.new(139,135),Point.new(134,136),Point.new(130,140),Point.new(128,142),Point.new(126,145),Point.new(122,150),Point.new(119,158),Point.new(117,163),Point.new(115,170),Point.new(114,175),Point.new(117,184),Point.new(120,190),Point.new(125,199),Point.new(129,203),Point.new(133,208),Point.new(138,213),Point.new(145,215),Point.new(155,218),Point.new(164,219),Point.new(166,219),Point.new(177,219),Point.new(182,218),Point.new(192,216),Point.new(196,213),Point.new(199,212),Point.new(201,211)])
		];
	}
	recognize { arg points, useProtractor;
		var radians, vector;
		var b = inf;
		var u = -1;
		var s;
		points = this.class.resample(points, this.class.numPoints);
		radians = this.class.indicativeAngle(points);
		points = this.class.rotateBy(points, neg(radians));
		points = this.class.scaleTo(points, this.class.squareSize);
		points = this.class.translateTo(points, this.class.origin);
		vector = this.class.vectorize(points); // for Protractor

		this.unistrokes.size.do({ arg i;
			var d;
			if (useProtractor, {
				d = this.class.optimalCosineDistance(this.unistrokes[i].vector, vector);
			},{
				d = this.class.distanceAtBestAngle(points, this.unistrokes[i], neg(this.class.angleRange), this.class.angleRange, this.class.anglePrecision);
			}); // for Protractor
			if (d < b, {
				b = d; // best (least) distance
				u = i; // unistroke
			});
		});
		if (u == -1,{
			^DollarResult.new("No match.", 0.0);
		}, {
			s = -1;
			if(useProtractor,{ s = 1.0 / b}, { s = 1.0 - (b / this.class.halfDiagonal)});
			^DollarResult.new(this.unistrokes[u].name, s);
		});
	}

	addgesture { arg name, points;
		var num = 0;
		this.unistrokes[this.unistrokes.size] = DollarUnistroke.new(name, points); // append unistroke.new
		this.unistrokes.size.do({ arg unistroke, i;
			if (this.unistrokes[i].name == name{
				num= num + 1;
			});
		});
		^num;
	}

/*	deleteusergestures {
		this.unistrokes.size = Numunistrokes; // clear any beyond the original set
		^Numunistrokes;
	}*/

	*resample { arg points, n;
		var iI = this.pathLength(points) / (n - 1);
		var dD = 0.0;
		var newpoints;
		var i;
		newpoints = [points[0]];
		i = 1;
		while({i < points.size},{
			var d = this.distance(points[i - 1], points[i]);
			if ((dD + d) >= iI,{
				var qx = points[i - 1].x + (((iI - dD) / d) * (points[i].x - points[i - 1].x));
				var qy = points[i - 1].y + (((iI - dD) / d) * (points[i].y - points[i - 1].y));
				var q = Point.new(qx, qy);
				newpoints = newpoints.add(q);
				points = points.insert(i, q);
				dD = 0.0;
			}, {
				dD = dD + d;
			});
			i = i + 1;
		});
		if (newpoints.size == (n - 1), {
			newpoints = newpoints.add(Point.new(points[points.size - 1].x, points[points.size - 1].y));
		});
		^newpoints;
	}

	*indicativeAngle { arg points;
		var c = this.centroid(points);
		^(atan2(c.y - points[0].y, c.x - points[0].x));
	}

	*rotateBy { arg points, radians;
		var c = this.centroid(points);
		var cos = cos(radians);
		var sin = sin(radians);
		var newpoints = Array.new();
		points.size.do ({ arg i;
			var qx = (points[i].x - c.x) * cos - ((points[i].y - c.y) * sin) + c.x;
			var qy = (points[i].x - c.x) * sin + ((points[i].y - c.y) * cos) + c.y;
			newpoints = newpoints.add(Point.new(qx, qy));
		});
		^newpoints;
	}
	*scaleTo { arg points, size;
		var bB = this.boundingBox(points), newpoints = Array.new();
		points.size.do ({ arg i;
			var qx = points[i].x * (size / bB.width);
			var qy = points[i].y * (size / bB.height);
			newpoints = newpoints.add(Point.new(qx, qy));
		});
		^newpoints;
	}
	*translateTo { arg points, pt;
		var c = this.centroid(points);
		var newpoints = Array.new();
		points.size.do ({ arg i;
			var qx = points[i].x + pt.x - c.x;
			var qy = points[i].y + pt.y - c.y;
			newpoints = newpoints.add(Point.new(qx, qy));
		});
		^newpoints;
	}
	*vectorize { arg points;
		var sum = 0.0;
		var vector = Array.new();
		var magnitude;
		points.size.do ({ arg i;
			vector = vector.add(points[i].x);
			vector = vector.add(points[i].y);
			sum = sum + ((points[i].x * points[i].x) + (points[i].y * points[i].y));
		});
		magnitude = sqrt(sum);
		vector.size.do { arg i;
			vector[i] = vector[i] / magnitude;
		};
		^vector;
	}
	*optimalCosineDistance { arg v1, v2;
		var a = 0.0;
		var b = 0.0;
		var angle;
		0.forBy((v1.size - 1),2,{ arg i;
			a = a + (v1[i] * v2[i]) + (v1[i + 1] * v2[i + 1]);
			b = b + (v1[i] * v2[i + 1]) - (v1[i + 1] * v2[i]);
		});
		angle = atan(b / a);
		^(acos(a * cos(angle) + (b * sin(angle))));
	}
	*distanceAtBestAngle { arg points, tT, a, b, threshold;
		var x1 = this.phi * a + ((1.0 - this.phi) * b);
		var f1 = this.distanceAtAngle(points, tT, x1);
		var x2 = (1.0 - this.phi) * a + (this.phi * b);
		var f2 = this.distanceAtAngle(points, tT, x2);
		while ( {abs(b - a) > threshold},
		{
			if (f1 < f2, {
				b = x2;
				x2 = x1;
				f2 = f1;
				x1 = this.phi * a + ((1.0 - this.phi) * b);
				f1 = this.distanceAtAngle(points, tT, x1);
			} , {
				a = x1;
				x1 = x2;
				f1 = f2;
				x2 = (1.0 - this.phi) * a + (this.phi * b);
				f2 = this.distanceAtAngle(points, tT, x2);
			});
		});
		^min(f1, f2);
	}
	*distanceAtAngle { arg points, tT, radians;
		var newpoints = this.rotateBy(points, radians);
		^this.pathDistance(newpoints, tT.points);
	}
	*centroid { arg points;
		var x = 0.0, y = 0.0;
		points.size.do ({ arg i;
			x = x +  points[i].x;
			y = y +  points[i].y;
		});
		x = x / points.size;
		y = y / points.size;
		^Point.new(x, y);
	}
	*boundingBox { arg points;
		var minX = inf;
		var maxX = neg(inf);
		var minY = inf;
		var maxY = neg(inf);
		points.size.do ({ arg i;
			minX = min(minX, points[i].x);
			minY = min(minY, points[i].y);
			minX = max(minX, points[i].x);
			maxY = max(maxY, points[i].y);
		});
		^Rect.new(minX, minY, maxX - minX, maxY - minY);
	}
	*pathDistance { arg pts1, pts2;
		var d = 0.0;
		pts1.size.do ({ arg i;
			d = d + this.distance(pts1[i], pts2[i]);
		});
		^(d / pts1.size);
	}
	*pathLength { arg points;
		var d = 0.0;
		1.for((points.size - 1),{ arg i;
			d = d + this.distance(points[i - 1], points[i]);
		});
		^d;
	}
	*distance { arg p1, p2;
		var dx = p2.x - p1.x;
		var dy = p2.y - p1.y;
		^sqrt( (dx * dx) + (dy * dy));
	}
}
