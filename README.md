# OneDollar SuperCollider Port

A SuperColloder (sclang) port of the JavaScript $1 Unistroke Recognizer.


## Installation

### github

Clone [this repository](https://github.com/dadaphl/oneDollar-supercollider.git) to the SuperCollider Extensions folder and recompile Class Library.

### Getting started

```
(
  p = [
    54@19,52@20,51@21,49@21,47@22,46@23,44@24,43@24,
    36@36,33@49,37@62,49@66,58@57,61@44,57@31,45@28
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
