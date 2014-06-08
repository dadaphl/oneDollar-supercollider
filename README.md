# OneDollar SuperCollider Port

A SuperColloder (sclang) port of the JavaScript $1 Unistroke Recognizer.


## Installation

### github

Clone [this repository](https://github.com/dadaphl/oneDollar-supercollider.git) to the SuperCollider Extensions folder and recompile Class Library.

### Getting started

```
(
  p = [
  	 Point.new( 54, 19 )
  	,Point.new( 52, 20 )
  	,Point.new( 51, 21 )
  	,Point.new( 49, 21 )
  	,Point.new( 47, 22 )
  	,Point.new( 46, 23 )
  	,Point.new( 44, 24 )
  	,Point.new( 43, 24 )
  	,Point.new( 36, 36 )
  	,Point.new( 33, 49 )
  	,Point.new( 37, 62 )
  	,Point.new( 49, 66 )
  	,Point.new( 58, 57 )
  	,Point.new( 61, 44 )
  	,Point.new( 57, 31 )
  	,Point.new( 45, 28 )
  ];
  d = DollarRecognizer.new ;
  r = d.recognize( p , false);
  [r.name, r.score].postln
)
```

Please checkout examples from the Examples directory

# License

Information about the $1 project can be found [here](http://depts.washington.edu/aimgroup/proj/dollar/).

Original Licence included with the source files.
