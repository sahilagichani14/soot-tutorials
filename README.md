# Soot Tutorials:
- X

## Body Transformers
1. #### Local Splitter
```
java -cp soot.jar soot.Main -cp . -pp -dump-body jb.ls Test

soot.jar: you can build the soot.jar by cloning the soot (https://github.com/soot-oss/soot) and building locally.
-cp: lets you specify the class path: e.g. "." for the current folder
-dump-body: Dump the internal representation of each method before and after phase phaseName
jb.ls: an example bodyInterceptor, e.g. phaseName for local splitter
Test: compiled class file named Test

javac Target.java
java -cp sootclasses-trunk-jar-with-dependencies.jar soot.Main -cp . -pp -dump-body jb.ls Target -p jb.ls enabled:false

```