JC = javac
JFLAGS = -Xlint
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
					FileIO.java\
					DSAGraph.java\
					DSAGraphNode.java\
					DSAGraphEdge.java\
					DSAEdgeWeight.java\
					DSALinkedList.java\
					Country.java\
					State.java\
					Location.java\
					Menu.java


default: classes

classes: $(CLASSES:.java=.class)

run:

	java Menu

clean:
		$(RM) *.class
