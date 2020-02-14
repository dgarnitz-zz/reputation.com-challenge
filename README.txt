DEPENDENCIES:
-OpenJDK 13
-Gradle 5.2.1

Gradle imports Jettison and libphonenumber: without them, you will need to download the jar files and configure the correct project structure.

_________________________________________________________________________________________________

RUN INSTRUCTIONS
all commands should be run from the project root - "reputation-challenge"
1) ./gradlew build
2) ./gradlew run --args '<input file path> <output file path>'
Where <input file path> is the ABSOLUTE path to a txt file and <output file path> is the ABSOLUTE path to location of the output plus the name of the output file you want to create.
Note that this program will not append to existing files, it will overwrite them.

_________________________________________________________________________________________________

DESIGN DECISIONS AND ASSUMPTIONS

Given that the purpose of the program is to serve as a utility that processes data rather than a data structure for storing and managing state, I choose to write all the functionality in one class with only static methods.
It would be straightforward to refactor it into an instantiable object.
I focused on decomposing the needed functionality into a series of relatively simple methods called sequentially.
While there is some repeated code, I choose not to elimiante all of it because it improves readability, which is important in this context.

While the program itself is simple, the use of Gradle greatly facilitated development by making it easier to add dependencies.
It also improves extensibility by making it easier to add unit tests.

I made a few assumptions in designing my program that hopefully do not cause it to fail on the test input. They are as follows:
1) colors contain only alphabetical characters or hyphens
2) the person has one last name when there are four inputs and the both the first and last name are contained in the first input
3) the last name always comes before the first name when there are five inputs
4) Valid US phone means only numbers that are connected, functioning US phone numbers, not just fake numbers in the correct format
