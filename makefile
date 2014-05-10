JFLAGS = -g -cp $(BIN) -d $(BIN)
JC = javac
BIN = bin
SRC = src

MazeSolver : $(BIN)/MazeSolver.class $(BIN)/MazeSolverGUI.class
	echo "java -cp bin MazeSolver" > MazeSolver
	chmod 755 MazeSolver

$(BIN)/MazeSolver.class : $(SRC)/MazeSolver.java $(BIN)/MazeSolverGUI.class
	$(JC) $(JFLAGS) $(SRC)/MazeSolver.java

$(BIN)/MazeSolverGUI.class : $(SRC)/MazeSolverGUI.java $(BIN)/Solver.class $(BIN)/Square.class
	$(JC) $(JFLAGS) $(SRC)/MazeSolverGUI.java

$(BIN)/Solver.class : $(SRC)/Solver.java $(BIN)/Stack.class $(BIN)/Queue.class
	$(JC) $(JFLAGS) $(SRC)/Solver.java

$(BIN)/Stack.class : $(SRC)/Stack.java $(BIN)/LinkedList.class
	$(JC) $(JFLAGS) $(SRC)/Stack.java

$(BIN)/Queue.class : $(SRC)/Queue.java $(BIN)/LinkedList.class
	$(JC) $(JFLAGS) $(SRC)/Queue.java

$(BIN)/LinkedList.class : $(SRC)/LinkedList.java
	$(JC) $(JFLAGS) $(SRC)/LinkedList.java

$(BIN)/Square.class : $(SRC)/Square.java
	$(JC) $(JFLAGS) $(SRC)/Square.java

clean :
	$(RM) $(BIN)/*.class
	rm MazeSolver
