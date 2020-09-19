JAVAC=	/usr/bin/javac
SRCDIR=	src
BINDIR=	bin 
docdir=	./docs

.SUFFIXES:	.java	.class

.java.class:
	$(JAVAC)	$<

default:
	$(JAVAC)	-d	(BINDIR)	$(SRCDIR)/*.java
clean:
	rm	$(BINDIR)/*.class
run1:
	java	-cp	bin Flow
run2:
	java	-cp	bin Flow	"\src\SampleData\largesample_in.txt"
run3:
	java	-cp	bin	FlowPanel
run4:
	java	-cp	bin	Terrain
run5:
	java	-cp	bin	Water
docs:
	javadoc	-d	$(docdir)	$(SRCDIR)

cleandocds:
	rm	-r	docs/*


