
class Curric(var name: String, var grade: String, var creditHours: Double){
    fun printDetails(){
        println("$name\nClass grade: $grade\n")
    }
}
//Pretty simple class.

fun query(question: String): String{
    var response = ""
    while (true){
        try{
            print(question)
            response = readLine() ?: "exc"
            if (response == "exc"){throw IllegalArgumentException()}
            break
        }
        catch(exception: IllegalArgumentException){println("Caught exception"); continue}
    }
    return response
}
//Takes a response and nullchecks it, then returns it if not null

fun createCurric(): Curric{
    var curricName: String
    var curricGrade: String
    var curricCreditHours: Double
    while (true){
        try{
            curricName = query(question = "Class name:\t")
            val curricGradeStore = query(question = "Class grade (letter):\t")
            curricGrade = if (curricGradeStore.capitalize() in listOf<String>("A", "A-", "B+", "B", "B-", "C+", "C-", "D+", "D", "D-", "F")){curricGradeStore.capitalize()}else{throw IllegalArgumentException()}
            val curricHoursStore = query(question = "Amount of credit hours the class is worth:\t")
            curricCreditHours = if (curricHoursStore.toDouble() !in 0.0..5.0){throw IllegalArgumentException()}else{curricHoursStore.toDouble()} //If conditional is met, the var is assigned the bracketed value.
            break
        }
        catch(exception: IllegalArgumentException){println("Invalid response."); continue}
    }
    val createdCurric = Curric(name = curricName, grade = curricGrade, creditHours = curricCreditHours)
    println("Created Curric ${createdCurric.name}.")
    return createdCurric
}
//Runs query() for all of the attributes needed for a Curric() and then creates it.

val gradeList = mutableListOf<Curric>() //List of Curric objects.

fun calculateGrades(grades: MutableList<Curric>): Double{
    val gradePointRanges = mutableMapOf<String, Double>("A" to 4.00, "A-" to 3.70, "B+" to 3.30, "B" to 3.00, "B-" to 2.70, "C+" to 2.30, "C" to 2.00, "C-" to 1.70, "D+" to 1.30, "D" to 1.00, "D-" to .70, "F" to 0.00)
    var attemptedCreditHours = 0.0
    var totalGradePoints = 0.0
    for (curricObject in gradeList){
        val gradePoints = gradePointRanges[curricObject.grade] ?: 0.0
        totalGradePoints += (gradePoints * curricObject.creditHours)
        attemptedCreditHours += curricObject.creditHours
    }
    return (totalGradePoints / attemptedCreditHours)
}
//Tabulates grade points and credit hours, then returns the GPA.


fun main(){
    var iterations: Int
    while (true) {
        print("How many classes are you taking?\n>\t")
        try{
            val iterations_str = readLine() ?: ""
            iterations = iterations_str.toInt()
            break
        }
        catch (exception: IllegalArgumentException){println("Invalid response."); continue}
    }
    for (i in 1..iterations){
        val createdClass = createCurric()
        gradeList.add(createdClass)
    }
    var grades = ""
    for (item in gradeList){
        grades += "${item.name.capitalize()}: ${item.grade}\n"
    }
    grades += "GPA: ${calculateGrades(gradeList)}\n"
    println(grades)
}
//Handles Curric creation and prints grades + GPA.